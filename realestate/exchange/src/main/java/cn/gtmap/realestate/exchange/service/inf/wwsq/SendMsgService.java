package cn.gtmap.realestate.exchange.service.inf.wwsq;

import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/19
 * @description
 */
public interface SendMsgService {
    /**
     * 发送短信
     * @param map
     * @param mainid
     */
    void sendMessage(Map map, String mainid);

    /**
     * 发送短信，不走配置表
     * @param data
     * @param phone
     * @param msgType
     */
    public void smsMsg(Map<String, String> data, String phone, String msgType);

    /**
     * 记录日志
     *
     * @param parma     替换的参数
     * @param status    需要发送短信方法的状态值
     * @param phone     电话号
     * @param issuccess 是否发送成功
     * @param errorMsg  失败信息
     */
    public void saveMsgLog(String parma, String status, String phone, String issuccess, String errorMsg);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证联系电话的正确性
     * @date : 2022/11/29 14:25
     */
    boolean checkPhone(String phone);
}
