package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwHDO;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlZxdjDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwHMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.gx.GxService;
import cn.gtmap.realestate.exchange.service.national.*;
import cn.gtmap.realestate.exchange.util.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
 * @version 1.0, 2018/12/13
 * @description 数据汇交 service 实现
 */
@Service
public class ShareModelHandlerServiceImpl implements ShareModelHandlerService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareModelHandlerServiceImpl.class);
    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private BdcExchangeZddzService bdcExchangeZddzService;
    @Autowired
    private GxService gxService;
    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;
    @Autowired
    private DjfDjYwxxService djfDjYwxxService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private Set<QlfQlQsztService> qlfQlQsztServices;
    @Resource(name = "gxEntityMapper")
    private EntityMapper gxEntityMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Value("${data.version:standard}")
    private String dataVersion;

    @Autowired
    private KttFwHMapper kttFwHMapper;

    @Override
    public DataModel getShareDataModel(NationalAccessXmlService nationalAccessXmlService, String xmid) {
        DataModel dataModel = nationalAccessXmlService.getNationalAccessDataModel(xmid);
        dataModel = bdcExchangeZddzService.handleZddz(dataModel, xmid);
        return dataModel;
    }

    @Override
    public void shareRunningXmByXmid(String xmid) {
        // 根据 xmid 查找不动产项目
        if (StringUtils.isNotBlank(xmid)) {
            gxService.saveDjfDjYwxxDO(xmid);
        }
    }

    @Override
    public void shareRunningAllXmByProcessInsId(String processInsId) {
        // 根据 xmid 查找不动产项目
        if (StringUtils.isNotBlank(processInsId)) {
            List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                for (BdcXmDO bdcXmDOTemp : bdcXmList) {
                    gxService.saveDjfDjYwxxDO(bdcXmDOTemp.getXmid());
                }
            }
        }
    }

    @Override
    public void shareXmByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
            shareXmByBdcXmDo(bdcXmDO,null);
        }
    }

    @Override
    public void shareAllXmByProcessInsId(String processInsId) {
        if (StringUtils.isNotBlank(processInsId)) {
            List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                for (BdcXmDO bdcXmDOTemp : bdcXmList) {
                    shareXmByBdcXmDo(bdcXmDOTemp,null);
                }
            }
        }
    }

    @Override
    public void shareXmStatusByXmid(String xmid, String status) {
        this.shareXmStatusByXmid(xmid, status, StringUtils.EMPTY);
    }

    /**
     * 根据项目主键共享当前项目状态，可用于项目删除，退回
     *
     * @param xmid   项目主键
     * @param status
     * @param reason reason
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void shareXmStatusByXmid(String xmid, String status, String reason) {
        DjfDjYwxxDO djfDjYwxxDO = djfDjYwxxService.queryYwxxByXmid(xmid);
        if (djfDjYwxxDO != null && StringUtils.isNotBlank(status)) {
            djfDjYwxxDO.setXmzt(status);
            djfDjYwxxDO.setLczt(status);
            if (StringUtils.isNotBlank(reason)) {
                djfDjYwxxDO.setSpyj(reason);
            }
            djfDjYwxxDO.setUpdatetime(new Date());
            djfDjYwxxService.saveYwxx(djfDjYwxxDO);
        }
    }

    @Override
    public void shareAllXmStatusByProcessInsId(String processInsId, String status, String reason) {
        if (StringUtils.isNotBlank(processInsId)) {
            List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                for (BdcXmDO bdcXmDOTemp : bdcXmList) {
                    shareXmStatusByXmid(bdcXmDOTemp.getXmid(), status, reason);
                }
            }
        }
    }

    /**
     * @param xmid 项目主键
     * @param rzid
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据项目主键补偿共享当前项目
     */
    @Override
    public CommonResponse shareXmByXmidForCompensate(String xmid, String rzid) {
        if (StringUtils.isNotBlank(xmid) && StringUtils.isNotBlank(rzid)) {
            BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
            if (bdcXmDO!= null && shareXmByBdcXmDo(bdcXmDO,rzid)){
                return CommonResponse.ok();
            }else {
                return CommonResponse.fail("推送共享失败");
            }
        }
        return CommonResponse.fail("缺少必填参数");
    }

    private boolean shareXmByBdcXmDo(BdcXmDO bdcXmDO,String rzid) {
        List<String> resultList = new ArrayList<>(8);
        if (bdcXmDO != null) {
            try {
                // 获取不同类型的策略 service
                NationalAccessXmlService nationalAccessXmlService = accesssModelHandlerService.getAccessXmlService(bdcXmDO);
                // 利用对应策略的 service 生成 message
                if (!ObjectUtils.isEmpty(nationalAccessXmlService)) {
                    DataModel dataModel = getShareDataModel(nationalAccessXmlService, bdcXmDO.getXmid());
                    //南通推送共享库需要在抵押流程和查封流程至共享库时需要分别多添加qlr和户室信息
                    //包含F的budcdyh都要推送
                    if (dataVersion.equals(CommonConstantUtils.NANTONG)) {
                        if (bdcXmDO.getBdcdyh().contains("F")) {
                            Map<String, String> map = new HashMap();
                            map.put("ywh", bdcXmDO.getXmid());
                            if (bdcXmDO != null && CommonConstantUtils.BDCDYFWLX_DUZH.equals(bdcXmDO.getBdcdyfwlx())) {
                                map.put("sfdz", "true");
                            }
                            List<KttFwHDO> kttFwHList = kttFwHMapper.queryKttFwHList(map);
                            if (CollectionUtils.isNotEmpty(kttFwHList)) {
                                for (KttFwHDO data : kttFwHList) {
                                    Object temp = fillPk(data);
                                    if (temp != null) {
                                        BeanUtil.setEntityFieldValue(Date.class, "updatetime", temp, new Date());
                                        gxEntityMapper.saveOrUpdate(temp, data.getBdcdyh());

                                    }
                                }
                            }
                        }
                   /* if (nationalAccessXmlService instanceof NationalAccessXmlDyqdjImpl){
                        nationalAccessXmlService = (NationalAccessXmlService)Container.getBean("nationalAccessXmlDyqdjWithH");;
                    }else */
                        if (nationalAccessXmlService instanceof NationalAccessXmlCfdjImpl) {
                            nationalAccessXmlService = (NationalAccessXmlService) Container.getBean("nationalAccessXmlCfdjWithQlr");
                        }
                    }

                    if (dataModel != null) {
                        LOGGER.info("===>保存DataModel:{}", JSON.toJSONString(dataModel));
                        CommonResponse<List<String>> listCommonResponse = gxService.saveGxDataModelAndReturnResult(dataModel);
                        if (!listCommonResponse.isSuccess()){
                            resultList.addAll(listCommonResponse.getData());
                        }
                    }
                    gxService.saveDjfDjYwxxDO(bdcXmDO.getXmid());
                    //如果已经登簿，修改上一手权属状态
                    if (bdcXmDO.getDjsj() != null && StringUtils.isNotBlank(bdcXmDO.getDbr())) {
                        List<String> yxmids = commonService.queryYZxxqlXmidListByXmid(bdcXmDO.getXmid());
                        if (CollectionUtils.isNotEmpty(yxmids)) {
                            for (String yxmid : yxmids) {
                                for (QlfQlQsztService qlfQlQsztService : qlfQlQsztServices) {
                                    qlfQlQsztService.updateQsztByYwh(yxmid);
                                }
                                Example example = new Example(QlfQlZxdjDO.class);
                                Example.Criteria criteria = example.createCriteria();
                                criteria.andEqualTo("ywh", bdcXmDO.getXmid());
                                criteria.andEqualTo("zxywh", yxmid);
                                List<QlfQlZxdjDO> list = gxEntityMapper.selectByExample(example);

                                if (CollectionUtils.isEmpty(list)) {
                                    LOGGER.info("当前{}项目注销原权利，在QLF_QL_ZXDJ没有查找到记录，新增一条注销记录", bdcXmDO.getXmid());

                                    QlfQlZxdjDO qlfQlZxdj = new QlfQlZxdjDO();
                                    Date currentDate = Calendar.getInstance().getTime();
                                    qlfQlZxdj.setBdcdyh(bdcXmDO.getBdcdyh());
                                    qlfQlZxdj.setYwh(bdcXmDO.getXmid());
                                    qlfQlZxdj.setZxywh(yxmid);
                                    qlfQlZxdj.setZxsj(bdcXmDO.getDjsj());
                                    qlfQlZxdj.setDbr(bdcXmDO.getDbr());
                                    qlfQlZxdj.setBdcqzh(bdcXmDO.getBdcqzh());
                                    qlfQlZxdj.setBz(bdcXmDO.getBz());
                                    qlfQlZxdj.setUpdatetime(currentDate);
                                    qlfQlZxdj.setCreatetime(currentDate);
                                    gxEntityMapper.insertSelective(qlfQlZxdj);
                                }
                            }
                        }
                    }
                } else {
                    LOGGER.error("数据共享 - 未找到对应的策略服务 xmid：{}", bdcXmDO.getXmid());
                    resultList.add("数据共享 -  未找到对应的策略服务 xmid：{}" + bdcXmDO.getXmid());
                }
            }catch (Exception e){
                LOGGER.error("数据共享 - 推送共享数据异常:",e);
                resultList.add("数据共享 - 推送共享数据异常:"+e.getMessage());
            }
            if (CollectionUtils.isNotEmpty(resultList)){
                if(StringUtils.isBlank(rzid)){
                    //保存推送共享库失败情况
                    BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                    bdcCzrzDO.setRzid(UUIDGenerator.generate16());
                    dozerBeanMapper.map(bdcXmDO, bdcCzrzDO, "initCzrz");
                    bdcCzrzDO.setCzzt(0);
                    bdcCzrzDO.setCzyy(resultList.toString());
                    bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_TSGX.key());
                    bdcCzrzDO.setCzsj(new Date());
                    entityMapper.insert(bdcCzrzDO);
                }else {
                    BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                    bdcCzrzDO.setRzid(rzid);
                    bdcCzrzDO.setCzzt(0);
                    entityMapper.updateByPrimaryKeySelective(bdcCzrzDO);
                }
                return false;
            }else {
                if(StringUtils.isNotBlank(rzid)){
                    BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                    bdcCzrzDO.setRzid(rzid);
                    bdcCzrzDO.setCzzt(1);
                    entityMapper.updateByPrimaryKeySelective(bdcCzrzDO);
                }
            }
        }
        return true;
    }

    /**
     * @param data
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 填充主键
     */
    private static Object fillPk(Object data) {
        try {
            List<Field> pkFieldedList = AnnotationsUtils.getAnnotationField(data, Id.class);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(data));
            if (CollectionUtils.isNotEmpty(pkFieldedList)) {
                for (Field field : pkFieldedList) {
                    if (StringUtils.isBlank(jsonObject.getString(field.getName()))) {
                        jsonObject.put(field.getName(), UUIDGenerator.generate());
                    }
                }
            }
            return jsonObject.toJavaObject(data.getClass());
        } catch (Exception e) {
            LOGGER.error("保存共享数据填充主键异常:{}", JSONObject.toJSONString(data), e);
        }
        return null;
    }
}
