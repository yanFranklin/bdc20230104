package cn.gtmap.realestate.init.service.qlxx.qlfl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.domain.BdcFwfsssDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlDataAbstractService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.impl.InitYxmToBdcCfServiceImpl;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 初始化房地产权信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
@ConfigurationProperties(prefix = "init.default")
public abstract class InitBdcFdcqAbstractService extends InitBdcQlDataAbstractService {
    private static Logger logger = LoggerFactory.getLogger(InitBdcFdcqAbstractService.class);

    /**
     * 获取房屋性质的配置
     */
    private Map<String,Integer> fwxz;

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //参数空值返回
        if (initServiceQO == null) {
            return initServiceDTO;
        }
        if (initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        initServiceDTO = initQlxx(initServiceQO,initServiceDTO);
        //取出生成的权利
        BdcQl initQlxx=initServiceDTO.getBdcQl();
        //非对照权籍信息的
        if (initQlxx!=null) {
            logger.info("房地产权信息：{}", JSON.toJSONString(initQlxx));
            //多幢的处理
            if ((initQlxx instanceof BdcFdcqDO) && CommonConstantUtils.FWLX_DUOZH.equals(((BdcFdcqDO) initQlxx).getBdcdyfwlx())) {
                List<BdcFdcqFdcqxmDO> fdcqXmList = initFdcqXm(initServiceQO, initQlxx.getQlid(), initServiceDTO);
                //为不动产权赋值
                if (CollectionUtils.isNotEmpty(fdcqXmList)) {
                    initServiceDTO.setBdcFdcqFdcqxmList(fdcqXmList);
                }else if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcFdcqFdcqxmList())){
                    // qlsjly配置多个值  例如：1，2  1生成FdcqXm数据 2没有生成  解决qlid和FdcqXm表不一致问题
                    for(BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO:initServiceDTO.getBdcFdcqFdcqxmList()){
                        bdcFdcqFdcqxmDO.setQlid(initQlxx.getQlid());
                    }
                }
            }

            if(!initServiceQO.isSfdzbflpbsj()){
                // 子户室的处理
                List<BdcFwfsssDO> fsssList = initFdcqFsss(initServiceQO);
                if (CollectionUtils.isNotEmpty(fsssList)) {
                    initServiceDTO.setFwfsssList(fsssList);
                }
            }
        }
        //房屋性质默认值处理
        if(MapUtils.getInteger(fwxz,initServiceQO.getBdcXm().getDjxl())!=null && initQlxx instanceof BdcFdcqDO){
            ((BdcFdcqDO) initQlxx).setFwxz(MapUtils.getInteger(fwxz,initServiceQO.getBdcXm().getDjxl()));
        }
        return initServiceDTO;
    }

    /**
     * 处理房地产权多幢的信息
     * @param initServiceDTO
     * @param initServiceQO
     * @param qlid
     * @return
     */
    public abstract List<BdcFdcqFdcqxmDO> initFdcqXm(InitServiceQO initServiceQO, String qlid, InitServiceDTO initServiceDTO);

    /**
     * 处理房地产权子户室的信息
     *
     * @param initServiceQO
     * @return
     */
    public abstract List<BdcFwfsssDO> initFdcqFsss(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException;


    public Map<String, Integer> getFwxz() {
        return fwxz;
    }

    public void setFwxz(Map<String, Integer> fwxz) {
        this.fwxz = fwxz;
    }
}
