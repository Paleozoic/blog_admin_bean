package com.paleo.blog.remote.sys.menu;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.pojo.sys.menu.Menu;


public interface IMenuService {
	/**
	 * 获取菜单列表
	 * @return
	 */
	List<Menu> getMenuList();
	/**
	 * 根据ID获取菜单
	 * @param menuId
	 * @return
	 */
	Menu getMenuById(Long menuId);
	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 */
	Menu addMenu(Menu menu);
	/**
	 * 删除菜单
	 * @param menuId
	 */
	void deleteMenuById(Long menuId);
	/**
	 * 删除菜单，包括其下子菜单
	 * @param menuId
	 */
	void deleteMenuIncludeChildrenById(Long menuId);
	/**
	 * 更新菜单
	 * @param bo
	 */
	void updateMenu(Menu menu);

	/**
	 * 菜单排序
	 * @param menuTreeJson JSON.stringify($.fn.zTree.getZTreeObj("menuTree_${pageId}").getNodes())
	 */
	void sortMenu(String menuTreeJson);
	/**
	 * 功能菜单列表（分页）
	 * @param menu menu里面的menuId为父菜单ID，menuName为功能菜单的name
	 * @param bounds
	 * @return
	 */
	PageInfo<Menu> showMenuChildrenPage(Menu menu, RowBounds bounds);
	/**
	 * 获取角色已授权的菜单
	 * @param roleId
	 * @return
	 */
	List<Menu> getRoleMenuList(Long roleId);
	/**
	 * 获取用户已被授权的菜单
	 * @param userId
	 * @return
	 */
	List<Menu> getUserMenuList(Long userId);
	/**
	 * 判断是否menuCode是否唯一
	 * @param menu
	 * @return
	 */
	List<Menu> isUnique(Menu menu);
	/**
	 * 根据menuCodes获取Menus
	 * @param hasMenuCode 含有menuCode的List<Menu>
	 * @return
	 */
	List<Menu> getMenuByMenuCodes(List<Menu> hasMenuCode);
}
