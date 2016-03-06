package com.paleo.blog.service.sys.dept.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.Page;
import com.paleo.blog.pojo.sys.dept.Dept;

public interface IDeptDao {
	
	public List<Dept> getDeptList(@Param("bo") Dept dept);
	
	public void addDept(@Param("bo") Dept dept);

	public Dept getDeptById(@Param("deptId")Long deptId);

	public void updateDept(@Param("bo")Dept dept);
	
	public void updateTrees(@Param("oldTree")String oldTree, @Param("newTree")String newTree,@Param("levRes")int levRes);

	public Page<Dept> showDeptPage(@Param("bo")Dept dept, RowBounds bounds);

	public List<Dept> getChildDeptList(@Param("bo")Dept dept);

	public void deleteDeptById(@Param("deptId")Long deptId);

}
