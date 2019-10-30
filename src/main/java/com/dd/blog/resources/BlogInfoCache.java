package com.dd.blog.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class BlogInfoCache {

	@Autowired
	private MongoOperations mongoOperations;

	Map<String, String> subMenu = new HashMap<String, String>();

	public Map<String, String> getSubMneu() {
		if (subMenu.isEmpty()) {
			Query query = new Query();
			List<Document> docs = mongoOperations.find(query, Document.class, "submenu");
			if (docs != null && !docs.isEmpty()) {
				for (Document document : docs) {
					subMenu.put(document.getString("submenu_id"), document.getString("name"));
				}
			}
		}
		return subMenu;
	}

}
