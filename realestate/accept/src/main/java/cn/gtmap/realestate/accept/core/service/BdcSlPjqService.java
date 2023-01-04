package cn.gtmap.realestate.accept.core.service;


import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcMkqmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPjqDTO;
import cn.gtmap.realestate.common.core.dto.accept.SlymPjqDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlPjqQO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

/**
 * @program: realestate
 * @description: 评价器接口
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-08-08 15:01
 **/
public interface BdcSlPjqService {

    /**
     * @param ywbh 交易信息id
     * @param jdmc 节点名称
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    BdcSlPjqDO queryBdcSlPjqByYwbh(String ywbh, String jdmc);

    /**
     * @param bdcSlJyxxDO 交易信息
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理评价器信息
     */
    BdcSlPjqDO insertBdcSlPjq(BdcSlPjqDO bdcSlJyxxDO);

    /**
     * @param bdcSlJyxxDO 不动产受理交易信息
     * @return 保存数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不动产受理评价器信息
     */
    Integer updateBdcSlPjq(BdcSlPjqDO bdcSlJyxxDO);
    
    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlPjqDTO 不动产受理评价器DTO
     * @return: List<BdcSlPjqDTO> 受理评价统计信息
     * @description 分组查询评价统计信息
     */
    Page<BdcSlPjqDO> listBdcSlPjTjByPage(BdcSlPjqDTO bdcSlPjqDTO,Integer pageSize, Integer pageNumber);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlPjqQO 不动产受理评价器DTO
     * @return: List<BdcSlPjqDTO> 受理评价统计信息
     * @description 分组分组查询评价统计信息
     */
    Page<BdcSlPjqDTO> listGroupBdcSlPjTjByPage(BdcSlPjqQO bdcSlPjqQO,Integer pageSize, Integer pageNumber);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlPjqQO 不动产受理评价器DTO
     * @return: List<BdcSlPjqDTO> 受理评价统计信息
     * @description 分组查询评价统计信息
     */
    List<BdcSlPjqDTO> listGroupBdcSlPjTj(BdcSlPjqQO bdcSlPjqQO);

    /**
      * @param gzlslid 工作流实例ID
      * @param taskid 任务ID
      * @return 评价器信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据配置调用不同评价器
      */
    SlymPjqDTO commonPj(String gzlslid, String taskid,String clientip);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 摩科人证对比
     */
    Object mkrzdb(String qlrmc, String qlrzjh, String gzlslid,String clientip);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置调用不同人证对比
     */
    Object commonRzdb(String qlrmc, String qlrzjh,String gzlslid,String clientip);

    /**
     * @param slymPjqDTO 评价结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存评价记录
     */
    void savePjjl(SlymPjqDTO slymPjqDTO, UserDto userDto);

    /**
     * @param gzlslid 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 摩科签字
     */
    Object mkqz(String qlrmc, String qlrzjh,String gzlslid,String clientip);


    /**
     * @param processInsId 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 工作流事件，推送评价给省厅
     */
    Object mktspj(String processInsId,String taskId,String clientip);

    /**
     * @param
     * @return 评价器信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 摩科回传已签字文件到附件中心
     */
    Object mkqzhc(BdcMkqmDTO bdcMkqmDTO) throws IOException;

}
