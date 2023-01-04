package cn.gtmap.realestate.exchange.service.impl.inf.gx;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.service.inf.gx.SyncDataShareService;
import cn.gtmap.realestate.exchange.service.national.DjfDjYwxxService;
import cn.gtmap.realestate.exchange.service.inf.gx.GxDataDbService;
import cn.gtmap.realestate.exchange.service.inf.gx.GxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description
 */
@Service
public class GxServiceImpl implements GxService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GxServiceImpl.class);


    @Autowired
    private List<GxDataDbService> gxDataDbServiceList;

    @Autowired
    private DjfDjYwxxService djfDjYwxxService;
    @Autowired
    private SyncDataShareService syncDataShareService;
    // 判断是否需要共享Sync表
    @Value("${share.db.sync:false}")
    private boolean shareSync;

    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存共享结构数据
     */
    @Override
    public void saveGxDataModel(DataModel dataModel) {
        // 保存业务信息
        if (CollectionUtils.isNotEmpty(gxDataDbServiceList)) {
            for (GxDataDbService service : gxDataDbServiceList) {
                service.saveData(dataModel);
            }
        }
    }

    @Override
    public void saveGxDataModelOld(DataModelOld dataModel) {
        // 保存业务信息
        if (CollectionUtils.isNotEmpty(gxDataDbServiceList)) {
            for (GxDataDbService service : gxDataDbServiceList) {
                service.saveDataOld(dataModel);
            }
        }
    }

    /**
     * @param ywh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造 并保存 DJFDJYWXX实体
     */
    @Override
    public void saveDjfDjYwxxDO(String ywh) {
        if (StringUtils.isNotBlank(ywh)) {
            DjfDjYwxxDO djfDjYwxxDO = djfDjYwxxService.queryYwxxByXmid(ywh);
            if (djfDjYwxxDO != null) {
                djfDjYwxxService.saveYwxx(djfDjYwxxDO);
                if (shareSync) {
                    syncDataShareService.saveOrUpdateSyncData(djfDjYwxxDO);
                }
            }
        }
    }

    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 保存共享结构数据（并返回保存结果情况）
     */
    @Override
    public CommonResponse<List<String>> saveGxDataModelAndReturnResult(DataModel dataModel) {
        List<String> reusltList = new ArrayList<>(gxDataDbServiceList.size());
        // 保存业务信息
        if(CollectionUtils.isNotEmpty(gxDataDbServiceList)){
            for(GxDataDbService service : gxDataDbServiceList){
                CommonResponse<String> commonResponse = service.saveDataAndReutrnResult(dataModel);
                if (!commonResponse.isSuccess()){
                    reusltList.add(commonResponse.getData());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(reusltList)){
            return CommonResponse.fail(reusltList);
        }else {
            return CommonResponse.ok();
        }
    }
}
