package com.fuhuitong.nitri.sys.mapper;

import com.fuhuitong.nitri.sys.entity.AweekStatistics;
import com.fuhuitong.nitri.sys.entity.Dashboard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * pc首页统计
 *
 * @Author Wang
 * @Date 2019/6/6 0006 16:19
 **/
public interface DashboardMapper extends BaseMapper<Dashboard> {
    Dashboard homePageStatistics(@Param("merchantsId") String merchantsId);

    List<AweekStatistics> getAweekStatistics(@Param("merchantsId") String merchantsId);
}
