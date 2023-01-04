package cn.gtmap.realestate.certificate.web.rest; 

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.BaseUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/** 
* BdcZsRestController Tester. 
* 
* @author <a href="mailto:bianwen@gtmap.cn">bianwen</a> 
* @version 1.0 11/13/2018
* @description 证书服务测试类
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CertificateApp.class)
@WebAppConfiguration
public class BdcZsRestControllerTest extends BaseUnitTest {
    /**
     * 证书请求URL前置路径
     */
    private static final String REST_PATH_ZS = "/certificate/rest/v1.0/zs";


/** 
* 
* Method: queryBdcZs(@PathVariable String zsid) 
* 
*/ 
@Test
public void testQueryBdcZs() throws Exception {
    String url="/12345678";
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url),MockMvcResultMatchers.status().isOk());
    url="/";
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url),MockMvcResultMatchers.status().isNotFound());
} 

/** 
* 
* Method: queryBdcZsByXmid(@PathVariable String xmid) 
* 
*/ 
@Test
public void testQueryBdcZsByXmid() throws Exception {
    String url="/123456/zs";
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url),MockMvcResultMatchers.status().isOk());
    url="/zs";
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url),MockMvcResultMatchers.status().is2xxSuccessful());
} 

/** 
* 
* Method: listBdcZs(BdcZsQO bdcZsQO) 
* 
*/ 
@Test
public void testListBdcZs() throws Exception { 
    String url="/list";
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url).param("bdcdyh","320902004004GB00018F00010004")
            .param("bdcqzh","")
            .param("zl",""),
            MockMvcResultMatchers.status().isOk());

    //测试不传入任何参数
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url),
            MockMvcResultMatchers.status().isOk());

    //测试不存在于BdcZsQO实体类内的参数查询
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url).param("zsid","987653"),
            MockMvcResultMatchers.status().isOk());
} 

/** 
* 
* Method: bdcZsByPageJson(Pageable pageable, BdcZsQO bdcZsQO) 
* 
*/ 
@Test
public void testBdcZsByPageJson() throws Exception {
    String url="/page";
    //测试按shlsh倒排序，每页一条数据，取第三页的所有符合传参条件证书信息
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url)
                    .param("size","1").param("page","2").param("sort","zhlsh,desc")
                    .param("bdcdyh","320902004004GB00018F00010004")
                    .param("bdcqzh","")
                    .param("zl",""),
            MockMvcResultMatchers.status().isOk());

    //测试按shlsh倒排序，取所有符合传参条件的证书信息
    mockMvcController(MockMvcRequestBuilders.get(REST_PATH_ZS+url)
                    .param("size","").param("page","").param("sort","zhlsh,desc")
                    .param("bdcdyh","320902004004GB00018F00010004")
                    .param("bdcqzh","")
                    .param("zl",""),
            MockMvcResultMatchers.status().isOk());
} 

/** 
* 
* Method: updateBdcZs(BdcZsDO bdcZsDO) 
* 
*/ 
@Test
public void testUpdateBdcZs() throws Exception {
    String url="";

    mockMvcController(MockMvcRequestBuilders.patch(REST_PATH_ZS+url)
            .param("zsid","12345678")
            .param("bdcdyh","xxxxxxx"),
            MockMvcResultMatchers.status().isCreated());
} 


} 
