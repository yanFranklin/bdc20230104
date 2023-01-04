package cn.gtmap.realestate.accept.quartz;


import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.core.service.BdcYjdhSfxxGxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2020/12/24
 * @description 受理定时任务
 */
@Component
@EnableScheduling
public class AcceptQuartzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptQuartzService.class);


    private static final Pattern pattern = Pattern.compile("\\d+");

    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcYjdhSfxxGxService bdcYjdhSfxxGxService;
    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;


    /**
     * 提送缴库定时任务是否开启
     */
    @Value("${quartz.tsjk.enable:false}")
    private boolean tsjkEnable;

    /**
     * 验证月结银行缴库状态定时任务是否开启
     */
    @Value("${quartz.yzyjyhzt.enable:false}")
    private boolean yzyjyhztEnable;

    /**
     * 月结校验时间范围配置
     */
    @Value("${quartz.yzyjyhzt.time:}")
    private String yzyjyhztTime;

    /**
     * 定时查询已缴费未缴库数据进行缴库操作
     * <p>查询受理库的<code>bdc_sl_sfxx</code>表，存在 hj 不为 0，
     * 且 sfzt=2，jkfs=2，yhjkrkzt 为空的数据，去执行缴库操作</p>
     */
    @Scheduled(cron = "${quartz.tsjk.cron:0 0 22 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_TSJK_JOB_NAME, description = "推送缴库定时任务", waitTime = 30L, leaseTime = 600L)
    public void BdcTsJkQuartzTask(){
        if(tsjkEnable){
            LOGGER.info("执行定时缴库任务。");
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryYjfAndWjkSfxx();
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                Map<String, String> yjdhMap = new HashMap<>(8);
                for(BdcSlSfxxDO bdcSlSfxxDO:bdcSlSfxxDOList){
                    if(CommonConstantUtils.SF_S_DM.equals(bdcSlSfxxDO.getSfyj())){
                        // 月结数据缴库
                        String yjdh = this.bdcYjdhSfxxGxService.queryYjfYjdh(bdcSlSfxxDO.getSfxxid());
                        if(StringUtils.isNotBlank(yjdh) && !yjdhMap.containsKey(yjdh)){
                            yjdhMap.put(yjdh,yjdh);
                            this.getSysAndJkrk(String.valueOf(CommonConstantUtils.SF_S_DM), "", yjdh);
                        }
                    }else{
                        // 缴费数据缴库
                        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfxxDO.getGzlslid());
                        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                            this.getSysAndJkrk(String.valueOf(CommonConstantUtils.SF_F_DM), bdcXmDTOList.get(0).getXmid(), bdcXmDTOList.get(0).getSlbh());
                        }
                    }
                }

            }
        }
    }

    private void getSysAndJkrk(String sfyj, String xmid, String slbh){
        Map<String, String> paramMap = new HashMap<>(4);
        try{
            paramMap.put("sfyj", sfyj);
            paramMap.put("xmid", xmid);
            paramMap.put("slbh", slbh);
            exchangeInterfaceFeignService.requestInterface("getSysAndJkrk", paramMap);
        }catch(Exception e){
            LOGGER.error("定时缴库失败。请求参数：{}; 异常信息：{};", paramMap.toString(), e.getMessage());
        }
    }

    /**
     * 定时检测未缴库入库的银行机构，进行黑名单标记
     */
    @Scheduled(cron = "${quartz.yzyjyhzt.cron:0 0 22 15 * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_YZYHZT_JOB_NAME, description = "验证月结银行缴库状态定时任务", waitTime = 30L, leaseTime = 600L)
    public void YzYjYhZtQuartzTask(){
        if(yzyjyhztEnable && StringUtils.isNotBlank(yzyjyhztTime)){
            LinkedList<String> timeConfig = this.matchNumber(yzyjyhztTime);
            if(timeConfig.size() == 4){
                Pair<String, String> timePair = this.resolveTime(timeConfig);
                BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
                bdcSlSfxxQO.setKssj(timePair.getLeft());
                bdcSlSfxxQO.setJzsj(timePair.getRight());
                List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryNotJkrkSfxx(bdcSlSfxxQO);
                if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                    List<String> yhmcList = bdcSlSfxxDOList.stream().map(BdcSlSfxxDO::getJfrxm).distinct().collect(Collectors.toList());
                    this.bdcXtJgFeignService.batchModifyXtJgSfky(yhmcList, CommonConstantUtils.SF_F_DM);
                }
            }
        }
    }


    /**
     * 银行黑名单自动解除任务是否开启
     */
    @Value("${quartz.yhhmdjc.enable:false}")
    private boolean yhhmdjcEnable;

    /**
     * 银行黑名单自动解除任务
     * <p>每天获取 {@code BDC_XT_JG} 中的黑名单的银行数据，根据{@code yzyjyhztTime}月结校验时间范围配置判断是否全部缴库入库。
     * 已完成缴库入库则解除黑名单</p>
     */
    @Scheduled(cron = "${quartz.yhhmdjc.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_YHHMDJC_JOB_NAME, description = "银行黑名单解除", waitTime = 1L, leaseTime = 600L)
    public void YjYhHmdJcQuartzTask(){
        if(yhhmdjcEnable){
            // 获取黑名单银行
            BdcXtJgDO bdcXtJgDO = new BdcXtJgDO();
            bdcXtJgDO.setSfky(String.valueOf(CommonConstantUtils.SF_F_DM));
            List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.queryBdcXtJg(bdcXtJgDO);
            if(CollectionUtils.isNotEmpty(bdcXtJgDOList)){
                LinkedList<String> timeConfig = this.matchNumber(yzyjyhztTime);
                if(timeConfig.size() == 4){
                    Pair<String, String> timePair = this.resolveTime(timeConfig);
                    BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
                    bdcSlSfxxQO.setKssj(timePair.getLeft());
                    bdcSlSfxxQO.setJzsj(timePair.getRight());
                    for(BdcXtJgDO xtJgDO : bdcXtJgDOList){
                        bdcSlSfxxQO.setJfrxm(xtJgDO.getJgmc());
                        // 查询该银行在时间段内，是否存在未缴库的数据
                        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryNotJkrkSfxx(bdcSlSfxxQO);
                        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
                            // 银行的月结数据中银行缴库入库状态均为1，则自动将黑名单解除
                            List<String> yhmcList = new ArrayList<>(1);
                            yhmcList.add(xtJgDO.getJgmc());
                            this.bdcXtJgFeignService.batchModifyXtJgSfky(yhmcList, CommonConstantUtils.SF_S_DM);
                        }
                    }
                }
            }
        }
    }

    /**
     * 返回值：Pair<KSSJ, JZSJ> 开始时间，截止时间
     * 0/1~0/30  -1/15~0/15
     */
    private Pair<String, String> resolveTime(LinkedList<String> timeConfig){
        int kssjMonth = Integer.parseInt(timeConfig.get(0)) == 0 ? 1 : 2;
        int jzsjMonth = Integer.parseInt(timeConfig.get(2)) == 0 ? 1 : 2;
        int kssjDay = Integer.parseInt(timeConfig.get(1));
        int jzsjDay = Integer.parseInt(timeConfig.get(3));

        LocalDate today = LocalDate.now();
        LocalDate kssj = today.minusMonths(kssjMonth);
        LocalDate kssjLastDay = kssj.with(TemporalAdjusters.lastDayOfMonth());
        if(kssjLastDay.getDayOfMonth() < kssjDay){
            kssj= kssj.withDayOfMonth(kssjLastDay.getDayOfMonth());
        }else{
            kssj = kssj.withDayOfMonth(kssjDay);
        }
        LocalDate jzsj = today.minusMonths(jzsjMonth);
        LocalDate jzsjLastDay = jzsj.with(TemporalAdjusters.lastDayOfMonth());
        if(jzsjLastDay.getDayOfMonth() < jzsjDay){
            jzsj = jzsj.withDayOfMonth(kssjLastDay.getDayOfMonth());
        }else{
            jzsj = jzsj.withDayOfMonth(jzsjDay);
        }
        return Pair.of(kssj.toString(), jzsj.toString());
    }

    /**
     * 解析时间配置
     */
    private LinkedList<String> matchNumber(String timeConfig){
        LinkedList<String> numbers = new LinkedList<String>();
//        Pattern p = Pattern.compile("\\d+");
        Matcher m = pattern.matcher(timeConfig);
        while (m.find()) {
            numbers.add(m.group());
        }
        return numbers;
    }

}


