package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

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
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GxWwSqxxServiceImplTest {
    @Autowired
    GxWwSqxxServiceImpl gxWwSqxxService;

    @Test
    public void querycqzdyxx() {
        List<Map> querycqzdyxx = gxWwSqxxService.querycqzdyxx("皖(2022)蜀山区不动产证明第1000044号");
        log.info("{}", JSON.toJSONString(querycqzdyxx));
    }

    @Test
    public void getBdczsxx() {
        List<Map> querycqzdyxx = gxWwSqxxService.getBdczsxx(
                "皖(2018)合肥市不动产权第10023999号",
                "",
                "",
                "",
                ""
        );
        log.info("{}", JSON.toJSONString(querycqzdyxx));
    }

}