package com.paleo.blog.shiro.realm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.pojo.sys.role.Role;
import com.paleo.blog.pojo.sys.user.User;
import com.paleo.blog.remote.sys.menu.IMenuService;
import com.paleo.blog.remote.sys.role.IRoleService;
import com.paleo.blog.remote.sys.user.IUserService;

public class LoginRealm extends AuthorizingRealm {
	
	@Resource(name="blog.service.sys.user.imp.UserService")
	private IUserService userService;
	@Resource(name="blog.service.sys.role.imp.RoleService")
	private IRoleService roleService;
	@Resource(name="blog.service.sys.menu.imp.MenuService")
	private IMenuService menuService;
	
	/**
	 * 用户权限验证，这一步获取权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		String userName = (String) principals.fromRealm(this.getName()).iterator().next(); 
		Long userId =  (Long) principals.getPrimaryPrincipal();
		if( userId != null ){ 
	    	// 查询用户授权信息
	        List<Role> userRoles=roleService.getUserRoleList(userId);
	        List<Menu> userMenus =menuService.getUserMenuList(userId);
	        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); 
	        if( userRoles != null && !userRoles.isEmpty() ){ 
	            for( Role role:userRoles ){
	            	info.addRole(Long.toString(role.getRoleId()));
	            }
	         } 
	        if( userMenus != null && !userMenus.isEmpty() ){ 
	            for( Menu menu:userMenus ){
	            	info.addStringPermission(menu.getMenuCode());
	            }
	         } 
	        return info;
	      }       
		return null;
	}

	/**
	 * 用户登陆验证，这一步获取登陆信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken; 
		String userName = token.getUsername();
		String password = String.valueOf(token.getPassword());
		if(userName==null||password==null) return null;//提交登陆表单，会进入这里2次。第一次userName为null，password有值。第二次userName、password都有值。原因不明。
		User user = userService.getUserByName(userName);
		if(user!=null){
			//这一步传入数据库查找的用户密码，返回SimpleAuthenticationInfo，Shiro会通过SimpleAuthenticationInfo的password和UsernamePasswordToken的password进行匹配
			return new SimpleAuthenticationInfo(user.getUserId(),user.getPassword(),this.getName());
		}else{
			throw new UnknownAccountException("用户不存在！");
		}
	}

}
