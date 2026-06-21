package cn.xx.sdk.domain.model;

    
public enum Model {

    QWEN3_7_MAX("qwen3.7-max", "千问 Qwen3.7 旗舰模型，适用于复杂任务、代码生成、Agent 自动化等高要求场景"),

    QWEN3_7_PLUS("qwen3.7-plus", "千问 Qwen3.7 Plus 模型，兼顾效果与成本，适用于代码评审、通用问答、多模态理解等场景"),

    QWEN3_6_PLUS("qwen3.6-plus", "千问 Qwen3.6 Plus 模型，适用于通用文本生成、代码分析、较高质量内容生产等场景"),

    QWEN3_6_FLASH("qwen3.6-flash", "千问 Qwen3.6 Flash 模型，适用于对响应速度和调用成本敏感的简单任务"),

    QWEN3_5_PLUS("qwen3.5-plus", "千问 Qwen3.5 Plus 模型，适用于高吞吐、生产工作流、编码智能体等场景"),

    QWEN3_5_FLASH("qwen3.5-flash", "千问 Qwen3.5 Flash 模型，适用于低成本、快速响应的通用任务"),
    ;

    private final String code;
    private final String info;

    Model(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }
    public String getInfo() {
        return info;
    }
}
