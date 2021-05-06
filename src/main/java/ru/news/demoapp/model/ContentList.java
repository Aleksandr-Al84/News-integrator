package ru.news.demoapp.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ContentList {
    private List<Content> list;

    public ContentList() {
        list = new ArrayList<>();
    }
}
