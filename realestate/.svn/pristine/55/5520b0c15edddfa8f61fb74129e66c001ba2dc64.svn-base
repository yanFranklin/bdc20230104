package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.building.FwJsydzrzxxDTO;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/16
 * @description 房屋建设用地自然幢信息服务
 */
public interface FwJsydzrzxxService {

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息查询QO对象
     * @return 建设用地自然幢列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据建设用地自然幢主键集合查询建设用地自然幢列表
     */
    List<FwJsydzrzxxDO> listFwJsydzrzxx(FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * 分页查询建设用地自然幢列表
     * @param pageable 分页参数
     * @param map 查询参数
     * @return Page<FwJsydzrzxxDTO> 房屋建设用地自然幢信息分页结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yoayi</a>
     */
    Page<FwJsydzrzxxDTO> listFwJsydzrzxxByPage(Pageable pageable, Map map);

    /**
     * 根据建设用地自然幢主键集合查询建设用地自然幢和状态信息
     * @return 建设用地自然幢信息DTO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yoayi</a>
     */
    List<FwJsydzrzxxDTO> listFwJsydzrzxxWithZt(FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息查询QO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据建设用地自然幢主键集合批量更新量化抵押权利状态
     */
    void updateFwJsydzrzxxLhdycsPl(FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * @param fwJsydzrzxxQO 建设用地自然幢信息查询QO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据建设用地自然幢主键集合批量更新状态
     */
    void updateFwJsydzrzxxZtPl(FwJsydzrzxxQO fwJsydzrzxxQO);

    /**
     * 批量更新建设用地自然幢信息
     * @param fwJsydzrzxxDOList 房屋建设用地自然幢信息集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateFwJsydzrzxxPl(List<FwJsydzrzxxDO> fwJsydzrzxxDOList);

    /**
     * 根据不动产单元号查询建设用地自然幢的量化抵押状态
     * @param bdcdyh 不动产单元号
     * @return integer 量化抵押状态
     */
    Integer queryLhdyqlZtByBdcdyh(String bdcdyh);

    /**
     * 根据不动产单元号查询建设用地自然幢的量化首登状态
     * @param bdcdyh 不动产单元号
     * @return integer 量化首登状态(1:量化首登状态；0:非量化首登状态)
     */
    Integer queryLhsdqlZtByBdcdyh(String bdcdyh);

    /**
     * 根据fwdcbIndex查询建设用地自然幢的量化抵押状态
     * <p>判断方式：当前宗地是否拥有{@code lhdycs=0}的情况，
     * 有则判断当前的 {@code lhdycs>1}的情况才为量化抵押状态。
     * 否则不是量化抵押状态</p>
     * @param fwDcbIndex
     * @return integer 量化抵押状态
     */
    Integer queryLhdyqlZtByFwDcbIndexAndDjh(String fwDcbIndex, String lszd);

    /**
     * 根据地籍号查询当前宗地是否存在量化抵押状态
     * @param djh 地籍号
     * @return integer 是否存在量化抵押 0 ：不存在 大于1则存在
     */
    Integer queryZdLhxxByDjh(String djh);

    /**
     * 根据不动产单元号查询建设用地自然幢的量化查封状态
     * @param bdcdyh 不动产单元号
     * @return integer 量化查封状态
     */
    Integer queryLhCfZtByBdcdyh(String bdcdyh);

    /**
     * 根据不动产单元号查询建设用地自然幢的量化查封数据量
     * @param bdcdyh 不动产单元号
     * @return integer 量化查封信息数据量
     */
    Integer queryLhCfxxByBdcdyh(String bdcdyh);

    /**
     * 根据不动产单元号校验单元号所在宗地上逻辑幢的量化查封状态
     * @param bdcdyh 不动产单元号
     * @return 量化查封状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Integer checkJsydLhcfztByLhCzrz(String bdcdyh);

}
