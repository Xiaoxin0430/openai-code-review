package cn.xx.sdk.domain.service.impl;

import cn.xx.sdk.domain.AbstractOpenAiCodeReviewService;
import cn.xx.sdk.domain.model.Model;
import cn.xx.sdk.infrastructure.git.GitCommand;
import cn.xx.sdk.infrastructure.openai.IOpenAI;
import cn.xx.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.xx.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import cn.xx.sdk.infrastructure.weixin.WeiXin;
import cn.xx.sdk.infrastructure.weixin.dto.TemplateMessageDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxin
 * @description
 * @create 2026/6/24 14:38
 */


public class OpenAiCodeReviewService extends AbstractOpenAiCodeReviewService {
    public OpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, WeiXin weiXin) {
        super(gitCommand, openAI, weiXin);
    }

    @Override
    protected String getDiffCode() throws IOException, InterruptedException{
        return gitCommand.diff();
    }

    @Override
    protected String codeReview(String diffCode) throws Exception {
        ChatCompletionRequestDTO chatCompletionRequestDTO = new ChatCompletionRequestDTO();
        chatCompletionRequestDTO.setModel(Model.QWEN3_7_PLUS.getCode());
        chatCompletionRequestDTO.setMessages(
                "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言，请根据 git diff 记录，对代码做出评审。代码如下：\n" + diffCode
        );

        ChatCompletionSyncResponseDTO completions = openAI.completions(chatCompletionRequestDTO);
        return completions.getOutputText();

    }

    @Override
    protected String recordCodeReview(String recommend) throws Exception {
        return gitCommand.commitAndPush(recommend);
    }

    @Override
    protected void pushMessage(String logUrl) throws Exception {
        Map<String, Map<String, String>> data = new HashMap<>();
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.REPO_NAME, gitCommand.getProject());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.BRANCH_NAME, gitCommand.getBranch());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_AUTHOR, gitCommand.getAuthor());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_MESSAGE, gitCommand.getMessage());
    }

    ;
}
