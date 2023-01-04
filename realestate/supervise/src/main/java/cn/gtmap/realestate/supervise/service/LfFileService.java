package cn.gtmap.realestate.supervise.service;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.supervise.core.domain.BdcLfFjxxDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LfFileService {
    StorageDto uploadFile(MultipartFile file, String ywid);

    List<BdcLfFjxxDO> listLfFiles(String ywid);

    Integer deleteFiles(List<BdcLfFjxxDO> fjxxDOList);
}
