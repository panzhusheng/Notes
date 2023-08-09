package com.example.notes.bean;

import java.io.Serializable;

public class Notes implements Serializable {
    private int id;
    private String title;
    private String content;
    private String time;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Notes() {
    }

    public Notes(int id, String title, String content, String time,String user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
