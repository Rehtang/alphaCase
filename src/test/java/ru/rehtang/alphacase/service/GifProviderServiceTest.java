package ru.rehtang.alphacase.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import ru.rehtang.alphacase.dto.DataDto;
import ru.rehtang.alphacase.dto.GifResponseDto;
import ru.rehtang.alphacase.feign.GifFeignClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GifProviderServiceTest {
    @MockBean
    private GifFeignClient client;

    @Test
    void getRichGif() {
        GifResponseDto richy = new GifResponseDto(List.of(new DataDto("Richy")));

        when(client.receiveGif(anyString(), anyInt(), anyString(), anyInt())).thenReturn(richy);

        var receivedGif = client.receiveGif(anyString(), anyInt(), anyString(), anyInt());

        Assert.notNull(receivedGif, "it is null!");
    }

    @Test
    void getSadGif() {
        GifResponseDto sad = new GifResponseDto(List.of(new DataDto("Sad")));

        when(client.receiveGif(anyString(), anyInt(), anyString(), anyInt())).thenReturn(sad);

        var receivedGif = client.receiveGif(anyString(), anyInt(), anyString(), anyInt());

        Assert.notNull(receivedGif, "it is null!");
    }
}