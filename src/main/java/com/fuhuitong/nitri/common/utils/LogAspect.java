package com.fuhuitong.nitri.common.utils;

/**
 * @Author Wang
 * @Date 2019/4/27 0027 12:54
 **/


import com.fuhuitong.nitri.sys.entity.SysLog;
import com.fuhuitong.nitri.sys.mapper.SysLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

   /* @Autowired
    private SysOperationLogImpl sysOperationLogService;
*/

   @Autowired
   private SysLogMapper  sysLogMapper;

   private String res="";

    // 配置织入点
    @Pointcut("@annotation(com.fuhuitong.nitri.common.utils.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void dobefore() {
        log.info("====>切面之前会执行");
    }

    /**
     * 在代码执行之后，不论代码成功执行还是抛出异常都会执行到这里
     */
    @After("logPointCut()")
    public void doAfter() {
        log.info("====>切面执行之后");
    }

    @AfterReturning(value = "logPointCut()", returning = "result")
    public void AfterReturning(Object result) {
        if (result != null)
            log.info("====>执行结果返回值：" + result.toString());
            res=result.toString();
    }


    /**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }


    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }


            // 获取request

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log aopLog = method.getAnnotation(Log.class);

            SysLog sysLog=new SysLog();

            if (e != null) {
                // sysOperationLog.setExceptionDetail(e.getMessage().toString());
            }
            if (log != null) {
               sysLog.setTitle(aopLog.value());
            }


            String currentip = IpUtil.getIpAddr();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            log.info("--------------------------"+res);

            sysLog.setRequestIp(currentip);
            sysLog.setMethod(methodName);
            sysLog.setRequestParams(getParameterMap(RequestHolder.getHttpServletRequest()).toString());
            sysLog.setResponseParams(res);
            sysLog.setUserId(ContextHolder.getCurrentUser().getUsername());
            sysLogMapper.insert(sysLog);
          /*  sysOperationLog.setMethod(className + "." + methodName + "()");
            sysOperationLog.setUserId(ContextHolder.getCurrentUser().getUsername());
            sysOperationLog.setParams(getParameterMap(RequestHolder.getHttpServletRequest()).toString());
            sysOperationLog.setRequestIp(currentip);
            sysOperationLogService.addLog(sysOperationLog);//*/

        } catch (Exception exp) {
            //throw new AsException(ResultJson.failure(ResultCode.UNAUTHORIZED));
            // 记录本地异常日志
            log.error("==LogAspect异常==");
            // log.error("异常信息:{}", exp.getMessage());
            //   exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 切点
     * @return 方法描述
     * @throws Exception
    /*     */
/* public void getControllerMethodDescription(Log log) throws Exception
    {
        Log.
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
         //   setRequestParam(operLog);
        }
    }*/

    /**
     * 获取请求的参数，放到SysOperLog中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */

/*
    private void setRequestParam(SysOperLog operLog) throws Exception
    {
        Map<String, String[]> map = RequestHolder.getHttpServletRequest().getParameterMap();

        int len=params.length();
        operLog.setOperParam(params);
    }
*/


    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }


    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }


}

