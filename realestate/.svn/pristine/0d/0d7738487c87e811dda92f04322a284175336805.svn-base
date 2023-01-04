package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.vo.config.ui.BdcYzhVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/1/30
 * @description 不动产证书印制号配置
 */
public interface BdcXtZsyzhMapper {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhVO 证书印制号模板
     * @return {int} 印制号数量
     * @description 查询指定区间证书印制号数量
     */
    int countYzh(BdcYzhVO bdcYzhVO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param yzhid 印制号ID
     * @return {List} 受理编号信息
     * @description 查询印制号对应证书关联的项目受理编号
     */
    List<String> listBdcXmSlbhByZsyzh(@Param("yzhid") String yzhid);

    /**
     * 清除证书中已使用的证书印制号
     * @param zsid 证书id
     * @return 更新记录数量
     */
    int clearZsyzh(@Param("zsid") String zsid);
}
