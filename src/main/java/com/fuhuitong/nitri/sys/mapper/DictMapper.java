package com.fuhuitong.nitri.sys.mapper;

import com.fuhuitong.nitri.sys.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 11:59
 **/

public interface DictMapper extends BaseMapper<Dict> {
    List<Dict> dictType();
}
