package cn.gtmap.realestate.exchange.service.impl.nantong;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrSwDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataFjclDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.component.BdcDmToDsfZdComponent;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.nantong.NantongBtswService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/4/26
 * @description
 */
@Service
public class NantongBtswServiceImpl implements NantongBtswService {

    private static Logger LOGGER = LoggerFactory.getLogger(NantongBtswServiceImpl.class);

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper exchangeDozerMapper;

    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;


    /**
     * 获取请求参数
     *
     * @param tsswDataDTO
     * @return Object
     * @author wangyinghao
     * @description
     */
    @Override
    public Object getClfjyxxParam(TsswDataDTO tsswDataDTO) {
        //基本信息
        JSONObject baseInfo = new JSONObject();
        exchangeDozerMapper.map(tsswDataDTO, baseInfo, "clfjyxxbase");

        //cjxx
        JSONObject cjxx = new JSONObject();
        exchangeDozerMapper.map(tsswDataDTO, cjxx, "clfjyxxcjxx");

        //fyxx
        JSONObject fyxx = new JSONObject();
        exchangeDozerMapper.map(tsswDataDTO, fyxx, "clfjyxxfyxx");

        //权利人
        JSONArray csfxxList = new JSONArray();
        JSONArray zrfxxList = new JSONArray();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())) {
            List<BdcSlSqrSwDTO> qlrList = tsswDataDTO.getSqrSwList().stream()
                    .filter(bdcSlSqrSwDTO -> CommonConstantUtils.QLRLB_QLR.equals(bdcSlSqrSwDTO.getSqrlb()))
                    .collect(Collectors.toList());
            List<BdcSlSqrSwDTO> ywrList = tsswDataDTO.getSqrSwList().stream()
                    .filter(bdcSlSqrSwDTO -> CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrSwDTO.getSqrlb()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (BdcSlSqrSwDTO bdcSlSqrSwDTO : qlrList) {
                    JSONObject csfxx = new JSONObject();
                    exchangeDozerMapper.map(bdcSlSqrSwDTO, csfxx, "clfjyxxzrfxx");
                    if (CollectionUtils.isNotEmpty(bdcSlSqrSwDTO.getBdcSlJtcyDOList())) {
                        JSONArray jtcyxxList = new JSONArray();
                        for (BdcSlJtcyDO bdcSlJtcyDO : bdcSlSqrSwDTO.getBdcSlJtcyDOList()) {
                            JSONObject jtcyxx = new JSONObject();
                            exchangeDozerMapper.map(bdcSlJtcyDO, jtcyxx, "clfjyxxjtcyxx");
                            jtcyxxList.add(jtcyxx);
                        }
                        csfxx.put("jtcyGrid", jtcyxxList);
                    }
                    csfxxList.add(csfxx);
                }
            }
            if (CollectionUtils.isNotEmpty(ywrList)) {
                for (BdcSlSqrSwDTO bdcSlSqrSwDTO : ywrList) {
                    JSONObject zrfxx = new JSONObject();
                    exchangeDozerMapper.map(bdcSlSqrSwDTO, zrfxx, "clfjyxxzrfxx");
                    if (CollectionUtils.isNotEmpty(bdcSlSqrSwDTO.getBdcSlJtcyDOList())) {
                        JSONArray jtcyxxList = new JSONArray();
                        for (BdcSlJtcyDO bdcSlJtcyDO : bdcSlSqrSwDTO.getBdcSlJtcyDOList()) {
                            JSONObject jtcyxx = new JSONObject();
                            exchangeDozerMapper.map(bdcSlJtcyDO, jtcyxx, "clfjyxxjtcyxx");
                            jtcyxxList.add(jtcyxx);
                        }
                        zrfxx.put("jtcyGrid", jtcyxxList);
                    }
                    zrfxxList.add(zrfxx);
                }
            }
        }

        //影印资料
        JSONArray yyzlList = new JSONArray();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getFjclList())) {
            for (TsswDataFjclDTO tsswDataFjclDTO : tsswDataDTO.getFjclList()) {
                JSONObject yyzlxx = new JSONObject();
                exchangeDozerMapper.map(tsswDataFjclDTO, yyzlxx, "clfjyxxjtyyzl");
                yyzlList.add(yyzlxx);
            }
        }

        //组装
        baseInfo.put("cjxxModel", cjxx);
        baseInfo.put("fyxxModel", fyxx);
        baseInfo.put("csfxxModelGrid", csfxxList);
        baseInfo.put("zrfxxModelGrid", zrfxxList);
        baseInfo.put("yyzlmodelGrid", yyzlList);
        return baseInfo;
    }


    /**
     * 获取请求参数
     *
     * @param tsswDataDTO
     * @return Object
     * @author wangyinghao
     * @description
     */
    @Override
    public Object getZlfjyxxParam(TsswDataDTO tsswDataDTO) {
        //基本信息
        JSONObject baseInfo = new JSONObject();
        exchangeDozerMapper.map(tsswDataDTO, baseInfo, "clfjyxxbase");

        //cjxx
        JSONObject cjxx = new JSONObject();
        exchangeDozerMapper.map(tsswDataDTO, cjxx, "clfjyxxcjxx");

        //fyxx
        JSONObject fyxx = new JSONObject();
        exchangeDozerMapper.map(tsswDataDTO, fyxx, "clfjyxxfyxx");

        //权利人
        JSONArray csfxxList = new JSONArray();
        JSONArray zrfxxList = new JSONArray();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())) {
            List<BdcSlSqrSwDTO> qlrList = tsswDataDTO.getSqrSwList().stream()
                    .filter(bdcSlSqrSwDTO -> CommonConstantUtils.QLRLB_QLR.equals(bdcSlSqrSwDTO.getSqrlb()))
                    .collect(Collectors.toList());
            List<BdcSlSqrSwDTO> ywrList = tsswDataDTO.getSqrSwList().stream()
                    .filter(bdcSlSqrSwDTO -> CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrSwDTO.getSqrlb()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (BdcSlSqrSwDTO bdcSlSqrSwDTO : qlrList) {
                    JSONObject csfxx = new JSONObject();
                    exchangeDozerMapper.map(bdcSlSqrSwDTO, csfxx, "clfjyxxzrfxx");
                    if (CollectionUtils.isNotEmpty(bdcSlSqrSwDTO.getBdcSlJtcyDOList())) {
                        JSONArray jtcyxxList = new JSONArray();
                        for (BdcSlJtcyDO bdcSlJtcyDO : bdcSlSqrSwDTO.getBdcSlJtcyDOList()) {
                            JSONObject jtcyxx = new JSONObject();
                            exchangeDozerMapper.map(bdcSlSqrSwDTO, jtcyxx, "clfjyxxjtcyxx");
                            jtcyxxList.add(jtcyxx);
                        }
                        csfxx.put("jtcyGrid", jtcyxxList);
                    }
                    csfxxList.add(csfxx);
                }
            }
            if (CollectionUtils.isNotEmpty(ywrList)) {
                for (BdcSlSqrSwDTO bdcSlSqrSwDTO : ywrList) {
                    JSONObject zrfxx = new JSONObject();
                    exchangeDozerMapper.map(bdcSlSqrSwDTO, zrfxx, "clfjyxxzrfxx");
                    zrfxxList.add(zrfxx);
                }
            }
        }

        //影印资料
        JSONArray yyzlList = new JSONArray();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getFjclList())) {
            for (TsswDataFjclDTO tsswDataFjclDTO : tsswDataDTO.getFjclList()) {
                JSONObject yyzlxx = new JSONObject();
                exchangeDozerMapper.map(tsswDataFjclDTO, yyzlxx, "clfjyxxjtyyzl");
                yyzlList.add(yyzlxx);
            }
        }

        //发票信息
        JSONObject fpxx = new JSONObject();
        exchangeDozerMapper.map(tsswDataDTO.getBdcSlJyxx(), fpxx, "zlfjyxxjtfpxx");

        //组装
        baseInfo.put("cjxxModel", cjxx);
        baseInfo.put("fyxxModel", fyxx);
        baseInfo.put("csfxxModelGrid", csfxxList);
        baseInfo.put("zrfxxModelGrid", zrfxxList);
        baseInfo.put("yyzlmodelGrid", yyzlList);
        baseInfo.put("fpxxModelGrid", fpxx);

        return baseInfo;
    }

    /**
     * 3推送房产交易审核状态信息
     * 50:采集审核不通过
     * 60：采集审核成功
     * 70：申报成功
     * 80：申报失败
     *
     * @param param
     * @return
     */
    @Override
    public Object swkkzt(JSONObject param) {
        LOGGER.info("推送房产交易审核状态信息,{}", JSON.toJSONString(param));
        JSONObject response = new JSONObject();
        String ythywid = param.getString("ythywid");
        String shzt = param.getString("shzt");
        if (StringUtil.isBlank("ythywid") || StringUtil.isBlank("shzt")) {
            response.put("code", "0");
            response.put("msg", "参数错误");
            return response;
        }
        String gzlslid = getGzlslid(ythywid);
        if (StringUtil.isBlank(gzlslid)) {
            response.put("code", "0");
            response.put("msg", "参数错误");
            return response;
        }
        Integer wszt = 0;
        if (shzt.equals("50")) {
            wszt = 1;
        } else if (shzt.equals("60")) {
            wszt = 5;
        } else if (shzt.equals("70")) {
            wszt = 8;
        } else if (shzt.equals("80")) {
            wszt = 6;
        }
        bdcSlHsxxFeignService.updateWsztByGzlslid(wszt, gzlslid);
        response.put("code", "1");
        return response;
    }

    /**
     * 税务端将扣款状态推送到不动产
     *
     * @param param
     * @return
     */
    @Override
    public Object sendShztxx(JSONObject param) {
        LOGGER.info("税务端将扣款状态推送到不动产,{}", JSON.toJSONString(param));
        JSONObject response = new JSONObject();
        String ythywid = param.getString("ythywid");
        JSONObject kkxx = param.getJSONObject("kkxx");
        if (StringUtil.isBlank("ythywid")
                || Objects.isNull(kkxx)
                || Objects.isNull(kkxx.get("zrfcsfbz"))
        ) {
            response.put("code", "0");
            response.put("msg", "参数错误");
            return response;
        }
        //获取工作流定义ID
        String gzlslid = getGzlslid(ythywid);
        if (StringUtil.isBlank(gzlslid)) {
            response.put("code", "0");
            response.put("msg", "参数错误");
            return response;
        }
        if (kkxx.containsKey("kkfhdm") && kkxx.get("kkfhdm").equals("000")) {
            Integer wszt = 10;
            String qlrlb= CommonConstantUtils.QLRLB_QLR;
            if(kkxx.get("zrfcsfbz").equals("0")){
                qlrlb = CommonConstantUtils.QLRLB_YWR;
            }
            bdcSlHsxxFeignService.updateWsztByQlrlb(wszt, gzlslid,qlrlb);
        }
        response.put("code", "1");
        return response;
    }


    private String getGzlslid(String ythywid){
        String[] s = ythywid.split("_");
        if (s.length != 2) {
            return null;
        }
        return s[1];
    }
}
