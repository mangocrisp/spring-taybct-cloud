package io.github.mangocrisp.spring.taybct.common.exception;

import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalExceptionReporter;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalPrinter;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

/**
 * 全局异常处理，处理可预见的异常
 *
 * @author zhaoxz
 * @since 2023-03-15
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalRestExceptionTranslator {

    /**
     * 记录请求的异常
     */
    final IGlobalExceptionReporter globalExceptionReporter;

    /**
     * 把异常如何抛出
     */
    final IGlobalPrinter globalExceptionPrinter;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleError(MissingServletRequestParameterException e) {
        log.warn("缺少请求参数:{}", e.getMessage());
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        return R.fail(ResultCode.VALIDATE_ERROR.getCode(), message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleError(MethodArgumentTypeMismatchException e) {
        log.warn("请求参数格式错误:{}", e.getMessage());
        String message = String.format("请求参数格式错误: %s", e.getName());
        return R.fail(ResultCode.VALIDATE_ERROR.getCode(), message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleError(MethodArgumentNotValidException e) {
        log.warn("参数验证失败:{}", e.getMessage());
        return handleError(e.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleError(BindException e) {
        log.warn("参数绑定失败:{}", e.getMessage());
        return handleError(e.getBindingResult());
    }

    private R<?> handleError(BindingResult result) {
        FieldError error = result.getFieldError();
        if (Objects.nonNull(error)) {
            String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
            return R.fail(ResultCode.VALIDATE_ERROR.getCode(), message);
        }
        return R.fail(ResultCode.VALIDATE_ERROR);
    }

    /**
     * 最后找不到是什么异常再被这里拦截
     *
     * @param e        异常
     * @param request  请求
     * @param response 响应
     */
    @ExceptionHandler(Throwable.class)
    public void UnknownException(Throwable e
            , HttpServletRequest request
            , HttpServletResponse response) {
        // 记录日志
        globalExceptionReporter.recording(request, e);
        // 抛出异常
        globalExceptionPrinter.print(e, response);
    }
}
