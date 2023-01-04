package cn.gtmap.realestate.exchange.service.inf.wwsq;

import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-26
 * @description 外网申请 个人档案查询服务
 */
public interface GrdacxService {


    /**
     * @param requestBody
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据权利人证件号 查询档案信息
     */
    List<GrdacxSqxx> getSqxx(GrdacxRequestBody requestBody);

    /**
     * @param xmid
     * @param cqzh
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID获取申请信息
     */
    GrdacxSqxx getSqxxByXmid(String xmid, String cqzh);


    /**
     * @param requestBody
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 个人档案查询，查询房地产权和建设用地使用权（土地证） ，该接口不查预告信息
     */
    List<GrdacxSqxx> getSqxxWithoutYg(GrdacxRequestBody requestBody);
}
