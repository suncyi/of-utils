package com.suncy.ofutils.test;

import com.alibaba.fastjson.JSON;
import com.suncy.biz.IUserBiz;
import com.suncy.ofutils.tools.OfJsonUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @auther suncy
 * @Date 2020/11/13  9:38
 */
@SpringBootTest
public class OfServerTest {

    @Autowired
    private OfTestController ofTestServer;

    @Test
    public void ofInvokeTest() throws Exception {
        Class targetClz = IUserBiz.class;
        String methodName = "getUserName";
        //Long userId, String userType
        TypeValueBean bean = new TypeValueBean(Long.class.getName(), 123124L);
        List<TypeValueBean> params = Lists.newArrayList(new TypeValueBean(Long.class.getName(), 123124L),
                new TypeValueBean(String.class.getName(), null));
        System.out.println(JSON.toJSON(params));
        Object obj = ofTestServer.invokeTarget(targetClz, methodName, params);
        System.out.println(obj);
    }

    @Test
    public void invokeJsonTest() throws Exception {
        Class targetClz = IUserBiz.class;
        String methodName = "getUserName";
        //Long userId, String userType
        TypeValueBean bean = new TypeValueBean(Long.class.getName(), 123124L);
        List<TypeValueBean> params = Lists.newArrayList(new TypeValueBean(Long.class.getName(), 123124L),
                new TypeValueBean(String.class.getName(), null));
        System.out.println(JSON.toJSON(params));
        //        Object obj = ofTestServer.invokeTarget(targetClz, methodName, params);
        Object obj = ofTestServer.invoke(targetClz.getName(), methodName, OfJsonUtil.toJson(params));
        System.out.println(obj);
    }

}
