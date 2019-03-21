package com.dd.blog.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;

@Repository
public class BlogInfoDAO {
	@Autowired
	private MongoOperations mongoOperations;

	public Map<String, List<Document>> findIinfo() {
		Map<String, List<Document>> documentMap = new HashMap<String, List<Document>>();
		List<Document> menus = mongoOperations.find(new Query(), Document.class, "menu");
		if (menus != null && !menus.isEmpty()) {
			documentMap.put("menu", menus);
		}

		List<Document> submenus = mongoOperations.find(new Query(), Document.class, "submenu");
		if (submenus != null && !submenus.isEmpty()) {
			documentMap.put("submenu", submenus);
		}

		List<Document> submenucontents = mongoOperations.find(new Query(), Document.class, "submenucontent");
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
				mongoOperations.save(basicDBObject, "menu");
			}
			if ("Sub Menu".equalsIgnoreCase(selectType)) {
				List<SubMenu> menus = blogInfoVO.getSubMenus();
				basicDBObject.put("submenu_id", menus.get(0).getSubmenu_id());
				basicDBObject.put("submenu_name", menus.get(0).getSubmenu_name());
				basicDBObject.put("menu_ref", menus.get(0).getMenu_ref());
				mongoOperations.save(basicDBObject, "submenu");

				basicDBObject = new BasicDBObject();
				basicDBObject.put("conetent_id", menus.get(0).getSubmenu_id() + "_content");
				basicDBObject.put("content", menus.get(0).getSubmenu_name());
				basicDBObject.put("submenu_ref", menus.get(0).getSubmenu_id());
				basicDBObject.put("menu_ref", menus.get(0).getMenu_ref());
				mongoOperations.save(basicDBObject, "submenucontent");
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
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
						if ((!"_id".equalsIgnoreCase(key))
								&& subMenuContents.get(0).getConetent_id().equalsIgnoreCase(doc.getString(key))) {
							doc.put("content", subMenuContents.get(0).getContent());
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
}
