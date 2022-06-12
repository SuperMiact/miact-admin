package cn.miact.domain.common;

import cn.miact.exception.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结果模型
 */
@Data
@ApiModel(
        value = "统一返回结果实体",
        description = "封装统一返回结果信息实体"
)
public class ResponseResult<T> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3066438227389023754L;

    /**
     * 是否成功
     * 本次请求是否成功
     */
    @ApiModelProperty(
            name = "success",
            value = "是否成功",
            required = true,
            dataType = "Boolean"
    )
    private Boolean success;

    /**
     * 编码
     * 错误代码
     */
    @ApiModelProperty(
            name = "code",
            value = "编码",
            required = false,
            dataType = "String"
    )
    private String code;

    /**
     * 描述信息
     * 失败的信息，参数验证失败，数据库保存失败这些信息
     */
    @ApiModelProperty(
            value = "描述信息"
    )
    private String message;

    /**
     * 结果
     */
    @ApiModelProperty(
            value = "泛型结果T"
    )
    private T result;

    /**
     * 成功
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T result){
        ResponseResult<T> responseResult = new ResponseResult<>();

        responseResult.setCode("200");
        responseResult.setSuccess(Boolean.TRUE);
        responseResult.setResult(result);

        return responseResult;
    }

    /**
     * 成功
     * @param result
     * @param <T>
     * @param message
     * @return
     */
    public static <T> ResponseResult<T> success(T result,String message){
        ResponseResult<T> responseResult = new ResponseResult<>();

        responseResult.setCode("200");
        responseResult.setSuccess(Boolean.TRUE);
        responseResult.setResult(result);
        responseResult.setMessage(message);

        return responseResult;
    }


    /**
     * 失败
     */
    public static <T> ResponseResult<T> failure(String code,String message){
        ResponseResult<T> responseResult = new ResponseResult<>();

        responseResult.setSuccess(Boolean.FALSE);
        responseResult.setCode(code);
        responseResult.setMessage(message);

        return responseResult;
    }

    /**
     * 失败
     */
    public static <T> ResponseResult<T> failure(ErrorCodeEnum codeEnum){
        return failure(codeEnum.getCode(),codeEnum.getMessage());
    }
}
