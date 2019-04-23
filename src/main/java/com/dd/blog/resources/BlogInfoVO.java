package com.dd.blog.resources;

import java.util.List;

public class BlogInfoVO {
	private List<Menu> menuList;
	private List<SubMenu> subMenus;
	private List<SubMenuContent> subMenuContents;
	String subMenuId;
	String menuId;
	String content_id;
	//boolean isHidden;
	private Menu menu;
	private SubMenu subMenu;

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

}
