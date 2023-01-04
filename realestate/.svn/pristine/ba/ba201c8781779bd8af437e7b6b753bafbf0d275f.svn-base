package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-25
 * @description
 */
public class BdcdySqlConstants {

    /**
     * 定着物特征码  W
     */
    public final static String ZD_DJDCB_SQL = "SELECT Z.BDCDYH,Z.TDZL AS ZL,Z.DJH,Z.ZD_DJDCB_INDEX AS QJID,'ZD' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,Z.tdyt YT,null QSXZ,null JZMJ FROM ZD_DJDCB Z ";
    public final static String ZD_DJDCB_QLR_SQL = " INNER JOIN(SELECT DJH FROM ZD_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY DJH) W ON Z.DJH=W.DJH";
    public final static String ZD_DJDCB_QLR_SQL_WITHFK = " INNER JOIN(SELECT DJDCB_INDEX FROM ZD_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY DJDCB_INDEX) W ON Z.ZD_DJDCB_INDEX=W.DJDCB_INDEX";

    public final static String QSZD_DJDCB_SQL = "SELECT Q.BDCDYH,Q.TDZL AS ZL,Q.DJH,Q.QSZD_DJDCB_INDEX AS QJID,'QSZD' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,Q.tdyt YT,null QSXZ,null JZMJ FROM QSZD_DJDCB Q ";
    public final static String QSZD_DJDCB_QLR_SQL = " INNER JOIN (SELECT DJH FROM QSZD_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY DJH) E ON Q.DJH=E.DJH";
    public final static String QSZD_DJDCB_QLR_SQL_WITHFK = " INNER JOIN (SELECT DJDCB_INDEX FROM QSZD_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY DJDCB_INDEX) E ON Q.QSZD_DJDCB_INDEX=E.DJDCB_INDEX";

    public final static String QSZD_LS_DJDCB_SQL = "SELECT Q.BDCDYH,Q.TDZL AS ZL,Q.DJH,Q.QSZD_DJDCB_INDEX AS QJID,'QSZD' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,Q.tdyt YT,null QSXZ,null JZMJ FROM H_QSZD_DJDCB Q ";

    public final static String NYD_DJDCB_SQL = "SELECT N.BDCDYH,N.TDZL AS ZL,N.DJH,N.NYD_DJDCB_INDEX AS QJID,'NYD' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,N.tdyt YT,N.QSXZ,null JZMJ FROM NYD_DJDCB N ";
    public final static String NYD_DJDCB_QLR_SQL = " INNER JOIN (SELECT DJH FROM NYD_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY DJH) H ON N.DJH=H.DJH";
    public final static String NYD_DJDCB_QLR_SQL_WITHFK = " INNER JOIN (SELECT DJDCB_INDEX FROM NYD_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY DJDCB_INDEX) E ON N.NYD_DJDCB_INDEX=E.DJDCB_INDEX";

    public final static String ZH_DJDCB_SQL = "SELECT Z.BDCDYH,Z.HDWZ AS ZL,Z.ZHDM DJH,Z.ZH_DJDCB_INDEX AS QJID," +
            "'ZH' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,Z.HDYT YT,null QSXZ,null JZMJ FROM ZH_DJDCB Z ";
    public final static String ZH_DJDCB_QLR_SQL = " INNER JOIN (SELECT ZHDM FROM ZH_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY ZHDM) E ON Z.ZHDM = E.ZHDM ";
    public final static String ZH_DJDCB_QLR_SQL_WITHFK = " INNER JOIN (SELECT DJDCB_INDEX FROM ZH_QLR WHERE QLRMC ${QLRMH_SQL} GROUP BY DJDCB_INDEX) E ON Z.ZH_DJDCB_INDEX = E.DJDCB_INDEX";

    public final static String JYQDK_DJDCB_SQL = "SELECT N.BDCDYH,N.ZL,substr(N.BDCDYH,0,19) AS DJH,N.JYQDKDCB_INDEX AS QJID,'JYQ' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,null AS YT,N.QSXZ, null JZMJ FROM JYQDK_DCB N ";
    public final static String JYQDK_DJDCB_QLR_SQL = " INNER JOIN (SELECT R.JYQDKDCB_INDEX FROM JYQDK_DCBQLR_REL R INNER JOIN JYQDK_QLR Q ON Q.JYQDKQLR_INDEX =R.JYQDKQLR_INDEX WHERE Q.QLRMC ${QLRMH_SQL} GROUP BY R.JYQDKDCB_INDEX) H ON N.JYQDKDCB_INDEX=H.JYQDKDCB_INDEX";

    public final static String ZD_LS_DJDCB_SQL = "SELECT Z.BDCDYH,Z.TDZL AS ZL,Z.DJH,Z.ZD_DJDCB_INDEX AS QJID,'ZD' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,Z.tdyt YT,null QSXZ,null JZMJ FROM H_ZD_DJDCB Z ";





    /**
     * 定着物特征码 L
     */
    public final static String LQ_DCB_SQL = "SELECT B.BDCDYH, B.ZL, B.DJH, B.LQ_DCB_INDEX AS QJID,'LQ' AS LX,'1' AS BDCZT,'' FJH,'' YSFWBM,A.tdyt YT,A.QSXZ QSXZ,null JZMJ " +
            " FROM NYD_DJDCB A " +
            " INNER JOIN LQ_DCB B ON A.DJH=B.DJH";
    public final static String LQ_DCB_QLR_SQL = " INNER JOIN " +
            "(SELECT DJH FROM NYD_QLR WHERE  SFLDSYQR = '1' AND QLRMC ${QLRMH_SQL} GROUP BY DJH) C " +
            " ON B.DJH = C.DJH";

    public final static String LQ_DCB_QLR_SQL_WITHFK = " INNER JOIN " +
            "(SELECT DJDCB_INDEX FROM NYD_QLR WHERE  SFLDSYQR = '1' AND QLRMC ${QLRMH_SQL} GROUP BY DJDCB_INDEX) C " +
            " ON A.NYD_DJDCB_INDEX = C.DJDCB_INDEX";
    public final static String LQ_DCB_CBZD_QLR_SQL = " INNER JOIN " +
            "(SELECT R.BDCDYH FROM CBZD_DCBCBF_REL R INNER JOIN CBZD_CBF Q ON Q.CBZD_CBF_INDEX =R.CBZD_CBF_INDEX WHERE Q.CBFMC ${QLRMH_SQL} GROUP BY R.BDCDYH) C " +
            " ON A.BDCDYH = C.BDCDYH";




    /**
     * 定着物特征码 F
     */
    public final static String FW_XMXX_SQL = "SELECT Q.BDCDYH, Q.ZL, substr(Q.BDCDYH,0,19) DJH,Q.FW_XMXX_INDEX AS QJID,'XMXX' AS LX, TO_CHAR(Q.BDCZT) BDCZT,'' FJH,'' YSFWBM ,null yt,null QSXZ,M.JZMJ FROM FW_XMXX Q LEFT JOIN (SELECT SUM(l.SCJZMJ) JZMJ, l.FW_XMXX_INDEX FROM FW_LJZ l group by l.FW_XMXX_INDEX) M on Q.FW_XMXX_INDEX=M.FW_XMXX_INDEX ";
    public final static String FW_XMXX_QLR_SQL =" INNER JOIN(SELECT FW_INDEX FROM FW_FCQLR WHERE QLR ${QLRMH_SQL} GROUP BY FW_INDEX) E ON Q.FW_XMXX_INDEX=E.FW_INDEX";

    public final static String FW_LJZ_SQL = "SELECT Q.BDCDYH,Q.ZLDZ AS ZL,substr(Q.BDCDYH,0,19) DJH, Q.FW_DCB_INDEX AS QJID,'LJZ' AS LX, TO_CHAR(Q.BDCZT) BDCZT,'' FJH,'' YSFWBM ,q.fwyt yt,null QSXZ,Q.SCJZMJ AS JZMJ FROM FW_LJZ Q ";
    public final static String FW_LJZ_QLR_SQL = " INNER JOIN (SELECT FW_INDEX FROM FW_FCQLR WHERE QLR ${QLRMH_SQL} GROUP BY FW_INDEX) E ON Q.FW_DCB_INDEX=E.FW_INDEX";

    public final static String FW_HS_SQL = "SELECT Q.BDCDYH,Q.ZL,substr(Q.BDCDYH,0,19) DJH, Q.FW_HS_INDEX AS QJID,'HS' AS LX, TO_CHAR(Q.BDCZT) BDCZT,TO_CHAR(Q.FJH) FJH,to_char(Q.YSFWBM) YSFWBM,Q.GHYT YT,null QSXZ,Q.SCJZMJ AS JZMJ FROM FW_HS Q ";
    public final static String FW_HS_QLR_SQL = "INNER JOIN (SELECT FW_INDEX FROM FW_FCQLR WHERE QLR ${QLRMH_SQL} GROUP BY FW_INDEX) E ON Q.FW_HS_INDEX=E.FW_INDEX";

    public final static String FW_YCHS_SQL = "SELECT Q.BDCDYH,Q.ZL,substr(Q.BDCDYH,0,19) DJH, Q.FW_HS_INDEX AS QJID,'YCHS' AS LX, TO_CHAR(Q.BDCZT) BDCZT,TO_CHAR(Q.FJH) FJH,TO_CHAR(Q.YSFWBM) YSFWBM,Q.GHYT YT,null QSXZ,Q.SCJZMJ AS JZMJ FROM FW_YCHS Q ";
    public final static String FW_YCHS_QLR_SQL = "INNER JOIN (SELECT FW_INDEX FROM FW_FCQLR WHERE QLR ${QLRMH_SQL} GROUP BY FW_INDEX) E ON Q.FW_HS_INDEX=E.FW_INDEX";



    public final static String FW_LS_XMXX_SQL = "SELECT Q.BDCDYH, Q.ZL, substr(Q.BDCDYH,0,19) DJH,Q.FW_XMXX_INDEX AS QJID,'XMXX' AS LX, TO_CHAR(Q.BDCZT) BDCZT,'' FJH,'' YSFWBM ,null yt,null QSXZ,0 as JZMJ  FROM H_FW_XMXX Q";


    public final static String FW_LS_LJZ_SQL = "SELECT Q.BDCDYH,Q.ZLDZ AS ZL,substr(Q.BDCDYH,0,19) DJH, Q.FW_DCB_INDEX AS QJID,'LJZ' AS LX, TO_CHAR(Q.BDCZT) BDCZT,'' FJH,'' YSFWBM ,q.fwyt yt,null QSXZ,Q.SCJZMJ AS JZMJ FROM H_FW_LJZ Q ";


    public final static String FW_LS_HS_SQL = "SELECT Q.BDCDYH,Q.ZL,substr(Q.BDCDYH,0,19) DJH, Q.FW_HS_INDEX AS QJID,'HS' AS LX, TO_CHAR(Q.BDCZT) BDCZT,TO_CHAR(Q.FJH) FJH,'' YSFWBM,Q.GHYT YT,null QSXZ,Q.SCJZMJ AS JZMJ FROM H_FW_HS Q ";





    /**
     * 定着物特征码 Q
     */
    public final static String DZW_DCB_SQL = "SELECT D.BDCDYH,D.ZL,substr(D.BDCDYH,0,19) DJH, D.DZW_DCB_INDEX AS QJID,'DZW' AS LX,TO_CHAR(D.BDCZT) BDCZT,'' FJH,'' YSFWBM,null yt,null QSXZ,null JZMJ FROM DZW_DCB D ";
    public final static String DZW_DCB_QLR_SQL = "INNER JOIN (SELECT DZW_DCB_INDEX FROM DZW_QLR WHERE QLR ${QLRMH_SQL} GROUP BY DZW_DCB_INDEX) E  ON D.DZW_DCB_INDEX = E.DZW_DCB_INDEX";

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  权利人模糊方式-精确
      */
    public final static String QLRMH_WHERE_JQ=" = #{qlr}";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  权利人模糊方式-左模糊
     */
    public final static String QLRMH_WHERE_ZMH=" LIKE '%'|| #{qlr}";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  权利人模糊方式-右模糊
     */
    public final static String QLRMH_WHERE_YMH=" LIKE #{qlr} || '%'";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  权利人模糊方式-全模糊
     */
    public final static String QLRMH_WHERE_QMH=" LIKE '%'|| #{qlr} || '%'";

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 权利人模糊方式-精确
      */
    public final static String QLRMH_SQL="${QLRMH_SQL}";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description LQ_DCB 无数据
     */
    public final static String NO_LQ_DCB="  LEFT JOIN LQ_DCB L ON L.BDCDYH = N.BDCDYH WHERE L.LQ_DCB_INDEX IS NULL";



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return boolean
     * @description 判断分页查询地籍信息，权利人关联关系是否用外键
     */
    public static boolean relationDjxxQlrWithFk(){
       String fk = EnvironmentConfig.getEnvironment().getProperty("zdqlr.relation.fk","false");
       return BooleanUtils.toBoolean(fk);
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理qlr sql
     */
    public static String dealWithQlrSql(String withQlrSql,String mhlx){
        if(StringUtils.equals("1",mhlx)){
            withQlrSql =withQlrSql.replace(QLRMH_SQL,QLRMH_WHERE_JQ);
        }else if(StringUtils.equals("2",mhlx)){
            withQlrSql =withQlrSql.replace(QLRMH_SQL,QLRMH_WHERE_ZMH);
        }else if(StringUtils.equals("3",mhlx)){
            withQlrSql =withQlrSql.replace(QLRMH_SQL,QLRMH_WHERE_YMH);
        }else{
            withQlrSql =withQlrSql.replace(QLRMH_SQL,QLRMH_WHERE_QMH);
        }
        return withQlrSql;


    }

}
