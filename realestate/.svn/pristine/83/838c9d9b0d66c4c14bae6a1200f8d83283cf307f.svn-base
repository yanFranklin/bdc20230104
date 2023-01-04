package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ShareDataFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcYgAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * 预告权利的实现  读取原项目
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/15.
 * @description
 */
@Service
public class InitYxmToBdcYgServiceImpl extends InitBdcYgAbstractService {
    private static Logger logger = LoggerFactory.getLogger(InitYxmToBdcYgServiceImpl.class);

    @Value("${init.dozerVersion:standard}")
    protected String version;

    @Autowired
    private ShareDataFeignService shareDataFeignService;

    /**
     * 抽象方法 设置对照开关值
     *
     * @return 对照开关值
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }

    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回权利信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        BdcYgDO bdcYgDO=initFromYxm(initServiceQO,BdcYgDO.class);
        this.readOldSystemData(initServiceQO, bdcYgDO);
        return dealYg(initServiceQO,bdcYgDO);
    }

    /**
     * （常州）读取原系统相关数据赋值到当前权利
     * @param initServiceQO
     * @param bdcQl 当前权利
     */
    protected void readOldSystemData(InitServiceQO initServiceQO, BdcYgDO bdcQl) {
        if(isTargetVersion() && isRoomidNotBlank(initServiceQO)) {
            logger.info("常州复制原系统预告权利信息，当前预告权利：{}", bdcQl.getQlid());
            JSONObject param = new JSONObject();
            param.put("roomid", initServiceQO.getDsfSlxxDTO().getRoomid());
            JSONObject result = shareDataFeignService.ycfwxxCx(param);
            if(null != result && null != result.get("data")) {
                JSONObject data = result.getJSONObject("data");
                initDozerMapper.map(data, bdcQl, "yxmygxx");
                // 部分总层数、所在层非纯数值
                setSzcZcs(bdcQl, data, "szc", "zcs");
                setXmxx(initServiceQO, data);
                logger.info("常州复制原系统预告权利信息，roomid:{}，权利信息：{}", initServiceQO.getDsfSlxxDTO().getRoomid(), data.toJSONString());
            }
        }
    }

    /**
     * 所在层、总层数
     * @param bdcQl 预告权利
     * @param data 原系统数据
     */
    private void setSzcZcs(BdcYgDO bdcQl, JSONObject data, String... fields) {
        for(String fieldName : fields) {
            try {
                Field field = bdcQl.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(bdcQl, Integer.parseInt(data.getString(fieldName)));
            } catch (Exception e) { }
        }
    }

    /**
     * 赋值项目信息
     * @param initServiceQO
     * @param data 原系统数据
     */
    private void setXmxx(InitServiceQO initServiceQO, JSONObject data) {
        try {
            if(null != initServiceQO.getBdcXm()){
                // 项目坐落
                if (StringUtils.isBlank(initServiceQO.getBdcXm().getZl())) {
                    initServiceQO.getBdcXm().setZl(data.getString("zl"));
                }
                // 定着物面积
                if (null == initServiceQO.getBdcXm().getDzwmj()) {
                    initServiceQO.getBdcXm().setDzwmj(Double.valueOf(String.valueOf(data.get("ycjzmj"))));
                }
            }
        } catch (Exception e) { }
    }

    private boolean isTargetVersion() {
        return CommonConstantUtils.SYSTEM_VERSION_CZ.equals(version);
    }

    private boolean isRoomidNotBlank(InitServiceQO initServiceQO) {
        return null != initServiceQO
                && null != initServiceQO.getDsfSlxxDTO()
                && StringUtils.isNotBlank(initServiceQO.getDsfSlxxDTO().getRoomid());
    }
}
