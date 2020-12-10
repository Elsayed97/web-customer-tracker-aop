package com.saiko.springdemo.aspects;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;





@Aspect
@Component
public class CRMLoggingAspect {
	
	//add logger
    private Logger myLogger = Logger.getLogger(getClass().getName());
	
    // add pointcut expression
    @Pointcut("execution(* com.saiko.springdemo.controller.*.*(..))")
    private void forControllerPackage() {}
    
    // do the same for service and dao
 	@Pointcut("execution(* com.saiko.springdemo.service.*.*(..))")
 	private void forServicePackage() {}
 	
 	@Pointcut("execution(* com.saiko.springdemo.dao.*.*(..))")
 	private void forDaoPackage() {}
 	
 	// do fo all project 
 	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage() ")
 	private void forAppFlow() {}
 	
 	// add beforeAdvice
 	@Before("forAppFlow()")
 	public void before(JoinPoint theJoinPoint) {
 		// display message we are calling
 		 String theMethod = theJoinPoint.getSignature().toShortString();
 		 myLogger.info("=====>> in @Before: calling method: " + theMethod);
 		 
 		// get method arguments
 		 Object [] args = theJoinPoint.getArgs();
 		// loop thru and display args
 		for (Object tempArg : args) {
 			myLogger.info("=====>> argument: " + tempArg);
 		}

 	}
 	
 // add @AfterReturning advice
 	@AfterReturning(
 			pointcut="forAppFlow()",
 			returning="theResult"
 			)
 	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
 	
 		// display method we are returning from
 		String theMethod = theJoinPoint.getSignature().toShortString();
 		myLogger.info("=====>> in @AfterReturning: from method: " + theMethod);
 				
 		// display data returned
 		myLogger.info("=====>> result: " + theResult);
 	
 	}

 	
	

}
