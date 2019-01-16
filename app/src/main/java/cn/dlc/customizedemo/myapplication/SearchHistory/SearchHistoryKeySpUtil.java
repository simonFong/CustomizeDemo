package cn.dlc.customizedemo.myapplication.SearchHistory;

import java.util.List;

/**
 * Created by fengzimin  on  2019/01/11.
 * interface by
 */
public class SearchHistoryKeySpUtil {
    private static List<String> mHistoryListData;

    /**
     * 更新历史记录sp
     *
     * @param key
     */
    public static void refreshHistroy(String key) {
        if (mHistoryListData.contains(key)) {
            mHistoryListData.remove(key);
        }
        mHistoryListData.add(0, key);
        SharedPreferencesUtil.putListData(Constants.SEARCH_KEY_HISTORY, mHistoryListData);
    }

    /**
     * 删除单条历史记录
     *
     * @param key
     */
    public static void deleteSingleHistory(String key) {
        mHistoryListData.remove(key);
        SharedPreferencesUtil.putListData(Constants.SEARCH_KEY_HISTORY, mHistoryListData);
    }

    /**
     * 获取搜索历史
     *
     * @return
     */
    public static List<String> getHistoryKeyList() {
        mHistoryListData = SharedPreferencesUtil.getListData(Constants.SEARCH_KEY_HISTORY, String.class);
        return mHistoryListData;
    }

    /**
     * 清除所有历史记录
     */
    public static void clearHistoryList() {
        mHistoryListData.clear();
        SharedPreferencesUtil.putListData(Constants.SEARCH_KEY_HISTORY, mHistoryListData);
    }
}
