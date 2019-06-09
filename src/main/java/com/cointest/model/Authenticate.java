package com.cointest.model;

/**
 * Created by edward on 6/20/17.
 */
public class Authenticate {
    private final long id;
    private final String content;

    public Authenticate(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
