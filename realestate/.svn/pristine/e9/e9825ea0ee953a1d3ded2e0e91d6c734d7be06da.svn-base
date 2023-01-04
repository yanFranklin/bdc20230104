package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcSwService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcSwServiceImplTest {
    @Autowired
    BdcSwService bdcSwService;

    @Test
    @Rollback
    public void testtsClfSwxx() {
        bdcSwService.tsSwxx("283223","");
        System.out.println("成功");


    }

    @Test
    @Rollback
    public void testsaveOrUpdateSwxxDTO() {
        List<BdcSwxxDTO> bdcSwxxDTOList =new ArrayList<>();
        BdcSwxxDTO bdcSwxxDTO =new BdcSwxxDTO();
        BdcSlHsxxMxDO bdcSlHsxxMxDO =new BdcSlHsxxMxDO();
        bdcSlHsxxMxDO.setSjnse(100.00);
        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList =new ArrayList<>();
        bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO);
        bdcSwxxDTO.setBdcSlHsxxMxDOList(bdcSlHsxxMxDOList);
        BdcSlHsxxDO bdcSlHsxxDO =new BdcSlHsxxDO();
        bdcSlHsxxDO.setSqrlb("1");
        bdcSwxxDTO.setBdcSlHsxxDO(bdcSlHsxxDO);
        bdcSwxxDTOList.add(bdcSwxxDTO);
        bdcSwService.saveOrUpdateSwxxDTO(bdcSwxxDTOList,"283223");

        System.out.println("成功");


    }

    @Test
    @Rollback
    public void testgetSwxx() {
        //成功
        String data = "{\"hsxxList\": [{\"bdcSlHsxxDO\": {\"hdjsjg\": 718000.0,\"sjyzhj\": 14360.0,\"sqrlb\": \"1\",\"ynsehj\": 14360.0 },\"bdcSlHsxxMxDOList\": [{\"mxzl\": \"10119\",\"sjnse\": 14360.0,\"ynse\": 14360.0 }] }, {\"bdcSlHsxxDO\": {\"hdjsjg\": 718000.0,\"sjyzhj\": 0.0,\"sqrlb\": \"0\",\"ynsehj\": 0.0 },\"bdcSlHsxxMxDOList\": [{\"mxzl\": \"10106\",\"sjnse\": 0.0,\"ynse\": 0.0 }, {\"mxzl\": \"10101\",\"sjnse\": 0.0,\"ynse\": 0.0 }] }],\"responseCode\": \"200\",\"responseMsg\": \"成功\" } ";
        QuerySwxxResponseDTO responseDTO = JSONObject.parseObject(data, QuerySwxxResponseDTO.class);
        bdcSwService.handleQuerySwxxResponse(responseDTO,"1000","1");
        bdcSwService.handleQuerySwxxResponse(responseDTO,"1000","2");
        String tsdata ="{\n" +
                "\"body\": {\n" +
                "        \"htbh\": \"HT234xxx2\",\n" +
                "        \"jyuuid\": \"b99bccdefa08484c826165316368f607\",\n" +
                "        \"ywsldh\": \"2018111300005\"\n" +
                "    },\n" +
                "    \"head\": {\n" +
                "        \"channel_id\": \"AHxxBDC\",\n" +
                "        \"rtn_code\": \"200\",\n" +
                "        \"rtn_msg\": \"失败测试\",\n" +
                "        \"tran_date\": \"20181119\",\n" +
                "        \"tran_id\": \"ahsw.fcjy.yth.fcjyclfcj\",\n" +
                "        \"tran_seq\": \"4bc12232e7907b56a6006ad3dba4\"\n" +
                "    }\n" +
                "} ";
//        bdcSwService.getSwxx("1000", "1234");
//        System.out.println("成功");
    }

    @Test
    @Rollback
    public void testclfdata() {
        //存量房接口返回结构
        String clfdata ="{\"msg\":\"操作成功\",\"bdcSlJyxx\":[{\"jyxxid\":null,\"xmid\":null,\"htbh\":\"1556593636542\",\"htzt\":null,\"htbah\":\"1556593636542\",\"dj\":null,\"htdjsj\":\"2019-04-30 00:00:00\",\"htbasj\":\"2019-07-10 00:00:00\",\"qszyfs\":null,\"qszydx\":null,\"qszyyt\":null,\"jylx\":null,\"htmj\":114.67,\"scjydjsj\":null,\"qztzrq\":null,\"jyje\":2210000.0,\"fkfs\":null,\"ysxkzh\":null,\"ghxkzh\":null,\"ghyszmbh\":null,\"jgysbabh\":null,\"jgysbasj\":null,\"ghyssj\":null,\"sbjg\":null,\"bhzzsje\":null,\"fczfzsj\":null}],\"code\":\"00000\",\"bdcQlr\":[{\"qlrid\":null,\"xmid\":null,\"qlrmc\":\"文俊\",\"zjzl\":1,\"zjh\":\"510722198411237831\",\"txdz\":null,\"yb\":null,\"xb\":null,\"frmc\":null,\"frdh\":null,\"frzjzl\":null,\"frzjh\":null,\"dlrmc\":null,\"dlrdh\":null,\"dlrzjzl\":null,\"dlrzjh\":null,\"dljg\":null,\"qlrlx\":null,\"qlrlb\":\"0\",\"qlbl\":null,\"gyfs\":null,\"gyqk\":null,\"dh\":\"13721063910\",\"sshy\":null,\"bz\":null,\"sfczr\":null,\"sxh\":null,\"bdcqzh\":null,\"zsid\":null,\"qlrftmj\":null},{\"qlrid\":null,\"xmid\":null,\"qlrmc\":\"高铭\",\"zjzl\":1,\"zjh\":\"321183199105162018\",\"txdz\":null,\"yb\":null,\"xb\":null,\"frmc\":null,\"frdh\":null,\"frzjzl\":null,\"frzjh\":null,\"dlrmc\":null,\"dlrdh\":null,\"dlrzjzl\":null,\"dlrzjh\":null,\"dljg\":null,\"qlrlx\":null,\"qlrlb\":\"1\",\"qlbl\":null,\"gyfs\":null,\"gyqk\":null,\"dh\":\"18756067209\",\"sshy\":null,\"bz\":null,\"sfczr\":null,\"sxh\":null,\"bdcqzh\":null,\"zsid\":null,\"qlrftmj\":null}],\"bdcSlSqr\":[{\"sqrid\":null,\"xmid\":null,\"sqrmc\":\"文俊\",\"zjzl\":1,\"zjh\":\"510722198411237831\",\"txdz\":null,\"yb\":null,\"xb\":null,\"frmc\":null,\"frdh\":null,\"dlrmc\":null,\"dlrdh\":null,\"dljg\":null,\"sqrlx\":null,\"sqrlb\":\"0\",\"qlbl\":null,\"gyfs\":null,\"gyqk\":null,\"dh\":\"13721063910\",\"sshy\":null,\"bz\":null,\"sfczr\":null,\"sxh\":null,\"hyzk\":null,\"sfbdhj\":null,\"sbfwtc\":null,\"fwtc\":null,\"gyrsffq\":null,\"sfjm\":null,\"sfzxqs\":null,\"jtmwwyzz\":null,\"dlrzjzl\":null,\"dlrzjh\":null,\"frzjzl\":null,\"frzjh\":null},{\"sqrid\":null,\"xmid\":null,\"sqrmc\":\"高铭\",\"zjzl\":1,\"zjh\":\"321183199105162018\",\"txdz\":null,\"yb\":null,\"xb\":null,\"frmc\":null,\"frdh\":null,\"dlrmc\":null,\"dlrdh\":null,\"dljg\":null,\"sqrlx\":null,\"sqrlb\":\"1\",\"qlbl\":null,\"gyfs\":null,\"gyqk\":null,\"dh\":\"18756067209\",\"sshy\":null,\"bz\":null,\"sfczr\":null,\"sxh\":null,\"hyzk\":null,\"sfbdhj\":null,\"sbfwtc\":null,\"fwtc\":null,\"gyrsffq\":null,\"sfjm\":null,\"sfzxqs\":null,\"jtmwwyzz\":null,\"dlrzjzl\":null,\"dlrzjh\":null,\"frzjzl\":null,\"frzjh\":null}]}";
        FcjyBaxxDTO fcjyBaxxDTO = JSONObject.parseObject(clfdata, FcjyBaxxDTO.class);
         if(fcjyBaxxDTO != null){

        }
        //工商接口返回结构
        String json ="{\"msg\":\"Message:901-委办局/企业【9811000048659837】访问服务【201906211705467q2clWHwgHXWgJjAIaC】成功，已有效查询到数据！\",\"qyxx\":[{\"bdcQlrDO\":{\"frmc\":\"丁飞飞\",\"qlrmc\":\"合肥飞凌服饰有限公司\",\"txdz\":\"合肥市蜀山区金寨路155号黄金广场5幢C1106室\",\"zjh\":\"9134010009286856XM\"},\"qyzt\":\"06\"}],\"code\":\"true\"}";
         //商品房返回结构
        String zlfjson ="{\n" +
                "\t\"bdcSlJyxx\": {\n" +
                "\t\t\"htbah\": \"1202003969\",\n" +
                "\t\t\"htbh\": \"1202003969\",\n" +
                "\t\t\"jyje\": 777773.0\n" +
                "\t},\n" +
                "\t\"bdcQlr\": [{\n" +
                "\t\t\"qlrlb\": \"1\",\n" +
                "\t\t\"qlrmc\": \"王中梅\",\n" +
                "\t\t\"zjh\": \"342127197304137186\"\n" +
                "\t}],\n" +
                "\t\"bdcSlSqr\": [{\n" +
                "\t\t\"sqrlb\": \"1\",\n" +
                "\t\t\"sqrmc\": \"王中梅\",\n" +
                "\t\t\"zjh\": \"342127197304137186\"\n" +
                "\t}],\n" +
                "\t\"bdcSlFwxx\": {\n" +
                "\t\t\"fjh\": \"123\"\n" +
                "\t}\n" +
                "}";
        String hyxx="{\n" +
                "  \"jtcy\": {\n" +
                "    \"jtcyid\": \"\",\n" +
                "    \"jtcymc\": \"李能庆\",\n" +
                "    \"sqrid\": \"\",\n" +
                "    \"ysqrgx\": \"\",\n" +
                "    \"zjh\": \"34262219780517321\",\n" +
                "    \"zjzl\": null\n" +
                "  },\n" +
                "  \"hyzt\": \"结婚\"\n" +
                "}";
        String gaxx="[ \n" +
                "{ \n" +
                "\"jtcyid\": \"\", \n" +
                "\"jtcymc\": \"父亲\", \n" +
                "\"sqrid\": \"\", \n" +
                "\"ysqrgx\": \"父亲\", \n" +
                "\"zjh\": \"152827199011110909\", \n" +
                "\"zjzl\": null \n" +
                "}, \n" +
                "{ \n" +
                "\"jtcyid\": \"\", \n" +
                "\"jtcymc\": \"母亲\", \n" +
                "\"sqrid\": \"\", \n" +
                "\"ysqrgx\": \"母亲\", \n" +
                "\"zjh\": \"152827199011110909\", \n" +
                "\"zjzl\": null \n" +
                "}, \n" +
                "{ \n" +
                "\"jtcyid\": \"\", \n" +
                "\"jtcymc\": \"子女\", \n" +
                "\"sqrid\": \"\", \n" +
                "\"ysqrgx\": \"子女\", \n" +
                "\"zjh\": \"152827199011110909\", \n" +
                "\"zjzl\": null \n" +
                "}, \n" +
                "{ \n" +
                "\"jtcyid\": \"\", \n" +
                "\"jtcymc\": \"兄弟姐妹\", \n" +
                "\"sqrid\": \"\", \n" +
                "\"ysqrgx\": \"兄弟姐妹\", \n" +
                "\"zjh\": \"152827199011110909\", \n" +
                "\"zjzl\": null \n" +
                "} \n" +
                "]";

        //商品房返回结构
        String zlf1json ="[ \n" +"{\n" +
                "\t\"bdcSlJyxx\": {\n" +
                "\t\t\"htbah\": \"1202003969\",\n" +
                "\t\t\"htbh\": \"1202003969\",\n" +
                "\t\t\"jyje\": 777773.0\n" +
                "\t},\n" +
                "\t\"bdcQlr\": [{\n" +
                "\t\t\"qlrlb\": \"1\",\n" +
                "\t\t\"qlrmc\": \"王中梅\",\n" +
                "\t\t\"zjh\": \"342127197304137186\"\n" +
                "\t}],\n" +
                "\t\"bdcSlSqr\": [{\n" +
                "\t\t\"sqrlb\": \"1\",\n" +
                "\t\t\"sqrmc\": \"王中梅\",\n" +
                "\t\t\"zjh\": \"342127197304137186\"\n" +
                "\t}],\n" +
                "\t\"bdcSlFwxx\": {\n" +
                "\t\t\"fjh\": \"123\"\n" +
                "\t}\n" +
                "} \n" +
                "]";

    }
}
