package cn.miact.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户DO实体
 */
@Data
@TableName("tb_user")
public class UserDO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4327800274107483993L;

    /*** 用户主信息 ***/
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /*** 系统主信息 ***/
    /**
     * 数据库主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据的创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 数据的修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modified;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 最后修改者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operator;

    /**
     * 逻辑删除字段：0：正常，1：逻辑删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    /**
     * 版本号
     * 数据每一次更改都要累加这个版本号，这样也是为了做数据的乐观锁可以使用的
     */
    @Version  //在执行update或者insert操作时，给我们自动+1
              //@Version作用  可以为更新操作来加一个乐观锁的控制，而这个乐观锁就是使用version字段来做的
              //举个例子  如果我要更新的数据，当前数据库版本号里面是1，我在传递时，传递的版本号是2 跟我的数据库不一致 此条更新语句就没有效果 从而通过这个版本号来达到锁的目的
    @TableField(fill = FieldFill.INSERT)
    private Long version;
}
