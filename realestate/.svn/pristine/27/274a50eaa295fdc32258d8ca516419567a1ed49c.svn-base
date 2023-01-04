package cn.gtmap.realestate.inquiry.service.jsc;

import cn.gtmap.realestate.common.core.dto.inquiry.jsc.*;
import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface BdcXuanchengJscService {

    /**
     *
     * @param jscCommomQO
     * @return
     */
    public List<Integer> queryJscDjZmj(JscCommomQO jscCommomQO);

    /**
     * 登记数量图表汇总
     *
     * @param info
     * @return
     */
    public Map<String, List<JscDjslDTO>> queryJscDjslList(JscCommomQO jscCommomQO);


    /**
     * 驾驶舱 概况
     *
     * @param info
     * @return
     */
    public List<Integer> queryJscSummary(JscCommomQO jscCommomQO);

    /**
     * 驾驶舱 林权权利
     *
     * @param info
     * @return
     */
    public Map<String, List<JscLqqlDTO>> queryJscQl(JscCommomQO jscCommomQO);

    /**
     * 驾驶舱 登记趋势
     *
     * @param info
     * @return
     */
    public JscLqqsDTO queryJscTrend(JscCommomQO jscCommomQO);

    /**
     * 驾驶舱 交易情况（转移登记）
     *
     * @param info
     * @return
     */
    public List<JscLqjyDTO> queryJscTransaction(JscCommomQO jscCommomQO);

    /**
     * 登记数量图表汇总
     *
     * @param info
     * @return
     */
    public Map<String, JscLqjyDTO> queryJscDjslMap(JscCommomQO jscCommomQO);


    /**
     * 历史遗留问题柱图
     *
     * @param info
     * @return
     */
    public Map<String,List<JscLsylwtDTO>> queryLsytwt(JscCommomQO jscCommomQO);



    /**
     * 未解决问题概况区县占比图
     *
     * @param info
     * @return
     */
    public List<JscLsylwtDTO> queryLsylwtWjjQxzb(JscCommomQO jscCommomQO);


    /**
     * 区县中未解决问题柱图（要按照问题类型进行补充）
     *
     * @param info
     * @return
     */
    public List<JscLsylwtDTO> queryLsylwtQxWtlxzb(JscCommomQO jscCommomQO);
}
