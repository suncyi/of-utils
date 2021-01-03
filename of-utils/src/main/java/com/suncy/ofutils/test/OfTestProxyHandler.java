package com.suncy.ofutils.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.suncy.ofutils.tools.HttpUtils;
import com.suncy.ofutils.tools.OfJsonUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/14  21:14
 */
public class OfTestProxyHandler implements InvocationHandler {

    private static String baseeUrl = "http://localhost:%s/of/test/invoke.do";

    private String url;

    private Class targetClz;

    private int serverPort;

    public OfTestProxyHandler(Class targetClz, int serverPort) {
        this.targetClz = targetClz;
        this.serverPort = serverPort;
        this.url = String.format(baseeUrl, serverPort);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        if (methodName.contains("toString")) {
            System.out.println("捉住 toString");
        }

        List<TypeValueBean> params = ImmutableList.of();
        // 当前请求方法的参数类型
        Type[] paramTypes = method.getParameterTypes();
        if (null != args) {
            params = Lists.newArrayListWithCapacity(paramTypes.length);
            for (int i = 0; i < paramTypes.length; i++) {
                Type paramType = paramTypes[i];
                Object value = args[i];
                if (paramType instanceof ParameterizedType) {
                    params.add(new TypeValueBean(((ParameterizedType) paramType).getRawType().getTypeName(), value));
                } else {
                    params.add(new TypeValueBean(paramType.getTypeName(), value));
                }

            }
        }
        Map<String, Object> httpParams = ImmutableMap
                .of("clzName", targetClz.getName(), "method", methodName, "paramPairJson", OfJsonUtil.toJson(params));
        // 请求
        Object result = HttpUtils.post(url, httpParams);

        Type retType = method.getGenericReturnType();
        if (retType.equals(void.class)) {
            return result;
        }
        result = OfJsonUtil.fromJson(result.toString(), retType);
        return result;
    }
}
