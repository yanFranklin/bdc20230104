package cn.gtmap.realestate.building.service.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.HFwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description
 */
public abstract class FwLjzBgAbstractService implements FwBgService<FwLjzDO>{


    @Autowired
    private BdcdyZtService bdcdyZtService;

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

    @Resource(name = "fwHsJbxxBgServiceImpl")
    protected FwBgService fwHsJbxxBgService;

    @Resource(name = "fwHsMsServiceImpl")
    protected FwBgService fwHsMsService;

    @Autowired
    protected FwHsService fwHsService;

    @Autowired
    protected FwHstBgService fwHstBgService;

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
    public FwLjzDO jbxxBg(String bgbh, String bglx, FwLjzDO yDO, FwLjzDO bgDo) {
        List<FwLjzDO> yList = new ArrayList<>(1);
        yList.add(yDO);
        List<FwLjzDO> list = new ArrayList<>(1);
        list.add(bgDo);
        bglx = StringUtils.isNotBlank(bglx)? bglx : Constants.BGLX_BG;
        FwBgBO<FwLjzDO> bgBO = new FwBgBO<>(bgbh,bglx,yList,list);
        List<FwLjzDO> newList = bgFrame(bgBO);
        if(CollectionUtils.isNotEmpty(newList)){
            return newList.get(0);
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
    public FwLjzDO hbBg(String bgbh, List<FwLjzDO> yDOList, FwLjzDO bgDO) {
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
    public List<FwLjzDO> cfBg(String bgbh, FwLjzDO yDO, List<FwLjzDO> bgDOList) {
        return null;
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
    public void msBg(String bgbh,String bglx,  FwLjzDO yDO) {
        bglx = StringUtils.isNotBlank(bglx)?bglx:Constants.BGLX_MS;
        List<FwLjzDO> yList = new ArrayList<>(1);
        yList.add(yDO);
        FwBgBO<FwLjzDO> bgBO = new FwBgBO<>(bgbh,bglx,yList,null);
        bgFrame(bgBO);
    }
    /**
     * @param bgBO
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 基本信息变更
     */
    public List<FwLjzDO> bgFrame(FwBgBO<FwLjzDO> bgBO) {
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
        dealHst(bgBO);
        // 7. 处理幢下户室
        dealFwHs(bgBO);
        // 8. 删除
        delete(bgBO);
        return bgBO.getNewList();
    }

    public void delete(FwBgBO<FwLjzDO> bgBO){
        for(FwLjzDO fwLjzDO : bgBO.getyList()){
            fwLjzService.deleteLjz(fwLjzDO,false);
        }
    }

    public void insert(FwBgBO<FwLjzDO> bgBO){
        if(CollectionUtils.isNotEmpty(bgBO.getNewList())){
            List<FwLjzDO> newLjzList = new ArrayList<>(bgBO.getNewList().size());
            for(FwLjzDO fwLjzDO : bgBO.getNewList()){
                FwLjzDO newLjz = new FwLjzDO();
                BeanUtils.copyProperties(fwLjzDO,newLjz);
                newLjz.setFwDcbIndex(UUIDGenerator.generate());
                newLjz.setBdcdyh(null);
                newLjzList.add(fwLjzService.insertLjz(newLjz));
            }
            bgBO.setNewList(newLjzList);
        }
    }

    public void bak(FwBgBO<FwLjzDO> bgBO){
        List<HFwLjzDO> hList = new ArrayList<>(bgBO.getyList().size());
        for(FwLjzDO fwLjzDO : bgBO.getyList()){
            HFwLjzDO hFwLjzDO = new HFwLjzDO();
            BeanUtils.copyProperties(fwLjzDO, hFwLjzDO);
            hFwLjzDO.setBgbh(bgBO.getBgbh());
            hList.add(hFwLjzDO);
        }
        entityMapper.insertBatchSelective(hList);
    }

    public abstract void dealBgjl(FwBgBO<FwLjzDO> bgBO);

    public abstract void checkParam(FwBgBO<FwLjzDO> bgBO);

    public abstract void dealFwHs(FwBgBO<FwLjzDO> bgBO);

    public abstract void dealQlr(FwBgBO<FwLjzDO> bgBO);

    public abstract void dealHst(FwBgBO<FwLjzDO> bgBO);

    @Override
    public void saveSingleHsbgjlb(String bgbh, String bglx, FwLjzDO yFwLjz, FwLjzDO newFwLjz){

        // 当变更前后的 BDCDYH 都为空时 不记录变更记录表
        if(StringUtils.isBlank(yFwLjz.getBdcdyh())
                && StringUtils.isBlank(newFwLjz.getBdcdyh())){
            return ;
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
        // 新 房屋主键
        if(newFwLjz != null){
            sjHsbgljbDO.setFwIndex(newFwLjz.getFwDcbIndex());
            // 新 BDCDYH
            sjHsbgljbDO.setBdcdyh(newFwLjz.getBdcdyh());
            // 新 BDCDYFWLX
            sjHsbgljbDO.setBdcdyfwlx(newFwLjz.getBdcdyfwlx());
        }
        if(yFwLjz != null){
            // 旧 房屋主键
            sjHsbgljbDO.setYfwIndex(yFwLjz.getFwDcbIndex());
            // 旧 BDCDYH
            sjHsbgljbDO.setYbdcdyh(yFwLjz.getBdcdyh());
            // 旧 BDCDYFWLX
            sjHsbgljbDO.setYbdcdyfwlx(yFwLjz.getBdcdyfwlx());
        }
        entityMapper.insertSelective(sjHsbgljbDO);
    }
}
