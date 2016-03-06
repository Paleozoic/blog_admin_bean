package com.paleo.blog.web.tools.dept;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paleo.blog.pojo.sys.dept.Dept;
import com.paleo.blog.remote.sys.dept.IDeptService;
import com.paleo.blog.web.core.BaseController;

@RequestMapping("/tools/dept/")
@Controller("blog.web.tools.dept.ctrl.DeptController")
public class DeptController extends BaseController{

	@Resource(name="blog.service.sys.dept.imp.DeptService")
	private IDeptService deptService;
	
	@RequestMapping(value = "single_select", method = { RequestMethod.POST, RequestMethod.GET })
	public String single_select(Dept bo,ModelMap rps){
		List<Dept> deptList = deptService.getDeptList(bo);
		rps.addAttribute("deptList", deptList);
		return "_common/tools/dept/single_select";
	}
}
