package cn.miact.domain.dto;

import cn.miact.util.LocalDateTimeStringConverter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Excel导出实体对象
 */
@Data
public class UserExportDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1920074186328295956L;

    /**
     * String 类型
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * Integer 类型
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * Long类型
     */
    @ExcelProperty(value = "版本号")
    private Long version;

    /**
     * LocalDateTime 类型
     */
    @ExcelProperty(value = "创建时间",converter = LocalDateTimeStringConverter.class)
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒")
    private LocalDateTime created;

}
