package cn.gtmap.realestate.certificate.core.service.impl;


import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzLshMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLshDo;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzLshService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 
 */
@Service
public class BdcDzzzLshServiceImpl implements BdcDzzzLshService {
    public final Logger logger = LoggerFactory.getLogger(BdcDzzzLshServiceImpl.class);


    @Autowired
    BdcDzzzLshMapper bdcDzzzLshMapper;

    @Override
    @RedissonLock(lockKey = Constants.REDISSON_LOCK_LSH, description = "获取电子证照流水号")
    public String getBdcDzzzLsh(String zzlxdm, String zzbfjgdm) {
        String dzzzLshUseModeOrder = EnvironmentConfig.getEnvironment().getProperty("dzzz.lsh.useMode.order");
        if (StringUtils.equals(dzzzLshUseModeOrder, "true")) {

            Integer maxLsh = null;
            Map map = Maps.newHashMap();
            map.put("zzlxdm", zzlxdm);
            map.put("zzbfjgdm", zzbfjgdm);
            List<BdcDzzzLshDo> bdcDzzzLshDoList = bdcDzzzLshMapper.getBdcDzzzLsh(map);
            if (CollectionUtils.isNotEmpty(bdcDzzzLshDoList) && StringUtils.isNotBlank(bdcDzzzLshDoList.get(0).getLsh())) {
                maxLsh = Integer.parseInt(bdcDzzzLshDoList.get(0).getLsh());
            }

            logger.info(("不动产电子证照流水号：" + maxLsh));
            final Integer lsh = (maxLsh == null ? 1 : (maxLsh + 1));

            String dzzzlshPattern = EnvironmentConfig.getEnvironment().getProperty("dzzz.lsh.pattern");
            final DecimalFormat df;
            if (StringUtils.isNotBlank(dzzzlshPattern)) {
                df = new DecimalFormat(dzzzlshPattern);
            } else {
                df = new DecimalFormat("000000000");
            }
            String zzlsh = df.format(lsh);

            if (StringUtils.isNotEmpty(zzlsh)) {
                //保存受理编号
                BdcDzzzLshDo bdcDzzzLshDo = new BdcDzzzLshDo();
                bdcDzzzLshDo.setLshid(UUIDGenerator.generate());
                bdcDzzzLshDo.setLsh(zzlsh);
                bdcDzzzLshDo.setZzbfjgdm(zzbfjgdm);
                bdcDzzzLshDo.setCjsj(DateUtil.now());
                bdcDzzzLshDo.setZzlxdm(zzlxdm);
                bdcDzzzLshMapper.saveBdcDzzzLsh(bdcDzzzLshDo);

                return zzlsh;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
