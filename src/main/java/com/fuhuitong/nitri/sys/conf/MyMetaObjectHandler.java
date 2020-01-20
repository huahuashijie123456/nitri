package com.fuhuitong.nitri.sys.conf;

import com.fuhuitong.nitri.common.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Author Wang
 * @Date 2019/4/13 0013 12:58
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("============================");
        String id= snowflakeIdWorker.nextId()+"";
        setFieldValByName("id",id, metaObject);
        setFieldValByName("createTime", System.currentTimeMillis(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        this.setFieldValByName("updateTime",System.currentTimeMillis(), metaObject);
    }
}