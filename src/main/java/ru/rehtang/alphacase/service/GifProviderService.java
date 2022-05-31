package ru.rehtang.alphacase.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rehtang.alphacase.dto.GifResponseDto;
import ru.rehtang.alphacase.feign.GifFeignClient;

@Service
@RequiredArgsConstructor
public class GifProviderService {
    private final GifFeignClient client;

    @Value("${alphaCase.feign.gif.apiKey}")
    private String gifApiKey;

    @Value("${alphaCase.feign.gif.searchPhrase.rich}")
    private String richPhrase;

    @Value("${alphaCase.feign.gif.searchPhrase.sad}")
    private String sadPhrase;

    @Value("${alphaCase.feign.gif.limit}")
    private int limit;

    public GifResponseDto getRichGif() {
        return client.receiveGif(gifApiKey, limit, richPhrase);
    }

    public GifResponseDto getSadGif() {
        return client.receiveGif(gifApiKey, limit, sadPhrase);
    }
}
