package com.paleo.blog.shiro.chain.imp;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.remote.sys.menu.IMenuService;
import com.paleo.blog.shiro.chain.remote.AbstractFilterChainDefinitionsService;

/**
 * 简单实现shiro过滤链动态加载，来源从数据库中加载
 * 
 * @author HyDe Paleo修改于 2015-12
 *
 */
public class SimpleFilterChainDefinitionsService extends AbstractFilterChainDefinitionsService {

	@Resource(name="blog.service.sys.menu.imp.MenuService")
	private IMenuService menuService;

	/**
	 * 实现父类方法，从资源表中取出数据，并封装成shiro过滤器可以加载的对像
	 */
	@Override
	public Map<String, String> initOtherPermission() {
		List<Menu> menuList = menuService.getMenuList();
		Map<String, String> map = new HashMap<String, String>();
		for (Menu menu : menuList) {
			if(StringUtils.isNotBlank(menu.getUrl())){
				map.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING,(String)menu.getMenuCode()));
			}
		}
		return map;
	}

}
