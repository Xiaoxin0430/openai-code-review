package cn.xx.sdk.infrastructure.openai.dto;

import cn.xx.sdk.domain.model.Model;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ChatCompletionRequestDTO {

    private String model = Model.QWEN3_7_PLUS.getCode();

    private List<Map<String, String>> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(String content) {
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", content);
        this.messages = Collections.singletonList(message);
    }
}
