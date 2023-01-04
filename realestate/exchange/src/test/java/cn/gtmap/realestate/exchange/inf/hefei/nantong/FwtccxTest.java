package cn.gtmap.realestate.exchange.inf.hefei.nantong;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-22
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class FwtccxTest {

    @Test
    public void test(){
        Map paramMap = new HashMap();
        paramMap.put("qlrmc","张三");
        paramMap.put("zjh","1242353546467");
        List<Map> paramList = new ArrayList<>(1);
        paramList.add(paramMap);
        String beanName = "nt_yth_fwtccx";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramList,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(response));
        Object response = requestBuilder.invoke();
        System.out.println("请求参数：" + JSONObject.toJSONString(requestBuilder.getRequestBody()));
        System.out.println("响应参数：" + JSONObject.toJSONString(response));
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }

    private static String response = "{\n" +
            "\t\"success\": true,\n" +
            "\t\"qqsj\": \"2020-03-31 17:24:52:600\",\n" +
            "\t\"page\": 0,\n" +
            "\t\"size\": 0,\n" +
            "\t\"qtime\": 0,\n" +
            "\t\"records\": 5,\n" +
            "\t\"total\": 0,\n" +
            "\t\"cxbh\": null,\n" +
            "\t\"jlid\": null,\n" +
            "\t\"statusCode\": 0,\n" +
            "\t\"message\": null,\n" +
            "\t\"data\": {\n" +
            "\t\t\"IsSuccessful\": \"查询成功\",\n" +
            "\t\t\"fwlist\": [{\n" +
            "\t\t\t\t\"xh\": 1,\n" +
            "\t\t\t\t\"fttdmj\": \"55.61\",\n" +
            "\t\t\t\t\"bz\": \"不动产权证书:1本;不动产登记证明:1本;\\n不动产权证已领取\",\n" +
            "\t\t\t\t\"dytdmj\": \"\",\n" +
            "\t\t\t\t\"qllx\": \"国有建设用地使用权/房屋所有权\",\n" +
            "\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\"sfzh\": \"\",\n" +
            "\t\t\t\t\"yyxxlist\": [],\n" +
            "\t\t\t\t\"qlrxxlist\": [{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2020-02-28\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"苏(2020)南通开发区不动产权第0001701号\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"32062419721207861X\",\n" +
            "\t\t\t\t\t\t\"fj\": \"按份共有：陆鑫蓉:50.0% 陆云:25.0% 杭丽:25.0%\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"156067b1d81541718e5a4e1bd204bbd0\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"按份共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"0.25\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"苏(2018)南通开发区不动产权证明单第0022933号\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"陆云\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"0.25\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2020-02-28\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"苏(2020)南通开发区不动产权第0001701号\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"320624197503037164\",\n" +
            "\t\t\t\t\t\t\"fj\": \"按份共有：陆鑫蓉:50.0% 陆云:25.0% 杭丽:25.0%\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"82fece12c2854f078d068773e5b79cea\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"按份共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"0.25\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"苏(2018)南通开发区不动产权证明单第0022933号\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"杭丽\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"0.25\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2020-02-28\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"苏(2020)南通开发区不动产权第0001701号\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"320601199702190326\",\n" +
            "\t\t\t\t\t\t\"fj\": \"按份共有：陆鑫蓉:50.0% 陆云:25.0% 杭丽:25.0%\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"e76efe9eb44f4b5bb6203adbbdc8eeb4\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"按份共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"0.5\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"苏(2018)南通开发区不动产权证明单第0022933号\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"陆鑫蓉\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"0.5\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"cdsj\": \"2020-03-31 17:24:52:600\",\n" +
            "\t\t\t\t\"xzzt\": \"查封/抵押/锁定(限制状态以登记查询窗口为准)\",\n" +
            "\t\t\t\t\"szc\": \"1-3\",\n" +
            "\t\t\t\t\"dyaqxxlist\": [{\n" +
            "\t\t\t\t\t\"dymj\": \"\",\n" +
            "\t\t\t\t\t\"dbsj\": \"2020-02-28\",\n" +
            "\t\t\t\t\t\"dyaqlr\": \"中国民生银行股份有限公司南通分行\",\n" +
            "\t\t\t\t\t\"dyfj\": \" 土地抵押面积:55.61㎡\\n 房屋抵押面积:163.63㎡\\n\",\n" +
            "\t\t\t\t\t\"zwlxqx\": \"2016-08-18起2031-08-18止\",\n" +
            "\t\t\t\t\t\"zgzqse\": \"2373464.6\",\n" +
            "\t\t\t\t\t\"fzrq\": \"2020-02-28\",\n" +
            "\t\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\t\"djzmh\": \"苏(2020)南通开发区不动产证明第0001103号\",\n" +
            "\t\t\t\t\t\"djyy\": \"房屋（构筑物）抵押权首次登记\",\n" +
            "\t\t\t\t\t\"dyfs\": \"一般抵押\",\n" +
            "\t\t\t\t\t\"bdbzqse\": \"100\"\n" +
            "\t\t\t\t}],\n" +
            "\t\t\t\t\"jzmj\": \"163.63\",\n" +
            "\t\t\t\t\"qlxz\": \"出让/商品房\",\n" +
            "\t\t\t\t\"qlr\": \"\",\n" +
            "\t\t\t\t\"bdclx\": \"土地、房屋\",\n" +
            "\t\t\t\t\"mj\": \"房屋建筑面积163.63㎡\",\n" +
            "\t\t\t\t\"fwzl\": \"景瑞御府48幢102室\",\n" +
            "\t\t\t\t\"hth\": \"\",\n" +
            "\t\t\t\t\"qlbsm\": \"\",\n" +
            "\t\t\t\t\"dyqxxlist\": [],\n" +
            "\t\t\t\t\"zyzxsj\": \"\",\n" +
            "\t\t\t\t\"fjh\": \"102\",\n" +
            "\t\t\t\t\"fwfzxxlist\": [],\n" +
            "\t\t\t\t\"tdsyqx\": \"国有建设用地使用权:2085-08-05止\",\n" +
            "\t\t\t\t\"cfxxlist\": [{\n" +
            "\t\t\t\t\t\"djsj\": \"2020-03-03\",\n" +
            "\t\t\t\t\t\"cfjg\": \"南通市经济技术开发区综合执法局\",\n" +
            "\t\t\t\t\t\"cflx\": \"查封\",\n" +
            "\t\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\t\"cffw\": \"景瑞御府48幢102室\",\n" +
            "\t\t\t\t\t\"cfqx\": \"2020-03-03 02:03:11起\",\n" +
            "\t\t\t\t\t\"cfwh\": \"序号：132\",\n" +
            "\t\t\t\t\t\"cxbz\": \"\"\n" +
            "\t\t\t\t}],\n" +
            "\t\t\t\t\"cs\": \"3/1-3\",\n" +
            "\t\t\t\t\"jznd\": \"\",\n" +
            "\t\t\t\t\"jyjg\": \"237.3464\",\n" +
            "\t\t\t\t\"djsj\": \"2020-02-28\",\n" +
            "\t\t\t\t\"fwyt\": \"城镇住宅用地/住宅\",\n" +
            "\t\t\t\t\"fwjg\": \"钢筋混凝土结构\",\n" +
            "\t\t\t\t\"jgsj\": \"\",\n" +
            "\t\t\t\t\"sdxxlist\": [{\n" +
            "\t\t\t\t\t\"sdyy\": \"根据管委会通知冻结\",\n" +
            "\t\t\t\t\t\"sdr\": \"于建云\",\n" +
            "\t\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\t\"sdsj\": \"2020-03-03\"\n" +
            "\t\t\t\t}],\n" +
            "\t\t\t\t\"bdcdyh\": \"320604001001GB00029F00480002\",\n" +
            "\t\t\t\t\"ygxxlist\": [],\n" +
            "\t\t\t\t\"zcs\": \"3\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"xh\": 2,\n" +
            "\t\t\t\t\"fttdmj\": \"\",\n" +
            "\t\t\t\t\"bz\": \"<?xml version=\\\"1.0\\\" encoding=\\\"GB2312\\\" ?><aboutinfos></aboutinfos>\",\n" +
            "\t\t\t\t\"dytdmj\": \"\",\n" +
            "\t\t\t\t\"qllx\": \"-\",\n" +
            "\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\"sfzh\": \"\",\n" +
            "\t\t\t\t\"yyxxlist\": [],\n" +
            "\t\t\t\t\"qlrxxlist\": [{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-10-08\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13002757,13002756\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"32062419721207861X\",\n" +
            "\t\t\t\t\t\t\"fj\": \"星月花园11幢车库04室，层高小于2.2米，面积19.83㎡。\\n陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"900DC06A5FB349C0ADBC7B6297B59D38\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"陆云\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-10-08\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13002757,13002756\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"320624197503037164\",\n" +
            "\t\t\t\t\t\t\"fj\": \"星月花园11幢车库04室，层高小于2.2米，面积19.83㎡。\\n陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"E65E246E34864C34BD947C619BE9852D\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"杭丽\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"cdsj\": \"2020-03-31 17:24:52:600\",\n" +
            "\t\t\t\t\"xzzt\": \"正常(限制状态以登记查询窗口为准)\",\n" +
            "\t\t\t\t\"szc\": \"0\",\n" +
            "\t\t\t\t\"dyaqxxlist\": [],\n" +
            "\t\t\t\t\"jzmj\": \"19.83\",\n" +
            "\t\t\t\t\"qlxz\": \"商品房\",\n" +
            "\t\t\t\t\"qlr\": \"\",\n" +
            "\t\t\t\t\"bdclx\": \"土地、房屋\",\n" +
            "\t\t\t\t\"mj\": \"房屋建筑面积19.83\",\n" +
            "\t\t\t\t\"fwzl\": \"星月花园11幢车库04室\",\n" +
            "\t\t\t\t\"hth\": \"\",\n" +
            "\t\t\t\t\"qlbsm\": \"\",\n" +
            "\t\t\t\t\"dyqxxlist\": [],\n" +
            "\t\t\t\t\"zyzxsj\": \"\",\n" +
            "\t\t\t\t\"fjh\": \"\",\n" +
            "\t\t\t\t\"fwfzxxlist\": [],\n" +
            "\t\t\t\t\"tdsyqx\": \"\",\n" +
            "\t\t\t\t\"cfxxlist\": [],\n" +
            "\t\t\t\t\"cs\": \"6/0\",\n" +
            "\t\t\t\t\"jznd\": \"\",\n" +
            "\t\t\t\t\"jyjg\": \"\",\n" +
            "\t\t\t\t\"djsj\": \"2013-10-08\",\n" +
            "\t\t\t\t\"fwyt\": \"车库\",\n" +
            "\t\t\t\t\"fwjg\": \"混合\",\n" +
            "\t\t\t\t\"jgsj\": \"\",\n" +
            "\t\t\t\t\"sdxxlist\": [],\n" +
            "\t\t\t\t\"bdcdyh\": \"\",\n" +
            "\t\t\t\t\"ygxxlist\": [],\n" +
            "\t\t\t\t\"zcs\": \"6\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"xh\": 3,\n" +
            "\t\t\t\t\"fttdmj\": \"13.84\",\n" +
            "\t\t\t\t\"bz\": \"<?xml version=\\\"1.0\\\" encoding=\\\"GB2312\\\" ?><aboutinfos></aboutinfos>\",\n" +
            "\t\t\t\t\"dytdmj\": \"0\",\n" +
            "\t\t\t\t\"qllx\": \"-/-\",\n" +
            "\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\"sfzh\": \"\",\n" +
            "\t\t\t\t\"yyxxlist\": [],\n" +
            "\t\t\t\t\"qlrxxlist\": [{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-10-08\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13002755,13002754\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"32062419721207861X\",\n" +
            "\t\t\t\t\t\t\"fj\": \"售后留房发证\\n陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"D78844AEBCD84EFEA543B406E4375E07\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"陆云\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-10-08\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13002755,13002754\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"320624197503037164\",\n" +
            "\t\t\t\t\t\t\"fj\": \"售后留房发证\\n陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"EAB4EB9AC7CB44A8AE8C429DCFB4E689\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"杭丽\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"cdsj\": \"2020-03-31 17:24:52:600\",\n" +
            "\t\t\t\t\"xzzt\": \"正常(限制状态以登记查询窗口为准)\",\n" +
            "\t\t\t\t\"szc\": \"7\",\n" +
            "\t\t\t\t\"dyaqxxlist\": [],\n" +
            "\t\t\t\t\"jzmj\": \"130.05\",\n" +
            "\t\t\t\t\"qlxz\": \"出让/商品房\",\n" +
            "\t\t\t\t\"qlr\": \"\",\n" +
            "\t\t\t\t\"bdclx\": \"土地、房屋\",\n" +
            "\t\t\t\t\"mj\": \"房屋建筑面积130.05\",\n" +
            "\t\t\t\t\"fwzl\": \"星月花园28幢703室\",\n" +
            "\t\t\t\t\"hth\": \"\",\n" +
            "\t\t\t\t\"qlbsm\": \"\",\n" +
            "\t\t\t\t\"dyqxxlist\": [],\n" +
            "\t\t\t\t\"zyzxsj\": \"\",\n" +
            "\t\t\t\t\"fjh\": \"\",\n" +
            "\t\t\t\t\"fwfzxxlist\": [],\n" +
            "\t\t\t\t\"tdsyqx\": \"\",\n" +
            "\t\t\t\t\"cfxxlist\": [],\n" +
            "\t\t\t\t\"cs\": \"10/7\",\n" +
            "\t\t\t\t\"jznd\": \"\",\n" +
            "\t\t\t\t\"jyjg\": \"\",\n" +
            "\t\t\t\t\"djsj\": \"2013-10-08\",\n" +
            "\t\t\t\t\"fwyt\": \"城镇住宅用地/住宅\",\n" +
            "\t\t\t\t\"fwjg\": \"钢筋混凝土\",\n" +
            "\t\t\t\t\"jgsj\": \"\",\n" +
            "\t\t\t\t\"sdxxlist\": [],\n" +
            "\t\t\t\t\"bdcdyh\": \"320604001001GB00168F00280093\",\n" +
            "\t\t\t\t\"ygxxlist\": [],\n" +
            "\t\t\t\t\"zcs\": \"10\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"xh\": 4,\n" +
            "\t\t\t\t\"fttdmj\": \"13.84\",\n" +
            "\t\t\t\t\"bz\": \"<?xml version=\\\"1.0\\\" encoding=\\\"GB2312\\\" ?><aboutinfos></aboutinfos>\",\n" +
            "\t\t\t\t\"dytdmj\": \"0\",\n" +
            "\t\t\t\t\"qllx\": \"-/-\",\n" +
            "\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\"sfzh\": \"\",\n" +
            "\t\t\t\t\"yyxxlist\": [],\n" +
            "\t\t\t\t\"qlrxxlist\": [{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-09-24\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13001857,13001856\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"320624197503037164\",\n" +
            "\t\t\t\t\t\t\"fj\": \"陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"6A63B3F703954609B1B57E06DBED5912\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"杭丽\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-09-24\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13001857,13001856\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"32062419721207861X\",\n" +
            "\t\t\t\t\t\t\"fj\": \"陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"6CC37AB7EFE048F7A1983B218AE36522\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"陆云\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-09-24\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13001857,13001856\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"320624197503037164\",\n" +
            "\t\t\t\t\t\t\"fj\": \"陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"6A63B3F703954609B1B57E06DBED5912\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"杭丽\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"dbsj\": \"2013-09-24\",\n" +
            "\t\t\t\t\t\t\"bdcqzh\": \"13001857,13001856\",\n" +
            "\t\t\t\t\t\t\"qlrzjh\": \"32062419721207861X\",\n" +
            "\t\t\t\t\t\t\"fj\": \"陆云、杭丽共同共有\",\n" +
            "\t\t\t\t\t\t\"qlrid\": \"6CC37AB7EFE048F7A1983B218AE36522\",\n" +
            "\t\t\t\t\t\t\"gyqk\": \"共同共有\",\n" +
            "\t\t\t\t\t\t\"gyfe\": \"\",\n" +
            "\t\t\t\t\t\t\"fzrq\": \"\",\n" +
            "\t\t\t\t\t\t\"ybdcqzh\": \"\",\n" +
            "\t\t\t\t\t\t\"qlr\": \"陆云\",\n" +
            "\t\t\t\t\t\t\"qlbl\": \"\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"cdsj\": \"2020-03-31 17:24:52:600\",\n" +
            "\t\t\t\t\"xzzt\": \"正常(限制状态以登记查询窗口为准)\",\n" +
            "\t\t\t\t\"szc\": \"8\",\n" +
            "\t\t\t\t\"dyaqxxlist\": [],\n" +
            "\t\t\t\t\"jzmj\": \"130.05\",\n" +
            "\t\t\t\t\"qlxz\": \"出让/商品房\",\n" +
            "\t\t\t\t\"qlr\": \"\",\n" +
            "\t\t\t\t\"bdclx\": \"土地、房屋\",\n" +
            "\t\t\t\t\"mj\": \"房屋建筑面积130.05\",\n" +
            "\t\t\t\t\"fwzl\": \"星月花园28幢803室\",\n" +
            "\t\t\t\t\"hth\": \"\",\n" +
            "\t\t\t\t\"qlbsm\": \"\",\n" +
            "\t\t\t\t\"dyqxxlist\": [],\n" +
            "\t\t\t\t\"zyzxsj\": \"\",\n" +
            "\t\t\t\t\"fjh\": \"\",\n" +
            "\t\t\t\t\"fwfzxxlist\": [],\n" +
            "\t\t\t\t\"tdsyqx\": \"\",\n" +
            "\t\t\t\t\"cfxxlist\": [],\n" +
            "\t\t\t\t\"cs\": \"10/8\",\n" +
            "\t\t\t\t\"jznd\": \"\",\n" +
            "\t\t\t\t\"jyjg\": \"\",\n" +
            "\t\t\t\t\"djsj\": \"2013-09-24\",\n" +
            "\t\t\t\t\"fwyt\": \"城镇住宅用地/住宅\",\n" +
            "\t\t\t\t\"fwjg\": \"钢筋混凝土\",\n" +
            "\t\t\t\t\"jgsj\": \"\",\n" +
            "\t\t\t\t\"sdxxlist\": [],\n" +
            "\t\t\t\t\"bdcdyh\": \"320604001001GB00168F00280099\",\n" +
            "\t\t\t\t\"ygxxlist\": [],\n" +
            "\t\t\t\t\"zcs\": \"10\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"xh\": 5,\n" +
            "\t\t\t\t\"fttdmj\": \"\",\n" +
            "\t\t\t\t\"bz\": \"<?xml version=\\\"1.0\\\" encoding=\\\"GB2312\\\" ?><aboutinfos></aboutinfos>\",\n" +
            "\t\t\t\t\"dytdmj\": \"\",\n" +
            "\t\t\t\t\"qllx\": \"-\",\n" +
            "\t\t\t\t\"qszt\": \"现势\",\n" +
            "\t\t\t\t\"sfzh\": \"\",\n" +
            "\t\t\t\t\"yyxxlist\": [],\n" +
            "\t\t\t\t\"qlrxxlist\": [],\n" +
            "\t\t\t\t\"cdsj\": \"2020-03-31 17:24:52:600\",\n" +
            "\t\t\t\t\"xzzt\": \"正常(限制状态以登记查询窗口为准)\",\n" +
            "\t\t\t\t\"szc\": \"-1\",\n" +
            "\t\t\t\t\"dyaqxxlist\": [],\n" +
            "\t\t\t\t\"jzmj\": \"12.19\",\n" +
            "\t\t\t\t\"qlxz\": \"商品房\",\n" +
            "\t\t\t\t\"qlr\": \"\",\n" +
            "\t\t\t\t\"bdclx\": \"土地、房屋\",\n" +
            "\t\t\t\t\"mj\": \"房屋建筑面积12.19\",\n" +
            "\t\t\t\t\"fwzl\": \"星月花园28幢B107室\",\n" +
            "\t\t\t\t\"hth\": \"\",\n" +
            "\t\t\t\t\"qlbsm\": \"\",\n" +
            "\t\t\t\t\"dyqxxlist\": [],\n" +
            "\t\t\t\t\"zyzxsj\": \"\",\n" +
            "\t\t\t\t\"fjh\": \"\",\n" +
            "\t\t\t\t\"fwfzxxlist\": [],\n" +
            "\t\t\t\t\"tdsyqx\": \"\",\n" +
            "\t\t\t\t\"cfxxlist\": [],\n" +
            "\t\t\t\t\"cs\": \"10/-1\",\n" +
            "\t\t\t\t\"jznd\": \"\",\n" +
            "\t\t\t\t\"jyjg\": \"\",\n" +
            "\t\t\t\t\"djsj\": \"2013-09-24\",\n" +
            "\t\t\t\t\"fwyt\": \"车库\",\n" +
            "\t\t\t\t\"fwjg\": \"钢筋混凝土\",\n" +
            "\t\t\t\t\"jgsj\": \"\",\n" +
            "\t\t\t\t\"sdxxlist\": [],\n" +
            "\t\t\t\t\"bdcdyh\": \"\",\n" +
            "\t\t\t\t\"ygxxlist\": [],\n" +
            "\t\t\t\t\"zcs\": \"10\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"records\": 5,\n" +
            "\t\t\"IsABnormal\": \"1\",\n" +
            "\t\t\"YWBH\": \"公开2020区号0700012845\"\n" +
            "\t}\n" +
            "}";
}
