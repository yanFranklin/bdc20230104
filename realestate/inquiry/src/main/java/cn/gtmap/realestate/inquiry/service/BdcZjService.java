package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjdDO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/3/30.
 * @description 质检接口
 */
public interface BdcZjService {

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param pageable 分页对象
     * @param bdcZjQO 查询条件
     * @return {Page} 质检查询分页参数
     * @description 质检分页查询
     */
    Page<BdcXmDO> listBdcZjxx(Pageable pageable, BdcZjQO bdcZjQO);

    /**
     * 随机筛选质检信息，创建质检单生成质检单编号
     * @param bdcZjQO 不动产质检查询DO
     * @return 质检单编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    String generateRandomZjd(BdcZjQO bdcZjQO);

    /**
     * 手动筛选质检信息，创建质检单生成质检单编号
     * @param bdcXmDOList 不动产项目信息
     * @return 质检单编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    String generateManualZjd(List<BdcXmDO> bdcXmDOList);

    /**
     * 根据质检ID删除质检信息
     * @param zjid 质检ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void deleteZjxx(String zjid);

    /**
     * 获取质检信息以及质检明细内容
     * @param zjid 质检ID
     * @return 质检信息、质检情况明细内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcZjxxDTO queryBdcZjxxAndMx(String zjid);

    /**
     * 根据条件查询质检信息
     * @param bdcZjDO 质检信息DO
     * @return 质检信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcZjDO> queryBdcZjxx(BdcZjDO bdcZjDO);

    /**
     * 更新质检单质检状态
     * @param zjdid 质检单ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void modifyZjdZt(String zjdid);

    /**
     * 根据质检单ID获取质检单与关联的质检信息
     * @param zjdid 质检单ID
     * @return 质检单以及质检信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcZjdDTO queryBdcZjdAndZjxx(String zjdid);

    /**
     * 根据质检单ID获取质检信息
     * @param zjdid 质检单ID
     * @return 质检信息信息集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcZjDO> queryBdcZjxxByZjdId(String zjdid);

    /**
     * 保存质检信息和质检明细信息
     * @param bdcZjxxDTO 不动产质检信息DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void saveZjxxAndZjmx(BdcZjxxDTO bdcZjxxDTO);

    /**
     * 根据质检单ID删除质检单
     * @param zjdid 质检单ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void deleteZjd(String zjdid);

    /**
     * 随机获取 100 条需要质检的项目数据
     * @param bdcZjQO 质检查询对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcZjXmxxDTO> randomBdcZjXmxx(BdcZjQO bdcZjQO);

    /**
     * 保存质检信息
     * @param bdcZjDO 不动产质检信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcZjDO saveBdcZjDO(BdcZjDO bdcZjDO);

    /**
     * 获取不动产质检结果汇总数据
     * @param bdcZjQO 质检查询对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcZjXmxxDTO> queryBdcZjjgHzxx(BdcZjQO bdcZjQO);

    /**
     * 批量生成质检
     */
    public BdcZjdDO plCreateBdcZj(BdcPlZjDTO bdcPlZjDTO);

    /**
     * 批量质检质检单详情
     *
     */
    public BdcPlZjxxDTO plZjInfo(String zjdbh);
}
