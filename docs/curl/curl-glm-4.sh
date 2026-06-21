curl -X POST https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions \
-H "Authorization: Bearer sk-121f3d46be254580bcedc3d692f8ae5a" \
-H "Content-Type: application/json" \
-d '{"model":"qwen3.7-plus","messages":[{"role":"system","content":"You are a helpful assistant."},{"role":"user","content":"你是谁？"}]}'

  curl -X POST https://dashscope.aliyuncs.com/compatible-mode/v1/responses \
-H "Authorization: Bearer $DASHSCOPE_API_KEY" \
-H "Content-Type: application/json" \
-d '{
    "model": "qwen3.7-plus",
    "input": "你能做些什么？"
}'