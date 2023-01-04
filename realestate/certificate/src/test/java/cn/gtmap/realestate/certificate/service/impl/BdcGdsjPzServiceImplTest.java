package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.BaseUnitTest;
import cn.gtmap.realestate.certificate.core.mapper.BdcConfigMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcGdsjPzMapper;
import cn.gtmap.realestate.certificate.service.BdcGdsjPzService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import groovy.util.Node;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 11/19/2018
 * @description BdcGdServiceImpl Tester.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CertificateApp.class)
@WebAppConfiguration
public class BdcGdsjPzServiceImplTest extends BaseUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGdsjPzServiceImplTest.class);

    BdcGdsjPzService bdcGdsjPzService;

    @Autowired
    BdcGdsjPzMapper bdcGdsjPzMapper;
    @Autowired
    BdcConfigMapper bdcConfigMapper;

    @Mock
    BdcXmFeignService bdcXmFeignService;

    @Before
    public void before() throws Exception {
        bdcGdsjPzService = mock(BdcGdsjPzServiceImpl.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description queryBdcGdxx(Map gdpz)
     */
    @Test
    public void testQueryBdcGdxx() throws Exception {
        Map param = Maps.newHashMap();
        param.put("djxl", "100");
        param.put("xmid", "1");
        Map pzResult = bdcGdsjPzMapper.queryBdcGdsjPz(param);
        pzResult.put("xmid", "1");
        Map result = bdcGdsjPzService.queryBdcGdxx(pzResult);
        Assert.assertNotNull(result);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description gdBdcxx(String gzlslid)
     */
    @Test
    public void testGdBdcxx() throws Exception {
        String gzlslid = "gzlslid";
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.listBdcXmDO();
        when(bdcXmFeignService.listBdcXm((eq(bdcXmQO)))).thenReturn(bdcXmDOList);
        String djxl = bdcXmDOList.get(0).getDjxl();
        if (StringUtils.isEmpty(djxl)) {
            throw new AppException("登记小类不能为空！");
        }
        Map configParam = Maps.newHashMap();
        configParam.put("djxl", djxl);
        Map gdpz = bdcGdsjPzMapper.queryBdcGdsjPz(configParam);
        if (MapUtils.isEmpty(gdpz)) {
            throw new AppException("归档配置结果为空");
        }
        /**归档配置xml*/
        Node gdNode = getBdcGdNode(CommonConstantUtils.XML_PATH_GDXX);
        /**归档archive model 配置xml*/
        Node gdMcNode = getBdcGdNode(CommonConstantUtils.XML_PATH_GDMC);
        /**归档类型，qllx,djxl,djlx,bdclx*/
        String gdlx = XmlUtils.getGdlxpz(gdMcNode);
        /**登记信息是否归多条*/
        int djxxgdt = Integer.parseInt(String.valueOf(gdpz.get(CommonConstantUtils.GD_DJXXGDT)));
        /**原文是否归多条*/
        int ywgdt = Integer.parseInt(String.valueOf(gdpz.get(CommonConstantUtils.GD_YWGDT)));
        /**doc xml字符串*/
        String docStr = XmlUtils.parseGdDoc(gdNode);
        StringBuilder xmlBulider = new StringBuilder();
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(bdcXmDO);
            String gdlxValue = String.valueOf(jsonObject.get(gdlx));
            String archiveName = XmlUtils.getArchiveModelName(gdlxValue, gdMcNode);
            gdNode = XmlUtils.setXmlArchiveName(gdNode,archiveName);
            /**archive xml字符串*/
            String archiveStr = XmlUtils.parseGdArchive(gdNode);
            gdpz.put("xmid", bdcXmDO.getXmid());
            Map bdcGdxx = queryBdcGdxx(gdpz);
            /**添加未查询出的字段*/
            bdcGdxx=XmlUtils.replaceNull(gdNode,bdcGdxx);
            String gdxml = XmlUtils.replaceGdXmlValue(archiveStr, bdcGdxx);
            xmlBulider.append(gdxml);
            xmlBulider.append(queryBdcGdYw(docStr,ywgdt));
            if (djxxgdt == 0) {
                break;
            }
        }
        LOGGER.info(XmlUtils.processCharacterSet(XmlUtils.replacePageInXml(gdNode, xmlBulider.toString())));

    }

    private List<BdcXmDO> listBdcXmDO() {
        BdcXmDO bdcXmDO1 = new BdcXmDO();
        BdcXmDO bdcXmDO2 = new BdcXmDO();
        bdcXmDO1.setGzlslid("gzlslid");
        bdcXmDO1.setXmid("1");
        bdcXmDO1.setDjxl("100");
        bdcXmDO1.setQllx(4);

        bdcXmDO2.setGzlslid("gzlslid");
        bdcXmDO2.setXmid("123456");
        bdcXmDO2.setDjxl("100");
        bdcXmDO2.setQllx(6);
        return Lists.newArrayList(bdcXmDO1, bdcXmDO2);
    }

    private Node getBdcGdNode(String path) {
        Node gdNode = null;
        Resource resource = new ClassPathResource(path);
        try (InputStream inputStream = resource.getInputStream()) {
            gdNode = XmlUtils.getXmlNodeByInputStream(inputStream);
        } catch (IOException e) {
            LOGGER.debug("未找到对应的归档xml" + e.getMessage(), e);
            throw new AppException(1002, "未找到对应的xml");
        }
        return gdNode;
    }

    private Map queryBdcGdxx(Map gdpz) {
        String sqls = String.valueOf(gdpz.get("GDSJY"));
        if (StringUtils.isEmpty(sqls)) {
            throw new AppException("数据源不能为空！");
        }
        if (sqls.matches(CommonConstantUtils.ZF_ZW_FH)) {
            throw new AppException("配置的归档数据源中不允许有中文字符！");
        }
        List<String> sqlList = org.assertj.core.util.Lists.newArrayList(sqls.split(CommonConstantUtils.ZF_YW_FH));
        List<Map> resultList = org.assertj.core.util.Lists.newArrayList();
        sqlList.forEach(sql -> {
            if (!sql.contains(CommonConstantUtils.SQL_CS_XMID)) {
                throw new AppException("配置的sql中缺少查询条件！");
            }
            gdpz.put("sql", sql);
            List<Map> result = bdcConfigMapper.executeConfigSql(gdpz);
            resultList.addAll(result);
        });
        Map result = Maps.newHashMap();
        resultList.forEach(map -> {
            result.putAll(map);
        });
        return result;
    }

    public String queryBdcGdYw(String docStr,Integer ywgdt) {
        StringBuilder ywBulider=new StringBuilder();
        //TODO 获取原文归档xml
        return ywBulider.toString();
    }


} 
