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
        chatCompletionRequestDTO.setInput(
                "你是一个严谨的高级编程架构师，请根据下面的 git diff 做代码评审。\n" +
                        "要求：\n" +
                        "1. 必须使用 Markdown 输出。\n" +
                        "2. 必须严格按照指定格式输出，不要增加额外章节。\n" +
                        "3. 优先指出可能导致 bug、线上风险、安全问题、性能问题、可维护性问题的内容。\n" +
                        "4. 如果某个章节没有发现问题，写“无”。\n" +
                        "5. 建议要具体，尽量指出涉及的类、方法或代码片段。\n\n" +
                        "输出格式：\n" +
                        "## 代码评审报告\n\n" +
                        "### 1. 总体评价\n" +
                        "- 结论：\n" +
                        "- 风险等级：低 / 中 / 高\n\n" +
                        "### 2. 主要问题\n" +
                        "- 问题：\n" +
                        "- 影响：\n" +
                        "- 建议：\n\n" +
                        "### 3. 潜在风险\n" +
                        "- 风险：\n" +
                        "- 建议：\n\n" +
                        "### 4. 可优化点\n" +
                        "- 优化点：\n" +
                        "- 建议：\n\n" +
                        "### 5. 评审结论\n" +
                        "- 是否建议合并：是 / 否\n" +
                        "- 合并前必须处理的问题：\n\n" +
                        "git diff 如下：\n" + diffCode
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
