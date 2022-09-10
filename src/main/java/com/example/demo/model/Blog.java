package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String cover;

    private String content;

    public Blog() {
    }

    public Blog(Long id, String title, String cover, String content) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.content = content;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
