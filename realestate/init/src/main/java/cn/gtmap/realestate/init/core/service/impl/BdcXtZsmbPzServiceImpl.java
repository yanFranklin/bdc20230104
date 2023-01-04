package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.service.BdcXtZsmbPzService;
import cn.gtmap.realestate.init.mapper.RunSqlMapper;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzMbService;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/14.
 * @description
 */
@Service
public class BdcXtZsmbPzServiceImpl implements BdcXtZsmbPzService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private RunSqlMapper runSqlMapper;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private InitBeanFactory initBeanFactory;

    /**
     * 获取证书相关模板
     *
     * @param qllx
     * @return
     */
    @Override
    public BdcXtZsmbPzDO queryBdcXtZsmb(Integer qllx) {
        BdcXtZsmbPzDO bdcXtZsmbPzDO=null;
        if(qllx!=null){
            Example example=new Example(BdcXtZsmbPzDO.class);
            example.createCriteria().andEqualTo("qllx",qllx);
            List<BdcXtZsmbPzDO> list=entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(list)){
                bdcXtZsmbPzDO=list.get(0);
            }
        }
        return bdcXtZsmbPzDO;
    }


    /**
     * 获取证书相关模板内容
     *
     * @param bdcXmDO
     * @return
     */
    @Override
    public BdcZsDO queryZsmbContent(BdcXmDO bdcXmDO) {
        BdcZsDO bdcZsDO=null;
        if(bdcXmDO!=null && bdcXmDO.getQllx()!=null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            //走代码服务
            Map<Integer, InitBdcqzMbService> mapZsMbServices=initBeanFactory.getZsMbServices();
            if(MapUtils.isNotEmpty(mapZsMbServices)){
                if(mapZsMbServices.containsKey(bdcXmDO.getQllx())){
                    InitBdcqzMbService initBdcqzMbService= mapZsMbServices.get(bdcXmDO.getQllx());
                    bdcZsDO=initBdcqzMbService.queryZsMbServiceData(bdcXmDO);
                }
            }
            //查询sql配置服务
            BdcXtZsmbPzDO bdcXtZsmbPzDO=queryBdcXtZsmb(bdcXmDO.getQllx());
            if (bdcXtZsmbPzDO!=null && StringUtils.isNotBlank(bdcXtZsmbPzDO.getMbsql()) && StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                String[] sqls=bdcXtZsmbPzDO.getMbsql().split("；|;");
                for(String sql:sqls){
                    if(StringUtils.isNotBlank(sql)){
                        Map<String, String> map = new HashMap();
                        map.put("sql", sql);
                        map.put("xmid", bdcXmDO.getXmid());
                        List<HashMap> list=runSqlMapper.runSql(map);
                        if(CollectionUtils.isNotEmpty(list)){
                            //存储配置查询的数据
                            HashMap<String,String> sourceMap=new HashMap<>();
                            HashMap<String,String> contentMap=list.get(0);
                            if(MapUtils.isNotEmpty(contentMap)){
                                //将key转成小写
                                for(Map.Entry<String,String> entry:contentMap.entrySet()){
                                    sourceMap.put(entry.getKey().toLowerCase(),entry.getValue());
                                }
                            }
                            //空的话创建对象
                            if(bdcZsDO==null){
                                bdcZsDO=new BdcZsDO();
                            }
                            //对照到证书对象中
                            dozerUtils.sameBeanDateConvert(sourceMap,bdcZsDO,false);
                        }
                    }
                }
            }
        }
        return bdcZsDO;
    }
}
