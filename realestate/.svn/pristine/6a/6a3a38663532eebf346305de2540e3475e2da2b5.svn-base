package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlYjxxQO;

import java.util.List;

/**
 * <p> 提供邮寄信息的增加、删除、修改、查询的接口服务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/12.
 * @description 不动产受理邮寄信息接口服务
 */
public interface BdcSlYjxxService {
    /**
     * 新增不动产受理邮寄信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlYjxxDO 不动产受理邮寄信息DO
     * @return {@link BdcSlYjxxDO} 邮寄信息DO
     */
    BdcSlYjxxDO insertBdcSlYjxx(BdcSlYjxxDO bdcSlYjxxDO);
    /**
     * 根据邮件信息ID修改不动产受理邮寄信息
     * <p> 根据邮件信息ID更新不动产受理邮寄信息，其中不更新字段为空和｛@code null｝的值 <br>
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlYjxxDO 不动产受理邮寄信息DO
     * @return void 无返回值
     */
    void updateBdcSlYjxxByYjxxId(BdcSlYjxxDO bdcSlYjxxDO);
    /**
     * 更新不动产受理邮寄信息
     * @param json 不动产邮寄信息
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>、
     * @return 修改数量
     * @description 更新受理项目
     */
    Integer updateBdcYjxx(String json);

    /**
     * 根据邮件信息ID删除不动产受理邮寄信息
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param yjxxId 不动产受理邮件信息ID
     * @return void 无返回值
     */
    void removeYjxxByYjxxId(String yjxxId);
    /**
     * 根据流程实例ID信息删除
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return void 无返回值
     */
    void removeYjxxByGzlslid(String gzlslid);
    /**
     * 根据工作流实例id查询不动产受理邮寄信息
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流程实例ID
     * @return List<BdcSlYjxxDO> 不动产受理邮寄信息集合
     */
    List<BdcSlYjxxDO> queryYjxxByGzlslid(String gzlslid);

    /**
     * 查询邮寄信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param bdcSlYjxxQO 查询参数
     * @return List<BdcSlYjxxDTO> 不动产受理邮寄信息集合
     */
    List<BdcSlYjxxDO> listBdcSlYjxx(BdcSlYjxxQO bdcSlYjxxQO);

    /**
     * 根据邮寄信息ID查询不动产受理邮寄信息
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param yjxxId 邮件信息ID
     * @return BdcSlYjxxDO 不动产受理邮寄信息
     */
    BdcSlYjxxDO queryYjxxByYjxxId(String yjxxId);
    /**
     * 初始化不动产邮寄信息
     * <p> 初始化不动产邮寄信息，当收件人不为空时，通过工作流实例ID获取权利人信息，多个权利人时，默认采用第一权利人。
     *
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return BdcSlYjxxDO 不动产邮寄信息DO
     */
    BdcSlYjxxDO initBdcYjxx(String gzlslid);

    /**
     * 互联网创建流程时，生成邮寄信息数据
     * @param bdcSlYjxxDO  互联网传递的收件人信息
     * @author <a href="mailtoyaoyi@gtmap.cn">yaoyi</a>
     * @return void
     */
    void initBdcYjxx(BdcSlYjxxDO bdcSlYjxxDO);

    /**
     * 推送邮寄信息数据至EMS
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 推送结果
     */
    String pushYjxxToEms(String processInsId,String currentUserName);

}
