package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcLshMapper;
import cn.gtmap.realestate.accept.service.BdcLshService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
 * @version 1.0, 2019/10/9
 * @description 流水号服务
 */
@Service
public class BdcLshServiceImpl implements BdcLshService {
    private static final FastDateFormat MONTH_DATE_FORMAT = FastDateFormat.getInstance("yyyyMM");
    private static final FastDateFormat YEAR_DATE_FORMAT = FastDateFormat.getInstance("yyyy");
    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产流水号数据mapper
     */
    @Autowired
    BdcLshMapper bdcLshMapper;

    /**
     * @param ywlx   业务类型
     * @param zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据自增时间范围、自增业务类型  生成流水号
     * 加锁，60s内尝试获取锁，加锁后30s内若未手动释放锁则自动释放锁
     */
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_BDCXMBH, description = "获取项目编号", waitTime = 60L, leaseTime = 30L)
    @Override
    public Integer queryLsh(String ywlx, String zzsjfw) {
        final Map param = new HashMap();
        final Date beginDate = new Date();
        if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_MONTH)) {
            final Date endDate = DateUtils.addMonths(beginDate, 1);
            param.put("beginDate", MONTH_DATE_FORMAT.format(beginDate) + "01");
            param.put("endDate", MONTH_DATE_FORMAT.format(endDate) + "01");
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_YEAR)) {
            final Date endDate = DateUtils.addYears(beginDate, 1);
            param.put("beginDate", YEAR_DATE_FORMAT.format(beginDate) + "0101");
            param.put("endDate", YEAR_DATE_FORMAT.format(endDate) + "0101");
        } else if (StringUtils.equals(zzsjfw, "ALL")) {
            /*
             * ALL 不考虑自增时间范围
             */
            param.put("beginDate", "");
            param.put("endDate", "");
        } else {
            final Date endDate = DateUtils.addDays(beginDate, 1);
            param.put("beginDate", DATE_FORMAT.format(beginDate));
            param.put("endDate", DATE_FORMAT.format(endDate));
        }
        if (StringUtils.isNotBlank(ywlx)) {
            param.put("ywlx", ywlx);
        }

        Integer maxlsh = bdcLshMapper.getMaxLsh(param);
        final Integer lsh = (maxlsh == null ? 1 : (maxlsh + 1));
        //手动提交事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        try {
            insertLsh(lsh, ywlx);
            //提交事务
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }



        return lsh;
    }

    /**
     * @param ywlx   业务类型
     * @param zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据自增时间范围、自增业务类型  生成流水号
     * 加锁，60s内尝试获取锁，加锁后30s内若未手动释放锁则自动释放锁
     */
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_BDCXMBH, description = "获取项目编号", waitTime = 60L, leaseTime = 30L)
    @Override
    public Integer queryLshBySlsj(String ywlx, String zzsjfw, Date slsj) {
        final Map param = new HashMap();
        if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_MONTH)) {
            final Date endDate = DateUtils.addMonths(slsj, 1);
            param.put("beginDate", MONTH_DATE_FORMAT.format(slsj) + "01");
            param.put("endDate", MONTH_DATE_FORMAT.format(endDate) + "01");
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_YEAR)) {
            final Date endDate = DateUtils.addYears(slsj, 1);
            param.put("beginDate", YEAR_DATE_FORMAT.format(slsj) + "0101");
            param.put("endDate", YEAR_DATE_FORMAT.format(endDate) + "0101");
        } else if (StringUtils.equals(zzsjfw, "ALL")) {
            /*
             * ALL 不考虑自增时间范围
             */
            param.put("beginDate", "");
            param.put("endDate", "");
        } else {
            final Date endDate = DateUtils.addDays(slsj, 1);
            param.put("beginDate", DATE_FORMAT.format(slsj));
            param.put("endDate", DATE_FORMAT.format(endDate));
        }
        if (StringUtils.isNotBlank(ywlx)) {
            param.put("ywlx", ywlx);
        }

        Integer maxlsh = bdcLshMapper.getMaxLsh(param);
        final Integer lsh = (maxlsh == null ? 1 : (maxlsh + 1));
        //手动提交事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        try {
            insertLshBySlsj(lsh, ywlx,slsj);
            //提交事务
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }



        return lsh;
    }



    /**
     * @param lsh  流水号
     * @param ywlx 业务类型
     * @return lsh 最大流水号
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 插入流水号
     */
    private void insertLsh(Integer lsh, String ywlx) {
        final Map lshVo = new HashMap();
        lshVo.put("lshid", UUIDGenerator.generate16());
        lshVo.put("lsh", lsh);
        lshVo.put("ywlx", ywlx);
        lshVo.put("cjsj", new Date());
        bdcLshMapper.insertLsh(lshVo);
    }

    /**
     * @param lsh  流水号
     * @param ywlx 业务类型
     * @return lsh 最大流水号
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 插入流水号
     */
    private void insertLshBySlsj(Integer lsh, String ywlx,Date slsj) {
        final Map lshVo = new HashMap();
        lshVo.put("lshid", UUIDGenerator.generate16());
        lshVo.put("lsh", lsh);
        lshVo.put("ywlx", ywlx);
        lshVo.put("cjsj", slsj);
        bdcLshMapper.insertLsh(lshVo);
    }
}
