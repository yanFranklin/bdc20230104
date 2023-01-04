package cn.gtmap.realestate.exchange.service.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.shiji.BdcShijiZzxzDTO;
import cn.gtmap.realestate.exchange.core.vo.BdcShijiStatisticsVO;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.http.HttpServletResponse;

/**
 * 市级接口相关处理服务
 */
public interface BdcShijiService {

    /**
     * 市级接口日志记录台账
     *
     * @param request
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Object listQueryLog(BdcShijiStatisticsVO request, int pageNumber, int pageSize);

    /**
     * 导出日志excel统计表格
     *
     * @param request
     * @param response
     * @return
     */
    Object exportStaticLog(BdcShijiStatisticsVO request, HttpServletResponse response);

    /**
     * 处理证照下载返回
     *
     * @param zzxzResponse
     * @return
     */
    CommonResponse<JSONArray> dealWithZzxzResponse(BdcShijiZzxzDTO zzxzResponse);

    /**
     * 同步市级接口Es日志至数据库
     */
    void syncShijiEsLogToDB(String kssj);

    /**
     * 添加受理材料信息
     *
     * @param gzlslid  工作流实例ID
     * @param foldName 文件夹名称
     * @param wjzxId   文件中心ID
     */
    void addSjclxx(String gzlslid, String foldName, String wjzxId);

    /**
     * @param fjmc  附件名称
     * @param fjurl 附件URL
     * @param wjjid 文件夹ID
     * @description 根据附件URL地址下载并上传附件
     */
    void downAndUploadFjFromUrl(String fjurl, String fjmc, String wjjid);

    /**
     * @param fjmc  附件名称
     * @param fjurl 附件URL
     * @param wjjid 文件夹ID
     * @description 根据附件URL地址下载并上传附件-抛异常
     */
    void downAndUploadFjFromUrlThrowException(String fjurl, String fjmc, String wjjid);

}
