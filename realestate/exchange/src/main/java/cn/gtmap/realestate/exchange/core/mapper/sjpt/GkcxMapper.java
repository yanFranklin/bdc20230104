package cn.gtmap.realestate.exchange.core.mapper.sjpt;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-24
 * @description 原 Analysis包  跟 有房无房查询相关查询SQL
 */
public interface GkcxMapper {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description 获取住房查询流水号
     */
    Integer getZfcxLsh();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.Integer
     * @description 
     */
    Integer getBdcxxLybh();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description
     */
    List<Map<String, Object>> getBdcXxcxbh(Map paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.Integer
     * @description
     */
    Integer getFwqsCxLsh();
    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param
     * @return java.lang.String
     * @description 获取查询机房屋权属查询流水号
     */
    Integer getCxjFwqscxLsh();
}
