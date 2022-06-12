package cn.miact.domain.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页查询返回实体
 */
@Data
public class PageResult<T> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -711471600510586478L;

    /**
     * 当前页号
     */
    private Integer pageNo;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 动态数据
     */
    private T data;
}
