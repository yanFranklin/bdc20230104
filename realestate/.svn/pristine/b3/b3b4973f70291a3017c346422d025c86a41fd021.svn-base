package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/6
 * @description 权利人去重分组方法
 */
public class BdcQlrGroupUtils {

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 权利人分组
     * 权利人类型为企业或者证件号为空的按照权利人名称分组去重
     * 权利人类型非企业的且证件号不为空按照证件号分组去重,并且需要考虑证件号15位18位的转换
     */
    public static List<BdcQlrGroupDTO> groupQlrxx(List<BdcQlrDO> bdcQlrDOList) {
        List<BdcQlrGroupDTO> bdcQlrGroupDTOList = new ArrayList<>();
        if(CollectionUtils.isEmpty(bdcQlrDOList)){
            return bdcQlrGroupDTOList;
        }
        Map<String, List<BdcQlrDO>> qlrMap =new HashMap<>();
        List<BdcQlrDO> qyList = bdcQlrDOList.stream().filter(bdcQlrDO -> CommonConstantUtils.QLRLX_QY.equals(bdcQlrDO.getQlrlx()) ||StringUtils.isBlank(bdcQlrDO.getZjh())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(qyList)) {
            //根据权利人名称分组
            qlrMap.putAll(qyList.stream().collect(Collectors.groupingBy(BdcQlrDO::getQlrmc)));
            bdcQlrDOList.removeAll(qyList);
        }
        //根据证件号分组
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            bdcQlrDOList.forEach(bdcQlrDO ->
                    bdcQlrDO.setZjh(CardNumberTransformation.idCard15to18(bdcQlrDO.getZjh()))
            );
            qlrMap.putAll(bdcQlrDOList.stream().collect(Collectors.groupingBy(BdcQlrDO::getZjh)));
        }
        if(MapUtils.isNotEmpty(qlrMap)) {
            for (Map.Entry<String, List<BdcQlrDO>> entry : qlrMap.entrySet()) {
                BdcQlrGroupDTO bdcQlrGroupDTO = new BdcQlrGroupDTO();
                List<String> otherBdcQlrDOList = new ArrayList<>();
                List<String> otherXmidList = new ArrayList<>();
                for (int i = 0; i < entry.getValue().size(); i++) {
                    //第一条数据取出来，其他数据放在另一个list里面
                    if (i == 0) {
                        BdcQlrDO bdcQlrDO = entry.getValue().get(0);
                        bdcQlrGroupDTO.setBdcQlrDO(bdcQlrDO);
                    } else {
                        otherBdcQlrDOList.add(entry.getValue().get(i).getQlrid());
                        otherXmidList.add(entry.getValue().get(i).getXmid());
                        bdcQlrGroupDTO.setOtherBdcQlridList(otherBdcQlrDOList);
                        bdcQlrGroupDTO.setOtherXmidList(otherXmidList);
                    }
                }
                bdcQlrGroupDTOList.add(bdcQlrGroupDTO);
            }
        }
        return bdcQlrGroupDTOList;

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 权利人分组后去重
      */
    public static List<BdcQlrDO> distinctQlrxx(List<BdcQlrDO> bdcQlrDOList){
        List<BdcQlrDO> distinctQlrList =new ArrayList<>();
        List<BdcQlrGroupDTO> bdcQlrGroupDTOS =groupQlrxx(bdcQlrDOList);
        if(CollectionUtils.isNotEmpty(bdcQlrGroupDTOS)){
            for(BdcQlrGroupDTO bdcQlrGroupDTO:bdcQlrGroupDTOS){
                distinctQlrList.add(bdcQlrGroupDTO.getBdcQlrDO());

            }
        }
        return distinctQlrList;

    }


}
