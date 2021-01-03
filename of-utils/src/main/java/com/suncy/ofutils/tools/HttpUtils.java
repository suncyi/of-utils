package com.suncy.ofutils.tools;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.Maps;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/14  10:05
 */
public class HttpUtils {

    private static HCB hcb = null;

    static {
        try {
            hcb = HCB.custom().pool(100, 10) //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
                    .ssl()        //https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                    .retry(5);    //重试5次
        } catch (HttpProcessException e) {
            throw new RuntimeException("HttpUtils 初始化异常");
        }
    }

    public static String post(String url, Map<String, Object> params) throws HttpProcessException {

        HttpClient hc = hcb.build();
        Header[] headers = HttpHeader.custom().userAgent("javacl").other("customer", "自定义").build();
        // 转一下 map,传入不可变map HttpConfig会报错
        Map<String, Object> map = Maps.newHashMap(params);
        HttpConfig config = HttpConfig.custom().headers(headers)    //设置headers，不需要时则无需设置
                .url(url)              //设置请求的url
                .map(map)              //设置请求参数，没有则无需设置
                .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
                .client(hc);    //如果只是简单使用，无需设置，会自动获取默认的一个client对象
        String result = HttpClientUtil.post(config);
        return result;
    }
}
