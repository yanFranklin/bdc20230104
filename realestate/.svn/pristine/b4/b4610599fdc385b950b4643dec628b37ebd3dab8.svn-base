package cn.gtmap.realestate.init.service.dlr;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href=""mailto:liaoxiang@gtmap.cn>liaoxiang</a>
 * @version 1.0, 2022/5/14.
 * @description 初始化代理人基础服务
 */
public abstract class InitBdcDlrAbstractService implements InitService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(InitBdcDlrAbstractService.class);



    @Override
    public String getVersion() {
        return null;
    }


    @Override
    public String getCode() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 参数空值返回
        if (initServiceQO == null) {
            return initServiceDTO;
        }
        if (initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        if(CollectionUtils.isNotEmpty(initServiceQO.getBdcDlrDOList())){
            LOGGER.info("{}初始化读取代理人信息:{}",initServiceQO.getBdcXm() != null?initServiceQO.getBdcXm().getSlbh():"",initServiceQO.getBdcDlrDOList());
            initServiceDTO.setBdcDlrDOList(initServiceQO.getBdcDlrDOList());
        }
        return initServiceDTO;
    }

    @Override
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel) throws Exception {
        return null;
    }

    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception {
        return initServiceDTO;
    }


}
