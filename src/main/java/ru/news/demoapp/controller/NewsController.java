package ru.news.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import ru.news.demoapp.model.Content;
import ru.news.demoapp.model.ContentList;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/meduza")
public class NewsController {
    //private AtomicInteger atomicInteger = new AtomicInteger();
    private final String url = "http://localhost:8080/news";
    private final AtomicInteger atomicInteger;

    public NewsController(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    @GetMapping("/news")
    public String getNews(@RequestParam(value = "page", required = false)
                                      String page, ModelMap modelMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(url)
                .append("?page")
                .append(page);

        String url = stringBuilder.toString();
        WebClient webClient = WebClient.create();
        ContentList responseJson = webClient.get()
                .uri(url)
                .exchange()
                .block()
                .bodyToMono(ContentList.class)
                .block();

        assert responseJson != null;

        List<Content> contentList = responseJson.getList();
        modelMap.addAttribute("contentList", contentList);

        return "news";
    }

    @PostMapping("/button")
    public String page(@ModelAttribute(name = "next") String nextButton,
                       @ModelAttribute(name = "previous") String previousButton) {
        if (nextButton.equals("next")) {
            atomicInteger.incrementAndGet();
            return "redirect:/meduza/news?page=" + atomicInteger.get();
        } else {
            if (atomicInteger.get() == 0) {
                return "redirect:/meduza/news?page=0";
            } else {
                atomicInteger.decrementAndGet();
                return "redirect:/meduza/news?page=" + atomicInteger.get();
            }

        }


//    public AtomicInteger getAtomicInteger() {
//        return atomicInteger;
//    }
    }
}

