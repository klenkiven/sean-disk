package cn.edu.tuyt.sea2.seandisk.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件工具
 * @author KlenKiven
 */
public class FileUtils {

    /**
     * 计算文件的SHA-1
     * @param inputStream 文件输入流
     * @return SHA-1值
     */
    public static String fileSHA1Compute(InputStream inputStream) {
        try(inputStream) {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[1024*1024*10];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            StringBuilder sha1 = new StringBuilder(new BigInteger(1, digest.digest()).toString(16));
            int length = 40 - sha1.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    sha1.insert(0, "0");
                }
            }
            return sha1.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将输入流输出到输出流
     *
     */
    public static void inputToOutputStream(InputStream inputStream, OutputStream outputStream) {
        try (inputStream; outputStream) {
            byte[] buffer = new byte[1024*1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
