package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.enums.natural.ZrzyZdEnum;
import cn.gtmap.realestate.natural.service.ZrzyZdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/23
 * @description 字典服务
 */
@Service
public class ZrzyZdServiceImpl implements ZrzyZdService {

    /**
     * 字典缓存
     */
    @Autowired
    private BdcZdCache bdcZdCache;

    /**
     * 全部字典项
     */
    public static final Map<String, List<Map>> zdMap = new ConcurrentHashMap<>(19);

    @Override
    public Map<String, List<Map>> listZrzyzd(){
        return zdMap;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 初始化所有字典项
      */
    @PostConstruct
    public void initZrzyZd(){

        //循环字典枚举值加入
        for (ZrzyZdEnum zrzyZdEnum : ZrzyZdEnum.values()) {
            putZdMapByZrzyZdEnum(zrzyZdEnum);
        }

    }

    /**
     * 根据枚举把字典表更新或插入进去
     *
     * @param zrzyZdEnum
     */
    private void putZdMapByZrzyZdEnum(ZrzyZdEnum zrzyZdEnum) {
        zdMap.put(StringUtils.lowerCase(zrzyZdEnum.name()), bdcZdCache.getZdTableList(zrzyZdEnum.getTableName(), zrzyZdEnum.getTableClass()));
    }


}
