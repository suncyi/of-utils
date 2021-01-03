package com.suncy.ofutils.biz;

import com.suncy.biz.impl.MmPrivateCompent;
import com.suncy.ofutils.biz.ofinterface.IMmPrivateCompent;
import com.suncy.ofutils.test.OfTestAutowired;
import com.suncy.ofutils.test.OfTestInitializer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @auther suncy
 * @Date 2021/1/3  17:28
 */
public class MmPrivateCompentTest {

    @OfTestAutowired(targetClzz = MmPrivateCompent.class)
    private IMmPrivateCompent mmPrivateCompent;

    public MmPrivateCompentTest() throws IllegalAccessException {
        OfTestInitializer.initTest(this, 8080);
        System.out.println("init sucess ");
    }

    @Test
    public void invokeTest() {
        mmPrivateCompent.priSay("suncy");
        mmPrivateCompent.proSay("suncy ");
    }

    @Test
    public void getMethTest() throws NoSuchMethodException {
        Class clz = MmPrivateCompent.class;
        Method meth = clz.getDeclaredMethod("defSay", new Class[] { String.class });
        System.out.println(meth);

    }

}
