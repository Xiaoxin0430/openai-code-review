package cn.xx.sdk.infrastructure.openai;


import cn.xx.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.xx.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

public interface IOpenAI {
    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO)throws Exception;
}
