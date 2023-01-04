package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;

/**
 * 处理汇交数据model
 *
 * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
 * @version 1.0, 2018/12/13
 * @description
 */
public interface ShareModelHandlerService {


    /**
     * @param nationalAccessXmlService
     * @param xmid
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 获取共享的数据对象模型
     */
    DataModel getShareDataModel(NationalAccessXmlService nationalAccessXmlService, String xmid);

    /**
     * @param xmid
     * @description 根据项目id共享受理节点djfdjywxx
     */
    void shareRunningXmByXmid(String xmid);

    /**
     * @param processInsId
     * @description 根据项目主键共享同一流程所有受理节点项目
     */
    void shareRunningAllXmByProcessInsId(String processInsId);

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目
     */
    void shareXmByXmid(String xmid);

    /**
     * @param processInsId
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目
     */
    void shareAllXmByProcessInsId(String processInsId);

    /**
     * @param xmid 项目主键
     * @param xmid status
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目状态，可用于项目删除，退回
     */
    void shareXmStatusByXmid(String xmid, String status);

    /**
     * 根据项目主键共享当前项目状态，可用于项目删除，退回
     *
     * @param xmid   项目主键
     * @param xmid   status
     * @param reason reason
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void shareXmStatusByXmid(String xmid, String status, String reason);

    /**
     * @param processInsId
     * @param status       status
     * @param reason       reason
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享同一流程所有项目状态，可用于项目删除，退回
     */
    void shareAllXmStatusByProcessInsId(String processInsId, String status, String reason);

    /**
     * @param xmid 项目主键
     * @param rzid
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据项目主键补偿共享当前项目
     */
    CommonResponse shareXmByXmidForCompensate(String xmid,String rzid);
}
