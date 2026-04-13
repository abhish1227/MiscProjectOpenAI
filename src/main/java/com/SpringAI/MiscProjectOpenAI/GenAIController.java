package com.SpringAI.MiscProjectOpenAI;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GenAIController {

    private final ChatService chatService;
    private final ImageService imageService;

    public GenAIController(ChatService chatService, ImageService imageService){
        this.chatService = chatService;
        this.imageService = imageService;
    }

    @GetMapping("/ask")
    public String getResponse(@RequestParam String prompt){
        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-options")
    public String getResponseOptions(@RequestParam String prompt){
        return chatService.getResponseOptions(prompt);
    }

    @GetMapping("/generate-image")
    public void generateImages(HttpServletResponse response, @RequestParam String prompt) throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        String imageUrl = imageResponse.getResult().getOutput().getUrl();
        response.sendRedirect(imageUrl);
    }

    @GetMapping("/generate-image-options")
    public List<String> generateImageOptions(@RequestParam String prompt){
        ImageResponse imageResponse = imageService.generateImageOptions(prompt);
        return imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
    }

}
