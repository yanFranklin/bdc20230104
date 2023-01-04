package cn.gtmap.realestate.etl.core.service;


import cn.gtmap.realestate.etl.core.domian.wwsq.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/06/11,1.0
 * @description 互联网业务信息查询数据层级方法
 */
public interface HlwYwxxDataService {

    /**
     * 根据互联网项目id查询互联网项目
     *
     * @param hlwxmid
     * @return
     */
    GxWwSqxmDo gxWwsqxmByHlwXmid(String hlwxmid);

    /**
     * 根据互联网项目id查询所有申请信息，单个，组合，批量
     *
     * @param xmid
     * @return
     */
    List<GxWwSqxxDo> listGxWwSqxxByXmid(String xmid);

    /**
     * 根据互联网申请信息id查询权利人信息
     *
     * @param sqxxid
     * @return
     */
    List<GxWwSqxxQlrDo> listGxWwSqxxQlrBySqxxid(String sqxxid);

    /**
     * 根据互联网申请信息id查询材料信息
     *
     * @param sqxxId
     * @return
     */
    List<GxWwSqxxClxxDo> listGxWwSqxxClxxBySqxxid(String sqxxId);

    /**
     * 根据互联网材料id查询附件信息
     *
     * @param clxxid
     * @return
     */
    List<GxWwSqxxFjxxDo> listGxWwSqxxFjxxByClid(String clxxid);

    /**
     * 根据互联网申请信息id查询税务信息
     *
     * @param sqxxid
     * @return
     */
    List<GxWwSwxxDo> listGxWwSwxxBySqxxid(String sqxxid);

    /**
     * 根据互联网税务信息id查询明细信息
     *
     * @param swid
     * @return
     */
    List<GxWwSwmxDo> listGxWwSwmxBySwid(String swid);

}
