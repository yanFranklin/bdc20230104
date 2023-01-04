package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFgfDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfSsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfSsxxQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.service.BdcFgfService;
import cn.gtmap.realestate.register.service.BdcGzyzService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/13
 * @description 规则验证服务实现类
 */
@Service
public class BdcGzyzServiceImpl implements BdcGzyzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzyzServiceImpl.class);

    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    ExchangeInterfaceRestService exchangeInterfaceRestService;
    @Autowired
    BdcFgfService bdcFgfService;
    @Autowired
    BdcSwFeignService bdcSwFeignService;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;
    /**
     * @param gzlslid 工作流实例ID
     * @return List 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-房改房-是否允许办理查询
     */
    @Override
    public BdcGzyzVO checkFgfSfyxbl(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数gzlslid！");
        }

        List<BdcFgfDO> bdcFgfDOList = bdcFgfService.getLcFgfxm(gzlslid);
        if (CollectionUtils.isEmpty(bdcFgfDOList)) {
            LOGGER.warn("gzllsid{}未查询到房改房相关项目信息", gzlslid);
            return null;
        }

        BdcGzyzVO bdcGzyzVO = new BdcGzyzVO();
        for (BdcFgfDO bdcFgfDO : bdcFgfDOList) {
            if (StringUtils.isAnyBlank(bdcFgfDO.getSlbh(), bdcFgfDO.getXmid())) {
                throw new MissingArgumentException("缺失slbh或者xmid查询信息，不予查询");
            }
            LOGGER.warn("房改房查询参数：{}", bdcFgfDO.toString());
            try {
                Object result = exchangeInterfaceRestService.requestInterface("fgf_cxxwjg", bdcFgfDO);
                if (null == result) {
                    bdcGzyzVO.setTsxx("未返回状态");
                    LOGGER.info("{}房改房未查询到结果", gzlslid);
                } else {
                    LOGGER.info("{}房改房查询结果：{}", gzlslid, result.toString());

                    Map resultMap = (Map) result;
                    if (!resultMap.containsKey("yxcode") || !Constants.SUCCEED_CODE.equals(MapUtils.getIntValue(resultMap, "yxcode"))) {
                        bdcGzyzVO.setTsxx("未返回状态");
                    } else if (Constants.BYBL.equals(MapUtils.getString(resultMap, "yxdata"))) {
                        bdcGzyzVO.setTsxx("不可办理");
                    } else if (!Constants.KYBL.equals(MapUtils.getString(resultMap, "yxdata"))) {
                        // 其它所有非可办理状态都处理为未返回状态信息
                        bdcGzyzVO.setTsxx("未返回状态");
                    }
                }
            } catch (Exception e) {
                LOGGER.error("验证房改房接口异常！", e);
                bdcGzyzVO.setTsxx("未返回状态");
            } finally {
                if (StringUtils.isNotBlank(bdcGzyzVO.getTsxx())) {
                    return bdcGzyzVO;
                }
            }
        }

        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-税务-建设银行缴库入库状态（未入库，则补退）
     */
    @Override
    public BdcGzyzVO checkYhjkrk(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList) || CollectionUtils.size(bdcXmDOList) != 1) {
            return null;
        }
        String xmid = bdcXmDOList.get(0).getXmid();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            return null;
        }
        for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
            if (CommonConstantUtils.SF_S_DM.equals(bdcSlSfxxDO.getYhjkrkzt())) {
                return null;
            }
        }
        BdcSfSsxxQO bdcSfSsxxQO =new BdcSfSsxxQO();
        bdcSfSsxxQO.setXmid(xmid);
        bdcSfSsxxQO.setCxbz(CommonConstantUtils.SFSSXX_CXBZ_SF_SS);
        bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
        BdcSfSsxxDTO bdcSfSsxxDTO = bdcSlSfxxFeignService.queryBdcSfSsxxDTO(bdcSfSsxxQO);
        if (null == bdcSfSsxxDTO || (CollectionUtils.size(bdcSfSsxxDTO.getBdcSfxxDTOS()) == 0 && CollectionUtils.size(bdcSfSsxxDTO.getBdcSwxxDTOS()) == 0)) {
            bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
            bdcSfSsxxDTO = bdcSlSfxxFeignService.queryBdcSfSsxxDTO(bdcSfSsxxQO);
        }
        if (null == bdcSfSsxxDTO || (CollectionUtils.size(bdcSfSsxxDTO.getBdcSfxxDTOS()) == 0 && CollectionUtils.size(bdcSfSsxxDTO.getBdcSwxxDTOS()) == 0)) {
            return null;
        }
        try {
            Object response = bdcSwFeignService.yhjkrk(bdcSfSsxxDTO);
            if (null != response) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(response));
                if (jsonObject.containsKey("yhjkrkzt") && CommonConstantUtils.SF_S_DM.equals(jsonObject.getInteger("yhjkrkzt"))) {
                    return null;
                }
            }
        } catch (Exception e) {
            LOGGER.error("推送银行缴库入库接口异常！", e.getMessage());
        }
        BdcGzyzVO bdcGzyzVO = new BdcGzyzVO();
        bdcGzyzVO.setTsxx("建设银行缴库入库未推送成功！");
        return bdcGzyzVO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 返回验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证税费缴库入库状态
     */
    @Override
    public BdcGzyzVO checkSfjkrk(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.warn("缺失参数gzlslid！");
            return null;
        }
        // 设置为0，不允许登簿
        Integer result = 0;
        try {
            // 调用受理接口，获取税费缴库入库情况。{-1:线下缴费（可登簿），0:失败(不可登簿)，1：成功（可登簿），2：页面给予confirm提示，可忽略继续登簿}
            result = bdcSlSfxxFeignService.checkJfqk(gzlslid);
            LOGGER.warn("gzlslid-{}查询缴库入库结果：{}", gzlslid, result);
        } catch (Exception e) {
            LOGGER.error("验证缴库入库接口异常：{}", e.getMessage(), e);
        } finally {
            BdcGzyzVO bdcGzyzVO = new BdcGzyzVO();
            bdcGzyzVO.setTsxx(result.toString());
            return bdcGzyzVO;
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证线下缴费是否已上传税费缴纳凭证
     */
    @Override
    public BdcGzyzVO checkXxJfPz(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.warn("缺失参数gzlslid！");
            return null;
        }
        // 设置为0，不允许登簿
        Integer result = 0;
        try {
            // 调用受理接口，获取线下缴费是否已上传税费缴纳凭证。Integer {@code 0} 未上传凭证 {@code 1} 已上传凭证 {@code 2} 线上缴费，不做验证
            result = bdcSlSfxxFeignService.checkXxJfPz(gzlslid);
            LOGGER.warn("gzlslid-{}线下缴费是否已上传税费缴纳凭证结果：{}", gzlslid, result);
        } catch (Exception e) {
            LOGGER.error("验证线下缴费是否已上传税费缴纳凭证接口异常：{}", e.getMessage(), e);
        } finally {
            BdcGzyzVO bdcGzyzVO = new BdcGzyzVO();
            bdcGzyzVO.setTsxx(result.toString());
            return bdcGzyzVO;
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果信息 {@code 0} 不通过  {@code 1} 通过
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 验证抵押权首次登记的抵押权人是否全部是签约银行 (必须都是签约银行，才返回结果1)
     */
    @Override
    public Map<String, String> checkSfxyjg(String gzlslid) {
        Map<String, String> result = new HashMap<>();
        result.put("code","0");
        if (StringUtils.isBlank(gzlslid)) {
            result.put("msg","工作流实例ID为空！");
            return result;
        }
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            result.put("msg","未获取到项目信息！");
            return result;
        }
        String xmid = bdcXmDOList.get(0).getXmid();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            result.put("msg","未获取到权利人信息！");
            return result;
        }
        List<String> jgmcList = bdcQlrDOList.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.toList());
        List<BdcXtJgDO> bdcXtJgList = bdcXtJgFeignService.queryBatchBdcXtJg(jgmcList);
        if(CollectionUtils.isEmpty(bdcXtJgList)){
            result.put("code","0");
            result.put("msg","校验不通过！");
            LOGGER.info("gzlslid-{}验证抵押权首次登记的抵押权人是否全部是签约银行：{}", gzlslid, result);
            return result;
        }
        bdcXtJgList = bdcXtJgList.stream().filter(bdcXtJgDO -> bdcXtJgDO.getSfxyjg() == null || bdcXtJgDO.getSfxyjg() == 0).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcXtJgList)){
            result.put("code","1");
            result.put("msg","校验通过！");
        }else{
            result.put("code","0");
            result.put("msg","校验不通过！");
        }
        LOGGER.info("gzlslid-{}验证抵押权首次登记的抵押权人是否全部是签约银行：{}", gzlslid, result);
        return result;
    }

    @Override
    public Map<String, String> checkZdzfzdbj(String gzlslid){
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.info("自动转发或自动办结验证缺失processInsId！");
            return null;
        }
        Map<String, String> result = new HashMap<>(1);
        try{
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            bdcGzYzQO.setZhbs("ZDZF_ZDBJ_YZ");
            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("gzlslid", gzlslid);
            bdcGzYzQO.setParamMap(paramMap);
            BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO = bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
            if(Objects.nonNull(bdcGzZhgzTsxxDTO)){
                result.put("code", "1");
            }else{
                result.put("code", "0");
            }
        }catch(Exception e){
            LOGGER.error("调用规则验证是否自动转发或自动办结失败。", e);
        }
        LOGGER.info("gzlslid:{}验证自动转发或自动办结结果:{}",gzlslid,result);
        return result;

    }
}
