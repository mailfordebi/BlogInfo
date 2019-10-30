package com.dd.blog.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	BlogInfoCache blogInfoCache;

	@RequestMapping("/richText")
	public ResponseEntity<String> richText() throws IOException {
		File fi = new File(getClass().getClassLoader().getResource("test.txt").getFile());
		InputStream is = new FileInputStream(fi);
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();
		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		String fileAsString = sb.toString();
		buf.close();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-type", "text/javascript;charset=UTF-8");
		return ResponseEntity.ok().headers(responseHeaders).body(fileAsString);
	}

	@RequestMapping("/test")
	public String test() {
		return "test";
	}
	
	@RequestMapping("/newBlog")
	public String newBlog() {
		return "newpost";
	}

	@RequestMapping("/login")
	public ModelAndView login(@ModelAttribute("page") String page) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		modelAndView.addObject("page", page);
		return modelAndView;
	}

	@RequestMapping("/logoutblog")
	public ModelAndView logoutblog() {
		ModelAndView modelAndView = new ModelAndView();
		logedIn = false;
		modelAndView.addObject("logedIn", "false");
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfoIndex(BlogInfoUtil.HOME);
		pageNo = 1;
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, 0, pageNo);
		blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/loginSubMit")
	public ModelAndView loginSubMit(@ModelAttribute("username") String username,
			@ModelAttribute("password") String password, @ModelAttribute("page") String pg) {
		ModelAndView modelAndView = null;
		if (blogInfoDAO.validate(username, password)) {
			if ("blog".equals(pg)) {
				modelAndView = new ModelAndView();
				BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfoIndex(BlogInfoUtil.HOME);
				pageNo = 1;
				blogInfoVO = getPostWithNoOfRecord(blogInfoVO, 0, pageNo);
				blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
				modelAndView.addObject("blogInfo", blogInfoVO);
				modelAndView.addObject("loginId", "qazxswedcvfr");
				modelAndView.setViewName("index");
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

	@RequestMapping("/")
	public ModelAndView index() {
		return myblogindex("");
	}

	@RequestMapping("/index")
	public ModelAndView index1() {
		return myblogindex("");
	}

	@RequestMapping("/myblogindex")
	public ModelAndView myblogindex(@RequestParam("submenuRef") String submenuRef) {
		ModelAndView modelAndView = new ModelAndView();
		// BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(null, false);
		if (StringUtils.isEmpty(submenuRef)) {
			submenuRef = BlogInfoUtil.HOME;
		}
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfoIndex(submenuRef);
		pageNo = 1;
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, 0, pageNo);
		blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
		blogInfoVO.setSubMenuRef(submenuRef);
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.addObject("page", pageNo);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/newpost")
	public ModelAndView handleNewPost(@RequestParam("submenuRef") String submenuRef) {
		if (StringUtils.isEmpty(submenuRef)) {
			submenuRef = BlogInfoUtil.HOME;
		}
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfoIndex(submenuRef);
		int index = pageNo;
		pageNo--;
		modelAndView.addObject("page", pageNo);
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, pageNo - 1, index - 1);
		blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
		blogInfoVO.setSubMenuRef(submenuRef);
		modelAndView.addObject("blogInfo", blogInfoVO);
		// modelAndView.addObject("oldPost", true);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/olderpost")
	public ModelAndView handleOlderPost(@RequestParam("submenuRef") String submenuRef) {
		if (StringUtils.isEmpty(submenuRef)) {
			submenuRef = BlogInfoUtil.HOME;
		}
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfoIndex(submenuRef);
		int index = pageNo;
		pageNo++;
		modelAndView.addObject("page", pageNo);
		blogInfoVO = getPostWithNoOfRecord(blogInfoVO, index, pageNo);
		blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
		blogInfoVO.setSubMenuRef(submenuRef);
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.setViewName("index");
		return modelAndView;
	}

	private BlogInfoVO getPostWithNoOfRecord(BlogInfoVO blogInfoVO, int index, int pageNo) {
		List<SubMenuContent> subMenuContents = blogInfoVO.getSubMenuContents();
		List<SubMenuContent> subMenuContents2 = new ArrayList<SubMenuContent>();

		// Need to change as per the requirement...
		int noOfRecordsPerPage = 5;
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

	@RequestMapping(value = "/postComment", method = RequestMethod.POST)
	public ModelAndView postComment(@RequestParam("comment") String comment, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("website") String website,
			@RequestParam("blogId") String blogId) {

		if (!blogInfoDAO.saveComment(comment, name, email, website, blogId)) { //
			System.out.println("Error while saving");
		}

		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(blogId, false);
		blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
		modelAndView.addObject("blogInfo", blogInfoVO);
		modelAndView.setViewName("post");
		return modelAndView;

	}

	@RequestMapping("/blogPost")
	public ModelAndView blogPost(@RequestParam("blogId") String blogId, @RequestParam("loginId") String loginId) {
		ModelAndView modelAndView = new ModelAndView();
		BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(blogId, false);
		blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
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

	@RequestMapping("/addBlog")
	public ModelAndView addBlog(@ModelAttribute("techType") String techType,
			@ModelAttribute("blogHeader") String blogHeader, @ModelAttribute("headerTag") String headerTag,
			@RequestParam("themeImage") MultipartFile themeImage, @ModelAttribute("postedBy") String postedBy,
			@ModelAttribute("contentBody") String contentBody, @RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4, @RequestParam("file5") MultipartFile file5,
			@RequestParam("file6") MultipartFile file6, @RequestParam("file7") MultipartFile file7,
			@RequestParam("file8") MultipartFile file8, @RequestParam("file9") MultipartFile file9,
			@RequestParam("file10") MultipartFile file10, @RequestParam("file11") MultipartFile file11)
			throws Exception {
		String content_id = "";
		String pattern = "EEEEE_MMMMM_yyyy_MM_dd_HH_mm_ssMs_SSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		content_id = simpleDateFormat.format(new Date());

		if (!file1.isEmpty()) {
			byte[] imageContent = file1.getBytes();
			String imageName = file1.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file2.isEmpty()) {
			byte[] imageContent = file2.getBytes();
			String imageName = file2.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file3.isEmpty()) {
			byte[] imageContent = file3.getBytes();
			String imageName = file3.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file4.isEmpty()) {
			byte[] imageContent = file4.getBytes();
			String imageName = file4.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file5.isEmpty()) {
			byte[] imageContent = file5.getBytes();
			String imageName = file5.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file6.isEmpty()) {
			byte[] imageContent = file6.getBytes();
			String imageName = file6.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file7.isEmpty()) {
			byte[] imageContent = file7.getBytes();
			String imageName = file7.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file8.isEmpty()) {
			byte[] imageContent = file8.getBytes();
			String imageName = file8.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file9.isEmpty()) {
			byte[] imageContent = file9.getBytes();
			String imageName = file9.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file10.isEmpty()) {
			byte[] imageContent = file10.getBytes();
			String imageName = file10.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}
		if (!file11.isEmpty()) {
			byte[] imageContent = file11.getBytes();
			String imageName = file11.getOriginalFilename();
			blogInfoDAO.saveImage(imageContent, imageName, content_id, false);
		}

		BlogInfoVO blogInfoVO = new BlogInfoVO();
		if (!themeImage.isEmpty()) {
			String imageName = themeImage.getOriginalFilename();
			byte[] imageContent = BlogInfoUtil.imageResize(themeImage.getBytes(), imageName);
			//blogInfoDAO.saveImage(imageContent, imageName, content_id, true);
			blogInfoVO.setImageContent(imageContent);
		}
		List<SubMenuContent> subMenuContents = new ArrayList<SubMenuContent>();
		SubMenuContent subMenuContent = new SubMenuContent();
		subMenuContent.setContent_header(blogHeader);
		subMenuContent.setContentHeaderTag(headerTag);
		subMenuContent.setContent(contentBody);
		subMenuContent.setSubmenu_ref(techType);
		subMenuContent.setMenu_ref("miscellaneous");
		subMenuContent.setConetent_id(content_id);
		subMenuContent.setCreated_date(new Date());
		subMenuContent.setPostedBy(postedBy);
		subMenuContents.add(subMenuContent);
		blogInfoVO.setSubMenuContents(subMenuContents);
		if (blogInfoDAO.insert(blogInfoVO)) {
			return myblogindex("");
		} else {
			throw new Exception("Unable to save");
		}
	}

	@RequestMapping("/editInfo")
	public ModelAndView editInfo(@RequestParam("contentId") String contentId, @RequestParam("isBlog") boolean isBlog) {
		ModelAndView modelAndView = null;
		if (isBlog) {
			modelAndView = new ModelAndView();
			BlogInfoVO blogInfoVO = blogInfoDAO.getBlogInfo(contentId, true);
			blogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
			modelAndView.addObject("blogInfo", blogInfoVO);
			modelAndView.addObject("editMode", true);
			modelAndView.setViewName("post");
		}
		modelAndView.addObject("editMode", true);
		return modelAndView;
	}

	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ModelAndView saveContent(@ModelAttribute("updateContent") String content,
			@ModelAttribute("content_id") String content_id, @RequestParam("image") MultipartFile file,
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
				updatedBlogInfoVO.setSubMenu(blogInfoCache.getSubMneu());
				modelAndView.addObject("blogInfo", updatedBlogInfoVO);
				modelAndView.addObject("editMode", false);
				modelAndView.setViewName("post");
			}
			return modelAndView;
		} else {
			throw new Exception("Unable to save");
		}

	}
}