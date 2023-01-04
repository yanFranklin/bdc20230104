package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdyZtDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcXtLscsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.core.mapper.BdcFczmMapper;
import cn.gtmap.realestate.inquiry.service.BdcNtFczmService;
import cn.gtmap.realestate.inquiry.service.BdcZfxxCxService;
import cn.gtmap.realestate.inquiry.service.BdcZszmCxService;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/02
 * @description 南通 房产证明处理逻辑类
 */
@Service
public class BdcNtFczmServiceImpl implements BdcNtFczmService {
    /** 日志处理 */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcNtFczmServiceImpl.class);

    /** 日期格式化 */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.CHINA);
    private static final DateTimeFormatter DATE_FORMATTER2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");

    @Autowired
    private UserManagerUtils userManagerUtils;

    /** 房产证明Mapper */
    @Autowired
    private BdcFczmMapper bdcFczmMapper;

    /** 编号服务 */
    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    /** Redis操作 */
    @Autowired
    private RedisUtils redisUtils;

    /** 住房接口 */
    @Autowired
    private BdcZfxxCxService bdcZfxxCxService;

    /** 打印服务 */
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    /** 临时参数服务 */
    @Autowired
    private BdcXtLscsFeignService lscsFeignService;

    @Autowired
    private BdcZszmCxService bdcZszmCxService;

    @Autowired
    private PdfController pdfController;

    /** 有房无房证明列表排序按照证号排序 */
    @Value("${zhcx.yfwfzm.bdcqzhpx:false}")
    private Boolean yfwfzmpx;

    /** 打印文件路径 */
    @Value("${print.path:}")
    private String printPath;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTO 房产证明信息
     * @return {String} redis key
     * @description 保存（南通）房产证明信息至缓存
     */
    @Override
    public String saveNtFcdaXx(BdcFczmDTO bdcFczmDTO) {
        if(null == bdcFczmDTO || StringUtils.isBlank(bdcFczmDTO.getBh())){
            throw  new NullPointerException("查询子系统：查询房产证明参数为空");
        }

        String key ;
        if(CommonConstantUtils.QHKZM.equals(bdcFczmDTO.getType())) {
            // 迁户口证明
            key = CommonConstantUtils.QHKZM + bdcFczmDTO.getBh();
        } else {
            // 有房无房证明
            key = bdcFczmDTO.getBh();
        }

        redisUtils.addStringValue(key, JSON.toJSONString(bdcFczmDTO), 120);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTOList 房产证明信息
     * @return {String} redis key
     * @description 批量保存（南通）房产证明信息至缓存
     */
    @Override
    public String saveNtFcdaXxPl(List<BdcFczmDTO> bdcFczmDTOList) {
        if(CollectionUtils.isEmpty(bdcFczmDTOList)){
            throw  new NullPointerException("查询子系统：查询房产证明参数为空");
        }

        String key = CommonConstantUtils.REDIS_INQUIRY_NT_QTZM + UUIDGenerator.generate16();
        redisUtils.addStringValue(key, JSON.toJSONString(bdcFczmDTOList), 120);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明打印信息（用户编辑）
     */
    @Override
    public String getNtFczmXml(String key) {
        if(StringUtils.isBlank(key)){
            throw  new NullPointerException("查询子系统：房产证明打印参数为空");
        }

        String data = redisUtils.getStringValue(key);
        return this.getNtFczmXml(JSON.parseObject(data, BdcFczmDTO.class));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明批量打印信息
     */
    @Override
    public String getNtFczmPlXml(String key) {
        if(StringUtils.isBlank(key)){
            throw  new NullPointerException("查询子系统：其他证明批量打印参数为空");
        }

        String data = redisUtils.getStringValue(key);
        if(StringUtils.isBlank(data)){
            throw  new NullPointerException("查询子系统：未获取到其他证明批量打印数据内容！");
        }
        List<BdcFczmDTO> bdcFczmDTOList = JSON.parseArray(data, BdcFczmDTO.class);
        if(CollectionUtils.isEmpty(bdcFczmDTOList)){
            throw  new NullPointerException("查询子系统：未获取到其他证明批量打印数据内容！");
        }

        return this.getNtFczmPlXml(bdcFczmDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）有房无房证明打印信息
     */
    @Override
    public String getNtYfwfzmXml(String key) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：有房无房打印没有参数信息");
        }

        // 从缓存获取参数信息
        String data = redisUtils.getStringValue(key);
        BdcFczmDTO bdcFczmDTO = JSON.parseObject(data, BdcFczmDTO.class);
        if(null == bdcFczmDTO || StringUtils.isBlank(bdcFczmDTO.getCxsqr())){
            throw new AppException("查询子系统：有房无房打印没有权利人参数信息");
        }

        // 查询房产信息
        List<BdcZfxxDTO> zfxxDTOList = this.getZfxx(bdcFczmDTO);

        if(CollectionUtils.isEmpty(zfxxDTOList)){
            // 无房证明
            bdcFczmDTO.setZmnr(bdcFczmDTO.getCxsqr() + "名下无不动产登记记录。");
            return this.getNtFczmXml(bdcFczmDTO);
        } else {
            // 有房证明
            return this.getYfzmXml(bdcFczmDTO, zfxxDTOList);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTO 权利人参数
     * @return {List} 房产信息
     * @description  查询指定权利人的房产信息
     */
    private List<BdcZfxxDTO> getZfxx(BdcFczmDTO bdcFczmDTO) {
        // 获取权利人参数信息
        List<BdcQlrQO> qlrQOList = new ArrayList<>(2);
        String[] sfzh = bdcFczmDTO.getSfzh().split(",");
        String[] cxsqr = bdcFczmDTO.getCxsqr().split(",");
        for(int i = 0; i < sfzh.length; i++){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrmc(cxsqr[i]);
            bdcQlrQO.setZjh(sfzh[i]);
            qlrQOList.add(bdcQlrQO);
        }

        // 查询房产信息
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrQOList);
        bdcZfxxQO.setCxly("1");
        return bdcZfxxCxService.listBdcNtZfxxDTO(bdcZfxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTO 权利人参数
     * @param zfxxDTOList 房产信息
     * @return {String} 证明XML
     * @description  获取有房证明打印XML
     */
    private String getYfzmXml(BdcFczmDTO bdcFczmDTO, List<BdcZfxxDTO> zfxxDTOList) {
        // 获取子表数据
        Multimap<String, List> childData = ArrayListMultimap.create();
        List<Map<String,String>> zfxxList = new ArrayList<>();

        // 按照物分组（对于分别持证的合并展示证号）
        Map<String, List<BdcZfxxDTO>> zfxxGroup = zfxxDTOList.stream().collect(Collectors.groupingBy(BdcZfxxDTO::getXmid));
        for(Map.Entry<String, List<BdcZfxxDTO>> entry : zfxxGroup.entrySet()){
            List<BdcZfxxDTO> zfxxDTOS = entry.getValue();
            if(CollectionUtils.isNotEmpty(zfxxDTOS) && null != zfxxDTOS.get(0)){
                String bdcqzh = zfxxDTOS.stream().map(BdcZfxxDTO::getBdcqzh).distinct().collect(Collectors.joining(CommonConstantUtils.ZF_YW_DH));

                Map map = new HashMap(4);
                map.put("BDCQZH", bdcqzh);
                map.put("ZL", zfxxDTOS.get(0).getZl());
                map.put("GHYT", zfxxDTOS.get(0).getGhyt());
                map.put("QLRMC", StringUtils.join(
                        zfxxDTOS.stream().map(t -> t.getQlrmc()).collect(Collectors.toList()), CommonConstantUtils.ZF_YW_DH));
                map.put("QLRZJH", StringUtils.join(
                        zfxxDTOS.stream().map(t -> t.getQlrzjh()).collect(Collectors.toList()), CommonConstantUtils.ZF_YW_DH));

                // 设置限制状态（按物分组，取第一个单元即可）
                this.setBdcdyXzzt(map, zfxxDTOS.get(0).getBdcdyh());
                zfxxList.add(map);
            }
        }

        // 房产信息按照坐落排序
        int index = 1;
        if(CollectionUtils.isNotEmpty(zfxxList)) {
            if(zfxxList.size() > 1) {
                if(yfwfzmpx) {
                    // 按照不动产权证号排序
                    Collections.sort(zfxxList, (zf1, zf2) -> {
                        if (null == zf1 || null == zf2 || StringUtils.isBlank(zf1.get("BDCQZH")) || StringUtils.isBlank(zf2.get("BDCQZH"))) {
                            return 1;
                        }
                        Collator instance = Collator.getInstance(Locale.CHINA);
                        return instance.compare(zf1.get("BDCQZH"), zf2.get("BDCQZH"));
                    });
                } else {
                    // 默认按照座落排序
                    Collections.sort(zfxxList, (zf1, zf2) -> {
                        if (null == zf1 || null == zf2 || StringUtils.isBlank(zf1.get("ZL")) || StringUtils.isBlank(zf2.get("ZL"))) {
                            return 1;
                        }
                        Collator instance = Collator.getInstance(Locale.CHINA);
                        return instance.compare(zf1.get("ZL"), zf2.get("ZL"));
                    });
                }
            }

            // 添加序号
            for(Map<String, String> zfxx : zfxxList) {
                zfxx.put("XH", String.valueOf(index++));
            }
        }

        childData.put("zfxx",zfxxList);

        // 获取主表数据
        Map<String,String> parentData = new HashMap<>(1);
        parentData.put("BH", bdcFczmDTO.getBh());
        parentData.put("CXSQR", bdcFczmDTO.getCxsqr());
        parentData.put("SFZH", bdcFczmDTO.getSfzh());
        parentData.put("CXSD", bdcFczmDTO.getCxsd());//有房无房、有房无房（企业获取查询时点）；
        parentData.put("ZMSJ", bdcFczmDTO.getZmsj());
        parentData.put("JBR", bdcFczmDTO.getJbr());

        // 设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(Constants.NT_YFZM_XML);
        bdcDysjDTOList.add(bdcDysjDTO);

        // 获取打印XML数据
        return  bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} FR3打印的XML数据
     * @description 根据指定内容生成（南通）房产证明打印信息
     */
    private String getNtFczmXml(BdcFczmDTO bdcFczmDTO){
        // 设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = this.getFczmDysj(bdcFczmDTO);
        bdcDysjDTOList.add(bdcDysjDTO);

        // 获取打印XML数据
        return  bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} FR3打印的XML数据
     * @description 生成单个房产证明打印信息
     */
    private BdcDysjDTO getFczmDysj(BdcFczmDTO bdcFczmDTO) {
        // 生成主表内容
        Map<String, String> parentData = new HashMap<>(1);
        parentData.put("BH", bdcFczmDTO.getBh());
        parentData.put("CXSQR", bdcFczmDTO.getCxsqr());
        parentData.put("SFZH", bdcFczmDTO.getSfzh());
        parentData.put("CXSD", bdcFczmDTO.getCxsd());
        parentData.put("ZMNR", bdcFczmDTO.getZmnr());
        parentData.put("ZMSJ", bdcFczmDTO.getZmsj());
        parentData.put("JBR", bdcFczmDTO.getJbr());

        // 生成子表数据、只含有姓名证件号信息，其余为无
        Multimap<String, List> childData = ArrayListMultimap.create();
        List<Map<String,String>> zfxxList = new ArrayList<>();
        Map map = new HashMap(8);
        map.put("QLRMC", bdcFczmDTO.getCxsqr());
        map.put("QLRZJH", bdcFczmDTO.getSfzh());
        map.put("BDCQZH", "无");
        map.put("ZL", "无");
        map.put("GHYT", "无");
        map.put("XH", "1");
        zfxxList.add(map);
        childData.put("zfxx",zfxxList);

        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(Constants.NT_ZFXX_FR3_ZD);
        return bdcDysjDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} FR3打印的XML数据
     * @description 根据指定内容生成（南通）房产证明打印信息
     */
    private String getNtFczmPlXml(List<BdcFczmDTO> bdcFczmDTOList){
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(bdcFczmDTOList.size());

        for(BdcFczmDTO bdcFczmDTO : bdcFczmDTOList){
            BdcDysjDTO bdcDysjDTO = this.getFczmDysj(bdcFczmDTO);
            bdcDysjDTOList.add(bdcDysjDTO);
        }

        // 获取打印XML数据
        return  bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDyzmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description （南通）将要打印的抵押证明参数信息保存至Redis中
     */
    @Override
    public BdcDyzmDyDTO saveDyzmCsxxToRedis(List<BdcDyzmDTO> bdcDyzmDTOList) {
        if(CollectionUtils.isEmpty(bdcDyzmDTOList)){
            throw new NullPointerException("查询子系统：出具抵押证明未选中要导出数据");
        }

        // 获取抵押证明查询号
        String cxh = this.getCxh();

        // 保存参数信息
        for(BdcDyzmDTO bdcDyzmDTO : bdcDyzmDTOList){
            bdcDyzmDTO.setCxh(cxh);
        }
        String json = JSON.toJSONString(bdcDyzmDTOList);
        String redisKey = redisUtils.addStringValue(CommonConstantUtils.REDIS_INQUIRY_DYZM + UUIDGenerator.generate(), json, 120);

        BdcDyzmDyDTO bdcDyzmDyDTO = new BdcDyzmDyDTO();
        bdcDyzmDyDTO.setCxh(cxh);
        bdcDyzmDyDTO.setRedisKey(redisKey);
        return bdcDyzmDyDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} 打印的数据内容
     * @description （南通）获取抵押证明打印对应的XML数据
     */
    @Override
    public String getPrintXmlOfDyzm(String key) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：出具抵押证明未获取到要导出的数据");
        }

        String data = redisUtils.getStringValue(key);
        if(StringUtils.isBlank(data)){
            throw new AppException("查询子系统：抵押证明未获取到要导出的数据");
        }
        List<BdcDyzmDTO> bdcDyzmDTOList = JSON.parseArray(data, BdcDyzmDTO.class);

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(bdcDyzmDTOList.size());
        for(BdcDyzmDTO bdcDyzmDTO: bdcDyzmDTOList){
            Map<String, Object> map = new HashMap<>(1);
            map.put("bdcdyh", bdcDyzmDTO.getBdcdyh());
            map.put("xmid", bdcDyzmDTO.getXmid());
            map.put("cxh", bdcDyzmDTO.getCxh());
            map.put("jbr", bdcDyzmDTO.getUserName());
            bdcdyhListMap.add(map);
        }
        /// 打印模板：bdcDyzm.fr3
        paramMap.put("bdcDyzm", bdcdyhListMap);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {String} 房产出售时间
     * @description 获取房产出售时间
     */
    @Override
    public String getFdcqCssj(String xmid) {
        if(StringUtils.isBlank(xmid)){
            return "";
        }

        List<BdcXmDO> bdcXmDOList = bdcFczmMapper.getBdcXmNextXmDO(xmid);
        if(CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0) || null == bdcXmDOList.get(0).getDjsj()){
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(bdcXmDOList.get(0).getDjsj());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明不动产单元号到redis
     */
    @Override
    public BdcQszmDyDTO saveNtFcdaBdcdyhToRedis(List<BdcQszmQO> list) {
        if(CollectionUtils.isEmpty(list)){
            throw new NullPointerException("查询子系统：打印权属证明参数为空！");
        }

        // 获取抵押证明查询号
        String cxh = this.getQszmCxh();
        for(BdcQszmQO bdcQszmQO : list){
            bdcQszmQO.setCxh(cxh);
        }

        // 缓存查询参数到Redis
        String key = CommonConstantUtils.REDIS_INQUIRY_FCDA + UUIDGenerator.generate();
        redisUtils.addStringValue(key,JSON.toJSONString(list),120);

        BdcQszmDyDTO bdcQszmDyDTO = new BdcQszmDyDTO();
        bdcQszmDyDTO.setCxh(cxh);
        bdcQszmDyDTO.setRedisKey(key);
        return bdcQszmDyDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    @Override
    public String getPrintXmlOfQszm(String key) {
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
            mapTemp.put("jbr", bdcQszmQO.getUsername());
            mapTemp.put("time", bdcQszmQO.getCyrq());
            bdcdyhListMap.add(mapTemp);
        }
        // 打印模板：bdcZfxxDa.fr3
        paramMap.put("bdcZfxxDa", bdcdyhListMap);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param bdcPrintParamDTO 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 缓存打印利害关系人、律师查询参数到redis
     */
    @Override
    public BdcPrintParamQO saveLhgxrLscxPrintParam(BdcPrintParamDTO bdcPrintParamDTO) {
        if (null == bdcPrintParamDTO || CollectionUtils.isEmpty(bdcPrintParamDTO.getXmids()) || StringUtils.isBlank(bdcPrintParamDTO.getDylx())) {
            throw new NullPointerException("查询子系统：打印利害关系人、律师查询参数为空！");
        }

        // 受理编号
        String dylx = "lhgxrcx".equals(bdcPrintParamDTO.getDylx()) ? Constants.BDC_NT_LHGXR_XLH : Constants.BDC_NT_LSCX_XLH;
        Integer lsh = bdcBhFeignService.queryLsh(dylx, "DAY");
        String cxbh = DateUtils.date2LocalDateTime(new Date()).format(DATE_FORMATTER) + StringToolUtils.appendZero(String.valueOf(lsh), 6);
        bdcPrintParamDTO.setCxbh(cxbh);

        // 二维码
        String ewmurl = bdcPrintParamDTO.getRegisterUi() + "/rest/v1.0/print/ewm/" + cxbh;
        bdcPrintParamDTO.setEwmurl(ewmurl);

        // 缓存xmid到临时参数表，方便后续打印数据源SQL查询
        List<BdcXtLscsDO> bdcXtLscsDOList = new ArrayList<>();
        String xmidKey = UUIDGenerator.generate16();
        for(String xmid : bdcPrintParamDTO.getXmids()) {
            BdcXtLscsDO bdcXtLscsDO = new BdcXtLscsDO();
            bdcXtLscsDO.setCsmc(xmidKey);
            bdcXtLscsDO.setCsz(xmid);
            bdcXtLscsDO.setSfsc(1);
            bdcXtLscsDOList.add(bdcXtLscsDO);
        }
        lscsFeignService.addValues(bdcXtLscsDOList);
        bdcPrintParamDTO.setXmidkey(xmidKey);

        // 不需要再用xmid集合，置空
        bdcPrintParamDTO.setXmids(null);

        String redisKey = Constants.REDIS_INQUIRY_LHGXRLSCS + UUIDGenerator.generate16();
        redisUtils.addStringValue(redisKey, JSON.toJSONString(bdcPrintParamDTO), 600);

        BdcPrintParamQO bdcPrintParamQO = new BdcPrintParamQO();
        bdcPrintParamQO.setRedisKey(redisKey);
        bdcPrintParamQO.setCxbh(cxbh);
        return bdcPrintParamQO;
    }

    /**
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （南通）获取打印利害关系人、律师查询的XML数据
     */
    @Override
    public String getPrintXmlOfLhgxrLscx(String key) {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("查询子系统：打印利害关系人、律师查询证明没有参数信息");
        }

        String json = redisUtils.getStringValue(key);
        BdcPrintParamDTO bdcPrintParamDTO = JSONArray.parseObject(json, BdcPrintParamDTO.class);
        if(null == bdcPrintParamDTO || StringUtils.isBlank(bdcPrintParamDTO.getXmidkey())){
            throw new NullPointerException("查询子系统：打印利害关系人、律师查询证明没有参数信息");
        }

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        Map<String, Object> mapTemp = new HashMap<>(1);
        mapTemp.put("slbh", bdcPrintParamDTO.getCxbh());
        mapTemp.put("ewm", bdcPrintParamDTO.getEwmurl());
        mapTemp.put("lssws", StringToolUtils.valueOf(bdcPrintParamDTO.getLssws(), "/"));
        mapTemp.put("lsmc", StringToolUtils.valueOf(bdcPrintParamDTO.getLsmc(), "/"));
        mapTemp.put("lhgxr", StringToolUtils.valueOf(bdcPrintParamDTO.getLhgxr(), "/"));
        mapTemp.put("lhgxrzjh", StringToolUtils.valueOf(bdcPrintParamDTO.getLhgxrzjh(), "/"));
        mapTemp.put("dqrq1", DateFormatUtils.format(new Date(), "yyyy年MM月dd日"));
        mapTemp.put("dqrq2", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
        mapTemp.put("dylx", bdcPrintParamDTO.getDylx());
        mapTemp.put("jbr", bdcPrintParamDTO.getJbr());
        mapTemp.put("xmidkey", bdcPrintParamDTO.getXmidkey());
        bdcdyhListMap.add(mapTemp);

        // 打印数据源
        paramMap.put(bdcPrintParamDTO.getDylx(), bdcdyhListMap);
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 南通获取权属证明及对应PDF文件信息（为了支持后续需求变动，例如字段新增直接返回Map集合，对照打印数据源）
     * @param bdcQszmQO 权利人证号、不动产权证号参数
     * @return {List} 权属证明信息
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryQszm(BdcQszmQO bdcQszmQO) throws Exception {
        if(null == bdcQszmQO || StringUtils.isAnyBlank(bdcQszmQO.getQlrzjh(), bdcQszmQO.getBdcqzh())) {
            return Collections.emptyList();
        }

        // 先调用综合查询查询出房产单元信息
        Page<BdcZszmDTO> zszmInfo = this.queryFdcqXmxx(bdcQszmQO);
        if (null == zszmInfo || CollectionUtils.isEmpty(zszmInfo.getContent())) {
            return Collections.emptyList();
        }

        // 获取查询证明号、经办人
        String cxh = this.getQszmCxh(), jbr = null;
        try {
             jbr = userManagerUtils.getCurrentUserName();
        } catch (Exception e) {
            LOGGER.error("权属证明接口获取经办人异常！");
        }

        List<Map<String, Object>> qszmxxList = new ArrayList<>();
        for(BdcZszmDTO zszmDTO : zszmInfo.getContent()) {
            // 生成权属证明打印XML
            String qszmXml = this.generateQszmPrintXml(cxh, jbr, zszmDTO);
            if(StringUtils.isBlank(qszmXml)) {
                continue;
            }

            // 解析XML内容封装对应返回实体数据
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new ByteArrayInputStream(qszmXml.getBytes()));
            Element rootElement = document.getRootElement();
            Map<String, Object> qszmxx = this.parseQszmXmlToData(rootElement);

            // 生成证明PDF文件将Base64内容返回
            qszmxx.put("qszmpdf", this.generatePdf(qszmXml));

            qszmxxList.add(qszmxx);
        }

        return qszmxxList;
    }

    /**
     * 根据权利人证号、产证号查询出房地产权项目信息（调用综合查询功能）
     * @param bdcQszmQO 权利人证号、不动产权证号参数
     * @return {BdcZszmDTO} 产权信息
     */
    private Page<BdcZszmDTO> queryFdcqXmxx(BdcQszmQO bdcQszmQO) {
        Pageable pageable = new PageRequest(0, 10^5);
        BdcZszmQO zszmQO = new BdcZszmQO();
        zszmQO.setBdcqzh(bdcQszmQO.getBdcqzh());
        zszmQO.setQlrzjh(new String[]{bdcQszmQO.getQlrzjh()});
        return bdcZszmCxService.listBdcZszm(pageable, zszmQO);
    }

    /**
     * 调用打印数据源生成权属证明打印XML数据
     * @param cxh 查询号
     * @param jbr 经办人
     * @param fdcqdyxx 单元信息
     * @return {String} 权属证明打印XML
     */
    private String generateQszmPrintXml(String cxh, String jbr, BdcZszmDTO fdcqdyxx) {
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        Map<String, Object> mapTemp = new HashMap<>(1);
        mapTemp.put("bdcdyh", fdcqdyxx.getBdcdyh());
        mapTemp.put("qszt", fdcqdyxx.getQszt());
        mapTemp.put("gzlslid", fdcqdyxx.getGzlslid());
        mapTemp.put("bdcqzh", fdcqdyxx.getBdcqzh());
        mapTemp.put("cxh", cxh);
        mapTemp.put("jbr", jbr);

        bdcdyhListMap.add(mapTemp);
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        paramMap.put("bdcZfxxDa", bdcdyhListMap);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 解析XML内容，封装对应信息，返回实体数据
     * @param pageElement 打印XML根节点
     * @return {Map} 封装后的权属证明信息
     */
    private Map<String, Object> parseQszmXmlToData(Element pageElement) {
        Map<String, Object> result = new HashMap<>();

        List<Element> elements = pageElement.elements();
        for(Element element : elements) {
            if(StringUtils.equals("datas", element.getName())) {
                // 单元基本信息
                result.put("bdcJbxx", this.parseDataElements(element));
            }
            else if(StringUtils.equals("detail", element.getName())) {
                // 详细子表对应内容解析
                String id = element.attributeValue("ID");
                result.put(id.toLowerCase(), this.parseDetailElement(element));
            }
        }

        return result;
    }

    /**
     * 解析 Detail子表内容
     * @param element 子表根节点
     * @return {List} 子表数据（可能有多行，所以返回List）
     */
    private List<Map<String, String>> parseDetailElement(Element element) {
        List<Map<String, String>> list = new ArrayList<>();

        List<Element> rowElements = element.elements("row");
        if(CollectionUtils.isNotEmpty(rowElements)) {

            for(Element rowElement : rowElements) {
               list.add(parseDataElements(rowElement));
            }
        }
        return list;
    }

    /**
     * 解析具体的Data节点
     * @param element Data节点父节点
     * @return {Map} Data节点数据
     */
    private Map<String, String> parseDataElements(Element element) {
        List<Element> dataElements = element.elements("data");
        Map<String, String> data = new HashMap<>();
        if(CollectionUtils.isNotEmpty(dataElements)) {
            for(Element dataElement : dataElements) {
                String text = StringUtils.isBlank(dataElement.getText()) ? "/" : dataElement.getText();
                data.put(dataElement.attributeValue("name"), text);
            }
        }
        return data;
    }

    /**
     * 权属证明XML生成PDF
     * @param xml 打印XML内容
     * @return {String} PDF对应Base64字符串
     */
    private String generatePdf(String xml) {
        LOGGER.info("权属证明接口生成PDF对应XML内容：{}", xml);

        // 生成PDF文件
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "ntfwqszm.docx");
        pdfExportDTO.setFileName("权属证明");
        pdfExportDTO.setXmlData(xml);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        LOGGER.info("权属证明接口生成pdf文件路径：{}", pdfFilePath);

        // 转换Base64数据
        try {
            File pdfFile = new File(pdfFilePath);
            return Base64Utils.getPDFBinary(pdfFile);
        } catch (Exception e) {
            LOGGER.error("权属证明接口生成PDF异常，异常信息：");
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取抵押证明查询号
     */
    private String getCxh(){
        Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_NT_DYZM_XLH, "YEAR");

        StringBuilder builder = new StringBuilder("苏(");
        builder.append(LocalDate.now().getYear());
        builder.append(")南通市不动产证明查");
        builder.append(StringToolUtils.appendZero(String.valueOf(lsh), 6));
        builder.append("号");
        return builder.toString();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权属证明查询号
     */
    private String getQszmCxh() {
        Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_NT_QSZM_XLH, "DAY");
        return DateUtils.date2LocalDateTime(new Date()).format(DATE_FORMATTER) + StringToolUtils.appendZero(String.valueOf(lsh), 6);
    }

    @Override
    public String generateYfwfzmXml(BdcFczmDTO bdcFczmDTO) {
        // 获取证书证明流水号
        bdcFczmDTO = this.getFczmParam(bdcFczmDTO, "BDC_NT_ZSZM_BH_XLH","有房无房证明");

        // 将打印所需参数缓存至redis中
        String redisKey = this.saveNtFcdaXx(bdcFczmDTO);

        // 获取打印的xm数据
        String xml = this.getNtYfwfzmXml(redisKey);
        return xml;
    }

    /**
     *  获取证明编号信息
     */
    private BdcFczmDTO getFczmParam(BdcFczmDTO bdcFczmDTO, String type, String typeInfo) {
        // 编号序列号
        Integer bhxlh = bdcBhFeignService.queryLsh(type, CommonConstantUtils.ZZSJFW_DAY);

        bdcFczmDTO.setBh(DATE_FORMATTER.format(LocalDateTime.now()) + StringToolUtils.appendZero(String.valueOf(bhxlh), 6));
        bdcFczmDTO.setCxsd(DATE_FORMATTER2.format(LocalDateTime.now()));
        bdcFczmDTO.setZmsj(StringToolUtils.getChineseDate());

        // 获取经办人信息即当前用户
        try {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(null != userDto && StringUtils.isNotBlank(userDto.getAlias())) {
                bdcFczmDTO.setJbr(userDto.getAlias());
            }
        } catch (Exception e) {
            LOGGER.error("{}打印获取经办人信息错误，对应打印编号：{}", typeInfo, bdcFczmDTO.getBh());
        }

        return bdcFczmDTO;
    }

    /**
     * 设置有房证明对应单元限制状态
     * @param zfxx 住房信息
     * @param bdcdyh 不动产单元号
     */
    private void setBdcdyXzzt(Map zfxx, String bdcdyh) {
        zfxx.put("DYAZT", "无抵押");
        zfxx.put("CFZT", "无查封");
        zfxx.put("YGZT", "无预告");
        zfxx.put("YYZT", "无异议");

        BdcdyZtDTO ztxx = bdcdyZtFeignService.queryBdcdyZt(bdcdyh);
        if(null != ztxx) {
            if(null != ztxx.getDya() && ztxx.getDya()) {
                zfxx.put("DYAZT", "抵押");
            }

            if(null != ztxx.getCf() && ztxx.getCf()) {
                zfxx.put("CFZT", "查封");
            }

            if((null != ztxx.getYg() && ztxx.getYg())  || (null != ztxx.getYdya() && ztxx.getYdya())) {
                zfxx.put("YGZT", "预告");
            }

            if(null != ztxx.getYy() && ztxx.getYy()) {
                zfxx.put("YYZT", "异议");
            }
        }
    }
}
