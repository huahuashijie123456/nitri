package com.fuhuitong.nitri.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuhuitong.nitri.sys.entity.Dict;
import com.fuhuitong.nitri.sys.mapper.DictMapper;
import com.fuhuitong.nitri.common.utils.DictUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 12:02
 **/
@Service
public class DictServiceImpl {
    @Autowired
    private DictMapper dictMapper;

    public List<Dict> getDictList() {
        return dictMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 查询字典带分页
     *
     * @param page
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public IPage<Dict> getDictAllPage(Page<Dict> page, Dict dict) {
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<Dict>();
        if(StringUtils.isNotBlank(dict.getType())){
            queryWrapper.eq(Dict::getType,dict.getType());
        }
        if(StringUtils.isNotBlank(dict.getLabel())){
            queryWrapper.like(Dict::getLabel,dict.getLabel());
        }
        queryWrapper.orderByDesc(Dict::getType);
        return dictMapper.selectPage(page, queryWrapper);
    }


    /**
     * 添加字典
     *
     * @param dict
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addDict(Dict dict) {
        DictUtils.delDictRedis();
        return dictMapper.insert(dict);

    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delDict(String id) {
        DictUtils.delDictRedis();
        return dictMapper.deleteById(id);
    }

    /**
     * 修改字典
     *
     * @param dict
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateDict(Dict dict) {
        DictUtils.delDictRedis();
        return  dictMapper.updateById(dict);
    }

    public List<Map<String,String >> dicType(){
        List<Map<String,String >> list = new ArrayList<>();
        List<Dict> listDic = dictMapper.dictType();
        for (Dict dict:listDic ) {
            Map<String,String > map = new HashMap<>();
            map.put("value",dict.getType());
            map.put("text",dict.getDescription()+"("+dict.getType()+")");
            map.put("description",dict.getDescription());
            list.add(map);
        }
        return list;
    }
}
