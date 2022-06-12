package cn.miact.util;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验工具类
 */
public class ValidatorUtils {

    /**
     * 校验器
     */
    private static Validator validator = Validation
            .buildDefaultValidatorFactory().getValidator();

    /**
     * 参数校验
     * @param object
     * @param groups
     * @param <T>
     */
    public static <T> void validate(T object,Class... groups){
        Set<ConstraintViolation<T>> validate =
                validator.validate(object, groups);

        // 如果校验结果不为空
        if (!CollectionUtils.isEmpty(validate)){
            StringBuilder exceptionMessage = new StringBuilder();
            validate.forEach(ConstraintViolation -> {
                exceptionMessage.append(ConstraintViolation.getMessage());
            });

            throw new RuntimeException(exceptionMessage.toString());
        }
    }

}
