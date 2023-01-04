package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.natural.core.mapper.ZrzyLshMapper;
import cn.gtmap.realestate.natural.core.service.ZrzySlbhService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/1
 * @description 自然资源受理编号服务
 */
@Service
public class ZrzySlbhServiceImpl implements ZrzySlbhService {

    @Autowired
    private ZrzyLshMapper zrzyLshMapper;

    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;

    @Override
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_ZRZYXMBH, description = "获取自然资源项目编号", waitTime = 60L, leaseTime = 30L)
    public String generateSlbh(String ywlx, String zzsjfw,Integer zzxlh,String prebh){
        String nyr = "";
        Integer lsh = queryLsh(ywlx, zzsjfw);
        if (StringUtils.equals(CommonConstantUtils.ZZSJFW_YEAR,zzsjfw)) {
            nyr = DateUtils.getCurrYearYear();
        } else if (StringUtils.equals(CommonConstantUtils.ZZSJFW_MONTH,zzsjfw)) {
            nyr = DateUtils.getCurrYearMonth();
        } else if (StringUtils.equals(CommonConstantUtils.ZZSJFW_DAY,zzsjfw)) {
            nyr = DateUtils.getCurrDay();
        }
        /// 长度不足，补零拼接
        String bhlsh = String.valueOf(lsh);
        if(bhlsh.length() < zzxlh){
            do{
                bhlsh = StringUtils.join("0", bhlsh);
            }while (bhlsh.length() < zzxlh);
        }

        return prebh +nyr + bhlsh;

    }

    /**
     * @param ywlx   业务类型
     * @param zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据自增时间范围、自增业务类型  生成流水号
     */
    private Integer queryLsh(String ywlx, String zzsjfw){
        final Map param = new HashMap();
        final Date beginDate = new Date();
        if (StringUtils.equals(zzsjfw, CommonConstantUtils.ZZSJFW_MONTH)) {
            final Date endDate = org.apache.commons.lang3.time.DateUtils.addMonths(beginDate, 1);
            param.put("beginDate", FastDateFormat.getInstance("yyyyMM").format(beginDate) + "01");
            param.put("endDate", FastDateFormat.getInstance("yyyyMM").format(endDate) + "01");
        } else if (StringUtils.equals(zzsjfw, CommonConstantUtils.ZZSJFW_YEAR)) {
            final Date endDate = org.apache.commons.lang3.time.DateUtils.addYears(beginDate, 1);
            param.put("beginDate", FastDateFormat.getInstance("yyyy").format(beginDate) + "0101");
            param.put("endDate", FastDateFormat.getInstance("yyyy").format(endDate) + "0101");
        } else if (StringUtils.equals(zzsjfw, "ALL")) {
            /*
             * ALL 不考虑自增时间范围
             */
            param.put("beginDate", "");
            param.put("endDate", "");
        } else {
            final Date endDate = org.apache.commons.lang3.time.DateUtils.addDays(beginDate, 1);
            param.put("beginDate", FastDateFormat.getInstance("yyyy-MM-dd").format(beginDate));
            param.put("endDate", FastDateFormat.getInstance("yyyy-MM-dd").format(endDate));
        }
        if (StringUtils.isNotBlank(ywlx)) {
            param.put("ywlx", ywlx);
        }

        Integer maxlsh = zrzyLshMapper.getMaxLsh(param);
        final Integer lsh = (maxlsh == null ? 1 : (maxlsh + 1));
        //手动提交事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        try {
            insertZrzyLsh(lsh, ywlx);
            //提交事务
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        return lsh;
    }

    /**
      * @param lsh 最大流水号
      * @param ywlx 业务类型
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 插入流水号
      */
    private void insertZrzyLsh(Integer lsh, String ywlx) {
        final Map lshVo = new HashMap();
        lshVo.put("lshid", UUIDGenerator.generate16());
        lshVo.put("lsh", lsh);
        lshVo.put("ywlx", ywlx);
        lshVo.put("cjsj", new Date());
        zrzyLshMapper.insertLsh(lshVo);
    }
}
