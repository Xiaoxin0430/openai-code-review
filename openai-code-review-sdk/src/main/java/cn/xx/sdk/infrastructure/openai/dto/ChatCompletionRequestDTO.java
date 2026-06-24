package cn.xx.sdk.infrastructure.openai.dto;

import cn.xx.sdk.domain.model.Model;
import java.util.Collections;
import java.util.Map;
public class ChatCompletionRequestDTO {

    private String model = Model.QWEN3_7_PLUS.getCode();

    private Map<String, Object> input;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public void setInput(String content) {
        Map<String, String> message = new java.util.HashMap<>();
        message.put("role", "user");
        message.put("content", content);

        this.input = Collections.singletonMap("messages", Collections.singletonList(message));
    }
}