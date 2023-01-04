package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.GrdacxService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-08-04
 * @description 房产交易相关特殊处理
 */
@Service
public class SzfgfServiceImpl {

    protected static Logger LOGGER = LoggerFactory.getLogger(SzfgfServiceImpl.class);

    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private GrdacxService standardGrdacx;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Value("${clf.zy.djxl:2000402}")
    private String clfzyDjxl;


    /**
     * @param
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 更新是否省直房改房字段
     */
    public List<JSONObject> updateSfszfgf(List<JSONObject> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            for (JSONObject param : params) {
                if (MapUtils.getInteger(param, "sfszfgf") != null
                        && (StringUtils.isNotBlank(MapUtils.getString(param, "qlrmc"))
                        || StringUtils.isNotBlank(MapUtils.getString(param, "qlrzjh"))
                        || StringUtils.isNotBlank(MapUtils.getString(param, "bdcqzh")))) {
                    List<BdcXmFbDO> bdcXmFbDOList = bdcXmMapper.listBdcXmFbDo(param);
                    if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                        int count = 0;
                        for (BdcXmFbDO bdcXmFbDO : bdcXmFbDOList) {
                            bdcXmFbDO.setSfszfgf(MapUtils.getInteger(param, "sfszfgf"));
                            count = count + entityMapper.saveOrUpdate(bdcXmFbDO, bdcXmFbDO.getXmid());
                        }
                        param.put("count", count);
                    } else {
                        param.put("count", 0);
                    }
                } else {
                    param.put("count", 0);
                }
            }
        }
        return params;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 更新是否省直房改房字段
     */
    public List<Map<String, Object>> queryCqxx(JSONObject params) {
        if (MapUtils.isNotEmpty(params)
                && StringUtils.isNotBlank(MapUtils.getString(params, "qlrmc"))
                && StringUtils.isNotBlank(MapUtils.getString(params, "qlrzjh"))) {
            List<Map<String, Object>> result = bdcXmMapper.listFgfCqxx(params);
            if (CollectionUtils.isNotEmpty(result)) {
                for (Map<String, Object> map : result) {
                    CommonUtil.lowerMapKey(map);
                    if (StringUtils.isNotBlank(MapUtils.getString(map, "xmid"))) {
                        map.put("qlrs", commonService.listBdcQlrByXmid(MapUtils.getString(map, "xmid"), CommonConstantUtils.QLRLB_QLR));
                        map.put("ywrs", commonService.listBdcQlrByXmid(MapUtils.getString(map, "xmid"), CommonConstantUtils.QLRLB_YWR));
                    }
                    if (StringUtils.isNotBlank(MapUtils.getString(map, "bdcdyh"))) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setBdcdyh(MapUtils.getString(map, "bdcdyh"));
                        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                        String sfybzy = "0";
                        String[] clfzyDjxlList = StringUtils.split(clfzyDjxl, ",");
                        if (CollectionUtils.isNotEmpty(list)) {
                            for (BdcXmDO bdcXmDO : list) {
                                if (ArrayUtils.contains(clfzyDjxlList, bdcXmDO.getDjxl())) {
                                    sfybzy = "1";
                                    break;
                                }
                            }
                        }
                        map.put("sfybzy", sfybzy);
                    }
                }
                return result;
            }
        }
        return null;
    }

}
