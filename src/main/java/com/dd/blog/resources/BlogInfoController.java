package com.dd.blog.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({ "logedIn", "pageNo" })
public class BlogInfoController {
	int pageNo = 0;
	boolean logedIn = false;

	@Autowired
	private BlogInfoDAO blogInfoDAO;

	@RequestMapping("/index")
	public ModelAndView index() {
		String subMenuId = blogInfoDAO.getFirstSubMeny("design_pattern");
		ModelAndView modelAndView = getModelAndView("design_pattern", subMenuId, false);
		return modelAndView;
	}

	@RequestMapping("/home")
	public ModelAndView home() {
		String subMenuId = blogInfoDAO.getFirstSubMeny("design_pattern");
		ModelAndView modelAndView = getModelAndView("design_pattern", subMenuId, false);
		return modelAndView;
	}

	@RequestMapping("/login")
	public ModelAndView login(@ModelAttribute("page") String page) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		modelAndView.addObject("page", page);
		return modelAndView;
	}

	@RequestMapping("/logout")
	public ModelAndView logout() {
		ModelAndView modelAndView = null;
		String subMenuId = blogInfoDAO.getFirstSubMeny("design_pattern");
		modelAndView = getModelAndView("design_pattern", subMenuId, false);
		logedIn = false;
		modelAndView.addObject("logedIn", "false");
		return modelAndView;
	}

	@RequestMapping("/logoutblog")
	public ModelAndView logoutblog() {
		ModelAndView modelAndView = new ModelAndView();
		logedIn = false;
		modelAndView.addObject("logedIn", "false");
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(null, false);
		pageNo = 1;
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, 0, pageNo);
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/loginSubMit")
	public ModelAndView loginSubMit(@ModelAttribute("username") String username,
			@ModelAttribute("password") String password, @ModelAttribute("page") String pg) {
		ModelAndView modelAndView = null;
		if (blogInfoDAO.validate(username, password)) {
			String subMenuId = blogInfoDAO.getFirstSubMeny("design_pattern");
			if ("blog".equals(pg)) {
				modelAndView = new ModelAndView();
				BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(null, false);
				pageNo = 1;
				blogInfoVO = getPostWithNoOfRecord(blogInfoVO, 0, pageNo);
				modelAndView.addObject("blogInfo", blogInfoVO);
				modelAndView.addObject("loginId", "qazxswedcvfr");
				modelAndView.setViewName("index");
			} else {
				modelAndView = getModelAndView("design_pattern", subMenuId, false);
			}
			logedIn = true;
			modelAndView.addObject("logedIn", "true");
		} else {
			modelAndView = new ModelAndView();
			modelAndView.setViewName("login");
			logedIn = false;
			modelAndView.addObject("logedIn", "false");
			modelAndView.addObject("error", "User name and Password incorrect");
		}
		return modelAndView;
	}

	@RequestMapping("/settings")
	public ModelAndView settings(HttpServletRequest request) {
		String logedIn1 = (String) request.getSession().getAttribute("logedIn");
		ModelAndView modelAndView = null;
		if ("true".equalsIgnoreCase(logedIn1)) {
			modelAndView = getModelAndView("", "", false);
			modelAndView.addObject("settings", "true");
		} else {
			modelAndView = new ModelAndView();
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}

	@RequestMapping("/myblogindex")
	public ModelAndView myblogindex() {
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(null, false);
		pageNo = 1;
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, 0, pageNo);
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.addObject("page", pageNo);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/newpost")
	public ModelAndView handleNewPost() {
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(null, false);
		int index = pageNo;
		pageNo--;
		modelAndView.addObject("page", pageNo);
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, pageNo - 1, index - 1);
		modelAndView.addObject("blogInfo", blogInfoVO);
		// modelAndView.addObject("oldPost", true);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/olderpost")
	public ModelAndView handleOlderPost() {
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(null, false);
		int index = pageNo;
		pageNo++;
		modelAndView.addObject("page", pageNo);
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, index, pageNo);
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	private BlogInfoVO getPostWithNoOfRecord(BlogInfoVO blogInfoVO, int index, int pageNo) {
		List<SubMenuContent> subMenuContents = blogInfoVO.getSubMenuContents();
		List<SubMenuContent> subMenuContents2 = new ArrayList<SubMenuContent>();
		
		// Need to change as per the requirement...
		int noOfRecordsPerPage = 2;
		index = index * noOfRecordsPerPage;
		pageNo = pageNo * noOfRecordsPerPage;
		if (pageNo > subMenuContents.size()) {
			pageNo = subMenuContents.size();
		}
		for (int i = index; i < pageNo; i++) {
			subMenuContents2.add(subMenuContents.get(i));
		}
		blogInfoVO.setSubMenuContents(subMenuContents2);

		if (index != 0) {
			blogInfoVO.setNewPost(true);
		}
		if (pageNo < subMenuContents.size()) {
			blogInfoVO.setOldPost(true);
		}
		return blogInfoVO;
	}

	@RequestMapping("/about")
	public ModelAndView about() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("about");
		modelAndView.addObject("logedIn", logedIn);
		return modelAndView;
	}

	@RequestMapping("/blogPost")
	public ModelAndView blogPost(@RequestParam("blogId") String blogId, @RequestParam("loginId") String loginId) {
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(blogId, false);
		modelAndView.addObject("blogInfo", blogInfoVO);
		if ("qazxswedcvfr".equals(loginId)) {
			logedIn = true;
			modelAndView.addObject("logedIn", true);
		}
		modelAndView.addObject("editMode", false);
		modelAndView.setViewName("post");
		return modelAndView;
	}

	@RequestMapping("/contact")
	public ModelAndView contact() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("contact");
		modelAndView.addObject("logedIn", logedIn);
		return modelAndView;
	}

	@RequestMapping("/addmenu")
	public ModelAndView addMenu(@ModelAttribute("selectType") String selectType,
			@ModelAttribute("menuid") String menuId, @ModelAttribute("menuName") String menuName,
			@ModelAttribute("submenuid") String subMenuId, @ModelAttribute("submenuName") String subMenuName,
			@ModelAttribute("menuselect") String menuRef, @ModelAttribute("sortOrder") String sortOrder,
			@ModelAttribute("contentHeaderTag") String contentHeaderTag,
			@ModelAttribute("submenuselect") String blogType, @ModelAttribute("contentHeader") String contentHeader,
			@ModelAttribute("contentBody") String contentBody, @ModelAttribute("contentid") String contentid,
			@RequestParam("themeImage") MultipartFile file, @ModelAttribute("postedBy") String postedBy,
			@ModelAttribute("menuHidden") String menuHidden, @ModelAttribute("menuRefToHide") String menuRefToHide,
			@ModelAttribute("subMenuHidden") String subMenuHidden) throws Exception {

		BlogInfoVO blogInfoVO = new BlogInfoVO();
		boolean isToInsert = false;
		boolean isToUpdate = false;
		if ("Menu".equalsIgnoreCase(selectType)) {
			List<Menu> menus = new ArrayList<Menu>();
			Menu menu = new Menu();
			menu.setMenu_id(menuId);
			menu.setMenu_name(menuName);
			menu.setSortOrder(sortOrder);
			menus.add(menu);
			blogInfoVO.setMenuList(menus);
			blogInfoVO.setMenuId(menuId);
			isToInsert = true;
		}
		if ("Sub Menu".equalsIgnoreCase(selectType)) {
			List<SubMenu> subMenus = new ArrayList<SubMenu>();
			SubMenu subMenu = new SubMenu();
			subMenu.setSubmenu_id(subMenuId);
			subMenu.setSubmenu_name(subMenuName);
			subMenu.setMenu_ref(menuRef);
			subMenu.setSortOrder(sortOrder);
			subMenus.add(subMenu);
			blogInfoVO.setSubMenus(subMenus);
			blogInfoVO.setSubMenuId(subMenuId);
			isToInsert = true;
		}
		if ("Misc".equalsIgnoreCase(selectType)) {
			if (!file.isEmpty()) {
				byte[] imageContent = file.getBytes();
				String imageName = file.getOriginalFilename();
				blogInfoDAO.saveImage(imageContent, imageName, contentid, true);
			}
			List<SubMenuContent> subMenuContents = new ArrayList<SubMenuContent>();
			SubMenuContent subMenuContent = new SubMenuContent();
			subMenuContent.setContent_header(contentHeader);
			subMenuContent.setContentHeaderTag(contentHeaderTag);
			subMenuContent.setContent(contentBody);
			subMenuContent.setSubmenu_ref(blogType);
			subMenuContent.setMenu_ref("miscellaneous");
			subMenuContent.setConetent_id(contentid);
			subMenuContent.setCreated_date(new Date());
			subMenuContent.setPostedBy(postedBy);
			subMenuContents.add(subMenuContent);
			blogInfoVO.setSubMenuContents(subMenuContents);
			isToInsert = true;
		}
		if ("Hide Menu".equalsIgnoreCase(selectType)) {
			isToUpdate = true;
			Menu menu = new Menu();
			menu.setMenu_id(menuRefToHide);
			if ("true".equalsIgnoreCase(menuHidden)) {
				menu.setHidden(true);
			}
			blogInfoVO.setMenu(menu);
		}
		// TODO Need to implement for Sub menu hiding
		if ("Hide Sub Menu".equalsIgnoreCase(selectType)) {
			isToUpdate = true;
			SubMenu subMenu = new SubMenu();
			// subMenu.setSubmenu_id(subMenuRef);
			if ("true".equalsIgnoreCase(subMenuHidden)) {
				subMenu.setHidden(true);
			}
			blogInfoVO.setSubMenu(subMenu);
		}

		if (isToInsert && blogInfoDAO.insert(blogInfoVO, selectType)) {
			ModelAndView modelAndView = getModelAndView("miscellaneous", null, false);
			return modelAndView;
		} else if (isToUpdate && blogInfoDAO.updateHideOrShow(blogInfoVO, selectType)) {
			ModelAndView modelAndView = getModelAndView("miscellaneous", null, false);
			return modelAndView;
		} else {
			throw new Exception("Unable to save");
		}

	}

	@RequestMapping("/menu")
	public ModelAndView menu(@RequestParam("menuid") String menuId) {
		ModelAndView modelAndView = null;
		if ("miscellaneous".equalsIgnoreCase(menuId)) {
			modelAndView = getModelAndView(menuId, null, false);
		} else {
			String subMenuId = blogInfoDAO.getFirstSubMeny(menuId);
			modelAndView = getModelAndView(menuId, subMenuId, false);
		}
		return modelAndView;
	}

	@RequestMapping("/submenu")
	public ModelAndView submenu(@RequestParam("menuid") String menuId, @RequestParam("submenuid") String subMenuId) {
		ModelAndView modelAndView = getModelAndView(menuId, subMenuId, false);
		return modelAndView;
	}

	@RequestMapping("/editInfo")
	public ModelAndView editInfo(@RequestParam("menuid") String menuId, @RequestParam("subMenuid") String subMenuId,
			@RequestParam("isBlog") boolean isBlog) {
		ModelAndView modelAndView = null;
		if (isBlog) {
			modelAndView = new ModelAndView();
			BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(menuId, true);
			modelAndView.addObject("blogInfo", blogInfoVO);
			modelAndView.addObject("editMode", true);
			modelAndView.setViewName("post");
		} else {
			modelAndView = getModelAndView(menuId, subMenuId, true);
		}
		modelAndView.addObject("editMode", true);
		return modelAndView;
	}

	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ModelAndView saveContent(@ModelAttribute("updateContent") String content,
			@ModelAttribute("content_id") String content_id, @ModelAttribute("subMenuId") String subMenuId,
			@ModelAttribute("menuId") String menuId, @RequestParam("image") MultipartFile file,
			@RequestParam("isBlog") boolean isBlog) throws Exception {

		if (!file.isEmpty()) {
			byte[] imageContent = file.getBytes();
			String imageName = file.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}

		BlogInfoVO blogInfoVO = new BlogInfoVO();
		List<SubMenuContent> subMenuContents = new ArrayList<SubMenuContent>();
		SubMenuContent subMenuContent = new SubMenuContent();
		subMenuContent.setConetent_id(content_id);
		subMenuContent.setContent(content);
		subMenuContents.add(subMenuContent);
		blogInfoVO.setSubMenuContents(subMenuContents);
		if (blogInfoDAO.save(blogInfoVO)) {
			ModelAndView modelAndView = null;
			if (isBlog) {
				modelAndView = new ModelAndView();
				BlogInfoVO updatedBlogInfoVO = blogInfoDAO.getBlogInfo(content_id, false);
				modelAndView.addObject("blogInfo", updatedBlogInfoVO);
				modelAndView.addObject("editMode", false);
				modelAndView.setViewName("post");
			} else {
				modelAndView = getModelAndView(menuId, subMenuId, false);
				modelAndView.addObject("editMode", false);
			}
			return modelAndView;
		} else {
			throw new Exception("Unable to save");
		}

	}

	public ModelAndView getModelAndView(String menuId, String submenuId, boolean isEditMode) {
		ModelAndView modelAndView = new ModelAndView();
		Map<String, List<Document>> documentMap = null;
		documentMap = blogInfoDAO.findIinfo(menuId);
		BlogInfoVO blogInfoVO = new BlogInfoVO();

		if (documentMap != null && !documentMap.isEmpty()) {
			List<Document> menus = documentMap.get("menu");
			List<Document> subMenus = documentMap.get("submenu");
			List<Document> contents = documentMap.get("content");

			List<Menu> menuList = new ArrayList<Menu>();
			List<SubMenu> subMenusList = new ArrayList<SubMenu>();
			List<SubMenuContent> menuContentsList = new ArrayList<SubMenuContent>();
			for (Document document : menus) {
				Menu menu = new Menu();
				menu.setMenu_id((String) document.get("menu_id"));
				menu.setMenu_name((String) document.get("menu_name"));
				menu.setHidden((boolean) document.get("hidden"));
				menuList.add(menu);
			}

			if (StringUtils.isEmpty(menuId)) {
				menuId = "miscellaneous";
			}

			for (Document document : subMenus) {
				if (document.getString("menu_ref").equalsIgnoreCase(menuId)) {
					SubMenu content = new SubMenu();
					content.setSubmenu_id((String) document.get("submenu_id"));
					content.setSubmenu_name((String) document.get("submenu_name"));
					content.setMenu_ref(menuId);
					subMenusList.add(content);

				}
			}
			for (Document document : contents) {
				if (menuId != null && menuId.equalsIgnoreCase(document.getString("menu_ref"))
						&& "miscellaneous".equals(menuId)) {
					if (submenuId == null || submenuId.equalsIgnoreCase(document.getString("submenu_ref"))) {
						SubMenuContent content = new SubMenuContent();
						content.setConetent_id((String) document.get("conetent_id"));
						content.setContent_header(document.getString("content_header"));
						String cont = (String) document.get("content");
						if (!isEditMode) {
							cont = blogInfoDAO.replaceImageContent(cont);
						}

						String up_content = "";
						String down_content = "";
						if (cont.length() > 400) {
							up_content = cont.substring(0, 400);
							down_content = cont.substring(400);
						} else {
							up_content = cont;
						}

						content.setUp_content(up_content);
						content.setDown_content(down_content);
						content.setContent(cont.trim());
						content.setMenu_ref(document.getString("menu_ref"));
						content.setSubmenu_ref(document.getString("submenu_ref"));
						menuContentsList.add(content);
						blogInfoVO.setSubMenuId(null);
						blogInfoVO.setMenuId(document.getString("menu_ref"));
						blogInfoVO.setContent_id((String) document.get("conetent_id"));
					}
				} else if (submenuId != null && submenuId.equalsIgnoreCase(document.getString("submenu_ref"))
						&& menuId != null && menuId.equalsIgnoreCase(document.getString("menu_ref"))) {
					SubMenuContent content = new SubMenuContent();
					content.setConetent_id((String) document.get("conetent_id"));
					String cont = (String) document.get("content");
					if (!isEditMode) {
						cont = blogInfoDAO.replaceImageContent(cont);
					}
					content.setContent(cont.trim());
					menuContentsList.add(content);
					blogInfoVO.setSubMenuId((String) document.get("submenu_ref"));
					blogInfoVO.setMenuId(document.getString("menu_ref"));
					blogInfoVO.setContent_id((String) document.get("conetent_id"));
					break;
				}
			}

			blogInfoVO.setMenuList(menuList);
			blogInfoVO.setSubMenus(subMenusList);
			blogInfoVO.setSubMenuContents(menuContentsList);
			setPrevAndNextSubMenu(blogInfoVO);
		}
		modelAndView.setViewName("index1");
		modelAndView.addObject("blogInfo", blogInfoVO);
		return modelAndView;

	}

	private void setPrevAndNextSubMenu(BlogInfoVO blogInfoVO) {
		if (blogInfoVO != null && blogInfoVO.getSubMenus() != null && !blogInfoVO.getSubMenus().isEmpty()) {
			for (int i = 0; i < blogInfoVO.getSubMenus().size(); i++) {
				if (blogInfoVO.getSubMenus().get(i).getSubmenu_id().equals(blogInfoVO.getSubMenuId())) {
					if (blogInfoVO.getSubMenus().size() == 1) {
						blogInfoVO.setPrevSubMenuId("");
						blogInfoVO.setPrevSubMenuName("");
						blogInfoVO.setPrevMenuId("");
						blogInfoVO.setNextSubMenuId("");
						blogInfoVO.setNextSubMenuName("");
					} else {
						if (i > 0 && i < blogInfoVO.getSubMenus().size() - 1) {
							SubMenu prev = blogInfoVO.getSubMenus().get(i - 1);
							blogInfoVO.setPrevSubMenuId(prev.getSubmenu_id());
							blogInfoVO.setPrevSubMenuName(prev.getSubmenu_name());
							blogInfoVO.setPrevMenuId(prev.getMenu_ref());
							SubMenu next = blogInfoVO.getSubMenus().get(i + 1);
							blogInfoVO.setNextSubMenuId(next.getSubmenu_id());
							blogInfoVO.setNextSubMenuName(next.getSubmenu_name());
							blogInfoVO.setNextMenuId(next.getMenu_ref());
						} else if (i == 0) {
							SubMenu next = blogInfoVO.getSubMenus().get(i + 1);
							blogInfoVO.setNextSubMenuId(next.getSubmenu_id());
							blogInfoVO.setNextSubMenuName(next.getSubmenu_name());
							blogInfoVO.setNextMenuId(next.getMenu_ref());
							blogInfoVO.setPrevSubMenuId("");
							blogInfoVO.setPrevSubMenuName("");
						} else {
							SubMenu prev = blogInfoVO.getSubMenus().get(i - 1);
							blogInfoVO.setPrevSubMenuId(prev.getSubmenu_id());
							blogInfoVO.setPrevSubMenuName(prev.getSubmenu_name());
							blogInfoVO.setPrevMenuId(prev.getMenu_ref());
							blogInfoVO.setNextSubMenuId("");
							blogInfoVO.setNextSubMenuName("");
						}
					}
				}
			}
		}
	}
}