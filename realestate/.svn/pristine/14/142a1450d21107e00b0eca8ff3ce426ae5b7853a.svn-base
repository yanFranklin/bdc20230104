package cn.gtmap.realestate.exchange.service;

import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService;

import java.util.List;

public interface AccessModelHandlerService {
    /**
     * @param bdcXmDO
     * @param bdcXmDOList 用于处理一些逻辑，为了防止每次都查就改到外面传，可以不传。
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理自动汇交具体方法
     */
    void access(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList);

    /**
     * @param bdcXmDO
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据项目编码获取相应的策略服务
     */
    NationalAccessXmlService getAccessXmlService(BdcXmDO bdcXmDO);

    /**
     * @param nationalAccessXmlService
     * @param xmid
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 获取上报数据对象模型
     */
    MessageModel getMessageModel(NationalAccessXmlService nationalAccessXmlService, String xmid);

    /**
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 针对已有messageModel结构的场景  汇报上交
     */
    boolean autoAccessByMessageModel(MessageModel messageModel);

    /**
     * @param bdcXmDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    String getBizMsgId(BdcXmDO bdcXmDO);

    /**
     * @param qxdm
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据区县代码 获取流水号
     */
    String getBizMsgId(String qxdm);

    /**
     * @param messageModel
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据上报数据对象模型生成 xml 字符串
     */
    String getAccessXML(MessageModel messageModel);

    /**
     * @param xmidList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目主键List 回报单个项目
     */
    void autoAccessByXmidList(List<String> xmidList);

    /**
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据时间区间获取要上报的xmidList
     */
    List<String> getXmListToAccessByTimeInterval(String startDate, String endDate, String type, String xmly);

    /**
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据时间区间上报
     */
    void autoAccessByTimeInterval(List<String> xmidList);

    /**
     * @param bdcXmDO
     * @param sfgzyz  是否需要验证
     * @param sfjlrz  是否记录日志
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否上报，特殊逻辑判断
     * @date : 2022/6/23 15:49
     */
    boolean sfsb(BdcXmDO bdcXmDO, String sfgzyz, Boolean sfjlrz);

    /**
     * 根据已生成的messageModel进行接入数据保存
     *
     * @param
     * @return
     * @Date 2022/6/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void saveBdcAccessByMessageModel(BdcAccessLog bdcAccessLog, Integer bs, MessageModel messageModel, String xyxx);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存接入表数据
     * @date : 2022/7/19 8:51
     */
    void saveBdcAccess(BdcAccessLog bdcAccessLog, Integer bs, BdcXmDO bdcXmDO, String xyxx);

}
