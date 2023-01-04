package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcSlZjjgServiceImplTest {

    @Autowired
    StorageClientMatcher storageClient;


    @MockBean
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;

    @Test
    public void getFileStorageUrl() {
        InputStream inputStream;
        StorageDto dto;
        try {
            inputStream = new FileInputStream(new File("Oracle.pdf"));

            //创建一个文件目录
            StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, "234", "资金监管", "");
            String fileContent = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(IOUtils.toByteArray(inputStream));
            System.out.println(fileContent);
            //文件上传到大云
            MultipartFile file = Base64Utils.base64ToMultipart(fileContent);
            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
            multipartDto.setName("资金监管.pdf");
            multipartDto.setNodeId(storageDto.getId());
            multipartDto.setSpaceCode("234");
            try {
                multipartDto.setData(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename("资金监管.pdf");
            dto = storageClient.multipartUpload(multipartDto);
        }catch (Exception e){

        }
    }
}