package com.mySampleApplication.server;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
    /** Доступные языки локализации */
    private static String[] languages = new String[] {"ru", "en"};
    private static ResourceBundle res = null;
    private static DateTimeFormatter localeTimeFormatter = null;

    static { // по-умолчанию русская локализация
        setup("ru");
    }
    /** Получение локализированного значения через ResourseBundle */
    public static String getString(String str) {
        if (res==null) return null;
        return res.getString(str);
    }

    /** Получение локализированного времени для указанного часового пояса */
    public static String getTime() {
        if (res == null || localeTimeFormatter == null) return null;
        return localeTimeFormatter.format(LocalDateTime.now(ZoneId.of(res.getString("zoneid"))));
    }

    /** Выбор языка локализации */
    public static boolean setup(String lang) {
        // по-умолчанию русский язык
        if(lang == null) {
            lang = "ru";
        }
        // если запрашиваемая локализация отсутствует в списке поддерживаемых languages
        if( !Arrays.asList(languages).contains(lang) ) {
            // некорректная локализация
            return false;
        }
        // Файлы ресурсов post.properties, post_en.properties и post_ru.properties, post_de.properties
        // Установка локализации в соответствии с выбором пользователя
        res = ResourceBundle.getBundle("post", new Locale(lang));
        // Создание форматтера для локализации даты
        localeTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss", new Locale(lang));
        return true;
    }
}
