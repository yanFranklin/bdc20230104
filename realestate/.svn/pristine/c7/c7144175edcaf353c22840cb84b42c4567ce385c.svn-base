package cn.gtmap.realestate.exchange.service.impl.inf.changzhou;

import cn.gtmap.gtc.storage.clients.v1.StorageClient;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.methods.HttpGet;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * shaoliyao
 * 常州-住建 权利人处理
 */
@Service
public class FcjyQlrDealServiceImpl {

    /**
     * 访问住建局文件下载接口地址
     */
    @Value("${zjj.fjxz.url:}")
    protected String zjjFjxzUrl;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    StorageClientMatcher storageClient;

    private static Logger LOGGER = LoggerFactory.getLogger(FcjyQlrDealServiceImpl.class);

    public static List<BdcQlrDO> splitQlrsByDh(Object object) {
        List<BdcQlrDO> list = new ArrayList<>();
        if (object != null) {
            try {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                LOGGER.error("常州合同转换，jsonObject：{}", JSONObject.toJSONString(jsonObject));
                if (jsonObject != null) {
                    if (StringUtils.isNotBlank(jsonObject.getString("CMR"))) {
                        BdcQlrDO bdcYwr = new BdcQlrDO();
                        bdcYwr.setSxh(1);
                        bdcYwr.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                        bdcYwr.setQlrmc(jsonObject.getString("CMR"));
                        bdcYwr.setZjh(jsonObject.getString("CMRZJH"));
                        bdcYwr.setZjzl(getZjzl(jsonObject.getString("CMRZJZL")));
                        list.add(bdcYwr);
                    }
                    if (StringUtils.isNotBlank(jsonObject.getString("MSRMC"))) {
                        String[] qlrList = StringUtils.split(jsonObject.getString("MSRMC"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrzjzls = StringUtils.split(jsonObject.getString("MSRZJZL"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrzjhs = StringUtils.split(jsonObject.getString("MSRZJH"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrlxdzs = StringUtils.split(jsonObject.getString("MSRLXDZ"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrlxdhs = StringUtils.split(jsonObject.getString("MSRLXDH"), CommonConstantUtils.ZF_YW_DH);
                        String[] wtdlrs = StringUtils.split(jsonObject.getString("WTDLR"), CommonConstantUtils.ZF_YW_DH);
                        String[] wtdlrzjzls = StringUtils.split(jsonObject.getString("WTDLRZJZL"), CommonConstantUtils.ZF_YW_DH);
                        String[] wtdlrzjhs = StringUtils.split(jsonObject.getString("WTDLRZJH"), CommonConstantUtils.ZF_YW_DH);
                        Integer qlrsxh =1;
                        for (int i = 0; i < qlrList.length; i++) {
                            BdcQlrDO bdcqlr = new BdcQlrDO();
                            bdcqlr.setSxh(qlrsxh);
                            qlrsxh++;
                            bdcqlr.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                            bdcqlr.setQlrmc(qlrList[i]);
                            bdcqlr.setZjzl(getZjzl(getIndexStrOrFristStr(msrzjzls, i)));
                            bdcqlr.setZjh(getIndexStrOrFristStr(msrzjhs, i));
                            bdcqlr.setTxdz(getIndexStrOrFristStr(msrlxdzs, i));
                            bdcqlr.setDh(getIndexStrOrFristStr(msrlxdhs, i));
                            bdcqlr.setDlrmc(getIndexStrOrFristStr(wtdlrs, i));
                            bdcqlr.setDlrzjh(getIndexStrOrFristStr(wtdlrzjhs, i));
                            bdcqlr.setDlrzjzl(getBdcdmByDsfzd("DSF_ZD_QLRLB", "htxx", getIndexStrOrFristStr(wtdlrzjzls, i)));
                            list.add(bdcqlr);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        return list;
    }

    public static List<BdcSlSqrDO> splitSqrsByDh(Object object) {
        List<BdcSlSqrDO> list = new ArrayList<>();
        if (object != null) {
            try {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                LOGGER.error("常州合同转换，jsonObject：{}", JSONObject.toJSONString(jsonObject));
                if (jsonObject != null) {
                    if (StringUtils.isNotBlank(jsonObject.getString("CMR"))) {
                        BdcSlSqrDO bdcYwr = new BdcSlSqrDO();
                        bdcYwr.setSxh(1);
                        bdcYwr.setSqrlb(CommonConstantUtils.QLRLB_YWR);
                        bdcYwr.setSqrmc(jsonObject.getString("CMR"));
                        bdcYwr.setZjh(jsonObject.getString("CMRZJH"));
                        bdcYwr.setZjzl(getZjzl(jsonObject.getString("CMRZJZL")));
                        list.add(bdcYwr);
                    }
                    if (StringUtils.isNotBlank(jsonObject.getString("MSRMC"))) {
                        String[] qlrList = StringUtils.split(jsonObject.getString("MSRMC"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrzjzls = StringUtils.split(jsonObject.getString("MSRZJZL"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrzjhs = StringUtils.split(jsonObject.getString("MSRZJH"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrlxdzs = StringUtils.split(jsonObject.getString("MSRLXDZ"), CommonConstantUtils.ZF_YW_DH);
                        String[] msrlxdhs = StringUtils.split(jsonObject.getString("MSRLXDH"), CommonConstantUtils.ZF_YW_DH);
                        String[] wtdlrs = StringUtils.split(jsonObject.getString("WTDLR"), CommonConstantUtils.ZF_YW_DH);
                        String[] wtdlrzjzls = StringUtils.split(jsonObject.getString("WTDLRZJZL"), CommonConstantUtils.ZF_YW_DH);
                        String[] wtdlrzjhs = StringUtils.split(jsonObject.getString("WTDLRZJH"), CommonConstantUtils.ZF_YW_DH);
                        Integer qlrsxh =1;
                        for (int i = 0; i < qlrList.length; i++) {
                            BdcSlSqrDO bdcqlr = new BdcSlSqrDO();
                            bdcqlr.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                            bdcqlr.setSxh(qlrsxh);
                            qlrsxh++;
                            bdcqlr.setSqrmc(qlrList[i]);
                            bdcqlr.setZjzl(getZjzl(getIndexStrOrFristStr(msrzjzls, i)));
                            bdcqlr.setZjh(getIndexStrOrFristStr(msrzjhs, i));
                            bdcqlr.setTxdz(getIndexStrOrFristStr(msrlxdzs, i));
                            bdcqlr.setDh(getIndexStrOrFristStr(msrlxdhs, i));
                            bdcqlr.setDlrmc(getIndexStrOrFristStr(wtdlrs, i));
                            bdcqlr.setDlrzjh(getIndexStrOrFristStr(wtdlrzjhs, i));
                            bdcqlr.setDlrzjzl(getZjzl(getIndexStrOrFristStr(wtdlrzjzls, i)));
                            list.add(bdcqlr);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        return list;
    }

    @Nullable
    private static Integer getZjzl(String dsfzjzl) {
        if (StringUtils.isNotBlank(dsfzjzl)) {
            String bdczjzl = getBdcdmByDsfzd("DSF_ZD_QLRZJZL", "htxx", dsfzjzl);
            if (StringUtils.isNotBlank(bdczjzl) && NumberUtils.isNumber(bdczjzl)) {
                return NumberUtils.createInteger(getBdcdmByDsfzd("DSF_ZD_QLRZJZL", "htxx", dsfzjzl));
            }
        }
        return null;
    }

    private static String getIndexStrOrFristStr(String[] strArray, int i) {
        String str = "";
        if (strArray != null && i < strArray.length) {
            str = strArray[i];
        } else if (ArrayUtils.isNotEmpty(strArray)) {
            str = strArray[0];
        }
        return str;
    }

    private static String getBdcdmByDsfzd(String zdbs, String dsfxtbs, String dsfzdz) {
        Object bean = Container.getBean(BdcZdFeignService.class);
        if (bean != null && StringUtils.isNotBlank(dsfzdz)) {
            BdcZdFeignService zdService = (BdcZdFeignService) bean;
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdbs);
            bdcZdDsfzdgxDO.setDsfzdz(dsfzdz);
            bdcZdDsfzdgxDO.setDsfxtbs(dsfxtbs);
            BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
            if (result != null) {
                return result.getBdczdz();
            }
        }
        return "";
    }

    /**
     * 根据文件id下载文件
     *
     * @param fileid 文件id
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object zjjFjxx(String fileId,String jgxybh) {
        LOGGER.info("文件编号：{}，{}", fileId,jgxybh);
       /* String testUrl = "http://192.168.2.71:8669/realestate-certificate/rest/v1.0/zs/{zsid}?access_token=70c4fc20-90f3-4a66-947a-7fd90499d22d";
        testUrl = testUrl.replace("{zsid}",htbh);*/

        if (StringUtils.isBlank(fileId)) {
            throw new MissingArgumentException("缺失文件id参数，请检查！");
        }
        String url = zjjFjxzUrl.replace("{fileId}", fileId);
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream;
        StorageDto dto;
        try {
            inputStream = httpClientService.doGetReturnStream(url);
            LOGGER.info("根据文件id下载文件返回：{}", inputStream);

            //创建一个文件目录
            StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, jgxybh, "资金监管", "");

            String fileContent ="data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(IOUtils.toByteArray(inputStream));
            LOGGER.info("根据文件id下载文件返回：{}", fileContent);
            //文件上传到大云
            MultipartFile file = Base64Utils.base64ToMultipart(fileContent);
            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
            multipartDto.setName("资金监管.pdf");
            multipartDto.setNodeId(storageDto.getId());
            multipartDto.setSpaceCode(jgxybh);
            try {
                multipartDto.setData(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                byte[] bytes = new byte[100];
                multipartDto.setData(bytes);
            }
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename("资金监管.pdf");
            dto = storageClient.multipartUpload(multipartDto);
        } catch (Exception e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", url, fileId, e);
            throw new AppException("httpGet 住建局下载文件请求异常");
        }
        LOGGER.info("文件返回：{}", dto);

        return dto;
    }

}
