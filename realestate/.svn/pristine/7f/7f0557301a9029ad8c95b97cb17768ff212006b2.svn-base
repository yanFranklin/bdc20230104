package cn.gtmap.realestate.engine.util;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/6
 * @description 常量类
 */
public class Constants {
    /**
     * 正则表达式：数字
     */
    public static final String REGEX_NUMBER = "[0-9]+";
    /**
     * 布尔类型对应字符串：TRUE
     */
    public static final String BOOLEAN_CAP_TRUE = "TRUE";
    /**
     * 布尔类型对应字符串：FALSE
     */
    public static final String BOOLEAN_CAP_FALSE = "FALSE";
    /**
     * 布尔类型对应字符串：true
     */
    public static final String BOOLEAN_LOW_TRUE = "true";
    /**
     * 布尔类型对应字符串：false
     */
    public static final String BOOLEAN_LOW_FALSE = "false";
    /**
     * 操作符数组
     */
    public static final String[] OPERATORS = {"包含", "不包含", "开始于", "结束于", "为空", "不为空", ">", ">=", "<", "<=", "==", "!="};
    /**
     * 问号
     */
    public static final String QUESTION_MARK = "?";
    /**
     * 用户名
     */
    public static final String USER_NAME = "admin";
    /**
     * 密码
     */
    public static final String USER_PASS_WORD = "123456";
    /**
     * 元素定位前缀
     */
    public static final String STR_THIS = "this[\"";
    /**
     * 中括号
     */
    public static final String STR_BRACKET_LEFT = "[";
    /**
    /**
     * 中括号
     */
    public static final String STR_BRACKET_RIGHT = "\"]";
    /**
     * 点
     */
    public static final String STR_POINT = ".";
    /**
     * 点
     */
    public static final String ESC_POINT = "\\.";
    /**
     * >>
     */
    public static final String DOU_GREATER = ">>";
    /**
     * Token请求地址
     */
    public static final String TOKEN_URL = "http://account/account/oauth/token?grant_type=client_credentials" +
            "&client_id=accountUIClientID&client_secret=accountUIClientSecret";
    /**
     * 规则执行结果
     */
    public static final String RULE_RESULT = "RULERESULT";

    /**
     * Redis key定义
     */
    // Token键定义
    public static final String REDIS_RULE_TOKEN = "REDIS_RULE_TOKEN";

    /**
     * KIE缓存map最大上限
     */
    public static final int KIE_MAP_MAX_SIZE = 500;

    /**
     * 规则校验表达式内容框架
     */
    public static final String RULE_EXPRESSION = "package cn.gtmap.realestate.engine.drools\r\n" +
            "import java.util.Map\r\n" +
            "import org.apache.commons.collections.CollectionUtils\r\n" +
            "import org.apache.commons.collections.MapUtils\r\n" +
            "rule \"Realestate Rule Engine\"\r\n" +
            "\tlock-on-active true\r\n" +
            "\twhen\r\n" +
            "\t\t$map:Map({0})\r\n" +
            "\tthen\r\n" +
            "\t\t$map.put(\"{1}\", \"{2}\");\r\n" +
            "\t\tupdate($map);\r\n" +
            "end\r\n";

    /**
     * 自定义日志类型：规则信息操作
     */
    public static final String LOG_ACTION_GZCZ = "GZCZ";
    /**
     * 自定义日志类型：规则验证
     */
    public static final String LOG_ACTION_GZYZ= "GZYZ";

    /**
     * 子规则操作类型-保存
     */
    public static final String ZGZ_ACTION_SAVE = "保存子规则";
    /**
     * 子规则操作类型-删除
     */
    public static final String ZGZ_ACTION_DELETE = "删除子规则";

    /**
     * 组合规则操作类型-保存
     */
    public static final String ZHGZ_ACTION_SAVE = "保存组合规则";
    /**
     * 组合规则操作类型-删除
     */
    public static final String ZHGZ_ACTION_DELETE = "删除组合规则";

    /**
     * 组合规则操作类型-新增
     */
    public static final String ZHGZ_ACTION_ADD = "新增组合规则";

    /**
     * 规则类型-子规则
     */
    public static final String GZLX_ZGZ = "子规则";

    /**
     * 规则类型-组合规则
     */
    public static final String GZLX_ZHGZ = "组合规则";

    /**
     * 切点执行切点 前置
     */
    public static final String EXCUTEPOINT_DOBEFORE = "doBefore";
    /**
     * 切点执行切点 后置
     */
    public static final String EXCUTEPOINT_DOAFTER = "doAfter";
}
