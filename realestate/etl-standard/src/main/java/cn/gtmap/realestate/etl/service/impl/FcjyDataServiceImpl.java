package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcNtFjParamDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.etl.core.mapper.exchange.FcjyDataMapper;
import cn.gtmap.realestate.etl.service.FcjyDataService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/2
 * @description 房产交易服务
 */
@Service
public class FcjyDataServiceImpl implements FcjyDataService {

    private  final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    @Qualifier("bdcRepository")
    private Repository repo;

    @Autowired(required = false)
    private FcjyDataMapper fcjyDataMapper;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;


    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  交易附件URL地址
      */
    @Value("${fcjy.htfjurl:}")
    private String htfjurl;

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description  交易附件URL地址(易签宝)
     */
    @Value("${fcjy.dzqmurl:}")
    private String dzqmurl;

    @Override
    public Page<Map> bdcClfHtxxByPageJson(Pageable pageable, Map<String,String> map){
        return repo.selectPaging("listClfHtxxByPage", map, pageable);
    }

    @Override
    public Page<Map> bdcSpfHtxxByPageJson(Pageable pageable, Map<String,String> map){
        return repo.selectPaging("listSpfHtxxByPage", map, pageable);
    }

    @Override
    public void uploadFcjyfj(String fwfclx,String gzlslid,String htbh,BdcNtFjParamDTO bdcNtFjParamDTO){
        if(StringUtils.isAnyBlank(fwfclx,gzlslid,htbh)){
            throw new AppException("上传交易附件缺失参数fwfclx,htbh,gzlslid");
        }
        if(StringUtils.isBlank(htfjurl)){
            throw new AppException("未配置房产交易附件地址,请检查");
        }
        if(bdcNtFjParamDTO == null){
            throw new AppException("缺失权利人证件号信息");
        }

        logger.info("上传附件参数为：{},{},{},{}",fwfclx,gzlslid,htbh,JSONObject.toJSONString(bdcNtFjParamDTO));

        if(StringUtils.equals(CommonConstantUtils.FCJY_TYPE_CLF,fwfclx)){
            uploadClfHtfj(gzlslid, htbh,bdcNtFjParamDTO);
        }else if(StringUtils.equals(CommonConstantUtils.FCJY_TYPE_SPF,fwfclx)){
            uploadSpfHtfj(gzlslid, htbh,bdcNtFjParamDTO);
        }

    }

    /**
      * @param gzlslid 工作流实例ID
      * @param htbh 合同编号
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 上传存量房合同附件
      */
    private void uploadClfHtfj(String gzlslid,String htbh,BdcNtFjParamDTO bdcNtFjParamDTO){
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = "";
        if (Objects.nonNull(userDto) && StringUtils.isNotBlank(userDto.getId())) {
            userid = userDto.getId();
        }
        StorageDto storageDto =storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID,gzlslid,"合同备案附件",userid);
        if(storageDto != null){
            // 添加受理收件材料信息
            if(StringUtils.isNotBlank(storageDto.getId())){
                this.addSjclxx(gzlslid, storageDto.getName(), storageDto.getId());
            }
            String clffjUrl=htfjurl+"/Contract2/";
            //合同附件
            downAndUploadFjFromUrl(clffjUrl+htbh +".pdf",htbh + ".pdf",storageDto.getId());
            downAndUploadFjFromUrl(clffjUrl + htbh + ".jpg", htbh + ".jpg", storageDto.getId());
            //结婚证附件(出卖方)
            downAndUploadFjFromUrl(clffjUrl + htbh + "_JY.jpg", htbh + "_JY.jpg", storageDto.getId());
            //结婚证附件（买受方)
            downAndUploadFjFromUrl(clffjUrl + htbh + "_JQ.jpg", htbh + "_JQ.jpg", storageDto.getId());
            //发票附件
            downAndUploadFjFromUrl(clffjUrl + htbh + "_FP.jpg", htbh + "_FP.jpg", storageDto.getId());
            Set<String> qlrzjhSet = bdcNtFjParamDTO.getQlrzjh();
            Set<String> ywrzjhSet = bdcNtFjParamDTO.getYwrzjh();

            //身份证附件(出卖方)，户口本
            if(CollectionUtils.isNotEmpty(ywrzjhSet)) {
                int num =1;
                for (String zjh : ywrzjhSet) {
                    downAndUploadFjFromUrl(clffjUrl + htbh + "_SC" +num +".jpg", htbh  + "_SC" +num +".jpg", storageDto.getId());
                    downAndUploadFjFromUrl(clffjUrl + htbh + "_HY" +num +".jpg", htbh  + "_HY" +num +".jpg", storageDto.getId());
                    num++;
                }
            }
            //身份证附件(买受方)
            if(CollectionUtils.isNotEmpty(qlrzjhSet)) {
                int num =1;
                for (String zjh : qlrzjhSet) {
                    downAndUploadFjFromUrl(clffjUrl + htbh + "_SM" +num +".jpg", htbh + "_SM" +num +".jpg", storageDto.getId());
                    downAndUploadFjFromUrl(clffjUrl + htbh + "_HQ" +num +".jpg", htbh + "_HQ" +num +".jpg", storageDto.getId());
                    num++;
                }
            }

            //获取电子签名PDF
            if (StringUtils.isNotBlank(dzqmurl)) {
                downAndUploadFjFromUrl(dzqmurl+htbh,htbh+"_DZQM.pdf", storageDto.getId());
                //测试数据
                //dzqmurl = "http://58.221.216.202/Contract1/r12820190718001.pdf";
                //downAndUploadFjFromUrl(dzqmurl,htbh+"_DZQM.pdf", storageDto.getId());
            }
        }

    }

    /**
     * 添加受理材料信息
     * @param gzlslid  工作流实例ID
     * @param foldName 文件夹名称
     * @param wjzxId 文件中心ID
     */
    private void addSjclxx(String gzlslid,String foldName, String wjzxId){
        // 获取文件序号
        List<BdcSlSjclDO> sjclList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
        int xh = 0;
        if(CollectionUtils.isNotEmpty(sjclList)){
            xh = sjclList.size();
        }
        // 初始化附件材料信息
        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
        bdcSlSjclDO.setGzlslid(gzlslid);
        bdcSlSjclDO.setClmc(foldName);
        bdcSlSjclDO.setWjzxid(wjzxId);
        bdcSlSjclDO.setFs(1);
        bdcSlSjclDO.setYs(1);
        bdcSlSjclDO.setMrfs(1);
        bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
        bdcSlSjclDO.setXh(++xh);

        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
            bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
        }
        this.bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传商品房合同附件
     */
    private void uploadSpfHtfj(String gzlslid,String htbh,BdcNtFjParamDTO bdcNtFjParamDTO){
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = "";
        if (Objects.nonNull(userDto) && StringUtils.isNotBlank(userDto.getId())) {
            userid = userDto.getId();
        }
        StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "合同备案附件", userid);
        if (storageDto != null) {
            // 添加受理收件材料信息
            if(StringUtils.isNotBlank(storageDto.getId())){
                this.addSjclxx(gzlslid, storageDto.getName(), storageDto.getId());
            }
            String spffjUrl = htfjurl + "/Contract1/";
            //合同附件
            downAndUploadFjFromUrl(spffjUrl + htbh + ".pdf", htbh + ".pdf", storageDto.getId());
            downAndUploadFjFromUrl(spffjUrl + htbh + ".jpg", htbh + ".jpg", storageDto.getId());
            //发票附件
            downAndUploadFjFromUrl(spffjUrl + htbh + "_F.jpg", htbh + "_F.jpg", storageDto.getId());
            Set<String> qlrzjhSet = bdcNtFjParamDTO.getQlrzjh();
            //身份证附件
            if(CollectionUtils.isNotEmpty(qlrzjhSet)) {
                int num =1;
                for (String zjh : qlrzjhSet) {
                    downAndUploadFjFromUrl(spffjUrl + htbh + "_S" +num +".jpg", htbh + "_S" +num +".jpg", storageDto.getId());
                    num++;
                }
            }

            //获取电子签名PDF
            if (StringUtils.isNotBlank(dzqmurl)) {
                downAndUploadFjFromUrl(dzqmurl+htbh,htbh+"_DZQM.pdf", storageDto.getId());
                //测试数据
                //dzqmurl = "http://58.221.216.202/Contract1/r12820190718001.pdf";
                //downAndUploadFjFromUrl(dzqmurl,htbh+"_DZQM.pdf", storageDto.getId());
            }
        }
    }

    /**
      * @param fjmc 附件名称
      * @param fjurl 附件URL
      * @param wjjid 文件夹ID
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据附件URL地址下载并上传附件
      */
    private void downAndUploadFjFromUrl(String fjurl,String fjmc,String wjjid){
        InputStream is;
        try {
            is = httpClientService.doGetReturnStream(fjurl);
            logger.info("根据url{}地址获取附件{}", fjurl, JSON.toJSONString(is));
            if(is != null){
                byte[] dataBytes = Base64Utils.getByteByIn(is);
                if(dataBytes != null){
                    MultipartDto multipartDto = getUploadParamDto(wjjid, dataBytes,fjmc);
                    StorageDto dto = storageClient.multipartUpload(multipartDto);
                    logger.info("附件上传成功 fjmc:{},storageid:{}",fjmc,dto.getId());
                }
            }
        } catch (Exception e) {
            logger.error("下载附件 {} 获取流异常",fjmc,e);
        }
    }

    /**
      * @param wjjid 文件夹ID
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 组织上传附件对象
      */
    private static MultipartDto getUploadParamDto(String wjjid, byte[] dataBytes,String fileName){
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(wjjid);
        multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        if(dataBytes != null){
            multipartDto.setData(dataBytes);
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(dataBytes.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(fileName);
        }
        return multipartDto;
    }
}
