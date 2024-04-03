package com.jobRecomment.exception;
import com.jobRecomment.domain.bean.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class globalException {

    @ExceptionHandler(Exception.class)
    public Result globalExceptionHandler(Exception e){
        e.getStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");
    }
}
