package cn.gtmap.realestate.exchange.service.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.lzzt.CourtLzztRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.lzzt.CourtLzztResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.yancheng.court.SdJsBaseResponseDTO;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version  2020-12-08 11:05:54
 * @description (盐城) 一体化相关服务处理
 */
public interface BdcCourtService {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 锁定不动产单元
     * @param bdcDysdDOList
     * @return
     */
    ExchangeDsfCommonResponse sdBdcdy(List<BdcDysdDO> bdcDysdDOList);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 锁定不动产单元
     * @param bdcZssdDOList
     * @return
     */
    ExchangeDsfCommonResponse sdBdczs(List<BdcZssdDO> bdcZssdDOList);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 解锁不动产单元
     * @param bdcDysdDO
     * @return
     */
    ExchangeDsfCommonResponse jsBdcdy(BdcDysdDO bdcDysdDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 解锁不动产证书
     * @param bdcZssdDO
     * @return
     */
    ExchangeDsfCommonResponse jsBdczs(BdcZssdDO bdcZssdDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 根据不动产单元号判断落宗状态 1已落宗 0未落宗
     * @param courtLzztRequestDTO
     */
    ExchangeDsfCommonResponse<CourtLzztResponseDTO> cxlzztByBdcdyh(CourtLzztRequestDTO courtLzztRequestDTO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 根据权利人名称和证件号查询当前权利人的所有现势登记业务信息
     * 1.首次登记的产权不查询 CourtYwxxcxResponseDTO
     * 2.如果查询的证件类型是组织机构代码，就按照权利人名称精确或者证件号精确来查询；
     * @param
     */
    Object ywxxcx(CourtYwxxcxRequestDTO courtYwxxcxRequestDTO);
}
