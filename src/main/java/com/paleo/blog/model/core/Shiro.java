package com.paleo.blog.model.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.paleo.blog.model.core.bo.ShiroMenus;
import com.paleo.blog.pojo.sys.menu.Menu;
import com.paleo.blog.tools.reflect.WrapUtils;

public class Shiro {
	//"perms[{0}]"; // 资源结构格式
	//正则 http://www.cnblogs.com/yirlin/archive/2006/04/12/373222.html
	private static final String PATTERN_PERMS = "^perms\\[(.*)\\]$";
	
	@SuppressWarnings("unchecked")
	public static ShiroMenus getMenuCodesFromChains(Map<String, String> chains) {
		ShiroMenus res = new ShiroMenus();
		List<com.paleo.blog.model.core.bo.Menu> hasMenuCode = WrapUtils.list();
		List<com.paleo.blog.model.core.bo.Menu> noMenuCode = WrapUtils.list();
		Pattern p=Pattern.compile(PATTERN_PERMS);  
		Set<Entry<String, String>> c =  chains.entrySet();
		for (Iterator<Entry<String, String>> iterator = c.iterator(); iterator.hasNext();) {
			com.paleo.blog.model.core.bo.Menu e = new com.paleo.blog.model.core.bo.Menu();
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			e.setUrl(entry.getKey());
			e.setPerms(entry.getValue());
			Matcher m=p.matcher(entry.getValue());  
			if(m.find()){  
				e.setMenuCode( m.group(1));
			}else{//没有匹配
				e.setMenuCode(null);
			}
			if(StringUtils.isEmpty(e.getMenuCode())){
				noMenuCode.add(e);
			}else{
				hasMenuCode.add(e);
			}
		}
		res.setHasMenuCode(hasMenuCode);
		res.setNoMenuCode(noMenuCode);
		return res;
	}

	/**
	 * 合并数据，将perms，url，menuCode，menu信息合并在一起,menuList与hasMenuCode的对应顺序是一样的
	 * @param menuList 根据hasMenuCode的menuCode查出来的menuList
	 * @param hasMenuCode  含有menuCode，url，perms的List<Map>
	 * @return
	 * @throws Exception 
	 */
	public static List<Menu> addPermsToMenus(List<Menu> menuList, List<Menu> hasMenuCode) throws Exception {
		if(menuList.size()!=hasMenuCode.size()){
			throw new Exception("数据异常！");
		}else{
			List<Menu> res = WrapUtils.list();
			for(int i=0;i<hasMenuCode.size();i++){
				Menu e = hasMenuCode.get(i);
				e.setMenuName(menuList.get(i).getMenuName());
				e.setUpperMenuName( menuList.get(i).getUpperMenuName());
				res.add(e);
			}
			return res;
		}
	}

}
