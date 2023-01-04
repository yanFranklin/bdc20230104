package cn.gtmap.realestate.inquiry.service.impl.yancheng;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.*;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.*;
import cn.gtmap.realestate.common.core.dto.pub.ResponseHeadEntityDTO;
import cn.gtmap.realestate.common.core.enums.BdcZqDjztEnum;
import cn.gtmap.realestate.common.core.enums.BdcZqShjdEnum;
import cn.gtmap.realestate.common.core.enums.BdcZqShztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDtcxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZqZxsqMapper;
import cn.gtmap.realestate.inquiry.service.yancheng.BdcZqSdService;
import cn.gtmap.realestate.inquiry.service.yancheng.BdcZqService;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/16
 * @description 盐城征迁系统服务接口
 */
@Service
public class BdcZqServiceImpl implements BdcZqService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZqServiceImpl.class);

    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZqZxsqMapper bdcZqZxsqMapper;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private StorageUtils storageUtils;
    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private TaskUtils taskUtils;
    @Autowired
    private BdcZqSdService bdcZqSdService;
    @Autowired
    private BdcDtcxFeignService bdcDtcxFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;

    /**
     * 是否复审（默认复审）
     */
    @Value("${zq.fs.enable:true}")
    private boolean fsEnable;

    /**
     * 保存注销申请信息
     * @param bdcZqZxsqDO 注销申请信息
     * @return String 注销申请信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public String saveZxsq(BdcZqZxsqDO bdcZqZxsqDO) {
        if(null == bdcZqZxsqDO) {
            throw new AppException("未定义注销申请信息");
        }

        if(StringUtils.isBlank(bdcZqZxsqDO.getSqxxid())) {
            bdcZqZxsqDO.setSqxxid(UUIDGenerator.generate16());

            // 注销申请编号
            String zxsqbh = Constants.BDC_YC_ZQ_ZXSQ_BH_PRE + bdcBhFeignService.queryBh(Constants.BDC_YC_ZQ_ZXSQ_BH, CommonConstantUtils.ZZSJFW_YEAR);
            bdcZqZxsqDO.setZxsqbh(zxsqbh);
            // 初始审核状态：正在编辑
            bdcZqZxsqDO.setShzt(BdcZqShztEnum.ZZBJ.getCode());

            UserDto userDto = userManagerUtils.getCurrentUser();
            bdcZqZxsqDO.setZxsqr(userDto.getAlias());
            bdcZqZxsqDO.setZxsqrid(userDto.getId());
            bdcZqZxsqDO.setZxsqsj(new Date());

            entityMapper.insertSelective(bdcZqZxsqDO);
        } else {
            entityMapper.updateByPrimaryKeySelective(bdcZqZxsqDO);
        }

        LOGGER.info("盐城征迁：保存注销申请信息1条，对应ID：{}，xmid:{}, 不动产单元号：{}", bdcZqZxsqDO.getSqxxid(), bdcZqZxsqDO.getXmid(), bdcZqZxsqDO.getBdcdyh());
        return bdcZqZxsqDO.getSqxxid();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateZxsq(BdcZqZxsqDTO bdcZqZxsqDTO) {
        BdcZqZxsqDO bdcZqZxsqDO = new BdcZqZxsqDO();
        BeanUtils.copyProperties(bdcZqZxsqDTO,bdcZqZxsqDO);
        if(Objects.nonNull(bdcZqZxsqDO.getShzt()) && bdcZqZxsqDO.getShzt().equals(BdcZqShztEnum.YTJ.getCode())){
            //如果更新审核状态，且要将审核状态更新为提交则判断是否要进行复审
            if(Objects.nonNull(fsEnable) && (!fsEnable)){
                bdcZqZxsqDO.setShzt(BdcZqShztEnum.SHTG.getCode());
            }
        }
        bdcZqZxsqMapper.updateZxsq(bdcZqZxsqDO);

        // 保存审核信息
        if(this.isYtj(bdcZqZxsqDTO) || this.isYsh(bdcZqZxsqDTO)) {
            BdcZqShxxDO bdcZqShxxDO = new BdcZqShxxDO();
            bdcZqShxxDO.setShxxid(UUIDGenerator.generate16());
            bdcZqShxxDO.setSqxxid(bdcZqZxsqDTO.getSqxxid());
            bdcZqShxxDO.setShjd(this.isYtj(bdcZqZxsqDTO) ? BdcZqShjdEnum.QRJD.getCode() : BdcZqShjdEnum.SHJD.getCode());

            UserDto userDto = userManagerUtils.getCurrentUser();
            bdcZqShxxDO.setShryxm(userDto.getAlias());
            bdcZqShxxDO.setShryid(userDto.getId());
            bdcZqShxxDO.setShsj(new Date());
            bdcZqShxxDO.setQmid(userDto.getSignId());
            entityMapper.insertSelective(bdcZqShxxDO);
        }
    }

    /**
     * 是否已提交状态
     * @param bdcZqZxsqDTO
     * @return 是 true ; 否 false
     */
    private boolean isYtj(BdcZqZxsqDTO bdcZqZxsqDTO) {
        return BdcZqShztEnum.YTJ.getCode().intValue() == bdcZqZxsqDTO.getShzt().intValue();
    }

    /**
     * 是否已审核状态
     * @param bdcZqZxsqDTO
     * @return 是 true ; 否 false
     */
    private boolean isYsh(BdcZqZxsqDTO bdcZqZxsqDTO) {
        return BdcZqShztEnum.SHTG.getCode().intValue() == bdcZqZxsqDTO.getShzt().intValue();
    }

    /**
     * 获取注销申请信息
     * @param sqxxid 注销申请信息ID
     * @return BdcZqZxsqDO 注销申请信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public BdcZqZxsqDO getZxsq(String sqxxid) {
        if(StringUtils.isBlank(sqxxid)) {
            throw new AppException("未定义注销申请查询参数");
        }
        return entityMapper.selectByPrimaryKey(BdcZqZxsqDO.class, sqxxid);
    }

    /**
     * 解冻
     * @param bdcZqDjDOS 冻结单元信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchUpdateDj(List<BdcZqDjDO> bdcZqDjDOS) {
        if(CollectionUtils.isEmpty(bdcZqDjDOS)) {
            throw new AppException("未定义需要冻结的数据");
        }

        for(BdcZqDjDO bdcZqDjDO : bdcZqDjDOS) {
            // 更新冻结表
            entityMapper.updateByPrimaryKeySelective(bdcZqDjDO);

            // 解锁不动产单元和证书
            BdcZqDjDO djxx = entityMapper.selectByPrimaryKey(BdcZqDjDO.class, bdcZqDjDO.getDjxxid());
            this.jsBdcdyZs4Jd(djxx);
        }
    }

    /**
     * 新建附件文件夹
     * @param sqxxid 注销申请信息ID
     */
    @Override
    public String createZxsqFjcl(String sqxxid) {
        if(StringUtils.isBlank(sqxxid)) {
            return "";
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = userDto == null ? "" : userDto.getId();

        // 新建文件夹
        StorageDto storageDto = storageClient.createRootFolder("clientId", sqxxid, Constants.YC_ZQ_ZXSQ_FJ, userid);
        if(null == storageDto) {
            return "";
        }

        // 保存材料信息
        BdcZqFjclDO bdcZqFjclDO = new BdcZqFjclDO();
        bdcZqFjclDO.setFjid(UUIDGenerator.generate16());
        bdcZqFjclDO.setSqxxid(sqxxid);
        bdcZqFjclDO.setClmc(Constants.YC_ZQ_ZXSQ_FJ);
        bdcZqFjclDO.setWjzxid(storageDto.getId());
        entityMapper.insertSelective(bdcZqFjclDO);
        LOGGER.info("盐城征迁：保存附件材料信息1条，对应ID：{}，对应申请信息ID：{}", bdcZqFjclDO.getFjid(), sqxxid);

        return storageDto.getId();
    }

    /**
     * 征迁审批完，在登记查看注销申请台账 新建注销项目
     * @param bdcZqZxsqDTO 注销申请信息
     * @return BdcXmDO 项目信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BdcXmDO createProcess(BdcZqZxsqDTO bdcZqZxsqDTO) {
        if(null == bdcZqZxsqDTO || StringUtils.isAnyBlank(bdcZqZxsqDTO.getXmid(), bdcZqZxsqDTO.getLcbs())) {
            throw new AppException("未定义新建项目所需参数信息");
        }

        // 1 先创建受理基本信息
        BdcCshSlxmDTO bdcCshSlxmDTO = this.initSljbxx(bdcZqZxsqDTO);

        // 2 再创建流程
        BdcXmDO bdcXmDO = this.initBdcXm(bdcZqZxsqDTO, bdcCshSlxmDTO);

        // 3 保存新建的注销项目信息到征迁申请表
        this.updateZqxmZxlcxx(bdcZqZxsqDTO, bdcXmDO);

        // 4、同步附件材料
        Example example = new Example(BdcZqZxsqDO.class);
        example.createCriteria().andEqualTo("xmid", bdcZqZxsqDTO.getXmid());
        List<BdcZqZxsqDO> zxsqDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(zxsqDOList) || null == zxsqDOList.get(0)) {
            return bdcXmDO;
        }
        storageUtils.copyAllFjcl(null, zxsqDOList.get(0).getSqxxid(), Constants.WJZX_CLIENTID, bdcXmDO.getGzlslid());

        return bdcXmDO;
    }

    /**
     * 获取征迁冻结文号
     * @return String 冻结文号
     */
    @Override
    public String getDjwh() {
        // 注销申请编号
        return Constants.BDC_YC_ZQ_DJSQ_BH_PRE + bdcBhFeignService.queryBh(Constants.BDC_YC_ZQ_DJSQ_BH, CommonConstantUtils.ZZSJFW_YEAR);
    }

    /**
     * 图形冻结选中不动产单元处理接口
     * @param djDOList 冻结单元信息
     * @return ResultDataVO 返回信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseHeadEntityDTO djBdcdy(List<BdcZqDjDO> djDOList) {
        if(CollectionUtils.isEmpty(djDOList)) {
            return this.getResponseEntity("1", "未传参冻结单元信息");
        }

        for(BdcZqDjDO bdcZqDjDO : djDOList) {
            if (StringUtils.isBlank(bdcZqDjDO.getBdcdyh())) {
                return this.getResponseEntity("2", "存在未定义单元号数据");
            }

            if (StringUtils.isBlank(bdcZqDjDO.getDjyy())) {
                return this.getResponseEntity("2", "存在未定义冻结原因数据");
            }
        }

        try {
            String djwh = djDOList.get(0).getDjwh();
            if(StringUtils.isBlank(djwh)) {
                djwh = this.getDjwh();
            }

            UserDto userDto = userManagerUtils.getCurrentUser();

            for(BdcZqDjDO bdcZqDjDO : djDOList) {
                if(StringUtils.isBlank(bdcZqDjDO.getDjwh())) {
                    bdcZqDjDO.setDjwh(djwh);
                }

                bdcZqDjDO.setDjxxid(UUIDGenerator.generate16());
                bdcZqDjDO.setDjzt(BdcZqDjztEnum.YDJ.getCode());
                if(null == bdcZqDjDO.getDjsqsj()) {
                    bdcZqDjDO.setDjsqsj(new Date());
                }
                if(null != userDto && StringUtils.isBlank(bdcZqDjDO.getDjsqr())) {
                    bdcZqDjDO.setDjsqr(userDto.getAlias());
                    bdcZqDjDO.setDjsqrid(userDto.getId());
                }

                // 锁定单元、证书
                bdcZqSdService.sdBdcdyZs4Dj(bdcZqDjDO);
            }
            entityMapper.insertBatchSelective(djDOList);
            LOGGER.info("征迁冻结单元数据：{}", JSON.toJSONString(djDOList));

            return this.getResponseEntity("0", "冻结成功");
        } catch (Exception e) {
            e.printStackTrace();
            return this.getResponseEntity("4", "冻结处理失败");
        }
    }

    /**
     * 征迁在审核通过台账、审核台账退回功能请求
     *
     * @param bdcZqThxxDO 退回信息
     * @return 退回信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zxsqTh(BdcZqThxxDO bdcZqThxxDO) {
        if(null == bdcZqThxxDO || StringUtils.isEmpty(bdcZqThxxDO.getSqxxid())){
            throw new AppException("参数信息为空!");
        }

        // 1、保存退回信息
        if(StringUtils.isBlank(bdcZqThxxDO.getThxxid())) {
            bdcZqThxxDO.setThxxid(UUIDGenerator.generate16());
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            bdcZqThxxDO.setThryxm(userDto.getAlias());
            bdcZqThxxDO.setThryid(userDto.getId());
            bdcZqThxxDO.setThsj(new Date());
        }
        entityMapper.insertSelective(bdcZqThxxDO);

        // 2、解锁单元和证书
        BdcZqZxsqDO zq = entityMapper.selectByPrimaryKey(BdcZqZxsqDO.class, bdcZqThxxDO.getSqxxid());
        if(null == zq || isShq(zq.getShzt())) {
            LOGGER.warn("注销审核台账退回不处理解锁单元、证书，对应注销申请信息ID：{}", bdcZqThxxDO.getSqxxid());
        } else {
            this.jsBdcdyZs4Zx(bdcZqThxxDO.getSqxxid());
        }

        // 3、更新申请信息状态
        BdcZqZxsqDO bdcZqZxsqDO = new BdcZqZxsqDO();
        bdcZqZxsqDO.setSqxxid(bdcZqThxxDO.getSqxxid());
        bdcZqZxsqDO.setShzt(BdcZqShztEnum.YTH.getCode());
        entityMapper.updateByPrimaryKeySelective(bdcZqZxsqDO);

        return bdcZqThxxDO.getThxxid();
    }

    /**
     * 判断当前是否是审核之前状态
     * @param shzt 审核状态
     * @return 空、正在编辑、已提交 true ; 其它 false
     */
    private boolean isShq(Integer shzt) {
        if(null == shzt) {
            return true;
        }

        if(shzt.intValue() == BdcZqShztEnum.ZZBJ.getCode()) {
            return true;
        }

        if(shzt.intValue() == BdcZqShztEnum.YTJ.getCode()) {
            return true;
        }

        return false;
    }

    /**
     * 征迁流程删除退回信息同步接口
     *
     * @param bdcZqLcthDTO 删除退回信息
     * @return 退回信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public String zxsqLcth(BdcZqLcthDTO bdcZqLcthDTO) {
        if(null == bdcZqLcthDTO || StringUtils.isEmpty(bdcZqLcthDTO.getGzlslid())){
            throw new AppException("参数信息为空!");
        }

        // 查询流程对应的注销申请
        Example example = new Example(BdcZqZxsqDO.class);
        example.createCriteria().andEqualTo("zxlcgzlslid", bdcZqLcthDTO.getGzlslid());
        List<BdcZqZxsqDO> zxsqDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(zxsqDOList) || null == zxsqDOList.get(0)) {
            throw new AppException("未查询到对应流程注销申请信息!");
        }

        // 1、保存退回信息
        BdcZqThxxDO bdcZqThxxDO = new BdcZqThxxDO();
        bdcZqThxxDO.setThxxid(UUIDGenerator.generate16());
        bdcZqThxxDO.setSqxxid(zxsqDOList.get(0).getSqxxid());
        bdcZqThxxDO.setThsj(new Date());

        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            bdcZqThxxDO.setThryxm(userDto.getAlias());
            bdcZqThxxDO.setThryid(userDto.getId());
        }

        if(StringUtils.isBlank(bdcZqLcthDTO.getThyy())) {
            bdcZqThxxDO.setThyy("登记流程删除");
        } else {
            bdcZqThxxDO.setThyy(bdcZqLcthDTO.getThyy());
        }
        entityMapper.insertSelective(bdcZqThxxDO);

        // 2、更新申请信息状态
        BdcZqZxsqDO bdcZqZxsqDO = zxsqDOList.get(0);
        if(null == bdcZqLcthDTO.getThzt()) {
            bdcZqZxsqDO.setShzt(BdcZqShztEnum.LCSC.getCode());
        } else {
            bdcZqZxsqDO.setShzt(bdcZqLcthDTO.getThzt());
        }
        // 流程删除需要将注销申请表中的流程信息置空
        bdcZqZxsqDO.setZxlcxmid(null);
        bdcZqZxsqDO.setZxlcslbh(null);
        bdcZqZxsqDO.setZxlcgzlslid(null);
        LOGGER.info("流程删除退回更新注销申请信息：{}", JSON.toJSONString(bdcZqZxsqDO));
        entityMapper.updateByPrimaryKeyNull(bdcZqZxsqDO);

        // 3、解锁单元、证书
        this.jsBdcdyZs4Zx(zxsqDOList.get(0).getSqxxid());

        return bdcZqThxxDO.getThxxid();
    }

    /**
     * 单元冻结（全部冻结）
     * @param bdcZqDjDTO 参数信息
     * @return 返回状态
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseHeadEntityDTO dydjQbdj(BdcZqDjDTO bdcZqDjDTO) {
        if(null == bdcZqDjDTO) {
            throw new MissingArgumentException("未定义全部冻结参数");
        }

        try {
            if(null == bdcZqDjDTO.getDjxx()) {
                bdcZqDjDTO.setDjxx(new BdcZqDjDO());
            }

            // 冻结文号
            if(StringUtils.isBlank(bdcZqDjDTO.getDjxx().getDjwh())) {
                bdcZqDjDTO.getDjxx().setDjwh(this.getDjwh());
            }

            // 冻结操作人
            if(StringUtils.isBlank(bdcZqDjDTO.getDjxx().getDjsqr())) {
                UserDto userDto = userManagerUtils.getCurrentUser();
                if(null != userDto) {
                    bdcZqDjDTO.getDjxx().setDjsqr(userDto.getAlias());
                    bdcZqDjDTO.getDjxx().setDjsqrid(userDto.getId());
                }
            }

            // 冻结时间支持修改
            if(null == bdcZqDjDTO.getDjxx().getDjsqsj()) {
                bdcZqDjDTO.getDjxx().setDjsqsj(new Date());
            }

            // 需要排除不冻结的单元
            HashSet<String> bdcdyZhSet = new HashSet<>();
            if(CollectionUtils.isNotEmpty(bdcZqDjDTO.getBdjdy())) {
                for(BdcZqDjDO bdcZqDjDO : bdcZqDjDTO.getBdjdy()) {
                    bdcdyZhSet.add(bdcZqDjDO.getBdcdyh() + "&" + bdcZqDjDO.getBdcqzh());
                }
            }

            // 全部冻结分批次调用自定义查询SQL，将查询出的记录冻结处理，避免一次查询数据过多
            // 循环处理为避免while内部处理不当无法退出，采用for，另外最大循环数1000，支持查询数据 1000 * 200，正常情况满足数据量需求
            String queryParamStr = null == bdcZqDjDTO.getCxcs() ? "" : JSON.toJSONString(bdcZqDjDTO.getCxcs());
            for(int i = 0; i < 1000; i++) {
                Page result = bdcDtcxFeignService.listResult(queryParamStr, i, 500, null);
                if (null == result || CollectionUtils.isEmpty(result.getContent())) {
                    LOGGER.warn("征迁全部冻结操作当前循环{}未查询到单元信息，冻结操作结束，对应预定义冻结文号：{}，冻结操作人：{}", i, bdcZqDjDTO.getDjxx().getDjwh(), bdcZqDjDTO.getDjxx().getDjsqr());
                    break;
                }

                this.dydjQbdj(bdcZqDjDTO, result.getContent(), bdcdyZhSet);
            }

            return this.getResponseEntity("0", "冻结成功");
        } catch (Exception e) {
            e.printStackTrace();
            return this.getResponseEntity("4", "冻结处理失败");
        }
    }

    /**
     * 组织“产权查询指定区域不动产信息汇总”Excel导出参数
     *
     * @param bdcZqXxhzDTO 参数信息
     * @return ExcelExportDTO 导出Excel相关参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public ExcelExportDTO xxhzExcel(BdcZqXxhzDTO bdcZqXxhzDTO) {
        if(null == bdcZqXxhzDTO || StringUtils.isBlank(bdcZqXxhzDTO.getParam())) {
            throw new MissingArgumentException("导出汇总表异常：未定义参数");
        }

        // 调用自定义查询获取数据内容
        Page result = bdcDtcxFeignService.listResult(bdcZqXxhzDTO.getParam(), 0, 10000, null);
        if (null == result || CollectionUtils.isEmpty(result.getContent())) {
            throw new AppException("导出汇总表异常：未查询到数据");
        }

        // 组织导出Excel数据参数DTO
        ExcelExportDTO excelExportDTO = new ExcelExportDTO();

        List<HashMap> oldExcelData = result.getContent();
        List<HashMap> newExcelData = new ArrayList<>(oldExcelData.size() + 100);

        // 每个地籍对应建筑面积和（登记、权籍分别统计）
        BigDecimal jzmj = new BigDecimal(0);
        // 按照地籍号房屋用途分类统计的建筑面积和： key > 地籍号；value > key: 房屋用途；value：对应用途面积和
        Map<String, LinkedHashMap<String, BigDecimal>> mjMap = new LinkedHashMap<>();

        // 添加汇总标题
        this.addTitleRows(newExcelData);

        for(int i = 0; i < oldExcelData.size(); i++) {
            newExcelData.add(oldExcelData.get(i));

            String curDjh = StringToolUtils.valueOf(oldExcelData.get(i).get("DJH"), "");
            BigDecimal curRowJzmj = BigDecimal.valueOf(doubleOf(oldExcelData.get(i).get("JZMJ"), 0.0));
            jzmj = jzmj.add(curRowJzmj);

            // 计算不同地籍各类房屋用途面积和
            String fwyt = StringToolUtils.valueOf(oldExcelData.get(i).get("FWYT"), "其它");
            LinkedHashMap<String, BigDecimal> djhMap = mjMap.get(curDjh);
            if(MapUtils.isEmpty(djhMap)) {
                // 当前地籍号对应统计数据不存在
                djhMap = new LinkedHashMap();
                mjMap.put(curDjh, djhMap);
            }
            BigDecimal sum = djhMap.get(fwyt) == null ? new BigDecimal(0) : djhMap.get(fwyt);
            sum = sum.add(curRowJzmj);
            mjMap.get(curDjh).put(fwyt, sum);

            // 最后一行
            if(i == oldExcelData.size() - 1) {
                newExcelData.add(newDataMap(" , , , , , , , , ,合计建筑面积," + jzmj.toString() + ", , , , "));
                break;
            }

            String nextDjh = StringToolUtils.valueOf(oldExcelData.get(i + 1).get("DJH"), "");
            if(!StringUtils.equals(curDjh, nextDjh)) {
                // 当前行地籍号和下一行地籍号不一致说明当前汇总地籍号结束，增加汇总行
                newExcelData.add(newDataMap(" , , , , , , , , ,合计建筑面积," + jzmj.toString() + ", , , , "));
                jzmj = new BigDecimal(0);
            }
        }

        // 添加汇总信息
        this.addHzData(newExcelData, mjMap);

        excelExportDTO.setData(JSON.toJSONString(newExcelData));
        excelExportDTO.setFileName("指定区域不动产信息汇总");
        excelExportDTO.setSheetName("指定区域不动产信息汇总");
        excelExportDTO.setCellTitle("地籍号,不动产单元号,权利人,坐落,权利类型,宗地面积,权利人,坐落,不动产权证号,房屋用途,建筑面积,是否注销,是否抵押,是否查封");
        excelExportDTO.setCellKey("DJH,BDCDYH,TDSYZMC,TDZL,SYQLX,SCMJ,QLR,ZL,BDCQZH,FWYT,JZMJ,SFZX,SFDY,SFCF");
        excelExportDTO.setCellWidth("25,35,25,35,10,18,15,40,35,18,10,10,10,15");
        excelExportDTO.setMergeSameCell(true);
        excelExportDTO.setMergeCellKey("DJH");
        excelExportDTO.setAddBorder(true);
        return excelExportDTO;
    }

    /**
     * 组织“指定区域内的现势建设用地使用权”Excel导出参数
     *
     * @param bdcZqXxhzDTO 参数信息
     * @return ExcelExportDTO 导出Excel相关参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public ExcelExportDTO xxhzJsydsyqExcel(BdcZqXxhzDTO bdcZqXxhzDTO) {
        if(null == bdcZqXxhzDTO || StringUtils.isBlank(bdcZqXxhzDTO.getParam())) {
            throw new MissingArgumentException("导出汇总表异常：未定义参数");
        }

        // 获取 指定区域建设用地信息汇总 自定义查询配置
        DtcxDO dtcxDO = new DtcxDO();
        dtcxDO.setCxdh("zdqyjsydsyqhz");
        List<DtcxDO> list = entityMapper.selectByObj(dtcxDO);
        if(CollectionUtils.isEmpty(list) || null == list.get(0)) {
            throw new AppException("未定义自定义查询：征迁指定区域建设用地信息汇总");
        }

        // 调用自定义查询获取数据内容
        Map<String, Object> param = JSON.parseObject(bdcZqXxhzDTO.getParam(), Map.class);
        // 前端请求时候默认查询第一个工作表sheet对应自定义查询 zqxxhz，这里第二个工作表sheet替换下自定义查询目标
        param.put("cxdh", "zdqyjsydsyqhz");
        param.put("cxid", list.get(0).getCxid());
        Page result = bdcDtcxFeignService.listResult(JSON.toJSONString(param), 0, 10000, null);

        List<HashMap> newExcelData = new ArrayList<>(result.getContent().size() + 3);
        // 设置标题行
        newExcelData.add(newDataMap2(" , , , , , , , , , , , ,单位：平方米"));
        newExcelData.add(newDataMap2("地籍号,调查表,调查表,调查表,调查表,登记信息,登记信息,登记信息,登记信息,登记信息,登记信息,限制信息,限制信息"));
        newExcelData.add(newDataMap2("地籍号,权利人,坐落,权利类型,宗地面积,权利人,坐落,不动产权证号,土地用途,使用权面积,是否注销,是否抵押,是否查封"));
        // 数据行，这里允许空数据导出
        newExcelData.addAll(result.getContent());

        // 组织导出Excel数据参数DTO
        ExcelExportDTO excelExportDTO = new ExcelExportDTO();
        excelExportDTO.setData(JSON.toJSONString(newExcelData));
        excelExportDTO.setFileName("指定区域建设用地使用权信息汇总");
        excelExportDTO.setSheetName("指定区域建设用地使用权信息汇总");
        excelExportDTO.setCellTitle("地籍号,权利人,坐落,权利类型,宗地面积,权利人,坐落,不动产权证号,土地用途,使用权面积,是否注销,是否抵押,是否查封");
        excelExportDTO.setCellKey("DJH,TDSYZMC,TDZL,SYQLX,SCMJ,QLR,ZL,BDCQZH,TDYT,SYQMJ,SFZX,SFDY,SFCF");
        excelExportDTO.setCellWidth("25,25,35,10,18,25,40,35,18,20,10,10,15");
        excelExportDTO.setMergeSameCell(true);
        excelExportDTO.setMergeCellKey("DJH");
        excelExportDTO.setAddBorder(true);
        return excelExportDTO;
    }

    @Override
    public String batchInsertZxsq(List<BdcZqZxsqDO> bdcZqZxsqDOS){
        if(CollectionUtils.isEmpty(bdcZqZxsqDOS)) {
            throw new AppException("未定义注销申请信息");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        // 注销申请编号
        String zxsqbh = Constants.BDC_YC_ZQ_ZXSQ_BH_PRE + bdcBhFeignService.queryBh(Constants.BDC_YC_ZQ_ZXSQ_BH, CommonConstantUtils.ZZSJFW_YEAR);
        List<BdcZqZxsqDO> zqZxsqDOList =new ArrayList<>();
        for(BdcZqZxsqDO bdcZqZxsqDO:bdcZqZxsqDOS){
            if(StringUtils.isNotBlank(bdcZqZxsqDO.getBdcdyh())) {
                bdcZqZxsqDO.setSqxxid(UUIDGenerator.generate16());
                bdcZqZxsqDO.setZxsqbh(zxsqbh);
                // 初始审核状态：正在编辑
                bdcZqZxsqDO.setShzt(BdcZqShztEnum.ZZBJ.getCode());
                bdcZqZxsqDO.setZxsqr(userDto.getAlias());
                bdcZqZxsqDO.setZxsqrid(userDto.getId());
                bdcZqZxsqDO.setZxsqsj(new Date());
                zqZxsqDOList.add(bdcZqZxsqDO);
            }
        }
        int count =0;
        if(CollectionUtils.isNotEmpty(zqZxsqDOList)) {
            count = entityMapper.insertBatchSelective(zqZxsqDOList);
        }
        LOGGER.info("征迁：保存注销申请信息{}条,注销申请编号：{}", count,zxsqbh);
        if(count ==0){
            return null;
        }
        return zxsqbh;


    }

    @Override
    public BdcZqZxsqDO queryZxsqByBdcdyh(String bdcdyh) {
        if(StringUtils.isBlank(bdcdyh)) {
            throw new AppException("未定义注销申请查询参数");
        }
        Example example =new Example(BdcZqZxsqDO.class);
        example.setOrderByClause("sqxxid DESC");
        example.createCriteria().andEqualTo("bdcdyh",bdcdyh);
        List<BdcZqZxsqDO> bdcZqZxsqDOS =entityMapper.selectByExample(BdcZqZxsqDO.class, example);
        if(CollectionUtils.isNotEmpty(bdcZqZxsqDOS)){
            return bdcZqZxsqDOS.get(0);
        }
        return null;
    }

    /**
     * 执行全部冻结操作
     * @param bdcZqDjDTO 冻结文号等信息
     * @param content 冻结单元集合
     * @param bdcdyZhSet 不需要冻结的单元证号信息
     */
    private void dydjQbdj(BdcZqDjDTO bdcZqDjDTO, List<Map> content, HashSet<String> bdcdyZhSet) {
        List<BdcZqDjDO> zqDjDOList = new ArrayList<>();
        for(Map item : content) {
            String bdcdyh = (String) item.get("BDCDYH");
            String bdcqzh = (String) item.get("BDCQZH");
            Integer djzt = integerOf(item.get("DJZT"));

            if(null != djzt && 1 == djzt) {
                LOGGER.info("当前冻结文号{}对应冻结操作单元：{}，证号：{}对应记录已冻结过，不再冻结处理", bdcZqDjDTO.getDjxx().getDjwh(), bdcdyh, bdcqzh);
                continue;
            }

            // 排除不需要冻结的记录
            if(bdcdyZhSet.contains(bdcdyh + "&" + bdcqzh)) {
                LOGGER.info("当前冻结文号{}对应冻结操作单元：{}，证号：{}对应记录已被排除冻结", bdcZqDjDTO.getDjxx().getDjwh(), bdcdyh, bdcqzh);
                continue;
            }

            BdcZqDjDO bdcZqDjDO = new BdcZqDjDO();
            bdcZqDjDO.setDjxxid(UUIDGenerator.generate16());
            bdcZqDjDO.setBdcdyh(bdcdyh);
            bdcZqDjDO.setBdcqzh(bdcqzh);
            bdcZqDjDO.setQllx(integerOf(item.get("QLLX")));
            bdcZqDjDO.setBdclx(integerOf(item.get("BDCLX")));
            bdcZqDjDO.setDjlx(integerOf(item.get("DJLX")));
            bdcZqDjDO.setZl(valueOf(item.get("ZL")));
            bdcZqDjDO.setQlrmc(valueOf(item.get("QLR")));
            bdcZqDjDO.setQlrzjh(valueOf(item.get("QLRZJH")));
            bdcZqDjDO.setBdcdywybh(valueOf(item.get("BDCDYWYBH")));
            bdcZqDjDO.setDjyy(bdcZqDjDTO.getDjxx().getDjyy());
            bdcZqDjDO.setDjsqr(bdcZqDjDTO.getDjxx().getDjsqr());
            bdcZqDjDO.setDjsqrid(bdcZqDjDTO.getDjxx().getDjsqrid());
            bdcZqDjDO.setDjsqsj(bdcZqDjDTO.getDjxx().getDjsqsj());
            bdcZqDjDO.setDjkssj(bdcZqDjDTO.getDjxx().getDjkssj());
            bdcZqDjDO.setDjjssj(bdcZqDjDTO.getDjxx().getDjjssj());
            bdcZqDjDO.setDjwh(bdcZqDjDTO.getDjxx().getDjwh());
            bdcZqDjDO.setDjzt(BdcZqDjztEnum.YDJ.getCode());
            zqDjDOList.add(bdcZqDjDO);
        }

        if(CollectionUtils.isEmpty(zqDjDOList)) {
            return;
        }
        int count = entityMapper.insertBatchSelective(zqDjDOList);
        LOGGER.info("当前冻结文号{}对应冻结操作新增冻结记录{}条", bdcZqDjDTO.getDjxx().getDjwh(), count);

        // 锁定单元、证书
        bdcZqSdService.sdBdcdyZs4Dj(zqDjDOList);
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    private static Integer integerOf(Object obj) {
        return (obj == null) ? null : (Integer)obj;
    }

    /**
     * 组织返回信息
     * @param code 编码
     * @param message 内容
     */
    private ResponseHeadEntityDTO getResponseEntity(String code, String message) {
        ResponseHeadEntityDTO response = new ResponseHeadEntityDTO();
        response.setResponsecode(code);
        response.setResponseinfo(message);
        response.setResponsetime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return response;
    }

    /**
     * 冻结单元查询设置现势产权信息
     * @param bdcZqDjDO 冻结单元信息
     */
    private String setXscqXx(BdcZqDjDO bdcZqDjDO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcZqDjDO.getBdcdyh());
        bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
        bdcXmQO.setQllxs(this.getXscqQllx());
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(xmDOList) || null == xmDOList.get(0)) {
            LOGGER.error("征迁冻结当前单元{}未查询到现势产权信息", bdcZqDjDO.getBdcdyh());
            return bdcZqDjDO.getBdcdyh();
        }

        bdcZqDjDO.setBdcqzh(xmDOList.get(0).getBdcqzh());
        bdcZqDjDO.setZl(xmDOList.get(0).getZl());
        bdcZqDjDO.setQllx(xmDOList.get(0).getQllx());
        bdcZqDjDO.setDjlx(xmDOList.get(0).getDjlx());
        bdcZqDjDO.setBdclx(xmDOList.get(0).getBdclx());
        bdcZqDjDO.setQlrmc(xmDOList.get(0).getQlr());
        bdcZqDjDO.setQlrzjh(xmDOList.get(0).getQlrzjh());
        bdcZqDjDO.setZdzhmj(xmDOList.get(0).getZdzhmj());
        return "";
    }

    /**
     * 获取现势产权权利类型
     */
   private List<Integer> getXscqQllx() {
        List<Integer> qllxs = new ArrayList<>();
        qllxs.addAll(Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
        qllxs.add(Integer.parseInt(CommonConstantUtils.QLXX_QLLX_JSYDSYQ));
        qllxs.add(Integer.parseInt(CommonConstantUtils.QLXX_QLLX_TDSYQ));
        qllxs.add(Integer.parseInt(CommonConstantUtils.QLXX_QLLX_TDCBJYQNYDDQTSYQ));
        qllxs.add(Integer.parseInt(CommonConstantUtils.QLXX_QLLX_LQ));
        return qllxs;
   }

    /**
     * 保存新建的注销项目信息到征迁申请表
     * @param bdcZqZxsqDTO
     * @param bdcXmDO
     */
    private void updateZqxmZxlcxx(BdcZqZxsqDTO bdcZqZxsqDTO, BdcXmDO bdcXmDO) {
        BdcZqZxsqDO bdcZqZxsqDO = new BdcZqZxsqDO();
        bdcZqZxsqDO.setZxlcxmid(bdcXmDO.getXmid());
        bdcZqZxsqDO.setZxlcslbh(bdcXmDO.getSlbh());
        bdcZqZxsqDO.setZxlcgzlslid(bdcXmDO.getGzlslid());

        Example example = new Example(BdcZqZxsqDO.class);
        example.createCriteria().andEqualTo("xmid", bdcZqZxsqDTO.getXmid());
        entityMapper.updateByExampleSelective(bdcZqZxsqDO, example);
        LOGGER.info("更新征迁注销申请记录，对应xmid：{}, 更新的数据：zxlcxmid:{}, zxlcslbh:{} zxlcgzlslid:{}",
                bdcZqZxsqDTO.getXmid(), bdcXmDO.getXmid(), bdcXmDO.getSlbh(), bdcXmDO.getGzlslid());
    }

    /**
     *  创建受理基本信息
     * @param bdcZqZxsqDTO 注销申请信息
     * @return  BdcCshSlxmDTO 受理信息
     */
    private BdcCshSlxmDTO initSljbxx(BdcZqZxsqDTO bdcZqZxsqDTO) {
        UserDto user = userManagerUtils.getCurrentUser();
        if (null == user) {
            throw new AppException("未获取到用户信息");
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcZqZxsqDTO.getXmid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)) {
            throw new AppException("未获取到指定注销项目信息");
        }

        BdcCshSlxmDTO bdcCshSlxmDTO = new BdcCshSlxmDTO();
        bdcCshSlxmDTO.setGzldyid(bdcZqZxsqDTO.getLcbs());
        bdcCshSlxmDTO.setJbxxid(UUIDGenerator.generate16());
        bdcCshSlxmDTO.setBdcSlYwxxDTOList(new ArrayList<>());

        BdcSlYwxxDTO bdcSlYwxxDTO = new BdcSlYwxxDTO();
        bdcCshSlxmDTO.getBdcSlYwxxDTOList().add(bdcSlYwxxDTO);

        bdcSlYwxxDTO.setXmid(bdcXmDOList.get(0).getXmid());
        bdcSlYwxxDTO.setBdcdyh(bdcXmDOList.get(0).getBdcdyh());
        bdcSlYwxxDTO.setZl(bdcXmDOList.get(0).getZl());
        bdcSlYwxxDTO.setBdcdywybh(bdcXmDOList.get(0).getBdcdywybh());

        bdcSlXmFeignService.cshYxxm(user.getId(), bdcCshSlxmDTO);
        LOGGER.info("征迁创建注销流程，对应受理基本信息ID：{}", bdcCshSlxmDTO.getJbxxid());
        return bdcCshSlxmDTO;
    }

    /**
     * 新建注销项目
     * @param bdcZqZxsqDTO 注销申请信息
     * @param bdcCshSlxmDTO 受理信息
     * @return BdcXmDO 新创建的项目
     */
    private BdcXmDO initBdcXm(BdcZqZxsqDTO bdcZqZxsqDTO, BdcCshSlxmDTO bdcCshSlxmDTO) {
        BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
        bdcSlCshDTO.setJbxxid(bdcCshSlxmDTO.getJbxxid());
        String slbh = bdcBhFeignService.queryBh("slbh", "");
        bdcSlCshDTO.setSlbh(slbh);
        bdcSlCshDTO.setGzldyid(bdcZqZxsqDTO.getLcbs());

        // 新建流程，指定角色领取
        TaskData taskData = processInstanceClient.directStartByRole(bdcSlCshDTO.getGzldyid(), bdcZqZxsqDTO.getRoleCodes(), "",null,"");
        if(null == taskData || StringUtils.isBlank(taskData.getProcessInstanceId())) {
            LOGGER.error("征迁新建注销流程失败，对应项目ID：{}", bdcZqZxsqDTO.getXmid());
            throw new AppException("征迁新建注销流程失败");
        }

        // 初始化成功标志
        boolean cshSuccess = false;
        try {
            bdcSlCshDTO.setSlsj(taskData.getStartTime());
            bdcSlCshDTO.setGzldymc(taskData.getProcessDefName());
            bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
            bdcSlJbxxFeignService.insertBdcSlJbxx(bdcSlCshDTO);
            bdcSlFeignService.cshBdcSlxx(bdcSlCshDTO.getJbxxid());
            //标志初始化成功
            cshSuccess = true;
            bdcSlFeignService.cshSjcl(taskData.getProcessInstanceId());
            bdcSlSfxxFeignService.cshSfxx(taskData.getProcessInstanceId(), null);
        } catch (Exception ex) {
            LOGGER.error("征迁新建注销流程失败，对应项目ID：{}", bdcZqZxsqDTO.getXmid());

            //删除受理信息
            bdcSlFeignService.deleteBdcSlxx(taskData.getProcessInstanceId());
            //删除业务信息
            if (cshSuccess) {
                //初始化成功执行
                try {
                    bdcInitFeignService.deleteYwxx(taskData.getProcessInstanceId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //删除流程
            taskUtils.deleteTask(taskData.getProcessInstanceId(), taskData.getReason());
            throw new AppException("征迁新建注销流程失败");
        }

        // 查询新生成项目
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)) {
            throw new AppException("征迁新建注销流程失败");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        //说明创建成功，开始回写 zxcqzh 注销产权证号  38463 盐城_登记系统3.0网上业务状态栏修改需求
        Map<String, Object> paramMap = new HashMap<>();

        if (StringUtils.isNotBlank(bdcZqZxsqDTO.getBdcqzh())) {
            paramMap.put("ZXCQZH", bdcZqZxsqDTO.getBdcqzh());
        }
        // 执行回写
        try{
            if (MapUtils.isNotEmpty(paramMap)) {
                LOGGER.info("抵押注销大云回写字段异常参数,paramMap:{}", JSON.toJSONString(paramMap));
                bdcYwsjHxFeignService.updateBdcYwsj( bdcXmDO.getGzlslid(), paramMap);
            }
        } catch (Exception e){
            LOGGER.info("大云回写字段异常,slbh:{}", bdcXmDO.getSlbh(), e);
        }

        // 更新审批来源
        BdcXmDO param = new BdcXmDO();
        param.setXmid(bdcXmDO.getXmid());
        param.setSply(CommonConstantUtils.SPLY_YC_ZQ);
        bdcXmFeignService.updateBdcXm(param);

        LOGGER.info("已完成征迁新建注销流程，对应原项目ID：{}，新注销项目受理编号：{}，工作流实例ID：{}", bdcZqZxsqDTO.getXmid(), slbh, bdcXmDO.getGzlslid());
        return bdcXmDO;
    }

    /**
     * 征迁在审核通过台账退回、流程删除时同步解锁锁定单元、证书
     * @param sqxxid 征迁注销申请信息ID
     */
    private void jsBdcdyZs4Zx(String sqxxid) {
        BdcZqZxsqDO bdcZqZxsqDO = entityMapper.selectByPrimaryKey(BdcZqZxsqDO.class, sqxxid);

        BdcZqJsDTO bdcZqJsDTO = new BdcZqJsDTO();
        bdcZqJsDTO.setId(bdcZqZxsqDO.getSqxxid());
        bdcZqJsDTO.setJsyy(Constants.YC_ZQZX_JSYY);
        bdcZqJsDTO.setSdyy(Constants.YC_ZQZX_SDYY);
        bdcZqJsDTO.setBdcdyh(bdcZqZxsqDO.getBdcdyh());
        bdcZqJsDTO.setBdcqzh(bdcZqZxsqDO.getBdcqzh());
        bdcZqSdService.jsBdcdyZs(bdcZqJsDTO);
    }

    /**
     * 征迁在解冻时同步解锁单元、证书
     * @param bdcZqDjDO 解冻信息
     */
    private void jsBdcdyZs4Jd(BdcZqDjDO bdcZqDjDO) {
        BdcZqDjDO zqDjDO = entityMapper.selectByPrimaryKey(BdcZqDjDO.class, bdcZqDjDO.getDjxxid());
        BdcZqJsDTO bdcZqJsDTO = new BdcZqJsDTO();
        bdcZqJsDTO.setId(bdcZqDjDO.getDjxxid());
        bdcZqJsDTO.setJsyy(BdcZqSdServiceImpl.getDjjsyy(bdcZqDjDO));
        bdcZqJsDTO.setSdyy(BdcZqSdServiceImpl.getDjsdyy(bdcZqDjDO));
        bdcZqJsDTO.setBdcdyh(bdcZqDjDO.getBdcdyh());
        bdcZqJsDTO.setBdcqzh(null == zqDjDO ? "" : zqDjDO.getBdcqzh());
        bdcZqSdService.jsBdcdyZs(bdcZqJsDTO);
    }

    private HashMap newDataMap(String values) {
        String[] valuesArray = values.split(",");

        HashMap map = new HashMap();
        String names = "DJH,BDCDYH,TDSYZMC,TDZL,SYQLX,SCMJ,QLR,ZL,BDCQZH,FWYT,JZMJ,SFZX,SFDY,SFCF";
        int index = 0;
        for(String name : names.split(",")) {
            map.put(name, valuesArray[index++]);
        }
        return map;
    }

    private HashMap newDataMap2(String values) {
        String[] valuesArray = values.split(",");

        HashMap map = new HashMap();
        String names = "DJH,TDSYZMC,TDZL,SYQLX,SCMJ,QLR,ZL,BDCQZH,TDYT,SYQMJ,SFZX,SFDY,SFCF";
        int index = 0;
        for(String name : names.split(",")) {
            map.put(name, valuesArray[index++]);
        }
        return map;
    }

    /**
     * 添加标题行
     * @param newExcelData
     */
    private void addTitleRows(List<HashMap> newExcelData) {
        newExcelData.add(newDataMap(" , , , , , , , , , , , , ,单位：平方米"));
        newExcelData.add(newDataMap("地籍号,不动产单元号,调查表,调查表,调查表,调查表,登记信息,登记信息,登记信息,登记信息,登记信息,登记信息,限制信息,限制信息"));
        newExcelData.add(newDataMap("地籍号,不动产单元号,权利人,坐落,权利类型,宗地面积,权利人,坐落,不动产权证号,房屋用途,建筑面积,是否注销,是否抵押,是否查封"));
    }

    /**
     * 在Excel最后添加汇总面积内容
     * @param newExcelData 扩展后表格数据集合
     * @param mjMap 统计面积信息
     */
    private void addHzData(List<HashMap> newExcelData, Map<String, LinkedHashMap<String, BigDecimal>> mjMap) {
        if(MapUtils.isEmpty(mjMap)) {
            return;
        }

        newExcelData.add(newDataMap(" , , , , , , , , , , , , , "));
        newExcelData.add(newDataMap("其中：, , , , , , , , , , , , , "));

        // 所有宗地登记信息中户室建筑面积总和
        BigDecimal sum = new BigDecimal(0);

        List<HashMap> list = new ArrayList<>(100);
        for(Map.Entry<String, LinkedHashMap<String, BigDecimal>> entry : mjMap.entrySet()) {
            Map<String, BigDecimal> value = entry.getValue();
            if(MapUtils.isNotEmpty(value)) {
                for(Map.Entry<String, BigDecimal> entry2 : value.entrySet()) {
                    sum = sum.add(entry2.getValue());
                    list.add(newDataMap(entry.getKey() + "," + entry2.getKey()  +  "总建筑面积:" + entry2.getValue() + "平方米" + ", , , , , , , , , , , , , , "));
                }
            }
        }

        newExcelData.add(newDataMap("登记建筑面积总和," + sum  +  "平方米" + ", , , , , , , , , , , , , , "));
        newExcelData.addAll(list);
    }

    private Double doubleOf(Object obj, Double defaultVal) {
        return (obj == null) ? defaultVal : Double.valueOf(obj.toString());
    }
}
