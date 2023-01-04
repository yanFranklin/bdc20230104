package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.core.mapper.BdcYzhMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcZsMapper;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.BdcYzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhExcelDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhZtDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYzhFzlTjDTO;
import cn.gtmap.realestate.common.core.enums.BdcYzhZtEnum;
import cn.gtmap.realestate.common.core.enums.BdcZssyqkEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhSyqkQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYzhTjQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsyzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.rules.BdcGzQtYzFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 不动产印制号查询服务实现类
 */
@Service
public class BdcYzhServiceImpl implements BdcYzhService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYzhServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcYzhMapper bdcYzhMapper;
    @Autowired
    BdcZsMapper bdcZsMapper;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    BdcGzQtYzFeignService bdcGzQtYzFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcYzhScService bdcYzhScService;
    @Autowired
    BdcYzhSymxService bdcYzhSymxService;
    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    EntityService entityService;
    @Autowired
    BdcXtZsyzhFeignService bdcXtZsyzhFeignService;
    @Autowired
    BdcXmService bdcXmService;

    @Value("${zsbd.yzZszmQxdmAndUserQxdm:true}")
    private Boolean yzZszmQxdmAndUserQxdm;

    @Value("${xnyzh.hz:HF}")
    private String xnyzhHz ;

    /**
     * @param yzhid 印制号ID
     * @return BdcYzhDTO 印制号DTO类
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键查询印制号信息
     */
    @Override
    public BdcYzhDTO queryBdcYzhAndYzhmx(String yzhid) {
        if (StringUtils.isBlank(yzhid)) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        BdcYzhDTO bdcYzhDTO = new BdcYzhDTO();

        BdcYzhQO bdcYzhQO = new BdcYzhQO();
        bdcYzhQO.setYzhid(yzhid);
        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhMapper.queryBdcYzh(bdcYzhQO);

        if (CollectionUtils.isNotEmpty(bdcYzhDTOList) && null != bdcYzhDTOList.get(0)) {
            bdcYzhDTO = bdcYzhDTOList.get(0);
            List<BdcYzhsymxDO> bdcYzhsymxDOList = bdcYzhSymxService.queryBdcYzhsymx(bdcYzhDTO.getYzhid());
            bdcYzhDTO.setBdcYzhsymxDOList(bdcYzhsymxDOList);
        }
        return bdcYzhDTO;
    }



    /**
     * @param bdcYzhQO 印制号查询对象
     * @return List<BdcYzhDTO> 返回印制号DTOList
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询印制号
     */
    @Override
    public List<BdcYzhDTO> queryListBdcYzh(BdcYzhQO bdcYzhQO) {
        if (null == bdcYzhQO) {
            throw new MissingArgumentException("缺少查询参数");
        }
        // 当前配置 不验证 区县代码时  设置qxdm查询条件为空
        if(!yzZszmQxdmAndUserQxdm){
            bdcYzhQO.setQxdm(null);
        }
        return bdcYzhMapper.queryBdcYzh(bdcYzhQO);
    }

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO对象
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新不动产印制号使用情况, 生成印制号使用明细
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcYzhsymxDO updateBdcYzhSyqk(BdcYzhSyqkQO bdcYzhSyqkQO) {
        LOGGER.warn("----开始更新印制号{}", bdcYzhSyqkQO);
        if (null == bdcYzhSyqkQO || StringUtils.isBlank(bdcYzhSyqkQO.getYzhid())) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }
        // 先更新印制号的使用情况
        BdcYzhDO bdcYzhDO = new BdcYzhDO();
        bdcYzhDO.setYzhid(bdcYzhSyqkQO.getYzhid());
        bdcYzhDO.setSyqk(bdcYzhSyqkQO.getSyqk());
        bdcYzhDO.setZsid(bdcYzhSyqkQO.getZsid());
        bdcYzhDO.setSysj(new Date());
        // 还原印制号时，需要将印制号中的zsid置空
        if (CommonConstantUtils.SYQK_YLY.equals(bdcYzhSyqkQO.getSyqk())) {
            bdcYzhDO.setZsid(null);
        }

        int result = bdcYzhMapper.updateYzhSyqk(bdcYzhDO);
        LOGGER.warn("印制号主表更新数量{}", result);
        // 更新成功后执行
        if (result > 0) {
            // 将印制号更新到证书（注意，获取印制号时，qzysxlh应该是有值的。作废印制号、还原印制号时，qzysxlh是空的）
            if (StringUtils.isNotBlank(bdcYzhSyqkQO.getZsid())) {
                BdcZsDO bdcZsDO = new BdcZsDO();
                bdcZsDO.setZsid(bdcYzhSyqkQO.getZsid());
                bdcZsDO.setQzysxlh(bdcYzhSyqkQO.getQzysxlh());
                bdcZsMapper.updateBdcZsByZsid(bdcZsDO);
            }

            // 生成使用明细
            BdcYzhsymxDO bdcYzhsymxDO = bdcYzhSymxService.insertBdcYzhSymx(bdcYzhSyqkQO);
            LOGGER.warn("印制号更新成功，返回使用明细：{}", bdcYzhsymxDO);
            return bdcYzhsymxDO;
        }
        return null;
    }

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO对象
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新不动产印制号使用情况
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer revertYzhAndSyqk(BdcYzhSyqkQO bdcYzhSyqkQO) {
        LOGGER.info("更新印制号具体入参：{}", JSON.toJSONString(bdcYzhSyqkQO));
        // 判空
        if (StringUtils.isBlank(bdcYzhSyqkQO.getYzhid()) || null == bdcYzhSyqkQO.getSyqk()) {
            return 0;
        }
        // 先更新印制号的使用情况
        BdcYzhDO bdcYzhDO = new BdcYzhDO();
        bdcYzhDO.setYzhid(bdcYzhSyqkQO.getYzhid());
        bdcYzhDO.setSyqk(bdcYzhSyqkQO.getSyqk());
        bdcYzhDO.setZsid(bdcYzhSyqkQO.getZsid());
        // 还原印制号时，需要将印制号中的zsid置空
        if (CommonConstantUtils.SYQK_YLY.equals(bdcYzhSyqkQO.getSyqk())) {
            bdcYzhDO.setZsid(null);
        }

        int result = bdcYzhMapper.updateYzhSyqk(bdcYzhDO);

        // 更新成功后执行,这里需要删除点击获取印制号后，又手动修改印制号并点击保存
        // 原来的yzh使用明细需要删除
        if (result > 0 && StringUtils.isNotEmpty(bdcYzhDO.getYzhid())) {
            bdcYzhMapper.deleteYzhSymx(bdcYzhDO);
        }
        return result;
    }

    /**
     * @param zsid     证书ID
     * @param bdcYzhQO 印制号查询QO
     * @return BdcYzhDTO 印制号DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 为证书获取印制号并更新到证书
     */
    @Override
    public BdcYzhDTO queryBdcZsYzh(String zsid, BdcYzhQO bdcYzhQO) {
        if (StringUtils.isBlank(zsid) || null == bdcYzhQO) {
            throw new MissingArgumentException(messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
        }

        LOGGER.info("获取印制号查询入参为：{}",JSONObject.toJSONString(bdcYzhQO));
        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhScService.queryBatchYzh(1, bdcYzhQO);
        BdcYzhDTO bdcYzhDTO = CollectionUtils.isEmpty(bdcYzhDTOList) ? null : bdcYzhDTOList.get(0);

        // 更新印制号证书信息
        updateZsxxToYzh(zsid, bdcYzhQO, bdcYzhDTO, null);

        return bdcYzhDTO;
    }

    /**
     * @param zsid      证书ID
     * @param bdcYzhQO  印制号查询qo
     * @param bdcYzhDTO 获取到的印制号
     * @param slbh 受理编号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 证书获取印制号时，更新印制号证书信息
     */
    private void updateZsxxToYzh(String zsid, BdcYzhQO bdcYzhQO, BdcYzhDTO bdcYzhDTO, String slbh) {
        if (null != bdcYzhDTO && StringUtils.isNotBlank(bdcYzhDTO.getYzhid())) {
            String syyy = "证书印制号获取";

            BdcYzhSyqkQO bdcYzhSyqkQO = new BdcYzhSyqkQO();

            BeanUtils.copyProperties(bdcYzhDTO, bdcYzhSyqkQO);

            bdcYzhSyqkQO.setZsid(zsid);
            bdcYzhSyqkQO.setSlbh(slbh);
            bdcYzhSyqkQO.setSyqk(CommonConstantUtils.SYQK_YSY);
            bdcYzhSyqkQO.setSyyy(syyy);
            bdcYzhSyqkQO.setSyr(bdcYzhQO.getSyr());
            bdcYzhSyqkQO.setSyrid(bdcYzhQO.getSyrid());
            BdcYzhsymxDO bdcYzhsymxDO = updateBdcYzhSyqk(bdcYzhSyqkQO);

            List<BdcYzhsymxDO> bdcYzhsymxDOList = new ArrayList();
            bdcYzhsymxDOList.add(bdcYzhsymxDO);
            bdcYzhDTO.setBdcYzhsymxDOList(bdcYzhsymxDOList);

            return;
        }
        LOGGER.info("updateZsxxToYzh，获取到的印制号为空或yzhid为空！");
    }

    /**
     * @param bdcYzhQOList 印制号查询QOList
     * @return List<BdcYzhDTO> 印制号获取结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量获取证书印制号
     */
    @Override
    public List<BdcYzhDTO> queryBatchZsYzh(List<BdcYzhQO> bdcYzhQOList) {
        if (CollectionUtils.isEmpty(bdcYzhQOList)) {
            throw new MissingArgumentException("缺失印制号查询bdcYzhQOList");
        }
        List<BdcYzhDTO> resultList = new ArrayList();
        for (BdcYzhQO bdcYzhQO : bdcYzhQOList) {
            if (CollectionUtils.isNotEmpty(bdcYzhQO.getZsidList())) {
                List<String> zsidList = bdcYzhQO.getZsidList();
                if (CollectionUtils.isEmpty(zsidList)) {
                    throw new MissingArgumentException("获取印制号时，缺失zsidList的值！");
                }

                // 批量情况下，避免后期多次查询slbh这里将slbh作为参数传递
                String slbh = "";
                List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(zsidList.get(0));
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0)) {
                    slbh = (bdcXmDOList.get(0).getSlbh());
                }

                int yzhNum = CollectionUtils.size(bdcYzhQO.getZsidList());
                List<BdcYzhDTO> bdcYzhDTOList = bdcYzhScService.queryBatchYzh(yzhNum, bdcYzhQO);
                if (CollectionUtils.isNotEmpty(bdcYzhDTOList) && CollectionUtils.size(bdcYzhDTOList) == yzhNum) {
                    int index = 0;
                    for (BdcYzhDTO bdcYzhDTO : bdcYzhDTOList) {
                        // 更新印制号证书信息
                        updateZsxxToYzh(zsidList.get(index), bdcYzhQO, bdcYzhDTO, slbh);
                        index++;
                    }
                    resultList.addAll(bdcYzhDTOList);
                }
            }
        }
        return resultList;
    }

    /**
     * @param bdcYzhQO 印制号查询
     * @return BdcGzyzTsxxDTO 验证提示信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证印制号是否可用, 不可用返回null
     */
    @Override
    public BdcYzxxDTO queryYzhYzxx(BdcYzhQO bdcYzhQO) {
        // 当前配置 不验证 区县代码时  设置qxdm查询条件为空
        if(!yzZszmQxdmAndUserQxdm){
            bdcYzhQO.setQxdm(null);
        }
        // 验证印制号是否可用（根据条件查询，查询到则印制号可用，并返回查询到的印制号ID。未查询到时印制号不可用，返回值为null，）
        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhMapper.queryBdcYzh(bdcYzhQO);
        if (CollectionUtils.isNotEmpty(bdcYzhDTOList) && null != bdcYzhDTOList.get(0) && StringUtils.isNotBlank(bdcYzhDTOList.get(0).getYzhid())) {
            BdcYzxxDTO bdcYzxxDTO = new BdcYzxxDTO();
            bdcYzxxDTO.setYzxsjid(bdcYzhDTOList.get(0).getYzhid());
            bdcYzxxDTO.setTsxx("验证到印制号可用。");
            return bdcYzxxDTO;
        }
        return null;
    }

    @Override
    public BdcYzxxDTO queryYzhYztsxx(BdcYzhQO bdcYzhQO){
        if (StringUtils.isBlank(bdcYzhQO.getQxdm())) {
            throw new MissingArgumentException("缺失区县代码！");
        }
        if (StringUtils.isBlank(bdcYzhQO.getQzysxlh())) {
            throw new MissingArgumentException("没有权证印刷序列号！");
        }
        String lqfs = bdcXtZsyzhFeignService.getZsyzhLqfs(bdcYzhQO.getQxdm());
        yzhLqfsAndSetValue(bdcYzhQO, null, lqfs);

        BdcYzxxDTO bdcYzxxDTO = queryYzhYzxx(bdcYzhQO);
        if (null != bdcYzxxDTO) {
            return bdcYzxxDTO;
        }
        // 验证没有通过，则判断具体的没有通过的原因
        return generateYzhTsxx(bdcYzhQO, lqfs);

    }



    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qzysxlh 权证印刷序列号（印制号）
     * @param userName 用户账号
     * @description 验证某个用户将使用的印制号状态，例如是否可用
     */
    @Override
    public BdcYzhZtDTO checkYzhStatus(String qzysxlh, String userName) {
        if(StringUtils.isAnyBlank(qzysxlh, userName)) {
            return this.setResult(BdcYzhZtEnum.STATUS_0, "未传参数", qzysxlh, userName);
        }

        Example example = new Example(BdcYzhDO.class);
        example.createCriteria().andEqualTo("qzysxlh", qzysxlh);
        List<BdcYzhDO> bdcYzhDOList = entityMapper.selectByExampleNotNull(example);

        // 不存在印制号
        if(CollectionUtils.isEmpty(bdcYzhDOList) || null == bdcYzhDOList.get(0)) {
            return this.setResult(BdcYzhZtEnum.STATUS_1, "印制号不存在", qzysxlh, userName);
        }

        // 印制号已作废、遗失、销毁
        BdcYzhDO bdcYzhDO = bdcYzhDOList.get(0);
        if(BdcZssyqkEnum.ZF.getCode().equals(bdcYzhDO.getSyqk()) || BdcZssyqkEnum.YS.getCode().equals(bdcYzhDO.getSyqk()) || BdcZssyqkEnum.XH.getCode().equals(bdcYzhDO.getSyqk())) {
            return this.setResult(BdcYzhZtEnum.STATUS_1, "印制号已作废、遗失、销毁", qzysxlh, userName);
        }

        // 印制号已经被使用（这里直接根据印制号主表使用情况判断，不关联查询明细表，因为只要使用了主表的状态就应该是已使用、已打证状态）
        if(BdcZssyqkEnum.YSY.getCode().equals(bdcYzhDO.getSyqk()) || BdcZssyqkEnum.YDZ.equals(bdcYzhDO.getSyqk())) {
            return this.setResult(BdcYzhZtEnum.STATUS_2, "印制号已被使用", qzysxlh, userName);
        }

        // 印制号已领用，即初始未使用状态
        if(BdcZssyqkEnum.YLY.getCode().equals(bdcYzhDO.getSyqk())) {
            String lqrid = bdcYzhDO.getLqrid();
            if(StringUtils.isBlank(lqrid)) {
                return this.setResult(BdcYzhZtEnum.STATUS_3, "印制号记录没有领取人数据", qzysxlh, userName);
            }

            UserDto userDto = userManagerUtils.getUserByUserid(lqrid);
            if(null == userDto || StringUtils.isBlank(userDto.getUsername())) {
                return this.setResult(BdcYzhZtEnum.STATUS_3, "系统不存在印制号领取人对应的账户", qzysxlh, userName);
            }

            if(StringUtils.equals(userName, userDto.getUsername())) {
                // 印制号领取人是指定的人员
                return this.setResult(BdcYzhZtEnum.STATUS_4, "印制号领取人是指定的人员且是已领用状态，可使用", qzysxlh, userName);
            } else {
                return this.setResult(BdcYzhZtEnum.STATUS_3, "印制号领取人不是指定的人员", qzysxlh, userName);
            }
        }

        return this.setResult(BdcYzhZtEnum.STATUS_1, "印制号不存在或被删除", qzysxlh, userName);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param zsid 证书ID
     * @return {String} 虚拟印制号
     * @description 当领证方式为电子证照时，获取虚拟印制号
     */
    @Override
    public String getXnyzh(String processInsId, String zsid) {
        if(StringUtils.isBlank(processInsId) && StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("获取虚拟印制号参数为空");
        }

        // 先判断当前证书记录印制号是否存在，如果已经存在不管是正常印制号还是虚拟印制号都不新生成
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        bdcZsQO.setZsid(zsid);
        List<BdcZsDO> bdcZsDOList = bdcZsService.listBdcZs(bdcZsQO);

        if(CollectionUtils.isEmpty(bdcZsDOList) || null == bdcZsDOList.get(0)) {
            LOGGER.warn("获取虚拟印制号中止，未获取到证书记录，工作流实例ID{}，证书ID{}", processInsId, zsid);
            throw new AppException("未获取到证书记录");
        }

        // 如果zsid有值，说明是证书证明列表打开单个证书、证明页面；如果processInsId有值，说明是直接访问单个证书、证明页面；所以这里可以直接获取第一条记录
        BdcZsDO bdcZsDO = bdcZsDOList.get(0);
        if(StringUtils.isNotBlank(bdcZsDO.getQzysxlh())) {
            LOGGER.warn("获取虚拟印制号中止，当前证书记录已经存在印制号{}，工作流实例ID{}，证书ID{}", bdcZsDO.getQzysxlh(), processInsId, zsid);
            return bdcZsDO.getQzysxlh();
        }

        if (StringUtils.isBlank(bdcZsDOList.get(0).getZsid())) {
            LOGGER.warn("获取虚拟印制号中止，未获取到zsid，工作流实例ID{}，证书ID{}", processInsId, zsid);
            throw new AppException("未获取到zsid");
        }
        // 判断领证方式是否是电子证照
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmService.listBdcXmfbByZsid(bdcZsDOList.get(0).getZsid());
        if(CollectionUtils.isEmpty(bdcXmFbDOList) || null == bdcXmFbDOList.get(0)) {
            LOGGER.warn("获取虚拟印制号中止，未获取到项目附表记录，工作流实例ID{}", processInsId);
            return null;
        }
        if( ! CommonConstantUtils.LZFS_DZZZ.equals(bdcXmFbDOList.get(0).getLzfs())) {
            LOGGER.warn("获取虚拟印制号中止，领证方式不是电子证照，工作流实例ID{}，证书ID{}", processInsId, zsid);
            return null;
        }

        // 证书、证明需要分开编号，证明单页面不加此请求故不做过滤处理
        Integer lsh = bdcBhFeignService.queryLsh(CommonConstantUtils.BDC_XNYZH + bdcZsDO.getZslx(), "ALL");
        // 这里流水号位数直接写死7位，假设有一天达到序号超出7位数，会直接返回序号本身，不影响
        String xnyzh =  CommonConstantUtils.XZQH_AH + StringToolUtils.appendZero(String.valueOf(lsh), 7) + xnyzhHz;

        // 更新到库中记录
        BdcZsDO zs = new BdcZsDO();
        zs.setZsid(bdcZsDO.getZsid());
        zs.setQzysxlh(xnyzh);
        int count = entityMapper.updateByPrimaryKeySelective(zs);
        LOGGER.info("更新证书{}虚拟印制号为{}，更新记录数量：{}", zs.getZsid(), xnyzh, count);

        return xnyzh;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @description 清除虚拟印制号
     */
    @Override
    public int updateXnyzhToNull(String zsid) {
        if(StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("清除虚拟印制号参数为空");
        }

        int count = bdcYzhMapper.updateYzhToNull(zsid);
        LOGGER.info("清除虚拟印制号，更新记录数量：{}, 证书ID：{}", count, zsid);
        return count;
    }

    /**
     * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param bdcYzhQO 证书证明ID集合
     * @return: {String} 领证方式
     * @description 批量获取虚拟印制号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<BdcZsDO> getBatchXnyzh(String processInsId, BdcYzhQO bdcYzhQO) {
        if(StringUtils.isBlank(processInsId) && (null == bdcYzhQO || CollectionUtils.isEmpty(bdcYzhQO.getZsidList()))){
            throw new MissingArgumentException("批量获取虚拟印制号参数为空");
        }

        List<BdcZsDO> result = new ArrayList<>();
        if(null != bdcYzhQO && CollectionUtils.isNotEmpty(bdcYzhQO.getZsidList())) {
            // 1、场景1：选中部分数据
            for(String zsid : bdcYzhQO.getZsidList()) {
                this.resolveXnyzh(zsid, result);
            }
        } else {
            // 2、场景2：选中全部数据或默认全部数据
            // 2.1、获取所有证书记录
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(processInsId);
            List<BdcZsDO> bdcZsDOList = bdcZsService.listBdcZs(bdcZsQO);
            if(CollectionUtils.isEmpty(bdcZsDOList)) {
                return Collections.emptyList();
            }

            // 2.2、生成虚拟印制号
            for(BdcZsDO zsDO : bdcZsDOList) {
                if(null == zsDO || StringUtils.isBlank(zsDO.getZsid())) {
                    continue;
                }

                if(StringUtils.isNotBlank(zsDO.getQzysxlh())) {
                    LOGGER.warn("当前证书记录已经存在印制号{}，证书ID{}", zsDO.getQzysxlh(), zsDO.getZsid());
                    continue;
                }
                this.resolveXnyzh(zsDO.getZsid(), result);
            }
        }

        LOGGER.info("批量生成虚拟印制号：{}, 工作流实例ID：{}", result, processInsId);
        return result;
    }

    /**
     * 印制号废证量统计
     */
    @Override
    public List<BdcYzhFzlTjDTO> yzhFzlTj(BdcYzhTjQO bdcYzhTjQO) {
        List<BdcYzhFzlTjDTO> countList = this.bdcYzhMapper.yzhFzlTj(bdcYzhTjQO);
        List<String> nameList = countList.stream().map(BdcYzhFzlTjDTO::getSyr).distinct().filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.toList());
        // 统计人员领用量
        if(CollectionUtils.isNotEmpty(nameList)){
            String name = StringUtils.join(nameList, ",");
            bdcYzhTjQO.setRymc(name);
            List<BdcYzhFzlTjDTO> lylList = this.bdcYzhMapper.yzhLyTj(bdcYzhTjQO);
            Map<String, Integer> lylMap = lylList.stream().collect(Collectors.toMap(BdcYzhFzlTjDTO::getSyr,BdcYzhFzlTjDTO::getLyl));
            for(BdcYzhFzlTjDTO bdcYzhFzlTjDTO:countList){
                Integer lyl = lylMap.get(bdcYzhFzlTjDTO.getSyr());
                if(null != lyl){
                    bdcYzhFzlTjDTO.setLyl(lyl);
                }
            }
        }
        return countList;
    }

    /**
     * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @param zsDOList 结果证书信息集合
     * @description 获取指定证书证明的虚拟印制号
     */
    private void resolveXnyzh(String zsid, List<BdcZsDO> zsDOList) {
        String xnyzh = this.getXnyzh(null, zsid);
        if(StringUtils.isBlank(xnyzh)) {
            return;
        }

        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setZsid(zsid);
        bdcZsDO.setQzysxlh(xnyzh);
        zsDOList.add(bdcZsDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param status 印制号状态
     * @param info 原因
     * @param qzysxlh 印制号
     * @param userName 用户名
     * @description 处理日志和返回值信息
     */
    private BdcYzhZtDTO setResult(BdcYzhZtEnum status, String info, String qzysxlh, String userName) {
        LOGGER.info("核查印制号返回状态{}，原因：{}，目标验证印制号{}，用户名{}", status.getCode(), info, qzysxlh, userName);
        return new BdcYzhZtDTO(status);
    }

    /**
     * @param bdcYzhQO 印制号查询QO
     * @param userDto
     * @param lqfs
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 确认印制号的领取方式并修改参数值
     */
    private void yzhLqfsAndSetValue(BdcYzhQO bdcYzhQO, UserDto userDto, String lqfs) {
        if (null == userDto) {
            userDto = userManagerUtils.getCurrentUser();
        }
        if (null == userDto) {
            throw new UserInformationAccessException("未获取到当前用户信息！");
        }

        if (StringUtils.isBlank(lqfs)) {
            if (StringUtils.isBlank(bdcYzhQO.getQxdm())) {
                throw new MissingArgumentException("缺失区县代码！");
            }
            lqfs = bdcXtZsyzhFeignService.getZsyzhLqfs(bdcYzhQO.getQxdm());
        }

        if (StringUtils.isNotBlank(lqfs)) {
            // 按人员领取
            if (StringUtils.equals(lqfs, CommonConstantUtils.YZH_LQFS_RY)) {
                bdcYzhQO.setLqrid(userDto.getId());
            } else if (StringUtils.equals(lqfs, CommonConstantUtils.YZH_LQFS_BM)) {
                // 按领取部门领取
                if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                    throw new MissingArgumentException("系统所配印制号获取方式为：部门领取，但是当前用户没有所在部门的信息！");
                }
                bdcYzhQO.setLqbmid(userDto.getOrgRecordList().get(0).getId());
            }
        }
    }

    /**
     * @param bdcYzhQO 印制号查询QO
     * @param lqfs     领取方式
     * @return BdcYzxxDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证到印制号不可用时，封装具体的提示信息
     */
    private BdcYzxxDTO generateYzhTsxx(BdcYzhQO bdcYzhQO, String lqfs) {
        BdcYzxxDTO bdcYzxxDTO = new BdcYzxxDTO();
        // 不可用时返回null，判断不可用的原因
        BdcYzhQO bdcYzhQOTemp = new BdcYzhQO();
        bdcYzhQOTemp.setQzysxlh(bdcYzhQO.getQzysxlh());
        List<BdcYzhDTO> bdcYzhDTOList = queryListBdcYzh(bdcYzhQOTemp);

        if (CollectionUtils.isEmpty(bdcYzhDTOList)) {
            bdcYzxxDTO.setTsxx("验证失败，未查询到该权证印刷序列号");
            return bdcYzxxDTO;
        }
        if (CollectionUtils.size(bdcYzhDTOList) > 1) {
            bdcYzxxDTO.setTsxx("验证失败，查询到该权证印刷序列号有多个！");
            return bdcYzxxDTO;
        }
        for (BdcYzhDTO bdcYzhDTO : bdcYzhDTOList) {

            if(yzZszmQxdmAndUserQxdm){
                if (!StringUtils.equals(bdcYzhQO.getQxdm(), bdcYzhDTO.getQxdm())) {
                    bdcYzxxDTO.setTsxx("验证失败，区县代码不一致！");
                    return bdcYzxxDTO;
                }
            }

            if (!bdcYzhQO.getZslx().equals(bdcYzhDTO.getZslx())) {
                bdcYzxxDTO.setTsxx("验证失败，证书类型不一致！");
                return bdcYzxxDTO;
            }
            if (!CommonConstantUtils.SYQK_YLY.equals(bdcYzhDTO.getSyqk())) {
                bdcYzxxDTO.setTsxx("验证失败，该权证印刷序列号不可使用！");
                return bdcYzxxDTO;
            }
            if (StringUtils.equals(lqfs, CommonConstantUtils.YZH_LQFS_RY) && !StringUtils.equals(bdcYzhQO.getLqrid(),
                    bdcYzhDTO.getLqrid())) {
                bdcYzxxDTO.setTsxx("验证失败，领取人员不一致！");
            } else if (StringUtils.equals(lqfs, CommonConstantUtils.YZH_LQFS_BM) && !StringUtils.equals(bdcYzhQO
                    .getLqbmid(), bdcYzhDTO.getLqbmid())) {
                bdcYzxxDTO.setTsxx("验证失败，领取部门不一致！");
            }
        }
        return bdcYzxxDTO;
    }

    /**
     * @param yzhidList 印制号ID列表
     * @return List<BdcYzhExcelDTO> 不动产印制号及明细
     * @author <a href="mailto:hejian@gtmap.cn">hejain</a>
     * @description 批量获取印制号及使用明细
     */
    @Override
    public List<BdcYzhExcelDTO> queryBdcYzhSymx(List<String> yzhidList) {
        if(CollectionUtils.isEmpty(yzhidList)) {
            throw new MissingArgumentException("印制号ID列表参数为空");
        }

        List<BdcYzhExcelDTO> BdcYzhSymxList= new ArrayList<>();
        if(CollectionUtils.size(yzhidList) > 500) {
            List<List> lists = ListUtils.subList(yzhidList, 500);
            for(List subList : lists) {
                // 查询数据
                List<BdcYzhExcelDTO> yzhList = bdcYzhMapper.queryBdcYzhSymx(subList);
                if (CollectionUtils.isNotEmpty(yzhList)){
                    BdcYzhSymxList.addAll(yzhList);
                }
            }
        } else {
            // 查询数据
            List<BdcYzhExcelDTO> yzhList = bdcYzhMapper.queryBdcYzhSymx(yzhidList);
            if (CollectionUtils.isNotEmpty(yzhList)){
                BdcYzhSymxList.addAll(yzhList);
            }
        }

        return BdcYzhSymxList;
    }

}
