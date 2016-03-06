package com.paleo.test.sys.menu;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.paleo.blog.model.sys.menu.factory.TreeFactory;
import com.paleo.blog.model.sys.menu.tree.bjui.TreeUtils;
import com.paleo.blog.model.sys.menu.tree.ztree.MenuTree;
import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.remote.sys.account.IAccountService;
import com.paleo.blog.remote.sys.menu.IMenuService;
import com.paleo.blog.tools.json.jackson.JacksonUtils;
import com.paleo.blog.tools.reflect.WrapUtils;
import com.paleo.test.basic.BasicTest;

public class menu extends BasicTest{

	@Resource(name="blog.service.sys.menu.imp.MenuService")
	private IMenuService menuService;
	
	@Resource(name="blog.service.sys.account.imp.AccountService")
	private IAccountService accountService;
	
	@Test
	public void testBuildTree(){
		Map user = WrapUtils.model();
		List<Menu> menuMapList = menuService.getMenuList();
		List<MenuTree> menuList = JacksonUtils.json2List(JacksonUtils.obj2Json(menuMapList), MenuTree.class);
		MenuTree res = TreeFactory.buildTree(menuList);
		System.out.println(JacksonUtils.obj2Json(res));
	}
	
	@Test
	public void testBuildMenuTree(){
		com.paleo.blog.model.sys.menu.tree.bjui.MenuTree mt = TreeUtils.buildMenuTree(accountService.getAccount(3l).getMenuList());
		System.out.println(mt);
	}
	
}
