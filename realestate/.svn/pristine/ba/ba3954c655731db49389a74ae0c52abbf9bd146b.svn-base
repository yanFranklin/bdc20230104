package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.tsztjs.YrbSwTsztjs;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.tsztjs.response.YrbTsswztResponse;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.XmlEntityCommonConvertUtil;
import cn.gtmap.realestate.exchange.service.inf.standard.JiangSuSwWebService;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Service
@WebService(name = "JiangSuSwWebService",
        targetNamespace = "http://service.exchange.realestate.gtmap.cn",
        endpointInterface = "cn.gtmap.realestate.exchange.service.inf.standard.JiangSuSwWebService")
public class JiangSuSwWebServiceImpl implements JiangSuSwWebService {

    private final Logger LOGGER = LoggerFactory.getLogger(JiangSuSwWebServiceImpl.class);

    @Autowired
    @Lazy
    private BdcZdFeignService zdFeignService;

    @Autowired
    @Lazy
    BdcSlHsxxFeignService bdcSlHsxxFeignService;

    @Autowired
    @Lazy
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    @Lazy
    private BdcSwFeignService bdcSwFeignService;

    @Autowired
    @Lazy
    private BdcSlXmFeignService bdcSlXmFeignService;

    /**
     * 接受税务返回的任务状态
     *
     * @param xmlStr 任务状态
     * @return
     * @Date 2022/10/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public String RWZTTS(String xmlStr) {
        LOGGER.info(xmlStr);
        LOGGER.info("税务状态，接受请求参数为：{}", xmlStr);
        YrbTsswztResponse fhmResponse = new YrbTsswztResponse();

        if (StringUtils.isNotBlank(xmlStr)) {
            String json = XmlEntityCommonConvertUtil.xmlToJson(xmlStr);
            JSONObject jsonObject = JSONObject.parseObject(json);

            YrbSwTsztjs yrbSwTsztjs = new YrbSwTsztjs();
            yrbSwTsztjs = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("SBZTTSXXLIST"), YrbSwTsztjs.class);
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs("SW_SBZT");
            bdcZdDsfzdgxDO.setDsfzdz(yrbSwTsztjs.getShzt());
            bdcZdDsfzdgxDO.setDsfxtbs("SW");
            BdcZdDsfzdgxDO zdDsfzdgxDO = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
            // 税务推送作废状态时，更新hsxx 退回原因
            String zfyy = "";
            if(StringUtils.equals("30", yrbSwTsztjs.getShzt())){
                try {
                    zfyy = URLDecoder.decode(yrbSwTsztjs.getBz(), "GBK") ;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("税务作废原因转码失败，转码内容为：{}", yrbSwTsztjs.getBz());
                }
            }
            if (null != zdDsfzdgxDO) {
                if (StringUtils.isNotBlank(yrbSwTsztjs.getSjbh())) {
                    //slbh查bdc_sl_jbxx 表，获取gzlslid,再去更新wszt
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(yrbSwTsztjs.getSjbh(), "");
                    if (null != bdcSlJbxxDO && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
                        try {
                            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
                            bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
                            List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
                            if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
                                for(BdcSlHsxxDO bdcSlHsxxDO: bdcSlHsxxDOList){
                                    bdcSlHsxxDO.setWszt(Integer.valueOf(zdDsfzdgxDO.getBdczdz()));
                                    bdcSlHsxxDO.setThyy(zfyy);
                                    this.bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
                                }
                            }else{
                                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                                bdcSlHsxxDO.setXmid(bdcSlXmDOList.get(0).getXmid());
                                bdcSlHsxxDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                                bdcSlHsxxDO.setWszt(Integer.valueOf(zdDsfzdgxDO.getBdczdz()));
                                bdcSlHsxxDO.setThyy(zfyy);
                                this.bdcSlHsxxFeignService.insertBdcSlHsxxDO(bdcSlHsxxDO);
                            }
//                            bdcSlHsxxFeignService.updateWsztByGzlslid(Integer.valueOf(zdDsfzdgxDO.getBdczdz()), bdcSlJbxxDO.getGzlslid());
                            fhmResponse.setFhm(Constants.SUCCESS_CODE_0);
                            fhmResponse.setFhxx("更新完税状态成功！");
                        } catch (Exception e) {
                            fhmResponse.setFhm(Constants.FAIL_CODE_1);
                            fhmResponse.setFhxx("更新完税状态失败！原因：" + e.getMessage());
                        }
                    }else {
                        fhmResponse.setFhm(Constants.FAIL_CODE_1);
                        fhmResponse.setFhxx("查不到该流程信息！请检查数据！");
                    }
                } else {
                    fhmResponse.setFhm(Constants.FAIL_CODE_1);
                    fhmResponse.setFhxx("收件编号为空！");
                }

            } else {
                fhmResponse.setFhm(Constants.FAIL_CODE_1);
                fhmResponse.setFhxx("审核状态未配置对照！请检查第三方字典标识SW_SBZT，系统标识SW相关对照配置！");
            }
        } else {
            fhmResponse.setFhm(Constants.FAIL_CODE_1);
            fhmResponse.setFhxx("接受状态信息为空！");
        }

        try {
            String dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(fhmResponse, YrbTsswztResponse.class);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            LOGGER.info("返回：{}", dataXml);
            return dataXml;
        } catch (Exception e) {
            LOGGER.info("接受税务状态返回转码异常：{}", e);
        }
        return xmlStr;
    }
}
