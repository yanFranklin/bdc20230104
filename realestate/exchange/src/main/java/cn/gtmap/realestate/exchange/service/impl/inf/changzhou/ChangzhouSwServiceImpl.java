package cn.gtmap.realestate.exchange.service.impl.inf.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.SwHsztDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.XmlEntityCommonConvertUtil;
import cn.gtmap.realestate.exchange.core.dto.changzhou.fssrdzh.jfcx.request.JfcxModel;
import cn.gtmap.realestate.exchange.core.dto.changzhou.fssrdzh.request.FssrDzhData;
import cn.gtmap.realestate.exchange.util.Md5Util;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.MsgEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 13:39 2020/10/28
 * @description 常州-税务相关操作接口
 */
@Service(value = "changzhouSwServiceImpl")
public class ChangzhouSwServiceImpl {

    private static Logger LOGGER = LoggerFactory.getLogger(ChangzhouSwServiceImpl.class);

    /**
     * 是否需要将权利人名称作为查询条件
     */
    @Value("${sw.jtfccx.qlrmc:true}")
    private Boolean needQlrmc;

    /**
     * 家庭房产查询为空是否直接返回空，false 返回每个权利人空房产信息XML，true 返回 空
     */
    @Value("${sw.jtfccx.wfcfhk:false}")
    private Boolean wfcfhk;

    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;
    @Autowired
    BdcZfxxCxFeignService bdcZfxxCxFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    /**
     * 根据合同编号更新核税状态
     *
     * @param htbh 合同编号
     * @param hszt 核税状态
     * @param thyy 退回原因
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 13:57 2020/10/28
     */
    public Map updateHszt(String htbh, Integer hszt, String thyy) {
        Map<String, Object> result = new HashedMap(4);
        Map<String, Object> head = new HashedMap(4);
        Map<String, Object> data = new HashedMap(4);
        //默认值
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.ERROR.getMsg();
        if (StringUtils.isBlank(htbh) && null != hszt && StringUtils.isBlank(thyy)) {
            head.put(Constants.STATUSCODE, returncode);
            head.put(Constants.MSG, msg);
            data.put(Constants.MSG, MsgEnum.PARAM_ERROR.getMsg());
            result.put("head", head);
            result.put("data", data);
            return result;
        }
        SwHsztDTO hsztDTO = new SwHsztDTO();
        hsztDTO.setHszt(hszt);
        hsztDTO.setHtbh(htbh);
        hsztDTO.setThyy(thyy);
        Integer count = 0;
        count = bdcSlHsxxFeignService.updateHszt(hsztDTO);
        if (count > 0) {
            returncode = Constants.CODE_SUCCESS;
            msg = MsgEnum.SUCCESS.getMsg();
            data.put("htbh", hsztDTO.getHtbh());
            data.put("count", count);
            data.put(Constants.MSG, MsgEnum.SUCCESS.getMsg());

            head.put(Constants.STATUSCODE, returncode);
            head.put(Constants.MSG, msg);

        } else {
            head.put(Constants.STATUSCODE, returncode);
            head.put(Constants.MSG, msg);
            data.put("htbh", hsztDTO.getHtbh());
            data.put("count", count);
            data.put(Constants.MSG, MsgEnum.ERROR.getMsg());

        }
        result.put("head", head);
        result.put("data", data);
        return result;
    }

    /**
     * 对datakey进行加密后，返回参数
     *
     * @param fssrDzhData fssrDzhData
     * @return FssrDzhData
     * @Date 2020/12/16
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object encodeBl(FssrDzhData fssrDzhData) {
        if (null != fssrDzhData) {
            String data1 = JSONObject.toJSONString(fssrDzhData.getData(), SerializerFeature.WriteNullStringAsEmpty);
            String data = fssrDzhData.getToken() + data1;
            String datakey = Md5Util.getMd5To32(data,"");
            fssrDzhData.setDatakey(datakey);
        }
        LOGGER.info("-=-=---=-=-加密后12121{}", JSONObject.toJSONString(fssrDzhData, SerializerFeature.WriteNullStringAsEmpty));
        return JSONObject.toJSONString(fssrDzhData, SerializerFeature.WriteNullStringAsEmpty);
    }

    /**
     * 对datakey进行加密后，返回参数
     *
     * @param jfcxModel jfcxModel
     * @return FssrDzhData
     * @Date 2020/12/16
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object encodeQr(JfcxModel jfcxModel) {
        if (null != jfcxModel) {
            String data1 =  JSONObject.toJSONString(jfcxModel.getData(), SerializerFeature.WriteNullStringAsEmpty);
            String data = jfcxModel.getToken() + data1;
            String datakey = Md5Util.getMd5To32(data,"");
            jfcxModel.setDatakey(datakey);

        }
        LOGGER.info("-=-=---=-=-jfcxModel加密后123{}", JSONObject.toJSONString(jfcxModel, SerializerFeature.WriteNullStringAsEmpty));

        return JSONObject.toJSONString(jfcxModel, SerializerFeature.WriteNullStringAsEmpty);
    }

    /**
     * 家庭房产信息查询
     * @param qlrxx 权利人信息
     * @return {TaxbizmlResponseDTO} 房产信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public String queryFcxx(String qlrxx) {
        if(StringUtils.isBlank(qlrxx)) {
            throw new MissingArgumentException("未定义权利人参数信息");
        }

        String traceId = UUIDGenerator.generate16();
        LOGGER.info("{}税务查询家庭房产信息入参：{}", traceId, qlrxx);
        String json = XmlEntityCommonConvertUtil.xmlToJson(qlrxx);
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject taxbizml = jsonObject.getJSONObject("TAXBIZML");
        JSONObject fwtcxxlist = taxbizml.getJSONObject("FWTCXXLIST");

        Object grxxObj = fwtcxxlist.get("GRXX");
        if(null == grxxObj) {
            throw new MissingArgumentException("未定义权利人参数信息");
        }

        List<Map> qlrList = new ArrayList<>();
        if(grxxObj instanceof  JSONArray) {
            qlrList = JSON.parseArray(JSON.toJSONString(grxxObj), Map.class);
        } else {
            qlrList.add(JSON.parseObject(JSON.toJSONString(grxxObj), Map.class));
        }

        List<BdcZfxxDTO> bdcZfxxDTOList = this.queryBdcZfxx(qlrList);

        JSONArray fwxxArray = new JSONArray();
        if(CollectionUtils.isEmpty(bdcZfxxDTOList)) {
            if(wfcfhk) {
                return "";
            }

            for(Map grxx : qlrList){
                BdcZfxxDTO bdcZfxx = new BdcZfxxDTO();
                bdcZfxx.setQlrmc(String.valueOf(grxx.get("XM")));
                bdcZfxx.setQlrzjh(String.valueOf(grxx.get("SFZJHM")));

                JSONObject fwxxObj = new JSONObject();
                fwxxObj.put("FWXX", resolveFwxx(bdcZfxx, 0));
                fwxxArray.add(fwxxObj);
            }
        } else {
            List<String> bdcdyhList = bdcZfxxDTOList.stream().map(BdcZfxxDTO::getBdcdyh).distinct().collect(Collectors.toList());

            for(BdcZfxxDTO bdcZfxx : bdcZfxxDTOList) {
                JSONObject fwxxObj = new JSONObject();
                fwxxObj.put("FWXX", resolveFwxx(bdcZfxx, bdcdyhList.size()));
                fwxxArray.add(fwxxObj);
            }
        }

        JSONObject fwxxListObj = new JSONObject();
        fwxxListObj.put("FWTCXXLIST", fwxxArray);

        JSONObject taxbizmlObj = new JSONObject();
        taxbizmlObj.put("TAXBIZML", fwxxListObj);

        String fcxxXml = XmlEntityCommonConvertUtil.jsonToXml(taxbizmlObj);
        fcxxXml = fcxxXml.replaceAll("</FWTCXXLIST><FWTCXXLIST>", "");
        String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        fcxxXml = xmlHead + fcxxXml;
        LOGGER.info("{}税务查询家庭房产信息返回信息：{}", traceId, fcxxXml);
        return fcxxXml;
    }

    /**
     * 调用 查询子系统 有房无房查询接口
     * @param qlrList 权利人信息
     * @return {List} 住房信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private List<BdcZfxxDTO> queryBdcZfxx(List<Map> qlrList) {
        BdcZfxxQO zfxxQO = new BdcZfxxQO();
        zfxxQO.setQlrxx(new ArrayList<>());
        for(Map grxx : qlrList){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setZjh(String.valueOf(grxx.get("SFZJHM")));
            if(needQlrmc) {
                bdcQlrQO.setQlrmc(String.valueOf(grxx.get("XM")));
            }
            zfxxQO.getQlrxx().add(bdcQlrQO);
        }
        zfxxQO.setCxly("1");
        return bdcZfxxCxFeignService.listBdcNtZfxxDTO(zfxxQO);
    }

    /**
     * 查询权利人共有人
     * @param bdcZfxx 房屋信息
     * @return {String} 共有人
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String queryQlrGyr(BdcZfxxDTO bdcZfxx) {
        if(null == bdcZfxx || StringUtils.isBlank(bdcZfxx.getXmid())) {
            return "";
        }

        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcZfxx.getXmid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlrWithMhlx(bdcQlrQO);
        if(CollectionUtils.isEmpty(qlrDOList) || 1 == qlrDOList.size()) {
            return "";
        }

        List<BdcQlrDO> gyrList = qlrDOList.stream().filter(bdcQlr -> !StringUtils.equals(bdcQlr.getZjh(), bdcZfxx.getQlrzjh())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(gyrList)) {
            return "";
        }
        return StringToolUtils.resolveBeanToAppendStr(gyrList, "getQlrmc", CommonConstantUtils.ZF_YW_DH);
    }

    /**
     * 组织房屋信息
     * @param bdcZfxx 房产信息
     * @param jttc 家庭套次
     * @return {JSONObject} 房屋信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private JSONObject resolveFwxx(BdcZfxxDTO bdcZfxx, int jttc) {
        JSONObject fwxx = new JSONObject();
        fwxx.put("NSRMC", bdcZfxx.getQlrmc());
        fwxx.put("NSRZJHM", bdcZfxx.getQlrzjh());
        fwxx.put("FWZL", bdcZfxx.getZl());
        fwxx.put("DJZK", 1);
        fwxx.put("DJRQ", null == bdcZfxx.getDjsj() ? "" : DateFormatUtils.format(bdcZfxx.getDjsj(), "yyyy-MM-dd"));
        fwxx.put("FWMJ", bdcZfxx.getJzmj());
        fwxx.put("QZH", bdcZfxx.getBdcqzh());
        fwxx.put("DJLX", bdcZfxx.getDjlx());
        fwxx.put("FWYT", bdcZfxx.getGhyt());
        fwxx.put("JTTC", jttc);
        fwxx.put("GYR", this.queryQlrGyr(bdcZfxx));
        return fwxx;
    }
}
