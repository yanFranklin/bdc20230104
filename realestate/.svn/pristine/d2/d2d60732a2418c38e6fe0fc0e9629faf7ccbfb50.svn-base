package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlDyaqDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-25
 * @description 自助查询机 一体化流程创建 相关服务
 */
@Service
public class NtLccjService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NtLccjService.class);
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;
    @Autowired
    private CommonService commonService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper exchangeDozerMapper;

    /**
     * @param sqxxList
     * @param cjparam
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 自助办税机创建一体化流程  处理项目信息
     */
    public WwsqCjBdcXmRequestDTO dealXmxx(JSONArray sqxxList, WwsqCjBdcXmRequestDTO cjparam, JSONObject sply) {
        if (CollectionUtils.isNotEmpty(sqxxList)) {
            List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
            BdcSdqghDTO bdcSdqghDTO = new BdcSdqghDTO();
            for(int i = 0 ; i < sqxxList.size() ; i++){
                JSONObject sqxx = sqxxList.getJSONObject(i);
                BdcSlXmDTO cqxm = getCqxm(sqxx);
                if (StringUtils.isNotBlank(MapUtils.getString(sply, "sply")) && cqxm.getDsfSlxxDTO() != null) {
                    cqxm.getDsfSlxxDTO().setSply(MapUtils.getString(sply, "sply"));
                }
                cqxm.getDsfSlxxDTO().setSxh(xmDTOList.size()+1);
                xmDTOList.add(cqxm);
                // 组合流程
                if (sqxx.getJSONObject("dyxx") != null
                        && CommonConstantUtils.SF_S_DM.equals(sqxx.getJSONObject("dyxx").getInteger("zhlc"))) {
                    BdcSlXmDTO dyxm = getDyxm(sqxx);
                    if (StringUtils.isNotBlank(MapUtils.getString(sply, "sply")) && dyxm.getDsfSlxxDTO() != null) {
                        dyxm.getDsfSlxxDTO().setSply(MapUtils.getString(sply, "sply"));
                    }
                    dyxm.getDsfSlxxDTO().setSxh(xmDTOList.size() + 1);
                    xmDTOList.add(dyxm);
                }
                //处理水电气
                if (null != sqxx.getJSONObject("ghxx")) {
                    exchangeDozerMapper.map(sqxx.getJSONObject("ghxx"), bdcSdqghDTO, "nt_hlwcj_sdq");
                    if (null != bdcSdqghDTO) {
                        cjparam.getBdcSlxxDTO().setBdcSdqghDTO(bdcSdqghDTO);
                    }
                }
            }
            cjparam.getBdcSlxxDTO().setBdcSlXmList(xmDTOList);
        }
        return cjparam;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sqxx
     * @return cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO
     * @description 获取抵押权项目
     */
    private BdcSlXmDTO getDyxm(JSONObject sqxx){
        BdcSlXmDTO dyxm = exchangeBeanRequestService.request("cjServiceGetDyxm",sqxx,BdcSlXmDTO.class);
        dealDyxx(dyxm,sqxx.getString("dyxx"));
        return dyxm;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sqxx
     * @return cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO
     * @description 获取产权项目
     */
    private BdcSlXmDTO getCqxm(JSONObject sqxx) {
        BdcSlXmDTO cqxm = exchangeBeanRequestService.request("cjServiceGetCqxm", sqxx, BdcSlXmDTO.class);
        if (cqxm != null && StringUtils.isNotBlank(sqxx.getString("fwlx"))) {
            BdcSlFwxxDO bdcSlFwxxDO = new BdcSlFwxxDO();
            if (cqxm.getBdcSlQl() != null) {
                bdcSlFwxxDO = (BdcSlFwxxDO) cqxm.getBdcSlQl();
            }
            String fwlx = commonService.dsfZdToBdcDm("BDC_ZD_FWLX", "ntlccj", sqxx.getString("fwlx"));
            if (StringUtils.isNotBlank(fwlx)) {
                bdcSlFwxxDO.setFwlx(NumberUtils.toInt(fwlx));
            }
            cqxm.setBdcSlQl(bdcSlFwxxDO);
        }
        return cqxm;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dyXmDTO
     * @param dyXmDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @description 处理 转移抵押 组合流程 但不传递 抵押信息的场景
     */
    public void dealDyxx(BdcSlXmDTO dyXmDTO,String dyxx){
        // 循环 dyXmDTO 的 申请人 只保留权利人 并给他们处理为义务人
        if(CollectionUtils.isNotEmpty(dyXmDTO.getBdcSlSqrDOList())){
            Iterator<BdcSlSqrDO> temp = dyXmDTO.getBdcSlSqrDOList().iterator();
            while (temp.hasNext()){
                BdcSlSqrDO sqrDO = temp.next();
                if(!StringUtils.equals(CommonConstantUtils.QLRLB_QLR,sqrDO.getSqrlb())){
                    temp.remove();
                }else{
                    sqrDO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(dyXmDTO.getBdcSlSqrDTOList())){
            Iterator<BdcSlSqrDTO> dtoTemp = dyXmDTO.getBdcSlSqrDTOList().iterator();
            while (dtoTemp.hasNext()){
                BdcSlSqrDTO sqrDTO = dtoTemp.next();
                if(!StringUtils.equals(CommonConstantUtils.QLRLB_QLR,sqrDTO.getBdcSlSqrDO().getSqrlb())){
                    dtoTemp.remove();
                }else{
                    sqrDTO.getBdcSlSqrDO().setSqrlb(CommonConstantUtils.QLRLB_YWR);
                }
            }
        }
        // 处理抵押土地面积和抵押房产面积
        if (dyXmDTO != null && StringUtils.isNotBlank(dyxx)) {
            JSONObject dyxxJson = JSONObject.parseObject(dyxx);
            if (dyxxJson != null && (StringUtils.isNotBlank(dyxxJson.getString("dytdmj")) || StringUtils.isNotBlank(dyxxJson.getString("dyfcmj")))) {
                BdcSlDyaqDO bdcSlDyaqDO = new BdcSlDyaqDO();
                if (dyXmDTO.getBdcSlQl() != null) {
                    bdcSlDyaqDO = (BdcSlDyaqDO) dyXmDTO.getBdcSlQl();
                }
                try {
                    if (StringUtils.isNotBlank(dyxxJson.getString("dytdmj"))) {
                        bdcSlDyaqDO.setTddymj(Double.parseDouble(dyxxJson.getString("dytdmj")));
                    }
                    if (StringUtils.isNotBlank(dyxxJson.getString("dyfcmj"))) {
                        bdcSlDyaqDO.setFwdymj(Double.parseDouble(dyxxJson.getString("dyfcmj")));
                    }
                } catch (Exception e) {
                    LOGGER.error("外网抵押土地面积或抵押房产面积参数错误：", e);
                }
                dyXmDTO.setBdcSlQl(bdcSlDyaqDO);
            }
        }
    }

    /**
     *
     * @param dataDTO
     * 处理权利比例
     */
    public TsswDataDTO dealQlbl(TsswDataDTO dataDTO) {
        if(dataDTO == null){
            return dataDTO;
        }
        List<BdcSlSqrDO> bdcSlSqrDOList = dataDTO.getSqrList();
        //保证共有比例之和为1
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            // 获取 权利人
            List<BdcSlSqrDO> qlrList = new ArrayList<>();
            List<BdcSlSqrDO> ywrList = new ArrayList<>();
            for(BdcSlSqrDO temp:bdcSlSqrDOList){
                if(CommonConstantUtils.QLRLB_QLR.equals(temp.getSqrlb())){
                    qlrList.add(temp);
                }else{
                    ywrList.add(temp);
                }
            }
            dealSqrQlbl(qlrList);
            dealSqrQlbl(ywrList);
            dataDTO.setSqrList(qlrList);
            dataDTO.getSqrList().addAll(ywrList);
        }
        return dataDTO;
    }

    private void dealSqrQlbl(List<BdcSlSqrDO> bdcSlSqrDOList){
        if(CollectionUtils.isEmpty(bdcSlSqrDOList)){
            return;
        }
        if (!CommonConstantUtils.GYFS_AFGY.equals(bdcSlSqrDOList.get(0).getGyfs())) {
            DecimalFormat df = new DecimalFormat();//格式化小数
            String qlbl = df.format((float) 1 / bdcSlSqrDOList.size());
            for (int i = 0; i < bdcSlSqrDOList.size(); i++) {
                BdcSlSqrDO bdcSlSqrDO = bdcSlSqrDOList.get(i);
                if (i < (bdcSlSqrDOList.size() - 1)) {
                    bdcSlSqrDO.setQlbl(qlbl);
                } else {
                    bdcSlSqrDO.setQlbl(df.format(1 - (Double.parseDouble(qlbl) * (bdcSlSqrDOList.size() - 1))));
                }
            }
        }
    }

}
