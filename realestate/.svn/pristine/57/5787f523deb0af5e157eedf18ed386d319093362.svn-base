package cn.gtmap.realestate.certificate.quartz;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.mapper.BdcZsMapper;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.impl.BdcBdcqzhGgServiceImpl;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/02/18
 * @description  不动产权证号定时任务处理
 */
@Component
@EnableScheduling
public class BdcBdcqzhQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBdcqzhQuartzService.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcBdcqzhGgServiceImpl bdcBdcqzhGgService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BdcZsMapper bdcZsMapper;

    /**
     * 证号是否区分部门，如果没有配置则默认1，即默认是需要按照登记部门区分
     */
    @Value("${zhqfbm:1}")
    private Integer zhqfbm;

    /**
     * 证号是否区分区县代码，如果没有配置则默认1，即默认是需要按照区县代码区分
     */
    @Value("${zhqfqxdm:1}")
    private Integer zhqfqxdm;

    /**
     * 是否启用跳号回收功能
     */
    @Value("${quartz.bdcqzhthhs.enable:false}")
    private Boolean enableBdcqzhthhs;

    /**
     * 证号跳号回收初始号：具体从哪个号开始回收跳号，例如库中号 1 2 5 6 8 10，那么从3号开始回收，则回收的号依次是 3 4 7 9
     */
    @Value("${quartz.bdcqzhthhs.csh:1}")
    private Integer bdcqzhthhsCsh;


    /**
     * 获取跳号的不动产权证号序号
     * 1、留作实际获取证号时候使用，保证证号整体不跳号
     * 2、只针对Redis缓存方式生成证号（bdcqzh.needlock = false），对于数据库获取证号方式（bdcqzh.needlock = true）本身即支持跳号处理，则无需此定时任务
     */
    @Scheduled(cron = "${quartz.bdcqzhthhs.cron:0 0 21 * * ?}")
    public void resolveBdcqzhTh() {
        if(!enableBdcqzhthhs) {
            return;
        }

        Example example = new Example(BdcXtZsbhmbDO.class);
        example.createCriteria().andEqualTo("nf", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        List<BdcXtZsbhmbDO> zsbhmbDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(zsbhmbDOList)) {
            LOGGER.error("未查询到当前年份证号配置模板");
            return;
        }

        for(BdcXtZsbhmbDO zsbhmbDO : zsbhmbDOList) {
            for(int zslx = 1; zslx <= 3; zslx++) {
                BdcBdcqzhBO qo = new BdcBdcqzhBO();
                qo.setNf(zsbhmbDO.getNf());
                qo.setZhqfqxdm(zhqfqxdm);
                qo.setQxdm(zsbhmbDO.getQxdm());
                qo.setQxdms(bdcBdcqzhGgService.getQxdmKey(zsbhmbDO.getQxdm(), true));
                qo.setZslx(zslx);
                qo.setZhqfbm(zhqfbm);
                qo.setDjbmdm(zsbhmbDO.getDjbmdm());
                qo.setCssxh(bdcqzhthhsCsh);

				// 获取跳号，理论上存在一次性获取不到所有跳号可能，但是无所谓，查出部分跳号使用即可，后续跳号在证书表记录够多情况下可以都查询出来
                List<String> zhthList = bdcZsMapper.listBdcZhth(qo);
                if(CollectionUtils.isNotEmpty(zhthList)) {
                    String key = bdcBdcqzhGgService.getZhthhsRedisKey(qo.getNf(), qo.getQxdm(), qo.getDjbmdm(), qo.getZslx());
                    redisUtils.addSetValue(key, zhthList);
                    LOGGER.info("{}：证号回收缓存{}个证号，对应内容：{}", key, zhthList.size(), zhthList);
                }
            }
        }
    }
}
