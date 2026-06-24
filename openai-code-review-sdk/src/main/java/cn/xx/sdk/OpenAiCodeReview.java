package cn.xx.sdk;

import cn.xx.sdk.domain.service.impl.OpenAiCodeReviewService;
import cn.xx.sdk.infrastructure.git.GitCommand;
import cn.xx.sdk.infrastructure.openai.IOpenAI;
import cn.xx.sdk.infrastructure.openai.impl.ChatGLM;
import cn.xx.sdk.infrastructure.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenAiCodeReview {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiCodeReview.class);

//    微信配置
    private String weixin_appid = "";
    private String weixin_secret = "";
    private String weixin_touser = "";
    private String weixin_template_id = "";

//    chatGlm配置
    private String chatglm_apiHost = "https://dashscope.aliyuncs.com/compatible-mode/v1/responses";
    private String chatglm_apiKeySecret = "";

//    Github配置
    private String github_review_log_uri;
    private String github_token;

    // 工程配置 - 自动获取
    private String github_project;
    private String github_branch;
    private String github_author;

    public static void main(String[] args) throws Exception {
        GitCommand gitCommand = new GitCommand(
                getEnv("CODE_REVIEW_LOG_URI"),
                getEnv("CODE_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

        WeiXin weiXin = new WeiXin(
                getEnv("WEIXIN_APPID"),
                getEnv("WEIXIN_SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );


        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(gitCommand, openAI, weiXin);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value  || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }



}


