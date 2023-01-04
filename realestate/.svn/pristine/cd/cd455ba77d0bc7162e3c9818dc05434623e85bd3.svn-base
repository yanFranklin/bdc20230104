package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblLogService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 补录日志实现类测试类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 11:05 上午 2020/4/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class BdcXxblLogServiceImplTest {
    @Autowired
    private BdcXxblLogService bdcXxblLogService;

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Test
    public void testListLog() {
        Page<AuditLogDto> auditLogDtos = bdcXxblLogService.listLog(null, "3740543", LogEventEnum.BLLC_MODIFY);
        System.out.println(JSON.toJSONString(auditLogDtos));
    }
}
