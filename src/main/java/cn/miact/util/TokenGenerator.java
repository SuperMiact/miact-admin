package cn.miact.util;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author : mawei
 * @Classname : TokenGenerator
 * @createDate : 2022-06-13 17:34:48
 * @Description :
 */
public class TokenGenerator {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new ApiException("生成Token失败", e);
        }
    }
}

