package com.paleo.blog.service.sys.dept.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.pojo.sys.dept.Dept;
import com.paleo.blog.remote.sys.dept.IDeptService;
import com.paleo.blog.service.sys.dept.dao.IDeptDao;
import com.paleo.blog.tools.SPT;

@Service("blog.service.sys.dept.imp.DeptService")
public class DeptService implements IDeptService{
	@Resource
	private IDeptDao deptDao;

	public PageInfo<Dept> showDeptPage(Dept dept,RowBounds bounds) {
		return new PageInfo<Dept>(deptDao.showDeptPage(dept,bounds));
	}
	
	
	public List<Dept> getDeptList(Dept dept) {
		return deptDao.getDeptList(dept);
	}
	
	@Transactional
	public Dept addDept(Dept dept) {
		deptDao.addDept(dept);
		//获取upper menu后再获取tree
		String tree = this.getDeptById(dept.getUpperDeptId()).getTree();
		//拼接
		tree  = tree+dept.getDeptId()+SPT.DOT.getSpt();
		dept.setTree(tree);
		dept.setLevel(tree.split("\\"+SPT.DOT.getSpt()).length-1);
		updateDept(dept);
		return dept;
	}
	
	public Dept getDeptById(Long deptId) {
		return deptDao.getDeptById(deptId);
	}
	
	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void updateDept(Dept dept) {
		//获取dept后再获取tree
		Dept father = this.getDeptById(dept.getUpperDeptId());
		String newTree = new StringBuilder(father.getTree()).append(dept.getDeptId()).append(SPT.DOT.getSpt()).toString();
		dept.setTree(newTree);
		
		String oldTree = dept.getTree();
		//level的改变
		int levRes = newTree.split("\\"+SPT.DOT.getSpt()).length - oldTree.split("\\"+SPT.DOT.getSpt()).length;
		dept.setLevel(newTree.split("\\"+SPT.DOT.getSpt()).length);;
		
		
		deptDao.updateDept(dept);
		//将关联的子机构的tree修改为newTree,前缀替换
		deptDao.updateTrees(oldTree,newTree,levRes);
	}

	public List<Dept> getChildDeptList(Dept dept) {
		return deptDao.getChildDeptList(dept);
	}

	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void deleteDeptById(Long deptId) {
		deptDao.deleteDeptById(deptId);
	}

}
