package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.core.dto.common.BdcXmForZxAccessDTO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessModelHandlerServiceImplTest {

    @Autowired
    private CommonService commonService;

    @Autowired
    private AccessModelHandlerServiceImpl accessModelHandlerService;

    @Test
    public void accessWithZxxm() {
        String param = "433J5348241J37E1,433J5348301J37E4,468F2440EO1J3032,468F2440D41J302Z";
        Set<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOSet = commonService.queryBdcXmForZxAccessListByXmidsAndWlxmAndZxyql(Arrays.asList(param.split(",")));
        if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOSet)) {
            accessModelHandlerService.accessWlxm(bdcXmForZxAccessDTOSet);
        }
    }
}