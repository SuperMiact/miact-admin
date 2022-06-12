package cn.miact.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户VO实体
 * 展示层暴露的信息应该遵循，用到哪个展示哪个的原则，不要把过多的信息暴露到展示层
 * 这样对信息安全和以后的维护都是不太好的
 */
@Data
public class UserVO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1041246946551471321L;

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
}
