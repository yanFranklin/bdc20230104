package cn.gtmap.realestate.common.util;


import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/2/28
 * @description 判断审批来源为一窗受理或互联网
 */
public class CheckWwsqOrYcslUtils {

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据审批来源判断是否为一窗受理
     */
    public static boolean checkIsYcsl(Integer sply) {
        boolean isYcsl = false;
        if (CommonConstantUtils.SPLY_YCSL.equals(sply)
                || CommonConstantUtils.SPLY_WWSQ_YCSL.equals(sply)
                || CommonConstantUtils.SPLY_YCTB_SS.equals(sply)) {
            isYcsl = true;

        }
        return isYcsl;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据审批来源判断是否为外网申请（互联网+）
     */
    public static boolean checkIsWwsq(Integer sply) {
        boolean isWwsq = false;
        if (CommonConstantUtils.SPLY_WWSQ.equals(sply)
                || CommonConstantUtils.SPLY_WWSQ_DETAIL.contains(sply)
                || CommonConstantUtils.SPLY_WWSQ_YCSL.equals(sply)) {
            isWwsq = true;

        }
        return isWwsq;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据审批来源判断是否为银行系统（互联网+）
     */
    public static boolean checkIsYhxt(Integer sply) {
        boolean isYhxt = false;
        if (CommonConstantUtils.SPLY_WWSQ_YHXT.equals(sply)) {
            isYhxt = true;

        }
        return isYhxt;
    }

    public static boolean checkIsWwsqBypz(Integer sply, Map<String, String> hlwMap) {
        boolean isWwsq = false;
        if (Objects.nonNull(sply)) {
            if (Objects.nonNull(hlwMap)) {
                for (String key : hlwMap.keySet()) {
                    if (key.equals(sply.toString())) {
                        isWwsq = true;
                    }
                }
            }
        }

        return isWwsq;
    }
}
