package com.paleo.blog.model.sys.role;

import java.util.List;
import java.util.Map;

import com.paleo.blog.model.sys.role.bo.SelectedRole;
import com.paleo.blog.tools.reflect.WrapUtils;

public class Role {
	/**
	 * 增加selected属性和isManaged
	 * @param roleList 授权用户可授权角色列表
	 * @param userRoleList 被授权用户已被授权角色列表
	 * @return 
	 */
	public static List<SelectedRole> buildSelectedRoles(List<com.paleo.blog.pojo.sys.role.Role> roleList,List<com.paleo.blog.pojo.sys.role.Role> userRoleList){
		List<SelectedRole> selectedRoleList = WrapUtils.list();
		try{
			for(int i=0;i<roleList.size();i++){
				com.paleo.blog.pojo.sys.role.Role role = roleList.get(i);
				for(com.paleo.blog.pojo.sys.role.Role urole:userRoleList){
					SelectedRole selectedRole = new SelectedRole(role);
					if((role.getRoleId()).compareTo(urole.getRoleId())==0){
						selectedRole.setSelected(true);
						role.setIsManaged(userRoleList.get(i).getIsManaged());
					}
					selectedRoleList.add(selectedRole);
				}
			}
		}catch(Exception e){
			//异常返回自身
		}
		return selectedRoleList;
	}
}
