package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.YbdcdyhMapper;
import cn.gtmap.realestate.building.service.YbdcdyhService;
import cn.gtmap.realestate.common.core.dto.building.YbdcdyhResponseDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022-07-07
 * @description 原不动产单元号相关服务
 */
@Service
public class YbdcdyhServiceImpl implements YbdcdyhService {

    @Autowired
    private YbdcdyhMapper ybdcdyhMapper;



    /**
     * @description 查询房屋原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 10:51
     * @param fwBdcdyhList
     * @return List<YbdcdyhResponseDTO>
     */
    @Override
    public List<YbdcdyhResponseDTO> queryFwYbdcdyhList(List<String> fwBdcdyhList) {
        if (CollectionUtils.isEmpty(fwBdcdyhList)) {
            return null;
        }
        return ybdcdyhMapper.queryFwYbdcdyhList(fwBdcdyhList);
    }

    /**
     * @description 查询土地原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 10:51
     * @param tdBdcdyhList
     * @return List<YbdcdyhResponseDTO>
     */
    @Override
    public List<YbdcdyhResponseDTO> queryTdYbdcdyhList(List<String> tdBdcdyhList) {
        if (CollectionUtils.isEmpty(tdBdcdyhList)) {
            return null;
        }
        // 截取不动产单元号前19位为地籍号
        List<String> djhList = tdBdcdyhList.stream().map(bdcdyh -> bdcdyh.substring(0, 19)).collect(Collectors.toList());
        List<YbdcdyhResponseDTO> ybdcdyhResponseDTOList = ybdcdyhMapper.queryTdYbdcdyhList(djhList);

        // 将不动产单元号替换查询结果中的19位土地地籍号
        if (CollectionUtils.isNotEmpty(ybdcdyhResponseDTOList)) {
            for (YbdcdyhResponseDTO ybdcdyhResponseDTO : ybdcdyhResponseDTOList) {
                for (String bdcdyh : tdBdcdyhList) {
                    if (StringUtils.equals(bdcdyh.substring(0, 19), ybdcdyhResponseDTO.getBdcdyh())) {
                        ybdcdyhResponseDTO.setBdcdyh(bdcdyh);
                    }
                }
            }
        }
        return ybdcdyhResponseDTOList;
    }
}
