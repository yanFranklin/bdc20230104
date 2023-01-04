package cn.gtmap.realestate.inquiry.service.jsc.impl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.Base64Util;
import cn.gtmap.realestate.inquiry.InquiryApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InquiryApp.class)
@Slf4j
public class BdcXuanchengJscServiceImplTest {
    @Autowired
    BdcXuanchengJscServiceImpl bdcXuanchengJscService;

    @Autowired
    EntityMapper entityMapper;

    @Test
    public void queryJscDjZmj() {
    }

    @Test
    public void queryJscDjslList() {
        JscCommomQO info = new JscCommomQO();
        info.setTimeFrame("1");
        info.setSummaryDimension("MONTH");
        bdcXuanchengJscService.queryJscDjslList(info);
    }

    @Test
    public void queryJscSummary() {
    }

    @Test
    public void queryJscQl() {
    }

    @Test
    public void queryJscTrend() {
    }

    @Test
    public void queryJscTransaction() {
        String content = "JVBERi0xLjUNCjQgMCBvYmoNCjw8L1R5cGUgL1BhZ2UvUGFyZW50IDMgMCBSL0NvbnRlbnRzIDUgMCBSL01lZGlhQm94IFswIDAgNTk1LjI5OTk4Nzc5IDg0MS45MDAwMjQ0MV0vUmVzb3VyY2VzPDwvRm9udDw8L0ZBQUFBSiA5IDAgUi9GQUFBQkUgMTQgMCBSL0ZBQUFCSCAxNyAwIFIvRkFBQUNDIDIyIDAgUj4+L1hPYmplY3Q8PC9YMSA2IDAgUi9YMiA3IDAgUj4+Pj4vR3JvdXAgPDwvVHlwZS9Hcm91cC9TL1RyYW5zcGFyZW5jeS9DUy9EZXZpY2VSR0I+Pj4+DQplbmRvYmoNCjUgMCBvYmoNCjw8L0xlbmd0aCAyNiAwIFIvRmlsdGVyIC9GbGF0ZURlY29kZT4+c3RyZWFtDQp4nNVWZ5PUOBB9BQu7hCIcOYu4Sxitsiwym9gDLiyYPKSCAurqgOL/f6ElTfB4tMDwDbvslt0tdb/X6rYlE3R2JN0qI3kQQihjJHv7iX1lMikl015yZZmVmnvbV31Iyk9MakPy/yS94MGEEFyw9Ea0nj+y9xfYGk0WPE+ikwsvkkl6TEsr7rz2wVX5neXCxlM61iFzOrSwKhvnCLTPEUQpQ5U8Z/mRPWafo8OftMsWkQ96nZXkZf6JZEtfKPL+2WDG2EiJDbLEjFKZGZLxSv6SbDKRjERj1CMhGCm9M9lT63EI3YgMiWS8oossW9B/aJctCHoexHuCrtaDXikuQy+3XkXjhZrNr9yi4w4jKPX7niltLkk0WUVp1oEpz13wlEbL6gjjA5vDhvP1fyQ2joip0stNUSzXjTgEM477GElQphnGwjKjPdMMwxjFFXkmpikmikOk7VTFQOZYcvAb3omNtRE+XMWrXp18nw8lGjks8pEnrvYmxpqo3/XzNbroHC2mxqdQ4jCFTdiMacxgC7aOz5RVLmtples5UE758bV0X41tRdxBjOAeqVOqQKnKtKy2aaFyk9x4O+QC29Gl+LADO7ELu/EH9mAvXfuwHwdwEIdwGEdwlEbHcBwnSDONxF+HHHe0TLtOKOdZvUSrncQpbMNp7CzQqA2B19R5nDDSSadtidQzBQIiRm+HCZ0UY0zQWZwrxHR5uNlGl/OOG5MbfD81s+jOlYNTwnEdg6MmMGls3fMU3IUe9xdxaRDK4uKAkw0loi7ReYgyE7Ozj7LTAcc8BGVIQmF6/RzNQMPAwiVTRyOTO5DPIuJElcchi8u4gqu4huu4gZu4hQUs0uRrUUnLD/JS3yPbJSxjBbfzMgar+BN3cBf30ugv/E0Zni1wqIWYmLtMmsmu/sG/WMtddADlPh7Qzmaoyf1Dkqt4NIJrKzH3eH2m";
        byte[] decode = Base64Util.decode(content);
        String s = new String(decode);
        System.out.println(s);
    }

    /**
     * where中带子查询
     */
    @Test
    public void entityInsert() {
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setRzid("1231221");
        bdcCzrzDO.setCzzt(1);
        bdcCzrzDO.setSlbh("123");
        entityMapper.insertSelective(bdcCzrzDO);
    }
}