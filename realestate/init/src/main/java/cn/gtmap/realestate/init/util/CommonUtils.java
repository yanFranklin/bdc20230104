package cn.gtmap.realestate.init.util;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/11.
 * @description
 */

public class CommonUtils {

    /**
     * String 转int 当字符串为空时 返回0
     * @param s
     * @return
     */
    public static int stringParseIntDealNull(String s){
        int result = 0;
        if(StringUtils.isNotBlank(s)){
            result = Integer.parseInt(s);
        }
        return result;
    }
    /**
    * @author chenchunxue 2020/9/7
    * @param initServiceQO
    * @return boolean
    * @description 判断是否查询权籍数据 常州qlsjly配置1才查询 其他情况不查询
    */
    public static boolean sfcxQj(InitServiceQO initServiceQO){
        boolean sfcxqj = true;
        // 注销流程，不生成权利，并且不读取 lpb 数据，不查询权籍数据
        if (null != initServiceQO.getBdcCshFwkgSl() && CommonConstantUtils.SF_F_DM.equals(initServiceQO.getBdcCshFwkgSl().getSfscql())
                && !StringUtils.equals(initServiceQO.getBdcCshFwkgSl().getQlsjly(), Constants.QLSJLY_LPB)) {
            sfcxqj = false;
        }
        return sfcxqj;
    }
    /**
    * @author chenchunxue 2020/9/11
    * @param sourceQllxs
    * @param destObject
    * @return boolean
    * @description 判断destObject中的qllx是否在sourceQllxs中
    */
    public static boolean isSameQllx(String[] sourceQllxs,Object destObject){
        BdcXmDO bdcXmDO = JSONObject.parseObject(JSONObject.toJSONString(destObject), BdcXmDO.class);
        if(bdcXmDO!=null && CommonUtil.indexOfStrs(Constants.PFM_ZH_M,bdcXmDO.getQllx().toString())){
            return true;
        }
        return false;
    }
}
