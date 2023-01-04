package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.domain.building.ZdJsydsybDO;
import cn.gtmap.realestate.common.core.dto.building.FwJsydzrzxxDTO;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/22
 * @description 宗地建设用地量化信息rest服务
 */
public interface ZdJsydLhxxRestService {

    /**
     * @param djh 地籍号
     * @return 宗地建设用地使用表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据地籍号查询宗地建设用地使用表
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/jsydsyb/{djh}")
    ZdJsydsybDO queryZdJsydsybByDjh(@PathVariable(name = "djh") String djh);

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息查询QO对象
     * @return 建设用地自然幢列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据建设用地自然幢主键集合查询建设用地自然幢列表
     */
    @PostMapping("/building/rest/v1.0/zdjsydlh/jsydzrz")
    List<FwJsydzrzxxDO> listFwJsydzrzxx(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * 分页查询建设用地自然幢列表
     * @param pageable 分页参数
     * @param paramJson 查询参数
     * @return Page<FwJsydzrzxxDO> 房屋建设用地自然幢信息分页结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yoayi</a>
     */
    @PostMapping("/building/rest/v1.0/zdjsydlh/jsydzrz/page")
    Page<FwJsydzrzxxDTO> listFwJsydzrzxxByPageJson(Pageable pageable,
                                                   @RequestParam(name = "paramJson", required = false) String paramJson);
    /**
     * 根据建设用地自然幢主键集合查询建设用地自然幢和状态信息
     * @return 建设用地自然幢信息DTO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yoayi</a>
     */
    @PostMapping("/building/rest/v1.0/zdjsydlh/jsydzrz/zt")
    List<FwJsydzrzxxDTO> listFwJsydzrzxxWithZt(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息查询QO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据建设用地自然幢主键集合更新量化抵押权利状态
     */
    @PostMapping("/building/rest/v1.0/zdjsydlh/lhdyqzt")
    void updateFwJsydzrzxxLhdycsPl(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息查询QO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据建设用地自然幢信息查询QO条件，更新状态（可以更新量化抵押状态、量化首登状态）
     */
    @PostMapping("/building/rest/v1.0/zdjsydlh/zt/pl")
    void updateFwJsydzrzxxZtPl(@RequestBody FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * 批量更新建设用地自然幢信息
     * @param fwJsydzrzxxDOList 建设用地自然幢信息集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/building/rest/v1.0/zdjsydlh/zrzxx/pl")
    void updateFwJsydzrzxxPl(@RequestBody List<FwJsydzrzxxDO> fwJsydzrzxxDOList);

    /**
     * 根据不动产单元号查询建设用地自然幢的量化抵押状态
     * @param bdcdyh 不动产单元号
     * @return integer 量化抵押状态
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/lhdyqzt/{bdcdyh}")
    Integer queryLhdyqlZtByBdcdyh(@PathVariable(name="bdcdyh") String bdcdyh);

    /**
     * 根据不动产单元号查询建设用地自然幢的量化首登状态
     * @param bdcdyh 不动产单元号
     * @return integer 量化首登状态
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/lhsdqlzt/{bdcdyh}")
    Integer queryLhsdqlZtByBdcdyh(@PathVariable(name="bdcdyh") String bdcdyh);

    /**
     * 根据fwDcbIndex查询建设用地自然幢的量化抵押状态
     * <p>判断方式：当前宗地是否拥有{@code lhdyqlzt=0}的情况，有则判断当前的 {@code lhdyqlzt=1}和 null 的情况才为量化抵押状态。
     * 否则不是量化抵押状态</p>
     * @param fwDcbIndex
     * @return integer 量化抵押状态
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/lhdyqzt/fwdcbindex")
    Integer queryLhdyqlZtByFwDcbIndexAndDjh(@RequestParam(name="fwDcbIndex") String fwDcbIndex, @RequestParam(name="djh") String djh);

    /**
     * 根据地籍号查询当前宗地是否办理过量化抵押
     * <p>判断方式
     * 1、存在量化抵押状态为 {@code 0} 的情况，则为已办理过
     * 2、量化抵押状态{@code 1}的情况 ，则为已办理过
     * 3、没有数据或 所有量化抵押状态为空，则为未办理过</p>
     * @param djh 地籍号
     * @return integer 是否存在量化抵押 0 未办理过量化抵押， 1办理过
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/lhdyqzt/zd/{djh}")
    Integer queryZdLhxxByDjh(@PathVariable(name="djh") String djh);

    /**
     * 根据不动产单元号查询建设用地自然幢的量化查封状态
     * @param bdcdyh 不动产单元号
     * @return integer 量化查封状态
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/lhcfzt/{bdcdyh}")
    Integer queryZdLhCfZtByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * 根据不动产单元号查询是否存在量化查封数据
     * <p>查询 fw_jsydzrzxx 表中该lszd的量化查封数据</p>
     * @param bdcdyh 不动产单元号
     * @return 量化查封数据量
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/lhcfxx/{bdcdyh}")
    Integer queryZdLhcfxxByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * 根据不动产单元号校验单元号所在宗地上逻辑幢的量化查封状态
     * @param bdcdyh 不动产单元号
     * @return 量化查封状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/building/rest/v1.0/zdjsydlh/lhcfzt/czrz/{bdcdyh}")
    Integer checkJsydLhcfztByLhCzrz(@PathVariable(name = "bdcdyh") String bdcdyh);
}
