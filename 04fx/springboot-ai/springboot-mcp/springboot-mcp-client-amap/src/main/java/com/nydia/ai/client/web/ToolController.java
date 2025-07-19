package com.nydia.ai.client.web;

import com.nydia.ai.client.service.AiService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvhq
 * @date 2024.06.12
 */
@RestController
@RequestMapping("/tools")
public class ToolController {

    private final AiService aiService;

    public ToolController(AiService aiService, ToolCallbackProvider mcpTools) {
        this.aiService = aiService;
    }

    /**
     * 使用阿里云对话服务（AMAP）进行文本对话
     *
     * @param prompt 用户输入的对话内容
     * @return ResponseEntity 包含对话结果的HTTP响应
     */
    @GetMapping("")
    public ResponseEntity<String> amap(String prompt) {
        return aiService.getAiResponse(prompt);
    }

}
