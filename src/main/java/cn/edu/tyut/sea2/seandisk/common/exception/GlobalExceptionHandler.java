package cn.edu.tyut.sea2.seandisk.common.exception;

import cn.edu.tyut.sea2.seandisk.common.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实验室统一业务异常
 *
 * @author klenkiven
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> error(Exception e) {
        e.printStackTrace();
        return Result.fail().message(e.getMessage());
    }

    @ExceptionHandler(GeneralException.class)
    @ResponseBody
    public Result<?> labException(GeneralException e) {
        e.printStackTrace();
        return Result.fail().message(e.getMessage());
    }
}
