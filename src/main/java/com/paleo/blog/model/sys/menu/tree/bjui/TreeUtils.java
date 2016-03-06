package com.paleo.blog.model.sys.menu.tree.bjui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

public class TreeUtils {
	
	private static final long TREE_ROOT = 0l;
	
	/**
	 * http://www.oschina.net/code/snippet_551773_19709
	 * 这个转换还需要考虑一些极端的情况，导致的错误，暂不测试
	 * TODO 根据Account取出的menuList构造MenuTree
	 * @param menuList
	 * @return
	 */
	 public static MenuTree buildMenuTree(List<com.paleo.blog.model.sys.account.bo.Menu> menuList) {
	        /**
	         * 定义“数组-链表”，该数组链表的每一项相当于一深度为2的小树
	         * Map的key相当于“数组”的某一项，Map的value相当于该key所拥有的“链表”
	         * 这里，key为父节点ID，list为具有相同父节点ID的所有同级子节点实体list（属于该父节点的所有子节点）
	         */
	        Map<Long, List<MenuTree>> arrayListMap = new HashMap<Long, List<MenuTree>>();
	 
	        for (com.paleo.blog.model.sys.account.bo.Menu menu : menuList) {
	            // 变量定义务必在循环内，对象是引用，不能重复使用同一个对象变量
	        	MenuTree e2t = new MenuTree();
	        	e2t.setMenu(buildMenuTree(menu));
	 
	            Long fatherId = menu.getUpperMenuId();
	            // 获取当前遍历结点的父ID，并判断该父节点的数组链表项是否存在，如果该“数组项-链表项”不存在，则新建一个，并放入“数组-链表”
	            if (arrayListMap.get(fatherId) == null) {
	                List<MenuTree> list = new ArrayList<MenuTree>();
	                list.add(e2t);
	                arrayListMap.put(fatherId, list);
	            } else {
	                List<MenuTree> valueList = arrayListMap.get(fatherId);
	                valueList.add(e2t);
	                arrayListMap.put(fatherId, valueList);
	            }
	        }
	        // 以上，至此，第一遍遍历完毕，非叶子节点都拥有一个“数组-链表项”，也即“最小的树”已创建完毕
	 
	        // 以下，对“数组链表”Map进行遍历，更改“最小的树”的从属关系（更改指针指向），也即把所有小树组装成大树
	        for (Map.Entry<Long, List<MenuTree>> entry : arrayListMap
	                .entrySet()) {
	            // 获取当前遍历“数组项-链表项”的链表项，并对链表项进行遍历，从“数组-链表”小树中找到它的子节点，并将该子节点加到该小树的children中
	            List<MenuTree> smallTreeList = new ArrayList<MenuTree>();
	            smallTreeList = entry.getValue();
	            int nodeListSize = smallTreeList.size();
	            for (int i = 0; i < nodeListSize; i++) {
	                Long findID = smallTreeList.get(i).getMenuId();
	                List<MenuTree> findList = arrayListMap.get(findID);
	                // 以下操作不能取出对象存放在变量中，否则将破坏树的完整性
	                smallTreeList.get(i).setChildren(findList);
	            }
	        }
	        // 获取以0为父Id的链表项，该链表项是根节点实体，里面已封装好各子节点，可以由于多个根节点，即这些根结点的父Id都为0
	        List<MenuTree> rootNodeList = arrayListMap.get(TREE_ROOT);
	 
	        return rootNodeList.get(0);
	    }
	
	
	/**
	 * TODO 获取root menu，数据库查出来放在第一个，但是这里用for循环取
	 * @param menuList
	 * @return
	 */
	private static com.paleo.blog.model.sys.account.bo.Menu  getRootMenu(List<com.paleo.blog.model.sys.account.bo.Menu> menuList){
		for(com.paleo.blog.model.sys.account.bo.Menu menu:menuList){
			if(menu.getUpperMenuId()==TREE_ROOT){
				return menu;
			}
		}
		return null;
	}
	
	/**
	 * TODO 根据menu构造MenuTree
	 * @param menu
	 * @return
	 */
	private static MenuTree buildMenuTree(com.paleo.blog.model.sys.account.bo.Menu menu){
		BeanCopier copier = BeanCopier.create(com.paleo.blog.model.sys.account.bo.Menu.class, MenuTree.class, true); 
		MenuTree menuTree = new MenuTree();
		copier.copy(menu, menuTree, new MenuConverter());
		menuTree.sortChildren();//排序
		return menuTree;
	}
	/**
	 * http://czj4451.iteye.com/blog/2044101
	 * https://github.com/cglib/cglib/blob/master/cglib/src/main/java/net/sf/cglib/core/Converter.java
	 * 转换器，将url前面的/去掉，用于bjui封装的ztree菜单
	 */
	static class MenuConverter implements Converter {  
		  
		@Override
		public Object convert(Object value, Class target, Object context) {
			if("setUrl".equals(context)){
				if(((String)value).length()-1>0){
					return ((String)value).substring(1);
				}
			}
			return value;
		}  
	}  
	
	@Test
	public void test(){
		/**
		 * 测试一下BeanCopier
		 */
		com.paleo.blog.model.sys.account.bo.Menu menu = new com.paleo.blog.model.sys.account.bo.Menu();
		menu.setLevel(11111);
		buildMenuTree(menu);
	}
	
	
}
