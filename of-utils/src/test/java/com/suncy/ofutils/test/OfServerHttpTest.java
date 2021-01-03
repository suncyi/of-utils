package com.suncy.ofutils.test;

import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.ImmutableMap;
import com.suncy.biz.IUserBiz;
import com.suncy.ofutils.tools.HttpUtils;
import com.suncy.ofutils.tools.OfJsonUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/14  14:21
 */
public class OfServerHttpTest {

    @OfTestAutowired
    private IUserBiz userBiz;

    @Test
    public void httpInvokeTest() throws HttpProcessException {
        String url = "http://localhost:8080/of/test/invoke.do";
        // String clzName, String method, String paramPairJson
        List<TypeValueBean> params = Lists.newArrayList(new TypeValueBean(Long.class.getName(), 123124L),
                new TypeValueBean(String.class.getName(), null));
        String clzName = IUserBiz.class.getName();

        Map<String, Object> httpParams = ImmutableMap
                .of("clzName", clzName, "method", "getUserName", "paramPairJson", OfJsonUtil.toJson(params));

        String result = HttpUtils.post(url, httpParams);
        System.out.println(result);
    }

    @Test
    public void proxyHttpInvodeTest() {
        //        HelloInterface proxyHello = (HelloInterface) Proxy
        //                .newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);
        OfTestProxyHandler handler = new OfTestProxyHandler(IUserBiz.class, 8080);
        IUserBiz userBiz = (IUserBiz) Proxy
                .newProxyInstance(IUserBiz.class.getClassLoader(), new Class[] { IUserBiz.class }, handler);
        String result = userBiz.getUserName(123231L, "asdfssdf");
        System.out.println("ofTest result:" + result);
    }

    @Test
    public void initTest() throws IllegalAccessException {

        Object targetTest = this;
        //        OfTestAutowire field = targetTest.getClass().getAnnotation(OfTestAutowire.class);
        Field[] fields = targetTest.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OfTestAutowired.class)) {
                field.setAccessible(true);
                Class type = field.getType();
                OfTestProxyHandler handler = new OfTestProxyHandler(type, 8080);
                Object proxy = Proxy.newProxyInstance(type.getClassLoader(), new Class[] { type }, handler);
                field.set(targetTest, proxy);
            }
        }
        this.userBiz.getUserName(12312L, "1111");
        System.out.println("啊啊啊");

    }
}
