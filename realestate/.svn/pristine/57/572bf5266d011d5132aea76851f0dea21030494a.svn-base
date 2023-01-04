package cn.gtmap.realestate.register.util;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/12/5
 * @description 转换工具类
 */
public class ConvertUtils {

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param bdcdyh
    * @return
    * @description 将房屋的不动产单元转换为纯土地的不动产单元
    */
    public static String convertFToW(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
            StringBuilder sb = new StringBuilder(28).append(bdcdyh.substring(0, 19)).append(CommonConstantUtils.SUFFIX_ZD_BDCDYH);
            return sb.toString();
        }
        return "";
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
}
