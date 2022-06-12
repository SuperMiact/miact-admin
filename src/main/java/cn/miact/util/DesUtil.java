package cn.miact.util;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author : mawei
 * @Classname : DesUtil
 * @createDate : 2022-06-10 15:54:50
 * @Description : DES加密
 */
public class DesUtil {
    // 定义key，如果使用des加密，密钥必须是8个字节
    private static String key = "12345678";
    // 算法
    private static String transformation = "DES";
    // 加密类型
    private static String algorithm = "DES";

    public static String createDesEncrypt(String input) {
        try {
            return encryptDes(input, key, transformation, algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String createDesDecrypt(String encode)  {
        try {
            return decryptDes(encode,key,transformation,algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DES解密数据
     */
    private static String decryptDes(String encode, String key, String transformation, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] bytes = cipher.doFinal(Base64.decode(encode));
        return new String(bytes);
    }

    /**
     * DES加密数据
     */
    private static String encryptDes(String input, String key, String transformation, String algorithm) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数：表示key的字节
        // 第二个参数：表示加密的类型
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        // 进行加密初始化
        // 第一个参数表示模式，加密模式，解密模式
        // 第二个参数表示：加密的规则
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        // 调用加密方法
        // 参数表示原文的字节数组
        byte[] bytes = cipher.doFinal(input.getBytes());

        //  创建Base64对象
        String encode = Base64.encode(bytes);
        return encode;
    }
}
