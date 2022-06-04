package ru.rehtang.alphacase.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.xml.crypto.Data;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AlphaRestControllerTest {
    /*@Value("${alphaCase.feign.currency.symbol}")
    private String base;*/

    private final String RESULT_URI = "/result";
    private final String CURRENCY_URI = "/currency";
    private final String GIF_URI = "/gif";

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


    }

    @Test
    void returnResult() {

        when(curService.getCurrency()).thenReturn(new CurrencyResponseDto("USD", new RatesDto(74.0001)));
        when(curService.getYesterdayCurrency()).thenReturn(new CurrencyResponseDto("USD", new RatesDto(74.220)));
        when(gifService.getSadGif()).thenReturn(new GifResponseDto(List.of(new DataDto("sadgif1"), new DataDto("sadgif2"), new DataDto("sadgif3"))));

        buildGetRequest(RESULT_URI, status().isOk(), new DataDto("sadgif1"));

    }

    @Test
    void receiveGif() {
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