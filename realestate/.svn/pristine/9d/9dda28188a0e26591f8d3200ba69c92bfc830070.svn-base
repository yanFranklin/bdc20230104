package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxCsDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcDyqdDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFczmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.core.mapper.BdcFczmMapper;
import cn.gtmap.realestate.inquiry.service.BdcFczmService;
import cn.gtmap.realestate.inquiry.service.BdcZfxxCxService;
import cn.gtmap.realestate.inquiry.util.BdcFczmUtils;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.PDF_HHF;
import static cn.gtmap.realestate.common.util.CommonConstantUtils.ZF_YW_DH;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/17
 * @description 住房查询：房产证明打印服务逻辑类
 */
@Service
public class BdcFczmServiceImpl implements BdcFczmService {
    private static final String NO_ZFXX_FOUND = "查询子系统：未获取打印住房查询证明的XML数据";
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZfxxCxServiceImpl.class);
    /**
     * 日期格式
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分");
    /**
     * 日期格式
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式
     */
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * Redis操作
     */
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 住房接口
     */
    @Autowired
    private BdcZfxxCxService bdcZfxxCxService;
    /**
     * 打印服务
     */
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;
    /**
     * 编号服务
     */
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    /**
     * 人员信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcFczmMapper bdcFczmMapper;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcFczmUtils bdcFczmUtils;

    /**
     * 流水号位数配置，默认4位
     */
    @Value("${yancheng.yfwfzm.lshws:4}")
    private String lshws;

    /**
     * 模板中受理编号模板
     */
    @Value("${yancheng.yfwfzm.slbhmb:}")
    private String slbhmb;

    /**
     * 打印清单受理编号格式
     */
    @Value("${yancheng.dyqd.bhmb:}")
    private String dyqdbhmb;

    /**
     * 盐城综合查询打印清单编号流水号位数，默认6位
     */
    @Value("${yancheng.dyqd.lshwss:6}")
    private String dyqdlshws;

    /**
     * 盐城综合查询打印有无土地信息证明受理编号格式
     */
    @Value("${yancheng.ywtdxxzm.bhmb:}")
    private String ywtdxxzmbhmb;

    /**
     * 盐城综合查询打印有无土地信息证明编号流水号位数，默认6位
     */
    @Value("${yancheng.ywtdxxzm.lshwss:6}")
    private String ywtdxxzmlshws;

    /**
     * 注销产权查询年限
     */
    @Value("${yancheng.yfwfzmls.zxcqcxnx:3}")
    private String zxcqcxnx;

    /**
     * 注销产权下一手流程定义ID
     */
    @Value("${yancheng.yfwfzmls.zxcqxyslc:123123}")
    private String zxcqxyslc;

    /**
     * 房产证明版本
     */
    @Value("${fczm.version:}")
    private String fczmversion;

    /**
     * 盐城打印清单排序限制权利状态权重优先级（单个限制状态时，按照设置的顺序排）
     */
    @Value("${dyqd.xzqlqz:查封,预查封,抵押,预抵押,预告,异议,地役,锁定}")
    private String xzqlqz;

    /**
     * 综合查询有房无房证明是程序获取数据，默认fr3打印
     * 现为支持pdf打印，房产信息子表可设置字体等，子表ID可配置
     */
    @Value("${zhcx.yfwfzm.zfxxtable:}")
    private String zfxxTable;

    /**
     * 综合查询有房无房证明是程序获取数据，默认fr3打印
     * 现为支持pdf打印，权利人子表可设置字体等，子表ID可配置
     */
    @Value("${zhcx.yfwfzm.qlrxxtable:}")
    private String qlrxxTable;

    /**
     * 不动产权证号分割长度（太长会超出表格，分割换行）
     */
    @Value("${zhcx.yfwfzm.bdcqzhfgcd:}")
    private String bdcqzhfgcd;

    /**
     * 综合查询有房无房证明：房产信息行政区域配置，例如 {'320800':'淮安市','320805':'淮安市','320802':'淮安市','320826':'涟水县','320831':'金湖县'}
     */
    @Value("#{${zhcx.yfwfzm.xzqy: null}}")
    private Map<String, String> xzqy;

    /**
     * 有房无房证明,是否打印第一个申请人，默认true
     */
    @Value("${zhcx.yfwfzm.sfdysqr:true}")
    private Boolean sfdysqr;

    @Autowired
    private EntityMapper entityMapper;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxCsDTO 参数信息
     * @return {String} 缓存KEY值
     * @description 将住房查询证明请求的参数--权利人信息缓存到Redis
     */
    @Override
    public String saveZfxxQlrxxToRedis(BdcZfxxCsDTO bdcZfxxCsDTO){
        if(null == bdcZfxxCsDTO || CollectionUtils.isEmpty(bdcZfxxCsDTO.getQlrQOList())){
            throw new NullPointerException("查询子系统：住房查询证明请求的参数--权利人信息为空！");
        }

        // 保存至Redis
        String key = CommonConstantUtils.REDIS_INQUIRY_ZFXX + UUIDGenerator.generate();
        // 权利人
        if(CollectionUtils.isNotEmpty(bdcZfxxCsDTO.getQlrQOList())){
            redisUtils.addHashValue(key, Constants.ZFXX_QLRXX, JSON.toJSONString(bdcZfxxCsDTO.getQlrQOList()), 120);
        }
        // 家庭成员
        if(CollectionUtils.isNotEmpty(bdcZfxxCsDTO.getJtcyDOList())){
            redisUtils.addHashValue(key, Constants.JTCY_QLRXX, JSON.toJSONString(bdcZfxxCsDTO.getJtcyDOList()), 120);
        }
        //权利人曾用名
        if(CollectionUtils.isNotEmpty(bdcZfxxCsDTO.getQlrcymQOList())){
            redisUtils.addHashValue(key, Constants.ZFXX_QLRCYMXX, JSON.toJSONString(bdcZfxxCsDTO.getQlrcymQOList()), 120);
        }
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getEwmurl())) {
            redisUtils.addHashValue(key, Constants.ZFXX_EWM, bdcZfxxCsDTO.getEwmurl(), 120);
        }
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getBdcdyh())) {
            redisUtils.addHashValue(key, Constants.ZFXX_BDCDYH, bdcZfxxCsDTO.getBdcdyh(), 120);
        }
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getCxmd())){
            redisUtils.addHashValue(key, Constants.ZFXX_CXMD, bdcZfxxCsDTO.getCxmd(), 120);
        }
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getUseralias())){
            redisUtils.addHashValue(key, Constants.ZFXX_USERALIAS, bdcZfxxCsDTO.getUseralias(), 120);
        }
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getVersion())){
            redisUtils.addHashValue(key, Constants.VERSION, bdcZfxxCsDTO.getVersion(), 120);
        }
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getPrintType())){
            redisUtils.addHashValue(key, Constants.PRINT_TYPE, bdcZfxxCsDTO.getPrintType(), 120);
        }
        // 权籍管理代码，行政区划
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getQjgldm())){
            redisUtils.addHashValue(key, Constants.ZFXX_QJGLDM, bdcZfxxCsDTO.getQjgldm(), 120);
        }
        // 是否需要过滤规划用途
        if(StringUtils.isNotBlank(bdcZfxxCsDTO.getSfghyt())){
            redisUtils.addHashValue(key, Constants.ZFXX_SFGHYT, bdcZfxxCsDTO.getSfghyt(), 120);
        }
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 参数信息Key
     * @return {String} XML数据
     * @description 获取打印住房查询证明的XML数据
     */
    @Override
    public String getPrintXmlOfZfxx(String key) {
        if(StringUtils.isBlank(key)){
            throw new AppException(NO_ZFXX_FOUND);
        }

        // 获取缓存参数
        List<BdcQlrQO> qlrQOList = this.listQlrxx(key);
        // 获取要打印的不动产单元
        String bdcdyh = redisUtils.getHashValue(key, Constants.ZFXX_BDCDYH);
        // 版本
        String version = redisUtils.getHashValue(key, Constants.VERSION);
        // 权籍管理代码
        String qjgldm = redisUtils.getHashValue(key, Constants.ZFXX_QJGLDM);
        // 是否过滤规划用途
        String sfghyt = redisUtils.getHashValue(key, Constants.ZFXX_SFGHYT);

        // 家庭成员中有曾用名 张三（张小三） 查询的时候张三和
        List<BdcQlrQO> qlrJtcy = this.getJtcyQlrList(key);

        List<BdcQlrQO> cfQlrJtcyCym = dealJtcyCym(qlrJtcy);

        // 家庭成员
        qlrQOList.addAll(CollectionUtils.isNotEmpty(cfQlrJtcyCym)?cfQlrJtcyCym:qlrJtcy);
        //曾用名
        qlrQOList.addAll(listQlrcymxx(key));

        LOGGER.info("有房无房历史查询参数：{}",JSONObject.toJSONString(qlrQOList));

        // 获取权利人住房信息
        List<BdcZfxxDTO> zfxxDTOList = this.queryZfxx(qlrQOList, bdcdyh, version,qjgldm, sfghyt);

        // 合并统一房产信息，例如连云港要求家庭查询时候同一个房产的权利人合并展示且显示一条即可
        zfxxDTOList = bdcFczmUtils.hbxtfc(zfxxDTOList);

        // 生成主表数据
        Map<String, String> parentData = this.getParentData(key, zfxxDTOList, version, this.listQlrxx(key),listQlrcymxx(key));
        // 生成子表数据（重新获取下权利人数据，避免上面查询证号被修改）
        Multimap<String, List> childData = this.getChildData(this.listQlrxx(key), zfxxDTOList, version, key);

        // 舒城页面用的合肥页面，如果配置了版本，将version改成舒城的
        if(StringUtils.isNotBlank(fczmversion)){
            version = fczmversion;
        }
        // 设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(this.getFr3Xml(version, key));
        bdcDysjDTOList.add(bdcDysjDTO);

        // 获取打印XML数据
        return  bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    private static List<BdcQlrQO> dealJtcyCym(List<BdcQlrQO> qlrJtcy){
        List<BdcQlrQO> cfQlrJtcy = new ArrayList<>();
        for(BdcQlrQO bdcQlrQO : qlrJtcy){
            if(bdcQlrQO.getQlrmc().indexOf("（") > -1 && bdcQlrQO.getQlrmc().indexOf("）") > -1){
                BdcQlrQO cymQO = bdcQlrQO;
                String [] mcArr = bdcQlrQO.getQlrmc().split("（");
                if(mcArr.length == 2){
                    String ymc = mcArr[0];
                    String cymc = mcArr[1].substring(0,mcArr[1].length()-1);

                    BdcQlrQO bdcQlrQO1 = new BdcQlrQO();
                    BeanUtils.copyProperties(bdcQlrQO,bdcQlrQO1);

                    bdcQlrQO1.setQlrmc(ymc);
                    cfQlrJtcy.add(bdcQlrQO1);

                    bdcQlrQO.setQlrmc(cymc);
                    cfQlrJtcy.add(bdcQlrQO);
                }

            }else{
                cfQlrJtcy.add(bdcQlrQO);
            }
        }
        return cfQlrJtcy;
    }

    /**
     * 查询权利人房产信息
     * @param qlrQOList 权利人信息
     * @param bdcdyh 不动产单元号
     * @param version 版本信息
     * @param qjgldm 权籍管理代码
     * @param sfghyt 是否过滤规划用途
     * @return 房产信息
     */
    private List<BdcZfxxDTO> queryZfxx(List<BdcQlrQO> qlrQOList, String bdcdyh, String version, String qjgldm, String sfghyt) {
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrQOList);
        bdcZfxxQO.setBdcdyh(bdcdyh);
        bdcZfxxQO.setCxly("1");
        bdcZfxxQO.setQjgldm(qjgldm);
        bdcZfxxQO.setSfghyt(sfghyt);

        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)) {
            bdcZfxxQO.setHbcq(CommonConstantUtils.YES);
            return bdcZfxxCxService.listBdcNtZfxxDTO(bdcZfxxQO);
        }
        return bdcZfxxCxService.listBdcZfxxDTO(bdcZfxxQO);
    }

    /**
     * 根据不同版本采用对应的XML打印数据结构
     * @param version 版本
     * @return XML打印结构
     */
    private String getFr3Xml(String version, String key) {
        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)) {
            // 盐城
            String printType = redisUtils.getHashValue(key, Constants.PRINT_TYPE);
            if(Constants.YC_YFWFZMLS.equals(printType)) {
                // 历史房产证明
                return Constants.YC_ZFXX_LS_FR3_ZD;
            }
            // 现势房产证明
            return Constants.YC_ZFXX_FR3_ZD;
        }
       if(CommonConstantUtils.SYSTEM_VERSION_SC.equals(version)) {
            // 舒城房产证明
            return Constants.ZFXX_FR3_ZD_SC;
        }

        // 合肥房产证明
        return Constants.ZFXX_FR3_ZD;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list [{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
     * @return {String} redis key
     * @description 缓存房产档案不动产单元号到redis
     */
    @Override
    public String saveBdcFcdaBdcdyhToRedis(List<BdcFczmQO> list) {
        String key = CommonConstantUtils.REDIS_INQUIRY_FCDA + UUIDGenerator.generate();
        redisUtils.addStringValue(key,JSON.toJSONString(list),120);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印房产档案的XML数据
     */
    @Override
    public String getPrintXmlOfFcda(String key) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：房产档案没有参数信息");
        }

        // 从redis获取缓存的参数信息
        String json = redisUtils.getStringValue(key);
        List<BdcFczmQO> bdcFczmQOList  = JSON.parseArray(json, BdcFczmQO.class);

        // 封装参数
        Map<String, List<Map>> printParam = new HashMap<>(8);
        List<Map> paramList = new ArrayList<>();

        for(BdcFczmQO bdcFczmQO : bdcFczmQOList){
            Map<String, Object> param = new HashMap<>(8);
            param.put("bdcdyh", bdcFczmQO.getBdcdyh());
            param.put("qszt", bdcFczmQO.getQszt());
            param.put("gzlslid", bdcFczmQO.getGzlslid());
            param.put("bdcqzh", bdcFczmQO.getBdcqzh());
            param.put("jbr", bdcFczmQO.getUseralias());

            paramList.add(param);
        }

        // 打印模板：bdcZfxxDa.fr3
        printParam.put("bdcZfxxDa", paramList);
        return bdcPrintFeignService.print(printParam);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 参数信息Key
     * @description 获取主表数据
     */
    protected Map<String, String> getParentData(String key,  List<BdcZfxxDTO> zfxxDTOList, String version, List<BdcQlrQO> qlrQOList,List<BdcQlrQO> qlrcymQOList) {
        Map<String,String> parentData = new HashMap<>(1);
        // 获取二维码内容
        String ewmurl = redisUtils.getHashValue(key, Constants.ZFXX_EWM);

        // 获取查询目的内容
        String cxmd = redisUtils.getHashValue(key, Constants.ZFXX_CXMD);
        parentData.put("cxmd", (StringUtils.isBlank(cxmd)|| StringUtils.equals("null",cxmd))? "/" : cxmd);

        // 当前登录用户中文名（通过页面查询缓存获取不直接调服务获取因为打印的服务被配置权限忽略了）
        String userAlias = redisUtils.getHashValue(key, Constants.ZFXX_USERALIAS);
        parentData.put("jbr", StringUtils.isBlank(userAlias) ? " " : userAlias);

        // 权籍管理代码
        String qjgldm = redisUtils.getHashValue(key, Constants.ZFXX_QJGLDM);
        List<Map> zdList = bdcZdFeignService.queryBdcZd("qjgldm");
        // 盐城地市没有权籍管理代码字典
        if (CollectionUtils.isNotEmpty(zdList)){
            // 处理权籍代码的特殊情况,为null或有多个，取第一行
            if( "null".equals(qjgldm) || qjgldm.contains(",")) {
                qjgldm = zdList.get(0).get("DM").toString();
            }
            String qjglmc = StringToolUtils.convertBeanPropertyValueOfZdByString(qjgldm, zdList);
            parentData.put("qjglmc", qjglmc);
        }
        String qy = "";
        if ("340100".equals(qjgldm)){
            qy = "(不含肥东县、肥西县、长丰县、庐江县和巢湖市)";
        }
        parentData.put("qy", qy);
        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)) {
            // 获取流水号
            String yfwf = CollectionUtils.isEmpty(zfxxDTOList) ? "无房" : "有房";
            String cxbh = this.getYcDybh(yfwf);
            try {
                parentData.put("ewm", ewmurl + URLEncoder.encode(cxbh, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("打印有房无房证明二维码编码失败");
            }
            parentData.put("cxbh", cxbh);

            // 申请人
            if(CollectionUtils.isNotEmpty(qlrQOList) && null != qlrQOList.get(0)) {
                parentData.put("sqr", qlrQOList.get(0).getQlrmc());
                parentData.put("sqrzjh", qlrQOList.get(0).getZjh());
            } else {
                parentData.put("sqr", "/");
                parentData.put("sqrzjh", "/");
            }
            // 曾用名
            if(CollectionUtils.isNotEmpty(qlrcymQOList) && null != qlrcymQOList.get(0)) {
                parentData.put("sqrcym", qlrcymQOList.get(0).getQlrmc());
            }else {
                parentData.put("sqrcym", "/");
            }

            // 查询时间
            parentData.put("dysj", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
        } else if(StringUtils.isNotBlank(fczmversion)&&CommonConstantUtils.SYSTEM_VERSION_SC.equals(fczmversion)){
            // 获取流水号
            String lsh = this.getDybh();
            parentData.put("ewm", ewmurl + lsh);
            parentData.put("bh", lsh);
            // 当前查询时间
            parentData.put("dqcxsj", DateFormatUtils.format(new Date(), "yyyy年MM月dd日"));
            // 打印时间
            parentData.put("dysj", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
        } else {
            // 获取流水号
            String lsh = this.getDybh();
            parentData.put("ewm", ewmurl + lsh);
            parentData.put("bh", lsh);

            // 查询时间
            parentData.put("dysj", LocalDateTime.now().format(formatter));
        }

        return parentData;
    }

    /**
     * 获取盐城有房无房打印编号
     * @param yfwf 有房无房标识
     * @return 证明编号
     */
    private String getYcDybh(String yfwf) {
        if(StringUtils.isBlank(slbhmb)) {
            slbhmb = "yfwf-nf-区号-qwm-lsh";
        }

        String cxbh = slbhmb.replace("yfwf", yfwf);
        cxbh = cxbh.replace("nf", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        Integer sxh = bdcBhFeignService.queryLsh(Constants.BDC_YC_ZSZM_XLH, "YEAR");
        String lsh = StringToolUtils.appendZero(String.valueOf(sxh), Integer.valueOf(lshws));
        cxbh = cxbh.replace("lsh", lsh).replaceAll("-", "");
        return cxbh;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qlrQOList 权利人信息
     * @param zfxxDTOList 住房信息
     * @param version 版本
     * @param key redis缓存参数key
     * @description 获取子表数据
     */
    protected Multimap<String, List> getChildData(List<BdcQlrQO> qlrQOList, List<BdcZfxxDTO> zfxxDTOList, String version, String key) {
        Multimap<String,List> childData = ArrayListMultimap.create();

        // 权利人信息
        List<Map<String, String>> qlrxx = getQlrList(qlrQOList);
        childData.put("ZfxxQlrxx", qlrxx);
        // 自定义设置PDF打印有房无房证明房产信息子表ID
        if(StringUtils.isNotBlank(qlrxxTable)) {
            childData.put(qlrxxTable, qlrxx);
        }

        // 房产信息
        String split = CommonConstantUtils.SYSTEM_VERSION_YC.equals(version) ? "/" : "——";
        List<Map<String, String>> fcxx = this.getFczmZfxxList(zfxxDTOList, split);
        childData.put("ZfxxList", fcxx);
        // 自定义设置PDF打印有房无房证明房产信息子表ID
        if(StringUtils.isNotBlank(zfxxTable)) {
            childData.put(zfxxTable, fcxx);
        }

        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)) {
            // 家庭成员
            childData.put("JtcyList", this.getJtcyList(key));
            // 历史房产
            this.getLsZszm(key, childData, split);
        }

        return childData;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取有房无房证明中权利人信息子表
     */
    private List<Map<String, String>> getQlrList(List<BdcQlrQO> qlrQOList) {
        int index = 1;
        List<Map<String,String>> qlrxxList = new ArrayList<>();

        for(BdcQlrQO bdcQlrQO : qlrQOList){
            Map map = new HashMap(3);
            map.put("xh", index++);
            map.put("qlrmc", bdcQlrQO.getQlrmc());
            map.put("zjh", bdcQlrQO.getZjh());
            map.put("count", qlrQOList.size());
            map.put("ysqrgx", bdcQlrQO.getYsqrgx());
            
            if (!sfdysqr && CommonConstantUtils.YSQRGX_BR_MC.equals(map.get("ysqrgx"))) {
                continue;
            }
            qlrxxList.add(map);
        }
        return qlrxxList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zfxxDTOList 住房信息
     * @description 获取有房无房证明中房产信息子表
     */
    private List<Map<String, String>> getFczmZfxxList(List<BdcZfxxDTO> zfxxDTOList, String split) {
        int index = 1;
        List<Map<String,String>> result = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(zfxxDTOList)){
            for(BdcZfxxDTO zfxx : zfxxDTOList){
                Map map = new HashMap();
                map.put("xh", index++);
                map.put("qlrmc", zfxx.getQlrmc());
                map.put("qlrzjh", zfxx.getQlrzjh());
                map.put("bdcqzh", zfxx.getBdcqzh());
                setZhZjhHh(zfxx, map);

                if(StringUtils.isBlank(zfxx.getYxtcqzh())){
                    map.put("bdcqzh2", zfxx.getBdcqzh());
                } else {
                    map.put("bdcqzh2", zfxx.getBdcqzh() + "/" + zfxx.getYxtcqzh());
                }

                map.put("yxtcqzh", zfxx.getYxtcqzh());
                map.put("zl", zfxx.getZl());
                map.put("jzmj", zfxx.getJzmj());
                map.put("ghyt", zfxx.getGhyt());
                map.put("gyqk", zfxx.getGyqk());
                map.put("fj", zfxx.getFj());
                map.put("bdcdyh", zfxx.getBdcdyh());
                map.put("djsj", null == zfxx.getDjsj() ? split : DateFormatUtils.format(zfxx.getDjsj(), DateUtils.sdf));
                map.put("djsj2", null == zfxx.getDjsj() ? split : DateFormatUtils.format(zfxx.getDjsj(), DateUtils.sdf_ymdhms));
                map.put("zslx", "4".equals(String.valueOf(zfxx.getZslx())) ? "网签合同" : "证书");
                map.put("xzzt", getXzztmc(zfxx));
                map.put("count", zfxxDTOList.size());

                // 不同的区划代码可能对应一个行政区域名称，例如市本级的几个区域
                if(MapUtils.isNotEmpty(xzqy)) {
                    map.put("xzqy", MapUtils.getString(xzqy, zfxx.getQxdm()));
                } else {
                    map.put("xzqy", zfxx.getQxdm());
                }

                // 需求62944：bdcdyh 为丘号时返回空值，原证书字段分列，分为 证书号与网签合同号里两个字段（为了现场可能需求变动，保留原字段，新增不同格式值字段）
                if(StringUtils.isNotBlank(zfxxTable)) {
                    // 只有PDF打印时候处理
                    if (CommonConstantUtils.SF_S_DM.equals(zfxx.getSfhtba())) {
                        map.put("wqhth", zfxx.getBdcqzh());
                        map.put("wqhth2", bdcFczmUtils.addNewLine(zfxx.getBdcqzh(), StringUtils.isBlank(bdcqzhfgcd) ? 12 : Integer.parseInt(bdcqzhfgcd)));
                    } else {
                        map.put("bdcqzh3", zfxx.getBdcqzh());
                        map.put("bdcqzh4", bdcFczmUtils.addNewLine(zfxx.getBdcqzh(), StringUtils.isBlank(bdcqzhfgcd) ? 12 : Integer.parseInt(bdcqzhfgcd)));
                        map.put("bdcdyh2", zfxx.getBdcdyh());
                        map.put("bdcdyh3", bdcFczmUtils.addNewLine(zfxx.getBdcdyh(), 14));
                    }
                }

                // 舒城新增
                setScZd(zfxx, map);
                result.add(map);
            }
        } else {
            Map map = new HashMap();
            map.put("xh", split);
            map.put("qlrmc", split);
            map.put("qlrzjh", split);
            map.put("qlrzjh2", split);
            map.put("bdcqzh", split);
            map.put("bdcqzh2", split);
            map.put("yxtcqzh", split);
            map.put("zl", split);
            map.put("jzmj", split);
            map.put("ghyt", split);
            map.put("gyqk", split);
            map.put("fj", split);
            map.put("bdcdyh", split);
            map.put("djsj", split);
            map.put("djsj2", split);
            map.put("zslx", split);
            map.put("xzzt", split);
            map.put("count", "1");
            map.put("xzqy", split);
            if(StringUtils.isNotBlank(fczmversion)&&CommonConstantUtils.SYSTEM_VERSION_SC.equals(fczmversion)){
                map.put("qlxz", "—/—");
            }
            result.add(map);
        }
        return result;
    }

    /**
     * 设置证号、证件号换行以支持PDF换行展示
     * @param zfxx 房产信息
     * @param map 打印返回信息
     */
    private void setZhZjhHh(BdcZfxxDTO zfxx, Map map) {
        if(StringUtils.isNotBlank(zfxxTable)) {
            map.put("qlrzjh2", StringUtils.join(StringUtils.split(zfxx.getQlrzjh(), ZF_YW_DH), PDF_HHF));
            // 不动产权证号添加换行符
            if(StringUtils.isNotBlank(bdcqzhfgcd) && StringUtils.isNotBlank(zfxx.getBdcqzh())){
                map.put("bdcqzh", bdcFczmUtils.addNewLine(zfxx.getBdcqzh(), Integer.valueOf(bdcqzhfgcd)));
            }
        } else {
            map.put("qlrzjh2", zfxx.getQlrzjh());
        }
    }

    /**
     * 设置舒城特殊字段
     * @param zfxx 房产信息
     * @param map 打印返回信息
     */
    private void setScZd(BdcZfxxDTO zfxx, Map map) {
        if(StringUtils.isNotBlank(fczmversion)&& CommonConstantUtils.SYSTEM_VERSION_SC.equals(fczmversion)){
            // 权利性质，舒城新增
            if(StringUtils.isBlank(zfxx.getQlxzmc())){
                zfxx.setQlxzmc("—");
            }
            if(StringUtils.isBlank(zfxx.getFwxzmc())){
                zfxx.setFwxzmc("—");
            }
            map.put("qlxz", zfxx.getQlxzmc()+"/"+zfxx.getFwxzmc());
            // 权利人名称展示所有共有人
            map.put("qlrmc", zfxx.getQlr().replace(",","/"));
        }
    }

    /**
     * 拼接展示限制信息
     * @param zfxx 房产信息
     * @return 限制信息
     */
    private String getXzztmc(BdcZfxxDTO zfxx) {
        if(null == zfxx) {
            return "";
        }

        List<String> xzztmc = new ArrayList<>();
        if(null != zfxx.getSfcf() && zfxx.getSfcf()) {
            xzztmc.add("查封");
        }
        if(null != zfxx.getSfdya() && zfxx.getSfdya()) {
            xzztmc.add("抵押");
        }
        if(null != zfxx.getSfyy() && zfxx.getSfyy()) {
            xzztmc.add("异议");
        }
        if(null != zfxx.getSfyg() && zfxx.getSfyg()) {
            xzztmc.add("预告");
        }
        return StringUtils.join(xzztmc, "、");
    }

    /**
     *  获取缓存家庭成员，转换为权利人形式
     *
     *  获取家庭成员信息，目前只有盐城综合查询有家庭成员，查询房产需要和户主一起查询 20201204
     *  如果是合肥版本走此逻辑，因为没有家庭成员信息，获取缓存空，也就单独的权利人查询了
     *
     * @param key redis 缓存key
     * @return 家庭成员权利人集合
     */
    private List<BdcQlrQO> getJtcyQlrList(String key) {
        List<BdcJtcyDO> jtcyDOList = this.listJtcy(key);

        List<BdcQlrQO> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(jtcyDOList)) {
            for(BdcJtcyDO bdcJtcyDO : jtcyDOList) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setQlrmc(bdcJtcyDO.getJtcymc());
                bdcQlrQO.setZjh(bdcJtcyDO.getJtcyzjh());
                result.add(bdcQlrQO);
            }
        }
        return result;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis缓存参数key
     * @description 获取有房无房证明中家庭成员信息子表
     */
    private List<Map<String, String>> getJtcyList(String key) {
        List<BdcJtcyDO> jtcyDOList = this.listJtcy(key);

        int index = 1;
        List<Map<String,String>> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(jtcyDOList)){
            for(BdcJtcyDO jtcyDO : jtcyDOList){
                Map map = new HashMap();
                map.put("xh", index++);
                map.put("qlrmc", jtcyDO.getJtcymc());
                map.put("qlrzjh", jtcyDO.getJtcyzjh());
                map.put("qlrjtcygx", jtcyDO.getYhzgx());
                result.add(map);
            }
        } else {
            Map map = new HashMap();
            map.put("xh", index++);
            map.put("qlrmc", "/");
            map.put("qlrzjh", "/");
            map.put("qlrjtcygx", "/");
            result.add(map);
        }

        return result;
    }

    /**
     * 获取权利人历史房产信息（盐城）
     * @param key 查询参数redis缓存key
     * @param childData 子表数据集合对象
     * @param split 空默认符号
     */
    private void  getLsZszm(String key, Multimap<String,List> childData, String split) {
        if(StringUtils.isBlank(zxcqxyslc)) {
            LOGGER.error("盐城综合查询有房无房证明（历史）出证异常，原因：未定义注销产权下一手工作流定义ID，无法查询过滤");
            throw new AppException();
        }

        // 获取证明打印类型
        String printType = redisUtils.getHashValue(key, Constants.PRINT_TYPE);
        if(!Constants.YC_YFWFZMLS.equals(printType)) {
            return;
        }

        // 如果当前是打印历史证明（对应yancheng/zszm/bdcZszm.html中“有房无房证明（历史）”打印）
        // 只查询本人名下的历史产权数据
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(this.listQlrxx(key));
        bdcZfxxQO.setBdcdyh(redisUtils.getHashValue(key, Constants.ZFXX_BDCDYH));
        bdcZfxxQO.setCxly("1");
        bdcZfxxQO.setZxcqcxnx(DateUtils.getSpecifiedYear(-Integer.parseInt(zxcqcxnx), "yyyyMMdd"));
        bdcZfxxQO.setZxcqxyslc(zxcqxyslc);
        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxCxService.listBdcLsZfxxDTO(bdcZfxxQO);

        int index = 1;
        List<Map<String, String>> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(zfxxDTOList)) {
            for(BdcZfxxDTO bdcZfxxDTO : zfxxDTOList){
                Map map = new HashMap();
                map.put("xh", index++);
                map.put("yqlrmc", bdcZfxxDTO.getYqlrmc());
                map.put("yqlrzjh", bdcZfxxDTO.getYqlrzjh());
                map.put("zl", bdcZfxxDTO.getZl());
                map.put("jzmj", bdcZfxxDTO.getJzmj());
                map.put("bdcqzh", bdcZfxxDTO.getBdcqzh());
                map.put("ghyt", bdcZfxxDTO.getGhyt());
                map.put("zxdjsj", DateFormatUtils.format(bdcZfxxDTO.getZxdjsj(), DATE_FORMAT));
                result.add(map);
            }
        } else {
            Map map = new HashMap();
            map.put("xh", index++);
            map.put("yqlrmc", split);
            map.put("yqlrzjh", split);
            map.put("zl", split);
            map.put("bdcqzh", split);
            map.put("jzmj", split);
            map.put("ghyt", split);
            map.put("zxdjsj", split);
            result.add(map);
        }
        childData.put("LsZfxxList", result);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description 根据已选要打印抵押查封证明的产权信息，获取对应的不动产单元，保存至Redis中
     */
    @Override
    public String getBdcdyhRedisKeyOfDyacfzm(List<BdcZszmDTO> bdcZszmDTOList) {
        if(CollectionUtils.isEmpty(bdcZszmDTOList)){
            throw new NullPointerException("查询子系统：出具抵押查封证明未选中要导出数据");
        }

        // 获取对应的不动产单元号(旧版综合查询以产权为核心，需要关联查询证书对应不动产单元号；现在直接以物为核心，直接获取即可)
        Set<String> bdcdyhSet = bdcZszmDTOList.stream().filter(zszm ->
                StringUtils.isNotBlank(zszm.getBdcdyh())).map(BdcZszmDTO::getBdcdyh).collect(Collectors.toSet());
        if(CollectionUtils.isEmpty(bdcdyhSet)){
            throw new NullPointerException("查询子系统：出具抵押查封证明对应数据未查询到关联不动产单元号信息");
        }

        // 保存至Redis
        return redisUtils.addSetValue(CommonConstantUtils.REDIS_INQUIRY_BDCDYH + UUIDGenerator.generate(), bdcdyhSet, 120);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 不动产单元号对应Redis键
     * @return {String} XML数据
     * @description 获取抵押查封证明打印的XML数据
     */
    @Override
    public String getPrintXmlOfDyacfzm(String key) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：出具抵押查封证明未获取到要导出的数据");
        }

        // 获取不动产单元号
        Set<String> bdcdyhSet = redisUtils.getSetValue(key);
        if(CollectionUtils.isEmpty(bdcdyhSet)){
            throw new NullPointerException("查询子系统：出具抵押查封证明未获取到要导出的数据");
        }

        // 获取经办人、所在机构
        String userName = "";
        String orgName = "";
        try{
            userName = userManagerUtils.getCurrentUserName();
            orgName = userManagerUtils.getOrganizationByUserName(userName);
        } catch (Exception e){
            LOGGER.error("不动产查询子系统——证书证明查询打印抵押查封证明：获取当前经办人信息出错{}", e.getMessage());
        }

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(bdcdyhSet.size());
        for(String bdcdyh : bdcdyhSet){
            Map<String, Object> map = new HashMap<>(1);
            map.put("bdcdyh", bdcdyh);
            map.put("jbr", userName);
            map.put("jbjg", orgName);
            bdcdyhListMap.add(map);
        }
        /// 打印模板：bdcDyaCfZm.fr3
        paramMap.put("bdcDyaCfZm", bdcdyhListMap);

        return bdcPrintFeignService.print(paramMap);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人参数信息Redis键
     * @return {List} 权利人信息
     * @description 根据房产信息证明查询权利人参数信息
     */
    protected List<BdcQlrQO> listQlrxx(String key) {
        String json = redisUtils.getHashValue(key, Constants.ZFXX_QLRXX);
        if(StringUtils.isBlank(json)){
            throw new NullPointerException(NO_ZFXX_FOUND);
        }

        List<BdcQlrQO> qlrQOList = JSON.parseArray(json, BdcQlrQO.class);
        if(CollectionUtils.isEmpty(qlrQOList)){
            throw new NullPointerException(NO_ZFXX_FOUND);
        }
        return qlrQOList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人曾用名参数信息Redis键
     * @return {List} 权利人曾用名信息
     * @description 根据房产信息证明查询权利人曾用名参数信息
     */
    protected List<BdcQlrQO> listQlrcymxx(String key) {
        String json = redisUtils.getHashValue(key, Constants.ZFXX_QLRCYMXX);
        if(StringUtils.isNotBlank(json) &&!"null".equals(json)){
            return JSON.parseArray(json, BdcQlrQO.class);
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人参数信息Redis键
     * @return {List} 权利人信息
     * @description 获取缓存的家庭成员信息
     */
    protected List<BdcJtcyDO> listJtcy(String key) {
        String json = redisUtils.getHashValue(key, Constants.JTCY_QLRXX);
        if(StringUtils.isNotBlank(json)) {
            return JSON.parseArray(json, BdcJtcyDO.class);
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取打印编号（目前全局唯一编号，例如2019070100012）
     */
    private String getDybh(){
        String time = LocalDateTime.now().format(formatter2);
        Integer sxh = bdcBhFeignService.queryLsh(Constants.BDC_HF_ZSZM_XLH, "DAY");
        return  time + StringToolUtils.appendZero(String.valueOf(sxh), 5);
    }

    /**
     * 获取盐城打印清单的XML数据
     *
     * @param key reids缓存Key
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public String getPrintXmlOfDyqd(String key) {
        if(StringUtils.isBlank(key)){
            throw new AppException("未定义查询参数");
        }

        // 获取缓存参数
        String json = redisUtils.getHashValue(key, "data");
        if(StringUtils.isBlank(json)){
            throw new NullPointerException("未定义查询参数");
        }

        List<BdcDyqdDTO> bdcDyqdDTOList = JSON.parseArray(json, BdcDyqdDTO.class);
        if(CollectionUtils.isEmpty(bdcDyqdDTOList)){
            throw new NullPointerException("未定义查询参数");
        }

        // 生成主表数据
        Map<String, String> parentData = this.getDyqdParentData(key);
        // 生成子表数据（重新获取下权利人数据，避免上面查询证号被修改）
        Multimap<String, List> childData = this.getDyqdChildData(bdcDyqdDTOList);

        // 设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(Constants.YC_ZHCX_DYQD_FR3_ZD);
        bdcDysjDTOList.add(bdcDysjDTO);

        // 获取打印XML数据
        return  bdcPrintFeignService.printDatas(bdcDysjDTOList);

    }

    /**
     * 缓存盐城综合查询打印有无土地信息证明参数
     *
     * @param bdcQlrQO 权利人信息
     * @return BdcPrintParamQO 缓存参数等信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public BdcPrintParamQO saveYwtdxxzmParamToRedis(BdcQlrQO bdcQlrQO) {
        if(null == bdcQlrQO || StringUtils.isBlank(bdcQlrQO.getQlrmc())) {
            throw new AppException("未定义参数");
        }

        String key = CommonConstantUtils.YC_ZHCX_YWTDXXZM + UUIDGenerator.generate16();
        redisUtils.addHashValue(key, "qlrxx", JSON.toJSONString(bdcQlrQO), Long.valueOf("600"));

        //家庭成员
        if (CollectionUtils.isNotEmpty(bdcQlrQO.getJtcyDOList())){
            redisUtils.addHashValue(key,Constants.JTCY_QLRXX,JSON.toJSONString(bdcQlrQO.getJtcyDOList()),Long.valueOf("600"));
        }

        List<Map<String, String>> jtcyList = this.getJtcyList(key);
        List<BdcXtLscsDO> list = new ArrayList<>();
        for (int i = 0; i< jtcyList.size(); i++){
            BdcXtLscsDO bdcXtLscsDO = new BdcXtLscsDO();
            bdcXtLscsDO.setCsmc(key);
            bdcXtLscsDO.setCsz(String.valueOf(jtcyList.get(i).get("xh")));
            bdcXtLscsDO.setCsz1(jtcyList.get(i).get("qlrmc"));
            bdcXtLscsDO.setCsz2(jtcyList.get(i).get("qlrzjh"));
            bdcXtLscsDO.setCsz3(jtcyList.get(i).get("qlrjtcygx"));
            bdcXtLscsDO.setSfsc(1);
            list.add(bdcXtLscsDO);
        }
        Example example = new Example(BdcXtLscsDO.class);
        example.createCriteria().andEqualTo("csmc",key);
        entityMapper.deleteByExample(example);
        entityMapper.insertBatchSelective(list);
        // 经办人
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            redisUtils.addHashValue(key, "jbr", userDto.getAlias(), Long.valueOf("600"));
        }

        // 生成查询编号
        if(StringUtils.isBlank(ywtdxxzmbhmb)) {
            ywtdxxzmbhmb = "公开-nf-区号-lsh";
        }
        String cxbh = ywtdxxzmbhmb.replace("nf", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        Integer sxh = bdcBhFeignService.queryLsh(Constants.BDC_YC_YWTDXXZM_XLH, "YEAR");
        String lsh = StringToolUtils.appendZero(String.valueOf(sxh), Integer.valueOf(ywtdxxzmlshws));
        cxbh = cxbh.replace("lsh", lsh).replaceAll("-", "");
        redisUtils.addHashValue(key, "cxbh", cxbh, Long.valueOf("600"));

        BdcPrintParamQO paramQO = new BdcPrintParamQO();
        paramQO.setRedisKey(key);
        paramQO.setCxbh(cxbh);
        return paramQO;
    }

    /**
     * 获取盐城综合查询打印有无土地信息证明的XML数据
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public String getPrintXmlOfYwtdxxzm(String key) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：盐城综合查询打印有无土地信息证明没有参数信息");
        }

        String qlrxx = redisUtils.getHashValue(key, "qlrxx");
        BdcQlrQO bdcQlrQO = JSONArray.parseObject(qlrxx, BdcQlrQO.class);
        if(null == bdcQlrQO || StringUtils.isBlank(bdcQlrQO.getQlrmc())){
            throw new NullPointerException("查询子系统：盐城综合查询打印有无土地信息证明没有参数信息");
        }

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        Map<String, Object> param = new HashMap<>(1);
        param.put("jbr", redisUtils.getHashValue(key, "jbr"));
        param.put("cxbh", redisUtils.getHashValue(key, "cxbh"));
        param.put("qlrmc", bdcQlrQO.getQlrmc());
        param.put("qlrzjh", bdcQlrQO.getZjh());
        param.put("key",key);
        bdcdyhListMap.add(param);

        // 打印数据源
        paramMap.put("ywtdxxzm", bdcdyhListMap);
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 生成盐城综合查询打印清单模板XML数据主表内容
     * @param key redis数据key
     */
    private Map<String, String> getDyqdParentData(String key) {
        Map<String,String> parentData = new HashMap<>(1);

        // 生成查询编号
        if(StringUtils.isBlank(dyqdbhmb)) {
            dyqdbhmb = "公开-nf-区号-lsh";
        }
        String cxbh = dyqdbhmb.replace("nf", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        Integer sxh = bdcBhFeignService.queryLsh(Constants.BDC_YC_DYQD_XLH, "YEAR");
        String lsh = StringToolUtils.appendZero(String.valueOf(sxh), Integer.valueOf(dyqdlshws));
        cxbh = cxbh.replace("lsh", lsh).replaceAll("-", "");
        parentData.put("cxbh", cxbh);

        // 打印时间
        parentData.put("dysj", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH:mm:ss"));

        // 当前登录用户中文名（通过页面查询缓存获取不直接调服务获取因为打印的服务被配置权限忽略了）
        String userAlias = redisUtils.getHashValue(key, "useralias");
        parentData.put("jbr", StringUtils.isBlank(userAlias) ? " " : userAlias);

        return parentData;
    }

    /**
     * 生成盐城综合查询打印清单模板XML数据子表内容
     * @param bdcDyqdDTOList 选择的查询记录数据
     */
    private Multimap<String, List> getDyqdChildData(List<BdcDyqdDTO> bdcDyqdDTOList) {
        Multimap<String,List> childData = ArrayListMultimap.create();
        // 打印清单列表排序
        this.compareDyqd(bdcDyqdDTOList);
        // 处理限制状态字段格式
        for(BdcDyqdDTO bdcDyqdDTO : bdcDyqdDTOList) {
            if(StringUtils.isNotBlank(bdcDyqdDTO.getXzzt())) {
                if(StringUtils.equals("正常", bdcDyqdDTO.getXzzt())) {
                   // bdcDyqdDTO.setXzzt("/");
                } else {
                    bdcDyqdDTO.setXzzt(StringUtils.join(bdcDyqdDTO.getXzzt().split(" "), "/"));
                }
            } else {
                bdcDyqdDTO.setXzzt("正常");
            }

            if(StringUtils.isNotBlank(bdcDyqdDTO.getBdcdyh())) {
                if (!StringUtils.equals(bdcDyqdDTO.getJzmj(),"/")) {
                    bdcDyqdDTO.setJzmj(bdcFczmMapper.queryBdcdyJzmj(bdcDyqdDTO.getBdcdyh()));
                }
                // 根据不动产类型判断，当为土地时，用途取xm表的zdzhyt并转换成中文，面积取jsydsyq表的syqmj
                if(CommonConstantUtils.BDCLX_TD.equals(bdcDyqdDTO.getBdclx())){
                    bdcDyqdDTO.setJzmj(bdcDyqdDTO.getTdzsyqmj());
                    bdcDyqdDTO.setGhyt(bdcDyqdDTO.getTdyt());
                }
            }
        }

        childData.put("DyqdList", bdcDyqdDTOList);
        return childData;
    }

    /**
     * 盐城打印清单列表排序，排序规则：
     * 1、2个及以上限制的，按照限制数量，数量越多的往前排，相同数量限制的按照证号倒排
     * 2、只有一个限制的按照 查封抵押预告异议排，相同限制按证号倒排
     * 3、正常状态的都按证号倒排
     * @param bdcDyqdDTOList 打印清单列表数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void compareDyqd(List<BdcDyqdDTO> bdcDyqdDTOList) {
        // 获取限制权利状态权重优先级
        String[] xzqlqzArray = xzqlqz.split(ZF_YW_DH);
        Map<String, Integer> xzqlqzMap = new HashMap<>();
        for(int i = 0; i < xzqlqzArray.length; i++) {
            xzqlqzMap.put(xzqlqzArray[i], xzqlqzArray.length - i);
        }

        bdcDyqdDTOList.forEach(bdcDyqdDTO -> {
            if(StringUtils.isBlank(bdcDyqdDTO.getXzzt()) || "正常".equals(bdcDyqdDTO.getXzzt().trim())) {
                bdcDyqdDTO.setXzqlqz(0);
            } else {
                int xzqlLength = bdcDyqdDTO.getXzzt().split(" ").length;
                if(xzqlLength > 1) {
                    bdcDyqdDTO.setXzqlqz(xzqlLength * 10);
                } else {
                    bdcDyqdDTO.setXzqlqz(xzqlqzMap.get(bdcDyqdDTO.getXzzt().trim()));
                }
            }
        });

        bdcDyqdDTOList.sort((a, b) -> {
            if(a.getXzqlqz().equals(b.getXzqlqz())) {
                // 按证号倒排
                if(StringUtils.isBlank(a.getBdcqzh())) {
                    return 1;
                } else if(StringUtils.isBlank(b.getBdcqzh())) {
                    return -1;
                }
                return b.getBdcqzh().compareTo(a.getBdcqzh());
            } else {
                return b.getXzqlqz() - a.getXzqlqz();
            }
        });
    }
    public void sortZfxx(BdcQlrQO bdcQlrQO,Multimap<String, List> childData){
        Collection<List> zfxx = childData.get(zfxxTable);
        Collection<List> qlrxx = childData.get(qlrxxTable);
        Map<String,Integer> sort = new HashMap<>();
        String[] qlrList = bdcQlrQO.getQlrmc().split(",");
        for (int i=0;i<qlrList.length;i++){
            String qlr = qlrList[i];
            sort.put(qlr,i+1);
        }
        for (List<Map<String,String>> list : qlrxx) {
            for (int i=0;i<list.size();i++){
                Map<String, String> map = list.get(i);
                String qlr = map.get("qlrmc");
                if(sort.containsKey(qlr)){
                    if(sort.get(qlr)==1){
                        list.remove(i);
                    }else{
                        map.put("xh", String.valueOf(sort.get(qlr)-1));
                        map.put("count",String.valueOf(sort.get(qlr)-1));
                    }
                }
            }
            Collections.sort(list, new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    String str1 = o1.get("xh");
                    String str2 = o2.get("xh");
                    if(StringUtils.isNotBlank(str1) && StringUtils.isNotBlank(str2)){
                        return str1.compareTo(str2);
                    }else{
                        return -1;
                    }
                }
            });
        }
        for (List<Map<String,String>> list : zfxx) {
            for (Map<String, String> map : list) {
                String[] qlrmc = map.get("qlrmc").split(",");
                for (String qlr : qlrmc) {
                    if(sort.containsKey(qlr)){
                        map.put("sort", String.valueOf(sort.get(qlr)));
                        break;
                    }
                }
            }
            Collections.sort(list, new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    String str1 = o1.get("sort");
                    String str2 = o2.get("sort");
                    if(StringUtils.isNotBlank(str1) && StringUtils.isNotBlank(str2)){
                        if(!str1.equals(str2)){
                           return str1.compareTo(str2);
                        }
                    }
                    String time1 = o1.get("djsj");
                    String time2 = o2.get("djsj");
                    if(StringUtils.isNotBlank(time1) && StringUtils.isNotBlank(time2)){
                        return time1.compareTo(time2);
                    }else{
                        return -1;
                    }
                }
            });
            for (int i=0; i<list.size(); i++) {
                Map<String, String> map = list.get(i);
                map.put("xh", String.valueOf(i+1));
                map.put("count",String.valueOf(i+1));
            }
        }
    }
}
