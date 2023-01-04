package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXxbdPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXxbdSjPzDO;
import cn.gtmap.realestate.common.core.dto.BdcXxbdDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcXxbdQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BuildingConfigFeignService;
import cn.gtmap.realestate.common.core.service.feign.etl.EtlConfigFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.RestRpcUtils;
import cn.gtmap.realestate.config.core.mapper.BdcSqlMapper;
import cn.gtmap.realestate.config.service.BdcXxbdPzService;
import cn.gtmap.realestate.config.service.BdcXxbdService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/27
 * @description 信息比对服务
 */
@Service
public class BdcXxbdServiceImpl implements BdcXxbdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXxbdServiceImpl.class);

    @Autowired
    BdcSqlMapper bdcSqlMapper;

    @Autowired
    EtlConfigFeignService etlConfigFeignService;

    @Autowired
    BuildingConfigFeignService buildingConfigFeignService;

    @Autowired
    BdcSlPrintFeignService bdcSlPrintFeignService;

    @Autowired
    private BdcXxbdPzService bdcXxbdPzService;

    @Autowired
    RestRpcUtils restRpcUtils;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  字段名称
     */
    private static final String ZDMC = "zdmc";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  字段值
     */
    private static final String ZDZ = "zdz";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否相等
     */
    private static final String SFXD = "sfxd";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  请求方式-1:SQL 2:服务
     */
    private static final String QQFS_SQL = "1";

    private static final String QQFS_FW = "2";



    @Override
    public BdcXxbdDTO generateXxbdDTO(BdcXxbdQO bdcXxbdQO){
        if(StringUtils.isBlank(bdcXxbdQO.getBdlx())){
            throw new AppException("信息比对未设置比对类型");
        }
        if(MapUtils.isEmpty(bdcXxbdQO.getParamMap())){
            throw new AppException("信息比对未获取查询参数");
        }
        //读取配置
        BdcXxbdPzDO bdcXxbdPzDO =bdcXxbdPzService.queryBdcXxbdPzByBdlx(bdcXxbdQO.getBdlx());
        if(bdcXxbdPzDO ==null){
            throw new AppException("信息比对,未获取比对配置表数据,比对类型为"+bdcXxbdQO.getBdlx());
        }
        List<BdcXxbdSjPzDO> bdcXxbdSjPzDOList = bdcXxbdPzService.listBdcXxbdPzByBdlx(bdcXxbdQO.getBdlx());
        if(CollectionUtils.isEmpty(bdcXxbdSjPzDOList)){
            throw new AppException("信息比对,未获取比对数据配置表数据,比对类型为"+bdcXxbdQO.getBdlx());
        }
        String bdzd =bdcXxbdPzDO.getBdzd();
        if(!CommonUtil.isJSONObject(bdzd)){
            throw new AppException("信息比对,比对配置表比对字段不是JSONObject结构,比对类型为"+bdcXxbdQO.getBdlx());
        }
        //获取比对字段
        LinkedHashMap<String,Object> bdzdMap = JSONObject.parseObject(bdzd,LinkedHashMap.class);
        //获取比对数据结果
        LinkedHashMap<String,Map<String,Object>> sjlyBdsjMap =new LinkedHashMap<>(bdcXxbdSjPzDOList.size());
        for(BdcXxbdSjPzDO bdcXxbdSjPzDO:bdcXxbdSjPzDOList){
            if(StringUtils.isBlank(bdcXxbdSjPzDO.getSjlymc())){
                throw new AppException("信息比对,比对数据配置表数据来源名称为空,比对类型为"+bdcXxbdQO.getBdlx());
            }
            //执行服务或SQL,获取数据
            Map<String,Object> bdsjMap =queryXxbdSj(bdcXxbdSjPzDO,bdcXxbdQO);
            sjlyBdsjMap.put(bdcXxbdSjPzDO.getSjlymc(),bdsjMap);
        }
        //组织数据结构
        return changeToBdcXxbdDTO(bdzdMap,sjlyBdsjMap);
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取信息比对数据
     */
    private Map<String,Object> queryXxbdSj(BdcXxbdSjPzDO bdcXxbdSjPzDO,BdcXxbdQO bdcXxbdQO){
        if(StringUtils.equals(QQFS_SQL,bdcXxbdSjPzDO.getQqfs())){
            //SQL
            return queryXxbdSjBySQL(bdcXxbdSjPzDO,bdcXxbdQO);
        }else if(StringUtils.equals(QQFS_FW,bdcXxbdSjPzDO.getQqfs())){
            //服务
            return queryXxbdSjByFw(bdcXxbdSjPzDO,bdcXxbdQO);
        }
        return null;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据SQL获取比对数据
     */
    private Map<String,Object> queryXxbdSjBySQL(BdcXxbdSjPzDO bdcXxbdSjPzDO,BdcXxbdQO bdcXxbdQO){
        Map<String,Object> bdsjMap =new HashMap<>();
        if(StringUtils.isNotBlank(bdcXxbdSjPzDO.getQqnr()) &&StringUtils.isNotBlank(bdcXxbdSjPzDO.getSjy())) {
            Map paramMap =bdcXxbdQO.getParamMap();
            //验证参数是否有值
            List<String> csList = Lists.newArrayList(bdcXxbdSjPzDO.getCs().split(CommonConstantUtils.ZF_YW_DH));
            csList.forEach(cs -> {
                if (paramMap.get(cs) == null) {
                    throw new MissingArgumentException("比对类型："+bdcXxbdSjPzDO.getBdlx()+"代码缺失比对配置的参数:{}" + cs);
                }
            });
            //多条sql用分号分隔
            List<String> sqlList = Lists.newArrayList(bdcXxbdSjPzDO.getQqnr().toLowerCase().split(CommonConstantUtils.ZF_YW_FH));
            sqlList.forEach(sql -> {
                paramMap.put("sql", sql);
                List<Map> result;
                if (StringUtils.equals(CommonConstantUtils.SJY_BDCDJ, bdcXxbdSjPzDO.getSjy())) {
                    //登记库
                    result = bdcSqlMapper.executeConfigSql(paramMap);

                } else if(StringUtils.equals(CommonConstantUtils.SJY_BDCSL,bdcXxbdSjPzDO.getSjy())){
                    //受理库
                    result =bdcSlPrintFeignService.executeConfigSql(paramMap);
                }else if(StringUtils.equals(CommonConstantUtils.SJY_BDCQJ,bdcXxbdSjPzDO.getSjy())){
                    //权籍库
                    result =buildingConfigFeignService.executeConfigSql(paramMap);
                } else if (StringUtils.equals(CommonConstantUtils.SJY_YDJZH, bdcXxbdSjPzDO.getSjy()) || StringUtils.equals(CommonConstantUtils.SJY_YDJBF, bdcXxbdSjPzDO.getSjy())) {
                    //登记2.0整合库和备份库
                    paramMap.put("sjy", bdcXxbdSjPzDO.getSjy());
                    result = etlConfigFeignService.executeConfigSql(paramMap);
                }else{
                    throw new AppException("信息比对执行SQL数据源不支持,比对类型"+bdcXxbdSjPzDO.getBdlx()+"数据源"+bdcXxbdSjPzDO.getBdlx());
                }
                if (CollectionUtils.isNotEmpty(result)) {
                    Map<String, String> data = result.get(0);
                    if(MapUtils.isNotEmpty(data)) {
                        for (Map.Entry entry : data.entrySet()) {
                            bdsjMap.put(entry.getKey().toString().toLowerCase(), entry.getValue());
                        }
                    }
                }
            });
        }
        return bdsjMap;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据服务获取比对数据
     */
    private Map<String,Object> queryXxbdSjByFw(BdcXxbdSjPzDO bdcXxbdSjPzDO,BdcXxbdQO bdcXxbdQO){
        Map<String,Object> bdsjMap =new HashMap<>();
        if(StringUtils.isNotBlank(bdcXxbdSjPzDO.getQqyy()) &&StringUtils.isNotBlank(bdcXxbdSjPzDO.getQqnr())) {
            Map paramMap = bdcXxbdQO.getParamMap();
            //验证参数是否有值
            List<String> csList = Lists.newArrayList(bdcXxbdSjPzDO.getCs().split(CommonConstantUtils.ZF_YW_DH));
            csList.forEach(cs -> {
                if (paramMap.get(cs) == null) {
                    throw new MissingArgumentException("比对类型："+bdcXxbdSjPzDO.getBdlx()+"代码缺失比对配置的参数:{}" + cs);
                }
            });
            //执行服务
            List<Map<String, Object>> result = (List<Map<String, Object>>) restRpcUtils.getRpcRequest(bdcXxbdSjPzDO.getQqyy(), bdcXxbdSjPzDO.getQqnr(), paramMap);
            if (CollectionUtils.isNotEmpty(result)) {
                Map<String, Object> data = result.get(0);
                if(MapUtils.isNotEmpty(data)) {
                    for (Map.Entry entry : data.entrySet()) {
                        bdsjMap.put(entry.getKey().toString().toLowerCase(), entry.getValue());
                    }
                }
            }
        }
        return bdsjMap;

    }

    /**
     * @param bdzdMap 比对字段与比对名称
     * @param sjlyBdsjMap 数据来源名称与比对数据结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  转换数据结构
     */
    private BdcXxbdDTO changeToBdcXxbdDTO(Map<String,Object> bdzdMap,Map<String,Map<String,Object>> sjlyBdsjMap){
        BdcXxbdDTO bdcXxbdDTO =new BdcXxbdDTO();
        List<String> sjlyList =new ArrayList<>();
        for (Map.Entry<String, Map<String,Object>> entry : sjlyBdsjMap.entrySet()) {
            sjlyList.add(entry.getKey());
        }
        List<Map<String,Object>> bdcBdsjDTOList =new ArrayList<>();
        //循环比对字段
        for (Map.Entry<String, Object> entry : bdzdMap.entrySet()) {
            LinkedHashMap<String,Object> bdsjDTO =new LinkedHashMap<>();
            bdsjDTO.put(ZDMC,entry.getValue());
            Integer sfxd =CommonConstantUtils.SF_S_DM;
            //获取每个数据来源当前字段的值
            for(int i=0;i<sjlyList.size();i++) {
                //获取每个数据来源当前字段的值
                String bdsjValue ="";
                if(sjlyBdsjMap.get(sjlyList.get(i)) !=null &&sjlyBdsjMap.get(sjlyList.get(i)).get(entry.getKey().toLowerCase()) != null) {
                    bdsjValue =sjlyBdsjMap.get(sjlyList.get(i)).get(entry.getKey().toLowerCase()).toString();
                }
                //比较数据值
                if(i >0 &&!StringUtils.equals(bdsjValue,bdsjDTO.get(ZDZ+(i)).toString())){
                    sfxd =CommonConstantUtils.SF_F_DM;
                }
                bdsjDTO.put(ZDZ+(i+1), bdsjValue);
            }
            bdsjDTO.put(SFXD,sfxd);
            bdcBdsjDTOList.add(bdsjDTO);
        }
        bdcXxbdDTO.setSjlyList(sjlyList);
        bdcXxbdDTO.setBdcBdsjDTOList(bdcBdsjDTOList);
        return bdcXxbdDTO;
    }




}
