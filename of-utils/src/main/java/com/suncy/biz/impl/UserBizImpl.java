package com.suncy.biz.impl;

import com.google.common.collect.Maps;
import com.suncy.biz.IUserBiz;
import com.suncy.biz.dto.UserDto;
import com.suncy.ofutils.tools.OfJsonUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/13  9:40
 */
@Service
public class UserBizImpl implements IUserBiz {
    @Override
    public String getUserName(Long userId) {

        return "admin";
    }

    @Override
    public String getUserName(Long userId, String userType) {
        System.out.println(String.format("UserBizImpl --- getUserName,userId:%s,userType:%s", userId, userType));
        return "admin  " + userType;
    }

    @Override
    public void saveUser(Long userId, String userName) {
        System.out.println(String.format("save user,userId:%s,userName:%s", userId, userName));
    }

    @Override
    public void typeUser(long userId, int sex, char scode, String name, List<Object> ss) {

    }

    @Override
    public UserDto getUser(Long userId, boolean addName) {
        System.out.println(String.format("UserBizImpl getUser userId:%s,addName:%s", userId, addName));
        return UserDto.builder().userId(1232123L).userName("suncy").address("bj").build();
    }

    @Override
    public void addUser(Long userId, UserDto user) {
        System.out.println("进入业务");
        System.out.println(String.format("UserBizImpl addUser userId:%s,user:%s", userId, OfJsonUtil.toJson(user)));
    }

    @Override
    public Map<Long, UserDto> batchFind(List<UserDto> users) {
        Map<Long, UserDto> map = Maps.newHashMapWithExpectedSize(users.size());
        for (UserDto user : users) {
            user.setAddress(user.getAddress() + " 经过Biz");
            map.put(user.getUserId(), user);
        }
        //UserDto user = UserDto.builder().userId(1232123L).userName("suncy").address("bj").build();
        return map;
    }
}
