package com.hat.shiro.utils;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 测试md5和盐值加密密码
 */
public class md5 {

    public static final String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 2;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }

    public static void main(String[] args) {
        String password = md5("123456", "salt");
        System.out.println(password);
        //加密后的结果
        //00b3187384f2708025074f28764a4a30
    }
}