package cn.gtmap.realestate.common.core.cache;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.BdcMapZdConvertDTO;
import cn.gtmap.realestate.common.core.service.BdcZdGlService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hy</a>
 * @version 1.0, 2018/11/7
 * @description 对于不动产登记中需要用到的字典项进行缓存
 */
@Service
public class BdcZdCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZdCache.class);

    private static final Map<String, List> zdCacheMap = new HashMap(100);

    public static final List<BdcMapZdConvertDTO> defaultConvertVoList = new ArrayList(50);

    @Autowired
    BdcZdGlService bdcZdGlService;

    /**
     * 手动对字典缓存赋值
     *
     * @param fieldName  对应的字段名
     * @param cacheTable 要缓存的字典名
     * @param cacheData  要缓存的数据
     * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    public void addZdCacheMap(String fieldName, String cacheTable, List<Map<String, Object>> cacheData) {
        if (CollectionUtils.isNotEmpty(cacheData) && StringUtils.isNotBlank(cacheTable)) {
            cacheTable = StringUtils.lowerCase(cacheTable);
            zdCacheMap.put(cacheTable, cacheData);
            defaultConvertVoList.add(new BdcMapZdConvertDTO(StringUtils.upperCase(fieldName), StringUtils.upperCase(cacheTable)));
        }
    }

    /**
     * 用来刷新字典缓存map中的缓存 有两种情况1.之前没有，新增 2.之前有，更新
     *
     * @param cacheTable 要缓存的字典表名
     */
    public void refreshZdCacheMap(String cacheTable, Class cacheClass) {
        if (StringUtils.isNotBlank(cacheTable)) {
            //表名全部转换为小写
            cacheTable = StringUtils.lowerCase(cacheTable);
            try {
                //先根据表名获取到这个表的字典list
                List<HashMap> zdList = bdcZdGlService.getZdTableData(cacheClass);
                if (CollectionUtils.isNotEmpty(zdList)) {
                    zdCacheMap.put(cacheTable, zdList);
                }
            } catch (Exception e) {
                LOGGER.error("插入字典缓存时报错", e);
            }
        }
    }

    /**
     * 得到缓存。同步静态方法
     *
     * @param key
     * @return
     */
    private static List<Map> getCache(String key) {
        return zdCacheMap.get(key);
    }

    /**
     * 判断是否存在一个缓存
     *
     * @param key
     * @return
     */
    private static boolean hasCache(String key) {
        return zdCacheMap.containsKey(key);
    }

    /**
     * 获得某个字典表的取值列表
     *
     * @param cacheTable 表名
     * @return
     */
    public List<Map> getZdTableList(String cacheTable, Class cacheClass) {
        List<Map> zdList = new ArrayList<>(0);
        if (StringUtils.isNotBlank(cacheTable)) {
            //表名全部转换为小写
            cacheTable = StringUtils.lowerCase(cacheTable);
            if (!hasCache(cacheTable)) {
                refreshZdCacheMap(cacheTable, cacheClass);
            }
            zdList = getCache(cacheTable);
            if(CollectionUtils.isEmpty(zdList)){
                zdList =new ArrayList<>(0);
            }
        }
        return zdList;
    }

    /**
     * 通过字典中的一个字段值获取另一个字段的值 不区分大小写
     *
     * @param cacheTable      表名
     * @param returnFieldName 需要获取的字段名称
     * @param getFieldName    用来获取的字段名称
     * @param getFeildValue   用来获取的字段值
     * @return
     */
    public Object getFeildValue(String cacheTable, Object getFeildValue, String returnFieldName, String getFieldName, Class cacheClass) {
        Object returnFieldValue = null;
        if (StringUtils.isNoneBlank(cacheTable, returnFieldName, getFieldName) && getFeildValue != null) {
            List<Map> zdList = getZdTableList(cacheTable, cacheClass);
            //转换字段名称都为大写，因为在数据库中查询出来的是map 都是大写的
            returnFieldName = StringUtils.upperCase(returnFieldName);
            getFieldName = StringUtils.upperCase(getFieldName);
            //循环list找出对应的那条数据
            if (CollectionUtils.isNotEmpty(zdList)) {
                for (Map zdMap : zdList) {
                    if (MapUtils.getObject(zdMap, getFieldName)!=null && StringUtils.equals(getFeildValue.toString(),MapUtils.getObject(zdMap, getFieldName).toString())) {
                        returnFieldValue = MapUtils.getObject(zdMap, returnFieldName);
                        break;
                    }
                }
            }
        }
        return returnFieldValue;
    }

    /**
     * 通过字典中的一个字段值获取另一个字段的值 不区分大小写
     *
     * @param cacheTable      表名
     * @param returnFieldName 需要获取的字段名称
     * @param getFeildValue   用来获取的字段值
     * @return
     */
    public Object getFeildValue(String cacheTable, Object getFeildValue, String returnFieldName, Class cacheClass) {
        return getFeildValue(cacheTable, getFeildValue, returnFieldName, "DM", cacheClass);
    }

    /**
     * 通过字典中的一个字段值获取另一个字段的值 不区分大小写
     *
     * @param cacheTable    表名
     * @param getFeildValue 用来获取的字段值
     * @return String 因为默认是名称代码 所以直接返回string类型
     */
    public String getFeildValue(String cacheTable, Object getFeildValue, Class cacheClass) {
        Object returnFieldValue = getFeildValue(cacheTable, getFeildValue, "MC", "DM", cacheClass);
        if (returnFieldValue != null) {
            return returnFieldValue.toString();
        }
        return "";
    }

    /**
     * 默认转换的map字典项
     */
    @PostConstruct
    private static void initConvertVoList() {
        // 登记类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("DJLX", "BDC_ZD_DJLX", BdcZdDjlxDO.class));
        // 证件种类
        defaultConvertVoList.add(new BdcMapZdConvertDTO("ZJZL", "BDC_ZD_ZJZL", BdcZdZjzlDO.class));
        // 面积单位
        defaultConvertVoList.add(new BdcMapZdConvertDTO("MJDW", "BDC_ZD_MJDW", BdcZdMjdwDO.class));
        // 权利人类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("QLRLX", "BDC_ZD_QLRLX", BdcZdQlrlxDO.class));
        // 房屋类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("FWLX", "BDC_ZD_FWLX", BdcZdFwlxDO.class));
        // 房屋性质
        defaultConvertVoList.add(new BdcMapZdConvertDTO("FWXZ", "BDC_ZD_FWXZ", BdcZdFwxzDO.class));
        // 房屋结构
        defaultConvertVoList.add(new BdcMapZdConvertDTO("FWJG", "BDC_ZD_FWJG", BdcZdFwjgDO.class));
        // 房屋用途
        defaultConvertVoList.add(new BdcMapZdConvertDTO("FWYT", "BDC_ZD_FWYT", BdcZdFwytDO.class));
        // 构(建)筑物类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("GJZWLX", "BDC_ZD_GJZWLX", BdcZdGjzwlxDO.class));
        // 养殖业方式
        defaultConvertVoList.add(new BdcMapZdConvertDTO("YZYFS", "BDC_ZD_YZYFS", BdcZdYzyfsDO.class));
        // 土地所有权性质
        defaultConvertVoList.add(new BdcMapZdConvertDTO("TDSYQXZ", "BDC_ZD_TDSYQXZ", BdcZdTdsyqxzDO.class));
        // 水域滩涂类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("SYTTLX", "BDC_ZD_SYTTLX", BdcZdSyttlxDO.class));
        // 林种
        defaultConvertVoList.add(new BdcMapZdConvertDTO("LZ", "BDC_ZD_LZ", BdcZdLzDO.class));
        // 起源
        defaultConvertVoList.add(new BdcMapZdConvertDTO("QY", "BDC_ZD_QY", BdcZdQyDO.class));
        // 抵押方式
        defaultConvertVoList.add(new BdcMapZdConvertDTO("DYFS", "BDC_ZD_DYFS", BdcZdDyfsDO.class));
        // 抵押不动产类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("DYBDCLX", "BDC_ZD_DYBDCLX", BdcZdDybdclxDO.class));
        // 预告登记种类
        defaultConvertVoList.add(new BdcMapZdConvertDTO("YGDJZL", "BDC_ZD_YGDJZL", BdcZdYgdjzlDO.class));
        // 权属状态
        defaultConvertVoList.add(new BdcMapZdConvertDTO("QSZT", "BDC_ZD_QSZT", BdcZdQsztDO.class));
        // 查封类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("CFLX", "BDC_ZD_CFLX", BdcZdCflxDO.class));
        // 权利类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("QLLX", "BDC_ZD_QLLX", BdcZdQllxDO.class));
        //地段级别字典表
        defaultConvertVoList.add(new BdcMapZdConvertDTO("DDJB", "BDC_ZD_DDJB", BdcZdDdjbDO.class));

        //查档类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("CDLX", "BDC_ZD_CDLX", BdcZdCdlxDO.class));
        //出具证明类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("CJZMLX", "BDC_ZD_CJZMLX", BdcZdCjzmlxDO.class));
        //取得方式
        defaultConvertVoList.add(new BdcMapZdConvertDTO("QDFS", "BDC_ZD_QDFS", BdcZdQdfsDO.class));
        //区县代码
        defaultConvertVoList.add(new BdcMapZdConvertDTO("QX", "BDC_ZD_QX", BdcZdQxDO.class));
        // 币种
        defaultConvertVoList.add(new BdcMapZdConvertDTO("BIZ", "BDC_ZD_BIZ", BdcZdBizDO.class));
        // 国籍
        defaultConvertVoList.add(new BdcMapZdConvertDTO("GJ", "BDC_ZD_GJ", BdcZdGjDO.class));
        // 中编办事业单位业务类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("ZBBSYDWYWLX", "BDC_ZD_ZBBSYDWYWLX", BDCZdZbbsydwywlxDO.class));
        // 金融许可证查询类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("JRXKZCXLX", "BDC_ZD_JRXKZCXLX", BdcZdJrxkzcxlxDO.class));
        // 审批来源
        defaultConvertVoList.add(new BdcMapZdConvertDTO("SPLY", "BDC_ZD_SPLY", BdcZdSplyDO.class));
        //发票类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("FPLX","BDC_ZD_FPLX",BdcZdFplxDO.class));
        //收费信息备注
        defaultConvertVoList.add(new BdcMapZdConvertDTO("SFXXBZ","BDC_ZD_SFXXBZ",BdcZdSfxxbzDO.class));

        //查询目的或用途
        defaultConvertVoList.add(new BdcMapZdConvertDTO("CXMDHYT","BDC_ZD_CXMDHYT",BdcZdCxmdhytDO.class));

        //登记簿记载信息
        defaultConvertVoList.add(new BdcMapZdConvertDTO("DJBJZXX","BDC_ZD_DJBJZXX",BdcZdDjbjzxxDO.class));

        //登记簿记载信息
        defaultConvertVoList.add(new BdcMapZdConvertDTO("DJYSPZ","BDC_ZD_DJYSPZ",BdcZdDjyspzDO.class));

        //查询结果要求
        defaultConvertVoList.add(new BdcMapZdConvertDTO("CXJGYQ","BDC_ZD_CXJGYQ",BdcZdCxjgyqDO.class));

        //修改原因
        defaultConvertVoList.add(new BdcMapZdConvertDTO("XGYY","BDC_ZD_XGYY",BdcZdXgyyDO.class));
        // 补录方式
        defaultConvertVoList.add(new BdcMapZdConvertDTO("BLFS","BDC_ZD_BLFS",BdcZdBlfsDO.class));
        // 权利人数据来源
        defaultConvertVoList.add(new BdcMapZdConvertDTO("QLRLY","BDC_ZD_QLRLY",BdcZdQlrlyDO.class));
        // 操作日志操作状态
        defaultConvertVoList.add(new BdcMapZdConvertDTO("CZRZCZZT", "BDC_ZD_CZRZCZZT", BdcZdCzrzczztDO.class));
        // 操作日志操作类型
        defaultConvertVoList.add(new BdcMapZdConvertDTO("CZRZCZLX", "BDC_ZD_CZRZCZLX", BdcZdCzrzczlxDO.class));
    }
}
