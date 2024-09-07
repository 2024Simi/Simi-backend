package com.project.simi.hanlder;

import com.project.simi.common.exception.BusinessException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.MDC;
import org.springframework.core.NestedRuntimeException;


public class ExceptionMDCBuilder {
    public static void getStringStringMap(Exception ex) {
        MDC.put("stack_trace", stackTraceStringBuilder(ex.getMessage(), ex.getStackTrace(), 10));
        MDC.put("error_originated", ex.getClass().getSimpleName());

        if (ex instanceof BusinessException be) {
            MDC.put("server_message", be.getResultCode().getCode() + " : " + be.getMessage());
        } else {
            MDC.put("server_message", ex.getMessage());
        }

        if (ex.getCause() != null) {
            MDC.put("cause", ex.getCause().toString());
        }

        if (ex instanceof NestedRuntimeException nre && nre.getRootCause() != null) {
            MDC.put("root_cause", nre.getRootCause().toString());
        }

        Class<?> clazz = ex.getClass();
        try {
            Method method = clazz.getDeclaredMethod("getReason");
            if (method.invoke(ex) != null) {
                MDC.put("reason", method.invoke(ex).toString());
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {

        }
    }

    private static String stackTraceStringBuilder(
        String rootMessage, StackTraceElement[] stackTrace, int maxDepth) {
        if (stackTrace == null) {
            return rootMessage;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(rootMessage).append("\n");
        maxDepth = Math.min(maxDepth, stackTrace.length);
        for (int i = 0; i < maxDepth; i++) {
            sb.append(stackTrace[i].toString()).append("\n\n");
        }

        return sb.toString();
    }
}
