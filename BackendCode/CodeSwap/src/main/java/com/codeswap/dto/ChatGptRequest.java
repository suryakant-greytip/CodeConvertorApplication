package com.codeswap.dto;

import java.util.ArrayList;
import java.util.List;


public class ChatGptRequest {

	private String model;
    private List<com.codeswap.dto.Message> messages;

    public ChatGptRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user",prompt));
    }
}
