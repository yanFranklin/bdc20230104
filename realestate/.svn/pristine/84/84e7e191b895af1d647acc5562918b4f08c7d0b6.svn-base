package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.exchange.fjxx.FjxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.fjxx.FjxxResponseDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.service.inf.FjxxService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/2 15:36
 * @description
 */
@Service
public class FjxxServiceImpl implements FjxxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FjxxServiceImpl.class);

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private StorageClientMatcher storageClient;

    @Override
    public ExchangeDsfCommonResponse queryFjxxBySlbh(String slbh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.error("该受理编号未查询到项目信息，受理编号：{}", slbh);
            return ExchangeDsfCommonResponse.fail("该受理编号未查询到项目信息，受理编号："+slbh);
        }

        List<FjxxDTO> fjxxDTOList = queryFjxx(bdcXmDOList);
        if(CollectionUtils.isEmpty(fjxxDTOList)) {
            return ExchangeDsfCommonResponse.ok(Collections.EMPTY_LIST);
        }

        FjxxResponseDTO response = new FjxxResponseDTO();
        response.setSlbh(slbh);
        response.setFile(fjxxDTOList);

        return ExchangeDsfCommonResponse.ok(response);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [storageDtoList]
     * @return java.util.List<cn.gtmap.gtc.storage.domain.dto.StorageDto>
     * @description 递归获取所有附件，去除文件夹
     */
    private List<StorageDto> queryAllStorageDtoExcludeFolder(List<StorageDto> storageDtoList){
        List<StorageDto> temp = new ArrayList<>();
        for(StorageDto storageDto : storageDtoList) {
            if(storageDto.getType() == 0) {
                if (CollectionUtils.isEmpty(storageDto.getChildren())){
                    continue;
                }else {
                    temp.addAll(queryAllStorageDtoExcludeFolder(storageDto.getChildren()));
                    continue;
                }
            }
            temp.add(storageDto);
        }
        return temp;
    }

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @params [bdcqzh]
     * @return cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse
     * @description 通过不动产权证号查询附件图片信息
     */
    @Override
    public ExchangeDsfCommonResponse queryFjxxByBdcqzh(String bdcqzh) {
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.queryXmByZsBdcqzh(bdcqzh);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.error("该不动产权证号未查询到项目信息，不动产权证号：{}", bdcqzh);
            return ExchangeDsfCommonResponse.fail("该不动产权证号未查询到项目信息，不动产权证号："+bdcqzh);
        }

        List<FjxxDTO> fjxxDTOList = queryFjxx(bdcXmDOList);
        if(CollectionUtils.isEmpty(fjxxDTOList)) {
            return ExchangeDsfCommonResponse.ok(Collections.EMPTY_LIST);
        }

        FjxxResponseDTO response = new FjxxResponseDTO();
        response.setBdcqzh(bdcqzh);
        response.setFile(fjxxDTOList);

        return ExchangeDsfCommonResponse.ok(response);
    }

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @params [bdcXmDOList]
     * @return List<FjxxDTO>
     * @description 通过不动产项目查询附件图片信息
     */
    public List<FjxxDTO> queryFjxx(List<BdcXmDO> bdcXmDOList) {
        List<StorageDto> storageDtos = storageClient.queryMenus("clientId", bdcXmDOList.get(0).getGzlslid(), null, 1, null, null,null,null);
        if(CollectionUtils.isEmpty(storageDtos)) {
            LOGGER.warn("该流程未查询到storage信息，工作流实例id：{}", bdcXmDOList.get(0).getGzlslid());
            return Collections.EMPTY_LIST;
        }
        LOGGER.info("该流程storage个数为：{}", storageDtos.size());

        List<FjxxDTO> fjxxDTOList = new ArrayList<>();
        List<StorageDto> allStorageDtosExcludeFolder = queryAllStorageDtoExcludeFolder(storageDtos);
        if(CollectionUtils.isEmpty(allStorageDtosExcludeFolder)) {
            LOGGER.warn("该流程未查询到附件信息，工作流实例id：{}", bdcXmDOList.get(0).getGzlslid());
            return Collections.EMPTY_LIST;
        }
        LOGGER.info("该流程附件个数为：{}", allStorageDtosExcludeFolder.size());
        LOGGER.info("附件信息：{}", JSONObject.toJSONString(allStorageDtosExcludeFolder));

        for(StorageDto storageDto : allStorageDtosExcludeFolder) {
            FjxxDTO fjxxDTO = new FjxxDTO();
            fjxxDTO.setFileName(storageDto.getName());
            fjxxDTO.setFileReadPath(storageDto.getDownUrl());
            fjxxDTO.setFileDownloadPath(fjxxDTO.getFileReadPath() + "?attachment=1");
            if(storageDto.getCreateAt() != null){
                fjxxDTO.setFileUploadTime(DateFormatUtils.format(storageDto.getCreateAt(), DateUtils.sdf_ymdhms));
            }
            fjxxDTOList.add(fjxxDTO);
        }
        return fjxxDTOList;
    }

}
