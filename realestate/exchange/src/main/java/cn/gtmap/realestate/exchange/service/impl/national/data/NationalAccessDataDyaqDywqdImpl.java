package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlDyaqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 国家接入-抵押物清单
 *
 * @author liwenshou
 * @version 1.0, 2017/2/6
 */
@Service
public class NationalAccessDataDyaqDywqdImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDyaqDywqdImpl.class);

    @Autowired
    private QlfQlDyaqMapper qlfQlDyaqMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;


    public DataModel getAccessDataModel(String ywh, DataModel dataModel, String bdcdyh) {
        // TODO
//        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
//            //获取当前项目的proid和上一手项目的proid
//            Map proidMap = new HashMap();
//            proidMap.put("proid", ywh);
//            List<Map> bdcXmRelList = bdcdjMapper.getBdcxmRelByMap(proidMap);
//            List<String> ywhList = new ArrayList();
//            if (CollectionUtils.isNotEmpty(bdcXmRelList)) {
//                for (Map tempBdcXmRel : bdcXmRelList) {
//                    if (StringUtils.isNoneBlank((String) tempBdcXmRel.get("YPROID"))) {
//                        ywhList.add((String) tempBdcXmRel.get("YPROID"));
//                    }
//                }
//            }
//            ywhList.add(ywh);
//
//            //根据ywhList获取在建建筑物抵押信息
//            List<QlfQlDyaqDywqdDO> qlfQlDyaqDywqdList = null;
//            List<BdcZjjzwxx> bdcZjjzwxxList = new ArrayList();
//            Map<String, Object> map = Maps.newHashMap();
//            map.put("ywhList", ywhList);
//            bdcZjjzwxxList.addAll(qlfQlDyaqMapper.queryBdcZjjzwxxList(map));
//
//            if (CollectionUtils.isNotEmpty(bdcZjjzwxxList)) {
//                qlfQlDyaqDywqdList = new ArrayList();
//                //组织数据
//                for (BdcZjjzwxx tempBdcZjjzwxx : bdcZjjzwxxList) {
//                    QlfQlDyaqDywqdDO tempQlfQlDyaqDywqd = new QlfQlDyaqDywqdDO();
//                    if (StringUtils.isNoneBlank(tempBdcZjjzwxx.getProid())) {
//                        tempQlfQlDyaqDywqd.setYwh(tempBdcZjjzwxx.getProid());
//                    }
//                    if (tempBdcZjjzwxx.getXh() != null) {
//                        tempQlfQlDyaqDywqd.setXh(Integer.parseInt(tempBdcZjjzwxx.getXh()));
//                    }
//                    if (StringUtils.isNoneBlank(tempBdcZjjzwxx.getZl())) {
//                        tempQlfQlDyaqDywqd.setZl(tempBdcZjjzwxx.getZl());
//                    }
//                    if (StringUtils.isNoneBlank(tempBdcZjjzwxx.getYt())) {
//                        tempQlfQlDyaqDywqd.setYt(tempBdcZjjzwxx.getYt());
//                    }
//                    if (tempBdcZjjzwxx.getDymj() != null) {
//                        tempQlfQlDyaqDywqd.setMj(tempBdcZjjzwxx.getJzmj());
//                    }
//                    if (StringUtils.isNoneBlank(tempBdcZjjzwxx.getBdcdyh())) {
//                        tempQlfQlDyaqDywqd.setBdcdyh(tempBdcZjjzwxx.getBdcdyh());
//                    }
//                    if (StringUtils.isNoneBlank(tempBdcZjjzwxx.getFwbm())) {
//                        tempQlfQlDyaqDywqd.setFwbh(tempBdcZjjzwxx.getFwbm());
//                    }
//                    //权属状态取登记库中，抵押物清单表中的dyzt字段，dyzt为0或空时表示抵押，dyzt为1时表示注销
//                    tempQlfQlDyaqDywqd.setQszt(StringUtils.isNotBlank(tempBdcZjjzwxx.getDyzt()) ? tempBdcZjjzwxx.getDyzt() : "0");
//                    if (StringUtils.isNoneBlank(tempBdcZjjzwxx.getZxproid())) {
//                        tempQlfQlDyaqDywqd.setZxdyywh(tempBdcZjjzwxx.getZxproid());
//                    }
//                    if (StringUtils.isNoneBlank(tempBdcZjjzwxx.getJyyy())) {
//                        tempQlfQlDyaqDywqd.setZxdyyy(tempBdcZjjzwxx.getJyyy());
//                    }
//                    if (tempBdcZjjzwxx.getJyrq() != null) {
//                        tempQlfQlDyaqDywqd.setZxsj(tempBdcZjjzwxx.getJyrq());
//                    }
//                    qlfQlDyaqDywqdList.add(tempQlfQlDyaqDywqd);
//                }
//            }
//
//            if (CollectionUtils.isNotEmpty(qlfQlDyaqDywqdList)) {
//                dataModel.setQlfQlDyaqDywqd(qlfQlDyaqDywqdList);
//            }
//        }
        return dataModel;
    }

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        return getAccessDataModel(ywh, dataModel, null);
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        return null;
    }

    @Override
    public List<AccessData> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "QLF_QL_DYAQ_DYWQD";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlDyaqDywqd();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return null;
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
            dataModel.setQlfQlDyaqDywqd(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {

    }
}
