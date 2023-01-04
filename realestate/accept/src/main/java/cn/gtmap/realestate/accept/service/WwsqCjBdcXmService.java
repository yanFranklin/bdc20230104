package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.accept.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-11
 * @description 外网申请创建项目
 */
public interface WwsqCjBdcXmService {

    /**
     * @param wwsqCjBdcXmRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 请求创建BDC项目
     */
    WwsqCjxmResponseDTO cjBdcXm(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception;

    /**
     * @param wwsqCjBdcXmRequestDTO 外网申请请求对象
     * @return 受理信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  请求创建非登记流程项目
     */
    WwsqCjBdcXmResponseDTO cjFdjlcXm(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param wwsqCjBdcXmRequestDTO
     * @param gzyzResult
     * @return List<Map<String,Object>>
     * @description 第三方创建  默认 带入规则验证带过来的外联项目ID （如果存在非WLZS验证不通过的子规则，则不处理，全部返回）
     */
    List<Map<String,Object>> dsfDealWlzsXmid(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO,List<Map<String,Object>> gzyzResult);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcSlxxDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 验证不动产单元
     */
    List<Map<String,Object>> yzBdcdy(BdcSlxxDTO bdcSlxxDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gzlslid
     * @param autoForwardTaskDTO 下一节点信息
     * @return java.lang.Boolean
     * @description 外网申请项目 根据工作流实例ID 自动转发
     */
    Boolean autoTurnWorkflow(String gzlslid, AutoForwardTaskDTO autoForwardTaskDTO);

    /**
     * @param gzlslid            工作流实例ID
     * @param autoForwardTaskDTO 下一节点信息
     * @author <a href="mailto:liyinqiao@gtmap.cn">liaoxiang</a>
     * @description 外网申请自动转发(包含规则验证)
     */
    Map<String, String> autoTurnWithGzyz(String gzlslid, AutoForwardTaskDTO autoForwardTaskDTO);

    /**
     * 外网申请自动转发
     * <ul>
     *     <li>如果节点未被认领，由配置中默认用户自动认领</li>
     *     <li>自动签名：签名信息取上一个节点用户，如果未被认领则取配置中默认用户</li>
     *     <li>转发前验证：必填项和规则验证</li>
     *     <li>转发到下个节点</li>
     * </ul>
     *
     * @param gzlslid            工作流实例ID
     * @param autoForwardTaskDTO 下一节点信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Map<String, String> autoTurn(String gzlslid, AutoForwardTaskDTO autoForwardTaskDTO);
}
