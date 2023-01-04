package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpCxTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpDjlxtjMxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpDyjeMxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpFzltjMxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpMxtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/07/11:19
 * @Description: 不动产大屏查询
 */
@Repository
public interface BdcDpCxMapper {

    /**
     * 当日登记类型统计
     *
     * @param bdcDpTjQO
     * @return java.util.List<java.util.Map>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpCxTjDTO> listJrdjlx(BdcDpTjQO bdcDpTjQO);

    /**
     * 发证量统计
     *
     * @return java.util.List<java.util.Map>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpCxTjDTO> listFzslTj(BdcDpTjQO bdcDpTjQO);

    /**
     * 抵押总金额统计，dybdclx =1，土地抵押总金额；dybdclx =2，住宅抵押总金额
     *
     * @param bdcDpTjQO
     * @return java.util.List<java.util.Map>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpCxTjDTO> listDyzje(BdcDpTjQO bdcDpTjQO);

    /**
     * 按月份统计住宅抵押量、抵押金额
     *
     * @param bdcDpTjQO
     * @return java.util.List<java.util.Map>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<Map> listDyjeByMonth(BdcDpTjQO bdcDpTjQO);

    /**
     * 住宅统计
     *
     * @param bdcDpTjQO
     * @return java.util.List<java.util.Map>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpCxTjDTO> listZzTj(BdcDpTjQO bdcDpTjQO);

    List<Map> countBdcDsfLogByGxbmbz(BdcDpTjQO bdcDpTjQO);

    Map getJrsbjl(BdcDpTjQO bdcDpTjQO);

    BdcDpCxTjDTO getZmgzjcsl(BdcDpTjQO bdcDpTjQO);

    BdcDpCxTjDTO getJcxmsl(BdcDpTjQO bdcDpTjQO);
    /**
     * 按照 “流程名称/登记小类”统计
     *
     * @param bdcDpTjQO
     * @return List<BdcDpCxTjDTO>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpCxTjDTO> getLctjByDjlx(BdcDpTjQO bdcDpTjQO);

    /**
     * 今日登记类型
     * @param bdcDpMxtjQO 不动产大屏明细统计QO
     * @return java.util.List<BdcDpDjlxtjMxDTO>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpDjlxtjMxDTO> listDjxl(BdcDpMxtjQO bdcDpMxtjQO);
    /**
     * 今日登记小类
     *
     * @param bdcDpMxtjQO 不动产大屏明细统计QO
     * @return java.util.List<BdcDpDjlxtjMxDTO>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpDjlxtjMxDTO> listDjlx(BdcDpMxtjQO bdcDpMxtjQO);

    /**
     * 证书统计图表
     * @param bdcDpMxtjQO 不动产大屏明细统计QO
     * @return java.util.List<BdcDpFzltjMxDTO>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpFzltjMxDTO> getFzltjTb(BdcDpMxtjQO bdcDpMxtjQO);

    /**
     * 今日登记类型统计明细台账，按照项目来源统计
     * @param bdcDpMxtjQO 不动产大屏明细统计QO
     * @return java.util.List<BdcDpDjlxtjMxDTO>
     * @date 2022/04/08
     * @author sunyuzhaung
     */
    List<BdcDpDjlxtjMxDTO> listDjxlXmly(BdcDpMxtjQO bdcDpMxtjQO);
}
