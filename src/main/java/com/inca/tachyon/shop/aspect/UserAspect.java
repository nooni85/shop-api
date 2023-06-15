package com.inca.tachyon.shop.aspect;

import com.inca.tachyon.shop.app.user.entity.User;
import com.inca.tachyon.shop.app.user.service.UserService;
import com.inca.tachyon.shop.auth.jwt.JwtTokenizer;
import com.inca.tachyon.shop.exception.ExceptionCode;
import com.inca.tachyon.shop.exception.TachyonShopException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class UserAspect {
    private final UserService userService;

    private final JwtTokenizer jwtTokenizer;

    @Around("execution(* *(.., @LoginUser (*), ..))")
    public Object grantUser(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();

        String token = Optional.ofNullable(request.getHeader("Authorization")).orElseThrow(() -> new TachyonShopException(ExceptionCode.BAD_TOKEN));
        User user = jwtTokenizer.toUser(token);

        Object[] args = pjp.getArgs();

        // get all annotations for arguments
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[][] annotations;


        annotations = pjp.getTarget().getClass().
                    getMethod(methodName, parameterTypes).getParameterAnnotations();

        //Find annotated argument
        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation.annotationType() == LoginUser.class) {
                    Object raw = args[i];
                    if (raw instanceof User) {
                        // and replace it with a new value
                        args[i] = user;
                    }
                }
            }
        }
        //execute original method with new args
        return pjp.proceed(args);
    }
}
