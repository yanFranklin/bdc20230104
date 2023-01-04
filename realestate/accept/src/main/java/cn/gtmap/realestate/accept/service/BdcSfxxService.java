package cn.gtmap.realestate.accept.service;

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

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/02
 * @description 不动产收费信息接口服务
 */
public interface BdcSfxxService {

    /**
     * 废除二维码信息
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void abolishEwm(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 抵押批量缴费流程，废除二维码信息
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void abolishEwmDyapl(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 根据收费信息ID查询收费信息并推送待缴费信息
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    BdcTsdjfxxResponseDTO cxtsdjfxx(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 查询财政收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 查询财政收费状态接口返回结果对象
     */
    BdcQuerySfztDTO querySfzt(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 抵押批量缴费查询财政收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 查询财政收费状态接口返回结果对象
     */
    BdcQuerySfztDTO dyaplQuerySfztByTsid(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 同步收费信息表中的收费状态
     * <p>根据收费信息ID，查询所有收费项目，判断所有收费项目收费状态为 已收费时，
     * 更新收费信息表收费状态为已收费，否则为未收费</p>
     *
     * @param sfxxid 收费信息id
     */
    void syncSfxxSfzt(String sfxxid, String jksj);

    /**
     * 退款申请
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 退款申请接口返回结果
     */
    Map tksq(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 修改收费项目的收费状态，同步更新收费信息的收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void modifySfxmSfzt(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 获取登记收费二维码 和 土地收益金二维码
     * @param sfxxid 收费信息ID
     * @return left：登记收费二维码； right: 土地收益金二维码
     */
    Map<String, String> getSfEwmAndTdsyjEwm(String sfxxid);

    /**
     * 领取发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 领取发票号
     */
    String lqfph(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 作废发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void zffph(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 取消发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void qxfph(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 更新收费信息ID
     * @param bdcSfxxCzQOList 不动产受理收费信息常州QO集合
     */
    void rechangeSfxxid(List<BdcSfxxCzQO> bdcSfxxCzQOList);

    /**
     * 更改缴款方式修改收费状态、缴款时间
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void changeJkfsModifySfzt(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 修改收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void modifySfzt(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 批量更新收费信息收费状态 和 收费项目缴费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    void plModifySfzt(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * 根据收费信息ID查询收费项目信息, 并根据土地收益金进行过滤
     * @param sfxxid  收费信息ID
     * @param sftdsyj 是否土地收益金
     * @return 收费项目信息集合
     */
    List<BdcSlSfxmDO> queryBdcSlSfxmBySfxxidWithFilter(String sfxxid, boolean sftdsyj);

    /**
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return 收费推送信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询产权收费推送信息
     */
    BdcLcTsjjsDTO listCqTssfDTO(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return 收费推送信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询抵押权收费推送信息
     */
    BdcLcTsjjsDTO listDyaqTssfDTO(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 同步未缴费信息
     *
     * @param jftbsj 日期
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void wjftb(String jftbsj);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当前流程合一支付的二维码信息
     * @date : 2022/5/17 16:46
     */
    Map querHyzfEwm(String gzlslid, String xmid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合一支付的缴费状态并更新
     * @date : 2022/5/17 17:00
     */
    void queryHyzfJfzt(String gzlslid, String xmid);

    /**
     * @param bdcSlJfblQO
     * @return
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 缴费办理
     */
    BdcSfjfblResponseDTO jfbl(BdcSlJfblQO bdcSlJfblQO);

    /**
     * @param bdcSlJfblQO
     * @param sfyj
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询缴费情况
     * @date : 2022/5/26 18:43
     */
    void cxjfqk(BdcSlJfblQO bdcSlJfblQO, boolean sfyj);

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 非税开票
     * @date : 2022/5/26 18:43
     */
    Map<String, String> fskp(BdcSlJfblQO bdcSlJfblQO);

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 作废缴款通知书
     * @date : 2022/5/26 18:43
     */
    Map<String, String> zfjktzs(BdcSlJfblQO bdcSlJfblQO);

    /**
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return 查询收费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询小微企业收费信息
     */
    BdcLcTsjjsDTO listXwqySfxx(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 1、更改收费信息减免原因为小微企业、是否收登记费为否
     * 2、修改收费项目登记费实收金额为0
     * 3、重新计算收费信息合计
     *
     * @param bdcSfxxCzQO 不动产收费信息QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void modifySfxxJmyyAndSfsdjf(BdcSfxxCzQO bdcSfxxCzQO);

    /**
     * @param slbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 挂接收费信息
     * @date : 2022/8/1 14:08
     */
    void gjSfxx(String slbh, String sfxxid, String qlrlb);

    /**
     * @param
     * @param htbh
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcYhkkDTO>
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/20 15:50
     * @description 获取扣款信息
     **/
    List<BdcYhkkDTO> getYhkkxx(String htbh, String gzlslid);

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcYhkkDTO>
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 9:01
     * @description 获取税票
     **/
    CommonResponse hqsp(String gzlslid, String qlrlb);


}
