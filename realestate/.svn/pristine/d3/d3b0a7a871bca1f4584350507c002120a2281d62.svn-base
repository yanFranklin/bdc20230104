package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpMxtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/07/11:22
 * @Description: 不动产大屏查询
 */
public interface BdcDpCxService {

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏今日登记类型数据接口
     */
    List<BdcDpCxTjDTO> listJrdjlx(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量数据接口
     */
    Object listFzslTj(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押融资数据接口
     */
    BdcDyrzTjDTO getTyrzTj(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取住宅统计数据接口
     */
    BdcDpCxZzTjDTO getZzTj(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取共享次数情况接口
     */
    Object getGxcsTj(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取上报情况接口
     */
    Object getSbqk(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取规则检查情况接口
     */
    Object getGzjcgk(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取本期得分情况接口
     */
    Object getBqdfqk(BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpMxtjQO 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型详细台账获取表格信息接口
     */
    Page<BdcDpDjlxtjMxDTO> getDjlxtjMx(Pageable pageable, BdcDpMxtjQO bdcDpMxtjQO);

    /**
     * @param bdcDpMxtjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型详细台账获取图表信息接口
     */
    List<BdcDpDjlxtjMxDTO> getDjlxtjTb(BdcDpMxtjQO bdcDpMxtjQO);
    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据”流程名称/登记小类“统计
     */
    Object getLctjByDjlx(BdcDpTjQO bdcDpTjQO);

    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @date 2022/7/5 11:37
    * @param bdcDpMxtjQO
    * @return 发证量统计
    * @description
    **/
    List<BdcDpFzltjMxDTO> getFzltjTb(BdcDpMxtjQO bdcDpMxtjQO);

    /**
     * @param bdcDpMxtjQO 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量详细台账获取表格信息接口
     */
    Page<BdcDpFzltjMxDTO> getFztjMx(Pageable pageable,BdcDpMxtjQO bdcDpMxtjQO);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/7/5 11:37
     * @param bdcDpTjQO
     * @return 抵押融资详细台账的图表统计接口
     * @description
     **/
    Object getDyjetjTb(BdcDpTjQO bdcDpTjQO);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/7/5 11:37
     * @param bdcDpMxtjQO
     * @return 抵押融资详细台账的表格统计接口
     * @description
     **/
    Page<BdcDpDyjeMxDTO> getDyjetjMx(Pageable pageable,BdcDpMxtjQO bdcDpMxtjQO);
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取省级接入质量评分接口
     */
    List<SjzlpfResponseDTO> getQualityScore(BdcDpTjQO bdcDpTjQO);

    /**
     * @return 大屏查询统计配置信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取大屏查询统计配置信息
     */
    Object getBdcDpConfig();
}
