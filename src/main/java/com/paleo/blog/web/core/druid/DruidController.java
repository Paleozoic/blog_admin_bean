package com.paleo.blog.web.core.druid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paleo.blog.web.core.BaseController;

@RequestMapping("/core/druid/")
@Controller("blog.web.core.druid.ctrl.DruidController")
public class DruidController extends BaseController{

	@RequestMapping("/druid_monitor")
	public String druid(ModelMap rps){
		return "_common/core/druid/monitor";
	}
}
