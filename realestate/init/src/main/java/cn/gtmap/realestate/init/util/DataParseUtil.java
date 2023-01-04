package cn.gtmap.realestate.init.util;

import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.util.SqlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/11.
 * @description
 */

public class DataParseUtil {

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
     * 批量更新使用 将更新对象转成执行的sql需要的map
     * @return
     */
    public static Map bdcDjxxUpdateQOParseSqlMap(BdcDjxxUpdateQO bdcDjxxUpdateQO,Class c){
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(bdcDjxxUpdateQO.getJsonStr());
        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, c.getName());
        if (!statement.contains("SET")) {
            return null;
        }
        map.put("statement", statement);
        //where 条件
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //获取实体对象
        Object object = JSON.parseObject(bdcDjxxUpdateQO.getJsonStr(), c);
        map.put("record", object);
        return map;
    }
}
