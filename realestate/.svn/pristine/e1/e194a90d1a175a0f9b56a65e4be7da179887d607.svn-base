package cn.gtmap.realestate.common.config.mq.enums;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/12/12
 * @description 定义rabbitMq需要的常量
 */
public class RabbitMqEnum {

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param
    * @return
    * @description 定义数据交换方式
    */
    public enum Exchange {
        CONTRACT_FANOUT("CONTRACT_FANOUT", "消息分发"),
        CONTRACT_TOPIC("CONTRACT_TOPIC", "消息订阅"),
        CONTRACT_DIRECT("CONTRACT_DIRECT", "点对点");

        private String code;
        private String name;

        Exchange(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param
     * @return
     * @description 定义队列名称
     */
    public enum QueueName {

        BDCDYZTQUEUE("update-bdcdyzt-queue", "同步不动产单元状态队列"),
        BDCDYSDZTQUEUE("update-bdcdysdzt-queue", "更新不动产单元锁定状态队列"),
        BDCDYXXQUEUE("update-bdcdyxx-queue", "同步不动产单元信息队列"),
        SYNCBDCDYZTQUEUE("sync-bdcdyzt-queue", "匹配同步不动产单元状态队列"),
        HXQLFJQUEUE("hxqlfj-queue", "回写权利附记"),
        BDCDJINSERTAUDITLOGQUEUE("bdc-exchange-insert-audit-log-queue", "exchange日志落表操作队列"),
        SYNCHDHSFEQUEUE("sync-hdhsfe-queue", "核定宗地上所有户室份额"),
        LOGRECORDQUEUE("log-record-queue", "日志记录队列"),
        BDCDBHJSBQUEUE("register-access-queue", "登簿汇交上报数据对比队列"),
        JKGLLOGQUEUE("zdygjlog", "接口管理日志队列"),
        TOPICTEST1("TOPICTEST1", "topic测试队列");

        private String code;
        private String name;

        QueueName(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param
     * @return
     * @description 定义routing_key
     */
    public enum QueueEnum {
        BDCDYXXQUEUE("update-bdcdyxx-queue_key", "同步不动产单元信息队列key"),
        SYNCBDCDYZTQUEUE("sync-bdcdyzt-queue_key", "匹配同步不动产单元状态队列key"),
        BDCDYSDZTQUEUE("update-bdcdysdzt-queue_key", "同步不动产单元锁定状态队列key"),
        HXQLFJQUEUE("hxqlfj-queue_key", "回写权利附记队列key"),
        BDCDJINSERTAUDITLOGQUEUE("bdc-exchange-insert-audit-log-queue_key", "exchange日志落表操作队列key"),
        SYNCHDHSFEQUEUE("sync-hdhsfe-queue_key", "核定宗地上所有户室份额key"),
        LOGRECORDQUEUE("log-record-queue_key", "日志记录队列key"),
        BDCDJDBHJQUNEUE("register-access-queue_key", "登簿汇交上报队列key"),
        TESTTOPICQUEUE1("*.TEST.*", "topic测试队列key");


        private String code;
        private String name;

        QueueEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
