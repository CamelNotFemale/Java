package com.mySampleApplication.client.internationalization;
import com.google.gwt.i18n.client.Constants;
/** интерфейс получения ресурсов для интернационализации из Text_XXX.properties */
public interface Text extends Constants
{
    String btnAddComment();

    String errServer();

    String errServer_userList();

    String errServer_commentList();

    String btnGetComment();

    String btnClose();

    String titleResultTable();

    String username();

    String comment();

    String date();

    String btnSend();

    String placeholder_name();

    String placeholder_comment();

    String title();

    String header();
}
