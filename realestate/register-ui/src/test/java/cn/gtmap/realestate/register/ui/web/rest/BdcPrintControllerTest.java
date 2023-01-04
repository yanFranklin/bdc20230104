package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcShxxRestService;
import cn.gtmap.realestate.register.BaseUnitTest;
import cn.gtmap.realestate.register.ui.App;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;


/**
 * @param
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @return
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcPrintControllerTest extends BaseUnitTest {
    @Autowired
    BdcShxxRestService bdcShxxRestService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcPrintControllerTest.class);


    /**
     * Method: printZs(@PathVariable(name = "zsid") String zsid, @PathVariable(name = "zslx") String zslx)
     */
    @Test
    public void testPrintZs() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: printBatchZs(@RequestParam(name = "zsidList") String zsidStr, @PathVariable(value = "zslx") String zslx)
     */
    @Test
    public void testPrintBatchZs() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: printAllZs(@PathVariable(value = "zslx") String zslx, @RequestParam(name = "gzlslid") String gzlslid)
     */
    @Test
    public void testPrintAllZs() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: fzjlPrintXml(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid, @RequestParam(name = "dylx") String dylx)
     */
    @Test
    public void testFzjlPrintXml() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: yjdPrintXml(@PathVariable(value = "yjdid") String yjdid)
     */
    @Test
    public void testYjdPrintXml() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: spbPrintXml(@PathVariable(value = "gzlslid") String gzlslid, @PathVariable(value = "dylx") String dylx)
     */
    @Test
    public void testSpbPrintXml() throws Exception {
//        String gzlslid="18272";//1212
//        String gzlslid="19386";//94
//        String gzlslid="38341";//909
        String gzlslid="42623";//69
        String dylx="bdcSqSpb";
        Date before=new Date();
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setGzlslid(gzlslid);
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        String page = bdcShxxRestService.bdPrintXml(bdcPrintDTO);
        Date after=new Date();
        LOGGER.info("打印时间："+(after.getTime()-before.getTime()));
        LOGGER.info("打印："+page);
    }

    @Test
    public void testDywqd(){
        Date before=new Date();
        String gzlslid="18143";//1000
//        String gzlslid="19386";//100
        String dylx="dyawqd";
        String page = bdcBdcdyFeignService.bdcdyPrintXml(gzlslid, dylx, this.bdcUrl());
        Date after=new Date();
        LOGGER.info("打印时间："+(after.getTime()-before.getTime()));
        LOGGER.info("打印："+page);
    }

    /**
     * Method: ewm(@PathVariable(name = "ewmnr") String ewmnr, HttpServletResponse response)
     */
    @Test
    public void testEwm() throws Exception {
//TODO: Test goes here... 
    }

    @Test
    public void testDifferent(){
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
        int count = 0;
        List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
        Map map = new HashMap();
        map.put("orgName","部门");
        map.put("processName","流程");
        map.put("taskName","节点");
        map.put("realCount","次数");
        map.put("count","2");
        listMap.add(map);
        Map map2 = new HashMap();
        map2.put("orgName","部门2");
        map2.put("processName","流程2");
        map2.put("taskName","节点2");
        map2.put("realCount","次数2");
        map2.put("count","2");
        listMap.add(map2);
        Multimap<String, List> zbMap = ArrayListMultimap.create();
        zbMap.put("bdc_gzltj",listMap);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj("{}");
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(zbMap));
        String currentms = System.currentTimeMillis()+"";
        bdcDysjDTO.setDyzd("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<fetchdatas> \n" +
                "  <page> \n" +
                "    <datas> \n" +
                "    </datas>  \n" +
                "    <detail ID=\"bdc_gzltj\"> \n" +
                "      <row ID=\"${count}\"> \n" +
                "\t\t\t<data name=\"taskName\" type=\"String\">$taskName</data>\n" +
                "\t\t\t<data name=\"processName\" type=\"String\">$processName</data>\n" +
                "\t\t\t<data name=\"realCount\" type=\"String\">$realCount</data>\n" +
                "\t\t\t<data name=\"orgName\" type=\"String\">$orgName</data>\n" +
                "      </row> \n" +
                "    </detail> \n" +
                "  </page> \n" +
                "</fetchdatas>");
        bdcDysjDTOList.add(bdcDysjDTO);
        String xml =  bdcPrintFeignService.printDatas(bdcDysjDTOList);
        LOGGER.info("xml======="+xml);
    }


} 
