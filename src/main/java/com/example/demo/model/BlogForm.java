package com.example.demo.model;

import org.springframework.web.multipart.MultipartFile;


public class BlogForm {

    private Long id;

    private String title;

    private MultipartFile cover;

    private String content;

    private Category category;

    public BlogForm() {
    }

    public BlogForm(Long id, String title, MultipartFile cover, String content, Category category) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.category = category;
    }

    public BlogForm(String title, MultipartFile cover, String content, Category category) {
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getCover() {
        return cover;
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
