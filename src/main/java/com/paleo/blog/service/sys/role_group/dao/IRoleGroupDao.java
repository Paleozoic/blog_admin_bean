package com.paleo.blog.service.sys.role_group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.paleo.blog.pojo.sys.role_group.RoleGroup;

public interface IRoleGroupDao {

	public List<RoleGroup> getRoleGroupList(@Param("bo")RoleGroup roleGroup);

	public List<RoleGroup> showRoleGroupPage(@Param("bo")RoleGroup roleGroup, RowBounds bounds);

	public void addRoleGroup(@Param("bo")RoleGroup roleGroup);

	public void updateRoleGroup(@Param("bo")RoleGroup roleGroup);

	public RoleGroup getRoleGroupById(@Param("roleGroupId")Long roleGroupId);

	public List<RoleGroup> getChildRoleGroupList(@Param("bo")RoleGroup roleGroup);

	public void deleteRoleGroupById(@Param("roleGroupId")Long roleGroupId);

	public void updateTrees(@Param("oldTree")String oldTree, @Param("newTree")String newTree, @Param("levRes")int levRes);

}
