package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwJsydzrzxxMapper;
import cn.gtmap.realestate.building.core.service.FwJsydzrzxxService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.common.core.domain.BdcLhxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwJsydzrzxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcLhlxEnum;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcLhxxCzrzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/16
 * @description 房屋建设用地自然幢信息服务
 */
@Service
public class FwJsydzrzxxServiceImpl implements FwJsydzrzxxService {

    @Autowired
    private FwJsydzrzxxMapper fwJsydzrzxxMapper;

    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private BdcLhxxCzrzFeignService bdcLhxxCzrzFeignService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Repo repo;

    @Override
    public List<FwJsydzrzxxDO> listFwJsydzrzxx(FwJsydzrzxxQO fwJsydzrzxxQO){
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxQO.getFwJsydzrzxxIndexList())
                ||StringUtils.isNotBlank(fwJsydzrzxxQO.getLszd()) || CollectionUtils.isNotEmpty(fwJsydzrzxxQO.getNotInfwJsydzrzxxIndexList())) {
            return fwJsydzrzxxMapper.listFwJsydzrzxx(fwJsydzrzxxQO);
        }
        return Collections.emptyList();
    }

    @Override
    public Page<FwJsydzrzxxDTO> listFwJsydzrzxxByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listFwJsydzrzxxByPage", map, pageable);
    }

    @Override
    public List<FwJsydzrzxxDTO> listFwJsydzrzxxWithZt(FwJsydzrzxxQO fwJsydzrzxxQO) {
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxQO.getFwJsydzrzxxIndexList())
                ||StringUtils.isNotBlank(fwJsydzrzxxQO.getLszd()) || CollectionUtils.isNotEmpty(fwJsydzrzxxQO.getNotInfwJsydzrzxxIndexList())) {
            return fwJsydzrzxxMapper.listFwJsydzrzxxByPage(fwJsydzrzxxQO);
        }
        return Collections.emptyList();
    }

    @Override
    public void updateFwJsydzrzxxLhdycsPl(FwJsydzrzxxQO fwJsydzrzxxQO) {
        if((CollectionUtils.isNotEmpty(fwJsydzrzxxQO.getFwJsydzrzxxIndexList()) || StringUtils.isNotBlank(fwJsydzrzxxQO.getLszd()))
                && Objects.nonNull(fwJsydzrzxxQO.getLhdycs())){
            fwJsydzrzxxMapper.updateFwJsydzrzxxZtPl(fwJsydzrzxxQO);
        }
    }

    @Override
    public void updateFwJsydzrzxxZtPl(FwJsydzrzxxQO fwJsydzrzxxQO) {
        // 过滤不存在更新条件情况
        if(CollectionUtils.isEmpty(fwJsydzrzxxQO.getFwJsydzrzxxIndexList()) && StringUtils.isBlank(fwJsydzrzxxQO.getLszd()) ){
            return;
        }
        // 过滤更新为空的情况
        if(Objects.isNull(fwJsydzrzxxQO.getLhdycs()) && Objects.isNull(fwJsydzrzxxQO.getLhsdqlzt()) ){
            return;
        }
        fwJsydzrzxxMapper.updateFwJsydzrzxxZtPl(fwJsydzrzxxQO);
    }

    @Override
    public void updateFwJsydzrzxxPl(List<FwJsydzrzxxDO> fwJsydzrzxxDOList) {
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
            this.entityMapper.batchSaveSelective(fwJsydzrzxxDOList);
        }
    }

    /**
     * 判断当前不动产单元号，是否处于量化抵押状态
     * <p>判断方式：
     *      1、判断 FW_JSYDZRZXX 有没有宗地数据，没有则不是量化抵押
     *      2、有宗地数据，并且宗地的lhdycs>0，是量化抵押
     *      3、有宗地数据，存在lhdycs = 0 的， 没有当前逻辑幢数据，则是量化抵押
     *      4、有宗地数据，存在lhdycs = 0 的， 当前逻辑幢 lhdycs = 0 则不是量化抵押；lhdycs > 0 则是量化抵押
     * </p>
     */
    @Override
    public Integer queryLhdyqlZtByBdcdyh(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            String djh = bdcdyh.substring(0,19);
            if(queryLhdyaCount(djh, null)>0){
                if(queryLhdyaCount(djh, CommonConstantUtils.SF_F_DM) > 0){
                    BdcdyResponseDTO bdcdyDTO = bdcdyService.queryBdcdy(bdcdyh, null);
                    if(null != bdcdyDTO && StringUtils.isNotBlank(bdcdyDTO.getFwDcbIndex())) {
                        List<FwJsydzrzxxDO> fwJsydzrzxxDOList = queryFwJsydzrzxxByFwDcbIndex(bdcdyDTO.getFwDcbIndex());
                        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
                            FwJsydzrzxxDO fwJsydzrzxxDO = fwJsydzrzxxDOList.get(0);
                            // 判断当前宗地是否勾选量化抵押业务
                            if(CommonConstantUtils.SF_F_DM.equals(fwJsydzrzxxDO.getLhdycs())){
                                return CommonConstantUtils.SF_F_DM;
                            }else{
                                return CommonConstantUtils.SF_S_DM;
                            }
                        }
                    }
                    // 未入库的逻辑幢信息为量化抵押状态
                    return CommonConstantUtils.SF_S_DM;
                }else{
                    return CommonConstantUtils.SF_S_DM;
                }
            }
        }
        return CommonConstantUtils.SF_F_DM;
    }

    @Override
    public Integer queryLhsdqlZtByBdcdyh(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            BdcdyResponseDTO bdcdyDTO = bdcdyService.queryBdcdy(bdcdyh, null);
            if(null != bdcdyDTO && StringUtils.isNotBlank(bdcdyDTO.getFwDcbIndex())) {
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = queryFwJsydzrzxxByFwDcbIndex(bdcdyDTO.getFwDcbIndex());
                if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
                    // 判断当前的量化首登权利状态
                    FwJsydzrzxxDO fwJsydzrzxxDO = fwJsydzrzxxDOList.get(0);
                    if(CommonConstantUtils.SF_S_DM.equals(fwJsydzrzxxDO.getLhsdqlzt())){
                        return CommonConstantUtils.SF_S_DM;
                    }else{
                        return CommonConstantUtils.SF_F_DM;
                    }
                }
                // 没有量化首登自然幢数据时，要求限制住
                return CommonConstantUtils.SF_F_DM;
            }
        }
        return null;
    }

    /**
     * 查询当前宗地中是否拥有已勾选的楼幢信息
     */
    private int queryLhdyaCount(String lszd, Integer lhdycs){
        if(StringUtils.isNotBlank(lszd)){
            FwJsydzrzxxDO fwJsydzrzxxDO = new FwJsydzrzxxDO();
            fwJsydzrzxxDO.setLszd(lszd);
            if(Objects.nonNull(lhdycs)){
                fwJsydzrzxxDO.setLhdycs(lhdycs);
            }
            return entityMapper.count(fwJsydzrzxxDO);
        }
        return 0;
    }

    /**
     * 根据{@code fwDcbIndex} 查询房屋建设用地自然幢信息
     */
    private List<FwJsydzrzxxDO> queryFwJsydzrzxxByFwDcbIndex(String fwDcbIndex){
        Example example = new Example(FwJsydzrzxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fwDcbIndex", fwDcbIndex);
        return entityMapper.selectByExample(example);
    }

    /**
     * 获取当前当前量化抵押状态
     * <p>判断方式：
     *  1、判断 FW_JSYDZRZXX 有没有宗地数据，没有则不是量化抵押
     *  2、有宗地数据，并且宗地的lhdycs>0，是量化抵押
     *  3、有宗地数据，存在lhdycs = 0 的， 没有当前逻辑幢数据，则是量化抵押
     *  4、有宗地数据，存在lhdycs = 0 的， 当前逻辑幢 lhdycs = 0 则不是量化抵押；lhdycs > 0 则是量化抵押
     *  </p>
     * @param fwDcbIndex 房屋调查表index
     * @param lszd 地籍号
     * @return 量化抵押状态  1：量化抵押状态 0 ：非量化抵押状态
     */
    @Override
    public Integer queryLhdyqlZtByFwDcbIndexAndDjh(String fwDcbIndex, String lszd) {
        if(StringUtils.isNotBlank(lszd) && queryLhdyaCount(lszd, null) > 0){
            if(queryLhdyaCount(lszd, CommonConstantUtils.SF_F_DM) > 0){
                // 宗地存在自然幢信息，并且存在量化抵押状态为 0， 则自然幢的 lhdyqlzt= 1 或 null的情况时，是量化抵押； lhdyqlzt=0 时，不是量化抵押
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = queryFwJsydzrzxxByFwDcbIndex(fwDcbIndex);
                if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)
                        && CommonConstantUtils.SF_F_DM.equals(fwJsydzrzxxDOList.get(0).getLhdycs())){
                    return CommonConstantUtils.SF_F_DM;
                }
                return CommonConstantUtils.SF_S_DM;
            }else{
                // 量化抵押状态都是 1 的情况，则是量化抵押
                return CommonConstantUtils.SF_S_DM;
            }
        }
        // FW_JSYDZRZXX 没有宗地的数据，则不是量化抵押状态
        return CommonConstantUtils.SF_F_DM;
    }

    /**
     * 验证当前地籍号是否存在量化抵押信息
     * @param djh 地籍号
     * @return
     */
    @Override
    public Integer queryZdLhxxByDjh(String djh) {
        if(StringUtils.isNotBlank(djh)){
            // 1、存在量化抵押状态为0的情况，为办理过
            if(queryLhdyaCount(djh, CommonConstantUtils.SF_F_DM) > 0){
                return CommonConstantUtils.SF_S_DM;
            }
            // 2、量化抵押状态都为1的情况，为办理过
            Example example = new Example(FwJsydzrzxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("lszd", djh);
            criteria.andGreaterThanOrEqualTo("lhdycs", 1);
            List<FwJsydzrzxxDO> fwJsydzrzxxDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
                return CommonConstantUtils.SF_S_DM;
            }
        }
        return CommonConstantUtils.SF_F_DM;
    }

    @Override
    public Integer queryLhCfZtByBdcdyh(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            String djh = bdcdyh.substring(0,19);
            // 查询 fw_jsydzrzxx 表是否存在当前宗地的数据
            if(queryLhCfCount(djh, null) > 0){
                BdcdyResponseDTO bdcdyDTO = bdcdyService.queryBdcdy(bdcdyh, null);
                if(null != bdcdyDTO && StringUtils.isNotBlank(bdcdyDTO.getFwDcbIndex())) {
                    List<FwJsydzrzxxDO> fwJsydzrzxxDOList = queryFwJsydzrzxxByFwDcbIndex(bdcdyDTO.getFwDcbIndex());
                    if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
                        FwJsydzrzxxDO fwJsydzrzxxDO = fwJsydzrzxxDOList.get(0);
                        // 判断当前宗地是否勾选量化查封业务
                        if(CommonConstantUtils.SF_F_DM.equals(fwJsydzrzxxDO.getLhcfcs())){
                            return CommonConstantUtils.SF_F_DM;
                        }else{
                            return CommonConstantUtils.SF_S_DM;
                        }
                    }
                }
            }
        }
        return CommonConstantUtils.SF_F_DM;
    }

    @Override
    public Integer queryLhCfxxByBdcdyh(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            String djh = bdcdyh.substring(0,19);
            if(queryLhCfCount(djh, null) > 0){
                Example example = new Example(FwJsydzrzxxDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("lszd", djh);
                criteria.andGreaterThanOrEqualTo("lhcfcs", 0);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = entityMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
                    return fwJsydzrzxxDOList.size();
                }
            }
        }
        return 0;
    }

    /**
     * 查询当前宗地中是否拥有已勾选的楼幢信息
     */
    private int queryLhCfCount(String lszd, Integer lhcfcs){
        if(StringUtils.isNotBlank(lszd)){
            FwJsydzrzxxDO fwJsydzrzxxDO = new FwJsydzrzxxDO();
            fwJsydzrzxxDO.setLszd(lszd);
            if(Objects.nonNull(lhcfcs)){
                fwJsydzrzxxDO.setLhcfcs(lhcfcs);
            }
            return entityMapper.count(fwJsydzrzxxDO);
        }
        return 0;
    }

    @Override
    public Integer checkJsydLhcfztByLhCzrz(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            String djh = bdcdyh.substring(0,19);
            if(queryLhCfCount(djh, null) > 0){
                if(queryLhCfCount(djh, CommonConstantUtils.SF_F_DM) > 0){
                    BdcdyResponseDTO bdcdyDTO = bdcdyService.queryBdcdy(bdcdyh, null);
                    if(null != bdcdyDTO && StringUtils.isNotBlank(bdcdyDTO.getFwDcbIndex())) {
                        String fwDcbIndex = bdcdyDTO.getFwDcbIndex();
                        int lhcfcs = this.computeLhcf(fwDcbIndex, djh);
                        if(lhcfcs > 0){
                            return CommonConstantUtils.SF_S_DM;
                        }else{
                            return CommonConstantUtils.SF_F_DM;
                        }
                    }
                    // 未入库的逻辑幢信息为量化查封状态
                    return CommonConstantUtils.SF_S_DM;
                }else{
                    return CommonConstantUtils.SF_S_DM;
                }
            }
        }
        return CommonConstantUtils.SF_F_DM;
    }

    private int computeLhcf(String fwDcbIndex, String lszd){
        BdcLhxxCzrzDO bdcLhxxCzrzDO = new BdcLhxxCzrzDO();
        bdcLhxxCzrzDO.setLhlx(BdcLhlxEnum.LHCF.getCode());
        bdcLhxxCzrzDO.setFwDcbIndex(fwDcbIndex);
        bdcLhxxCzrzDO.setLszd(lszd);
        List<BdcLhxxCzrzDO> bdcLhxxCzrzDOList = this.bdcLhxxCzrzFeignService.queryBdcLhxxCrzz(bdcLhxxCzrzDO);
        int czlx = 0;
        if(CollectionUtils.isNotEmpty(bdcLhxxCzrzDOList)){
            czlx = bdcLhxxCzrzDOList.stream().mapToInt(BdcLhxxCzrzDO::getCzlx).sum();
        }
        return czlx;
    }

}
