package com.suncy.ofutils.tiny;

/**
 * @auther suncy
 * @Date 2020/11/17  14:15
 */
public class OoTest {

    public void sum(Node a, Node b) {

        Node ra = re(a);
        Node rb = re(b);
        Node res = new Node();
        int sum10 = 0;
        while (ra.next != null || rb.next != null) {
            if (ra.next != null && rb.next == null) {
                //b 结束
                res.next = ra;
                ra = ra.next;
            } else if (ra.next == null && rb.next != null) {
                res.next = rb;
                rb = rb.next;
            } else {
                int csum = ra.val + rb.val + sum10;
                sum10 = csum / 10; // 进位
                res.val = csum % 10;
                res.next = new Node();
                ra = ra.next;
                rb = rb.next;
            }
        }
        // 输出

    }

    // 翻转
    public Node re(Node a) {

        Node temp = a;
        a = a.next;
        Node r = temp;
        r.next = null;
        while (a.next != null) {
            temp = a;
            a = a.next;
            temp.next = r;
            r = temp;
        }
        return r;
    }

    public static class Node {
        int val;
        Node next;
    }
}
