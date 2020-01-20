package com.fuhuitong.nitri.sys.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuhuitong.nitri.sys.entity.SysConfig;
import com.fuhuitong.nitri.sys.mapper.SysConfigMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2019-09-23
 */
@Service
public class SysConfigServiceImpl {
    @Autowired
    private SysConfigMapper sysConfigMapper;


    /**
     * 根据配置编号查询配置 （支付通道用）
     * @param configCode
     * @return
     */
    @Cacheable(key = "#p0", cacheNames = "sysPayChannel")
    public SysConfig selectOneConfig(String configCode){
        return sysConfigMapper.selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigCode,configCode));
    }


    /**
     * 根据配置编号修改配置  （支付通道用）
     * @param sysConfig
     * @return
     */
    @CacheEvict(allEntries = true, cacheNames = {"sysPayChannel"})
    public int updateSysConfig(SysConfig sysConfig){
        return sysConfigMapper.update(sysConfig,new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigCode,sysConfig.getConfigCode()));
    }


}
