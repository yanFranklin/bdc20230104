package cn.gtmap.realestate.exchange.service.impl.inf.ycsl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ycsl.YcslHsztService;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019-09-24
 * @description 一窗受理 核税状态
 */
@Service
public class YcslHsztServiceImpl implements YcslHsztService {

    @Autowired
    private BdcSlHsxxFeignService bdcSlHsxxFeignService;

    @Autowired
    private CommonService commonService;

    /**
     * 更新完税状态
     * @param map
     */
    @Override
    public void saveWszt(Map map) {
        // 获取核税信息 受理号下所有XM的核税信息
        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>();
        String slbh = MapUtils.getString(map, "slbh");
        if(StringUtil.isNotEmpty(slbh)){
            List<BdcXmDO> bdcXmList = commonService.listBdcXmBySlbh(slbh);
            if(CollectionUtils.isNotEmpty(bdcXmList)){
                BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
                bdcSlHsxxQO.setXmid(MapUtils.getString(map, "xmid"));
                List<BdcSlHsxxDO> tempList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
                if(CollectionUtils.isNotEmpty(tempList)){
                    bdcSlHsxxDOList.addAll(tempList);
                }
            }
        }

        Integer wszt = null;
        // 获取完税状态
        String wsztjson = MapUtils.getString(map, "wszt");
        if (StringUtil.isNotBlank(wsztjson)) {
            JSONObject wsztObject = JSONObject.parseObject(wsztjson);
            if (StringUtil.isNotBlank(wsztObject.getString("data"))) {
                JSONObject dataObject = JSONObject.parseObject("data");
                wszt = dataObject.getInteger("wszt");
            }
        }

        // 进行更新
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            for (BdcSlHsxxDO bdcSlHsxxDO: bdcSlHsxxDOList){
                bdcSlHsxxDO.setWszt(wszt);
                bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
            }
        }
    }
}
