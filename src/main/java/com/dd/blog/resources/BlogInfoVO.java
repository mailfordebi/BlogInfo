package com.dd.blog.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogInfoVO {
	private List<Menu> menuList;
	private List<SubMenu> subMenus;
	private List<SubMenuContent> subMenuContents;
	String subMenuId;
	String menuId;
	String content_id;
	private Menu menu;
	private SubMenu subMenu;
	private String themeimage;

	private String prevSubMenuId;
	private String prevSubMenuName;
	private String nextSubMenuId;
	private String nextSubMenuName;
	private String prevMenuId;
	private String nextMenuId;

	private boolean oldPost;
	private boolean newPost;
	Map<String, String> relatedBlogs = null;
	Map<String, String> latestBlogs = null;

	public BlogInfoVO() {
		relatedBlogs = new HashMap<String, String>();
		latestBlogs = new HashMap<String, String>();
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

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<SubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}

	public List<SubMenuContent> getSubMenuContents() {
		return subMenuContents;
	}

	public void setSubMenuContents(List<SubMenuContent> subMenuContents) {
		this.subMenuContents = subMenuContents;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
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

	public Map<String, String> getRelatedBlogs() {
		return relatedBlogs;
	}

	public Map<String, String> getLatestBlogs() {
		return latestBlogs;
	}

	public void setLatestBlogs(String key, String value) {
		this.latestBlogs.put(key, value);
	}

	public void setRelatedBlogs(String key, String value) {
		this.relatedBlogs.put(key, value);
	}

}
