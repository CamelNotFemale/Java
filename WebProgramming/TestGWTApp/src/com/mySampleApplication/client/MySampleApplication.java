package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {
    private static final String SRV_ERR = "Ошибка сервера! ";
    private static final String SRV_ERR_GET_READER_LIST = "Невозможно получить список пользователей.";
    private static final String SRV_ERR_GET_BOOK_LIST = "Невозможно получить список комментариев пользователя.";
    private static final String GET_COMMENT_LIST_BTN = "Получить список комментариев";
    private static final String ADD_COMMENT_BTN = "Добавить комментарий";
    private static final String CLOSE_BTN = "Закрыть";
    private static final String BOOKS_WND_TITLE = "Все комментарии пользователя ";
    /** RPC-сервис */
    private final MySampleApplicationServiceAsync myService = MySampleApplicationService.App.getInstance();

    /** Элементы главной для общего доступа в функциях */
    final ListBox userListBox = new ListBox(false);
    final Label errorLabel = new Label();

    public void onModuleLoad() {
        final Button sendButton = new Button(GET_COMMENT_LIST_BTN);
        final Button addCommentButton = new Button(ADD_COMMENT_BTN);
        sendButton.addStyleName("sendButton");
        RootPanel.get("userListBoxContainer").add(userListBox);
        RootPanel.get("errorLabelContainer").add(errorLabel);
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("addButtonContainer").add(addCommentButton);

        userListBox.setFocus(true);
        refreshUserList();

        final CellTable<Post> mainTable = createCellTable();
        final ListDataProvider<Post> mainDataProvider = new ListDataProvider<Post>();
        mainDataProvider.addDataDisplay(mainTable);
        RootPanel.get("allCommentsTable").add(mainTable);
        myService.getPostList(
            new AsyncCallback<List<Post>>() {
                public void onFailure(Throwable caught) {
                    errorLabel.setText(SRV_ERR+SRV_ERR_GET_READER_LIST);
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
        nameArea.getElement().setPropertyString("placeholder", "enter username");
        final TextArea commentArea = new TextArea();
        commentArea.getElement().setPropertyString("placeholder", "enter comment");
        final Button sendCommentBtn = new Button("Отправить");
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

        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(BOOKS_WND_TITLE);
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button(CLOSE_BTN);
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
                        dialogBox.setText(SRV_ERR);
                        serverResponseLabel
                                .addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SRV_ERR+SRV_ERR_GET_BOOK_LIST);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                    public void onSuccess(List<Post> result) {
                        dialogBox.setText(BOOKS_WND_TITLE + username);
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
    private CellTable<Post> createCellTable() {
        final CellTable<Post> table = new CellTable<Post>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<Post> authorColumn = new TextColumn<Post>() {
            public String getValue(Post object) {
                return object.getUsername();
            }
        };
        table.addColumn(authorColumn, "Пользователь");
        TextColumn<Post> titleColumn = new TextColumn<Post>() {
            public String getValue(Post object) {
                return object.getComment();
            }
        };
        table.addColumn(titleColumn, "Комментарий");
        TextColumn<Post> dateColumn = new TextColumn<Post>() {
            public String getValue(Post object) {
                return object.getDate();
            }
        };
        table.addColumn(dateColumn, "Дата");

        return table;
    }
    private void refreshUserList() {
        myService.getUserList(new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                errorLabel.setText(SRV_ERR+SRV_ERR_GET_READER_LIST);
            }

            public void onSuccess(List<String> result) {
                userListBox.clear();
                for(String r : result){userListBox.addItem(r);
                }
            }
        });
    }
}
