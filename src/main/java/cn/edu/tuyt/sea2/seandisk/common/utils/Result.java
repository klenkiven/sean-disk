package cn.edu.tuyt.sea2.seandisk.common.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 全局统一返回结果类
 *
 * @author klenkiven
 */
@Data
public final class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result(){}

    private static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(T body, HttpStatus status) {
        Result<T> result = build(body);
        result.setCode(status.value());
        result.setMessage(status.getReasonPhrase());
        return result;
    }

    public static <T> Result<T> build(HttpStatus code, String message) {
        Result<T> result = build(null);
        result.setCode(code.value());
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功
     * @param data 数据
     * @param <T> 数据类型
     * @return Result对象
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, HttpStatus.OK);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     * @param data 数据
     * @param <T> 数据类型
     * @return Result对象
     */
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

}
