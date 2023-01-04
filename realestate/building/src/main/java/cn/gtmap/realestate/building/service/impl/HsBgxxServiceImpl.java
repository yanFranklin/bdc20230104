package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.BgbhService;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.service.HsBgxxService;
import cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBgRequestDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.building.FwHsHbVO;
import cn.gtmap.realestate.common.core.vo.building.FwhsBgVO;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/30
 * @description
 */
@Service
public class HsBgxxServiceImpl implements HsBgxxService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private BgbhService bgbhService;

    /**
     * @param fwHsBgxxDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 保存户室变更信息
     */
    @Override
    public FwHsBgxxDO insertFwHsBgxx(FwHsBgxxDO fwHsBgxxDO) {
        if (!CheckEntityUtils.checkPk(fwHsBgxxDO)) {
            fwHsBgxxDO.setHsBgIndex(UUIDGenerator.generate());
        }
        if (StringUtils.isBlank(fwHsBgxxDO.getBgbh())) {
            String bgbh = bgbhService.maxBgbh();
            if (StringUtils.isNotBlank(bgbh)) {
                fwHsBgxxDO.setBgbh(bgbh);
            }
        }
        entityMapper.insertSelective(fwHsBgxxDO);
        return fwHsBgxxDO;
    }

    /**
     * @param bgbh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据变更编号查询意见信息
     */
    @Override
    public FwHsBgxxDO getFwHsBgxxByBgbh(String bgbh) {
        FwHsBgxxDO fwHsBgxxDO = new FwHsBgxxDO();
        if (StringUtils.isNotBlank(bgbh)) {
            Example example = new Example(FwHsBgxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bgbh", bgbh);
            List<FwHsBgxxDO> fwHsBgxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(fwHsBgxxDOList)) {
                fwHsBgxxDO = fwHsBgxxDOList.get(0);
            }
        }
        return fwHsBgxxDO;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室变更dto获取有效不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwhsBgRequestDTO(FwhsBgRequestDTO fwhsBgRequestDTO) {
        List<String> bdcdyhList=new ArrayList<>();
        if(fwhsBgRequestDTO!=null&&CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getyFwHsIndexList())){
            for(String fwHsIndex:fwhsBgRequestDTO.getyFwHsIndexList()){
                if(StringUtils.isNotBlank(fwHsIndex)){
                    FwHsDO fwHsDO=fwHsService.queryFwHsByIndex(fwHsIndex);
                    if(fwHsDO!=null){
                        bdcdyhList.addAll(fwHsService.listValidBdcdyhByFwHsDO(fwHsDO));
                    }
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室变更VO获取有效不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwhsBgVo(FwhsBgVO FwhsBgVO) {
        List<String> bdcdyhList=new ArrayList<>();
        if(FwhsBgVO!=null&&StringUtils.isNotBlank(FwhsBgVO.getFwHsIndex())){
            bdcdyhList=fwHsService.listValidBdcdyhByFwHsIndex(FwhsBgVO.getFwHsIndex());
        }
        return bdcdyhList;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室合并VO获取有效不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwhsHbVo(FwHsHbVO fwHsHbVO) {
        List<String> bdcdyhList=new ArrayList<>();
        if(fwHsHbVO!=null&&CollectionUtils.isNotEmpty(fwHsHbVO.getYfwHsIndexList())){
            for(String fwHsIndex:fwHsHbVO.getYfwHsIndexList()){
                bdcdyhList.addAll(fwHsService.listValidBdcdyhByFwHsIndex(fwHsIndex));
            }
        }
        return bdcdyhList;
    }
}