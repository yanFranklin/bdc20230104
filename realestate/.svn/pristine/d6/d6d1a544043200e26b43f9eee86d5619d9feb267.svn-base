package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlTfxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlTfxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/19
 * @description 不动产退费信息接口服务
 */
public interface BdcSlTfxxService {

    /**
     * 分页查询不动产受理退费信息
     * @param pageable 分页参数
     * @param json 查询参数
     * @return 不动产受理退费信息List
     */
    Page<BdcSlTfxxDO> listBdcSlTfxx(Pageable pageable, String json);

    /**
     * 查询不动产受理退费信息集合
     * @param bdcSlTfxxQO 不动产退费信息QO
     * @return 不动产退费信息List
     */
    List<BdcSlTfxxDO> queryBdcSlTfxxList(BdcSlTfxxQO bdcSlTfxxQO);

    /**
     * 根据受理编号获取收费信息，生成退费信息内容
     * @param slbh 受理编号
     * @return 退费信息集合
     */
    List<BdcSlTfxxDO> generateTfxx(String slbh);

    /**
     * 批量保存或新增不动产受理退费信息
     * @param bdcSlTfxxDOList 不动产受理退费信息集合
     */
    void plSaveBdcSlTfxx(List<BdcSlTfxxDO> bdcSlTfxxDOList);

    /**
     * 批量删除不动产退费信息
     * @param tfxxidList 退费信息ID集合
     */
    void plRemoveBdcSlTfxx(List<String> tfxxidList);
}
