package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.MessageModelBdc;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzVO;
import cn.gtmap.realestate.exchange.core.dto.common.MessageModelOld;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;

import java.util.List;

/**
 * 处理汇交数据model
 *
 * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
 * @version 1.0, 2018/12/13
 * @description
 */
public interface AccesssModelHandlerService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmDO
     * @return java.lang.String
     * @description
     */
    String getBizMsgId(BdcXmDO bdcXmDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qxdm
     * @return java.lang.String
     * @description 根据区县代码 获取流水号
     */
    String getBizMsgId(String qxdm);

    /**
     * @param bdcXmDO
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据项目编码获取相应的策略服务
     */
    NationalAccessXmlService getAccessXmlService(BdcXmDO bdcXmDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywdm
     * @return cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService
     * @description 根据YWDM 获取相应的策略服务
     */
    NationalAccessXmlService getAccessXmlServiceByYwdm(String ywdm);

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
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据上报数据对象模型生成 xml 字符串
     */
    String getAccessXML(MessageModel messageModel);

    /**
     * @param messageModel
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据上报数据对象模型生成 xml 字符串
     */
    String getAccessXMLFroYzx(MessageModelBdc messageModel);

    String getAccessXMLOld(MessageModelOld messageModel);


    /**
     * @param pId
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流实例ID 查询实例下所有项目回报
     */
    void autoAccessByProcessInsId(String pId);

    /*
     * 根据gzlslid外联注销项目接入
     * */
    void autoAccessWlzxXm(String gzlslid);

    /**
     * @param xmid
     * @param withAllXm
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据项目主键 回报单个项目
     */
    void autoAccessByXmid(String xmid, Boolean withAllXm);

    /**
     * @param xmidList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目主键List 回报单个项目
     */
    void autoAccessByXmidList(List<String> xmidList);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上报销账台账上报并更新销账状态
     * @date : 2022/12/13 16:50
     */
    void actoAccessBySbxz(List<SbxzVO> sbxzVOList);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联项目上报
     * @date : 2022/12/6 13:56
     */
    void autoAccessWlxmByXmidList(List<String> xmidList);

    /**
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据时间区间获取要上报的xmidList
     */
    List<String> getXmListToAccessByTimeInterval(String startDate, String endDate, String type, String xmly);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @return void
     * @description 根据时间区间上报
     */
    void autoAccessByTimeInterval(List<String> xmidList);

    /**
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 针对已有messageModel结构的场景  汇报上交
     */
    boolean autoAccessByMessageModel(MessageModel messageModel);

    boolean autoAccessByMessageModelOld(MessageModelOld messageModel);

    /**
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 复制dataModel
     */
    void filterDataModel(MessageModel messageModel);

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据项目id单独上报
     * @Date 2022/5/5
     * @Param bdcXmDO
     * @Param bdcXmDOList
     **/
    void singleAccess(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList);

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 更新wxzbwxx 表的xzzt
     * @date : 2022/6/22 10:37
     */
    void updateWxzBwxxXzzt(List<String> xmidList, String xzzt);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据主键id更新销账状态
     * @date : 2022/9/29 9:06
     */
    void updateXzztByid(List<String> idList, String xzzt);

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

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联数据保存接入表
     * @date : 2022/12/2 15:12
     */
    void saveWlxmAccess(BdcAccessLog bdcAccessLog, Integer bs, BdcXmDO bdcXmDO, String xyxx, String gxid);

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新未接入表数据处理状态为1
     * @date : 2022/6/30 13:55
     */
    void updateWjrClzt(List<String> xmidList, Integer clzt);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 具体开始上报逻辑组织数据等
     * @date : 2022/11/18 11:07
     */
    void startAccess(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新销账状态
     * @date : 2022/11/25 9:06
     */
    void updateXzzt();

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新存量数据销账状态
     * @date : 2022/11/28 10:47
     */
    void updateClsjXzzt();

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断外联项目是否上报
     * @date : 2022/12/8 17:01
     */
    boolean wlxmSfsb(BdcXmDO bdcXmDO, String sfgzyz, Boolean sfjlrz, String gxid);


}
