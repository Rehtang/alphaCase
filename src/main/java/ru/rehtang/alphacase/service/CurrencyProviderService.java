package ru.rehtang.alphacase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rehtang.alphacase.dto.CurrencyResponseDto;
import ru.rehtang.alphacase.feign.CurrencyFeignClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CurrencyProviderService {
    private final CurrencyFeignClient client;

    @Value("${alphaCase.feign.currency.apiKey}")
    private String currencyApiKey;

    @Value("${alphaCase.feign.currency.symbol}")
    private String base;

    public CurrencyResponseDto getCurrency() {
        return client.receiveCurrency(currencyApiKey, base);
    }

    public CurrencyResponseDto getYesterdayCurrency() {
        String y = new SimpleDateFormat("yyyy-MM-dd").format(yesterday());
        return client.receiveYesterdayCurrency(y, currencyApiKey, base);

    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
