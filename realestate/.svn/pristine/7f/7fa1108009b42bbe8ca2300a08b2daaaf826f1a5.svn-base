package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.bg.CxBgService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-27
 * @description
 */
@Service
public class CxBgServiceImpl implements CxBgService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Autowired
    private FwZhsService fwZhsService;
    @Autowired
    private FwFcqlrService fwFcqlrService;

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 执行撤销变更操作
     */
    @Override
    public void executeBgRevoke(String bgbh) {
        if (StringUtils.isNotBlank(bgbh)) {
            //1.根据s_sj_hsbgjlb中的bgbh查询h_fw_hs表，获取原房屋数据。
            List<SSjHsbgljbDO> sSjHsbgljbDOList = listBgjlbByBgbh(bgbh);
            //判断是否最后一次变更，如果是，则撤销
            if (isLastBg(sSjHsbgljbDOList)) {
                //2.撤销  删除之前变更新增的户室、子户室、权利人
                deleteRevokeHs(sSjHsbgljbDOList);
                //3.撤销  新增之前删除的户室、子户室、权利人
                insertRevokeHs(sSjHsbgljbDOList);
                //4.删除户室变更记录表
                deleteFwHsBgxx(bgbh);
            }
        }
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bgbh
     * @return void
     * @description 删除户室变更信息表
     */
    private void deleteFwHsBgxx(String bgbh){
        Example example=new Example(FwHsBgxxDO.class);
        example.createCriteria().andEqualTo("bgbh",bgbh);
        entityMapper.deleteByExample(example);
    }

    /**
     * @param sSjHsbgljbDOList
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 撤销  删除之前变更新增的户室
     */
    private void deleteRevokeHs(List<SSjHsbgljbDO> sSjHsbgljbDOList) {
        if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
            for (SSjHsbgljbDO sSjHsbgljbDO : sSjHsbgljbDOList) {
                fwHsService.deleteHsByFwHsIndex(sSjHsbgljbDO.getFwIndex(),false);
                fwZhsService.deleteZhsByFwHsIndex(sSjHsbgljbDO.getFwIndex());
                fwFcqlrService.deleteFcqlrByFwIndex(sSjHsbgljbDO.getFwIndex());
                entityMapper.delete(sSjHsbgljbDO);
            }
        }
    }

    /**
     * @param sSjHsbgljbDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 撤销  新增变更之前的删除的户室
     */
    private void insertRevokeHs(List<SSjHsbgljbDO> sSjHsbgljbDOList) {
        if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
            for (SSjHsbgljbDO sSjHsbgljbDO : sSjHsbgljbDOList) {
                //还原户室
                HFwHsDO hFwHsDO = entityMapper.selectByPrimaryKey(HFwHsDO.class, sSjHsbgljbDO.getYfwIndex());
                if (hFwHsDO != null && StringUtils.isNotBlank(hFwHsDO.getFwHsIndex())) {
                    FwHsDO fwHsDO = new FwHsDO();
                    BeanUtils.copyProperties(hFwHsDO, fwHsDO);
                    fwHsDO.setBdczt(Constants.BDCZT_KY);
                    fwHsService.insertFwHs(fwHsDO);
                    entityMapper.deleteByPrimaryKey(HFwHsDO.class, hFwHsDO.getFwHsIndex());
                }

                //还原子户室
                Example zhsExample = new Example(HFwZhsDO.class);
                Example.Criteria zhsCriteria = zhsExample.createCriteria();
                zhsCriteria.andEqualTo("fwHsIndex", sSjHsbgljbDO.getYfwIndex());
                List<HFwZhsDO> hFwZhsDOList=entityMapper.selectByExample(zhsExample);
                if(CollectionUtils.isNotEmpty(hFwZhsDOList)){
                    for(HFwZhsDO hFwZhsDO:hFwZhsDOList){
                        FwZhsDO fwZhsDO=new FwZhsDO();
                        BeanUtils.copyProperties(hFwZhsDO, fwZhsDO);
                        fwZhsService.insertZhs(fwZhsDO);
                        entityMapper.deleteByPrimaryKey(HFwZhsDO.class,hFwZhsDO.getFwZhsIndex());
                    }
                }

                //还原权利人
                Example qlrExample = new Example(HFwFcqlrDO.class);
                Example.Criteria criteria = qlrExample.createCriteria();
                criteria.andEqualTo("fwIndex", sSjHsbgljbDO.getYfwIndex());
                List<HFwFcqlrDO> hFwFcqlrDOList=entityMapper.selectByExample(qlrExample);
                if(CollectionUtils.isNotEmpty(hFwFcqlrDOList)){
                    for(HFwFcqlrDO hFwFcqlrDO:hFwFcqlrDOList){
                        FwFcqlrDO fwFcqlrDO=new FwFcqlrDO();
                        BeanUtils.copyProperties(hFwFcqlrDO,fwFcqlrDO);
                        fwFcqlrService.insertFwFcQlr(fwFcqlrDO);
                        entityMapper.deleteByPrimaryKey(HFwFcqlrDO.class,hFwFcqlrDO.getFwFcqlrIndex());
                    }
                }
            }
        }
    }

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键查询变更记录表
     */
    @Override
    public List<SSjHsbgljbDO> listBgjlbByBgbh(String bgbh) {
        List<SSjHsbgljbDO> sSjHsbgljbDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(bgbh)) {
            Example example = new Example(SSjHsbgljbDO.class);
            example.createCriteria().andEqualTo("bgbh", bgbh);
            sSjHsbgljbDOList = entityMapper.selectByExample(example);
        }
        return sSjHsbgljbDOList;
    }
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 执行撤销变更操作
     */
    @Override
    public void executeBgRevokeByFwHsIndex(String fwHsIndex) {
        if(StringUtils.isNotBlank(fwHsIndex)){
            Example example = new Example(SSjHsbgljbDO.class);
            example.createCriteria().andEqualTo("fwIndex", fwHsIndex);
            List<SSjHsbgljbDO> sSjHsbgljbDOList = entityMapper.selectByExample(example);
            executeBgRevoke(sSjHsbgljbDOList.get(0).getBgbh());
        }
    }

    /**
     * @param sSjHsbgljbDOList
     * @return boolean
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 判断是否最后一次变更
     */
    private boolean isLastBg(List<SSjHsbgljbDO> sSjHsbgljbDOList) {
        boolean result = false;
        if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
            for (SSjHsbgljbDO sSjHsbgljbDO : sSjHsbgljbDOList) {
                if (StringUtils.isNotBlank(sSjHsbgljbDO.getFwIndex())) {
                    FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(sSjHsbgljbDO.getFwIndex());
                    if (fwHsDO != null) {
                        result = true;
                        break;
                    }
                    //撤销的话，没生成新的户室，所以当类型为灭失的时候直接确定是最后一次变更
                } else if (StringUtils.equals(sSjHsbgljbDO.getBglx(), Constants.BGLX_MS)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
