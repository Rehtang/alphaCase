package ru.rehtang.alphacase.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rehtang.alphacase.dto.CurrencyResponseDto;

@FeignClient(name = "currencyFeignClient", url = "${alphaCase.feign.currency.url}")
public interface CurrencyFeignClient {
    @GetMapping("/latest.json")
    CurrencyResponseDto receiveCurrency(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );

    @GetMapping("/historical/{date}.json")
    CurrencyResponseDto receiveYesterdayCurrency(
            @PathVariable String date,
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );

}
