package cn.gtmap.realestate.exchange.service.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthCancelDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthQueryDzzzxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthSfxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwsqxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.request.YthYjxxRequestDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2020-12-01
 * @description (盐城) 一体化相关服务处理
 */
public interface BdcYthService {

    /**
     * 盐城_一体化撤件请求
     *
     * @param ythCancelDTO 盐城_一体化撤件请求参数
     * @return CommonResponse 返回参数
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    CommonResponse ythCancel(YthCancelDTO ythCancelDTO);

    /**
     * @param ythSfxxDTO 一体化收费信息请求参数
     * @return CommonResponse 返回参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 盐城_接收一体化平台收费信息
     */
    CommonResponse ythJsSfxx(YthSfxxDTO ythSfxxDTO);

    /**
     * @param ythYwsqxxDTO 盐城_一体化业务信息请求参数
     * @return CommonResponse 返回参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 盐城_一体化平台申请创建业务接口
     */
    CommonResponse ythJsSqxxCj(YthYwsqxxDTO ythYwsqxxDTO);

    /**
     * 盐城一体化推送受理信息接口
     *
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    CommonResponse ythTsSlxx(String gzlslid);

    /**
     * 盐城一体化推送审核信息接口
     *
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    CommonResponse ythTsShxx(String gzlslid);

    /**
     * 盐城_一体化发证信息同步
     *
     * @param gzlslid 盐城_一体化发证信息同步请求参数
     * @return CommonResponse 返回参数
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    CommonResponse ythTsFzxx(String gzlslid);

    /**
     * 登记删除业务，通知一体化
     *
     * @param gzlslid gzlslid
     * @Date 2020/12/4
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    CommonResponse deleteTzYth(String gzlslid, String reason);

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [httpServletRequest]
     * @description 一体化 以人查房
     */
    JSONArray queryYthYrcf(HttpServletRequest httpServletRequest);

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 一体化 推送预测楼盘信息
     */
    CommonResponse ythTsyclp(String fwDcbIndex,String qjgldm);

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 一体化 查询电子证照信息
     */
    CommonResponse<JSONObject> ythQueyrDzzzxx(YthQueryDzzzxxDTO ythQueryDzzzxxDTO);


    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 一体化 接收邮寄信息
     */
    JSONObject ythYjxx(YthYjxxRequestDTO ythYjxxRequestDTO);
}
