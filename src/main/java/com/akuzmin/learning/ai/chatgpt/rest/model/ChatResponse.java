package com.akuzmin.learning.ai.chatgpt.rest.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {

    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private Message message;
    }

}
