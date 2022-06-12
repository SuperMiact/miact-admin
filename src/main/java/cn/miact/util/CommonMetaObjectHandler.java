package cn.miact.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 公共元数据处理器
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * 在插入时增加字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新建时，开始填充系统字段！");

        this.strictInsertFill(metaObject,"created",
                LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,"modified",
                LocalDateTime.class,LocalDateTime.now());

        this.strictInsertFill(metaObject,"creator",
                String.class,"TODO 从上下文获取当前人");
        this.strictInsertFill(metaObject,"operator",
                String.class,"TODO 从上下文获取当前人");

        this.strictInsertFill(metaObject,"status",
                Integer.class,0);
        this.strictInsertFill(metaObject,"version",
                Long.class,1L);
    }

    /**
     * 在更新时增加字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新时，开始填充系统字段！");

        this.strictUpdateFill(metaObject,"modified",
                LocalDateTime.class,LocalDateTime.now());

        this.strictUpdateFill(metaObject,"operator",
                String.class,"TODO 从上下文获取修改人");
    }
}
