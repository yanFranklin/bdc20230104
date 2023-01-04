package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-23
 * @description 合肥交易状态相关服务
 */
public interface HfJyztRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ysfwbmList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO>
     * @description 根据YSFWBM 查询 交易状态
     */
    @PostMapping("/building/rest/v1.0/hf/jyzt/queryjyztbyysfwbm")
    List<HfJyztResponseDTO> queryJyztByYsfwbm(@RequestBody List<String> ysfwbmList,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
