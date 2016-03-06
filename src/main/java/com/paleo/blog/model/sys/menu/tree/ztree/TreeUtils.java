package com.paleo.blog.model.sys.menu.tree.ztree;

import java.util.ArrayList;
import java.util.List;

import com.paleo.blog.tools.SPT;
import com.paleo.blog.tools.json.jackson.JacksonUtils;

public class TreeUtils {
	
	private static final long TREE_ROOT = 0l;
	/**
	 * 遍历多叉树，不带虚根节点
	 * @param menuTree
	 * @return //此方法只是返回List<Menu> 并没有维护 tree(叶子路径) orderNo(同级排序号) level(级别，虚根菜单为1级)
	 */
//	 public List<Menu> iteratorTree(MenuTree menuTree){  
//        List<Menu> menuList = new ArrayList<Menu>(); 
//        if(menuTree != null) {
//        	if(menuTree.getUpperMenuId().intValue()==0){//这一句的逻辑判断：如果是虚根节点，upperMenuId为0，则先添加至List
//        		menuList.add(menuTree.getMenu());
//        	}
//            for (MenuTree menu : menuTree.getChildren()){  
//            	menuList.add(menu.getMenu());
//                if (!menu.isLeaf() ) {     
//                	menuList.addAll(iteratorTree(menu));
//                }  
//            }  
//        }  
//        return menuList;  
//	}  
	   
	/**
	 * 遍历多叉树，不带虚根节点
	 * @param menuTree
	 * @param tree 第一次迭代，tree应为null，之后每一次递归，tree都应该为其父菜单的tree
	 * @return //需要同时维护的字段tree(叶子路径) orderNo(同级排序号) level(级别，虚根菜单为1级)
	 */
	public static List<Menu> iteratorTree(MenuTree menuTree,String tree){  
        List<Menu> menuList = new ArrayList<Menu>(); 
        if(menuTree != null) {
        	if(menuTree.getUpperMenuId()==TREE_ROOT){//这一句的逻辑判断：如果是虚根节点，upperMenuId为0，则先添加至List
        		tree = tree+menuTree.getMenuId()+SPT.DOT.getSpt();
        		menuTree.setTree(tree);
        		menuTree.setLevel(tree.split("\\"+SPT.DOT.getSpt()).length-1);
        		menuList.add(menuTree.getMenu());
        	}
        	List<MenuTree> children = menuTree.getChildren();
        	MenuTree child;
        	//先序遍历
        	//注意：j是迭代深度，当child是叶子节点，即没有children，则会回到第一层，所以将j赋值为0
            for (int i=0;i<children.size();i++){  
            	child = children.get(i);
            	child.setOrderNo(i+1);
            	String curTree = tree+child.getMenuId()+SPT.DOT.getSpt();
            	child.setTree(curTree);
            	child.setLevel(curTree.split("\\"+SPT.DOT.getSpt()).length-1);
            	menuList.add(child);
                if (!child.isLeaf() ) { 
                	/**
                	 * 递归传当前节点的上一级节点的tree
                	 * 1-----2-----3  i=0 upperMenu的tree为tree（上一个递归传进来的tree，当前节点父级tree）
                	 * 		 |
                	 * 		 ------4  i=1 upperMenu的tree为curTree（当前节点的tree）
                	 */
                	menuList.addAll(iteratorTree(child,i==0?tree:curTree));
                }
            }  
        }  
        return menuList;  
	} 

}
