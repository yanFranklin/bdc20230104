package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.exchange.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.core.domain.exchange.MessageModel;

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
     * @return 汇交结果  true 成功  false失败  null未配置
     */
    Boolean upload(MessageModel messageModel);

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
     * @param message
     */
    CommonResponse dealWithResponseForMq(String message);

}
