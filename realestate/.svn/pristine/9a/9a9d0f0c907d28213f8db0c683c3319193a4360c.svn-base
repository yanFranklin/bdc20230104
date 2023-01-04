package cn.gtmap.realestate.exchange.service.impl.inf.gx;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.gx.GxDataDbService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.BeanUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description 根据主键处理共享库数据
 */
public abstract class GxDataDbByPkServiceImpl implements GxDataDbService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GxDataDbByPkServiceImpl.class);
    @Resource(name = "gxEntityMapper")
    private EntityMapper gxEntityMapper;
    @Value("${data.version:}")
    protected String dataVersion;
    @Value("${switch.datasource.flag:false}")
    protected boolean datasourceSwitch;
    //合肥不需要新版上报默认值
    @Value("${access.newDefault:true}")
    public boolean newDefault;
    //拼接字符替换
    @Value("${accessLog.replacezf:,}")
    public String replaceZf;
    @Value("${accessLog.turn-on-replacezf:false}")
    public boolean turnOnReplacezf;
    //从大云平台读取受理办结时间
    @Value("${access.slsjfromdy:false}")
    public boolean slsjFromWorkflow;
    @Autowired
    AccessLogTypeService accessLogTypeService;
    //qxdm是否需要对照
    @Value("${qxdm.convert:false}")
    protected boolean qxdmConvert;

    @Autowired
    public CommonService commonService;
    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存数据到共享库
     */
    @Override
    public void saveData(DataModel dataModel) {
        List dataList = getData(dataModel);
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Object data : dataList) {
                if (CheckEntityUtils.checkPk(data)) {
                    try {
                        BeanUtil.setEntityFieldValue(Date.class, "updatetime", data, new Date());
                        Map<String, Object> pkValMap = CheckEntityUtils.getPkValue(data);
                        if (pkValMap.size() == 1) {
                            Iterator<Map.Entry<String, Object>> iterator = pkValMap.entrySet().iterator();
                            Map.Entry<String, Object> entry = iterator.next();
                            gxEntityMapper.saveOrUpdate(data, pkValMap.get(entry.getKey()));
                        } else {
                            gxEntityMapper.saveOrUpdate(data, pkValMap);
                        }
                    } catch (Exception e) {
                        LOGGER.error("插入共享库ByPk报错,插入数据：{}", JSON.toJSONString(data), e);
                    }
                }
            }
        }
    }

    @Override
    public void saveDataOld(DataModelOld dataModel) {
        List dataList = getDataOld(dataModel);
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Object data : dataList) {
                if (CheckEntityUtils.checkPk(data)) {
                    try {
                        BeanUtil.setEntityFieldValue(Date.class, "updatetime", data, new Date());
                        Map<String, Object> pkValMap = CheckEntityUtils.getPkValue(data);
                        if (pkValMap.size() == 1) {
                            Iterator<Map.Entry<String, Object>> iterator = pkValMap.entrySet().iterator();
                            Map.Entry<String, Object> entry = iterator.next();
                            gxEntityMapper.saveOrUpdate(data, pkValMap.get(entry.getKey()));
                        } else {
                            gxEntityMapper.saveOrUpdate(data, pkValMap);
                        }
                    } catch (Exception e) {
                        LOGGER.error("old插入共享库ByPk报错,插入数据：{}", JSON.toJSONString(data), e);
                    }
                }
            }
        }
    }

    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 保存数据到共享库并返回操作结果
     */
    @Override
    public CommonResponse<String> saveDataAndReutrnResult(DataModel dataModel) {
        List dataList = getData(dataModel);
        String dealErrorStr = null;
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Object data : dataList) {
                if (CheckEntityUtils.checkPk(data)) {
                    try {
                        try {
                            //有可能会没有updatetime这个字段
                            data.getClass().getDeclaredField("updatetime");
                            BeanUtil.setEntityFieldValue(Date.class, "updatetime", data, new Date());
                        } catch (Exception e) {

                        }
                        Map<String, Object> pkValMap = CheckEntityUtils.getPkValue(data);
                        if (pkValMap.size() == 1) {
                            Iterator<Map.Entry<String, Object>> iterator = pkValMap.entrySet().iterator();
                            Map.Entry<String, Object> entry = iterator.next();
                            gxEntityMapper.saveOrUpdate(data, pkValMap.get(entry.getKey()));
                        } else {
                            gxEntityMapper.saveOrUpdate(data, pkValMap);
                        }
                    } catch (Exception e) {
                        LOGGER.error("插入共享库ByPk报错,插入数据：{}", JSON.toJSONString(dataList), e);
                        dealErrorStr = "插入共享库ByPk报错: " + e.getMessage() + ",插入数据:" + JSON.toJSONString(dataList);
                        if (dealErrorStr.length() > 1000){
                            dealErrorStr = "插入共享库ByPk报错: " + e.getMessage() + ",插入数据:" + dataList.get(0).getClass();
                        }
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(dealErrorStr)) {
            return CommonResponse.fail(dealErrorStr);
        } else {
            return CommonResponse.ok();
        }
    }

    public void sendMsg(String msg) {
        MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
        msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_13.getYjlx());
        msgNoticeDTO.setSlbh(msg);
        accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
    }
}
