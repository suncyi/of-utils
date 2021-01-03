package com.suncy.biz.impl;

import org.springframework.stereotype.Service;

/**
 * @auther suncy
 * @Date 2021/1/3  17:27
 */

@Service
public class MmPrivateCompent {

    private void priSay(String name) {
        System.out.println("MmPrivateCompent.priSay:" + name);
    }

    protected void proSay(String name) {
        System.out.println("MmPrivateCompent.proSay:" + name);
    }

    public void pubSay(String name) {
        System.out.println("MmPrivateCompent.pubSay:" + name);
    }

    void defSay(String name) {
        System.out.println("MmPrivateCompent.defSay:" + name);
    }

}
