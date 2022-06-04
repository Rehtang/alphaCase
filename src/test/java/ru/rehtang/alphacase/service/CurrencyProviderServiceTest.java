package ru.rehtang.alphacase.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import ru.rehtang.alphacase.dto.CurrencyResponseDto;
import ru.rehtang.alphacase.dto.RatesDto;
import ru.rehtang.alphacase.feign.CurrencyFeignClient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyProviderServiceTest {
    @MockBean
    private CurrencyFeignClient curClient;
    @Value("${alphaCase.feign.currency.symbol}")
    private String base;

    @Test
    void getCurrency() {
        CurrencyResponseDto actual = new CurrencyResponseDto(base, new RatesDto(75.00055));

        when(curClient.receiveCurrency(anyString(), anyString())).thenReturn(actual);
        var received = curClient.receiveCurrency(anyString(), anyString());

        Assert.notNull(received, "it's NULL!");
    }

    @Test
    void getYesterdayCurrency() {
        CurrencyResponseDto yesterday = new CurrencyResponseDto(base, new RatesDto(85.365654));

        when(curClient.receiveCurrency(anyString(), anyString())).thenReturn(yesterday);
        var received = curClient.receiveCurrency(anyString(), anyString());

        Assert.notNull(received, "it's NULL!");
    }
}