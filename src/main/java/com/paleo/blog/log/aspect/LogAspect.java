package com.paleo.blog.log.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.paleo.blog.tools.SPT;

@Component
@Aspect
public class LogAspect {
	
	private static Logger log = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* com.paleo.blog.remote..*(..))")
	public void doBefore(JoinPoint jp) {
		String clazzName = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		log.info(clazzName+SPT.COLON.getSpt()+methodName+" start……");
	}
	
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		return null;
	}

	@AfterReturning("execution(* com.paleo.blog.remote..*(..))")
	public void doAfter(JoinPoint jp) {
		String clazzName = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		log.info(clazzName+SPT.COLON.getSpt()+methodName+" end……");
	}

	@AfterThrowing(value="execution(* com.paleo.blog.remote..*(..))",throwing="ex")
	public void doThrowing(JoinPoint jp, Throwable ex) {
		String clazzName = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		log.info(clazzName+SPT.COLON.getSpt()+methodName+" error……");
		log.info(ex.getMessage());
	}
	
}
