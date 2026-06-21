package cn.xx.sdk.domain.model;


import java.util.List;

import java.util.List;

public class ChatCompletionSyncResponse {

    private List<Output> output;

    public List<Output> getOutput() {
        return output;
    }

    public void setOutput(List<Output> output) {
        this.output = output;
    }

    /**
     * 直接获取千问最终返回文本
     */
    public String getOutputText() {
        if (output == null || output.isEmpty()) {
            return null;
        }

        for (Output outputItem : output) {
            if (!"message".equals(outputItem.getType())) {
                continue;
            }

            List<Content> contentList = outputItem.getContent();
            if (contentList == null || contentList.isEmpty()) {
                continue;
            }

            StringBuilder result = new StringBuilder();

            for (Content contentItem : contentList) {
                if ("output_text".equals(contentItem.getType())) {
                    result.append(contentItem.getText());
                }
            }

            if (result.length() > 0) {
                return result.toString();
            }
        }

        return null;
    }

    public static class Output {
        private String id;
        private String type;
        private String role;
        private String status;
        private List<Content> content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }
    }

    public static class Content {
        private String type;
        private String text;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}