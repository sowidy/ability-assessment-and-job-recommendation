package com.jobRecomment.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ClassName: CallPythonScript
 * Package: com.jobRecomment.utils
 * Description:
 * Author by: HUANG
 * Created by: 2024/4/12 13:10
 * Version 1.0
 */
public class CallPythonScript {
    public static void call(String argument) {
        try {
            //当前工作目录
            String currentWorkingDirectory = System.getProperty("user.dir");
            ProcessBuilder pb = new ProcessBuilder("python", currentWorkingDirectory + "\\python\\main.py",argument);
            // 启动进程
            Process process = pb.start();
            // 读取Python脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // 等待Python脚本执行完毕
            int exitCode = process.waitFor();
            if(exitCode != 0) throw new IOException();
            System.out.println("Python脚本执行完毕，退出码：" + exitCode);
        } catch (IOException | InterruptedException e) {
            System.out.println("Python脚本执行出错");
            e.printStackTrace();
        }
    }
}
