package cn.gtmap.realestate.exchange.service.impl.inf.sjpt.thread;

import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqqXm;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxsq.request.SjptCxsqCtrlRequestDTO;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.CxjgService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.thread.GxPeCxqqServiceThread;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GxPeCxqqServiceThreadImpl implements GxPeCxqqServiceThread {

    private static Logger LOGGER = LoggerFactory.getLogger(GxPeCxqqServiceThread.class);

    @Autowired
    private EntityMapper sjptEntityMapper;
    @Autowired
    AccessLogTypeService accessLogTypeService;

    @Autowired
    private CxjgService cxjgService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    // 查询结果提交的 服务ID
    private static final String CXJG_BEANNAME = "sjpt_commitCxjgWithLog";


    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param gxPeCxqqList 申请集合
     * @param sfQuartz 是否是定时任务触发的
     * @return
     * @description 批量执行查询
     */
    @Override
    public void batchExexuteQueryCxqq(List<GxPeCxqq> gxPeCxqqList, boolean sfQuartz) throws Exception {

//        LOGGER.info("线程开始处理：");
//        LOGGER.info("参数：{}", JSON.toJSONString(gxPeCxqqList));
//        LOGGER.info("参数：{}", sfQuartz);
        if (CollectionUtils.isNotEmpty(gxPeCxqqList)) {
            for (GxPeCxqq gxPeCxqq : gxPeCxqqList) {
                try {
                    //查询
                    Example example = new Example(GxPeCxqqXm.class);
                    example.createCriteria().andEqualTo("cxsqdh", gxPeCxqq.getCxsqdh());
                    List<GxPeCxqqXm> gxPeCxqqXmList = sjptEntityMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(gxPeCxqqXmList)) {
                        for (GxPeCxqqXm gxPeCxqqXm : gxPeCxqqXmList) {
                            /**
                             * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
                             * @description 定时任务只有未查询的才会执行；手点查询的都忽略直接重新查
                             */
                            if (!sfQuartz || !StringUtils.equals(gxPeCxqqXm.getCxzt(), SjptConstants.CXQQXM_ZT_YCL)) {
                                try {
                                    cxjgService.queryAndSaveApply(gxPeCxqqXm);
                                } catch (Exception e) {
                                    LOGGER.error("errorMsg:", e);
                                    if (!sfQuartz) {
                                        throw e;
                                    }
                                }
                            }
                        }
                        sjptEntityMapper.batchSaveSelective(gxPeCxqqXmList);
                    }
                    sjptEntityMapper.updateByPrimaryKeySelective(cxjgService.flushCxqqZt(gxPeCxqq));
                } catch (Exception e) {
                    LOGGER.error("errorMsg:", e);
                    // 只有一条说明是手动执行
                    if (gxPeCxqqList.size() == 1) {
                        throw e;
                    }
                }
            }
        }
    }

    @Override
    public void batchCommitCxsq(List<GxPeCxqq> gxPeCxqqList){
        for(GxPeCxqq cxqq : gxPeCxqqList){
            ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(CXJG_BEANNAME);
            if(exchangeBean != null){
                SjptCxsqCtrlRequestDTO requestDTO = new SjptCxsqCtrlRequestDTO();
                requestDTO.setBeanName("sjpt_cxjg");
                requestDTO.setRequestObject(cxqq);
                Object responseBody = exchangeBeanRequestService.request(CXJG_BEANNAME,requestDTO);
                if (responseBody != null && responseBody instanceof JSONArray) {
                    JSONObject responseJson = ((JSONArray)responseBody).getJSONObject(0);
                    if(responseJson.get("head") != null){
                        JSONObject headJson = responseJson.getJSONObject("head");
                        if(headJson != null && org.apache.commons.lang.StringUtils.equals(headJson.getString("code"),"0000")){
                            cxqq.setZt(SjptConstants.CXQQ_ZT_YCLYTS);
                            cxqq.setSbsj(new Date());
                            sjptEntityMapper.updateByPrimaryKey(cxqq);
                        }
                        if(headJson != null && org.apache.commons.lang.StringUtils.equals(headJson.getString("code"),"2013")) {
                            cxqq.setZt(SjptConstants.CXQQ_ZT_CS);
                            sjptEntityMapper.updateByPrimaryKey(cxqq);
                        }
                    }
                }
            }else{
                throw new AppException("无法获取名称为：" + CXJG_BEANNAME + "的配置信息");
            }
        }
    }

}
