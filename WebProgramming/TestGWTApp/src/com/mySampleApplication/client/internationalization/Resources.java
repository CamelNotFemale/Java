package com.mySampleApplication.client.internationalization;
import com.google.gwt.core.client.GWT;

/** Класс Ресурсов для получения параметров из внешних файлов */
public class Resources {
    /** Ресурс, соответствующий параметрам из Text_XXX.properties */
    public static final Text TEXT = GWT.create(Text.class);
}
