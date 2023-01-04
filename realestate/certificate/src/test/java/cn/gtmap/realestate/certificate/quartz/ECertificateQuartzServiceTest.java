package cn.gtmap.realestate.certificate.quartz;

import cn.gtmap.realestate.certificate.CertificateApp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * ECertificateQuartzService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>03/25/2020</pre>
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CertificateApp.class)
@WebAppConfiguration
public class ECertificateQuartzServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ECertificateQuartzServiceTest.class);

    @Autowired
    private ECertificateQuartzService eCertificateQuartzService;

    @Before
    public void before() throws Exception {
        LOGGER.warn("ECertificateQuartzServiceTest开始");
    }

    @After
    public void after() throws Exception {
        LOGGER.warn("ECertificateQuartzServiceTest结束");
    }

    /**
     * Method: syncCreateECertificate()
     */
    @Test
    public void testSyncCreateECertificate() throws Exception {
//TODO: Test goes here...
        LOGGER.warn("syncCreateECertificate开始");
        eCertificateQuartzService.syncCreateECertificate();
        LOGGER.warn("syncCreateECertificate结束");

    }
} 
