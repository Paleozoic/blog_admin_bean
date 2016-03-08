package com.paleo.blog.web.sys.role_group;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.pojo.sys.role_group.RoleGroup;
import com.paleo.blog.remote.sys.role.IRoleService;
import com.paleo.blog.remote.sys.role_group.IRoleGroupService;
import com.paleo.blog.tools.mvc.ctrl.MSG;
import com.paleo.blog.tools.mvc.page.PageUtils;
import com.paleo.blog.tools.mvc.view.EmptyViewResolver;
import com.paleo.blog.web.core.BaseController;
import com.paleo.blog.web.core.mvc.bjui.bo.BjuiView;

@RequestMapping("/sys/role_group/")
@Controller("blog.web.sys.role_group.ctrl.RoleGroupController")
public class RoleGroupController extends BaseController{
	
	@Resource(name="blog.service.sys.role_group.imp.RoleGroupService")
	private IRoleGroupService roleGroupService;
	@Resource(name="blog.service.sys.role.imp.RoleService")
	private IRoleService roleService;
	
	@RequestMapping(value = "list_role_group_tree", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_role_group_tree(RoleGroup bo,ModelMap rps){
		bo.setRoleGroupId(1l);
		List<RoleGroup> roleGroupList = roleGroupService.getRoleGroupList(bo);
		rps.put("roleGroupList", roleGroupList);
		return "sys/role_group/list_role_group_tree";
	}
	
	@RequestMapping(value = "list_role_group", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_role_group(RoleGroup bo,ModelMap rps){
		PageInfo<RoleGroup> roleGroupPage = roleGroupService.showRoleGroupPage(bo,PageUtils.buildRowBounds(req()));
		rps.put("roleGroupPage", roleGroupPage);
		rps.addAttribute("roleGroupTreeId",req().getParameter("roleGroupTreeId"));//前端ztree的div id
		return "sys/role_group/list_role_group";
	}
	
	@RequestMapping(value = "add_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String add_view(RoleGroup bo,ModelMap rps){
		rps.addAttribute("roleGroupTreeId",req().getParameter("roleGroupTreeId"));//前端ztree的div id
		rps.addAttribute("bo",bo);
		return "sys/role_group/add";
	}
	
	@RequestMapping(value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public String add(RoleGroup bo,ModelMap rps){
		try{
			roleGroupService.addRoleGroup(bo);
			BjuiView.success(MSG.ADD_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.ADD_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "edit_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit_view(RoleGroup bo,ModelMap rps){
		rps.addAttribute("bo",roleGroupService.getRoleGroupById(bo.getRoleGroupId()));
		return "sys/role_group/edit";
	}
	
	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit(RoleGroup bo,ModelMap rps){
		try{
			roleGroupService.updateRoleGroup(bo);
			BjuiView.success(MSG.UPDATE_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.UPDATE_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;//我估计去掉FreeMarker的配置，这里返回null也可以
	}
	
	@RequestMapping(value = "delete",  method = { RequestMethod.POST, RequestMethod.GET })
	public String delete(RoleGroup bo,ModelMap rps){
		try {
			int childRoleGroupNum = roleGroupService.getChildRoleGroupList(bo).size();
			int childRoleNum = roleService.getRoleListInGroup(bo).size();
			if(childRoleGroupNum>0){//1. 判断是否存在子角色组，存在子角色组则不允许删除，返回结果
				BjuiView.fail("该角色组下存在"+childRoleGroupNum+"个子角色组，不允许删除！");
			}else if(childRoleNum>0){//2. 如果不存在子角色组，判断角色组是否有用户，有用户则不允许删除，返回结果
				BjuiView.fail("该角色组下存在"+childRoleNum+"个角色，不允许删除！");
			}else{//3. 否则便可删除角色组
				roleGroupService.deleteRoleGroupById(bo.getRoleGroupId());
				BjuiView.success(MSG.DELETE_SUCCESS.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			BjuiView.fail(MSG.DELETE_FAIL.getMsg());
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
}
