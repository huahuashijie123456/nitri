package com.fuhuitong.nitri.sys.service;

import com.fuhuitong.nitri.sys.entity.SysArea;
import com.fuhuitong.nitri.sys.mapper.SysAreaMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 行政区划
 *
 * @Author Wang
 * @Date 2019/5/10 0010 16:09
 **/
@Service
public class SysAreaServiceImpl {
    @Autowired
    private SysAreaMapper sysAreaMapper;

    /**
     * 查询行政区划 含省 tree树 pc
     *
     * @return
     */

    @Cacheable(key = "#root.methodName", cacheNames = "findSysAreaPC")
    public List<SysArea> findSysAreaList() {
        return sysAreaMapper.selectList(new QueryWrapper<SysArea>().eq("tree_level", "0").ne("status", "2").or().eq("tree_level", "1"));
    }

    @Cacheable(key = "#root.methodName", cacheNames = "findSysAreaH5")
    public List<SysArea> findH5List() {
        return sysAreaMapper.selectList(new QueryWrapper<SysArea>().eq("tree_level", "1").orderByAsc("pinyin"));
    }


    /**
     * 修改行政区划
     *
     * @param sysArea
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public int updateSysAreaList(SysArea sysArea) {
        return sysAreaMapper.update(sysArea, new QueryWrapper<SysArea>().eq("area_code", sysArea.getAreaCode()));
    }


}
