package cn.gtmap.realestate.etl.service.impl;


import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.etl.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDyafeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYyXxCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcYyVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.etl.core.mapper.exchange.BdcXmMapper;
import cn.gtmap.realestate.etl.core.model.BzjdModel;
import cn.gtmap.realestate.etl.core.model.DoTaskResponseModel;
import cn.gtmap.realestate.etl.core.model.XscqModel;
import cn.gtmap.realestate.etl.core.qo.TransactionInquiryCfxxQO;
import cn.gtmap.realestate.etl.core.qo.TransactionInquiryDyxxQO;
import cn.gtmap.realestate.etl.core.qo.TransactionInquiryQO;
import cn.gtmap.realestate.etl.service.TransactionInquiryExternalInterface;
import cn.gtmap.realestate.etl.util.Constants;
import cn.gtmap.realestate.etl.util.XmlUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description 蚌埠交易查询webservice
 */
@Service
@WebService(name = "TransactionInquiryExternalInterface",
        targetNamespace = "http://service.etl.realestate.gtmap.cn",
        endpointInterface = "cn.gtmap.realestate.etl.service.TransactionInquiryExternalInterface")
public class TransactionInquiryExternalInterfaceImpl implements TransactionInquiryExternalInterface {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Lazy
    public XmlUtils xmlUtils;

    @Autowired
    @Lazy
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    @Lazy
    private BdcSlJyxxFeignService bdcSlJyxxFeignService;

    @Autowired
    @Lazy
    private BdcCfxxFeignService bdcCfxxFeignService;

    @Autowired
    @Lazy
    private BdcDyafeignService bdcDyafeignService;

    @Autowired
    @Lazy
    private BdcYyXxCxFeignService bdcYyXxCxFeignService;

    @Autowired
    @Lazy
    private BdcBdcdyFeignService bdcBdcdyFeignService;

    @Autowired
    @Lazy
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    @Lazy
    private BdcSlZdFeignService bdcSlZdFeignService;

    private static final DateTimeFormatter DATE_FORMATYMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
    @Autowired
    @Lazy
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    @Lazy
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    @Lazy
    private BdcXmMapper bdcXmMapper;

    @Autowired
    @Lazy
    private BdcQlrFeignService qlrFeignService;

    private static final String DOASK_TYPE_FILE_HANDLER_FLAG = "3";

    private static final String DOASK_TYPE_QUERY_DYXX_FLAG = "5";

    private static final String DOASK_TYPE_QUERY_CFXX_FLAG = "6";

    @Autowired
    @Lazy
    private PhotoForOrderServiceImpl photoForOrderService;


    @Value("#{'${ysgg.djxl:}'.split(',')}")
    private List<String> ysggDjxl;

    /**
     * @param param
     * @return
     * @modify wyh flag为6时添加权利人,产权证号
     */
    @Override
    public String DoAsk(String param) {
        try {
            log.info("------参数:{}", param);
            List<Element> elementList = xmlUtils.getElementsByNode(param, "param");
            if (CollectionUtils.isEmpty(elementList) || elementList.size() > 2) {
                throw new AppException("参数为空！请检查！");
            }
            Element node = elementList.get(0);
            String flag = node.attributeValue("flag");
            log.info("------flag:{}", flag);
            //查询附件接口
            if (DOASK_TYPE_FILE_HANDLER_FLAG.equals(flag)) {
                Element element = xmlUtils.getElementsByNode(param, "param").get(0).element("id");
                if (element != null) {
                    String id = (String) element.getData();
                    if (StringUtils.isNotBlank(id)) {
                        return photoForOrderService.queryFileInfo(param);
                    } else {
                        return photoForOrderService.queryAllFileInfo(param);
                    }
                } else {
                    return photoForOrderService.queryAllFileInfo(param);
                }
            } else if (DOASK_TYPE_QUERY_DYXX_FLAG.equals(flag)) {
                TransactionInquiryDyxxQO transactionInquiryDyxxQO = new TransactionInquiryDyxxQO();
                Field[] fields = transactionInquiryDyxxQO.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String key = field.getName();
                    Element element = node.element(key);
                    String text = Objects.nonNull(element) ? element.getTextTrim() : "";
                    String name = "";
                    if (key.length() > 1) {
                        name = key.substring(0, 1).toUpperCase() + key.substring(1);
                    }
                    Method method = transactionInquiryDyxxQO.getClass().getMethod("set" + name, String.class);
                    method.invoke(transactionInquiryDyxxQO, text);
                }
                log.info("dyxx------查询参数:{}", transactionInquiryDyxxQO);
                List<DyxxDTO> dyxxDTOList = bdcXmMapper.listDyxx(transactionInquiryDyxxQO.getCqdjbh(), StringUtils.isNotBlank(transactionInquiryDyxxQO.getBdcqzh()) ? transactionInquiryDyxxQO.getBdcqzh() : transactionInquiryDyxxQO.getBdcqzmh());
                if (CollectionUtils.isNotEmpty(dyxxDTOList)) {
                    List<Map> zdMapList = bdcZdFeignService.queryBdcZd("dyfs");
                    for (DyxxDTO dyxxDTO : dyxxDTOList) {
                        if (CollectionUtils.isNotEmpty(zdMapList) && dyxxDTO != null && StringUtils.isNotBlank(dyxxDTO.getDylx())) {
                            for (Map map : zdMapList) {
                                if (StringUtils.equals(dyxxDTO.getDylx(), MapUtils.getString(map, "DM"))) {
                                    if (Objects.nonNull(map.get("MC"))) {
                                        dyxxDTO.setDylx(map.get("MC").toString());
                                    }
                                }
                            }
                        }
                    }
                }

                DoTaskResponseModel doTaskResponseModel = new DoTaskResponseModel();
                doTaskResponseModel.setDyxxDTOList(dyxxDTOList);
                JAXBContext jaxbContext = JAXBContext.newInstance(DoTaskResponseModel.class);
                StringWriter writer = new StringWriter();
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(doTaskResponseModel, writer);
                String xmlStr = writer.toString();
                if (xmlStr.contains(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")) {
                    xmlStr = xmlStr.replaceAll(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                }
                xmlStr = StringUtils.replace(xmlStr, "&quot;", "'");
                return xmlStr;
            } else if (DOASK_TYPE_QUERY_CFXX_FLAG.equals(flag)) {
                TransactionInquiryCfxxQO transactionInquiryCfxxQO = new TransactionInquiryCfxxQO();
                Field[] fields = transactionInquiryCfxxQO.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String key = field.getName();
                    Element element = node.element(key);
                    String text = Objects.nonNull(element) ? element.getTextTrim() : "";
                    String name = "";
                    if (key.length() > 1) {
                        name = key.substring(0, 1).toUpperCase() + key.substring(1);
                    }
                    Method method = transactionInquiryCfxxQO.getClass().getMethod("set" + name, String.class);
                    method.invoke(transactionInquiryCfxxQO, text);
                }
                log.info("cfxx------查询参数:{}", transactionInquiryCfxxQO);
                List<CfxxDTO> cfxxList = bdcXmMapper.listCfxx(transactionInquiryCfxxQO.getCqdjbh(), transactionInquiryCfxxQO.getBdcqzh());
                //根据受理编号分组,里面可能会有多个证书号
                Map<String, List<CfxxDTO>> cfxxDTOMap = cfxxList.stream().collect(Collectors.groupingBy(CfxxDTO::getCfdjbh));

                List<CfxxDTO> cfxxDTOList = new ArrayList<>();
                for (Map.Entry<String, List<CfxxDTO>> stringListEntry : cfxxDTOMap.entrySet()) {
                    CfxxDTO cfxxItem = stringListEntry.getValue().get(0);
                    String cqzh = stringListEntry.getValue()
                            .stream()
                            .map(CfxxDTO::getCqzh)
                            .filter(Objects::nonNull)
                            .distinct()
                            .collect(Collectors.joining(","));

                    String cqr = stringListEntry.getValue()
                            .stream()
                            .map(CfxxDTO::getCqr)
                            .filter(Objects::nonNull)
                            .distinct()
                            .collect(Collectors.joining(","));

                    CfxxDTO cfxxDTO = new CfxxDTO();
                    BeanUtils.copyProperties(cfxxItem, cfxxDTO);
                    cfxxDTO.setCqzh(cqzh);
                    cfxxDTO.setCqr(cqr);
                    cfxxDTOList.add(cfxxDTO);
                }

                log.info("cfxx------返回参数:{}", JSON.toJSONString(cfxxDTOList));
                if (CollectionUtils.isNotEmpty(cfxxDTOList)) {
                    for (CfxxDTO cfxxDTO : cfxxDTOList) {
                        if (StringUtils.isNotBlank(cfxxDTO.getBdcdyh())) {
                            Map<String, Object> queryCfmj = new HashMap<>(1);
                            queryCfmj.put("bdcdyh", cfxxDTO.getBdcdyh());
                            List<CfmjDTO> cfmjDTOS = bdcXmMapper.queryCfmj(queryCfmj);
                            if (CollectionUtils.isNotEmpty(cfmjDTOS)) {
                                if (cfmjDTOS.get(0).getJzmj() != null && cfmjDTOS.get(0).getJzmj() != 0 && cfmjDTOS.get(0).getSyqmj() != null && cfmjDTOS.get(0).getSyqmj() != 0) {
                                    cfxxDTO.setCfmj(Double.toString(cfmjDTOS.get(0).getJzmj()));
                                } else if (cfmjDTOS.get(0).getJzmj() != null && cfmjDTOS.get(0).getJzmj() != 0 && cfmjDTOS.get(0).getSyqmj() == null) {
                                    cfxxDTO.setCfmj(Double.toString(cfmjDTOS.get(0).getJzmj()));
                                } else if (cfmjDTOS.get(0).getSyqmj() != null && cfmjDTOS.get(0).getSyqmj() != 0 && cfmjDTOS.get(0).getJzmj() == null) {
                                    cfxxDTO.setCfmj(Double.toString(cfmjDTOS.get(0).getSyqmj()));
                                }
                            }
                        }
                    }
                }
                log.info("cfxx------返回参数:{}", JSON.toJSONString(cfxxDTOList));
                DoTaskResponseModel doTaskResponseModel = new DoTaskResponseModel();
                doTaskResponseModel.setCfxxDTOList(cfxxDTOList);
                JAXBContext jaxbContext = JAXBContext.newInstance(DoTaskResponseModel.class);
                StringWriter writer = new StringWriter();
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.marshal(doTaskResponseModel, writer);
                String xmlStr = writer.toString();
                if (xmlStr.contains(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")) {
                    xmlStr = xmlStr.replaceAll(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                }
                xmlStr = StringUtils.replace(xmlStr, "&quot;", "'");
                return xmlStr;
            } else {
                TransactionInquiryQO transactionInquiryQO = new TransactionInquiryQO();
                Field[] fields = transactionInquiryQO.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String key = field.getName();
                    Element element = node.element(key);
                    String text = Objects.nonNull(element) ? element.getTextTrim() : "";
                    String name = "";
                    if (key.length() > 1) {
                        name = key.substring(0, 1).toUpperCase() + key.substring(1);
                    }
                    Method method = transactionInquiryQO.getClass().getMethod("set" + name, String.class);
                    method.invoke(transactionInquiryQO, text);
                }
                log.info("------查询参数:{}", transactionInquiryQO);
                if (StringUtils.isBlank(transactionInquiryQO.getCqzh()) && StringUtils.isBlank(transactionInquiryQO.getXm()) && StringUtils.isBlank(transactionInquiryQO.getZjhm())) {
                    throw new AppException("查询参数为空！请检查！");
                }

                String xmlStr = "";
                Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                Map<String, List<Map>> slZdMap = bdcSlZdFeignService.listBdcSlzd();

                // 交易处存量房网签发出数据请求只查询现势产权(用途:存量房合同挂牌和签约)：
                if (StringUtils.equals("1", flag)) {
                    List<XscqDTO> xscqDTOList = bdcXmMapper.listXscq(transactionInquiryQO.getXm(), transactionInquiryQO.getZjhm(), transactionInquiryQO.getCqzh());
                    if (CollectionUtils.isEmpty(xscqDTOList)) {
                        xscqDTOList = bdcXmMapper.listXscqWithTd(transactionInquiryQO.getXm(), transactionInquiryQO.getZjhm(), transactionInquiryQO.getCqzh());
                        if (CollectionUtils.isEmpty(xscqDTOList)) {
                            return errorMsg("查询不到结果");
                        }
                    }
                    log.info("-----查询结果：{}", JSON.toJSONString(xscqDTOList));

                    //组织遗失公告状态
                    if (CollectionUtils.isNotEmpty(ysggDjxl)) {
                        for (XscqDTO xscqDTO : xscqDTOList) {
                            List<BdcXmDO> bdcXmDOList = bdcXmMapper.ggxm(CommonConstantUtils.QSZT_TEMPORY, CommonConstantUtils.AJZT_YB_DM, xscqDTO.getBdcdyh(), ysggDjxl);
                            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                                log.info("-----存在遗失公告：{}", JSON.toJSONString(bdcXmDOList));
                                xscqDTO.setGszt("1");
                            } else {
                                log.info("-----不存在遗失公告：{}", JSON.toJSONString(bdcXmDOList));
                                xscqDTO.setGszt("0");
                            }
                        }
                    } else {
                        throw new AppException("遗失公告登记小类为空！请检查配置");

                    }

                    // 查询受理交易信息
                    cxJyxx(zdMap, slZdMap, xscqDTOList);

                    XscqModel xscqModel = new XscqModel();
                    xscqModel.setXscqDTOList(xscqDTOList);

                    JAXBContext jaxbContext = JAXBContext.newInstance(XscqModel.class);
                    StringWriter writer = new StringWriter();
                    Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.marshal(xscqModel, writer);
                    xmlStr = writer.toString();
                    if (xmlStr.contains(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")) {
                        xmlStr = xmlStr.replaceAll(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                    }
                    xmlStr = StringUtils.replace(xmlStr, "&quot;", "'");

                }
                // 交易处存量房网签发出数据请求(用途:检索存量房合同的办证进度)
                if (StringUtils.equals("2", flag)) {
                    if (StringUtils.isBlank(transactionInquiryQO.getHtbh())) {
                        throw new AppException("合同编号参数为空！请检查！");
                    }
                    List<BzjdDTO> bzjdDTOList = bdcXmMapper.listBzjd(transactionInquiryQO.getHtbh());
                    if (CollectionUtils.isEmpty(bzjdDTOList)) {
                        //未查询到数据匹配下土地权利表
//                        bzjdDTOList = bdcXmMapper.listBzjdWithTd(transactionInquiryQO.getHtbh());
//                        if (CollectionUtils.isEmpty(bzjdDTOList)){
                        return errorMsg("查无此数据！");
//                        }
                    }
                    List<String> xmidList = bzjdDTOList.stream().map(BzjdDTO::getXmid).distinct().collect(Collectors.toList());
                    BaseQO baseQO = new BaseQO();
                    baseQO.setIds(xmidList);
                    log.info("------项目ids:{}", xmidList.size());
                    List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmids(baseQO);
                    log.info("------交易信息:{}", bdcSlJyxxDOList.size());
                    // 根据合同编号过滤数据
                    if (StringUtils.isNotEmpty(transactionInquiryQO.getHtbh())) {
                        if (CollectionUtils.isEmpty(bdcSlJyxxDOList)) {
                            return errorMsg("未办理！");
                        }
                        bdcSlJyxxDOList = bdcSlJyxxDOList.stream().filter(a -> a.getHtbh().equals(transactionInquiryQO.getHtbh())).collect(Collectors.toList());
                        List<String> slXmidList = bdcSlJyxxDOList.stream().map(BdcSlJyxxDO::getXmid).distinct().collect(Collectors.toList());
                        if (CollectionUtils.isEmpty(slXmidList)) {
                            return errorMsg("未办理");
                        }
                        bzjdDTOList = bzjdDTOList.stream().filter(a -> slXmidList.contains(a.getXmid())).collect(Collectors.toList());
                    }
                    queryBjzd(zdMap, slZdMap, bzjdDTOList, bdcSlJyxxDOList);

                    BzjdModel bzjdModel = new BzjdModel();
                    bzjdModel.setBzjdDTOList(bzjdDTOList);
                    JAXBContext jaxbContext = JAXBContext.newInstance(BzjdModel.class);
                    StringWriter writer = new StringWriter();
                    Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.marshal(bzjdModel, writer);
                    xmlStr = writer.toString();
                    xmlStr = StringUtils.replace(xmlStr, "&quot;", "'");
                }
                return xmlStr;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return errorMsg("查询失败！");
        }
    }

    private void queryBjzd(Map<String, List<Map>> zdMap, Map<String, List<Map>> slZdMap, List<BzjdDTO> bzjdDTOList, List<BdcSlJyxxDO> bdcSlJyxxDOList) {
        Map<String, List<BdcSlJyxxDO>> bdcSlJyxxDOMap = CollectionUtils.isNotEmpty(bdcSlJyxxDOList) ?
                bdcSlJyxxDOList.stream().collect(Collectors.groupingBy(BdcSlJyxxDO::getXmid)) : new ConcurrentHashMap<>(16);

        bzjdDTOList.forEach(bzjdDTO -> {
            if (bdcSlJyxxDOMap.containsKey(bzjdDTO.getXmid()) && Objects.nonNull(bdcSlJyxxDOMap.get(bzjdDTO.getXmid()))) {
                bzjdDTO.setJyfs(null == bdcSlJyxxDOMap.get(bzjdDTO.getXmid()).get(0).getJylx() ? StringUtils.EMPTY : StringToolUtils.convertBeanPropertyValueOfZdByString(
                        bdcSlJyxxDOMap.get(bzjdDTO.getXmid()).get(0).getJylx(), slZdMap.get(Constants.JYLX)
                ));
                bzjdDTO.setHtbh(null != bdcSlJyxxDOMap.get(bzjdDTO.getXmid()).get(0).getHtbh() ? bdcSlJyxxDOMap.get(bzjdDTO.getXmid()).get(0).getHtbh() : StringToolUtils.EMPTY);
                bzjdDTO.setSbgz(null != bdcSlJyxxDOMap.get(bzjdDTO.getXmid()).get(0).getPgjg() ? bdcSlJyxxDOMap.get(bzjdDTO.getXmid()).get(0).getPgjg().toString() : StringToolUtils.EMPTY);
            }
            //空值处理
            dealSpaceForBzjdDTO(zdMap, bzjdDTO);
        });
    }

    private void cxJyxx(Map<String, List<Map>> zdMap, Map<String, List<Map>> slZdMap, List<XscqDTO> xscqDTOList) {
        List<String> xmidList = xscqDTOList.stream().map(XscqDTO::getXmid).distinct().collect(Collectors.toList());
        BaseQO baseQO = new BaseQO();
        baseQO.setIds(xmidList);
        log.info("------项目ids:{}", xmidList.size());
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmids(baseQO);
        log.info("------交易信息:{}", bdcSlJyxxDOList.size());
        Map<String, List<BdcSlJyxxDO>> bdcSlJyxxDOMap = CollectionUtils.isNotEmpty(bdcSlJyxxDOList) ?
                bdcSlJyxxDOList.stream().collect(Collectors.groupingBy(BdcSlJyxxDO::getXmid)) : new ConcurrentHashMap<>(16);

        for (XscqDTO xscqDTO : xscqDTOList) {
            // 处理受理相关字段
            dealSlxxForXscqDTO(slZdMap, bdcSlJyxxDOMap, xscqDTO);
            //查询抵押
            List<Map> dyList = bdcDyafeignService.listDyaByBdcdyhAndqszt(xscqDTO.getBdcdyh(), CommonConstantUtils.QSZT_VALID);
            //53543 【蚌埠】交易产权接口新增字段需求
            List<XscqDyxxDTo> dyxxDToList = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(dyList)) {
                for (Map map : dyList) {
                    XscqDyxxDTo xscqDyxxDTo = new XscqDyxxDTo();
                    xscqDyxxDTo.setDydbsj(null != map.get("DJSJ") ? String.valueOf(map.get("DJSJ")) : "");
                    if (CommonConstantUtils.SF_S_DM.equals(map.get("JZZR"))) {
                        xscqDyxxDTo.setDyqsfxzzy("是");
                    } else if (CommonConstantUtils.SF_F_DM.equals(map.get("JZZR"))) {
                        xscqDyxxDTo.setDyqsfxzzy("否");
                    } else {
                        xscqDyxxDTo.setDyqsfxzzy("");
                    }
                    //查询抵押权人信息
                    BdcQlrQO qlrQO = new BdcQlrQO();
                    qlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    qlrQO.setXmid((String) map.get("XMID"));
                    List<BdcQlrDO> bdcQlrDOList = qlrFeignService.listBdcQlr(qlrQO);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        xscqDyxxDTo.setDyqr(bdcQlrDOList.get(0).getQlrmc());
                    } else {
                        xscqDyxxDTo.setDyqr("");
                    }
                    //查询项目表证书号信息
                    BdcXmQO xmQO = new BdcXmQO();
                    xmQO.setXmid((String) map.get("XMID"));
                    List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(xmQO);
                    if (CollectionUtils.isNotEmpty(xmDOList)) {
                        xscqDyxxDTo.setDyqbdcdjzmh(xmDOList.get(0).getBdcqzh());
                    } else {
                        xscqDyxxDTo.setDyqbdcdjzmh("");

                    }
                    dyxxDToList.add(xscqDyxxDTo);
                }

            } else {
                XscqDyxxDTo xscqDyxxDTo = new XscqDyxxDTo();
                xscqDyxxDTo.setDydbsj("");
                xscqDyxxDTo.setDyqbdcdjzmh("");
                xscqDyxxDTo.setDyqr("");
                xscqDyxxDTo.setDyqsfxzzy("");
                dyxxDToList.add(xscqDyxxDTo);
            }
            xscqDTO.setDyxxList(dyxxDToList);
            //异议
            BdcYyVO bdcYyVO = new BdcYyVO();
            bdcYyVO.setBdcdyh(xscqDTO.getBdcdyh());
            List yyList = bdcYyXxCxFeignService.listBdcYy(JSON.toJSONString(bdcYyVO));
            //预告
            List ygList = bdcBdcdyFeignService.listYgByBdcdyh(xscqDTO.getBdcdyh());
            //查封
            List cfList = bdcCfxxFeignService.listBdccfByBdcdyh(xscqDTO.getBdcdyh());

            xscqDTO.setCfzt(CollectionUtils.isEmpty(cfList) ? "0" : String.valueOf(cfList.size()));
            xscqDTO.setDyzt(CollectionUtils.isEmpty(dyList) ? "0" : String.valueOf(dyList.size()));
            xscqDTO.setYyzt(CollectionUtils.isEmpty(yyList) ? "0" : String.valueOf(yyList.size()));
            xscqDTO.setYgzt(CollectionUtils.isEmpty(ygList) ? "0" : String.valueOf(ygList.size()));
            // 转化字典项
            dealZdForXscqDTO(zdMap, xscqDTO);
            // 处理权籍相关字段
            dealQjForXscqDTO(xscqDTO);
            //处理空字段
            dealSpaceForXscqDTO(xscqDTO, zdMap, slZdMap);
        }
    }

    /**
     * 默认值处理
     *
     * @param
     * @return
     * @Date 2021/4/22
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private void dealSpaceForBzjdDTO(Map<String, List<Map>> zdMap, BzjdDTO bzjdDTO) {
        if (StringUtils.isNotEmpty(bzjdDTO.getYt())) {
            bzjdDTO.setYt(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bzjdDTO.getYt()), zdMap.get(Constants.FWYT)));
        } else {
            bzjdDTO.setYt("");
        }

        if (StringUtils.isNotEmpty(bzjdDTO.getGyfs())) {
            bzjdDTO.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bzjdDTO.getGyfs()), zdMap.get(Constants.GYFS)));
        } else {
            bzjdDTO.setGyfs("");
        }

        if (StringUtils.isNotEmpty(bzjdDTO.getCqbj())) {
            bzjdDTO.setCqbj(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bzjdDTO.getCqbj()), zdMap.get(Constants.QSZT)));
        } else {
            bzjdDTO.setCqbj("");
        }
        if (StringUtils.isNotEmpty(bzjdDTO.getJg())) {
            bzjdDTO.setJg(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bzjdDTO.getJg()), zdMap.get(Constants.FWJG)));
        } else {
            bzjdDTO.setJg("");
        }

        if (StringUtils.isNotEmpty(bzjdDTO.getXzqy())) {
            bzjdDTO.setXzqy(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bzjdDTO.getXzqy()), zdMap.get(Constants.QX)));
        } else {
            bzjdDTO.setXzqy("");
        }
        if (StringUtils.isNotEmpty(bzjdDTO.getFwxz())) {
            bzjdDTO.setFwxz(StringToolUtils.convertBeanPropertyValueOfZdByString(bzjdDTO.getFwxz(), zdMap.get(Constants.FWXZ)));
        } else {
            bzjdDTO.setFwxz("");
        }
        if (null != bzjdDTO.getQlrxz()) {
            if (CommonConstantUtils.QLRLB_QLR.equals(bzjdDTO.getQlrxz())) {
                bzjdDTO.setQlrxz("受让方");
            }
            if (CommonConstantUtils.QLRLB_YWR.equals(bzjdDTO.getQlrxz())) {
                bzjdDTO.setQlrxz("转让方");
            }
        } else {
            bzjdDTO.setQlrxz("");
        }
        if (StringUtils.isBlank(bzjdDTO.getCqzh())) {
            bzjdDTO.setCqzh("");
        }
    }

    /**
     * 处理空字段值，防止xml不生成节点
     */
    private void dealSpaceForXscqDTO(XscqDTO xscqDTO, Map<String, List<Map>> zdMap, Map<String, List<Map>> slZdMap) {
        xscqDTO.setFwbm(StringUtils.isEmpty(xscqDTO.getFwbm()) ? StringUtils.EMPTY : xscqDTO.getFwbm());
        xscqDTO.setCqdjbh(StringUtils.isEmpty(xscqDTO.getCqdjbh()) ? StringUtils.EMPTY : xscqDTO.getCqdjbh());
        xscqDTO.setBdcdyh(StringUtils.isEmpty(xscqDTO.getBdcdyh()) ? StringUtils.EMPTY : xscqDTO.getBdcdyh());
        xscqDTO.setXm(StringUtils.isEmpty(xscqDTO.getXm()) ? StringUtils.EMPTY : xscqDTO.getXm());
        xscqDTO.setZjhm(StringUtils.isEmpty(xscqDTO.getZjhm()) ? StringUtils.EMPTY : xscqDTO.getZjhm());
        xscqDTO.setFwzl(StringUtils.isEmpty(xscqDTO.getFwzl()) ? StringUtils.EMPTY : xscqDTO.getFwzl());
        xscqDTO.setJg(StringUtils.isEmpty(xscqDTO.getJg()) ? StringUtils.EMPTY : xscqDTO.getJg());
        xscqDTO.setYt(StringUtils.isEmpty(xscqDTO.getYt()) ? StringUtils.EMPTY : xscqDTO.getYt());
        xscqDTO.setFwxz(StringUtils.isEmpty(xscqDTO.getFwxz()) ? StringUtils.EMPTY : xscqDTO.getFwxz());
        xscqDTO.setGyfs(StringUtils.isEmpty(xscqDTO.getGyfs()) ? StringUtils.EMPTY : xscqDTO.getGyfs());
        xscqDTO.setFwcqrq(StringUtils.isEmpty(xscqDTO.getFwcqrq()) ? StringUtils.EMPTY : xscqDTO.getFwcqrq());
        xscqDTO.setZxbj(StringUtils.isEmpty(xscqDTO.getZxbj()) ? StringUtils.EMPTY : xscqDTO.getZxbj());
        xscqDTO.setCqzh(StringUtils.isEmpty(xscqDTO.getCqzh()) ? StringUtils.EMPTY : xscqDTO.getCqzh());
        xscqDTO.setGyfe(StringUtils.isEmpty(xscqDTO.getGyfe()) ? StringUtils.EMPTY : xscqDTO.getGyfe());
        xscqDTO.setJyfs(StringUtils.isEmpty(xscqDTO.getJyfs()) ? StringUtils.EMPTY : StringToolUtils.convertBeanPropertyValueOfZdByString(xscqDTO.getJyfs(), slZdMap.get(Constants.JYLX)));
        xscqDTO.setZh(StringUtils.isEmpty(xscqDTO.getZh()) ? StringUtils.EMPTY : xscqDTO.getZh());
        xscqDTO.setJm(StringUtils.isEmpty(xscqDTO.getJm()) ? StringUtils.EMPTY : xscqDTO.getJm());
        xscqDTO.setYt2(StringUtils.isEmpty(xscqDTO.getYt2()) ? StringUtils.EMPTY : xscqDTO.getYt2());
        xscqDTO.setTdjyfs(StringUtils.isEmpty(xscqDTO.getTdjyfs()) ? StringUtils.EMPTY : xscqDTO.getTdjyfs());
        xscqDTO.setSzqx(StringUtils.isEmpty(xscqDTO.getSzqx()) ? StringUtils.EMPTY : xscqDTO.getSzqx());
        xscqDTO.setJznd(null == xscqDTO.getJznd() ? StringUtils.EMPTY : xscqDTO.getJznd());
        xscqDTO.setJzqh(StringUtils.isEmpty(xscqDTO.getJzqh()) ? StringUtils.EMPTY : xscqDTO.getJzqh());
        xscqDTO.setSzcs(StringUtils.isEmpty(xscqDTO.getSzcs()) ? StringUtils.EMPTY : xscqDTO.getSzcs());
        xscqDTO.setZcs(StringUtils.isEmpty(xscqDTO.getZcs()) ? StringUtils.EMPTY : xscqDTO.getZcs());

    }

    /**
     * 处理 xscqDTO 中权籍相关字段 <br>
     * 1. jznd -> ljz.jgrq
     * 2. jzqh -> ljz.fwmc (处理)
     * 3. zh -> ljz.dh
     *
     * @param xscqDTO xscqDTO
     */
    private void dealQjForXscqDTO(XscqDTO xscqDTO) {
        if (StringUtils.isBlank(xscqDTO.getFwbm())) {
            return;
        }
        BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(xscqDTO.getBdcdyh(), null, "");
        if (null != bdcdyResponseDTO && null != bdcdyResponseDTO.getFwDcbIndex()) {
            FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(bdcdyResponseDTO.getFwDcbIndex(), "");
            if (null != fwLjzDO) {
                xscqDTO.setJznd(fwLjzDO.getJgrq() != null ? DateUtils.formateTime(fwLjzDO.getJgrq(), DATE_FORMATYMDHMS) : StringUtils.EMPTY);
                xscqDTO.setJzqh(StringUtils.isNotBlank(fwLjzDO.getFwmc()) ? dealFwmc(fwLjzDO.getFwmc()) : StringUtils.EMPTY);
                xscqDTO.setZh(StringUtils.isNotBlank(fwLjzDO.getDh()) ? fwLjzDO.getDh() : StringUtils.EMPTY);
            }
        }

    }

    /**
     * 处理 xscqDTO 中受理相关字段<br>
     * 1. jyfs -> jylx（mc）
     * 2. sbgz -> pgjg
     *
     * @param slZdMap        受理字典
     * @param bdcSlJyxxDOMap bdcSlJyxxDOMap
     * @param xscqDTO        xscqDTO
     */
    private void dealSlxxForXscqDTO(Map<String, List<Map>> slZdMap, Map<String, List<BdcSlJyxxDO>> bdcSlJyxxDOMap, XscqDTO xscqDTO) {
        if (bdcSlJyxxDOMap.containsKey(xscqDTO.getXmid()) && Objects.nonNull(bdcSlJyxxDOMap.get(xscqDTO.getXmid()))) {
            List<BdcSlJyxxDO> bdcSlJyxxDOS = bdcSlJyxxDOMap.get(xscqDTO.getXmid());
            BdcSlJyxxDO bdcSlJyxxDO = CollectionUtils.isNotEmpty(bdcSlJyxxDOS) ? bdcSlJyxxDOS.get(0) : new BdcSlJyxxDO();
            xscqDTO.setJyfs((null == bdcSlJyxxDO.getJylx()) ? StringUtils.EMPTY : StringToolUtils.convertBeanPropertyValueOfZdByString(bdcSlJyxxDO.getJylx(), slZdMap.get(Constants.JYLX)));
            xscqDTO.setSbgz(Objects.isNull(bdcSlJyxxDO.getPgjg()) ? StringUtils.EMPTY : bdcSlJyxxDO.getPgjg().toString());
        } else {
            xscqDTO.setJyfs(StringUtils.EMPTY);
            xscqDTO.setSbgz(StringUtils.EMPTY);
        }
    }

    /**
     * 处理 xscqDTO 中需要转换的字典项
     *
     * @param xscqDTO xscqDTO
     * @param zdMap   zdMap
     */
    private void dealZdForXscqDTO(Map<String, List<Map>> zdMap, XscqDTO xscqDTO) {
        if (StringUtils.isNotEmpty(xscqDTO.getYt())) {
            xscqDTO.setYt(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(xscqDTO.getYt()), zdMap.get(Constants.FWYT)));
        } else {
            xscqDTO.setYt("");
        }

        if (StringUtils.isNotEmpty(xscqDTO.getGyfs())) {
            xscqDTO.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(xscqDTO.getGyfs()), zdMap.get(Constants.GYFS)));
        } else {
            xscqDTO.setGyfs("");
        }

        if (StringUtils.isNotEmpty(xscqDTO.getFwbm())) {
            xscqDTO.setTdxz(convertTdxz(xscqDTO.getFwbm()));
        } else {
            xscqDTO.setTdxz("");
        }
        if (StringUtils.isNotEmpty(xscqDTO.getTdjyfs())) {
            xscqDTO.setTdjyfs(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(xscqDTO.getTdjyfs()), zdMap.get(Constants.QLXZ)));
        } else {
            xscqDTO.setTdjyfs("");
        }

        if (StringUtils.isNotEmpty(xscqDTO.getFwxz())) {
            xscqDTO.setFwxz(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(xscqDTO.getFwxz()), zdMap.get(Constants.FWXZ)));
        } else {
            xscqDTO.setFwxz("");
        }

        if (StringUtils.isNotEmpty(xscqDTO.getYt2())) {
            xscqDTO.setYt2(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(xscqDTO.getYt2()), zdMap.get(Constants.FWLX)));
        } else {
            xscqDTO.setYt2("");
        }

        if (StringUtils.isNotEmpty(xscqDTO.getJg())) {
            xscqDTO.setJg(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(xscqDTO.getJg()), zdMap.get(Constants.FWJG)));
        } else {
            xscqDTO.setJg("");
        }

        if (StringUtils.isNotEmpty(xscqDTO.getSzqx())) {
            xscqDTO.setSzqx(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(xscqDTO.getSzqx()), zdMap.get(Constants.QX)));
        } else {
            xscqDTO.setSzqx("");
        }

        if (StringUtils.isNotEmpty(xscqDTO.getJznd())) {
            xscqDTO.setJznd("");
        }
        if (StringUtils.isNotEmpty(xscqDTO.getJzqh())) {
            xscqDTO.setJzqh("");
        }

        //给蚌埠交易处返回时，qszt赋值改变，1-0 ，2-1
        if (Constants.QSZT_XS.equals(xscqDTO.getQszt())) {
            xscqDTO.setQszt("0");
            xscqDTO.setZxbj("0");
        } else if (Constants.QSZT_LS.equals(xscqDTO.getQszt())) {
            xscqDTO.setQszt("1");
            xscqDTO.setZxbj("1");
        }


    }

    /**
     * 根据 bdcdyh 获取土地性质，只处理 G 和 J 其他类型不处理
     *
     * @param bdcdyh bdcdyh
     * @return 土地性质名称
     */
    private String convertTdxz(String bdcdyh) {
        String dm = StringUtils.substring(bdcdyh, 12, 13);
        if (StringUtils.isNotBlank(dm)) {
            switch (dm) {
                case "G":
                    return "国有土地";
                case "J":
                    return "集体土地";
                default:
                    return "";
            }
        }
        return "";
    }

    /**
     * 处理错误信息
     *
     * @param msg msg
     * @return xml 错误信息
     */
    private String errorMsg(String msg) {
        return "<data><errormessage>" + msg + "</errormessage></data>";
    }

    /**
     * 处理房屋名称<br>
     * 1. 去除所有数字
     * 2. 去除｛号楼｝
     * 3. 截取前 10 位字符
     *
     * @param fwmc fwmc
     * @return 小区名称
     */
    private String dealFwmc(String fwmc) {
        if (StringUtils.isBlank(fwmc)) {
            return StringUtils.EMPTY;
        }
        // 去除数字
        String wszfwmc = fwmc.replaceAll("\\d+", "");
        // 去除 号楼
        String hlfwmc = wszfwmc.replaceAll("号楼", "");
        // 截取前 10 位
        return StringUtils.substring(hlfwmc, 0, 10);
    }
}
