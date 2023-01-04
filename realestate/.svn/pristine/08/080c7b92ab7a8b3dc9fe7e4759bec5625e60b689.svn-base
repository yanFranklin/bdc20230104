package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.PoliceHouseholdMembersRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.PoliceHouseholdMembersResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.PoliceHouseholdMembersResponseDataDTO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ProvincialPublicSecurityDepartmentFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxx.response.XgbmHyxxResponseCxjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxx.response.XgbmHyxxResponseDTO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-09
 * @description 南通地区 和受理子系统相关服务
 */
@Service
public class NantongAcceptService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private DozerBeanMapper exchangeDozerMapper;

    @Autowired
    ProvincialPublicSecurityDepartmentFeignService provincialPublicSecurityDepartmentFeignService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonObject
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 处理省级平台返回的婚姻信息 只在结婚状态时处理家庭成员信息
     */
    public Map<String,Object> filterHyxx(JSONObject jsonObject){
        if(jsonObject != null){
            Map<String,Object> hyxxMap = new HashMap<>();
            XgbmHyxxResponseDTO response = JSONObject.toJavaObject(jsonObject,XgbmHyxxResponseDTO.class);
            if(response != null && response.getData() != null){
                List<XgbmHyxxResponseCxjg> cxjgList = response.getData().getCxjg();
                if(CollectionUtils.isNotEmpty(cxjgList)){
                    // 根据 登记日期排序
                    Collections.sort(cxjgList);
                    XgbmHyxxResponseCxjg cxjg = cxjgList.get(0);
                    String hyzt = commonService.dsfZdToBdcDm("DSF_ZD_SJPT_HYZT","sjpt",cxjg.getOP_TYPE());
                    hyxxMap.put("hyzt",hyzt);
                    if (StringUtils.equals(CommonConstantUtils.HYZK_YH_MC, hyzt)) {
                        BdcSlJtcyDO bdcSlJtcyDO = new BdcSlJtcyDO();
                        bdcSlJtcyDO.setJtcymc(cxjg.getSPOUSE_NAME());
                        bdcSlJtcyDO.setZjh(cxjg.getSPOUSE_CERT_NUM());
                        String zjzl = commonService.dsfZdToBdcDm("DSF_ZD_SJPT_ZJZL","sjpt",cxjg.getSPOUSE_CERT_TYPE());
                        if(StringUtils.isNotBlank(zjzl)&& NumberUtils.isNumber(zjzl)){
                            bdcSlJtcyDO.setZjzl(Integer.parseInt(zjzl));
                        }
                        hyxxMap.put("jtcy",bdcSlJtcyDO);
                    }
                }else{
                    hyxxMap.put("hyzt","未婚");
                }
            }
            return hyxxMap;
        }
        return null;
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 过滤查询未成年子女信息
      */
    public List<BdcSlJtcyDO> filterZnxx(JSONObject jsonObject){
        List<BdcSlJtcyDO> bdcSlJtcyDOList =new ArrayList<>();
        if(jsonObject != null) {
            PoliceHouseholdMembersRequestDTO info = JSONObject.toJavaObject(jsonObject, PoliceHouseholdMembersRequestDTO.class);
            if(info != null &&StringUtils.isNotBlank(info.getIdentityNumber())){
                PoliceHouseholdMembersResponseDTO policeHouseholdMembersResponseDTO =provincialPublicSecurityDepartmentFeignService.policeHouseholdMembers(info);
                if(policeHouseholdMembersResponseDTO != null &&CollectionUtils.isNotEmpty(policeHouseholdMembersResponseDTO.getResponseDataDTOList())){
                    String cxryhzgx ="";
                    //先确认查询人与户主关系
                    for(PoliceHouseholdMembersResponseDataDTO dataDTO:policeHouseholdMembersResponseDTO.getResponseDataDTOList()){
                        String yhzgx = commonService.dsfZdToBdcDm("DSF_ZD_JTCYGX","sjpt",dataDTO.getYhzgx());
                        dataDTO.setYhzgx(yhzgx);
                        if(StringUtils.equals(info.getIdentityNumber(),dataDTO.getGmsfhm())){
                            cxryhzgx =yhzgx;
                        }
                    }
                    //需要查找查询人的未成年子女,各种情况需要对应的与户主关系
                    String yhzgx ="";
                    if(StringUtils.equals("户主",cxryhzgx) ||StringUtils.equals("夫妻",cxryhzgx)){
                        //查询人为户主或者为户主的配偶,当前查询人的子女即为户主的子女
                        yhzgx ="子女";
                    }else if(StringUtils.equals("父母",cxryhzgx)){
                        //查询人为户主的父母,当前查询人的子女即为户主以及户主的兄弟姐妹
                        yhzgx ="户主,兄弟姐妹";

                    }else if(StringUtils.equals("子女",cxryhzgx)){
                        //查询人为户主的子女,当前查询人的子女即为户主以及户主的孙子女
                        yhzgx ="孙子女";

                    }
                    for(PoliceHouseholdMembersResponseDataDTO dataDTO:policeHouseholdMembersResponseDTO.getResponseDataDTOList()){
                        if((StringUtils.isBlank(yhzgx) ||cn.gtmap.realestate.common.util.CommonUtil.indexOfStrs(yhzgx.split(CommonConstantUtils.ZF_YW_DH), dataDTO.getYhzgx())) &&!CommonUtil.checkSfcn(dataDTO.getGmsfhm())){
                            BdcSlJtcyDO slJtcyDO =new BdcSlJtcyDO();
                            exchangeDozerMapper.map(dataDTO, slJtcyDO, "acceptJtcyxx_jtcy");
                            slJtcyDO.setYsqrgx(CommonConstantUtils.YSQRGX_WCNZN_MC);
                            bdcSlJtcyDOList.add(slJtcyDO);
                        }
                    }
                }
            }

        }

        return bdcSlJtcyDOList;


    }


}
