package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("MySampleApplicationService")
/** интерфейс сервиса возможных действий со страницей */
public interface MySampleApplicationService extends RemoteService {

    /** Получение списка имён пользователей
     * @return список имён пользователей
     */
    List<String> getUserList();

    /** Получение всего списка комментариев
     * @return список записей пользователей
     */
    List<Post> getPostList();

    /** Получение списка сообщений пользователя {@link Post#username}
     * @param username - имя пользователя
     * @return список записей пользователя
     */
    List<Post> getPostList(String username);

    /** Добавление записи
     * @param post - новая запись
     */
    void addPost(Post post);

    /**
     * Utility/Convenience class.
     * Use MySampleApplicationService.App.getInstance() to access static instance of MySampleApplicationServiceAsync
     */
    public static class App {
        private static MySampleApplicationServiceAsync ourInstance = GWT.create(MySampleApplicationService.class);

        public static synchronized MySampleApplicationServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
