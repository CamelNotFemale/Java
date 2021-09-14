package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.client.MySampleApplicationService;
import com.mySampleApplication.client.Post;

import java.util.*;
/** Реализация логики сервера - некий сервлет */
public class MySampleApplicationServiceImpl extends RemoteServiceServlet implements MySampleApplicationService {

    private static List<Post> db = null;
    static {
        db = new ArrayList<>();
        db.add(new Post("ДимонЛимон", "всем приветв этом чатике", new Date().toString()));
        db.add(new Post("Чипибарум", "ждУ оБНовлЕний", new Date().toString()));
        db.add(new Post("ДимонЛимон", "проверка 7 лабы", new Date().toString()));
    }
    @Override
    public List<String> getUserList() {
        Set<String> result = new HashSet<>();
        for (Post post: db) {
            result.add(post.getUsername());
        }
        return new ArrayList<>(result);
    }

    @Override
    public List<Post> getPostList() {
        return db;
    }
    @Override
    public List<Post> getPostList(String username) {
        List<Post> result = new ArrayList<>();
        for (Post post: db) {
            if (post.getUsername().equals(username)) {
                result.add(post);
            }
        }
        return result;
    }

    @Override
    public void addPost(Post post) {
        if (post != null) db.add(post);
    }
}