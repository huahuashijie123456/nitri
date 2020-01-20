package com.fuhuitong.nitri.xgd.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.common.utils.Log;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.xgd.entity.NitriArticle;
import com.fuhuitong.nitri.xgd.queryObject.NitriArticleQo;
import com.fuhuitong.nitri.xgd.service.impl.NitriArticleServiceImpl;
import com.fuhuitong.nitri.xgd.utils.CustomStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 文章管理 前端控制器
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@RestController
@RequestMapping("article")
@Slf4j
@Api(description = "文章管理")
public class NitriArticleController {

	@Autowired
	private NitriArticleServiceImpl articleService;

	/**
	 * 文章列表
	 * @param page 页码
	 * @param size 每页显示条数
	 * @return 文章列表
	 */
	@ApiOperation(value = "文章列表")
	@GetMapping(value = "articleListByPage")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "size", value = "大小", paramType = "query", dataType = "Integer", required = true)
	})
	public CompletableFuture<ResultJson> articleListByPage(int page, int size)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			if (page < 0 || size < 0)
			{
				ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				Page<NitriArticle> pageInfo = new Page<>(page, size);
				Page<NitriArticle> pages = articleService.articleList(pageInfo);
				return null == pages
						? ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "查询结果为空")
						: ResultJson.ok(pages);
			}
			catch (Exception e)
			{
				log.error("查询文章列表异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@PostMapping("addArticle")
	@Log(value = "添加文章")
	@ApiOperation(value = "添加文章")
	public CompletableFuture<ResultJson> addArticle(NitriArticleQo articleQo)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			if (null == articleQo)
			{
				return ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				Integer insertResult = articleService.articleAdd(articleQo);
				return insertResult > 0
						? ResultJson.ok()
						: ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "添加失败");
			}
			catch (Exception e)
			{
				log.error("添加文章异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@PutMapping("updateArticle")
	@Log(value = "修改文章")
	@ApiOperation(value = "修改文章")
	public CompletableFuture<ResultJson> updateArticle(NitriArticleQo articleQo)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			if (null == articleQo)
			{
				return ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				Integer updateResult = articleService.articleUpdate(articleQo);
				return updateResult > 0
						? ResultJson.ok()
						: ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "修改失败");
			}
			catch (Exception e)
			{
				log.error("修改文章异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@DeleteMapping("deleteArticle")
	@Log(value = "删除文章")
	@ApiOperation(value = "删除文章")
	public CompletableFuture<ResultJson> deleteArticle(Long id)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			// 请求id不为空，且大于等于0
			if (CustomStringUtil.isBlank(id) || id < 0L)
			{
				return ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				Integer deleteResult = articleService.delete(id);
				return deleteResult > 0
						? ResultJson.ok()
						: ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "删除失败");
			}
			catch (Exception e)
			{
				log.error("删除文章异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@GetMapping("findOne")
	@Log(value = "后台管理系统查找文章")
	@ApiOperation(value = "后台管理系统查找文章")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "文章编号", paramType = "query", dataType = "long", required = true),
	})
	public CompletableFuture<ResultJson> findOne(Long id)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			// 请求id不为空，且大于等于0
			if (CustomStringUtil.isBlank(id) || id < 0L)
			{
				return ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				NitriArticleQo articleQo = articleService.find(id);
				return null == articleQo
						? ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "查询结果为空")
						: ResultJson.ok(articleQo);
			}
			catch (Exception e)
			{
				log.error("查找文章异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@GetMapping("articleListBySite")
	@Log(value = "公众号查找文章列表，限制显示个数")
	@ApiOperation(value = "公众号查找文章列表，限制显示个数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "siteId", value = "栏目编号", paramType = "query", dataType = "long", required = true),
	})
	public CompletableFuture<ResultJson> articleListBySite(Long siteId, Boolean limitFlag)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			// 栏目编号不为空，且大于等于0
			if (CustomStringUtil.isBlank(siteId) || siteId < 0L || null == limitFlag)
			{
				return ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				List<NitriArticle> articleList = articleService.selectList(siteId, limitFlag);
				return null == articleList
						? ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "查询结果为空")
						: ResultJson.ok(articleList);
			}
			catch (Exception e)
			{
				log.error("查找文章异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}
}

