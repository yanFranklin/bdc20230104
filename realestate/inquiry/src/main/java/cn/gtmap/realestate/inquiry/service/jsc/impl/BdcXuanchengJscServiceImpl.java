package cn.gtmap.realestate.inquiry.service.jsc.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.jsc.*;
import cn.gtmap.realestate.common.core.enums.SummaryDimension;
import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.CommonMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcJscMapper;
import cn.gtmap.realestate.inquiry.service.jsc.BdcXuanchengJscService;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.inquiry.service.jsc.impl.TendencyPadding.DEFAULTQX;

/**
 * 宣城驾驶舱
 */
@Service
public class BdcXuanchengJscServiceImpl implements BdcXuanchengJscService {
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    BdcJscMapper bdcJscMapper;

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;

    @Value("${jsc.randomata:false}")
    private Boolean randomdata;

    public static final String REDIS_KEY_PREFIX = "XuanchengJsc_";

    /**
     * 林权登记总面积
     *
     * @param jscCommomQO
     * @return
     */
    @Override
    public List<Integer> queryJscDjZmj(JscCommomQO jscCommomQO) {
        List<String> qxdmList = getCurrentQxList();
        //处理查询时间条件
        TendencyPadding.paddingQueryTime(jscCommomQO);
        //查询登记总面积
        Integer mj = bdcJscMapper.queryDjMj(
                jscCommomQO.getStartTime(),
                jscCommomQO.getEndTime()
        );

        //登记总件数
        Integer sl = bdcJscMapper.queryDjSl(jscCommomQO.getStartTime(),
                jscCommomQO.getEndTime());

        List<Map> ldmjAndZd = acceptBdcdyFeignService.getLdmjAndZd(StringUtils.join(qxdmList,","));

        //汇总所有的林地面积和宗地数量
        Double ldmj = 0.0;
        Integer zdslTotal = 0;
        for (Map map : ldmjAndZd) {
            String scmj = MapUtils.getString(map, "SCMJ");
            String dkscmj = MapUtils.getString(map, "DKSCMJ");
            String zdsl = MapUtils.getString(map, "ZDSL");
            if(StringUtils.isNotEmpty(scmj)){
                ldmj += Double.parseDouble(scmj);
            }
            if(StringUtils.isNotEmpty(dkscmj)){
                ldmj += Double.parseDouble(dkscmj);
            }
            if(StringUtils.isNotEmpty(zdsl)){
                zdslTotal += Integer.parseInt(zdsl);
            }
        }

        if(randomdata){
            Random random = new Random();
            if(sl <= 0){
                sl = random.nextInt(10000000);
            }
            if(mj <= 0){
                mj = random.nextInt(100000000);
            }
            if(ldmj <= 0){
                ldmj = random.nextDouble();
            }
            if(zdslTotal <= 0){
                zdslTotal = random.nextInt(1000000000);
            }
        }
        return Arrays.asList(sl, mj,ldmj.intValue(),zdslTotal);
    }


    /**
     * 登记数量图表汇总
     *
     * @param jscCommomQO
     * @return
     */
    @Override
    public Map<String, List<JscDjslDTO>> queryJscDjslList(JscCommomQO jscCommomQO) {
        //处理查询时间条件
        TendencyPadding.paddingQueryTime(jscCommomQO);
        //查询原始数据
        List<JscDjslDTO> jscDjslDTOS = new ArrayList<>();
        jscDjslDTOS = getSjslData(jscCommomQO);

        Map<String, List<JscDjslDTO>> djslMap = jscDjslDTOS
                .stream()
                .filter(jscDjslDTO -> Objects.nonNull(jscDjslDTO.getTendencyDate()))
                .collect(Collectors.groupingBy(JscDjslDTO::getTendencyDate));

        //补全空缺数据
        TendencyPadding.paddingResultDate(djslMap, jscCommomQO);

        Set<String> keys = djslMap.keySet();
        List<String> sordedKeys = keys.stream().sorted().collect(Collectors.toList());
        //对健排序
        //排序
        Map<String, List<JscDjslDTO>> linkedHashMap = new LinkedHashMap();
        for (String sordedKey : sordedKeys) {
            linkedHashMap.put(sordedKey, djslMap.get(sordedKey));
        }

        return linkedHashMap;
    }

    private List<JscDjslDTO> getSjslData(JscCommomQO jscCommomQO) {
        List<JscDjslDTO> jscDjslDTOS = new ArrayList<>();
        jscDjslDTOS.addAll(bdcJscMapper.queryJscDjslDy(
                jscCommomQO.getStartTime(),
                jscCommomQO.getEndTime(),
                jscCommomQO.getSummaryDimension()
        ));
        jscDjslDTOS.addAll(bdcJscMapper.queryJscDjslCf(
                jscCommomQO.getStartTime(),
                jscCommomQO.getEndTime(),
                jscCommomQO.getSummaryDimension()
        ));
        jscDjslDTOS.addAll(bdcJscMapper.queryJscDjslDj(
                jscCommomQO.getStartTime(),
                jscCommomQO.getEndTime(),
                jscCommomQO.getSummaryDimension()
        ));
        return jscDjslDTOS;
    }

    /**
     * 驾驶舱 概况
     *
     * @param jscCommomQO@return
     */
    @Override
    public List<Integer> queryJscSummary(JscCommomQO jscCommomQO) {
        String rediskey = REDIS_KEY_PREFIX + "Summary" + jscCommomQO.getTimeFrame();
        String stringValue = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(stringValue)) {
            return JSON.parseArray(stringValue, Integer.class);
        }
        TendencyPadding.paddingQueryTime(jscCommomQO);
        //查询原始数据
        Integer yb = bdcJscMapper.queryJscSummaryYb(jscCommomQO.getStartTime(), jscCommomQO.getEndTime());
        Integer blz = bdcJscMapper.queryJscSummaryBlz(jscCommomQO.getStartTime(), jscCommomQO.getEndTime());
        Integer wtj = bdcJscMapper.queryJscSummaryBlLsyl(jscCommomQO.getStartTime(), jscCommomQO.getEndTime());
        List<Integer> data = new ArrayList<>();
        data.add(yb);
        data.add(blz);
        data.add(wtj);
        redisUtils.addStringValue(rediskey, JSON.toJSONString(data), 3600);
        return data;
    }

    /**
     * 驾驶舱 林权权利
     *
     * @param jscCommomQO
     * @return
     */
    @Override
    public Map<String, List<JscLqqlDTO>> queryJscQl(JscCommomQO jscCommomQO) {
        //处理查询时间条件
        TendencyPadding.paddingQueryTime(jscCommomQO);
        //查询原始数据
        //如果查询新的广德县的区县数据，则将老的区县数据也加上
        if (CollectionUtils.isNotEmpty(jscCommomQO.getQxdmList()) &&
                jscCommomQO.getQxdmList().contains("341882")) {
            jscCommomQO.getQxdmList().add("341822");
        }
        List<JscLqqlDTO> jscLqqlDTOS = bdcJscMapper.queryJscQl(
                jscCommomQO.getStartTime(),
                jscCommomQO.getEndTime(),
                jscCommomQO.getQxdmList()
        );
        //设置区县代码
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        for (JscLqqlDTO jscLqqlDTO : jscLqqlDTOS) {
            jscLqqlDTO.setQxdm(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(jscLqqlDTO.getQxdm()), zdMap.get("qx")));
            jscLqqlDTO.setType(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(jscLqqlDTO.getType()), zdMap.get("qllx")));
        }
        Map<String, List<JscLqqlDTO>> djslMap = jscLqqlDTOS
                .stream().collect(Collectors.groupingBy(JscLqqlDTO::getQxdm));

        TendencyPadding.paddingResultQx(djslMap, jscCommomQO);
        return djslMap;
    }

    /**
     * 驾驶舱 登记趋势
     *
     * @param jscCommomQO@return
     */
    @Override
    public JscLqqsDTO queryJscTrend(JscCommomQO jscCommomQO) {
        jscCommomQO.setSummaryDimension(SummaryDimension.MONTH.getCode());
        jscCommomQO.setTimeFrame(TendencyPadding.YEAR);
        Map<String, List<JscDjslDTO>> stringListMap = queryJscDjslList(jscCommomQO);
        //处理数据格式
        JscLqqsDTO jscLqqsDTO = new JscLqqsDTO();
        for (Map.Entry<String, List<JscDjslDTO>> stringListEntry : stringListMap.entrySet()) {
            int total = stringListEntry.getValue().stream().mapToInt(JscDjslDTO::getNum).sum();
            jscLqqsDTO.getTitle().add(stringListEntry.getKey());
            jscLqqsDTO.getTotal().add(String.valueOf(total));
        }

        //处理新增减少
        if (CollectionUtil.isNotEmpty(jscLqqsDTO.getTotal())) {
            for (int i = 0; i < jscLqqsDTO.getTotal().size(); i++) {
                String total = jscLqqsDTO.getTotal().get(i);
                int totalInt = Integer.parseInt(total);
                //第一个永远是新增
                if (CollectionUtil.isEmpty(jscLqqsDTO.getAdd())) {
                    jscLqqsDTO.getAdd().add(total);
                    jscLqqsDTO.getDe().add("-");
                    continue;
                }
                //计算对比上次是新增还是减少
                Integer prevTotal = Integer.parseInt(jscLqqsDTO.getTotal().get(i - 1));

                //减少
                if (prevTotal > totalInt) {
                    jscLqqsDTO.getAdd().add("-");
                    jscLqqsDTO.getDe().add(String.valueOf(prevTotal - totalInt));
                    continue;
                }
                //新增或持平
                if (totalInt >= prevTotal) {
                    jscLqqsDTO.getAdd().add(String.valueOf(totalInt - prevTotal));
                    jscLqqsDTO.getDe().add("-");
                }
            }
            List<String> total = new ArrayList<>();
            for (int i = 0; i < jscLqqsDTO.getTotal().size(); i++) {
                if(i == 0){
                    total.add("0");
                    continue;
                }
                Integer currentNum = Integer.parseInt(total.get(i - 1));
                if(!jscLqqsDTO.getAdd().get(i - 1).equals("-")){
                    currentNum += Integer.parseInt(jscLqqsDTO.getAdd().get(i - 1));
                }
                if(!jscLqqsDTO.getDe().get(i).equals("-")){
                    currentNum -= Integer.parseInt(jscLqqsDTO.getDe().get(i));
                }
                total.add(currentNum.toString());
            }
            jscLqqsDTO.setTotal(total);
        }
        return jscLqqsDTO;
    }

    /**
     * 驾驶舱 交易情况（转移登记）
     *
     * @param jscCommomQO@return
     */
    @Override
    public List<JscLqjyDTO> queryJscTransaction(JscCommomQO jscCommomQO) {
        TendencyPadding.paddingQueryTime(jscCommomQO);
        if (CollectionUtils.isNotEmpty(jscCommomQO.getQxdmList()) &&
                jscCommomQO.getQxdmList().contains("341882")) {
            jscCommomQO.getQxdmList().add("341822");
        }
        List<JscLqjyDTO> jscLqjyDTOS = bdcJscMapper.queryJscJyqk(
                jscCommomQO.getStartTime(),
                jscCommomQO.getEndTime(),
                jscCommomQO.getQxdmList(),
                jscCommomQO.getDjlx()
        );
        jscLqjyDTOS = jscLqjyDTOS.stream().filter(jscLqjyDTO -> Objects.nonNull(jscLqjyDTO.getQxdm())).collect(Collectors.toList());
        //设置区县代码
        List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
        for (JscLqjyDTO jscLqjyDTO : jscLqjyDTOS) {
            jscLqjyDTO.setQxdm(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(jscLqjyDTO.getQxdm()), qxZdmap));
        }
        return jscLqjyDTOS;
    }

    /**
     * 登记数量图表汇总
     *
     * @param jscCommomQO@return
     */
    @Override
    public Map<String, JscLqjyDTO> queryJscDjslMap(JscCommomQO jscCommomQO) {
        //查询原始数据
        List<String> qxdmList = getCurrentQxList();
        List<JscLqjyDTO> jscQxjysls = bdcJscMapper.queryQXDjSl();
        List<Map> ldmjAndZd = acceptBdcdyFeignService.getLdmjAndZd(StringUtils.join(qxdmList,","));
        List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");

        //将数据填充到固定类型中
        for (Map map : ldmjAndZd) {
            boolean findMatch = false;
            String qxdm = MapUtils.getString(map, "QXDM");
            Double scmj = MapUtils.getDouble(map, "SCMJ");
            Double dkscmj = MapUtils.getDouble(map, "DKSCMJ");
            Integer zdsl = MapUtils.getInteger(map, "ZDSL");
            for (JscLqjyDTO jscQxjysl : jscQxjysls) {
                if(Objects.nonNull(qxdm) &&
                        Objects.nonNull(jscQxjysl.getQxdm())
                        && jscQxjysl.getQxdm().equals(qxdm)){
                    findMatch = true;
                    if (Objects.nonNull(dkscmj)){
                        jscQxjysl.setLdmj(dkscmj.intValue());

                    }
                    if (Objects.nonNull(scmj)){
                        jscQxjysl.setLdmj(jscQxjysl.getLdmj() + scmj.intValue());
                    }
                    jscQxjysl.setZds(zdsl);
                }
            }
            if(!findMatch){
                JscLqjyDTO jscQxjysl = new JscLqjyDTO();
                jscQxjysl.setQxdm(qxdm);
                jscQxjysl.setMj(0);
                jscQxjysl.setJysl(0);
                jscQxjysl.setLdmj(dkscmj.intValue() + scmj.intValue());
                jscQxjysl.setZds(zdsl);
            }
        }

        for (JscLqjyDTO jscLqjyDTO : jscQxjysls) {
            if(StringUtils.isNotEmpty(jscLqjyDTO.getQxdm())) {
                jscLqjyDTO.setQxdm(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(jscLqjyDTO.getQxdm()), qxZdmap));
            }
        }
        Map<String, JscLqjyDTO> jscQxjyslMap = jscQxjysls
                .stream().filter(jscLqjyDTO -> Objects.nonNull(jscLqjyDTO.getQxdm()))
                .collect(Collectors.toMap(JscLqjyDTO::getQxdm, o->o));
        //补全空缺数据
        for (String qx : DEFAULTQX) {
            if(!jscQxjyslMap.containsKey(qx)){
                if(randomdata){
                    Random random = new Random();
                    JscLqjyDTO jscLqjyDTO = new JscLqjyDTO();
                    jscLqjyDTO.setQxdm(qx);
                    jscLqjyDTO.setMj(random.nextInt(100));
                    jscLqjyDTO.setJysl(random.nextInt(100));
                    jscLqjyDTO.setLdmj(random.nextInt(100));
                    jscLqjyDTO.setZds(random.nextInt(100));
                    jscQxjyslMap.put(qx,jscLqjyDTO);
                }else {
                    JscLqjyDTO jscLqjyDTO = new JscLqjyDTO();
                    jscLqjyDTO.setQxdm(qx);
                    jscLqjyDTO.setMj(0);
                    jscLqjyDTO.setJysl(0);
                    jscLqjyDTO.setLdmj(0);
                    jscLqjyDTO.setZds(0);
                    jscQxjyslMap.put(qx,jscLqjyDTO);
                }
            }
        }
        //TendencyPadding.paddingResultQxNum(jscQxjyslMap,jscCommomQO);

        return jscQxjyslMap;
    }

    /**
     * 历史遗留问题柱图
     *
     * @param jscCommomQO@return
     */
    @Override
    public Map<String,List<JscLsylwtDTO>> queryLsytwt(JscCommomQO jscCommomQO) {
        List<String> qxdmList = getCurrentQxList();
        List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
        Map<String,List<JscLsylwtDTO>> result = new HashMap<>();
        //按照区县，状态，类型汇总的数据
        List<JscLsylwtDTO> querylsylsj = bdcJscMapper.querylsylsj(qxdmList);
        //汇总总数
        Map<String, List<JscLsylwtDTO>> qxsj = querylsylsj
                .stream().collect(Collectors.groupingBy(JscLsylwtDTO::getQxdm));

        //总数
        for (Map.Entry<String, List<JscLsylwtDTO>> stringListEntry : qxsj.entrySet()) {
            int totalNum = stringListEntry.getValue().stream().mapToInt(JscLsylwtDTO::getWtsl).sum();
            List<JscLsylwtDTO> data = new ArrayList<>();
            JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
            jscLsylwtDTO.setWtsl(totalNum);
            jscLsylwtDTO.setLsylwtlx("问题总量");
            jscLsylwtDTO.setQxdm(stringListEntry.getKey());
            data.add(jscLsylwtDTO);
            result.put(stringListEntry.getKey(),data);
        }

        //解决数量，未解决数量
        for (Map.Entry<String, List<JscLsylwtDTO>> stringListEntry : result.entrySet()) {
            int wjjsl = qxsj.get(stringListEntry.getKey())
                    .stream().filter(jscLsylwtDTO -> jscLsylwtDTO.getWtsjzt() == 1)
                    .mapToInt(JscLsylwtDTO::getWtsl)
                    .sum();

            int yjjsl = qxsj.get(stringListEntry.getKey())
                    .stream().filter(jscLsylwtDTO -> jscLsylwtDTO.getWtsjzt() == 2)
                    .mapToInt(JscLsylwtDTO::getWtsl)
                    .sum();
            //解决数量
            JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
            jscLsylwtDTO.setQxdm(stringListEntry.getKey());
            jscLsylwtDTO.setWtsjzt(1);
            jscLsylwtDTO.setWtsl(wjjsl);
            jscLsylwtDTO.setLsylwtlx("未解决总量");
            stringListEntry.getValue().add(jscLsylwtDTO);
            //已解决
            JscLsylwtDTO jscLsylwtYjjDTO = new JscLsylwtDTO();
            jscLsylwtYjjDTO.setQxdm(stringListEntry.getKey());
            jscLsylwtYjjDTO.setWtsjzt(1);
            jscLsylwtYjjDTO.setWtsl(yjjsl);
            jscLsylwtYjjDTO.setLsylwtlx("解决总量");
            stringListEntry.getValue().add(jscLsylwtYjjDTO);
        }

        //填充没有数据的区县
        for (String s : qxdmList) {
            if (CollectionUtils.isEmpty(result.get(s))) {
                if(randomdata){
                    List<JscLsylwtDTO> objects = new ArrayList<>();
                    Random random = new Random();
                    //总是
                    JscLsylwtDTO jscLsylwtTotalDTO = new JscLsylwtDTO();
                    jscLsylwtTotalDTO.setQxdm(s);
                    jscLsylwtTotalDTO.setWtsjzt(1);
                    jscLsylwtTotalDTO.setWtsl(random.nextInt(100));
                    jscLsylwtTotalDTO.setLsylwtlx("问题总量");
                    objects.add(jscLsylwtTotalDTO);

                    //解决数量
                    JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
                    jscLsylwtDTO.setQxdm(s);
                    jscLsylwtDTO.setWtsjzt(1);
                    jscLsylwtDTO.setWtsl(random.nextInt(100));
                    jscLsylwtDTO.setLsylwtlx("未解决总量");
                    objects.add(jscLsylwtDTO);
                    //已解决
                    JscLsylwtDTO jscLsylwtYjjDTO = new JscLsylwtDTO();
                    jscLsylwtYjjDTO.setQxdm(s);
                    jscLsylwtYjjDTO.setWtsjzt(1);
                    jscLsylwtYjjDTO.setWtsl(random.nextInt(100));
                    jscLsylwtYjjDTO.setLsylwtlx("解决总量");
                    objects.add(jscLsylwtYjjDTO);
                    result.put(s,objects);
                }else {
                    result.put(s,new ArrayList<>());
                }
            }
        }

        Map<String,List<JscLsylwtDTO>> resultQx = new HashMap<>();
        //设置区县代码
        for (Map.Entry<String, List<JscLsylwtDTO>> stringListEntry : result.entrySet()) {
            String qxmc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(stringListEntry.getKey()), qxZdmap);
            if(StringUtils.isNotEmpty(qxmc)){
                resultQx.put(qxmc,stringListEntry.getValue());
            }
        }

        return resultQx;
    }

    /**
     * 未解决问题概况区县占比图
     *
     * @param jscCommomQO@return
     */
    @Override
    public List<JscLsylwtDTO> queryLsylwtWjjQxzb(JscCommomQO jscCommomQO) {
        List<JscLsylwtDTO> result = new ArrayList<>();
        List<String> qxdmList = getCurrentQxList();
        List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
        //按照区县，状态，类型汇总的数据
        List<JscLsylwtDTO> querylsylsj = bdcJscMapper.querylsylsj(qxdmList);
        //筛选出未解决的数据后按照区县进行汇总
        Map<String, List<JscLsylwtDTO>> lysj = querylsylsj.stream().filter(jscLsylwtDTO -> jscLsylwtDTO.getWtsjzt().equals(1))
                .collect(Collectors.groupingBy(JscLsylwtDTO::getQxdm));
        for (Map.Entry<String, List<JscLsylwtDTO>> stringListEntry : lysj.entrySet()) {
            int wtsl = stringListEntry.getValue().stream().mapToInt(JscLsylwtDTO::getWtsl).sum();
            JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
            jscLsylwtDTO.setQxdm(stringListEntry.getKey());
            jscLsylwtDTO.setWtsl(wtsl);
            result.add(jscLsylwtDTO);
        }
        //补全数据
        for (String s : qxdmList) {
            if(!lysj.containsKey(s)){
                if(randomdata){
                    Random random = new Random();
                    JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
                    jscLsylwtDTO.setQxdm(s);
                    jscLsylwtDTO.setWtsl(random.nextInt(100));
                    result.add(jscLsylwtDTO);
                }else {
                    JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
                    jscLsylwtDTO.setQxdm(s);
                    jscLsylwtDTO.setWtsl(0);
                    result.add(jscLsylwtDTO);
                }
            }
        }
        for (JscLsylwtDTO jscLsylwtDTO : result) {
            if(StringUtils.isNotEmpty(jscLsylwtDTO.getQxdm())) {
                jscLsylwtDTO.setQxdm(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(jscLsylwtDTO.getQxdm()), qxZdmap));
            }
        }

        return result;
    }

    /**
     * 区县中未解决问题柱图（要按照问题类型进行补充）
     *
     * @param jscCommomQO@return
     */
    @Override
    public List<JscLsylwtDTO> queryLsylwtQxWtlxzb(JscCommomQO jscCommomQO) {
        List<JscLsylwtDTO> result = new ArrayList<>();
        List<Map> lsylwtlx = bdcZdFeignService.queryBdcZd("lsylwtlx");
        //按照区县，状态，类型汇总的数据--只会有一个区县
        List<JscLsylwtDTO> querylsylsj = bdcJscMapper.querylsylsj(jscCommomQO.getQxdmList());
        //按照问题类型进行汇总
        Map<String, List<JscLsylwtDTO>> lsyl = querylsylsj.stream()
                .collect(Collectors.groupingBy(JscLsylwtDTO::getLsylwtlx));
        for (Map.Entry<String, List<JscLsylwtDTO>> stringListEntry : lsyl.entrySet()) {
            int wtsl = stringListEntry.getValue().stream().mapToInt(JscLsylwtDTO::getWtsl).sum();
            JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
            jscLsylwtDTO.setLsylwtlx(stringListEntry.getKey());
            jscLsylwtDTO.setWtsl(wtsl);
            result.add(jscLsylwtDTO);
        }

        //填充没有的问题类型数据
        for (Map map : lsylwtlx) {
            int dm = MapUtils.getInteger(map, "DM");
            if (!lsyl.containsKey(dm)){
                if(randomdata){
                    Random random = new Random();
                    JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
                    jscLsylwtDTO.setLsylwtlx(dm + "");
                    jscLsylwtDTO.setWtsl(random.nextInt(100));
                    result.add(jscLsylwtDTO);
                }else {
                    JscLsylwtDTO jscLsylwtDTO = new JscLsylwtDTO();
                    jscLsylwtDTO.setLsylwtlx(dm + "");
                    jscLsylwtDTO.setWtsl(0);
                    result.add(jscLsylwtDTO);
                }
            }
        }
        for (JscLsylwtDTO jscLsylwtDTO : result) {
            if(StringUtils.isNotEmpty(jscLsylwtDTO.getLsylwtlx())) {
                jscLsylwtDTO.setLsylwtlx(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(jscLsylwtDTO.getLsylwtlx()), lsylwtlx));
            }
        }
        return result;
    }



    private List<String> getCurrentQxList(){
        List<String> qxdmList = new ArrayList<>();
        List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
        for (Map map : qxZdmap) {
            String dm = MapUtils.getString(map, "DM");
            if (StringUtils.isNotEmpty(dm)){
                qxdmList.add(dm);
            }
        }
        return qxdmList;
    }

}
