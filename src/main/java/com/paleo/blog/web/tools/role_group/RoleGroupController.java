package com.paleo.blog.web.tools.role_group;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paleo.blog.pojo.sys.role_group.RoleGroup;
import com.paleo.blog.remote.sys.role_group.IRoleGroupService;
import com.paleo.blog.web.core.BaseController;

@RequestMapping("/tools/role_group/")
@Controller("blog.web.tools.role_group.ctrl.RoleGroupController")
public class RoleGroupController extends BaseController{

	@Resource(name="blog.service.sys.role_group.imp.RoleGroupService")
	private IRoleGroupService roleGroupService;
	
	@RequestMapping(value = "single_select", method = { RequestMethod.POST, RequestMethod.GET })
	public String single_select(RoleGroup bo,ModelMap rps){
		List<RoleGroup> roleGroupList = roleGroupService.getRoleGroupList(bo);
		rps.addAttribute("roleGroupList", roleGroupList);
		return "_common/tools/role_group/single_select";
	}
}
