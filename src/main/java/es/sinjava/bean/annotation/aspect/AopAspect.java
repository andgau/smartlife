package es.sinjava.bean.annotation.aspect;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.sinjava.bean.TracedItem;
import es.sinjava.service.TraceService;

@Aspect
@Component
public class AopAspect {

	private TracedItem trace;

	//Volcado sobre la bbdd
	private TraceService traceService;
	
	//Volcado sobre el servicio rest
	
	

	public AopAspect(@Autowired TraceService traceServiceIn) {
		traceService = traceServiceIn;
		trace = new TracedItem();
		try {
			trace.setHost(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// si falla le damos el nombre del thread
			trace.setHost(Thread.currentThread().getName());
		}
	}

	@Around("@annotation(es.sinjava.bean.annotation.SmartAudit)")
	public Object auditing(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();

		Object proceed = joinPoint.proceed();

		long executionTime = (System.nanoTime() - start )/ 1_000_000;

		trace.setId(null);
		trace.setClsName(joinPoint.getSignature().getDeclaringTypeName());
		trace.setMethodName(joinPoint.getSignature().getName().replaceAll(trace.getClsName(), ""));
		trace.setLap(executionTime);
		trace.setCreated(LocalDateTime.now());
		traceService.save(trace);
		return proceed;
	}

}
