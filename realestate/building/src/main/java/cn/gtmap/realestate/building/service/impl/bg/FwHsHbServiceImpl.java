package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.bg.FwHsBgAbstractService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.ManageConfigUtil;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.core.domain.building.HFwZhsDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.NumberUtil;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-01
 * @description
 */
@Service
public class FwHsHbServiceImpl extends FwHsBgAbstractService {

    @Override
    public void dealBgjl(FwBgBO<FwHsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getyList())
                && CollectionUtils.isNotEmpty(bgBO.getNewList())
                && bgBO.getyList().size() > 1
                && bgBO.getNewList().size() == 1) {
            FwHsDO newFwHsDO = bgBO.getNewList().get(0);
            for (FwHsDO yFwHsDO : bgBO.getyList()) {
                saveSingleHsbgjlb(bgBO.getBgbh(), bgBO.getBglx(), yFwHsDO, newFwHsDO);
            }
        }
    }

    @Override
    public void checkParam(FwBgBO<FwHsDO> bgBO) {
        if (StringUtils.isBlank(bgBO.getBgbh())) {
            throw new MissingArgumentException("bgbh");
        }
        if (CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() == 1) {
            throw new MissingArgumentException("yFwHsDOList");
        }
        if (CollectionUtils.isEmpty(bgBO.getNewList()) || bgBO.getNewList().size() != 1) {
            throw new MissingArgumentException("fwHsDOList");
        }
    }

    @Override
    public void dealZHs(FwBgBO<FwHsDO> bgBO) {
        String config = ManageConfigUtil.getHsHbConfig().getHsfrzhsz();
        // ????????????????????? ????????????????????? ??????????????? ?????? ?????????????????????
        if(StringUtils.equals(Constants.FWHB_ZHS_CONFIG_CHOOSE, config)){
            delZhs(bgBO);
        }else{
            // ??????????????????
            extendAllZhs(bgBO);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description ??????????????????
     */
    private void extendAllZhs(FwBgBO<FwHsDO> bgBO){
        List<HFwZhsDO> bakList = new ArrayList<>();
        List<FwZhsDO> newZhsDOList = new ArrayList<>();
        FwHsDO newFwHs = bgBO.getNewList().get(0);
        for(FwHsDO yFwHs : bgBO.getyList()){
            List<FwZhsDO> fwZhsDOList = fwZhsService.listFwZhsByFwHsIndex(yFwHs.getFwHsIndex());
            if(CollectionUtils.isNotEmpty(fwZhsDOList)){
                for(FwZhsDO fwZhsDO : fwZhsDOList){
                    HFwZhsDO bakZhs = new HFwZhsDO();
                    BeanUtils.copyProperties(fwZhsDO,bakZhs);
                    bakList.add(bakZhs);

                    FwZhsDO nFwZhs = new FwZhsDO();
                    BeanUtils.copyProperties(fwZhsDO,nFwZhs);
                    nFwZhs.setFwZhsIndex(UUIDGenerator.generate());
                    nFwZhs.setFwHsIndex(newFwHs.getFwHsIndex());
                    newZhsDOList.add(nFwZhs);
                }
                // ??????
                fwZhsService.deleteZhsByFwHsIndex(yFwHs.getFwHsIndex());
            }
        }
        if(CollectionUtils.isNotEmpty(bakList)
                && CollectionUtils.isNotEmpty(newZhsDOList)){
            // ??????
            entityMapper.insertBatchSelective(bakList);
            // ??????
            fwZhsService.batchInsert(newZhsDOList);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description ??????????????????
     */
    private void delZhs(FwBgBO<FwHsDO> bgBO){
        List<HFwZhsDO> bakList = new ArrayList<>();
        for(FwHsDO yFwHs : bgBO.getyList()){
            List<FwZhsDO> fwZhsDOList = fwZhsService.listFwZhsByFwHsIndex(yFwHs.getFwHsIndex());
            if(CollectionUtils.isNotEmpty(fwZhsDOList)){
                for(FwZhsDO fwZhsDO : fwZhsDOList){
                    HFwZhsDO bakZhs = new HFwZhsDO();
                    BeanUtils.copyProperties(fwZhsDO,bakZhs);
                    bakList.add(bakZhs);
                }
                // ??????
                fwZhsService.deleteZhsByFwHsIndex(yFwHs.getFwHsIndex());
            }
        }
        if(CollectionUtils.isNotEmpty(bakList)){
            // ??????
            entityMapper.insertBatchSelective(bakList);
        }
    }

    @Override
    public void dealQlr(FwBgBO<FwHsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getyList())) {
            for (FwHsDO yFwHs : bgBO.getyList()) {
                if (StringUtils.isNotBlank(yFwHs.getFwHsIndex())) {
                    List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yFwHs.getFwHsIndex());
                    if (CollectionUtils.isNotEmpty(yQlrList)) {
                        fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwHs.getFwHsIndex(), yQlrList, null);
                    }
                }
            }
        }
    }

    @Override
    public void insert(FwBgBO<FwHsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getNewList())) {
            List<FwHsDO> newList = new ArrayList<>();
            //????????????
            //1.??????????????????
            Double sumJzmj = NumberUtil.formatDigit(bgBO.getyList().stream().filter(yHsDO -> Objects.nonNull(yHsDO.getScjzmj())).mapToDouble(FwHsDO::getScjzmj).sum(), 4);
            //2. ????????????????????????
            Double sumTnjzmj = NumberUtil.formatDigit(bgBO.getyList().stream().filter(yHsDO -> Objects.nonNull(yHsDO.getSctnjzmj())).mapToDouble(FwHsDO::getSctnjzmj).sum(), 4);
            //3. ????????????????????????
            Double sumFtjzmj = NumberUtil.formatDigit(bgBO.getyList().stream().filter(yHsDO -> Objects.nonNull(yHsDO.getScftjzmj())).mapToDouble(FwHsDO::getScftjzmj).sum(), 4);
            //4. ?????????????????????
            String fjh = StringToolUtils.resolveBeanToAppendStr(bgBO.getyList(), "getFjh", CommonConstantUtils.ZF_YW_XG);
            for (FwHsDO fwHsDO : bgBO.getNewList()) {
                fwHsDO.setFwHsIndex(UUIDGenerator.generate());
                fwHsDO.setBdcdyh(null);
                fwHsDO.setScjzmj(sumJzmj);
                fwHsDO.setScftjzmj(sumFtjzmj);
                fwHsDO.setSctnjzmj(sumTnjzmj);
                fwHsDO.setFjh(fjh);
                newList.add(fwHsDO);
            }
            bgBO.setNewList(fwHsService.batchInsertFwHs(newList));
        }
    }

    @Override
    public void delete(FwBgBO<FwHsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getyList())) {
            for (FwHsDO yFwhs : bgBO.getyList()) {
                fwHsService.deleteFwhs(yFwhs, false);
            }
        }
    }

}
