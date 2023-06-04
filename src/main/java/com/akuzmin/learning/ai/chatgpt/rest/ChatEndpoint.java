package com.akuzmin.learning.ai.chatgpt.rest;

import com.akuzmin.learning.ai.chatgpt.rest.model.ChatRequest;
import com.akuzmin.learning.ai.chatgpt.rest.model.ChatResponse;
import com.akuzmin.learning.ai.chatgpt.rest.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ChatEndpoint {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    /**
     * Creates a chat request and sends it to the OpenAI API
     * Returns the first message from the API response
     *
     * @param prompt the prompt to send to the API
     * @return first message from the API response
     */
    @GetMapping(value = "/chat", produces = APPLICATION_JSON_VALUE)
    public Message chat(@RequestParam String prompt) {
        ChatRequest request = new ChatRequest(model, prompt);

        ChatResponse response = restTemplate.postForObject(
                apiUrl,
                request,
                ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return new Message("", "No response");
        }

        return response.getChoices().get(0).getMessage();
    }

}