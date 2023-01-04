package cn.gtmap.realestate.building.service;


import cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxZdResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-16
 * @description 地籍信息相关服务业务逻辑
 */
public interface DjxxService {

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号 构造 DJXX DTO 包括（djdcb dcb qlr）
     */
    DjxxResponseDTO getDjxxDTOForInitByBdcdyh(String bdcdyh,String djqlrgxid);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description [备份] 根据不动产单元号 构造 DJXX DTO 包括（djdcb dcb qlr）
     */
    DjxxResponseDTO getHDjxxDTOForInitByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO
     * @description 根据不动产单元号查询DJDCB
     */
    DjDcbResponseDTO getDjdcbDTOByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description 根据不动产单元号 查询（djdcb  qlr）
     */
    DjDcbAndQlrResponseDTO getDjdcbAndQlrByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcdyh
     * @return ZdQsdcDO
     * @description 根据不动产单元号 查询权属调查信息
     */
    ZdQsdcDO queryZdQsdcByBdcdyh(String bdcdyh);

    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据zl查询地籍信息（多个）
     * @Date 2022/5/9
     **/
    DjxxZdResponseDTO queryBdcjsydsyqByzl(String zl);



}
