package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxZdResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-17
 * @description 地籍信息相关服务
 */
public interface DjxxRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、DCB、权利人
     */
    @GetMapping("/building/rest/v1.0/djxx/{bdcdyh}")
    DjxxResponseDTO queryDjxxByBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据zl查询地籍信息（多个）
     * @Date 2022/5/9
     **/
    @GetMapping("/building/rest/v1.0/djxx/zl")
    DjxxZdResponseDTO queryBdcjsydsyqByzl(@RequestParam(name = "zl") String zl);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param djqlrgxid 地籍权利人关系ID
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、DCB、权利人
     */
    @GetMapping("/building/rest/v1.0/djxx/bdcdyh/{bdcdyh}")
    DjxxResponseDTO queryDjxx(@PathVariable("bdcdyh") String bdcdyh, @RequestParam(name = "djqlrgxid", required = false) String djqlrgxid, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据承包宗地与承包方关系主键查询承包宗地籍信息 包含DJDCB、DCB、权利人
     */
    @GetMapping("/building/rest/v1.0/cbzddjxx/{bdcdyh}/{cbzdDcbcbfrelIndex}")
    DjxxResponseDTO queryCbzdDjxxByCbzdDcbcbfrelIndex(@PathVariable("bdcdyh") String bdcdyh, @PathVariable("cbzdDcbcbfrelIndex") String cbzdDcbcbfrelIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、DCB、权利人
     */
    @GetMapping("/building/rest/v1.0/hdjxx/{bdcdyh}")
    DjxxResponseDTO queryHDjxxByBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO
     * @description 根据BDCDYH查询土地地籍调查表
     */
    @GetMapping("/building/rest/v1.0/djxx/djdcb/{bdcdyh}")
    DjDcbResponseDTO queryDjDcbByBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、权利人
     */
    @GetMapping("/building/rest/v1.0/djxx/djdcbwithqlr/{bdcdyh}")
    DjDcbAndQlrResponseDTO getDjdcbAndQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);
}
