package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.common.core.domain.building.CbzdDcbDO;
import cn.gtmap.realestate.common.core.domain.building.JyqDkDcbDO;
import cn.gtmap.realestate.common.core.domain.building.LqDcbDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-15
 * @description
 */
public class TzmUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TzmUtils.class);

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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.Class[]
     * @description 根据特征码获取实体数组
     */
    public static Class[] getDOArrByBdcdyh(String bdcdyh){
        TzmEnum tzmEnum = getTzmByTzmdm(getZdAndDzwTzmWithBdcdyh(bdcdyh));
        if(tzmEnum != null){
            return tzmEnum.getDoArr();
        }
        return new Class[0];
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.Class
     * @description 根据BDCDYH获取地籍调查表
     */
    public static Class getDjdcbDoWithBdcdyh(String bdcdyh){
        TzmEnum tzmEnum = getTzmByTzmdm(getZdAndDzwTzmWithBdcdyh(bdcdyh));
        if(tzmEnum != null){
            Class[] classarr = tzmEnum.getDoArr();
            return classarr[0];
        }
        return null;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param tzm
     * @return java.lang.Class
     * @description 根据拼接特征码（宗地特征码第二位 + 定着物特征码） 获取地籍信息实体
     */
    public static Class getDjdcbDoWithTzm(String tzm){
        TzmEnum tzmEnum = getTzmByTzmdm(tzm);
        if(tzmEnum != null){
            Class[] classarr = tzmEnum.getDoArr();
            return classarr[0];
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param tzm
     * @return java.lang.Class
     * @description 根据拼接特征码（宗地特征码第二位 + 定着物特征码） 获取地籍信息处理Service
     */
    public static String[] getBeanNameWithTzm(String tzm){
        TzmEnum tzmEnum = getTzmByTzmdm(tzm);
        if(tzmEnum != null){
            return tzmEnum.getDjxxServiceBeanName();
        }
        return new String[0];
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.String
     * @description 根据不动产单元号 获取 处理服务 BEAN name
     */
    public static String[] getBeanNameByBdcdyh(String bdcdyh){
        TzmEnum tzmEnum = getTzmByTzmdm(getZdAndDzwTzmWithBdcdyh(bdcdyh));
        if(tzmEnum != null){
            return tzmEnum.getDjxxServiceBeanName();
        }
        return new String[0];
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param tzmdm
     * @return cn.gtmap.realestate.building.util.TzmEnum
     * @description 根据特征码 获取枚举
     */
    public static TzmEnum getTzmByTzmdm(String tzmdm){
        TzmEnum tzmEnum = null;
        try {
            tzmEnum = TzmEnum.valueOf(tzmdm);
        }catch (Exception e){
            LOGGER.error("找不到宗地特征码对应的地籍枚举值:{}",tzmdm);
        }
        return tzmEnum;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 根据BDCDYH 查询承包宗
     */
    public static boolean hasCbzdDcb(String bdcdyh){
        Class[] classes = getDOArrByBdcdyh(bdcdyh);
        if(classes.length > 0){
            for(Class temp : classes){
                if(temp.equals(CbzdDcbDO.class)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 
     */
    public static boolean hasLqDcb(String bdcdyh){
        Class[] classes = getDOArrByBdcdyh(bdcdyh);
        if(classes.length > 0){
            for(Class temp : classes){
                if(temp.equals(LqDcbDO.class)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据BDCDYH 查询经营地块
     */
    public static boolean hasJyqdkDcb(String bdcdyh){
        Class[] classes = getDOArrByBdcdyh(bdcdyh);
        if(classes.length > 0){
            for(Class temp : classes){
                if(temp.equals(JyqDkDcbDO.class)){
                    return true;
                }
            }
        }
        return false;
    }


}
