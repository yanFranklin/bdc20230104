package cn.gtmap.realestate.etl.core.convert;

import cn.gtmap.realestate.etl.core.domian.IRecvimgDataDO;
import cn.gtmap.realestate.etl.core.vo.PopupFileDataVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OldSystemFileConverter {

    String FILE_SUFFIX_JPG = ".JPG";

    /**
     * @param iRecvimgDataDO
     * @return
     */
    @Mappings({
            @Mapping(target = "id", source = "rimgid"),
            @Mapping(target = "clientId", source = "recvid"),
            @Mapping(target = "name", source = "rimgid"),
            @Mapping(target = "downUrl", source = "scantime"),
            @Mapping(target = "suffix", source = "rname")
    })
    PopupFileDataVO getPopupFileDataByIRecvingData(IRecvimgDataDO iRecvimgDataDO);

    default List<PopupFileDataVO> getPopupFileDataListByIRecvingDataList(List<IRecvimgDataDO> iRecvimgDataDOList, String filePath, String downloadUrl, List<String> fileNoSuffixList) throws UnsupportedEncodingException {
        if (iRecvimgDataDOList == null) {
            return null;
        }
        List<PopupFileDataVO> list = new ArrayList<PopupFileDataVO>(iRecvimgDataDOList.size());
        for (IRecvimgDataDO iRecvimgDataDO : iRecvimgDataDOList) {
            PopupFileDataVO recvingData = getPopupFileDataByIRecvingData(iRecvimgDataDO);
            String fileSuffix = "";
            if(StringUtils.isNotBlank(recvingData.getSuffix())){
                fileSuffix = "." + recvingData.getSuffix();
            }else{
                if(!nosuffix(fileNoSuffixList, recvingData.getDownUrl())){
                    fileSuffix = FILE_SUFFIX_JPG;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            String downUrl = stringBuilder.append(downloadUrl).append("path=").
                    append(URLEncoder.encode(filePath + recvingData.getDownUrl(), "utf-8"))
                    .append("&fileName=")
                    .append(URLEncoder.encode(recvingData.getName() + fileSuffix, "utf-8")).toString();
            recvingData.setDownUrl(downUrl);
            recvingData.setType("6");
            list.add(recvingData);
        }
        return list;
    }

    default boolean nosuffix(List<String> fileNoSuffixList, String filePath){
        if(CollectionUtils.isNotEmpty(fileNoSuffixList) && StringUtils.isNotBlank(filePath)){
            for(String name : fileNoSuffixList){
                if(filePath.indexOf(name) > -1){
                    return true;
                }
            }
        }
        return false;
    }


}
