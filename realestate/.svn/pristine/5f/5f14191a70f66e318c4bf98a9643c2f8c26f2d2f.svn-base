package cn.gtmap.realestate.exchange.web;

import cn.gtmap.realestate.exchange.core.cache.BdcZdCache;
import cn.gtmap.realestate.exchange.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.ex.ErrorCode;
import cn.gtmap.realestate.exchange.util.enums.BdcZdEnum;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/29
 * @description 不动产字典项服务
 */
@Controller
@Api(tags = "不动产字典项服务")
@RequestMapping("/rest/v1.0/bdczd")
public class BdcZdRestController extends BaseController {

    /**
     * 字典缓存
     */
    @Autowired
    private BdcZdCache bdcZdCache;

    /**
     * 全部字典项
     */
    public static final Map<String, List<Map>> zdMap = new ConcurrentHashMap<>(128);


    @RequestMapping("/mul")
    @ResponseBody
    public Map mulListZd(String zdNames) {
        if (StringUtils.isNotBlank(zdNames)) {
            String[] arr = zdNames.split(",");
            return getZdList(arr);
        }
        return new HashMap(0);
    }

    public Map getZdList(String[] zdNames) {
        Map<String, List<Map>> resultMap = new HashMap<>(zdNames.length);
        if (ArrayUtils.isNotEmpty(zdNames)) {
            for (String zdName : zdNames) {
                resultMap.put(zdName, this.queryBdcZd(zdName));
            }
        }
        return resultMap;
    }


    public Map<String, List<Map>> listBdcZd() {
        return zdMap;
    }

    public List<Map> queryBdcZd(@PathVariable(name = "zdmc") String zdmc) {
        if (StringUtils.isEmpty(zdmc)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数字典名称");
        }
        return zdMap.get(zdmc);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 初始化所有字典项
     */
    @PostConstruct
    public void initBdcZd() {
        //2020-7-31 sly 把所有字典改为枚举，循环加入，方便重用
        for (BdcZdEnum bdcZdEnum : BdcZdEnum.values()) {
            putZdMapByBdcZdEnum(bdcZdEnum);
        }
    }

    /**
     * 根据枚举吧字典表更新或插入进去
     *
     * @param bdcZdEnum
     */
    private void putZdMapByBdcZdEnum(BdcZdEnum bdcZdEnum) {
        zdMap.put(StringUtils.lowerCase(bdcZdEnum.name()), bdcZdCache.getZdTableList(bdcZdEnum.getTableName(), bdcZdEnum.getTableClass()));
    }


}
