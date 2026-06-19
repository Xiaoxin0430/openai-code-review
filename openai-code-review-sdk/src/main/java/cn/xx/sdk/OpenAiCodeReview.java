package cn.xx.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xiaoxin
 * @description
 * @create 2026/6/17 16:05
 */


public class OpenAiCodeReview {
    public static void main(String[] args) throws IOException {
        System.out.println("请注意倒车");

        //代码检出
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
        processBuilder.directory(new File("."));

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        int exitCode = process.exitValue();
        System.out.println("Exited with code " + exitCode);

        System.out.println("评审代码" + sb.toString());

    }
}
