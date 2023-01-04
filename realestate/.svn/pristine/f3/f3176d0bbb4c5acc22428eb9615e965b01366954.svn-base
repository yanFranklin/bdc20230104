package cn.gtmap.realestate.accept.service;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/10/9
 * @description 流水号服务
 */
public interface BdcLshService {

    /**
     * @param ywlx   业务类型
     * @param zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据自增时间范围、自增业务类型  生成流水号
     */
    Integer queryLsh(String ywlx, String zzsjfw);

    /**
     * @param ywlx 业务类型
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据自增时间范围、自增业务类型 以及受理时间 生成流水号(处理重复受理编号)
     */
    Integer queryLshBySlsj(String ywlx, String zzsjfw, Date slsj);
}
