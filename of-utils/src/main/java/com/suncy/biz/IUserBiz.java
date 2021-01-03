package com.suncy.biz;

import com.suncy.biz.dto.UserDto;

import java.util.List;
import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/13  9:40
 */
public interface IUserBiz {
    String getUserName(Long userId);

    String getUserName(Long userId, String userType);

    void saveUser(Long userId, String userName);

    void typeUser(long userId, int sex, char scode, String name, List<Object> ss);

    UserDto getUser(Long userId, boolean addName);

    void addUser(Long userId, UserDto user);

    Map<Long, UserDto> batchFind(List<UserDto> users);
}
