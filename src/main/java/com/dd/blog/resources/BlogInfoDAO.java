package com.dd.blog.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;

@Repository
public class BlogInfoDAO {
	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	BlogInfoCache blogInfoCache;

	public boolean insert(BlogInfoVO blogInfoVO) {
		try {
			BasicDBObject basicDBObject = new BasicDBObject();
			List<SubMenuContent> subMenuContents = blogInfoVO.getSubMenuContents();
			basicDBObject.put("conetent_id", subMenuContents.get(0).getConetent_id());
			basicDBObject.put("content", subMenuContents.get(0).getContent());
			basicDBObject.put("content_header", subMenuContents.get(0).getContent_header());
			basicDBObject.put("contentheaderTag", subMenuContents.get(0).getContentHeaderTag());
			basicDBObject.put("created_date", subMenuContents.get(0).getCreated_date());
			basicDBObject.put("submenu_ref", subMenuContents.get(0).getSubmenu_ref());
			basicDBObject.put("menu_ref", subMenuContents.get(0).getMenu_ref());
			basicDBObject.put("postedBy", subMenuContents.get(0).getPostedBy());
			basicDBObject.put("themeImage", blogInfoVO.getImageContent());
			mongoOperations.save(basicDBObject, "submenucontent");
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean saveImage(byte[] imageContent, String imageName, String content_Id, boolean isThemeImage) {
		Query query = new Query();
		query.addCriteria(Criteria.where("imageName").is(imageName));
		List<Document> docs = mongoOperations.find(query, Document.class, "images");
		if (docs != null && !docs.isEmpty()) {
			System.out.println("Error Error Error Error Error Error Error.......... Duplicate key for the image");
			return false;
		}

		if (imageContent != null && !StringUtils.isEmpty(imageName)) {
			try {
				BasicDBObject basicDBObject = new BasicDBObject();
				basicDBObject.put("imageName", imageName);
				basicDBObject.put("imagecontent", imageContent);
				basicDBObject.put("content_id", content_Id);
				basicDBObject.put("isThemeImage", isThemeImage);
				mongoOperations.save(basicDBObject, "images");
			} catch (Exception e) {
				System.out.println(e);
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean saveComment(String comment, String name, String email, String website, String blogId) {
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.put("blogId", blogId);
		basicDBObject.put("comment", comment);
		basicDBObject.put("name", name);
		basicDBObject.put("email", email);
		basicDBObject.put("website", website);
		Date date = Calendar.getInstance().getTime();
		basicDBObject.put("date", date);
		try {
			mongoOperations.save(basicDBObject, "comment");
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	public boolean save(BlogInfoVO blogInfoVO) {

		List<SubMenuContent> subMenuContents = blogInfoVO.getSubMenuContents();
		if (subMenuContents != null && !subMenuContents.isEmpty()) {
			try {
				BasicDBObject basicDBObject = new BasicDBObject();
				Query query = new Query();
				query.addCriteria(Criteria.where("conetent_id").is(subMenuContents.get(0).getConetent_id()));
				Document doc = mongoOperations.findOne(query, Document.class, "submenucontent");
				if (doc != null) {
					for (String key : doc.keySet()) {
						if ("content".equalsIgnoreCase(key)
								&& !StringUtils.isEmpty(subMenuContents.get(0).getContent())) {
							basicDBObject.put(key, subMenuContents.get(0).getContent().trim());
						} else {
							basicDBObject.put(key, doc.get(key));
						}
					}
					mongoOperations.save(basicDBObject, "submenucontent");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean validate(String userName, String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(userName));
		query.addCriteria(Criteria.where("password").is(password));
		Document doc = mongoOperations.findOne(query, Document.class, "login");
		if (doc != null) {
			return true;
		} else {
			return false;
		}
	}

	public String replaceImageContent(String content) {
		Query query = new Query();
		List<Document> docs = mongoOperations.find(query, Document.class, "images");

		if (docs != null && !docs.isEmpty()) {
			for (Document document : docs) {
				if (content.contains(document.getString("imageName"))) {
					Binary imageData = document.get("imagecontent", Binary.class);
					byte[] imageByteData = imageData.getData();
					String base64Image = Base64.getEncoder().encodeToString(imageByteData);
					content = content.replace(document.getString("imageName"), "data:image/jpg;base64," + base64Image);
				}
			}
		}
		return content;
	}

	public BlogInfoVO getBlogInfo(String contentId, boolean isEditable) {
		BlogInfoVO blogInfoVO = new BlogInfoVO();
		List<SubMenuContent> subMenuContents = new ArrayList<SubMenuContent>();
		Query query = null;
		query = new Query();
		if (contentId != null) {
			query.addCriteria(Criteria.where("conetent_id").is(contentId));
		}
		List<Document> docs = mongoOperations.find(query, Document.class, "submenucontent");
		String submenu_ref = "";
		if (docs != null && !docs.isEmpty()) {
			for (Document document : docs) {
				SubMenuContent subMenuContent = new SubMenuContent();
				subMenuContent.setConetent_id(document.getString("conetent_id"));
				subMenuContent.setContent_header(document.getString("content_header"));
				subMenuContent.setContentHeaderTag(document.getString("contentheaderTag"));
				if (isEditable) {
					subMenuContent.setContent(document.getString("content"));
				} else {
					subMenuContent.setContent(replaceImageContent(document.getString("content")));
				}
				subMenuContent.setPostedBy(document.getString("postedBy"));
				subMenuContent.setMenu_ref(document.getString("menu_ref"));
				subMenuContent.setSubmenu_ref(document.getString("submenu_ref"));
				submenu_ref = document.getString("submenu_ref");
				subMenuContent.setDate(BlogInfoUtil.getStringDate(document.getDate("created_date")));
				Binary imageData = document.get("themeImage", Binary.class);
				if (imageData != null) {
					byte[] imageByteData = imageData.getData();
					blogInfoVO.setThemeimage(Base64.getEncoder().encodeToString(imageByteData));
				} else {
					File fi = new File(getClass().getClassLoader().getResource("oom.jpg").getFile());
					String fileContent = "";
					try {
						fileContent = Base64.getEncoder().encodeToString(Files.readAllBytes(fi.toPath()));
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					blogInfoVO.setThemeimage(fileContent);
				}
				subMenuContents.add(subMenuContent);
			}
		}

		if (!StringUtils.isEmpty(contentId)) {
			// Related Post
			query = new Query();
			query.addCriteria(Criteria.where("submenu_ref").is(submenu_ref));
			query.with(new Sort(Sort.Direction.DESC, "created_date"));
			query.limit(6);
			List<Document> relatedDocs = mongoOperations.find(query, Document.class, "submenucontent");
			if (relatedDocs != null && !relatedDocs.isEmpty()) {
				for (Document document : relatedDocs) {
					if (!contentId.equals(document.getString("conetent_id"))) {
						SubMenuContent related = new SubMenuContent();
						related.setConetent_id(document.getString("conetent_id"));
						related.setContent_header(document.getString("content_header"));
						blogInfoVO.setRelatedBlogs(related);
					}
				}
			}

			// Latest Posts
			query = new Query();
			query.addCriteria(Criteria.where("menu_ref").is("miscellaneous"));
			query.with(new Sort(Sort.Direction.DESC, "created_date"));
			query.limit(6);
			List<Document> latestDocs = mongoOperations.find(query, Document.class, "submenucontent");
			if (latestDocs != null && !latestDocs.isEmpty()) {
				for (Document document : latestDocs) {
					if (!contentId.equals(document.getString("conetent_id"))) {
						SubMenuContent latest = new SubMenuContent();
						latest.setConetent_id(document.getString("conetent_id"));
						latest.setContent_header(document.getString("content_header"));
						blogInfoVO.setLatestBlogs(latest);
					}
				}
			}

			query = new Query();
			query.addCriteria(Criteria.where("blogId").is(contentId));
			query.with(new Sort(Sort.Direction.DESC, "date"));
			query.limit(6);
			List<Document> commentDocs = mongoOperations.find(query, Document.class, "comment");
			if (commentDocs != null && !commentDocs.isEmpty()) {
				for (Document document : commentDocs) {
					Comment comment = new Comment();
					comment.setComment(document.getString("comment"));
					comment.setName(document.getString("name"));
					comment.setEmail(document.getString("email"));
					comment.setWebsite(document.getString("website"));
					comment.setDate(BlogInfoUtil.getStringDate(document.getDate("date")));
					blogInfoVO.setComments(comment);
				}
			}

		}
		blogInfoVO.setSubMenuContents(subMenuContents);
		return blogInfoVO;
	}

	public BlogInfoVO getBlogInfoIndex(String pageName) {
		BlogInfoVO blogInfoVO = new BlogInfoVO();
		List<SubMenuContent> subMenuContents = new ArrayList<SubMenuContent>();
		Query query = null;

		File fi = new File(getClass().getClassLoader().getResource("oom.jpg").getFile());
		String fileContent = "";
		try {
			byte[] imgResize = BlogInfoUtil.imageResize(Files.readAllBytes(fi.toPath()), "oom.jpg");
			fileContent = Base64.getEncoder().encodeToString(imgResize);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		blogInfoVO.setThemeimage(fileContent);

		query = new Query();
		if (!BlogInfoUtil.HOME.equalsIgnoreCase(pageName)) {
			query.addCriteria(Criteria.where("submenu_ref").is(pageName));
			query.with(new Sort(Sort.Direction.ASC, "created_date"));
		} else {
			query.with(new Sort(Sort.Direction.DESC, "created_date"));
		}
		List<Document> docs = mongoOperations.find(query, Document.class, "submenucontent");
		if (docs != null && !docs.isEmpty()) {
			for (Document document : docs) {
				SubMenuContent subMenuContent = new SubMenuContent();
				subMenuContent.setConetent_id(document.getString("conetent_id"));
				subMenuContent.setContent_header(document.getString("content_header"));
				subMenuContent.setContentHeaderTag(document.getString("contentheaderTag"));
				subMenuContent.setContent(replaceImageContent(document.getString("content")));
				subMenuContent.setPostedBy(document.getString("postedBy"));
				subMenuContent.setSubmenu_ref(document.getString("submenu_ref"));
				subMenuContent.setSubMenuName(blogInfoCache.getSubMneu().get(document.getString("submenu_ref")));
				subMenuContent.setDate(BlogInfoUtil.getStringDate(document.getDate("created_date")));
				Binary imageData = document.get("themeImage", Binary.class);
				if (imageData != null) {
					byte[] imageByteData = imageData.getData();
					subMenuContent.setIndivisualThemeimage(Base64.getEncoder().encodeToString(imageByteData));
				} else {
					subMenuContent.setIndivisualThemeimage(fileContent);
				}
				subMenuContents.add(subMenuContent);
			}
		}

		blogInfoVO.setSubMenuContents(subMenuContents);
		return blogInfoVO;
	}

}
