package com.nepxion.thunder.console.context;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Neptune
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.awt.Font;

import com.nepxion.swing.font.FontContext;
import com.nepxion.swing.lookandfeel.LookAndFeelManager;
import com.nepxion.util.locale.LocaleContext;

public class UIContext {
    private static final String FONT_NAME_YAHEI = "\u5fae\u8f6f\u96c5\u9ed1"; // 微软雅黑
    private static final int FONT_SMALL_SIZE_YAHEI = 11;
    private static final int FONT_MIDDLE_SIZE_YAHEI = FONT_SMALL_SIZE_YAHEI + 1;
    private static final int FONT_LARGE_SIZE_YAHEI = FONT_SMALL_SIZE_YAHEI + 2;

    private static final String FONT_NAME_CALIBRI = "Calibri";
    private static final int FONT_SMALL_SIZE_CALIBRI = 12;
    private static final int FONT_MIDDLE_SIZE_CALIBRI = FONT_SMALL_SIZE_CALIBRI + 1;
    private static final int FONT_LARGE_SIZE_CALIBRI = FONT_SMALL_SIZE_CALIBRI + 2;

    public static void initialize() {
        FontContext.registerFont(getFontName(), Font.PLAIN, getDefaultFontSize());

        LookAndFeelManager.setNimbusLookAndFeel();
    }

    public static String getFontName() {
        if (LocaleContext.getLocale() == LocaleContext.LOCALE_ZH_CN) {
            return FONT_NAME_YAHEI;
        }

        return FONT_NAME_CALIBRI;
    }

    public static int getDefaultFontSize() {
        String fontName = getFontName();
        if (fontName.equals(FONT_NAME_YAHEI)) {
            return FONT_MIDDLE_SIZE_YAHEI;
        } else {
            return FONT_MIDDLE_SIZE_CALIBRI;
        }
    }

    public static int getSmallFontSize() {
        String fontName = getFontName();
        if (fontName.equals(FONT_NAME_YAHEI)) {
            return FONT_SMALL_SIZE_YAHEI;
        } else {
            return FONT_SMALL_SIZE_CALIBRI;
        }
    }

    public static int getMiddleFontSize() {
        String fontName = getFontName();
        if (fontName.equals(FONT_NAME_YAHEI)) {
            return FONT_MIDDLE_SIZE_YAHEI;
        } else {
            return FONT_MIDDLE_SIZE_CALIBRI;
        }
    }

    public static int getLargeFontSize() {
        String fontName = getFontName();
        if (fontName.equals(FONT_NAME_YAHEI)) {
            return FONT_LARGE_SIZE_YAHEI;
        } else {
            return FONT_LARGE_SIZE_CALIBRI;
        }
    }
}