package com.example.storage;

import com.example.models.Post;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<Post> postList = new ArrayList<>();

    static {
        postList.add(new Post("ДимонЛимон", "Всем привет в этом чатике"));
        postList.add(new Post("Чипибарум", "Жду обновлений приложения с нетерпением!"));
    }

    public static void save(String username, String comment) {
        if (username != null && comment != null)
            postList.add(new Post(username, comment));
    }

    public static List<Post> getPostList() {
        return postList;
    }

    public static List<Post> findByName(String username) {
        List<Post> result = new ArrayList<>();
        for (Post post: postList) {
            if (post.getUsername().equals(username)) {
                result.add(post.copy());
            }
        }
        return (result.size() > 0) ? result : null;
    }
}
