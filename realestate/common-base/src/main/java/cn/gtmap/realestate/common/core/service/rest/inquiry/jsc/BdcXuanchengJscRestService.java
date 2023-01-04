package cn.gtmap.realestate.common.core.service.rest.inquiry.jsc;

import cn.gtmap.realestate.common.core.dto.inquiry.jsc.*;
import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 宣城图表接口
 */
public interface BdcXuanchengJscRestService {
    /**
     * 林权登记总面积
     *
     * @param jscCommomQO
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/zmj")
    public List<Integer> queryJscDjZmj(@RequestBody JscCommomQO jscCommomQO);

    /**
     * 登记数量图表汇总
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryDjsl")
    public Map<String, List<JscDjslDTO>> queryJscDjslList(@RequestBody JscCommomQO jscCommomQO);


    /**
     * 驾驶舱 概况
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/querySummary")
    public List<Integer> queryJscSummary(@RequestBody JscCommomQO jscCommomQO);

    /**
     * 驾驶舱 林权权利
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryQl")
    public Map<String, List<JscLqqlDTO>> queryJscQl(@RequestBody JscCommomQO jscCommomQO);

    /**
     * 驾驶舱 登记趋势
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryTrend")
    public JscLqqsDTO queryJscTrend(@RequestBody JscCommomQO jscCommomQO);

    /**
     * 驾驶舱 交易情况（转移登记）
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryTransaction")
    public List<JscLqjyDTO> queryJscTransaction(@RequestBody JscCommomQO jscCommomQO);


    /**
     * 登记数量图表汇总
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryDjslMap")
    public Map<String, JscLqjyDTO> queryJscDjslMap(@RequestBody JscCommomQO jscCommomQO);

    /**
     * 历史遗留问题柱图
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryLsylwt")
    public  Map<String,List<JscLsylwtDTO>> queryLsylwt(@RequestBody JscCommomQO jscCommomQO);


    /**
     * 未解决问题概况区县占比图
     *
     * @param info
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryLsylwtWjjQxzb")
    public List<JscLsylwtDTO> queryLsylwtWjjQxzb(@RequestBody JscCommomQO jscCommomQO);

    /**
     * 区县中未解决问题柱图（要按照问题类型进行补充）
     *
     * @param jscCommomQO@return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bjsctj/queryLsylwtQxWtlxzb")
    public List<JscLsylwtDTO> queryLsylwtQxWtlxzb(JscCommomQO jscCommomQO);
}
