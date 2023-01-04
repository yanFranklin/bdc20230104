package cn.gtmap.realestate.common.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/18
 * @description
 */
public class BdcdyhToolUtils {
    private static final Integer START = 0;
    private static final Integer SPLIT_ONE = 6;
    private static final Integer SPLIT_TWO = 12;
    private static final Integer SPLIT_THREE = 19;



    /**
     * @param bdcdyh 不动产单元号
     * @return String  不动产单元号特征码
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 判断不动产单元号是不是28位，截取第20位的特征码。否则返回参数bdcdyh
     */
    public static String getDzwTzm(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.length(bdcdyh) == CommonConstantUtils.BDCDYH_LENGTH) {
            return StringUtils.substring(bdcdyh, CommonConstantUtils.BDCLX_TZM_INDEX - 1, CommonConstantUtils.BDCLX_TZM_INDEX);
        }
        return bdcdyh;
    }
    /**
     * @param bdcdyh 不动产单元号
     * @return String 返回格式化后的bdcdyh
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 格式化bdcdyh的显示样式
     */
    public static String formatBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.length(bdcdyh) == CommonConstantUtils.BDCDYH_LENGTH) {
            return StringUtils.substring(bdcdyh, START, SPLIT_ONE)
                    .concat(" ").concat(StringUtils.substring(bdcdyh, SPLIT_ONE, SPLIT_TWO))
                    .concat(" ").concat(StringUtils.substring(bdcdyh, SPLIT_TWO, SPLIT_THREE))
                    .concat(" ").concat(StringUtils.substring(bdcdyh, SPLIT_THREE, CommonConstantUtils.BDCDYH_LENGTH));
        }
        return bdcdyh;
    }

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param bdcdyh
     * @return
     * @description 将房屋的不动产单元转换为纯土地的不动产单元
     */
    public static String convertFToW(String bdcdyh){
        if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
            // 查询当前单元号的特征码，仅对房屋进行转换
            String tzm = queryTzmByBdcdyh(bdcdyh);
            if (!StringUtils.equals(tzm, CommonConstantUtils.BHTZM_FW)) {
                return bdcdyh;
            }
            StringBuilder sb = new StringBuilder(28).append(bdcdyh.substring(0, 19)).append(CommonConstantUtils.SUFFIX_ZD_BDCDYH);
            return sb.toString();

        }
        return "";
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 土地、房屋、林地的不动产单元号
     * @return {boolean} 不是 ： false ; 是 ：true
     * @description 根据不动产单元号判断是否是土地、房屋、林地
     */
    public static boolean isTdFwOrLd(String bdcdyh){
        String dzwTzm = BdcdyhToolUtils.getDzwTzm(bdcdyh);

        return CommonConstantUtils.DZWTZM_TD.equals(dzwTzm)
                || CommonConstantUtils.BHTZM_FW.equals(dzwTzm)
                || CommonConstantUtils.BHTZM_LQ.equals(dzwTzm);
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 判断是否是 纯土地的BDCDY
     */
    public static boolean isTdBdcdy(String bdcdyh){
        String dzwTzm = BdcdyhToolUtils.getDzwTzm(bdcdyh);
        if(StringUtils.equals(CommonConstantUtils.DZWTZM_TD,dzwTzm)){
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param bdcdyh
     * @return
     * @description 根据不动产单元号判断是否是宗海特征码
     */
    public static boolean ifZh(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
            String zdzhtzm = bdcdyh.substring(13,14);
            if(ArrayUtils.contains(CommonConstantUtils.ZH_TZM,zdzhtzm)){
                return true;
            }
        }
        return false;
    }
    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param zdzhh
     * @return
     * @description 根据宗地宗海号判断是否是宗海特征码
     */
    public static boolean ifZhh(String zdzhh){
        if(StringUtils.isNotBlank(zdzhh) && zdzhh.length() == 19) {
            String zdzhtzm = zdzhh.substring(13,14);
            if(ArrayUtils.contains(CommonConstantUtils.ZH_TZM,zdzhtzm)){
                return true;
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param zdzhh
     * @return
     * @description 将宗地宗海号转换为纯土地的不动产单元
     */
    public static String convertToW(String zdzhh){
        if(StringUtils.isNotBlank(zdzhh) && zdzhh.length() == 19) {
            StringBuilder sb = new StringBuilder(28).append(zdzhh).append(CommonConstantUtils.SUFFIX_ZD_BDCDYH);
            return sb.toString();
        }
        return "";
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取受理编号特征码（代码摘自受理同名方法）
     */
    public static String queryTzmByBdcdyh(String bdcdyh) {
        String tzm = "";
        String dzwtzm = bdcdyh.substring(19, 20);
        String qslxdm = StringUtils.substring(bdcdyh, 13, 14);
        if (StringUtils.equals(dzwtzm, CommonConstantUtils.BHTZM_FW)) {
            tzm = CommonConstantUtils.BHTZM_FW;
        } else if (StringUtils.equals(dzwtzm, CommonConstantUtils.DZWTZM_TD)) {
            tzm = CommonConstantUtils.BHTZM_TD;
            if (StringUtils.equals(CommonConstantUtils.QSLXDM_H, qslxdm) || StringUtils.contains(CommonConstantUtils.QSLXDM_G, qslxdm)) {
                //海域
                tzm = CommonConstantUtils.BHTZM_HY;
            } else if (StringUtils.equals(CommonConstantUtils.QSLXDM_L, qslxdm)) {
                //林权
                tzm = CommonConstantUtils.BHTZM_LQ;
            }

        } else if (StringUtils.equals(dzwtzm, CommonConstantUtils.BHTZM_LQ)) {
            tzm = CommonConstantUtils.BHTZM_LQ;
        } else {
            tzm = CommonConstantUtils.BHTZM_FW;
        }
        return tzm;
    }

    /**
     * @param slbh 不动产单元号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理编号是否包含特征码
     */
    public static boolean isContainSlbhTzm(String slbh){
        if(StringUtils.isBlank(slbh)){
            return false;
        }
        for(String tzm: CommonConstantUtils.SLBH_TZM){
            if(slbh.startsWith(tzm)){
                return true;
            }
        }
        return false;

    }
    /**
     * @param bdcdyh 28位不动产单元号
     * @param lx 权籍表类型
     * @return 不动产类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元获取不动产类型
     */
    public static String queryBdclxByBdcdyh(String bdcdyh, String lx) {
        String bdclx = "";
        if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
            String zdtzm = bdcdyh.substring(13, 14);
            String dzwtzm = bdcdyh.substring(19, 20);
            switch (dzwtzm) {
                case "W":
                    if (StringUtils.equals(CommonConstantUtils.ZDTZM_H, zdtzm)) {
                        bdclx = "5";
                        break;
                    } else if (StringUtils.equals(CommonConstantUtils.ZDTZM_G, zdtzm)) {
                        bdclx = "13";
                        break;
                    } else {
                        bdclx = "1";
                        break;
                    }
                case "F":
                    if (StringUtils.equals(CommonConstantUtils.LX_GZW, lx)) {
                        if (StringUtils.equals(CommonConstantUtils.ZDTZM_H, zdtzm) || StringUtils.equals(CommonConstantUtils.ZDTZM_G, zdtzm)){
                            bdclx = "6";
                        }else {
                            bdclx = "8";
                        }
                        break;
                    } else if (StringUtils.equals(CommonConstantUtils.ZDTZM_H, zdtzm) || StringUtils.equals(CommonConstantUtils.ZDTZM_G, zdtzm)) {
                        bdclx = "10";
                        break;
                    }else if (!StringUtils.equals(CommonConstantUtils.LX_YCHS, lx)) {
                        bdclx = "2";
                        break;
                    }  else {
                        bdclx = "2/4";
                        break;
                    }
                case "L":
                    if (StringUtils.equals(CommonConstantUtils.ZDTZM_H, zdtzm) || StringUtils.equals(CommonConstantUtils.ZDTZM_G, zdtzm)) {
                        bdclx = "11";
                        break;

                    } else {
                        bdclx = "3";
                        break;
                    }
                case "Q":
                    if (StringUtils.equals(CommonConstantUtils.ZDTZM_H, zdtzm) || StringUtils.equals(CommonConstantUtils.ZDTZM_G, zdtzm)) {
                        bdclx = "12";
                        break;
                    } else {
                        bdclx = "7/9";
                        break;
                    }
                default:
                    break;
            }

        }
        return bdclx;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.Boolean
     * @description 判断 BDCDYH 是否是虚拟的BDCDYH
     */
    public static boolean checkXnbdcdyh(String bdcdyh) {
        boolean isXn = false;
        if(StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28){
            String zdzhsxh = StringUtils.substring(bdcdyh, 6, 12);
            String qslxdm =StringUtils.substring(bdcdyh, 13, 14);
            //虚拟单元号
            if (StringUtils.equals(zdzhsxh, CommonConstantUtils.ZDZHSXH_XN) && !ArrayUtils.contains(CommonConstantUtils.ZH_TZM,qslxdm)) {
                isXn = true;
            }
        }
        return isXn;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {boolean} true : 是 ； false：不是
     * @description 判断 BDCDYH 是否是宅基地不动产单元号
     */
    public static boolean checkBdcdyhIsZjd(String bdcdyh) {
        if(StringUtils.isBlank(bdcdyh) || bdcdyh.length() < 14) {
            return false;
        }

        //宅基地 JC 标志在第13、14位 : 340104404006JC00058F00010201
        String zjddm = StringUtils.substring(bdcdyh, 12, 14);
        if(StringUtils.equals(CommonConstantUtils.ZJDTZM_JC, zjddm)) {
            return true;
        }
        return false;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {boolean} true : 是 ； false：不是
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  判断bdcdyh为虚拟量化单元号
     */
    public static boolean checkXnLhbdcdyh(String bdcdyh) {
        boolean isXnLh = false;
        if(StringUtils.isNotBlank(bdcdyh) && StringUtils.length(bdcdyh) == CommonConstantUtils.BDCDYH_LENGTH
                && bdcdyh.endsWith(CommonConstantUtils.SUFFIX_XNLH_BDCDYH)){
            isXnLh =true;
        }
        return isXnLh;

    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {boolean} true : 是 ； false：不是
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  判断bdcdyh为宗地单元号
     */
    public static boolean checkZdBdcdyh(String bdcdyh){
        boolean isZd = false;
        if(StringUtils.isNotBlank(bdcdyh) && StringUtils.length(bdcdyh) == CommonConstantUtils.BDCDYH_LENGTH
                && bdcdyh.endsWith(CommonConstantUtils.SUFFIX_ZD_BDCDYH)){
            isZd = true;
        }
        return isZd;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  bdcdywybh为空时通过一定规则生成bdcdywybh
      */
    public static String dealBdcdywybh(String bdcdyh){
        String bdcdywybh ="";
        // 虚拟单元号是F和L的生成bdcdywybh
        Boolean isInZdlxSource = judgeZdlxInSourceByBdcdyh(bdcdyh,
                new String[]{CommonConstantUtils.BDCLX_TZM_F, CommonConstantUtils.BDCLX_TZM_L});
        //虚拟单元按公式生成
        if (isInZdlxSource) {
            // 1.替换不动产单元号特征码
            bdcdyh=ReplaceTzm(bdcdyh);
            StringBuffer wybh=new StringBuffer();
            // 2.截取19位
            wybh.append(bdcdyh.substring(0,19));
            // 3.将1的内容先右取8位，再左取4位，不足补0。
            wybh.append(bdcdyh.substring(bdcdyh.length() - 8).substring(0, 4));
            // 4.将1的内容右取4位，不足补0。
            wybh.append(bdcdyh.substring(bdcdyh.length() - 4));
            // 6.循环处理4的内容，获取房屋编码最后一位
            wybh.append(GetFwbmLastPart(wybh.toString()));

            bdcdywybh =wybh.toString();
        }
        return bdcdywybh;

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 判断不动产单元号的宗地类型
      */
    private static Boolean judgeZdlxInSourceByBdcdyh(String bdcdyh, String[] zdlxSource) {
        Boolean isInZdlxSource = false;
        if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 20) {
            String dzwTzm = bdcdyh.substring(19, 20);
            if (CommonUtil.indexOfStrs(zdlxSource,dzwTzm)) {
                isInZdlxSource = true;
            }
        }
        return isInZdlxSource;
    }

    /**
     * 替换不动产单元号特征码
     * @param bdcdyh
     * @return
     */
    private static String ReplaceTzm(String bdcdyh){
        bdcdyh=bdcdyh.replaceAll("G", "0");
        bdcdyh=bdcdyh.replaceAll("J", "1");
        bdcdyh=bdcdyh.replaceAll("Z", "2");
        bdcdyh=bdcdyh.replaceAll("A", "0");
        bdcdyh=bdcdyh.replaceAll("B", "1");
        bdcdyh=bdcdyh.replaceAll("C", "2");
        bdcdyh=bdcdyh.replaceAll("D", "3");
        bdcdyh=bdcdyh.replaceAll("E", "4");
        bdcdyh=bdcdyh.replaceAll("F", "5");
        bdcdyh=bdcdyh.replaceAll("S", "6");
        bdcdyh=bdcdyh.replaceAll("X", "7");
        bdcdyh=bdcdyh.replaceAll("W", "8");
        bdcdyh=bdcdyh.replaceAll("Y", "9");
        return bdcdyh;
    }

    /**
     * 获取房屋编码最后一位
     * @param fwmbFirstPart
     * @return
     */
    private static String GetFwbmLastPart(String fwmbFirstPart){
        String sFwbmLastPart = StringUtils.EMPTY;
        // 循环截取i++位数，然后将取出的数再右取1位得到值，该数值再加上上一次循环得到的结果，除以10取余再乘2，所得的结果再除以11取余
        int iResultTmp = 10;
        for (int i = 1; i <= fwmbFirstPart.length(); i++) {
            // 1.左取I位
            String sSub = fwmbFirstPart.substring(0, i);
            // 2.在1的结果上右取1位
            sSub = sSub.substring(sSub.length() - 1);
            // 3.在2的结果上加上上次循环得到的结果，第一次则加10
            int iSub= NumberUtils.toInt(sSub);
            iSub = iSub + iResultTmp;
            // 4.在3的结果上除以10取余再乘2,所得的结果再除以11取余
            iResultTmp = ((iSub % 10) * 2) % 11;
        }
        // 房屋编码最后一位的验证规则  IF(AE2=0,1-AE2,IF(AE2>1,11-AE2,1-AE2)) 其中，AE2为循环得到的结果。
        sFwbmLastPart = String.valueOf(iResultTmp > 1 ? 11 - iResultTmp : 1 - iResultTmp);
        return sFwbmLastPart;
    }

    /**
     * 截取不动产单元号前6位
     * @param bdcdyh 不动产单元号
     * @return {String} 单元号前6位
     */
    public static String subPreSixBdcdyh(String bdcdyh) {
        if(StringUtil.isBlank(bdcdyh)) {
            return "";
        }
        return bdcdyh.substring(0, 6);
    }

    /**
     * 获取不动产单元号中乡镇代码 第7到9位
     * @param bdcdyh
     * @return
     */
    public static String getXzdm(String bdcdyh) {
        if(StringUtil.isBlank(bdcdyh)) {
            return "";
        }
        return bdcdyh.substring(6, 9);
    }
}
