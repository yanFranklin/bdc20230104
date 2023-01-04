package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.BaseUnitTest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BdcPrintServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/17/2018</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcPrintServiceImplTest extends BaseUnitTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcPrintServiceImpl bdcPrintService;

    private Integer FBCZ = 1;
    private String DYLX_ZS = "zs";
    private Integer QLR = 1;

    /**
     * Method: print(List<Map<String, List<Map>>> list)
     */
    @Test
    public void testPrint() throws Exception {
        String xmid = "4444444";
        Integer zslx = 1;
        String xml = zsPrinting(xmid, zslx);
        LOGGER.info(xml);
    }

    private String zsPrinting(String xmid, Integer zslx) {
        String xml = "";
        if (StringUtils.isNotBlank(xmid) && null != zslx) {
            Map<String, List<Map>> paramMap = new HashMap(1);
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (null != bdcXmDO) {
                Example example = new Example(BdcQlrDO.class);
                example.createCriteria().andEqualTo("xmid", xmid).andEqualTo("qlrlb", QLR);
                List<BdcQlrDO> bdcQlrDOList = entityMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    List<Map> maps = new ArrayList();
                    if (FBCZ.equals(bdcXmDO.getSqfbcz())) {
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                            Map<String, String> map = new HashMap(2);
                            map.put("zsid", bdcQlrDO.getZsid());
                            maps.add(map);
                        }
                    } else {
                        Map<String, String> map = new HashMap(2);
                        map.put("zsid", bdcQlrDOList.get(0).getZsid());
                        maps.add(map);
                    }

                    paramMap.put(DYLX_ZS, maps);
                    xml = bdcPrintService.print(paramMap);
                }
            }
            LOGGER.info(paramMap.toString());
        }

        return xml;
    }


} 
