package com.paleo.blog.model.sys.menu.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.paleo.blog.model.sys.menu.tree.ztree.MenuTree;

/**
 * 树工厂，用于创建一棵树
 * @author 原作者miles Paleo修改于 2015-12月
 * @datetime 13-12-13 下午4:34 
 * 
 */
public class TreeFactory {
    /**
     * 通过一个实现了Tree接口的集合，构建成一棵树
     * （实际上返回的也是一个集合，只是集合中的元素只有根节点，所有的子节点都在根节点下）
     *
     * @param nodes 要转成树的集合
     * @param clazz Tree的实现
     * @param <T>
     * @return
     */
	//注：这个方法主要是讲数据库的List转为Tree，然后通过Jackson将tree转为json传到前端。但是由于ztree支持list形式的数据，所以此方法仅作备用。
    public static  MenuTree buildTree(List<MenuTree> nodes) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        Map<Long, MenuTree> linkedHashMap = new LinkedHashMap<Long,MenuTree>();
        //convert list to map
        for (MenuTree node : nodes) {
            linkedHashMap.put(node.getMenuId(), node);
        }
        //遍历map中的所有元素，设置孩子节点
        Iterator<Map.Entry<Long, MenuTree>> iterator = linkedHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, MenuTree> entry = iterator.next();
            MenuTree temp = entry.getValue();
            if (temp.getUpperMenuId().intValue() == 0) {
                trees.add(temp);
                continue;
            }
            MenuTree parent = linkedHashMap.get(temp.getUpperMenuId());
            if (parent == null) {
                throw new NullPointerException("上级id为[" + temp.getUpperMenuId() + "]的数据不存在!");
            }
            parent.addChild(temp);
        }
        return trees.isEmpty()?null:trees.get(0);
    }
}