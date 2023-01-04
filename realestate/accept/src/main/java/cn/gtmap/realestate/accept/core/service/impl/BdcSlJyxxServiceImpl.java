package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.config.JyxxFwytYddConfig;
import cn.gtmap.realestate.accept.core.mapper.BdcSlJyxxMapper;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.core.service.impl.jyxx.*;
import cn.gtmap.realestate.accept.service.BdcCommonSlService;
import cn.gtmap.realestate.accept.service.BdcSlJyxxAbstractService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.wxzjyz.request.WxzjYzRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxFileDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht.ZjClfHtxxDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht.ZjHtxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.jyqd.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmZhxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJyxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.accept.FcjyxxQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDsQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.etl.RudongFcjyDataFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理交易信息
 */
@Service
public class BdcSlJyxxServiceImpl implements BdcSlJyxxService,BdcCommonSlService {
    private static final String MSG_HXFAIL = "回写信息失败";
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    private BdcSlSqrService bdcSlSqrService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlJyxxMapper bdcSlJyxxMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;
    @Autowired
    JyxxFwytYddConfig jyxxFwytYddConfig;
    @Autowired
    EntityService entityService;
    @Autowired
    RudongFcjyDataFeignService rudongFcjyDataFeignService;
    @Autowired
    BdcDjxlPzService bdcDjxlPzService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    Set<BdcSlJyxxAbstractService> bdcSlJyxxAbstractServiceSet;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    StorageClientMatcher storageClientMatcher;
    @Autowired
    BdcZsFeignService bdcZsFeignService;


    @Resource(name = "tsBdcFcjyTzYwxxServiceImpl")
    TsBdcFcjyYwxxService tsBdcFcjyTzYwxxServiceImpl;
    @Resource(name = "tsBdcFcjyDelYwxxServiceImpl")
    TsBdcFcjyYwxxService tsBdcFcjyDelYwxxServiceImpl;
    @Resource(name = "tsBdcFcjyHyYwxxServiceImpl")
    TsBdcFcjyYwxxService tsBdcFcjyHyYwxxServiceImpl;
    @Resource(name = "tsBdcFcjyZyDjxxServiceImpl")
    TsBdcFcjyZyDjxxServiceImpl tsBdcFcjyZyDjxxServiceImpl;
    @Resource(name = "tsBdcFcjyDyDjxxServiceImpl")
    TsBdcFcjyDyDjxxServiceImpl tsBdcFcjyDyDjxxServiceImpl;
    @Value("#{'${spf.gzldyid:}'.split(',')}")
    protected List<String> spfdyidList;

    /**
     * 一人办件接收住建推送合同信息的电子文件，文件夹名称
     */
    @Value("${yrbj.dzhtwjjmc:电子合同}")
    private String zjHtxxWjjmc;
    /**
     * 一人办件，推送住建合同信息的附件文件夹名称集合，多个文件夹使用"," 分隔
     */
    @Value("${yrbj.tszj.wjjmc:}")
    private String tszjWjjmc;
    /**
     * 一人办件权利人签字文件夹名称
     */
    @Value("${yrbj.qlrqz.wjjmc:权利人签名}")
    private String qlrqzWjjmc;
    /**
     * 一人办件义务人签字文件夹名称
     */
    @Value("${yrbj.ywrqz.wjjmc:义务人签名}")
    private String ywrqzWjjmc;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlJyxxServiceImpl.class);

    @Override
    public List<BdcSlJyxxDO> listBdcSlJyxxByXmid(String xmid) {
        List<BdcSlJyxxDO> bdcSlJyxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlJyxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            bdcSlJyxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlJyxxDOList)) {
            bdcSlJyxxDOList = Collections.emptyList();
        }
        return bdcSlJyxxDOList;
    }

    @Override
    public List<BdcSlJyxxDO> listBdcSlJyxxByHtbh(String htbh) {
        List<BdcSlJyxxDO> bdcSlJyxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(htbh)) {
            Example example = new Example(BdcSlJyxxDO.class);
            example.createCriteria().andEqualTo("htbh", htbh);
            bdcSlJyxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlJyxxDOList)) {
            bdcSlJyxxDOList = Collections.emptyList();
        }
        return bdcSlJyxxDOList;

    }

    @Override
    public List<BdcSlJyxxDO> listBdcSlJyxx(BdcSlJyxxQO bdcSlJyxxQO) {
        if(StringUtils.isBlank(bdcSlJyxxQO.getGzlslid()) && StringUtils.isBlank(bdcSlJyxxQO.getHtbh()) && StringUtils.isBlank(bdcSlJyxxQO.getXmid())){
            throw new AppException(ErrorCode.MISSING_ARG, "查询参数不能为空");
        }
        return this.bdcSlJyxxMapper.listBdcSlJyxx(bdcSlJyxxQO);
    }

    @Override
    public BdcSlJyxxDO saveBdcSlJyxx(BdcSlJyxxDO bdcSlJyxxDO) {
        if (StringUtils.isNotBlank(bdcSlJyxxDO.getJyxxid())) {
            entityMapper.updateByPrimaryKeySelective(bdcSlJyxxDO);
        } else {
            bdcSlJyxxDO.setJyxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlJyxxDO);
        }
        return bdcSlJyxxDO;
    }

    /**
     * @param jyxxid 交易信息id
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据交易信息id获取不动产受理交易信息
     */
    @Override
    public BdcSlJyxxDO queryBdcSlJyxxByJyxxid(String jyxxid) {
        if (StringUtils.isBlank(jyxxid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlJyxxDO.class, jyxxid);
    }

    /**
     * @param bdcSlJyxxDO 交易信息
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理交易信息
     */
    @Override
    public BdcSlJyxxDO insertBdcSlJyxx(BdcSlJyxxDO bdcSlJyxxDO) {
        if (bdcSlJyxxDO != null) {
            if (StringUtils.isBlank(bdcSlJyxxDO.getJyxxid())) {
                bdcSlJyxxDO.setJyxxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlJyxxDO);
        }
        return bdcSlJyxxDO;
    }

    /**
     * @param jyxxid 交易信息id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    @Override
    public Integer deleteBdcSlJyxxByJyxxid(String jyxxid) {
        int result = 0;
        if (StringUtils.isNotBlank(jyxxid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlJyxxDO.class, jyxxid);
        }
        return result;
    }

    /**
     * @param xmid 项目id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    @Override
    public Integer deleteBdcSlJyxxByXmid(String xmid) {
        int result = 0;
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlJyxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param name   姓名
     * @param cardNo 证件号
     * @return http返回信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房产交易限购信息
     */
    @Override
    public XgxxHttpResponseDTO queryFcjyXgxx(String name, String cardNo) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(cardNo)) {
            throw new MissingArgumentException(messageProvider.getMessage("限购查询缺失名称和证件号"));
        }
        String beanName = "hfFcjyXgxx_http";
        // 参数可使用MAP类型传递，也可以定义请求实体传递
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cardNo", cardNo);
        paramMap.put("name", name);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        if (response != null) {
            return JSONObject.parseObject(JSON.toJSONString(response), XgxxHttpResponseDTO.class);
        }
        return new XgxxHttpResponseDTO();
    }

    /**
     * @param fcjyxxQO 房产交易查询对象
     * @return http返回信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房产交易合同信息
     */
    @Override
    @Transactional
    public FcjyBaxxDTO queryFcjyHtxx(FcjyxxQO fcjyxxQO) {
        if (StringUtils.isBlank(fcjyxxQO.getHtbh()) || StringUtils.isBlank(fcjyxxQO.getXmid())) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("contractNo", fcjyxxQO.getHtbh());
        paramMap.put("htbh", fcjyxxQO.getHtbh());
        Object response = new Object();
        //如果不传入房屋类型，根据配置判断
        String fwlx =fcjyxxQO.getFwlx();
        if (StringUtils.isBlank(fwlx)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(fcjyxxQO.getXmid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                if (CollectionUtils.isNotEmpty(spfdyidList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid()) && spfdyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                    fwlx = CommonConstantUtils.FCJY_TYPE_SPF;
                } else {
                    fwlx = CommonConstantUtils.FCJY_TYPE_CLF;
                }
            }
        }
        String beanName =fcjyxxQO.getBeanName();
        if (StringUtils.equals(fwlx, CommonConstantUtils.FCJY_TYPE_CLF)) {
            if(StringUtils.isBlank(beanName)){
                beanName ="fcjyClfHtxx";
            }
            response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        } else if (StringUtils.equals(fwlx, CommonConstantUtils.FCJY_TYPE_SPF)) {
            if(StringUtils.isBlank(beanName)){
                beanName ="fcjySpfBaxx";
            }
            response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        }
        if (Objects.nonNull(response)) {
            LOGGER.info("合同编号:{},查询合同信息接口调用成功，响应内容：{}", fcjyxxQO.getHtbh(),response);
            FcjyBaxxDTO fcjyClfHtxx = JSONObject.parseObject(JSON.toJSONString(response), FcjyBaxxDTO.class);
            if (Objects.nonNull(fcjyClfHtxx)) {
                //常州权利人还需要再分割一次
                if(StringUtils.equals("fcjyClfHtxx",beanName) &&fcjyxxQO.isSplitQlr()){
                    splitQlrs(fcjyClfHtxx);
                }
                fcjyClfHtxx.setXmid(fcjyxxQO.getXmid());
                handleClfHtxxResponse(fcjyClfHtxx, fcjyxxQO.getXmid(), fcjyxxQO.getLclx());
                // 对权利人信息做去空格操作
                if(CollectionUtils.isNotEmpty(fcjyClfHtxx.getBdcQlr())){
                    for (BdcQlrDO bdcQlrDO : fcjyClfHtxx.getBdcQlr()) {
                        if(StringUtils.isNotBlank(bdcQlrDO.getQlrmc())){
                            bdcQlrDO.setQlrmc(StringUtils.deleteWhitespace(bdcQlrDO.getQlrmc()));
                        }
                        if(StringUtils.isNotBlank(bdcQlrDO.getZjh())){
                            bdcQlrDO.setZjh(StringUtils.deleteWhitespace(bdcQlrDO.getZjh()));
                        }
                    }
                }
                if(fcjyxxQO.isModifyDsQlr()){
                    //更新第三权利人
                    modifyDsQlr(fcjyxxQO.getXmid(),fcjyClfHtxx.getBdcQlr(),true);
                }
                return fcjyClfHtxx;
            }
        }
        return null;
    }

    /**
     * @param fcjyxxQO 房产交易查询对象
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询淮安房产交易合同信息
     */
    @Override
    public FcjyClfHtxxDTO queryHaFcjyHtxx(FcjyxxQO fcjyxxQO) {
        if (StringUtils.isBlank(fcjyxxQO.getHtbh()) || StringUtils.isBlank(fcjyxxQO.getXmid())) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("caseid", fcjyxxQO.getHtbh());
        Object response = new Object();
        //如果不传入房屋类型，根据配置判断
        String fwlx = fcjyxxQO.getFwlx();
        if (StringUtils.isBlank(fwlx)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(fcjyxxQO.getXmid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                if (CollectionUtils.isNotEmpty(spfdyidList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid()) && spfdyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                    fwlx = CommonConstantUtils.FCJY_TYPE_SPF;
                } else {
                    fwlx = CommonConstantUtils.FCJY_TYPE_CLF;
                }
            }
        }
        String beanName = fcjyxxQO.getBeanName();
        if (StringUtils.equals(fwlx, CommonConstantUtils.FCJY_TYPE_CLF)) {
            if(StringUtils.isBlank(beanName)){
                beanName ="fcjyClfHtxx";
            }
            LOGGER.info("合同编号:{},查询合同信息接口请求，beanName:{}, 请求参数：{}", fcjyxxQO.getHtbh(), beanName, paramMap);
            response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
            LOGGER.info("合同编号:{},查询合同信息接口调用成功，响应内容：{}", fcjyxxQO.getHtbh(),response);
            if (Objects.nonNull(response)) {
                FcjyClfHtxxDTO fcjyClfHtxxDTO = JSONObject.parseObject(JSON.toJSONString(response), FcjyClfHtxxDTO.class);
                LOGGER.info(fcjyClfHtxxDTO.toString());
                handleHaFcjyxxResponse(fcjyClfHtxxDTO, fcjyxxQO, "交易合同附件", false);
                return fcjyClfHtxxDTO;
            }
        } else if (StringUtils.equals(fwlx, CommonConstantUtils.FCJY_TYPE_SPF)) {
            if(StringUtils.isBlank(beanName)){
                beanName ="fcjySpfBaxx";
            }
            LOGGER.info("合同编号:{},查询合同信息接口，beanName:{}, 请求参数：{}", fcjyxxQO.getHtbh(), beanName, paramMap);
            response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
            LOGGER.info("合同编号:{},查询合同信息接口调用成功，响应内容：{}", fcjyxxQO.getHtbh(),response);
            if (Objects.nonNull(response)) {
                FcjyClfHtxxDTO fcjyClfHtxxDTO = JSONObject.parseObject(JSON.toJSONString(response), FcjyClfHtxxDTO.class);
                handleHaFcjyxxResponse(fcjyClfHtxxDTO, fcjyxxQO, "交易合同附件", true);
                return fcjyClfHtxxDTO;
            }
        }
        return null;
    }

    /**
     * @description 查询淮安房屋信息是否签售
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/19 16:11
     * @param fwbh 房屋编号
     * @param xmmc 项目名称
     * @param ysxkzh 预售许可证号
     * @param fh 房号
     * @param qsdm 区属代码
     * @param beanName
     * @return boolean
     */
    @Override
    public Object queryHaFcjyFwsfqs(String fwbh, String xmmc, String ysxkzh, String fh, String qsdm, String beanName) {
        Object response = null;
        boolean fwsfqs = false;
        Map<String, Object> paramMap = new HashMap<>(16);
        if ("fcjyFwsfqs".equals(beanName)) {
            // 调用判断房屋是否已经销售接口
            if (StringUtils.isBlank(fwbh)) {
                throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
            }
            paramMap.put("houseno", fwbh);
            response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
            if (Objects.isNull(response)) {
                throw new AppException("未查询到房屋签售信息");
            }
            JSONObject sfqsObj = JSONObject.parseObject(JSON.toJSONString(response), JSONObject.class);
            if ("true".equals(sfqsObj.getString("isSaled"))) {
                fwsfqs = true;
            }
        } else if ("fcjyLpxx".equals(beanName)) {
            // 调用获取楼盘信息接口
            if ((StringUtils.isBlank(xmmc) && StringUtils.isBlank(ysxkzh)) || StringUtils.isBlank(fh) || StringUtils.isBlank(qsdm)) {
                throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
            }
            paramMap.put("projectname", xmmc);
            paramMap.put("permitno", ysxkzh);
            paramMap.put("qxdm", qsdm);
            response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
            if (Objects.isNull(response)) {
                throw new AppException("未查询到楼盘信息");
            }
            JSONObject lpxxObj = JSONObject.parseObject(JSON.toJSONString(response), JSONObject.class);
            // 楼盘编号
            String buildId = lpxxObj.getJSONArray("build").getJSONObject(0).getString("buildid");
            if (StringUtils.isBlank(buildId)) {
                throw new AppException("未查询到楼盘信息");
            }
            Map<String, Object> fwxxMap = new HashMap<>();
            fwxxMap.put("buildid", buildId);
            fwxxMap.put("room", fh);
            Object fwxxResponse = exchangeInterfaceFeignService.requestInterface("fcjyFwxx", fwxxMap);
            if (Objects.isNull(fwxxResponse)) {
                throw new AppException("未查询到房屋签售信息");
            }
            JSONObject fwxxObj = JSONObject.parseObject(JSON.toJSONString(fwxxResponse), JSONObject.class);
            // 销售状态 0为未销售，1为销售
            String issaleid = fwxxObj.getJSONArray("fwinfo").getJSONObject(0).getString("issaleid");
            if (StringUtils.isBlank(issaleid)) {
                throw new AppException("未查询到房屋签售信息");
            }
            if ("1".equals(issaleid)) {
                fwsfqs = true;
            }
        }
        return fwsfqs;
    }

    /**
     * @param gzlslid
     * @param ywlx 业务类型型 0:正常业务;1:一键注销业务;2：更新业务;
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 推送淮安房产交易通知业务信息
     * @date : 2022/8/31 19:11
     */
    @Override
    public void tsHaFcjyTsYwxx(String gzlslid, String ywlx, String xmids) {
        TsFcjyYwxxDTO tsFcjyYwxxDTO = new TsFcjyYwxxDTO();
        tsFcjyYwxxDTO.setGzlslid(gzlslid);
        tsFcjyYwxxDTO.setXmids(xmids);
        tsFcjyYwxxDTO.setYwlx(ywlx);
        Map<String, Object> paramMap = tsBdcFcjyTzYwxxServiceImpl.getFcjyTsYwxxRequestParam(tsFcjyYwxxDTO);
        // 发起推送，true表示推送成功，false推送失败
        boolean tsResult = tsBdcFcjyTzYwxxServiceImpl.tsFcjyYwxx(paramMap);
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setGzlslid(gzlslid);
        bdcCzrzDO.setCzcs(JSON.toJSONString(tsFcjyYwxxDTO));
        if (tsResult) {
            bdcCzrzDO.setCzjg("推送成功");
            bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
            tsBdcFcjyTzYwxxServiceImpl.addTssbLog(bdcCzrzDO);
        } else {
            bdcCzrzDO.setCzjg("推送失败");
            bdcCzrzDO.setCzzt(CommonConstantUtils.SF_F_DM);
            tsBdcFcjyTzYwxxServiceImpl.addTssbLog(bdcCzrzDO);
        }
    }

    /**
     * @param gzlslid
     * @param reason
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 推送淮安房产交易删除业务信息
     * @date : 2022/8/31 19:11
     */
    @Override
    public void tsHaFcjyDelYwxx(String gzlslid, String reason) {
        TsFcjyYwxxDTO tsFcjyYwxxDTO = new TsFcjyYwxxDTO();
        tsFcjyYwxxDTO.setGzlslid(gzlslid);
        tsFcjyYwxxDTO.setReason(reason);
        Map<String, Object> paramMap = tsBdcFcjyDelYwxxServiceImpl.getFcjyTsYwxxRequestParam(tsFcjyYwxxDTO);
        // 发起推送，true表示推送成功，false推送失败
        boolean tsResult = tsBdcFcjyDelYwxxServiceImpl.tsFcjyYwxx(paramMap);
        // 删除后,业务信息也会删除，无法通过gzlslid获取请求参数，所以需要将整个请求参数保存到数据库中
        JSONObject czcsObj = new JSONObject();
        czcsObj.put("paramMap", JSONObject.toJSONString(paramMap));
        String czcs = czcsObj.toJSONString();
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setGzlslid(gzlslid);
        bdcCzrzDO.setCzcs(czcs);
        if (tsResult) {
            bdcCzrzDO.setCzjg("推送成功");
            bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
            tsBdcFcjyDelYwxxServiceImpl.addTssbLog(bdcCzrzDO);
        } else {
            bdcCzrzDO.setCzjg("推送失败");
            bdcCzrzDO.setCzzt(CommonConstantUtils.SF_F_DM);
            tsBdcFcjyDelYwxxServiceImpl.addTssbLog(bdcCzrzDO);
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 推送淮安房产交易还原业务信息
     * @date : 2022/8/31 19:11
     */
    @Override
    public void tsHaFcjyHyYwxx(String gzlslid) {
        TsFcjyYwxxDTO tsFcjyYwxxDTO = new TsFcjyYwxxDTO();
        tsFcjyYwxxDTO.setGzlslid(gzlslid);
        Map<String, Object> paramMap = tsBdcFcjyHyYwxxServiceImpl.getFcjyTsYwxxRequestParam(tsFcjyYwxxDTO);
        // 发起推送，true表示推送成功，false推送失败
        boolean tsResult = tsBdcFcjyHyYwxxServiceImpl.tsFcjyYwxx(paramMap);
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setGzlslid(gzlslid);
        bdcCzrzDO.setCzcs(JSON.toJSONString(tsFcjyYwxxDTO));
        if (tsResult) {
            bdcCzrzDO.setCzjg("推送成功");
            bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
            tsBdcFcjyHyYwxxServiceImpl.addTssbLog(bdcCzrzDO);
        } else {
            bdcCzrzDO.setCzjg("推送失败");
            bdcCzrzDO.setCzzt(CommonConstantUtils.SF_F_DM);
            tsBdcFcjyHyYwxxServiceImpl.addTssbLog(bdcCzrzDO);
        }
    }

    /**
     * @param rzid
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 补推淮安房产交易业务信息
     * @date : 2022/8/31 19:11
     */
    @Override
    public boolean btHaFcjyYwxx(String rzid) {
        boolean tsResult = false;
        if (StringUtils.isNotBlank(rzid)) {
            BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
            bdcCzrzDO.setRzid(rzid);
            List<BdcCzrzDO> bdcCzrzDOList = bdcCzrzFeignService.listBdcCzrz(bdcCzrzDO);
            if (CollectionUtils.isNotEmpty(bdcCzrzDOList)) {
                bdcCzrzDO = bdcCzrzDOList.get(0);
                String beanName = bdcCzrzDO.getCzmc();
                String czcs = bdcCzrzDO.getCzcs();
                if (TsBdcFcjyTzYwxxServiceImpl.BEANNAME.equals(beanName)) {
                    TsFcjyYwxxDTO tsFcjyYwxxDTO = JSONObject.parseObject(czcs, TsFcjyYwxxDTO.class);
                    Map<String, Object> paramMap = tsBdcFcjyTzYwxxServiceImpl.getFcjyTsYwxxRequestParam(tsFcjyYwxxDTO);
                    tsResult = tsBdcFcjyTzYwxxServiceImpl.tsFcjyYwxx(paramMap);
                }else if (TsBdcFcjyDelYwxxServiceImpl.BEANNAME.equals(beanName)) {
                    String requestParam = JSONObject.parseObject(czcs, JSONObject.class).getString("paramMap");
                    Map<String, Object> paramMap = JSONObject.parseObject(requestParam, Map.class);
                    tsResult = tsBdcFcjyDelYwxxServiceImpl.tsFcjyYwxx(paramMap);
                } else if (TsBdcFcjyHyYwxxServiceImpl.BEANNAME.equals(beanName)) {
                    TsFcjyYwxxDTO tsFcjyYwxxDTO = JSONObject.parseObject(czcs, TsFcjyYwxxDTO.class);
                    Map<String, Object> paramMap = tsBdcFcjyHyYwxxServiceImpl.getFcjyTsYwxxRequestParam(tsFcjyYwxxDTO);
                    tsResult = tsBdcFcjyHyYwxxServiceImpl.tsFcjyYwxx(paramMap);
                }
                if (tsResult) {
                    bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
                    bdcCzrzDO.setCzjg("补推成功");
                } else {
                    bdcCzrzDO.setCzjg("补推失败");
                }
                bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
                bdcCzrzDO.setCzsj(new Date());
                bdcCzrzFeignService.modifyBdcCzrzByRzid(bdcCzrzDO);
            }
        }
        return tsResult;
    }

    //更新第三权利人
    private List<BdcDsQlrDO> modifyDsQlr(String xmid,List<BdcQlrDO> bdcQlrDOList,boolean isCsh){
        if(isCsh) {
            bdcQlrFeignService.delBdcDsQlr(xmid, CommonConstantUtils.DSQLR_QLRLB_CMR);
            bdcQlrFeignService.delBdcDsQlr(xmid, CommonConstantUtils.DSQLR_QLRLB_MSR);
        }
        List<BdcDsQlrDO> bdcDsQlrDOList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            for(BdcQlrDO qlr:bdcQlrDOList) {
                BdcDsQlrDO dsQlrDO =new BdcDsQlrDO();
                BeanUtils.copyProperties(qlr, dsQlrDO);
                if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlr.getQlrlb())){
                    dsQlrDO.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_MSR);
                }else{
                    dsQlrDO.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_CMR);
                }
                dsQlrDO.setXmid(xmid);
                dsQlrDO.setQlrid(UUIDGenerator.generate16());
                bdcDsQlrDOList.add(dsQlrDO);
            }
            bdcQlrFeignService.insertBdcDsQlrPl(bdcDsQlrDOList);
        }
        return bdcDsQlrDOList;
    }

    //分割权利人
    private void splitQlrs(FcjyBaxxDTO fcjyBaxxDTO) {
        if(CollectionUtils.isNotEmpty(fcjyBaxxDTO.getBdcQlr())) {
            List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
            for (BdcQlrDO bdcQlrDO : fcjyBaxxDTO.getBdcQlr()) {
                Integer qlrsxh =1;
                Integer ywrsxh =1;
                if (StringUtils.isNotBlank(bdcQlrDO.getQlrmc()) && bdcQlrDO.getQlrmc().contains(CommonConstantUtils.ZF_YW_DH)) {
                    String[] qlrmcList = StringUtils.split(bdcQlrDO.getQlrmc(), CommonConstantUtils.ZF_YW_DH);
                    String[] zjhList = StringUtils.split(bdcQlrDO.getZjh(), CommonConstantUtils.ZF_YW_DH);
                    String[] txdzList = StringUtils.split(bdcQlrDO.getTxdz(), CommonConstantUtils.ZF_YW_DH);
                    String[] zjzlList = StringUtils.split(bdcQlrDO.getZjzl() != null?bdcQlrDO.getZjzl().toString():"", CommonConstantUtils.ZF_YW_DH);
                    String[] dlrzjzlList = StringUtils.split(bdcQlrDO.getDlrzjzl(), CommonConstantUtils.ZF_YW_DH);
                    String[] dhList = StringUtils.split(bdcQlrDO.getDh(), CommonConstantUtils.ZF_YW_DH);
                    String[] dlrmcList = StringUtils.split(bdcQlrDO.getDlrmc(), CommonConstantUtils.ZF_YW_DH);
                    String[] dlrzjhList = StringUtils.split(bdcQlrDO.getDlrzjh(), CommonConstantUtils.ZF_YW_DH);
                    for (int i = 0; i < qlrmcList.length; i++) {
                        BdcQlrDO qlr = new BdcQlrDO();
                        qlr.setQlrmc(qlrmcList[i]);
                        qlr.setZjzl(bdcQlrDO.getZjzl() != null?Integer.parseInt(getIndexStrOrFristStr(zjzlList, i)):null);
                        qlr.setZjh(getIndexStrOrFristStr(zjhList, i));
                        qlr.setTxdz(getIndexStrOrFristStr(txdzList, i));
                        qlr.setDh(getIndexStrOrFristStr(dhList, i));
                        qlr.setDlrmc(getIndexStrOrFristStr(dlrmcList, i));
                        qlr.setDlrzjh(getIndexStrOrFristStr(dlrzjhList, i));
                        qlr.setDlrzjzl(getIndexStrOrFristStr(dlrzjzlList, i));
                        qlr.setQlrlb(bdcQlrDO.getQlrlb());
                        if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcQlrDO.getQlrlb())) {
                            qlr.setSxh(qlrsxh);
                            qlrsxh++;
                        }else if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR,bdcQlrDO.getQlrlb())){
                            qlr.setSxh(ywrsxh);
                            ywrsxh++;
                        }
                        bdcQlrDOList.add(qlr);
                    }
                } else {
                    if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcQlrDO.getQlrlb())) {
                        bdcQlrDO.setSxh(qlrsxh);
                        qlrsxh++;
                    }else if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR,bdcQlrDO.getQlrlb())){
                        bdcQlrDO.setSxh(ywrsxh);
                        ywrsxh++;
                    }
                    bdcQlrDOList.add(bdcQlrDO);
                }
            }
            fcjyBaxxDTO.setBdcQlr(bdcQlrDOList);
        }
        if(CollectionUtils.isNotEmpty(fcjyBaxxDTO.getBdcSlSqr())) {
            List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
            for(BdcSlSqrDO bdcSlSqrDO:fcjyBaxxDTO.getBdcSlSqr()){
                Integer qlrsxh =1;
                Integer ywrsxh =1;
                if(StringUtils.isNotBlank(bdcSlSqrDO.getSqrmc()) &&bdcSlSqrDO.getSqrmc().contains(CommonConstantUtils.ZF_YW_DH)){
                    String[] qlrmcList = StringUtils.split(bdcSlSqrDO.getSqrmc(), CommonConstantUtils.ZF_YW_DH);
                    String[] zjhList = StringUtils.split(bdcSlSqrDO.getZjh(), CommonConstantUtils.ZF_YW_DH);
                    String[] txdzList = StringUtils.split(bdcSlSqrDO.getTxdz(), CommonConstantUtils.ZF_YW_DH);
                    String[] zjzlList = StringUtils.split(bdcSlSqrDO.getZjzl() != null?bdcSlSqrDO.getZjzl().toString():"", CommonConstantUtils.ZF_YW_DH);
                    String[] dlrzjzlList = StringUtils.split(bdcSlSqrDO.getDlrzjzl() != null ?bdcSlSqrDO.getDlrzjzl().toString():"", CommonConstantUtils.ZF_YW_DH);
                    String[] dhList = StringUtils.split(bdcSlSqrDO.getDh(), CommonConstantUtils.ZF_YW_DH);
                    String[] dlrmcList = StringUtils.split(bdcSlSqrDO.getDlrmc(), CommonConstantUtils.ZF_YW_DH);
                    String[] dlrzjhList = StringUtils.split(bdcSlSqrDO.getDlrzjh(), CommonConstantUtils.ZF_YW_DH);

                    for (int i = 0; i < qlrmcList.length; i++) {
                        BdcSlSqrDO sqr =new BdcSlSqrDO();
                        sqr.setSqrmc(qlrmcList[i]);
                        sqr.setZjzl(bdcSlSqrDO.getZjzl() != null?Integer.parseInt(getIndexStrOrFristStr(zjzlList, i)):null);
                        sqr.setZjh(getIndexStrOrFristStr(zjhList, i));
                        sqr.setTxdz(getIndexStrOrFristStr(txdzList, i));
                        sqr.setDh(getIndexStrOrFristStr(dhList, i));
                        sqr.setDlrmc(getIndexStrOrFristStr(dlrmcList, i));
                        sqr.setDlrzjh(getIndexStrOrFristStr(dlrzjhList, i));
                        sqr.setDlrzjzl(bdcSlSqrDO.getDlrzjzl() != null ?Integer.parseInt(getIndexStrOrFristStr(dlrzjzlList,i)):null);
                        sqr.setSqrlb(bdcSlSqrDO.getSqrlb());
                        if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcSlSqrDO.getSqrlb())) {
                            sqr.setSxh(qlrsxh);
                            qlrsxh++;
                        }else if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR,bdcSlSqrDO.getSqrlb())){
                            sqr.setSxh(ywrsxh);
                            ywrsxh++;
                        }
                        bdcSlSqrDOList.add(sqr);
                    }
                }else{
                    if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcSlSqrDO.getSqrlb())) {
                        bdcSlSqrDO.setSxh(qlrsxh);
                        qlrsxh++;
                    }else if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR,bdcSlSqrDO.getSqrlb())){
                        bdcSlSqrDO.setSxh(ywrsxh);
                        ywrsxh++;
                    }
                    bdcSlSqrDOList.add(bdcSlSqrDO);
                }
            }
            fcjyBaxxDTO.setBdcSlSqr(bdcSlSqrDOList);

        }

    }

    private static String getIndexStrOrFristStr(String[] strArray, int i) {
        String str = "";
        if (strArray != null && i < strArray.length) {
            str = strArray[i];
        } else if (strArray != null &&strArray.length>0) {
            str = strArray[0];
        }
        return str;
    }

    @Override
    public void handleClfHtxxResponse(FcjyBaxxDTO fcjyClfHtxxDTO, String xmid, String lclx) {
        // 处理受理交易信息
        this.handleSlJyxx(xmid, fcjyClfHtxxDTO.getBdcSlJyxx());
        // 处理受理房屋信息数据
        this.handleSlFwxx(xmid, fcjyClfHtxxDTO.getBdcSlFwxx());

        // 获取当前项目数据
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            if(StringUtils.equals(CommonConstantUtils.LCLX_ZH_STR,lclx)){
                modifyQlrAndDjxxByFcjy(bdcXmDOList.get(0), fcjyClfHtxxDTO);
            }else{
                // 2020-02-19：当lclx为批量流程时，一个项目获取同步处理权利人信息
                BdcXmQO queryParam =new BdcXmQO();
                queryParam.setGzlslid(bdcXmDOList.get(0).getGzlslid());
                List<BdcXmDO> bdcXmDOS = this.bdcXmFeignService.listBdcXm(queryParam);
                if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                    for(BdcXmDO bdcXmDO : bdcXmDOS){
                        modifyQlrAndDjxxByFcjy(bdcXmDO,fcjyClfHtxxDTO);
                    }
                }
            }
            // 根据xmid更新权利信息的交易金额
            this.handleBdcXmAndQlxx(fcjyClfHtxxDTO);
        }
    }

    /**
     * @description 处理淮安房产合同信息响应数据
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/12 9:51
     * @param fcjyClfHtxxDTO 淮安房产合同信息响应数据
     * @param fcjyxxQO 查询对象
     * @param foldName 文件夹名称
     * @param sfdqywr
     * @return void
     */
    public void handleHaFcjyxxResponse(FcjyClfHtxxDTO fcjyClfHtxxDTO, FcjyxxQO fcjyxxQO, String foldName, Boolean sfdqywr) {
        BdcSlJyxxDO bdcSlJyxx = fcjyClfHtxxDTO.getBdcSlJyxx();
        if (bdcSlJyxx == null) {
            throw new AppException("缺失交易信息");
        }
        //部分交易信息获取到登记
        this.updateBdcXmJyhth(bdcSlJyxx.getHtbh(), fcjyxxQO.getGzlslid(), fcjyxxQO.getXmid());
        // 更新权利信息
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(fcjyxxQO.getXmid());
        if (null != bdcQl) {
            String className = "";
            // 不动产单元唯一编号
            String bdcdywybh = "";
            if (bdcQl instanceof BdcFdcqDO) {
                className = BdcFdcqDO.class.getName();
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                bdcdywybh = bdcFdcqDO.getBdcdywybh();
            }
            if (bdcQl instanceof BdcYgDO) {
                className = BdcYgDO.class.getName();
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                bdcdywybh = bdcYgDO.getBdcdywybh();
            }
            if (StringUtils.isNotBlank(className)) {
                try {
                    // modifyBdcQl方法中有对金额除以10000
                    this.modifyBdcQl(fcjyxxQO.getXmid(), fcjyxxQO.getGzlslid(), bdcdywybh,
                            String.valueOf(bdcSlJyxx.getJyje() * 10000), className);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("南通获取住建交易信息，更新权利信息失败");
                }
            }
        }
        //更新交易信息
        this.updateSlJyxxByXmid(fcjyxxQO.getXmid(), JSONObject.toJSONString(bdcSlJyxx), null);

        // 更新权利人和义务人
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(fcjyxxQO.getXmid());
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            if(StringUtils.equals(CommonConstantUtils.LCLX_ZH_STR,fcjyxxQO.getLclx())){
                handleQlrxx(bdcXmDOList.get(0), fcjyClfHtxxDTO.getBdcQlr(), sfdqywr);
            }else{
                // 2020-02-19：当lclx为批量流程时，一个项目获取同步处理权利人信息
                BdcXmQO queryParam = new BdcXmQO();
                queryParam.setGzlslid(bdcXmDOList.get(0).getGzlslid());
                List<BdcXmDO> bdcXmDOS = this.bdcXmFeignService.listBdcXm(queryParam);
                if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                    for(BdcXmDO bdcXmDO : bdcXmDOS){
                        handleQlrxx(bdcXmDO, fcjyClfHtxxDTO.getBdcQlr(), sfdqywr);
                    }
                }
            }
        }
        //上传交易附件
        List<TsswDataFjclXxDTO> fileList = fcjyClfHtxxDTO.getFileList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            for (TsswDataFjclXxDTO fjclXxDTO : fileList) {
                String fjmc = fjclXxDTO.getFjmc();
                String base64Str = fjclXxDTO.getBase64();
                if (StringUtils.isBlank(fjmc) || StringUtils.isBlank(base64Str)) {
                    continue;
                }
                BdcPdfDTO bdcPdfDTO = null;
                // 判断base64字符串是否拥有头信息，没有添加jpg的base64头信息
                if (!base64Str.startsWith("data:")) {
                    base64Str = CommonConstantUtils.BASE64_QZ_IMAGE + base64Str;
                }
                try {
                    bdcPdfDTO = new BdcPdfDTO(fcjyxxQO.getGzlslid(), "", fjmc, foldName, CommonConstantUtils.WJHZ_JPG);
                    bdcPdfDTO.setBase64str(base64Str);
                    bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
                } catch (IOException e) {
                    LOGGER.error("生成电子合同信息失败：{}", e.getMessage());
                }
            }
        }
    }

    // 修改不动产的登记权利人信息、不动产受理权利人信息、处理登记业务数据读取
    public void modifyQlrAndDjxxByFcjy(BdcXmDO bdcXmDO, FcjyBaxxDTO fcjyClfHtxxDTO){
        handleQlrxx(bdcXmDO, fcjyClfHtxxDTO.getBdcQlr(),null);
        handleSlSqrxx(bdcXmDO.getXmid(),fcjyClfHtxxDTO.getBdcSlSqr());
    }

    /**
     * 处理受理交易信息，使用接口返回的交易信息补全受理库中的交易信息内容
     * 采用先删后增的模式
     */
    private void handleSlJyxx(String xmid, BdcSlJyxxDO bdcSlJyxxDO){
        if (bdcSlJyxxDO != null && CheckParameter.checkAnyParameter(bdcSlJyxxDO)) {
            // 获取原交易信息内容，替换接口反馈交易信息为空的属性值
            List<BdcSlJyxxDO> bdcSlJyxxDOList = this.listBdcSlJyxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                for (BdcSlJyxxDO jyxxDO : bdcSlJyxxDOList) {
                    BeansResolveUtils.cloneSourceNotNullToTargetNullProperty(jyxxDO, bdcSlJyxxDO);
                }
            }
            // 删除原有交易信息
            this.deleteBdcSlJyxxByXmid(xmid);
            bdcSlJyxxDO.setXmid(xmid);
            this.insertBdcSlJyxx(bdcSlJyxxDO);
        }
    }

    /**
     * 处理房屋信息，使用接口返回的房屋信息补全受理库中的房屋信息
     */
    private void handleSlFwxx(final String xmid, BdcSlFwxxDO bdcSlFwxxDO){
        if(StringUtils.isNotBlank(xmid) && null != bdcSlFwxxDO){
            bdcSlFwxxDO.setXmid(xmid);
            bdcSlFwxxService.saveBdcSlFwxx(bdcSlFwxxDO);
        }
    }

    /**
     * 更新登记权利人信息，先删除原有权利人与义务人信息，在新增接口返回人员信息
     */
    private void handleQlrxx(BdcXmDO bdcXmDO, List<BdcQlrDO> bdcQlrDOList,Boolean sfdqywr){
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDO.getGzlslid());
        // 处理登记权利人信息
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            if(sfdqywr ==null) {
                sfdqywr = this.checkReadYwr(bdcXmDO, xmlx, bdcQlrDOList);
            }
            String xmid = bdcXmDO.getXmid();

            LOGGER.info("删除xmid为{}的权利人信息，是否删除对应的义务人信息：{}", xmid, sfdqywr);
            //获取交易信息时删除对应原有的权利人义务人
            delQlrYwr(xmlx, xmid, sfdqywr);

            String gzlslid = bdcXmDO.getGzlslid();
            int qlrsxh = 0;
            int ywrsxh = 0;
            int sxh = 0;
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                //获取交易信息中权利人义务人信息
                if (Objects.nonNull(bdcQlrDO) && StringUtils.isNotBlank(bdcQlrDO.getQlrmc())) {
                    if ((StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcQlrDO.getQlrlb())) || (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcQlrDO.getQlrlb()) && sfdqywr)) {
                        bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_JY);
                        if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcQlrDO.getQlrlb())) {
                            qlrsxh++;
                            sxh = qlrsxh;
                        }
                        if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcQlrDO.getQlrlb())) {
                            ywrsxh++;
                            sxh = ywrsxh;
                        }
                        //权利人类型空的时候做处理
                        if (Objects.isNull(bdcQlrDO.getQlrlx())) {
                            Integer qlrlx = CommonConstantUtils.QLRLX_QT;
                            //身份证
                            if (CommonConstantUtils.ZJZL_SFZ.equals(bdcQlrDO.getZjzl())) {
                                qlrlx = CommonConstantUtils.QLRLX_GR;
                            } else if (Objects.nonNull(bdcQlrDO.getZjzl())) {
                                qlrlx = CommonConstantUtils.QLRLX_QY;
                            }
                            bdcQlrDO.setQlrlx(qlrlx);
                        }
                        if (CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
                            //组合流程考虑同步问题
                            //考虑组合流程权利人同步问题
                            bdcQlrDO.setXmid(xmid);
                            bdcQlrDO.setSxh(sxh);
                            List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(JSONObject.parseObject(JSONObject.toJSONString(bdcQlrDO)), Constants.FUN_INSERT);
                            for (JSONObject object : jsonObjectList) {
                                BdcQlrDO zhqlr = JSONObject.toJavaObject(object, BdcQlrDO.class);
                                bdcQlrFeignService.insertBdcQlr(zhqlr);
                            }
                        } else {
                            bdcQlrDO.setXmid(xmid);
                            bdcQlrDO.setSxh(sxh);
                            if(bdcQlrDO.getQlrtz() ==null){
                                bdcQlrDO.setQlrtz(bdcQlrFeignService.getQlrtzMrz(bdcXmDO.getQllx(),bdcQlrDO.getQlrlb()));
                            }
                            bdcQlrFeignService.insertBdcQlr(bdcQlrDO);
                        }
                    }
                }
            }
            //同步更新冗余字段和共有情况
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(gzlslid);
            bdcRyzdFeignService.updateGyqkWithGzlslid(gzlslid);
            //回写信息到平台
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
        }
    }

    /**
     * 更新受理申请人信息，只删除权利人与家庭成员信息，保留义务人数据。在新增交易接口中的权利人信息
     */
    private void handleSlSqrxx(final String xmid, List<BdcSlSqrDO> bdcSlSqrDOList){
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            //只删除权利人,义务人数据保留
            bdcSlSqrService.deleteSqrAndJtcyByXmid(xmid,CommonConstantUtils.QLRLB_QLR);
            for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                //只获取交易信息中权利人信息
                if(Objects.nonNull(bdcSlSqrDO) && StringUtils.isNotBlank(bdcSlSqrDO.getSqrmc()) &&StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcSlSqrDO.getSqrlb())){
                    //申请人类型空的时候做处理
                    if(Objects.isNull(bdcSlSqrDO.getSqrlx())){
                        Integer qlrlx=CommonConstantUtils.QLRLX_QT;
                        //身份证
                        if(CommonConstantUtils.ZJZL_SFZ.equals(bdcSlSqrDO.getZjzl())){
                            qlrlx=CommonConstantUtils.QLRLX_GR;
                        }else if(Objects.nonNull(bdcSlSqrDO.getZjzl())){
                            qlrlx=CommonConstantUtils.QLRLX_QY;
                        }
                        bdcSlSqrDO.setSqrlx(qlrlx);
                    }
                    bdcSlSqrDO.setXmid(xmid);
                    bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
                    bdcSlSqrService.insertBdcSlSqr(bdcSlSqrDO);
                }
            }
        }
    }

    /**
     * 校验是否读取义务人
     * @param bdcXmDO  不动产项目DO
     * @param xmlx  项目类型
     * @param bdcQlrDOList  交易信息接口返回的权利人信息
     * @return boolean true: 读取义务人； false : 不读取义务人
     */
    private boolean checkReadYwr(BdcXmDO bdcXmDO, int xmlx, List<BdcQlrDO> bdcQlrDOList){
        //标志是否读取义务人
        Boolean sfdqywr = false;

        String xmid = bdcXmDO.getXmid();
        //当前为预告登记，义务人读取房产交易义务人
        if (CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())) {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
            if (bdcQl instanceof BdcYgDO && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, ((BdcYgDO) bdcQl).getYgdjzl())) {
                sfdqywr = true;
            }
           // 预告登记，当交易信息接口中不存在义务人信息时，不删除义务人内容
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                List<BdcQlrDO> ywrList = bdcQlrDOList.stream().filter(item -> CommonConstantUtils.QLRLB_YWR.equals(item.getQlrlb()))
                        .collect(Collectors.toList());
                if(CollectionUtils.isEmpty(ywrList)){
                    sfdqywr = false;
                }
            }
        }
        //当前项目为组合流程第二手并且上一手为转移登记,义务人读取房产交易义务人
        if (CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
            List<BdcXmZhxxDTO> bdcXmZhxxDTOList = bdcXmFeignService.queryBdcXmZhxx(xmid);
            if (CollectionUtils.isNotEmpty(bdcXmZhxxDTOList)) {
                for (int j = 0; j < bdcXmZhxxDTOList.size(); j++) {
                    BdcXmZhxxDTO bdcXmZhxxDTO = bdcXmZhxxDTOList.get(j);
                    if (StringUtils.equals(bdcXmZhxxDTO.getXmid(), xmid) && bdcXmZhxxDTO.getSxh() == 2 && CommonConstantUtils.DJLX_ZYDJ_DM.equals(bdcXmZhxxDTOList.get(0).getDjlx())) {
                        sfdqywr = true;
                    }
                }
            }
        }
        return sfdqywr;
    }

    @Override
    @Transactional
    public int updateBatchBdcSlJyxx(String gzlslid, String jsonStr,List<String> xmidList) {
        int count = 0;
        if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数jsonStr或gzlslid");
        }

        //获取实体对象
        BdcSlJyxxDO bdcSlJyxxDO = JSON.parseObject(jsonStr, BdcSlJyxxDO.class);
        if (bdcSlJyxxDO ==null) {
            throw new MissingArgumentException("未获取到交易信息！gzlslid：" + gzlslid);
        }
        if(CollectionUtils.isEmpty(xmidList)){
            List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    xmidList.add(bdcXmDTO.getXmid());
                }
            } else {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    xmidList = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
                }
            }
        }

        //批量更新,根据其中一个判断是否更新还是新增
        if(CollectionUtils.isNotEmpty(xmidList)){
            List<BdcSlJyxxDO> bdcSlJyxxDOList =listBdcSlJyxxByXmid(xmidList.get(0));
            if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
                //批量更新
                count =updateBatchBdcSlJyxxByXmids(jsonStr, xmidList, bdcSlJyxxDO);
            }else{
                //批量新增
                count =insertBatchBdcSlJyxxByXmids(bdcSlJyxxDO, xmidList);
            }
        }
        return count;
    }

    /**
     * @param xmid    项目ID
     * @param jsonStr 更新json字符串
     * @param djxl    登记小类
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按照项目更新相关受理交易信息
     */
    @Override
    public int updateXmSlJyxx(String xmid, String jsonStr, String djxl) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }

        //获取实体对象
        BdcSlJyxxDO bdcSlJyxxDO = JSON.parseObject(jsonStr, BdcSlJyxxDO.class);
        if (bdcSlJyxxDO == null) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        // 单个项目批量插入，但是只更新对应xmid的信息
        if (StringUtils.isBlank(bdcSlJyxxDO.getJyxxid())) {
            String gzlslid = this.getGzlslidByXmid(xmid);
            if (StringUtils.isBlank(gzlslid)) {
                throw new MissingArgumentException("未获取到工作流实例ID,xmid：" + xmid);
            }

            //批量插入
            return insertBatchBdcSlJyxx(bdcSlJyxxDO, gzlslid, djxl);
        } else {
            //批量更新(xmid有值时，根据xmid更新)
            bdcSlJyxxDO.setXmid(xmid);
            return updateBatchBdcSlJyxx(jsonStr, null, bdcSlJyxxDO, djxl);
        }
    }

    // 获取工作流实例ID, 先获取受理项目表中数据，没有则获取登记项目表里面的数据
    private String getGzlslidByXmid(String xmid){
        BdcSlXmDO bdcSlXmDO =  this.bdcSlXmService.queryBdcSlXmByXmid(xmid);
        if(Objects.nonNull(bdcSlXmDO)){
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
            return bdcSlJbxxDO.getGzlslid();
        }else{
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(bdcXmDOList)){
                return bdcXmDOList.get(0).getGzlslid();
            }
        }
        return null;
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param xmid    项目ID
     * @param jsonStr 更新json字符串
     * @param djxl    登记小类
     * @return 更新数据量
     * @description 根据xmid更新相关受理交易信息
     */
    @Override
    public int updateSlJyxxByXmid(String xmid, String jsonStr, String djxl) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList = this.listBdcSlJyxxByXmid(xmid);
        LOGGER.info("通过当前项目id查询交易信息--------------：{}",bdcSlJyxxDOList);

        // 判断当前XMID是否拥有交易信息，有则直接更新，无则添加交易信息
        if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
            LOGGER.info("要更新的jyxx为--------------：{}",jsonStr);
            BdcSlJyxxDO bdcSlJyxxDO = JSON.parseObject(jsonStr, BdcSlJyxxDO.class);
            bdcSlJyxxDO.setXmid(xmid);
            return updateBatchBdcSlJyxx(jsonStr, null, bdcSlJyxxDO, djxl);
        }else{
            return this.updateXmSlJyxx(xmid, jsonStr, djxl);
        }
    }

    @Override
    public void dealFcjyBaxxDTO(BdcSlYwxxDTO bdcSlYwxxDTO, String xmid, BdcSlxxInitDTO bdcSlxxInitDTO,String gzldyid){
        LOGGER.info("处理房产交易备案信息数据{}", JSON.toJSONString(bdcSlYwxxDTO.getFcjyBaxxDTO()));
        if(bdcSlYwxxDTO.getFcjyBaxxDTO() != null){
            FcjyBaxxDTO fcjyBaxxDTO =bdcSlYwxxDTO.getFcjyBaxxDTO();
            if(fcjyBaxxDTO.getBdcSlJyxx() != null){
                List<BdcSlJyxxDO> bdcSlJyxxDOList =new ArrayList<>();
                BdcSlJyxxDO bdcSlJyxxDO =new BdcSlJyxxDO();
                BeanUtils.copyProperties(fcjyBaxxDTO.getBdcSlJyxx(), bdcSlJyxxDO);
                bdcSlJyxxDO.setJyxxid(UUIDGenerator.generate16());
                bdcSlJyxxDO.setXmid(xmid);
                if(Objects.isNull(bdcSlJyxxDO.getHtdjsj()) && Objects.nonNull(bdcSlJyxxDO.getHtbasj())){
                    bdcSlJyxxDO.setHtdjsj(bdcSlJyxxDO.getHtbasj());
                }
                bdcSlJyxxDOList.add(bdcSlJyxxDO);
                bdcSlxxInitDTO.setBdcSlJyxxDOList(bdcSlJyxxDOList);
            }
            if(CollectionUtils.isNotEmpty(fcjyBaxxDTO.getBdcSlSqr())) {
                //判断是否获取交易接口义务人,满足条件：预告登记
                Boolean sfReadywr = false;
                if (CommonConstantUtils.QLLX_YG_DM.equals(bdcSlYwxxDTO.getQllx()) && StringUtils.isBlank(bdcSlYwxxDTO.getXmid())) {
                    sfReadywr = true;
                }
                List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
                for (BdcSlSqrDO bdcSlJySqrDO : fcjyBaxxDTO.getBdcSlSqr()) {
                    //获取交易接口中权利人义务人
                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlJySqrDO.getSqrlb()) ||
                            (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlJySqrDO.getSqrlb()) && sfReadywr)) {
                        BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                        BeanUtils.copyProperties(bdcSlJySqrDO, bdcSlSqrDO);
                        if(StringUtils.isNotBlank(bdcSlJySqrDO.getSqrmc())){
                            bdcSlSqrDO.setSqrmc(StringUtils.deleteWhitespace(bdcSlJySqrDO.getSqrmc()));
                        }
                        if(StringUtils.isNotBlank(bdcSlJySqrDO.getZjh())){
                            bdcSlSqrDO.setZjh(StringUtils.deleteWhitespace(bdcSlJySqrDO.getZjh()));
                        }
                        bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
                        bdcSlSqrDO.setXmid(xmid);
                        bdcSlSqrDOList.add(bdcSlSqrDO);
                    }
                }
                bdcSlxxInitDTO.setBdcSlSqrDOList(bdcSlSqrDOList);
            }
            if(fcjyBaxxDTO.getBdcSlFwxx() != null){
                List<BdcSlFwxxDO> bdcSlFwxxDOList =new ArrayList<>();
                BdcSlFwxxDO bdcSlFwxxDO =new BdcSlFwxxDO();
                BeanUtils.copyProperties(fcjyBaxxDTO.getBdcSlFwxx(), bdcSlFwxxDO);
                bdcSlFwxxDO.setFwxxid(UUIDGenerator.generate16());
                bdcSlFwxxDO.setXmid(xmid);
                bdcSlFwxxDOList.add(bdcSlFwxxDO);
                bdcSlxxInitDTO.setBdcSlFwxxDOList(bdcSlFwxxDOList);
            }

            List<BdcSlSqrDO> curBdcSlSqrDOList = CollectionUtils.isNotEmpty(bdcSlxxInitDTO.getBdcSlSqrDOList())?bdcSlxxInitDTO.getBdcSlSqrDOList():new ArrayList<>();

            // 处理抵押权利
            List<BdcSlQl> bdcSlQlList =new ArrayList<>();
            if(fcjyBaxxDTO.getListBdcSlDyaqDTOSy() != null){

                linkDyaXmid(xmid,fcjyBaxxDTO.getListBdcSlDyaqDTOSy(),bdcSlYwxxDTO.getDjxl(),gzldyid,bdcSlYwxxDTO.getQllx());

                for(BdcSlDyaqDTO bdcSlDyaqDTO : fcjyBaxxDTO.getListBdcSlDyaqDTOSy()){
                    if (Objects.nonNull(bdcSlDyaqDTO.getBdcSlDyaqDO()) && StringUtils.isNotBlank(bdcSlDyaqDTO.getBdcSlDyaqDO().getXmid())) {
                        bdcSlDyaqDTO.getBdcSlDyaqDO().setId(UUIDGenerator.generate16());
                        bdcSlQlList.add(bdcSlDyaqDTO.getBdcSlDyaqDO());
                        dealSlDyaqSqr(bdcSlDyaqDTO, curBdcSlSqrDOList);
                        // 处理完商业之后 不再继续处理 这里商业只有一个抵押数据，不会存在多个商业贷款
                        fcjyBaxxDTO.setListBdcSlDyaqDTOSy(null);
                    }
                }
            }
            if(fcjyBaxxDTO.getListBdcSlDyaqDTOGjj() != null){
                linkDyaXmid(xmid,fcjyBaxxDTO.getListBdcSlDyaqDTOGjj(),bdcSlYwxxDTO.getDjxl(),gzldyid,bdcSlYwxxDTO.getQllx());
                for(BdcSlDyaqDTO bdcSlDyaqDTO : fcjyBaxxDTO.getListBdcSlDyaqDTOGjj()){
                    if (Objects.nonNull(bdcSlDyaqDTO.getBdcSlDyaqDO()) && StringUtils.isNotBlank(bdcSlDyaqDTO.getBdcSlDyaqDO().getXmid())) {
                        bdcSlDyaqDTO.getBdcSlDyaqDO().setId(UUIDGenerator.generate16());
                        bdcSlQlList.add(bdcSlDyaqDTO.getBdcSlDyaqDO());
                        dealSlDyaqSqr(bdcSlDyaqDTO, curBdcSlSqrDOList);
                        // 处理完公积金之后 不再继续处理 这里商业只有一个抵押数据，不会存在多个商业贷款
                        fcjyBaxxDTO.setListBdcSlDyaqDTOGjj(null);

                    }
                }
            }
            if(fcjyBaxxDTO.isModifyDsQlr()) {
                modifyDsQlr(xmid, fcjyBaxxDTO.getBdcQlr(), false);
            }
            bdcSlxxInitDTO.setBdcSlSqrDOList(curBdcSlSqrDOList);
            bdcSlxxInitDTO.setBdcSlQlList(bdcSlQlList);

            LOGGER.info("fcjybaxxdto处理完毕：{}",JSONObject.toJSONString(bdcSlxxInitDTO));

        }
    }

    /**
     * 通过sldyaq中的贷款方式 映射slxm的xmid
     * @param xmid
     * @param bdcSlDyaqDTOList
     */
    private void linkDyaXmid(String xmid,List<BdcSlDyaqDTO> bdcSlDyaqDTOList,String djxl,String gzldyid,Integer qllx){

        boolean zhlc = false;
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzService.queryBdcDjxlPzByGzldyid(gzldyid);
        // 登记小类配置大于3条时 认定为组合流程 此时匹配抵押权的xmid 不需要通过djxl
        if(CollectionUtils.isNotEmpty(bdcDjxlPzDOList) && bdcDjxlPzDOList.size() > 2){
            zhlc = true;
        }
        // 是组合按揭流程
        if(zhlc){
            for(BdcSlDyaqDTO bdcSlDyaqDTO : bdcSlDyaqDTOList){
                LOGGER.info("映射slxm的xmid,djlx分别为：{}{}",bdcSlDyaqDTO.getDjxl(),djxl);
                if (StringUtils.equals(bdcSlDyaqDTO.getDjxl(), djxl) && Objects.nonNull(bdcSlDyaqDTO.getBdcSlDyaqDO())) {
                    bdcSlDyaqDTO.getBdcSlDyaqDO().setXmid(xmid);
                }
            }
        }else{
            // 存量房按揭 商品房按揭
            for(BdcSlDyaqDTO bdcSlDyaqDTO : bdcSlDyaqDTOList){
                if (qllx.equals(CommonConstantUtils.QLLX_DYAQ_DM) && Objects.nonNull(bdcSlDyaqDTO.getBdcSlDyaqDO())) {
                    bdcSlDyaqDTO.getBdcSlDyaqDO().setXmid(xmid);
                }
            }
        }
    }

    /**
     * 处理抵押权中的抵押权利人
     * @param bdcSlDyaqDTO
     * @param curBdcSlSqrDOList
     */
    private void dealSlDyaqSqr(BdcSlDyaqDTO bdcSlDyaqDTO,List<BdcSlSqrDO> curBdcSlSqrDOList){
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlDyaqDTO.getBdcSlSqrDOList();
        // 抵押权中 产权的权利人要作为抵押的义务人
        if(CollectionUtils.isNotEmpty(curBdcSlSqrDOList)){
            for(BdcSlSqrDO bdcSlSqrDO:curBdcSlSqrDOList){
                bdcSlSqrDO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
            }
        }
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
            for(BdcSlSqrDO bdcSlDyaqSqrDO:bdcSlSqrDOList){
                //获取交易接口中权利人义务人
                BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
                BeanUtils.copyProperties(bdcSlDyaqSqrDO, bdcSlSqrDO);
                bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
                bdcSlSqrDO.setXmid(bdcSlDyaqDTO.getBdcSlDyaqDO().getXmid());
                curBdcSlSqrDOList.add(bdcSlSqrDO);
            }
        }
    }

    /**
     * @param bdcSlJyxxDO 受理交易信息
     * @param gzlslid 工作流实例ID
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量插入受理交易信息
     */
    private int insertBatchBdcSlJyxx(BdcSlJyxxDO bdcSlJyxxDO, String gzlslid,String djxl) {
        int count = 0;
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        List<BdcSlJyxxDO> jyxxDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                if(StringUtils.isBlank(djxl) ||StringUtils.equals(djxl,bdcXmDTO.getDjxl())) {
                    //传入登记小类为空或者传入登记小类与受理项目登记小类一致，才生成交易信息
                    BdcSlJyxxDO jyxxDO = new BdcSlJyxxDO();
                    BeanUtils.copyProperties(bdcSlJyxxDO, jyxxDO);
                    jyxxDO.setXmid(bdcXmDTO.getXmid());
                    jyxxDO.setJyxxid(UUIDGenerator.generate16());
                    jyxxDOList.add(jyxxDO);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(jyxxDOList)) {
            List<List> partList = ListUtils.subList(jyxxDOList, 500);
            for (List data : partList) {
                count += entityMapper.insertBatchSelective(data);
            }
        }
        return count;
    }

    /**
     * @param bdcSlJyxxDO 受理交易信息
     * @param
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量插入受理交易信息
     */
    private int insertBatchBdcSlJyxxByXmids(BdcSlJyxxDO bdcSlJyxxDO, List<String> xmids) {
        int count = 0;
        List<BdcSlJyxxDO> jyxxDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(xmids)) {
            for (String xmid : xmids) {
                BdcSlJyxxDO jyxxDO = new BdcSlJyxxDO();
                BeanUtils.copyProperties(bdcSlJyxxDO, jyxxDO);
                jyxxDO.setXmid(xmid);
                jyxxDO.setJyxxid(UUIDGenerator.generate16());
                jyxxDOList.add(jyxxDO);
            }
        }
        if (CollectionUtils.isNotEmpty(jyxxDOList)) {
            List<List> partList = ListUtils.subList(jyxxDOList, 500);
            for (List data : partList) {
                count += entityMapper.insertBatchSelective(data);
            }
        }
        return count;
    }

    /**
     * @param jsonStr 更新json下字符串
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理交易信息
     */
    private int updateBatchBdcSlJyxx(String jsonStr, String gzlslid, BdcSlJyxxDO bdcSlJyxxDO,String djxl) {
        String xmid = bdcSlJyxxDO.getXmid();
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlJyxxDO.class.getName());
        if(!statement.contains("SET")) {
            return 0;
        }
        map.put("statement", statement);
        //where 条件
        map.put("record", bdcSlJyxxDO);
        if (StringUtils.isNotBlank(xmid)) {
            //where 条件
            map.put("xmid", xmid);
            LOGGER.info("更新的jyxx参数--------------：{}",map);
            return bdcSlJyxxMapper.updateXmBdcSlJyxx(map);
        } else {
            List<String> xmids =new ArrayList<>();
            List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                for(BdcXmDTO bdcXmDTO:bdcXmDTOList){
                    if(StringUtils.isBlank(djxl) ||StringUtils.equals(djxl,bdcXmDTO.getDjxl())) {
                        xmids.add(bdcXmDTO.getXmid());
                    }
                }
            }
            //where 条件
            map.put("xmids", xmids);
            return bdcSlJyxxMapper.batchUpdateBdcSlJyxx(map);
        }
    }

    /**
     * @param jsonStr 更新json下字符串
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理交易信息
     */
    private int updateBatchBdcSlJyxxByXmids(String jsonStr, List<String> xmids, BdcSlJyxxDO bdcSlJyxxDO) {
        int count =0;
        String xmid = bdcSlJyxxDO.getXmid();
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlJyxxDO.class.getName());
        if(!statement.contains("SET")) {
            return 0;
        }
        map.put("statement", statement);
        //where 条件
        map.put("record", bdcSlJyxxDO);
        if (StringUtils.isNotBlank(xmid)) {
            //where 条件
            map.put("xmid", xmid);
            return bdcSlJyxxMapper.updateXmBdcSlJyxx(map);
        } else if(CollectionUtils.isNotEmpty(xmids)) {
            //分组更新
            List<List> partList = ListUtils.subList(xmids, 1000);
            for (List data : partList) {
                //where 条件
                map.put("xmids", data);
                count +=bdcSlJyxxMapper.batchUpdateBdcSlJyxx(map);
            }

        }
        return count;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取交易信息时删除对应原有的权利人义务人
     */
    private void delQlrYwr(int xmlx,String xmid,Boolean sfdqywr){
        //删除项目表权利人信息
        if(CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
            //考虑组合流程同步问题
            List<BdcXmZhxxDTO> bdcXmZhxxDTOList = bdcXmFeignService.queryBdcXmZhxx(xmid);
            if (CollectionUtils.isNotEmpty(bdcXmZhxxDTOList)) {
                for (int j = 0; j < bdcXmZhxxDTOList.size(); j++) {
                    BdcXmZhxxDTO bdcXmZhxxDTO = bdcXmZhxxDTOList.get(j);
                    if (!StringUtils.equals(bdcXmZhxxDTO.getXmid(), xmid)) {
                        if (bdcXmZhxxDTO.getSxh() == 1) {
                            //组合另一个项目为第一手,如果为转移登记,删除权利人信息,将房产交易接口获取的义务人作为权利人
                            if(CommonConstantUtils.DJLX_ZYDJ_DM.equals(bdcXmZhxxDTO.getDjlx()) &&Boolean.TRUE.equals(sfdqywr)) {
                                bdcQlrFeignService.delQlr(bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                            }
                        }
                        if (bdcXmZhxxDTO.getSxh() == 2) {
                            //查询组合项目房屋开关表
                            BdcCshFwkgSlDO bdcCshFwkgSl =bdcXmFeignService.queryFwkgsl(bdcXmZhxxDTO.getXmid());
                            if(bdcCshFwkgSl != null){
                                //如果权利人或义务人数据来源为原权利人,同步删除权利人或义务人
                                if(Constants.QLRSJLY_YQLR.equals(bdcCshFwkgSl.getQlrsjly())){
                                    bdcQlrFeignService.delQlr(bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                                }
                                if(Constants.QLRSJLY_YQLR.equals(bdcCshFwkgSl.getYwrsjly())){
                                    bdcQlrFeignService.delQlr(bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                                }
                            }
                        }
                    }else{
                        if (bdcXmZhxxDTO.getSxh() == 1) {
                            //当前转移为第一手项目
                            bdcQlrFeignService.delQlr(xmid,CommonConstantUtils.QLRLB_QLR);
                            if(sfdqywr){
                                //删除义务人
                                bdcQlrFeignService.delQlr(xmid,CommonConstantUtils.QLRLB_YWR);
                            }
                        }
                        if (bdcXmZhxxDTO.getSxh() == 2) {
                            //当前转移为第二手项目,上一手为转移，删除原有权利人义务人，均以房产交易接口为准；上一手不为转移，不删除义务人
                            bdcQlrFeignService.delQlr(xmid,CommonConstantUtils.QLRLB_QLR);
                            if(CommonConstantUtils.DJLX_ZYDJ_DM.equals(bdcXmZhxxDTOList.get(0).getDjlx()) &&sfdqywr) {
                                bdcQlrFeignService.delQlr(xmid, CommonConstantUtils.QLRLB_YWR);
                            }

                        }

                    }
                }
            }
        }else {
            //删除权利人
            bdcQlrFeignService.delQlr(xmid, CommonConstantUtils.QLRLB_QLR);
            if(sfdqywr){
                //如果需要读取义务人,删除原有的义务人
                bdcQlrFeignService.delQlr(xmid, CommonConstantUtils.QLRLB_YWR);

            }

        }
    }

    @Override
    public String queryHfwxzjJnzt(String xmid){
        String wxzjjnzt ="";
        if(StringUtils.isNotBlank(xmid)){
            List<BdcSlSqrDO> bdcSlSqrDOList =bdcSlSqrService.listBdcSlSqrByXmid(xmid,CommonConstantUtils.QLRLB_QLR);
            if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
                BdcSlJyxxDO bdcSlJyxxDO =new BdcSlJyxxDO();
                List<BdcSlJyxxDO> bdcSlJyxxDOList =listBdcSlJyxxByXmid(xmid);
                if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
                    bdcSlJyxxDO =bdcSlJyxxDOList.get(0);
                }
                BdcSlFwxxDO bdcSlFwxxDO =new BdcSlFwxxDO();
                List<BdcSlFwxxDO> bdcSlFwxxDOList =bdcSlFwxxService.listBdcSlFwxxByXmid(xmid);
                if(CollectionUtils.isNotEmpty(bdcSlFwxxDOList)){
                    bdcSlFwxxDO =bdcSlFwxxDOList.get(0);
                }
                if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
                    bdcSlJyxxDO =bdcSlJyxxDOList.get(0);
                }
                for(int i = 0 ; i < bdcSlSqrDOList.size() ; i++){
                    BdcSlSqrDO bdcSlSqrDO =bdcSlSqrDOList.get(i);
                    WxzjYzRequestDTO wxzjYzRequestDTO = new WxzjYzRequestDTO();
                    //合同编号
                    wxzjYzRequestDTO.setNewHouseBarginNo(bdcSlJyxxDO.getHtbh());
                    //幢号
                    wxzjYzRequestDTO.setBuildingNo(bdcSlFwxxDO.getFwdh());
                    //姓名
                    wxzjYzRequestDTO.setName(bdcSlSqrDO.getSqrmc());
                    //房间号
                    wxzjYzRequestDTO.setHouseNo(bdcSlFwxxDO.getFjh());

                    //证件号
                    wxzjYzRequestDTO.setIdNo(bdcSlSqrDO.getZjh());
                    Object response = exchangeInterfaceFeignService.requestInterface("hfwxzjyz", wxzjYzRequestDTO);
                    if (response == null) {
                        throw new AppException("房产维修资金返回数据为空");
                    }
                    LOGGER.info("房产维修资金返回数据：{}", response);
                    if(Constants.WXZJJNZT_YJN.equals(response) ||Constants.WXZJJNZT_MJ.equals(response)){
                        //存在已缴或者免缴的时候直接返回状态
                        wxzjjnzt =response.toString();
                        break;
                    }else if(i == bdcSlSqrDOList.size() -1){
                        //均为未缴,维修资金缴纳状态为未缴
                        wxzjjnzt =response.toString();
                    }

                }

            }
        }
        return wxzjjnzt;

    }

    /**
     * 处理主房关联后,再次关联附房的逻辑
     * @param fcjyBaxxDTO
     * @param xmid
     */
    @Override
    public void  dealFsssBaxx(FcjyBaxxDTO fcjyBaxxDTO,String xmid){
        LOGGER.info("开始处理附属设施，备案信息实体：{}，xmid：{}",fcjyBaxxDTO,xmid);
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setXmid(xmid);
        List<BdcSlXmDO> listSlxm = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if(CollectionUtils.isEmpty(listSlxm)){
            throw new AppException("未找到受理项目信息,xmid"+xmid);
        }
        String jbxxid = listSlxm.get(0).getJbxxid();
        dealFwxx(fcjyBaxxDTO.getBdcSlFwxx(), xmid, jbxxid, fcjyBaxxDTO.getBdcSlJyxx());
    }

    @Override
    public void deleteBdcSlJyxx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        if (!CheckParameter.checkAnyParameter(bdcSlDeleteCsDTO)) {
            throw new AppException("删除参数为空" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlJyxxMapper.deleteBdcSlJyxx(bdcSlDeleteCsDTO);

    }

    /**
     * 处理主房和附房 同时关联的情况
     *
     * @param bdcSlFwxxDO,bdcSlJyxx
     */
    private void dealBaxx(BdcSlFwxxDO bdcSlFwxxDO, BdcSlJyxxDO bdcSlJyxx, String xmid) {
        Integer fwyt = bdcSlFwxxDO.getFwyt();
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        String fwytmc = StringToolUtils.convertBeanPropertyValueOfZd(fwyt, zdMap.get("fwyt"));


        /*车库的备案合同号为：201030391293912，
        车库的坐落为：丽都花园4幢09室，车库的交易价格为20万元，
        车库合同签订时间2019年10月20日*/
        if (StringUtils.isEmpty(fwytmc)) {
            throw new AppException("房屋用途没有值！");
        }
        String curBz = "";

        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setXmid(xmid);
        List<BdcSlXmDO> listSlxm = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if(CollectionUtils.isEmpty(listSlxm)){
            throw new AppException("未查询到受理项目信息！");
        }
        String jbxxid = listSlxm.get(0).getJbxxid();
        List<BdcSlXmDO> listAllSlxm = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid,"");

        BdcSlFwxxDO thisBdcSlFwxxDO = null;
        BdcSlJyxxDO bdcSlJyxxDO = null;
        List<String> notfsssxmids = new ArrayList<>();

        for(int i=0;i<listAllSlxm.size();i++){
            Integer sfzf = listAllSlxm.get(i).getSfzf();
            LOGGER.info("当前xm是否主房：{}",sfzf);

            String curXmid = listAllSlxm.get(i).getXmid();
            List<BdcSlFwxxDO> listFw = bdcSlFwxxService.listBdcSlFwxxByXmid(curXmid);
            List<BdcSlJyxxDO> listJy = listBdcSlJyxxByXmid(curXmid);

            LOGGER.info("遍历受理项目查询房屋和交易信息：{}，{}",listFw,listJy);

            if(CollectionUtils.isNotEmpty(listFw)){

                boolean curYtFsssflag = (sfzf==0);
                if(!curYtFsssflag){
                    thisBdcSlFwxxDO = listFw.get(0);
                    String zfxmid = thisBdcSlFwxxDO.getXmid();
                    listJy = listBdcSlJyxxByXmid(zfxmid);
                    LOGGER.info("通过主房的xmid获取交易信息：{}，{}",zfxmid,listJy);
                    if(CollectionUtils.isNotEmpty(listJy) && bdcSlJyxxDO == null){
                        bdcSlJyxxDO = listJy.get(0);
                    }
                }else{
                    LOGGER.info("非主房的xmid：{}",curXmid);
                    notfsssxmids.add(curXmid);
                }
            }
        }

        if(thisBdcSlFwxxDO == null ){
            return;
        }

        // 获取备注
        for(int i=0;i<notfsssxmids.size();i++){
            List<BdcSlFwxxDO> listFw = bdcSlFwxxService.listBdcSlFwxxByXmid(notfsssxmids.get(i));
            List<BdcSlJyxxDO> listJy = listBdcSlJyxxByXmid(notfsssxmids.get(i));
            bdcSlXmQO.setXmid(notfsssxmids.get(i));
            List<BdcSlXmDO> listxm = bdcSlXmService.listBdcSlXm(bdcSlXmQO);

            LOGGER.info("非主房的xmid查询的房屋，交易，项目数据：{}，{}，{}",listFw,listJy,listxm);

            if(CollectionUtils.isNotEmpty(listFw)
                    && CollectionUtils.isNotEmpty(listJy)
                    && CollectionUtils.isNotEmpty(listxm)) {
                String htbh1 = listJy.get(0).getHtbh();
                String zl1 = listxm.get(0).getZl();
                DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日");
                String dateStr1 = null;
                if (null != bdcSlJyxx.getHtbasj()) {
                    dateStr1 = df1.format(bdcSlJyxx.getHtbasj());
                } else {
                    dateStr1 = "";
                }
                StringBuilder bzSb = new StringBuilder("");
                String mc = getMcByFsssYt(listFw.get(i).getFwyt());
                bzSb.append(mc + "的备案合同号为：" + htbh1 + "，")
                        .append(mc + "的坐落为：" + zl1 + "，")
                        .append(mc + "合同签订时间" + dateStr1 + "。");
                if (StringUtils.isNotEmpty(htbh1)) {
                    curBz += bzSb.toString();
                }
            }
        }

        if(StringUtils.isEmpty(curBz)){
            return;
        }
        LOGGER.info("备注描述：{}",curBz);
        List<BdcSlJyxxDO> list = this.listBdcSlJyxxByXmid(thisBdcSlFwxxDO.getXmid());
        if(CollectionUtils.isNotEmpty(list)){
            bdcSlJyxxDO = list.get(0);
            LOGGER.info("根据当前项目id交易信息，xmid：{}，jyxxlist:{}",thisBdcSlFwxxDO.getXmid(),list);
        }
        if(bdcSlJyxxDO !=null){
            LOGGER.info("执行更新操作，新备注内容：{}",curBz);
            bdcSlJyxxDO.setBz(curBz);
            this.updateSlJyxxByXmid(thisBdcSlFwxxDO.getXmid(), JSON.toJSONString(bdcSlJyxxDO),null);
        }
    }

    /**
     * @param bdcSlJyxxDO 交易信息
     * @return 不动产受理交易信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增不动产受理交易信息
     */
    private void insertBdcSlJyxxSingle(BdcSlJyxxDO bdcSlJyxxDO) {
        List<BdcSlJyxxDO> bdcSlJyxxDOList = this.listBdcSlJyxxByXmid(bdcSlJyxxDO.getXmid());
        // 判断当前XMID是否拥有交易信息，有则直接更新，无则添加交易信息
        if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
            bdcSlJyxxDO.setJyxxid(bdcSlJyxxDOList.get(0).getJyxxid());
            updateBatchBdcSlJyxx(JSON.toJSONString(bdcSlJyxxDO), null, bdcSlJyxxDO, null);
        }else{
            if (StringUtils.isBlank(bdcSlJyxxDO.getJyxxid())) {
                bdcSlJyxxDO.setJyxxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlJyxxDO);
        }
    }

    /**
     * 根据附属设施用途获取名称
     * @param fwyt
     * @return
     */
    public String getMcByFsssYt(Integer fwyt) {
        if(fwyt == null){
            return "";
        }
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        String fwytmc = StringToolUtils.convertBeanPropertyValueOfZd(fwyt, zdMap.get("fwyt"));
        if(fwytmc.indexOf("车库")>-1 ){
            return "车库";
        }
        if(fwytmc.indexOf("阁楼")>-1 ){
            return "阁楼";
        }
        if(fwytmc.indexOf("储藏室")>-1 || fwytmc.indexOf("地下室")>-1){
            return "储藏室";
        }
        if(fwytmc.indexOf("车位")>-1 ||  fwytmc.indexOf("物管")>-1){
            return  "车库";
        }
        return "";
    }

    @Override
    public void batchDelete(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        deleteBdcSlJyxx(bdcSlDeleteCsDTO);
    }

    /**
     * 判断此次交易信息的用途是否能映射到受理房屋信息里面
     * @param map
     * @param fwyt
     * @return
     */
    private Map includeFwyt(Map<Integer,String> map, Integer fwyt){
        Map result = new HashMap();
        result.put("flag",false);
        result.put("xmid","");
        if(map.containsKey(fwyt)){
            result.put("flag",true);
            result.put("xmid",map.get(fwyt));
            return result;
        }else{
           for(Map.Entry<String, List<String>> entry : jyxxFwytYddConfig.getFwytMap().entrySet()){
                List<String> listYsYt = entry.getValue();
                for(int i=0;i<listYsYt.size();i++){
                    if(map.containsKey(Integer.parseInt(listYsYt.get(i)))){
                        result.put("flag",true);
                        result.put("xmid",map.get(Integer.parseInt(listYsYt.get(i))));
                        break;
                    }
                }
           }
        }
        return result;
    }

    /**
     * 常州房产交易接口成功状态CODE
     */
    private final static String CZ_FCJYHTXX_SUCCESS_CODE = "00000000";

    /**
     * 调用合同交易备案信息接口，获取房产交易合同交易信息
     * @param htbh 合同编号
     * @param fwlx 房屋类型
     * @param xmid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 房产交易合同信息
     */
    @Override
    public Object queryFcjyHtxxByHtbh(String htbh, String fwlx, String xmid,boolean sfck) {
        if(StringUtils.isBlank(htbh)){
            throw new MissingArgumentException("未获取到合同编号信息。");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("contractNo", htbh);
        //如果不传入房屋类型，根据配置判断
        if (StringUtils.isBlank(fwlx)) {
            if (StringUtils.isNotBlank(xmid)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    if (CollectionUtils.isNotEmpty(spfdyidList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid()) && spfdyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                        fwlx = CommonConstantUtils.FCJY_TYPE_SPF;
                    } else {
                        fwlx = CommonConstantUtils.FCJY_TYPE_CLF;
                    }
                }
            }
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList =new ArrayList<>();
        if(sfck) {
           bdcSlJyxxDOList = listBdcSlJyxxByXmid(xmid);
       }

        if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList) &&StringUtils.isNoneBlank(bdcSlJyxxDOList.get(0).getHtbh(),bdcSlJyxxDOList.get(0).getZl())){
            //数据库存在,组织数据库数据到前台
            JyxxResponseDTO jyxxResponseDTO =new JyxxResponseDTO();
            jyxxResponseDTO.setHtxx(Object2MapUtils.object2MapExceptNull(bdcSlJyxxDOList.get(0)));
            jyxxResponseDTO.getHtxx().put("fwlx",fwlx);
            jyxxResponseDTO.setQlrsjly(Constants.JYQLRSJLY_DSQLR);
            queryJyxxResponseDTOQlr(jyxxResponseDTO,xmid);
            return jyxxResponseDTO;
        }else {
            Object response = new Object();
            // 存量房传义务人名称；商品房传权利人名称
            if (StringUtils.equals(fwlx, CommonConstantUtils.FCJY_TYPE_CLF)) {
                paramMap.put("xm", getBdcQlrmc(xmid, CommonConstantUtils.QLRLB_YWR));
                LOGGER.info("查询存量房合同信息接口，请求参数：{}", paramMap);
                response = exchangeInterfaceFeignService.requestInterface("czFcjyClfHtxx", paramMap);
            } else if (StringUtils.equals(fwlx, CommonConstantUtils.FCJY_TYPE_SPF)) {
                paramMap.put("xm", getBdcQlrmc(xmid, CommonConstantUtils.QLRLB_QLR));
                LOGGER.info("查询商品房合同信息接口，请求参数：{}", paramMap);
                response = exchangeInterfaceFeignService.requestInterface("czFcjySpfBaxx", paramMap);
            }
            LOGGER.info("合同编号:{},查询合同信息接口调用成功，响应内容：{}", htbh, response);
            if (Objects.nonNull(response)) {
                Map map = (Map) response;
                if (StringUtils.equals(CZ_FCJYHTXX_SUCCESS_CODE, (CharSequence) map.get("code"))) {
                    map.put("fwlx", fwlx);
                    //处理保存
                    if(sfck) {
                        filterCzSpfClfHtxx(map, xmid);
                    }
                    return map;

                }
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目ids查询受理交易信息
     */
    @Override
    public List<BdcSlJyxxDO> listBdcSlJyxxByXmids(BaseQO baseQO) {
        if (Objects.isNull(baseQO) || CollectionUtils.isEmpty(baseQO.getIds())) {
            return Lists.newArrayList();
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList =new ArrayList<>();
        List<List> lists = ListUtils.subList(baseQO.getIds(), 500);
        for (List partXmids : lists) {
            List<BdcSlJyxxDO> jyxxDOList =bdcSlJyxxMapper.listBdcSlJxxByXmids(partXmids);
            if(CollectionUtils.isNotEmpty(jyxxDOList)){
                bdcSlJyxxDOList.addAll(jyxxDOList);
            }
        }
        return bdcSlJyxxDOList;
    }


    @Override
    public boolean checkHtbhLinked(String htbh,String fwyt){
        if(StringUtils.isBlank(htbh)){
            throw new MissingArgumentException("缺失参数合同编号。");
        }
        //先查询当前合同编号是否已存在记录
        List<BdcSlJyxxDO> bdcSlJyxxDOList = listBdcSlJyxxByHtbh(htbh);

        //不存在记录,返回未关联
        if(CollectionUtils.isEmpty(bdcSlJyxxDOList)){
            return false;
        }

        //存在记录,入参fwyt为空,返回已关联
        if(StringUtils.isBlank(fwyt)){
            return true;
        }

        //入参存在fwyt
        //判断是否需要对照
        if(jyxxFwytYddConfig.getFwytMap().containsKey(fwyt)){
            fwyt =MapUtils.getString(jyxxFwytYddConfig.getFwytMap(),fwyt);
        }
        //如果房屋用途与数据库交易信息中用途一致,返回已关联,否则返回未关联
        List<BdcSlFwxxDO> bdcSlFwxxDOList =bdcSlFwxxService.listBdcSlFwxxByXmid(bdcSlJyxxDOList.get(0).getXmid());
        return CollectionUtils.isNotEmpty(bdcSlFwxxDOList) &&bdcSlFwxxDOList.get(0).getFwyt() != null &&StringUtils.equals(fwyt,bdcSlFwxxDOList.get(0).getFwyt().toString());

    }

    @Override
    public void updateJyxxByHtbh(JSONObject jsonObject, String htbh){
        if(jsonObject ==null ||StringUtils.isBlank(htbh)){
            throw new AppException("更新交易信息缺失参数");
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList =listBdcSlJyxxByHtbh(htbh);
        if(CollectionUtils.isEmpty(bdcSlJyxxDOList)){
            throw new AppException("当前不动产受理编号未查询到交易信息"+htbh);
        }
        for(BdcSlJyxxDO jyxxDO:bdcSlJyxxDOList){
            jsonObject.put("jyxxid",jyxxDO.getJyxxid());
            entityService.updateByJsonEntity(JSONObject.toJSONString(jsonObject),BdcSlJyxxDO.class);
        }

    }

    /**
     * 根据项目ID与申请人类别获取第一个申请人的名称
     */
    private String getBdcQlrmc(String xmid, String qlrlb){
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(qlrlb);
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            return bdcQlrDOList.get(0).getQlrmc();
        }
        return "";
    }

    /**
     * 查询房产交易信息并导入数据
     * <p>使用needImport控制是否导入查询到房产交易数据
     * @param fcjyxxQO 房产交易QO
     * @return
     */
    @Override
    @Transactional
    public FcjyBaxxDTO queryFcjyxxWithImport(FcjyxxQO fcjyxxQO){
        if (!CheckParameter.checkAnyParameter(fcjyxxQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        Map<String, Object> paramMap = new HashMap<>();
        String htbh = fcjyxxQO.getHtbh();
        paramMap.put("contractNo", CommonUtil.getOrElse(htbh, ""));
        paramMap.put("xm", CommonUtil.getOrElse(fcjyxxQO.getQlrmc(), ""));
        paramMap.put("zjh",  CommonUtil.getOrElse(fcjyxxQO.getZjh(), ""));
        paramMap.put("fwbm", CommonUtil.getOrElse(fcjyxxQO.getFwbm(), ""));
        LOGGER.info("合同编号:{}, 查询合同交易信息beanName：{}，请求参数：{}", htbh, fcjyxxQO.getBeanName(), paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface(fcjyxxQO.getBeanName(), paramMap);
        LOGGER.info("合同编号:{}, 查询合同信息接口调用成功，响应内容：{}", htbh, JSON.toJSONString(response));
        FcjyBaxxDTO fcjyBaxxDTO = JSONObject.parseObject(JSON.toJSONString(response), FcjyBaxxDTO.class);

        // 判断前端是否指定需要导入交易信息内容，默认不导入
        if(fcjyxxQO.isNeedImport() && StringUtils.isNotBlank(fcjyxxQO.getXmid())){
            fcjyBaxxDTO.setXmid(fcjyxxQO.getXmid());
            BeansResolveUtils.clonePropertiesValue(fcjyxxQO, fcjyBaxxDTO);
            handleFcjyxx(fcjyBaxxDTO, fcjyxxQO.getLclx());
        }
        return fcjyBaxxDTO;
    }

    /**
     * 处理房产交易信息包含以下几点内容：
     * 1、处理不动产项目中的交易合同号与权利信息中的交易金额
     * 2、处理不动产受理交易信息，更新为房产接口中的交易信息内容
     * 3、处理房屋信息，使用接口返回的房屋信息补全受理库中的房屋信息
     * 4、更新登记权利人信息，先删除原有权利人与义务人信息，在新增接口返回人员信息
     * 5、更新受理申请人信息，只删除权利人与家庭成员信息，保留义务人数据。在新增交易接口中的权利人信息
     *
     * @param fcjyBaxxDTO 房产交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void handleFcjyxx(FcjyBaxxDTO fcjyBaxxDTO, String lclx){
        String xmid = fcjyBaxxDTO.getXmid();
        // 处理受理交易信息
        if(fcjyBaxxDTO.isModifyJyxx()){
            this.handleSlJyxx(xmid, fcjyBaxxDTO.getBdcSlJyxx());
        }
        // 处理受理房屋信息数据
        if(fcjyBaxxDTO.isModifyFwxx()){
            this.handleSlFwxx(xmid, fcjyBaxxDTO.getBdcSlFwxx());
        }

        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(new BdcXmQO(xmid));
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            fcjyBaxxDTO.setGzlslid(bdcXmDO.getGzlslid());

            // 处理项目交易合同号与权利信息交易金额
            if(fcjyBaxxDTO.isModifyXmAndQlxx()){
                this.handleBdcXmAndQlxx(fcjyBaxxDTO);
            }

            //更新登记权利人信息与受理申请人信息
            if(fcjyBaxxDTO.isModifyQlrxx()){
                if(StringUtils.equals(CommonConstantUtils.LCLX_ZH_STR, lclx)){
                    handleQlrxx(bdcXmDO, fcjyBaxxDTO.getBdcQlr(),null);
                    handleSlSqrxx(xmid, fcjyBaxxDTO.getBdcSlSqr());
                }else{
                    // 2020-02-19：当lclx为批量流程时，一个项目获取同步处理权利人信息
                    List<BdcXmDO> bdcXmDOS = this.bdcXmFeignService.listBdcXm(
                            new BdcXmQO().withGzlslid(bdcXmDO.getGzlslid())
                    );
                    if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                        for (BdcXmDO bdcxm : bdcXmDOS) {
                            handleQlrxx(bdcxm, fcjyBaxxDTO.getBdcQlr(),null);
                            handleSlSqrxx(bdcxm.getXmid(), fcjyBaxxDTO.getBdcSlSqr());
                        }
                    }
                }
            }
        }
    }

    /**
     * @param qlrxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人是否限购，返回名称证件号和是否限购
     * @date : 2021/4/20 15:01
     */
    @Override
    public XgxxDTO listQlrXgxx(List<Object> qlrxx) {
        if (CollectionUtils.isEmpty(qlrxx)) {
            return null;
        }
        LOGGER.info("验证限购信息权利人数据{}", qlrxx);
        for (Object o : qlrxx) {
            List<String> paramList = Arrays.asList(String.valueOf(o).split(CommonConstantUtils.ZF_YW_XG));
            if (CollectionUtils.isEmpty(paramList) || paramList.size() != 3) {
                //此处为了方便解析参数，做了固定只能长度为三，且名称证件号，单元号顺序不可改变
                continue;
            }
            String qlrmc = paramList.get(0);
            String zjh = paramList.get(1);
            String bdcdyh = paramList.get(2);
            if (StringUtils.isAnyBlank(qlrmc, zjh, bdcdyh)) {
                continue;
            }
            String beanName = "hfFcjyXgxx_http";
            // 参数可使用MAP类型传递，也可以定义请求实体传递
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("cardNo", zjh);
            paramMap.put("name", qlrmc);
            paramMap.put("bdcdyh", bdcdyh);
            LOGGER.info("限购接口查询入参{}", paramMap);
            Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
            if (response != null) {
                LOGGER.info("限购接口查询结果{}", response);
                XgxxHttpResponseDTO xgxxHttpResponseDTO = JSONObject.parseObject(JSON.toJSONString(response), XgxxHttpResponseDTO.class);
                if (StringUtils.equals("00000", xgxxHttpResponseDTO.getCode())) {
                    if (!StringUtils.equals(CommonConstantUtils.SF_ZW_S, xgxxHttpResponseDTO.getData().getIsCanBuy())) {
                        //返回不为“是”，直接返回当前人员信息说明存在限购
                        XgxxDTO xgxxDTO = new XgxxDTO();
                        xgxxDTO.setIsCanBuy(xgxxHttpResponseDTO.getData().getIsCanBuy());
                        xgxxDTO.setMc(qlrmc);
                        xgxxDTO.setZjh(zjh);
                        xgxxDTO.setBdcdyh(bdcdyh);
                        return xgxxDTO;
                    }
                } else {
                    //查不到的人说明不通过,直接返回
                    XgxxDTO xgxxDTO = new XgxxDTO();
                    xgxxDTO.setIsCanBuy(xgxxHttpResponseDTO.getCode());
                    xgxxDTO.setZjh(zjh);
                    xgxxDTO.setBdcdyh(bdcdyh);
                    xgxxDTO.setMc(qlrmc);
                    return xgxxDTO;
                }
            }
        }
        return null;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人限购信息
     * @date : 2021/4/21 11:30
     */
    @Override
    public List<XgxxDTO> listXgxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }
        List<XgxxDTO> xgxxDTOList = new ArrayList<>(1);
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            String beanName = "hfFcjyXgxx_http";
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDTO.getXmid());
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                        // 参数可使用MAP类型传递，也可以定义请求实体传递
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("cardNo", bdcQlrDO.getZjh());
                        paramMap.put("name", bdcQlrDO.getQlrmc());
                        paramMap.put("bdcdyh", bdcXmDTO.getBdcdyh());
                        LOGGER.info("限购接口查询入参{}", paramMap);
                        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
                        if (response != null) {
                            LOGGER.info("限购接口查询结果{}", response);
                            XgxxHttpResponseDTO xgxxHttpResponseDTO = JSONObject.parseObject(JSON.toJSONString(response), XgxxHttpResponseDTO.class);
                            //返回不为“是”，直接返回当前人员信息说明存在限购
                            XgxxDTO xgxxDTO = new XgxxDTO();
                            if (StringUtils.equals("00000", xgxxHttpResponseDTO.getCode()) && Objects.nonNull(xgxxHttpResponseDTO.getData())) {
                                xgxxDTO.setIsCanBuy(xgxxHttpResponseDTO.getData().getIsCanBuy());
                            } else {
                                xgxxDTO.setIsCanBuy("否");
                            }
                            xgxxDTO.setMc(bdcQlrDO.getQlrmc());
                            xgxxDTO.setZjh(bdcQlrDO.getZjh());
                            xgxxDTO.setBdcdyh(bdcXmDTO.getBdcdyh());
                            xgxxDTOList.add(xgxxDTO);
                        }
                    }
                }
            }
        }
        return xgxxDTOList;
    }

    @Override
    public void dealSlJyxx(FcjyBaxxDTO fcjyBaxxDTO,String gzlslid,String cqxmid){
        if(null == fcjyBaxxDTO){
            throw new AppException("未获取到房产交易合同信息！");
        }
        BdcSlJyxxDO bdcSlJyxxDO = fcjyBaxxDTO.getBdcSlJyxx();
        if(null == bdcSlJyxxDO || StringUtils.isBlank(cqxmid)){
            throw new AppException("缺失交易信息");
        }else{
            fcjyBaxxDTO.setXmid(cqxmid);
            fcjyBaxxDTO.setGzlslid(gzlslid);
            handleBdcXmAndQlxx(fcjyBaxxDTO);
        }
        this.dealSpfClfJyxx(fcjyBaxxDTO, cqxmid,false);
        dealJyDyaqxx(fcjyBaxxDTO);
        //上传交易附件
        if(fcjyBaxxDTO.getFcjyxxQO() != null &&StringUtils.isNotBlank(fcjyBaxxDTO.getFcjyxxQO().getVersion())) {
            BdcSlJyxxAbstractService bdcSlJyxxAbstractService = InterfaceCodeBeanFactory.getBean(bdcSlJyxxAbstractServiceSet, fcjyBaxxDTO.getFcjyxxQO().getVersion());
            if (null != bdcSlJyxxAbstractService) {
                bdcSlJyxxAbstractService.uploadJyFj(fcjyBaxxDTO, gzlslid);

            }
        }

    }

    @Override
    public void dealNtDjxx(JSONObject param,String gzlslid) throws Exception{
        if(param.isEmpty()){
            throw new NullPointerException("未获取到房产交易合同信息！");
        }
        JSONArray fwArray = param.getJSONArray("fw");
        BdcSlJyxxDO bdcSlJyxx = this.convertToBdcSlJyxx(JSON.toJSONString(param.getJSONObject("bdcSlJyxx")));
        if (bdcSlJyxx == null || (StringUtils.isBlank(bdcSlJyxx.getXmid()))) {
            throw new AppException("缺失交易信息");
        }
        //部分交易信息获取到登记
        this.updateBdcXmJyhth(bdcSlJyxx.getHtbh(), gzlslid, bdcSlJyxx.getXmid());
        // 更新权利信息
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcSlJyxx.getXmid());
        if (null != bdcQl) {
            String className = "";
            if(bdcQl instanceof BdcFdcqDO){
                className = BdcFdcqDO.class.getName();
            }
            if(bdcQl instanceof BdcYgDO){
                className = BdcYgDO.class.getName();
            }
            if(StringUtils.isNotBlank(className)){
                for(int i=0; i < fwArray.size(); i++){
                    JSONObject fw = fwArray.getJSONObject(i);
                    this.modifyBdcQl(bdcSlJyxx.getXmid(), gzlslid, fw.getString("fwbm"),
                            fw.getString("je"), className);
                }
            }
        }
        BigDecimal total = new BigDecimal(bdcSlJyxx.getJyje()).divide(new BigDecimal(10000));
        bdcSlJyxx.setJyje(total.doubleValue());
        //更新交易信息
        this.updateSlJyxxByXmid(bdcSlJyxx.getXmid(),JSONObject.toJSONString(bdcSlJyxx),null);
        //上传交易附件
        FcjyBaxxDTO fcjyBaxxDTO =new FcjyBaxxDTO();
        fcjyBaxxDTO.setBdcSlJyxx(bdcSlJyxx);
        if(param.getJSONObject("fcjyxxQO") != null) {
            fcjyBaxxDTO.setFcjyxxQO(JSONObject.parseObject(JSON.toJSONString(param.getJSONObject("fcjyxxQO")), FcjyxxQO.class));
        }
        if(fcjyBaxxDTO.getFcjyxxQO() != null &&StringUtils.isNotBlank(fcjyBaxxDTO.getFcjyxxQO().getVersion())) {
            BdcSlJyxxAbstractService bdcSlJyxxAbstractService = InterfaceCodeBeanFactory.getBean(bdcSlJyxxAbstractServiceSet, fcjyBaxxDTO.getFcjyxxQO().getVersion());
            if (null != bdcSlJyxxAbstractService) {
                bdcSlJyxxAbstractService.uploadJyFj(fcjyBaxxDTO, gzlslid);

            }
        }

    }

    @Override
    public List<FcjyHtxxDTO> dealNtZjjyxx(List<FcjyClfHtxxDTO> fcjyClfHtxxDTOList, String gzlslid, String xmid, String foldName) {
        List<FcjyHtxxDTO> list = new ArrayList<>();
        for (FcjyClfHtxxDTO fcjyClfHtxxDTO : fcjyClfHtxxDTOList) {
            BdcSlJyxxDO bdcSlJyxx = fcjyClfHtxxDTO.getBdcSlJyxx();
            if (bdcSlJyxx == null) {
                throw new AppException("缺失交易信息");
            }
            //部分交易信息获取到登记
            this.updateBdcXmJyhthByXmid(bdcSlJyxx.getHtbh(), gzlslid, xmid);
            // 更新权利信息
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if (null != bdcQl) {
                String className = "";
                // 不动产单元唯一编号
                String bdcdywybh = "";
                if (bdcQl instanceof BdcFdcqDO) {
                    className = BdcFdcqDO.class.getName();
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    bdcdywybh = bdcFdcqDO.getBdcdywybh();
                }
                if (bdcQl instanceof BdcYgDO) {
                    className = BdcYgDO.class.getName();
                    BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                    bdcdywybh = bdcYgDO.getBdcdywybh();
                }
                if (StringUtils.isNotBlank(className)) {
                    try {
                        // modifyBdcQl方法中有对金额除以10000
                        this.modifyBdcQl(xmid, gzlslid, bdcdywybh,
                                String.valueOf(bdcSlJyxx.getJyje() * 10000), className);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("南通获取住建交易信息，更新权利信息失败");
                    }
                }
            }
            //更新交易信息
            this.updateSlJyxxByXmid(xmid, JSONObject.toJSONString(bdcSlJyxx), null);
            //上传交易附件
            List<TsswDataFjclXxDTO> fileList = fcjyClfHtxxDTO.getFileList();
            if (CollectionUtils.isNotEmpty(fileList)) {
                for (TsswDataFjclXxDTO fjclXxDTO : fileList) {
                    String base64Str = fjclXxDTO.getBase64();
                    // 判断base64字符串是否拥有头信息，没有添加pdf的base64头信息
                    if (!base64Str.startsWith("data:")) {
                        base64Str = CommonConstantUtils.BASE64_QZ_PDF + base64Str;
                    }
                    try {
                        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", fjclXxDTO.getFjmc(), foldName, CommonConstantUtils.WJHZ_PDF);
                        bdcPdfDTO.setBase64str(base64Str);
                        bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
                    } catch (IOException e) {
                        LOGGER.error("生成电子合同信息失败：{}", e.getMessage());
                    }
                }
            }
            // 页面展示
            FcjyHtxxDTO fcjyHtxxDTO = new FcjyHtxxDTO();
            fcjyHtxxDTO.setHtbh(bdcSlJyxx.getHtbh());
            List<BdcQlrDO> qlrDOList = fcjyClfHtxxDTO.getBdcQlr();
            List<String> msrmc = new ArrayList<>();
            List<String> cmrmc = new ArrayList<>();
            for (BdcQlrDO bdcQlrDO : qlrDOList) {
                // 1-权利人；2-义务人
                if (CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())) {
                    msrmc.add(bdcQlrDO.getQlrmc());
                    fcjyHtxxDTO.setMsrmc(msrmc);
                } else if (CommonConstantUtils.QLRLB_YWR.equals(bdcQlrDO.getQlrlb())) {
                    cmrmc.add(bdcQlrDO.getQlrmc());
                    fcjyHtxxDTO.setCmrmc(cmrmc);
                }
            }
            fcjyHtxxDTO.setZl(fcjyClfHtxxDTO.getBdcSlXmDO().getZl());
            list.add(fcjyHtxxDTO);
        }
        return list;
    }

    @Override
    public void dealNtSpfBaJyxx(List<JSONObject> ntSpfBaJyxxList, String gzlslid, String xmid, String foldName) {
        List<FcjyHtxxDTO> list = new ArrayList<>();
        for (JSONObject ntSpfBaJyxx : ntSpfBaJyxxList) {
            String htbh = ntSpfBaJyxx.getString("htbh");
            // 单位：元
            String htjg = ntSpfBaJyxx.getString("htjg");
            String base64Str = ntSpfBaJyxx.getString("filebase64");

            BdcSlJyxxDO bdcSlJyxx = new BdcSlJyxxDO();
            bdcSlJyxx.setHtbh(htbh);
            bdcSlJyxx.setJyje(Double.parseDouble(htjg));
            updateJyxx(gzlslid, xmid,bdcSlJyxx);
            //上传交易附件
            if (StringUtils.isNotBlank(base64Str)) {
                // 判断base64字符串是否拥有头信息，没有添加pdf的base64头信息
                if (!base64Str.startsWith("data:")) {
                    base64Str = CommonConstantUtils.BASE64_QZ_PDF + base64Str;
                }
                try {
                    BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", "", foldName, CommonConstantUtils.WJHZ_PDF);
                    bdcPdfDTO.setBase64str(base64Str);
                    bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
                } catch (IOException e) {
                    LOGGER.error("生成电子合同信息失败：{}", e.getMessage());
                }
            }
        }
    }

    @Override
    public void dealTzSpfBaJyxx(List<HtxxDTO> ntSpfBaJyxxList, String gzlslid, String xmid, String foldName) {
        if (CollectionUtils.isNotEmpty(ntSpfBaJyxxList)) {
            for (HtxxDTO ntSpfBaJyxx : ntSpfBaJyxxList) {
                String htbh = ntSpfBaJyxx.getContractno();
                // 单位：元
                String htjg = ntSpfBaJyxx.getContractprice();
                htjg = htjg.replace(",", "");
                List<HtxxFileDTO> fileDTOList = ntSpfBaJyxx.getFileList();
                String fileUrl = "";
                String fileName = "";
                if (CollectionUtils.isNotEmpty(fileDTOList)) {
                    fileUrl = fileDTOList.get(0).getFilePath();
                    fileName = fileDTOList.get(0).getFileName();
                }
                BdcSlJyxxDO bdcSlJyxx = new BdcSlJyxxDO();
                bdcSlJyxx.setHtbh(htbh);
                bdcSlJyxx.setJyje(Double.parseDouble(htjg));
                bdcSlJyxx.setHtbasj(DateUtils.formatDate(ntSpfBaJyxx.getReviewTime(), DateUtils.sdf_ymdhms));
                bdcSlJyxx.setHtdjsj(DateUtils.formatDate(ntSpfBaJyxx.getCommitTime(), DateUtils.sdf_ymdhms));
                // 更新交易信息
                updateJyxx(gzlslid, xmid, bdcSlJyxx);
                //上传交易附件
                if (StringUtils.isNotBlank(fileUrl)) {
                    try {
                        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", fileName, foldName, CommonConstantUtils.WJHZ_PDF);
                        bdcPdfDTO.setPdfUrl(fileUrl);
                        bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
                    } catch (IOException e) {
                        LOGGER.error("生成电子合同信息失败：{}", e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * @param gzlslid     工作流实例id
     * @param xmid
     * @param bdcSlJyxxDO 交易信息
     * @description: 更新交易信息
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/8 9:14
     **/
    private void updateJyxx(String gzlslid, String xmid, BdcSlJyxxDO bdcSlJyxxDO) {
        if (bdcSlJyxxDO == null || StringUtils.isBlank(bdcSlJyxxDO.getHtbh()) || bdcSlJyxxDO.getJyje() == null) {
            throw new AppException("南通住建商品房备案交易信息，缺失交易信息");
        }
        //部分交易信息获取到登记
        this.updateBdcXmJyhthByXmid(bdcSlJyxxDO.getHtbh(), gzlslid, xmid);
        // 更新权利信息
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (null != bdcQl) {
            String className = "";
            // 不动产单元唯一编号
            String bdcdywybh = "";
            // 只针对房地产权
            if (bdcQl instanceof BdcFdcqDO) {
                className = BdcFdcqDO.class.getName();
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                bdcdywybh = bdcFdcqDO.getBdcdywybh();
            }
            if (StringUtils.isNotBlank(className)) {
                try {
                    // modifyBdcQl方法中有对金额除以10000
                    this.modifyBdcQl(xmid, gzlslid, bdcdywybh, Double.toString(bdcSlJyxxDO.getJyje()), className);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("南通住建商品房备案交易信息，更新权利信息失败");
                }
            }
        }
        //更新交易信息
        this.updateSlJyxxByXmid(xmid, JSONObject.toJSONString(bdcSlJyxxDO), null);
    }

    // 将JSON字符串转换为BdcSlJyxxDO实体类
    private BdcSlJyxxDO convertToBdcSlJyxx(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        {
            // 转换为格式化的json
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // 如果json中有新增的字段并且是实体类类中不存在的，不报错
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return mapper.readValue(jsonString,BdcSlJyxxDO.class);
    }

    // 更新不动产项目交易合同号信息
    private void updateBdcXmJyhth(String htbh,String processInsId, String xmid){
        //部分交易信息获取到登记
        JSONObject xmobj = new JSONObject();
        xmobj.put("jyhth", htbh);
        //更新项目表
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(xmobj));
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(processInsId)) {
            map.put("gzlslid", processInsId);
        } else {
            map.put("xmids", xmid.split(CommonConstantUtils.ZF_YW_DH));
        }
        bdcDjxxUpdateQO.setWhereMap(map);
        bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
    }

    /**
     * @description 优先按xmid更新不动产项目交易合同号信息-南通57594需求
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/19 16:48
     * @param htbh 合同编号
     * @return null
     */
    private void updateBdcXmJyhthByXmid(String htbh,String processInsId, String xmid){
        //部分交易信息获取到登记
        JSONObject xmobj = new JSONObject();
        xmobj.put("jyhth", htbh);
        //更新项目表
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(xmobj));
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(xmid)) {
            map.put("xmids", xmid.split(CommonConstantUtils.ZF_YW_DH));
        } else {
            map.put("gzlslid", processInsId);
        }
        bdcDjxxUpdateQO.setWhereMap(map);
        bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
    }

    // 更新权利信息中的交易价格
    private void modifyBdcQl(String xmid, String processInsId, String bdcdywybh, String je, String className) throws Exception {
        if(StringUtils.isNotBlank(je)){
            Map<String, Object> map = new HashMap<>();
            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
            JSONObject param = new JSONObject();
             /*接口返回值金额为元，系统将元处理为万元进行保存
             BdcYgDO中交易金额为jyje，BdcFdcqDO交易金额为交易价格*/
            if (BdcYgDO.class.getName().equals(className)) {
                param.put("jyje", new BigDecimal(je).divide(new BigDecimal(10000)));
            } else {
                param.put("jyjg", new BigDecimal(je).divide(new BigDecimal(10000)));
            }
            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(param));
            if (StringUtils.isNotBlank(processInsId)) {
                map.put("gzlslid", processInsId);
            } else {
                map.put("xmids", xmid.split(CommonConstantUtils.ZF_YW_DH));
            }
            map.put("bdcdywybh",bdcdywybh);
            bdcDjxxUpdateQO.setWhereMap(map);
            bdcDjxxUpdateQO.setClassName(className);
            bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
        }
    }


    @Override
    public void dealSpfClfJyxx(FcjyBaxxDTO fcjyBaxxDTO, String xmid,Boolean uploadFj) {
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺少参数项目ID信息。");
        }
        BdcSlJyxxDO bdcSlJyxxDO = fcjyBaxxDTO.getBdcSlJyxx();
        if(null != bdcSlJyxxDO){
            updateSlJyxxByXmid(xmid, JSON.toJSONString(bdcSlJyxxDO),null);
        }
        BdcSlFwxxDO bdcSlFwxxDO = fcjyBaxxDTO.getBdcSlFwxx();
        if(null != bdcSlFwxxDO){
            bdcSlFwxxDO.setXmid(xmid);
            bdcSlFwxxService.updateBdcSlFwxxByXmid(bdcSlFwxxDO);
        }
        //上传交易附件
        if (Boolean.TRUE.equals(uploadFj) && fcjyBaxxDTO.getFcjyxxQO() != null && StringUtils.isNotBlank(fcjyBaxxDTO.getFcjyxxQO().getVersion())) {
            BdcSlJyxxAbstractService bdcSlJyxxAbstractService = InterfaceCodeBeanFactory.getBean(bdcSlJyxxAbstractServiceSet, fcjyBaxxDTO.getFcjyxxQO().getVersion());
            if (null != bdcSlJyxxAbstractService) {
                bdcSlJyxxAbstractService.uploadJyFj(fcjyBaxxDTO, fcjyBaxxDTO.getGzlslid());

            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承预告的交易信息
     * @date : 2022/1/6 15:02
     */
    @Override
    public void extendYgjyxx(String gzlslid) {
        //预转现的情况下产权作为主历史关系，预告作为外联项目
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxBySlid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                List<String> wlxmidList = new ArrayList<>(CollectionUtils.size(bdcXmLsgxDOList));
                for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                    if (Objects.equals(CommonConstantUtils.SF_S_DM, bdcXmLsgxDO.getWlxm())) {
                        wlxmidList.add(bdcXmLsgxDO.getYxmid());
                    }
                }
                List<BdcXmDO> wlxmDOList = bdcXmFeignService.listBdcXmByXmids(wlxmidList);
                if (CollectionUtils.isNotEmpty(wlxmDOList)) {
                    LOGGER.info("当前流程{}外联项目id{}", gzlslid, wlxmidList);
                    for (BdcXmDO wlxm : wlxmDOList) {
                        if (CommonConstantUtils.QLLX_YG_DM.equals(wlxm.getQllx())) {
                            //查预告的交易信息
                            List<BdcSlJyxxDO> ygJyxxDOList = this.listBdcSlJyxxByXmid(wlxm.getXmid());
                            if (CollectionUtils.isNotEmpty(ygJyxxDOList)) {
                                BdcSlJyxxDO ygJyxx = ygJyxxDOList.get(0);
                                LOGGER.info("流程{}外联预告的交易信息id{}", gzlslid, ygJyxx.getJyxxid());
                                BdcXmQO bdcXmQO = new BdcXmQO();
                                bdcXmQO.setGzlslid(gzlslid);
                                List<BdcXmDO> bdcXmDTOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                                for (BdcXmDO bdcXmDO : bdcXmDTOList) {
                                    List<BdcSlJyxxDO> bdcSlJyxxList = this.listBdcSlJyxxByXmid(bdcXmDO.getXmid());
                                    BdcSlJyxxDO extendJyxx = new BdcSlJyxxDO();
                                    BeanUtils.copyProperties(ygJyxx, extendJyxx);
                                    if (CollectionUtils.isNotEmpty(bdcSlJyxxList)) {
                                        //更新数据
                                        BdcSlJyxxDO bdcSlJyxxDO = bdcSlJyxxList.get(0);
                                        extendJyxx.setJyxxid(bdcSlJyxxDO.getJyxxid());
                                        extendJyxx.setXmid(bdcSlJyxxDO.getXmid());
                                    } else {
                                        //新增数据
                                        extendJyxx.setXmid(bdcXmDO.getXmid());
                                    }
                                    //更新项目表的jyhth字段
                                    bdcXmDO.setJyhth(ygJyxx.getHtbh());
                                    bdcXmFeignService.updateBdcXm(bdcXmDO);
                                    this.saveBdcSlJyxx(extendJyxx);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param fcjyBaxxDTO
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/8/3 18:20
     */
    @Override
    public void dealYrbjJyxx(FcjyBaxxDTO fcjyBaxxDTO, String xmid) {
        LOGGER.info("开始处理一人办件交易信息，xmid：{}，备案信息实体：{}", xmid, fcjyBaxxDTO);
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setXmid(xmid);
        List<BdcSlXmDO> listSlxm = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isEmpty(listSlxm)) {
            throw new AppException("未找到受理项目信息,xmid" + xmid);
        }
        String jbxxid = listSlxm.get(0).getJbxxid();
        if (CollectionUtils.isNotEmpty(fcjyBaxxDTO.getBdcSlFwxxDOList())) {
            //不会存在多套房屋办件，取第一条即可
            dealFwxx(fcjyBaxxDTO.getBdcSlFwxxDOList().get(0), xmid, jbxxid, fcjyBaxxDTO.getBdcSlJyxx());
        }

    }

    /**
     * @param fcjyBaxxDTO 交易备案信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理交易抵押信息, 如果交易存在抵押信息带入抵押项目
     */
    private void dealJyDyaqxx(FcjyBaxxDTO fcjyBaxxDTO) {
        if ((CollectionUtils.isNotEmpty(fcjyBaxxDTO.getListBdcSlDyaqDTOSy()) || CollectionUtils.isNotEmpty(fcjyBaxxDTO.getListBdcSlDyaqDTOGjj())) && StringUtils.isNotBlank(fcjyBaxxDTO.getGzlslid())) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(fcjyBaxxDTO.getGzlslid());
            bdcXmQO.setQllxs(Arrays.asList(CommonConstantUtils.QLLX_DYAQ_DM));
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                //只有一个抵押,交易哪个抵押存在数据带入哪个
                if(bdcXmDOList.size() ==1){
                    if(CollectionUtils.isNotEmpty(fcjyBaxxDTO.getListBdcSlDyaqDTOSy())){
                        drDyaqxx(fcjyBaxxDTO.getListBdcSlDyaqDTOSy().get(0),bdcXmDOList.get(0));
                    }else{
                        drDyaqxx(fcjyBaxxDTO.getListBdcSlDyaqDTOGjj().get(0),bdcXmDOList.get(0));
                    }
                }else if(bdcXmDOList.size() ==2 &&CollectionUtils.isNotEmpty(fcjyBaxxDTO.getListBdcSlDyaqDTOSy()) &&CollectionUtils.isNotEmpty(fcjyBaxxDTO.getListBdcSlDyaqDTOGjj())){
                    //组合贷款,根据djxl匹配带入
                    Map<String,BdcSlDyaqDTO> djxlDyaqxx =new HashMap<>();
                    if(fcjyBaxxDTO.getListBdcSlDyaqDTOGjj().get(0) != null &&StringUtils.isNotBlank(fcjyBaxxDTO.getListBdcSlDyaqDTOGjj().get(0).getDjxl())) {
                        djxlDyaqxx.put(fcjyBaxxDTO.getListBdcSlDyaqDTOGjj().get(0).getDjxl(), fcjyBaxxDTO.getListBdcSlDyaqDTOGjj().get(0));
                    }
                    if(fcjyBaxxDTO.getListBdcSlDyaqDTOSy().get(0) != null &&StringUtils.isNotBlank(fcjyBaxxDTO.getListBdcSlDyaqDTOSy().get(0).getDjxl())) {
                        djxlDyaqxx.put(fcjyBaxxDTO.getListBdcSlDyaqDTOSy().get(0).getDjxl(), fcjyBaxxDTO.getListBdcSlDyaqDTOSy().get(0));
                    }
                    for(BdcXmDO bdcXmDO:bdcXmDOList){
                        if(StringUtils.isNotBlank(bdcXmDO.getDjxl()) &&djxlDyaqxx.get(bdcXmDO.getDjxl()) != null){
                            drDyaqxx(djxlDyaqxx.get(bdcXmDO.getDjxl()),bdcXmDO);
                        }

                    }
                }
            }


        }

    }

    /**
      * @param dyxm 抵押项目
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 带入抵押信息
      */
    private void drDyaqxx(BdcSlDyaqDTO bdcSlDyaqDTO,BdcXmDO dyxm){
        LOGGER.info("带入抵押信息：{},抵押项目ID:{}",bdcSlDyaqDTO,dyxm != null ?dyxm.getXmid():"空");
        if(bdcSlDyaqDTO != null &&dyxm != null &&StringUtils.isNotBlank(dyxm.getXmid())){
            //带入权利人信息
            handleQlrxx(dyxm,bdcSlDyaqDTO.getBdcQlrDOList(),false);
            //带入抵押信息
            if(bdcSlDyaqDTO.getBdcSlDyaqDO() != null) {
                BdcQl bdcQl =bdcQllxFeignService.queryQlxx(dyxm.getXmid());
                if(bdcQl instanceof BdcDyaqDO) {
                    BdcDyaqDO bdcDyaqDO =(BdcDyaqDO) bdcQl;
                    //更新
                    BeanUtils.copyProperties(bdcSlDyaqDTO.getBdcSlDyaqDO(), bdcDyaqDO, ObjectUtils.getNullPropertyNames(bdcSlDyaqDTO.getBdcSlDyaqDO()));
                    bdcQllxFeignService.updateDyaq(bdcDyaqDO);
                }
            }

        }

    }

    /**
     * 处理不动产项目中的交易合同号与权利信息中的交易金额
     */
    private void handleBdcXmAndQlxx(FcjyBaxxDTO fcjyBaxxDTO) {
        BdcSlJyxxDO bdcSlJyxxDO = fcjyBaxxDTO.getBdcSlJyxx();
        if (Objects.nonNull(bdcSlJyxxDO)) {
            // 更新项目表合同编号信息
            if (StringUtils.isNotBlank(bdcSlJyxxDO.getHtbh())) {
                batchModifyBdcXmJyhth(bdcSlJyxxDO.getHtbh(), fcjyBaxxDTO.getXmid(), fcjyBaxxDTO.getGzlslid());
            }
            if(null != bdcSlJyxxDO.getJyje()){
                BdcSlFwxxDO bdcSlFwxxDO = Optional.ofNullable(fcjyBaxxDTO.getBdcSlFwxx()).orElse(new BdcSlFwxxDO());
                batchModifyQlxxJyje(bdcSlJyxxDO.getJyje(), bdcSlFwxxDO.getFwbm(),
                        fcjyBaxxDTO.getXmid(), fcjyBaxxDTO.getGzlslid());
            }
        }
    }

    /**
     * 批量更新登记项目表的交易合同号信息
     */
    private void  batchModifyBdcXmJyhth(String htbh, String xmid, String gzlslid){
        JSONObject xmobj = new JSONObject();
        xmobj.put("jyhth", htbh);
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(xmobj));
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(gzlslid)) {
            map.put("gzlslid", gzlslid);
        } else {
            map.put("xmids", xmid.split(CommonConstantUtils.ZF_YW_DH));
        }
        bdcDjxxUpdateQO.setWhereMap(map);
        bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
    }

    /**
     * 批量更新权利信息中的交易金额内容
     */
    private void batchModifyQlxxJyje(Double jyje, String fwbm, String xmid, String gzlslid){
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (null != bdcQl) {
            String className = "";
            if(bdcQl instanceof BdcFdcqDO){
                className = BdcFdcqDO.class.getName();
            }
            if(bdcQl instanceof BdcYgDO){
                className = BdcYgDO.class.getName();
            }
            if(StringUtils.isNotBlank(className)){
                Map<String, Object> map = new HashMap<>();
                BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                JSONObject param = new JSONObject();
                // BdcYgDO中交易金额为jyje，BdcFdcqDO交易金额为交易价格
                if (BdcYgDO.class.getName().equals(className)) {
                    param.put("jyje", jyje);
                } else {
                    param.put("jyjg", jyje);
                }
                bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(param));
                if (StringUtils.isNotBlank(gzlslid)) {
                    map.put("gzlslid", gzlslid);
                } else {
                    map.put("xmids", xmid.split(CommonConstantUtils.ZF_YW_DH));
                }
                if(StringUtils.isNotBlank(fwbm)){
                    map.put("bdcdywybh", fwbm);
                }
                bdcDjxxUpdateQO.setWhereMap(map);
                bdcDjxxUpdateQO.setClassName(className);
                try{
                    bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
                }catch (Exception e){
                    LOGGER.error("更新权利信息中的交易价格信息失败,xmid:{},gzlslid:{}",xmid,gzlslid, e);
                }
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取交易对象权利人
     */
    private void queryJyxxResponseDTOQlr(JyxxResponseDTO jyxxResponseDTO,String xmid){
        if(StringUtils.isBlank(xmid) ||jyxxResponseDTO ==null){
            return;
        }
        //第三权利人
        if(StringUtils.equals(Constants.JYQLRSJLY_DSQLR,jyxxResponseDTO.getQlrsjly())) {
            BdcDsQlrQO bdcDsQlrQO = new BdcDsQlrQO();
            bdcDsQlrQO.setXmid(xmid);
            List<BdcDsQlrDO> bdcDsQlrDOList = bdcQlrFeignService.listBdcDsQlr(bdcDsQlrQO);
            if (CollectionUtils.isNotEmpty(bdcDsQlrDOList)) {
                List<BdcDsQlrDO> msrList = bdcDsQlrDOList.stream().filter(dsQlr -> StringUtils.equals(CommonConstantUtils.DSQLR_QLRLB_MSR, dsQlr.getQlrlb())).collect(Collectors.toList());
                List<BdcDsQlrDO> cmrList = bdcDsQlrDOList.stream().filter(dsQlr -> StringUtils.equals(CommonConstantUtils.DSQLR_QLRLB_CMR, dsQlr.getQlrlb())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(cmrList)) {
                    if(cmrList.size() >1){
                        BdcDsQlrDO dsQlrDO =new BdcDsQlrDO();
                        dsQlrDO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(cmrList, "getQlrmc", ","));
                        dsQlrDO.setZjzl(cmrList.get(0).getZjzl());
                        dsQlrDO.setZjh(StringToolUtils.resolveBeanToAppendStr(cmrList, "getZjh", ","));
                        jyxxResponseDTO.setCmrxx(Collections.singletonList(dsQlrDO));
                    }else {
                        jyxxResponseDTO.setCmrxx(cmrList);
                    }
                }
                if(CollectionUtils.isNotEmpty(msrList)) {
                    if(msrList.size() >1){
                        BdcDsQlrDO dsQlrDO =new BdcDsQlrDO();
                        dsQlrDO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(msrList, "getQlrmc", ","));
                        dsQlrDO.setZjzl(msrList.get(0).getZjzl());
                        dsQlrDO.setZjh(StringToolUtils.resolveBeanToAppendStr(msrList, "getZjh", ","));
                        jyxxResponseDTO.setMsrxx(Collections.singletonList(dsQlrDO));
                    }else {
                        jyxxResponseDTO.setMsrxx(msrList);
                    }

                }
            }
        }else if(StringUtils.equals(Constants.JYQLRSJLY_SLSQR,jyxxResponseDTO.getQlrsjly())) {
            List<BdcSlSqrDO> bdcSlSqrDOList =bdcSlSqrService.listBdcSlSqrByXmid(xmid,null);
            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                List<BdcSlSqrDO> msrList = bdcSlSqrDOList.stream().filter(sqr -> StringUtils.equals(CommonConstantUtils.QLRLB_QLR, sqr.getSqrlb())).collect(Collectors.toList());
                List<BdcSlSqrDO> cmrList = bdcSlSqrDOList.stream().filter(sqr -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR, sqr.getSqrlb())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(cmrList)) {
                    jyxxResponseDTO.setCmrxx(cmrList);
                }
                if(CollectionUtils.isNotEmpty(msrList)) {
                    jyxxResponseDTO.setMsrxx(msrList);
                }
            }

        }else if(StringUtils.equals(Constants.JYQLRSJLY_DJQLR,jyxxResponseDTO.getQlrsjly())) {
            BdcQlrQO bdcQlrQO =new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            List<BdcQlrDO> bdcQlrDOList =bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                List<BdcQlrDO> msrList = bdcQlrDOList.stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlr.getQlrlb())).collect(Collectors.toList());
                List<BdcQlrDO> cmrList = bdcQlrDOList.stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlr.getQlrlb())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(cmrList)) {
                    jyxxResponseDTO.setCmrxx(cmrList);
                }
                if(CollectionUtils.isNotEmpty(msrList)) {
                    jyxxResponseDTO.setMsrxx(msrList);
                }
            }

        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  过滤解析商品房交易存量房交易信息-常州
     */
    private JyxxResponseDTO filterCzSpfClfHtxx(Map map,String xmid){
        JyxxResponseDTO jyxxResponseDTO =new JyxxResponseDTO();
        jyxxResponseDTO.setHtxx(map);
        if(StringUtils.equals(CommonConstantUtils.FCJY_TYPE_CLF,MapUtils.getString(map,"fwlx"))){
            if(MapUtils.getObject(map,"qlrxx") != null){
                List<BdcDsQlrDO> dsQlrDOList =JSONObject.parseArray(JSONObject.toJSONString(MapUtils.getObject(map,"qlrxx")), BdcDsQlrDO.class);
                if(CollectionUtils.isNotEmpty(dsQlrDOList)){
                    List<BdcDsQlrDO> msrList = dsQlrDOList.stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlr.getQlrlb())).collect(Collectors.toList());
                    List<BdcDsQlrDO> cmrList = dsQlrDOList.stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlr.getQlrlb())).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(cmrList)) {
                        jyxxResponseDTO.setCmrxx(cmrList);
                    }
                    if(CollectionUtils.isNotEmpty(msrList)) {
                        jyxxResponseDTO.setMsrxx(msrList);
                    }
                }
            }

        }else{
            BdcDsQlrDO msr =new BdcDsQlrDO();
            if(StringUtils.isNotBlank(MapUtils.getString(map,"msrmc"))) {
                msr.setQlrmc(MapUtils.getString(map,"msrmc"));
                msr.setZjzl(MapUtils.getInteger(map,"msrzjzl"));
                msr.setZjh(MapUtils.getString(map,"msrzjh"));
                msr.setTxdz(MapUtils.getString(map,"msrlxdz"));
                msr.setDh(MapUtils.getString(map,"msrlxdh"));
                msr.setDlrmc(MapUtils.getString(map,"wtdlr"));
                msr.setDlrzjzl(MapUtils.getString(map,"wtdlrzjzl"));
                msr.setDlrzjh(MapUtils.getString(map,"wtdlrzjh"));
                jyxxResponseDTO.setMsrxx(Collections.singletonList(msr));
            }
            BdcDsQlrDO cmr =new BdcDsQlrDO();
            if(StringUtils.isNotBlank(MapUtils.getString(map,"cmr"))) {
                cmr.setQlrmc(MapUtils.getString(map, "cmr"));
                cmr.setZjzl(MapUtils.getInteger(map, "cmrzjzl"));
                cmr.setZjh(MapUtils.getString(map, "cmrzjh"));
                cmr.setDh(MapUtils.getString(map, "cmrlxdh"));
                jyxxResponseDTO.setCmrxx(Collections.singletonList(cmr));
            }
        }
        //保存第三权利人和交易信息
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setHtbh(MapUtils.getString(map, "htbh"));
        bdcSlJyxxDO.setZl(MapUtils.getString(map, "zl"));
        //处理备案时间
        String basj = MapUtils.getString(map, "basj", "");
        bdcSlJyxxDO.setHtbasj(DateUtils.getDateFromStr(basj));
        bdcSlJyxxDO.setHtdjsj(DateUtils.getDateFromStr(basj));
        //合同价格，合同面积
        bdcSlJyxxDO.setHtmj(MapUtils.getDouble(map, "jzmj", 0.00));
        bdcSlJyxxDO.setJyje(MapUtils.getDouble(map, "cjje", 0.00));
        if (StringUtils.isNotBlank(bdcSlJyxxDO.getHtbh())) {
            List<BdcSlJyxxDO> bdcSlJyxxDOList = listBdcSlJyxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                bdcSlJyxxDO.setJyxxid(bdcSlJyxxDOList.get(0).getJyxxid());
            }
            bdcSlJyxxDO.setXmid(xmid);
            saveBdcSlJyxx(bdcSlJyxxDO);
        }
        BdcDsQlrQO bdcDsQlrQO = new BdcDsQlrQO();
        bdcDsQlrQO.setXmid(xmid);
        //bdcDsQlrQO.
        bdcQlrFeignService.delBdcDsQlr(xmid,CommonConstantUtils.DSQLR_QLRLB_CMR);
        bdcQlrFeignService.delBdcDsQlr(xmid,CommonConstantUtils.DSQLR_QLRLB_MSR);
        if(CollectionUtils.isNotEmpty(jyxxResponseDTO.getCmrxx())) {
            for(Object cmrxx:jyxxResponseDTO.getCmrxx()) {
                BdcDsQlrDO cmr =(BdcDsQlrDO) cmrxx;
                cmr.setXmid(xmid);
                cmr.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_CMR);
                bdcQlrFeignService.insertBdcDsQlr(cmr);
            }
        }
        if(CollectionUtils.isNotEmpty(jyxxResponseDTO.getMsrxx())) {
            for(Object msrxx:jyxxResponseDTO.getMsrxx()) {
                BdcDsQlrDO msr =(BdcDsQlrDO) msrxx;
                msr.setXmid(xmid);
                msr.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_MSR);
                bdcQlrFeignService.insertBdcDsQlr(msr);
            }
        }
        return jyxxResponseDTO;


    }

    private void dealFwxx(BdcSlFwxxDO bdcSlFwxx, String xmid, String jbxxid, BdcSlJyxxDO bdcSlJyxxDO) {
        Integer fwyt = bdcSlFwxx.getFwyt();
        List<BdcSlXmDO> listAllSlxm = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
        LOGGER.info("根据当前jbxxid查询受理项目信息：{}，{}", jbxxid, listAllSlxm);
        Map<Integer, String> mapForFwytToXmid = new HashMap<>();
        Map<String, BdcSlFwxxDO> xmidWithFwxxMap = new HashMap<>(); // xmid和fwxx的映射
        // 做一个 xmid和用途，用途和xmid的映射
        // 我们认定 一体化的流程下 只有一个主房和多个不同类型的附房
        for (int i = 0; i < listAllSlxm.size(); i++) {
            String curXmid = listAllSlxm.get(i).getXmid();
            List<BdcSlFwxxDO> listFw = bdcSlFwxxService.listBdcSlFwxxByXmid(curXmid);
            LOGGER.info("根据当前xmid:{}查询房屋信息：{}", curXmid, listFw);
            if (CollectionUtils.isNotEmpty(listFw)) {
                mapForFwytToXmid.put(listFw.get(0).getFwyt(), curXmid);
                xmidWithFwxxMap.put(curXmid, listFw.get(0));
            }
        }


        // 新的逻辑 交易的用途和房屋用途 可以 1对多 对应

        if (!mapForFwytToXmid.containsKey(fwyt)) {
            LOGGER.info("配置房屋用途映射关系为：{}", jyxxFwytYddConfig.getFwytMap());
            if (jyxxFwytYddConfig.getFwytMap().containsKey(fwyt + "")) {
                boolean fwytFlag = (Boolean) this.includeFwyt(mapForFwytToXmid, fwyt).get("flag");
                if (!fwytFlag) {
                    throw new AppException("未找到对应用途的房屋信息！");
                }
            } else {
                throw new AppException("未找到对应用途的房屋信息！");
            }
        }

        BdcSlFwxxDO bdcSlFwxxDO = new BdcSlFwxxDO();
        BeanUtils.copyProperties(bdcSlFwxx, bdcSlFwxxDO);
        if (null != bdcSlFwxxDO) {
            // 通过当前的用途找到xmid,如果fwxx里面已有这个房屋，则对这个房屋做更新即可,并且xmid设置成原有的。
            String matchedXmid = "";
            // 匹配了选择的备案信息中相同房屋用途的xmid。（可能存在一个主房、多个附属设施的情况）
            Map map = this.includeFwyt(mapForFwytToXmid, fwyt);
            if ((Boolean) map.get("flag")) {
                matchedXmid = map.get("xmid").toString();
            } else {
                // 如果没有匹配到相同房屋用途,则需要设置当前的xmid
                matchedXmid = xmid;
            }
            bdcSlFwxxDO.setXmid(matchedXmid);
            // 此处将空字符串转为null处理
            String fj = bdcSlFwxxDO.getFj();
            String slr = "";
            //根据jbxxid 找到jbxx
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid(jbxxid);
            if (Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getSlr())) {
                slr = bdcSlJbxxDO.getSlr();
            }
            if (StringUtils.isBlank(fj)) {
//                bdcSlFwxxDO.setFj(null);
                bdcSlFwxxDO.setFj("受理人:" + slr);
            } else {
                bdcSlFwxxDO.setFj(fj + "受理人:" + slr);
            }
            // 若当前房屋的主房面积存在时，不替换备案信息里的主房面积
            if (Objects.nonNull(matchedXmid)) {
                // 原受理项目房屋信息的建筑面积
                Double jzmj = xmidWithFwxxMap.get(matchedXmid).getJzmj();
                if (Objects.nonNull(jzmj)) {
                    bdcSlFwxxDO.setJzmj(jzmj);
                }
            }
            List<BdcSlFwxxDO> fwxxList = bdcSlFwxxService.listBdcSlFwxxByXmid(bdcSlFwxxDO.getXmid());
            if (CollectionUtils.isNotEmpty(fwxxList)) {// 有了，则需要把房屋用途置空，不能更新房屋用途，因为用途不一定一样，以受理里面为主
                bdcSlFwxxDO.setFwyt(null);
            }
            this.bdcSlFwxxService.updateBdcSlFwxxByXmid(bdcSlFwxxDO);

        }
        // 通过当前的用途找到xmid,如果fwxx里面已有这个房屋，
        // 则对这个房屋做更新即可,并且xmid设置成原有的
        String updateJyxxXmid = "";
        Map map = this.includeFwyt(mapForFwytToXmid, fwyt);
        if ((Boolean) map.get("flag")) {
            updateJyxxXmid = map.get("xmid").toString();
        } else {// 如果没有这个fw,则需要设置当前的xmid
            updateJyxxXmid = xmid;
        }
        if (null != bdcSlJyxxDO) {
            bdcSlJyxxDO.setXmid(updateJyxxXmid);
            insertBdcSlJyxxSingle(bdcSlJyxxDO);
        }

        // 附属设施要处理备注
        try {
            LOGGER.info("开始处理交易信息备注");
            dealBaxx(bdcSlFwxx, bdcSlJyxxDO, xmid);
        } catch (Exception e) {
            throw new AppException("处理备案信息失败！错误原因：{}" + e.toString());
        }
    }

    @Override
    public Object jyhy(String gzlslid, String xmid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG,"缺失参数：工作流实例ID");
        }
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setGzlslid(gzlslid);
        if(StringUtils.isNotBlank(xmid)){
            bdcSlXmQO.setXmid(xmid);
        }
        List<BdcSlXmDO> listSlxm = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isEmpty(listSlxm)) {
            throw new AppException(ErrorCode.CUSTOM, "未找到受理项目信息，项目id:" + xmid);
        }
        String ycqzh = listSlxm.get(0).getYbdcqz();
        if(StringUtils.isBlank(ycqzh)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到原产权证号信息，项目id:" + xmid);
        }
        // 获取产权证号中所有数字，例：苏（2020）海安市不动产权第0000001号，只取20200000001作为产权证号查询
        String cqzhjc = ycqzh.replaceAll("[^0-9]+", "");
        List<BdcSlSqrDO> bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(listSlxm.get(0).getXmid(), CommonConstantUtils.QLRLB_YWR);
        if(CollectionUtils.isEmpty(bdcSlSqrDOList)){
            throw new AppException(ErrorCode.CUSTOM, "为获取到义务人信息，项目id:" + xmid);
        }
        bdcSlSqrDOList = bdcSlSqrDOList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcSlSqrDO::getZjh))), ArrayList::new));

        List<Object> reponseList = new ArrayList<>(bdcSlSqrDOList.size());
        for(BdcSlSqrDO bdcSlSqrDO: bdcSlSqrDOList){
            Map<String, String> param = new HashMap<>(2);
            param.put("cqzh",cqzhjc);
            param.put("zjh",bdcSlSqrDO.getZjh());
            LOGGER.info("交易核验请求参数：{}", param.toString());
            Object response = this.exchangeInterfaceFeignService.requestInterface("yrb_clfhy", param);
            LOGGER.info("交易核验返回值：{}", JSON.toJSONString(response));
            if(Objects.nonNull(response)){
                reponseList.add(response);
            }
        }
        return reponseList;
    }

    @Override
    public void fkzjzt(String gzlslid, String htbh, String zjh) {
        if(StringUtils.isBlank(htbh)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：合同编号");
        }

        if(StringUtils.isBlank(zjh)){
           if(StringUtils.isBlank(gzlslid)){
               throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID");
           }
            List<BdcSlXmDO> bdcSlXmDOList =  this.bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isEmpty(bdcSlXmDOList)){
                throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理收费项目信息");
            }
            List<BdcSlSqrDO> bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), CommonConstantUtils.QLRLB_QLR);
            if(CollectionUtils.isEmpty(bdcSlSqrDOList)){
                throw new AppException(ErrorCode.MISSING_ARG, "未查询到受理申请人信息，工作流实例ID：" + gzlslid);
            }
            zjh = bdcSlSqrDOList.get(0).getZjh();
        }

        Map<String, String> param = new HashMap<>(4);
        param.put("bh",  htbh);
        param.put("zjh", zjh);
        // 获取结果： 1（已获取） 2（已取消）
        param.put("hqjg", String.valueOf(CommonConstantUtils.SF_S_DM));
        param.put("hqsj", DateUtils.formateDateToString(new Date(), DateUtils.DATE_FORMAT));
        LOGGER.info("通知住建获取结果信息，请求参数：{}", param.toString());
        this.exchangeInterfaceFeignService.requestInterface("yrb_clfhqjg", param);
    }

    @Override
    public void jsZjHtxx(ZjClfHtxxDataDTO zjClfHtxxDataDTO) {
        if(Objects.isNull(zjClfHtxxDataDTO)){
            throw new AppException(ErrorCode.MISSING_ARG, "接收住建推送的合同信息为空");
        }
        LOGGER.info("接收到住建信息：{}", JSON.toJSONString(zjClfHtxxDataDTO));
        // 登记系统只需要获取：合同编号、交易价格及附件pdf，PDF获取至文件夹（可配置）；
        ZjHtxxDTO zjHtxxDTO = zjClfHtxxDataDTO.getHtxx();
        if(Objects.isNull(zjHtxxDTO) || StringUtils.isBlank(zjHtxxDTO.getBH())){
            throw new AppException(ErrorCode.MISSING_ARG, "接收住建推送的合同信息为空");
        }
        if(StringUtils.isBlank(zjHtxxDTO.getYCQZH())){
            throw new AppException(ErrorCode.MISSING_ARG, "接收住建推送的原产权证号为空");
        }
        // 根据原产权证号更新数据
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzhjc(zjHtxxDTO.getYCQZH());
        bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(bdcZsDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到产权证号为：" + zjHtxxDTO.getYCQZH() +"的证书信息。");
        }
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setYbdcqz(bdcZsDOList.get(0).getBdcqzh());
        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到原产权证号为：" + bdcZsDOList.get(0).getBdcqzh() +"的受理项目信息");
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList = this.listBdcSlJyxxByXmid(bdcSlXmDOList.get(0).getXmid());
        if(CollectionUtils.isEmpty(bdcSlJyxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取当前项目ID：" + bdcSlXmDOList.get(0).getXmid() + "，关联的一人办件交易信息。" );
        }
        LOGGER.info("住建推送合同信息为：{}", StringUtils.left(JSON.toJSONString(zjClfHtxxDataDTO), 4000));
        BdcSlJyxxDO bdcSlJyxxDO = bdcSlJyxxDOList.get(0);
        // 住建推送过来的金额单位为元，我们需要转换为万元存储
        if(StringUtils.isNotBlank(zjHtxxDTO.getHTJE())){
            Double jyje = new Double(Optional.ofNullable(zjHtxxDTO.getHTJE()).orElse("0"));
            bdcSlJyxxDO.setJyje(jyje/10000);
        }
        bdcSlJyxxDO.setHtbh(zjHtxxDTO.getBH());
        this.saveBdcSlJyxx(bdcSlJyxxDO);

        // 获取工作流实例ID，并上传电子合同信息至附件
        if(StringUtils.isNotBlank(zjHtxxDTO.getDZHT())){
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlXmDOList.get(0).getJbxxid());
            if(Objects.nonNull(bdcSlJbxxDO)){
                // 1.上传电子合同信息
                this.uploadDzht(bdcSlJbxxDO.getGzlslid(), zjHtxxDTO.getDZHT());

                // 2.接收完成之后，通知住建反馈状态
                this.fkzjzt(bdcSlJbxxDO.getGzlslid(), zjHtxxDTO.getBH(), zjHtxxDTO.getMSRZJH());
            }
        }
    }

    // 上传电子合同信息至附件中
    private void uploadDzht(String gzlslid, String base64) {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(base64)) {
            if (!base64.startsWith("data:")) {
                base64 = CommonConstantUtils.BASE64_QZ_PDF + base64;
            }
            try {
                bdcUploadFileUtils.uploadBase64File(base64, gzlslid, zjHtxxWjjmc, "电子合同", ".pdf");
            } catch (IOException e) {
                LOGGER.error("当前流程实例ID:{}, 上传住建推送的电子合同信息失败。", gzlslid, e);
            }
        }
    }

    @Override
    public void tsZjSlxx(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数，工作流实例ID");
        }
        ZjClfQdDTO zjClfQdDTO = new ZjClfQdDTO();

        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(Objects.isNull(bdcSlJbxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理基本信息，工作流实例ID:" + gzlslid);
        }
        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理项目信息，工作流实例ID:" + gzlslid);
        }
        // 获取产权证号中所有数字，例：苏（2020）海安市不动产权第0000001号，只取20200000001作为产权证号查询
        ZjClfDjxxDTO djxx = new ZjClfDjxxDTO();
        if(StringUtils.isNotBlank(bdcSlXmDOList.get(0).getYbdcqz())){
            String cqzhjc = bdcSlXmDOList.get(0).getYbdcqz().replaceAll("[^0-9]+", "");
            djxx.setCQZH(cqzhjc);
        }
        zjClfQdDTO.setDjxx(djxx);

        // 组织房屋信息
        List<ZjClfFwxxDTO> fwxx = new ArrayList<>(bdcSlXmDOList.size());
        List<Map> fwytZdMap = bdcZdFeignService.queryBdcZd("fwyt");
        for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList){
            List<BdcSlFwxxDO> bdcSlFwxxDOList = this.bdcSlFwxxService.listBdcSlFwxxByXmid(bdcSlXmDO.getXmid());
            if(CollectionUtils.isNotEmpty(bdcSlFwxxDOList)){
                ZjClfFwxxDTO zjClfFwxxDTO = new ZjClfFwxxDTO();
                zjClfFwxxDTO.setFH(bdcSlFwxxDOList.get(0).getFjh());
                Integer fwytDm = bdcSlFwxxDOList.get(0).getFwyt();
                zjClfFwxxDTO.setFWYT(StringToolUtils.convertBeanPropertyValueOfZd(fwytDm, fwytZdMap));
                zjClfFwxxDTO.setFWMJ(bdcSlFwxxDOList.get(0).getJzmj().toString());
                List<BdcSlJyxxDO> bdcSlJyxxDOList = this.listBdcSlJyxxByXmid(bdcSlXmDO.getXmid());
                if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
                    // 将万元转换成元
                    zjClfFwxxDTO.convertWyToy(bdcSlJyxxDOList.get(0).getJyje());
                }
                fwxx.add(zjClfFwxxDTO);
            }
        }
        zjClfQdDTO.setFwxx(fwxx);

        // 组织权利人信息
        List<BdcSlSqrDO> bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), CommonConstantUtils.QLRLB_QLR);
        List<ZjClfMsfxxDTO> msfxx = new ArrayList<>(bdcSlSqrDOList.size());
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
            this.setBdfe(bdcSlSqrDOList);
            for(BdcSlSqrDO bdcSlSqrDO: bdcSlSqrDOList){
                ZjClfMsfxxDTO zjClfMsfxxDTO = new ZjClfMsfxxDTO();
                zjClfMsfxxDTO.setMSR(bdcSlSqrDO.getSqrmc());
                zjClfMsfxxDTO.setMSRZJHM(bdcSlSqrDO.getZjh());
                zjClfMsfxxDTO.setMSRGX("");
                zjClfMsfxxDTO.setZYBL(bdcSlSqrDO.getQlbl());
                zjClfMsfxxDTO.setMSRDH(bdcSlSqrDO.getDh());
                zjClfMsfxxDTO.setMSRDZ(bdcSlSqrDO.getTxdz());
                msfxx.add(zjClfMsfxxDTO);
            }
            zjClfQdDTO.setMsfxx(msfxx);
        }

        // 附件信息
        this.generateZjFjxx(gzlslid, zjClfQdDTO);

        Map<String, Object> param = new HashMap<>(2);
        param.put("data", zjClfQdDTO);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(param, SerializerFeature.WriteNullStringAsEmpty));
        LOGGER.info("推送住建交易启动接口信息：{}", StringUtils.left(jsonObject.toJSONString(), 4000));
        this.exchangeInterfaceFeignService.requestInterface("yrb_clfqd", jsonObject);
    }

    /**
     * @param fcjyxxQO 房产交易查询对象
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 查询淮安房产交易合同信息
     */
    @Override
    public String queryEsfWqHtxx(FcjyxxQO fcjyxxQO) {
        if (StringUtils.isBlank(fcjyxxQO.getHtbh()) || StringUtils.isBlank(fcjyxxQO.getXmid()) || StringUtils.isBlank(fcjyxxQO.getGzlslid())) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        String beanName = "esfwqhtxx";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("htbh", fcjyxxQO.getHtbh());
        LOGGER.info("获取二手房网签合同信息，beanName:{}, 请求参数：{}", beanName, paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        LOGGER.info("合同编号:{},获取二手房网签合同信息接口调用成功，响应内容：{}", fcjyxxQO.getHtbh(),response);
        if (Objects.nonNull(response)) {
            JSONObject content = JSONObject.parseObject(JSONObject.toJSONString(response));
            JSONObject headJsonObject = content.getJSONObject("head");
            JSONObject data = content.getJSONObject("data");

            if(!"0000".equals(headJsonObject.getString("returncode"))){
                throw new AppException("获取二手房网签合同信息失败");
            }
            if (content.getJSONObject("data") != null) {
                EsfWqHtxxDTO esfWqHtxxDTO = JSONObject.parseObject(JSON.toJSONString(data), EsfWqHtxxDTO.class);
                LOGGER.info(esfWqHtxxDTO.toString());
                String msg = handleEsfWqHtxx(esfWqHtxxDTO, fcjyxxQO);
                return msg;
            }
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 工作流事件，推送不动产转移和抵押登记登簿信息
     */
    @Override
    public void tsBdcZyDyDjxx(String gzlslid) {
        TsFcjyYwxxDTO tsFcjyYwxxDTO = new TsFcjyYwxxDTO();
        tsFcjyYwxxDTO.setGzlslid(gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                if (CollectionUtils.isNotEmpty(spfdyidList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid()) && spfdyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                    tsFcjyYwxxDTO.setYwlx("1");
                } else {
                    tsFcjyYwxxDTO.setYwlx("2");
                }

                // 转移类
                List<BdcXmDO> zyXmDOList = bdcXmDOList.stream().filter(bdcXmDO -> Arrays.asList(CommonConstantUtils.QLLX_FDCQ).contains(bdcXmDO.getQllx())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(zyXmDOList)) {
                    Map<String, Object> paramMap = tsBdcFcjyZyDjxxServiceImpl.getFcjyTsYwxxRequestParam(tsFcjyYwxxDTO);
                    // 发起推送，true表示推送成功，false推送失败
                    boolean tsResult = tsBdcFcjyZyDjxxServiceImpl.tsFcjyYwxx(paramMap);
                    BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                    bdcCzrzDO.setGzlslid(gzlslid);
                    bdcCzrzDO.setCzcs(JSON.toJSONString(tsFcjyYwxxDTO));
                    if (tsResult) {
                        bdcCzrzDO.setCzjg("推送成功");
                        bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
                        tsBdcFcjyZyDjxxServiceImpl.addTssbLog(bdcCzrzDO);
                    } else {
                        bdcCzrzDO.setCzjg("推送失败");
                        bdcCzrzDO.setCzzt(CommonConstantUtils.SF_F_DM);
                        tsBdcFcjyZyDjxxServiceImpl.addTssbLog(bdcCzrzDO);
                    }
                }

                // 抵押类
                List<BdcXmDO> dyXmDOList = bdcXmDOList.stream().filter(bdcXmDO -> Arrays.asList(CommonConstantUtils.QLLX_DYAQ_DM).contains(bdcXmDO.getQllx())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(dyXmDOList)) {
                    Map<String, Object> paramMap = tsBdcFcjyDyDjxxServiceImpl.getFcjyTsYwxxRequestParam(tsFcjyYwxxDTO);
                    // 发起推送，true表示推送成功，false推送失败
                    boolean tsResult = tsBdcFcjyDyDjxxServiceImpl.tsFcjyYwxx(paramMap);
                    BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                    bdcCzrzDO.setGzlslid(gzlslid);
                    bdcCzrzDO.setCzcs(JSON.toJSONString(tsFcjyYwxxDTO));
                    if (tsResult) {
                        bdcCzrzDO.setCzjg("推送成功");
                        bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
                        tsBdcFcjyDyDjxxServiceImpl.addTssbLog(bdcCzrzDO);
                    } else {
                        bdcCzrzDO.setCzjg("推送失败");
                        bdcCzrzDO.setCzzt(CommonConstantUtils.SF_F_DM);
                        tsBdcFcjyDyDjxxServiceImpl.addTssbLog(bdcCzrzDO);
                    }
                }
            }
        }

    }

    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @param esfWqHtxxDTO
     * @param fcjyxxQO
    * @description 获取权利人信息
    **/
    private String handleEsfWqHtxx(EsfWqHtxxDTO esfWqHtxxDTO,FcjyxxQO fcjyxxQO){
        if(esfWqHtxxDTO != null && StringUtils.isNotBlank(esfWqHtxxDTO.getBdcdyh())){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(fcjyxxQO.getGzlslid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            List<String> bdcdyhList = bdcXmDOList.stream().map(BdcXmDO::getBdcdyh).collect(Collectors.toList());
            if(bdcdyhList.contains(esfWqHtxxDTO.getBdcdyh())){
                List<EsfWqHtxxZtxxDTO> ZtxxDTOS = esfWqHtxxDTO.getZtxx();
                if(CollectionUtils.isNotEmpty(ZtxxDTOS)){
                    for (EsfWqHtxxZtxxDTO esfWqHtxxZtxxDTO : ZtxxDTOS) {
                        BdcQlrDO bdcQlrDO = new BdcQlrDO();
                        bdcQlrDO.setQlrmc(esfWqHtxxZtxxDTO.getZtmc());
                        bdcQlrDO.setZjzl(Integer.parseInt(esfWqHtxxZtxxDTO.getZjlx()));
                        bdcQlrDO.setZjh(esfWqHtxxZtxxDTO.getZjh());
                        bdcQlrDO.setQlrlx(CommonConstantUtils.QLRLX_GR);
                        if(esfWqHtxxZtxxDTO.getZjlx()!= null && esfWqHtxxZtxxDTO.getZtlb().equals("0")){
                            bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        }else{
                            bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                        }
                        bdcQlrDO.setDh(esfWqHtxxZtxxDTO.getDhhm());
                        bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO,fcjyxxQO.getGzlslid(),"");
                    }

                }
            }else{
                return "查询成功，不动产单元号不一致不保存";
            }
            return "查询成功";
        }
        return null;
    }

    // 设置权利人权利比例
    private void setBdfe(List<BdcSlSqrDO> bdcSlSqrDOList) {
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            for(BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                if(Objects.nonNull(bdcSlSqrDO.getGyfs())) {
                    if (bdcSlSqrDO.getGyfs() == 0) {
                        bdcSlSqrDO.setQlbl("100");
                    } else if (bdcSlSqrDO.getGyfs() == 1) {
                        bdcSlSqrDO.setQlbl("50");
                    } else if(StringUtils.isBlank(bdcSlSqrDO.getQlbl())){
                        bdcSlSqrDO.setQlbl("100");
                    }
                }
            }
        }
    }

    // 根据配置的 tszjWjjmc 推送住建的文件夹名称，获取文件信息进行推送
    private void generateZjFjxx(String gzlslid, ZjClfQdDTO zjClfQdDTO){
        List<ZjClfFjxxDTO> fjxx = new ArrayList<>(10);
        List<BdcSlSjclDO> bdcSlSjclDOList = this.bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlSjclDOList) && StringUtils.isNotBlank(tszjWjjmc)){
            for(BdcSlSjclDO bdcSlSjclDO: bdcSlSjclDOList){
                if(tszjWjjmc.indexOf(bdcSlSjclDO.getClmc()) > -1){
                    //查询文件夹下文件
                    fjxx.addAll(this.handlerFjxx(gzlslid, bdcSlSjclDO.getClmc(), String.valueOf(bdcSlSjclDO.getSjlx())));
                }
                // 组织权利人电子签名
                if(StringUtils.equals(qlrqzWjjmc, bdcSlSjclDO.getClmc())){
                    List<ZjClfFjxxDTO> qlrqzxx = this.handlerFjxx(gzlslid, bdcSlSjclDO.getClmc(), String.valueOf(bdcSlSjclDO.getSjlx()));
                    if(CollectionUtils.isNotEmpty(qlrqzxx) && CollectionUtils.isNotEmpty(qlrqzxx.get(0).getIMGXX())){
                        List<ZjClfFjxxDTO.IMGXXBean> imgxxBeanList = qlrqzxx.get(0).getIMGXX();
                        zjClfQdDTO.setMSFDZQM(imgxxBeanList.get(0).getIMG());
                    }
                }
                // 组织义务人电子签名
                if(StringUtils.equals(ywrqzWjjmc, bdcSlSjclDO.getClmc())){
                    List<ZjClfFjxxDTO> qlrqzxx = this.handlerFjxx(gzlslid, bdcSlSjclDO.getClmc(), String.valueOf(bdcSlSjclDO.getSjlx()));
                    if(CollectionUtils.isNotEmpty(qlrqzxx) && CollectionUtils.isNotEmpty(qlrqzxx.get(0).getIMGXX())){
                        List<ZjClfFjxxDTO.IMGXXBean> imgxxBeanList = qlrqzxx.get(0).getIMGXX();
                        zjClfQdDTO.setCRFDZQM(imgxxBeanList.get(0).getIMG());
                    }
                }
            }
            zjClfQdDTO.setFjxx(fjxx);
        }
    }

    // 获取材料名称下面的文件内容
    private  List<ZjClfFjxxDTO> handlerFjxx(String gzlslid, String clmc, String sjlx){
        List<ZjClfFjxxDTO> fjxx = new ArrayList<>(10);
        //查询文件夹下文件
        List<StorageDto> list = storageClientMatcher.listStoragesByName("clientId", gzlslid, null, clmc, 1, 0);
        if(CollectionUtils.isNotEmpty(list)){
            List<StorageDto> listFile = storageClientMatcher.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null,0,null);
            if (CollectionUtils.isNotEmpty(listFile)) {
                int i = 1;
                ZjClfFjxxDTO zjClfFjxxDTO = new ZjClfFjxxDTO();
                zjClfFjxxDTO.setFJMC(clmc);
                zjClfFjxxDTO.setFJLX(this.getDsfZdDzxx("BDC_SL_SJLX", "ZJ", sjlx));
                List<ZjClfFjxxDTO.IMGXXBean> imgxxBeanList = new ArrayList<>(listFile.size());
                for (StorageDto storage : listFile) {
                    BaseResultDto baseResultDto = storageClientMatcher.downloadBase64(storage.getId());
                    ZjClfFjxxDTO.IMGXXBean imgxxBean = new ZjClfFjxxDTO.IMGXXBean();
                    imgxxBean.setYM(String.valueOf(i++));
                    String img = baseResultDto.getMsg();
                    if(StringUtils.isNotBlank(img)){
                        img = img.split(",")[1];
                    }
                    imgxxBean.setIMG(img);
                    imgxxBeanList.add(imgxxBean);
                }
                zjClfFjxxDTO.setIMGXX(imgxxBeanList);
                fjxx.add(zjClfFjxxDTO);
            }
        }
        return fjxx;
    }

    // 获取第三方对照信息
    public String getDsfZdDzxx(String zdbs, String dsfzdbs, String bdczdz) {
        //数据归属地区进行对照
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setBdczdz(bdczdz);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfzdbs);
        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        if (null != result && StringUtils.isNotBlank(result.getDsfzdz())) {
            return result.getDsfzdz();
        }
        return "";
    }

}
