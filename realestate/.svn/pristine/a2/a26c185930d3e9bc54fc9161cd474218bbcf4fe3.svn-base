package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.DjhZtService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.ZdzhDjxxDO;
import cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DjhZtServiceImpl implements DjhZtService {
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcdyh
     * @param isjd
     * @return cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询djh冻结信息
     */
    @Override
    public DjhZtResponseDTO getDjhZtByBdcdyh(String bdcdyh,String isjd) {
        if(StringUtils.isNotBlank(bdcdyh)){
            String djh=bdcdyh.substring(0,19);
            return getDjhZtByDjh(djh,isjd);
        }
        return null;
    }

    /**
     * @param djh
     * @param isjd
     * @return DjhZtResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 查看地籍号状态
     */
    @Override
    public DjhZtResponseDTO getDjhZtByDjh(String djh,String isjd) {
        DjhZtResponseDTO djhZtResponseDTO = new DjhZtResponseDTO();
        if (StringUtils.isNotBlank(djh)) {
            djhZtResponseDTO.setDjh(djh);
            Example example = new Example(ZdzhDjxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("zdzhdm", djh);
            // isjd状态：1为解冻状态，0或空为未解冻的状态
            if(Constants.ZDZH_DJXX_ISJD_JD.equals(isjd)){
                criteria.andEqualTo("isjd", isjd);
            }else{
                criteria.andEqualNvlTo("isjd", Constants.ZDZH_DJXX_ISJD_NOJD, Constants.ZDZH_DJXX_ISJD_NOJD);
            }
            List<ZdzhDjxxDO> zdzhDjxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(zdzhDjxxDOList)) {
                djhZtResponseDTO.setZdzhDjxxDOList(zdzhDjxxDOList);
            }
        }
        return djhZtResponseDTO;
    }

    /**
     * @param djhList
     * @param isjd
     * @return List<DjhZtResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量根据djh查询冻结信息
     */
    @Override
    public List<DjhZtResponseDTO> listDjhZtByDjh(List<String> djhList,String isjd) {
        if (CollectionUtils.isNotEmpty(djhList)) {
            List<DjhZtResponseDTO> djhZtResponseDTOList = new ArrayList<>();
            for (String djh : djhList) {
                djhZtResponseDTOList.add(getDjhZtByDjh(djh,isjd));
            }
            return djhZtResponseDTOList;
        }
        return Collections.EMPTY_LIST;
    }
}
