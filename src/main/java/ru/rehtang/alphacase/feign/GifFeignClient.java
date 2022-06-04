package ru.rehtang.alphacase.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rehtang.alphacase.dto.GifResponseDto;

@FeignClient(name = "gifFeignClient", url = "${alphaCase.feign.gif.url}")
public interface GifFeignClient {

    @GetMapping
    GifResponseDto receiveGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("limit") int limit,
            @RequestParam("q") String searchPhrase,
            @RequestParam("offset") int startPosition
    );
}
