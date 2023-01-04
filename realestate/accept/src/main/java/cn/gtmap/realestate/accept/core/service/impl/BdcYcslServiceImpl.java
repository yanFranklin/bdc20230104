package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;
import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 一窗受理信息
 */
@Service
public class BdcYcslServiceImpl implements BdcYcslService {

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    private BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    private BdcSlHsxxService bdcSlHsxxService;
    @Autowired
    private BdcSlSqrService bdcSlSqrService;
    @Autowired
    private BdcSlJtcyService bdcSlJtcyService;
    @Autowired
    private BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlJyxxCezsService bdcSlJyxxCezsService;
    @Autowired
    private BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcSlTdcrjService bdcSlTdcrjService;

    @Value("${print.path:}")
    private String printPath;

    @Override
    public YcslYmxxDTO queryYcslYmxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage("error." + Constants.PARAM_ERROR));
        }
        YcslYmxxDTO ycslYmxxDTO = new YcslYmxxDTO();

        //生成一窗受理房屋信息
        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(xmid);
        BdcSlFwxxDO bdcSlFwxxDO = new BdcSlFwxxDO();
        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
            bdcSlFwxxDO = bdcSlFwxxDOList.get(0);
        }
        ycslYmxxDTO.setBdcSlFwxxDO(bdcSlFwxxDO);
        //生成一窗交易信息
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            bdcSlJyxxDO = bdcSlJyxxDOList.get(0);
        }

        ycslYmxxDTO.setBdcSlJyxxDO(bdcSlJyxxDO);

        //生成一窗交易差额征收信息
        BdcSlJyxxCezsDO bdcSlJyxxCezsDO = new BdcSlJyxxCezsDO();
        List<BdcSlJyxxCezsDO> bdcSlJyxxCezsDOList = bdcSlJyxxCezsService.listBdcSlJyxxCezsByXmid(xmid);
        if(CollectionUtils.isNotEmpty(bdcSlJyxxCezsDOList)){
            bdcSlJyxxCezsDO = bdcSlJyxxCezsDOList.get(0);
        }
        ycslYmxxDTO.setBdcSlJyxxCezsDO(bdcSlJyxxCezsDO);

        //生成一窗税务信息
        //权利人税务信息
        List<BdcSwxxDTO> bdcZrfSwxxList = bdcSlHsxxService.queryBdcSwxxDTO(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(bdcZrfSwxxList)) {
            ycslYmxxDTO.setBdcZrfSwxxList(bdcZrfSwxxList);
        }

        //权利人税务信息
        List<BdcSwxxDTO> bdcZcfSwxxList = bdcSlHsxxService.queryBdcSwxxDTO(xmid, CommonConstantUtils.QLRLB_YWR);
        if (CollectionUtils.isNotEmpty(bdcZcfSwxxList)) {
            ycslYmxxDTO.setBdcZcfSwxxList(bdcZcfSwxxList);
        }

        //生成一窗受理申请人信息
        List<BdcSlSqrDTO> bdcSlZrfDTOList = generateSqrxx(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(bdcSlZrfDTOList)) {
            ycslYmxxDTO.setBdcSlZrfDTOList(bdcSlZrfDTOList);
        }
        List<BdcSlSqrDTO> bdcSlZcfDTOList = generateSqrxx(xmid, CommonConstantUtils.QLRLB_YWR);
        if (CollectionUtils.isNotEmpty(bdcSlZcfDTOList)) {
            ycslYmxxDTO.setBdcSlZcfDTOList(bdcSlZcfDTOList);
        }

        //土地出让金信息
        List<BdcSlTdcrjDO> bdcSlTdcrjDOList =bdcSlTdcrjService.listBdcSlTdcrjByXmid(xmid);
        if(CollectionUtils.isNotEmpty(bdcSlTdcrjDOList)) {
            ycslYmxxDTO.setBdcSlTdcrjDOList(bdcSlTdcrjDOList);
        }

        return ycslYmxxDTO;

    }



    /**
     * @param xmid 项目ID
     * @return 一窗受理申请人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成一窗受理申请人信息
     */
    private List<BdcSlSqrDTO> generateSqrxx(String xmid, String sqrlb) {
        List<BdcSlSqrDTO> bdcSlSqrDTOList = new ArrayList<>();
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, sqrlb);
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            //处理共有方式
            bdcSlSqrDOList = bdcSlSqrService.changeSlSqrGyfs(bdcSlSqrDOList, xmid);
            for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                BdcSlSqrDTO bdcSlSqrDTO = new BdcSlSqrDTO();
                bdcSlSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                List<BdcSlJtcyDO> bdcSlJtcyDOList = bdcSlJtcyService.listBdcSlJtcyBySqrid(bdcSlSqrDO.getSqrid());
                if (CollectionUtils.isNotEmpty(bdcSlJtcyDOList)) {
                    bdcSlSqrDTO.setBdcSlJtcyDOList(bdcSlJtcyDOList);
                }
                bdcSlSqrDTOList.add(bdcSlSqrDTO);
            }
        }
        return bdcSlSqrDTOList;


    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 一窗受理申请人信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 生成一窗受理核税信息单
     */
    @Override
    public List<YcslFjxxDTO> queryYcslFjxx(String gzlslid) {
        List<YcslFjxxDTO> list = new ArrayList<>();
        YcslFjxxDTO ycslFjxxDTO = new YcslFjxxDTO();
        if (StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("参数缺失！");
        }

        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (null != bdcSlJbxxDO && StringUtils.isBlank(bdcSlJbxxDO.getQxdm())) {
            throw new MissingArgumentException("区县代码为空");
        }

        String beanName = "btsw_sendHsxx";
        String ythywid = bdcSlJbxxDO.getQxdm() + "_" + gzlslid;
        Map paramMap = new HashMap(1);
        paramMap.put("ythywid", ythywid);

        Object object = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

        if (null != object) {
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(object));
            Map<String, String> map  = (Map<String, String>) jsonObj.get("data");

            if (CollectionUtils.isNotEmpty(map.keySet())) {
                for (String key : map.keySet()){

                    if (StringUtils.isNotBlank(key)) {
                        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                        bdcZdDsfzdgxDO.setZdbs("BDC_ZD_SW_PDF");
                        bdcZdDsfzdgxDO.setBdczdz(key);
                        bdcZdDsfzdgxDO.setDsfxtbs("sw");
                        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                        if (result == null && StringUtils.isBlank(result.getBdczdz())){
                            throw new MissingArgumentException("第三方字典值为空!");
                        }
                        ycslFjxxDTO.setBase64str(map.get(key));
                        ycslFjxxDTO.setPdffjid(key);
                        String pdfpath = this.getPath(key, gzlslid, map.get(key));
                        if (StringUtils.isBlank(pdfpath)){
                            throw new MissingArgumentException("生成文件失败!");
                        }
                        ycslFjxxDTO.setPdfpath(pdfpath);
                        ycslFjxxDTO.setPdffjmc(result.getDsfzdz());
                        list.add(ycslFjxxDTO);
                    }
                }
            }
        }
        return list;
    }

    private String getPath(String pdffjid, String gzlslid, String base64Str){
        String xmlStr = null;
        if (StringUtils.isNotBlank(pdffjid) && StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(base64Str)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(pdffjid).append(gzlslid).append(".pdf");
            xmlStr = printPath + "temp/" + stringBuilder;
            Base64Utils.decodeBase64StrToFile(base64Str, xmlStr);
        }
        return xmlStr;
    }


    /**
     * @param xmid 项目ID
     * @return 权利人和义务人税务信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 查询税务信息
     */
    public BdcSfDTO queryYcslSwxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage("error." + Constants.PARAM_ERROR));
        }
        BdcSfDTO bdcSfDTO = new BdcSfDTO();

        //权利人税务信息
        List<BdcSlHsxxDO> bdcZrfSwxxList = bdcSlHsxxService.queryQlrSwxx(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(bdcZrfSwxxList)) {
            bdcSfDTO.setBdcZrfSwxxList(bdcZrfSwxxList);
        }
        //权利人税务信息
        List<BdcSlHsxxDO> bdcZcfSwxxList = bdcSlHsxxService.queryQlrSwxx(xmid, CommonConstantUtils.QLRLB_YWR);
        if (CollectionUtils.isNotEmpty(bdcZcfSwxxList)) {
            bdcSfDTO.setBdcZcfSwxxList(bdcZcfSwxxList);
        }
        return bdcSfDTO;
    }

}
