package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqZhlcSjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.service.inf.FjclService;
import cn.gtmap.realestate.exchange.util.Base64Util;
import cn.gtmap.realestate.exchange.util.FtpUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-05
 * @description ?????????  ???????????? ????????????
 */
@Service
public class FjclServiceImpl implements FjclService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    //????????????????????????????????????????????????????????????????????????ftp???????????????
    @Value("${dycj.wwsqftp.ip:}")
    private String ip;

    @Value("${dycj.wwsqftp.username:}")
    private String username;

    @Value("${dycj.wwsqftp.password:}")
    private String password;

    @Value("${dycj.wwsqftp.port:}")
    private String port;

    /** ?????????????????????url??????IP????????????,key???????????????IP????????????,value???????????????IP????????????
     *  * ???????????????{'http://192.168.2.87:8030':'http://192.168.2.70:8030'}
     *  */
    @Value("#{${wwsq.fjurl.ipportdz:null}}")
    private Map<String, String> fjurlDz;

    /**
     * @param bdcXmDOList
     * @param fjclList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????
     */
    @Override
    public void uploadAndSaveFjcl(List<BdcXmDO> bdcXmDOList, List<FjclDTO> fjclList) {
        String gzlslid = "";
        // space_id???gzlslid
        gzlslid = bdcXmDOList.get(0).getGzlslid();
        uploadAndSaveFjcl(gzlslid, fjclList);
    }

    /**
     * @param gzlslid
     * @param fjclList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????ID????????????????????????
     */
    @Override
    public void uploadAndSaveFjcl(String gzlslid, List<FjclDTO> fjclList) {
        LOGGER.info("?????????????????? gzlslid{}????????????:{}", gzlslid,fjclList);
        //?????????????????????,??????????????????
        fjclList =JSONObject.parseArray(JSONObject.toJSONString(fjclList),FjclDTO.class);
        // space_id???gzlslid
        String spaceId = gzlslid;
        if (StringUtil.isBlank(spaceId)) {
            return;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
        String djxl = "";
        if (CollectionUtils.isNotEmpty(list)) {
            djxl = list.get(0).getDjxl();
        }
        if (CollectionUtils.isNotEmpty(fjclList)) {
            for (FjclDTO fjcl : fjclList) {
                if (StringUtils.isNotBlank(fjcl.getClmc())) {
                    // ??????????????????????????? ???????????????
                    StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, spaceId, fjcl.getClmc(), "");
                    if (storageDto != null && CheckParameter.checkAnyParameter(storageDto)) {
                        if (CollectionUtils.isNotEmpty(fjcl.getFjclmxDTOList())) {
                            for (FjclmxDTO fjclmxDTO : fjcl.getFjclmxDTOList()) {
                                downAndUploadFjcl(fjclmxDTO, storageDto);
                            }
                        }
                        // ???????????????????????????,??????????????????????????????
                        BdcSlSjclDO bdcSlSjclDO;
                        List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, fjcl.getClmc());
                        if (CollectionUtils.isNotEmpty(bdcSlSjclDOS)) {
                            bdcSlSjclDO = bdcSlSjclDOS.get(0);
                        } else {
                            bdcSlSjclDO = new BdcSlSjclDO();
                        }
                        BeanUtils.copyProperties(fjcl, bdcSlSjclDO);
                        if (CollectionUtils.isEmpty(fjcl.getFjclmxDTOList()) && fjcl.getMrfjys() != null) {
                            bdcSlSjclDO.setYs(fjcl.getMrfjys());
                        }

                        // ??????????????????
                        String fjlx = fjcl.getFjlx();
                        if (StringUtils.isNotBlank(fjlx)) {
                            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                            bdcZdDsfzdgxDO.setZdbs("BDC_SL_ZD_SJLX");
                            bdcZdDsfzdgxDO.setDsfzdz(fjlx);
                            bdcZdDsfzdgxDO.setDsfxtbs("wwsq");
                            BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                            if (result != null && StringUtils.isNotBlank(result.getBdczdz())) {
                                bdcSlSjclDO.setSjlx(Integer.parseInt(result.getBdczdz()));
                            }
                        }
                        bdcSlSjclDO.setDjxl(djxl);
                        bdcSlSjclDO.setGzlslid(gzlslid);
                        bdcSlSjclDO.setWjzxid(storageDto.getId());
                        if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
                            bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                        } else {
                            bdcSlSjclFeignService.updateBdcSlSjcl(bdcSlSjclDO);
                        }
                    }
                }else{
                    LOGGER.error("??????????????????,??????????????????, gzlslid{},fjcl:{}", gzlslid,fjcl);
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @param fjclArray
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????
     */
    @Override
    public void asynUploadWithGzlslid(String gzlslid, JSONArray fjclArray) {
        new Thread() {
            @Override
            public void run() {
                List<FjclDTO> fjclDTOS = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(fjclArray)) {
                    for (int i = 0; i < fjclArray.size(); i++) {
                        FjclDTO dto = fjclArray.getObject(i, FjclDTO.class);
                        fjclDTOS.add(dto);
                    }
                }
                uploadAndSaveFjcl(gzlslid, fjclDTOS);
            }
        }.start();
    }

    /**
     * @param wwsqCjBdcXmResponseDTO
     * @param fjclList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public void asynUploadAndSaveFjcl(WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO, List<FjclDTO> fjclList) {
        if (wwsqCjBdcXmResponseDTO != null && CollectionUtils.isNotEmpty(wwsqCjBdcXmResponseDTO.getBdcXmDOList())
                && CollectionUtils.isNotEmpty(fjclList)) {

            new Thread() {
                @Override
                public void run() {
                    uploadAndSaveFjcl(wwsqCjBdcXmResponseDTO.getBdcXmDOList(), fjclList);
                }
            }.start();
        }
    }

    /**
     * @param zhlcSjclDTOList
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description ????????????
     */
    @Override
    public void asynUploadAndSaveFjclForZhlc(List<WwsqZhlcSjclDTO> zhlcSjclDTOList) {
        if (CollectionUtils.isNotEmpty(zhlcSjclDTOList)) {
            new Thread() {
                @Override
                public void run() {
                    uploadAndSaveFjclForZhlc(zhlcSjclDTOList);
                }
            }.start();
        }
    }

    /**
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description ????????????????????????????????????????????????????????????????????????
     */
    @Override
    public void uploadAndSaveFjclForZhlc(List<WwsqZhlcSjclDTO> zhlcSjclDTOList) {
        String gzlslid = "";
        // space_id???gzlslid
        gzlslid = zhlcSjclDTOList.get(0).getGzlslid();
        if (CollectionUtils.isNotEmpty(zhlcSjclDTOList)) {
            for (WwsqZhlcSjclDTO zhlcSjclDTO : zhlcSjclDTOList) {
                LOGGER.info("?????????????????????????????? gzlslid{}", gzlslid);
                // space_id???gzlslid
                String spaceId = zhlcSjclDTO.getGzlslid();
                if (StringUtil.isBlank(spaceId)) {
                    return;
                }
                List<Map> djxlZdMap = bdcZdFeignService.queryBdcZd("djxl");
                String djxlmc = StringToolUtils.convertBeanPropertyValueOfZdByString(zhlcSjclDTO.getDjxl(), djxlZdMap);
                List<FjclDTOForZhlc> fjclList = zhlcSjclDTO.getFjxx();
                if (CollectionUtils.isNotEmpty(fjclList)) {
                    for (FjclDTOForZhlc fjcl : fjclList) {
                        if (StringUtils.isNotBlank(fjcl.getClmc())) {
                            // ??????????????????????????? ???????????????
//                            StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, fjcl.getClmc(), "");
                            StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, djxlmc, "");
                            if (storageDto != null && CheckParameter.checkAnyParameter(storageDto)) {
                                StorageDto childFolder = childFolder = storageClient.createFolder(Constants.WJZX_CLIENTID, gzlslid, storageDto.getId(), fjcl.getClmc(), "");
                                if (CollectionUtils.isNotEmpty(fjcl.getClnr())) {
                                    for (FjclmxDTO fjclmxDTO : fjcl.getClnr()) {
                                        downAndUploadFjcl(fjclmxDTO, childFolder);
                                    }
                                }
                                // ???????????????????????????,??????????????????????????????
                                BdcSlSjclDO bdcSlSjclDO;
//                                List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, fjcl.getClmc());
                                BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                                bdcSlSjclQO.setDjxl(zhlcSjclDTO.getDjxl());
                                bdcSlSjclQO.setGzlslid(gzlslid);
                                bdcSlSjclQO.setClmc(fjcl.getClmc());
                                List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);

                                if (CollectionUtils.isNotEmpty(bdcSlSjclDOS)) {
                                    bdcSlSjclDO = bdcSlSjclDOS.get(0);
                                } else {
                                    bdcSlSjclDO = new BdcSlSjclDO();
                                }
                                BeanUtils.copyProperties(fjcl, bdcSlSjclDO);
                                if (CollectionUtils.isEmpty(fjcl.getClnr()) && fjcl.getMrfjys() != null) {
                                    bdcSlSjclDO.setYs(fjcl.getMrfjys());
                                }

                                // ??????????????????
                                String fjlx = fjcl.getFjlx();
                                if (StringUtils.isNotBlank(fjlx)) {
                                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                                    bdcZdDsfzdgxDO.setZdbs("BDC_SL_ZD_SJLX");
                                    bdcZdDsfzdgxDO.setDsfzdz(fjlx);
                                    bdcZdDsfzdgxDO.setDsfxtbs("wwsq");
                                    BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                                    if (result != null && StringUtils.isNotBlank(result.getBdczdz())) {
                                        bdcSlSjclDO.setSjlx(Integer.parseInt(result.getBdczdz()));
                                    }
                                }
                                bdcSlSjclDO.setDjxl(zhlcSjclDTO.getDjxl());
                                bdcSlSjclDO.setGzlslid(gzlslid);
                                bdcSlSjclDO.setWjzxid(childFolder.getId());
                                if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
                                    bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                                } else {
                                    bdcSlSjclFeignService.updateBdcSlSjcl(bdcSlSjclDO);
                                }
                            }
                        }
                    }
                }


            }
        }
    }


    private void downAndUploadFjcl(FjclmxDTO fjclmxDTO, StorageDto storageDto) {
        boolean move = false;
        if (fjclmxDTO != null && StringUtils.isNotBlank(fjclmxDTO.getFjid())) {
            try {
                // ????????????
                BaseResultDto baseResultDto = storageClient.moveAllStorages(storageDto.getId(),
                        Stream.of(fjclmxDTO.getFjid()).collect(Collectors.toList()));
                if (baseResultDto != null && baseResultDto.getCode() == 0) {
                    move = true;
                } else {
                    LOGGER.info("?????????????????? fjid:{},storageid:{},msg:{}",
                            fjclmxDTO.getFjid(), storageDto.getId(), baseResultDto != null ? baseResultDto.getMsg() : "????????????");
                }
            } catch (Exception e) {
                LOGGER.error("?????????????????? fjid:{},storageid:{}", fjclmxDTO.getFjid(), storageDto.getId(), e);
            }
        }

        // ??????????????????????????????
        if (!move && fjclmxDTO != null
                && StringUtils.isNotBlank(fjclmxDTO.getFjurl()) && StringUtils.isBlank(fjclmxDTO.getCsfs())) {
            if(StringUtils.isNotBlank(fjclmxDTO.getFjurl()) && MapUtils.isNotEmpty(fjurlDz)){
                fjclmxDTO.setFjurl(changeFjUrl(fjclmxDTO.getFjurl()));
            }
            InputStream is = null;
            try {
                LOGGER.info("?????????????????? url:{},spaceId:{}",fjclmxDTO.getFjurl(),storageDto.getSpaceId());
                is = httpClientService.doGetReturnStream(fjclmxDTO.getFjurl());
                if (is != null) {
                    byte[] dataBytes = Base64Util.getByteByIn(is);
                    if (dataBytes != null) {
                        MultipartDto multipartDto = getUploadParamDto(storageDto, dataBytes, fjclmxDTO.getFjmc());
                        StorageDto dto = storageClient.multipartUpload(multipartDto);
                        LOGGER.info("?????????????????? fjmc:{},storageid:{},spaceId:{}", fjclmxDTO.getFjmc(), dto.getId(),dto.getSpaceId());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("downFjcl {} ??????????????????{},spaceId:{}", fjclmxDTO.getFjmc(), e,storageDto.getSpaceId());
            }
        } else if (!move && fjclmxDTO != null
                && StringUtils.isNotBlank(fjclmxDTO.getFjnr())) {
            // ???????????????(base64code)?????????
            MultipartFile file = null;
            if (fjclmxDTO.getFjnr().contains("data:")) {
                LOGGER.info("????????????data???????????????{}", fjclmxDTO.getFjmc());
                file = Base64Utils.base64ToMultipart(fjclmxDTO.getFjnr());
            } else {
                LOGGER.info("???????????????data???????????????{}", fjclmxDTO.getFjmc());
                file = Base64Utils.base64ToMultipart("data:image/jpeg;base64," + fjclmxDTO.getFjnr());
            }
            if (file != null) {
                try {
                    MultipartDto multipartDto = getUploadParamDto(storageDto, file.getBytes(), fjclmxDTO.getFjmc());
                    StorageDto dto = storageClient.multipartUpload(multipartDto);
                    LOGGER.info("?????????????????? fjmc:{},storageid:{}", fjclmxDTO.getFjmc(), dto.getId());
                } catch (IOException e) {
                    LOGGER.error("downFjcl {} ??????????????????{}", fjclmxDTO.getFjmc(), e);
                }
            }
        }else if (!move && fjclmxDTO != null
                && StringUtils.isNotBlank(fjclmxDTO.getFjurl()) && "ftp".equals(fjclmxDTO.getCsfs())) {
            //ftp????????????
            LOGGER.info("ftp???????????????????????? url:{},spaceId:{}",fjclmxDTO.getFjurl(),storageDto.getSpaceId());
            FTPClient ftpClient = new FTPClient();
            InputStream inputStream = null;
            String localCharset = "GBK";
            try {
                ftpClient.setAutodetectUTF8(true);
                ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                    // ??????????????????UTF-8???????????????????????????????????????UTF-8???????????????????????????????????????GBK???.
                    localCharset = "UTF-8";
                }
                ftpClient.setControlEncoding(localCharset);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                inputStream = FtpUtil.downloadFtpFile(ftpClient, fjclmxDTO.getFjurl(), fjclmxDTO.getFjmc());
                if (inputStream != null) {
                    byte[] dataBytes = IOUtils.toByteArray(inputStream);
                    if (dataBytes != null) {
                        MultipartDto multipartDto = getUploadParamDto(storageDto, dataBytes, fjclmxDTO.getFjmc());
                        StorageDto dto = storageClient.multipartUpload(multipartDto);
                        LOGGER.info("ftp???????????????????????? fjmc:{},storageid:{},spaceId:{}", fjclmxDTO.getFjmc(), dto.getId(),dto.getSpaceId());
                    }
                }
            } catch (IOException e) {
                LOGGER.error("ftp??????downFjcl {} ??????????????????{},spaceId:{}", fjclmxDTO.getFjmc(), e,storageDto.getSpaceId());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        ftpClient.completePendingCommand();
                    } catch (IOException e) {
                        LOGGER.error("errorMsg:", e);
                    }
                }
            }

        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private static MultipartDto getUploadParamDto(StorageDto storageDto, byte[] dataBytes, String fileName) throws
            IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        //?????????????????????????????????????????????
        multipartDto.setRename(0);
        if (dataBytes != null) {
            multipartDto.setData(dataBytes);
            // ????????????????????????excel??????????????????????????????contenttype?????????application/octet-stream
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(dataBytes.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(fileName);
        }
        return multipartDto;
    }

    /**
     * @param url ???????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ??????????????????????????????
     */
    private String changeFjUrl(String url){
        if(StringUtils.isNotBlank(url) && MapUtils.isNotEmpty(fjurlDz)){
            for (Map.Entry<String, String> entry : fjurlDz.entrySet()) {
                if(url.contains(entry.getKey())){
                    LOGGER.info("??????????????????{}",entry.getKey());
                    url =url.replace(entry.getKey(),entry.getValue());
                    LOGGER.info("??????????????????{}",entry.getValue());
                }
            }
        }
        return url;
    }

}
