package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrSwDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.SwHsztDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request.YrbClfFcjyxxRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request.YrbClfFyjbxxRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request.YrbClfRwjshouseRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request.YrbClfTsxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request.YrbClfskxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.YrbClfskxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request.YrbEwmjkxxRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request.YrbEwmjkxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response.YrbEwmjkxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.request.FcjydjkxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response.FcjydjkxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.request.YrbFcjyEwmRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.request.YrbFcjyEwmTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.response.YrbFcjyEwmDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.request.YrbFcjysbxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.response.YrbFcjysbxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.request.YrbFcjysbxxQrTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.response.YrbFcjysbqrResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.request.YrbFcjyyjkRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.request.YrbFcjyyjkTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.response.YrbFcjyyjkDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.request.YrbQslxdhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.response.YrbQslxdhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.request.YrbQswspzhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbQswspzhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.request.YrbQswsxxhqTzxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.response.YrbQswsxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.request.YrbRwztTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.response.YrbRwztDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.request.YrbSbztcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.request.YrbSbztcxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.response.YrbSbztcxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.response.YrbSbztcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYxzlTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.response.YrbYxzlResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.request.YrbZlfskxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response.YrbZlfjsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.XmlEntityCommonConvertUtil;
import cn.gtmap.realestate.exchange.core.dto.nantong.sw.HtbhWithFjxx.HtbhWithFjxx;
import cn.gtmap.realestate.exchange.core.dto.nantong.sw.HtbhWithFjxx.HtbhWithFjxxData;
import cn.gtmap.realestate.exchange.core.dto.nantong.sw.fjxx.FjclNantongSwDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sw.fjxx.FjxxBean;
import cn.gtmap.realestate.exchange.core.dto.nantong.sw.fjxxDistinguishSfts.FjxxBeanSfts;
import cn.gtmap.realestate.exchange.core.dto.nantong.sw.fjxxDistinguishSfts.FjxxDistinguishSftsDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sw.tsswxx.response.RdTsswxxResponseDTO;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.sw.NKlsjkClientService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nkstar.lsjkclient.bean.nklsjkResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-09
 * @description 税务相关特殊处理
 */
@Service
public class NantongSwServiceImpl {

    protected static final Logger LOGGER = LoggerFactory.getLogger(NantongSwServiceImpl.class);

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcZdFeignService zdFeignService;

    @Resource(name = "exchangeDozerMapper")
    DozerBeanMapper dozerBeanMapper;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;


    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private BdcSlHsxxFeignService hsxxFeignService;

    @Value("${rd.tsswxx.htxx.fwdy:1}")
    private String fwdyDefauleValue;

    @Autowired
    private BdcXmFeignService xmFeignService;

    /**
     * 税务访问大云附件地址映射 IP 和 端口
     */
    @Value("${sw.fj.ip_port:}")
    protected String swFjIpPortStandard;

    /**
     * 访问银行抵押合同接口地址
     */
    @Value("${yh.dyht.url:}")
    protected String yhDyhtYrl;
    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    NKlsjkClientService cl;


    /**
     * 根据合同编号更新核税状态，并上传电子税票
     *
     * @param
     * @return
     * @Date 2021/4/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Map<String, Object> updateWsztAndFjxx(@RequestBody HtbhWithFjxxData jsxx) {
        if (null != jsxx && CollectionUtils.isNotEmpty(jsxx.getJsxx())) {
            for (HtbhWithFjxx htbhWithFjxx : jsxx.getJsxx()) {
                if (StringUtils.isNotBlank(htbhWithFjxx.getHtbh()) && StringUtils.isNotBlank(htbhWithFjxx.getJszt())) {
                    SwHsztDTO hsztDTO = new SwHsztDTO();
                    hsztDTO.setHtbh(htbhWithFjxx.getHtbh());
                    hsztDTO.setHszt(Integer.valueOf(htbhWithFjxx.getJszt()));
                    hsztDTO.setWszt(Integer.valueOf(htbhWithFjxx.getJszt()));
                    Integer count = hsxxFeignService.updateHszt(hsztDTO);
                    if (count > 0) {
                        LOGGER.info("更新核税状态和完税状态成功！");
                    } else {
                        throw new AppException("更新核税状态和完税状态失败！");
                    }
                    //保存附件
                    if (StringUtils.isNotBlank(htbhWithFjxx.getFjmc())) {
                        String gzlslid = "";
                        String djxl = "";
                        String name = "电子税票";
                        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.queryBdcSlJyxxByHtbh(htbhWithFjxx.getHtbh());
                        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                            BdcSlXmDO bdcSlXmDO = bdcSlXmFeignService.queryBdcSlXmByXmid(bdcSlJyxxDOList.get(0).getXmid());
                            if (bdcSlXmDO != null) {
                                djxl = bdcSlXmDO.getDjxl();
                                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                                if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
                                    gzlslid = bdcSlJbxxDO.getGzlslid();
                                }
                            }
                        }
                        //创建电子税票文件夹
                        StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, name, "");
                        if (null != storageDto) {
                            if (StringUtils.isNotBlank(htbhWithFjxx.getFjbase64())) {
                                MultipartFile file = Base64Utils.base64ToMultipart(CommonConstantUtils.BASE64_QZ_PDF + htbhWithFjxx.getFjbase64());
                                try {
                                    MultipartDto multipartDto = getUploadParamDto(storageDto, file, htbhWithFjxx);
                                    StorageDto storageDto1 = storageClient.multipartUpload(multipartDto);
                                    if (null != storageDto) {
                                        LOGGER.info("上传电子税票信息成功：{}", storageDto.getDownUrl());
                                    } else {
                                        throw new AppException("电子税票失败！");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    throw new AppException("保存电子税票PDF文件操作失败!");
                                }
                            }
                            // 保存受理库收件材料,有的更新，没有的插入
                            BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                            BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                            bdcSlSjclQO.setGzlslid(gzlslid);
                            bdcSlSjclQO.setClmc(htbhWithFjxx.getFjmc());
                            List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);

                            if (CollectionUtils.isNotEmpty(bdcSlSjclDOS)) {
                                bdcSlSjclDO = bdcSlSjclDOS.get(0);
                            } else {
                                bdcSlSjclDO = new BdcSlSjclDO();
                            }
                            bdcSlSjclDO.setYs(1);
                            bdcSlSjclDO.setFs(1);
                            bdcSlSjclDO.setSjlx(2);
                            bdcSlSjclDO.setDjxl(djxl);
                            bdcSlSjclDO.setGzlslid(gzlslid);
                            bdcSlSjclDO.setWjzxid(storageDto.getId());
                            if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
                                bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                            } else {
                                bdcSlSjclFeignService.updateBdcSlSjcl(bdcSlSjclDO);
                            }
                        }
                    }
                } else {
                    throw new MissingArgumentException("合同编号和交税状态均不可为空！");
                }
            }
        } else {
            throw new MissingArgumentException("参数不能为空！");
        }


        return null;
    }

    private static MultipartDto getUploadParamDto(StorageDto storageDto, MultipartFile file, HtbhWithFjxx htbhWithFjxx) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        if (file != null) {
            multipartDto.setData(file.getBytes());
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(htbhWithFjxx.getFjmc());
            multipartDto.setName(htbhWithFjxx.getFjmc());
        }
        return multipartDto;
    }

    /**
     * 调用如东推送税务信息接口并处理参数
     *
     * @param request
     * @return
     */
    public Object rdTsswxx(RdTsswxxResponseDTO request) {
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
        if (jsonObject.containsKey("csfnsrxx") && jsonObject.getJSONArray("csfnsrxx") != null && jsonObject.getJSONArray("csfnsrxx").size() > 0) {
            JSONArray tempJSONArray = new JSONArray();
            for (int i = 0; i < jsonObject.getJSONArray("csfnsrxx").size(); i++) {
                JSONObject csfnsrxx = jsonObject.getJSONArray("csfnsrxx").getJSONObject(i);
                JSONObject temp = new JSONObject();
                if (csfnsrxx.containsKey("jtcytcxx") && csfnsrxx.getString("jtcytcxx").equals("-1")) {
                    csfnsrxx.put("jtcytcxx", "0");
                }

                csfnsrxx.keySet().forEach(key -> {
                    temp.put(key.toLowerCase(), null == csfnsrxx.get(key) ? "" : csfnsrxx.get(key));
                });
                if (null == csfnsrxx.get("wcnzn")) {
                    temp.put("wcnzn", new ArrayList<>());
                }
                tempJSONArray.add(temp);
            }

            jsonObject.put("csfnsrxx", tempJSONArray);
        }
        String htqdsj = "";
        if (jsonObject.containsKey("zrfnsrxx") && jsonObject.getJSONArray("zrfnsrxx") != null && jsonObject.getJSONArray("zrfnsrxx").size() > 0) {

            if (jsonObject.getJSONObject("htxx") != null && jsonObject.getJSONObject("htxx").containsKey("htqdsj")) {
                htqdsj = (String) jsonObject.getJSONObject("htxx").get("htqdsj");
            }
            JSONArray tempJSONArray = new JSONArray();
            for (int i = 0; i < jsonObject.getJSONArray("zrfnsrxx").size(); i++) {
                JSONObject zrfnsrxx = jsonObject.getJSONArray("zrfnsrxx").getJSONObject(i);
                JSONObject temp = new JSONObject();
                String finalHtqdsj = htqdsj;
                if (zrfnsrxx.containsKey("jtcytcxx") && zrfnsrxx.getString("jtcytcxx").equals("-1")) {
                    zrfnsrxx.put("jtcytcxx", "0");
                }
                zrfnsrxx.keySet().forEach(key -> {
                    temp.put(key.toLowerCase(), null == zrfnsrxx.get(key) ? "" : zrfnsrxx.get(key));
                    if (StringUtils.isNotBlank(finalHtqdsj)) {
                        temp.put("djsj", finalHtqdsj);
                    }
                });

                if (null == zrfnsrxx.get("wcnzn")) {
                    temp.put("wcnzn", new ArrayList<>());
                }
                tempJSONArray.add(temp);
            }
            jsonObject.put("zrfnsrxx", tempJSONArray);
        }
        JSONObject fpxx = new JSONObject();
        JSONArray fpxxJSONArray = new JSONArray();
        if (jsonObject.getJSONObject("htxx") != null && jsonObject.getJSONObject("htxx").containsKey("fpdm")) {
            fpxx.put("fpdm", jsonObject.getJSONObject("htxx").remove("fpdm"));
        }
        if (jsonObject.getJSONObject("htxx") != null && jsonObject.getJSONObject("htxx").containsKey("fphm")) {
            fpxx.put("fphm", jsonObject.getJSONObject("htxx").remove("fphm"));
        }
        if (fpxx.size() > 0) {
            fpxx.put("fpbase64", null);
            fpxxJSONArray.add(fpxx);
            jsonObject.remove("fpxx");
            jsonObject.put("fpxx", fpxxJSONArray);
        } else {
            fpxx.put("fpbase64", null);
            fpxx.put("fpdm", null);
            fpxx.put("fphm", null);
            fpxxJSONArray.add(fpxx);
            jsonObject.remove("fpxx");
            jsonObject.put("fpxx", fpxxJSONArray);
        }
        if (jsonObject.getJSONObject("htxx") != null) {
            JSONObject htxx = jsonObject.getJSONObject("htxx");
            if (htxx.containsKey("hTQDSJ") && htxx.get("hTQDSJ") != null) {
                htxx.put("hTQDSJ", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .format(new Date(htxx.getLong("hTQDSJ"))));
            }
            JSONObject tempHtxx = new JSONObject();
            htxx.keySet().forEach(key -> {
                tempHtxx.put(key.toLowerCase(), null == htxx.get(key) ? "" : htxx.get(key));
            });
            tempHtxx.put("fwdy", fwdyDefauleValue);
            jsonObject.remove("htxx");
            jsonObject.put("htxx", tempHtxx);
        }
        return exchangeBeanRequestService.request("rd_tsswxx_url", jsonObject);
    }

    /**
     * 根据xmid查询流程附件材料
     *
     * @param xmid xmid
     * @return
     * @Date 2021/6/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object queryFjxxByxmid(String xmid) {
        LOGGER.info(xmid);
        //根据xmid查询到gzlslid
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid不可为空！");
        }
        BdcXmQO xmQo = new BdcXmQO();
        xmQo.setXmid(xmid);
        List<BdcXmDO> bdcXmDOS = xmFeignService.listBdcXm(xmQo);
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
            String gzlslid = bdcXmDOS.get(0).getGzlslid();
            FjclNantongSwDTO fjclNantongSwDTO = new FjclNantongSwDTO();
            fjclNantongSwDTO.setXmid(xmid);
            //查询受理收件材料信息
            List<FjxxBean> fjxxBeans = new ArrayList<>();
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                for (BdcSlSjclDO slSjclDO : bdcSlSjclDOList) {
                    if (StringUtils.isNotBlank(slSjclDO.getClmc())) {
                        FjxxBean fjxxBean = new FjxxBean();
                        fjxxBean.setClmc(slSjclDO.getClmc());
                        fjxxBean.setFs(null != slSjclDO.getFs() ? String.valueOf(slSjclDO.getFs()) : "");
                        fjxxBean.setYs(null != slSjclDO.getYs() ? String.valueOf(slSjclDO.getYs()) : "");
                        fjxxBean.setFjlx(null != slSjclDO.getSjlx() ? String.valueOf(slSjclDO.getSjlx()) : "");
                        fjxxBean.setMrfjys(null != slSjclDO.getMrfs() ? String.valueOf(slSjclDO.getMrfs()) : "");
                        List<StorageDto> list = storageClient.listStoragesByName("clientId", gzlslid, null, slSjclDO.getClmc(), 1, 0);
                        if (CollectionUtils.isEmpty(list)) {
                            continue;
                        }

                        List<StorageDto> listFile = storageClient.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null, 0, null);
                        if (CollectionUtils.isEmpty(listFile)) {
                            continue;
                        }
                        List<FjclmxDTO> fjclmxDTOS = new ArrayList<>();
                        for (StorageDto storageDto : listFile) {
                            FjclmxDTO fjclmxDTO = new FjclmxDTO();
                            fjclmxDTO.setFjid(storageDto.getId());
                            fjclmxDTO.setFjmc(storageDto.getName());
                            // 直接推送到税务附件过大会导致传输慢，因此改为传附件URL地址，由税务异步调用下载附件，字段还是继续沿用base64字段
                            String url = storageDto.getDownUrl();
                            if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(swFjIpPortStandard)) {
                                // 大云附件地址例如：http://192.168.2.87:8030/storage/rest/files/download/ff8080817399496301740064a45a0363
                                url = "http://" + swFjIpPortStandard + url.substring(url.indexOf("/storage"));
                            }
                            fjclmxDTO.setFjurl(url);
                            fjclmxDTOS.add(fjclmxDTO);
                        }
                        fjxxBean.setClnr(fjclmxDTOS);
                        fjxxBeans.add(fjxxBean);
                    }

                }
                fjclNantongSwDTO.setFjxx(fjxxBeans);
            }
            return fjclNantongSwDTO;
        } else {
            throw new AppException("xmid未查到流程数据，请检查！");
        }

    }

    /**
     * @param slbh 受理编号
     * @return
     * @Date 2021/7/20
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object fjxxDistinguishSfts(String slbh) {
        //根据受理编号先查登记库流程，如果有，则不用查受理库
        LOGGER.info("税务查询附件接口，区分一窗流程已推送和未推送，受理编号：{}", slbh);
        LOGGER.info("查询开始：{}", new Date());
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh不可为空！");
        }
        BdcXmQO xmQo = new BdcXmQO();
        xmQo.setSlbh(slbh);
        List<BdcXmDO> xmDOList = xmFeignService.listBdcXm(xmQo);
        if (CollectionUtils.isNotEmpty(xmDOList)) {
            //说明已推送到登记，不用再去查受理库
            String gzlslid = xmDOList.get(0).getGzlslid();
            FjxxDistinguishSftsDTO fjxxDistinguishSftsDTO = organizationFjxx(gzlslid);
            fjxxDistinguishSftsDTO.setSlbh(slbh);
            LOGGER.info("查询结束1：{}", new Date());

            return fjxxDistinguishSftsDTO;
        } else {
            //未推送到登记，去查受理库
            BdcSlJbxxDO jbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "1");
            if (null != jbxxDO) {
                String gzlslid = jbxxDO.getGzlslid();
                FjxxDistinguishSftsDTO fjxxDistinguishSftsDTO = organizationFjxx(gzlslid);
                fjxxDistinguishSftsDTO.setSlbh(slbh);
                LOGGER.info("查询结束2：{}", new Date());

                return fjxxDistinguishSftsDTO;
            }
        }
        return new FjxxDistinguishSftsDTO();
    }

    /**
     * 根据工作流实例id查询附件材料
     *
     * @param gzlslid 工作流实例id
     * @return
     * @Date 2021/7/20
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public FjxxDistinguishSftsDTO organizationFjxx(String gzlslid) {
        FjxxDistinguishSftsDTO distinguishSftsDTO = new FjxxDistinguishSftsDTO();
        //查询受理收件材料信息
        List<FjxxBeanSfts> fjxxBeans = new ArrayList<>();
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            for (BdcSlSjclDO slSjclDO : bdcSlSjclDOList) {
                if (StringUtils.isNotBlank(slSjclDO.getClmc())) {
                    FjxxBeanSfts fjxxBean = new FjxxBeanSfts();
                    fjxxBean.setClmc(slSjclDO.getClmc());
                  /*  fjxxBean.setFs(null != slSjclDO.getFs() ? String.valueOf(slSjclDO.getFs()) : "");
                    fjxxBean.setYs(null != slSjclDO.getYs() ? String.valueOf(slSjclDO.getYs()) : "");
                    fjxxBean.setFjlx(null != slSjclDO.getSjlx() ? String.valueOf(slSjclDO.getSjlx()) : "");
                    fjxxBean.setMrfjys(null != slSjclDO.getMrfs() ? String.valueOf(slSjclDO.getMrfs()) : "");*/
                    List<StorageDto> list = storageClient.listStoragesByName("clientId", gzlslid, null, slSjclDO.getClmc(), 1, 0);
                    if (CollectionUtils.isEmpty(list)) {
                        continue;
                    }

                    List<StorageDto> listFile = storageClient.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null, 0, null);
                    if (CollectionUtils.isEmpty(listFile)) {
                        continue;
                    }
                    List<FjclmxDTO> fjclmxDTOS = new ArrayList<>();
                    for (StorageDto storageDto : listFile) {
                        FjclmxDTO fjclmxDTO = new FjclmxDTO();
                        fjclmxDTO.setFjid(storageDto.getId());
                        fjclmxDTO.setFjmc(storageDto.getName());
                        //根据id查询base64码
                        BaseResultDto resultDto = storageClient.downloadBase64(storageDto.getId());
                        if (resultDto != null && resultDto.getCode() == 0) {
                            if (StringUtils.isNotBlank(resultDto.getMsg())) {
                                fjclmxDTO.setFjnr(resultDto.getMsg());
                            }
                        } else {
                            throw new AppException("获取文件base64失败！");
                        }
                        fjclmxDTOS.add(fjclmxDTO);
                    }
                    fjxxBean.setClnr(fjclmxDTOS);
                    fjxxBeans.add(fjxxBean);
                }

            }
            distinguishSftsDTO.setFjxx(fjxxBeans);
        }

        return distinguishSftsDTO;
    }


    /**
     * 根据抵押合同号查询银行抵押合同文件
     *
     * @param htbh
     * @return
     * @Date 2021/12/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object getDyaHtxx(String htbh) {
        LOGGER.info("抵押合同编号：{}", htbh);
       /* String testUrl = "http://192.168.2.71:8669/realestate-certificate/rest/v1.0/zs/{zsid}?access_token=70c4fc20-90f3-4a66-947a-7fd90499d22d";
        testUrl = testUrl.replace("{zsid}",htbh);*/

        if (StringUtils.isBlank(htbh)) {
            throw new MissingArgumentException("缺失抵押合同编号参数，请检查！");
        }
        yhDyhtYrl.replace("{contractNumber}", htbh);
        HttpGet httpGet = new HttpGet(yhDyhtYrl);
        String response = "";
        try {
            response = httpClientService.doGet(httpGet);
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", yhDyhtYrl, htbh, e);
            throw new AppException("httpGet 银行抵押合同文件请求异常");
        }
        JSONObject responseObject = JSONObject.parseObject(response);
        LOGGER.info("抵押合同返回：{}", response);

        return responseObject;
    }


    /**
     * 1.3	任务状态接收【A003】
     *
     * @param yrbRwztTaxbizml 任务状态接收
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbRwztDTO> tsRwzt(YrbRwztTaxbizml yrbRwztTaxbizml) {
        if (Objects.isNull(yrbRwztTaxbizml)) {
            throw new MissingArgumentException("增量房计税信息不能全为空！");
        }
        String rwzt = "";
        if (StringUtils.isNotBlank(yrbRwztTaxbizml.getRwjshouselist().getRWZT())) {
            rwzt = yrbRwztTaxbizml.getRwjshouselist().getRWZT();
        }
        String rwzt_dsf = getDsfZdDzxx("DSF_ZD_RWZT", "SW", rwzt);
        yrbRwztTaxbizml.getRwjshouselist().setRWZT(rwzt_dsf);

        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbRwztTaxbizml.getRwjshouselist().getSJGSDQ())) {
            sjgsdq = yrbRwztTaxbizml.getRwjshouselist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbRwztTaxbizml.getRwjshouselist().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbRwztTaxbizml, YrbRwztTaxbizml.class);

            LOGGER.info("请求税务任务状态接收信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("RWZT", data);
           /* nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "\t<RWJSHOUSELIST> \n" +
                    "\t\t<FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "\t</RWJSHOUSELIST>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求RWZT返回码：" + res.getResponseCode());
            LOGGER.info("请求RWZT返回信息：" + res.getResponseMessage());
            LOGGER.info("请求RWZT返回数据：" + res.getResponseData());
            YrbRwztDTO yrbRwztDTO = new YrbRwztDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbRwztDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbRwztDTO.class);
                LOGGER.info("税务任务状态接收返回值：{}", yrbRwztDTO.toString());
                if (yrbRwztDTO.getRwjshouselist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbRwztDTO);
                } else {
                    return BdcCommonResponse.fail(yrbRwztDTO.getRwjshouselist().getFhxx(), yrbRwztDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbRwztDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送RWZT失败！");
    }

    // 获取第三方对照信息
    public String getDsfZdDzxx(String zdbs, String dsfzdbs, String bdczdz) {
        //数据归属地区进行对照
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setBdczdz(bdczdz);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfzdbs);
        BdcZdDsfzdgxDO result = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        LOGGER.info("---第三方字典返回值:{},查询参数:{}", result, bdcZdDsfzdgxDO);
        if (null != result && StringUtils.isNotBlank(result.getDsfzdz())) {
            return result.getDsfzdz();
        }
        return bdczdz;
    }

    /**
     * 1.4.	增量房计税信息获取【A004】
     *
     * @param yrbZlfskxxTaxbizml 增量房计税信息获取
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbZlfjsxxDTO> zlfSkxxhq(YrbZlfskxxTaxbizml yrbZlfskxxTaxbizml) {
        if (Objects.isNull(yrbZlfskxxTaxbizml)) {
            throw new MissingArgumentException("增量房计税信息不能全为空！");
        }

        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbZlfskxxTaxbizml.getZlfskxxhqlist().getSJGSDQ())) {
            sjgsdq = yrbZlfskxxTaxbizml.getZlfskxxhqlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbZlfskxxTaxbizml.getZlfskxxhqlist().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbZlfskxxTaxbizml, YrbZlfskxxTaxbizml.class);

            LOGGER.info("南通请求税务推送文件信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("ZLFSKXXHQ", data);
            /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "\t <ZLFJSJGLIST> \n" +
                    "\t    <FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "  </ZLFJSJGLIST> \n" +
                    "<ZLFSKXXHQLIST> \n" +
                    "\t\t<NSRMC>纳税人名称</NSRMC>\n" +
                    "\t\t<ZSXMMC>征收项目名称</ZSXMMC>\n" +
                    "\t\t<ZSPMMC>征收品目名称</ZSPMMC>\n" +
                    "\t\t<ZSZMMC>征收子目名称</ZSZMMC>\n" +
                    "\t\t<YSX>1</YSX>\n" +
                    "\t\t<JCX>1</JCX>\n" +
                    "\t\t<SL>0</SL>\n" +
                    "\t\t<YNSE>0</YNSE>\n" +
                    "\t\t<JMXZ>0</JMXZ>\n" +
                    "\t\t<JMSE>0</JMSE>\n" +
                    "\t\t<YBTSE>0</YBTSE>\n" +
                    "\t</ZLFSKXXHQLIST>\n" +
                    "<ZLFSKXXHQLIST> \n" +
                    "\t\t<NSRMC>纳税人名称</NSRMC>\n" +
                    "\t\t<ZSXMMC>征收项目名称</ZSXMMC>\n" +
                    "\t\t<ZSPMMC>征收品目名称</ZSPMMC>\n" +
                    "\t\t<ZSZMMC>征收子目名称</ZSZMMC>\n" +
                    "\t\t<YSX>1</YSX>\n" +
                    "\t\t<JCX>1</JCX>\n" +
                    "\t\t<SL>0</SL>\n" +
                    "\t\t<YNSE>0</YNSE>\n" +
                    "\t\t<JMXZ>0</JMXZ>\n" +
                    "\t\t<JMSE>0</JMSE>\n" +
                    "\t\t<YBTSE>0</YBTSE>\n" +
                    "\t</ZLFSKXXHQLIST>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求ZLFSKXXHQ返回码：" + res.getResponseCode());
            LOGGER.info("请求ZLFSKXXHQ返回信息：" + res.getResponseMessage());
            LOGGER.info("请求ZLFSKXXHQ返回数据：" + res.getResponseData());
            YrbZlfjsxxDTO yrbZlfjsxxDTO = new YrbZlfjsxxDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbZlfjsxxDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbZlfjsxxDTO.class);
                LOGGER.info("增量房计税信息返回值：{}", yrbZlfjsxxDTO.toString());
                if (yrbZlfjsxxDTO.getZlfjsjglist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbZlfjsxxDTO);
                } else {
                    return BdcCommonResponse.fail(yrbZlfjsxxDTO.getZlfjsjglist().getFhxx(), yrbZlfjsxxDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbZlfjsxxDTO);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }

    /**
     * 2.12.	存量房计税信息获取【A005】
     *
     * @param yrbClfskxxhqTaxbizml 存量房计税信息获取
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbClfskxxhqDTO> clfSkxxhq(YrbClfskxxhqTaxbizml yrbClfskxxhqTaxbizml) {
        if (Objects.isNull(yrbClfskxxhqTaxbizml)) {
            throw new MissingArgumentException("存量房计税信息获取不能全为空！");
        }

        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbClfskxxhqTaxbizml.getClfskxxhqlist().getSJGSDQ())) {
            sjgsdq = yrbClfskxxhqTaxbizml.getClfskxxhqlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbClfskxxhqTaxbizml.getClfskxxhqlist().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbClfskxxhqTaxbizml, YrbClfskxxhqTaxbizml.class);

            LOGGER.info("存量房计税信息获取信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("CLFSKXXHQ", data);
          /*  nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "\t <CLFFJSJGLIST> \n" +
                    "\t    <FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "\t\t<HDJSJG>核定计税价格</HDJSJG>\n" +
                    "  </CLFFJSJGLIST> \n" +
                    "<CLFSKXXHQLIST> \n" +
                    "\t\t<DJXH>登记序号</DJXH>\n" +
                    "\t\t<NSRMC>纳税人名称</NSRMC>\n" +
                    "\t\t<ZSXMMC>征收项目名称</ZSXMMC>\n" +
                    "\t\t<ZSPMMC>征收品目名称</ZSPMMC>\n" +
                    "\t\t<ZSZMMC>征收子目名称</ZSZMMC>\n" +
                    "\t\t<YSX>应税项</YSX>\n" +
                    "\t\t<JCX>减免项</JCX>\n" +
                    "\t\t<SL>税率</SL>\n" +
                    "\t\t<YNSE>应纳税额</YNSE>\n" +
                    "\t\t<JMXZ>减免性质</JMXZ>\n" +
                    "\t\t<JMSE>减免税额</JMSE>\n" +
                    "\t\t<YBTSE>本期应补（退）税额</YBTSE>\n" +
                    "\t</CLFSKXXHQLIST>\n" +
                    "</TAXBIZML>\n");*/
            LOGGER.info("请求CLFSKXXHQ返回码：" + res.getResponseCode());
            LOGGER.info("请求CLFSKXXHQ返回信息：" + res.getResponseMessage());
            LOGGER.info("请求CLFSKXXHQ返回数据：" + res.getResponseData());
            YrbClfskxxhqDTO yrbClfskxxhqDTO = new YrbClfskxxhqDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbClfskxxhqDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbClfskxxhqDTO.class);
                LOGGER.info("存量房计税信息接收返回值：{}", yrbClfskxxhqDTO.toString());
                if (yrbClfskxxhqDTO.getClfjsjglist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbClfskxxhqDTO);
                } else {
                    return BdcCommonResponse.fail(yrbClfskxxhqDTO.getClfjsjglist().getFhxx(), yrbClfskxxhqDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbClfskxxhqDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送RWZT失败！");
    }


    /**
     * 1.5.	影像资料信息接收【A006】
     *
     * @param yrbYxzlTaxbizml 税务文件信息
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbYxzlResponseDTO> yxzlJs(YrbYxzlTaxbizml yrbYxzlTaxbizml) {
        if (Objects.isNull(yrbYxzlTaxbizml)) {
            throw new MissingArgumentException("影像资料信息不能全为空！");
        }
        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbYxzlTaxbizml.getYrbYxzlxxjsList().getSJGSDQ())) {
            sjgsdq = yrbYxzlTaxbizml.getYrbYxzlxxjsList().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbYxzlTaxbizml.getYrbYxzlxxjsList().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbYxzlTaxbizml, YrbYxzlTaxbizml.class);

            LOGGER.info("南通请求税务推送文件信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("YXZLXXJS", data);
           /* nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "\t<YXZLXXJSLIST> \n" +
                    "\t\t<FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "\t</YXZLXXJSLIST>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求YXZLXXJS返回码：" + res.getResponseCode());
            LOGGER.info("请求YXZLXXJS返回信息：" + res.getResponseMessage());
            LOGGER.info("请求YXZLXXJS返回数据：" + res.getResponseData());
            YrbYxzlResponseDTO yxzlResponseDTO = new YrbYxzlResponseDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yxzlResponseDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbYxzlResponseDTO.class);
                LOGGER.info("税务文件信息返回值：{}", yxzlResponseDTO.toString());
                if (yxzlResponseDTO.getYxzlxxjsList().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yxzlResponseDTO);
                } else {
                    return BdcCommonResponse.fail(yxzlResponseDTO.getYxzlxxjsList().getFhxx(), yxzlResponseDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yxzlResponseDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }


    /**
     * 1.7.	契税完税信息获取【A009】
     *
     * @param yrbQswsxxhqTzxbizml 契税完税信息获取
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbQswsxxhqDTO> qsWsxxhq(YrbQswsxxhqTzxbizml yrbQswsxxhqTzxbizml) {
        if (Objects.isNull(yrbQswsxxhqTzxbizml)) {
            throw new MissingArgumentException("契税完税信息获取不能全为空！");
        }

        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbQswsxxhqTzxbizml.getFcjyskxxlist().getSJGSDQ())) {
            sjgsdq = yrbQswsxxhqTzxbizml.getFcjyskxxlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbQswsxxhqTzxbizml.getFcjyskxxlist().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbQswsxxhqTzxbizml, YrbQswsxxhqTzxbizml.class);

            LOGGER.info("南通契税完税信息获取信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("QSWSXXHQ", data);
            /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML> \n" +
                    "  <FCJYSKXXLIST> \n" +
                    "\t\t<FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "\t</FCJYSKXXLIST>\n" +
                    "<QSWSFJXXLIST> \n" +
                    "\t\t<FJLX>附件类型</FJLX>\n" +
                    "\t\t<FJID>附件ID</FJID>\n" +
                    "<WJSJ>文件数据</WJSJ>\n" +
                    "</QSWSFJXXLIST> \n" +
                    "</TAXBIZML>\n");*/
            LOGGER.info("请求QSWSXXHQ返回码：" + res.getResponseCode());
            LOGGER.info("请求QSWSXXHQ返回信息：" + res.getResponseMessage());
            LOGGER.info("请求QSWSXXHQ返回数据：" + res.getResponseData());
            YrbQswsxxhqDTO yrbQswsxxhqDTO = new YrbQswsxxhqDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbQswsxxhqDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbQswsxxhqDTO.class);
                LOGGER.info("房产交易缴款二维码获取返回值：{}", yrbQswsxxhqDTO.toString());
                if (yrbQswsxxhqDTO.getFcjyskxxlist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbQswsxxhqDTO);
                } else {
                    return BdcCommonResponse.fail(yrbQswsxxhqDTO.getFcjyskxxlist().getFhxx(), yrbQswsxxhqDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbQswsxxhqDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }

    /**
     * 2.10.	申报状态信息【A010】
     *
     * @param yrbSbztcxTaxbizml 申报状态信息
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbSbztcxDTO> sbztCx(YrbSbztcxTaxbizml yrbSbztcxTaxbizml) {
        if (Objects.isNull(yrbSbztcxTaxbizml)) {
            throw new MissingArgumentException("查询申报状态信息不能全为空！");
        }

        // 数据归属地区处理
        if (CollectionUtils.isNotEmpty(yrbSbztcxTaxbizml.getSbztxxlist())) {
            for (YrbSbztcxRequestDTO yrbSbztcxRequestDTO : yrbSbztcxTaxbizml.getSbztxxlist()) {
                String sjgsdq = "";
                if (StringUtils.isNotBlank(yrbSbztcxRequestDTO.getSJGSDQ())) {
                    sjgsdq = yrbSbztcxRequestDTO.getSJGSDQ();
                }
                String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
                yrbSbztcxRequestDTO.setSJGSDQ(sjgsdq_dsf);
            }
        }


        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbSbztcxTaxbizml, YrbSbztcxTaxbizml.class);

            LOGGER.info("查询申报状态信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("SBZTXX", data);
           /* nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "<SBZTXXLIST>\n" +
                    "<SJBH>收件编号</SJBH>\n" +
                    "<SHZT>审核状态</SHZT>\n" +
                    "<TSXX>状态说明</TSXX>\n" +
                    "<BZ>备注</BZ>\n" +
                    "<CLR>处理人</CLR>\n" +
                    "</SBZTXXLIST>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求SBZTXX返回码：" + res.getResponseCode());
            LOGGER.info("请求SBZTXX返回信息：" + res.getResponseMessage());
            LOGGER.info("请求SBZTXX返回数据：" + res.getResponseData());
            YrbSbztcxDTO yrbSbztcxResponseDTO = new YrbSbztcxDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbSbztcxResponseDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbSbztcxDTO.class);
                LOGGER.info("SBZTXX获取返回值：{}", yrbSbztcxResponseDTO.toString());
                return BdcCommonResponse.ok(yrbSbztcxResponseDTO);

            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbSbztcxResponseDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }


    /**
     * 1.9.	房产交易申报信息获取【A014】
     *
     * @param yrbFcjysbxxTaxbizml 房产交易申报信息获取
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjysbxxDTO> fcjySbxx(YrbFcjysbxxTaxbizml yrbFcjysbxxTaxbizml) {
        if (Objects.isNull(yrbFcjysbxxTaxbizml)) {
            throw new MissingArgumentException("房产交易申报信息不能全为空！");
        }

        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbFcjysbxxTaxbizml.getFcjysbxxlist().getSJGSDQ())) {
            sjgsdq = yrbFcjysbxxTaxbizml.getFcjysbxxlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbFcjysbxxTaxbizml.getFcjysbxxlist().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbFcjysbxxTaxbizml, YrbFcjysbxxTaxbizml.class);

            LOGGER.info("南通请求税务房产交易申报信息信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("FCJYSBXX", data);
           /* nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML> \n" +
                    "  <FCJYSBXXLIST> \n" +
                    "\t\t<FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "\t</FCJYSBXXLIST>\n" +
                    "<FCJYSBFJXXLIST> \n" +
                    "\t\t<FJLX>附件类型</FJLX>\n" +
                    "\t\t<FJID>附件ID</FJID>\n" +
                    "<WJSJ>文件数据</WJSJ>\n" +
                    "</FCJYSBFJXXLIST> \n" +
                    "</TAXBIZML>\n");*/
            LOGGER.info("请求FCJYSBXX返回码：" + res.getResponseCode());
            LOGGER.info("请求FCJYSBXX返回信息：" + res.getResponseMessage());
            LOGGER.info("请求FCJYSBXX返回数据：" + res.getResponseData());
            YrbFcjysbxxDTO yrbFcjysbxxDTO = new YrbFcjysbxxDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbFcjysbxxDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbFcjysbxxDTO.class);
                LOGGER.info("房产交易申报信息返回值：{}", yrbFcjysbxxDTO.toString());
                if (yrbFcjysbxxDTO.getFcjysbxxlist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbFcjysbxxDTO);
                } else {
                    return BdcCommonResponse.fail(yrbFcjysbxxDTO.getFcjysbxxlist().getFhxx(), yrbFcjysbxxDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbFcjysbxxDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }


    /**
     * 1.10.	房产交易申报信息确认【A015】
     *
     * @param yrbFcjysbxxQrTaxbizml 房产交易申报信息确认
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjysbqrResponse> fcjySbxxqr(YrbFcjysbxxQrTaxbizml yrbFcjysbxxQrTaxbizml) {
        if (Objects.isNull(yrbFcjysbxxQrTaxbizml)) {
            throw new MissingArgumentException("房产交易申报信息不能全为空！");
        }
        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist().getSJGSDQ())) {
            sjgsdq = yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbFcjysbxxQrTaxbizml, YrbFcjysbxxQrTaxbizml.class);

            LOGGER.info("南通请求税务房产交易申报信息信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("FCJYSBXXQR", data);
            /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "\t<FCJYSBQRXXLIST> \n" +
                    "\t\t<FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "\t</FCJYSBQRXXLIST>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求FCJYSBXXQR返回码：" + res.getResponseCode());
            LOGGER.info("请求FCJYSBXXQR返回信息：" + res.getResponseMessage());
            LOGGER.info("请求FCJYSBXXQR返回数据：" + res.getResponseData());
            YrbFcjysbqrResponse yrbFhmResponse = new YrbFcjysbqrResponse();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbFhmResponse = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbFcjysbqrResponse.class);
                LOGGER.info("房产交易申报信息确认返回值：{}", yrbFhmResponse.toString());
                if (yrbFhmResponse.getFcjysbqrxxlist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbFhmResponse);
                } else {
                    return BdcCommonResponse.fail(yrbFhmResponse.getFcjysbqrxxlist().getFhxx(), yrbFhmResponse);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbFhmResponse);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }

    /**
     * 1.11.	房产交易应缴款列表【A017】
     *
     * @param yrbFcjyyjkTaxbizml 房产交易应缴款列表
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjyyjkDTO> queryFcjyyjkList(YrbFcjyyjkTaxbizml yrbFcjyyjkTaxbizml) {
        if (Objects.isNull(yrbFcjyyjkTaxbizml)) {
            throw new MissingArgumentException("房产交易应缴款列表不能全为空！");
        }

        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbFcjyyjkTaxbizml.getFcjyyjskList().getSJGSDQ())) {
            sjgsdq = yrbFcjyyjkTaxbizml.getFcjyyjskList().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbFcjyyjkTaxbizml.getFcjyyjskList().setSJGSDQ(sjgsdq_dsf);
        // 收件编号处理，将数据归属地区+收件编号拼起来
        YrbFcjyyjkRequestDTO yrbFcjyyjkRequestDTO = yrbFcjyyjkTaxbizml.getFcjyyjskList();
        yrbFcjyyjkTaxbizml.getFcjyyjskList().setSJBH(yrbFcjyyjkRequestDTO.getSJGSDQ() + yrbFcjyyjkRequestDTO.getSJBH());

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbFcjyyjkTaxbizml, YrbFcjyyjkTaxbizml.class);

            LOGGER.info("南通请求房产交易应缴款列表信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("QUERYfCJYYJKLIST", data);
            /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML> \n" +
                    "  <FCJYYJKJG>\n" +
                    "     <FHM>0</FHM>\n" +
                    "     <FHXX>返回信息</FHXX>\n" +
                    "  </FCJYYJKJG>\n" +
                    "  <FCJYYJSKSJ>\n" +
                    "      <FCJYYJSKLIST> \n" +
                    "    \t\t<NSRSBH>纳税人识别号</NSRSBH>\n" +
                    "\t\t<NSRMC>纳税人名称</NSRMC>\n" +
                    "\t\t<DJXH>登记序号</DJXH>\n" +
                    "\t     <ZGSWSKFJDM>主管税务科所分局代码</ZGSWSKFJDM>\n" +
                    "\t     <PZXH>凭证序号</PZXH>\n" +
                    "\t     <YZPZMXXH>应征凭证序号明细序号</YZPZMXXH>\n" +
                    "\t     <YZPZZLDM>应征凭证种类代码</YZPZZLDM>\n" +
                    "          <SKSSQQ>税款所属期起</SKSSQQ>\n" +
                    "          <SKSSQZ>税款所属期起</SKSSQZ>\n" +
                    "\t     <YBTSE>应补（退）税额</YBTSE>\n" +
                    "\t     <JKZT>缴款状态</JKZT>\n" +
                    "     </FCJYYJSKLIST>\n" +
                    "    <FCJYYJSKLIST> \n" +
                    "    \t\t<NSRSBH>纳税人识别号</NSRSBH>\n" +
                    "\t\t<NSRMC>纳税人名称</NSRMC>\n" +
                    "\t\t<DJXH>登记序号</DJXH>\n" +
                    "\t     <ZGSWSKFJDM>主管税务科所分局代码</ZGSWSKFJDM>\n" +
                    "\t     <PZXH>凭证序号</PZXH>\n" +
                    "\t     <YZPZMXXH>应征凭证序号明细序号</YZPZMXXH>\n" +
                    "\t     <YZPZZLDM>应征凭证种类代码</YZPZZLDM>\n" +
                    "          <SKSSQQ>税款所属期起</SKSSQQ>\n" +
                    "          <SKSSQZ>税款所属期起</SKSSQZ>\n" +
                    "\t     <YBTSE>应补（退）税额</YBTSE>\n" +
                    "\t     <JKZT>缴款状态</JKZT>\n" +
                    "     </FCJYYJSKLIST>\n" +
                    "</FCJYYJSKSJ>\n" +
                    " </TAXBIZML>\n");*/
            LOGGER.info("请求QUERYfCJYYJKLIST返回码：" + res.getResponseCode());
            LOGGER.info("请求QUERYfCJYYJKLIST返回信息：" + res.getResponseMessage());
            LOGGER.info("请求QUERYfCJYYJKLIST返回数据：" + res.getResponseData());
            YrbFcjyyjkDTO yrbFcjyyjkResponse = new YrbFcjyyjkDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);
                YrbFhmResponse fhmResponse = new YrbFhmResponse();
                fhmResponse = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("FCJYYJKJG"), YrbFhmResponse.class);
                if (Constants.STATUS_SUCCESS.equals(fhmResponse.getFhm())) {
                    yrbFcjyyjkResponse = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("FCJYYJSKSJ"), YrbFcjyyjkDTO.class);
                    LOGGER.info("房产交易应缴款列表返回值：{}", yrbFcjyyjkResponse.toString());
                    return BdcCommonResponse.ok(yrbFcjyyjkResponse);
                } else {
                    return BdcCommonResponse.fail(fhmResponse.getFhxx(), yrbFcjyyjkResponse);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbFcjyyjkResponse);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }

    /**
     * 1.12.	房产交易缴款二维码获取【A018】
     *
     * @param yrbFcjyEwmTaxbizml 房产交易缴款二维码获取
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjyEwmDTO> getFcjyewmJkxx(YrbFcjyEwmTaxbizml yrbFcjyEwmTaxbizml) {
        if (Objects.isNull(yrbFcjyEwmTaxbizml)) {
            throw new MissingArgumentException("房产交易缴款二维码获取不能全为空！");
        }

        // 数据归属地区处理
        if (CollectionUtils.isNotEmpty(yrbFcjyEwmTaxbizml.getFcjyewmlist())) {
            for (YrbFcjyEwmRequest yrbFcjyEwmRequest : yrbFcjyEwmTaxbizml.getFcjyewmlist()) {
                String sjgsdq = "";
                if (StringUtils.isNotBlank(yrbFcjyEwmRequest.getSJGSDQ())) {
                    sjgsdq = yrbFcjyEwmRequest.getSJGSDQ();
                }
                String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
                yrbFcjyEwmRequest.setSJGSDQ(sjgsdq_dsf);
            }
        }

        String dataXml = null;
        for (YrbFcjyEwmRequest yrbFcjyEwmRequest : yrbFcjyEwmTaxbizml.getFcjyewmlist()) {
            yrbFcjyEwmRequest.setSJBH(yrbFcjyEwmRequest.getSJGSDQ() + yrbFcjyEwmRequest.getSJBH());
        }

        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbFcjyEwmTaxbizml, YrbFcjyEwmTaxbizml.class);

            LOGGER.info("南通请求房产交易应缴款列表信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("GETFCJYEWMJKXX", data);
            /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML> \n" +
                    "  <FCJYJKERMJG>\n" +
                    "     <FHM>返回码</FHM>\n" +
                    "     <FHXX>返回信息</FHXX>\n" +
                    "   </FCJYJKERMJG>\n" +
                    "   <KKEWM> \n" +
                    "\t\t<DZSPHM>电子税票号</DZSPHM>\n" +
                    "\t\t<KKJE>扣款金额</KKJE>\n" +
                    "\t\t<EWM>二维码</EWM>\n" +
                    "  </KKEWM>\n" +
                    "</TAXBIZML>\n");*/
            LOGGER.info("请求GETFCJYEWMJKXX返回码：" + res.getResponseCode());
            LOGGER.info("请求GETFCJYEWMJKXX返回信息：" + res.getResponseMessage());
            LOGGER.info("请求GETFCJYEWMJKXX返回数据：" + res.getResponseData());
            YrbFcjyEwmDTO yrbFcjyEwmDTO = new YrbFcjyEwmDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbFcjyEwmDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbFcjyEwmDTO.class);
                LOGGER.info("房产交易缴款二维码获取返回值：{}", yrbFcjyEwmDTO.toString());
                if (yrbFcjyEwmDTO.getFcjyjkermjg().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbFcjyEwmDTO);
                } else {
                    return BdcCommonResponse.fail(yrbFcjyEwmDTO.getFcjyjkermjg().getFhxx(), yrbFcjyEwmDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbFcjyEwmDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }


    /**
     * 1.13.	房产交易缴款结果查询【A019】
     *
     * @param yrbEwmjkxxTaxbizml 房产交易缴款结果查询
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbEwmjkxxDTO> queryEwmjkxx(YrbEwmjkxxTaxbizml yrbEwmjkxxTaxbizml) {
        if (Objects.isNull(yrbEwmjkxxTaxbizml)) {
            throw new MissingArgumentException("房产交易缴款结果查询不能全为空！");
        }
        // 数据归属地区处理
        if (CollectionUtils.isNotEmpty(yrbEwmjkxxTaxbizml.getFcjyzfquerylist())) {
            for (YrbEwmjkxxRequest yrbEwmjkxxRequest : yrbEwmjkxxTaxbizml.getFcjyzfquerylist()) {
                String sjgsdq = "";
                if (StringUtils.isNotBlank(yrbEwmjkxxRequest.getSJGSDQ())) {
                    sjgsdq = yrbEwmjkxxRequest.getSJGSDQ();
                }
                String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
                yrbEwmjkxxRequest.setSJGSDQ(sjgsdq_dsf);
            }
        }

        String dataXml = null;
        for (YrbEwmjkxxRequest yrbEwmjkxxRequest : yrbEwmjkxxTaxbizml.getFcjyzfquerylist()) {
            yrbEwmjkxxRequest.setSJBH(yrbEwmjkxxRequest.getSJGSDQ() + yrbEwmjkxxRequest.getSJBH());
        }
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbEwmjkxxTaxbizml, YrbEwmjkxxTaxbizml.class);

            LOGGER.info("房产交易缴款结果查询信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("QUERYEWMJKXX", data);
           /* nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML> \n" +
                    "  <FCJYJKCXJG>\n" +
                    "<FHM>返回码</FHM>\n" +
                    "      <FHXX>返回信息</FHXX>\n" +
                    "   </FCJYJKCXJG>\n" +
                    "   <KKJG> \n" +
                    "\t\t<JKZT>缴款状态</JKZT>\n" +
                    "\t\t<JKFHXX>缴款返回信息</JKFHXX>\n" +
                    " </KKJG>\n" +
                    "</TAXBIZML>\n");*/
            LOGGER.info("请求QUERYEWMJKXX返回码：" + res.getResponseCode());
            LOGGER.info("请求QUERYEWMJKXX返回信息：" + res.getResponseMessage());
            LOGGER.info("请求QUERYEWMJKXX返回数据：" + res.getResponseData());
            YrbEwmjkxxDTO yrbEwmjkxxDTO = new YrbEwmjkxxDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbEwmjkxxDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbEwmjkxxDTO.class);
                LOGGER.info("房产交易缴款结果查询返回值：{}", yrbEwmjkxxDTO.toString());
                if (yrbEwmjkxxDTO.getFcjyjkcxjg().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbEwmjkxxDTO);
                } else {
                    return BdcCommonResponse.fail(yrbEwmjkxxDTO.getFcjyjkcxjg().getFhxx(), yrbEwmjkxxDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbEwmjkxxDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }


    /**
     * 增量房任务接收【A001】登记推送计税
     *
     * @param tsswDataDTO 增量房登记推送计税
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFhmResponse> zlfrwTs(TsswDataDTO tsswDataDTO) {
        if (Objects.isNull(tsswDataDTO)) {
            throw new MissingArgumentException("增量房登记推送计税不能全为空！");
        }
        //开始数据处理对照
        YrbZlfHouseRequest houseRequest = new YrbZlfHouseRequest();
        dozerBeanMapper.map(tsswDataDTO, houseRequest, "zlf_house");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(houseRequest);

        YrbZlfQtRequest qtRequest = new YrbZlfQtRequest();
        qtRequest.setSffgyrdp("Y");
        YrbZlfFcjyxxRequest fcjyxxRequest = new YrbZlfFcjyxxRequest();
        dozerBeanMapper.map(tsswDataDTO, fcjyxxRequest, "zlf_jyxx");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(fcjyxxRequest);
        YrbZlfFyjbxxRequest zlfFyjbxxRequest = new YrbZlfFyjbxxRequest();
        dozerBeanMapper.map(tsswDataDTO, zlfFyjbxxRequest, "zlf_jbxx");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(zlfFyjbxxRequest);

        List<YrbZlfJyfxxRequest> jyfxxList = new ArrayList<>();
        List<YrbZlfHyxxRequest> hyxxList = new ArrayList<>();
        List<YrbZlfWcnznRequest> wxnxxList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())) {
            for (BdcSlSqrSwDTO sqrSwDTO : tsswDataDTO.getSqrSwList()) {
                YrbZlfJyfxxRequest jyfxxRequest = new YrbZlfJyfxxRequest();
                dozerBeanMapper.map(sqrSwDTO, jyfxxRequest, "zlf_jyfxx");
                if(StringUtils.isBlank(jyfxxRequest.getDz())){
                    String dzDzz = getDsfZdDzxx("BDC_ZLF_DZ", "SW", "DEFAULT");
                    if(StringUtils.isNotBlank(dzDzz)){
                        jyfxxRequest.setDz(dzDzz);
                    }
                }
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(jyfxxRequest);

                YrbZlfHyxxRequest hyxxRequest = new YrbZlfHyxxRequest();
                dozerBeanMapper.map(sqrSwDTO, hyxxRequest, "zlf_hyxx");
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(hyxxRequest);

                YrbZlfWcnznRequest wcnznRequest = new YrbZlfWcnznRequest();
                dozerBeanMapper.map(sqrSwDTO, wcnznRequest, "zlf_wcnjzxx");
                if (CollectionUtils.isNotEmpty(sqrSwDTO.getBdcSlJtcyDOList())) {
                    for (BdcSlJtcyDO jtcyDO : sqrSwDTO.getBdcSlJtcyDOList()) {
                        dozerBeanMapper.map(jtcyDO, wcnznRequest, "zlf_wcnxx");
                        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(wcnznRequest);

                        wxnxxList.add(wcnznRequest);
                    }
                } else {
                    wcnznRequest.setCzwcnbj("2");
                }
                wxnxxList.add(wcnznRequest);
                jyfxxList.add(jyfxxRequest);
                hyxxList.add(hyxxRequest);
            }
        } else {
            return BdcCommonResponse.fail("申请人信息为空！请检查数据!" + tsswDataDTO.getXmid());
        }
        YrbZlfRwjshouseRequest rwjshouseRequest = new YrbZlfRwjshouseRequest();
        rwjshouseRequest.setHousevo(houseRequest);
        rwjshouseRequest.setJyfxxList(jyfxxList);
        rwjshouseRequest.setHyxxRequestList(hyxxList);
        rwjshouseRequest.setWcnznRequestList(wxnxxList);
        rwjshouseRequest.setFyjbxx(zlfFyjbxxRequest);
        rwjshouseRequest.setFcjycjxx(fcjyxxRequest);
        rwjshouseRequest.setQt(qtRequest);
        YrbZkfTsxxTaxbizml yrbZkfTsxxTaxbizml = new YrbZkfTsxxTaxbizml();
        yrbZkfTsxxTaxbizml.setRwjshouselist(rwjshouseRequest);


        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbZkfTsxxTaxbizml, YrbZkfTsxxTaxbizml.class);

            LOGGER.info("推送税务增量房信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("ZLFRWJS", data);
           /* nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML> \n" +
                    "  <RWJSHOUSELIST> \n" +
                    "\t    <FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "  </RWJSHOUSELIST> \n" +
                    "</TAXBIZML>\n");*/
            LOGGER.info("请求ZLFRWJS返回码：" + res.getResponseCode());
            LOGGER.info("请求ZLFRWJS返回信息：" + res.getResponseMessage());
            LOGGER.info("请求ZLFRWJS返回数据：" + res.getResponseData());
            YrbFhmResponse fhmResponse = new YrbFhmResponse();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                fhmResponse = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("RWJSHOUSELIST"), YrbFhmResponse.class);
                LOGGER.info("推送税务增量房信息返回值：{}", fhmResponse.toString());
                if (fhmResponse.getFhm().equals("0")) {
                    return BdcCommonResponse.ok(fhmResponse);
                } else {
                    return BdcCommonResponse.fail(fhmResponse.getFhxx(), fhmResponse);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), fhmResponse);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }


    /**
     * 存量房任务接收【A002】登记推送计税
     *
     * @param tsswDataDTO 存量房登记推送计税
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFhmResponse> clfrwTs(TsswDataDTO tsswDataDTO) {
        if (Objects.isNull(tsswDataDTO)) {
            throw new MissingArgumentException("存量房登记推送计税不能全为空！");
        }
        //开始数据处理对照
        YrbZlfHouseRequest houseRequest = new YrbZlfHouseRequest();
        dozerBeanMapper.map(tsswDataDTO, houseRequest, "zlf_house");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(houseRequest);

        YrbZlfQtRequest qtRequest = new YrbZlfQtRequest();
        qtRequest.setSffgyrdp("Y");
        YrbClfFcjyxxRequest fcjyxxRequest = new YrbClfFcjyxxRequest();
        dozerBeanMapper.map(tsswDataDTO, fcjyxxRequest, "clf_jyxx");
        if (Objects.nonNull(tsswDataDTO.getBdcxmfb())) {
            dozerBeanMapper.map(tsswDataDTO.getBdcxmfb(), fcjyxxRequest, "clf_xm_jyxx");
        }
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(fcjyxxRequest);
        YrbClfFyjbxxRequest zlfFyjbxxRequest = new YrbClfFyjbxxRequest();
        dozerBeanMapper.map(tsswDataDTO, zlfFyjbxxRequest, "clf_jbxx");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(zlfFyjbxxRequest);

        List<YrbZlfJyfxxRequest> jyfxxList = new ArrayList<>();
        List<YrbZlfHyxxRequest> hyxxList = new ArrayList<>();
        List<YrbZlfWcnznRequest> wxnxxList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())) {
            for (BdcSlSqrSwDTO sqrSwDTO : tsswDataDTO.getSqrSwList()) {
                YrbZlfJyfxxRequest jyfxxRequest = new YrbZlfJyfxxRequest();
                dozerBeanMapper.map(sqrSwDTO, jyfxxRequest, "zlf_jyfxx");
                if(StringUtils.isBlank(jyfxxRequest.getDz())){
                    String dzDzz = getDsfZdDzxx("BDC_ZLF_DZ", "SW", "DEFAULT");
                    if(StringUtils.isNotBlank(dzDzz)){
                        jyfxxRequest.setDz(dzDzz);
                    }
                }
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(jyfxxRequest);

                YrbZlfHyxxRequest hyxxRequest = new YrbZlfHyxxRequest();
                dozerBeanMapper.map(sqrSwDTO, hyxxRequest, "zlf_hyxx");
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(hyxxRequest);

                YrbZlfWcnznRequest wcnznRequest = new YrbZlfWcnznRequest();
                dozerBeanMapper.map(sqrSwDTO, wcnznRequest, "zlf_wcnjzxx");
                if (CollectionUtils.isNotEmpty(sqrSwDTO.getBdcSlJtcyDOList())) {
                    for (BdcSlJtcyDO jtcyDO : sqrSwDTO.getBdcSlJtcyDOList()) {
                        dozerBeanMapper.map(jtcyDO, wcnznRequest, "zlf_wcnxx");
                        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(wcnznRequest);

                        wxnxxList.add(wcnznRequest);
                    }
                } else {
                    wcnznRequest.setCzwcnbj("2");
                }
                if (CommonConstantUtils.QLRLB_YWR.equals(sqrSwDTO.getSqrlb())) {
                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxDO.setZdbs("SW_TSSWXX_GYFS");
                    bdcZdDsfzdgxDO.setBdczdz(String.valueOf(sqrSwDTO.getGyfs()));
                    bdcZdDsfzdgxDO.setDsfxtbs("SW");
                    BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                    if (result != null && StringUtils.isNotBlank(result.getBdczdz())) {
                        fcjyxxRequest.setZrfgyfs_dm(result.getDsfzdz());
                    }
                }
                wxnxxList.add(wcnznRequest);
                jyfxxList.add(jyfxxRequest);
                hyxxList.add(hyxxRequest);
            }
        } else {
            return BdcCommonResponse.fail("申请人信息为空！请检查数据!" + tsswDataDTO.getXmid());
        }
        YrbClfRwjshouseRequest rwjshouseRequest = new YrbClfRwjshouseRequest();
        rwjshouseRequest.setHousevo(houseRequest);
        rwjshouseRequest.setJyfxxList(jyfxxList);
        rwjshouseRequest.setHyxxRequestList(hyxxList);
        rwjshouseRequest.setWcnznRequestList(wxnxxList);
        rwjshouseRequest.setFyjbxx(zlfFyjbxxRequest);
        rwjshouseRequest.setFcjycjxx(fcjyxxRequest);
        rwjshouseRequest.setQt(qtRequest);
        YrbClfTsxxTaxbizml yrbClfTsxxTaxbizml = new YrbClfTsxxTaxbizml();
        yrbClfTsxxTaxbizml.setRwjshouselist(rwjshouseRequest);


        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbClfTsxxTaxbizml, YrbClfTsxxTaxbizml.class);

            LOGGER.info("推送税务存量房信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("CLFRWJS", data);
           /* nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML> \n" +
                    "  <RWJSHOUSELIST> \n" +
                    "\t    <FHM>返回码</FHM>\n" +
                    "\t\t<FHXX>返回信息</FHXX>\n" +
                    "\t\t<SJBH>收件编号</SJBH>\n" +
                    "\t\t<HTBH>合同编号</HTBH>\n" +
                    "\t\t<JYUUID>交易编号</JYUUID>\n" +
                    "\t\t<FWUUID>房屋编号</FWUUID>\n" +
                    "  </RWJSHOUSELIST> \n" +
                    "</TAXBIZML>\n");*/
            LOGGER.info("请求CLFRWJS返回码：" + res.getResponseCode());
            LOGGER.info("请求CLFRWJS返回信息：" + res.getResponseMessage());
            LOGGER.info("请求CLFRWJS返回数据：" + res.getResponseData());
            YrbFhmResponse fhmResponse = new YrbFhmResponse();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                fhmResponse = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("RWJSHOUSELIST"), YrbFhmResponse.class);
                LOGGER.info("推送税务存量房信息返回值：{}", fhmResponse.toString());
                if (fhmResponse.getFhm().equals("0")) {
                    return BdcCommonResponse.ok(fhmResponse);
                } else {
                    return BdcCommonResponse.fail(fhmResponse.getFhxx(), fhmResponse);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), fhmResponse);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }

    /**
     * 2.20	 契税完税凭证获取【A020】
     *
     * @param yrbQswspzhqTaxbizml 契税完税凭证获取
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbQswspzhqDTO> qswspzHq(YrbQswspzhqTaxbizml yrbQswspzhqTaxbizml) {
        if (Objects.isNull(yrbQswspzhqTaxbizml)) {
            throw new MissingArgumentException("契税完税凭证获取不能全为空！");
        }
        // 数据归属地区处理
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbQswspzhqTaxbizml.getFcjyqswspzlist().getSJGSDQ())) {
            sjgsdq = yrbQswspzhqTaxbizml.getFcjyqswspzlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbQswspzhqTaxbizml.getFcjyqswspzlist().setSJGSDQ(sjgsdq_dsf);

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbQswspzhqTaxbizml, YrbQswspzhqTaxbizml.class);

            LOGGER.info("契税完税凭证获取信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("QSWSPZHQ", data);
           /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "<FCJYQSWSPZLIST>\n" +
                    "<FHM>返回码</FHM>\n" +
                    "<FHXX>返回信息</FHXX>\n" +
                    "<SJBH>收件编号</SJBH>\n" +
                    "<HTBH>合同编号</HTBH>\n" +
                    "<WSFS>完税方式</WSFS>\n" +
                    "</FCJYQSWSPZLIST>\n" +
                    "<QSWSFJXXLIST>\n" +
                    "<FJLX>1001</FJLX>\n" +
                    "<FJID>11111</FJID>\n" +
                    "<WJSJ>{\"YXWJ\": \"JVBERi0xLjYKJeLjz9MKMSAwIG9iaiA8PC9TdWJ0eXBlL0Zvcm0vR\"}</WJSJ>\n" +
                    "</QSWSFJXXLIST>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求QSWSPZHQ返回码：" + res.getResponseCode());
            LOGGER.info("请求QSWSPZHQ返回信息：" + res.getResponseMessage());
            LOGGER.info("请求QSWSPZHQ返回数据：" + StringUtils.left(res.getResponseData(), 999));
            YrbQswspzhqDTO yrbQswspzhqDTO = new YrbQswspzhqDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbQswspzhqDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbQswspzhqDTO.class);
                LOGGER.info("契税完税凭证获取信息返回值：{}", yrbQswspzhqDTO.toString());
                if (yrbQswspzhqDTO.getFcjyqswspzlist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbQswspzhqDTO);
                } else {
                    return BdcCommonResponse.fail(yrbQswspzhqDTO.getFcjyqswspzlist().getFhxx(), yrbQswspzhqDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbQswspzhqDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }


    /**
     * 2.21. 契税联系单获取 【A021】
     *
     * @param yrbQslxdhqTaxbizml 契税联系单获取
     * @return 成功或失败
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbQslxdhqDTO> qslxdHq(YrbQslxdhqTaxbizml yrbQslxdhqTaxbizml) {
        if (Objects.isNull(yrbQslxdhqTaxbizml)) {
            throw new MissingArgumentException("契税完税凭证获取不能全为空！");
        }
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbQslxdhqTaxbizml.getFcjyqslxdlist().getSJGSDQ())) {
            sjgsdq = yrbQslxdhqTaxbizml.getFcjyqslxdlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbQslxdhqTaxbizml.getFcjyqslxdlist().setSJGSDQ(sjgsdq_dsf);
        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(yrbQslxdhqTaxbizml, YrbQslxdhqTaxbizml.class);

            LOGGER.info("契税联系单获取信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("QSLXDHQ", data);
            /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "<FCJYQSLXDLIST>\n" +
                    "<FHM>返回码</FHM>\n" +
                    "<FHXX>返回信息</FHXX>\n" +
                    "<SJBH>收件编号</SJBH>\n" +
                    "<HTBH>合同编号</HTBH>\n" +
                    "<QSLXDFJXXLIST>\n" +
                    "<FJLX>附件类型</FJLX>\n" +
                    "<FJID>附件 ID</FJID>\n" +
                    "<WJSJ>文件数据</WJSJ>\n" +
                    "</QSLXDFJXXLIST>\n" +
                    "</FCJYQSLXDLIST>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求QSWSPZHQ返回码：" + res.getResponseCode());
            LOGGER.info("请求QSWSPZHQ返回信息：" + res.getResponseMessage());
            LOGGER.info("请求QSWSPZHQ返回数据：" + res.getResponseData());
            YrbQslxdhqDTO yrbQslxdhqDTO = new YrbQslxdhqDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                JSONObject jsonObject = JSONObject.parseObject(json);

                yrbQslxdhqDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), YrbQslxdhqDTO.class);
                LOGGER.info("契税完税凭证获取信息返回值：{}", StringUtils.left(yrbQslxdhqDTO.toString(), 999));
                if (yrbQslxdhqDTO.getFcjyqslxdlist().getFhm().equals("0")) {
                    return BdcCommonResponse.ok(yrbQslxdhqDTO);
                } else {
                    return BdcCommonResponse.fail(yrbQslxdhqDTO.getFcjyqslxdlist().getFhxx(), yrbQslxdhqDTO);
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), yrbQslxdhqDTO);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("推送失败！");
    }

    /**
     * @param fcjydjkxxhqTaxbizml
     * @return BdcCommonResponse<FcjydjkxxhqDTO>
     * @description 房产交易银行端待缴款信息获取
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/20 15:50
     */
    public BdcCommonResponse<FcjydjkxxhqDTO> fcjydjkxxHq(FcjydjkxxhqTaxbizml fcjydjkxxhqTaxbizml) {
        if (Objects.isNull(fcjydjkxxhqTaxbizml)) {
            throw new MissingArgumentException("房产交易银行端待缴款信息获取不能全为空！");
        }

        String dataXml = null;
        try {
            dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(fcjydjkxxhqTaxbizml, FcjydjkxxhqTaxbizml.class);

            LOGGER.info("房产交易银行端待缴款信息获取信息：{}", dataXml);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            nklsjkResponse res = cl.getResponse("FCJYDJKXXHQ", data);

            /*nklsjkResponse res = new nklsjkResponse();
            res.setResponseCode(0);
            res.setResponseData("<TAXBIZML>\n" +
                    "    <JKXXGRID>\n" +
                    "        <FHM>返回码</FHM>\n" +
                    "        <FHXX>返回信息</FHXX>\n" +
                    "        <SJBH>收件编号</SJBH>\n" +
                    "        <HTBH>合同编号</HTBH>\n" +
                    "        <HJSE>200.04</HJSE>\n" +
                    "        <ZLFCLFBZ>增量房存量房标志</ZLFCLFBZ>\n" +
                    "        <JKXXGRIDLB>\n" +
                    "            <JKXX>\n" +
                    "                <DZSPHM>电子税票号码(银行端缴款凭证序号)</DZSPHM>\n" +
                    "                <ZRFCSFBZ>转让方承受方标志0 转让方1 承受方</ZRFCSFBZ>\n" +
                    "                <DJXH>登记序号</DJXH>\n" +
                    "                <NSRSBH>纳税人识别号</NSRSBH>\n" +
                    "                <NSRMC>纳税人名称</NSRMC>\n" +
                    "                <YBTSE>100.02</YBTSE>\n" +
                    "                <JKQX>缴款期限，格式：2020-12-18</JKQX>\n" +
                    "                <SKSSJGDM>税务机关代码</SKSSJGDM>\n" +
                    "                <KKFHM>扣款返回码</KKFHM>\n" +
                    "                <KKFHXX>扣款返回信息</KKFHXX>\n" +
                    "            </JKXX>\n" +
                    "            <JKXX>\n" +
                    "                <DZSPHM>电子税票号码(银行端缴款凭证序号)</DZSPHM>\n" +
                    "                <ZRFCSFBZ>转让方承受方标志0 转让方1 承受方</ZRFCSFBZ>\n" +
                    "                <DJXH>登记序号</DJXH>\n" +
                    "                <NSRSBH>纳税人识别号</NSRSBH>\n" +
                    "                <NSRMC>纳税人名称</NSRMC>\n" +
                    "                <YBTSE>100.02</YBTSE>\n" +
                    "                <JKQX>缴款期限，格式：2020-12-18</JKQX>\n" +
                    "                <SKSSJGDM>税务机关代码</SKSSJGDM>\n" +
                    "                <KKFHM>扣款返回码</KKFHM>\n" +
                    "                <KKFHXX>扣款返回信息</KKFHXX>\n" +
                    "            </JKXX>\n" +
                    "        </JKXXGRIDLB>\n" +
                    "    </JKXXGRID>\n" +
                    "</TAXBIZML>");*/
            LOGGER.info("请求FCJYDJKXXHQ返回码：" + res.getResponseCode());
            LOGGER.info("请求FCJYDJKXXHQ返回信息：" + res.getResponseMessage());
            LOGGER.info("请求FCJYDJKXXHQ返回数据：" + res.getResponseData());
            FcjydjkxxhqDTO fcjydjkxxhqDTO = new FcjydjkxxhqDTO();
            if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())) {
                try {
                    String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    fcjydjkxxhqDTO = JSONObject.parseObject(jsonObject.getString("TAXBIZML"), FcjydjkxxhqDTO.class);
                    LOGGER.info("房产交易银行端待缴款信息获取信息返回值：{}", fcjydjkxxhqDTO.toString());
                    if (fcjydjkxxhqDTO.getJkxxgrid().getFhm().equals("0")) {
                        return BdcCommonResponse.ok(fcjydjkxxhqDTO);
                    } else {
                        return BdcCommonResponse.fail(fcjydjkxxhqDTO.getJkxxgrid().getFhxx(), fcjydjkxxhqDTO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return BdcCommonResponse.fail("请求FCJYDJKXXHQ返回数据为" + XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData()));
                }
            } else {
                return BdcCommonResponse.fail(res.getResponseMessage(), fcjydjkxxhqDTO);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("编码异常");
            e.printStackTrace();
        }
        return BdcCommonResponse.fail("获取失败！");
    }
}