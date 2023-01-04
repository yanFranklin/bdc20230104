package cn.gtmap.realestate.engine.service;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产组合规则校验逻辑接口定义
 */
public interface BdcGzZhgzCheckService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  需要校验的规则查询参数
     * @return {BdcGzZhgzTsxxDTO} 提示信息
     * @description 组合规则单次验证，获取对应提示信息（参数bdcGzYzQO传值：zhbs、paramMap）
     */
    BdcGzZhgzTsxxDTO getZhgzYzTsxx(BdcGzYzQO bdcGzYzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  验证查询参数
     * @return {List} 批量验证提示信息
     * @description 批量组合规则验证，获取对应提示信息
     *   说明：
     *   1、例如传递多个BDCDYH验证查封，返回每个BDCDYH对应的验证提示信息
     *   2、参数bdcGzYzQO传值：zhbs、bdcGzYzsjDTOList
     */
    List<BdcGzYzTsxxDTO> listBdcGzYzTsxx(BdcGzYzQO bdcGzYzQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  验证查询参数
     * @return {List} 批量验证提示信息
     * @description   批量规则验证（传入任意参数）
     *   说明：
     *   1、参数bdcGzYzQO传值：zhbs、paramList
     */
    List<BdcGzYzTsxxDTO> listBdcGzYzTsxxOfAnyParam(BdcGzYzQO bdcGzYzQO);
}
