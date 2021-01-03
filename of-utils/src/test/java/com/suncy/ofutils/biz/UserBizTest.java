package com.suncy.ofutils.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.suncy.biz.IUserBiz;
import com.suncy.biz.dto.UserDto;
import com.suncy.ofutils.test.OfTestAutowired;
import com.suncy.ofutils.test.OfTestInitializer;
import com.suncy.ofutils.tools.OfJsonUtil;
import com.suncy.ofutils.tools.ReflectUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/15  0:17
 */
public class UserBizTest {

    @OfTestAutowired
    private IUserBiz userBiz;

    public void UserBizTest() throws IllegalAccessException {
        OfTestInitializer.initTest(this, 8080);
        System.out.println("init sucess ");
    }

    @Test
    public void annTest() {

    }

    @Test
    public void getUserNameTest() {
        System.out.println("begin getUserNameTest");
        String obj = this.userBiz.getUserName(123123L, "wang san");
        System.out.println("test result ---- " + obj);
    }

    @Test
    public void getUserTest() {
        Object result = this.userBiz.getUser(1111L, false);
        System.out.println("::::getUserTest ---" + result);
    }

    @Test
    public void saveUserTest() {
        this.userBiz.saveUser(22222L, "jjjjj");
        System.out.println("===== saveUserTest ------ save user success ");
    }

    @Test
    public void addUserTest() {
        UserDto user = new UserDto();
        user.setUserName("suncy");
        user.setAddress("BeiJing");
        this.userBiz.addUser(12321L, user);
    }

    @Test
    public void batchFindTest() {
        UserDto user = UserDto.builder().userId(1232123L).userName("suncy t").address("bj").build();
        List<UserDto> users = Lists.newArrayList(user);
        Map<Long, UserDto> fmap = this.userBiz.batchFind(users);
        for (UserDto u : fmap.values()) {
            System.out.println(u.getUserName() + "  " + u.getAddress());
        }
        System.out.println(fmap);
    }

    @Test
    public void typeTest() throws NoSuchMethodException {
        UserDto user = UserDto.builder().userId(1232123L).userName("suncy t").address("bj").build();
        List<UserDto> users = Lists.newArrayList(user);

        Class clz = IUserBiz.class;
        Method method = clz.getMethod("batchFind", new Class[] { List.class });

        Type[] paramTypes = method.getGenericParameterTypes();
        for (Type type : paramTypes) {

            System.out.println("type to json : " + OfJsonUtil.toJson(type));
            System.out.println(type.getTypeName());
            if (type instanceof ParameterizedType) {
                System.out.println("getRawType:" + ((ParameterizedType) type).getRawType());
            } else {
                System.out.println(type);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Object obj = objectMapper.convertValue(OfJsonUtil.toJson(users), clz);
        }

        Class typeClz = ReflectUtil.name2Class("java.util.List<com.suncy.biz.dto.UserDto>");
        ObjectMapper objectMapper = new ObjectMapper();
        Object obj = objectMapper.convertValue(OfJsonUtil.toJson(users), clz);
        List<UserDto> l = (List<UserDto>) obj;
        System.out.println(l.get(0).getUserId());

    }
}
