package com.codeswap.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.codeswap.dto.ChatGptRequest;
import com.codeswap.dto.ChatGptResponse;
import com.codeswap.dto.Message;
import com.codeswap.service.CodeSwapServiceImpl;

@RestController
@RequestMapping("/codeswap")
public class CodeSwapController {

	@Value("${openai.model}")
	private String model;
	
	@Value("${openai.apiUrl}")
	private String apiUrl;
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private CodeSwapServiceImpl css;
	
	@GetMapping("/generate-response")
    public com.codeswap.dto.Message chat(@RequestParam("codeSnippet") String codeSnippet, @RequestParam("language") String language) {
        String prompt = css.generatePromptFromCodeSnippet(codeSnippet,language);
        ChatGptRequest request = new ChatGptRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiUrl, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage();
    }
	
	@GetMapping("/debug-code")
    public Message debugCode(@RequestParam("codeSnippet") String codeSnippet) throws UnsupportedEncodingException {
        String encodedCodeSnippet;
        encodedCodeSnippet = URLEncoder.encode(codeSnippet, StandardCharsets.UTF_8.toString());


        String prompt = css.generatePromptForCodeDebugging(codeSnippet);
        ChatGptRequest request = new ChatGptRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiUrl, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage();
    }
	
	@GetMapping("/code-quality-check")
    public Message codeQualityCheck(@RequestParam("codeSnippet") String codeSnippet) {
        String prompt = css.generatePromptForCodeQualityChecking(codeSnippet);
        ChatGptRequest request = new ChatGptRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiUrl, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage();
    }
	
	
}
