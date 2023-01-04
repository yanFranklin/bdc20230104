package cn.gtmap.realestate.etl.core.convert;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.etl.core.domian.BdcDsfRlsbDO;
import cn.gtmap.realestate.etl.core.vo.BdcDsfRlsbVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface FaceCheckConverter {

    @Mappings({
            @Mapping(target = "personId", source = "personid"),
            @Mapping(target = "personName", source = "personname"),
            @Mapping(target = "idCode", source = "idcode"),
            @Mapping(target = "cardImage", source = "cardimage"),
            @Mapping(target = "personImage", source = "personimage"),
            @Mapping(target = "faceFeature", source = "facefeature")
    })
    BdcDsfRlsbVO getBdcDsfRlsbVOByBdcDsfRlsbDO(BdcDsfRlsbDO bdcDsfRlsbDO);

    default List<BdcDsfRlsbVO> getBdcDsfRlsbVOListByBdcDsfRlsbDOList(List<BdcDsfRlsbDO> bdcDsfRlsbDO) {
        if (bdcDsfRlsbDO == null) {
            return null;
        }

        List<BdcDsfRlsbVO> list = new ArrayList<>(bdcDsfRlsbDO.size());
        for (BdcDsfRlsbDO bdcDsfRlsbDO1 : bdcDsfRlsbDO) {
            BdcDsfRlsbVO dsfRlsbDO = getBdcDsfRlsbVOByBdcDsfRlsbDO(bdcDsfRlsbDO1);
            if (dsfRlsbDO.getGender().equals(CzRlsbConstansEnum.MAN.getValue().toString())) {
                dsfRlsbDO.setGender(CzRlsbConstansEnum.MAN.getDescription());
            } else if (dsfRlsbDO.getGender().equals(CzRlsbConstansEnum.WOMAN.getValue().toString())) {
                dsfRlsbDO.setGender(CzRlsbConstansEnum.WOMAN.getDescription());
            } else if (dsfRlsbDO.getGender().equals(CzRlsbConstansEnum.UNKNOW.getValue().toString())) {
                dsfRlsbDO.setGender(CzRlsbConstansEnum.UNKNOW.getDescription());
            }
           /* if(null ==bdcDsfRlsbDO1.getCreatetime()){
                dsfRlsbDO.setCreatetime(null);
            }else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                dsfRlsbDO.setCreatetime(simpleDateFormat.f);
            }
            if(null ==bdcDsfRlsbDO1.getUpdatetime()){
                dsfRlsbDO.setUpdatetime(null);
            }else {
                dsfRlsbDO.setUpdatetime(DateUtils.formateDateToString(bdcDsfRlsbDO1.getUpdatetime(),DateUtils.DATE_FORMAT));
            }*/
            list.add(dsfRlsbDO);

        }

        return list;
    }

    @Mappings({
            @Mapping(target = "ywnum", source = "xmid"),
            @Mapping(target = "personName", source = "qlrmc"),
            @Mapping(target = "gender", source = "xb"),
//            @Mapping(target = "birth", source = "csrq"),
            @Mapping(target = "address", source = "txdz"),
            @Mapping(target = "idCode", source = "zjh"),
            @Mapping(target = "role", source = "sxh"),
            @Mapping(target = "family", source = "mz")
    })
    BdcDsfRlsbVO getBdcDsfRlsbVOByBdcQlrDO(BdcQlrDO bdcQlrDO);

    List<BdcDsfRlsbVO> getBdcDsfRlsbVOListByBdcQlrDOList(List<BdcQlrDO> bdcQlrDO);

    @Mappings({
            @Mapping(source = "personId", target = "personid"),
            @Mapping(source = "personName", target = "personname"),
            @Mapping(source = "idCode", target = "idcode"),
            @Mapping(source = "cardImage", target = "cardimage"),
            @Mapping(source = "personImage", target = "personimage"),
            @Mapping(source = "faceFeature", target = "facefeature")
    })
    BdcDsfRlsbDO getBdcDsfRlsbDOByBdcDsfRlsbVO(BdcDsfRlsbVO bdcDsfRlsbDO);

}
