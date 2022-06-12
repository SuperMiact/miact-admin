package cn.miact.domain.dto;

import cn.miact.util.InsertValidationGroup;
import cn.miact.util.UpdateValidationGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户DTO实体
 * 做数据的中间交换
 * 首先，用户的主信息都是需要的，他是通过数据库的提交保存进来的，所以主信息肯定要
 * 系统信息有哪些要？
 */
@Data
public class UserDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3362680183397838609L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空！",
            groups = {InsertValidationGroup.class})
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空！",
            groups = {InsertValidationGroup.class})
    @Length(min = 6,max = 18,
            message = "密码长度不能少于6位，不能多于18位！")
    private String password;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空！",
            groups = {InsertValidationGroup.class})
    @Email(message = "必须为有效邮箱！")
    private String email;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空",
            groups = {InsertValidationGroup.class})
    @Max(value = 60,message = "年龄不能大于60岁！")
    @Min(value = 10,message = "年龄不能小于18岁！")
    private Integer age;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空！",
            groups = {InsertValidationGroup.class})
    private String phone;

    /**
     * 版本号
     * 数据每一次更改都要累加这个版本号，这样也是为了做数据的乐观锁可以使用的
     */
    @NotNull(message = "版本号不能为空！",
            groups = UpdateValidationGroup.class)
    private Long version;

    /**
     * 创建时间
     */
    private LocalDateTime created;
}
