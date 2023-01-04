package cn.gtmap.realestate.exchange.web.rest.shucheng;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.exchange.ShuChengFcjyViewRestService;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/1/11
 * @description 舒城房产交易视图查询
 */
@RestController
@Api(tags = "舒城房产交易")
public class FcjyRestController implements ShuChengFcjyViewRestService {
    private static Logger LOGGER = LoggerFactory.getLogger(FcjyRestController.class);
    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;

    /**
     * 根据合同号查询交易信息
     *
     * @param htbh 合同编号
     * @return map map
     * @Date 2022/1/11
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Map queryShuchengFcjyView(String htbh) {
        if (StringUtils.isBlank(htbh)) {
            throw new MissingArgumentException("缺少合同编号必要参数！请检查");
        }
        List<BdcQlrDO> qlrDOList = new ArrayList<>();
        Map htxxMap = bdcXmMapper.queryFcjyView(htbh);
        if (MapUtils.isNotEmpty(htxxMap)) {
            //取出权利人字段，放入qlr对象中
            BdcQlrDO qlrDO = new BdcQlrDO();
            dozerBeanMapper.map(htxxMap, qlrDO, "shucheng_fcjy_qlr");
            qlrDOList.add(qlrDO);
            String docId = MapUtils.getString(htxxMap, "DOCID");
            //查询供游人信息
            if (StringUtils.isNotBlank(docId)) {
                List<Map> gyrList = bdcXmMapper.queryFcjyGyrView(docId);
                if (CollectionUtils.isNotEmpty(gyrList)) {
                    for (int i = 0; i < gyrList.size(); i++) {
                        BdcQlrDO gyrDo = new BdcQlrDO();
                        dozerBeanMapper.map(gyrList.get(i), gyrDo, "shucheng_fcjy_gyr");
                        dozerBeanMapper.map(htxxMap, gyrDo, "shucheng_fcjy_gyr_gyfs");
                        qlrDOList.add(gyrDo);
                    }
                }
            }
            htxxMap.put("QLR", qlrDOList);

        }
        return htxxMap;
    }
}
