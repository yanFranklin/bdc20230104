package cn.gtmap.realestate.portal.ui.utils;


/**
 * 常量类
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */
public class Constants {
    /**
     * 日志详情
     */
    public static final String DETAILS = "details";
    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";
    /**
     * 日志 IP
     */
    public static final String REMOTEADDR = "remoteaddr";
    /**
     * 账户在其他 ip 登录 消息类型
     */
    public static final String USER_DISCONNECT = "USER_DISCONNECT";

    private Constants() {

    }

    /**
     * 参数字符串 —— xmid
     */
    public static final String PARAM_XMID = "#{xmid}";
    /**
     * 参数字符串 —— gzlslid
     */
    public static final String PARAM_GZLSLID = "#{gzlslid}";
    /**
     * 参数字符串 —— taskId
     */
    public static final String PARAM_TASKID = "#{taskId}";

    /**
     * 任务转发参数 redis-key 前缀名
     */
    public static final String TASK_ZFCS_REDIS_PREFIX= "TASK_ZFCS_";

    /**
     * 自定义日志类型：流程创建操作
     */
    public static final String LOG_ACTION_PROCESS_CREATE = "PROCESS_CREATE";
    /**
     * 自定义日志类型：流程挂起
     */
    public static final String LOG_ACTION_PROCESS_HANG = "PROCESS_HANG";
    /**
     * 自定义日志类型：流程激活
     */
    public static final String LOG_ACTION_PROCESS_ACTIVE = "PROCESS_ACTIVE";
    /**
     * 自定义日志类型：流程撤销
     */
    public static final String LOG_ACTION_PROCESS_REVOKE = "PROCESS_REVOKE";
    /**
     * 自定义日志类型：流程加急
     */
    public static final String LOG_ACTION_PROCESS_URGENT = "PROCESS_URGENT";
    /**
     * 自定义日志类型：流程取消加急
     */
    public static final String LOG_ACTION_PROCESS_CANCEL_URGENT = "PROCESS_CANCEL_URGENT";
    /**
     * 自定义日志类型：任务终止操作
     */
    public static final String LOG_ACTION_PROCESS_STOP = "PROCESS_STOP";

    /**
     * 自定义日志类型：审核退回任务
     */
    public static final String LOG_ACTION_TASK_BACK_CHECK = "TASK_BACK_CHECK";
    /**
     * 自定义日志类型：任务删除操作
     */
    public static final String LOG_ACTION_TASK_DELETE = "TASK_DELETE";
    /**
     * 自定义日志类型：任务取回操作
     */
    public static final String LOG_ACTION_TASK_RETRIEVE = "TASK_RETRIEVE";
    /**
     * 自定义日志类型：任务加急
     */
    public static final String LOG_ACTION_TASK_URGENT = "TASK_URGENT";
    /**
     * 自定义日志类型：任务取消加急
     */
    public static final String LOG_ACTION_TASK_CANCEL_URGENT = "TASK_CANCEL_URGENT";
    /**
     * @description 消息附件文件夹
     */
    public static final String MSG_FILE = "消息附件";
    /**
     * 机构类型
     */
    public static final String TYPE_ORG = "org";
    /**
     * 用户类型
     */
    public static final String TYPE_USER = "user";

}
