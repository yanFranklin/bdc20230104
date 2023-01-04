package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.core.qo.DzzzCxQO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2020-06-17
 * @description 南通地区 电子证照查询相关服务
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Service(value = "ntDzzzService")
public class NtDzzzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NtDzzzService.class);

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcDzzzFeignService bdcDzzzFeignService;
    @Autowired
    private BdcZsFeignService zsFeignService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private CommonService commonService;


    /**
     * 获取权利类型与预告登记种类数据
     *
     * @return map 权利类型与预告登记种类
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public Object dzzzcxBySlbh(DzzzCxQO dzzzCxQo) {
        LOGGER.info(dzzzCxQo.toString());
        // 组装接口返回参数 status: 0 成功  1失败
        Map<String, Object> result = new HashedMap(4);
        Map<String, Object> head = new HashedMap(4);
        if (StringUtils.isNotBlank(dzzzCxQo.getSlbh())) {
            List<BdcZsDO> bdcZsDOList = new ArrayList<>();
            BdcXmQO bdcXmQo = new BdcXmQO();
            bdcXmQo.setSlbh(dzzzCxQo.getSlbh());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQo);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("未获取到不动产项目信息，受理编号为：" + dzzzCxQo.getSlbh());
            }
            List<Map<String, Object>> dataList = new ArrayList<>(bdcXmDOList.size());
            String errorMsg = "";
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                //去证书表查询bdcqzh
                List<BdcZsDO> ZsDoList = zsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
                bdcZsDOList.addAll(ZsDoList);
            }
            //去重
            Set<BdcZsDO> zsDoSet = new TreeSet<>(Comparator.comparing(BdcZsDO::getBdcqzh));
            zsDoSet.addAll(bdcZsDOList);
            for (BdcZsDO zsDO : zsDoSet) {
                DzzzResponseModel<List<Map>> dzzzResponseModel = this.getDzzzxx(dzzzCxQo.getYymc(), zsDO.getBdcqzh());
                if (StringUtils.equals("0", dzzzResponseModel.getHead().getStatus())) {
                    List<Map> dzzzxxList = dzzzResponseModel.getData();
                    // 向电子证照信息接口数据中添加权利信息与预告登记种类信息
                    Example example = new Example(BdcXmZsGxDO.class);
                    example.createCriteria().andEqualTo("zsid", zsDO.getZsid());
                    List<BdcXmZsGxDO> xmZsGxDOList = entityMapper.selectByExampleNotNull(example);
                    if (CollectionUtils.isNotEmpty(xmZsGxDOList)) {
                        BdcXmQO bdcXmQos = new BdcXmQO();
                        bdcXmQos.setXmid(xmZsGxDOList.get(0).getXmid());
                        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQos);
                        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                            Map qllxAndYgzlMap = this.getQllxAndYgzl(bdcXmDOS.get(0));
                            for (Map dzzzxx : dzzzxxList) {
                                dzzzxx.putAll(qllxAndYgzlMap);
                                dataList.add(dzzzxx);
                            }
                        }
                    }
                } else {
                    // 记录接口请求的异常信息，用于未获取到证照信息时，返回错误信息
                    errorMsg = dzzzResponseModel.getHead().getMessage();
                }
            }
            if (CollectionUtils.isNotEmpty(dataList)) {
                head.put("status", "0");
                head.put("message", "success");
            } else {
                head.put("status", "1");
                head.put("message", errorMsg);
            }
            result.put("data", dataList);
            result.put("head", head);

        } else if (StringUtils.isNotBlank(dzzzCxQo.getCzzt()) && StringUtils.isNotBlank(dzzzCxQo.getCzztdm())) {
            //查询条件是权利人和证件号时
           /* List<BdcQlrDO> qlrDOList = commonService.listBdcQlrByQlrAndZjh(dzzzCxQo.getCzzt(),dzzzCxQo.getCzztdm(),"1");
            if(CollectionUtils.isNotEmpty(qlrDOList)){

            }*/
            String errorMsg = "";
            List<Map<String, Object>> dataList = new ArrayList<>();
            DzzzResponseModel<List<Map>> dzzzResponseModel = this.getDzzzxxByqlr(dzzzCxQo);
            if (StringUtils.equals("0", dzzzResponseModel.getHead().getStatus())) {
                List<Map> dzzzxxList = dzzzResponseModel.getData();

                for (Map dzzzxx : dzzzxxList) {
                    dataList.add(dzzzxx);
                }

            } else {
                errorMsg = dzzzResponseModel.getHead().getMessage();
            }
            if (CollectionUtils.isNotEmpty(dataList)) {
                head.put("status", "0");
                head.put("message", "success");
            } else {
                head.put("status", "1");
                head.put("message", errorMsg);
            }
            result.put("data", dataList);
            result.put("head", head);
        } else {
            throw new AppException("必填参数不能为空");
        }
        return result;
    }

    /**
     * 调用电子证照接口获取证照信息
     *
     * @param yymc   应用名称
     * @param bdcqzh 不动产权证号
     * @return 电子证照信息
     */
    private DzzzResponseModel<List<Map>> getDzzzxx(String yymc, String bdcqzh) {
        Map paramMap = new HashMap(2);
        paramMap.put("bdcqzh", bdcqzh);
        Map dataMap = new HashMap(2);
        dataMap.put("data", paramMap);
        return bdcDzzzFeignService.zzcx2(yymc, JSONObject.toJSONString(dataMap));
    }

    private DzzzResponseModel<List<Map>> getDzzzxxByqlr(DzzzCxQO dzzzCxQo) {
        Map paramMap = new HashMap(12);
        paramMap.put("czzt", dzzzCxQo.getCzzt());
        paramMap.put("czztdm", dzzzCxQo.getCzztdm());
        paramMap.put("zzzt", null != dzzzCxQo.getZzzt() ? Integer.valueOf(dzzzCxQo.getZzzt()) : null);
        paramMap.put("zzlxdm", dzzzCxQo.getZzlxdm());
        Map dataMap = new HashMap(2);
        dataMap.put("data", paramMap);
        return bdcDzzzFeignService.zzjs2(dzzzCxQo.getYymc(), JSONObject.toJSONString(dataMap));

    }

    private Map getQllxAndYgzl(BdcXmDO bdcXmDO) {
        Map map = new HashedMap(4);
        // 获取权利类型
        String qllx = String.valueOf(bdcXmDO.getQllx());
        map.put("qllx", qllx);

        // 获取预告登记种类
        BdcQl bdcQl = this.bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        if (bdcQl != null) {
            Integer ygdjzl = null;
            if (bdcQl instanceof BdcYgDO) {
                ygdjzl = ((BdcYgDO) bdcQl).getYgdjzl();
            }
            map.put("ygdjzl", null == ygdjzl ? "" : String.valueOf(ygdjzl));
        }
        return map;
    }

}
