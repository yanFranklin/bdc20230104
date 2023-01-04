package cn.gtmap.realestate.exchange.core.service.impl;

import cn.gtmap.realestate.exchange.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.service.BdcZdGlService;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/7
 * @description 字典数据操作
 */
@Service
public class BdcZdGlServiceImpl implements BdcZdGlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZdGlServiceImpl.class);

    @Autowired
    @Qualifier("serverEntityMapper")
    private EntityMapper entityMapper;

    @Override
    public List<HashMap> getZdTableData(Class zdClass) {
        if (zdClass != null) {
            try {
                Example example = new Example(zdClass);
                List dataList = entityMapper.selectByExample(example);
                List<HashMap> mapList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (Object data : dataList) {
                        HashMap<String, Object> map = new HashMap<>();
                        Field[] fields = zdClass.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            map.put(StringUtils.upperCase(field.getName()), field.get(data));
                        }
                        mapList.add(map);
                    }
                }
                return mapList;
            } catch (IllegalAccessException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }

        return new ArrayList<HashMap>(0);
    }

    /**
     * @param zd 字典数据
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public int saveBdcZdTable(Object zd) {
        if (zd == null) {
            throw new AppException("保存的字典数据不能为空！");
        }
        return entityMapper.insertSelective(zd);
    }
}
