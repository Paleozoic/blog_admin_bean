package com.paleo.blog.remote.sys.dept;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.pojo.sys.dept.Dept;

public interface IDeptService {

	/**
	 * 更新部门
	 * @param dept
	 */
	void updateDept(Dept dept);
	/**
	 * 新增部门
	 * @param dept
	 * @return
	 */
	Dept addDept(Dept dept);
	/**
	 * 根据ID获取部门
	 * @param deptId
	 * @return
	 */
	Dept getDeptById(Long deptId);
	/**
	 * 获取部门列表
	 * @param dept
	 * @return
	 */
	List<Dept> getDeptList(Dept dept);
	/**
	 * 获取部门分页
	 * @param dept
	 * @param bounds
	 * @return
	 */
	PageInfo<Dept> showDeptPage(Dept dept,RowBounds bounds);
	/**
	 * dept的子部门列表（不包含子部门的子部门，只包含下一级部门）
	 * @param dept
	 * @return
	 */
	List<Dept> getChildDeptList(Dept dept);
	/**
	 * 删除机构（逻辑删除）
	 * @param deptId
	 */
	void deleteDeptById(Long deptId);

}
