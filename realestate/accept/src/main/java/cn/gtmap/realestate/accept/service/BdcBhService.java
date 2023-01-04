package cn.gtmap.realestate.accept.service;

import java.util.Date;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/6
 * @description 不动产编号服务
 */
public interface BdcBhService {


    /**
     * @param ywlx   业务类型
     * @param zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据自增时间范围、自增业务类型  生成编号
     */
    String queryBh(String ywlx, String zzsjfw);

    /**
     * @param ywlx 业务类型
     * @param zzsjfw 自增时间范围
     * @param zzxlh 自增序列号
     * @param prebh 编号前缀
     * @return 编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据自增时间范围、自增业务类型生成编号通用方法
     */
    String queryCommonBh(String ywlx, String zzsjfw,Integer zzxlh,String prebh);

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取编号特征码
     */
    String queryTzmByBdcdyh(String bdcdyh);

    /**
     * @param ywlx 业务类型
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    String queryBhBySlsj(String ywlx, String zzsjfw, Date slsj);



}
