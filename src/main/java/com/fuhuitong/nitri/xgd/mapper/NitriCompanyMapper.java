package com.fuhuitong.nitri.xgd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.xgd.entity.NitriCompany;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 企业 Mapper 接口
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Repository
public interface NitriCompanyMapper extends BaseMapper<NitriCompany> {

    /**
     * 院属企业分页列表
     * @param pageInfo 分页信息
     * @param nitriCompany 检索条件
     * @return
     */
    Page queryNitriCompanyPage(Page pageInfo, NitriCompany nitriCompany);

    /**
     * 修改企业上下架
     * @param nitriCompany 修改信息
     * @return
     */
    int updateNitriCompanyByStatus(NitriCompany nitriCompany);
}
