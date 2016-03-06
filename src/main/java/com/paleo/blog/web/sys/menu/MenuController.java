package com.paleo.blog.web.sys.menu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.remote.sys.menu.IMenuService;
import com.paleo.blog.tools.mvc.ctrl.MSG;
import com.paleo.blog.tools.mvc.ctrl.OPT;
import com.paleo.blog.tools.mvc.page.PageUtils;
import com.paleo.blog.tools.mvc.view.EmptyViewResolver;
import com.paleo.blog.tools.mvc.view.JsonView;
import com.paleo.blog.web.core.BaseController;
import com.paleo.blog.web.core.mvc.bjui.bo.BjuiView;
import com.paleo.blog.web.core.mvc.bjui.validator.Msg;

@RequestMapping("/sys/menu/")
@Controller("blog.web.sys.menu.ctrl.MenuController")
public class MenuController extends BaseController{
	
//	private static Logger log = LoggerFactory.getLogger(MenuController.class);
	
	@Resource(name="blog.service.sys.menu.imp.MenuService")
	private IMenuService menuService;
	
	@RequestMapping(value = "list_menu_children", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_menu_children(Menu bo,ModelMap rps){
		PageInfo<Menu> cmenuPage = menuService.showMenuChildrenPage(bo,PageUtils.buildRowBounds(req()));
		rps.put("cmenuPage", cmenuPage);
		return "sys/menu/list_menu_children";
	}
	
	@RequestMapping(value = "list_menu_tree", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_menu_tree(ModelMap rps){
		List<Menu> menuList = menuService.getMenuList();
		rps.put("menuList", menuList);
		return "sys/menu/list_menu_tree";
	}
	
	@RequestMapping(value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public String add(Menu bo,ModelMap rps){
		if(IsOPT(OPT.ADD)){
			//更新到数据库
			try{
				menuService.addMenu(bo);
				BjuiView.success(MSG.ADD_SUCCESS.getMsg(),true);
			}catch(Exception e){
				BjuiView.fail(MSG.ADD_FAIL.getMsg(),false);
			}
			return EmptyViewResolver.EMPTY_VIEW;//我估计去掉FreeMarker的配置，这里返回null也可以
		}
		//返回视图
		rps.addAttribute("bo",bo);
		return "sys/menu/add";
	}
	
	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit(Menu bo,ModelMap rps){
		if(IsOPT(OPT.UPDATE)){
			try {
				menuService.updateMenu(bo);
				BjuiView.success(MSG.UPDATE_SUCCESS.getMsg(),true);
			}catch(Exception e){
				e.printStackTrace();
				BjuiView.fail(MSG.UPDATE_FAIL.getMsg(),false);
			}
			return EmptyViewResolver.EMPTY_VIEW;
		}
		//返回视图
		rps.addAttribute("bo",menuService.getMenuById(bo.getMenuId()));
		return "sys/menu/edit";
	}
	
	@RequestMapping(value = "delete",  method = { RequestMethod.POST, RequestMethod.GET })
	public String delete(Menu bo,ModelMap rps){
		try {
			menuService.deleteMenuIncludeChildrenById(bo.getMenuId());
			BjuiView.success(MSG.DELETE_SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			BjuiView.fail(MSG.DELETE_FAIL.getMsg());
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "sort",  method = { RequestMethod.POST, RequestMethod.GET })
	public String sort(@RequestParam("menuTreeJson") String menuTreeJson,ModelMap rps){
		try {
			menuService.sortMenu(menuTreeJson);
			JsonView.success(res(), MSG.UPDATE_SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			JsonView.error(res(), MSG.UPDATE_FAIL.getMsg());
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@ResponseBody
	@RequestMapping(value = "is_unique", method = { RequestMethod.POST, RequestMethod.GET })
	public String is_unique(Menu bo){
		List<Menu> menuList = menuService.isUnique(bo);
		//{"ok":"名字很棒!"} => 成功， {"error":"错误消息"} => 失败
		if(menuList.size()>0 && bo.getMenuId().compareTo(menuList.get(0).getMenuId())!=0){//用户名存在，切存在的不是被修改的用户
			return Msg.error("标识码已存在！");
		}else{
			return Msg.ok("标识码可用。");
		}
	}
}
