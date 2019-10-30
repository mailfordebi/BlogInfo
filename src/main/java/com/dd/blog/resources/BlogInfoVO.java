package com.dd.blog.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlogInfoVO {
	private List<SubMenuContent> subMenuContents;
	String subMenuRef;
	String menuId;
	String content_id;
	private String themeimage;

	private String prevSubMenuId;
	private String prevSubMenuName;
	private String nextSubMenuId;
	private String nextSubMenuName;
	private String prevMenuId;
	private String nextMenuId;

	private boolean oldPost;
	private boolean newPost;
	List<SubMenuContent> relatedBlogs = null;
	List<SubMenuContent> latestBlogs = null;
	private List<Comment> comments;

	private Map<String, String> subMenu;
	
	private byte[] imageContent;

	public BlogInfoVO() {
		relatedBlogs = new ArrayList<SubMenuContent>();
		latestBlogs = new ArrayList<SubMenuContent>();
		comments = new ArrayList<Comment>();
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public List<SubMenuContent> getSubMenuContents() {
		return subMenuContents;
	}

	public void setSubMenuContents(List<SubMenuContent> subMenuContents) {
		this.subMenuContents = subMenuContents;
	}

	public String getSubMenuRef() {
		return subMenuRef;
	}

	public void setSubMenuRef(String subMenuRef) {
		this.subMenuRef = subMenuRef;
	}

	public String getPrevSubMenuId() {
		return prevSubMenuId;
	}

	public void setPrevSubMenuId(String prevSubMenuId) {
		this.prevSubMenuId = prevSubMenuId;
	}

	public String getPrevSubMenuName() {
		return prevSubMenuName;
	}

	public void setPrevSubMenuName(String prevSubMenuName) {
		this.prevSubMenuName = prevSubMenuName;
	}

	public String getNextSubMenuId() {
		return nextSubMenuId;
	}

	public void setNextSubMenuId(String nextSubMenuId) {
		this.nextSubMenuId = nextSubMenuId;
	}

	public String getNextSubMenuName() {
		return nextSubMenuName;
	}

	public void setNextSubMenuName(String nextSubMenuName) {
		this.nextSubMenuName = nextSubMenuName;
	}

	public String getPrevMenuId() {
		return prevMenuId;
	}

	public void setPrevMenuId(String prevMenuId) {
		this.prevMenuId = prevMenuId;
	}

	public String getNextMenuId() {
		return nextMenuId;
	}

	public void setNextMenuId(String nextMenuId) {
		this.nextMenuId = nextMenuId;
	}

	public String getThemeimage() {
		return themeimage;
	}

	public void setThemeimage(String themeimage) {
		this.themeimage = themeimage;
	}

	public boolean isOldPost() {
		return oldPost;
	}

	public void setOldPost(boolean oldPost) {
		this.oldPost = oldPost;
	}

	public boolean isNewPost() {
		return newPost;
	}

	public void setNewPost(boolean newPost) {
		this.newPost = newPost;
	}

	public List<SubMenuContent> getRelatedBlogs() {
		return relatedBlogs;
	}

	public List<SubMenuContent> getLatestBlogs() {
		return latestBlogs;
	}

	public void setLatestBlogs(SubMenuContent latestBlog) {
		this.latestBlogs.add(latestBlog);
	}

	public void setRelatedBlogs(SubMenuContent relatedBlog) {
		this.relatedBlogs.add(relatedBlog);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(Comment comment) {
		this.comments.add(comment);
	}

	public Map<String, String> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(Map<String, String> subMenu) {
		this.subMenu = subMenu;
	}

	public byte[] getImageContent() {
		return imageContent;
	}

	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}

}
