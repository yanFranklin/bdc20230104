package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.LqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdlxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDysdQO;
import cn.gtmap.realestate.common.core.qo.init.BdcZssdQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.BdcdyRestService;
import cn.gtmap.realestate.common.core.service.rest.building.LqRestService;
import cn.gtmap.realestate.common.core.service.rest.building.ZdRestService;
import cn.gtmap.realestate.common.core.service.rest.building.ZhRestService;
import cn.gtmap.realestate.common.core.service.rest.register.RegisterWorkflowRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.core.mapper.BdcSdMapper;
import cn.gtmap.realestate.inquiry.service.BdcSdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-07
 * @description 不动产单元、不动产权证号 锁定业务类
 */
@Service
public class BdcSdServiceImpl implements BdcSdService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcdyRestService bdcdyRestService;
    @Autowired
    private ZdRestService zdRestService;
    @Autowired
    private LqRestService lqRestService;
    @Autowired
    private ZhRestService zhRestService;
    @Autowired
    RegisterWorkflowRestService registerWorkflowRestService;

    @Autowired
    BdcSdMapper bdcSdMapper;

    /**
     * @param bdcDysdDOList
     * @return int
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产单元
     */
    @Override
    public int sdBdcdy(List<BdcDysdDO> bdcDysdDOList, Integer sdzt) {
        if (CollectionUtils.isEmpty(bdcDysdDOList)) {
            throw new AppException("锁定不动产单元的参数不能为空");
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
            bdcDysdDO.setDysdid(UUIDGenerator.generate16());
            bdcDysdDO.setSdzt(sdzt);
            if (userDto != null) {
                bdcDysdDO.setSdr(userDto.getAlias());
                bdcDysdDO.setSdrid(userDto.getId());
            }
            bdcDysdDO.setSdsj(new Date());
            
            // 获取不动产单元唯一编码
            if(StringUtils.isBlank(bdcDysdDO.getBdcdywybh())) {
                bdcDysdDO.setBdcdywybh(this.getBdcdywybh(bdcDysdDO.getBdcdyh()));
            }
        }
        return entityMapper.insertBatchSelective(bdcDysdDOList);
    }
    
    /**
     * 获取不动产单元号对应的单元唯一编码
     * 
     * @param bdcdyh 不动产单元号
     * @return String 不动产单元唯一编码
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getBdcdywybh(String bdcdyh) {
        // 获取不动产单元唯一编码 
        String tzm = BdcdyhToolUtils.queryTzmByBdcdyh(bdcdyh);
        String bdcdywybh ="";
        
        if(CommonConstantUtils.BHTZM_FW.equals(tzm)) {
            // 房屋
            BdcdyResponseDTO bdcdyDTO = bdcdyRestService.queryBdcdy(bdcdyh, null,"");
            if(null != bdcdyDTO && StringUtils.isNotBlank(bdcdyDTO.getFwbm())) {
                bdcdywybh= bdcdyDTO.getFwbm();
            }
        } 
        else if(CommonConstantUtils.BHTZM_TD.equals(tzm)) {
            // 土地
            ZdDjdcbDO zd = zdRestService.queryZdByBdcdyh(bdcdyh,"");
            if(null != zd && StringUtils.isNotBlank(zd.getDjh())) {
                bdcdywybh = zd.getDjh();
            }
        } 
        else if(CommonConstantUtils.BHTZM_LQ.equals(tzm)) {
            // 林权
            LqDcbDO lq = lqRestService.queryLqByBdcdyh(bdcdyh, "");
            if(null != lq && StringUtils.isNotBlank(lq.getDjh())) {
                bdcdywybh= lq.getDjh();
            }
        } 
        else if(CommonConstantUtils.BHTZM_HY.equals(tzm)) {
            // 宗海
            ZhDjdcbDO zh = zhRestService.queryZhByBdcdyh(bdcdyh,"");
            if(null != zh && StringUtils.isNotBlank(zh.getZhdm())) {
                bdcdywybh= zh.getZhdm();
            }
        }
        if(StringUtils.isBlank(bdcdywybh)){
            bdcdywybh =BdcdyhToolUtils.dealBdcdywybh(bdcdyh);
        }
        
        return bdcdywybh;
    }

    /**
     * @param bdcZssdDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产权证
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public int sdBdczs(List<BdcZssdDO> bdcZssdDOList, Integer sdzt,String sdyy) {
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            if (StringUtils.isBlank(bdcZssdDO.getZssdid())) {
                bdcZssdDO.setZssdid(UUIDGenerator.generate16());
            }
            UserDto userDto = userManagerUtils.getCurrentUser();
            bdcZssdDO.setSdzt(sdzt);
            if (userDto != null) {
                bdcZssdDO.setSdr(userDto.getAlias());
                bdcZssdDO.setSdrid(userDto.getId());
            }
            bdcZssdDO.setSdsj(new Date());
            bdcZssdDO.setSdyy(sdyy);
        }
        return entityMapper.insertBatchSelective(bdcZssdDOList);
    }

    @Override
    public void addBdcZsSdxx(List<BdcZssdDO> bdcZssdDOList, String sdyy) {
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            throw new AppException("锁定证书的信息不能为空");
        }
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            if (StringUtils.isBlank(bdcZssdDO.getZssdid())) {
                bdcZssdDO.setZssdid(UUIDGenerator.generate16());
            }
            // 注：新增证书锁定信息时，锁定状态为：0 未锁定
            bdcZssdDO.setSdzt(CommonConstantUtils.SDZT_JS);
            bdcZssdDO.setSdsj(new Date());
            if(StringUtils.isNotBlank(sdyy)){
                bdcZssdDO.setSdyy(sdyy);
            }
        }
        entityMapper.insertBatchSelective(bdcZssdDOList);
    }

    @Override
    public void addBdcDySdxx(List<BdcDysdDO> bdcDysdDOList, String sdyy) {
        if (CollectionUtils.isEmpty(bdcDysdDOList)) {
            throw new AppException("锁定不动产单元的参数不能为空");
        }
        for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
            bdcDysdDO.setDysdid(UUIDGenerator.generate16());
            bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_JS);
            bdcDysdDO.setSdsj(new Date());
            // 获取不动产单元唯一编码
            if(StringUtils.isBlank(bdcDysdDO.getBdcdywybh())) {
                bdcDysdDO.setBdcdywybh(this.getBdcdywybh(bdcDysdDO.getBdcdyh()));
            }
            if(StringUtils.isNotBlank(sdyy)){
                bdcDysdDO.setSdyy(sdyy);
            }
        }
        entityMapper.insertBatchSelective(bdcDysdDOList);
    }

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param bdcZssdDOList 证书锁定集合
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public int updateSdZs(List<BdcZssdDO> bdcZssdDOList) {
        int count = 0;
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            count += entityMapper.saveOrUpdate(bdcZssdDO, bdcZssdDO.getZssdid());
        }
        return count;
    }

    /**
     * @param bdcDysdDOList
     * @param jsyy
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产单元
     */
    @Override
    public int jsBdcdy(List<BdcDysdDO> bdcDysdDOList,
                       String jsyy) {
        if (CollectionUtils.isEmpty(bdcDysdDOList)) {
            throw new AppException("解锁不动产单元的参数不能为空");
        }
        int count = 0;
        UserDto userDto = userManagerUtils.getCurrentUser();
        for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
            bdcDysdDO.setSdzt(0);
            if (userDto != null) {
                bdcDysdDO.setJsr(userDto.getAlias());
                bdcDysdDO.setJsrid(userDto.getId());
            }
            bdcDysdDO.setJssj(new Date());
            bdcDysdDO.setJsyy(jsyy);

            count += entityMapper.updateByPrimaryKeySelective(bdcDysdDO);
        }
        return count;
    }

    /**
     * @param bdcZssdDOList
     * @param jsyy
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产权证
     */
    @Override
    public int jsBdczs(List<BdcZssdDO> bdcZssdDOList,
                       String jsyy) {
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            throw new AppException("解锁不动产证书的参数不能为空");
        }
        int count = 0;
        UserDto userDto = userManagerUtils.getCurrentUser();
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            bdcZssdDO.setSdzt(0);
            if (userDto != null) {
                bdcZssdDO.setJsr(userDto.getAlias());
                bdcZssdDO.setJsrid(userDto.getId());
            }
            bdcZssdDO.setJssj(new Date());
            bdcZssdDO.setJsyy(jsyy);

            count += entityMapper.updateByPrimaryKeySelective(bdcZssdDO);
        }
        return count;
    }

    /**
     * @param bdcDysdDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产单元锁定
     */
    @Override
    public List<BdcDysdDO> queryBdcdySd(BdcDysdDO bdcDysdDO) {
        if (bdcDysdDO == null || StringUtils.isBlank(bdcDysdDO.getBdcdyh())) {
            throw new AppException("获取不动产单元锁定的参数不为空！");
        }
        return entityMapper.select(bdcDysdDO);
    }

    /**
     * @param bdcZssdDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产证书锁定
     */
    @Override
    public List<BdcZssdDO> queryBdczsSd(BdcZssdDO bdcZssdDO) {
        if (!CheckParameter.checkAnyParameter(bdcZssdDO)) {
            return Collections.emptyList();
        }
        return entityMapper.select(bdcZssdDO);
    }

    /**
     * @param bdcDysdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产单元锁定备注
     */
    @Override
    public int saveBdcdysdBz(BdcDysdDO bdcDysdDO) {
        if (bdcDysdDO == null || StringUtils.isBlank(bdcDysdDO.getDysdid())) {
            throw new AppException("保存不动产单元锁定备注主键不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcDysdDO);
    }

    /**
     * @param bdcZssdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产证书锁定备注
     */
    @Override
    public int saveBdczssdBz(BdcZssdDO bdcZssdDO) {
        if (bdcZssdDO == null || StringUtils.isBlank(bdcZssdDO.getZssdid())) {
            throw new AppException("保存不动产证书锁定备注主键不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcZssdDO);
    }

    /**
     * @param bdcZssdDOList 证书锁定集合
     * @return {int} 删除证书数目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除证书锁定信息 <br>
     * 删除补录修改流程时需要同步删除证书锁定信息。
     */
    @Override
    public int deleteBdczsSd(List<BdcZssdDO> bdcZssdDOList) {
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            throw new MissingArgumentException("bdcZssdDOList");
        }
        int count = 0;
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            Example example = new Example(BdcZssdDO.class);
            example.createCriteria().andEqualTo("zsid", bdcZssdDO.getZsid());
            count += entityMapper.deleteByExampleNotNull(example);
        }
        return count;
    }

    @Override
    public int deleteBdczsSdByGzlslid(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("gzlslid");
        }
        Example example = new Example(BdcZssdDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        return entityMapper.deleteByExample(example);
    }

    /**
     * 根据工作流实例ID查询证书锁定信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcZssdDO> 证书锁定DO集合
     */
    @Override
    public List<BdcZssdDO> queryBdczsSdByGzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("未获取到工作流实例ID。");
        }
        Example example = new Example(BdcZssdDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcDysdDO> queryBdcDySdByGzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("未获取到工作流实例ID。");
        }
        Example example = new Example(BdcDysdDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        return entityMapper.selectByExample(example);
    }

    /**
     * 通过工作流实例ID获取历史关系，通过原项目ID获取锁定/冻结数据
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @return List<BdcZssdDO> 证书锁定DO集合
     */
    @Override
    public List<BdcZssdDO> queryYxmZssd(String gzlslid, Integer sdzt) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("未获取到工作流实例ID。");
        }
        List<BdcXmDO> yxmList = this.bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), gzlslid, null);

        if(CollectionUtils.isEmpty(yxmList)){
            throw new AppException("未获取到原项目信息。");
        }
        List<String> xmidList = yxmList.stream().filter(t -> StringUtils.isNotBlank(t.getXmid()))
                .map(BdcXmDO::getXmid).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(xmidList)){
            throw new AppException("未获取到原项目信息。");
        }
        BdcZssdQO bdcZssdQO = new BdcZssdQO();
        bdcZssdQO.setXmidList(xmidList);
        if(Objects.nonNull(sdzt)){
            bdcZssdQO.setSdzt(sdzt);
        }
        return this.bdcSdMapper.queryBdcZssd(bdcZssdQO);
    }

    @Override
    public List<BdcDysdDO> queryYxmDysd(String gzlslid, Integer sdzt) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("未获取到工作流实例ID。");
        }
        List<BdcXmDO> yxmList = this.bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), gzlslid, null);

        if(CollectionUtils.isEmpty(yxmList)){
            throw new AppException("未获取到项目信息。");
        }
        List<String> xmidList = yxmList.stream().filter(t -> StringUtils.isNotBlank(t.getXmid()))
                .map(BdcXmDO::getXmid).collect(Collectors.toList());
        Set<String> bdcdyhList = yxmList.stream().filter(t -> StringUtils.isNotBlank(t.getBdcdyh()))
                .map(BdcXmDO::getBdcdyh).collect(Collectors.toSet());
        if(CollectionUtils.isEmpty(bdcdyhList) || CollectionUtils.isEmpty(xmidList)){
            throw new AppException("未获取到原不动产信息。");
        }
        BdcDysdQO bdcDysdQO = new BdcDysdQO();
        bdcDysdQO.setBdcdyhList(new ArrayList<>(bdcdyhList));
        bdcDysdQO.setXmidList(xmidList);
        if(Objects.nonNull(sdzt)){
            bdcDysdQO.setSdzt(sdzt);
        }
        return this.bdcSdMapper.queryBdcDysd(bdcDysdQO);
    }

    /**
     * 锁定/冻结 不动产证书或不动产单元数据
     * <p>大云调用接口，冻结流程办结时触发</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void bdczsSdDj(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("未获取到工作流实例ID。");
        }
        {  // 证书冻结
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setSdsj(new Date());
            bdcZssdDO.setSdzt(CommonConstantUtils.SDZT_SD);
            bdcZssdDO.setSdlx(BdcSdlxEnum.ZSSD.getCode());
            Example example = new Example(BdcZssdDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            entityMapper.updateByExampleSelectiveNotNull(bdcZssdDO, example);
        }
        {  // 单元冻结
            BdcDysdDO bdcDysdDO = new BdcDysdDO();
            bdcDysdDO.setSdsj(new Date());
            bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_SD);
            bdcDysdDO.setSdlx(BdcSdlxEnum.ZSSD.getCode());
            Example example = new Example(BdcDysdDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            entityMapper.updateByExampleSelectiveNotNull(bdcDysdDO, example);
        }
        { // 更新当前当前qszt为现势，原项目qszt为历史
            this.registerWorkflowRestService.updateQsztSyncQjForDj(gzlslid);
        }
    }

    /**
     * 解锁/解冻 不动产证书或不动产单元数据
     * <p>大云调用接口，解冻流程办结时触发</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void bdczsSdJd(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("未获取到工作流实例ID。");
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        // 解冻不动产证书数据
        List<BdcZssdDO> bdcZssdDOList = this.queryYxmZssd(gzlslid, CommonConstantUtils.SDZT_SD);
        if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
            for(BdcZssdDO bdcZssdDO : bdcZssdDOList){
                bdcZssdDO.setJssj(new Date());
                bdcZssdDO.setSdzt(CommonConstantUtils.SDZT_JS);
                if(Objects.nonNull(userDto)){
                    bdcZssdDO.setJsr(userDto.getAlias());
                    bdcZssdDO.setJsrid(userDto.getId());
                }
                entityMapper.updateByPrimaryKeySelective(bdcZssdDO);
            }
        }
        // 解冻不动产单元数据
        List<BdcDysdDO> bdcDysdDOList = this.queryYxmDysd(gzlslid, CommonConstantUtils.SDZT_SD);
        if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
            for(BdcDysdDO bdcDysdDO : bdcDysdDOList){
                bdcDysdDO.setJssj(new Date());
                bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_JS);
                if(Objects.nonNull(userDto)){
                    bdcDysdDO.setJsr(userDto.getAlias());
                    bdcDysdDO.setJsrid(userDto.getId());
                }
                entityMapper.updateByPrimaryKeySelective(bdcDysdDO);
            }
        }

        { // 更新当前当前qszt为现势，原项目qszt为历史
            this.registerWorkflowRestService.updateQsztSyncQjForDj(gzlslid);
        }
    }

    /**
     * 通过工作流实例ID修改证书锁定数据
     * @param bdcZssdDO 证书锁定DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void updateZssdByGzlslid(BdcZssdDO bdcZssdDO) {
        Example example= new Example(BdcZssdDO.class,false);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", bdcZssdDO.getGzlslid());
        entityMapper.updateByExampleSelectiveNotNull(bdcZssdDO, example);
    }

    @Override
    public void updateDysdByGzlslid(BdcDysdDO bdcDysdDO) {
        Example example= new Example(BdcDysdDO.class,false);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", bdcDysdDO.getGzlslid());
        entityMapper.updateByExampleSelectiveNotNull(bdcDysdDO, example);
    }

    @Override
    public void jsBdcdyhByBdcdyh(BdcDysdDO bdcDysdDO) {
        if(StringUtils.isEmpty(bdcDysdDO.getBdcdyh())){
            throw new AppException("bdcdyh不能为空!");
        }
        if(CollectionUtils.isEmpty(bdcSdMapper.queryBdcdySdByBdcdyh(bdcDysdDO.getBdcdyh()))){
            throw new AppException("根据当前bdcdyh查询不到锁定信息!");
        }
        bdcSdMapper.updateBdcdySdByBdcdyh(bdcDysdDO);
    }

    @Override
    public void jsBdczsByCqzh(BdcZssdDO bdcZssdDO) {
        if(StringUtils.isEmpty(bdcZssdDO.getCqzh())){
            throw new AppException("cqzh不能为空!");
        }
        if(CollectionUtils.isEmpty(bdcSdMapper.queryBdcZsSdByCqzh(bdcZssdDO.getCqzh()))){
            throw new AppException("根据当前cqzh查询不到锁定信息!");
        }
        bdcSdMapper.updateBdczsSdByCqzh(bdcZssdDO);
    }

    /**
     * 根据主键ID查询锁定信息
     * @param sdxxid 主键ID
     * @return Object 单元或者证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public Object queryBdcSdxxById(String sdxxid) {
        if(StringUtils.isBlank(sdxxid)) {
            throw new AppException("查询参数sdxxid为空!");
        }

        // 先查单元锁定
        BdcDysdDO bdcDysdDO = entityMapper.selectByPrimaryKey(BdcDysdDO.class, sdxxid);
        if(null != bdcDysdDO) {
            return bdcDysdDO;
        }

        // 再查证书锁定
        BdcZssdDO zssdDO = entityMapper.selectByPrimaryKey(BdcZssdDO.class, sdxxid);
        return zssdDO;
    }

    /**
     * 根据cqzh批量查询证书锁定信息
     * @param cqzhList
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    public List<BdcZssdDO> queryBdcZssdByCqzhs(List<String> cqzhList) {
        return bdcSdMapper.queryBdcZsSdByCqzhs(cqzhList);
    }

    @Override
    public void jsBdczsByCqzh(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("未获取到工作流实例ID。");
        }
        List<BdcZsDO> bdcZsDOList = this.bdcZsFeignService.queryYxmBdcqzhByGzlslid(processInsId, null);
        if(CollectionUtils.isNotEmpty(bdcZsDOList)){
            List<BdcZssdDO> bdcZssdDOList = new ArrayList<>(10);
            for(BdcZsDO bdcZsDO : bdcZsDOList){
                BdcZssdDO queryParam = new BdcZssdDO();
                queryParam.setCqzh(bdcZsDO.getBdcqzh());
                List<BdcZssdDO> bdcZssdDOS = this.queryBdczsSd(queryParam);
                if(CollectionUtils.isNotEmpty(bdcZssdDOS)){
                    bdcZssdDOList.addAll(bdcZssdDOS);
                }
            }
            if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
                String jsyy = "业务审批通过，不动产证书自动解锁。";
                this.jsBdczs(bdcZssdDOList, jsyy);
            }
        }
    }

    @Override
    public int batchDeleteBdcZssd(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new MissingArgumentException("证书锁定ID");
        }
       return this.bdcSdMapper.batchDeleteBdcZssd(list);
    }

    /**
     * 根据XMID查询证书锁定信息
     *
     * @param xmid@author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcZssdDO> 证书锁定DO集合
     */
    @Override
    public List<BdcZssdDO> queryBdczsSdByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        return bdcSdMapper.queryYxmBdcqzhByXmid(xmid);
    }
}
