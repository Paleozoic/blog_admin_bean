package com.paleo.blog.web.core.demo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paleo.blog.remote.demo.IDemoService;

@Controller("blog.web.core.demo.DemoController")
@RequestMapping("/demo/")
public class DemoController {
	
	private static Logger log = LoggerFactory.getLogger(DemoController.class);
	
	@Resource(name="blog.service.demo.imp.DemoService")
	private IDemoService demoService;
	
	@RequestMapping(value = "test", method = { RequestMethod.POST, RequestMethod.GET })
	public String test(ModelMap rps,HttpServletRequest req,HttpServletResponse res) {
		System.out.println(demoService.test());
		return "demo/test";
	}
}
