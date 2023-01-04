package cn.gtmap.realestate.building.service.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.domain.building.HFwXmxxDO;
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
 * @description 房屋项目信息变更
 */
public abstract class FwXmxxBgAbstractService implements FwBgService<FwXmxxDO> {

    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Resource(name = "fwLjzJbxxBgServiceImpl")
    protected FwBgService fwLjzJbxxBgService;

    @Resource(name = "fwLjzMsServiceImpl")
    protected FwBgService fwLjzMsService;

    @Autowired
    protected FwFcQlrBgService fwFcQlrBgService;

    @Autowired
    protected EntityMapper entityMapper;

    @Autowired
    protected FwXmxxService fwXmxxService;

    @Autowired
    protected FwLjzService fwLjzService;

    @Autowired
    protected FwFcqlrService fwFcqlrService;

    @Autowired
    protected FwHstService fwHstService;

    @Autowired
    protected FwHstBgService fwHstBgService;

    /**
     * @param bgbh
     * @param yDO
     * @param bgDo
     * @return FwXmxxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 基本信息变更操作
     */
    @Override
    @Transactional
    public FwXmxxDO jbxxBg(String bgbh, String bglx,FwXmxxDO yDO, FwXmxxDO bgDo) {
        List<FwXmxxDO> fwXmxxDOList = new ArrayList<>();
        fwXmxxDOList.add(yDO);
        List<FwXmxxDO> newList = new ArrayList<>();
        newList.add(bgDo);
        FwBgBO<FwXmxxDO> bgBO = new FwBgBO<>(bgbh,bglx,fwXmxxDOList,newList);
        return bgFrame(bgBO);
    }

    /**
     * @param bgbh
     * @param yDOList
     * @param bgDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 合并变更
     */
    @Override
    @Transactional
    public FwXmxxDO hbBg(String bgbh, List<FwXmxxDO> yDOList, FwXmxxDO bgDO) {
        List<FwXmxxDO> newList = new ArrayList<>();
        newList.add(bgDO);
        FwBgBO<FwXmxxDO> bgBO = new FwBgBO<>(bgbh,Constants.BGLX_HB,yDOList,newList,bgDO);
        return bgFrame(bgBO);
    }

    /**
     * @param bgbh
     * @param yDO
     * @param bgDOList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 拆分变更
     */
    @Override
    public List<FwXmxxDO> cfBg(String bgbh, FwXmxxDO yDO, List<FwXmxxDO> bgDOList) {
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
    public void msBg(String bgbh, String bglx, FwXmxxDO yDO) {
        List<FwXmxxDO> fwXmxxDOList = new ArrayList<>();
        fwXmxxDOList.add(yDO);
        if(StringUtils.isBlank(bglx)){
            bglx = Constants.BGLX_MS;
        }
        FwBgBO<FwXmxxDO> bgBO = new FwBgBO<>(bgbh,bglx,fwXmxxDOList,null);
        bgFrame(bgBO);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @description 变更框架
     */
    private FwXmxxDO bgFrame(FwBgBO<FwXmxxDO> bgBO){
        // 验证参数 （抽象方法）
        checkParam(bgBO);
        // 备份
        bak(bgBO);
        // 新增
        insertNew(bgBO);
        // 处理记录（抽象方法）
        saveBgjl(bgBO);
        // 处理权利人
        dealFwFcqlr(bgBO);
        // 处理户室图
        dealHst(bgBO);
        // 处理项目下的逻辑幢
        dealLjz(bgBO);
        // 删除
        deleteFwXmxx(bgBO);

        if(CollectionUtils.isNotEmpty(bgBO.getNewList())){
            return bgBO.getNewList().get(0);
        }else {
            return null;
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description 验证参数
     */
    public abstract void checkParam(FwBgBO<FwXmxxDO> bgBO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description 备份
     */
    private void bak(FwBgBO<FwXmxxDO> bgBO){
        for(FwXmxxDO fwXmxxDO : bgBO.getyList()){
            HFwXmxxDO hFwXmxxDO = new HFwXmxxDO();
            BeanUtils.copyProperties(fwXmxxDO,hFwXmxxDO);
            hFwXmxxDO.setBgbh(bgBO.getBgbh());
            entityMapper.insertSelective(hFwXmxxDO);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @description 插入新的
     */
    private void insertNew(FwBgBO<FwXmxxDO> bgBO){
        if(CollectionUtils.isNotEmpty(bgBO.getNewList())){
            FwXmxxDO newFwXmxx = new FwXmxxDO();
            BeanUtils.copyProperties(bgBO.getNewList().get(0),newFwXmxx);
            newFwXmxx.setFwXmxxIndex(UUIDGenerator.generate());
            newFwXmxx.setBdcdyh(null);
            List<FwXmxxDO> newList = new ArrayList<>();
            newList.add(fwXmxxService.insertFwXmxx(newFwXmxx));
            bgBO.setNewList(newList);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description 保存变更记录
     */
    public abstract void saveBgjl(FwBgBO<FwXmxxDO> bgBO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description 删除项目
     */
    private void deleteFwXmxx(FwBgBO<FwXmxxDO> bgBO){
        for(FwXmxxDO fwXmxxDO:bgBO.getyList()){
            fwXmxxService.deleteFwXmxx(fwXmxxDO,true);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description  处理FWFCQLR
     */
    public abstract void dealFwFcqlr(FwBgBO<FwXmxxDO> bgBO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description
     */
    public abstract void dealHst(FwBgBO<FwXmxxDO> bgBO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgBO
     * @return void
     * @description 处理逻辑幢
     */
    public abstract void dealLjz(FwBgBO<FwXmxxDO> bgBO);


    @Override
    public void saveSingleHsbgjlb(String bgbh,String bglx,FwXmxxDO yFwXmxx,FwXmxxDO newFwxmxx){
        // 当变更前后的 BDCDYH 都为空时 不记录变更记录表
        if(StringUtils.isBlank(yFwXmxx.getBdcdyh())
                && StringUtils.isBlank(newFwxmxx.getBdcdyh())){
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
        if(newFwxmxx != null){
            // 新房屋主键
            sjHsbgljbDO.setFwIndex(newFwxmxx.getFwXmxxIndex());
            // 新BDCDYH
            sjHsbgljbDO.setBdcdyh(newFwxmxx.getBdcdyh());
            // 新 BDCDYFWLX
            sjHsbgljbDO.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
        }
        if(yFwXmxx != null){
            // 旧房屋主键
            sjHsbgljbDO.setYfwIndex(yFwXmxx.getFwXmxxIndex());
            // 旧BDCDYH
            sjHsbgljbDO.setYbdcdyh(yFwXmxx.getBdcdyh());
            // 旧 BDCDYFWLX
            sjHsbgljbDO.setYbdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
        }
        entityMapper.insertSelective(sjHsbgljbDO);
    }
}
