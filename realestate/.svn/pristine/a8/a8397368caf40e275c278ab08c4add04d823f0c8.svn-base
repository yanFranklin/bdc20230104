package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjSjDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjfDjSjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjSjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-登记收件信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataDjSjImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDjSjImpl.class);

    @Autowired
    private DjfDjSjMapper djfDjSjMapper;

    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private BdcXmFeignService xmFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<DjfDjSjDO> djfDjSjList = djfDjSjMapper.queryDjfDjSjList(map);
            if (CollectionUtils.isNotEmpty(djfDjSjList)) {
                djfDjSjList.forEach(djfDjSjDO -> {
                    if (djfDjSjDO.getCreatetime() == null) {
                        djfDjSjDO.setCreatetime(new Date());
                    }
                    if (slsjFromWorkflow) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setXmid(ywh);
                        List<BdcXmDO> bdcXmDOList = xmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            List<TaskData> taskDataList = processTaskClient.listProcessTask(bdcXmDOList.get(0).getGzlslid());
                            if (taskDataList != null && taskDataList.size() > 0) {
                                for (int i = 0; i < taskDataList.size(); i++) {
                                    if (StringUtils.equals(taskDataList.get(i).getTaskName(), CommonConstantUtils.JD_SL) && StringUtils.isNotBlank(taskDataList.get(i).getTaskId())) {
                                        djfDjSjDO.setSjsj(taskDataList.get(i).getEndTime());
                                        break;
                                    }
                                }
                            }
                        }

                    }
                    if (qxdmConvert) {
                        //需要根据qxdm进行对照
                        djfDjSjDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", djfDjSjDO.getQxdm()));
                    }
                    if (newDefault) {

                        ClassHandleUtil.setDefaultValueToNullField(djfDjSjDO);
                    }
                });
                setData(dataModel, djfDjSjList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<DjfDjSjOldDO> djfDjSjList = djfDjSjMapper.queryDjfDjSjListOld(map);
            if (CollectionUtils.isNotEmpty(djfDjSjList)) {
                djfDjSjList.forEach(djfDjSjDO -> {
                    if (djfDjSjDO.getCreatetime() == null) {
                        djfDjSjDO.setCreatetime(new Date());
                    }
                });
                setDataOld(dataModel, djfDjSjList);
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
        return "DJF_DJ_SJ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getDjfDjSjList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getDjfDjSjList();
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
            dataModel.setDjfDjSjList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setDjfDjSjList(dataList);
        }
    }
}
