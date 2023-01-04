package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQlrQO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.zzcxj.response.FwqsCxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BdcFwQsxxServiceImplTest {
    @Autowired
    BdcFwQsxxServiceImpl bdcFwQsxxService;

    @Test
    public void queryFwQsxx() {
        FwqlCxRequestDTO fwqlCxRequestDTO = new FwqlCxRequestDTO();

        List<FwqlCxRequestQlr> qlrxx = new ArrayList<>();
        FwqlCxRequestQlr bdcFwQsxxQlrQO = new FwqlCxRequestQlr();
        bdcFwQsxxQlrQO.setQlr("开发商");
        qlrxx.add(bdcFwQsxxQlrQO);

        fwqlCxRequestDTO.setQlrlist(qlrxx);

        List<FwqsCxResponseDTO> fwqsCxResponseDTOS = bdcFwQsxxService.queryFwQsxx(fwqlCxRequestDTO, "");
        log.info("{}", JSON.toJSONString(fwqsCxResponseDTOS));
    }
}