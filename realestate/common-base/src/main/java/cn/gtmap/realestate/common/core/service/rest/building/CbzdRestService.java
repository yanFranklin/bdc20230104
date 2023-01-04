package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description 承包宗地相关服务
 */
public interface CbzdRestService {
    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询承包宗地基本信息
     */
    @GetMapping("/building/rest/v1.0/cbzd/{bdcdyh}")
    CbzdDcbDO queryCbzdByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.CbzdDcbDO
     * @description 根据DJH查询承包宗地基本信息
     */
    @GetMapping("/building/rest/v1.0/cbzd/djh/{djh}")
    CbzdDcbDO queryCbzdByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH查询承包宗地承包方信息
     */
    @GetMapping("/building/rest/v1.0/cbzd/cbf/{bdcdyh}")
    List<CbzdCbfDO> listCbfByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param cbzdDcbcbfrelIndex 承包方与地块关系主键
     * @return 承包方信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据承包方地块关系主键查询承包宗地承包方信息
     */
    @GetMapping("/building/rest/v1.0/cbzd/cbf/cbzdDcbcbfrelIndex/{cbzdDcbcbfrelIndex}")
    List<CbzdCbfDO> listCbfByCbzdDcbcbfrelIndex(@PathVariable("cbzdDcbcbfrelIndex") String cbzdDcbcbfrelIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.CbzdFbfDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询发包方信息
     */
    @GetMapping("/building/rest/v1.0/cbzd/fbf/{bdcdyh}")
    List<CbzdFbfDO> listFbfByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询承包方不动产单元
     */
    @PostMapping("/building/rest/v1.0/cbzd/page")
    Page<Map> listCbzdBdcdy(Pageable pageable,
                            @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 验证BDCDYH 是否 有承包信息
     */
    @GetMapping("/building/rest/v1.0/cbzd/yzbdcdyh/{bdcdyh}")
    boolean yzbdcdyh(@PathVariable("bdcdyh") String bdcdyh);

    /**
     * @param jtIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 通过家庭Index 查询家庭成员信息
     */
    @GetMapping("/building/rest/v1.0/cbzd/jtcy/{jtIndex}")
    List<NydJtcyDO> listCbzdJtcyByJtIndex(@PathVariable("jtIndex")String jtIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydZdmjDO>
     * @description 用外键 查询 承包宗地 分类面积列表
     */
    @GetMapping("/building/rest/v1.0/cbzd/nydzdmj/{djdcbIndex}")
    List<NydZdmjDO> listNydZdmjByDjdcbIndex(@PathVariable("djdcbIndex")String djdcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   查询当前地块对应承包方的所有地块关系集合
     */
    @GetMapping("/building/rest/v1.0/cbzd/cbzddcbcbfrel/{cbzdDcbcbfrelIndex}")
    List<CbzdDcbcbfRelDO> listCbfCbzdDcbcbfRelList(@PathVariable("cbzdDcbcbfrelIndex")String cbzdDcbcbfrelIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
