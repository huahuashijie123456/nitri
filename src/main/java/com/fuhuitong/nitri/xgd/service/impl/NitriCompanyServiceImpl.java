package com.fuhuitong.nitri.xgd.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.xgd.entity.NitriCompany;
import com.fuhuitong.nitri.xgd.mapper.NitriCompanyMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 企业 服务实现类
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Service
public class NitriCompanyServiceImpl {


    @Autowired
    private NitriCompanyMapper nitriCompanyMapper;
    /**
     * 院属企业分页列表
     * @param pageInfo 分页对象
     * @param nitriCompany
     * @return 分页对象
     * TODO 无阅读量
     */
    public Page<NitriCompany> queryNitriCompanyPage(Page<NitriCompany> pageInfo, NitriCompany nitriCompany) {
        Page<NitriCompany> page=nitriCompanyMapper.queryNitriCompanyPage(pageInfo,nitriCompany);
        return  page;
    }

    /**
     * 企业详情
     * @param id 企业id
     * @return  企业详情
     */
    public NitriCompany queryNitriCompany(String id) {
        NitriCompany nitriCompany=nitriCompanyMapper.selectById(id);
        return  nitriCompany;
    }

    /**
     * 删除企业
     * @param nitriCompany
     * @return
     */

    public int delNitriCompany(NitriCompany nitriCompany) {
        return nitriCompanyMapper.deleteById(nitriCompany.getId());
    }


    /**
     * 修改企业信息
     * @param nitriCompany
     * @return
     */
    public int updateNitriCompanyByCompanyType(NitriCompany nitriCompany) {
        return nitriCompanyMapper.updateById(nitriCompany);
    }

    /**
     * 修改企业上下架
     * @param nitriCompany 修改信息
     * @return
     */
    public int updateNitriCompanyByStatusUp(NitriCompany nitriCompany) {
        LambdaQueryWrapper<NitriCompany> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(NitriCompany::getId,nitriCompany.getId());
        wrapper.eq(NitriCompany::getStatus,1);
        return nitriCompanyMapper.update(nitriCompany,wrapper);
    }
    /**
     * 修改企业上架
     * @param nitriCompany 修改信息
     * @return
     */
    public int updateNitriCompanyByStatusDown(NitriCompany nitriCompany) {
        LambdaQueryWrapper<NitriCompany> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(NitriCompany::getId,nitriCompany.getId());
        wrapper.eq(NitriCompany::getStatus,0);
        return nitriCompanyMapper.update(nitriCompany,wrapper);
    }


    public int addNitriCompany(NitriCompany nitriCompany){
        return nitriCompanyMapper.insert(nitriCompany);
    }
    /**
     * 修改企业
     * @param nitriCompany
     * @return
     */
    public int updateNitriCompany(NitriCompany nitriCompany) {
        return nitriCompanyMapper.updateById(nitriCompany);
    }
}
