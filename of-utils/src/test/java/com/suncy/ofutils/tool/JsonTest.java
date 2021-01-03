package com.suncy.ofutils.tool;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/12  16:04
 */
public class JsonTest {

    @Test
    public void toJsonTest() {
        List<Pair<String, String>> pairs = Lists.newArrayList();
        pairs.add(new MutablePair("aa", "bb"));
        pairs.add(new MutablePair("aa", "bb"));
        String jsonStr = JSON.toJSON(pairs).toString();
        System.out.println(jsonStr);

        //        JSON.parse()
    }

    @Test
    public void arrayTypeTest() {
        System.out.println(Collection.class.isArray());
        System.out.println(List.class.isArray());
        System.out.println(Map.class.isArray());
        System.out.println(ImmutableMap.class.isArray());
        System.out.println(String.class.isArray());
    }
}
