package cn.gtmap.realestate.exchange.inf.hefei;

import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-14
 * @description TODO 推送税务信息 商品房信息
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class TsSwxxSpfTest {


    @Test
    public void test(){
        String beanName = "tsSwxxSpf";
        // 参数可使用MAP类型传递，也可以定义请求实体传递
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("xmid","1000");
        paramMap.put("jbxx",getBdcSlJbxxDO());
        paramMap.put("fwxx",getBdcSlFwxxDO());
        paramMap.put("xmxx",getBdcSlXmDO());
        paramMap.put("bdcJyxx",getBdcJyxxDO());
        paramMap.put("sqrList",getBdcSlSqlList());
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.invoke();
//        System.out.println(JSONObject.toJSONString(requestBuilder.getRequestBody()));
        Assert.assertEquals(CONVERT_REQUEST_JSON,JSONObject.toJSONString(requestBuilder.getRequestBody()));
    }


    private  static String getCurrDate(){
        FastDateFormat formater = FastDateFormat.getInstance("yyyyMMdd");
        return formater.format(new Date());
    }
    private static String CONVERT_REQUEST_JSON = "{\"head\":{\"tran_date\":\""+getCurrDate()+"\",\"tran_seq\":\"1000\",\"tran_id\":\"ahsw.fcjy.yth.fcjyzlfcj\",\"channel_id\":\"AHHFDBC\",\"token\":\"GJSWZJAHSSWJ\"},\"body\":{\"fh\":\"201\",\"ywsldh\":\"20313\",\"jdxz\":\"2301\",\"qszyfs\":\"qszyfs\",\"barq\":\"Thu Jun 06 00:00:00 CST 2019\",\"htqdrq\":\"Thu Jun 06 00:00:00 CST 2019\",\"qszydx\":\"qszydx\",\"srfxxlb\":[{\"szfe\":\"0.5\",\"xm\":\"权利人1\",\"zjlx\":\"1\",\"dz\":\"权利人通讯地址\",\"jtxh\":\"1\",\"zjhm\":\"2321001990999999999\"},{\"szfe\":\"0.5\",\"xm\":\"权利人2\",\"zjlx\":\"1\",\"dz\":\"权利人通讯地址\",\"jtxh\":\"2\",\"zjhm\":\"2321001990999999999\"}],\"swjgbm\":\"123456\",\"fwwzdz\":\"坐落\",\"fwyt\":\"3\",\"qszyyt\":\"qszyyt\",\"jcnd\":\"2009\",\"jzmj\":\"200.53\",\"fwnm\":\"2301066666666\",\"tnmj\":\"200.51\",\"xzqh\":\"23230\",\"htbh\":\"123456\",\"fwdh\":\"2栋\",\"fcbm\":\"原不动产权证\",\"szlc\":\"5\",\"jbrxm\":\"受理人\",\"zlc\":\"30\",\"htje\":\"200.52\",\"jzcx\":\"朝南\",\"xqmc\":\"小区名称\",\"kfsxxlb\":[{\"kfsnsrsbh\":\"2321001990999999999\",\"kfsmc\":\"义务人1\"},{\"kfsnsrsbh\":\"2321001990999999999\",\"kfsmc\":\"义务人2\"}],\"jzjg\":\"2\"}}";


    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        return serviceList;
    }


    public Object getBdcSlSqlList(){
        List<BdcSlSqrDO> sqrList = new ArrayList<>();
        BdcSlSqrDO sqr1 = new BdcSlSqrDO();
        sqr1.setSqrlb("1");
        sqr1.setSqrmc("权利人1");
        sqr1.setZjzl(1);
        sqr1.setZjh("2321001990999999999");
        sqr1.setTxdz("权利人通讯地址");
        sqr1.setQlbl("0.5");
        sqr1.setDh("18266544566");
        sqr1.setSxh(1);
        sqrList.add(sqr1);

        BdcSlSqrDO sqr2 = new BdcSlSqrDO();
        sqr2.setSqrlb("1");
        sqr2.setSqrmc("权利人2");
        sqr2.setZjzl(1);
        sqr2.setZjh("2321001990999999999");
        sqr2.setTxdz("权利人通讯地址");
        sqr2.setQlbl("0.5");
        sqr2.setDh("18266544566");
        sqr2.setSxh(2);
        sqrList.add(sqr2);

        BdcSlSqrDO ywr1 = new BdcSlSqrDO();
        ywr1.setSqrlb("2");
        ywr1.setSqrmc("义务人1");
        ywr1.setZjzl(1);
        ywr1.setZjh("2321001990999999999");
        ywr1.setTxdz("权利人通讯地址");
        ywr1.setQlbl("0.5");
        ywr1.setDh("18266544566");
        ywr1.setSxh(1);
        sqrList.add(ywr1);

        BdcSlSqrDO ywr2 = new BdcSlSqrDO();
        ywr2.setSqrlb("2");
        ywr2.setSqrmc("义务人2");
        ywr2.setZjzl(1);
        ywr2.setZjh("2321001990999999999");
        ywr2.setTxdz("权利人通讯地址");
        ywr2.setQlbl("0.5");
        ywr2.setDh("18266544566");
        ywr2.setSxh(2);
        sqrList.add(ywr2);
        return sqrList;
    }

    public Object getBdcSlJbxxDO(){
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setSlr("受理人");
        bdcSlJbxxDO.setSlbh("20313");
        return bdcSlJbxxDO;
    }

    public Object getBdcSlFwxxDO(){
        BdcSlFwxxDO fwxxDO = new BdcSlFwxxDO();
        fwxxDO.setFjh("201");
        fwxxDO.setFwdh("2栋");
        fwxxDO.setFwyt(3);
        fwxxDO.setJznf(2009);
        fwxxDO.setJzcx("朝南");
        fwxxDO.setFwjg(2);
        fwxxDO.setSzc(5);
        fwxxDO.setTnmj(200.51);
        fwxxDO.setXqmc("小区名称");
        fwxxDO.setXzqh("23230");
        fwxxDO.setZcs(30);
        return fwxxDO;
    }

    public Object getBdcSlXmDO(){
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setYbdcqz("原不动产权证");
        bdcSlXmDO.setBdcdyh("2301066666666");
        bdcSlXmDO.setZl("坐落");
        return bdcSlXmDO;
    }

    public Object getBdcJyxxDO(){
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setHtbasj(DateUtils.formatDate("2019-06-06 06:06:06"));
        bdcSlJyxxDO.setDj(200.51);
        bdcSlJyxxDO.setHtbh("123456");
        bdcSlJyxxDO.setJyje(200.52);
        bdcSlJyxxDO.setHtdjsj(DateUtils.formatDate("2019-06-06 06:06:06"));
        bdcSlJyxxDO.setHtmj(200.53);
        bdcSlJyxxDO.setJylx("2");
        bdcSlJyxxDO.setScjydjsj(DateUtils.formatDate("2019-06-06 06:06:06"));
        return bdcSlJyxxDO;
    }

}
