package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcXzWlzsDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcXzXmDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description 规则验证服务
 */
public interface BdcGzyzService {

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @return 验证结果（包含入参）
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 流程规则验证
     */
    List<Map<String, Object>> yzBdcgz(BdcGzYzQO bdcGzYzQO);

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 除流程验证外其他验证, 如匹配验证, 页面按钮操作验证等
     * @date : 2020/7/10 14:15
     */
    List<Map<String, Object>> qtyz(BdcGzYzQO bdcGzYzQO);

    /**
     * @param jbxxid 基本信息id
     * @return 查封文号数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建时验证查封文号是否存在不重复的
     */
    Integer yzCfwhSl(String jbxxid);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcdyh, qllx] 不动产隔字符串
     * @return: List<BdcXmDO> 符合权利类型的现势不动产单元
     * @description 验证该不动产单元在登记系统是否已生成权利
     */
    List<BdcXmDO> checkBdcdyScQl(String bdcdyh, String qllx);

    /**
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid验证一窗受理是否已经房查
     */
    Boolean checkSfFwcxForYcslByXmid(String xmid);

    /**
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一窗首套房验证-验证买房是否
     */
    BdcSlxxDTO checkStfFwtc(String xmid);

    /**
     * @param bdcdyh  不动产单元
     * @param gzlslid 当前流程
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证当前不动产单元号是否正在办理其他非登记流程
     */
    BdcSlJbxxDO checkIsblQtfdjlc(String bdcdyh, String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param zhbs    组合规则标识
     * @return 提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 非登记流程规则验证:流程转发/退回/
     */
    List<BdcGzYzTsxxDTO> fdjlcGzyz(String gzlslid, String zhbs);


    /**
     * @param jbxxid 基本信息id
     * @return 是否选全
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证一证多房的情况下，是否选全
     */
    boolean checkYzdfIsInclude(String jbxxid);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证商品房交易信息权利人是否一致
     */
    boolean checkQlrSfyz(String jbxxid);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否线上缴费成功 成功状态 sfzt=2&& jkfs=2
     * @date : 2020/3/6 8:56
     */
    boolean checkSfxsJfcg(String gzlslid);

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前添加购物车的数据的匹配落宗状态
     * @date : 2020/3/13 15:17
     */
    Map checkPplzzt(String jbxxid, String xztzlx);

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证当前选择的项目是否外联了证书
     * @result 1 表示外联了证书 0 表示没有外联证书 2 表示外联了证书但没有注销
     * @date : 2020/4/15 9:19
     */
    int checkSfwlzs(String jbxxid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证当前选择的项目是否外联了证书
     * @result 1 表示外联了证书 0 表示没有外联证书 2 表示外联了证书但没有注销
     */
    int checkWlzs(String gzlslid);

    /**
     * 用于校验外联土地证是否存在限制状态
     * @param xmid            项目ID
     * @param processDefKey  工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    boolean checkWltdzXzQl(String xmid, String processDefKey);

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证权籍数据关系是否一致
     * @date : 2020/6/9 16:59
     */
    boolean checkQjGxSfyz(String bdcdyh);

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证转移换证流程的登记小类是否选择了多个
     * @date : 2020/6/30 8:59
     */
    boolean checkZyhzDjxl(String jbxxid);

    /**
     * 验证当前流程是否是商品房流程
     * @param gzldyid  工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return boolean true：流程为商品房流程 false: 流程为存量房流程或其他流程
     */
    boolean checkSpfLc(String gzldyid);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证选择的承包方发包方是否一致
     */
    boolean checkCbfFbfSfyz(String jbxxid);

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证该xm是否满足省直房改办的状态
     */
    boolean checkSfszfgf(String bdcdyh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证承包方合同对应的所有地块是否全部添加
     */
    boolean checkCbfDkqbtj(String jbxxid);


    /**
     * @param jbxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证承包经营权在转移或者变更时是否外联了经营权证书
     * @date : 2020/11/4 17:21
     */
    boolean checkCbjyqSfwlzs(String jbxxid);

    /**
     * @param bdcSlXmDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证外网申请的数据是否和登记一致
     * @date : 2020/11/18 17:18
     */

    boolean checkWwsqDjSjSfyz(BdcSlXmDTO bdcSlXmDTO,String lclx);

    /**
     * 表单必填项验证
     * <p>与转发时验证表单必填项相同，校验表单配置中的校验SQL</p>
     * @param gzlslid 工作流实例ID
     * @return 校验结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object checkFormBtxYz(String gzlslid);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 校验结果
     * @description 调用exchange接口验证存量房是否已经存在备案状态合同信息
     */
    Object checkClfWqxxYz(String xmid);

    /**
     * @param gzlslid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 校验结果
     * @description 验证收费信息是否已作废
     */
    Object checkSfzfYz(String gzlslid);
    /**
     * @param gzlslid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 校验结果
     * @description 验证收费信息是否已冲红
     */
    Object checkSfchYz(String gzlslid);

    /**
     * @param processInsId 工作流实例ID
     * @return boolean true 已完税 false 未完税
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 验证流程的已推送完税状态
     */
    Boolean checkLcYtsswzt(String processInsId);

    /**
     * 是否申报核税
     * @param htbh 合同编号
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    Boolean checkSfSbhs(String htbh);

    /**
     * 获取配置的pplzztBdcqzhBs 用于验证匹配落宗状态
     *
     * @return
     */
    List pplzztBdcqzhBs();

    /*
     * 根据单元号查询现势产权是否备案
     * */
    Object checkWqbaZt(String bdcdyh);

    /**
      * @param xmid 项目
      * @return 比对结果
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 验证一窗受理比对信息
      */
    boolean checkYcslBdxx(String xmid,Boolean spflc);

    /**
     * @param htbh    合同编号
     * @param gzldyid 工作流定义id
     * @return 接口结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询需要带入合同备案信息
     */
    Object drHtbaxx(String htbh, String gzldyid, String cxlx);

    /**
     * 验证受理收件材料必传是否已上传
     *
     * @param gzlslid 工作流实例ID
     * @return 验证结果：null 已全部上传；其余为：存在必传但未传的收件材料
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object checkSjclSfsc(String gzlslid);

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断当前数据是否做过资金监管
     * @date : 2021/7/22 15:19
     */
    String checkSfzjjg(String xmid);

    /**
     * 验证资金监管是否办理撤销流程
     * <p>资金监管流程办理时，验证流程是否办理过资金监管撤销流程</p>
     * @param xmid 项目ID
     * @return 验证结果：true 已办理撤销流程； false 需办理资金监管撤销流程才能继续办理资金监管
     */
    boolean sfblZjjgcx(String xmid);

    /**
     * 验证是否资金监管,优先查询常州市存量房交易一体化平台
     * @param xmid
     * @param htbh
     */
    String sfzjjgYthpt(String xmid,String htbh);

    /**
     * @param yxmid
     * @author sunyuzhaung
     * @description 验证土地证是否关联商品房首次登记
     */
    int sfglSpfscdj(String yxmid);

    /**
     * @param bdcdyh
     * @author sunyuzhaung
     * @description 验证土地证的户室是否存在预告
     */
    Object checkTdHsCzyg(String bdcdyh);

    /**
     * @param bdcXzXmDTO 选择项目实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 选择不动产单元验证，验证一证多房数据是否全部选择
     */
    boolean checkXzYzdf(BdcXzXmDTO bdcXzXmDTO);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 权籍数据xsd校验
     * @date : 2022/11/21 9:29
     */
    Object qjsjXsdJy(String jbxxid);

    /**
     * @param bdcXzWlzsDTO 选择外联证书实体
     * @return 验证提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 单元号更正流程,外联证书验证
     */
    String checkDyhgzWlzs(BdcXzWlzsDTO bdcXzWlzsDTO);
}
