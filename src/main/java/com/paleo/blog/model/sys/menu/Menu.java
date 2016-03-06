package com.paleo.blog.model.sys.menu;

import java.util.Iterator;
import java.util.List;

import com.paleo.blog.model.sys.menu.bo.CheckedMenu;
import com.paleo.blog.tools.reflect.WrapUtils;

public class Menu {
	
	public final static int MENU_TYPE_FUNC = 1;//1为功能菜单
	/**
	 * 增加checked属性
	 * @param menuList 可授权菜单列表
	 * @param roleMenuList 被授权角色已被授权菜单列表
	 * @return 
	 */
	public static List<CheckedMenu> buildCheckedMenus(List<com.paleo.blog.pojo.sys.menu.Menu> menuList,List<com.paleo.blog.pojo.sys.menu.Menu> roleMenuList) {
		List<CheckedMenu> checkedMenuList = WrapUtils.list();
		try{
			for(int i=0;i<menuList.size();i++){
				com.paleo.blog.pojo.sys.menu.Menu menu = menuList.get(i);
				for(com.paleo.blog.pojo.sys.menu.Menu rmenu:roleMenuList){
					CheckedMenu checkedMenu = new CheckedMenu(menu);
					if((menu.getMenuId()).compareTo(rmenu.getMenuId())==0){
						checkedMenu.setChecked(true);
					}
					checkedMenuList.add(checkedMenu);
				}
			}
		}catch(Exception e){
			//异常返回自身
			e.printStackTrace();
		}
		return checkedMenuList;
	}

	/**
	 * 去除掉功能菜单，构建主菜单
	 * @param menuList
	 * @return
	 */
	public static List<com.paleo.blog.model.sys.account.bo.Menu> buildMainMenus(List< com.paleo.blog.model.sys.account.bo.Menu> menuList) {
		for (Iterator<com.paleo.blog.model.sys.account.bo.Menu> it = menuList.iterator(); it.hasNext();) {
			if(it.next().getType()==MENU_TYPE_FUNC){
				it.remove();
			}
		}
		return menuList;
	}
}
