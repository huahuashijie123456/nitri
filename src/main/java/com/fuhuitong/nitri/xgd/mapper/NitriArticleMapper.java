package com.fuhuitong.nitri.xgd.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.xgd.entity.NitriArticle;
import org.springframework.stereotype.Repository;

import java.awt.*;

/**
 * <p>
 * 文章管理 Mapper 接口
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Repository
public interface NitriArticleMapper extends BaseMapper<NitriArticle> {

	/**
	 * 后台管理系统文章分页列表
	 * @param pageInfo 分页对象
	 * @return 分页对象
	 */
	Page<NitriArticle> articleList(Page<NitriArticle> pageInfo);
}
