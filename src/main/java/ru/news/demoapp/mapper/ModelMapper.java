package ru.news.demoapp.mapper;

public interface ModelMapper<Model, DTO>{
    DTO toDto(Model model);
    Model toModel(DTO dto);

}
