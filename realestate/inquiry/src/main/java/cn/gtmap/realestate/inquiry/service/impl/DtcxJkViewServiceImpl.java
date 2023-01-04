package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.inquiry.common.DtcxConstants;
import cn.gtmap.realestate.inquiry.service.DtcxJkViewService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/17
 * @description 接口动态查询
 */
@Service
public class DtcxJkViewServiceImpl implements DtcxJkViewService {
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public Object listResultByPage(Pageable pageable, String jk, String jkid,String fhzkey, Map dataMap) {

        // 组织参数
        String cxjson = getJsonByCxxx(dataMap);
        if (StringUtils.isBlank(cxjson)) {
            throw new AppException("无法获取查询条件json");
        }
        // 组织分页参数
        cxjson = cxjson.replace("#{pagesize}", "\"" + pageable.getPageSize() + "\"");
        cxjson = cxjson.replace("#{pagenumber}", "\"" + pageable.getPageNumber() + "\"");

        Map data;

        switch (jk) {
            case "interface":
                data = (Map) exchangeInterfaceFeignService.requestInterface(jkid, JSONObject.parseObject(cxjson));
                break;
            case "wwsqinterface":
                data = (Map) exchangeInterfaceFeignService.wwsqRequestInterface(jkid, JSONObject.parseObject(cxjson));
                break;
            case "sjptinterface":
                data = (Map) exchangeInterfaceFeignService.sjptRequestInterface(jkid, JSONObject.parseObject(cxjson));
                break;
            default:
                throw new AppException("无法找到需要的接口");
        }

        return new GTAutoConfiguration.DefaultPageImpl((List) ((Map) data.get("data")).get(fhzkey),
                pageable.getPageNumber(), pageable.getPageSize(), Integer.parseInt((String) ((Map) data.get("data")).get("total")));
    }

    /**
     * 通过查询id 获取对应json
     *
     * @param mapCxxx
     * @return
     */
    private String getJsonByCxxx(Map mapCxxx) {
        if (mapCxxx == null || MapUtils.getString(mapCxxx, DtcxConstants.CXID) == null) {
            return null;
        }
        DtcxDO dtcxCx = entityMapper.selectByPrimaryKey(DtcxDO.class, MapUtils.getString(mapCxxx, DtcxConstants.CXID));
        Example example = new Example(DtcxCxtjDO.class);
        example.createCriteria().andEqualTo(DtcxConstants.CXID, MapUtils.getString(mapCxxx, DtcxConstants.CXID));
        List<DtcxCxtjDO> cxtjList = entityMapper.selectByExample(example);

        String cxjson = dtcxCx.getCxsql();
        // 用字段对应方式替换后的sql执行
        for (DtcxCxtjDO cxtj : cxtjList) {
            if (StringUtils.isBlank(mapCxxx.get(cxtj.getTjzdid()).toString())) {
                cxjson = cxjson.replace("#{" + cxtj.getTjzdid() + "}", "\"\"");
            } else {
                cxjson = cxjson.replace("#{" + cxtj.getTjzdid() + "}", "\"" + mapCxxx.get(cxtj.getTjzdid()).toString() + "\"");
            }
        }
        return cxjson;
    }

}
