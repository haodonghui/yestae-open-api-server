package com.yestae.common.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author wangpeng
 * @title: AESUtil
 * @packageName: com.yestae.common.utils
 * @description: AES加密
 * @date 2020-02-21 19:14
 */
@Slf4j
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */

    public static String encrypt2Base64(String content, String password) {

        try {

            AES aes = AES(password);
            return aes.encryptBase64(content);

        } catch (Exception ex) {

            LogUtil.error(log, "encrypt.error", ex);

        }

        return null;

    }


    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */

    public static String decryptByBase64(String content, String password) {

        String s = "";
        try {

            AES aes = AES(password);
            s = aes.decryptStrFromBase64(content, Charset.defaultCharset());

        } catch (Exception ex) {

            LogUtil.error(log, "decrypt.error", ex);

        }

        return s;

    }

    private static AES AES(String key) {
        return new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
    }



    public static void main(String[] args) {
        String s = "110102198610252310";

        String key = "1188716880427405";

        System.out.println("s:" + s);
        String s1 = AESUtil.encrypt2Base64(s, key);
        System.out.println("s1:" + s1);
        System.out.println("s2:" + AESUtil.decryptByBase64(s1, key));

        String a = "1188716880427405314";
        System.err.println(a.substring(0,16 ));

    }
}
