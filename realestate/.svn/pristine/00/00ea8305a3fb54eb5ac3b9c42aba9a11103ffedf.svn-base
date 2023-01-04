package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.AcceptBdcdyMapper;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.*;
import cn.gtmap.realestate.building.util.*;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.QlrlistResponse;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response.WwsqBdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.request.GzwxxcxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.response.GzwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdclxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.building.WwsqBdcdyxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityHelper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供根据BDCLX分页查询BDCDY服务
 */
@Service
public class AcceptBdcdyServiceImpl implements AcceptBdcdyService {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptBdcdyServiceImpl.class);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询Repo
     */
    @Autowired
    private Repo repo;

    @Autowired
    private ZdQlrService zdQlrService;

    @Autowired
    private NydQlrService nydQlrService;

    @Autowired
    private QszdQlrService qszdQlrService;

    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private BdcdySqlBuilderService bdcdySqlBuilderService;

    @Autowired
    private BdcdyLsSqlBuilderService bdcdyLsSqlBuilderService;

    @Autowired
    private LqService lqService;

    @Autowired
    private DzwQlrService dzwQlrService;

    @Autowired
    private ZhQlrService zhQlrService;

    @Autowired
    private GzwQlrService gzwQlrService;
    @Autowired
    protected MessageProvider messageProvider;

    @Autowired
    private AcceptBdcdyMapper acceptBdcdyMapper;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private FwYcHsService fwYcHsService;
    @Autowired
    private FwXmxxService fwXmxxService;
    @Autowired
    private JyqDkQlrService jyqDkQlrService;
    @Autowired
    ZdService zdService;
    @Autowired
    BdcSdFeignService bdcSdFeignService;
    @Autowired
    CbzdService cbzdService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    DjxxService djxxService;
    @Autowired
    BdcdyService bdcdyService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    EntityMapper entityMapper;

    @Value("#{'${jsc.ewqxdm:341822}'.split(',')}")
    private List<String> ewqxdm;

    /**
     * 不分页查询不动产单元 最大值
     */
    private static Integer MAX_QUERY_BDCDY = 1000;


    /**
     * @param pageable
     * @param qlxzAndZdtzm
     * @param zdtzm
     * @param dzwtzm
     * @param fwlx
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 土地及定着物
     */
    @Override
    public Page<Map> listTdAndDzwBdcdyByPage(Pageable pageable, String qlxzAndZdtzm, String zdtzm,
                                       String dzwtzm, String fwlx, Map<String, Object> paramMap) {
        Map<String, Object> bdcdyParamMap = getTdDzwQueryBdcdyMap(qlxzAndZdtzm, zdtzm, dzwtzm, fwlx, paramMap);
        return listByPageWithQlr("listTdDzwBdcdyByPageOrder",pageable,bdcdyParamMap);
    }

    /**
     * @param qlxzAndZdtzm BDCDYH 的 第13+14位
     * @param zdtzm        BDCDYH 的 第14位
     * @param dzwtzm       BDCDYH 的 第20位
     * @param fwlx         xmxx,ljz,hs,ychs
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页 土地及定着物
     */
    @Override
    public List<Map> listTdAndDzwBdcdy(String qlxzAndZdtzm, String zdtzm, String dzwtzm, String fwlx, Map<String, Object> paramMap) {
        Map<String, Object> bdcdyParamMap = getTdDzwQueryBdcdyMap(qlxzAndZdtzm, zdtzm, dzwtzm, fwlx, paramMap);
        return listTdDzwBdcdyWithQlr(bdcdyParamMap);
    }

    /**
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 海域及其定着物
     */
    @Override
    public Page<Map> listHyBdcdyByPage(Pageable pageable, String zdtzm,
                                          String dzwtzm, Map<String, Object> paramMap) {
        Map<String, Object> bdcdyParamMap = getHyAndGzwQueryBdcdyMap(zdtzm, dzwtzm, paramMap);
        return listByPageWithQlr("listHyDzwBdcdyByPageOrder",pageable,bdcdyParamMap);
    }

    /**
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页 海域及其定着物
     */
    @Override
    public List<Map> listHyBdcdy(String zdtzm, String dzwtzm, Map<String, Object> paramMap) {
        Map<String, Object> bdcdyParamMap = getHyAndGzwQueryBdcdyMap(zdtzm, dzwtzm, paramMap);
        return listHyDzwBdcdyWithQlr(bdcdyParamMap);
    }

    /**
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构筑物
     */
    @Override
    public Page<Map> listGzwBdcdyByPage(Pageable pageable, String zdtzm, String dzwtzm, Map<String, Object> paramMap) {
        Map<String, Object> bdcdyParamMap = getHyAndGzwQueryBdcdyMap(zdtzm, dzwtzm, paramMap);
        return listByPageWithQlr("listGzwBdcdyByPageOrder",pageable,bdcdyParamMap);
    }

    /**
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页构筑物
     */
    @Override
    public List<Map> listGzwBdcdy(String zdtzm, String dzwtzm, Map<String, Object> paramMap) {
        Map<String, Object> bdcdyParamMap = getHyAndGzwQueryBdcdyMap(zdtzm, dzwtzm, paramMap);
        return listGzwBdcdyWithQlr(bdcdyParamMap);
    }

    @Override
    public Page<Map> listCbzdBdcdyByPage(Pageable pageable, String zdtzm,
                                  String dzwtzm, Map<String, Object> paramMap){
        Map<String, Object> bdcdyParamMap = getHyAndGzwQueryBdcdyMap(zdtzm, dzwtzm, paramMap);
        Page<Map> result= repo.selectPaging("listCbfCbzdBdcdyByPageOrder", bdcdyParamMap, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return result;

    }

    @Override
    public List<Map> listCbzdBdcdy(String zdtzm,
                            String dzwtzm, Map<String, Object> paramMap){
        Map<String, Object> bdcdyParamMap = getHyAndGzwQueryBdcdyMap(zdtzm, dzwtzm, paramMap);
        return listCbzdBdcdyWithQlr(bdcdyParamMap);

    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyLcztResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询流程状态
     */
    @Override
    public BdcdyLcztResponseDTO queryBdcdyLczt(String bdcdyh) {
        Map<String,Object> resultMap = null;
        if(StringUtils.isNotBlank(bdcdyh)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("bdcdyh",bdcdyh);
            // 1. 先判断 是否为房屋的BDCDY
            if(TzmUtils.isFwBdcdy(bdcdyh)){
                resultMap = acceptBdcdyMapper.getFwBdcdyLcztByBdcdyh(paramMap);
            }else{
                Class[] doClassArr = TzmUtils.getDOArrByBdcdyh(bdcdyh);
                // 2.纯土地类型的 BDCDY
                if(doClassArr.length == 1 && TzmUtils.isTdBdcdy(bdcdyh)){
                    Class djdcbClass = doClassArr[0];
                    EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(djdcbClass);
                    String zdTableName = entityTable.getName();
                    if(StringUtils.isNotBlank(zdTableName)){
                        paramMap.put("zdTableName",zdTableName);
                        resultMap = acceptBdcdyMapper.getTdBdcdyLcztByBdcdyh(paramMap);
                    }
                }else if(doClassArr.length > 1){
                    // 3. 其他定作物 的 BDCDY
                    resultMap = acceptBdcdyMapper.getDcbBdcdyLcztByBdcdyh(paramMap);
                }
            }
        }
        if(resultMap != null){
            BdcdyLcztResponseDTO responseDTO = new BdcdyLcztResponseDTO();
            responseDTO.setBdcdyh(MapUtils.getString(resultMap,"BDCDYH"));
            responseDTO.setLczt(MapUtils.getString(resultMap,"LCZT"));
            return responseDTO;
        }
        return null;
    }

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 工作流实例ID 查询所有LJZ
     */
    @Override
    public List<FwLjzDO> listFwLjzByGzlslid(String gzlslid) {
        List<FwLjzDO> fwLjzDOList = new ArrayList<>();
        if(StringUtils.isNotBlank(gzlslid)){
            // 查询BDCXMLIST
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            HashSet<String> fwDcbIndexSet = new HashSet<>();
            HashSet<String> bdcdyhSet = new HashSet<>();
            Map<String, Object> fwHsMap = new HashMap<>(bdcXmDTOList.size());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                // 循环 查询逻辑幢
                for(BdcXmDTO bdcXmDTO : bdcXmDTOList){
                    if(StringUtils.isNotBlank(bdcXmDTO.getBdcdyh())){
                        // 如果 BDCDYHSET中已存在当前BDCDYH 则循环下一个
                        if(bdcdyhSet.contains(bdcXmDTO.getBdcdyh())){
                            continue;
                        }
                        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcXmDTO.getBdcdyh());
                        if(fwHsDO == null){
                            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcXmDTO.getBdcdyh());
                            if(fwYchsDO != null){
                                fwHsDO = new FwHsDO();
                                BeanUtils.copyProperties(fwYchsDO,fwHsDO);
                            }
                        }
                        // 查询户室类型的BDCDY
                        if(fwHsDO != null
                                && StringUtils.isNotBlank(fwHsDO.getFwDcbIndex())){
                            String fwDcbIndex = fwHsDO.getFwDcbIndex();
                            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
                            if (fwLjzDO != null && !fwDcbIndexSet.contains(fwDcbIndex)) {
                                fwLjzDOList.add(fwLjzDO);
                                // 查出这个幢下的所有户室
                                setFwHsBdcdyhSetByFwDcbIndex(bdcdyhSet, fwDcbIndex, fwHsMap);
                                setFwYcHsBdcdyhSetByFwDcbIndex(bdcdyhSet, fwDcbIndex, fwHsMap);
                            }
                        }
                    }
                }
            }
        }
        return fwLjzDOList;
    }

    /**
     * @param gzlslid 查询逻辑幢分组信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 工作流实例ID 查询所有LJZ,且计算当前所有的户室信息，并根据户室用途和终止日期信息分组
     * @date : 2021/1/14 14:35
     */
    @Override
    public LpxxDTO listFwljzFzxx(String gzlslid) {
        List<FwLjzDTO> fwLjzDTOList = new ArrayList<>();
        LpxxDTO lpxxDTO = new LpxxDTO();
        if (StringUtils.isNotBlank(gzlslid)) {
            // 查询BDCXMLIST
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                // 循环 查询逻辑幢
                HashSet<String> bdcdyhSet = new HashSet<>();
                HashSet<String> fwDcbIndexSet = new HashSet<>();
                Map<String, Object> fwHsMap = new HashMap<>(bdcXmDTOList.size());
                List<FwHsDO> fwHsDOList = new ArrayList<>(bdcXmDTOList.size());
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    if (StringUtils.isNotBlank(bdcXmDTO.getBdcdyh())) {
                        FwLjzDTO fwLjzDTO = new FwLjzDTO();
                        // 如果 BDCDYHSET中已存在当前BDCDYH 则循环下一个
                        if (bdcdyhSet.contains(bdcXmDTO.getBdcdyh())) {
                            FwHsDO fwHsDO = (FwHsDO) fwHsMap.get(bdcXmDTO.getBdcdyh());
                            fwHsDOList.add(fwHsDO);
                            continue;
                        }
                        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcXmDTO.getBdcdyh());
                        if (fwHsDO == null) {
                            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcXmDTO.getBdcdyh());
                            if (fwYchsDO != null) {
                                fwHsDO = new FwHsDO();
                                BeanUtils.copyProperties(fwYchsDO, fwHsDO);
                                //属性拷贝完后，实测户室 保存的是scjzmj 预测户室保存的额是ycjzmj
                                fwHsDO.setScjzmj(fwYchsDO.getYcjzmj());
                            }
                        }
                        // 查询户室类型的BDCDY
                        if (fwHsDO != null
                                && StringUtils.isNotBlank(fwHsDO.getFwDcbIndex())) {
                            fwHsDOList.add(fwHsDO);
                            String fwDcbIndex = fwHsDO.getFwDcbIndex();
                            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
                            if (fwLjzDO != null && !fwDcbIndexSet.contains(fwDcbIndex)) {
                                fwDcbIndexSet.add(fwDcbIndex);
                                fwLjzDTO.setFwLjzDO(fwLjzDO);
                                fwLjzDTOList.add(fwLjzDTO);
                                // 查出这个幢下的所有户室
                                setFwHsBdcdyhSetByFwDcbIndex(bdcdyhSet, fwDcbIndex, fwHsMap);
                                setFwYcHsBdcdyhSetByFwDcbIndex(bdcdyhSet, fwDcbIndex, fwHsMap);
                            }
                        }
                    }
                }
                //得到所有的逻辑幢和逻辑幢内的所有户室信息
                //所有户室数据根据用途和终止日期分组
                if (CollectionUtils.isNotEmpty(fwLjzDTOList)) {
                    //当前流程所有的户室数据根据fw_dcb_index 分组
                    Map<String, List<FwHsDO>> fwhsListMap = fwHsDOList.stream().collect(Collectors.groupingBy(FwHsDO::getFwDcbIndex));
                    //去重后表示当前所有户室用途和终止日期的种类数量是多少
                    HashSet<String> ghytSet = new HashSet<>(5);
                    HashSet<String> zzrqSet = new HashSet<>(5);
                    //循环逻辑幢
                    for (FwLjzDTO fwLjzDTO : fwLjzDTOList) {
                        if (StringUtils.isNotBlank(fwLjzDTO.getFwLjzDO().getLszd())) {
                            ZdDjdcbDO zdDjdcbDO = zdService.querZdByDjh(fwLjzDTO.getFwLjzDO().getLszd());
                            if (Objects.nonNull(zdDjdcbDO)) {
                                fwLjzDTO.setZdmj(zdDjdcbDO.getPzmj());
                            }
                        }
                        //获取逻辑幢下的户室
                        List<FwHsDO> fwHsDOS = fwhsListMap.get(fwLjzDTO.getFwLjzDO().getFwDcbIndex());
                        //根据用途和终止日期分组
                        Map<String, Map<String, List<FwHsDO>>> ytAndZzrqGroupMap = fwHsDOS.stream().collect(Collectors.groupingBy((item -> {
                            if (StringUtils.isBlank(item.getGhyt())) {
                                return "0";
                            }
                            return item.getGhyt();
                        }), Collectors.groupingBy((data -> {
                            if (Objects.isNull(data.getZzrq())) {
                                return "0";
                            }
                            return new SimpleDateFormat("yyyy-MM-dd").format(data.getZzrq());
                        }))));
                        LOGGER.info(JSONObject.toJSONString(ytAndZzrqGroupMap));
                        if (MapUtils.isNotEmpty(ytAndZzrqGroupMap)) {
                            List<GroupHsDTO> groupHsDTOList = new ArrayList<>(5);
                            for (Map.Entry<String, Map<String, List<FwHsDO>>> ghytEntry : ytAndZzrqGroupMap.entrySet()) {
                                //根据用途分组的数据
                                Map<String, List<FwHsDO>> ghytHsMap = ghytEntry.getValue();
                                for (Map.Entry<String, List<FwHsDO>> zzrqEntry : ghytHsMap.entrySet()) {
                                    //根据日期分组的数据
                                    List<FwHsDO> groupHsList = zzrqEntry.getValue();
                                    //计算数量
                                    GroupHsDTO groupHsDTO = new GroupHsDTO();
                                    groupHsDTO.setCount(groupHsList.size());
                                    groupHsDTO.setGhyt(ghytEntry.getKey());
                                    //计算建筑面积和土地面积
                                    groupHsDTO.setSumjzmj(NumberUtil.formatDigit(groupHsList.stream().filter(hs -> hs.getScjzmj() != null).mapToDouble(FwHsDO::getScjzmj).sum(), 3));
                                    groupHsDTO.setSumtdmj(NumberUtil.formatDigit(groupHsList.stream().filter(hs -> hs.getFttdmj() != null).mapToDouble(FwHsDO::getFttdmj).sum(), 3));
                                    groupHsDTO.setZzrq(zzrqEntry.getKey());
                                    ghytSet.add(ghytEntry.getKey());
                                    zzrqSet.add(zzrqEntry.getKey());
                                    groupHsDTOList.add(groupHsDTO);
                                }
                            }
                            fwLjzDTO.setGroupHsDTOList(groupHsDTOList);
                            fwLjzDTO.setSumJzmj(NumberUtil.formatDigit(groupHsDTOList.stream().filter(groupHsDTO -> groupHsDTO.getSumjzmj() != null).mapToDouble(GroupHsDTO::getSumjzmj).sum(), 3));
                            fwLjzDTO.setSumTdmj(NumberUtil.formatDigit(groupHsDTOList.stream().filter(groupHsDTO -> groupHsDTO.getSumtdmj() != null).mapToDouble(GroupHsDTO::getSumtdmj).sum(), 3));
                        }
                        fwLjzDTO.setFwHsDOList(fwHsDOS);
                        fwLjzDTO.setSumFwhs(fwHsDOS.size());
                    }
                    lpxxDTO.setSumGhytZl(ghytSet.size());
                    lpxxDTO.setSumZzrqZl(zzrqSet.size());
                    lpxxDTO.setSumJzmj(NumberUtil.formatDigit(fwLjzDTOList.stream().filter(fwLjzDTO -> fwLjzDTO.getSumJzmj() != null).mapToDouble(FwLjzDTO::getSumJzmj).sum(), 3));
                    lpxxDTO.setSumTdmj(NumberUtil.formatDigit(fwLjzDTOList.stream().filter(fwLjzDTO -> fwLjzDTO.getSumTdmj() != null).mapToDouble(FwLjzDTO::getSumTdmj).sum(), 3));
                    lpxxDTO.setSumFwhs(fwHsDOList.size());
                    lpxxDTO.setFwLjzDTOList(fwLjzDTOList);
                }
            }
        }
        return lpxxDTO;
    }

    @Override
    public String queryZlBySgbh(String sgbh){
        return acceptBdcdyMapper.queryZlBySgbh(sgbh);
    }


    /**
     * @param bdcdyhSet
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWDCBINDEX查询户室列表 将BDCDYH存入BDCDYHSET
     */
    private void setFwHsBdcdyhSetByFwDcbIndex(HashSet<String> bdcdyhSet, String fwDcbIndex, Map<String, Object> fwHsMap) {
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            for (FwHsDO fwHsDO : fwHsDOList) {
                if (StringUtil.isNotBlank(fwHsDO.getBdcdyh())) {
                    bdcdyhList.add(fwHsDO.getBdcdyh());
                    fwHsMap.put(fwHsDO.getBdcdyh(), fwHsDO);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            bdcdyhSet.addAll(bdcdyhList);
        }
    }

    /**
     * @param bdcdyhSet
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWDCBINDEX查询预测户室列表 将BDCDYH存入BDCDYHSET
     */
    private void setFwYcHsBdcdyhSetByFwDcbIndex(HashSet<String> bdcdyhSet, String fwDcbIndex, Map<String, Object> fwHsMap) {
        List<FwYchsDO> ychsList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ychsList)) {
            for (FwYchsDO ychs : ychsList) {
                if (StringUtil.isNotBlank(ychs.getBdcdyh()) &&!fwHsMap.containsKey(ychs.getBdcdyh())) {
                    bdcdyhList.add(ychs.getBdcdyh());
                    FwHsDO fwHsDO = new FwHsDO();
                    BeanUtils.copyProperties(ychs, fwHsDO);
                    fwHsDO.setScjzmj(ychs.getYcjzmj());
                    fwHsMap.put(fwHsDO.getBdcdyh(), fwHsDO);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            bdcdyhSet.addAll(bdcdyhList);
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdtzmArr
     * @param mainSqlList
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 处理主SQL为参数
     */
    private Map<String, Object> dealParamMap(String[] qlxzAndZdtzmArr,String[] zdtzmArr,List<String> mainSqlList,String[] dzwtzmArr){
        Map<String, Object> paramMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(mainSqlList)){
            StringBuilder sqlBuilder = new StringBuilder("");
            for(int i = 0 ; i < mainSqlList.size() ; i++){
                sqlBuilder.append(mainSqlList.get(i));
                if(i != mainSqlList.size() - 1){
                    sqlBuilder.append(" UNION ALL ");
                }
            }
            paramMap.put("querySql",sqlBuilder.toString());
        }
        paramMap.put("qlxzAndZdtzm",Arrays.asList(qlxzAndZdtzmArr));
        paramMap.put("zdtzm",Arrays.asList(zdtzmArr));
        paramMap.put("dzwtzm",Arrays.asList(dzwtzmArr));
        return paramMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sqlId
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 分页查询数据 包含权利人
     */
    private Page<Map> listByPageWithQlr(String sqlId,Pageable pageable, Map<String, Object> paramMap){
        Page<Map> result = repo.selectPaging(sqlId, paramMap, pageable);
        LOGGER.info("户室数据查询结束，循环处理权利人");
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                setQlr(data);
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        LOGGER.info("户室数据查询结束，权利人处理结束");
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @description
     */
    private List<Map> listTdDzwBdcdyWithQlr(Map<String, Object> paramMap){
        Integer count = acceptBdcdyMapper.countTdDzwBdcdy(paramMap);
        if(count == null){
            throw new AppException("查询不动产单元总数为空");
        }
        if(count > MAX_QUERY_BDCDY){
            throw new AppException("查询不动产单元数量超出最大值"+MAX_QUERY_BDCDY);
        }
        List<Map> resultList = acceptBdcdyMapper.listTdDzwBdcdyByPageOrder(paramMap);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map data : resultList) {
                setQlr(data);
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return resultList;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @description
     */
    private List<Map> listHyDzwBdcdyWithQlr(Map<String, Object> paramMap){
        Integer count = acceptBdcdyMapper.countHyDzwBdcdy(paramMap);
        if(count == null){
            throw new AppException("查询不动产单元总数为空");
        }
        if(count > MAX_QUERY_BDCDY){
            throw new AppException("查询不动产单元数量超出最大值"+MAX_QUERY_BDCDY);
        }
        List<Map> resultList = acceptBdcdyMapper.listHyDzwBdcdyByPageOrder(paramMap);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map data : resultList) {
                setQlr(data);
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return resultList;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @description
     */
    private List<Map> listGzwBdcdyWithQlr(Map<String, Object> paramMap){
        Integer count = acceptBdcdyMapper.countGzwBdcdy(paramMap);
        if(count == null){
            throw new AppException("查询不动产单元总数为空");
        }
        if(count > MAX_QUERY_BDCDY){
            throw new AppException("查询不动产单元数量超出最大值"+MAX_QUERY_BDCDY);
        }
        List<Map> resultList = acceptBdcdyMapper.listGzwBdcdyByPageOrder(paramMap);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map data : resultList) {
                setQlr(data);
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return resultList;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private List<Map> listCbzdBdcdyWithQlr(Map<String, Object> paramMap){
        Integer count = acceptBdcdyMapper.countGzwBdcdy(paramMap);
        if(count == null){
            throw new AppException("查询不动产单元总数为空");
        }
        if(count > MAX_QUERY_BDCDY){
            throw new AppException("查询不动产单元数量超出最大值"+MAX_QUERY_BDCDY);
        }
        List<Map> resultList = acceptBdcdyMapper.listCbfCbzdBdcdyByPageOrder(paramMap);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map data : resultList) {
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return resultList;
    }

    /**
     * @param dataMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 保存宗地相关权利人
     */
    private void setQlr(Map dataMap) {
        List<String> qlrList = new ArrayList<>();
        if (dataMap != null) {
            String djh = MapUtils.getString(dataMap,"DJH");
            String qjid = MapUtils.getString(dataMap,"QJID");
            String lx = MapUtils.getString(dataMap,"LX");
            String bdcdyh = MapUtils.getString(dataMap,"BDCDYH");
            // 宗地查询  区分 宗地权利人 \ 权属宗地权利人 \农用地宗地权利人
            if(StringUtils.equals("QSZD",lx)){
                List<QszdQlrDO> qlrDOList;
                if(BdcdySqlConstants.relationDjxxQlrWithFk()){
                    qlrDOList = qszdQlrService.listQszdQlrByQszdDjdcbIndex(qjid);
                }else{
                    qlrDOList = qszdQlrService.listQszdQlrByDjh(djh);
                }
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    for (QszdQlrDO qlr : qlrDOList) {
                        if(StringUtils.isNotBlank(qlr.getQlrmc())){
                            qlrList.add(qlr.getQlrmc());
                        }
                    }
                }
            }else if(StringUtils.equals("ZD",lx)){
                List<ZdQlrDO> zdQlrList;
                if(BdcdySqlConstants.relationDjxxQlrWithFk()){
                    zdQlrList = zdQlrService.listZdQlrByDjdcbIndex(qjid);
                } else {
                    zdQlrList = zdQlrService.listZdQlrByDjh(djh);
                }
                if (CollectionUtils.isNotEmpty(zdQlrList)) {
                    for (ZdQlrDO qlr : zdQlrList) {
                        if(StringUtils.isNotBlank(qlr.getQlrmc())){
                            qlrList.add(qlr.getQlrmc());
                        }
                    }
                }
            } else if (StringUtils.equals("NYD", lx)) {
                List<NydQlrDO> nydQlrList;
                if (BdcdySqlConstants.relationDjxxQlrWithFk()) {
                    nydQlrList = nydQlrService.listNydQlrByDjdcbIndex(qjid);
                } else {
                    nydQlrList = nydQlrService.listNydQlrByDjh(djh);
                }
                if (CollectionUtils.isNotEmpty(nydQlrList)) {
                    for (NydQlrDO qlr : nydQlrList) {
                        if (StringUtils.isNotBlank(qlr.getQlrmc())) {
                            qlrList.add(qlr.getQlrmc());
                        }
                    }
                }
            }else if (StringUtils.equals("JYQ", lx)) {
                List<JyqDkQlrDO> jyqDkQlrDOList =jyqDkQlrService .listJyqDkQlrByDjdcbIndex(qjid);;
                if (CollectionUtils.isNotEmpty(jyqDkQlrDOList)) {
                    for (JyqDkQlrDO qlr : jyqDkQlrDOList) {
                        if (StringUtils.isNotBlank(qlr.getQlrmc())) {
                            qlrList.add(qlr.getQlrmc());
                        }
                    }
                }
            } else if(StringUtils.equals("XMXX",lx)
                    || StringUtils.equals("LJZ",lx)
                    || StringUtils.equals("HS",lx)
                    || StringUtils.equals("YCHS",lx)){
                List<FwFcqlrDO> fwFcqlrDOList = fwFcqlrService.listFwFcQlrByFwIndex(qjid);
                if (CollectionUtils.isNotEmpty(fwFcqlrDOList)) {
                    for (FwFcqlrDO qlr : fwFcqlrDOList) {
                        if(StringUtils.isNotBlank(qlr.getQlr())){
                            qlrList.add(qlr.getQlr());
                        }
                    }
                }
            }else if(StringUtils.equals("LQ",lx)){
                //承包经营权权利人取承包方
                if (StringUtils.isNotBlank(bdcdyh) &&TzmUtils.hasCbzdDcb(bdcdyh)) {
                    List<CbzdCbfDO> cbzdCbfDOList =cbzdService.listCbfByBdcdyh(bdcdyh);
                    if (CollectionUtils.isNotEmpty(cbzdCbfDOList)) {
                        for (CbzdCbfDO qlr : cbzdCbfDOList) {
                            if (StringUtils.isNotBlank(qlr.getCbfmc())) {
                                qlrList.add(qlr.getCbfmc());
                            }
                        }
                    }
                }else {
                    List<NydQlrDO> nydQlrDOList;
//                    if (BdcdySqlConstants.relationDjxxQlrWithFk()) {
//                        nydQlrDOList = lqService.listLmsuqrByDcbIndex(qjid);
//                    } else {
//                        nydQlrDOList = lqService.listLmsuqrByDjh(djh);
//                    }
                    //sflmsuqr等字段不维护,不进行qlrlx过滤
                    if (BdcdySqlConstants.relationDjxxQlrWithFk()) {
                        nydQlrDOList = nydQlrService.listNydQlrByDjdcbIndex(qjid);
                    } else {
                        nydQlrDOList = nydQlrService.listNydQlrByDjh(djh);
                    }
                    if (CollectionUtils.isNotEmpty(nydQlrDOList)) {
                        for (NydQlrDO qlr : nydQlrDOList) {
                            if (StringUtils.isNotBlank(qlr.getQlrmc())) {
                                qlrList.add(qlr.getQlrmc());
                            }
                        }
                    }
                }
            }else if(StringUtils.equals("DZW",lx)){
                List<DzwQlrDO> dzwQlrList = dzwQlrService.listDzwQlrByDcbIndex(qjid);
                if(CollectionUtils.isNotEmpty(dzwQlrList)){
                    for (DzwQlrDO qlr : dzwQlrList) {
                        if(StringUtils.isNotBlank(qlr.getQlr())){
                            qlrList.add(qlr.getQlr());
                        }
                    }
                }
            }else if(StringUtils.equals("ZH",lx)){
                List<ZhQlrDO> zhQlrDOList = zhQlrService.listZhQlrByZhDcbIndex(qjid);
                if(CollectionUtils.isNotEmpty(zhQlrDOList)){
                    for (ZhQlrDO qlr : zhQlrDOList) {
                        if(StringUtils.isNotBlank(qlr.getQlrmc())){
                            qlrList.add(qlr.getQlrmc());
                        }
                    }
                }
            }else if(StringUtils.equals("GZW",lx)){
                List<GzwQlrDO> gzwQlrList = gzwQlrService.listGzwQlrByDcbIndex(qjid);
                if(CollectionUtils.isNotEmpty(gzwQlrList)){
                    for (GzwQlrDO qlr : gzwQlrList) {
                        if(StringUtils.isNotBlank(qlr.getQlr())){
                            qlrList.add(qlr.getQlr());
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(qlrList)) {
                dataMap.put("QLR",BuildingUtils.wmQlrMcWithList(qlrList));
            }
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 获取海域和构筑物查询参数MAP
     */
    private Map<String,Object> getHyAndGzwQueryBdcdyMap(String zdtzm,
                                                        String dzwtzm, Map<String, Object> paramMap){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.putAll(paramMap);
        if (StringUtils.isNotBlank(MapUtils.getString(resultMap, "qlxzAndZdtzm"))) {
            resultMap.put("qlxzAndZdtzm", Arrays.asList(MapUtils.getString(resultMap, "qlxzAndZdtzm").split(",")));
        }else{
            resultMap.put("qlxzAndZdtzm", new String[0]);
        }
        String[] zdtzmArr = new String[0];
        String[] dzwtzmArr = new String[0];
        if(StringUtils.isNotBlank(zdtzm)){
            zdtzmArr = zdtzm.split(",");
        }
        if(StringUtils.isNotBlank(dzwtzm)){
            dzwtzmArr = dzwtzm.split(",");
        }
        resultMap.put("zdtzm",Arrays.asList(zdtzmArr));
        resultMap.put("dzwtzm",Arrays.asList(dzwtzmArr));
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qlxzAndZdtzm
     * @param zdtzm
     * @param dzwtzm
     * @param fwlx
     * @param paramMap
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 构造查询BDCDY参数MAP
     */
    private Map<String,Object> getTdDzwQueryBdcdyMap(String qlxzAndZdtzm, String zdtzm,
                                                     String dzwtzm, String fwlx, Map<String, Object> paramMap){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(paramMap);
        String[] qlxzAndZdtzmArr = new String[0];
        String[] zdtzmArr = new String[0];
        String[] dzwtzmArr = new String[0];
        String[] fwlxArr = new String[0];
        if(StringUtils.isNotBlank(qlxzAndZdtzm)){
            qlxzAndZdtzmArr = qlxzAndZdtzm.split(",");
        }
        if(StringUtils.isNotBlank(zdtzm)){
            zdtzmArr = zdtzm.split(",");
        }
        if(StringUtils.isNotBlank(dzwtzm)){
            dzwtzmArr = dzwtzm.split(",");
        }
        if(StringUtils.isNotBlank(fwlx)){
            fwlxArr = fwlx.split(",");
        }
        // 判断是否需要查询权利人
        boolean withQlr = StringUtils.isNotBlank(MapUtils.getString(paramMap,"qlr"));
        //获取权利人模糊方式
        String qlrmh = MapUtils.getString(paramMap,"qlrmh");
        // 获取查询表的SQL
        List<String> mainSqlList = bdcdySqlBuilderService.getMainSqlList(dzwtzmArr, zdtzmArr, fwlxArr, withQlr,qlrmh);
        // 处理 查询SQL 和 特征码 存放进查询MAP
        if(CollectionUtils.isEmpty(mainSqlList)){
            LOGGER.error("受理模块分页查询不动产单元，找不到对应表异常：qlxzAndZdtzm:{},zdtzm:{},dzwtzm:{},fwlx:{}",qlxzAndZdtzm,zdtzm,dzwtzm,fwlx);
            throw new AppException(ErrorCodeConstants.ACCEPT_QUERYBDCDY_ERROR,
                    messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.ACCEPT_QUERYBDCDY_ERROR));
        }
        resultMap.putAll(dealParamMap(qlxzAndZdtzmArr,zdtzmArr,mainSqlList,dzwtzmArr));
        return resultMap;
    }


    private Map<String,Object> getLsTdDzwQueryBdcdyMap(String qlxzAndZdtzm, String zdtzm,
                                                     String dzwtzm, String fwlx, Map<String, Object> paramMap){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(paramMap);
        String[] qlxzAndZdtzmArr = new String[0];
        String[] zdtzmArr = new String[0];
        String[] dzwtzmArr = new String[0];
        String[] fwlxArr = new String[0];
        if(StringUtils.isNotBlank(qlxzAndZdtzm)){
            qlxzAndZdtzmArr = qlxzAndZdtzm.split(",");
        }
        if(StringUtils.isNotBlank(zdtzm)){
            zdtzmArr = zdtzm.split(",");
        }
        if(StringUtils.isNotBlank(dzwtzm)){
            dzwtzmArr = dzwtzm.split(",");
        }
        if(StringUtils.isNotBlank(fwlx)){
            fwlxArr = fwlx.split(",");
        }
        // 判断是否需要查询权利人
        boolean withQlr = StringUtils.isNotBlank(MapUtils.getString(paramMap,"qlr"));
        //获取权利人模糊方式
        String qlrmh = MapUtils.getString(paramMap,"qlrmh");
        // 获取查询表的SQL
        List<String> mainSqlList = bdcdyLsSqlBuilderService.getMainSqlList(dzwtzmArr, zdtzmArr, fwlxArr, withQlr,qlrmh);
        // 处理 查询SQL 和 特征码 存放进查询MAP
        if(CollectionUtils.isEmpty(mainSqlList)){
            LOGGER.error("受理模块分页查询不动产单元，找不到对应表异常：qlxzAndZdtzm:{},zdtzm:{},dzwtzm:{},fwlx:{}",qlxzAndZdtzm,zdtzm,dzwtzm,fwlx);
            throw new AppException(ErrorCodeConstants.ACCEPT_QUERYBDCDY_ERROR,
                    messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.ACCEPT_QUERYBDCDY_ERROR));
        }
        resultMap.putAll(dealParamMap(qlxzAndZdtzmArr,zdtzmArr,mainSqlList,dzwtzmArr));
        return resultMap;
    }


    /**
     * 获取幢信息
     *
     * @param bdcSlYwxxDTOList 受理项目前台
     * @return map 受理项目根据幢分组
     */
    @Override
    public Map<String, List<BdcSlYwxxDTO>> getLjzxx(List<BdcSlYwxxDTO> bdcSlYwxxDTOList) {
        Map<String, List<BdcSlYwxxDTO>> idListMap = new HashMap<>();
        HashSet bdcdyhSet = new HashSet();
        Map<String, String> bdcdyhFwDcbIndexMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            for (BdcSlYwxxDTO slYwxxDTO : bdcSlYwxxDTOList) {
                String bdclx = BdcdyhToolUtils.getDzwTzm(slYwxxDTO.getBdcdyh());
                String idzlStr;
                // 房屋
                if (CommonConstantUtils.BDCLX_TZM_F.equals(bdclx)) {
                    if (bdcdyhSet.contains(slYwxxDTO.getBdcdyh())) {
                        // 之前查询过
                        idzlStr = bdcdyhFwDcbIndexMap.get(slYwxxDTO.getBdcdyh());
                    } else {
                        idzlStr = getAndSaveLjzxx(slYwxxDTO.getBdcdyh(), bdcdyhSet, bdcdyhFwDcbIndexMap);
                    }
                    List<BdcSlYwxxDTO> list = idListMap.get(idzlStr);
                    // 已经存在累加
                    if (CollectionUtils.isNotEmpty(list)) {
                        list.add(slYwxxDTO);
                        idListMap.put(idzlStr, list);
                    } else {
                        // 不存在，新增
                        List<BdcSlYwxxDTO> slYwxxDTOS = new ArrayList<>();
                        slYwxxDTOS.add(slYwxxDTO);
                        if (StringUtils.isBlank(idzlStr) || CommonConstantUtils.ZF_YW_DH.equals(idzlStr)) {
                            LOGGER.info("未查询到对应的逻辑幢信息，bdcdyh为:{}", slYwxxDTO.getBdcdyh());
                            // 单独一组
                            idzlStr = slYwxxDTO.getXmid() + CommonConstantUtils.ZF_YW_DH + slYwxxDTO.getZl();
                        }
                        idListMap.put(idzlStr, slYwxxDTOS);
                    }
                } else {
                    // 土地
                    idzlStr = slYwxxDTO.getXmid() + CommonConstantUtils.ZF_YW_DH + slYwxxDTO.getZl();
                    List<BdcSlYwxxDTO> slYwxxDTOS = new ArrayList<>();
                    slYwxxDTOS.add(slYwxxDTO);
                    idListMap.put(idzlStr, slYwxxDTOS);
                }
            }
        }
        return idListMap;

    }

    @Override
    public String getQsxzByBdcdyh(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh) &&!TzmUtils.isFwBdcdy(bdcdyh)){
            DjDcbResponseDTO djDcbResponseDTO =djxxService.getDjdcbDTOByBdcdyh(bdcdyh);
            if(djDcbResponseDTO instanceof NydDjdcbResponseDTO){
                return ((NydDjdcbResponseDTO) djDcbResponseDTO).getQsxz();
            }else if(djDcbResponseDTO instanceof JyqDkDcbResponseDTO){
                return ((JyqDkDcbResponseDTO) djDcbResponseDTO).getQsxz();
            }


        }
        return null;

    }

    /**
     * @param qxdmList@return
     * @author
     * @description 获取区县下林地总面积和宗地数量
     */
    @Override
    public List<Map> getLdmjAndZd(List<String> qxdmList) {
        if(CollectionUtils.isNotEmpty(qxdmList)){
            qxdmList = new ArrayList<>(qxdmList);
            qxdmList.remove("");
            if(CollectionUtils.isNotEmpty(qxdmList) && CollectionUtils.isNotEmpty(ewqxdm)) {
                qxdmList.addAll(ewqxdm);
            }
        }
        return lqService.getLdmjAndZd(qxdmList);
    }

    @Override
    public Page<WwsqBdcdyxxDTO> listWwsqBdcdyxxDTOByPage(Pageable pageable, WwsqBdcdyxxQO wwsqBdcdyxxQO){
        Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(wwsqBdcdyxxQO),Map.class);
        if(MapUtils.isEmpty(paramMap)){
            throw new AppException("分页查询外网申请单元信息,查询条件为空");
        }
        String houseId =MapUtils.getString(paramMap,"houseid");
        String bdcdyh =MapUtils.getString(paramMap,"bdcdyh");
        //houseId地方特殊字段,特殊处理
        if(StringUtils.isNotBlank(houseId)){
            List<Map> hsList =bdcdyService.listFwScYcHsByHouseId(houseId);
            if(CollectionUtils.isEmpty(hsList)){
                return new PageImpl(new ArrayList(), pageable, 0);
            }
            String hsBdcdyh =MapUtils.getString(hsList.get(0), "BDCDYH", "");
            if(StringUtils.isBlank(bdcdyh) ||StringUtils.equals(bdcdyh,hsBdcdyh)){
                paramMap.put("bdcdyh",hsBdcdyh);
            }else{
                return new PageImpl(new ArrayList(), pageable, 0);
            }
        }
        //处理查询参数
        dealParam(paramMap);
        List<WwsqBdcdyxxDTO> wwsqBdcdyxxDTOList =new ArrayList<>();
        Page<Map> result = repo.selectPaging("listWwsqBdcdyxxDTOByPageOrder", paramMap, pageable);
        if(CollectionUtils.isNotEmpty(result.getContent())){
            //设置状态
            setBdcdyzt(result.getContent());
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            for (Map data : result.getContent()) {
                WwsqBdcdyxxDTO wwsqBdcdyxxDTO =JSONObject.parseObject(JSONObject.toJSONString(data),WwsqBdcdyxxDTO.class);
                //获取权利人信息
                List<QlrlistResponse> qlrlistResponseList =getQlrxx(data,zdMap);
                if(CollectionUtils.isNotEmpty(qlrlistResponseList)){
                    wwsqBdcdyxxDTO.setQlrlist(qlrlistResponseList);
                }
                // 房屋结构与zcs处理：获取优先取值权籍fw_hs表fwjg,如无值则取对应fw_ljz表的fwjg，zcs字段取对应fw_ljz的fwcs字段 huanghui
                String lx = MapUtils.getString(data,"LX");
                if ("HS".equalsIgnoreCase(lx) || "YCHS".equalsIgnoreCase(lx)) {
                    //查询逻辑幢信息
                    String fwDcbIndex = MapUtils.getString(data,"FWDCBINDEX");
                    if (StringUtils.isNotBlank(fwDcbIndex)) {
                        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
                        if (Objects.nonNull(fwLjzDO)) {
                            //zcs字段取对应fw_ljz的fwcs字段
                            if (Objects.nonNull(fwLjzDO.getFwcs())) {
                                wwsqBdcdyxxDTO.setZcs(String.valueOf(fwLjzDO.getFwcs()));
                            }
                            //fwjg获取优先取值权籍fw_hs表fwjg,如无值则取对应fw_ljz表的fwjg
                            String fwjg = MapUtils.getString(data,"FWJG");
                            if (StringUtils.isEmpty(fwjg) && StringUtils.isNotBlank(fwLjzDO.getFwjg())) {
                                wwsqBdcdyxxDTO.setFwjg(fwLjzDO.getFwjg());
                            }
                        }
                    }
                }
                wwsqBdcdyxxDTOList.add(wwsqBdcdyxxDTO);
            }
            //房屋宗地信息
            setFwzdxx(wwsqBdcdyxxDTOList);
            //字典信息
            setZdmc(wwsqBdcdyxxDTOList,zdMap);

        }
        return PageUtils.listToPage(wwsqBdcdyxxDTOList,pageable);

    }

    @Override
    public Page<GzwxxResponseDTO> listWwsqGzwxxDTOByPage(Pageable pageable, GzwxxcxQO gzwxxcxQO){
        Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(gzwxxcxQO),Map.class);
        if(MapUtils.isEmpty(paramMap)){
            throw new AppException("分页查询外网申请构筑物信息,查询条件为空");
        }
        //处理查询参数
        dealParam(paramMap);
        Page<Map> result = repo.selectPaging("listWwsqGzwxxDTOByPageOrder", paramMap, pageable);
        List<GzwxxResponseDTO> gzwxxResponseDTOList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(result.getContent())){
            //设置状态
            setBdcdyzt(result.getContent());
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            for (Map data : result.getContent()) {
                GzwxxResponseDTO gzwxxResponseDTO =JSONObject.parseObject(JSONObject.toJSONString(data),GzwxxResponseDTO.class);
                //获取权利人信息
                List<QlrlistResponse> qlrlistResponseList =getQlrxx(data,zdMap);
                if(CollectionUtils.isNotEmpty(qlrlistResponseList)){
                    gzwxxResponseDTO.setQlrlist(qlrlistResponseList);
                }
                gzwxxResponseDTOList.add(gzwxxResponseDTO);
            }
            //字典信息
            setGzwZdmc(gzwxxResponseDTOList,zdMap);
        }
        return PageUtils.listToPage(gzwxxResponseDTOList,pageable);
    }

    /**
     * 分页历史土地及定着物
     * @param pageable
     * @param qlxzAndZdtzm BDCDYH 的 第13+14位
     * @param zdtzm BDCDYH 的 第14位
     * @param dzwtzm BDCDYH 的 第20位
     * @param fwlx xmxx,ljz,hs,ychs
     * @param paramMap
     * @return
     */
    @Override
    public Page<Map> listLsTdAndDzwBdcdyByPage(Pageable pageable, String qlxzAndZdtzm, String zdtzm, String dzwtzm, String fwlx, Map<String, Object> paramMap) {
        Map<String, Object> bdcdyParamMap = getLsTdDzwQueryBdcdyMap(qlxzAndZdtzm, zdtzm, dzwtzm, fwlx, paramMap);
        return listByPageWithQlr("listTdDzwBdcdyByPageOrder",pageable,bdcdyParamMap);
    }

    /**
     * 查询逻辑幢信息，如果是户室，保存逻辑幢信息
     *
     * @param bdcdyh              不动产单元号
     * @param bdcdyhSet           不动产单元号集合
     * @param bdcdyhFwDcbIndexMap 不动产单元号和逻辑幢主键对照map
     * @return 主键拼接坐落
     */
    private String getAndSaveLjzxx(String bdcdyh, HashSet bdcdyhSet, Map<String, String> bdcdyhFwDcbIndexMap) {
        StringBuilder sb = new StringBuilder();
        String id = "";
        String zl = "";
        if (StringUtils.isNotBlank(bdcdyh)) {
            FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
            // 多幢
            if (fwXmxxDO != null) {
                id = fwXmxxDO.getFwXmxxIndex();
                zl = fwXmxxDO.getZl();
            } else {
                // 独幢
                FwLjzDO fwljzDO = fwLjzService.queryLjzByBdcdyh(bdcdyh);
                if (fwljzDO != null) {
                    id = fwljzDO.getFwDcbIndex();
                    zl = fwljzDO.getZldz();
                } else {
                    // 实测户室
                    FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
                    String fwDcbIndex = "";
                    String schs = "";
                    if (fwHsDO == null) {
                        // 预测户室
                        FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
                        if (fwYchsDO != null) {
                            fwDcbIndex = fwYchsDO.getFwDcbIndex();
                            schs = "false";
                        }
                    } else {
                        fwDcbIndex = fwHsDO.getFwDcbIndex();
                        schs = "true";
                    }
                    // 查逻辑幢
                    FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
                    if (fwLjzDO != null) {
                        id = fwDcbIndex;
                        zl = fwLjzDO.getZldz();
                        //查询逻辑幢下的所有户室,保存结果
                        queryHsByFwDcbIndex(schs, fwDcbIndex, StringUtils.isBlank(fwLjzDO.getZldz()) ? "" : fwLjzDO.getZldz(), bdcdyhSet, bdcdyhFwDcbIndexMap);
                    }
                }
            }
        }
        sb.append(id).append(CommonConstantUtils.ZF_YW_DH).append(StringUtils.isBlank(zl) ? "" : zl);
        return sb.toString();
    }

    /**
     * 如果是户室，查询fwDcbIndex相同的户室信息，并保存
     *
     * @param schs                是否为实测户室
     * @param fwDcbIndex          逻辑幢主键
     * @param zldz                坐落
     * @param bdcdyhSet           不动产单元号主键
     * @param bdcdyhFwDcbIndexMap 不动产单元号和逻辑幢主键对照map
     */
    private void queryHsByFwDcbIndex(String schs, String fwDcbIndex, String zldz, HashSet bdcdyhSet, Map<String, String> bdcdyhFwDcbIndexMap) {
        //查询逻辑幢下的所有户室,保存结果
        if (StringUtils.isNotBlank(schs) && "true".equals(schs)) {
            // 实测户室
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
            if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                for (FwHsDO hsDO : fwHsDOList) {
                    bdcdyhSet.add(hsDO.getBdcdyh());
                    bdcdyhFwDcbIndexMap.put(hsDO.getBdcdyh(), fwDcbIndex + CommonConstantUtils.ZF_YW_DH + zldz);
                }
            }
        }
        if (StringUtils.isNotBlank(schs) && "false".equals(schs)) {
            // 预测户室
            List<FwYchsDO> ychsDOList = fwYcHsService.queryYchsByFwDcbIndexNoSort(fwDcbIndex);
            if (CollectionUtils.isNotEmpty(ychsDOList)) {
                for (FwYchsDO ychsDO : ychsDOList) {
                    bdcdyhSet.add(ychsDO.getBdcdyh());
                    bdcdyhFwDcbIndexMap.put(ychsDO.getBdcdyh(), fwDcbIndex + CommonConstantUtils.ZF_YW_DH + zldz);
                }
            }

        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private void dealParam(Map<String,Object> paramMap){
        Boolean cxZd =true;
        Boolean cxFw =true;
        String bdclx =MapUtils.getString(paramMap,"bdclx");
        String zl =MapUtils.getString(paramMap,"zl");
        String zlmh =MapUtils.getString(paramMap,"zlmh");
        if(StringUtils.isNotBlank(bdclx)) {
            BdclxEnum tzmEnum = BdclxEnum.valueOf(bdclx);
            if(tzmEnum != null){
                if(StringUtils.isNotBlank(tzmEnum.getDzwtzm())) {
                    if(StringUtils.equals(CommonConstantUtils.DZWTZM_FW,tzmEnum.getDzwtzm())){
                        cxZd =false;
                    }else if(StringUtils.equals(CommonConstantUtils.DZWTZM_TD,tzmEnum.getDzwtzm()) ||StringUtils.equals(CommonConstantUtils.BDCLX_TZM_L,tzmEnum.getDzwtzm())){
                        cxFw =false;
                    }
                    paramMap.put("dzwtzm", Arrays.asList(tzmEnum.getDzwtzm().split(CommonConstantUtils.ZF_YW_DH)));
                }
                if(StringUtils.isNotBlank(tzmEnum.getZdtzm())) {
                    paramMap.put("zdtzm", Arrays.asList(tzmEnum.getZdtzm().split(CommonConstantUtils.ZF_YW_DH)));
                }
            }

        }
        if(StringUtils.isNotBlank(zlmh)) {
            if (StringUtils.isBlank(zl)) {
                paramMap.put("zl", zlmh);
                paramMap.put("zlmh", CommonConstantUtils.MHLX_QMH);
            }else{
                paramMap.put("zlmh", CommonConstantUtils.MHLX_JQ);
            }
        }
        paramMap.put("cxZd",cxZd);
        paramMap.put("cxFw",cxFw);

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询不动产单元字典处理
     */
    private void setZdmc(List<WwsqBdcdyxxDTO> wwsqBdcdyxxDTOList,Map<String, List<Map>> zdMap){

        for (WwsqBdcdyxxDTO wwsqBdcdyxxDTO : wwsqBdcdyxxDTOList) {
            wwsqBdcdyxxDTO.setZdytmc(StringUtils.isNotBlank(wwsqBdcdyxxDTO.getZdyt()) ?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(wwsqBdcdyxxDTO.getZdyt()),zdMap.get("tdyt")):"");
            wwsqBdcdyxxDTO.setZdqlxzmc(StringUtils.isNotBlank(wwsqBdcdyxxDTO.getZdqlxz())?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(wwsqBdcdyxxDTO.getZdqlxz()), zdMap.get("qlxz")):"");
            wwsqBdcdyxxDTO.setFwjgmc(StringUtils.isNotBlank(wwsqBdcdyxxDTO.getFwjg())?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(wwsqBdcdyxxDTO.getFwjg()), zdMap.get("fwjg")):"");
            wwsqBdcdyxxDTO.setFwlxmc(StringUtils.isNotBlank(wwsqBdcdyxxDTO.getFwlx())?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(wwsqBdcdyxxDTO.getFwlx()), zdMap.get("fwlx")):"");
            wwsqBdcdyxxDTO.setFwxzmc(StringUtils.isNotBlank(wwsqBdcdyxxDTO.getFwxz())?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(wwsqBdcdyxxDTO.getFwxz()), zdMap.get("fwxz")):"");
            wwsqBdcdyxxDTO.setGhytmc(StringUtils.isNotBlank(wwsqBdcdyxxDTO.getGhyt())?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(wwsqBdcdyxxDTO.getGhyt()), zdMap.get("fwyt")):"");
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询宗地信息
     */
    private void setFwzdxx(List<WwsqBdcdyxxDTO> wwsqBdcdyxxDTOList){
        for(WwsqBdcdyxxDTO wwsqBdcdyxxDTO:wwsqBdcdyxxDTOList){
            if(TzmUtils.isFwBdcdy(wwsqBdcdyxxDTO.getBdcdyh())) {
                if (StringUtils.isBlank(wwsqBdcdyxxDTO.getZdqlxz()) ||StringUtils.isBlank(wwsqBdcdyxxDTO.getZdyt()) ||StringUtils.isBlank(wwsqBdcdyxxDTO.getZdzhmj()) ||StringUtils.isBlank(wwsqBdcdyxxDTO.getTdsyqssj())
                ||StringUtils.isBlank(wwsqBdcdyxxDTO.getTdsyjssj())) {
                    DjDcbResponseDTO djDcbResponseDTO = djxxService.getDjdcbDTOByBdcdyh(BuildingUtils.getDjhByBdcdyh(wwsqBdcdyxxDTO.getBdcdyh()) + Constants.TD_BDCDYH_SUFIX);
                    if(djDcbResponseDTO != null){
                        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(djDcbResponseDTO));
                        String syqlx = jsonObject.getString("syqlx");
                        String tdyt = jsonObject.getString("tdyt");
                        String fzmj = jsonObject.getString("fzmj");
                        String scmj = jsonObject.getString("scmj");
                        if(StringUtils.isBlank(wwsqBdcdyxxDTO.getZdqlxz())){
                            wwsqBdcdyxxDTO.setZdqlxzmc(syqlx);
                        }
                        if(StringUtils.isBlank(wwsqBdcdyxxDTO.getZdyt())){
                            wwsqBdcdyxxDTO.setZdyt(tdyt);
                        }
                        if(StringUtils.isBlank(wwsqBdcdyxxDTO.getZdzhmj())){
                            wwsqBdcdyxxDTO.setZdzhmj((StringUtils.isNotBlank(fzmj))?fzmj:scmj);
                        }
                        if(StringUtils.isBlank(wwsqBdcdyxxDTO.getTdsyqssj())){
                            Date qsrq =jsonObject.getDate("qsrq");
                            if(qsrq != null) {
                                wwsqBdcdyxxDTO.setTdsyqssj(DateUtils.formateYmdhms(qsrq));
                            }
                        }
                        if(StringUtils.isBlank(wwsqBdcdyxxDTO.getTdsyjssj())){
                            Date zzrq =jsonObject.getDate("zzrq");
                            if(zzrq != null) {
                                wwsqBdcdyxxDTO.setTdsyjssj(DateUtils.formateYmdhms(zzrq));
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询状态
     */
    private void setBdcdyzt(List<Map> resultData){
        for(Map map:resultData){
            if(map.get("BDCDYH") != null){
                String bdcdyh =map.get("BDCDYH").toString();
                if(StringUtils.isNotBlank(bdcdyh)) {
                    SSjBdcdyhxsztDO sSjBdcdyhxsztDO = entityMapper.selectByPrimaryKey(SSjBdcdyhxsztDO.class, bdcdyh);
                    /**
                     * 互联网状态值：0是1否
                     */
                    if (sSjBdcdyhxsztDO != null) {
                        if (sSjBdcdyhxsztDO.getXsdyacs() != null && sSjBdcdyhxsztDO.getXsdyacs() > 0) {
                            map.put("sfdy", CommonConstantUtils.SF_F_DM.toString());
                        } else {
                            map.put("sfdy", CommonConstantUtils.SF_S_DM.toString());
                        }
                        if (sSjBdcdyhxsztDO.getXscfcs() != null && sSjBdcdyhxsztDO.getXscfcs() > 0) {
                            map.put("sfcf", CommonConstantUtils.SF_F_DM.toString());
                        } else {
                            map.put("sfcf", CommonConstantUtils.SF_S_DM.toString());
                        }
                    }
                }
            }
        }
    }


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  设置权利人信息
     */
    private List<QlrlistResponse> getQlrxx(Map<String,String> data,Map<String, List<Map>> zdMap){
        //获取权利人信息
        List<QlrlistResponse> qlrlistResponseList =acceptBdcdyMapper.queryQjQlr(data);
        if(CollectionUtils.isNotEmpty(qlrlistResponseList)){
            for(QlrlistResponse qlrlistResponse:qlrlistResponseList){
                qlrlistResponse.setGyfsmc(StringUtils.isNotBlank(qlrlistResponse.getGyfs()) ?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(qlrlistResponse.getGyfs()), zdMap.get("gyfs")):"");
                qlrlistResponse.setQlrsfzjzlmc(StringUtils.isNotBlank(qlrlistResponse.getQlrsfzjzl()) ?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(qlrlistResponse.getQlrsfzjzl()), zdMap.get("zjzl")):"");
                qlrlistResponse.setQlrfddbrzjzlmc(StringUtils.isNotBlank(qlrlistResponse.getQlrfddbrzjzl()) ?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(qlrlistResponse.getQlrfddbrzjzl()), zdMap.get("zjzl")):"");
            }
        }
        return qlrlistResponseList;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询构筑物字典处理
     */
    private void setGzwZdmc(List<GzwxxResponseDTO> gzwxxResponseDTOList,Map<String, List<Map>> zdMap){

        for (GzwxxResponseDTO gzwxxResponseDTO : gzwxxResponseDTOList) {
            gzwxxResponseDTO.setGzwlxmc(StringUtils.isNotBlank(gzwxxResponseDTO.getGzwlxdm()) ?StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(gzwxxResponseDTO.getGzwlxdm()),zdMap.get("gjzwlx")):"");

        }
    }



}
