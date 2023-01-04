package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeWsxx;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeWsxxJg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxws.response.SjptCxwsResponseCxjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxws.response.SjptCxwsResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxws.response.SjptCxwsResponseData;
import cn.gtmap.realestate.exchange.service.inf.sjpt.CxwsService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-11
 * @description 查询文书实现
 */
@Service
public class CxwsServiceImpl implements CxwsService{

    private final static Logger LOGGER = LoggerFactory.getLogger(CxwsServiceImpl.class);

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private EntityMapper sjptEntityMapper;

    /**
     * @param responseJson
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存查询文书信息
     */
    @Override
    public void saveCxwsxx(JSONObject responseJson) {
        if(responseJson != null){
            SjptCxwsResponseDTO responseDTO = JSONObject.toJavaObject(responseJson,SjptCxwsResponseDTO.class);
            if(CheckParameter.checkAnyParameter(responseDTO)){
                SjptCxwsResponseData data = responseDTO.getData();
                if(CheckParameter.checkAllParameter(data)
                        && CollectionUtils.isNotEmpty(data.getCxjg())){
                    // 先根据 cxsqdh wsbh 查询已有WSXX WSXX_JG
                    // 删掉数据库 和 国图大云文件中心文件
                    deleteOldWsxx(data.getCxsqdh(),data.getWsbh());

                    // 新增 WSXX
                    GxPeWsxx gxPeWsxx = new GxPeWsxx();
                    gxPeWsxx.setCxsqdh(data.getCxsqdh());
                    gxPeWsxx.setWsbh(data.getWsbh());
                    gxPeWsxx.setWsxxid(UUIDGenerator.generate16());
                    // 根据 WSXXID 创建国图文件中心文件夹
                    StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID_EXCHANGE,Constants.WJZX_SPACEID_SJPTWSXX,gxPeWsxx.getWsxxid(),"");
                    if(storageDto != null){
                        sjptEntityMapper.insertSelective(gxPeWsxx);

                        // 新增 WSXX_JG
                        List<SjptCxwsResponseCxjg> cxjgList = data.getCxjg();
                        // 循环 WSXX_JG 上传图片内容 将 结果ID 存进WJNR字段
                        List<GxPeWsxxJg> jgList = new ArrayList<>();
                        for(SjptCxwsResponseCxjg cxjg: cxjgList){
                            String wsxxStorageId = "";
                            try {
                                wsxxStorageId = uploadWsxx(cxjg.getWsnr(),storageDto.getId(),cxjg.getWjmc());
                            } catch (IOException e) {
                                LOGGER.error("文书信息上传失败，cxsqdh:{},wsbh:{},wjmc:{}",data.getCxsqdh(),data.getWsbh(),cxjg.getWjmc());
                            }
                            if(StringUtils.isNotBlank(wsxxStorageId)){
                                GxPeWsxxJg gxPeWsxxJg = new GxPeWsxxJg();
                                gxPeWsxxJg.setJgid(UUIDGenerator.generate16());
                                gxPeWsxxJg.setCxsqdh(data.getCxsqdh());
                                gxPeWsxxJg.setWsbh(data.getWsbh());
                                gxPeWsxxJg.setWjlx(cxjg.getWjlx());
                                gxPeWsxxJg.setWjmc(cxjg.getWjmc());
                                gxPeWsxxJg.setXh(cxjg.getXh());
                                gxPeWsxxJg.setWsnr(wsxxStorageId);
                                jgList.add(gxPeWsxxJg);
                            }
                        }

                        // 批量新增结果
                        if(CollectionUtils.isNotEmpty(jgList)){
                            sjptEntityMapper.insertBatchSelective(jgList);
                        }
                    }
                }
            }
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param base64Str
     * @param parentID
     * @param  wjmc
     * @return java.lang.String
     * @description 上传文书信息
     */
    private String uploadWsxx(String base64Str,String parentID,String wjmc) throws IOException {
        MultipartFile file = Base64Utils.base64ToMultipart("data:image/jpeg;base64,"+base64Str);
        if(file != null){
            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setClientId(Constants.WJZX_CLIENTID_EXCHANGE);
            multipartDto.setName(wjmc);
            multipartDto.setNodeId(parentID);
            multipartDto.setData(file.getBytes());
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(file.getOriginalFilename());
            StorageDto dto = storageClient.multipartUpload(multipartDto);
            if(dto != null){
                return dto.getId();
            }
        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private static MultipartDto getUploadParamDto(StorageDto storageDto, MultipartFile file) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        if(file != null){
            multipartDto.setData(file.getBytes());
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(file.getOriginalFilename());
            multipartDto.setName(file.getName());
        }
        return multipartDto;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxsqdh
     * @param wsbh
     * @return void
     * @description 删除已有的文书信息
     */
    private void deleteOldWsxx(String cxsqdh,String wsbh){
        // 删除 GX_PE_WSXX
        Example wsxxExample = new Example(GxPeWsxx.class);
        wsxxExample.createCriteria().andEqualTo("cxsqdh",cxsqdh).
                andEqualTo("wsbh",wsbh);
        List<GxPeWsxx> wsxxList = sjptEntityMapper.selectByExample(wsxxExample);
        if(CollectionUtils.isNotEmpty(wsxxList)){
            for(GxPeWsxx temp : wsxxList){
                List<StorageDto> wsbhFolderList = storageClient.listStoragesByName(Constants.WJZX_CLIENTID_EXCHANGE,Constants.
                        WJZX_SPACEID_SJPTWSXX,null,temp.getWsxxid(),1,0);
                if(CollectionUtils.isNotEmpty(wsbhFolderList)){
                    List<String> idList = new ArrayList<>();
                    for(StorageDto storageDto : wsbhFolderList){
                        idList.add(storageDto.getId());
                    }
                    storageClient.deleteStorages(idList);
                }
            }
        }

        sjptEntityMapper.deleteByExample(wsxxExample);

        // 删除 GX_PE_WSXX_JG
        Example wsxxJgExample = new Example(GxPeWsxxJg.class);
        wsxxJgExample.createCriteria().andEqualTo("cxsqdh",cxsqdh).
                andEqualTo("wsbh",wsbh);
        sjptEntityMapper.deleteByExample(wsxxJgExample);

    }

}
