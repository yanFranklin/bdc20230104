package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.enums.BdcZdEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcFczmMapper;
import cn.gtmap.realestate.inquiry.service.BdcBzbZhcxZmService;
import cn.gtmap.realestate.inquiry.service.BdcZfxxCxService;
import cn.gtmap.realestate.inquiry.util.BdcFczmUtils;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.core.enums.BdcZdEnum.YFWFCXFW;
import static cn.gtmap.realestate.common.util.CommonConstantUtils.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/03/02
 * @description （标准版）综合查询证明相关处理逻辑
 */
@Service
public class BdcBzbZhcxZmServiceImpl implements BdcBzbZhcxZmService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBzbZhcxZmServiceImpl.class);
    /**
     * 日期格式
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分");
    /**
     * 日期格式
     */
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 权属证明打印数据源
     */
    public static final String FWQSZM = "fwqszm";
    public static final String TDQSZM = "tdqszm";
    public static final String LQQSZM = "lqqszm";
    public static final String YFWFZMBHGS_1 = "1";

    public static final String FWBDCDYCX = "fwbdcdycx";
    public static final String TDBDCDYCX = "tdbdcdycx";

    @Autowired
    private BdcFczmMapper bdcFczmMapper;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * Redis操作
     */
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 合肥房产证明处理逻辑（标准版有些处理逻辑一致，直接复用合肥的）
     */
    @Autowired
    private BdcFczmServiceImpl bdcFczmService;

    /**
     * 打印服务
     */
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    /**
     * 住房信息查询
     */
    @Autowired
    private BdcZfxxCxService bdcZfxxCxService;

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

    /**
     * PDF打印
     */
    @Autowired
    private PdfController pdfController;

    /**
     * 公共逻辑处理
     */
    @Autowired
    private BdcFczmUtils bdcFczmUtils;

    /**
     * 打印数据源
     */
    @Autowired
    private BdcDypzFeignService bdcDypzFeignService;

    @Autowired
    protected BdcZdFeignService bdcZdFeignService;

    /**
     * 盐城综合查询打印权属证明受理编号格式
     */
    @Value("${yancheng.qszm.bhmb:}")
    private String qszmbhmb;

    /**
     * 盐城综合查询打印权属证明编号流水号位数，默认6位
     */
    @Value("${yancheng.qszm.lshwss:6}")
    private String qszmlshws;

    /**
     * 综合查询是否只打印选中数据默认不打
     */
    @Value("${zhcx.sfdyxzsj:false}")
    private Boolean sfdyxzsj;

    /**
     * 1.综合查询不需要打印的权利类型，根据登记小类配置，默认只不打印预告证明(淮安)
     * 2.如果配置了该项，只有配置的登记小类不打
     */
    @Value("${zhcx.bdydjxl:}")
    private String bdydjxl;

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
     * 综合查询有房无房证明默认部门名称
     */
    @Value("${zhcx.yfwfzm.mrbmmc:}")
    private String mrbmmc;

    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;

    /*
     * 设置可以在打印时编辑的角色
     * */
    @Value("${print.edit.role:}")
    private List<String> editRoleList;

    /**
     * 有房无房证明编号格式，由于不同地区格式要求可能不一样，每个地方配置对应标识，代码根据标识特殊处理
     * 1（连云港模式）有房证明编号样式：有房+年份+区号+流水号，无房证明编号样式：无房+年份+区号+流水号
     */
    @Value("${zhcx.yfwfzm.bhgs:}")
    private String bhgs;

    /**
     * 有房无房证明查询范围区划对应要展示名称
     * 例如：{'320722':'东海', '320724':'灌南'}
     */
    @Value("#{${zhcx.yfwfzm.xzqy: null}}")
    private LinkedHashMap<String, String> cxfwqhdz;

    /**
     * 有房无房证明,规划用途为zhcx.yfwfzm.bxsghyt时，是否查询展示房地产权表的用途名称：true 展示ytmc;  false 展示规划用途其他
     */
    @Value("${zhcx.yfwfzm.sfcxytmc:false}")
    private Boolean sfcxytmc;

    /**
     * 台账查询无需进行区域过滤的角色打印展示机构名称
     */
    @Value("${zhcx.yfwfzm.qsjgmc:全市不动产登记中心}")
    private String qsjgmc;

    /**
     * 有房无房证明,项目表ghyt为特定值时，取房地产权表的ytmc字段
     */
    @Value("#{'${zhcx.yfwfzm.bxsghyt:}'.split(',')}")
    private List<String> bxsghytList;
    /**
     * 连云港对于zfxx排序需求，默认false
     */
    @Value("${zhcx.sortZfxx:false}")
    private Boolean sortZfxx;
    /**
     * （标准版）保存有房无房证明打印参数信息
     * @param bdcQlrQO 权利人信息
     * @return {String} Redis key
     */
    @Override
    public String saveBzbYfwfzmXx(BdcQlrQO bdcQlrQO) {
        if (null == bdcQlrQO || StringUtils.isBlank(bdcQlrQO.getQlrmc())) {
            throw  new NullPointerException("查询子系统：房产证明参数为空");
        }

        if(null == bdcQlrQO.getCxdysj()) {
            bdcQlrQO.setCxdysj(false);
        }

        LOGGER.info("保存有房无房证明信息，sfdyxzsj参数：{}, Cxdysj:{}", sfdyxzsj, bdcQlrQO.getCxdysj());
        if (sfdyxzsj && !bdcQlrQO.getCxdysj()){
            //出示无房证明
            Boolean cswfzm = false;
            //通过权利人信息获取的住房信息
            List<BdcZfxxDTO> zfxxDTOList = this.getZfxx(bdcQlrQO);
            //去除zfxx中配置的无需打印的djxl数据
            if (StringUtils.isNotBlank(bdydjxl)){
                zfxxDTOList = zfxxDTOList.stream().filter(o -> !StringToolUtils.existItemEquals(o.getDjxl(), bdydjxl.split(","))).collect(Collectors.toList());
            }
            List<BdcZfxxDTO> zfxxDtoFilterList = new ArrayList<>();
            //对当前用户进行区县代码过滤
            List<String> filterQx = Container.getBean(BdcConfigUtils.class).qxdmFilter("zhcx","",bdcQlrQO.getModuleCode());
            bdcQlrQO.setQxdmList(filterQx);
            if (CollectionUtils.isNotEmpty(filterQx)){
                for (String qx : filterQx){
                    List<BdcZfxxDTO> list = zfxxDTOList.stream().filter(zfxxDto -> qx.equals(zfxxDto.getQxdm())).collect(Collectors.toList());
                    zfxxDtoFilterList.addAll(list);
                }
                if (CollectionUtils.isEmpty(zfxxDtoFilterList)){
                    cswfzm = true;
                }
            }
            if (!cswfzm){
                Set<String> xmid = new HashSet<>();
                //选中数据
                List<BdcZfxxDTO> zfxxDTOListXz = bdcQlrQO.getZfxxDTOList();
                Set<String> xmidXz = new HashSet<>();
                for (BdcZfxxDTO bdcZfxxDTO : zfxxDTOListXz){
                    xmidXz.add(bdcZfxxDTO.getXmid());
                }
                if (CollectionUtils.isNotEmpty(zfxxDtoFilterList)){
                    for (BdcZfxxDTO bdcZfxxDTO : zfxxDtoFilterList){
                        xmid.add(bdcZfxxDTO.getXmid());
                    }
                }else {
                    for (BdcZfxxDTO bdcZfxxDTO : zfxxDTOList){
                        xmid.add(bdcZfxxDTO.getXmid());
                    }
                }

                if (!xmidXz.containsAll(xmid)){
                    Map<String,Object> returnMap = new HashMap<>();
                    xmid.removeAll(xmidXz);
                    List<String> zllist = new ArrayList<>();
                    for(String str : xmid){
                        BdcZfxxDTO zfxxDTO = zfxxDTOList.stream().filter(o -> str.equals(o.getXmid())).findFirst().orElse(null);
                        if (zfxxDTO != null){
                            zllist.add(zfxxDTO.getZl());
                        }
                    }
                    String zlStr = String.join("，",zllist);
                    if (zlStr.split("，").length>=3){
                        returnMap.put("msg","坐落：["+zlStr.substring(0,zlStr.indexOf("，",zlStr.indexOf("，")+1))+"等...]漏选，请勾选后重试");
                    }else {
                        returnMap.put("msg","坐落：["+zlStr+"]漏选，请勾选后重试");
                    }
                    returnMap.put("code",-1);
                    returnMap.put("detailMsg","坐落：["+zlStr+"]漏选，请勾选后重试");
                    returnMap.put("data",xmid);
                    return JSON.toJSONString(returnMap);
                }
            }

        }

        // 获取用户相关信息
        String bmmc = userManagerUtils.getYhBmmc();
        bmmc = StringUtils.isBlank(bmmc) ? mrbmmc : bmmc;
        bdcQlrQO.setBmmc(bmmc);
        bdcQlrQO.setQxdm(userManagerUtils.getCurrentUserRegionCode());

        // 判断是否有异地查询角色
        bdcFczmUtils.isYdcxjs(bdcQlrQO);
        // 区县查询权限
        bdcQlrQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("zhcx","", bdcQlrQO.getModuleCode()));

        // 保存至Redis
        String key = CommonConstantUtils.REDIS_INQUIRY_BZB_FCZM + UUIDGenerator.generate();
        redisUtils.addStringValue(key, JSON.toJSONString(bdcQlrQO), 120);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 编号
     * @description 获取有房无房证明编号
     */
    @Override
    public String getYfwfzmBh() {
        return this.getDybh(null);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人信息Redis键
     * @return {String} XML数据
     * @description 获取打印房产证明的XML数据
     */
    @Override
    public String getPrintXmlOfZfxx(String key) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：有房无房打印没有参数信息");
        }

        // 从缓存获取参数信息
        String data = redisUtils.getStringValue(key);
        BdcQlrQO bdcQlrQO = JSON.parseObject(data, BdcQlrQO.class);
        if(null == bdcQlrQO || StringUtils.isBlank(bdcQlrQO.getQlrmc())){
            throw new AppException("查询子系统：有房无房打印没有权利人参数信息");
        }

        if(null == bdcQlrQO.getCxdysj()) {
            bdcQlrQO.setCxdysj(false);
        }

        List<BdcZfxxDTO> zfxxDTOList;
        if (sfdyxzsj  && !bdcQlrQO.getCxdysj()){
            zfxxDTOList = bdcQlrQO.getZfxxDTOList();
            LOGGER.info("获取有房无房证明信息读取勾选数据，sfdyxzsj参数：{}, Cxdysj:{}，数据{}条，权利人数据：{}",
                    sfdyxzsj, bdcQlrQO.getCxdysj(), CollectionUtils.size(zfxxDTOList), bdcQlrQO.getQlrmc() + ":" + bdcQlrQO.getZjh());
        }else {
            zfxxDTOList = this.getZfxx(bdcQlrQO);
            LOGGER.info("获取有房无房证明信息根据权利人查询，sfdyxzsj参数：{}, Cxdysj:{}，数据{}条，权利人数据：{}",
                    sfdyxzsj, bdcQlrQO.getCxdysj(), CollectionUtils.size(zfxxDTOList), bdcQlrQO.getQlrmc() + ":" + bdcQlrQO.getZjh());
        }

        if (sfcxytmc) {
            this.resolveGhyt(zfxxDTOList);
        }

        // 合并统一房产信息，例如连云港要求家庭查询时候同一个房产的权利人合并展示且显示一条即可
        zfxxDTOList = bdcFczmUtils.hbxtfc(zfxxDTOList);

        // 查询限制状态
        bdcFczmUtils.setXzzt(zfxxDTOList);

        if (StringUtils.isNotBlank(bdydjxl)){
            zfxxDTOList = zfxxDTOList.stream().filter(o -> !StringToolUtils.existItemEquals(o.getDjxl(), bdydjxl.split(","))).collect(Collectors.toList());
        }

        LOGGER.info("获取有房无房证明过滤类型后数据{}条，权利人数据：{}", CollectionUtils.size(zfxxDTOList), bdcQlrQO.getQlrmc() + ":" + bdcQlrQO.getZjh());

        // 获取子表数据
        Multimap<String, List> childData = bdcFczmService.getChildData(this.listQlrxx(bdcQlrQO), zfxxDTOList, null, null);
        // 获取主表数据
        Map<String, String> parentData = this.getParentData(bdcQlrQO, zfxxDTOList);

        // 连云港需求，调整zfxx的排序
        if(sortZfxx){
            bdcFczmService.sortZfxx(bdcQlrQO,childData);
        }

        // 设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));

        // 自定义设置PDF打印有房无房证明房产信息、权利人子表ID
        String xml = Constants.ZFXX_FR3_ZD;
        if(StringUtils.isNotBlank(zfxxTable)) {
            xml = xml.replace("ZfxxList", zfxxTable);
        }
        if(StringUtils.isNotBlank(qlrxxTable)) {
            xml = xml.replace("ZfxxQlrxx", qlrxxTable);
        }
        bdcDysjDTO.setDyzd(xml);

        bdcDysjDTOList.add(bdcDysjDTO);

        // 获取打印XML数据
        return  bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyuc</a>
     * @param zfxxDTOList
     * @return void
     * @description  规划用途为其他时，取房地产权表的ytmc字段
     */
    private void resolveGhyt(List<BdcZfxxDTO> zfxxDTOList) {
        List<String> xmidList = zfxxDTOList.stream().map(BdcZfxxDTO::getXmid).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(xmidList)) {
            //查询xmid-ghyt集合
            List<Map<String,String>> mapList = new ArrayList<>();
            List<List<String>> xmidListPartition = Lists.partition(xmidList, 500);
            for (List<String> xmids : xmidListPartition) {
                List<Map<String,String>> xmidGhytMapList = bdcFczmMapper.listXmidGhytMap(xmids,bxsghytList);
                mapList.addAll(xmidGhytMapList);
            }

            if (CollectionUtils.isNotEmpty(mapList)) {
                //转换为xmid-ghyt map数据
                Map<String, String> resultMap = this.listToMap(mapList);
                for (BdcZfxxDTO bdcZfxxDTO : zfxxDTOList) {
                    if (StringUtils.isNotBlank(resultMap.get(bdcZfxxDTO.getXmid()))) {
                        bdcZfxxDTO.setGhyt(resultMap.get(bdcZfxxDTO.getXmid()));
                    }
                }
            }
        }
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @param  mapList
     * @return map
     * @description  数据格式转换
     */
    private Map<String, String> listToMap(List<Map<String,String>> mapList) {
        Map<String, String> resultMap = new HashMap<>();
        for (Map<String, String> map : mapList) {
            resultMap.put(map.get("XMID"), map.get("GHYT"));
        }
        return resultMap;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQlrQO 权利人参数
     * @return {List} 房产信息
     * @description  查询指定权利人的房产信息
     */
    private List<BdcZfxxDTO> getZfxx(BdcQlrQO bdcQlrQO) {
        // 查询房产信息
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(this.listQlrxx(bdcQlrQO));
        bdcZfxxQO.setCxly("1");
        bdcZfxxQO.setQxdmList(bdcQlrQO.getQxdmList());
        return bdcZfxxCxService.listBdcNtZfxxDTO(bdcZfxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQlrQO 权利人参数信息
     * @return {List} 权利人信息
     * @description 根据房产信息证明查询权利人参数信息
     */
    protected List<BdcQlrQO> listQlrxx(BdcQlrQO bdcQlrQO) {
        // 获取权利人参数信息
        List<BdcQlrQO> qlrQOList = new ArrayList<>(2);
        String[] qlrmc = bdcQlrQO.getQlrmc().split(",");

        // 证件号条件要么为空，不为空时候数量和权利人名称参数数量一致
        String[] zjh = null;
        if(StringUtils.isNotBlank(bdcQlrQO.getZjh())){
            zjh = bdcQlrQO.getZjh().split(",");
        }

        // 与申请人关系条件要么为空，不为空时候数量和权利人名称参数数量一致
        String[] ysqrgx = null;
        if(StringUtils.isNotBlank(bdcQlrQO.getYsqrgx())){
            ysqrgx = bdcQlrQO.getYsqrgx().split(",");
        }

        // 匹配权利人参数信息
        for(int i = 0; i < qlrmc.length; i++){
            BdcQlrQO qlrxx = new BdcQlrQO();
            qlrxx.setQlrmc(qlrmc[i]);

            if(null != zjh && zjh.length > 0){
                qlrxx.setZjh(zjh[i]);
            }

            if(null != ysqrgx && ysqrgx.length > 0 && ysqrgx.length == qlrmc.length){

                if ( "-1".equals(ysqrgx[i])) {
                    qlrxx.setYsqrgx("");
                } else{
                    qlrxx.setYsqrgx(ysqrgx[i]);
                }

            }

            qlrQOList.add(qlrxx);
        }
        qlrQOList = qlrQOList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc()+";"+o.getZjh()))),ArrayList::new));
        return qlrQOList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取主表数据
     * @param bdcQlrQO
     * @param zfxxDTOList
     */
    private Map<String, String> getParentData(BdcQlrQO bdcQlrQO, List<BdcZfxxDTO> zfxxDTOList) {
        Map<String,String> parentData = new HashMap<>(1);
        parentData.put("dysj", LocalDateTime.now().format(formatter));
        parentData.put("bh", this.getDybh(zfxxDTOList));
        parentData.put("jbr", bdcQlrQO.getJbr());
        parentData.put("cxfw", bdcQlrQO.getBmmc());
        parentData.put("cxfw2", this.getCxfw(bdcQlrQO));
        parentData.put("cxfw3", null != bdcQlrQO.getYdcxjs() && bdcQlrQO.getYdcxjs() ? qsjgmc : bdcQlrQO.getBmmc());
        parentData.put("cxfw4", getCxfwByBmmc(bdcQlrQO.getBmmc()));
        parentData.put("qlrmc", bdcQlrQO.getQlrmc());
        parentData.put("qlrzjh", bdcQlrQO.getZjh());
        parentData.put("qlrmc2", StringUtils.replace(bdcQlrQO.getQlrmc(), ZF_YW_DH, ZF_KG_CHAR));
        parentData.put("qlrzjh2", StringUtils.replace(bdcQlrQO.getZjh(), ZF_YW_DH, ZF_KG_CHAR));
        parentData.put("qlrmc3", StringUtils.substringBefore(bdcQlrQO.getQlrmc(),ZF_YW_DH));
        parentData.put("qlrzjh3", StringUtils.substringBefore(bdcQlrQO.getZjh(),ZF_YW_DH));
        return parentData;
    }

    /**
     * 处理查询范围
     * @param bdcQlrQO
     * @return
     */
    private String getCxfw(BdcQlrQO bdcQlrQO) {
        if(MapUtils.isEmpty(cxfwqhdz)) {
            return bdcQlrQO.getBmmc();
        }

        // 本查询范围仅限于连云港市区，不包含东海、灌云、灌南及赣榆区
        String cxfw = "", fcxfw = "";

        if(CollectionUtils.isEmpty(bdcQlrQO.getQxdmList())) {
            for(Map.Entry<String, String> cxfwqh : cxfwqhdz.entrySet()) {
                cxfw = cxfw + (StringUtils.isBlank(cxfw) ? "" : CommonConstantUtils.ZF_ZW_DH) + cxfwqh.getValue();
            }
        } else {
            Map<String, String> userQxdm = bdcQlrQO.getQxdmList().stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
            for(Map.Entry<String, String> cxfwqh : cxfwqhdz.entrySet()) {
                if(StringUtils.isBlank(userQxdm.get(cxfwqh.getKey()))) {
                    // 说明不是当前查询范围
                    fcxfw = fcxfw + (StringUtils.isBlank(fcxfw) ? "" : CommonConstantUtils.ZF_ZW_DH) + cxfwqh.getValue();
                } else {
                    // 是当前查询范围
                    cxfw = cxfw + (StringUtils.isBlank(cxfw) ? "" : CommonConstantUtils.ZF_ZW_DH) + cxfwqh.getValue();
                }
            }
        }
        return "本查询范围仅限于" + cxfw + ( StringUtils.isBlank(fcxfw) ? "" : "，不包含" + fcxfw);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zfxxDTOList 房产信息
     * @description 获取打印编号
     */
    private String getDybh(List<BdcZfxxDTO> zfxxDTOList) {
        if (YFWFZMBHGS_1.equals(bhgs)) {
            String lshbs, qzbs;
            if(CollectionUtils.isEmpty(zfxxDTOList)) {
                // 无房证明编号样式：无房+年份+区号+流水号
                lshbs = Constants.BDC_BZB_WF_FCZM_XLH;
                qzbs = "无房";
            } else {
                // 有房证明编号样式：有房+年份+区号+流水号
                lshbs = Constants.BDC_BZB_YF_FCZM_XLH;
                qzbs = "有房";
            }

            Integer sxh = bdcBhFeignService.queryLsh(lshbs, "YEAR");
            String lsh = StringToolUtils.appendZero(String.valueOf(sxh), 8);
            return qzbs + DateFormatUtils.format(new Date(), DateUtils.DATE_N) + "区号" + lsh;
        } else {
            // 例如2019070100012
            String time = LocalDateTime.now().format(formatter2);
            Integer sxh = bdcBhFeignService.queryLsh(Constants.BDC_BZB_FCZM_XLH, "DAY");
            return  time + StringToolUtils.appendZero(String.valueOf(sxh), 5);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明不动产单元号到redis
     */
    @Override
    public BdcQszmDyDTO saveBdcQszmParamToRedis(List<BdcQszmQO> list) {
        if(CollectionUtils.isEmpty(list)){
            throw new NullPointerException("查询子系统：打印权属证明参数为空！");
        }

        // 获取当前登录用户
        String userName = "";
        try{
            userName = userManagerUtils.getUserAlias();
        } catch (Exception e){
            LOGGER.error("综合查询标准版——打印权属证明：获取当前经办人信息出错{}", e.getMessage());
        }

        if(StringUtils.isNotBlank(qszmbhmb) && (!CommonConstantUtils.BOOL_FALSE.equals(list.get(0).getSfcxfdppxx()))) {
            // 盐城版本：权属证明集成房屋土地等类型，对于有匹配土地的房产证，不管选择土地证还是房产证打印，出来的权利信息都需要有房产证及土地证
            list = bdcFczmMapper.queryPpdyxx(list);
            if(CollectionUtils.isEmpty(list)) {
                throw new AppException("未获取到现势产权信息！");
            }
        }

        // 获取证明查询号
        String cxh = this.getQszmCxh();
        for(BdcQszmQO bdcQszmQO : list){
            bdcQszmQO.setCxh(cxh);
            bdcQszmQO.setUsername(userName);
        }

        // 缓存查询参数到Redis
        String key = CommonConstantUtils.REDIS_INQUIRY_BZB_QSZM + UUIDGenerator.generate();
        redisUtils.addStringValue(key, JSON.toJSONString(list), 120);

        BdcQszmDyDTO bdcQszmDyDTO = new BdcQszmDyDTO();
        bdcQszmDyDTO.setCxh(cxh);
        bdcQszmDyDTO.setRedisKey(key);
        if (CollectionUtils.isNotEmpty(editRoleList)) {
            List<RoleDto> roleDtoList = userManagerUtils.getRolesByUserName(userManagerUtils.getCurrentUserName());
            if (CollectionUtils.isNotEmpty(roleDtoList)) {
                LOGGER.warn("打印配置的可编辑权限角色为{}当前用户{}对应的角色信息{}", editRoleList, userName, JSON.toJSONString(roleDtoList));
                for (RoleDto roleDto : roleDtoList) {
                    if (editRoleList.contains(roleDto.getName())) {
                        //其中一个角色满足编辑权限即可
                        bdcQszmDyDTO.setCanEdit(true);
                        break;
                    }
                }
            }
        }
        //根据用户判断当前是否有编辑权限
        return bdcQszmDyDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @param bdclx 不动产类型
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    @Override
    public String getPrintXmlOfQszm(String key, String bdclx) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：打印权属证明没有参数信息");
        }

        String json = redisUtils.getStringValue(key);
        List<BdcQszmQO> qszmQOList = JSONArray.parseArray(json, BdcQszmQO.class);
        if(CollectionUtils.isEmpty(qszmQOList)){
            throw new NullPointerException("查询子系统：打印权属证明没有参数信息");
        }

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        for(BdcQszmQO bdcQszmQO : qszmQOList){
            Map<String, Object> mapTemp = new HashMap<>(1);
            mapTemp.put("bdcdyh", bdcQszmQO.getBdcdyh());
            mapTemp.put("qszt", bdcQszmQO.getQszt());
            mapTemp.put("gzlslid", bdcQszmQO.getGzlslid());
            mapTemp.put("bdcqzh", bdcQszmQO.getBdcqzh());
            mapTemp.put("cxh", bdcQszmQO.getCxh());
            mapTemp.put("xmid", bdcQszmQO.getXmid());
            mapTemp.put("bdclx", bdcQszmQO.getBdclx());
            mapTemp.put("jbr", bdcQszmQO.getUsername());

            bdcdyhListMap.add(mapTemp);
        }

        // 打印模板：
        // 土地 tdqszm.fr3  打印类型 tdqszm
        // 房屋 fwqszm.fr3  打印类型 fwqszm
        // 林权 lqqszm.fr3  打印类型 lqqszm
        String dylx = FWQSZM;
        switch (bdclx) {
            case "1": dylx = TDQSZM; break;
            case "2": dylx = FWQSZM; break;
            case "3": dylx = LQQSZM; break;
        }
        paramMap.put(dylx, bdcdyhListMap);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权属证明查询号
     */
    @Override
    public String getQszmCxh() {
        if(StringUtils.isNotBlank(qszmbhmb)) {
            // 盐城权属证明编号
            String cxbh = qszmbhmb.replace("nf", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            Integer sxh = bdcBhFeignService.queryLsh(Constants.BDC_YC_QSZM_XLH, "YEAR");
            String lsh = StringToolUtils.appendZero(String.valueOf(sxh), Integer.valueOf(qszmlshws));
            return cxbh.replace("lsh", lsh).replaceAll("-", "");
        } else {
            Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_BZB_QSZM_XLH, "DAY");
            return DateUtils.date2LocalDateTime(new Date()).format(formatter2) + StringToolUtils.appendZero(String.valueOf(lsh), 6);
        }
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZszmDTOList 打印查档信息请求参数
     * @return {BdcZszmDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存查档信息参数信息到redis
     */
    @Override
    public Object saveBdcCdxxParamToRedis(List<BdcZszmDTO> bdcZszmDTOList) {
        if(CollectionUtils.isEmpty(bdcZszmDTOList)){
            throw new NullPointerException("查询子系统：打印查档信息参数为空！");
        }

        // 获取当前登录用户
        String userName = "";
        try{
            userName = userManagerUtils.getCurrentUserName();
        } catch (Exception e){
            LOGGER.error("查档查询标准版——打印查档信息：获取当前经办人信息出错{}", e.getMessage());
        }

        Map<String, Object> param = new HashMap<>(8);
        List<Map> list = new ArrayList<>(bdcZszmDTOList.size());
        for(BdcZszmDTO bdcZszmDTO:bdcZszmDTOList){
            Map<String, String> map = new HashMap<>(2);
            String cxh = this.getCdxxCxCxh();
            map.put("cxh", cxh);
            map.put("gzlslid", bdcZszmDTO.getGzlslid());
            map.put("xmid", bdcZszmDTO.getXmid());
            map.put("userName", userName);
            list.add(map);
        }
        param.put("param", list);

        // 缓存查询参数到Redis
        String key = CommonConstantUtils.REDIS_INQUIRY_BZB_CDXXCX + UUIDGenerator.generate();
        redisUtils.addStringValue(key,JSON.toJSONString(param),120);
        param.put("redisKey", key);
        return param;
    }

    /**
     * 获取打印查档结果的XML数据
     *
     * @param key  redis key
     * @param dylx 打印类型
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public String getPrintXmlOfCdxxJg(String key, String dylx) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：打印查档信息没有参数信息");
        }

        String json = redisUtils.getStringValue(key);
        Map<String, Object> map = (Map<String, Object>) JSON.parse(json);
        if(MapUtils.isEmpty(map)){
            throw new NullPointerException("查询子系统：打印查档信息没有参数信息");
        }

        Map<String, List<Map>> paramMap = Maps.newHashMap();
        paramMap.put(dylx, (List<Map>) map.get("param"));
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qlrQOList 权利人信息
     * @return {List} 房产信息
     * @description 查询权利人房产证明并生成PDF
     */
    @Override
    public List<BdcFczmPdfDTO> getFczmPdf(List<BdcQlrQO> qlrQOList) {
        if(CollectionUtils.isEmpty(qlrQOList)) {
            throw new MissingArgumentException("缺失权利人名称、证件号参数信息");
        }

        List<BdcFczmPdfDTO> fczmPdfDTOList = new ArrayList<>();
        try {
            for(BdcQlrQO bdcQlrQO : qlrQOList) {
                if(null == bdcQlrQO || StringUtils.isAnyBlank(bdcQlrQO.getQlrmc(), bdcQlrQO.getZjh())) {
                    throw new MissingArgumentException("缺失权利人名称、证件号参数信息");
                }

                BdcFczmPdfDTO bdcFczmPdfDTO = new BdcFczmPdfDTO();
                bdcFczmPdfDTO.setZfxx(new ArrayList<>());
                bdcFczmPdfDTO.setQlrmc(bdcQlrQO.getQlrmc());
                bdcFczmPdfDTO.setQlrzjh(bdcQlrQO.getZjh());
                bdcFczmPdfDTO.setQksm("本次查询范围不包含登记信息中身份信息不全的房屋登记记录，不包含预售备案登记信息。");
                fczmPdfDTOList.add(bdcFczmPdfDTO);

                // 查询房产信息，生成打印XML数据
                bdcQlrQO.setCxdysj(true);
                String redisKey = saveBzbYfwfzmXx(bdcQlrQO);
                String xml = getPrintXmlOfZfxx(redisKey);

                // 解析XML数据
                parseFczmXml(bdcFczmPdfDTO, xml);

                // 生成PDF
                OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
                pdfExportDTO.setFileName("有房无房证明");
                pdfExportDTO.setXmlData(xml);

                // 读取打印数据源上传的模板
                BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
                bdcDysjPzDO.setDylx("yfwfzm");
                List<BdcDysjPzDO> dysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
                if(CollectionUtils.isNotEmpty(dysjPzDOList) && null != dysjPzDOList.get(0) && StringUtils.isNotBlank(dysjPzDOList.get(0).getPdfpath())) {
                    pdfExportDTO.setModelName(dysjPzDOList.get(0).getPdfpath());
                } else {
                    pdfExportDTO.setModelName(printPath + "yfwfzm.docx");
                }

                // 证明文件先保留，不删除
                String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
                bdcFczmPdfDTO.setPdf(Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(new File(pdfFilePath))));
            }

            return fczmPdfDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("生成房产证明PDF失败");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQszmQOList 权属证明参数
     * @return WwsqResponse 权属证明
     * @description 查询指定单元权属证明并生成PDF
     */
    @Override
    public List<BdcQszmPdfDTO> getQszmPdf(List<BdcQszmQO> bdcQszmQOList) {
        if(CollectionUtils.isEmpty(bdcQszmQOList)) {
            throw new MissingArgumentException("缺失单元号参数");
        }

        List<BdcQszmPdfDTO> qszmPdfDTOList = new ArrayList<>();
        try {
            for(BdcQszmQO bdcQszmQO : bdcQszmQOList) {
                if (null == bdcQszmQO || StringUtils.isAnyBlank(bdcQszmQO.getBdcdyh())) {
                    throw new MissingArgumentException("缺失单元号参数");
                }

                if (null == bdcQszmQO.getBdclx()) {
                    throw new MissingArgumentException("缺失bdclx参数");
                }

                BdcQszmPdfDTO bdcQszmPdfDTO = new BdcQszmPdfDTO();
                bdcQszmPdfDTO.setBdcdyh(bdcQszmQO.getBdcdyh());
                bdcQszmPdfDTO.setBdclx(bdcQszmQO.getBdclx());
                bdcQszmPdfDTO.setJbr(bdcQszmQO.getUsername());
                bdcQszmPdfDTO.setQksm("1".equals(String.valueOf(bdcQszmQO.getBdclx())) ? "" : "不包括已办理转移登记、预告登记、首次登记的土地份额。");
                qszmPdfDTOList.add(bdcQszmPdfDTO);
                // 盐城权属证明查询房地匹配信息需要xmid，默认不查房地匹配信息
                bdcQszmQO.setSfcxfdppxx(CommonConstantUtils.BOOL_FALSE);
                // 查询权属信息，生成打印XML数据
                BdcQszmDyDTO redisInfo = saveBdcQszmParamToRedis(Arrays.asList(bdcQszmQO));
                String xml = getPrintXmlOfQszm(redisInfo.getRedisKey(), String.valueOf(bdcQszmQO.getBdclx()));

                bdcQszmPdfDTO.setZmbh(redisInfo.getCxh());
                bdcQszmPdfDTO.setXml(xml);
                bdcQszmPdfDTO.setZmsj(LocalDateTime.now().format(formatter));

                // 生成PDF
                OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
                String dylx = "1".equals(String.valueOf(bdcQszmQO.getBdclx())) ? "tdqszm" : "fwqszm";

                // 读取打印数据源上传的模板
                BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
                bdcDysjPzDO.setDylx(dylx);
                List<BdcDysjPzDO> dysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
                if(CollectionUtils.isNotEmpty(dysjPzDOList) && null != dysjPzDOList.get(0) && StringUtils.isNotBlank(dysjPzDOList.get(0).getPdfpath())) {
                    pdfExportDTO.setModelName(dysjPzDOList.get(0).getPdfpath());
                } else {
                    pdfExportDTO.setModelName(printPath + dylx + ".docx");
                }

                pdfExportDTO.setFileName("权属证明");
                pdfExportDTO.setXmlData(xml);
                // 证明文件先保留，不删除
                String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

                bdcQszmPdfDTO.setPdf(Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(new File(pdfFilePath))));
            }

            return qszmPdfDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("生成房产证明PDF失败");
        }
    }

    /**
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @return String 查询范围
     * @return
     */
    @Override
    public String getCxfw() {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        List<String> filterQx = Container.getBean(BdcConfigUtils.class).qxdmFilter("zhcx","","301");
        bdcQlrQO.setQxdmList(filterQx);
        return getCxfw(bdcQlrQO);
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zxy</a>
     * @param  bdcdyxxDTOList 打印请求参数
     * @return {BdcdyxxDTO} 缓存的Redis KEY
     * @description 缓存权属证明参数信息到redis
     */
    @Override
    public String saveDjbdcdyhParamToRedis(List<BdcdyxxDTO> bdcdyxxDTOList) {
        if(CollectionUtils.isEmpty(bdcdyxxDTOList)){
            throw new NullPointerException("查询子系统：不动产单元查询参数为空！");
        }

        // 获取当前登录用户
        String userName = "";
        try{
            userName = userManagerUtils.getUserAlias();
        } catch (Exception e){
            LOGGER.error("不动产单元查询——-打印：获取当前经办人信息出错{}", e.getMessage());
        }

        for(BdcdyxxDTO bdcdyxxDTO : bdcdyxxDTOList){
            bdcdyxxDTO.setUserName(userName);
        }

        // 缓存查询参数到Redis
        String key = CommonConstantUtils.REDIS_INQUIRY_BDCDYCX + UUIDGenerator.generate();
        redisUtils.addStringValue(key, JSON.toJSONString(bdcdyxxDTOList), 120);

        return key;
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zxy</a>
     * @param key redis key
     * @param bdclx 不动产类型
     * @return {String} XML数据
     * @description 获取打印不动产单元查询的XML数据
     */
    @Override
    public String getPrintXmlOfBdcdycx(String key, String bdclx) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：不动产单元查询参数是空");
        }

        String json = redisUtils.getStringValue(key);
        List<BdcdyxxDTO> bdcdyxxDTOList = JSONArray.parseArray(json, BdcdyxxDTO.class);
        if(CollectionUtils.isEmpty(bdcdyxxDTOList)){
            throw new NullPointerException("查询子系统：不动产单元查询参数为空");
        }

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        for(BdcdyxxDTO bdcdyxxDTO : bdcdyxxDTOList){
            Map<String, Object> mapTemp = new HashMap<>(1);
            mapTemp.put("bdcdyh", bdcdyxxDTO.getBdcdyh());
            mapTemp.put("qszt", bdcdyxxDTO.getQszt());
            mapTemp.put("xmid", bdcdyxxDTO.getXmid());
            mapTemp.put("bdclx", bdcdyxxDTO.getBdclx());
            mapTemp.put("jbr", bdcdyxxDTO.getUserName());

            bdcdyhListMap.add(mapTemp);
        }

        // 打印模板：
        // 土地 tdbdcdycx.fr3  打印类型 tdbdcdycx
        // 房屋 fwbdcdycx.fr3  打印类型 fwbdcdycx
        String dylx = FWBDCDYCX;
        switch (bdclx) {
            case "1": dylx = TDBDCDYCX; break;
            case "2": dylx = FWBDCDYCX; break;
        }
        paramMap.put(dylx, bdcdyhListMap);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 解析房产信息XML数据
     * @param bdcFczmPdfDTO 房产证明接口返回信息
     * @param xml 房产信息XML
     */
    private void parseFczmXml(BdcFczmPdfDTO bdcFczmPdfDTO, String xml) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new ByteArrayInputStream(xml.getBytes()));
            Element rootElement = document.getRootElement();

            Element pageNode = rootElement.element("page");
            Element datasNode = null != pageNode ? pageNode.element("datas") : rootElement.element("datas");

            // 获取基本信息
            if(null != datasNode) {
                List<Element> dataNodes = datasNode.elements("data");
                if (CollectionUtils.isNotEmpty(dataNodes)) {
                    Map<String, String> data = new HashMap<>();
                    for (Element dataNode : dataNodes) {
                        String text = StringUtils.isBlank(dataNode.getText()) ? "—" : dataNode.getText();
                        data.put(dataNode.attributeValue("name"), text);
                    }

                    bdcFczmPdfDTO.setZmbh(MapUtils.getString(data, "bh"));
                    bdcFczmPdfDTO.setZmsj(MapUtils.getString(data, "dysj"));
                    bdcFczmPdfDTO.setJbr(MapUtils.getString(data, "jbr"));
                }
            }

            // 获取房产信息
            String zfxxTableId = StringUtils.isBlank(zfxxTable) ? "ZfxxList" : zfxxTable;
            List<Element> detailNodes = null != pageNode ? pageNode.elements("detail") : rootElement.elements("detail");
            Element zfxxNode = null;
            for(Element detailNode : detailNodes) {
                if(StringUtils.equals(zfxxTableId, detailNode.attribute("ID").getValue())) {
                    zfxxNode = detailNode;
                    break;
                }
            }
            if(null == zfxxNode) {
                return;
            }

            List<Element> rowElements = zfxxNode.elements("row");
            if (CollectionUtils.isEmpty(rowElements)) {
                return;
            }

            for (Element rowElement : rowElements) {
                List<Element> dataNodes = rowElement.elements("data");
                if (CollectionUtils.isEmpty(dataNodes)) {
                    continue;
                }

                Map<String, String> data = new HashMap<>();
                for (Element dataNode : dataNodes) {
                    String text = StringUtils.isBlank(dataNode.getText()) ? "—" : dataNode.getText();
                    data.put(dataNode.attributeValue("name"), text);
                }

                BdcZfxxDTO zfxxDTO = new BdcZfxxDTO();
                zfxxDTO.setQlrmc(MapUtils.getString(data, "qlrmc"));
                zfxxDTO.setQlrzjh(MapUtils.getString(data, "qlrzjh"));
                zfxxDTO.setBdcqzh(MapUtils.getString(data, "bdcqzh"));
                zfxxDTO.setZl(MapUtils.getString(data, "zl"));
                zfxxDTO.setGhyt(MapUtils.getString(data, "ghyt"));
                zfxxDTO.setFj(MapUtils.getString(data, "fj"));
                zfxxDTO.setGyqk(MapUtils.getString(data, "gyqk"));
                String jzmj = MapUtils.getString(data, "jzmj");
                zfxxDTO.setJzmj((StringUtils.isBlank(jzmj) || !NumberUtils.isNumber(jzmj)) ? null : Double.valueOf(jzmj));

                bdcFczmPdfDTO.getZfxx().add(zfxxDTO);
            }
        } catch (Exception e) {
            // 这里不抛异常，即使解析失败也保证PDF的生成返回
            LOGGER.error("生成房产证明PDF失败：", e);
        }
    }

    /**
     * 获取查档信息查询号
     */
    private String getCdxxCxCxh() {
        Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_BZB_CDXXCX_XLH, "DAY");
        return DateUtils.date2LocalDateTime(new Date()).format(formatter2) + StringToolUtils.appendZero(String.valueOf(lsh), 6);
    }

    /**
     * @param bmmc 部门名称
     * @description 通过部门名称获取有房无房查询范围对照
     */
    private String getCxfwByBmmc(String bmmc) {
        if (StringUtils.isNotBlank(bmmc)) {
            bdcZdFeignService.refreshBdcZd("yfwfcxfw");
            List<Map> cxfwZdMap = bdcZdFeignService.queryBdcZd("yfwfcxfw");
            if (CollectionUtils.isNotEmpty(cxfwZdMap)) {
                for (Map cxfw : cxfwZdMap) {
                    if (bmmc.equals(cxfw.get("DM")) && cxfw.get("MC") != null) {
                        LOGGER.info("部门名称为:{},对应的有房无房查询范围:{}",bmmc,cxfw.get("MC"));
                        return cxfw.get("MC").toString();
                    }
                }
            }
            LOGGER.info("未找到部门名称为:{}的对应有房无房查询范围",bmmc);
            return "";
        }
        LOGGER.info("通过部门名称获取有房无房查询范围对照失败,部门名称为空");
        return "";
    }
}
