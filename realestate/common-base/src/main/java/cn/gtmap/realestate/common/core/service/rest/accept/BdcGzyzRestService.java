package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcXzWlzsDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcXzXmDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description 规则验证
 */
public interface BdcGzyzRestService {

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @return 验证结果（包含入参）
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 流程规则验证
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/gzyz")
    List<Map<String, Object>> yzBdcgz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO);

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 除流程验证外其他验证,如匹配验证,页面按钮操作验证等
     * @date : 2020/7/10 14:13
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/gzyz/qtyz")
    List<Map<String, Object>> qtyz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcdywybh] 不动产唯一编码 房屋编号或地籍
     * @return: Boolean
     * @description 验证不动产单元号、唯一编号与权籍是否一致
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/bdcdy/{bdcdywybh}")
    Boolean checkBdcdyhAndBdcdywybhConsistent(@PathVariable("bdcdywybh") String bdcdywybh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param processInsId 工作流实例ID
     * @return 规则验证信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证流程的收费状态
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfzt/{processInsId}")
    BdcGzyzVO checkLcSfzt(@PathVariable(name = "processInsId") String processInsId);

    /**
     * @param jbxxid 基本信息ID
     * @return 查封文号数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证查封文号是否重复（批量解封不允许不重复）
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/cjyz/{jbxxid}")
    Integer checkCfwhSfcf(@PathVariable(name = "jbxxid") String jbxxid);

    /**
     * @param qllx 权利类型的逗号分隔字符串
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcdyh 不动产单元号
     * @return: Boolean 验证结果 已生成权利返回 True 否则 False
     * @description 验证该不动产单元在登记系统是否已生成权利
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/bdcdy/scql/{bdcdyh}/{qllx}")
    List<BdcXmDO> checkBdcdyScQl(@PathVariable(name = "bdcdyh") String bdcdyh, @PathVariable(name = "qllx") String qllx);

    /**
     * @param xmid
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 首套房验证;当申请人申请首套房时,验证申请人是否有过买房记录
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/stfyz/{xmid}")
    BdcSlxxDTO checkStfFwtc(@PathVariable(name = "xmid") String xmid);

    /**
     * @param xmid
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 是否房查验证
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sffwcx/{xmid}")
    Boolean checkSfFwcxForYcslByXmid(@PathVariable(name = "xmid") String xmid);

    /**
     * @param bdcdyh  不动产单元
     * @param gzlslid 当前流程
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证当前不动产单元号是否正在办理其他非登记流程
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/qtfdjlc/{bdcdyh}")
    BdcSlJbxxDO checkIsblQtfdjlc(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "gzlslid", required = false) String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param zhbs    组合规则标识
     * @return 提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 非登记流程规则验证:流程转发/退回/
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/gzyz/fdjlc/{gzlslid}/{zhbs}")
    List<BdcGzYzTsxxDTO> fdjlcGzyz(@PathVariable(name = "gzlslid") String gzlslid, @PathVariable(name = "zhbs") String zhbs);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:chneyucheng@gtmap.cn">chneyucheng</a>
     * @description 验证一证多房的情况下，是否都都选全
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/checkYzdfIsInclude/{jbxxid}")
    boolean checkYzdfIsInclude(@PathVariable(name = "jbxxid") String jbxxid);


    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证商品房交易信息权利人是否一致
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/checkQlrSfyz/{jbxxid}")
    boolean checkQlrSfyz(@PathVariable(name = "jbxxid") String jbxxid);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否线上缴费成功 成功状态 sfzt=2&& jkfs=2
     * @date : 2020/3/6 8:39
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfxsjfcg/{gzlslid}")
    boolean checkSfxsJfcg(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前添加购物车的数据的匹配落宗状态
     * @date : 2020/3/13 14:27
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/pplzzt/{jbxxid}")
    Map checkPplzzt(@PathVariable(name = "jbxxid") String jbxxid, @RequestParam(name = "xztzlx") String xztzlx);


    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 用于创建验证当前流程是否存在外联证书
     * @date : 2020/4/15 9:17
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfwlzs/{jbxxid}")
    Integer checkSfwlzs(@PathVariable(name = "jbxxid") String jbxxid);

    /**
     * @param gzlslid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例ID, 验证当前流程是否存在外联证书
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/wlzs/{gzlslid}")
    Integer checkWlzs(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * 用于校验外联土地证是否存在限制状态
     * @param xmid            项目ID
     * @param processDefKey  工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/wltdzXzQl")
    boolean checkWltdzXzQl(@RequestParam(value = "xmid") String xmid, @RequestParam(value = "processDefKey") String processDefKey);

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据bdcdywybh查询权籍数据关系是否一致
     * @date : 2020/6/9 16:56
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/qjgxsfyz/{bdcdyh}")
    Boolean checkQjGxSfyz(@PathVariable(value = "bdcdyh", required = true) String bdcdyh);

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断转移换证流程是否登记小类都有选择
     * @date : 2020/6/30 8:55
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/zyhz/{jbxxid}")
    Boolean checkZyhzDjxl(@PathVariable(value = "jbxxid", required = true) String jbxxid);

    /**
     * 验证当前流程是否是商品房流程
     * @param gzldyid 工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return boolean true：流程为商品房流程 false: 流程为存量房流程或其他流程
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/spflc/{gzldyid}")
    Boolean checkSpfLc(@PathVariable(value = "gzldyid", required = true) String gzldyid);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证选择的承包方发包方是否一致
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/cbffbfyz/{jbxxid}")
    boolean checkCbfFbfSfyz(@PathVariable(name = "jbxxid") String jbxxid);

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证该xm是否满足省直房改办的状态
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfszfgf/{bdcdyh}")
    boolean checkSfszfgf(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证承包方合同对应的所有地块是否全部添加
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/cbfdkqbtj/{jbxxid}")
    boolean checkCbfDkqbtj(@PathVariable(name = "jbxxid") String jbxxid);

    /**
     * @param jbxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证承包经营权做变更或者转移时是否外联了证书
     * @date : 2020/11/4 17:18
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/cbjyqbwl/{jbxxid}")
    boolean checkCbjyqSfwl(@PathVariable(name = "jbxxid") String jbxxid);

    /**
     * @param bdcSlXmDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证外网申请的数据是否和登记一致
     * @date : 2020/11/18 17:18
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/gzyz/checkWwsqDjSjSfyz/{lclx}")
    boolean checkWwsqDjSjSfyz(@RequestBody BdcSlXmDTO bdcSlXmDTO, @PathVariable(name = "lclx") String lclx);

    /**
     * 表单必填项验证
     * <p>与转发时验证表单必填项相同，校验表单配置中的校验SQL</p>
     * @param gzlslid 工作流实例ID
     * @return 校验结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/form/btx/{gzlslid}")
    Object checkFormBtxYz(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 校验结果
     * @description 调用exchange接口验证存量房是否已经存在备案状态合同信息
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/clfwqxxyz/{xmid}")
    Object checkClfWqxxYz(@PathVariable(name = "xmid") String xmid);

    /**
     * @param gzlslid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 校验结果
     * @description 验证收费信息是否已作废
     * <p>验证场景：删除登记数据时，验证收费信息缴费编码不为空且是否作废字段为空的情况，给与删除提示</p>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfzfyz/{gzlslid}")
    Object checkSfzfYz(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 校验结果
     * @description 验证收费信息是否已冲红
     * <p>验证场景：删除登记数据时，验证收费信息缴费编码字段不为空且发票号不为空的情况，给与删除提示</p>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfchyz/{gzlslid}")
    Object checkSfchYz(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param processInsId 工作流实例ID
     * @return boolean true 已完税 false 未完税
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 验证流程的已推送完税状态
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/wszt/{processInsId}")
    Boolean checkLcYtsswzt(@PathVariable(name = "processInsId") String processInsId);

    /**
     * 是否申报核税
     * @param htbh
     * @return boolean true 已申报 false 未申报
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfSbhs/{htbh}")
    boolean checkSfSbhs(@PathVariable(name = "htbh") String htbh);

    /**
     * 获取配置的pplzztBdcqzhBs 用于验证匹配落宗状态
     *
     * @return
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/pplzzt/pplzztBdcqzhBs")
    List pplzztBdcqzhBs();


    /*
     * 根据单元号查询现势产权是否备案
     * */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/wqbazt/{bdcdyh}")
    Object checkWqBazt(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * @param xmid 项目
     * @param spflc 是否商品房流程
     * @return 比对结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证一窗受理比对信息
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/ycsl/bdxx")
    boolean checkYcslBdxx(@RequestParam(value = "xmid") String xmid, @RequestParam(value = "spflc") Boolean spflc);

    /**
     * @param htbh 合同编号
     * @param cxlx 查询类型 spf，clf
     * @return 接口结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 带入合同备案信息
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/htba/dyHtbaxx/{htbh}/{gzldyid}")
    Object drHtbaxx(@PathVariable(name = "htbh") String htbh, @PathVariable(name = "gzldyid") String gzldyid, @RequestParam(name = "cxlx") String cxlx);

    /**
     * 验证受理收件材料必传是否已上传
     *
     * @param gzlslid 工作流实例ID
     * @return 验证结果：null 已全部上传；其余为：存在必传但未传的收件材料
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sjcl/sfsc/{gzlslid}")
    Object checkSjclSfsc(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否资金监管
     * @date : 2021/7/22 15:06
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/zjjg/{xmid}")
    String sfzjjg(@PathVariable(name = "xmid") String xmid);

    /**
     * 验证资金监管是否办理撤销流程
     * <p>资金监管流程办理时，验证流程是否办理过资金监管撤销流程</p>
     * @param xmid 项目ID
     * @return 验证结果：true 已办理撤销流程； false 需办理资金监管撤销流程才能继续办理资金监管
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/zjjg/blcx/{xmid}")
    boolean sfblZjjgcx(@PathVariable(name = "xmid") String xmid);

    /**
     * @param xmid
     * @param htbh
     * @author wangyinghao
     * @description 验证是否资金监管
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/zjjg/ythpt/{yxmid}/{htbh}")
    String sfzjjgYthpt(@PathVariable(name = "yxmid",required = false) String yxmid,
                       @PathVariable(name = "htbh") String htbh);

    /**
     * @param yxmid
     * @author sunyuzhaung
     * @description 验证土地证是否关联商品房首次登记
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/sfpscdj/{yxmid}")
    Integer sfglSpfscdj(@PathVariable(name = "yxmid",required = false) String yxmid);

    /**
     * @param bdcdyh
     * @author sunyuzhaung
     * @description 验证土地证的户室是否存在预告
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/checkTdHsCzyg/{bdcdyh}")
    Object checkTdHsCzyg(@PathVariable(name = "bdcdyh", required = false) String bdcdyh);

    /**
     * @param bdcXzXmDTO 选择项目实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 选择不动产单元验证，验证一证多房数据是否全部选择
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/gzyz/yzdf/all")
    boolean checkXzYzdf(@RequestBody BdcXzXmDTO bdcXzXmDTO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 传入jbxxid对当前流程数据进行权籍的xsd 校验
     * @date : 2022/11/21 9:15
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/qjxsdjy")
    Object qjsjXsdJy(@RequestParam(name = "jbxxid") String jbxxid);

    /**
     * @param bdcXzWlzsDTO 选择外联证书实体
     * @return 验证提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 单元号更正流程,外联证书验证
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/gzyz/dyhgz/wlzs")
    String checkDyhgzWlzs(@RequestBody BdcXzWlzsDTO bdcXzWlzsDTO);
}
