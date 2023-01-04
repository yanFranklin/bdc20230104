package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcFwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareFwtcxxResultDTO;
import cn.gtmap.realestate.common.core.qo.accept.CompareFwtcxxQO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/10
 * @description 房屋信息查询
 */
public interface BdcSlFwCxService {

    /**
     * @param xmid 项目ID
     * @return 房屋信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID查询房屋信息
     */
    BdcFwxxDTO listFwxxByXmid(String xmid);

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param isYz  是否验证比较房查套次与申报套次
     * @return 住房信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人住房信息
     */
    List<BdcSlFwtcxxDO> listBdcZfxxDTO(String xmid, String sqrlb, Boolean isYz);

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param isYz  是否验证比较房查套次与申报套次
     * @return 住房信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人住房信息(南通版本)
     */
    List<BdcSlFwtcxxDO> listBdcZfxxDTONT(String xmid, String sqrlb, Boolean isYz);

    /**
     * 房屋套次信息对比
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param compareFwtcxxQO
     * @return
     */
    CompareFwtcxxResultDTO compareFwtcxx(CompareFwtcxxQO compareFwtcxxQO);

    /**
     * @param compareFwtcxxQO 套次比对信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 导入套次比对信息
     */
    void drFwtcxx(CompareFwtcxxQO compareFwtcxxQO);
}
