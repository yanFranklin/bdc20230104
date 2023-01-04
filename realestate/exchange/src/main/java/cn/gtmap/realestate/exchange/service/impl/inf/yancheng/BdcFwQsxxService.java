package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.dto.accept.ZzcxjSfxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.zzcxj.response.FwqsCxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2020-11-23
 * @description (盐城) 房屋权属信息处理接口定义
 */
public interface BdcFwQsxxService {
    /**
     * 查询房屋权属信息
     *
     * @param fwqlCxRequestDTO 自助查询机查询参数
     * @param cdsj             查档时间
     * @return List 房屋权属信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<FwqsCxResponseDTO> queryFwQsxx(FwqlCxRequestDTO fwqlCxRequestDTO, String cdsj);

    /**
     * @param param 参数
     * @return BdcSlSfxxZzcxjDTO 收费信息
     * @Date 2020/11/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Object queryZsSfxx(ZzcxjSfxxDTO param);
}
