package cn.gtmap.realestate.certificate.web.rest; 

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.BaseUnitTest;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/** 
* BdcGdxxRestController Tester. 
* 
* @author <a href="mailto:bianwen@gtmap.cn">bianwen</a> 
* @version 1.0 11/19/2018
* @description 归档服务单元测试
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CertificateApp.class)
@WebAppConfiguration
public class BdcGdxxRestControllerTest extends BaseUnitTest {
    @Autowired
    BdcGdxxRestController bdcGdxxRestController;
    /**
     * 归档请求URL前置路径
     */
    private static final String REST_PATH_GDXX = "/certificate/rest/v1.0/gdxx";

/** 
* 
* Method: postArchiveInfo(String archiveUrl, String archiveXml) 
* 
*/ 
@Test
public void testPostArchiveInfo() throws Exception {
    String url="/archive";
    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<list>\n" +
            "<archive type=\"Bdcscdj\">\n" +
            "<field name=\"zdzhh\">320684100003GB00207</field>\n" +
            "<field name=\"bdcdyh\">320684100003GB00207F48110028</field>\n" +
            "<field name=\"qlrmc\">宋小贤</field>\n" +
            "<field name=\"zl\">九如巷47号</field><field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
            "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000303号</field>\n" +
            "<field name=\"proId\">12O83221L5WY690J</field>\n" +
            "</archive>\n" +
            "</list>";
    mockMvcController(MockMvcRequestBuilders.post(REST_PATH_GDXX+url).param("archiveUrl","http://192.168.2.206:8064/archive/gateway.action")
                    .param("archiveXml",xml),
            MockMvcResultMatchers.status().isOk());
} 

/** 
* 
* Method: initBdcGdxx(String archiveResponseXml) 
* 
*/ 
@Test
public void testInitBdcGdxx() throws Exception {
    String url="/init";
    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<list result=\"succeed\">\n" +
            "<archive type=\"Bdcdydj\" result=\"succeed\">\n" +
            "<field name=\"zdzhh\">320684100003GB00207</field>\n" +
            "<field name=\"bdcdyh\">320684100003GB00207F48110028</field>\n" +
            "<field name=\"qlrmc\">宋小贤</field>\n" +
            "<field name=\"zl\">九如巷47号</field><field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
            "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000303号</field>\n" +
            "<field name=\"proId\">12O83221L5WY690J</field>\n" +
            "<field name=\"id\">f1671c0876614028824e671c005d0001</field>\n" +
            "<field name=\"mlh\">3</field><field name=\"ajh\">88</field>\n" +
            "</archive>\n" +
            "<archive type=\"Bdczydj\" result=\"fail\" msg=\"模型【Bdcscdj】未针对单位代码【320684】分配对应目录号\">\n" +
            "<field name=\"zdzhh\">320684108206GB00153</field>\n" +
            "<field name=\"bdcdyh\">320684108206GB00153F00010001</field>\n" +
            "<field name=\"mlh\"/><field name=\"qlrmc\">宋宋</field>\n" +
            "<field name=\"zl\">习正村</field>\n" +
            "<field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
            "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000515号</field>\n" +
            "<field name=\"proId\">12O83221L5WY6911</field>\n" +
            "</archive>\n" +
            "</list>";
    mockMvcController(MockMvcRequestBuilders.post(REST_PATH_GDXX+url).param("archiveResponseXml",xml),
            MockMvcResultMatchers.status().isOk());
} 


/** 
* 
* Method: insertBdcGdxxList(String archiveResponseXml) 
* 
*/ 
@Test
public void testInsertBdcGdxxList() throws Exception {
    String url="/list";
    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<list result=\"succeed\">\n" +
            "<archive type=\"Bdcdydj\" result=\"succeed\">\n" +
            "<field name=\"zdzhh\">320684100003GB00207</field>\n" +
            "<field name=\"bdcdyh\">320684100003GB00207F48110028</field>\n" +
            "<field name=\"qlrmc\">宋小贤</field>\n" +
            "<field name=\"zl\">九如巷47号</field><field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
            "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000303号</field>\n" +
            "<field name=\"proId\">12O83221L5WY690J</field>\n" +
            "<field name=\"id\">f1671c0876614028824e671c005d0001</field>\n" +
            "<field name=\"mlh\">3</field><field name=\"ajh\">88</field>\n" +
            "</archive>\n" +
            "<archive type=\"Bdczydj\" result=\"fail\" msg=\"模型【Bdcscdj】未针对单位代码【320684】分配对应目录号\">\n" +
            "<field name=\"zdzhh\">320684108206GB00153</field>\n" +
            "<field name=\"bdcdyh\">320684108206GB00153F00010001</field>\n" +
            "<field name=\"mlh\"/><field name=\"qlrmc\">宋宋</field>\n" +
            "<field name=\"zl\">习正村</field>\n" +
            "<field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
            "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000515号</field>\n" +
            "<field name=\"proId\">12O83221L5WY6911</field>\n" +
            "</archive>\n" +
            "</list>";
    mockMvcController(MockMvcRequestBuilders.post(REST_PATH_GDXX+url).param("archiveResponseXml",xml),
            MockMvcResultMatchers.status().isOk());
}


    /**
     *
     * Method: bdcGdxxByPage(Pageable pageable, BdcGdxxQO bdcGdxxQO)
     *
     */
    @Test
    public void testBdcGdxxByPage() throws Exception {
        String url="/page";
        //测试按归档时间倒排序，每页一条数据，取第一页的所有符合传参条件归档信息
        mockMvcController(MockMvcRequestBuilders.get(REST_PATH_GDXX+url)
                        .param("size","1").param("page","").param("sort","gdsj,desc")
                        .param("bdcdyh","320802003009GB00002F00010001")
                        .param("bdcqzh","")
                        .param("zl",""),
                MockMvcResultMatchers.status().isOk());

        //测试按归档时间倒排序，取所有符合传参条件的归档信息
        mockMvcController(MockMvcRequestBuilders.get(REST_PATH_GDXX+url)
                        .param("size","").param("page","").param("sort","gdsj,desc")
                        .param("bdcdyh","320802003009GB00002F00010001")
                        .param("bdcqzh","")
                        .param("zl",""),
                MockMvcResultMatchers.status().isOk());
    }

    /**
     *
     * Method: listBdcGdxx(BdcGdxxQO bdcGdxxQO)
     *
     */
    @Test
    public void testListBdcGdxx() throws Exception {
        String url="/list";
        mockMvcController(MockMvcRequestBuilders.get(REST_PATH_GDXX+url).param("bdcdyh","320802003009GB00002F00010001")
                        .param("bdcqzh","")
                        .param("zl",""),
                MockMvcResultMatchers.status().isOk());

        //测试不传入任何参数
        mockMvcController(MockMvcRequestBuilders.get(REST_PATH_GDXX+url),
                MockMvcResultMatchers.status().isOk());

        //测试不存在于实体类内的参数查询
        mockMvcController(MockMvcRequestBuilders.get(REST_PATH_GDXX+url).param("daid","987653"),
                MockMvcResultMatchers.status().isOk());
    }

    /**
     *
     * Method: queryBdcGdxx(String gdxxid)
     *
     */
    @Test
    public void testQueryBdcGdxx() throws Exception {
        String url="/f16729e13d34402c80856729defb0003";
        mockMvcController(MockMvcRequestBuilders.get(REST_PATH_GDXX+url),MockMvcResultMatchers.status().isOk());
        url="/";
        mockMvcController(MockMvcRequestBuilders.get(REST_PATH_GDXX+url),MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testPostArchiveByPz() throws Exception {
        bdcGdxxRestController.postArchiveByPz("5630504",null,null,"123");
    }

    @Test
    public void testPostArchiveByPz1() throws Exception {
        bdcGdxxRestController.postArchiveByPz("4591003",null,null,"123");
    }
} 
