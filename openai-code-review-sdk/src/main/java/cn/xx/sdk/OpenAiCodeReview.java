package cn.xx.sdk;



import cn.xx.sdk.domain.model.ChatCompletionRequest;
import cn.xx.sdk.domain.model.ChatCompletionSyncResponse;
import cn.xx.sdk.domain.model.Model;
import com.alibaba.fastjson2.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/**
 * @author xiaoxin
 * @description
 * @create 2026/6/17 16:05
 */


public class OpenAiCodeReview {
    public static void main(String[] args) throws Exception {
        System.out.println("请注意倒车");

//        1.代码检出
        ProcessBuilder processBuilder = new ProcessBuilder("git","diff","HEAD~1","HEAD");
        processBuilder.directory(new File("."));

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        StringBuilder diffCode = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            diffCode.append(line);
        }
        int exitCode = process.waitFor();
        System.out.println("Exited with code:" + exitCode);
        System.out.println("diff code：" + diffCode.toString());

        // 2. chatglm 代码评审
        String log = codeReview(diffCode.toString());
        System.out.println("code review：" + log);

    }
    private static String codeReview(String diffCode) throws Exception {

        String apiKeySecret = "sk-121f3d46be254580bcedc3d692f8ae5a";

        URL url = new URL("https://dashscope.aliyuncs.com/compatible-mode/v1/responses");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKeySecret);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(Model.QWEN3_7_PLUS.getCode());
        chatCompletionRequest.setInput(
                "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言，请根据 git diff 记录，对代码做出评审。代码如下：\n" + diffCode
        );
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(chatCompletionRequest).getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        System.out.println("评审结果：" + content.toString());

        ChatCompletionSyncResponse response = JSON.parseObject(content.toString(), ChatCompletionSyncResponse.class);
        String result = response.getOutputText();

        return result;


    }
}


