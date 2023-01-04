package cn.gtmap.realestate.inquiry.ui.util;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/19 10:05
 * @description  Engine UI 常量定义
 */
public class EngineUiConstant {
    /**
     * 子规则验证默认动态代码内容
     */
    public static final String ENGINE_INIT_CODE =
            "package cn.gtmap.realestate.engine.service.impl;\n" +
            "\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.List;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * 不动产子规则动态编码验证类 注意：包名、类名、方法签名请不要改动！\n" +
            " *\n" +
            " * @version 1.0, GTMAP\n" +
            " */\n" +
            "public class RuleCheck {\n" +
            "\n" +
            "  /**\n" +
            "   * 对数据流执行结果进行判断，获取对应的提示信息\n" +
            "   *\n" +
            "   * @param checkResult 数据流执行结果集合\n" +
            "   * @return { List } 子规则提示信息\n" +
            "   */\n" +
            "  public <T> List<String> getTsxxList(Map<String, T> checkResult) {\n" +
            "    if (null == checkResult) {\n" +
            "      return null;\n" +
            "    }\n" +
            "    List<String> tsxxList = new ArrayList();\n" +
            "\n" +
            "    // TODO 输入具体验证内容\n" +
            "    // ---------------------\n" +
            "\n" +
            "    // ---------------------\n" +
            "    return tsxxList;\n" +
            "  }\n" +
            "}\n";
}
