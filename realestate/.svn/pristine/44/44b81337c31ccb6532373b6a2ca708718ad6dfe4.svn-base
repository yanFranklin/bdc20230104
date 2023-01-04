package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.dto.building.FwHstRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-11-08
 * @description 房屋户室图相关服务
 */
public interface FwHstRestService {

    /**
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除户室图服务
     */
    @DeleteMapping("/building/rest/v1.0/hst/{fwHstIndex}")
    Integer deleteFwHstByFwHstIndex(@PathVariable("fwHstIndex") String fwHstIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除户室的户室图
     */
    @DeleteMapping("/building/rest/v1.0/hst/delfwhshst/{fwHsIndex}/{hslx}")
    void delFwhsHst(@PathVariable("fwHsIndex") String fwHsIndex, @PathVariable("hslx") String hslx, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除独幢平面图
     */
    @DeleteMapping("/building/rest/v1.0/hst/delfwljzpmt/{fwDcbIndex}")
    void delFwLjzPmt(@PathVariable("fwDcbIndex") String fwDcbIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查看逻辑幢下户室图缺失情况
     */
    @GetMapping("/building/rest/v1.0/hst/deficiency/{fwDcbIndex}")
    List<FwHsDO> listHstDeficiency(@PathVariable("fwDcbIndex") String fwDcbIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHstRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 户室图请求实体
     */
    @PutMapping("/building/rest/v1.0/hst")
    FwHstDO saveHst(@RequestBody FwHstRequestDTO fwHstRequestDTO);

    /**
     * @param fwHstRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存幢平面图
     */
    @PutMapping("/building/rest/v1.0/hst/pmt")
    FwHstDO saveLjzPmt(@RequestBody FwHstRequestDTO fwHstRequestDTO);

    /**
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询户室图
     */
    @GetMapping("/building/rest/v1.0/hst/{fwHstIndex}")
    FwHstDO queryHstByIndex(@PathVariable("fwHstIndex") String fwHstIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @GetMapping("/building/rest/v1.0/hst/queryFwHstByIndex")
    FwHstDO queryFwHstByIndex(@RequestParam(name = "fwHstIndex", required = false) String fwHstIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询 逻辑幢平面图 base64位码
     */
    @GetMapping("/building/rest/v1.0/hst/pmt/base64/{fwDcbIndex}")
    String queryLjzPmtBase64(@PathVariable(name = "fwDcbIndex") String fwDcbIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHsIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询户室图 base64位码
     */
    @GetMapping("/building/rest/v1.0/hst/base64/{fwHsIndex}")
    String queryFwHstBase64(@PathVariable(name = "fwHsIndex") String fwHsIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询房屋户室图
     */
    @GetMapping("/building/rest/v1.0/hst/base64bybdcdyh/{bdcdyh}")
    String queryFwHstBase64ByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh,qjgldm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据不动产单元号查询户室图数据，包含独幢户室图和户室户室图
     * @date : 2021/10/12 9:01
     */
    @GetMapping("/building/rest/v1.0/hst/base64/{bdcdyh}/dzhs")
    String queryHstBase64ForHsAndDz(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh,qjgldm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据不动产单元号查询户室图数据(合肥)
     * @date : 2021/10/12 9:01
     */
    @GetMapping("/building/rest/v1.0/hst/base64/{bdcdyh}/hsthf")
    List<String> queryHstBase64Hefei(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);
}
