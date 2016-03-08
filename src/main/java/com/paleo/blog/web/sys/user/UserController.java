package com.paleo.blog.web.sys.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.model.sys.account.AccountUtils;
import com.paleo.blog.pojo.sys.dept.Dept;
import com.paleo.blog.pojo.sys.role.Role;
import com.paleo.blog.pojo.sys.user.DeptUsers;
import com.paleo.blog.pojo.sys.user.User;
import com.paleo.blog.pojo.sys.user.UserRoles;
import com.paleo.blog.remote.sys.dept.IDeptService;
import com.paleo.blog.remote.sys.role.IRoleService;
import com.paleo.blog.remote.sys.user.IUserService;
import com.paleo.blog.tools.encrypt.MD5Utils;
import com.paleo.blog.tools.mvc.ctrl.MSG;
import com.paleo.blog.tools.mvc.page.PageUtils;
import com.paleo.blog.tools.mvc.view.EmptyViewResolver;
import com.paleo.blog.web.core.BaseController;
import com.paleo.blog.web.core.mvc.bjui.bo.BjuiView;
import com.paleo.blog.web.core.mvc.bjui.validator.Msg;

@RequestMapping("/sys/user/")
@Controller("blog.web.sys.user.ctrl.UserController")
public class UserController extends BaseController{
	@Resource(name="blog.service.sys.dept.imp.DeptService")
	private IDeptService deptService;
	@Resource(name="blog.service.sys.user.imp.UserService")
	private IUserService userService;
	@Resource(name="blog.service.sys.role.imp.RoleService")
	private IRoleService roleService;
	
	@RequestMapping(value = "list_dept_tree", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_dept_tree(Dept bo,ModelMap rps){
		//从session获取dept
		bo.setDeptId(1l);
		List<Dept> deptList = deptService.getDeptList(bo);
		rps.put("deptList", deptList);
		return "sys/user/list_dept_tree";
	}
	
	@RequestMapping(value = "list_user", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_user(DeptUsers bo,ModelMap rps){
		PageInfo<User> userPage = userService.showUserPage(bo,PageUtils.buildRowBounds(req()));
		rps.put("userPage", userPage);
		return "sys/user/list_user";
	}
	
	@RequestMapping(value = "add_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String add_view(User bo,ModelMap rps){
		rps.addAttribute("bo",bo);
		return "sys/user/add";
	}
	
	@RequestMapping(value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public String add(User bo,ModelMap rps){
		try{
			bo.setPassword(MD5Utils.customMD5(bo.getPassword()));
			userService.addUser(bo);
			BjuiView.success(MSG.ADD_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.ADD_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;//我估计去掉FreeMarker的配置，这里返回null也可以
	}
	
	@RequestMapping(value = "edit_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit_view(User bo,ModelMap rps){
		rps.addAttribute("bo",userService.getUserById(bo.getUserId()));
		return "sys/user/edit";
	}
	
	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit(User bo,ModelMap rps){
		try{
			userService.updateUser(bo);
			BjuiView.success(MSG.UPDATE_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.UPDATE_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;//我估计去掉FreeMarker的配置，这里返回null也可以
	}
	
	@RequestMapping(value = "authorize_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String authorize_view(UserRoles bo,ModelMap rps){
		List<Role> roleList =  roleService.getRoleList(AccountUtils.getUserId());//登陆用户可分配的角色
		List<Role> userRoleList =  roleService.getUserRoleList(bo.getUser().getUserId());//授权用户已授权的角色
		rps.put("selectedRoleList",com.paleo.blog.model.sys.role.Role.buildSelectedRoles(roleList,userRoleList));
		rps.put("user", userService.getUserById(bo.getUser().getUserId()));
		return "sys/user/authorize";
	}
	
	@RequestMapping(value = "authorize", method = { RequestMethod.POST, RequestMethod.GET })
	public String authorize(UserRoles bo,ModelMap rps){
		try{
			userService.authorizeUser(bo);
			BjuiView.success(MSG.AUTHRIZE_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.AUTHRIZE_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "delete",  method = { RequestMethod.POST, RequestMethod.GET })
	public String delete(User bo,ModelMap rps){
		try {
			userService.deleteUserById(bo.getUserId());
			BjuiView.success(MSG.DELETE_SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			BjuiView.fail(MSG.DELETE_FAIL.getMsg());
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@ResponseBody
	@RequestMapping(value = "is_unique", method = { RequestMethod.POST, RequestMethod.GET })
	public String is_unique(User bo){
		List<User> userList = userService.isUnique(bo);
		//{"ok":"名字很棒!"} => 成功， {"error":"错误消息"} => 失败
		if(userList.size()>0 && bo.getUserId().compareTo(userList.get(0).getUserId())!=0){//用户名存在，切存在的不是被修改的用户
			return Msg.error("用户已存在！");
		}else{
			return Msg.ok("用户名可用。");
		}
	}
}
