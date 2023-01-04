package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.csjzzxx.sfm.SfmResponse;
import cn.gtmap.realestate.common.core.qo.accept.CsjZzxxQO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/13.
 * @description 权利人外部接口方法
 * <p>提取原有<code>SlymQlrController<code/>中的方法，提供对外操作权利人信息的通用接口服务</p>
 */
@Validated
public interface BdcSlQlrService {

    /**
     * 修改权利人共有方式接口
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param:  bdcQlrList 不动产权利人集合,  gzlslid 工作流实例ID, xmid 项目ID, lclx 流程类型
     * @return: List<BdcQlrDO> 不动产权利人DO集合
     */
    List<BdcQlrDO> modifyBdcQlrGyfs(List<BdcQlrDO> bdcQlrList, String gzlslid, String xmid, String lclx) throws Exception;
    
    /**
     * 批量修改权利人（用于批量页面）
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: json 权利人集合JSON
     * @param: processInsId 流程实例ID
     * @param: xmid 项目ID
     * @return: Integer 修改数量
     */
    Integer updatePlBdcQlr(String json,String processInsId, String xmid) throws Exception;

    /**
     * @description 批量修改权利人(用于批量组合页面)
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: json 权利人集合JSON
     * @param: processInsId 流程实例ID
     * @param: xmid 项目ID
     * @param: djxl 登记小类
     * @return: List<BdcQlrDO> 不动产权利人DO集合
     */
    List<BdcQlrDO> updatePlzhBdcQlr(String json, String processInsId, String xmid, String djxl) throws Exception;

    /**
     * @param json 权利人集合
     * @param processInsId 工作流实例ID
     * @param djxl 登记小类
     * @return 新增权利人
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增权利人（批量组合）
     */
    BdcQlrDO insertPlzhBdcQlr(String json, String processInsId,String djxl,boolean syncTdsyqr);

    /**
     * @param qlrid 权利人ID
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量删除权利人(批量组合)
     */
    void deletePlzhBdcQlr(String qlrid, String processInsId) throws Exception;


    /**
     * 新增、修改、删除权利人时，同步土地使用权人
     *
     * @param json         权利人集合
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void syncTdsyqr(String json, String processInsId);


    /**
     * @param yyzzm 营业执照码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通页面输入营业执照调用接口查询权利人信息和附件信息
     * @date : 2021/5/11 10:53
     */
    void importYyzz(@NotBlank(message = "营业执照码不可为空") String yyzzm, @NotBlank(message = "工作流实例id不可为空") String gzlslid, String djxl) throws IOException;


    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询长三角证照信息并导入附件
     * @date : 2022/5/11 10:11
     */
    String querCsjZzxx(CsjZzxxQO csjZzxxQO);

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码查询长三角证照信息并带入权利人信息
     * @date : 2022/5/12 8:35
     */
    String queryCsjZzxxBySfm(CsjZzxxQO csjZzxxQO);


    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码
     * @date : 2022/5/12 8:35
     */
    List<SfmLiscenseInfoDTO> queryCsjZzxxBySfmCx(CsjZzxxQO csjZzxxQO);

    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码带入权利人信息
     * @date : 2022/5/12 8:35
     */
    void queryCsjZzxxBySfmAdd(CsjZzxxQO csjZzxxQO);


    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码带入附件
     * @date : 2022/5/12 8:35
     */
    void queryCsjZzxxBySfmAddFj(CsjZzxxQO csjZzxxQO);

}
