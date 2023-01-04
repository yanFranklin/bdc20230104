package cn.gtmap.realestate.building.service.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.HFwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description
 */
public abstract class FwHsBgAbstractService implements FwBgService<FwHsDO> {

    @Autowired
    protected BdcdyZtService bdcdyZtService;

    @Autowired
    protected EntityMapper entityMapper;

    @Autowired
    protected FwLjzService fwLjzService;

    @Autowired
    protected FwFcQlrBgService fwFcQlrBgService;

    @Autowired
    protected FwXmxxService fwXmxxService;

    @Autowired
    protected FwFcqlrService fwFcqlrService;

    @Autowired
    protected FwHstService fwHstService;

    @Autowired
    protected FwHsService fwHsService;

    @Autowired
    protected FwZhsService fwZhsService;

    /**
     * @param bgbh
     * @param bglx
     * @param yDO
     * @param bgDo
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 基本信息变更操作
     */
    @Override
    @Transactional
    public FwHsDO jbxxBg(String bgbh, String bglx, FwHsDO yDO, FwHsDO bgDo) {
        bglx = StringUtils.isNotBlank(bglx) ? bglx : Constants.BGLX_BG;
        List<FwHsDO> yList = new ArrayList<>(1);
        yList.add(yDO);
        List<FwHsDO> nList = new ArrayList<>(1);
        nList.add(bgDo);
        FwBgBO<FwHsDO> bgBO = new FwBgBO<>(bgbh, bglx, yList, nList);
        List<FwHsDO> resultList = bgFrame(bgBO);
        if (CollectionUtils.isNotEmpty(resultList)) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * @param bgbh
     * @param yDOList
     * @param bgDO
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 合并变更
     */
    @Override
    @Transactional
    public FwHsDO hbBg(String bgbh, List<FwHsDO> yDOList, FwHsDO bgDO) {
        List<FwHsDO> nList = new ArrayList<>(1);
        nList.add(bgDO);
        FwBgBO<FwHsDO> bgBO = new FwBgBO<>(bgbh, Constants.BGLX_HB, yDOList, nList);
        List<FwHsDO> resultList = bgFrame(bgBO);
        if (CollectionUtils.isNotEmpty(resultList)) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * @param bgbh
     * @param yDO
     * @param bgDOList
     * @return List<T>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 拆分变更
     */
    @Override
    @Transactional
    public List<FwHsDO> cfBg(String bgbh, FwHsDO yDO, List<FwHsDO> bgDOList) {
        List<FwHsDO> yList = new ArrayList<>(1);
        yList.add(yDO);
        FwBgBO<FwHsDO> bgBO = new FwBgBO<>(bgbh, Constants.BGLX_CF, yList, bgDOList);
        return bgFrame(bgBO);
    }

    /**
     * @param bgbh
     * @param yDO
     * @param bglx
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 灭失变更
     */
    @Override
    @Transactional
    public void msBg(String bgbh, String bglx, FwHsDO yDO) {
        bglx = StringUtils.isNotBlank(bglx) ? bglx : Constants.BGLX_MS;
        List<FwHsDO> yList = new ArrayList<>(1);
        yList.add(yDO);
        FwBgBO<FwHsDO> bgBO = new FwBgBO<>(bgbh, bglx, yList, null);
        bgFrame(bgBO);
    }

    /**
     * @param bgBO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 基本信息变更
     */
    public List<FwHsDO> bgFrame(FwBgBO<FwHsDO> bgBO) {
        // 1. 验证参数
        checkParam(bgBO);
        // 2. 备份
        bak(bgBO);
        // 3. 新增
        insert(bgBO);
        // 4. 处理记录
        dealBgjl(bgBO);
        // 5. 处理权利人
        dealQlr(bgBO);
        // 6. 处理户室图
//        dealHst(bgBO);
        // 7. 处理子户室
        dealZHs(bgBO);
        // 8. 删除
        delete(bgBO);
        return bgBO.getNewList();
    }

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 备份
     */
    private void bak(FwBgBO<FwHsDO> bgBO) {
        List<HFwHsDO> hFwHsDOList = new ArrayList<>();
        for (FwHsDO fwHsDO : bgBO.getyList()) {
            if (fwHsDO != null) {
                HFwHsDO hFwHsDO = new HFwHsDO();
                BeanUtils.copyProperties(fwHsDO, hFwHsDO);
                hFwHsDO.setBgbh(bgBO.getBgbh());
                hFwHsDOList.add(hFwHsDO);
            }
        }
        entityMapper.insertBatchSelective(hFwHsDOList);
    }

    public abstract void dealBgjl(FwBgBO<FwHsDO> bgBO);

    public abstract void checkParam(FwBgBO<FwHsDO> bgBO);

    public abstract void dealZHs(FwBgBO<FwHsDO> bgBO);

    public abstract void dealQlr(FwBgBO<FwHsDO> bgBO);

    public abstract void insert(FwBgBO<FwHsDO> bgBO);

    public abstract void delete(FwBgBO<FwHsDO> bgBO);

    /**
     * @param bgbh
     * @param bglx
     * @param yDO
     * @param newDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 单条变更记录保存
     */
    @Override
    public void saveSingleHsbgjlb(String bgbh, String bglx, FwHsDO yDO, FwHsDO newDO) {
        // 当变更前后的 BDCDYH 都为空时 不记录变更记录表
        if (StringUtils.isBlank(yDO.getBdcdyh())
                && StringUtils.isBlank(newDO.getBdcdyh())) {
            return;
        }
        SSjHsbgljbDO sjHsbgljbDO = new SSjHsbgljbDO();
        // 主键
        sjHsbgljbDO.setHsbgjlbIndex(UUIDGenerator.generate());
        // 变更编号
        sjHsbgljbDO.setBgbh(bgbh);
        // 变更类型
        sjHsbgljbDO.setBglx(bglx);
        // 变更日期
        sjHsbgljbDO.setBgrq(new Date());
        if (newDO != null) {
            // 新 房屋主键
            sjHsbgljbDO.setFwIndex(newDO.getFwHsIndex());
            // 新 BDCDYH
            sjHsbgljbDO.setBdcdyh(newDO.getBdcdyh());
            // 新 BDCDYFWLX
            if (StringUtils.isNotBlank(newDO.getFwDcbIndex())) {
                sjHsbgljbDO.setBdcdyfwlx(fwLjzService.queryBdcdyFwlxByFwDcbIndex(newDO.getFwDcbIndex()));
            }
        }
        if (yDO != null) {
            // 旧 房屋主键
            sjHsbgljbDO.setYfwIndex(yDO.getFwHsIndex());
            // 旧 BDCDYH
            sjHsbgljbDO.setYbdcdyh(yDO.getBdcdyh());
            // 旧 BDCDYFWLX
            if (StringUtils.isNotBlank(yDO.getFwDcbIndex())) {
                sjHsbgljbDO.setYbdcdyfwlx(fwLjzService.queryBdcdyFwlxByFwDcbIndex(yDO.getFwDcbIndex()));
            }
        }
        entityMapper.insertSelective(sjHsbgljbDO);
    }
}
