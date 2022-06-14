package cn.miact.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 数据查询DTO实体
 */
@Data
@Accessors(chain = true)
public class UserQueryDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 271946202232989048L;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空！")
    private String username;

    private String password;
}
