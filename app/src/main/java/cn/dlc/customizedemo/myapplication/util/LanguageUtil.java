package cn.dlc.customizedemo.myapplication.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

import cn.dlc.commonlibrary.utils.ResUtil;

/**
 * Created by John on 2018/10/12.
 */
public class LanguageUtil {

    public static final Locale LOCALE_AR = new Locale("ar", "MA");
    public static final Locale LOCALE_MS = new Locale("ms");
    public static final Locale LOCALE_TH = new Locale("th");
    public static final Locale LOCALE_VI = new Locale("vi");
    public static final Locale LOCALE_NL = new Locale("nl");
    public static final Locale LOCALE_CS = new Locale("cs");
    public static final Locale LOCALE_PL = new Locale("pl");

    //获取系统语言
    public static Locale getSystemLocal() {    
        return Resources.getSystem().getConfiguration().locale;
    }

    /**
     * 获取当前的语言
     *
     * @param context
     * @return
     */
    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    /**
     * 检查区域语言
     *
     * @param context
     */
    public static void checkLocal(Context context,Language defaultErrorLanguage) {

        Language language = getSavedLanguage(defaultErrorLanguage);
        Locale currentLocale = getCurrentLocale(context);

        if (language.getLocale().equals(currentLocale)) {
            return;
        } else {
            updateLocal(context, language.getLocale());
        }
    }

    public static Language getSavedLanguage(Language defaultErrorLanguage ) {
        try {
            return Language.values()[1];
//            return Language.values()[Integer.parseInt(UserHelper.get().getLoacal())];
        } catch (Exception e) {
            return defaultErrorLanguage;
        }
    }

    public static void saveLanguage(Language language) {
//        UserHelper.get().saveLoacal(String.valueOf(language.ordinal()));
    }

    private static void updateLocal(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Locale.setDefault(locale);
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, dm);
        ResUtil.init(context);
    }

    public enum Language {

        /**
         * 英文
         */
        EN(Locale.ENGLISH),

        /**
         * 简体中文
         */
        ZH_CN(Locale.SIMPLIFIED_CHINESE),

        /**
         * 繁体中文
         */
        ZH_TW(Locale.TRADITIONAL_CHINESE),

        /**
         * 意大利语
         */
        IT(Locale.ITALIAN),

        /**
         * 阿拉伯语
         */
        AR(LanguageUtil.LOCALE_AR),

        /**
         * 韩语
         */
        KO(Locale.KOREAN),

        /**
         * 马来西亚语
         */
        MS(LanguageUtil.LOCALE_MS),

        /**
         * 泰语
         */
        TH(LanguageUtil.LOCALE_TH),

        /**
         * 越南语
         */
        VI(LanguageUtil.LOCALE_VI),

        /**
         * 荷兰语
         */
        NL(LanguageUtil.LOCALE_NL),

        /**
         * 捷克语
         */
        CS(LanguageUtil.LOCALE_CS),

        /**
         * 德语
         */
        DE(Locale.GERMAN),

        /**
         * 法语
         */
        FR(Locale.FRENCH),

        /**
         * 波兰语
         */
        PL(LanguageUtil.LOCALE_PL),

        /**
         * 日语
         */
        JA(Locale.JAPANESE);

        private final Locale mLocale;

        Language(Locale locale) {
            mLocale = locale;
        }

        public Locale getLocale() {
            return mLocale;
        }
    }
    
}
