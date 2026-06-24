package cn.xx.sdk.infrastructure.openai.dto;

import cn.xx.sdk.domain.model.Model;
public class ChatCompletionRequestDTO {

    private String model = Model.QWEN3_7_PLUS.getCode();

    private String input;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String content) {
        this.input = content;
    }
}
