package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.YcHsZtResDTO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsGlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsZzglRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/10
 * @description 房屋预测户室相关服务
 */
public interface FwYcHsRestService {

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH查询户室基本信息
     */
    @GetMapping("/building/rest/v1.0/ychs/bdcdy/{bdcdyh}")
    FwYchsDO queryFwYcHsByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwYchsDO
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增预测户室服务
     */
    @PostMapping("/building/rest/v1.0/ychs")
    FwYchsDO insertFwYcHs(@RequestBody FwYchsDO fwYchsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwYchsDO
     * @param updateNull true表示空字段更新，false，表示空字段不更新
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改预测户室服务
     */
    @PutMapping("/building/rest/v1.0/ychs")
    Integer updateFwYcHs(@RequestBody FwYchsDO fwYchsDO, @RequestParam(name = "updateNull", required = false) boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据主键删除户室信息
     */
    @DeleteMapping("/building/rest/v1.0/ychs/{fwHsIndex}")
    Integer deleteYcHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description
     */
    @PostMapping("/building/rest/v1.0/ychs/listbypage")
    Page<FwYchsDO> listYchsByPage(Pageable pageable,
                                  @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param pageable
     * @param paramJson
     * @return Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description
     */
    @PostMapping("/building/rest/v1.0/ychs/gl/listbypage")
    Page<Map> glListYchsByPage(Pageable pageable,
                               @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询预测户室和权利人信息
     */
    @GetMapping("/building/rest/v1.0/ychs/ychsqlr/{bdcdyh}")
    YchsAndQlrResponseDTO queryYchsAndQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @description 根据主键查询预测户室
     */
    @GetMapping("/building/rest/v1.0/ychs/{fwHsIndex}")
    FwYchsDO queryFwYcHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @description 根据逻辑幢主键查询预测户室
     */
    @GetMapping("/building/rest/v1.0/ychs/list/{fwDcbIndex}")
    List<FwYchsDO> listFwYchsByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param ycScHsGlDTO
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 预测实测户室关联
     */
    @PostMapping("/building/rest/v1.0/ychs/ycschsgl")
    void ycscHsGl(@RequestBody YcScHsGlRequestDTO ycScHsGlDTO);

    /**
     * @param ycScHsGlDTO
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 预测实测户室取消关联
     */
    @PostMapping("/building/rest/v1.0/ychs/ycschsqxgl")
    void ycscHsQxGl(@RequestBody YcScHsGlRequestDTO ycScHsGlDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return void
     * @description 整幢取消关联
     */
    @GetMapping("/building/rest/v1.0/ychs/qxzzgl/{fwDcbIndex}")
    void ycscZzQxgl(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param ycScHsZzglRequestDTO
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description  整幢关联
     */
    @PostMapping("/building/rest/v1.0/ychs/zzgl")
    void ycscZzgl(@RequestBody YcScHsZzglRequestDTO ycScHsZzglRequestDTO);

    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋fwdcbindex查询预测户室的信息和状态
     * @date : 2020/12/10 10:12
     */
    @GetMapping("/building/rest/v1.0/ychs/zt/{fwDcbIndex}")
    List<YcHsZtResDTO> listYchsZt(@PathVariable(name = "fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * 根据房屋YSFWBM查询预测户室的信息和状态
     * @param YSFWBM
     * @return
     */
    @PostMapping("/building/rest/v1.0/ychs/listFwYchsByYsfwbm")
    List<FwYchsDO> listFwYchsByYsfwbm(@RequestParam(value = "YSFWBM", required = true) String YSFWBM);
}