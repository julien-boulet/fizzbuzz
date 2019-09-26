package com.boubou.fizzbuzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.boubou.fizzbuzz.controllers.GameController;
import com.boubou.fizzbuzz.entities.Statistic;
import com.boubou.fizzbuzz.repositories.StatisticRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FizzbuzzApplicationTests {

    private static final String BASE_URL = "api";
    @Value("${application.versionApi}")
    private String versionApi;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private GameController gameController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticRepository mockStatisticRepository;

    @Test
    public void contextLoads() {
        assertThat(gameController).isNotNull();
    }

    @Test
    public void getInvalidPath() throws Exception {
        this.mockMvc.perform(get("/invalidPath")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void getCommonFizzBuzz() throws Exception {

        // mock
        when(mockStatisticRepository.findByInt1AndInt2AndLimitAndStr1AndStr2(anyInt(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(Optional.empty());
        when(mockStatisticRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        ResultActions resultActions = this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "fizzbuzz")
                .contentType(MediaType.APPLICATION_JSON)
                .param("int1", Integer.toString(TestUtils.INT_1))
                .param("int2", Integer.toString(TestUtils.INT_2))
                .param("limit", Integer.toString(TestUtils.LIMIT))
                .param("str1", TestUtils.FIZZ)
                .param("str2", TestUtils.BUZZ))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("application/json"));

        List<String> result = objectMapper.readerFor(List.class).readValue(resultActions.andReturn().getResponse().getContentAsString());

        assertThat(result).isEqualTo(TestUtils.EXPECTED_RESULT);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -35, -60, 0})
    public void getFizzBuzzBadInt(int badInt) throws Exception {

        // ENGLISH error message
        this.fizzBuzzBadInt(badInt, "Invalid query parameters : '%s=%d' must be greater than 0", "en");

        // FRENCH error message
        this.fizzBuzzBadInt(badInt, "Les parametres de la requete sont invalides : '%s=%d' doit être strictement positif", "fr");
    }

    private void fizzBuzzBadInt(int badInt, String errorMessage, String en) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "fizzbuzz")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, en)
                .param("int1", Integer.toString(badInt))
                .param("int2", Integer.toString(TestUtils.INT_2))
                .param("limit", Integer.toString(TestUtils.LIMIT))
                .param("str1", TestUtils.FIZZ)
                .param("str2", TestUtils.BUZZ))
                .andExpect(status().is4xxClientError()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.format(errorMessage, "int1", badInt));

        result = this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "fizzbuzz")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, en)
                .param("int1", Integer.toString(TestUtils.INT_1))
                .param("int2", Integer.toString(badInt))
                .param("limit", Integer.toString(TestUtils.LIMIT))
                .param("str1", TestUtils.FIZZ)
                .param("str2", TestUtils.BUZZ))
                .andExpect(status().is4xxClientError()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.format(errorMessage, "int2", badInt));

        result = this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "fizzbuzz")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, en)
                .param("int1", Integer.toString(TestUtils.INT_2))
                .param("int2", Integer.toString(TestUtils.INT_2))
                .param("limit", Integer.toString(badInt))
                .param("str1", TestUtils.FIZZ)
                .param("str2", TestUtils.BUZZ))
                .andExpect(status().is4xxClientError()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.format(errorMessage, "limit", badInt));
    }

    @ParameterizedTest
    @MethodSource("com.boubou.fizzbuzz.TestUtils#blankStrings")
    public void getFizzBuzzBadString(String badString) throws Exception {

        // ENGLISH error message
        fizzBuzzBadString(badString, "Invalid query parameters : '%s=%s' must not be blank", "en");

        // FRENCH error message
        fizzBuzzBadString(badString, "Les parametres de la requete sont invalides : '%s=%s' ne peut pas être vide", "fr");

    }

    private void fizzBuzzBadString(String badString, String errorMessage, String en) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "fizzbuzz")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, en)
                .param("int1", Integer.toString(TestUtils.INT_1))
                .param("int2", Integer.toString(TestUtils.INT_2))
                .param("limit", Integer.toString(TestUtils.LIMIT))
                .param("str1", badString)
                .param("str2", TestUtils.BUZZ))
                .andExpect(status().is4xxClientError()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.format(errorMessage, "str1", badString));

        result = this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "fizzbuzz")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, en)
                .param("int1", Integer.toString(TestUtils.INT_1))
                .param("int2", Integer.toString(TestUtils.INT_2))
                .param("limit", Integer.toString(TestUtils.LIMIT))
                .param("str1", TestUtils.FIZZ)
                .param("str2", badString))
                .andExpect(status().is4xxClientError()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.format(errorMessage, "str2", badString));
    }

    @Test
    public void getOneTopStatisticWithResult() throws Exception {
        final Statistic statistic = Statistic.builder().int1(TestUtils.INT_1).int2(TestUtils.INT_2).limit(TestUtils.LIMIT).str1(TestUtils.FIZZ).str2(TestUtils.BUZZ).build();

        when(mockStatisticRepository.findFirstByOrderByCountDesc()).thenReturn(Optional.of(statistic));

        ResultActions resultActions = this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "oneTopStatistic"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("application/json"));

        Statistic result = objectMapper.readerFor(Statistic.class).readValue(resultActions.andReturn().getResponse().getContentAsString());
        assertThat(result).isEqualTo(statistic);
    }

    @Test
    public void getOneTopStatisticNoResult() throws Exception {
        when(mockStatisticRepository.findFirstByOrderByCountDesc()).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/" + BASE_URL + "/" + versionApi + "/" + "oneTopStatistic"))
                .andExpect(status().isNotFound());
    }
}
