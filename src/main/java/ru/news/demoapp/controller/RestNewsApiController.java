package ru.news.demoapp.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import ru.news.demoapp.mapper.ContentMapper;
import ru.news.demoapp.model.Content;
import ru.news.demoapp.model.ContentList;
import ru.news.demoapp.model.MeduzaNews;

import java.util.List;
import java.util.Map;


@RestController
public class RestNewsApiController {

    private final String url = "https://meduza.io/api/v3/search?chrono=news&locale=ru&page=0&per_page=24";

    private final ContentMapper contentMapper;

    public RestNewsApiController(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    @GetMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContentList news(@RequestParam(value = "page", required = false, defaultValue = "0")String page) throws JsonProcessingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("https://meduza.io/api/v3/search?chrono=news&locale=ru&page=0&per_page=")
                .append(page)
                .append("&per_page=24");

        String url = stringBuilder.toString();

        WebClient webClient = WebClient.create();
        String responseJson = webClient.get()
                .uri(url)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        MeduzaNews meduzaNews = stringToJsonMeduza(responseJson);
        List<Content> content = contentMapper.toDto(meduzaNews);
        return new ContentList(content);
    }

    @SneakyThrows
    private MeduzaNews stringToJsonMeduza(String text) {

        ObjectMapper objectMapper = new ObjectMapper();
        //new JodaModule()
        objectMapper.registerModule(new JSR310Module());
        return  objectMapper.readValue(text, MeduzaNews.class);
    }

}
