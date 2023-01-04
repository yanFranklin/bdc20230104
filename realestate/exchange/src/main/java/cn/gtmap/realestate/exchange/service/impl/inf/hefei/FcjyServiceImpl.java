package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.exchange.core.dto.common.CfQlWithXmQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.common.DyQlWithXmQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.common.QueryQlRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.common.YyQlWithXmQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.rcsd.BdcRcsdDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.rcsd.BdcRcsdFyxxZtDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.qo.fcjy.BdcRcsdQO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0  2020-03-23
 * @description 房产交易相关特殊处理
 */
@Service
public class FcjyServiceImpl {

    protected static Logger LOGGER = LoggerFactory.getLogger(FcjyServiceImpl.class);

    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    FwHsFeignService fwHsFeignService;

    @Autowired
    FwYcHsFeignService fwYcHsFeignService;


    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param paramJob
     * @return
     * @description 登记获取房产备案信息
     */
    public Object getBaxxByXmid(JSONObject paramJob){
        if (null != paramJob) {
            List<Map<String,String>> mapList = bdcXmMapper.getQlrAndBdcqzhByXmid((String)paramJob.get("xmid"));
            if (CollectionUtils.isNotEmpty(mapList)) {
                for (Map<String,String> map : mapList) {
                    Object obj =  getBaxxByZjhAndCqzh(map.get("ZJH"), map.get("BDCQZH"), map.get("YXTCQZH"), "fcjybaxxbycqzhandzjh");
                    if (getBaxxByZjhAndCqzh(obj)) {
                        return obj;
                    }
                }
            }
        }
        return new JSONObject();
    }

    private boolean getBaxxByZjhAndCqzh(Object obj){
        boolean flag = false;
        if (null != obj) {
            JSONObject job = JSON.parseObject(JSON.toJSONString(obj));
            if (null != job) {
                JSONArray joa = job.getJSONArray("bdcSlJyxx");
                if (CollectionUtils.isNotEmpty(joa)) {
                    JSONObject bdcSlJyxxDO = joa.getJSONObject(0);
                    if (null != bdcSlJyxxDO && StringUtils.isNotBlank(bdcSlJyxxDO.getString("htbh"))) {
                        flag = true;
                    }
                }
            }
        }

        return flag;
    }

    public Object getBaxxByZjhAndCqzh(String zjh,String cqzh,String yxtcqzh,String beanName){
        Object obj = getBaxxByZjhAndCqzh(zjh, cqzh, beanName);
        if (getBaxxByZjhAndCqzh(obj)) {
            return obj;
        }

        return getBaxxByZjhAndCqzh(zjh,yxtcqzh, beanName);
    }

    public Object getBaxxByZjhAndCqzh(String zjh, String cqzh, String beanName) {
        if (StringUtils.isNoneBlank(zjh, cqzh, beanName)) {
            Map paramMap = new HashMap();
            paramMap.put("cardNo", zjh);
            paramMap.put("realNo", cqzh);
            return exchangeBeanRequestService.request(beanName, paramMap);
        }
        return null;
    }

    /**
     * @param bdcRcsdQO
     * @return
     * @author <a href="mailto:huangjain@gtmap.cn">huangjain</a>
     * @description 人才锁定房源信息查询
     */
    public BdcCommonResponse<List<BdcRcsdDTO>> getRcsdFyxx(BdcRcsdQO bdcRcsdQO) {
        if (null != bdcRcsdQO) {
            List<BdcRcsdDTO> rcsdDTOList = bdcXmMapper.queryRcsdList(bdcRcsdQO);
            return BdcCommonResponse.ok(rcsdDTOList);
        } else {
            return BdcCommonResponse.fail("参数不可都为空！请检查参数！");
        }
    }


    /**
     * @param bdcRcsdQO
     * @return
     * @author <a href="mailto:huangjain@gtmap.cn">huangjain</a>
     * @description 人才锁定房源信息限制状态查询
     */
    public BdcCommonResponse<List<BdcRcsdFyxxZtDTO>> getRcsdFyxxZt(BdcRcsdQO bdcRcsdQO) {
        if (StringUtils.isNotBlank(bdcRcsdQO.getCqzh())) {
            //先用cqzh查到所有的bdcdyh，用bdcdyh查相关限制信息
            List<BdcRcsdFyxxZtDTO> bdcdyhList = bdcXmMapper.queryBdcxmByBdcqzh(bdcRcsdQO);
            if (CollectionUtils.isNotEmpty(bdcdyhList)) {
                for (BdcRcsdFyxxZtDTO rcsdFyxxZtDTO : bdcdyhList) {
                    //开始查询相关限制信息
                    QueryQlRequestDTO requestDTO = new QueryQlRequestDTO();
                    requestDTO.setBdcdyh(rcsdFyxxZtDTO.getBdcdyh());
                    requestDTO.setQszt("1");
                    List<DyQlWithXmQlrDTO> dyQlWithXmQlrDTOS = commonService.listDyaqByBdcdyh(requestDTO);
                    List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(requestDTO);
                    // 查询异议状态
                    List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(requestDTO);
                    if (CollectionUtils.isNotEmpty(dyQlWithXmQlrDTOS)) {
                        rcsdFyxxZtDTO.setSfdy("1");
                    } else {
                        rcsdFyxxZtDTO.setSfdy("0");
                    }
                    if (CollectionUtils.isNotEmpty(cfList)) {
                        rcsdFyxxZtDTO.setSfcf("1");
                    } else {
                        rcsdFyxxZtDTO.setSfcf("0");
                    }
                    if (CollectionUtils.isNotEmpty(yyList)) {
                        rcsdFyxxZtDTO.setSfyy("0");
                    } else {
                        rcsdFyxxZtDTO.setSfyy("1");
                    }
                }
                return BdcCommonResponse.ok(bdcdyhList);
            } else {
                return BdcCommonResponse.fail("查不到该产权数据，请检查数据！");
            }
        } else {
            return BdcCommonResponse.fail("cqzh参数不可为空！请检查参数！");
        }
    }

    /**
     * @param paramObject 接口参数
     * @return 交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号或者原项目ID获取房屋编码,读取商品房备案信息
     */
    public Object getSpfBaxxByBdcdyhOrYxmid(JSONObject paramObject){
        if (null != paramObject) {
            String bdcdyh =paramObject.getString("bdcdyh");
            String yxmid =paramObject.getString("xmid");
            String qjgldm =paramObject.getString("qjgldm");
            String fwbm =paramObject.getString("fwbm");
            if(StringUtils.isBlank(fwbm)) {
                //获取预售房屋编码
                if (StringUtils.isNotBlank(yxmid)) {
                    BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(yxmid);
                    if (bdcXmDO != null) {
                        fwbm = bdcXmDO.getYsfwbm();
                    }
                } else if (StringUtils.isNotBlank(bdcdyh)) {
                    FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh, qjgldm);
                    if (fwHsDO != null) {
                        fwbm = fwHsDO.getYsfwbm();
                    } else {
                        FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcdyh, qjgldm);
                        if (fwYchsDO != null) {
                            fwbm = fwYchsDO.getYsfwbm();
                        }
                    }
                }
            }
            if(StringUtils.isNotBlank(fwbm)){
                Map paramMap =new HashMap();
                paramMap.put("fwbm", fwbm);
                return exchangeBeanRequestService.request("hfFcjySpfBaxxByFwbm", paramMap);
            }
        }
        return new JSONObject();
    }
}
