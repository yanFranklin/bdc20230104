package cn.gtmap.realestate.etl.util;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-04-23
 * @description 打开处理失败数据定时任务开关
 */
public class QuartzUtil {

    public static boolean DISPOSE_FALSE_SWITCH = false;

    public static boolean isDisposeFalseSwitch() {
        return DISPOSE_FALSE_SWITCH;
    }

    public static void setDisposeFalseSwitch(boolean disposeFalseSwitch) {
        QuartzUtil.DISPOSE_FALSE_SWITCH = disposeFalseSwitch;
    }
}
