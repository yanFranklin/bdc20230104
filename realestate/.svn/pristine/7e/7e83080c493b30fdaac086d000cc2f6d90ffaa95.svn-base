package cn.gtmap.realestate.exchange.quartz;


import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.domain.JgLjzlxtxdGjDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.JgWxzbwxxDO;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.qo.JgWxzBwxxQO;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: realestate
 * @description: 上报销账定时服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-22 11:31
 **/
@Component
@EnableScheduling
public class SbxzQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SbxzQuartzService.class);
    @Autowired
    AccessLogMapper accessLogMapper;
    @Autowired
    private EntityMapper entityMapper;
    /*
     * 是否开启上报销账定时任务*/
    @Value("${sbxz.open:false}")
    private boolean sbxz;

    @Scheduled(cron = "${access.sbxz.cron:0 0 23 * * ?}")
    public void updateXzzt() {
        if (sbxz) {
            //查询xzzt 为 1 和2 的数据
            JgWxzBwxxQO jgWxzBwxxQO = new JgWxzBwxxQO();
            List<String> xzztList = new ArrayList<>(2);
            xzztList.add("1");
            xzztList.add("2");
            xzztList.add("4");
            jgWxzBwxxQO.setXzztList(xzztList);
            List<JgWxzbwxxDO> jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
            if (CollectionUtils.isNotEmpty(jgWxzbwxxDOList)) {
                LOGGER.warn("查询到未上报销账信息表销账类型为1 ,2,4 的数据共{}条", jgWxzbwxxDOList.size());
                for (JgWxzbwxxDO jgWxzbwxxDO : jgWxzbwxxDOList) {
                    //根据ywh在JG_LJZLCYXD_GJ中没有数据时，将xzzt由2更新成3，若ywh在JG_LJZLCYXD_GJ中仍存在数据时，则将xzzt由1更新成4
                    Example example = new Example(JgLjzlxtxdGjDO.class);
                    example.createCriteria().andEqualTo("ywh", jgWxzbwxxDO.getYwh());
                    List<JgLjzlxtxdGjDO> jgLjzlxtxdGjDOList = entityMapper.selectByExampleNotNull(example);
                    if (CollectionUtils.isNotEmpty(jgLjzlxtxdGjDOList)) {
                        if (!StringUtils.equals("1", jgWxzbwxxDO.getXzzt())) {
                            LOGGER.warn("业务号{}在增量详单存在数据，且状态不是未销账，更新销账状态- 失败", jgWxzbwxxDO.getYwh());
                            jgWxzbwxxDO.setXzzt(CommonConstantUtils.XZZT_XZSB);
                            jgWxzbwxxDO.setSjgxsj(new Date());
                        }
                    } else {
                        LOGGER.warn("业务号{}增量详单不存在数据，销账状态{} ，更新销账状态-成功", jgWxzbwxxDO.getYwh(), jgWxzbwxxDO.getXzzt());
                        if (StringUtils.equals(CommonConstantUtils.XZZT_ZZXZ, jgWxzbwxxDO.getXzzt())
                                || StringUtils.equals(CommonConstantUtils.XZZT_XZSB, jgWxzbwxxDO.getXzzt())
                                || StringUtils.equals(CommonConstantUtils.XZZT_WXZ, jgWxzbwxxDO.getXzzt())) {
                            jgWxzbwxxDO.setXzzt(CommonConstantUtils.XZZT_XZCG);
                            jgWxzbwxxDO.setSjgxsj(new Date());
                        }
                    }
                }
                entityMapper.batchSaveSelective(jgWxzbwxxDOList);
            }
        }
    }

}
