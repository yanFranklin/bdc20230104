package cn.gtmap.realestate.common.config.logaop;

import cn.gtmap.realestate.common.core.dto.inquiry.ZyTzDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParamsTslConstants {
    /**
     *  查询条件
     */
    public static final Map<String, String> PARAM = new HashMap<String, String>();
    /**
     *  查询条件中的英文转中文显示
     */
    public static final Map<String, Map<String, String>> PARAMCONDITION = new HashMap<String, Map<String, String>>();
    /**
     *  操作的资源名称或者台账名称
     */
    public static final List<ZyTzDTO> LISTZYTZDTO = new ArrayList<>();
    /**
     *  打印的key
     */
    public static final List<String> PRINTKEY = new ArrayList<>();


    static{
        initAllConstantVariable();
    }

    private static void initAllConstantVariable(){
        // 初始化listZyTzDto
//        ZyTzDTO zyTzDto = new ZyTzDTO();
//        zyTzDto.setTzType("listBdcdyByPage");
//        zyTzDto.setTzName("已登记不动产单元查询");
//        zyTzDto.setTzAddress("/realestate-inquiry-ui/view/bdcdycx/bdcdylist.html");
//        LISTZYTZDTO.add(zyTzDto);

        // 初始化PARAM
        PARAM.put("SFYWS", "是否完税");
        // 登簿验证信息
        PARAM.put("YZXX", "验证信息");
        // 登簿过滤规则验证
        PARAM.put("GLGZYZ", "过滤规则验证");
        // 登簿是否已执行上报
        PARAM.put("ACCESS", "是否已执行上报");
        // 登簿状态
        PARAM.put("DBZT", "登簿操作状态");
        // 登簿mq交换方式
        PARAM.put("EXCHANGE", "登簿MQ数据交换方式");
        // 登簿队列信息
        PARAM.put("ROUTEKEY", "登簿MQ队列信息");
        // 登簿mq发送的数据信息
        PARAM.put("SENDDATA", "登簿MQ队列信息");

        PARAM.put("HLNR","操作内容");
        PARAM.put("LWNR","例外内容");
        //PARAM.put("QLRMC","权利人");
        //PARAM.put("QLR","权利人");
        //PARAM.put("QLRLIST","权利人");
        PARAM.put("ZJH","证件号");
        PARAM.put("ZJHLIST","证件号");
        PARAM.put("QLRJTCYGXLIST","家庭成员关系");
        //PARAM.put("BDCDYH","不动产单元号");
        PARAM.put("CQZH","不动产权证号");

        PARAM.put("BDCQZH","不动产权证号");
        //PARAM.put("ZL","坐落");
        PARAM.put("QUERYXZZZT","是否查询限制状态");
        PARAM.put("QDYS","清单样式");
        PARAM.put("SFYF","数据类型");
        PARAM.put("SFDC","是否导出清单");
        PARAM.put("CXFS","查询方式");
        PARAM.put("AREA","查询地区");

        PARAM.put("DWDM","单位代码");
        PARAM.put("XMID","项目ID");
        PARAM.put("YXMID","原项目ID");
        PARAM.put("GZLSLID", "工作流实例ID");
        PARAM.put("XMIDLIST","项目ID");
        PARAM.put("FILENAME","文件名");
        PARAM.put("UPLOADTYPE","上传类型");
        PARAM.put("DOWNLOADTYPE","下载类型");
        PARAM.put("CXBH","查询编号");
        PARAM.put("JBR","经办人");

        PARAM.put("DLR","代理人");
        PARAM.put("DLRZJH","代理人证件号");
        PARAM.put("SQR","申请人");
        PARAM.put("SQRZJH","申请人证件号");
        PARAM.put("DJJG","登记机构");
        PARAM.put("DJJGLXDH","登记机构联系电话");
        PARAM.put("TEMPLATNAME","模板名称");
        PARAM.put("LB","类别");

        PARAM.put("FJ","附记");
        PARAM.put("CXMD","查询目的");
        PARAM.put("BDCLX","不动产类型");
        PARAM.put("DJQSRQ","登记起始日期");
        PARAM.put("DJJSRQ","登记结束日期");
        PARAM.put("QSZT","权属状态");
        PARAM.put("ARGS","请求参数");
        PARAM.put("ZHCXTJ","综合查询条件");
        PARAM.put("GZNR","规则内容");
        PARAM.put("BJQZGZ","规则编辑前内容");
        PARAM.put("BJHZGZ", "规则编辑后内容");

        PARAM.put("SINGLEQLR", "权利人");
        PARAM.put("SINGLEZJH", "权利人证件号");

        PARAM.put("BDCXMXX", "项目相关参数");
        PARAM.put("BDCXMLSGXLIST", "项目历史关系相关参数");
        PARAM.put("BDCQLRLIST", "权利人相关参数");

        PARAM.put("RESPONSE", "查询结果");
        // 信息补录 编辑前后信息
        PARAM.put("BEFORE", "修改前信息");
        PARAM.put("AFTER", "修改后信息");
        // 初始化PARAMCondition
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("1", "土地");
        map1.put("2", "土地和房屋");
        map1.put("3", "林地和林木");
        map1.put("4", "土地和在建建筑物");
        map1.put("5", "海域");
        map1.put("6", "海域和构筑物");
        map1.put("7", "其它");
        map1.put("9", "土地和其他");
        map1.put("10","海域和房屋");
        map1.put("11","海域和森林林木");
        map1.put("12","海域和其他");
        map1.put("13","无居民海岛");
        PARAMCONDITION.put("BDCLX",map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("0","临时");
        map2.put("1","现势");
        map2.put("2","历史");
        map2.put("3","终止");
        PARAMCONDITION.put("QSZT",map2);

        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("0","有房");
        map4.put("1","全部");
        PARAMCONDITION.put("SFYF",map4);

        Map<String, String> map6 = new HashMap<String, String>();
        map6.put("sureZjh","证件号精确");
        map6.put("1","证件号精确");
        map6.put("likeZjh","证件号模糊");
        map6.put("0","证件号模糊");
        map6.put("exactNameAndzjh","证件号精确");
        map6.put("exactNameLikeZjh","证件号模糊");
        PARAMCONDITION.put("CXFS",map6);

        PRINTKEY.add("QLR");
        PRINTKEY.add("QLRMC");
        PRINTKEY.add("ZJH");
        PRINTKEY.add("QLRZJH");
        PRINTKEY.add("ZL");
        PRINTKEY.add("CXBH");

    }

}
