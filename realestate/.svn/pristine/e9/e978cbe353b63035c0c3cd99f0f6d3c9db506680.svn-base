package cn.gtmap.realestate.certificate.quartz;

import cn.gtmap.realestate.certificate.service.BdcJjdService;
import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/10/20 16:11
 * @description 交接单状态修改定时任务
 */
@Component
public class JjdQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JjdQuartzService.class);

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcJjdService bdcJjdService;

    /**
     * 是否开启交接单状态修改定时任务
     */
    @Value("${jjd.state.modify:false}")
    private boolean jjdStateModify;

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return void
     * @description 交接单状态修改定时任务
     */
    @Scheduled(cron = "${eCertificate.jjdstatemodify.cron:0 0 19 * * ?}")
    public void jjdStateModifyTask() {
        Date currentDate = new Date();
        LOGGER.info("################################################开启交接单状态修改任务，当前时间：{}", currentDate);
        try {
            if (jjdStateModify) {
                List<BdcJjdDO> jjdDOList = bdcJjdService.queryBdcJjd();
                if (CollectionUtils.isEmpty(jjdDOList)) {
                    LOGGER.info("没有需要过滤处理的交接单");
                    return;
                }
                LOGGER.info("需要过滤的交接单的数量为：{}", jjdDOList.size());
                List<BdcJjdDO> needModifyJjdList = jjdDOList.stream().filter(Objects::nonNull)
                        .filter(bdcJjdDO -> StringUtils.isNotBlank(bdcJjdDO.getJjdid()))
                        .filter(bdcJjdDO -> filterJjd(bdcJjdDO)).collect(Collectors.toList());

                LOGGER.info("需要更新状态的交接单数量：{}", needModifyJjdList.size());
                if (CollectionUtils.isNotEmpty(needModifyJjdList)) {
                    // 设置接收时间
                    for (BdcJjdDO bdcJjdDO : needModifyJjdList) {
                        // 最晚创建新交接单的时间为接收时间
                        List<BdcJjdDO> AllNewJjdList = bdcJjdService.getAllNewJjdByJjdid(bdcJjdDO.getJjdid());
                        bdcJjdDO.setJssj(AllNewJjdList.get(0).getCjsj());

                        if (bdcJjdDO.getJssj() == null) {
                            List<BdcXmDO> bdcXmDOList = bdcJjdService.getBdcXmDOByJjdid(bdcJjdDO.getJjdid());
                            bdcJjdDO.setJssj(bdcXmDOList.get(0).getJssj());
                        }
                        bdcJjdDO.setJsks("流程已办结");
                        bdcJjdDO.setJjdzt(2);
                    }
                    int num = bdcJjdService.updateJjdzt(needModifyJjdList);
                }
            }
            LOGGER.info("#################### 交接单状态修改任务完成，当前时间：{} ###################", new Date());
        } catch (DataAccessException e) {
            LOGGER.error("交接单状态修改任务失败，数据库异常：{}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("交接单状态修改任务失败，异常信息： {}", e.getMessage());
        }
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmYjdGxDO, jjd]
     * @return boolean
     * @description 判断此流程是否创建新的交接单
     */
    private boolean checkHaveNewJjd(BdcXmYjdGxDO bdcXmYjdGxDO, BdcJjdDO jjd) {
        String gzlslid = bdcXmYjdGxDO.getXmid();

        List<BdcJjdDO> bdcJjdDOList = bdcJjdService.getBdcJjdListByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcJjdDOList)) {
            LOGGER.warn("根据工作流实例id {} 未查询到交接单项目关联关系记录 ", gzlslid);
            return false;
        }

        BdcJjdDO bdcJjdDO = bdcJjdDOList.get(0);
        return compareJjdCjsj(bdcJjdDO, jjd);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcJjdDO, jjd]
     * @return boolean
     * @description 比较是否是新的交接单
     */
    private boolean compareJjdCjsj(BdcJjdDO bdcJjdDO, BdcJjdDO jjd) {
        if (bdcJjdDO.getCjsj() != null && jjd.getCjsj() != null) {
            if (bdcJjdDO.getCjsj().after(jjd.getCjsj())) {
                return true;
            }
        }
        if (StringUtils.isNotBlank(bdcJjdDO.getJjdh()) && StringUtils.isNotBlank(jjd.getJjdh())) {
            if (Long.parseLong(bdcJjdDO.getJjdh()) > Long.parseLong(jjd.getJjdh())) {
                return true;
            }
        }
        return false;

    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [jjdDO]
     * @return boolean
     * @description 过滤交接单
     * 过滤条件：
     * 1.该交接单对应的所有流程都办结了
     * 2.该交接单对应的所有流程都创建了新的交接单
     * 满足这两点则此交接单状态需要修改为已接收
     */
    private boolean filterJjd(BdcJjdDO jjdDO) {
        List<BdcXmYjdGxDO> bdcXmYjdGxDOList = bdcJjdService.queryJjdGx(jjdDO.getJjdid());
        if (CollectionUtils.isEmpty(bdcXmYjdGxDOList)) {
            LOGGER.warn("此交接单未查到项目关联数据，交接单id : {}", jjdDO.getJjdid());
            return false;
        }

        for (BdcXmYjdGxDO bdcXmYjdGxDO : bdcXmYjdGxDOList) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            if (StringUtils.isBlank(bdcXmYjdGxDO.getXmid())) {
                LOGGER.warn("此交接单项目关联数据缺少工作流实例id，关联数据id：{}", bdcXmYjdGxDO.getGxid());
                return false;
            }
            bdcXmQO.setGzlslid(bdcXmYjdGxDO.getXmid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                LOGGER.warn("此工作流实例id不存在项目数据，工作流实例id：{}", bdcXmQO.getGzlslid());
                return false;
            }

            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            if (!CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
                return false;
            }

            if (!checkHaveNewJjd(bdcXmYjdGxDO, jjdDO)) {
                return false;
            }

        }
        return true;
    }
}
