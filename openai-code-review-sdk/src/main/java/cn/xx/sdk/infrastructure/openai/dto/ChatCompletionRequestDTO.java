package cn.xx.sdk.infrastructure.openai.dto;

import cn.xx.sdk.domain.model.Model;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void setInput(String input) {
        this.input = input;
    }
}
