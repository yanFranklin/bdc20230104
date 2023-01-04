package cn.gtmap.realestate.etl.convert;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.etl.core.thirdparty.order.response.Img;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThirdpartyOrderConvert {

    default Img getImgByStorageDto(StorageDto storageDto) {
        if (storageDto == null) {
            return null;
        }

        Img img = new Img();

        img.setMc(storageDto.getName());
        img.setId(storageDto.getId());
        img.setSl(String.valueOf(storageDto.getChildren().size()));

        return img;
    }

    List<Img> getImgListByStorageDtoList(List<StorageDto> storageDtoList);

}
