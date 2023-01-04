package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.DcxxDTO;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-27
 * @description 房屋户室调查信息服务
 */
public interface FwDcxxRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwIndex
     * @param bdcdyfwlx
     * @return cn.gtmap.realestate.common.core.dto.building.DcxxDTO
     * @description 根据BDCDYFWLX 和 FWINDEX 查询房屋户室调查信息
     */
    @GetMapping("/building/rest/v1.0/dcxx/{bdcdyfwlx}/{fwIndex}")
    DcxxDTO queryFwDcxx(@PathVariable("bdcdyfwlx") String bdcdyfwlx,
                                     @PathVariable("fwIndex") String fwIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dcxxDTO
     * @return void
     * @description 保存调查信息
     */
    @PutMapping("/building/rest/v1.0/dcxx")
    Integer updateFwDcxx(@RequestBody DcxxDTO dcxxDTO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwIndex
     * @param bdcdyfwlx
     * @return void
     * @description 删除房屋调查信息
     */
    @DeleteMapping("/building/rest/v1.0/dcxx/{bdcdyfwlx}/{fwIndex}")
    void deleteFwDcxx(@PathVariable("bdcdyfwlx") String bdcdyfwlx,
                                   @PathVariable("fwIndex") String fwIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
