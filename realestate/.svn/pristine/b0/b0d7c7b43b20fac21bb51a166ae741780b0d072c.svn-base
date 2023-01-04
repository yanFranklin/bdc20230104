package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBgRequestDTO;
import cn.gtmap.realestate.common.core.vo.building.FwHsHbVO;
import cn.gtmap.realestate.common.core.vo.building.FwhsBgVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-28
 * @description 与户室变更相关服务
 */
public interface FwhsBgRestService {

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 拆分服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/cf")
    List<FwHsDO> fwhsChaifen(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO);

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室合并服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/hb")
    Object fwhsHeBing(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO);

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室灭失服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/ms")
    void fwhsMieShi(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO);

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室变更服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/bg")
    FwHsDO fwhsJbxxBianGeng(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO);

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 拆分服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/cf/revoke/{bgbh}")
    void fwhsRevokeChaifen(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室合并服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/hb/revoke/{bgbh}")
    void fwhsRevokeHeBing(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室灭失服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/ms/revoke/{bgbh}")
    void fwhsRevokeMieShi(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室变更服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/bg/revoke/{bgbh}")
    void fwhsRevokeJbxxBianGeng(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室变更服务
     */
    @PutMapping("/building/rest/v1.0/fwhsbg/bg/revoke")
    void fwhsRevokeBg(@RequestParam(value = "fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHsBgxxDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 变更信息保存
     */
    @PostMapping("/building/rest/v1.0/fwhsbg/bgxx")
    FwHsBgxxDO insertFwHsBgxx(@RequestBody FwHsBgxxDO fwHsBgxxDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return java.lang.String
     * @description 获取变更编号
     */
    @PostMapping("/building/rest/v1.0/fwhsbg/bgbh")
    String maxBgbh();

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return boolean
     * @description 判断房屋是否有变更
     */
    @GetMapping("/building/rest/v1.0/fwhsbg/checkbgjl")
    boolean checkFwHsHistory(@RequestParam(value = "fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bgbh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @description 根据变更编号查询变更信息
     */
    @GetMapping("/building/rest/v1.0/fwhsbg/bgxx/{bgbh}")
    FwHsBgxxDO getFwHsBgxxByBgbh(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 获取户室变更配置
     */
    @GetMapping("/building/rest/v1.0/fwhsbg/hbconfig")
    String getHsHbConfig();

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室变更dto获取有效不动产单元号
     */
    @PostMapping("/building/rest/v1.0/fwhsbg/validbdcdyh/hsbgdto")
    List<String> listValidBdcdyhByFwhsBgRequestDTO(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室变更VO获取有效不动产单元号
     */
    @PostMapping("/building/rest/v1.0/fwhsbg/validbdcdyh/hsbgvo")
    List<String> listValidBdcdyhByFwhsBgVo(@RequestBody FwhsBgVO fwhsBgVO);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室合并VO获取有效不动产单元号
     */
    @PostMapping("/building/rest/v1.0/fwhsbg/validbdcdyh/hshbvo")
    List<String> listValidBdcdyhByFwhsHbVo(@RequestBody FwHsHbVO fwHsHbVO);
}
