package com.dd.blog.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyBlogController {

	@Autowired
	private BlogInfoDAO blogInfoDAO;

	@RequestMapping("/myblogindex")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(null);
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/about")
	public ModelAndView about() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("about");
		return modelAndView;
	}

	@RequestMapping("/blogPost")
	public ModelAndView blogPost() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("post");
		return modelAndView;
	}

	@RequestMapping("/contact")
	public ModelAndView contact() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("contact");
		return modelAndView;
	}

}
