package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.mySampleApplication.client.internationalization.Resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Точка начала приложения <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {
    /** RPC-сервис */
    private final MySampleApplicationServiceAsync myService = MySampleApplicationService.App.getInstance();

    /** Спискок пользователей */
    final ListBox userListBox = new ListBox(false);
    /** Место для вывода ошибки */
    final Label errorLabel = new Label();

    /** Метод загрузки страницы - аналог main() в Java */
    public void onModuleLoad() {
        // создаем главные элементы страницы
        final Button sendButton = new Button(Resources.TEXT.btnGetComment());
        final Button addCommentButton = new Button(Resources.TEXT.btnAddComment());
        sendButton.addStyleName("sendButton");
        RootPanel.get("userListBoxContainer").add(userListBox);
        RootPanel.get("errorLabelContainer").add(errorLabel);
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("addButtonContainer").add(addCommentButton);

        // заполняем список пользователей, имеющих сообщения
        userListBox.setFocus(true);
        refreshUserList();

        // создание и заполнение таблицы со всеми комментариями
        final CellTable<Post> mainTable = createCellTable();
        final ListDataProvider<Post> mainDataProvider = new ListDataProvider<Post>();
        mainDataProvider.addDataDisplay(mainTable);
        RootPanel.get("allCommentsTable").add(mainTable);
        myService.getPostList(
            new AsyncCallback<List<Post>>() {
                public void onFailure(Throwable caught) {
                    errorLabel.setText(Resources.TEXT.errServer()+Resources.TEXT.errServer_commentList());
                }
                public void onSuccess(List<Post> result) {
                    mainDataProvider.setList(result);
                }
            }
        );

        // работа с добавлением комментария
        final VerticalPanel addPanel = new VerticalPanel();
        addPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        addPanel.setVisible(false);
        final TextArea nameArea = new TextArea();
        nameArea.getElement().setPropertyString("placeholder", Resources.TEXT.placeholder_name());
        final TextArea commentArea = new TextArea();
        commentArea.getElement().setPropertyString("placeholder", Resources.TEXT.placeholder_comment());
        final Button sendCommentBtn = new Button(Resources.TEXT.btnSend());
        addCommentButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addPanel.setVisible(true);
                sendCommentBtn.setEnabled(true);
                sendCommentBtn.setFocus(true);
                addCommentButton.setEnabled(false);
            }
        });
        addPanel.add(nameArea);
        addPanel.add(commentArea);
        sendCommentBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String username = nameArea.getText();
                String comment = commentArea.getText();
                Post post = new Post(username, comment, new Date().toString());
                myService.addPost(post,
                        new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {

                            }
                            @Override
                            public void onSuccess(Void result) {
                                List<Post> newList = new ArrayList<Post>(mainDataProvider.getList());
                                newList.add(post);
                                mainDataProvider.setList(newList);
                                mainDataProvider.refresh();
                                refreshUserList();
                                addPanel.setVisible(false);
                                nameArea.setText("");
                                commentArea.setText("");
                                addCommentButton.setEnabled(true);
                            }
                        }
                );
            }
        });
        addPanel.add(sendCommentBtn);
        RootPanel.get("addPanelContainer").add(addPanel);

        // окно с результатом поиска комментариев заданного пользователя
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(Resources.TEXT.titleResultTable());
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button(Resources.TEXT.btnClose());
        closeButton.getElement().setId("closeButton");
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");

        final CellTable<Post> userTable = createCellTable();
        dialogVPanel.add(userTable);

        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });
        class RPCClickHandler implements ClickHandler, KeyUpHandler {
            public void onClick(ClickEvent event) {
                sendUserToServer();
            }
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendUserToServer();
                }
            }
            private void sendUserToServer() {
                errorLabel.setText("");
                final String username = userListBox.getValue(userListBox.getSelectedIndex());
                sendButton.setEnabled(false);
                myService.getPostList(username,

                new AsyncCallback<List<Post>>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText(Resources.TEXT.errServer());
                        serverResponseLabel
                                .addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(Resources.TEXT.errServer()+Resources.TEXT.errServer_commentList());
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                    public void onSuccess(List<Post> result) {
                        dialogBox.setText(Resources.TEXT.titleResultTable() + username);
                        userTable.setRowCount(result.size(), true);
                        userTable.setRowData(0, result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        }
        RPCClickHandler handler = new RPCClickHandler();
        sendButton.addClickHandler(handler);
    }

    /** Создание таблицы для отображения комментариев пользователей
     * @return таблица с заданным форматом для отображения комментариев
     * */
    private CellTable<Post> createCellTable() {
        final CellTable<Post> table = new CellTable<Post>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<Post> authorColumn = new TextColumn<Post>() {
            public String getValue(Post object) {
                return object.getUsername();
            }
        };
        table.addColumn(authorColumn, Resources.TEXT.username());
        TextColumn<Post> titleColumn = new TextColumn<Post>() {
            public String getValue(Post object) {
                return object.getComment();
            }
        };
        table.addColumn(titleColumn, Resources.TEXT.comment());
        TextColumn<Post> dateColumn = new TextColumn<Post>() {
            public String getValue(Post object) {
                return object.getDate();
            }
        };
        table.addColumn(dateColumn, Resources.TEXT.date());

        return table;
    }

    /** Обновление списка пользователей */
    private void refreshUserList() {
        myService.getUserList(new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                errorLabel.setText(Resources.TEXT.errServer()+Resources.TEXT.errServer_userList());
            }

            public void onSuccess(List<String> result) {
                userListBox.clear();
                for(String r : result){userListBox.addItem(r);
                }
            }
        });
    }
}
