package cn.gtmap.realestate.exchange.service.national;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.MessageModelBdc;

/**
 * @Auther xutao
 * @Date 2018-12-17
 * @Description
 */
public interface NationalAccessUpload {

    /**
     * 上报
     *
     * @param messageModel
     * @return 汇交结果  true 汇交成功  false汇交失败  null未配置或者ftp/sftp连接失败
     */
    Boolean upload(MessageModel messageModel);

    /**
     * 一致性结构上报
     *
     * @param messageModel
     * @return 汇交结果  true 成功  false失败  null未配置
     */
    Boolean upload(MessageModelBdc messageModel);

    /**
     * 上报
     *
     * @param messageModel
     * @return 汇交结果  true 成功  false失败  null未配置
     */
    Boolean checkData(MessageModel messageModel);

    /**
     * @param bizMsgIds 用逗号隔开的多个bizMsgId
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wzj</a>
     * @description 用于台账手动获取响应报文
     */
    void getReponse(String bizMsgIds);

    /**
     * 通过消息处理响应报文
     *
     * @param message
     */
    CommonResponse dealWithResponseForMq(String message);

    /**
     * @param dhList 用逗号隔开的多个dh
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 用于检测ftp或sftp服务器连接状态
     */
    void checkStatus();

}
