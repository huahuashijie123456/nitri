package com.fuhuitong.nitri.xgd.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.xgd.entity.NitriArticle;
import com.fuhuitong.nitri.xgd.mapper.NitriArticleMapper;
import com.fuhuitong.nitri.xgd.mapper.NitriSiteMapper;
import com.fuhuitong.nitri.xgd.queryObject.NitriArticleQo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章管理 服务实现类
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Service
@Slf4j
public class NitriArticleServiceImpl {

	@Autowired
	private NitriArticleMapper articleMapper;

	@Autowired
	private NitriSiteMapper siteMapper;

	/**
	 * 院属文章分页列表
	 * @param pageInfo 分页对象
	 * @return 分页对象
	 */
	public Page<NitriArticle> articleList(Page<NitriArticle> pageInfo) {
		Page<NitriArticle> page=articleMapper.articleList(pageInfo);
        log.info("文章列表：{}",page);
		return  page;
	}

	/**
	 * 添加文章
	 *
	 * @param articleQo 文章实体类
	 */
	public Integer articleAdd(NitriArticleQo articleQo)
	{
		NitriArticle targetArticle = new NitriArticle();
		BeanUtils.copyProperties(articleQo, targetArticle);
		int insertResult = articleMapper.insert(targetArticle);
		log.info("添加文章：{}，添加结果：{}", targetArticle, insertResult > 0 ? "成功" : "失败");
		return insertResult;
	}

	/**
	 * 修改文章
	 * @param articleQo 文章实体类
	 * @return 结果
	 */
	public Integer articleUpdate(NitriArticleQo articleQo)
	{
		NitriArticle targetArticle = new NitriArticle();
		BeanUtils.copyProperties(articleQo, targetArticle);
		int updateResult = articleMapper.updateById(targetArticle);
		log.info("修改文章：{}，修改结果：{}", targetArticle, updateResult > 0 ? "成功" : "失败");
		return updateResult;
	}

	/**
	 * 删除文章
	 *
	 * @param id 文章编号
	 * @return 结果
	 */
	public Integer delete(Long id)
	{
		int deleteResult = articleMapper.deleteById(id);
		log.info("文章编号：{}，删除结果：{}", id, deleteResult > 0 ? "成功" : "失败");
		return deleteResult;
	}

	/**
	 * 查找文章
	 *
	 * @param id 文章编号
	 * @return 文章
	 */
	public NitriArticleQo find(Long id)
	{
		NitriArticleQo articleQo = null;
		NitriArticle article = articleMapper.selectById(id);
		if (null != article)
		{
			articleQo = new NitriArticleQo();
			BeanUtils.copyProperties(article, articleQo);
			log.info("查找文章：{}", article);
		}
		return articleQo;
	}

	/**
	 * 查找展示的文章列表
	 *
	 * @param limitFlag 是否限制显示条数
	 * @param siteId 栏目编号
	 * @return 文章列表
	 */
	public List<NitriArticle> selectList(Long siteId, Boolean limitFlag)
	{
		// 按是否推荐，创建时间倒序
		LambdaQueryWrapper<NitriArticle> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(NitriArticle::getSiteId, siteId);
		wrapper.orderByDesc(NitriArticle::getRecommendSign);
		wrapper.orderByDesc(NitriArticle::getCreateTime);
		if (limitFlag)
		{
			// 找出文章列表显示条数
			Integer size = siteMapper.selectById(siteId).getSize();
			wrapper.last("LIMIT " + size);
		}
		List<NitriArticle> articleList = articleMapper.selectList(wrapper);
		log.info("返回的文章列表：{}", articleList);
		return articleList;
	}
}
