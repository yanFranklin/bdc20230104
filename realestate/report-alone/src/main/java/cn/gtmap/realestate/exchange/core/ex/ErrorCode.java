package cn.gtmap.realestate.exchange.core.ex;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/10/30
 * @description 不动产登记异常错误代码
 */
public interface ErrorCode {

    /**
     * 自定义错误起始值
     */
    int CUSTOM = 100;

    /**
     * 请求成功执行
     */
    int SUCCEED = 0;

    /**
     * 内部异常
     */
    int SERVER_EX = 1;

    /**
     * 空指异常
     */
    int NPE_EX = 2;

    /**
     * 内存溢出错误
     */
    int OOM_ERROR = 3;

    /**
     * 堆栈溢出错误
     */
    int SOF_ERROR = 4;

    /**
     * 功能尚未实现
     */
    int NOT_IMPL = 5;

    /**
     * 不支持此操作
     */
    int UN_SUPPORTED = 6;

    /**
     * 类访问异常
     */
    int CLASS_EX = 7;

    /**
     * IO异常
     */
    int IO_EX = 8;

    /**
     * 网络异常
     */
    int SOCKET_EX = 9;

    /**
     * 数据库访问异常
     */
    int DB_EX = 10;

    /**
     * 缓存异常
     */
    int CACHE_EX = 11;

    /**
     * 状态异常
     */
    int ILLEGAL_STATE = 51;

    /**
     * 非法参数
     */
    int ILLEGAL_ARG = 52;

    /**
     * 非法参数格式
     */
    int ILLEGAL_ARG_FORMAT = 53;

    /**
     * 参数不完整
     */
    int MISSING_ARG = 54;

    /**
     * 参数校验错误
     */
    int VALIDATION_EX = 55;

    /**
     * 实体对象异常
     */
    int ENTITY_EX = 70;

    /**
     * 实体对象未找到
     */
    int ENTITY_NOT_FOUND = 71;

    /**
     * 实体对象已存在
     */
    int ENTITY_EXISTS = 72;

    /**
     * 实体对象缺少对象标识(ID)
     */
    int ENTITY_NO_ID = 73;

    /**
     * 消息队列错误
     */
    int MQ_EX = 80;

    /**
     * 模板错误
     */
    int TPL_EX = 81;

    /**
     * 配置错误
     */
    int CONFIG_EX = 82;

    /**
     * 规则：未找到要验证的组合规则错误
     */
    int RULE_CHECK_EX = 101;
    /**
     * 规则：未找到要验证的子规则错误
     */
    int RULE_CHECK_ZGZ_EX = 105;
    /**
     * 规则：数据流未设置参数
     */
    int RULE_NO_PARAM_EX = 102;
    /**
     * 规则：数据流参数格式配置错误
     */
    int RULE_PARAM_SET_EX = 103;
    /**
     * 规则：规则验证子规则执行报错
     */
    int RULE_CHECK_ERROR = 109;
    /**
     * 规则：规则验证子规则SQL或服务地址为空
     */
    int RULE_NULL_ERROR = 110;
    /**
     * 规则：规则验证 子规则 数据流SQL或服务对应参数值为空
     */
    int RULE_PARAM_NULL_ERROR = 111;
    /**
     * 规则：规则表达式配置错误
     */
    int RULE_CHECK_EXP_ERROR = 112;
    /**
     * 规则：规则表达式执行验证错误
     */
    int RULE_EXP_EXEC_ERROR = 113;
    /**
     * 规则：未定义子规则或数据流空
     */
    int RULE_NO_ZGZ_SJL_ERROR = 114;
    /**
     * 查询： 以人查房参数证件号为空
     */
    int INQUIRY_ZFXX_PARAM_NO_ZJH = 104;
    /**
     * 查询： 以人查房参数权利人名称为空
     */
    int INQUIRY_ZFXX_PARAM_NO_QLRMC = 106;

    /**
     * 业务逻辑校验失败
     */
    int YW_JYSB = 107;
}
