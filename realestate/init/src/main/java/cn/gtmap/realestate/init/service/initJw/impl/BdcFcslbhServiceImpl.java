package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmFbService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BdcFcslbhServiceImpl extends InitBdcJwService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFwfsssJwServiceImpl.class);

    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    BdcXmFbService bdcXmFbService;

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description  受理编号自增时间范围
     */
    @Value("${fcslbh.zzsjfw:DAY}")
    private String zzsjfw;

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description  房产受理编号自增序列号位数
     */
    @Value("${fcslbh.zzxlh:4}")
    private Integer zzxlh;

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description  房产受理编号自增前置编号
     */
    @Value("${fcslbh.prebh:}")
    private String prebh;

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description  是否生成房产受理编号
     */
    @Value("${fcslbh.sfsc:false}")
    private boolean sfsc;

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        if (BooleanUtils.isTrue(sfsc) && CollectionUtils.isNotEmpty(listQO) && initResultDTO != null) {
            boolean sfzqlpbsj = listQO.get(0).isSfzqlpbsj();
            boolean sfdzbflpbsj = listQO.get(0).isSfzqlpbsj();

            List<BdcXmDO> bdcXmList = initResultDTO.getBdcXmList();
            List<BdcXmFbDO> bdcXmFbList = initResultDTO.getBdcXmFbList();
            if (BooleanUtils.isFalse(sfzqlpbsj) && BooleanUtils.isFalse(sfdzbflpbsj) && CollectionUtils.isNotEmpty(bdcXmList) && CollectionUtils.isNotEmpty(bdcXmFbList)) {
                // 生成房产受理编号
                String bdcfcslbh = bdcBhFeignService.queryCommonBh("bdcfcslbh", zzsjfw, zzxlh, prebh);
                LOGGER.info("生成房产受理编号{}", bdcfcslbh);

                // 更新BdcXmFbDO的房产受理编号
                for (BdcXmFbDO bdcXmFbDO : bdcXmFbList) {
                    bdcXmFbDO.setBdcfcslbh(bdcfcslbh);
                }
                // 更新后的BdcXmFbDO重新入库
                BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bdcfcslbh", bdcfcslbh);
                bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(jsonObject));
                Map whereMap = new HashMap<>();
                whereMap.put("gzlslid", bdcXmList.get(0).getGzlslid());
                bdcDjxxUpdateQO.setWhereMap(whereMap);
                try {
                    bdcXmFbService.updateBatchBdcXmFb(bdcDjxxUpdateQO);
                } catch (Exception e) {
                    LOGGER.error("更新项目附表房产受理编号出错, {}", e.getMessage());
                }
            }
        }
    }
}
