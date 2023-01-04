package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjtDjSlsqOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.DjtDjSlsqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-登记受理申请信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataDjSlsqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDjSlsqImpl.class);

    @Autowired
    private DjtDjSlsqMapper djtDjSlsqMapper;
    @Value("${access.newAccess:true}")
    private boolean newAccessModel;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private BdcXmFeignService xmFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<DjtDjSlsqDO> djtDjSlsqList = djtDjSlsqMapper.queryDjtDjSlsqList(map);
            if (CollectionUtils.isNotEmpty(djtDjSlsqList)) {
                if (newDefault) {
                    for (DjtDjSlsqDO djtDjSlsqDO : djtDjSlsqList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            djtDjSlsqDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", djtDjSlsqDO.getQxdm()));
                        }
                        if (slsjFromWorkflow) {
                            BdcXmQO bdcXmQO = new BdcXmQO();
                            bdcXmQO.setXmid(ywh);
                            List<BdcXmDO> bdcXmDOList = xmFeignService.listBdcXm(bdcXmQO);
                            if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getGzlslid())) {
                                List<TaskData> taskDataList = processTaskClient.listProcessTask(bdcXmDOList.get(0).getGzlslid());
                                if (taskDataList != null && taskDataList.size() > 0) {
                                    for (int i = 0; i < taskDataList.size(); i++) {
                                        if (StringUtils.equals(taskDataList.get(i).getTaskName(), CommonConstantUtils.JD_SL) && StringUtils.isNotBlank(taskDataList.get(i).getTaskId())) {
                                            djtDjSlsqDO.setSlsj(taskDataList.get(i).getEndTime());
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                        ClassHandleUtil.setDefaultValueToNullField(djtDjSlsqDO);
                    }
                }
                setData(dataModel, djtDjSlsqList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<DjtDjSlsqOldDO> djtDjSlsqList = djtDjSlsqMapper.queryDjtDjSlsqListOld(map);
            if (CollectionUtils.isNotEmpty(djtDjSlsqList)) {
                setDataOld(dataModel, djtDjSlsqList);
            }
        }
        return dataModel;
    }

    @Override
    public List<AccessData> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        if (newAccessModel) {
            return "DJT_DJ_SL";
        } else {
            return "DJT_DJ_SLSQ";

        }
    }

    /**
     * @param dataModel
     * @return java.util.List
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getDjtDjSlsqList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getDjtDjSlsqList();
    }

    /**
     * @param dataModel
     * @param dataList
     * @return java.util.List
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public void setData(DataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setDjtDjSlsqList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setDjtDjSlsqList(dataList);
        }
    }
}
