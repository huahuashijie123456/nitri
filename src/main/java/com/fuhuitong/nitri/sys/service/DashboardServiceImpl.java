package com.fuhuitong.nitri.sys.service;

import com.fuhuitong.nitri.sys.entity.AweekStatistics;
import com.fuhuitong.nitri.sys.entity.Dashboard;
import com.fuhuitong.nitri.sys.mapper.DashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/6/6 0006 16:45
 **/
@Service
public class DashboardServiceImpl {
    @Autowired
    private DashboardMapper dashboardMapper;

    public Dashboard homePageStatistics(String merchantsId) {
        return dashboardMapper.homePageStatistics(merchantsId);
    }

    public List<AweekStatistics> getAweekStatistics(String merchantsId) {
        return dashboardMapper.getAweekStatistics(merchantsId);
    }
}
