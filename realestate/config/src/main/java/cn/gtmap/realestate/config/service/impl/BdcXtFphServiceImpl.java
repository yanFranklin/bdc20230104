package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.BdcFphSymxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcFphSymxDTO;
import cn.gtmap.realestate.common.core.enums.BdcZssyqkEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityExistsException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcFphQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcFphVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.config.config.PropsConfig;
import cn.gtmap.realestate.config.core.mapper.BdcXtFphMapper;
import cn.gtmap.realestate.config.service.BdcXtFphService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description 发票号 业务类
 */
@Slf4j
@Service
public class BdcXtFphServiceImpl implements BdcXtFphService {

    private static final Logger logger = LoggerFactory.getLogger(BdcXtFphServiceImpl.class);
    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;
    /**
     * MyBatis ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;
    /**
     * 发票号 ORM操作
     */
    @Autowired
    private BdcXtFphMapper bdcXtFphMapper;
    /**
     * 用户操作
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    private PropsConfig propsConfig;
    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description  月结银行是否获取发票号
     */
    @Value("${hqfph.pcayjs:true}")
    private boolean pcayjs;

    /**
     * @param pageable     分页对象
     * @param fphParamJson 查询条件
     * @return {Page} 发票号配置分页数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询发票号配置数据列表
     */
    @Override
    public Page<BdcFphVO> listBdcFph(Pageable pageable, String fphParamJson) {
        return repo.selectPaging("listBdcXtFphByPage", JSON.parseObject(fphParamJson, BdcFphQO.class), pageable);
    }

    /**
     * @param bdcFphQO 发票号模板
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 生成发票号
     */
    @Override
    public int generateBdcFph(BdcFphQO bdcFphQO) {
        // 判断模板必填属性值，避免前端验证无效或者直接请求接口生成不走前端
        if (StringToolUtils.existItemNullOrEmpty(bdcFphQO.getNf(), bdcFphQO.getQxdm())
                || StringToolUtils.existIntegerItemNullOrEmpty(bdcFphQO.getQsbh(), bdcFphQO.getJsbh())) {
            throw new NullPointerException("证书印制号模板存在必填项为空问题！");
        }
        // 判断编号
        if (bdcFphQO.getQsbh() > bdcFphQO.getJsbh()) {
            throw new AppException("错误：证书印制号模板起始编号大于结束编号！");
        }
        // 判断证书印制号是否已经存在
        int num = bdcXtFphMapper.countYzh(bdcFphQO);
        if (num > 0) {
            throw new EntityExistsException("指定区间范围的部分证书印制号已经存在！");
        }

        // 领取部门名称有值 但是lqbmid没有值
        if (StringUtils.isNotBlank(bdcFphQO.getLqbm()) && StringUtils.isBlank(bdcFphQO.getLqbmid())) {
            throw new AppException("错误：缺失领取部门id！");
        }
        // 领取部门名称没有值 但是lqbmid有值
        if (StringUtils.isBlank(bdcFphQO.getLqbm()) && StringUtils.isNotBlank(bdcFphQO.getLqbmid())) {
            throw new AppException("错误：缺失领取部门名称！");
        }

        // 领取人有值 但是lqrid没有值
        if (StringUtils.isNotBlank(bdcFphQO.getLqr()) && StringUtils.isBlank(bdcFphQO.getLqrid())) {
            throw new AppException("错误：缺失领取人id！");
        }
        // 领取人没有值 但是lqrid有值
        if (StringUtils.isBlank(bdcFphQO.getLqr()) && StringUtils.isNotBlank(bdcFphQO.getLqrid())) {
            throw new AppException("错误：缺失领取人！");
        }

        // 获取用户信息（只调用一次服务）
        UserDto userDTO = userManagerUtils.getCurrentUser();
        String userName = null;
        String userId = null;
        if (null != userDTO) {
            userName = userDTO.getAlias();
            userId = userDTO.getId();
        }

        // 生成印制号
        int size = bdcFphQO.getJsbh() - bdcFphQO.getQsbh() + 1;
        List<BdcFphDO> bdcFphDOList = new ArrayList<>(size);
        for (int qsbh = bdcFphQO.getQsbh(); qsbh <= bdcFphQO.getJsbh(); qsbh++) {
            BdcFphDO bdcFphDO = new BdcFphDO();
            bdcFphDO.setFphid(UUIDGenerator.generate());
            bdcFphDO.setNf(bdcFphQO.getNf());
            bdcFphDO.setQxdm(bdcFphQO.getQxdm());
            bdcFphDO.setFph(this.getFphxlh(bdcFphQO, qsbh));
            // 初始状态为已领用
            bdcFphDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
            bdcFphDO.setCjr(userName);
            bdcFphDO.setCjrid(userId);
            bdcFphDO.setCjsj(new Date());
            bdcFphDO.setLqbm(bdcFphQO.getLqbm());
            bdcFphDO.setLqbmid(bdcFphQO.getLqbmid());
            bdcFphDO.setLqr(bdcFphQO.getLqr());
            bdcFphDO.setLqrid(bdcFphQO.getLqrid());
            bdcFphDO.setZzjfbs(bdcFphQO.getZzjfbs());

            bdcFphDOList.add(bdcFphDO);
            /// 为了防止要生成的印制号数量太大，采用分批量保存，避免全部一次保存严重耗时
            if (bdcFphDOList.size() >= 1000) {
                entityMapper.insertBatchSelective(bdcFphDOList);
                bdcFphDOList.clear();
            }
        }
        if (CollectionUtils.isNotEmpty(bdcFphDOList)) {
            entityMapper.insertBatchSelective(bdcFphDOList);
        }
        return size;
    }

    /**
     * @param bdcFphDO 发票号配置实体
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存发票号配置配置
     */
    @Override
    public int saveBdcFph(BdcFphDO bdcFphDO) {
        // 判断模板必填属性值，避免前端验证无效或者直接请求接口生成不走前端
        if (StringToolUtils.existItemNullOrEmpty(bdcFphDO.getNf(), bdcFphDO.getQxdm(), bdcFphDO.getFph())) {
            throw new NullPointerException("发票号配置模板存在必填项为空问题！");
        }

        // 判断是否已经存在指定印制号：如果当前记录印制号更改，但是其他记录已经对应其印制号，则冲突
        Example example = new Example(BdcFphDO.class);
        example.createCriteria().andEqualTo("fph", bdcFphDO.getFph());
        List<BdcFphDO> bdcFphDOList = entityMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(bdcFphDOList) && null != bdcFphDOList.get(0)
                && !StringUtils.equals(bdcFphDO.getFphid(), bdcFphDOList.get(0).getFphid())) {
            throw new EntityExistsException("发票号已经存在！");
        }

        return entityMapper.updateByPrimaryKeySelective(bdcFphDO);
    }

    @Override
    public void updateBdcFphxx(BdcFphDO bdcFphDO) {
        if(StringUtils.isBlank(bdcFphDO.getFphid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到发票号ID");
        }
        this.entityMapper.updateByPrimaryKeySelective(bdcFphDO);
    }

    /**
     * @param bdcFphDOList 发票号配置集合
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除发票号配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBdcFph(List<BdcFphDO> bdcFphDOList) {
        if (CollectionUtils.isEmpty(bdcFphDOList)) {
            return 0;
        }
        int count = 0;
        int mxCount = 0;
        for (BdcFphDO bdcFphDO : bdcFphDOList) {
            count += entityMapper.deleteByPrimaryKey(BdcFphDO.class, bdcFphDO.getFphid());
            //删除 发票号使用明细
            BdcFphSymxDO bdcFphSymxDO = new BdcFphSymxDO();
            bdcFphSymxDO.setFphid(bdcFphDO.getFphid());
            entityMapper.delete(bdcFphSymxDO);
        }
        return count;
    }

    /**
     * @param bdcFphSymxDO 发票号使用明细
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 作废发票号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBdcFph(BdcFphSymxDO bdcFphSymxDO) {
        if (StringUtils.isBlank(bdcFphSymxDO.getFphid())) {
            throw new MissingArgumentException("fphid");
        }
        // 保存证书作废明细信息
        bdcFphSymxDO.setFphsymxid(UUIDGenerator.generate());
        bdcFphSymxDO.setSyr(userManagerUtils.getCurrentUser().getAlias());
        bdcFphSymxDO.setSyrid(userManagerUtils.getCurrentUser().getId());
        bdcFphSymxDO.setSysj(new Date());
        bdcFphSymxDO.setSyqk(BdcZssyqkEnum.ZF.getCode());
        entityMapper.insert(bdcFphSymxDO);

        // 修改印制号使用情况
        BdcFphDO bdcFphDO = new BdcFphDO();
        bdcFphDO.setFphid(bdcFphSymxDO.getFphid());
        bdcFphDO.setSyqk(BdcZssyqkEnum.ZF.getCode());

        entityMapper.updateByPrimaryKeySelective(bdcFphDO);
    }

    /**
     * 作废发票号
     * @param fph 发票号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void zfBdcFph(String fph) {
        if(StringUtils.isBlank(fph)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到发票号");
        }
        Example example = new Example(BdcFphDO.class);
        example.createCriteria().andEqualTo("fph", fph);
        List<BdcFphDO> bdcFphDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(bdcFphDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "发票号不存在");
        }
        String fphid = bdcFphDOList.get(0).getFphid();
        {
            // 添加发票号作废使用明细
            BdcFphSymxDO bdcFphSymxDO = new BdcFphSymxDO();
            bdcFphSymxDO.setFphsymxid(UUIDGenerator.generate());
            bdcFphSymxDO.setFphid(fphid);
            bdcFphSymxDO.setSyr(userManagerUtils.getCurrentUser().getAlias());
            bdcFphSymxDO.setSyrid(userManagerUtils.getCurrentUser().getId());
            bdcFphSymxDO.setSysj(new Date());
            bdcFphSymxDO.setSyqk(BdcZssyqkEnum.ZF.getCode());
            bdcFphSymxDO.setSyyy("作废发票号");
            entityMapper.insertSelective(bdcFphSymxDO);
        }
        {
            // 作废发票号
            BdcFphDO bdcFphDO = new BdcFphDO();
            bdcFphDO.setFphid(fphid);
            bdcFphDO.setSyqk(BdcZssyqkEnum.ZF.getCode());
            entityMapper.updateByPrimaryKeySelective(bdcFphDO);
        }
    }

    @Override
    public void qxBdcFph(String fph) {
        if(StringUtils.isBlank(fph)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到发票号");
        }
        Example example = new Example(BdcFphDO.class);
        example.createCriteria().andEqualTo("fph", fph);
        List<BdcFphDO> bdcFphDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(bdcFphDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "发票号不存在");
        }
        String fphid = bdcFphDOList.get(0).getFphid();
        {
            // 添加发票号取消使用明细
            BdcFphSymxDO bdcFphSymxDO = new BdcFphSymxDO();
            bdcFphSymxDO.setFphsymxid(UUIDGenerator.generate());
            bdcFphSymxDO.setFphid(fphid);
            bdcFphSymxDO.setSyr(userManagerUtils.getCurrentUser().getAlias());
            bdcFphSymxDO.setSyrid(userManagerUtils.getCurrentUser().getId());
            bdcFphSymxDO.setSysj(new Date());
            bdcFphSymxDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
            bdcFphSymxDO.setSyyy("取消发票号");
            entityMapper.insertSelective(bdcFphSymxDO);
        }
        {
            // 取消发票号
            BdcFphDO bdcFphDO = new BdcFphDO();
            bdcFphDO.setFphid(fphid);
            bdcFphDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
            entityMapper.updateByPrimaryKeySelective(bdcFphDO);
        }
    }

    /**
     * @param bdcFphQO
     * @param qsbh
     * @return {String}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取发票号
     */
    private String getFphxlh(BdcFphQO bdcFphQO, int qsbh) {
        String fpqsbh = String.valueOf(qsbh);
        // 拼接 0
        if (fpqsbh.length() < bdcFphQO.getBhws()) {
            do {
                fpqsbh = StringUtils.join("0", fpqsbh);
            } while (fpqsbh.length() < bdcFphQO.getBhws());
        }
        return fpqsbh;
    }

    /**
     * @param bdcSlSfxxDOS
     * @param slbh
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取发票号, 再次 获取发票号 使用aop 更新收费信息已有的fph 的使用状态
     */
    @Override
    public List<BdcSlSfxxDO> getBdcFph(List<BdcSlSfxxDO> bdcSlSfxxDOS, String slbh, List<BdcFphDO> bdcFphDOList) {
        return this.getBdcFph(bdcSlSfxxDOS, slbh, StringUtils.EMPTY, bdcFphDOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_FPH, description = "获取发票号")
    public List<BdcSlSfxxDO> getBdcFph(List<BdcSlSfxxDO> bdcSlSfxxDOS, String slbh, String fplb, List<BdcFphDO> bdcFphDOList) {
        if (CollectionUtils.isEmpty(bdcSlSfxxDOS) || StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("sfxxid,slbh");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        final String userName = userDto.getAlias();
        final String userid = userDto.getId();
        /**
         * 获取 按月结算的结构，根据收费信息的缴费人 过滤
         */
        Pair<List<BdcSlSfxxDO>, List<BdcSlSfxxDO>> groupSfxxPair = this.groupFayjsAndAyjs(bdcSlSfxxDOS);
        List<BdcSlSfxxDO> bdcSlSfxxDOListFayjs = groupSfxxPair.getLeft();
        List<BdcSlSfxxDO> bdcSlSfxxDOListAyjs = groupSfxxPair.getRight();

        // 判断月结银行是否获取发票号
        if(!pcayjs){
            bdcSlSfxxDOListFayjs.addAll(bdcSlSfxxDOListAyjs);
            bdcSlSfxxDOListAyjs = new ArrayList<>(1);
        }

        if ((CollectionUtils.size(bdcFphDOList) < CollectionUtils.size(bdcSlSfxxDOListFayjs))) {
            throw new AppException("剩余发票号不足！");
        }

        List<BdcFphSymxDO> bdcFphSymxDOS = Lists.newArrayList();
        Queue<BdcFphDO> fphDOArrayDeque = Queues.newArrayDeque(bdcFphDOList);

        bdcSlSfxxDOListFayjs.forEach(bdcSlSfxxDO -> {
            BdcFphDO bdcFphDO = fphDOArrayDeque.poll();
            bdcSlSfxxDO.setFph(bdcFphDO.getFph(), fplb);
            bdcSlSfxxDO.setKprxm(userName);
            bdcSlSfxxDO.setHqfphsj(new Date());
            bdcFphDO.setSfxxid(bdcSlSfxxDO.getSfxxid());
            bdcFphDO.setSyqk(BdcZssyqkEnum.YSY.getCode());
            bdcFphDO.setFph(bdcFphDO.getFph());
            bdcFphDO.setSlbh(slbh);
            BdcFphSymxDO bdcFphSymxDO = new BdcFphSymxDO();
            bdcFphSymxDO.setFphsymxid(UUIDGenerator.generate16());
            //生成 使用明细
            bdcFphDO.setFphsymxid(bdcFphSymxDO.getFphsymxid());
            entityMapper.updateByPrimaryKeySelective(bdcFphDO);
            bdcFphSymxDO.setFphid(bdcFphDO.getFphid());
            bdcFphSymxDO.setSyr(userName);
            bdcFphSymxDO.setSyrid(userid);
            bdcFphSymxDO.setSyqk(BdcZssyqkEnum.YSY.getCode());
            bdcFphSymxDO.setSysj(new Date());
            bdcFphSymxDOS.add(bdcFphSymxDO);
        });
        bdcSlSfxxDOListAyjs.forEach(bdcSlSfxxDO -> {
            bdcSlSfxxDO.setFph("", fplb);
        });
        if (CollectionUtils.isNotEmpty(bdcFphSymxDOS)) {
            entityMapper.insertBatchSelective(bdcFphSymxDOS);
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        bdcSlSfxxDOList.addAll(bdcSlSfxxDOListAyjs);
        bdcSlSfxxDOList.addAll(bdcSlSfxxDOListFayjs);
        /**
         * 更新收费信息发票号
         */
        bdcSlSfxxFeignService.updateBdcSlSfxxList(bdcSlSfxxDOList);
        return bdcSlSfxxDOList;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取发票号信息
     */
    @Override
    public List<BdcFphDO> queryBdcFphDOList(List<BdcSlSfxxDO> bdcSlSfxxDOS) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        logger.info("获取发票号当前用户信息为:{}", userDto.toString());
        String qxdm = userManagerUtils.getRegionCodeByUserName(userDto.getUsername());
        logger.info("获取发票号当前用户qxdm为:{}", qxdm);
        int fphRquiredNum = bdcSlSfxxDOS.size();
        if(pcayjs){
            List<BdcSlSfxxDO> bdcSlSfxxDOListFayjs = this.groupFayjsAndAyjs(bdcSlSfxxDOS).getLeft();
            fphRquiredNum = bdcSlSfxxDOListFayjs.size();
            if(fphRquiredNum == 0){
                throw new AppException(ErrorCode.CUSTOM, "该银行为月结银行，无需开票");
            }
        }
        BdcFphQO bdcFphQO = new BdcFphQO();
        bdcFphQO.setSyqk(BdcZssyqkEnum.YLY.getCode());
        bdcFphQO.setNum(fphRquiredNum);
        String lqfs = propsConfig.getFphlqfs(qxdm);
        logger.info("发票号领取方式:{}", lqfs);
        if (StringUtils.isBlank(lqfs)){
            // 领取方式为没有配置，代表不需要开票
            throw new AppException(ErrorCode.CONFIG_EX, "未获取到领取方式配置，该机构所在行政区域不需要开票");
        }
        if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_BM, lqfs)) {
            // 按领取部门领取
            if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                throw new MissingArgumentException("系统所配印制号获取方式为：部门领取，但是当前用户没有所在部门的信息！");
            }
            bdcFphQO.setLqbmid(userDto.getOrgRecordList().get(0).getId());
        }
        if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_RY, lqfs)) {
            bdcFphQO.setLqrid(userDto.getId());
        }
        if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_QXDM, lqfs)) {
            bdcFphQO.setQxdm(qxdm);
        }
        logger.info("------查询发票号参数:{}", bdcFphQO);
        List<BdcFphDO> bdcFphDOList = bdcXtFphMapper.listBdcXtFph(bdcFphQO);
        if ((CollectionUtils.size(bdcFphDOList) < fphRquiredNum)) {
            throw new AppException(ErrorCode.CUSTOM, "剩余发票号不足！");
        }
        return bdcFphDOList;
    }

    @Override
    public List<BdcFphDO> getBdcFphDOList(int count) {
        if(count>0){
            final UserDto userDto = userManagerUtils.getCurrentUser();
            final String userName = userDto.getAlias();
            final String userid = userDto.getId();
            logger.info("获取发票号当前用户信息为:{}", userDto.toString());
            String qxdm = userManagerUtils.getRegionCodeByUserName(userDto.getUsername());
            logger.info("获取发票号当前用户qxdm为:{}", qxdm);
            int fphRquiredNum = count;
            BdcFphQO bdcFphQO = new BdcFphQO();
            bdcFphQO.setSyqk(BdcZssyqkEnum.YLY.getCode());
            bdcFphQO.setNum(fphRquiredNum);
            String lqfs = propsConfig.getFphlqfs(qxdm);
            logger.info("发票号领取方式:{}", lqfs);
            if (StringUtils.isBlank(lqfs)){
                // 领取方式为没有配置，代表不需要开票
                throw new AppException(ErrorCode.CONFIG_EX, "未获取到领取方式配置，该机构所在行政区域不需要开票");
            }
            if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_BM, lqfs)) {
                // 按领取部门领取
                if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                    throw new MissingArgumentException("系统所配印制号获取方式为：部门领取，但是当前用户没有所在部门的信息！");
                }
                bdcFphQO.setLqbmid(userDto.getOrgRecordList().get(0).getId());
            }
            if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_RY, lqfs)) {
                bdcFphQO.setLqrid(userDto.getId());
            }
            if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_QXDM, lqfs)) {
                bdcFphQO.setQxdm(qxdm);
            }
            logger.info("------查询发票号参数:{}", bdcFphQO);
            List<BdcFphDO> bdcFphDOList = bdcXtFphMapper.listBdcXtFph(bdcFphQO);
            if ((CollectionUtils.size(bdcFphDOList) < fphRquiredNum)) {
                throw new AppException(ErrorCode.CUSTOM, "剩余发票号不足！");
            }

            for(BdcFphDO bdcFphDO : bdcFphDOList){
                // 生成使用明细
                BdcFphSymxDO bdcFphSymxDO = new BdcFphSymxDO();
                bdcFphSymxDO.setFphsymxid(UUIDGenerator.generate16());
                bdcFphSymxDO.setFphid(bdcFphDO.getFphid());
                bdcFphSymxDO.setSyr(userName);
                bdcFphSymxDO.setSyrid(userid);
                bdcFphSymxDO.setSyqk(BdcZssyqkEnum.YSY.getCode());
                bdcFphSymxDO.setSysj(new Date());
                entityMapper.insertSelective(bdcFphSymxDO);
                // 发票号使用情况
                bdcFphDO.setSyqk(BdcZssyqkEnum.YSY.getCode());
                bdcFphDO.setFphsymxid(bdcFphSymxDO.getFphsymxid());
                entityMapper.updateByPrimaryKeySelective(bdcFphDO);
            }
            return bdcFphDOList;
        }
       return new ArrayList<>();
    }

    /**
     * 对收费信息进行分组，按月结算与非按月结算
     * @param bdcSlSfxxDOList
     * @return Pair<left, right> left: 非月结收费信息； rigth: 月结收费信息
     */
    public Pair<List<BdcSlSfxxDO>,List<BdcSlSfxxDO>> groupFayjsAndAyjs(List<BdcSlSfxxDO> bdcSlSfxxDOList){
        List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.listAyjsBdcXtJg();
        List<BdcSlSfxxDO> bdcSlSfxxDOListFayjs = new ArrayList<>();
        List<BdcSlSfxxDO> bdcSlSfxxDOListAyjs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
            Set<String> jgmcSet = bdcXtJgDOList.stream().map(p -> p.getJgmc()).collect(Collectors.toSet());
            bdcSlSfxxDOListFayjs = bdcSlSfxxDOList.stream().filter(bdcSlSfxxDO -> !jgmcSet.contains(bdcSlSfxxDO.getJfrxm())).collect(Collectors.toList());
            bdcSlSfxxDOListAyjs = bdcSlSfxxDOList.stream().filter(bdcSlSfxxDO -> jgmcSet.contains(bdcSlSfxxDO.getJfrxm())).collect(Collectors.toList());
        }
        return Pair.of(bdcSlSfxxDOListFayjs, bdcSlSfxxDOListAyjs);
    }
    /**
     * @param bdcFphDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改发票号使用状态 并且 修改 fphsymxid 和slbh
     */
    @Override
    public int updateBdcFphSyzt(BdcFphDO bdcFphDO) {
        return bdcXtFphMapper.updateBdcFphSyzt(bdcFphDO);
    }

    @Override
    public Integer getBdcFphSyzt(String fph) {
        Integer syzt = null;
        if (!CheckParameter.checkAnyParameter(fph)) {
            throw new AppException("发票号不能为空！");
        }
        Example example = new Example(BdcFphDO.class);
        example.createCriteria().andEqualTo("fph", fph);
        List<BdcFphDO> bdcFphDOList = entityMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(bdcFphDOList)
                && bdcFphDOList.get(0).getSyqk() != null) {
            syzt = bdcFphDOList.get(0).getSyqk();
        }
        return syzt;
    }

    @Override
    public Integer syqkEdit(List<BdcFphDO> bdcFphDOList) {
        Integer count = 0;
        for (BdcFphDO bdcFphDO : bdcFphDOList) {
            BdcFphQO bdcFphQO = new BdcFphQO();
            if (StringUtils.isNotBlank(bdcFphDO.getFph())) {
                bdcFphQO.setFph(bdcFphDO.getFph());
                bdcFphQO.setFphid(bdcFphDO.getFphid());
                List<BdcFphDO> bdcFphResultList = bdcXtFphMapper.listBdcXtFph(bdcFphQO);
                // 将查询结果进行更新
                if (CollectionUtils.isNotEmpty(bdcFphResultList)) {
                    for (BdcFphDO bdcFphResult : bdcFphResultList) {
                        bdcFphResult.setSyqk(bdcFphDO.getSyqk());
                        count += entityMapper.updateByPrimaryKeySelective(bdcFphResult);
                    }
                }
            }
        }
        return count;
    }

    @Override
    public Boolean checkYyFph(BdcFphQO bdcFphQO) {
        // 获取当前用户
        UserDto userDto = userManagerUtils.getCurrentUser();
        String qxdm = userManagerUtils.getRegionCodeByUserName(userDto.getUsername());
        // 获取领取方式
        String lqfs = propsConfig.getFphlqfs(qxdm);
        if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_BM, lqfs)) {
            // 按领取部门领取
            if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                throw new MissingArgumentException("系统所配印制号获取方式为：部门领取，但是当前用户没有所在部门的信息！");
            }
            bdcFphQO.setLqbmid(userDto.getOrgRecordList().get(0).getId());
        }
        if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_RY, lqfs)) {
            bdcFphQO.setLqrid(userDto.getId());
        }
        if (StringUtils.equals(CommonConstantUtils.YZH_LQFS_QXDM, lqfs)) {
            bdcFphQO.setQxdm(qxdm);
        }
        List<BdcFphDO> bdcFphDOList = bdcXtFphMapper.listBdcXtFph(bdcFphQO);
        return CollectionUtils.isNotEmpty(bdcFphDOList);
    }

    @Override
    public BdcFphDO getBdcFphDO(String fph) {
        BdcFphDO bdcFphDO = null;
        Example example = new Example(BdcFphDO.class);
        example.createCriteria().andEqualTo("fph", fph);
        List<BdcFphDO> bdcFphDOList = entityMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(bdcFphDOList)) {
            bdcFphDO = bdcFphDOList.get(0);
        }
        return bdcFphDO;
    }

    /**
     * @param fphid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取发票号使用明细
     * @date : 2020/11/26 18:32
     */
    @Override
    public List<BdcFphSymxDTO> listFphSymx(String fphid) {
        if(StringUtils.isBlank(fphid)) {
            throw new AppException("获取发票号使用明细缺失发票号id");
        }
        return bdcXtFphMapper.listFphSymx(fphid);
    }
}
