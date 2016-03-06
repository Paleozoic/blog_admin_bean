package com.paleo.blog.web.core.index;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paleo.blog.model.sys.account.AccountUtils;
import com.paleo.blog.model.sys.account.bo.Menu;
import com.paleo.blog.model.sys.menu.tree.bjui.TreeUtils;
import com.paleo.blog.remote.sys.account.IAccountService;
import com.paleo.blog.tools.json.jackson.JacksonUtils;
import com.paleo.blog.web.core.BaseController;

@Controller("blog.web.core.index.ctrl.IndexController")
public class IndexController extends BaseController{

	@Resource(name="blog.service.sys.account.imp.AccountService")
	private IAccountService accountService;
	
	@RequestMapping("/")
	public String index(ModelMap rps){
		Long userId = AccountUtils.getUserId();
		List<Menu> menuList = accountService.getAccount(userId).getMenuList();
		rps.put("menuTreeJson", JacksonUtils.obj2Json(TreeUtils.buildMenuTree(com.paleo.blog.model.sys.menu.Menu.buildMainMenus(menuList))));
		return "_common/main/index";
	}
}
