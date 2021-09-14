package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
/** интерфейс для асинхронного обращения для каждого из пользователей */
public interface MySampleApplicationServiceAsync {
    void getUserList(AsyncCallback<List<String>> callback);
    void getPostList(AsyncCallback<List<Post>> callback);
    void getPostList(String username, AsyncCallback<List<Post>> callback);
    void addPost(Post post, AsyncCallback<Void> async);
}
