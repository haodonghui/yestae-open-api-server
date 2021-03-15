package com.yestae.common.utils;

import com.google.common.base.Joiner;

import java.util.TreeMap;

/**
 * @author wangpeng
 * @title: SignUtils
 * @packageName: com.yestae.common.utils
 * @description: 参数加密
 * @date 2020-02-25 9:56
 */
public class SignUtils {
    public static void main(String[] args) {
        TreeMap<String, Object> treeMap = new TreeMap<>();

        String appid = "1188716880427405314";
        String mchid = "1188716584989020161";
        Long timestamp = 1584165119000L;
        Integer version = 1;
        String bizcontent = "{\"uid\":\"1135454191756427266\",\"accessToken\":\"b43543589c731f8292d1cb865c0b8e8d\"}";

        treeMap.put("appid", appid);
        treeMap.put("mchid", mchid );
        treeMap.put("timestamp", timestamp);
        treeMap.put("version", version);
        treeMap.put("bizcontent", bizcontent);
        String appsecret = "5312363C738A6B42015F9E7A0D6E247D";

        String join = Joiner.on("&").withKeyValueSeparator("=").join(treeMap);
        String md5 = Md5.md5(join + appsecret );
        System.err.println(md5);
    }
}
