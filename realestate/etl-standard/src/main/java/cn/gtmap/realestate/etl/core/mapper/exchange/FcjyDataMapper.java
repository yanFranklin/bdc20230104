package cn.gtmap.realestate.etl.core.mapper.exchange;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/6
 * @description f
 */
@Component
public interface FcjyDataMapper {

    /**
      * @param htbh 合同编号
      * @return 存量房合同信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据合同编号查询存量房合同信息
      */
     List<Map> listClfHtByHtbh(String htbh);

    /**
     * @param htbh 合同编号
     * @return 商品房合同信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据合同编号查询商品房合同信息
     */
    List<Map> listSpfHtByHtbh(String htbh);
}
