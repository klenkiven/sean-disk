package cn.edu.tuyt.sea2.seandisk.common.utils;

import cn.edu.tuyt.sea2.seandisk.common.exception.GeneralException;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Token相关工具
 * @author ：klenkiven
 * @date ：2021/7/12 9:50
 */
public final class TokenUtils {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(HEX_CODE[(b >> 4) & 0xF]);
            r.append(HEX_CODE[(b & 0xF)]);
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
            throw new GeneralException("生成Token失败", e);
        }
    }

}
