package cn.gtmap.realestate.building.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-11-15
 * @description
 */
public class DjdcbUtils {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.String
     * @description 根据BDCDYH查询特征码拼接字符
     */
    public static String getZdAndDzwTzmWithBdcdyh(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh)
                && bdcdyh.length() == 28){
            String zdtzm = bdcdyh.substring(13,14);
            String dzwtzm = bdcdyh.substring(19,20);
            return zdtzm + dzwtzm;
        }
        return "";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 判断是否是 纯土地的BDCDY
     */
    public static boolean isTdBdcdy(String bdcdyh){
        String dzwTzm = getDzwTzmByBdcdyh(bdcdyh);
        if(StringUtils.equals(Constants.ZDTZM_W,dzwTzm)){
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 验证是否为房屋的BDCDY
     */
    public static boolean isFwBdcdy(String bdcdyh){
        String dzwTzm = getDzwTzmByBdcdyh(bdcdyh);
        if(StringUtils.equals(Constants.ZDTZM_F,dzwTzm)){
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.String
     * @description 获取构筑物特征码
     */
    public static String getDzwTzmByBdcdyh(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh)
                && bdcdyh.length() == 28){
            return bdcdyh.substring(19,20);
        }
        return "";
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.lang.Class
     * @description 根据特征码获取宗地实体
     */
    public static String getTableNameByBdcdyh(String bdcdyh){
        DjdcbEnum djdcbEnum = DjdcbEnum.valueOf(getZdAndDzwTzmWithBdcdyh(bdcdyh));
        if(djdcbEnum != null){
            return djdcbEnum.getTableName();
        }
        return null;
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.lang.Class
     * @description 根据BDCDYH获取地籍调查表
     */
    public static Class getZdClassByBdcdyh(String bdcdyh){
        DjdcbEnum djdcbEnum = DjdcbEnum.valueOf(getZdAndDzwTzmWithBdcdyh(bdcdyh));
        if(djdcbEnum != null){
            Class zdClass = djdcbEnum.getZdClass();
            return zdClass;
        }
        return null;
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.lang.Class
     * @description 根据BDCDYH获取地籍调查表权利人
     */
    public static Class getZdQlrClassByBdcdyh(String bdcdyh){
        DjdcbEnum djdcbEnum = DjdcbEnum.valueOf(getZdAndDzwTzmWithBdcdyh(bdcdyh));
        if(djdcbEnum != null){
            Class zdClass = djdcbEnum.getZdQlrClass();
            return zdClass;
        }
        return null;
    }

}
