package cn.gtmap.realestate.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <Description> <br>
 *
 * @author xuzhou<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2022/5/26 9:01 <br>
 * @see cn.gtmap.realestate.common.util <br>
 * @since V1.0<br>
 */
public class PinyinUtil {

    /**
     * 得到中文首字母
     *
     * @param str 需要转化的中文字符串
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    /**
     * 得到中文全拼
     *
     * @param str 需要转化的中文字符串
     * @return
     */
    public static String getPingYin(String str) {
        char[] t1;
        t1 = str.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        int t0 = t1.length;
        try {
            for (char c : t1) {
                // 判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(c, t3);
                    t4.append(t2[0]);
                } else {
                    t4.append(Character.toString(c));
                }
            }
            return t4.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4.toString();
    }

    /**
     * 获得中文字符串首字母
     *
     * @param str
     * @return
     */
    public static String getFirstPinYin(String str) {
        String s = getPinYinHeadChar(str);
        StringBuilder sb = new StringBuilder(s);
        if (sb.length() > 1) {
            String ss = sb.delete(1, sb.length()).toString();
            return String.valueOf(Character.toUpperCase(ss.toCharArray()[0]));
        }
        return str;
    }



}
