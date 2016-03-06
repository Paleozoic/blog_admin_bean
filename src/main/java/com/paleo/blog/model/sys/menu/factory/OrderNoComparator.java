package com.paleo.blog.model.sys.menu.factory;

import java.util.Comparator;

import com.paleo.blog.model.sys.menu.tree.ztree.MenuTree;
/**
 * 根据MenuTree的OrderNo来对统计树节点排序（升序）
 * @author Paleo
 *
 */
@SuppressWarnings("rawtypes")
public class OrderNoComparator implements Comparator {
    // 按照节点编号比较
    public int compare(Object o1, Object o2) {
        int j1 = ((MenuTree)o1).getOrderNo();
        int j2 = ((MenuTree)o2).getOrderNo();
        return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
    }   
}
