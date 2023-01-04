package cn.gtmap.realestate.building.service.impl.bg;


import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.bg.FwYcHsBgAbstractService;
import cn.gtmap.realestate.common.core.domain.building.*;
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
 * @program: realestate
 * @description: 房屋预测户室合并实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-04-08 09:59
 **/
@Service
public class FwYcHsHbServiceImpl extends FwYcHsBgAbstractService {
    @Override
    public void dealBgjl(FwBgBO<FwYchsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getyList())
                && CollectionUtils.isNotEmpty(bgBO.getNewList())
                && bgBO.getyList().size() > 1
                && bgBO.getNewList().size() == 1) {
            FwYchsDO newFwHsDO = bgBO.getNewList().get(0);
            for (FwYchsDO yYcFwHsDO : bgBO.getyList()) {
                saveSingleHsbgjlb(bgBO.getBgbh(), bgBO.getBglx(), yYcFwHsDO, newFwHsDO);
            }
        }
    }

    @Override
    public void checkParam(FwBgBO<FwYchsDO> bgBO) {
        if (StringUtils.isBlank(bgBO.getBgbh())) {
            throw new MissingArgumentException("bgbh");
        }
        if (CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() == 1) {
            throw new MissingArgumentException("yFwYcHsDOList");
        }
        if (CollectionUtils.isEmpty(bgBO.getNewList()) || bgBO.getNewList().size() != 1) {
            throw new MissingArgumentException("fwYcHsDOList");
        }
    }

    @Override
    public void dealZHs(FwBgBO<FwYchsDO> bgBO) {
        List<HFwZhsDO> bakList = new ArrayList<>();
        for (FwYchsDO yFwYcHs : bgBO.getyList()) {
            List<FwZhsDO> fwZhsDOList = fwZhsService.listFwZhsByFwHsIndex(yFwYcHs.getFwHsIndex());
            if (CollectionUtils.isNotEmpty(fwZhsDOList)) {
                for (FwZhsDO fwZhsDO : fwZhsDOList) {
                    HFwZhsDO bakZhs = new HFwZhsDO();
                    BeanUtils.copyProperties(fwZhsDO, bakZhs);
                    bakList.add(bakZhs);
                }
                // 删除
                fwZhsService.deleteZhsByFwHsIndex(yFwYcHs.getFwHsIndex());
            }
        }
        if (CollectionUtils.isNotEmpty(bakList)) {
            // 备份
            entityMapper.insertBatchSelective(bakList);
        }
    }

    @Override
    public void dealQlr(FwBgBO<FwYchsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getyList())) {
            for (FwYchsDO yYcFwHs : bgBO.getyList()) {
                if (StringUtils.isNotBlank(yYcFwHs.getFwHsIndex())) {
                    List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yYcFwHs.getFwHsIndex());
                    if (CollectionUtils.isNotEmpty(yQlrList)) {
                        fwFcQlrBgService.dealQlr(bgBO.getBgbh(), yYcFwHs.getFwHsIndex(), yQlrList, null);
                    }
                }
            }
        }
    }

    @Override
    public void insert(FwBgBO<FwYchsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getNewList())) {
            List<FwYchsDO> newList = new ArrayList<>();
            //面积处理
            //1.建筑面积求和
            Double sumJzmj = NumberUtil.formatDigit(bgBO.getyList().stream().filter(yYcHsDO -> Objects.nonNull(yYcHsDO.getYcjzmj())).mapToDouble(FwYchsDO::getYcjzmj).sum(), 4);
            //2. 套内建筑面积求和
            Double sumTnjzmj = NumberUtil.formatDigit(bgBO.getyList().stream().filter(yYcHsDO -> Objects.nonNull(yYcHsDO.getYctnjzmj())).mapToDouble(FwYchsDO::getYctnjzmj).sum(), 4);
            //3. 分摊建筑面积求和
            Double sumFtjzmj = NumberUtil.formatDigit(bgBO.getyList().stream().filter(yYcHsDO -> Objects.nonNull(yYcHsDO.getYcftjzmj())).mapToDouble(FwYchsDO::getYcftjzmj).sum(), 4);
            //4. 房间号拼接展示
            String fjh = StringToolUtils.resolveBeanToAppendStr(bgBO.getyList(), "getFjh", CommonConstantUtils.ZF_YW_XG);
            for (FwYchsDO fwYcHsDO : bgBO.getNewList()) {
                fwYcHsDO.setFwHsIndex(UUIDGenerator.generate());
                fwYcHsDO.setBdcdyh(null);
                fwYcHsDO.setYcjzmj(sumJzmj);
                fwYcHsDO.setYcftjzmj(sumFtjzmj);
                fwYcHsDO.setYctnjzmj(sumTnjzmj);
                fwYcHsDO.setFjh(fjh);
                newList.add(fwYcHsDO);
            }
            bgBO.setNewList(fwYcHsService.batchInsertFwYchs(newList));
        }
    }

    @Override
    public void delete(FwBgBO<FwYchsDO> bgBO) {
        if (CollectionUtils.isNotEmpty(bgBO.getyList())) {
            for (FwYchsDO yFwYchs : bgBO.getyList()) {
                fwYcHsService.deleteYchs(yFwYchs, false);
            }
        }
    }
}
