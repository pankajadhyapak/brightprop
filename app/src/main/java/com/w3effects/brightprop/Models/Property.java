package com.w3effects.brightprop.Models;

public class Property {

    Integer id;
    String date;
    String slug;
    String link;
    Integer author;
    Integer featured_image;
    Content content;
    Title title;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(Integer featured_image) {
        this.featured_image = featured_image;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }


    public class Content{
        String rendered;

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }
    }

    public class Title{
        String rendered;

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }
    }
}
