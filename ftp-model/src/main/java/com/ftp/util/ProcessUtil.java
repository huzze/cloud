package com.ftp.util;

/**
 * @program: cloud
 * @description: 进度工具
 * @author: 林开颜
 * @create: 2024/4/7 15:17
 */
public class ProcessUtil {
    /**
     * 控制台打印进度条
     * @param index
     * @param total
     */
    public static void printProcess(int index, int total) {
        double progressPercentage = (double) index / total * 100;
        System.out.printf("\rProgress: %.2f%% [", progressPercentage);

        // 输出进度条
        for (int i = 0; i < Math.round(progressPercentage); i++) {
            System.out.print("|");
        }
        System.out.print("]");
    }
}
