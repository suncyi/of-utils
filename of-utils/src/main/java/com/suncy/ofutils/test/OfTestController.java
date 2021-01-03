package com.suncy.ofutils.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.suncy.ofutils.tools.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @auther suncy
 * @Date 2020/11/11  10:48
 */
@Controller("ofTestServer")
@RequestMapping("/of/test")
public class OfTestController {

    @Autowired
    public ApplicationContext context;

    //    public void invokeTarget(Class clz, String method, Object... params) throws Exception {
    //        Object bean = context.getBean(clz);
    //        System.out.println("bean.hashCode:" + bean);
    //        List<Class> paramTypes = Lists.newArrayListWithCapacity(params.length);
    //        for (Object obj : params) {
    //            paramTypes.add(obj.getClass());
    //        }
    //        System.out.println("paramTypes : " + JSON.toJSON(paramTypes));
    //        Method targetMethod = clz.getMethod(method, paramTypes.toArray(new Class[paramTypes.size()]));
    //        //        System.out.println(targetMethod.toString());
    //
    //        Object obj = targetMethod.invoke(bean, params);
    //    }

    public Object invokeTarget(Class clz, String method, List<TypeValueBean> params) throws Exception {
        Object targetObj = context.getBean(clz);

        List<Class> paramTypes = params.stream().map(p -> ReflectUtil.name2Class(p.getType()))
                .collect(Collectors.toList());
        Class[] paramTypeArr = paramTypes.toArray(new Class[paramTypes.size()]);

        //        List<Object> paramValues = params.stream().map(pair -> pair.toTypeValue()).collect(Collectors.toList());
        //        Object[] paramValueArr = paramValues.toArray(new Object[paramValues.size()]);

        Method targetMethod = clz.getDeclaredMethod(method, paramTypeArr);

        List<Object> paramValues = Lists.newArrayListWithCapacity(params.size());
        // 取实际的参数类型，根据实际的参数类型解析输入参数
        Type[] types = targetMethod.getGenericParameterTypes();
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            TypeValueBean typeValueBean = params.get(i);
            Object obj = typeValueBean.toTypeValue(type);
            paramValues.add(obj);
        }
        boolean isAccessible = targetMethod.isAccessible();
        if (!isAccessible) {
            targetMethod.setAccessible(true);
        }
        Object result = targetMethod.invoke(targetObj, paramValues.toArray(new Object[paramValues.size()]));
        if (!isAccessible) {
            targetMethod.setAccessible(false);
        }
        return result;
    }

    @RequestMapping("/invoke.do")
    @ResponseBody
    public Object invoke(String clzName, String method, String paramPairJson) throws Exception {
        if (Objects.equals(method, "toString")) {
            System.out.println("执行了 toString");
        }
        Class targertClz = Class.forName(clzName);
        List<TypeValueBean> params = JSON.parseArray(paramPairJson, TypeValueBean.class);
        Object result = this.invokeTarget(targertClz, method, params);
        return result;
    }

}
