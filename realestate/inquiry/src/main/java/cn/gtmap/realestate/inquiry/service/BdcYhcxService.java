package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxDyaDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;

import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询
 */
public interface BdcYhcxService {
    /**
     * 根据证书id查询项目信息
     * @param zsid
     * @return
     */
    List<BdcXmDO> listBdcXmByZsid(String zsid);


    /**
     * 通过不动产单元号查询查封
     * @param bdcdyhList
     * @return
     */
    List<BdcCfDO> listBdcCfByBdcdyh(List<String> bdcdyhList);

    /**
     * 通过不动产单元号查询抵押
     * @param bdcdyhList
     * @return
     */
    List<BdcYhcxDyaDTO> listBdcDyaByBdcdyh(List<String> bdcdyhList);

    /**
     * 通过不动产单元号查询异议
     * @param bdcdyhList
     * @return
     */
    List<BdcYyDO> listBdcYyByBdcdyh(List<String> bdcdyhList);

    /**
     * 通过不动产单元号查询锁定
     * @param bdcdyhList
     * @return
     */
    List<String> listBdcSdxxByBdcdyh(List<String> bdcdyhList);

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @description 获取不动产权证书列表
     */
    List<BdcZsDO> listBdcZs(BdcZsQO bdcZsQO);

}
