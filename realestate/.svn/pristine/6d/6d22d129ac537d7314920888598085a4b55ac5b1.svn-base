package cn.gtmap.realestate.exchange.service.impl.shucheng;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcYhUrlCsDO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.ScKlRanqiGhRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztUpdateHyDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.enums.BdcSdqRqfwbsmEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.config.SdqConfig;
import cn.gtmap.realestate.exchange.core.dto.shucheng.sq.ShuChengCommonDto;
import cn.gtmap.realestate.exchange.core.dto.shucheng.sq.request.ShuChengShuiCheckReqDto;
import cn.gtmap.realestate.exchange.core.dto.shucheng.sq.request.ShuChengShuiGhReqDto;
import cn.gtmap.realestate.exchange.core.dto.shucheng.sq.response.ShuChengShuiCheckResDto;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.shucheng.ShuChengSqdService;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/5/9
 * @description
 */
@Service
public class ShuChengSqdServiceImpl implements ShuChengSqdService {

    private static Logger LOGGER = LoggerFactory.getLogger(ShuChengSqdServiceImpl.class);

    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;


    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    SdqConfig sdqConfig;

    @Autowired
    ExchangeInterfaceRestService exchangeInterfaceRestService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    EntityMapper entityMapper;

    @Value("${shucheng.shui.checkUrl:}")
    private String checkUrl;

    @Value("${shucheng.shui.ghUrl:}")
    private String ghUrl;

    /**
     * 访问舒城水过户是否欠费的业务编码
     */
    private static final String SERVE_CODE_CHECK = "900011";
    /**
     * 访问舒城水过户的编业务码
     */
    private static final String SERVE_CODE_GH = "300011";

    /**
     *
     */
    private static final String VERSION = "hefei";

    /**
     * 科星供水类型
     */
    private static final String KXGSGHHANDLETYPE = "kxgsgh";
    private static final String KXGSJYHANDLETYPE = "kxgsjy";


    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户
     * @Date 2022/5/11 9:10
     **/
    @Override
    public void sgh(String processInsId, String userId, String qjgldm) {
        // 根据工作流id查询`不动产水电气过户信息表`，校验过户的数据和户号
        BdcSdqghDO bdcSdqghDO = getBdcSdqAndValid(processInsId, BdcSdqEnum.S);
        // 获取全部的权利人
        BdcQlrDO qlr = getQrl(processInsId, bdcSdqghDO);
        // 获取全部的业务人
        BdcQlrDO ywr = getYwl(processInsId, bdcSdqghDO);
        // 获取证件种类名称
        String qlrzjmc = getZjmc(qlr.getZjzl());
        String ywrzjmc = getZjmc(ywr.getZjzl());
        // 构建请求参数
        ShuChengCommonDto req = ShuChengCommonDto.buildHead(SERVE_CODE_GH);
        ShuChengShuiGhReqDto ghDto = new ShuChengShuiGhReqDto();

        // 构建请求参数  中的body
        ghDto.updateBodyNotData(bdcSdqghDO, qlr, ywr, ywrzjmc, qlrzjmc);
        updateDzzzxx(processInsId, ghDto);
        req.setBody(ghDto);
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
        });
        String requestUrl = "";
        if(StringUtils.isNotEmpty(qjgldm)){
            //从表中获取请求地址
            Example example = new Example(BdcYhUrlCsDO.class);
            example.createCriteria()
                    .andEqualTo("yhmc",qjgldm)
                    .andEqualTo("handlerType",KXGSGHHANDLETYPE)
                    .andEqualTo("version",VERSION);
            List<BdcYhUrlCsDO> bdcYhUrlCsDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcYhUrlCsDOList)){
                requestUrl = bdcYhUrlCsDOList.get(0).getYhurl();
            }else {
                LOGGER.info("科星水过户未找到对应的接口请求地址，请求参数为: {},{},{}", qjgldm,KXGSGHHANDLETYPE,VERSION);
                throw new AppException("科星水过户未找到对应的接口请求地址");
            }
        } else {
            //默认为舒城的地址（不用再去调整舒城现场的配置）
            requestUrl = ghUrl;
        }
        String str = httpClientUtils.sendPostRequest(requestUrl, paramMap, "舒城过户接口");
        JSONObject jsonObject = JSONObject.parseObject(str);
        if (!jsonObject.containsKey("body")) {
            LOGGER.info("舒城过户接口返回参数中不包含body参数，返回字符串为: {}", str);
            throw new AppException("舒城过户接口返回参数中不包含body参数，返回字符串为:" + str);
        }
        ShuChengShuiCheckResDto res = jsonObject.getObject("body", ShuChengShuiCheckResDto.class);
        updateSdqgh(bdcSdqghDO, BdcSdqEnum.S, res.getRtnCode());
    }

    /**
     * 处理登记簿
     * @param gzlslid
     * @param ghDto
     */
    private void updateDzzzxx(String gzlslid, ShuChengShuiGhReqDto ghDto) {
        CommonResponse<String> res = bdcSdqghFeignService.shuchengShuiDjbData(gzlslid);
        ghDto.updateData(res.getData(), "pdf");
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //检查舒城水过户是否欠费
     * @Date 2022/5/11 9:10
     **/
    @Override
    public CommonResponse<Boolean> sghjc(String processInsId, String userId, String qjgldm) {
        // 获取不动产项目信息并校验
//        getBdcXmAndValid(processInsId, BdcSdqEnum.S);
        // 构建请求参数
        ShuChengCommonDto req = ShuChengCommonDto.buildHead(SERVE_CODE_CHECK);
        ShuChengShuiCheckReqDto checkDto = new ShuChengShuiCheckReqDto();
        // 根据工作流id查询`不动产水电气过户信息表`，校验过户的数据和户号
        if (StringUtils.isEmpty(userId)) {
            BdcSdqghDO bdcSdqghDO = getBdcSdqAndValid(processInsId, BdcSdqEnum.S);
            checkDto.setUserID(bdcSdqghDO.getConsno());
        } else {
            checkDto.setUserID(userId);
        }
        req.setBody(checkDto);
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
        });
        String requestUrl = "";
        if(StringUtils.isNotEmpty(qjgldm)){
            //从表中获取请求地址
            Example example = new Example(BdcYhUrlCsDO.class);
            example.createCriteria()
                    .andEqualTo("yhmc",qjgldm)
                    .andEqualTo("handlerType",KXGSJYHANDLETYPE)
                    .andEqualTo("version",VERSION);
            List<BdcYhUrlCsDO> bdcYhUrlCsDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcYhUrlCsDOList)){
                requestUrl = bdcYhUrlCsDOList.get(0).getYhurl();
            }else {
                LOGGER.info("科星水过户未找到对应的接口请求地址，请求参数为: {},{},{}", qjgldm,KXGSJYHANDLETYPE,VERSION);
                throw new AppException("科星水过户未找到对应的接口请求地址");
            }
        } else {
            //默认为舒城的地址
            requestUrl = checkUrl;
        }
        String str = httpClientUtils.sendPostRequest(requestUrl, paramMap, "舒城查询欠费接口");
        JSONObject jsonObject = JSONObject.parseObject(str);
        if (!jsonObject.containsKey("body")) {
            LOGGER.info("舒城查询欠费接口返回参数中不包含body参数，body: {}", str);
            throw new AppException("舒城查询欠费接口返回参数中不包含body参数，body:" + str);
        }
        ShuChengShuiCheckResDto res = jsonObject.getObject("body", ShuChengShuiCheckResDto.class);
        if (!StrUtil.equals("0000", res.getRtnCode())) {
            LOGGER.info("舒城查询欠费接口返回状态码不为0000，body: {}", str);
            throw new AppException("未查询到信息，请核对后重新查询");
        }
        if (StrUtil.isNotEmpty(res.getTotalRcvblAmt())) {
            BigDecimal totalRcvblAmt = new BigDecimal(res.getTotalRcvblAmt());
            Boolean isFlag = totalRcvblAmt.compareTo(BigDecimal.ZERO) <= 0;
//            updateCheckInfo(bdcSdqghDO.getId(), isFlag, "");
            return CommonResponse.ok(isFlag);
        }
//        updateCheckInfo(bdcSdqghDO.getId(), false, "欠费");
        return CommonResponse.ok(false);
    }

    /**
     * @param processInsId
     * @Author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Description 舒城昆仑燃气过户-工作流事件
     * @Date 2022/7/11 9:10
     */
    @Override
    public void klRqgh(String processInsId) {
        // 根据工作流id查询`不动产水电气过户信息表`
        BdcSdqghDO bdcSdqghDO = getBdcSdqAndValid(processInsId, BdcSdqEnum.Q);
        // 舒城燃气过户有皖能和昆仑两种，如果是昆仑，调用
        if (bdcSdqghDO != null && !BdcSdqRqfwbsmEnum.KLRQ.key().equals(bdcSdqghDO.getRqfwbsm())) {
            LOGGER.info("舒城燃气过户，不属于昆仑燃气，工作流实例id:{}", processInsId);
            return;
        }
        //开始根据gzlslid,qlrmc,ywrmc查询权利人表组织相关数据
        ScKlRanqiGhRequestDTO klRanqiGhRequestDTO = new ScKlRanqiGhRequestDTO();
        /*Map<String, String> qlraramMap = new HashMap<>();
        qlraramMap.put("gzlslid", processInsId);
        qlraramMap.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        qlraramMap.put("qlrmc", bdcSdqghDO.getXhzmc());
        List<BdcQlrDO> qlrList = bdcdjMapper.queryQlrList(qlraramMap);
        if (CollectionUtils.isEmpty(qlrList)) {
            LOGGER.info("未查询到过户的权利人信息");
            throw new AppException("未查询到过户的权利人信息");
        }*/
        // 获取xhzmc的权利人
        BdcQlrDO qlr = getQrl(processInsId, bdcSdqghDO);
        // 获取xzmc的业务人
        BdcQlrDO ywr = getYwl(processInsId, bdcSdqghDO);
        klRanqiGhRequestDTO.setFromUame(ywr.getQlrmc());
        klRanqiGhRequestDTO.setFromUserhuhao(bdcSdqghDO.getConsno());
        klRanqiGhRequestDTO.setFromUserMobile(ywr.getDh());
        klRanqiGhRequestDTO.setFromUserSfid(ywr.getZjh());
        //权利人数据赋值
        klRanqiGhRequestDTO.setToUame(qlr.getQlrmc());
        klRanqiGhRequestDTO.setToUserMobile(qlr.getDh());
        klRanqiGhRequestDTO.setToUserSfid(qlr.getZjh());

        Object obj = exchangeInterfaceRestService.requestInterface("ranqi_kl_sqgh", klRanqiGhRequestDTO);
        if (null != obj) {
            LOGGER.info("推送舒城昆仑燃气数据返回：{}", obj.toString());
            // 更新blzt
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(obj));
            if (jsonObject.get("code") != null && "1".equals(jsonObject.get("code").toString())) {
                BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
                sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
                sdqBlztRequestDTO.setYwlx(BdcSdqEnum.Q.key());
                sdqBlztRequestDTO.setBlzt(3);
                bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
            }
        }


    }


    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取证件种类字典值
     * @Date 2022/5/26 11:23
     **/
    private String getZjmc(Integer zjzl) {
        if (Objects.isNull(zjzl)) {
            return "";
        }
        List<Map> zdMapList = bdcZdFeignService.queryBdcZd("zjzl");
        for(Map map : zdMapList){
            if(StringUtils.equals(zjzl.toString(), MapUtils.getString(map,"DM"))){
                return map.get("MC").toString();
            }
        }
        return "";
    }

   /**
    * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
    * @Description //更新状态
    * @Date 2022/5/11 9:13
    **/
    private void updateSdqgh(BdcSdqghDO bdcSdqghDO, BdcSdqEnum bdcSdqEnum, String retCode) {
        if (StrUtil.equals(CommonConstantUtils.SHUCHENG_RETURN_CODE_SUCCESS, retCode)) {
            //更新水电气表状态
            BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
            sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
            sdqBlztRequestDTO.setYwlx(bdcSdqEnum.key());
            sdqBlztRequestDTO.setBlzt(3);
            bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
        } else {
            updateFailRecord(bdcSdqghDO, bdcSdqEnum.key());
            throw new AppException("水过户失败！");
        }
    }


    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 更新核验信息
     * @Date 2022/4/28
     **/
    private void updateCheckInfo(String id, Boolean isFlag, String reason) {
        BdcSdqBlztUpdateHyDTO bdcSdqBlztUpdateHyDTO = new BdcSdqBlztUpdateHyDTO();
        bdcSdqBlztUpdateHyDTO.setId(id);
        bdcSdqBlztUpdateHyDTO.setHyjg(isFlag ? CommonConstantUtils.HYJG_SUCESS : CommonConstantUtils.HYJG_FAIL);
        bdcSdqBlztUpdateHyDTO.setHyxq(reason);
        bdcSdqghFeignService.updateSdqghhy(bdcSdqBlztUpdateHyDTO);
    }


    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户推送更新 bdcSdqgh
     * @Date 2022/5/11 9:13
     **/
    private void updateFailRecord(BdcSdqghDO bdcSdqghDO, Integer ywlx) {
        BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
        sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
        sdqBlztRequestDTO.setYwlx(ywlx);
        sdqBlztRequestDTO.setBlzt(4);
        if (Objects.nonNull(bdcSdqghDO.getTscs())) {
            sdqBlztRequestDTO.setTscs(bdcSdqghDO.getTscs() + 1);
        } else {
            sdqBlztRequestDTO.setTscs(1);
        }
        bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取类型获取过户号信息并校验
     * @Date 2022/5/11 9:14
     **/
    private BdcSdqghDO getBdcSdqAndValid(String processInsId, BdcSdqEnum ywlx) {
        BdcSdqywQO param = new BdcSdqywQO();
        param.setGzlslid(processInsId);
        param.setYwlx(ywlx.key());
        List<BdcSdqghDO> sdqghDOList = bdcSdqghFeignService.querySdqywOrder(param);
        if (CollectionUtils.isEmpty(sdqghDOList)) {
            LOGGER.info("未查询到需要{}过户的户号信息，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的户号信息!请检查！");
        }
        BdcSdqghDO bdcSdqghDO = sdqghDOList.get(0);
        if (StringUtils.isBlank(bdcSdqghDO.getConsno())) {
            LOGGER.info("未查询到需要{}过户的户号，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的户号!请检查！");
        }
        return bdcSdqghDO;
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取不动产项目信息并校验
     * @Date 2022/5/11 9:14
     **/
    private BdcXmDO getBdcXmAndValid(String processInsId, BdcSdqEnum ywlx) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.info("未查询到{}需要过户的项目信息，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的项目信息!请检查！");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        if (StringUtils.isEmpty(bdcXmDO.getBdcqzh())) {
            LOGGER.info("未查询到{}需要过户的不动产权证号，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的不动产权证号!请检查！");
        }
        return bdcXmDO;
    }



    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取工作流关联的所有权利人
     * @Date 2022/5/11 9:17
     **/
    private BdcQlrDO getQrl(String processInsId, BdcSdqghDO bdcSdqghDO) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> list = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(list)) {
            LOGGER.info("未查询到过户的权利人信息");
            throw new AppException("未查询到过户的权利人信息");
        }
        Boolean hasDefineNewName = StrUtil.isNotEmpty(bdcSdqghDO.getXhzmc());
        BdcQlrDO bdcQlrDO = hasDefineNewName ? list.stream()
                .filter(item -> StrUtil.equals(bdcSdqghDO.getXhzmc(), item.getQlrmc()))
                .findFirst().orElse(null) : list.get(0);
        if (Objects.isNull(bdcQlrDO)) {
            LOGGER.info("未查询到匹配的权利人信息，权利人列表：{}, 水电气过户表中旧用户名称", list, bdcSdqghDO.getXhzmc());
            throw new AppException("未查询到匹配的权利人信息");
        }
        return bdcQlrDO;
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取工作流关联的所有义务人
     * @Date 2022/5/11 9:17
     **/
    private BdcQlrDO getYwl(String processInsId, BdcSdqghDO bdcSdqghDO) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_YWR);
        List<BdcQlrDO> list = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(list)) {
            LOGGER.info("未查询到过户的义务人信息");
            throw new AppException("未查询到过户的义务人信息");
        }
        Boolean hasDefineNewName = StrUtil.isNotEmpty(bdcSdqghDO.getXhzmc());
        BdcQlrDO bdcQlrDO = hasDefineNewName ? list.stream()
                .filter(item -> StrUtil.equals(bdcSdqghDO.getHzmc(), item.getQlrmc()))
                .findFirst().orElse(null) : list.get(0);
        if (Objects.isNull(bdcQlrDO)) {
            LOGGER.info("未查询到匹配的义务人信息，义务人列表：{}, 水电气过户表中旧用户名称", list, bdcSdqghDO.getHzmc());
            throw new AppException("未查询到匹配的义务人信息");
        }
        return bdcQlrDO;
    }
}
