package cn.miact.miactadmin.cn.miact.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : mw
 * @Classname :User
 * @createDate : 2022-05-31 15:25:00
 * @Description :
 */
@Data
@TableName("tb_user")
public class User {
    private Integer id;
    private String name;
    private String password;
}
