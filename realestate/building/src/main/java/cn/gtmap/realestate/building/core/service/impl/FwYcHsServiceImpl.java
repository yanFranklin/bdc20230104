package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.bo.FwbmBO;
import cn.gtmap.realestate.building.core.mapper.FwYchsMapper;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.FwhsYwService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.LpbUtils;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.YcHsZtResDTO;
import cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.gtmap.realestate.building.core.service.impl.FwHsServiceImpl.PER_BATCH_INSERT_MAXNUM;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-10
 * @description 房屋预测户室serviceImpl
 */
@Service
public class FwYcHsServiceImpl implements FwYcHsService {

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 实体查询Mapper
     */
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private FwZhsService fwZhsService;
    @Autowired
    private FwFcqlrService fwFcqlrService;
    @Autowired
    private BdcdyhService bdcdyhService;
    @Autowired
    private Repo repo;

    @Autowired
    private FwhsYwService fwhsYwService;

    @Autowired
    private FwYchsMapper fwYchsMapper;

    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;

    /**
     * @param
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH查询预测户室基本信息
     */
    @Override
    public FwYchsDO queryFwYcHsByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(FwYchsDO.class);
            example.createCriteria().andEqualTo("bdcdyh", bdcdyh);
            List<FwYchsDO> fwYcHsDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(fwYcHsDOList)) {
                return fwYcHsDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param fwYchsDO
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增预测户室接口服务实现
     */
    @Override
    public FwYchsDO insertFwYcHs(FwYchsDO fwYchsDO) {
        if (fwYchsDO != null) {
            if (StringUtils.isBlank(fwYchsDO.getFwHsIndex())) {
                fwYchsDO.setFwHsIndex(UUIDGenerator.generate());
            }
            if (StringUtils.isBlank(fwYchsDO.getBdcdyh()) && StringUtils.isNotBlank(fwYchsDO.getFwDcbIndex())) {
                FwLjzDO fwLjzDO = entityMapper.selectByPrimaryKey(FwLjzDO.class, fwYchsDO.getFwDcbIndex());
                if (fwLjzDO != null && StringUtils.equals(Constants.BDCDYFWLX_H, fwLjzDO.getBdcdyfwlx())) {
                    String bdcdyh = bdcdyhService.creatFwBdcdyhByFwDcbIndex(fwLjzDO.getFwDcbIndex());
                    if (StringUtils.isNotBlank(bdcdyh)) {
                        fwYchsDO.setBdcdyh(bdcdyh);
                        fwYchsDO.setBdczt(Constants.BDCZT_KY);
                        if (StringUtils.isBlank(fwYchsDO.getFwbm()) && StringUtils.isNotBlank(fwYchsDO.getBdcdyh())) {
                            fwYchsDO.setFwbm(new FwbmBO(fwYchsDO.getBdcdyh()).getFwbm());
                        }
                    }
                }
            }
        }
        entityMapper.insertSelective(fwYchsDO);
        return fwYchsDO;
    }

    /**
     * @param fwYchsDO
     * @param updateNull true表示空字段更新，false，表示空字段不更新
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改预测户室接口服务实现
     */
    @Override
    public Integer updateFwYcHs(FwYchsDO fwYchsDO, boolean updateNull) {
        Integer updateResult = 0;
        if (fwYchsDO != null && CheckEntityUtils.checkPk(fwYchsDO)) {
            if (updateNull) {
                updateResult = entityMapper.updateByPrimaryKeyNull(fwYchsDO);
            } else {
                updateResult = entityMapper.updateByPrimaryKeySelective(fwYchsDO);
            }
        }
        return updateResult;
    }

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除户室接口服务
     */
    @Override
    @Transactional
    public Integer deleteYcHsByFwHsIndex(String fwHsIndex,boolean withRelated) {
        Integer deleteResult = 0;
        if (StringUtils.isNotBlank(fwHsIndex)) {
            deleteResult = entityMapper.deleteByPrimaryKey(FwYchsDO.class, fwHsIndex);
            if(withRelated){
                fwFcqlrService.deleteFcqlrByFwIndex(fwHsIndex);
                fwZhsService.deleteZhsByFwHsIndex(fwHsIndex);
            }
        }
        return deleteResult;
    }

    /**
     * @param fwYchsDO
     * @param withRelated
     * @return int
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据查询记录删除预测户室
     */
    @Override
    public int deleteYchs(FwYchsDO fwYchsDO, boolean withRelated) {
        int deleteResult = 0;
        if (fwYchsDO != null) {
            deleteResult = entityMapper.delete(fwYchsDO);
            if(withRelated){
                fwFcqlrService.deleteFcqlrByFwIndex(fwYchsDO.getFwHsIndex());
                fwZhsService.deleteZhsByFwHsIndex(fwYchsDO.getFwHsIndex());
            }
        }
        return deleteResult;
    }

    /**
     * @param fwYchsDO
     * @param updateNull
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    @Override
    public Integer updateFwYchsWithChangeBdcdyh(FwYchsDO fwYchsDO, boolean updateNull) {
        int result = 0;
        if(fwYchsDO != null
                && StringUtils.isNotBlank(fwYchsDO.getFwHsIndex())){
            fwYchsDO.setBdczt(StringUtils.isNotBlank(fwYchsDO.getBdcdyh())?Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateFwYcHs(fwYchsDO,updateNull);
        }
        return result;
    }

    /**
     * @param fwYchsDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    @Override
    public Integer updateFwYchsWithChangeBdcdyh(FwYchsDO fwYchsDO, boolean updateNull, String ybdcdyh) {
        int result = 0;
        if(fwYchsDO != null){
            fwYchsDO.setBdczt(StringUtils.isNotBlank(fwYchsDO.getBdcdyh())?Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateFwYcHs(fwYchsDO,updateNull);
        }
        return result;
    }


    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description
     */
    @Override
    public Page<FwYchsDO> listYchsByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listYchsByPageOrder", map, pageable);
    }

    /**
     * @param pageable
     * @param map
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">liyinqiao</a>
     * @description 关联功能分页查询预测户室信息
     */
    @Override
    public Page<Map> glListYchsByPage(Pageable pageable, Map map) {
        Page<FwYchsDO> result = repo.selectPaging("listYchsByPageOrder", map, pageable);
        List<Map> newResultList = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(result.getContent())){
            List<FwYchsDO> content = result.getContent();
            for(FwYchsDO temp : content){
                Map mapTemp = LpbUtils.parseObjectToMap(temp);
                // 查询关联户室
                List<FwHsDO> glHsList = fwhsYwService.listGlFwhs(temp);
                if(CollectionUtils.isNotEmpty(glHsList)){
                    String glhs = "";
                    for(FwHsDO fwHsDO : glHsList){
                        if(StringUtils.isNotBlank(fwHsDO.getFjh())){
                            glhs += fwHsDO.getFjh() + ",";
                        }
                    }
                    if(StringUtils.isNotBlank(glhs)){
                        glhs = glhs.substring(0,glhs.length()-1);
                    }
                    mapTemp.put("glhs",glhs);
                }
                newResultList.add(mapTemp);
            }
        }
        return new PageImpl(newResultList,pageable,result.getTotalElements());
    }

    /**
     * @param fwHsDO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询关联的预测户室列表
     */
    @Override
    public List<FwYchsDO> listGlYchs(FwHsDO fwHsDO) {
        if(fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getYcfwbm())){
            Example example = new Example(FwYchsDO.class);
            if(fwHsDO.getYcfwbm().contains(";")){
                String[] fwbmArr = fwHsDO.getYcfwbm().split(";");
                example.createCriteria().andIn("fwbm", Arrays.asList(fwbmArr));
                return entityMapper.selectByExample(example);
            }else{
                Map map=new HashMap();
                List<String> fwbms=new ArrayList<>();
                fwbms.add(fwHsDO.getYcfwbm());
                map.put("fwbms",fwbms);
                return fwYchsMapper.listYchsByPageOrder(map);
            }

        }
        return null;
    }

    @Override
    public YchsAndQlrResponseDTO queryYchsAndQlrByBdcdyh(String bdcdyh) {
        YchsAndQlrResponseDTO ychsAndQlrResponseDTO = null;
        FwYchsDO fwYchsDO = queryFwYcHsByBdcdyh(bdcdyh);
        if (fwYchsDO != null && StringUtils.isNotBlank(fwYchsDO.getFwHsIndex())) {
            ychsAndQlrResponseDTO = new YchsAndQlrResponseDTO();
            ychsAndQlrResponseDTO.setFwYchsDO(fwYchsDO);
            List<FwFcqlrDO> fcqlrDOList = fwFcqlrService.listFwFcQlrByFwIndex(fwYchsDO.getFwHsIndex());
            if (CollectionUtils.isNotEmpty(fcqlrDOList)) {
                ychsAndQlrResponseDTO.setFwFcqlrDOList(fcqlrDOList);
            }
        }
        return ychsAndQlrResponseDTO;
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据LJZ主键查询预测户室列表
     */
    @Override
    public List<FwYchsDO> queryYchsByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Example example = new Example(FwYchsDO.class);
            example.createCriteria().andEqualTo("fwDcbIndex", fwDcbIndex);
            example.setOrderByClause("wlcs desc,dyh asc,fjh asc,sxh asc");
            List<FwYchsDO> ychsList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(ychsList)) {
                return ychsList;
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据LJZ主键查询预测户室列表，不进行排序
     */
    @Override
    public List<FwYchsDO> queryYchsByFwDcbIndexNoSort(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Example example = new Example(FwYchsDO.class);
            example.createCriteria().andEqualTo("fwDcbIndex", fwDcbIndex);
            List<FwYchsDO> ychsList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(ychsList)) {
                return ychsList;
            }
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<FwhsQlrDTO> queryYchsWithQlr(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("fwDcbIndex", fwDcbIndex);
            return fwYchsMapper.listYchsWithQlr(paramMap);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询预测房屋户室
     */
    @Override
    public FwYchsDO queryFwYcHsByFwHsIndex(String fwHsIndex) {
        return entityMapper.selectByPrimaryKey(FwYchsDO.class, fwHsIndex);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwYchsDOList
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @description 批量新增预测户室
     */
    @Override
    public List<FwYchsDO> batchInsertFwYchs(List<FwYchsDO> fwYchsDOList) {
        List<FwYchsDO> insertList = new ArrayList<>();
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
            for (int i = 0; i < fwYchsDOList.size(); i++) {
                FwYchsDO curFwcHs = fwYchsDOList.get(i);
                if (!CheckEntityUtils.checkFk(curFwcHs)) {
                    break;
                }
                if (!CheckEntityUtils.checkPk(curFwcHs)) {
                    curFwcHs.setFwHsIndex(UUIDGenerator.generate());
                }
                //处理bdcdyh，fwbm
                if (StringUtils.isBlank(curFwcHs.getBdcdyh())) {
                    curFwcHs.setBdcdyh(bdcdyhService.creatFwBdcdyhByFwDcbIndex(curFwcHs.getFwDcbIndex()));
                    if(StringUtils.isNotBlank(curFwcHs.getBdcdyh())){
                        curFwcHs.setBdczt(Constants.BDCZT_KY);
                    }
                }
                if (StringUtils.isBlank(curFwcHs.getFwbm()) && StringUtils.isNotBlank(curFwcHs.getBdcdyh())) {
                    curFwcHs.setFwbm(new FwbmBO(curFwcHs.getBdcdyh()).getFwbm());
                }
                bdcdyhList.add(curFwcHs.getBdcdyh());
                insertList.add(curFwcHs);
                if (insertList.size() == PER_BATCH_INSERT_MAXNUM) {
                    entityMapper.insertBatchSelective(insertList);
                    bdcdyhList = new ArrayList<>();
                    insertList = new ArrayList<>();
                }
            }
        }

        if (CollectionUtils.isNotEmpty(insertList)) {
            entityMapper.insertBatchSelective(insertList);
        }
        return insertList;
    }
    /**
     * @param fwYchsDOList
     * @param updateNull
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋预测户室（批量）
     */
    @Override
    public void batchUpdateFwYcHs(List<FwYchsDO> fwYchsDOList, boolean updateNull) {
        if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
            for (FwYchsDO fwYchsDO : fwYchsDOList) {
                if (fwYchsDO == null || !CheckEntityUtils.checkPkAndFk(fwYchsDO)) {
                    break;
                }
               updateFwYcHs(fwYchsDO,updateNull);
            }
        }
    }

    /**
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWBM 查询可用的 FWYCHS
     */
    @Override
    public List<FwYchsDO> listKyFwYchsByFwbm(String fwbm) {
        if(StringUtils.isNotBlank(fwbm)){
            Example example = new Example(FwYchsDO.class);
            example.createCriteria().andEqualTo("fwbm", fwbm)
                    .andNotEqualNvlTo("bdczt", Constants.BDCZT_BKY, Constants.BDCZT_KY);
            List<FwYchsDO> resultList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList;
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param ysfwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 YSFWBM 查询可用的 FWYCHS
     */
    @Override
    public List<FwYchsDO> listKyFwYchsByYsfwbm(String ysfwbm, String zl) {
        if (StringUtils.isNotBlank(ysfwbm) || StringUtils.isNotBlank(zl)) {
            Example example = new Example(FwYchsDO.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(ysfwbm)) {
                criteria.andEqualTo("ysfwbm", ysfwbm).andNotEqualNvlTo("bdczt", Constants.BDCZT_BKY, Constants.BDCZT_KY);
            }
            if (StringUtils.isNotBlank(zl)) {
                criteria.andEqualTo("zl", zl);
            }
            List<FwYchsDO> resultList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList;
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋fwdcbindex查询预测户室的信息和状态
     * @date : 2020/12/10 10:12
     */
    @Override
    public List<YcHsZtResDTO> listFwychsZt(String fwDcbIndex,String qjgldm) {
        if(StringUtils.isBlank(fwDcbIndex)) {
            throw new AppException("查询预测户室缺失房屋调查表Index");
        }
        List<FwYchsDO> fwYchsDOList = this.queryYchsByFwDcbIndex(fwDcbIndex);
        List<YcHsZtResDTO> ycHsZtResDTOList = new ArrayList<>(CollectionUtils.size(fwYchsDOList));
        if(CollectionUtils.isNotEmpty(fwYchsDOList)) {
            for(FwYchsDO fwYchsDO : fwYchsDOList) {
                if(StringUtils.isNotBlank(fwYchsDO.getBdcdyh())) {
                    YcHsZtResDTO ycHsZtResDTO = new YcHsZtResDTO();
                    BeanUtils.copyProperties(fwYchsDO,ycHsZtResDTO);
                    BdcdyhZtResponseDTO bdcdyhZtResponseDTO = bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(fwYchsDO.getBdcdyh(),qjgldm);
                    if(Objects.nonNull(bdcdyhZtResponseDTO)) {
                        ycHsZtResDTO.setSfcf(bdcdyhZtResponseDTO.getCf() ? 1 : 0);
                        ycHsZtResDTO.setSfdy(bdcdyhZtResponseDTO.getDya() ? 1 : 0);
                        ycHsZtResDTO.setSfsd(bdcdyhZtResponseDTO.getSd() ? 1 : 0);
                        ycHsZtResDTO.setSfyg(bdcdyhZtResponseDTO.getYg() ? 1: 0);
                        ycHsZtResDTO.setSfyy(bdcdyhZtResponseDTO.getYy() ? 1 :0);
                    }
                    ycHsZtResDTOList.add(ycHsZtResDTO);
                }
            }
        }
        return ycHsZtResDTOList;
    }

    /**
     * @param YSFWBM
     * @author wangyinghao
     * @description 根据房屋YSFWBM查询预测户室的信息和状态
     * @date : 2020/12/10 10:12
     */
    @Override
    public List<FwYchsDO> listFwYchsByYsfwbm(String YSFWBM) {
        return fwYchsMapper.listFwYchsByYsfwbm(YSFWBM);
    }

}
