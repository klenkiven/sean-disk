package cn.edu.tyut.sea2.seandisk.module.disk.aspect;

import cn.edu.tyut.sea2.seandisk.module.disk.annotation.DiskOpLog;
import cn.edu.tyut.sea2.seandisk.module.disk.mapper.FileOpLogMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 记录文件操作日志的切面
 * @author KlenKiven
 */
@Aspect
@Component
@RequiredArgsConstructor
public class DiskOpAspect {

    public final FileOpLogMapper opLogMapper;

    @Pointcut("@annotation(cn.edu.tyut.sea2.seandisk.module.disk.annotation.DiskOpLog)")
    public void diskOpPointcut() { }

    @Around("diskOpPointcut()")
    public Object doDiskLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String op = methodSignature.getMethod().getAnnotation(DiskOpLog.class).value();
        System.out.println("发生业务" + op + ", Method Signature: " + methodSignature);
        return joinPoint.proceed();
    }

}
