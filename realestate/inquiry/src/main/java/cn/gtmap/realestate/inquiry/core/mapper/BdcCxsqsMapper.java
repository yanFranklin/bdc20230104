package cn.gtmap.realestate.inquiry.core.mapper;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/27
 * @description 不动产查询申请书
 */
public interface BdcCxsqsMapper {
    /**
     * @param dateStr 受理编号日期部分
     * @return int  查询到的结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前有多少查询受理编号
     */
    int countCxsldjbh(String dateStr);
}
