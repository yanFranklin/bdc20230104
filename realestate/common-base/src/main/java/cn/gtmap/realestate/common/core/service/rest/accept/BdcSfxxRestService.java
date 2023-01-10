package cn.gtmap.realestate.common.core.service.rest.accept;


import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcLcTsjjsDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQuerySfztDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfjfblResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSftjDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcTsdjfxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYhkkDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxxCzQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJfblQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/02
 * @description  不动产收费信息 Rest 接口服务
 */
public interface BdcSfxxRestService {

    /**
     * 废除二维码信息
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/abolish/ewm")
    void abolishEwm(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 抵押批量缴费流程，废除二维码信息
     * <p>抵押批量缴费流程，多个项目合并一起推送缴费，作废时根据tsid作废</p>
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/abolish/ewm/dyapl")
    void abolishEwmDyapl(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 查询推送缴费结果
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 推送待缴费信息返回结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/cxtsdjfxx")
    BdcTsdjfxxResponseDTO cxtsdjfxx(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 查询财政收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return BdcQuerySfztDTO 查询财政收费状态接口返回结果对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/sfzt")
    BdcQuerySfztDTO querySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 抵押批量查询财政收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return BdcQuerySfztDTO 查询财政收费状态接口返回结果对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/dyapl")
    BdcQuerySfztDTO dyaplQuerySfztByTsid(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 同步收费信息收费状态
     * <p>获取收费信息关联的所有收费项目，判断收费项目的收费状态是否为 已收费。
     * 全部为已收费则收费信息的收费状态为：已收费；否则为：未收费。</p>
     *
     * @param sfxxid
     */
    @GetMapping("/realestate-accept/rest/v1.0/cz/sfxx/sync/sfzt/{sfxxid}")
    void syncSfxxSfzt(@PathVariable(value = "sfxxid") String sfxxid, @RequestParam(value = "jksj", required = false) String jksj);

    /**
     * 退款申请
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 退款申请接口返回结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/tksq")
    Map tksq(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 修改收费项目的收费状态，同步更新收费信息的收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxm/sfzt")
    void modifySfxmSfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 获取登记收费二维码 和 土地收益金二维码
     * @param sfxxid 收费信息ID
     * @return left：登记收费二维码； right: 土地收益金二维码
     */
    @GetMapping("/realestate-accept/rest/v1.0/cz/sfxx/ewm/{sfxxid}")
    Map<String, String> getSfEwmAndTdsyjEwm(@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * 领取发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 发票号信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxm/lqfph")
    String lqfph(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 作废发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxm/zffph")
    void zffph(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 取消发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxm/qxfph")
    void qxfph(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 更改收费信息ID
     * <p>常州缴费书编码作废后，需要更新收费信息ID后，才能继续推送财务</p>
     * @param bdcSfxxCzQOList
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/rechange/sfxxid")
    void rechangeSfxxid(@RequestBody List<BdcSfxxCzQO> bdcSfxxCzQOList);

    /**
     * 更改缴款方式，更新缴款状态、缴费时间
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxm/jkfs")
    void changeJkfsModifySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 更改收费信息，收费项目缴费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/sfxm/sfzt")
    void modifySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 批量更新收费信息收费状态 和 收费项目缴费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/sfxm/sfzt/pl")
    void plModifySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 根据收费信息ID 获取收费项目并根据是否土地收益金进行过滤
     * @param sfxxid  收费信息ID
     * @param sftdsyj 是否土地收益金
     * @return 收费项目信息集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/cz/sfxm/list/{sfxxid}")
    List<BdcSlSfxmDO> queryBdcSlSfxmBySfxxidWithFilter(@PathVariable(value = "sfxxid") String sfxxid, @RequestParam(value = "sftdsyj") boolean sftdsyj);

    /**
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return 收费推送信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询产权收费推送信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/pltssf/cq")
    BdcLcTsjjsDTO listCqTssfDTO(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return 收费推送信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询抵押权收费推送信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/pltssf/dyaq")
    BdcLcTsjjsDTO listDyaqTssfDTO(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 同步未缴费信息
     *
     * @param jftbsj 日期
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxm/wjftb")
    void wjftb(@RequestParam(value = "jftbsj", required = true) String jftbsj);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前流程的合一支付二维码并在页面展现
     * @date : 2022/5/17 16:43
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/hyzf/{gzlslid}/ewm")
    Map queryHyzfEwm(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前流程的合一支付后的缴费状态
     * @date : 2022/5/17 16:43
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/hyzf/{gzlslid}/jfzt")
    void queryHyzfJfzt(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid);

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 缴费办理
     * @date : 2022/5/25 9:43
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/jfbl")
    BdcSfjfblResponseDTO jfbl(@RequestBody BdcSlJfblQO bdcSlJfblQO);

    /**
     * @param bdcSlJfblQO
     * @param sfyj
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询缴费情况
     * @date : 2022/5/26 18:43
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/cxjfqk")
    void cxjfqk(@RequestBody BdcSlJfblQO bdcSlJfblQO, @RequestParam(value = "sfyj") boolean sfyj);

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 非税开票
     * @date : 2022/5/27 9:43
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/fskp")
    Map<String, String> fskp(@RequestBody BdcSlJfblQO bdcSlJfblQO);

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 作废缴款通知书
     * @date : 2022/5/26 18:43
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/zfjktzs")
    Map<String, String> zfjktzs(@RequestBody BdcSlJfblQO bdcSlJfblQO);

    /**
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return 收费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询小微企业收费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/xwqy/sfxx")
    BdcLcTsjjsDTO listXwqySfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 1、更改收费信息减免原因为小微企业、是否收登记费为否
     * 2、修改收费项目登记费实收金额为0
     * 3、重新计算收费信息合计
     *
     * @param bdcSfxxCzQO 不动产收费信息QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/cz/sfxx/jmyy/sfsdjf")
    void modifySfxxJmyyAndSfsdjf(@RequestBody BdcSfxxCzQO bdcSfxxCzQO);

    /*
     * 挂接收费信息功能
     * */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/gj")
    void gjSfxx(@RequestParam(name = "slbh") String slbh, @RequestParam(name = "sfxxid") String sfxxid, @RequestParam(name = "qlrlb", required = false) String qlrlb);

    /**
     * @param
     * @param htbh
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcYhkkDTO>
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/20 15:51
     * @description 获取扣款信息
     **/
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/getYhkkxx")
    List<BdcYhkkDTO> getYhkkxx(@RequestParam(value = "htbh") String htbh,
                               @RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcYhkkDTO>
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 9:00
     * @description 获取税票
     **/
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/hqsp")
    CommonResponse hqsp(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);
}
