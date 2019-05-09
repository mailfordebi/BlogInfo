package com.dd.blog.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

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

	public Map<String, List<Document>> findIinfo(String menu) {
		Map<String, List<Document>> documentMap = new HashMap<String, List<Document>>();
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "sort"));
		List<Document> menus = mongoOperations.find(query, Document.class, "menu");
		if (menus != null && !menus.isEmpty()) {
			documentMap.put("menu", menus);
		}

		List<Document> submenus = mongoOperations.find(query, Document.class, "submenu");
		if (submenus != null && !submenus.isEmpty()) {
			documentMap.put("submenu", submenus);
		}

		query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "created_date"));
		if ("miscellaneous".equalsIgnoreCase(menu)) {
			query.limit(10);
		}
		List<Document> submenucontents = mongoOperations.find(query, Document.class, "submenucontent");
		if (submenucontents != null && !submenucontents.isEmpty()) {
			documentMap.put("content", submenucontents);
		}

		return documentMap;
	}

	public boolean insert(BlogInfoVO blogInfoVO, String selectType) {
		try {
			BasicDBObject basicDBObject = new BasicDBObject();
			if ("Menu".equalsIgnoreCase(selectType)) {
				List<Menu> menus = blogInfoVO.getMenuList();
				basicDBObject.put("menu_id", menus.get(0).getMenu_id());
				basicDBObject.put("menu_name", menus.get(0).getMenu_name());
				basicDBObject.put("sort", "1");
				basicDBObject.put("hidden", false);
				// update(menus.get(0).getSortOrder(), "menu", null);
				mongoOperations.save(basicDBObject, "menu");
			}
			if ("Sub Menu".equalsIgnoreCase(selectType)) {
				List<SubMenu> menus = blogInfoVO.getSubMenus();
				basicDBObject.put("submenu_id", menus.get(0).getSubmenu_id());
				basicDBObject.put("submenu_name", menus.get(0).getSubmenu_name());
				basicDBObject.put("menu_ref", menus.get(0).getMenu_ref());
				basicDBObject.put("sort", menus.get(0).getSortOrder());
				basicDBObject.put("hidden", false);
				update(menus.get(0).getSortOrder(), "submenu", menus.get(0).getMenu_ref());
				mongoOperations.save(basicDBObject, "submenu");

				basicDBObject = new BasicDBObject();
				basicDBObject.put("conetent_id", menus.get(0).getSubmenu_id() + "_content");
				basicDBObject.put("content", menus.get(0).getSubmenu_name());
				basicDBObject.put("submenu_ref", menus.get(0).getSubmenu_id());
				basicDBObject.put("menu_ref", menus.get(0).getMenu_ref());
				mongoOperations.save(basicDBObject, "submenucontent");
			}
			if ("Misc".equalsIgnoreCase(selectType)) {
				List<SubMenuContent> subMenuContents = blogInfoVO.getSubMenuContents();
				basicDBObject.put("conetent_id", subMenuContents.get(0).getConetent_id());
				basicDBObject.put("content", subMenuContents.get(0).getContent());
				basicDBObject.put("content_header", subMenuContents.get(0).getContent_header());
				basicDBObject.put("contentheaderTag", subMenuContents.get(0).getContentHeaderTag());
				basicDBObject.put("created_date", subMenuContents.get(0).getCreated_date());
				basicDBObject.put("submenu_ref", subMenuContents.get(0).getSubmenu_ref());
				basicDBObject.put("menu_ref", subMenuContents.get(0).getMenu_ref());
				basicDBObject.put("postedBy", subMenuContents.get(0).getPostedBy());
				mongoOperations.save(basicDBObject, "submenucontent");
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	// TODO Need to implement for sub menu hiding.....
	public boolean updateHideOrShow(@NotNull BlogInfoVO blogInfoVO, String selectType) {
		BasicDBObject basicDBObject = null;
		if ("Hide Menu".equalsIgnoreCase(selectType)) {
			basicDBObject = findDoc(blogInfoVO.getMenu().getMenu_id(), "menu", blogInfoVO.getMenu().isHidden());
			mongoOperations.save(basicDBObject, "menu");
			return true;
		} else if ("Hide Sub Menu".equalsIgnoreCase(selectType)) {
			basicDBObject = findDoc(blogInfoVO.getSubMenu().getSubmenu_id(), "submenu",
					blogInfoVO.getSubMenu().isHidden());
			mongoOperations.save(basicDBObject, "submenu");
			return true;
		} else {
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
						if ((!"_id".equalsIgnoreCase(key)) && (!"created_date".equalsIgnoreCase(key))
								&& "content".equalsIgnoreCase(key)) {
							if (!StringUtils.isEmpty(subMenuContents.get(0).getContent())) {
								doc.put("content", subMenuContents.get(0).getContent().trim());
							}
						}
					}
					for (String key : doc.keySet()) {
						basicDBObject.put(key, doc.get(key));
					}
					mongoOperations.save(basicDBObject, "submenucontent");
				} else {
					// basicDBObject.put("", v)
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return true;
		} else {
			return false;
		}
	}

	public BasicDBObject findDoc(String idValue, String collectionName, boolean isHidden) {
		BasicDBObject basicDBObject = new BasicDBObject();
		Query query = new Query();
		String keyId = "";
		if ("menu".equalsIgnoreCase(collectionName)) {
			keyId = "menu_id";
		} else if ("submenu".equalsIgnoreCase(collectionName)) {
			keyId = "submenu_id";
		} else {
			keyId = "conetent_id";
		}

		query.addCriteria(Criteria.where(keyId).is(idValue));
		Document doc = mongoOperations.findOne(query, Document.class, collectionName);
		doc.put("hidden", isHidden);

		if (doc != null) {
			for (String key : doc.keySet()) {
				basicDBObject.put(key, doc.get(key));
			}
		}

		return basicDBObject;
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

	public void update(String sortOrder, String collectionName, String menuRef) {
		int sort = 0;
		if (!StringUtils.isEmpty(sortOrder)) {
			sort = Integer.parseInt(sortOrder);
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("sort").is(sortOrder));
		if ("submenu".equalsIgnoreCase(collectionName)) {
			query.addCriteria(Criteria.where("menu_ref").is(menuRef));
		}
		List<Document> docs = mongoOperations.find(query, Document.class, collectionName);
		if (docs != null && !docs.isEmpty()) {
			for (Document doc : docs) {
				BasicDBObject basicDBObject = new BasicDBObject();
				if ("menu".equalsIgnoreCase(collectionName)) {
					basicDBObject.put("_id", doc.getString("_id"));
					basicDBObject.put("menu_id", doc.getString("menu_id"));
					basicDBObject.put("menu_name", doc.getString("menu_name"));
					basicDBObject.put("sort", String.valueOf(sort + 1));
					mongoOperations.save(basicDBObject, "menu");
				}
				if ("submenu".equalsIgnoreCase(collectionName)) {
					basicDBObject.put("_id", doc.get("_id"));
					basicDBObject.put("submenu_id", doc.getString("submenu_id"));
					basicDBObject.put("submenu_name", doc.getString("submenu_name"));
					basicDBObject.put("menu_ref", doc.getString("menu_ref"));
					basicDBObject.put("sort", String.valueOf(sort + 1));
					mongoOperations.save(basicDBObject, "submenu");
				}
			}
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

	public String getFirstSubMeny(String menuId) {
		String subMenuId = "";
		Query query = new Query();
		query.addCriteria(Criteria.where("menu_ref").is(menuId));
		query.with(new Sort(Sort.Direction.ASC, "sort"));
		List<Document> docs = mongoOperations.find(query, Document.class, "submenu");
		if (docs != null && !docs.isEmpty()) {
			Document d = docs.get(0);
			subMenuId = d.getString("submenu_id");
		}
		return subMenuId;
	}

	public BlogInfoVO getBlogInfo(String contentId) {
		BlogInfoVO blogInfoVO = new BlogInfoVO();
		List<SubMenuContent> subMenuContents = new ArrayList<SubMenuContent>();
		Query query = null;
		boolean isThemeFound = false;
		if (contentId != null) {
			query = new Query();
			query.addCriteria(Criteria.where("content_id").is(contentId));
			List<Document> documents = mongoOperations.find(query, Document.class, "images");
			if (documents != null && !documents.isEmpty()) {
				for (Document document : documents) {
					if (document.get("isThemeImage")!=null && document.getBoolean("isThemeImage")) {
						Binary imageData = document.get("imagecontent", Binary.class);
						byte[] imageByteData = imageData.getData();
						String base64Image = Base64.getEncoder().encodeToString(imageByteData);
						blogInfoVO.setThemeimage(base64Image);
						isThemeFound = true;
						break;
					}
				}
			}
		}
		if (!isThemeFound) {
			File fi = new File("home-bg.jpg");
			byte[] fileContent;
			try {
				fileContent = Files.readAllBytes(fi.toPath());
				blogInfoVO.setThemeimage(Base64.getEncoder().encodeToString(fileContent));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		}
		query = new Query();
		if (contentId != null) {
			query.addCriteria(Criteria.where("conetent_id").is(contentId));
		}
		query.addCriteria(Criteria.where("menu_ref").is("miscellaneous"));
		List<Document> docs = mongoOperations.find(query, Document.class, "submenucontent");

		if (docs != null && !docs.isEmpty()) {
			for (Document document : docs) {
				SubMenuContent subMenuContent = new SubMenuContent();
				subMenuContent.setConetent_id(document.getString("conetent_id"));
				subMenuContent.setContent_header(document.getString("content_header"));
				subMenuContent.setContentHeaderTag(document.getString("contentheaderTag"));
				subMenuContent.setContent(document.getString("content"));
				subMenuContent.setPostedBy(document.getString("postedBy"));
				subMenuContent.setMenu_ref(document.getString("menu_ref"));
				subMenuContent.setSubmenu_ref(document.getString("submenu_ref"));
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
				String strDate = formatter.format(document.getDate("created_date"));
				subMenuContent.setDate(strDate);
				subMenuContents.add(subMenuContent);
			}
		}
		blogInfoVO.setSubMenuContents(subMenuContents);

		return blogInfoVO;
	}
}
