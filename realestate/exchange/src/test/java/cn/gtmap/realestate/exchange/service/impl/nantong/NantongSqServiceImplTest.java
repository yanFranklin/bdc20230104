package cn.gtmap.realestate.exchange.service.impl.nantong;

import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi.ClnrDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi.QiGhHmDataDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class NantongSqServiceImplTest {
    @Autowired
    NantongSqServiceImpl nantongSqService;

    @Test
    public void formGh() {
        BdcSdqghDO bdcSdqghDO = new BdcSdqghDO();
        Map<String, Object> param = new HashMap<>();
        //param.put("sbdsImg",new String("122222").getBytes(StandardCharsets.UTF_8));
        param.put("name","122222");
        param.put("djyy","122222");
        param.put("gzldyid","122222");
        param.put("userCode","122222");
        Map<String, ClnrDTO> files = new HashMap<>();
        byte[] sbdsImgByte = new byte[500];
        for (int i = 0; i < 500; i++) {
            sbdsImgByte[i] = 1;
        }
        ClnrDTO clnrDTO = new ClnrDTO();
        clnrDTO.setFjmc("123.png");
        clnrDTO.setFjnr(new String(sbdsImgByte));
        files.put("multipartFiles",clnrDTO);
        nantongSqService.formGh(bdcSdqghDO,
                "http://192.168.5.89:8800/realestate-etl/wwsq/wjcs",
                param, files,BdcSdqEnum.Q, QiGhHmDataDto.class, "token");
    }

    @Test
    public void formGhZs() {
        nantongSqService.getzs("45P93554EL2B2EAM");
    }
}