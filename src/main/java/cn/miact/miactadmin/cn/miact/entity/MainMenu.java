package cn.miact.miactadmin.cn.miact.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author : mw
 * @Classname :MainMenu
 * @createDate : 2022-05-31 17:41:38
 * @Description :
 */
@Getter
@Setter
@TableName("tb_main_menu")
@Accessors(chain = true)
public class MainMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String pid;
    private String name;
    private String iconClass;
    private String url;
    private Integer sortOrder;
    private Integer status;
    @TableField(exist = false)
    private List<MainMenu> childNode;
}
