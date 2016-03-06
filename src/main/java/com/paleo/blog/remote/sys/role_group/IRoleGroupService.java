package com.paleo.blog.remote.sys.role_group;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.pojo.sys.role_group.RoleGroup;

public interface IRoleGroupService {
	
	List<RoleGroup> getRoleGroupList(RoleGroup roleGroup);

	PageInfo<RoleGroup> showRoleGroupPage(RoleGroup roleGroup, RowBounds bounds);

	RoleGroup addRoleGroup(RoleGroup roleGroup);

	void updateRoleGroup(RoleGroup roleGroup);

	RoleGroup getRoleGroupById(Long roleGroupId);

	List<RoleGroup> getChildRoleGroupList(RoleGroup roleGroup);

	void deleteRoleGroupById(Long roleGroupId);
}
