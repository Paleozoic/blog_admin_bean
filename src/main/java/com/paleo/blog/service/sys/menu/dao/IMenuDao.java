package com.paleo.blog.service.sys.menu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.paleo.blog.pojo.sys.menu.Menu;


public interface IMenuDao {

	public List<Menu> getMenuList();

	public Menu getMenuById(@Param("menuId")Long menuId);

	public void addMenu(@Param("bo")Menu menu);

	public void updateMenu(@Param("bo")Menu menu);

	public void deleteMenuById(@Param("menuId")Long menuId);

	public void deleteMenuIncludeChildrenById(@Param("menuId")Long menuId);

	public void sortMenu(@Param("menuList")List<com.paleo.blog.model.sys.menu.tree.ztree.Menu> menuList);

	public List<Menu> showMenuChildrenPage(@Param("bo")Menu menu, RowBounds bounds);
	
	List<Menu> getRoleMenuList(@Param("roleId")Long roleId);

	public List<Menu> getAllMenuList();

	public List<Menu> getUserMenuList(@Param("userId")Long userId);

	public List<Menu> isUnique(@Param("bo")Menu menu);

	public List<Menu> getMenuByMenuCodes(@Param("menuList")List<Menu> hasMenuCode);
}
