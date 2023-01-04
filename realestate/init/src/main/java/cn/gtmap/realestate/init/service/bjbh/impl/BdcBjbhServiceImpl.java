package cn.gtmap.realestate.init.service.bjbh.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcDjxlSbdjfkCsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.bjxx.SbdjResquestBjxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.bjxx.SbdjResquestBjxxServiceInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.slxx.SbdjResquestSlxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.slxx.SbdjResquestSlxxServiceInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj.SbdjResquestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj.SbdjResquestSbjbxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj.SbdjResquestSbjbxxServiceInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestSbjbxxCallInfoDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcBjbhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcZwQhResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.HefeiZwFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.mapper.BdcDjxlSbdjfkCsGxMapper;
import cn.gtmap.realestate.init.core.mapper.BdcXmFbMapper;
import cn.gtmap.realestate.init.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.init.service.bjbh.AsyncService;
import cn.gtmap.realestate.init.service.bjbh.BdcBjbhService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/23
 * @description 合肥办件编号
 */
@Service
public class BdcBjbhServiceImpl implements BdcBjbhService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBjbhServiceImpl.class);

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private HefeiZwFeignService hefeiZwFeignService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    private BdcXmFbMapper bdcXmFbMapper;

    @Autowired
    private BdcDjxlSbdjfkCsGxMapper bdcDjxlSbdjfkCsGxMapper;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    /**
     * 合肥创建项目特殊处理,项目是否关联政务办件号
     */
    @Value("${heifeitscl.sfglbjbh:true}")
    private boolean sfglbjbh;

    @Autowired
    private Repository repository;

    @Override
    public Page<BdcBjbhDTO> listBjbhByPage(Pageable pageable, String bdcBjbhQOStr) {
        Map<String, Object> paramMap = PageUtils.pageableSort(pageable, JSONObject.parseObject(bdcBjbhQOStr, HashMap.class));
        Page<BdcBjbhDTO> result = repository.selectPaging("listPageBjbhByPage", paramMap, pageable);
        return result;
    }

    @Override
    public BdcZwQhResponseDTO takeNumber(String processInsId) {
        LOGGER.info("项目是否关联政务办件编号:{}", sfglbjbh);
        if (!sfglbjbh) {
            BdcZwQhResponseDTO responseDTO = new BdcZwQhResponseDTO();
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg("未配置关联政务办件编号");
            return responseDTO;
        }
        return asyncService.completableFutureTask(processInsId);
    }

    /**
     * 4.3.1 申报登记
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public BdcZwQhResponseDTO sbdj(String processInsId) {
        LOGGER.info("项目进行申报登记:gzlslid:{}", processInsId);
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.info("工作流实例为空, 无法进行政务申报!");
            throw new AppException("工作流实例为空, 无法进行政务申报!");
        }
        BdcZwQhResponseDTO bdcZwQhResponseDTO = new BdcZwQhResponseDTO();
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOList = bdcXmMapper.listBdcXm(bdcXmQO);
            //查询项目附表，获取qjgldm
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setGzlslid(processInsId);
            List<BdcXmFbDO> bdcXmFbDOS = bdcXmFbMapper.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("项目不能为空!");
            }
            if (CollectionUtils.isEmpty(bdcXmFbDOS)) {
                throw new AppException("项目附表不能为空!");
            }

            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            String djxl = bdcXmDO.getDjxl();
            //已判断不能为空，直接取值
            String qjgldm = bdcXmFbDOS.get(0).getQjgldm();
            if (StringUtils.isEmpty(djxl)) {
                throw new AppException("未获取到登记小类!");
            }
            BdcDjxlSbdjfkCsgxDO bdcDjxlSbdjfkCsgxDO = bdcDjxlSbdjfkCsGxMapper.querySbdjfkCsByDjxl(djxl, qjgldm);
            if (Objects.isNull(bdcDjxlSbdjfkCsgxDO)) {
                throw new AppException("根据当前登记小类:【" + djxl + "】未查询到BDC_DJXL_SBDJFK_CS_GX相关参数");
            }
            SbdjResquestSbjbxxServiceInfoDTO serviceInfoDTO = new SbdjResquestSbjbxxServiceInfoDTO();
            try {
                BeanUtils.copyProperties(serviceInfoDTO, bdcDjxlSbdjfkCsgxDO);
                serviceInfoDTO.setApplyFrom(bdcDjxlSbdjfkCsgxDO.getApplyFrom());
            } catch (Exception e) {
                LOGGER.error("---异常信息:{},异常堆栈:{},参数:{}", e.getMessage(), e, JSONObject.toJSONString(bdcDjxlSbdjfkCsgxDO));
            }
            String unId = UUIDGenerator.generate();
            serviceInfoDTO.setUnId(unId);
            //要求取权利人表xh=1的 权利人数据推送
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            LOGGER.info("申报登记接口查到的权利人数据为：{}", bdcQlrDOList.toString());
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
                serviceInfoDTO.setApplyName(bdcQlrDO.getQlrmc());
                serviceInfoDTO.setApplyCardType(null != bdcQlrDO.getZjzl() ? bdcQlrDO.getZjzl().toString() : null);
                serviceInfoDTO.setApplyCardNumber(bdcQlrDO.getZjh());
                serviceInfoDTO.setContactMan(bdcQlrDO.getQlrmc());
                //联系电话就是个人取个人，企业就是代理人电话
                if (CommonConstantUtils.QLRLX_GR.equals(bdcQlrDO.getQlrlx())) {
                    serviceInfoDTO.setTelphone(bdcQlrDO.getDh());
                } else if (CommonConstantUtils.QLRLX_QY.equals(bdcQlrDO.getQlrlx())) {
                    serviceInfoDTO.setTelphone(bdcQlrDO.getDlrdh());

                }

            }
            serviceInfoDTO.setProjectName(bdcDjxlSbdjfkCsgxDO.getServiceName());
            serviceInfoDTO.setProjPwd("000000");
            serviceInfoDTO.setDataState("1");
            //办件编号取项目表中，已经取到编号的Zfxzspbh
            serviceInfoDTO.setProjId(bdcXmDO.getZfxzspbh());

            SbdjResquestSbjbxxDTO resquestSbjbxxDTO = new SbdjResquestSbjbxxDTO();
            resquestSbjbxxDTO.setServiceInfoDTO(serviceInfoDTO);

            SbdjResquestDTO resquestDTO = new SbdjResquestDTO();
            resquestDTO.setSbjbxxDTO(resquestSbjbxxDTO);
            LOGGER.info("---申报登记接口入参:{}", JSONObject.toJSONString(resquestSbjbxxDTO));
            SbdjfkResponseDTO responseDTO = hefeiZwFeignService.sbdj(resquestDTO);
            if (Objects.isNull(responseDTO) || Objects.isNull(responseDTO.getData())) {
                LOGGER.info("---申报登记反馈登记响应结果:{}", JSONObject.toJSONString(responseDTO));
                throw new AppException("申报登记登记接口响应为空!");
            }
            if (StringUtils.equals("01", responseDTO.getResult())) {

                bdcZwQhResponseDTO.setSuccess(true);
                bdcZwQhResponseDTO.setErrorMsg(responseDTO.getResultmsg());
                return bdcZwQhResponseDTO;
            } else {
                throw new AppException(responseDTO.getResultmsg());
            }
        } catch (Exception e) {
            LOGGER.error("---项目申报登记信息异常:{},异常gzlslid:{}", e.getMessage(), processInsId);
            bdcZwQhResponseDTO.setErrorMsg(e.getMessage());
            bdcZwQhResponseDTO.setSuccess(false);
            return bdcZwQhResponseDTO;
        }
    }

    /**
     * 4.3.3 申报登记_受理或删除信息推送
     *
     * @param processInsId
     * @param blzt
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public SbdjfkResponseDTO sbdjSlxx(String processInsId, String blzt) {
        LOGGER.info("项目进行申报信息推送，办理节点：{}:gzlslid:{}", blzt, processInsId);
        //如果为空，默认推送受理节点信息
        if (StringUtils.isBlank(blzt)) {
            blzt = Constants.ZWSB_SL;
        }
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.info("工作流实例为空, 无法进行政务申报!");
            throw new AppException("工作流实例为空, 无法进行政务申报!");
        }
        SbdjfkResponseDTO responseDTO = new SbdjfkResponseDTO();
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOList = bdcXmMapper.listBdcXm(bdcXmQO);
            //查询项目附表，获取qjgldm
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setGzlslid(processInsId);
            List<BdcXmFbDO> bdcXmFbDOS = bdcXmFbMapper.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("项目不能为空!");
            }
            if (CollectionUtils.isEmpty(bdcXmFbDOS)) {
                throw new AppException("项目附表不能为空!");
            }
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            String djxl = bdcXmDO.getDjxl();
            //已判断不能为空，直接取值
            String qjgldm = bdcXmFbDOS.get(0).getQjgldm();
            if (StringUtils.isEmpty(djxl)) {
                throw new AppException("未获取到登记小类!");
            }
            BdcDjxlSbdjfkCsgxDO bdcDjxlSbdjfkCsgxDO = bdcDjxlSbdjfkCsGxMapper.querySbdjfkCsByDjxl(djxl, qjgldm);
            if (Objects.isNull(bdcDjxlSbdjfkCsgxDO)) {
                throw new AppException("根据当前登记小类:【" + djxl + "】未查询到BDC_DJXL_SBDJFK_CS_GX相关参数");
            }
            SbdjResquestSlxxServiceInfoDTO slxxServiceInfoDTO = new SbdjResquestSlxxServiceInfoDTO();
            try {
                BeanUtils.copyProperties(slxxServiceInfoDTO, bdcDjxlSbdjfkCsgxDO);
            } catch (Exception e) {
                LOGGER.error("---异常信息:{},异常堆栈:{},参数:{}", e.getMessage(), e, JSONObject.toJSONString(bdcDjxlSbdjfkCsgxDO));
            }
            String unId = UUIDGenerator.generate();
            slxxServiceInfoDTO.setUnId(unId);
            slxxServiceInfoDTO.setProjId(bdcXmDO.getZfxzspbh());
            slxxServiceInfoDTO.setAcceptUsername(bdcXmDO.getSlr());
            //根据用户id查用户信息
            UserDto userDto = userManagerUtils.getUserByUserid(bdcXmDO.getSlrid());
            if (null == userDto || CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                LOGGER.info("查不到该用户信息和组织信息：userid{}", bdcXmDO.getSlrid());
                throw new UserInformationAccessException("查不到该用户信息！");
            }
            slxxServiceInfoDTO.setAcceptDeptname(userDto.getOrgRecordList().get(0).getName());
            slxxServiceInfoDTO.setAcceptDeptid(bdcDjxlSbdjfkCsgxDO.getDeptId());
            slxxServiceInfoDTO.setAcceptAreacode(userDto.getOrgRecordList().get(0).getRegionCode());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bdcXmDO.getSlsj());
            calendar.add(Calendar.MINUTE, +1);
            Date date = calendar.getTime();
            slxxServiceInfoDTO.setAcceptTime(null != date ? DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss") : "");
            slxxServiceInfoDTO.setAcceptDocument(bdcXmDO.getSlbh());
            if (Constants.ZWSB_SL.equals(blzt)) {
                slxxServiceInfoDTO.setAcceptResultCode(Constants.ZWSB_SL);
                slxxServiceInfoDTO.setRemark("受理");
            } else {
                slxxServiceInfoDTO.setAcceptResultCode(Constants.ZWSB_SC);
                slxxServiceInfoDTO.setRemark("不予受理");
            }
            SbdjResquestSlxxDTO slxxDTO = new SbdjResquestSlxxDTO();
            slxxDTO.setServiceInfoDTO(slxxServiceInfoDTO);
            SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
            callInfoDTO.setCallBackUrl("");
            slxxDTO.setCallInfoDTO(callInfoDTO);
            LOGGER.info("---申报受理接口入参:{}", JSONObject.toJSONString(slxxDTO));
            responseDTO = hefeiZwFeignService.sbdjSlxx(slxxDTO);
            if (Objects.isNull(responseDTO)) {
                LOGGER.info("---申报登记反馈登记响应结果:{}", JSONObject.toJSONString(responseDTO));
                throw new AppException("申报登记登记接口响应为空!");
            }
        } catch (Exception e) {
            LOGGER.error("---项目申报登记信息异常:{},异常gzlslid:{}", e.getMessage(), processInsId);
            return responseDTO;
        }


        return responseDTO;
    }

    /**
     * 4.3.8	办结
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public SbdjfkResponseDTO sbdjBjxx(String processInsId) {
        LOGGER.info("项目进行申报信息办结推送，办结节点gzlslid:{}", processInsId);
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.info("工作流实例为空, 无法进行政务申报!");
            throw new AppException("工作流实例为空, 无法进行政务申报!");
        }
        SbdjfkResponseDTO responseDTO = new SbdjfkResponseDTO();
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOList = bdcXmMapper.listBdcXm(bdcXmQO);
            //查询项目附表，获取qjgldm
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setGzlslid(processInsId);
            List<BdcXmFbDO> bdcXmFbDOS = bdcXmFbMapper.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("项目不能为空!");
            }
            if (CollectionUtils.isEmpty(bdcXmFbDOS)) {
                throw new AppException("项目附表不能为空!");
            }
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            String djxl = bdcXmDO.getDjxl();
            //已判断不能为空，直接取值
            String qjgldm = bdcXmFbDOS.get(0).getQjgldm();
            if (StringUtils.isEmpty(djxl)) {
                throw new AppException("未获取到登记小类!");
            }
            BdcDjxlSbdjfkCsgxDO bdcDjxlSbdjfkCsgxDO = bdcDjxlSbdjfkCsGxMapper.querySbdjfkCsByDjxl(djxl, qjgldm);
            if (Objects.isNull(bdcDjxlSbdjfkCsgxDO)) {
                throw new AppException("根据当前登记小类:【" + djxl + "】未查询到BDC_DJXL_SBDJFK_CS_GX相关参数");
            }
            SbdjResquestBjxxServiceInfoDTO bjxxServiceInfoDTO = new SbdjResquestBjxxServiceInfoDTO();
            try {
                BeanUtils.copyProperties(bjxxServiceInfoDTO, bdcDjxlSbdjfkCsgxDO);
            } catch (Exception e) {
                LOGGER.error("---异常信息:{},异常堆栈:{},参数:{}", e.getMessage(), e, JSONObject.toJSONString(bdcDjxlSbdjfkCsgxDO));
            }
            String unId = UUIDGenerator.generate();
            bjxxServiceInfoDTO.setUnId(unId);
            bjxxServiceInfoDTO.setProjId(bdcXmDO.getZfxzspbh());
            bjxxServiceInfoDTO.setTransactUsername(bdcXmDO.getDbr());
            //根据用户id查用户信息
            UserDto userDto = userManagerUtils.getUserByUserid(bdcXmDO.getSlrid());
            if (null == userDto || CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                LOGGER.info("查不到该用户信息和组织信息：userid{}", bdcXmDO.getSlrid());
                throw new UserInformationAccessException("查不到该用户信息！");
            }
            bjxxServiceInfoDTO.setTransactDeptname(userDto.getOrgRecordList().get(0).getName());
            bjxxServiceInfoDTO.setTransactDeptid(bdcDjxlSbdjfkCsgxDO.getDeptId());
            bjxxServiceInfoDTO.setTransactAreacode(userDto.getOrgRecordList().get(0).getRegionCode());
            bjxxServiceInfoDTO.setTransactTime(null != bdcXmDO.getDjsj() ? DateFormatUtils.format(bdcXmDO.getDjsj(), "yyyy-MM-dd HH:mm:ss") : "");
            bjxxServiceInfoDTO.setTransactResult("批准办结");
            bjxxServiceInfoDTO.setTransactResultCode("0501");
            bjxxServiceInfoDTO.setTransactDescribe("准予许可");
            if (StringUtils.isNotBlank(bdcXmDO.getBdcqzh())) {
                bjxxServiceInfoDTO.setResultIsneed(CommonConstantUtils.SF_S_DM.toString());
            } else {
                bjxxServiceInfoDTO.setResultIsneed(CommonConstantUtils.SF_F_DM.toString());

            }


            SbdjResquestBjxxDTO bjxxDTO = new SbdjResquestBjxxDTO();
            bjxxDTO.setServiceInfoDTO(bjxxServiceInfoDTO);
            SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
            callInfoDTO.setCallBackUrl("");
            bjxxDTO.setCallInfoDTO(callInfoDTO);
            LOGGER.info("---申报受理办结接口入参:{}", JSONObject.toJSONString(bjxxDTO));
            responseDTO = hefeiZwFeignService.sbdjBjxx(bjxxDTO);
            if (Objects.isNull(responseDTO)) {
                LOGGER.info("---申报登记办结登记响应结果:{}", JSONObject.toJSONString(responseDTO));
                throw new AppException("申报登记登记办结接口响应为空!");
            }
        } catch (Exception e) {
            LOGGER.error("---项目申报登记办结信息异常:{},异常gzlslid:{}", e, processInsId);
            return responseDTO;
        }


        return responseDTO;
    }
}
