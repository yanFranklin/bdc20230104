package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.BaseUnitTest;
import cn.gtmap.realestate.certificate.service.BdcGdxxService;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcGdxxQO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * BdcGdxxServiceImpl Tester.
 *
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
 * @version 1.0 11/19/2018
 * @description 归档接口单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CertificateApp.class)
@WebAppConfiguration
public class BdcGdxxServiceImplTest extends BaseUnitTest {
    @Autowired
    private BdcGdxxService bdcGdxxService;

    /**
     *
     * Method: insertBdcGdxx(BdcGdxxDO bdcGdxxDO)
     *
     */
    @Test
    public void testInsertBdcGdxx() throws Exception {
        BdcGdxxDO bdcGdxxDO=new BdcGdxxDO();
        bdcGdxxDO.setXmid("11111");
        bdcGdxxDO.setDaid("222222");
        bdcGdxxDO.setGdxxid(UUIDGenerator.generate16());
        bdcGdxxDO.setGdsj(new Date());
        bdcGdxxService.insertBdcGdxx(bdcGdxxDO);

        BdcGdxxQO bdcGdxxQO=new BdcGdxxQO();
        bdcGdxxQO.setXmid("11111");
        bdcGdxxQO.setDaid("222222");
        List gdList=bdcGdxxService.listBdcGdxx(bdcGdxxQO);
        assertEquals(1,gdList.size());

    }

    /**
     *
     * Method: postArchiveInfo(String archiveUrl, String archiveXml)
     *
     */
    @Test
    public void testPostArchiveInfo() throws Exception {
        String url="http://192.168.2.206:8064/archive/gateway.action";
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<list>\n" +
                "<archive type=\"Bdcscdj\">\n" +
                "<field name=\"zdzhh\">320684100003GB00207</field>\n" +
                "<field name=\"bdcdyh\">320684100003GB00207F48110028</field>\n" +
                "<field name=\"qlrmc\">宋小贤</field>\n" +
                "<field name=\"zl\">九如巷47号</field><field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
                "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000303号</field>\n" +
                "<field name=\"proId\">123456</field>\n" +
                "</archive>\n" +
                "<archive type=\"Bdcscdj\">\n" +
                "<field name=\"zdzhh\">320684108206GB00153</field>\n" +
                "<field name=\"bdcdyh\">320684108206GB00153F00010001</field>\n" +
                "<field name=\"mlh\"/><field name=\"qlrmc\">宋宋</field>\n" +
                "<field name=\"zl\">习正村</field>\n" +
                "<field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
                "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000515号</field>\n" +
                "<field name=\"proId\">56789</field>\n" +
                "</archive>\n" +
                "</list>";
        bdcGdxxService.postArchiveInfo(url,xml);


    }

/** 
* 
* Method: initBdcGdxx(String archiveResponseXml) 
* 
*/ 
@Test
public void testInitBdcGdxx() throws Exception { 

    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<list result=\"succeed\">\n" +
            "<archive type=\"Bdcscdj\" result=\"succeed\">\n" +
            "<field name=\"zdzhh\">320684100003GB00207</field>\n" +
            "<field name=\"bdcdyh\">320684100003GB00207F48110028</field>\n" +
            "<field name=\"qlrmc\">宋小贤</field>\n" +
            "<field name=\"zl\">九如巷47号</field><field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
            "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000303号</field>\n" +
            "<field name=\"proId\">12O83221L5WY690J</field>\n" +
            "<field name=\"id\">f1671c0876614028824e671c005d0001</field>\n" +
            "<field name=\"mlh\">3</field><field name=\"ajh\">88</field>\n" +
            "</archive>\n" +
            "<archive type=\"Bdcscdj\" result=\"fail\" msg=\"模型【Bdcscdj】未针对单位代码【320684】分配对应目录号\">\n" +
            "<field name=\"zdzhh\">320684108206GB00153</field>\n" +
            "<field name=\"bdcdyh\">320684108206GB00153F00010001</field>\n" +
            "<field name=\"mlh\"/><field name=\"qlrmc\">宋宋</field>\n" +
            "<field name=\"zl\">习正村</field>\n" +
            "<field name=\"qllx\">国有建设用地使用权/房屋所有权</field>\n" +
            "<field name=\"bdcqzh\">苏(2017)海门市不动产权第0000515号</field>\n" +
            "<field name=\"proId\">12O83221L5WY6911</field>\n" +
            "</archive>\n" +
            "</list>";
    List<BdcGdxxDO> list=bdcGdxxService.initBdcGdxx(xml);
    assertEquals(2,list.size());
} 

/** 
* 
* Method: listBdcGdxx(BdcGdxxQO bdcGdxxQO) 
* 
*/ 
@Test
public void testListBdcGdxx() throws Exception { 
    BdcGdxxQO bdcGdxxQO=new BdcGdxxQO();
    List bdcGdxxList=null;

    bdcGdxxList=bdcGdxxService.listBdcGdxx(bdcGdxxQO);
    assertTrue(CollectionUtils.isNotEmpty(bdcGdxxList));

    bdcGdxxQO.setBdcdyh("320802003009GB00002F00010001");
    bdcGdxxList=bdcGdxxService.listBdcGdxx(bdcGdxxQO);
    assertTrue(CollectionUtils.isNotEmpty(bdcGdxxList)&&bdcGdxxList.size()==1);

} 

/** 
* 
* Method: queryBdcGdxx(String gdxxid) 
* 
*/ 
@Test
public void testQueryBdcGdxx() throws Exception { 
    BdcGdxxDO bdcGdxxDO=null;
    bdcGdxxDO=bdcGdxxService.queryBdcGdxx("f16729e13d34402c80856729defb0003");
    assertNotNull(bdcGdxxDO);

    bdcGdxxDO=bdcGdxxService.queryBdcGdxx("");
    assertNull(bdcGdxxDO);

    bdcGdxxDO=bdcGdxxService.queryBdcGdxx("asdfg");
    assertNull(bdcGdxxDO);

} 


} 



