package cn.gtmap.realestate.init.service.bjbh;

import cn.gtmap.realestate.common.core.domain.BdcDjxlSbdjfkCsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestSbjbxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestSbjbxxServiceInfoDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcZwQhResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.HefeiZwFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.mapper.BdcDjxlSbdjfkCsGxMapper;
import cn.gtmap.realestate.init.core.mapper.BdcXmFbMapper;
import cn.gtmap.realestate.init.core.mapper.BdcXmMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 异步任务
 *
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020-12-15
 * @description
 */
@Service
public class AsyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private HefeiZwFeignService hefeiZwFeignService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcXmFbMapper bdcXmFbMapper;

    @Autowired
    private BdcDjxlSbdjfkCsGxMapper bdcDjxlSbdjfkCsGxMapper;

    //    @Async
    @Transactional
    public BdcZwQhResponseDTO completableFutureTask(String processInsId) {
        LOGGER.info("start async task");
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.error("工作流实例为空, 无法关联政务办件编码!");
            throw new AppException("工作流实例为空, 无法关联政务办件编码!");
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
                throw new AppException("根据当前登记小类:【" + djxl + "】未查询到相关参数");
            }
            SbdjfkResquestSbjbxxServiceInfoDTO sbjbxxServiceInfoDTO = new SbdjfkResquestSbjbxxServiceInfoDTO();
            try {
                BeanUtils.copyProperties(sbjbxxServiceInfoDTO, bdcDjxlSbdjfkCsgxDO);
                sbjbxxServiceInfoDTO.setApplyFrom(bdcDjxlSbdjfkCsgxDO.getApplyFrom());
            } catch (Exception e) {
                LOGGER.error("---异常信息:{},异常堆栈:{},参数:{}", e.getMessage(), e, JSONObject.toJSONString(bdcDjxlSbdjfkCsgxDO));
            }

            String unId = UUIDGenerator.generate();
            sbjbxxServiceInfoDTO.setUnId(unId);
            sbjbxxServiceInfoDTO.setCreateUserCode(userManagerUtils.getCurrentUserName());
            //要求取权利人表xh=1的 权利人数据推送
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            LOGGER.info("申报登记反馈接口查到的权利人数据为：{}", bdcQlrDOList.toString());
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
                sbjbxxServiceInfoDTO.setApplyName(bdcQlrDO.getQlrmc());
                sbjbxxServiceInfoDTO.setApplyCardType(null != bdcQlrDO.getZjzl() ? bdcQlrDO.getZjzl().toString() : null);
                sbjbxxServiceInfoDTO.setApplyCardNumber(bdcQlrDO.getZjh());
                sbjbxxServiceInfoDTO.setContactMan(bdcQlrDO.getQlrmc());
                //联系电话就是个人取个人，企业就是代理人电话
                if (CommonConstantUtils.QLRLX_GR.equals(bdcQlrDO.getQlrlx())) {
                    sbjbxxServiceInfoDTO.setTelphone(bdcQlrDO.getDh());
                } else if (CommonConstantUtils.QLRLX_QY.equals(bdcQlrDO.getQlrlx())) {
                    sbjbxxServiceInfoDTO.setTelphone(bdcQlrDO.getDlrdh());

                }

            }

            sbjbxxServiceInfoDTO.setProjectName(bdcDjxlSbdjfkCsgxDO.getServiceName());

            SbdjfkResquestSbjbxxDTO sbjbxxDTO = new SbdjfkResquestSbjbxxDTO();
            sbjbxxDTO.setServiceInfoDTO(sbjbxxServiceInfoDTO);

            SbdjfkResquestDTO sbdjfkResquestDTO = new SbdjfkResquestDTO();
            sbdjfkResquestDTO.setSbjbxxDTO(sbjbxxDTO);

            LOGGER.info("---申报登记反馈登记接口入参:{}", JSONObject.toJSONString(sbdjfkResquestDTO));
            SbdjfkResponseDTO responseDTO = hefeiZwFeignService.sbdjfk(sbdjfkResquestDTO);
            if (Objects.isNull(responseDTO) || Objects.isNull(responseDTO.getData())) {
                throw new AppException("申报登记反馈登记接口响应为空!");
            }
            LOGGER.info("---申报登记反馈登记响应结果:{}", JSONObject.toJSONString(responseDTO));
            if (StringUtils.equals("01", responseDTO.getResult())) {
                List<BdcXmFbDO> bdcXmFbDOList = new ArrayList<>();
                bdcXmDOList.forEach(bdcXmDO1 -> {
                    BdcXmFbDO bdcXmFbDO = new BdcXmFbDO();
                    bdcXmDO1.setZfxzspbh(responseDTO.getData().getProjid());
                    bdcXmFbDO.setUnid(unId);
                    bdcXmFbDO.setXmid(bdcXmDO1.getXmid());
                    bdcXmFbDO.setGzlslid(processInsId);
                    bdcXmFbDOList.add(bdcXmFbDO);
                });
                bdcXmMapper.updateZfxzspbh(bdcXmDOList);
                bdcXmFbMapper.batchInsertMerge(bdcXmFbDOList);
                Map<String, Object> data = new HashMap<>(16);
                data.put("UNID", unId);
                data.put("bjbh", responseDTO.getData().getProjid());
                bdcZwQhResponseDTO.setData(data);
                bdcZwQhResponseDTO.setSuccess(true);
                return bdcZwQhResponseDTO;
            } else {
                throw new AppException(responseDTO.getResultmsg());
            }
        } catch (Exception e) {
            LOGGER.error("---合肥创建项目关联政务办件编码异常,异常信息:{},异常堆栈:{},异常受理编号:{}", e.getMessage(), e, processInsId);
            bdcZwQhResponseDTO.setErrorMsg(e.getMessage());
            bdcZwQhResponseDTO.setSuccess(false);
            return bdcZwQhResponseDTO;
        }
    }
}
