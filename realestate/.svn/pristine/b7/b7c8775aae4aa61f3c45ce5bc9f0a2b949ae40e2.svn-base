package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.KttFwZrzDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwZrzMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import cn.gtmap.realestate.exchange.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 国家接入-自然幢信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataZrzImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZrzImpl.class);

    @Autowired
    private KttFwZrzMapper kttFwZrzMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<Map> mapList = kttFwZrzMapper.queryKttFwZrzList(map);
            Map<String, BdcFdcqDO> collect = null;
            mapList = kttFwZrzMapper.queryKttFwZrzList(map);
            List<KttFwZrzDO> kttFwZrzList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(mapList)) {
                for (Map zrzMap : mapList) {
                    String jgsj = MapUtils.getString(zrzMap, "JGRQ");
                    Date jgsjDate = DateUtils.formatDate(jgsj);
                    zrzMap.remove("JGRQ");
                    KttFwZrzDO zrz = JSONObject.parseObject(JSONObject.toJSONString(zrzMap), KttFwZrzDO.class);
                    zrz.setJgrq(jgsjDate);
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //大于十位取十位，不足则补全，作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    zrz.setBsm(Integer.valueOf(bsm));
                    if (collect!= null && collect.containsKey(zrz.getBdcdyh())){
                        if (zrz.getJgrq() == null){
                            try {
                                zrz.setJgrq(new SimpleDateFormat("yyyy-MM-dd").parse(collect.get(zrz.getBdcdyh()).getJgsj()));
                            }catch (Exception e){
                                LOGGER.info("转换jgsj出错:",e);
                            }
                        }
                        if (StringUtils.isBlank(zrz.getZcs())){
                            zrz.setZcs(collect.get(zrz.getBdcdyh()).getZcs().toString());
                        }
                        if (StringUtils.isBlank(zrz.getGhyt())){
                            zrz.setGhyt(collect.get(zrz.getBdcdyh()).getGhyt().toString());
                        }
                        if (StringUtils.isBlank(zrz.getFwjg())){
                            zrz.setFwjg(collect.get(zrz.getBdcdyh()).getFwjg().toString());
                        }
                        if (zrz.getZzdmj() == null){
                            zrz.setZzdmj(collect.get(zrz.getBdcdyh()).getZzdmj());
                        }
                    }
                    kttFwZrzList.add(zrz);
                }
            }
            if (CollectionUtils.isNotEmpty(kttFwZrzList)) {
                if (newDefault) {

                    for (KttFwZrzDO kttFwZrzDO : kttFwZrzList) {
                        ClassHandleUtil.setDefaultValueToNullField(kttFwZrzDO);
                    }
                }
                setData(dataModel, kttFwZrzList);
            }
        }
        return dataModel;
    }

    @Override
    public <T> List<T> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "KTT_FW_ZRZ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttFwZrzList();
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
            dataModel.setKttFwZrzList(dataList);
        }
    }

}
