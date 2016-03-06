package com.paleo.blog.service.sys.role_group.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.pojo.sys.role_group.RoleGroup;
import com.paleo.blog.remote.sys.role_group.IRoleGroupService;
import com.paleo.blog.service.sys.role_group.dao.IRoleGroupDao;
import com.paleo.blog.tools.SPT;

@Service("blog.service.sys.role_group.imp.RoleGroupService")
public class RoleGroupService implements IRoleGroupService{

	@Resource
	private IRoleGroupDao roleGroupDao;
	
	@Override
	public List<RoleGroup> getRoleGroupList(RoleGroup roleGroup) {
		return roleGroupDao.getRoleGroupList(roleGroup);
	}

	@Override
	public PageInfo<RoleGroup> showRoleGroupPage(RoleGroup roleGroup, RowBounds bounds) {
		return new PageInfo<RoleGroup>(roleGroupDao.showRoleGroupPage(roleGroup,bounds));
	}

	@Transactional
	public RoleGroup addRoleGroup(RoleGroup roleGroup) {
		roleGroupDao.addRoleGroup(roleGroup);
		//获取upper roleGroup后再获取tree
		String tree = (String) this.getRoleGroupById(roleGroup.getUpperRoleGroupId()).getTree();
		//拼接
		tree  = new StringBuilder(tree).append(roleGroup.getRoleGroupId()).append(SPT.DOT.getSpt()).toString();
		roleGroup.setTree(tree);
		roleGroup.setLevel(tree.split("\\"+SPT.DOT.getSpt()).length-1);
		roleGroupDao.updateRoleGroup(roleGroup);
		return roleGroup;
	}

	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void updateRoleGroup(RoleGroup roleGroup) {
		//获取roleGroup后再获取tree
		RoleGroup father = this.getRoleGroupById(roleGroup.getUpperRoleGroupId());
		String newTree = new StringBuilder(father.getTree()).append(roleGroup.getRoleGroupId()).append(SPT.DOT.getSpt()).toString();
		roleGroup.setTree(newTree);
		
		String oldTree = roleGroup.getTree();
		//level的改变
		int levRes = newTree.split("\\"+SPT.DOT.getSpt()).length - oldTree.split("\\"+SPT.DOT.getSpt()).length;
		roleGroup.setLevel(newTree.split("\\"+SPT.DOT.getSpt()).length);
		
		roleGroupDao.updateRoleGroup(roleGroup);
		//将关联的子角色组的tree修改为newTree,前缀替换
		roleGroupDao.updateTrees(oldTree,newTree,levRes);
	}

	@Override
	public RoleGroup getRoleGroupById(Long roleGroupId) {
		return roleGroupDao.getRoleGroupById(roleGroupId);
	}

	@Override
	public List<RoleGroup> getChildRoleGroupList(RoleGroup roleGroup) {
		return roleGroupDao.getChildRoleGroupList(roleGroup);
	}

	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void deleteRoleGroupById(Long roleGroupId) {
		 roleGroupDao.deleteRoleGroupById(roleGroupId);
	}

}
