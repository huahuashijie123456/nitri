package com.fuhuitong.nitri.sys.mapper;

import com.fuhuitong.nitri.sys.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/24 0024 11:06
 **/
public interface DepartmentMapper extends BaseMapper<Department> {
    List<Department> findDepartmentTree();
}
