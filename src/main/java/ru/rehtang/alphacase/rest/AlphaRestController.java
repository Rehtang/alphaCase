package ru.rehtang.alphacase.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rehtang.alphacase.dto.CurrencyResponseDto;
import ru.rehtang.alphacase.dto.DataDto;
import ru.rehtang.alphacase.dto.GifResponseDto;
import ru.rehtang.alphacase.service.CurrencyProviderService;
import ru.rehtang.alphacase.service.GifProviderService;

@RestController
@RequiredArgsConstructor
public class AlphaRestController {

    private final CurrencyProviderService currencyService;
    private final GifProviderService gifProviderService;

    @GetMapping("/currency")
    public CurrencyResponseDto receiveCurrency() {
        return currencyService.getCurrency();
    }

    @GetMapping("/result")
    public DataDto returnResult() {
        var today = currencyService.getCurrency();
        var yesterday = currencyService.getYesterdayCurrency();

        if (today.getRates().getRub() < yesterday.getRates().getRub()) {
            return gifProviderService.getSadGif().getDataDtoList().get(0);
        } else if (today.getRates().getRub() == yesterday.getRates().getRub()) {
            return new DataDto("nothing changed");
        } else {
            return gifProviderService.getRichGif().getDataDtoList().get(0);
        }

    }

    @GetMapping("/gif")
    public GifResponseDto receiveGif() {
        return gifProviderService.getRichGif();
    }

    @GetMapping("/sadGif")
    public GifResponseDto receiveSadGif() {
        return gifProviderService.getSadGif();
    }

}
