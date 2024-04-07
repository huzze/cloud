package com.ftp.util;

import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: cloud
 * @description:
 * @author: 林开颜
 * @create: 2024/3/29 10:22
 */
public class FileUtil {
    // 常见的半角到全角转换映射
    private static final String[] halfWidthSymbols = {"\\", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "]", "^", "_", "`", "{", "|", "}", "~"};
    private static final String[] fullWidthSymbols = {"＼", "！", "“", "＃", "￥", "％", "＆", "‘", "（", "）", "＊", "＋", "，", "－", "．", "／", "：", "；", "＜", "＝", "＞", "？", "＠", "［", "］", "＾", "＿", "｀", "｛", "｜", "｝", "～"};

    /**
     * 半角转换全角
     * @param name
     * @return
     */
    public static String symbolsHalfToFull(String name) {
        return StringUtils.replaceEach(name, halfWidthSymbols, fullWidthSymbols);
    }

    /**
     * 文件名文教半角转换
     * @param name
     * @return
     */
    public static String fileNameHalfToFull(String name) {
        String[] half = Arrays.stream(halfWidthSymbols).filter(s -> !StringUtils.equals(s, ".")).toArray(String[]::new);
        String[] full = Arrays.stream(fullWidthSymbols).filter(s -> !StringUtils.equals(s, "．")).toArray(String[]::new);
        return StringUtils.replaceEach(name, half, full);
    }

    /**
     * url对应的文件是否存在
     * @param fileUrl
     * @return
     */
    public static boolean isRemoteFileAvailable(String fileUrl)  {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode < 300;
        } catch (Exception e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
