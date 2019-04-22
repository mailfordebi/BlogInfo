package com.dd.blog.resources;

public class SubMenu {
	private String submenu_id;
	private String submenu_name;
	private String menu_ref;
	private String sortOrder;
	private boolean hidden;

	public String getSubmenu_id() {
		return submenu_id;
	}

	public void setSubmenu_id(String submenu_id) {
		this.submenu_id = submenu_id;
	}

	public String getSubmenu_name() {
		return submenu_name;
	}

	public void setSubmenu_name(String submenu_name) {
		this.submenu_name = submenu_name;
	}

	public String getMenu_ref() {
		return menu_ref;
	}

	public void setMenu_ref(String menu_ref) {
		this.menu_ref = menu_ref;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
