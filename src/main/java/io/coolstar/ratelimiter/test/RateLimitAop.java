package io.coolstar.ratelimiter.test;

import io.coolstar.ratelimiter.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
@Component
@Scope
@Aspect
public class RateLimitAop {
 
    @Autowired
    private HttpServletResponse response;
 
    private RateLimiter rateLimiter = new RateLimiter();//比如说，我这里设置"并发数"为1
 
    @Pointcut("@annotation(io.coolstar.ratelimiter.test.RateLimitAspect)")
    public void serviceLimit() {
 
    }
 
    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception {
        // TODO 获取接口的uri
        Boolean flag = rateLimiter.limit("app-1","/v1/user");
        Object obj = null;
        try {
            if (flag) {
                obj = joinPoint.proceed();
            }else{
                output(response, "failure");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("flag=" + flag + ",obj=" + obj);
        return obj;
    }
    
    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}