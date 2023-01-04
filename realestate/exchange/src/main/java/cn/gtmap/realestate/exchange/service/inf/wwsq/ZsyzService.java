package cn.gtmap.realestate.exchange.service.inf.wwsq;

import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.request.ZsyzRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzResponseData;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-27
 * @description 证书验证相关服务
 */
public interface ZsyzService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zsid
     * @return cn.gtmap.realestate.common.core.domain.BdcXmDO
     * @description 根据证书ID查询证书验证响应实体列表
     */
    List<ZsyzResponseData> queryZsyzListByZsid(String zsid);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cqzh
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzResponseData>
     * @description 根据产权证号查询
     */
    List<ZsyzResponseData> queryZsyzListByCqzh(String cqzh,String cqzhmh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param body
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzResponseData>
     * @description  根据证书ID 或者产权证号 查询 证书 （存在一证多房场景）
     */
    List<ZsyzResponseData> queryZs(ZsyzRequestBody body);

    /**
      *
      * @param body 查询参数
      * @return
      * @Date 2021/7/29
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    List<ZsyzResponseData> queryZsyzListByParam(ZsyzRequestBody body) ;
}
