package cn.gtmap.realestate.init.service.bjbh;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcBjbhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcZwQhResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BdcBjbhService {

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [pageable, bdcBjbhQOStr]
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.init.BdcBjbhDTO>
     * @description 分页查询办件编号
     */
    Page<BdcBjbhDTO> listBjbhByPage(Pageable pageable, String bdcBjbhQOStr);

    /**
     * @return cn.gtmap.realestate.common.core.dto.init.BdcZwQhResponseDTO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId]
     * @description 调用政务申报登记反馈接口取号
     */
    BdcZwQhResponseDTO takeNumber(String processInsId);

    /**
     * 4.3.1 申报登记
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    BdcZwQhResponseDTO sbdj(String processInsId);


    /**
     * 4.3.3 申报登记_受理或删除信息推送
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    SbdjfkResponseDTO sbdjSlxx(String processInsId, String blzt);

    /**
     * 4.3.8	办结
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    SbdjfkResponseDTO sbdjBjxx(String processInsId);
}
