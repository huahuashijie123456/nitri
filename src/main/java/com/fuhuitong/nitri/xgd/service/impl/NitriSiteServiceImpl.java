package com.fuhuitong.nitri.xgd.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuhuitong.nitri.xgd.entity.NitriSite;
import com.fuhuitong.nitri.xgd.mapper.NitriSiteMapper;
import com.fuhuitong.nitri.xgd.queryObject.NitriSiteQo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 栏目管理 服务实现类
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Service
@Slf4j
public class NitriSiteServiceImpl  {

	@Autowired
	private NitriSiteMapper siteMapper;

	/**
	 * 获取栏目列表
	 *
	 * @return 获取栏目列表
	 */
	public List<NitriSite> siteList()
	{
		LambdaQueryWrapper<NitriSite> wrapper = new LambdaQueryWrapper<>();
		// 删除标记：0-未删除  1-已删除
		wrapper.eq(NitriSite::getDeleteSign, 0);
		List<NitriSite> siteList = siteMapper.selectList(wrapper);
		log.info("栏目列表：{}",siteList);
		return siteList;
	}


	/**
	 * 添加栏目
	 *
	 * @param siteQo 栏目实体类
	 */
	public Integer siteAdd(NitriSiteQo siteQo)
	{
		NitriSite targetSite = new NitriSite();
		BeanUtils.copyProperties(siteQo, targetSite);
		int insertResult = siteMapper.insert(targetSite);
		log.info("添加栏目：{}，添加结果：{}", targetSite, insertResult > 0 ? "成功" : "失败");
		return insertResult;
	}

	/**
	 * 查找栏目
	 *
	 * @param id 栏目编号
	 * @return 栏目
	 */
	public NitriSiteQo find(Long id)
	{
		NitriSiteQo siteQo = null;
		NitriSite site = siteMapper.selectById(id);
		if (null != site)
		{
			siteQo = new NitriSiteQo();
			BeanUtils.copyProperties(site, siteQo);
		}
		log.info("查找栏目：{}", site);
		return siteQo;
	}

	/**
	 * 修改栏目
	 * @param siteQo 栏目实体类
	 * @return 结果
	 */
	public Integer siteUpdate(NitriSiteQo siteQo)
	{
		NitriSite targetSite = new NitriSite();
		BeanUtils.copyProperties(siteQo, targetSite);
		int updateResult = siteMapper.updateById(targetSite);
		log.info("修改栏目：{}，修改结果：{}", targetSite, updateResult > 0 ? "成功" : "失败");
		return updateResult;
	}

	/**
	 * 删除栏目
	 *
	 * @param id 栏目编号
	 * @return 结果
	 */
	public Integer delete(Long id)
	{
		int deleteResult = siteMapper.deleteById(id);
		log.info("栏目编号：{}，删除结果：{}", id, deleteResult > 0 ? "成功" : "失败");
		return deleteResult;
	}
}
