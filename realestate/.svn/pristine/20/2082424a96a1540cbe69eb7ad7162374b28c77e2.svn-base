package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.inquiry.InquiryApp;
import cn.gtmap.realestate.inquiry.service.BdcZhpSjclService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.*;

/**
 * @author by Administrator.
 * @version 1.0, 2019/7/11
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = InquiryApp.class)
public class BdcZhpSjclRestControllerTest {
    @Autowired
    private BdcZhpSjclService bdcZhpSjclService;

    @Test
    public void dlxx() throws Exception {
//        String json = "{\"DATE_TIME\":\"2019/7/25 15:40:33\",\"DT_NAME\":\"包河区不动产登记中心\",\"WAIT_NUMBER\":\"3\"," +
//                "\"QUEUE\":[{\"CALL_NUMBER\":\"A001\",\"BUZ_NAME\":\"综合业务\"}," +
//                "{\"CALL_NUMBER\":\"C001\",\"BUZ_NAME\":\"特殊业务\"}," +
//                "{\"CALL_NUMBER\":\"B001\",\"BUZ_NAME\":\"个人业务\"}]}";
//        bdcZhpSjclService.dlxx(json);

        String json = "{\"DATE_TIME\": \"2019/8/22 10:35:07\",\"DT_NAME\": \"合肥市不动产登记中心\",\"WAIT_NUMBER\": \"7\",\"BUZ_WINDOW\": [{\"BUZ_CODE\": \"A\",\"WINDOW\": [\"1\", \"2\", \"3\"],\"BUZ_NAME\": \"个人业务\"}, {\"BUZ_CODE\": \"C\",\"WINDOW\": \n" +
                "[\"1\", \"2\", \"3\", \"4\", \"5\", \"6\"],\"BUZ_NAME\": \"综合业务\"}, {\"BUZ_CODE\": \"B\",\"WINDOW\": [\"3\", \"4\", \"5\", \"6\"],\"BUZ_NAME\": \"个人业务\"}],\"QUEUE\": [{\"CALL_NUMBER\": \"A1001\",\"BUZ_CODE\": \"A\",\"BUZ_NAME\": \"个人业务\"}," +
                " {\"CALL_NUMBER\": \"A1002\",\"BUZ_CODE\": \"A\",\"BUZ_NAME\": \"个人业务\"}, {\"CALL_NUMBER\": \"B1001\",\"BUZ_CODE\": \"B\",\"BUZ_NAME\": \"个人业务\"}, {\"CALL_NUMBER\": \"B1002\",\"BUZ_CODE\": \"B\",\"BUZ_NAME\": \"个人业务\"}, {\"CALL_NUMBER\": \"C1001\",\"BUZ_CODE\": \"C\",\"BUZ_NAME\": \"综合业务\"}, {\"CALL_NUMBER\": \"C1002\",\"BUZ_CODE\": \"C\",\"BUZ_NAME\": \"综合业务\"}, {\"CALL_NUMBER\": \"C1003\",\"BUZ_CODE\": \"C\",\"BUZ_NAME\": \"综合" +
                "业务\"}]}";
        bdcZhpSjclService.dlxx(json);

//        bdcZhpSjclService.qkhj();
//        String jsonOld = "{\"DATE_TIME\":\"2019/7/9 15:40:01\",\"DT_NAME\":\"测试大厅\",\"WAIT_NUMBER\":\"2\"," +
//                "\"QUEUE\":[{\"CALL_NUMBER\":\"A001\",\"BUZ_NAME\":\"综合业务\"}," +
//                "{\"CALL_NUMBER\":\"B001\",\"BUZ_NAME\":\"个人业务\"}]}";
//        bdcZhpSjclService.dlxx(jsonOld);

//        String jsonOldNew = "{\"DATE_TIME\":\"2019/7/9 15:40:01\",\"DT_NAME\":\"测试大厅\",\"WAIT_NUMBER\":\"4\"," +
//                "\"QUEUE\":[{\"CALL_NUMBER\":\"A001\",\"BUZ_NAME\":\"综合业务\"}," +
//                "{\"CALL_NUMBER\":\"A002\",\"BUZ_NAME\":\"综合业务\"}," +
//                "{\"CALL_NUMBER\":\"A003\",\"BUZ_NAME\":\"综合业务\"}," +
//                "{\"CALL_NUMBER\":\"B001\",\"BUZ_NAME\":\"个人业务\"}]}";
//        bdcZhpSjclService.dlxx(jsonOldNew);

//        String jsonNew = "{\"DATE_TIME\":\"2019/7/9 15:41:01\",\"DT_NAME\":\"测试大厅\",\"WAIT_NUMBER\":\"3\"," +
//                "\"QUEUE\":[{\"CALL_NUMBER\":\"A002\",\"BUZ_NAME\":\"综合业务\"}," +
//                "{\"CALL_NUMBER\":\"A003\",\"BUZ_NAME\":\"综合业务\"}," +
//                "{\"CALL_NUMBER\":\"B001\",\"BUZ_NAME\":\"个人业务\"}]}";
//        bdcZhpSjclService.dlxx(jsonNew);

    }

    @Test
    public void xxcl() throws Exception {
//        String jsonJh = "{\"DATE_TIME\":\"2019/7/9 15:41:33\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"CALL\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"A001\"}";
//        bdcZhpSjclService.jhxx(jsonJh);

//        String jsonOldJh = "{\"DATE_TIME\":\"2019/7/9 15:41:01\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"CALL\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\"}";
//        bdcZhpSjclService.jhxx(jsonOldJh);

//        String jsonNewJh = "{\"DATE_TIME\":\"2019/7/9 15:42:01\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"CALL\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\",\"VALUE\":\"1\"}";
//        bdcZhpSjclService.jhxx(jsonNewJh);

//        String jsonZt = "{\"DATE_TIME\":\"2019/7/9 15:42:02\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"PAUSE\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\",\"VALUE\":\"1\"}";
//        bdcZhpSjclService.ztxx(jsonZt);

//        String jsonOldJczt = "{\"DATE_TIME\":\"2019/7/9 15:41:01\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"CANCELPAUSE\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\",\"VALUE\":\"1\"}";
//        bdcZhpSjclService.jcztxx(jsonOldJczt);

//        String jsonNewJczt = "{\"DATE_TIME\":\"2019/7/9 15:43:02\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"CANCELPAUSE\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\",\"VALUE\":\"1\"}";
//        bdcZhpSjclService.jcztxx(jsonNewJczt);

//        String jsonValue = "{\"DATE_TIME\":\"2019/7/9 15:41:02\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"VALUE\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\",\"VALUE\":\"1\"}";
//        bdcZhpSjclService.pfxx(jsonValue);

//        String jsonOldValue = "{\"DATE_TIME\":\"2019/7/9 15:41:01\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"VALUE\"," +
//                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\",\"VALUE\":\"0\"}";
//        bdcZhpSjclService.pfxx(jsonOldValue);

        String jsonNewValue = "{\"DATE_TIME\":\"2019/7/9 15:41:03\",\"DT_NAME\":\"测试大厅\",\"MODE\":\"VALUE\"," +
                "\"WIN_NO\":\"1\",\"CALL_NUMBER\":\"B001\",\"VALUE\":\"5\"}";
        bdcZhpSjclService.pfxx(jsonNewValue);

    }


}