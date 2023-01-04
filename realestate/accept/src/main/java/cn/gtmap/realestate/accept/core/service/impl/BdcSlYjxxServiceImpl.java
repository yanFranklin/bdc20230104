package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.config.BdcYjxxConfig;
import cn.gtmap.realestate.accept.core.mapper.BdcSlYjxxMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlYjxxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlYjxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description 不动产受理邮寄信息接口实现类
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/12.
 */
@Service
public class BdcSlYjxxServiceImpl implements BdcSlYjxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlYjxxServiceImpl.class);

    /**
     * EMS接口失败返回状态码
     */
    public final static String EMS_FAIL_CODE = "F";

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcYjxxConfig bdcYjxxConfig;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private BdcSlYjxxMapper bdcSlYjxxMapper;

    @Resource(name = "dozerSameNullFMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    /**
     * 新增不动产受理邮寄信息
     * @param bdcSlYjxxDO 不动产受理邮寄信息DO
     * @return {@link BdcSlYjxxDO} 邮寄信息DO
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public BdcSlYjxxDO insertBdcSlYjxx(BdcSlYjxxDO bdcSlYjxxDO) {
        if(Objects.nonNull(bdcSlYjxxDO)){
            if(StringUtils.isBlank(bdcSlYjxxDO.getYjxxid())){
                bdcSlYjxxDO.setYjxxid(UUIDGenerator.generate());
            }
            entityMapper.insertSelective(bdcSlYjxxDO);
        }
        return bdcSlYjxxDO;
    }

    /**
     * 根据邮件信息ID修改不动产受理邮寄信息
     * <p> 根据邮件信息ID更新不动产受理邮寄信息，其中不更新字段为空和｛@code null｝的值 <br>
     *
     * @param bdcSlYjxxDO 不动产受理邮寄信息DO
     * @return void 无返回值
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void updateBdcSlYjxxByYjxxId(BdcSlYjxxDO bdcSlYjxxDO) {
        if(Objects.isNull(bdcSlYjxxDO) || StringUtils.isBlank(bdcSlYjxxDO.getYjxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "更新邮件信息时，缺失参数信息。");
        }
        entityMapper.updateByPrimaryKeySelective(bdcSlYjxxDO);
    }

    /**
     * 更新不动产受理邮寄信息
     *
     * @param json 不动产邮寄信息
     * @return 修改数量
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public Integer updateBdcYjxx(String json) {
        return this.entityService.updateByJsonEntity(json, BdcSlYjxxDO.class);
    }

    /**
     * 根据邮件信息ID删除不动产受理邮寄信息
     * @param yjxxId 不动产受理邮件信息ID
     * @return void 无返回值
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void removeYjxxByYjxxId(String yjxxId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(yjxxId),"查询邮寄信息时，缺失参数邮寄信息ID！");
        entityMapper.deleteByPrimaryKey(BdcSlYjxxDO.class, yjxxId);
    }

    /**
     * 根据流程实例ID信息删除
     * @param gzlslid 工作流实例ID
     * @return void 无返回值
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void removeYjxxByGzlslid(String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"查询邮寄信息时，缺失参数工作流实例ID！");
        Example example = new Example(BdcSlYjxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", gzlslid);
        entityMapper.deleteByExample(example);
    }

    /**
     * 根据工作流实例id查询不动产受理邮寄信息
     * @param gzlslid 工作流程实例ID
     * @return List<BdcSlYjxxDO> 不动产受理邮寄信息集合
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public List<BdcSlYjxxDO> queryYjxxByGzlslid(String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid),"查询邮寄信息时，缺失参数工作流实例ID！");
        Example example = new Example(BdcSlYjxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", gzlslid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcSlYjxxDO> listBdcSlYjxx(BdcSlYjxxQO bdcSlYjxxQO) {
        if (!CheckParameter.checkAnyParameter(bdcSlYjxxQO, "yjxxid",  "slbh", "gzlslid")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER) + JSONObject.toJSONString(bdcSlYjxxQO));
        }
        return bdcSlYjxxMapper.listBdcSlYjxx(bdcSlYjxxQO);
    }

    @Override
    public BdcSlYjxxDO queryYjxxByYjxxId(String yjxxId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(yjxxId),"查询邮寄信息时，缺失参数邮寄信息ID！");
        return entityMapper.selectByPrimaryKey(BdcSlYjxxDO.class, yjxxId);
    }

    /**
     * 初始化不动产邮寄信息
     * <p> 初始化不动产邮寄信息，当收件人不为空时，通过工作流实例ID获取权利人信息，多个权利人时，默认采用第一权利人。
     *
     * @param gzlslid 工作流实例ID
     * @return BdcSlYjxxDO 不动产邮寄信息DO
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public BdcSlYjxxDO initBdcYjxx(String gzlslid) {
        final List<BdcSlYjxxDO> list = this.queryYjxxByGzlslid(gzlslid);
        BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
        if(CollectionUtils.isNotEmpty(list)){
            bdcSlYjxxDO = list.get(0);
        }
        initYjxx(bdcSlYjxxDO, gzlslid);
        return bdcSlYjxxDO;
    }

    @Override
    public void initBdcYjxx(BdcSlYjxxDO bdcSlYjxxDO) {
        try{
            if (StringUtils.isNoneBlank(bdcSlYjxxDO.getGzlslid())) {
                // 获取配置的收件人邮编与寄件人信息
                initJjrxx(bdcSlYjxxDO, bdcSlYjxxDO.getQxdm());
                if (StringUtils.isBlank(bdcSlYjxxDO.getSjryb())) {
                    bdcSlYjxxDO.setSjryb(Optional.ofNullable(bdcYjxxConfig.getSjryb()).orElse(BdcYjxxConfig.DEFAULT_YB));
                }
                this.insertBdcSlYjxx(bdcSlYjxxDO);
            }
        }catch(Exception e){
            LOGGER.error(String.format("初始化邮寄信息失败，%s", e.getMessage()), e);
        }
    }

    @Override
    public String pushYjxxToEms(String processInsId,String currentUserName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(processInsId),"推送邮寄信息失败，缺失参数工作流实例ID！");
        // 判断领证方式
        Integer lzfs = this.bdcLzrFeignService.getLzfsByGzlslid(processInsId);
        if(!CommonConstantUtils.LZFS_EMS.equals(lzfs)){
            LOGGER.info("processInsId:{},lzfs:{}领证方式不为EMS邮递，无法推送。",processInsId,lzfs);
            return this.getJson(false, "领证方式不为EMS邮递，无法推送。");
        }
        //判断物流单号是否为空
        List<BdcSlYjxxDO> bdcSlYjxxDOList = this.queryYjxxByGzlslid(processInsId);
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(bdcSlYjxxDOList),"未获取邮寄信息。");
        BdcSlYjxxDO bdcSlYjxxDO = bdcSlYjxxDOList.get(0);
        if(StringUtils.isNotBlank(bdcSlYjxxDO.getWlydh())){
            LOGGER.info("物流运单号不为空，无法进行推送。");
            return this.getJson(false,"物流运单号不为空，无法进行推送。");
        }
        if(BdcSlYjxxDTO.PUSHED.equals(bdcSlYjxxDO.getTszt())){
            LOGGER.info("已推送至EMS，请勿重新推送。");
            return this.getJson(false,"已推送至EMS，请勿重新推送。");
        }
        List<Map<String, String>> resultList = getYjxxPushedMsg(bdcSlYjxxDO,currentUserName);
        if(CollectionUtils.isEmpty(resultList)){
            throw new AppException("推送EMS失败，调用emsddjr接口返回值为空。");
        }
        Map<String,String> map = resultList.get(0);
        if(EMS_FAIL_CODE.equals(map.get("success"))){
            throw new AppException("推送EMS失败，"+ map.get("errorMsg"));
        }
        modifyPushState(bdcSlYjxxDO, map.get("txLogisticID"), map.get("mailNo"));
        return this.getJson(true, "推送成功");
    }

    private String getJson(boolean result, String message){
        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("result", result);
        resultMap.put("message", message);
        return JSON.toJSONString(resultMap);
    }

    /**
     * 获取推送邮寄信息返回值
     */
    private List<Map<String, String>> getYjxxPushedMsg(BdcSlYjxxDO bdcSlYjxxDO,String currentUserName){
        // 封装EMS推送接口参数，调用EMS推送接口
        Map<String,Object> paramMap = new HashMap<>();
        // 处理页面未获取到受理编号的情况
        String gzlslid =  bdcSlYjxxDO.getGzlslid();
        if(StringUtils.isBlank(bdcSlYjxxDO.getSlbh())){
            paramMap.put("slbh", gzlslid);
            bdcSlYjxxDO.withGzlslid(gzlslid).withSlbh(gzlslid);
        }else{
            paramMap.put("slbh", bdcSlYjxxDO.getSlbh());
            bdcSlYjxxDO.setWlddh(bdcSlYjxxDO.getSlbh());
        }
        bdcSlYjxxDO.setQxdm(getRegionCode(currentUserName));
        paramMap.put("bdcSlYjxxDOs",new LinkedList<BdcSlYjxxDO>(){{
            add(bdcSlYjxxDO);
        }});
        return (List<Map<String, String>>) exchangeInterfaceFeignService.requestInterface("emsddjr",
                JSONObject.toJSONStringWithDateFormat(paramMap,"yyyy-MM-dd"));
    }

    private String getRegionCode(String currentUserName) {
        String regionCode ="";
        if(StringUtils.isNotBlank(currentUserName)) {
            regionCode = userManagerUtils.getRegionCodeByUserName(currentUserName);
        }
        return regionCode;
    }

    /**
     * 更新邮寄信息状态
     * @param bdcSlYjxxDO 不动产受理邮寄信息DO
     * @param wlddh 物流订单号
     * @param mailNo 物流运单号
     */
    private void modifyPushState(BdcSlYjxxDO bdcSlYjxxDO,String wlddh,String mailNo){
        bdcSlYjxxDO.setWlddh(wlddh);
        bdcSlYjxxDO.setWlydh(mailNo);
        bdcSlYjxxDO.setTszt(BdcSlYjxxDTO.PUSHED);
        bdcSlYjxxDO.setTssj(new Date());
        this.updateBdcSlYjxxByYjxxId(bdcSlYjxxDO);
    }

    /**
     * 初始化邮寄信息
     */
    private void initYjxx(BdcSlYjxxDO bdcSlYjxxDO, String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)) {
            final List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                // 初始化收件人信息
                if (StringUtils.isBlank(bdcSlYjxxDO.getSjrmc())) {
                    BdcQlrDO bdcQlrDO = this.getBdcQlrxx(bdcXmDTOList.get(0).getXmid());
                    if (null != bdcQlrDO) {
                        bdcSlYjxxDO.withSlbh(bdcXmDTOList.get(0).getSlbh()).withGzlslid(gzlslid)
                                .withSjrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getDh(), bdcQlrDO.getYb(), bdcQlrDO.getTxdz());
                    }
                    if (StringUtils.isBlank(bdcSlYjxxDO.getSjryb())) {
                        bdcSlYjxxDO.setSjryb(Optional.ofNullable(bdcYjxxConfig.getSjryb()).orElse(BdcYjxxConfig.DEFAULT_YB));
                    }
                }
                // 初始化寄件人信息
                if (StringUtils.isBlank(bdcSlYjxxDO.getJjrmc())) {
                    String regionCode = getRegionCode(userManagerUtils.getCurrentUserName());
                    initJjrxx(bdcSlYjxxDO, regionCode);
                }
            } else {
                // 存在无项目数据的情况时，将邮寄信息的受理编号设置为工作流实例。 适用于一窗受理流程
                bdcSlYjxxDO.withSlbh(gzlslid).withGzlslid(gzlslid);
            }
        }
    }

    /**
     * 读取Yml配置的寄件人信息
     */
    private void initJjrxx(BdcSlYjxxDO bdcSlYjxxDO, String qxdm){
        if(StringUtils.isBlank(bdcSlYjxxDO.getJjrmc())){
            Map<String,String> map= bdcYjxxConfig.getJjrxxByQxdm(qxdm);
            if(MapUtils.isNotEmpty(map)){
                dozerBeanMapper.map(map, bdcSlYjxxDO);
            }
            if(StringUtils.isBlank(bdcSlYjxxDO.getJjryb())){
                bdcSlYjxxDO.setJjryb(BdcYjxxConfig.DEFAULT_YB);
            }
        }
    }

    private BdcQlrDO getBdcQlrxx(String xmid){
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            return bdcQlrDOList.get(0);
        }
        return null;
    }

}
