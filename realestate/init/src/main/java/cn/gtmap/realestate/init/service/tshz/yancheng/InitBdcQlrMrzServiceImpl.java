package cn.gtmap.realestate.init.service.tshz.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 盐城权利人默认值处理
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午3:03 2021/2/24
 */
@Service("bdcQlrMrzServiceImpl")
public class InitBdcQlrMrzServiceImpl implements InitBdcTsHzService {


    @Value("#{'${yctscl.sfczr.gzldyids:}'.split(',')}")
    private List<String> sfczrGzldyids;

    @Value("#{'${spf.mrz.djxls:}'.split(',')}")
    private List<String> qlrJyfeMrzList;

    /**
     * 特殊后置处理
     * 盐城权利人默认值处理
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if (initServiceDTO != null && CollectionUtils.isNotEmpty(sfczrGzldyids)
                && sfczrGzldyids.contains(initServiceDTO.getBdcXm().getGzldyid())) {
            List<BdcQlrDO> bdcQlrList = initServiceDTO.getBdcQlrList();
            if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                    if (bdcQlrDO.getSfczr() == null) {
                        bdcQlrDO.setSfczr(CommonConstantUtils.SF_F_DM);
                    }
                }
                initServiceDTO.setBdcQlrList(bdcQlrList);
            }
        }
        //常州权利人交易份额设置默认值
        if (initServiceDTO != null && CollectionUtils.isNotEmpty(qlrJyfeMrzList)
                && qlrJyfeMrzList.contains(initServiceDTO.getBdcXm().getDjxl())) {
            List<BdcQlrDO> bdcQlrList = initServiceDTO.getBdcQlrList();
            if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                    if (bdcQlrDO.getJyfe() == null && CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())) {
                        bdcQlrDO.setJyfe(100.00);
                    }
                }
                initServiceDTO.setBdcQlrList(bdcQlrList);
            }
        }
        return initServiceDTO;
    }
}
