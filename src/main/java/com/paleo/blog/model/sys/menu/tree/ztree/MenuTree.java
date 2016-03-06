package com.paleo.blog.model.sys.menu.tree.ztree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.paleo.blog.model.sys.menu.factory.OrderNoComparator;

public class MenuTree extends Menu{
	
	private List<MenuTree> children=new ArrayList<MenuTree>();
	
	public MenuTree addChild(MenuTree child) {
		children.add(child);
		return this;
	}
	
	/**
	 * 是否叶节点
	 * @return
	 */
	public boolean isLeaf() {
        if (children != null&&!children.isEmpty()) {
        	return false;
        }
        return true;   
    }

	 // 孩子节点排序（这个没测试）
	public void sortChildren() {
       // 对本层节点进行排序
       // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
       Collections.sort(children, new OrderNoComparator());
       // 对每个节点的下一层节点进行排序
       for (Iterator it = children.iterator(); it.hasNext();) {
           ((MenuTree) it.next()).sortChildren();
       }
   }
	

	/**
	 * 这个只是遍历打印，没有返回值，需要使用全局变量
	 */
//    public void traverse() {  
//        if (getMenuId().intValue()==0)  
//            return;  
//        System.out.println(getMenuName());  
//        if (this.isLeaf())  
//            return;  
//        for (MenuTree menu:children) {  
//        	menu.traverse();  
//        }  
//    }  
	 
	public List<MenuTree> getChildren() {
		return children;
	}
	
	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	
}
