package cn.gtmap.realestate.certificate.quartz;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.ECertificateClService;
import cn.gtmap.realestate.certificate.service.ECertificateService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDzzzCzFeginService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/3/24
 * @description 电子证照异步补偿机制
 */
@Component
@EnableScheduling
public class ECertificateQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ECertificateQuartzService.class);
    @Autowired
    private ECertificateService eCertificateService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcDzzzCzFeginService bdcDzzzCzFeginService;
    @Autowired
    private ECertificateClService eCertificateClService;

    /**
     * 是否开启生成电子证照定时任务
     */
    @Value("${eCertificate.sync.create:false}")
    private boolean syncCreate;

    /**
     * 是否开启注销电子证照定时任务
     */
    @Value("${eCertificate.sync.cancel:false}")
    private boolean syncCancel;

    /**
     * 是否开始同步电子证照信息定时任务，默认为false
     */
    @Value("${eCertificate.sync.dzzz:false}")
    private boolean syncDzzz;

    /**
     * 是否开启生成电子证照定时任务(合肥)
     */
    @Value("${eCertificate.hfbcsj.zz.flag:false}")
    private boolean hfSyncZz;

    /**
     * 是否开启生成存量电子证照定时任务(合肥)
     */
    @Value("${eCertificate.hfbcsj.clzz.flag:false}")
    private boolean hfSyncClZz;

    /**
     * 生成存量电子证照证书膳证的结束时间，格式为yyyy-MM-dd(合肥)
     */
    @Value("${eCertificate.hfbcsj.clzz.endtime:}")
    private String hfSyncClZzEndtime;

    /**
     * 生成存量电子证照证书每次生成的证书数量(合肥)
     */
    @Value("${eCertificate.hfbcsj.clzz.num:1000}")
    private Integer hfSyncClZzNum;

    /**
     * 是否开启注销电子证照定时任务(合肥)
     */
    @Value("${eCertificate.hfbcsj.zx.flag:false}")
    private boolean hfSyncZx;

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 异步电子证照生成补偿事件
     * @Scheduled 被该注解修饰的方法是没有返回值的.
     * 定时默认在晚20点执行
     */
    @Scheduled(cron = "${eCertificate.cron:0 0 20 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_YBSCDZZZ_JOB_NAME, description = "异步电子证照生成补偿事件定时任务", waitTime = 60L, leaseTime = 600L)
    public void syncCreateECertificate() {
        Date currentDate = new Date();
        LOGGER.warn("################################################开启电子证照补偿任务，当前时间：{}", currentDate);
        if (syncCreate) {
            String datePattern = "yyyyMMdd";

            String currentUserName = userManagerUtils.getCurrentUserName();
            if (StringUtils.isBlank(currentUserName)) {
                currentUserName = "证照补偿事件";
            }

            // 查询当日现势的项目信息
            List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXmByDjsj(currentDate, datePattern, CommonConstantUtils.QSZT_VALID);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    // 生成电子证照信息
                    eCertificateService.createXmECertificate(bdcXmDO, currentUserName);
                    // 注销原证照信息
                    eCertificateService.cancelYxmECertificate(bdcXmDO, currentUserName);
                }
            }
            // 查询当日注销流程的项目信息
            bdcXmDOList = bdcXmService.listBdcXmByDjsj(currentDate, datePattern, CommonConstantUtils.QSZT_HISTORY);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    // 注销原证照信息
                    eCertificateService.cancelYxmECertificate(bdcXmDO, currentUserName);
                }
            }
        }
        LOGGER.warn("##############################################################电子证照补偿任务执行结束，当前时间：{}", new Date());
    }


 /************************************为合肥电子证照补偿事件***********************************************************************************/
 /************************************参数配置和代码谨慎修改**********************************************************************************/
    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 异步电子证照生成补偿事件
     * @Scheduled 被该注解修饰的方法是没有返回值的.
     * 定时默认在晚20点执行
     */
    @Scheduled(cron = "${eCertificate.hfbcsj.zz.cron:0 0 20 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_HFTBZZ_JOB_NAME, description = "异步电子证照生成补偿事件", waitTime = 60L, leaseTime = 600L)
    public void hfSyncZz() {
        Date currentDate = new Date();
        LOGGER.warn("################################################开启电子证照制证补偿任务，当前时间：{}", currentDate);
        if (hfSyncZz) {
            List<String> zsids = bdcZsService.listSyncZzZsids();
            if(CollectionUtils.isNotEmpty(zsids)){
                LOGGER.info("查询出需要补偿制证的zsid集合：{}", zsids);
                for(int i=0;i<zsids.size();i++){
                    String zsid = zsids.get(i);
                    LOGGER.info("开始制作电子证照，当前zsid：{}",zsid);
                    try{
                        eCertificateService.createHefeiDzzz("",zsid,"","");
                        LOGGER.info("制作电子证照完成，当前zsid：{}完成", zsid);
                    }catch (Exception e){
                        LOGGER.info("制作电子证照异常，当前zsid：{}", zsid);
                        LOGGER.error("异常信息:", e);
                    }
                }
            }
        }
        LOGGER.warn("##############################################################电子证照制证补偿任务执行结束，当前时间：{}", new Date());
    }

    /************************************为合肥存量电子证照补偿事件***********************************************************************************/
    /************************************参数配置和代码谨慎修改**********************************************************************************/
    /**
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 异步存量电子证照生成补偿事件
     * @Scheduled 被该注解修饰的方法是没有返回值的.
     * 定时默认在晚20点执行
     */
    @Scheduled(cron = "${eCertificate.hfbcsj.clzz.cron:0 0 20 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_HFTBCLZZ_JOB_NAME, description = "异步存量电子证照生成补偿事件", waitTime = 60L, leaseTime = 600L)
    public void hfSyncClZz() {
        LOGGER.warn("################################################开启存量电子证照制证补偿任务，当前时间：{}", new Date());
        if (hfSyncClZz) {
            if (StringUtils.isBlank(hfSyncClZzEndtime) || (hfSyncClZzNum == null || hfSyncClZzNum < 0)) {
                LOGGER.error("膳证结束时间为空、每次生成的证书数量为空或小于0，存量电子证照制证补偿任务无法执行");
                return;
            }

            // 查询目前系统中存量数据，每次生成hfSyncClZzNum个电子证照
            Map<String, Object> map = new HashMap<>();
            map.put("endTime", hfSyncClZzEndtime);
            map.put("num", hfSyncClZzNum);
            List<String> zsids = bdcZsService.listSyncZzClZsids(map);
            if (CollectionUtils.isEmpty(zsids)) {
                LOGGER.error("未查询到需要生成电子证照的zsid信息，存量电子证照制证补偿任务无法执行");
                return;
            }
            Integer result = eCertificateClService.plCreateHefeiClDzzz(zsids);
            LOGGER.info("存量电子证照制证补偿任务共：{} 条，成功：{} 条", zsids.size(), result);
        }
        LOGGER.warn("##############################################################存量电子证照制证补偿任务执行结束，当前时间：{}", new Date());
    }


    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 异步电子证照注销补偿事件
     * @Scheduled 被该注解修饰的方法是没有返回值的.
     * 定时默认在晚21点执行
     */
    @Scheduled(cron = "${eCertificate.hfbcsj.zx.cron:0 0 21 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_YBDZZZZX_JOB_NAME, description = "异步电子证照注销补偿事件", waitTime = 60L, leaseTime = 600L)
    public void hfSyncZx() {
        Date currentDate = new Date();
        LOGGER.warn("################################################开启电子证照注销补偿任务，当前时间：{}", currentDate);
        if (hfSyncZx) {
            // 查询当日注销流程的项目信息
            List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXmByDjsj(currentDate, "yyyyMMdd", CommonConstantUtils.QSZT_HISTORY);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                LOGGER.info("查询出需要补偿注销的项目数量：{}", bdcXmDOList.size());
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    List<BdcZsDO> bdczs = bdcZsService.queryBdcZsByXmid(bdcXmDO.getXmid());
                    if (CollectionUtils.isNotEmpty(bdczs)) {
                        for(BdcZsDO zsDo : bdczs){
                            String zsid = zsDo.getZsid();
                            LOGGER.info("开始注销电子证照，当前zsid：{}",zsid);
                            try{
                                eCertificateService.zxHefeiDzzz("", zsid);
                                LOGGER.info("注销电子证照完成，当前zsid：{}完成", zsid);
                            }catch (Exception e){
                                LOGGER.info("注销电子证照异常，当前zsid：{}", zsid);
                                LOGGER.error("异常信息:", e);
                            }
                        }
                    }
                }
            }
        }
        LOGGER.warn("##############################################################电子证照注销补偿任务执行结束，当前时间：{}", new Date());
    }


    /**
     * 一次性查询出来电子证照信息数据量
     */
    public static final int INTERVAL_SIZE = 1000;
    /**
     * 电子证照信息补偿事件
     * 用于同步市级中存在电子证照数据，登记中存在证照标识，但没有证照信息的数据
     * @Scheduled 定时默认在晚21点执行
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Scheduled(cron = "${eCertificate.sync.dzzz.cron:0 0 22 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_DZZZBC_JOB_NAME, description = "电子证照信息补偿事件", waitTime = 90L, leaseTime = 600L)
    public void syncDzzz(){
        LOGGER.info("#################### 开启同步电子证照信息补偿任务，当前时间：{} ###################", new Date());
        // synvDzzz yml配置，用于控制是否开启电子证照定时同步数据
        if(syncDzzz){
            // 获取证书中存在未拥有证书信息的数据，设置 STORAGEID 为空, ZZBS不为空 的查询条件
            BdcDzzzQO bdcDzzzQO = new BdcDzzzQO();
            bdcDzzzQO.setSfscpdf("0");
            bdcDzzzQO.setSfsczzbs("1");
            bdcDzzzQO.setFzsjOrder("ASC");
            String param = JSON.toJSONString(bdcDzzzQO);
            // 统计目前系统中，存在证照标识，未同步的证照数据数量，按照1000区间大小，进行分页查询
            Page<BdcDzzzCxDTO> bdcDzzzCxDTOS = bdcDzzzCzFeginService.listBdcDzzzByPage(new PageRequest(0, INTERVAL_SIZE), param);
            LOGGER.info("总共{}条数据",bdcDzzzCxDTOS.getTotalElements());
            int totalPage = bdcDzzzCxDTOS.getTotalPages();

            for(int i = 0; i<= totalPage; i++){
                Page<BdcDzzzCxDTO> bdcDzzzByPage = bdcDzzzCzFeginService.listBdcDzzzByPage(new PageRequest(i, INTERVAL_SIZE), param);
                if(bdcDzzzByPage.hasContent()){
                    for(BdcDzzzCxDTO bdcDzzzCxDTO:bdcDzzzByPage.getContent()){
                        if(StringUtils.isAnyBlank(bdcDzzzCxDTO.getZzbs(), bdcDzzzCxDTO.getGzlslid(), bdcDzzzCxDTO.getBdcqzh())){
                            continue;
                        }
                        try {
                            //调用电子证照下载接口，获取证照信息，并上传至大云。
                            StorageDto storageDto = this.eCertificateService.uploadECertificate(bdcDzzzCxDTO.getGzlslid(),bdcDzzzCxDTO.getZzbs(),
                                    bdcDzzzCxDTO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF, "证照补偿事件");
                            if(null == storageDto){
                                throw new Exception("下载电子证照信息失败，获取到大云StorageDto数据为空。");
                            }
                            // 证书信息上传大云后，更新证书中的 storageid
                            BdcZsDO bdcZsDO = new BdcZsDO();
                            bdcZsDO.setZsid(bdcDzzzCxDTO.getZsid());
                            bdcZsDO.setStorageid(storageDto.getId());
                            bdcZsService.updateBdcZs(bdcZsDO);
                            LOGGER.info("已同步: {}，", bdcDzzzCxDTO.getBdcqzh());
                        } catch (Exception e) {
                            LOGGER.error("同步电子证照数据失败，证书ID:{},不动产权证号{}", bdcDzzzCxDTO.getZsid(), bdcDzzzCxDTO.getBdcqzh(), e);
                        }
                    }
                }
            }
        }
        LOGGER.info("#################### 同步电子证照信息任务完成，当前时间：{} ###################",  new Date());
    }

}
