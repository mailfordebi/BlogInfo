package com.dd.blog.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("logedIn")
public class BlogInfoController {

	@Autowired
	private BlogInfoDAO blogInfoDAO;

	@RequestMapping("/")
	public ModelAndView index() {
		return getModelAndView("", "");
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping("/logout")
	public ModelAndView logout() {
		ModelAndView modelAndView = null;
		modelAndView = getModelAndView("home", "dashboard");
		modelAndView.addObject("logedIn", "false");
		return modelAndView;
	}

	@RequestMapping("/loginSubMit")
	public ModelAndView loginSubMit(@ModelAttribute("username") String username,
			@ModelAttribute("password") String password) {
		ModelAndView modelAndView = null;
		if (blogInfoDAO.validate(username, password)) {
			modelAndView = getModelAndView("home", "dashboard");
			modelAndView.addObject("logedIn", "true");
		} else {
			modelAndView = new ModelAndView();
			modelAndView.setViewName("login");
			modelAndView.addObject("logedIn", "false");
			modelAndView.addObject("error", "User name and Password incorrect");
		}
		return modelAndView;
	}

	@RequestMapping("/settings")
	public ModelAndView settings(HttpServletRequest request) {
		String logedIn=(String) request.getSession().getAttribute("logedIn");
		ModelAndView modelAndView=null;
		if("true".equalsIgnoreCase(logedIn)) {
			modelAndView = getModelAndView("", "");
			modelAndView.addObject("settings", "true");
		}else {
			modelAndView = new ModelAndView();
			modelAndView.setViewName("login");
		}
		
		return modelAndView;
	}

	@RequestMapping("/home")
	public ModelAndView home() {
		return getModelAndView("home", "dashboard");
	}

	@RequestMapping("/addmenu")
	public ModelAndView addMenu(@ModelAttribute("selectType") String selectType,
			@ModelAttribute("menuid") String menuId, @ModelAttribute("menuName") String menuName,
			@ModelAttribute("submenuid") String subMenuId, @ModelAttribute("submenuName") String subMenuName,
			@ModelAttribute("menuselect") String menuRef) throws Exception {

		BlogInfoVO blogInfoVO = new BlogInfoVO();
		if ("Menu".equalsIgnoreCase(selectType)) {
			List<Menu> menus = new ArrayList<Menu>();
			Menu menu = new Menu();
			menu.setMenu_id(menuId);
			menu.setMenu_name(menuName);
			menus.add(menu);
			blogInfoVO.setMenuList(menus);
			blogInfoVO.setMenuId(menuId);
		}
		if ("Sub Menu".equalsIgnoreCase(selectType)) {
			List<SubMenu> menus = new ArrayList<SubMenu>();
			SubMenu menu = new SubMenu();
			menu.setSubmenu_id(subMenuId);
			menu.setSubmenu_name(subMenuName);
			menu.setMenu_ref(menuRef);
			menus.add(menu);
			blogInfoVO.setSubMenus(menus);
			blogInfoVO.setSubMenuId(subMenuId);
		}

		if (blogInfoDAO.insert(blogInfoVO, selectType)) {
			ModelAndView modelAndView = getModelAndView(menuId, "dashboard");
			return modelAndView;
		} else {
			throw new Exception("Unable to save");
		}

	}

	@RequestMapping("/menu")
	public ModelAndView menu(@RequestParam("menuid") String menuId) {
		return getModelAndView(menuId, "dashboard");
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard(@RequestParam("menuid") String menuId) {
		return getModelAndView(menuId, "dashboard");
	}

	@RequestMapping("/submenu")
	public ModelAndView factoryPattern(@RequestParam("menuid") String menuId,
			@RequestParam("submenuid") String subMenuId) {
		return getModelAndView(menuId, subMenuId);
	}

	@RequestMapping("/editInfo")
	public ModelAndView editInfo(@RequestParam("menuid") String menuId, @RequestParam("subMenuid") String subMenuId) {
		ModelAndView modelAndView = getModelAndView(menuId, subMenuId);
		modelAndView.addObject("editMode", true);
		return modelAndView;
	}

	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ModelAndView saveContent(@ModelAttribute("updateContent") String content,
			@ModelAttribute("content_id") String content_id, @ModelAttribute("subMenuId") String subMenuId,
			@ModelAttribute("menuId") String menuId) throws Exception {
		BlogInfoVO blogInfoVO = new BlogInfoVO();
		List<SubMenuContent> subMenuContents = new ArrayList<SubMenuContent>();
		SubMenuContent subMenuContent = new SubMenuContent();
		subMenuContent.setConetent_id(content_id);
		subMenuContent.setContent(content);
		subMenuContents.add(subMenuContent);
		blogInfoVO.setSubMenuContents(subMenuContents);
		if (blogInfoDAO.save(blogInfoVO)) {
			ModelAndView modelAndView = getModelAndView(menuId, subMenuId);
			modelAndView.addObject("editMode", false);
			return modelAndView;
		} else {
			throw new Exception("Unable to save");
		}

	}

	public ModelAndView getModelAndView(String menuId, String submenuId) {
		ModelAndView modelAndView = new ModelAndView();
		Map<String, List<Document>> documentMap = null;
		documentMap = blogInfoDAO.findIinfo();
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
				menuList.add(menu);
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
				if (submenuId.equalsIgnoreCase("dashboard")
						&& menuId.equalsIgnoreCase(document.getString("menu_ref"))) {
					// Set for dashboard page..
					blogInfoVO.setSubMenuId("dashboard");
					blogInfoVO.setMenuId(document.getString("menu_ref"));
					break;
				}
				if (submenuId.equalsIgnoreCase(document.getString("submenu_ref"))
						&& menuId.equalsIgnoreCase(document.getString("menu_ref"))) {
					SubMenuContent content = new SubMenuContent();
					content.setConetent_id((String) document.get("conetent_id"));
					content.setContent((String) document.get("content"));
					menuContentsList.add(content);
					blogInfoVO.setSubMenuId((String) document.get("submenu_ref"));
					blogInfoVO.setMenuId(document.getString("menu_ref"));
					blogInfoVO.setContent_id((String) document.get("conetent_id"));
				}
			}

			blogInfoVO.setMenuList(menuList);
			blogInfoVO.setSubMenus(subMenusList);
			blogInfoVO.setSubMenuContents(menuContentsList);
		}
		modelAndView.setViewName("index1");
		modelAndView.addObject("blogInfo", blogInfoVO);
		return modelAndView;

	}
}