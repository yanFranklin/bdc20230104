package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwYcHsService;
import cn.gtmap.realestate.building.service.YcScHsGlService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsGlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsZzglRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/30
 * @description
 */
@Service
public class YcScHsGlServiceImpl implements YcScHsGlService {

    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private FwYcHsService fwYcHsService;
    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private MessageProvider messageProvider;

    @Override
    public List<FwYchsDO> checkYchsCanGl(List<String> ychsIndexList) {
        List<FwYchsDO> fwYchsDOList = new ArrayList<>();
        for (String ychsIndex : ychsIndexList) {
            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByFwHsIndex(ychsIndex);
            if (fwYchsDO != null) {
                checkYchsFwbm(fwYchsDO);
                fwYchsDOList.add(fwYchsDO);
            }
        }
        return fwYchsDOList;
    }

    @Override
    public List<FwHsDO> checkSchsCanGl(List<String> schsIndexList) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schsIndexList)) {
            for (String schsIndex : schsIndexList) {
                FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(schsIndex);
                if (fwHsDO != null) {
                    // todo 待确认验证
//                    checkBdcdyWfzOrSd(fwHsDO.getBdcdyh());
                    fwHsDOList.add(fwHsDO);
                }
            }
        }
        return fwHsDOList;
    }

    @Override
    public void ycscHsGl(YcScHsGlRequestDTO ycScHsGlDTO) {
        if (CollectionUtils.isEmpty(ycScHsGlDTO.getYchsIndexList())) {
            throw new AppException("预测户室不能为空");
        }
        List<FwHsDO> fwHsDOList = checkSchsCanGl(ycScHsGlDTO.getSchsIndexList());
        List<FwYchsDO> fwYchsDOList = checkYchsCanGl(ycScHsGlDTO.getYchsIndexList());
        for (FwHsDO fwHsDO : fwHsDOList) {
            String ybdcdyh = fwHsDO.getBdcdyh();
            StringBuilder ycfwbm = new StringBuilder("");
            for (FwYchsDO fwYchsDO : fwYchsDOList) {
                ycfwbm.append(fwYchsDO.getFwbm()).append(";");
                if (fwHsDOList.size() == 1 && fwYchsDOList.size() == 1) {
                    fwHsDO.setBdcdyh(fwYchsDO.getBdcdyh());
                }
            }
            if (StringUtils.isNotBlank(ycfwbm)) {
                ycfwbm.deleteCharAt(ycfwbm.length() - 1);
                fwHsDO.setYcfwbm(ycfwbm.toString());
                fwHsService.updateFwHsWithChangeBdcdyh(fwHsDO, false,ybdcdyh);
            }
        }
    }

    @Override
    public void ycscHsQxGl(YcScHsGlRequestDTO ycScHsGlDTO) {
        for (String schsIndex : ycScHsGlDTO.getSchsIndexList()) {
            FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(schsIndex);
            if (fwHsDO != null) {
                String ybdcdyh = fwHsDO.getBdcdyh();
                fwHsDO.setYcfwbm("");
                fwHsDO.setBdcdyh("");
                fwHsService.updateFwHsWithChangeBdcdyh(fwHsDO, true,ybdcdyh);
            }
        }
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 整幢取消关联
     */
    @Override
    public void ycscZzQxgl(String fwDcbIndex) {
        if(StringUtils.isNotBlank(fwDcbIndex)){
            List<FwYchsDO> ychsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
            if(CollectionUtils.isEmpty(ychsDOList)){
                throw new EntityNotFoundException("当前幢下预测户室为空,无法取消关联");
            }
            if(CollectionUtils.isEmpty(fwHsDOList)){
                throw new EntityNotFoundException("当前幢下实测户室为空,无法取消关联");
            }
            qxgl(fwHsDOList);
        }else{
            throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsDOList
     * @return void
     * @description 循环实测户室 取消关联
     */
    private void qxgl(List<FwHsDO> fwHsDOList){
        for (FwHsDO fwHsDO : fwHsDOList) {
            if (fwHsDO != null) {
                fwHsDO.setYcfwbm("");
                fwHsDO.setBdcdyh("");
                fwHsService.updateFwHs(fwHsDO, true);
            }
        }
    }

    /**
     * @param ycScHsZzglRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 整幢关联
     */
    @Override
    @Transactional
    public void zzgl(YcScHsZzglRequestDTO ycScHsZzglRequestDTO) {
        if(ycScHsZzglRequestDTO != null
                && StringUtils.isNotBlank(ycScHsZzglRequestDTO.getFwDcbIndex())
                && StringUtils.isNotBlank(ycScHsZzglRequestDTO.getGlgxType())
                && StringUtils.isNotBlank(ycScHsZzglRequestDTO.getCxgl())){
            String fwDcbIndex = ycScHsZzglRequestDTO.getFwDcbIndex();
            String glgxType = ycScHsZzglRequestDTO.getGlgxType();
            List<FwYchsDO> ychsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
            if(CollectionUtils.isEmpty(ychsDOList)){
                throw new EntityNotFoundException("预测户室为空");
            }
            if(CollectionUtils.isEmpty(fwHsDOList)){
                throw new EntityNotFoundException("实测户室为空");
            }

            boolean filterGlhs = false;
            if(StringUtils.equals(Constants.ZZGL_CXGL_TRUE,ycScHsZzglRequestDTO.getCxgl())){
                // 先取消整幢关联
                qxgl(fwHsDOList);
            }else{
                filterGlhs = true;
            }

            // FWBM 类型关联
            if(StringUtils.equals(Constants.ZZGL_FWBM,glgxType)){
                zzglYcWithFwbm(ychsDOList,fwHsDOList,filterGlhs);
            }
            // DYH WLCS FJH 方式关联
            if(StringUtils.equals(Constants.ZZGL_DYHWLCSFJH,glgxType)){
                zzglYcWitDyhWlcsFjh(ychsDOList,filterGlhs);
            }
        }else{
            throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ychsDOList
     * @param fwHsDOList
     * @return void
     * @description  使用FWBM关联
     */
    private void zzglYcWithFwbm(List<FwYchsDO> ychsDOList,List<FwHsDO> fwHsDOList,boolean filterGlhs){
        for(FwYchsDO ychsDO:ychsDOList){
            if(StringUtils.isNotBlank(ychsDO.getFwbm())){
                List<FwHsDO> sameFwbmHsList = getFwhsListByFwbm(ychsDO.getFwbm(),fwHsDOList,filterGlhs);
                if(CollectionUtils.isNotEmpty(sameFwbmHsList)){
                    List<FwYchsDO> glYchsList = new ArrayList<>();
                    glYchsList.add(ychsDO);
                    ycGlschs(sameFwbmHsList,glYchsList);
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ychsDOList
     * @return void
     * @description
     */
    private void zzglYcWitDyhWlcsFjh(List<FwYchsDO> ychsDOList,boolean filterGlhs){
        for(FwYchsDO ychsDO : ychsDOList){
            if(StringUtils.isNotBlank(ychsDO.getDyh())
                    && StringUtils.isNotBlank(ychsDO.getFjh())
                    && ychsDO.getWlcs() != null){
                Example example = new Example(FwHsDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("dyh",ychsDO.getDyh())
                        .andEqualTo("wlcs",ychsDO.getWlcs())
                        .andEqualTo("fjh",ychsDO.getFjh())
                        .andEqualTo("fwDcbIndex",ychsDO.getFwDcbIndex());
                // 如果需要过滤已关联的户室  则增加查询条件
                if(filterGlhs){
                    criteria.andIsNull("ycfwbm");
                }
                List<FwHsDO> fwHsDOList = entityMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(fwHsDOList)){
                    List<FwYchsDO> glYchsList = new ArrayList<>();
                    glYchsList.add(ychsDO);
                    ycGlschs(fwHsDOList,glYchsList);
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsDOList
     * @param fwYchsDOList
     * @return void
     * @description 多对多关联
     */
    private void ycGlschs(List<FwHsDO> fwHsDOList,List<FwYchsDO> fwYchsDOList){
        for (FwHsDO fwHsDO : fwHsDOList) {
            String ybdcdyh = fwHsDO.getBdcdyh();
            StringBuilder ycfwbm = new StringBuilder("");
            for (FwYchsDO fwYchsDO : fwYchsDOList) {
                if(StringUtils.isBlank(fwYchsDO.getFwbm())){
                    fwYchsDO.setFwbm(UUIDGenerator.generate());
                    fwYcHsService.updateFwYcHs(fwYchsDO, false);
                }
                ycfwbm.append(fwYchsDO.getFwbm()).append(";");
                if (fwHsDOList.size() == 1 && fwYchsDOList.size() == 1) {
                    fwHsDO.setBdcdyh(fwYchsDO.getBdcdyh());
                }
            }
            if (StringUtils.isNotBlank(ycfwbm)) {
                ycfwbm.deleteCharAt(ycfwbm.length() - 1);
                fwHsDO.setYcfwbm(ycfwbm.toString());
                fwHsService.updateFwHsWithChangeBdcdyh(fwHsDO, false,ybdcdyh);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbm
     * @param fwHsDOList
     * @return void
     * @description 循环FWHSLIST 获取 FWBM 相同的户室列表
     */
    private List<FwHsDO> getFwhsListByFwbm(String fwbm,List<FwHsDO> fwHsDOList,boolean filterGlhs){
        List<FwHsDO> saveFwbmHsList = new ArrayList<>();
        for(FwHsDO fwHsDO:fwHsDOList){
            if(StringUtils.equals(fwbm,fwHsDO.getFwbm())){
                // 如果 需要过滤已关联户室 并且 户室的YCFWBM 不为空 则跳过
                if(filterGlhs && StringUtils.isNotBlank(fwHsDO.getYcfwbm())){
                    continue;
                }else{
                    saveFwbmHsList.add(fwHsDO);
                }
            }
        }
        return saveFwbmHsList;
    }


    private void checkYchsFwbm(FwYchsDO fwYchsDO) {
        if (StringUtils.isNotBlank(fwYchsDO.getFwbm())) {
            if (CollectionUtils.isNotEmpty(fwHsService.listFwhsByYcfwbm(fwYchsDO.getFwbm()))) {
                throw new AppException("预测户室已经关联了实测户室");
            }
        } else {
            fwYchsDO.setFwbm(UUIDGenerator.generate());
            fwYcHsService.updateFwYcHs(fwYchsDO, false);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return void
     * @description 验证不动产单元未发证或已锁定
    private void checkBdcdyWfzOrSd(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            SSjBdcdyhxsztDO sSjBdcdyhxsztDO = bdcdyZtService.querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            if (sSjBdcdyhxsztDO != null && (!StringUtils.equals(sSjBdcdyhxsztDO.getDjzt(), "1")
                    || (sSjBdcdyhxsztDO.getXssdcs() != null && sSjBdcdyhxsztDO.getXssdcs() > 0))) {
                throw new AppException("不动产单元未发证或已经锁定");
            }
        }
    }*/
}
