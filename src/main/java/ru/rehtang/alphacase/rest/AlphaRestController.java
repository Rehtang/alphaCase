package ru.rehtang.alphacase.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rehtang.alphacase.dto.CurrencyResponseDto;
import ru.rehtang.alphacase.dto.DataDto;
import ru.rehtang.alphacase.dto.GifResponseDto;
import ru.rehtang.alphacase.service.CurrencyProviderService;
import ru.rehtang.alphacase.service.GifProviderService;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
public class AlphaRestController {

    private final CurrencyProviderService currencyService;
    private final GifProviderService gifProviderService;

    @Value("${alphaCase.feign.gif.limit}")
    private Integer limit;

    @GetMapping("/currency")
    public CurrencyResponseDto receiveCurrency() {
        return currencyService.getCurrency();
    }

    @GetMapping("/result")
    public DataDto returnResult() {

        int random = ThreadLocalRandom.current().nextInt(0, limit);
        var today = currencyService.getCurrency();
        var yesterday = currencyService.getYesterdayCurrency();
        if (today.getRates().getRub() < yesterday.getRates().getRub()) {
            return gifProviderService.getSadGif().getDataDtoList().get(random);
        }
        return gifProviderService.getRichGif().getDataDtoList().get(random);
    }

    @GetMapping("/gif")
    public GifResponseDto receiveGif() {
        return gifProviderService.getRichGif();
    }

}
