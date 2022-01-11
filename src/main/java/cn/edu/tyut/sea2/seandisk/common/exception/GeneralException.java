package cn.edu.tyut.sea2.seandisk.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * 自定义全局异常类（固定处理）
 *
 * @author klenkiven
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralException extends RuntimeException {

    private Integer code = 500;

    /**
     * 抛出普通的异常信息
     * @param msg 异常信息
     */
    public GeneralException(String msg) {
        super(msg);
    }

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message 错误信息
     * @param code 错误号码
     */
    public GeneralException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param status 结果枚举对象
     */
    public GeneralException(HttpStatus status) {
        super(status.getReasonPhrase());
        this.code = status.value();
    }

    public GeneralException(String message, Exception e) {
        super(message, e);
    }

    @Override
    public String toString() {
        return "LabException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
