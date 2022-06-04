package ru.rehtang.alphacase.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.rehtang.alphacase.dto.CurrencyResponseDto;
import ru.rehtang.alphacase.dto.DataDto;
import ru.rehtang.alphacase.dto.GifResponseDto;
import ru.rehtang.alphacase.dto.RatesDto;
import ru.rehtang.alphacase.service.CurrencyProviderService;
import ru.rehtang.alphacase.service.GifProviderService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AlphaRestControllerTest {
    private final String RESULT_URI = "/result";
    private final String CURRENCY_URI = "/currency";
    private final String GIF_URI = "/gif";
    private final String SAD_GIF_URI = "/sadGif";
    @Value("${alphaCase.feign.currency.symbol}")
    private String base;
    @MockBean
    private CurrencyProviderService curService;
    @MockBean
    private GifProviderService gifService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void receiveCurrency() {
        CurrencyResponseDto today = new CurrencyResponseDto(base, new RatesDto(85.3186));
        CurrencyResponseDto exp = new CurrencyResponseDto(base, new RatesDto(85.3186));

        when(curService.getCurrency()).thenReturn(today);

        buildGetRequest(CURRENCY_URI, status().isOk(), exp);
    }

    @Test
    void returnNegativeResult() {
        CurrencyResponseDto today = new CurrencyResponseDto(base, new RatesDto(74.0001));
        CurrencyResponseDto yesterday = new CurrencyResponseDto(base, new RatesDto(74.220));
        GifResponseDto sadList = new GifResponseDto(List.of(new DataDto("sadgif1")));
        DataDto expData = new DataDto("sadgif1");

        when(curService.getCurrency()).thenReturn(today);
        when(curService.getYesterdayCurrency()).thenReturn(yesterday);
        when(gifService.getSadGif()).thenReturn(sadList);

        buildGetRequest(RESULT_URI, status().isOk(), expData);

    }

    @Test
    void returnPositiveResult() {
        CurrencyResponseDto today = new CurrencyResponseDto(base, new RatesDto(75.0001));
        CurrencyResponseDto yesterday = new CurrencyResponseDto(base, new RatesDto(73.220));
        GifResponseDto richList = new GifResponseDto(List.of(new DataDto("richGif")));
        DataDto expData = new DataDto("richGif");

        when(curService.getCurrency()).thenReturn(today);
        when(curService.getYesterdayCurrency()).thenReturn(yesterday);
        when(gifService.getRichGif()).thenReturn(richList);

        buildGetRequest(RESULT_URI, status().isOk(), expData);

    }

    @Test
    void returnEqualResult() {
        CurrencyResponseDto today = new CurrencyResponseDto(base, new RatesDto(75.0001));
        CurrencyResponseDto yesterday = new CurrencyResponseDto(base, new RatesDto(75.0001));
        DataDto expData = new DataDto("nothing changed");

        when(curService.getCurrency()).thenReturn(today);
        when(curService.getYesterdayCurrency()).thenReturn(yesterday);

        buildGetRequest(RESULT_URI, status().isOk(), expData);

    }

    @Test
    void receiveGif() {
        GifResponseDto richList = new GifResponseDto(List.of(new DataDto("richOne")));
        GifResponseDto expList = new GifResponseDto(List.of(new DataDto("richOne")));

        when(gifService.getRichGif()).thenReturn(richList);

        buildGetRequest(GIF_URI, status().isOk(), expList);

    }

    @Test
    void receiveSadGif() {
        GifResponseDto sadList = new GifResponseDto(List.of(new DataDto("sadOne")));
        GifResponseDto expList = new GifResponseDto(List.of(new DataDto("sadOne")));

        when(gifService.getSadGif()).thenReturn(sadList);

        buildGetRequest(SAD_GIF_URI, status().isOk(), expList);
    }

    private void buildGetRequest(String uri, ResultMatcher resultStatus, Object expObject) {
        try {
            mockMvc
                    .perform(
                            get(uri)
                                    .contentType(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(content().json(objectMapper.writeValueAsString(expObject))).andExpect(resultStatus);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}