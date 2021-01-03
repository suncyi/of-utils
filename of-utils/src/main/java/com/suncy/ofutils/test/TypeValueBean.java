package com.suncy.ofutils.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suncy.ofutils.tools.OfJsonUtil;
import com.suncy.ofutils.tools.ReflectUtil;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @auther suncy
 * @Date 2020/11/12  19:16
 */
@Getter
@Setter
public class TypeValueBean {
    private String type;
    private Object value;

    public TypeValueBean() {
    }

    public TypeValueBean(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Object toTypeValue() {
        if (null == value) {
            return value;
        }
        Class clz = null;
        try {
            clz = ReflectUtil.name2Class(type);
            ObjectMapper objectMapper = new ObjectMapper();
            Object obj = objectMapper.convertValue(this.value, clz);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("toTypeValue 报错，", e);
        }
    }

    public Object toTypeValue(Type type) {
        if (type instanceof ParameterizedType) {
            //            return JSON.parseObject(JsonUtil.toJson(this.value), type);
            //            return JsonUtil.parseJson(JsonUtil.toJson(this.value), type);
            return OfJsonUtil.fromJson(OfJsonUtil.toJson(this.value), type);
        } else {
            return this.toTypeValue();
        }
    }

    //        public static abstract class OfTypeRef<T> extends TypeReference {
    //            private Type ofType;
    //
    //            public OfTypeRef(Type type) {
    //                this.ofType = type;
    //            }
    //
    //            @Override
    //            public Type getType() {
    //                return this.ofType;
    //            }
    //        }
}
