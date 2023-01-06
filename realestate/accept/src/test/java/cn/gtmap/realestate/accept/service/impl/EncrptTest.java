package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.mapper.BdcLshMapper;
import cn.gtmap.realestate.accept.core.mapper.BdcSlHsxxMapper;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxmMapper;
import cn.gtmap.realestate.accept.core.mapper.BdcSlYwxxMapper;
import cn.gtmap.realestate.accept.service.BdcSlService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
//import cn.gtmap.sdk.mybatis.plugin.utils.JdbcConstantsEnum;
//import cn.gtmap.sdk.mybatis.plugin.utils.JdbcConstantsEnum;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/26
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class EncrptTest {
    @Autowired
    List<BdcSlService> bdcSlServiceList;

    @Autowired
    BdcSlYwxxMapper bdcSlYwxxMapper;

    @Autowired
    BdcLshMapper bdcLshMapper;

    @Autowired
    BdcSlSfxmMapper bdcSlSfxmMapper;

    @Autowired
    BdcSlHsxxMapper bdcSlHsxxMapper;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    Repository repository;

    @Test
    public void parse() {
        String statement = "select\n" +
                "    replace(to_char(wm_concat(distinct(t.qlrmc))), ',', '/')                       ywrmc,\n" +
                "       replace(to_char(wm_concat(distinct(mc))), ',', '/')                          ywrzjzl,\n" +
                "       replace(to_char(wm_concat(distinct(zjh))), ',', '/')                         ywrzjh,\n" +
                "       replace(to_char(wm_concat(distinct(txdz))), ',', '/')                        ywrtxdz,\n" +
                "       replace(to_char(wm_concat(distinct(frmc))), ',', '/')                        ywrfrmc,\n" +
                "       case\n" +
                "           when qlrlx = '1' then replace(to_char(wm_concat(dh)), ',', '/')\n" +
                "           when qlrlx = '2' then replace(to_char(wm_concat(frdh)), ',', '/') end as ywrfrdh,\n" +
                "       replace(to_char(wm_concat(distinct(dlrmc))), ',', '/')                       ywrdlrmc,\n" +
                "       replace(to_char(wm_concat(distinct(dlrdh))), ',', '/')                       ywrdldh,\n" +
                "       replace(to_char(wm_concat(distinct(dljg))), ',', '')                         ywrdljg,\n" +
                "       replace(to_char(wm_concat(distinct(yb))), ',', '/')                          ywryb\n" +
                "from (select distinct a.qlrmc,\n" +
                "                      b.mc,\n" +
                "                      a.zjh,\n" +
                "                      a.txdz,\n" +
                "                      a.frmc,\n" +
                "                      a.dh,\n" +
                "                      a.frdh,\n" +
                "                      a.dlrmc,\n" +
                "                      a.dlrdh,\n" +
                "                      a.dljg,\n" +
                "                      a.yb,\n" +
                "                      a.qlrlx\n" +
                "      from bdc_qlr a\n" +
                "               left join bdc_zd_zjzl b on a.zjzl = b.dm\n" +
                "      where a.QLRID in (select max(a.QLRID) as QLRID\n" +
                "                        from bdc_qlr a\n" +
                "                                 left join bdc_xm x on x.xmid = a.xmid\n" +
                "                                 left join BDC_CSH_FWKG_SL on x.XMID = BDC_CSH_FWKG_SL.ID\n" +
                "                        where a.qlrlb = 2\n" +
                "                          and x.GZLSLID = (select gzlslid from bdc_xm where xmid = '1')\n" +
                "                          and (\n" +
                "                                ((BDC_CSH_FWKG_SL.ZSXH is null or BDC_CSH_FWKG_SL.QLLX != 37) and\n" +
                "                                 x.DJXL = (select DJXL from BDC_CSH_FWKG_SL where ID = '1'))\n" +
                "                                or\n" +
                "                                ((BDC_CSH_FWKG_SL.ZSXH is not null or BDC_CSH_FWKG_SL.QLLX = 37) and\n" +
                "                                 BDC_CSH_FWKG_SL.ZSXH in (select ZSXH\n" +
                "                                                         from BDC_CSH_FWKG_SL\n" +
                "                                                         where ID = '1'))\n" +
                "                            )\n" +
                "                        group by a.qlrmc) ) t\n" +
                "    INNER JOIN BDC_XM on t.QLRMC = BDC_XM.DJXL\n" +
                "    INNER JOIN BDC_XM_LSGX BXL on BDC_XM.WJBS = BXL.WJBS \n" +
                "group by qlrlx";
        List<SQLStatement> statementList = SQLUtils
                .parseStatements(statement, "ORACLE");
        for (SQLStatement sqlStatement : statementList) {
            System.out.println(sqlStatement);
        }

    }

    @Test
    public void parse2() {
        //String statement = "select x.*,bdc_qlr.qlrmc,zjh from bdc_xm x INNER join bdc_qlr on x.xmid = bdc_qlr.xmid";
        String statement = "  select t.* from (\n" +
                "        select 1 as sz,t.fw_hs_index fwid,t.bdcdyh,t.zl,t.fjh,t.scjzmj,t.fw_dcb_index from fw_hs t\n" +
                "        where exists(select 1 from s_sj_bdcdyhxszt s where s.bdcdyh = t.bdcdyh and nvl(s.sfba,'0') in (?,?) )\n" +
                "        union all\n" +
                "        select t.fw_hs_index fwid,t.bdcdyh,t.zl,t.fjh,t.scjzmj,t.fw_dcb_index from fw_ychs t\n" +
                "        where exists(select 1 from s_sj_bdcdyhxszt s where s.bdcdyh = t.bdcdyh and nvl(s.sfba,'0')!=1)\n" +
                "        )t\n" +
                "        where 1=1";
//        String statement = "select *\n" +
//                "from BDC_XM where DJXL = 1\n" +
//                "              and SPLY in (2,3,4)\n" +
//                "              and SPLY in (?,?,?)\n" +
//                "              and AJZT > 1\n" +
//                "              and JSSJ > TO_DATE('2022-02-02')\n" +
//                "              and to_char(BDC_XM.BDCDYH) like '%123%'\n" +
//                ";";
        List<SQLStatement> statementList = SQLUtils
                .parseStatements(statement, "ORACLE");
        for (SQLStatement sqlStatement : statementList) {
            System.out.println(sqlStatement);
        }

    }


    /**
     * 简单查询情况
     */
    @Test
    public void parse1() {
        //List<String> className = getClassName("cn.gtmap.realestate.common.core.domain");
        //System.out.printf("className");
        Map map = Maps.newHashMap();
        map.put("xmids", Arrays.asList("1", "2"));
        map.put("qllxs", Arrays.asList("1", "2"));
        map.put("gzlslid", "12222121");
        repository.selectPaging("listYcslxxByPage", map, 0, 10);
    }

    /**
     * 日期查询
     */
    @Test
    public void dateQuery() {
        //List<String> className = getClassName("cn.gtmap.realestate.common.core.domain");
        //System.out.printf("className");
        bdcSlSfxmMapper.listNoSfxxYsjeData(new Date(),new Date());
    }

    /**
     * from中带子查询
     */
    @Test
    public void selectSubQuery() {
        //List<String> className = getClassName("cn.gtmap.realestate.common.core.domain");
        //System.out.printf("className");
        Map map = Maps.newHashMap();
        map.put("tjwd","tjwd");
        map.put("djbmdm", "djbmdm");
        map.put("kssj", "2022-01-22 09:11:02");
        map.put("tjwd", "userAndProcess");
        Pageable page = new PageRequest(1, 10);
        repository.selectPaging("listGroupBdcSlPjTj", map, page);
    }

    /**
     * where中带子查询
     */
    @Test
    public void whereSubQuery() {
        //List<String> className = getClassName("cn.gtmap.realestate.common.core.domain");
        //System.out.printf("className");
        bdcSlHsxxMapper.listBdcSlHsxxByGzlslid("123","234");
    }


    /**
     * where中带子查询
     */
    @Test
    public void entityInsert() {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setJbxxid("1234567891");
        bdcSlJbxxDO.setSlbh("123");
        bdcSlJbxxDO.setGzlslid("1234");
        bdcSlJbxxDO.setGzldyid("234");
        entityMapper.insert(bdcSlJbxxDO);
    }


    /**
     * where中带子查询
     */
    @Test
    public void entityUpdate() {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setGzlslid("1234567");
        Example example = new Example(BdcSlJbxxDO.class);
        example.createCriteria().andEqualTo("jbxxid","1234567891");
        entityMapper.updateByExampleSelective(bdcSlJbxxDO,example);
    }


    @Test
    public void select() {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setGzlslid("1234");
        Example example = new Example(BdcSlJbxxDO.class);
        example.createCriteria().andEqualTo("gzlslid","1234");
        List<Object> objects = entityMapper.selectByExample(example);
        System.out.printf(JSON.toJSONString(objects));
    }

    @Test
    @Rollback
    public void delete() {
        Example example = new Example(BdcSlJbxxDO.class);
        example.createCriteria()
                .andEqualTo("gzlslid","1234");
        entityMapper.deleteByExample(example);
    }
}
