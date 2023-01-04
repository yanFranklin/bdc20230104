package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcYjdhSfxxGxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYjSfxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcYjSfxxQO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/27
 * @description 不动产银行月结单号与受理编号关系REST服务
 */
public interface BdcYjdhSfxxGxRestService {

    /**
     * 根据月结收费信息QO查询月结收费信息
     * @param bdcYjSfxxQO  不动产月结收费信息DO
     * @return BdcYjSfxxDTO 不动产月结收费信息DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/yjdh/sfxx/list")
    List<BdcYjSfxxDTO> listYhyjSfxx(@RequestBody BdcYjSfxxQO bdcYjSfxxQO);

    /**
     * 生成月结单号收费信息数据
     * @param bdcYjdhSfxxGxDO 银行月结单号与收费信息关系DO
     * @return 银行月结单号与收费信息关系数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/yjdh/sfxx/gx")
    List<BdcYjdhSfxxGxDO> generateYjdhSfxxGx(@RequestBody List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDO);

    /**
     * 校验收费信息是否已经下单
     * @param bdcYjdhSfxxGxDOList  银行月结单号与收费信息关系DO
     * @return key: 月结单号， value： 受理编号信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/yjdh/sfxx/sfxd")
    Map<String, Set<String>> checkSfxxSfxd(@RequestBody List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList);

    /**
     * 根据月结单号查询月结收费信息数据
     * @param yjdh 月结单号
     * @return 月结单号收费关系数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/yjdh/gx/list")
    List<BdcYjdhSfxxGxDO> queryBdcYjdhSfxxGxByYjdh(@RequestParam(value="yjdh") String yjdh);

    /**
     * 根据收费信息ID获取月结收费信息
     *
     * @param sfxxid 收费信息ID
     * @return 月结单号集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/yjsf/yjdh/{sfxxid}")
    List<String> queryYjdhxxBySfxxId(@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * 查询月结缴费数据
     * <p>互联网调用的接口，和查询月结收费信息相同的逻辑，需要验证参数是否必填</p>
     *
     * @param bdcYjSfxxQO 不动产月结收费信息DO
     * @return BdcYjSfxxDTO 不动产月结收费信息DTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/yjdh/sfxx/list/hlw")
    Page<BdcYjSfxxDTO> listYhyjSfxxForHlw(@RequestBody BdcYjSfxxQO bdcYjSfxxQO, @RequestParam(value = "pageSize") int pageSzie,
                                          @RequestParam(value = "pageNumber")int pageNumber);

    /**
     * 根据月结缴费查询参数，生成月结单号
     * @param bdcYjSfxxQO 不动产月结收费信息DO
     * @return String  月结单号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/yjdh/slbh/hlw")
    BdcYjSfxxDTO queryYjdh(@RequestBody BdcYjSfxxQO bdcYjSfxxQO);

}
