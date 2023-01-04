package cn.gtmap.realestate.common.core.service.Impl;

import cn.gtmap.realestate.common.config.ZdpxConfig;
import cn.gtmap.realestate.common.core.dto.config.BdcZdChangeDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.BdcZdGlService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/7
 * @description 字典数据操作
 */
@Service
public class BdcZdGlServiceImpl implements BdcZdGlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZdGlServiceImpl.class);
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private ZdpxConfig zdpxConfig;
    /**
     * 字典排序规则
     */
    @Value("${zd.zdpxgz:}")
    private String zdpxgz;

    @Override
    public List<HashMap> getZdTableData(Class zdClass) {
        if (zdClass != null) {
            try {
                Example example = new Example(zdClass);
                Field[] zdfields = zdClass.getDeclaredFields();

                // 字典表包含顺序号标志
                boolean sxhFlag = false;
                for (Field field : zdfields) {
                    field.setAccessible(true);
                    if ("SXH".equals(StringUtils.upperCase(field.getName()))) {
                        sxhFlag = true;
                        break;
                    }
                }
                // 组装排序规则start
                // 单表规则
                String dbgz = null;
                // 获取各表的排序规则map
                Map<String, String> dbpxgzmap = zdpxConfig.getDbpxgzmap();
                if (MapUtils.isNotEmpty(dbpxgzmap)) {
                    Table table = (Table)zdClass.getAnnotation(Table.class);
                    if (null != table) {
                        String tableName = StringUtils.upperCase(table.name());
                        String dbpxgz = dbpxgzmap.get(tableName);
                        if (StringUtils.isNotBlank(dbpxgz)) {
                            dbgz = dbpxgz;
                        }
                    }
                }
                String pxgz = null;
                // 包含顺序号时用顺序号、拼接配置的规则
                if (sxhFlag) {
                    pxgz = "sxh asc";
                    if (StringUtils.isNotBlank(dbgz)) {
                        pxgz = pxgz + "," + dbgz;
                    }
                    else if (StringUtils.isNotBlank(zdpxgz)) {
                        pxgz = pxgz + "," + zdpxgz;
                    }
                }
                // 无顺序号取单表规则
                else {
                    if (StringUtils.isNotBlank(dbgz)) {
                        pxgz = dbgz;
                    }
                }
                // 组装排序规则end
                if (StringUtils.isNotBlank(pxgz)) {
                    example.setOrderByClause(pxgz);
                }

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
     * 新增字典数据
     *
     * @param zdClass        字典Class
     * @param bdcZdChangeDTO
     * @return 字典数据
     */
    @Override
    public void addZdTableData(Class zdClass, BdcZdChangeDTO bdcZdChangeDTO) {
        if (zdClass != null) {
            try {
                Object newZdItem = zdClass.newInstance();
                Field mc = zdClass.getDeclaredField("mc");
                mc.setAccessible(true);
                mc.set(newZdItem,bdcZdChangeDTO.getMc());

                Field dm = zdClass.getDeclaredField("dm");
                dm.setAccessible(true);
                dm.set(newZdItem,bdcZdChangeDTO.getDm());
                entityMapper.insertSelective(newZdItem);

            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 编辑字典数据
     *
     * @param zdClass        字典Class
     * @param bdcZdChangeDTO
     * @return 字典数据
     */
    @Override
    public void editZdTableData(Class zdClass, BdcZdChangeDTO bdcZdChangeDTO) {
        if (zdClass != null) {
            try {
                delZdTableData(zdClass,bdcZdChangeDTO);
                addZdTableData(zdClass,bdcZdChangeDTO);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 删除字典数据
     *
     * @param zdClass        字典Class
     * @param bdcZdChangeDTO
     * @return 字典数据
     */
    @Override
    public void delZdTableData(Class zdClass, BdcZdChangeDTO bdcZdChangeDTO) {
        if (zdClass != null) {
            try {
                Example example = new Example(zdClass);
                example.createCriteria().andEqualTo("dm",bdcZdChangeDTO.getOrgdm());
                entityMapper.deleteByExample(example);

            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
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
