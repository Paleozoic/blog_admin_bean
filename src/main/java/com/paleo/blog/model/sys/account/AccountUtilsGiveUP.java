/**
 * 不打算将account信息放进Session，因为account的信息动态更改，并不能及时更正Session内的信息，只能等待下次登录
 */
package com.paleo.blog.model.sys.account;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.paleo.blog.model.sys.account.bo.Dept;
import com.paleo.blog.model.sys.account.bo.Menu;
import com.paleo.blog.model.sys.account.bo.Role;
import com.paleo.blog.model.sys.account.bo.User;
import com.paleo.blog.tools.http.resource.WebResource;


public class AccountUtilsGiveUP {
	
	private final static String ACCOUNT_SESSION_KEY = "session:com.paleo.blog.model.account.Account";
	
	/**
	 * 从会话中取出账号
	 * @return
	 */
	public static Account fromSession() {
		Object obj = WebResource.session().getAttribute(ACCOUNT_SESSION_KEY);
		if (obj != null && obj.getClass() == Account.class) {
			return (Account)obj;
		} else {
			return null;
		}
	}
	
	/**
	 * 用户信息放入会话
	 * @param user
	 */
	public static void addSession(User user) {
		Account account = fromSession();
		assertBean(account);
		account.setUser(user);
		WebResource.session().setAttribute(ACCOUNT_SESSION_KEY, account);
	}
	
	/**
	 * 部门信息放入会话
	 * @param dept
	 */
	public static void addSession(Dept dept) {
		Account account = fromSession();
		assertBean(account);
		account.setDept(dept);
		WebResource.session().setAttribute(ACCOUNT_SESSION_KEY, account);
	}

	/**
	 * 角色信息放入会话
	 * @param roleList
	 */
	public static void addRolesSession(List<Role> roleList) {
		Account account = fromSession();
		assertBean(account);
		account.setRoleList(roleList);
		WebResource.session().setAttribute(ACCOUNT_SESSION_KEY, account);
	}
	
	/**
	 * 菜单信息放入会话
	 * @param menuList
	 */
	public static void addMenusSession(List<Menu> menuList) {
		Account account = fromSession();
		assertBean(account);
		account.setMenuList(menuList);
		WebResource.session().setAttribute(ACCOUNT_SESSION_KEY, account);
	}
	
	/**
	 * 账号信息放入会话
	 * @param account
	 */
	public static void addSession(Account account) {
		WebResource.session().setAttribute(ACCOUNT_SESSION_KEY, account);
	}
	
	/**
	 * 移除账号信息
	 */
	public static void removeSession() {
		WebResource.session().removeAttribute(ACCOUNT_SESSION_KEY);
	}
	
	/**
	 * 检查obj不为空.
	 * @param obj
	 */
    private static void assertBean(Object obj) {
        Validate.validState(obj != null,
        		obj.getClass().getName()+"can not be null!");
    }
}
