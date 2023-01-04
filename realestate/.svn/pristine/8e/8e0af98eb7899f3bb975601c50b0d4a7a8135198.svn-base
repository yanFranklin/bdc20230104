package cn.gtmap.realestate.exchange.service.impl.inf.shucheng;

import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.shucheng.fgj.ShuchengFgjYsfDjxxDTO;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.BdcFwQsxxServiceImpl;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleEvalVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleParameterizedOutputVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShuchengFgjServiceImplTest {
    @Autowired
    ShuchengFgjServiceImpl shuchengFgjService;

    @Test
    public void shuchengYsfDjsj() {
        ShuchengFgjYsfDjxxDTO clfHyxxDTO = new ShuchengFgjYsfDjxxDTO();
        clfHyxxDTO.setFwbm("6387423");
        clfHyxxDTO.setFwbm("1434522");
        clfHyxxDTO.setFwbm("1667882");
        clfHyxxDTO.setFwbm("1667882");
        ExchangeDsfCommonResponse exchangeDsfCommonResponse = shuchengFgjService.shuchengYsfDjsj(clfHyxxDTO);
        log.info("{}", JSON.toJSONString(exchangeDsfCommonResponse));
    }

    @Test
    public void shuchengYsfDjsj1() {
        String sql = "select GZLSLID, SLBH, LCMC, SLR, SLSJ, CZZT, YWMC from (\n" +
                "select GZLSLID,SLBH, LCMC, SLR, SLSJ, CZZT, '数据推送' as YWMC,CZSJ\n" +
                "from BDC_CZRZ\n" +
                "where CZLX = '5'\n" +
                "\n" +
                "UNION all\n" +
                "select distinct GZLSLID,BX.SLBH, BX.GZLDYMC as LCMC, SLR, SLSJ, -1 as CZZT, '查封' as YWMC, null as CZSJ\n" +
                "from BDC_CF\n" +
                "         INNER JOIN BDC_XM BX on BDC_CF.BDCDYH = BX.BDCDYH\n" +
                "where BX.AJZT = 2\n" +
                "  and BX.QSZT = 1\n" +
                "  and BX.GZLSLID is not null\n" +
                "  and BDC_CF.JFYWH is null\n" +
                "  and BDC_CF.SLBH not in (\n" +
                "    select B.SLBH\n" +
                "    from BDC_CZRZ\n" +
                "             INNER JOIN BDC_XM B on BDC_CZRZ.GZLSLID = B.GZLSLID\n" +
                "    where CZLX = '5'\n" +
                ")\n" +
                "\n" +
                "UNION all\n" +
                "\n" +
                "select distinct JFXM.GZLSLID, JFXM.SLBH, JFXM.GZLDYMC as LCMC, JFXM.SLR, JFXM.SLSJ, -1 as CZZT, '解封' as YWMC, null as CZSJ\n" +
                "from BDC_CF\n" +
                "         INNER JOIN BDC_XM BX on BDC_CF.BDCDYH = BX.BDCDYH\n" +
                "         INNER JOIN BDC_XM JFXM on BDC_CF.JFYWH = JFXM.SLBH\n" +
                "where BX.AJZT = 2\n" +
                "  and BX.QSZT = 1\n" +
                "  and BX.GZLSLID is not null\n" +
                "  and BDC_CF.JFYWH is not null\n" +
                "  and BDC_CF.SLBH not in (\n" +
                "    select B.SLBH\n" +
                "    from BDC_CZRZ\n" +
                "             INNER JOIN BDC_XM B on BDC_CZRZ.GZLSLID = B.GZLSLID\n" +
                "    where CZLX = '5'\n" +
                ")\n" +
                "              ) t\n" +
                "where 1 = 1;";
        List<Map> columns = new ArrayList<>();
        List<Map> alias = new ArrayList<>();

        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, "ORACLE");
        SQLStatement statement = statementList.get(0);
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) ((SQLSelect) ((SQLSelectStatement) statement)
                .getSelect()).getQuery();
        //最终的返回值
        List<SQLSelectItem> finalSelectList = query.getSelectList();

        //递归查找最终返回值得原始来源
        SQLTableSource finalFrom = query.getFrom();


        //直接是表
        if(finalFrom instanceof SQLExprTableSource){

        }
        //join表
        if(finalFrom instanceof SQLJoinTableSource){

        }
        //子查询
        if(finalFrom instanceof SQLSubqueryTableSource){

        }
        //with
        if(finalFrom instanceof SQLWithSubqueryClause.Entry){

        }


    }

    //查找一个字段最原始的来源是哪张表
    public void findColumnTable(List<Map> columns,List<Map> finalColumns,SQLSelectQueryBlock query){

    }

    @Test
    public void shuchengYsfDjsj2() {
        String sql = "select * from bdc_xm " +
                "where 1 = 1;";
        List<Map> columns = new ArrayList<>();
        List<Map> alias = new ArrayList<>();

        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, "ORACLE");
        SQLStatement statement = statementList.get(0);
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) ((SQLSelect) ((SQLSelectStatement) statement)
                .getSelect()).getQuery();
        //最终的返回值
        List<SQLSelectItem> finalSelectList = query.getSelectList();
        //查找最终返回值得原始来源


    }
}