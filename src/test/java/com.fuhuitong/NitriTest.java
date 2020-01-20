package com.fuhuitong;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.xgd.entity.NitriArticle;
import com.fuhuitong.nitri.xgd.mapper.NitriSiteMapper;
import com.fuhuitong.nitri.xgd.service.impl.NitriArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NitriTest
{
	@Autowired
	private NitriSiteMapper siteMapper;

	@Autowired
	private NitriArticleServiceImpl articleService;

	/**
	 * 后台管理系统查询文章列表
	 */
	@Test
	public void testArticleList()
	{
		Page<NitriArticle> articlePage = new Page<>(2, 5);
		Page<NitriArticle> articleList = articleService.articleList(articlePage);
		articleList.getRecords().forEach(article ->
				log.info("文章内容：{}", article));
	}

	/**
	 * 公众号查询文章列表
	 */
	@Test
	public void testSelectList()
	{
		List<NitriArticle> articleList = articleService.selectList(6L,true);
		Assert.assertNotNull(articleList);
		log.info("公众号文章列表：{}",articleList);
	}
}
