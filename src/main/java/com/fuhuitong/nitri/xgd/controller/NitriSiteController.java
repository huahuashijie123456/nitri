package com.fuhuitong.nitri.xgd.controller;


import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.common.utils.Log;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.xgd.entity.NitriSite;
import com.fuhuitong.nitri.xgd.queryObject.NitriSiteQo;
import com.fuhuitong.nitri.xgd.service.impl.NitriSiteServiceImpl;
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
 * 栏目管理 前端控制器
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@RestController
@RequestMapping("site")
@Api(description = "栏目管理")
@Slf4j
public class NitriSiteController {

	@Autowired
	private NitriSiteServiceImpl siteService;

	@GetMapping(value = "siteList")
	@Log(value = "获取栏目列表")
	@ApiOperation(value = "获取栏目列表")
	public CompletableFuture<ResultJson> siteList()
	{
		return CompletableFuture.supplyAsync(() ->
		{
			try
			{
				List<NitriSite> sites = siteService.siteList();
				return (null == sites || sites.isEmpty())
						? ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "查询结果为空")
						: ResultJson.ok(sites);
			}
			catch (Exception e)
			{
				log.error("获取栏目列表异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@PostMapping("addSite")
	@Log(value = "添加栏目")
	@ApiOperation(value = "添加栏目")
	public CompletableFuture<ResultJson> addSite(NitriSiteQo siteQo)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			if (null == siteQo)
			{
				return ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				Integer insertResult = siteService.siteAdd(siteQo);
				return insertResult > 0
						? ResultJson.ok()
						: ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "插入失败");
			}
			catch (Exception e)
			{
				log.error("添加栏目异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@GetMapping("findOne")
	@Log(value = "查找栏目")
	@ApiOperation(value = "查找栏目")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "栏目编号", paramType = "query", dataType = "long", required = true),
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
				NitriSiteQo siteQo = siteService.find(id);
				return null == siteQo
						? ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "查询结果为空")
						: ResultJson.ok(siteQo);

			}
			catch (Exception e)
			{
				log.error("查找栏目异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@DeleteMapping("deleteSite")
	@Log(value = "删除栏目")
	@ApiOperation(value = "删除栏目")
	public CompletableFuture<ResultJson> deleteSite(Long id)
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
				Integer deleteResult = siteService.delete(id);
				return deleteResult > 0
						? ResultJson.ok()
						: ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "删除失败");

			}
			catch (Exception e)
			{
				log.error("删除栏目异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}

	@PutMapping("updateSite")
	@Log(value = "修改栏目")
	@ApiOperation(value = "修改栏目")
	public CompletableFuture<ResultJson> updateSite(NitriSiteQo siteQo)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			if (null == siteQo)
			{
				return ResultJson.failure(ResultCode.BAD_REQUEST);
			}
			try
			{
				Integer updateResult = siteService.siteUpdate(siteQo);
				return updateResult > 0
						? ResultJson.ok()
						: ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), "修改失败");
			}
			catch (Exception e)
			{
				log.error("添加栏目异常：", e);
				return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(), e.getMessage());
			}
		});
	}
}

