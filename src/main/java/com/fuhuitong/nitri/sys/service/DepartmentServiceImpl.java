package com.fuhuitong.nitri.sys.service;

import com.fuhuitong.nitri.sys.entity.Department;
import com.fuhuitong.nitri.sys.mapper.DepartmentMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/24 0024 11:07
 **/
@Service
public class DepartmentServiceImpl {
    @Autowired
    private DepartmentMapper departmentMapper;


    /**
     * 添加部门
     *
     * @param department
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public int addDepartment(Department department) {
        return departmentMapper.insert(department);
    }

    /**
     * 查询部门
     *
     * @return
     */
    public List<Department> getDepartmentList() {
        return departmentMapper.selectList(new QueryWrapper<Department>());
    }

    /**
     * 查询部门及人员
     *
     * @return
     */
    public List<Department> findDepartmentTree() {
        return departmentMapper.findDepartmentTree();
    }


    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public int delDepartmentById(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        queryWrapper.or();
        queryWrapper.eq("parent_id", id);
        return departmentMapper.delete(queryWrapper);
    }


}
