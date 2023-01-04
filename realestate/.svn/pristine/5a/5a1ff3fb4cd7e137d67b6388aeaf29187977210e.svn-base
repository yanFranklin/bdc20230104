package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzYwxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzYwxxDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxxYsj;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzYwxxService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxYsjService;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.ISO7064Util;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;


/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  生成元数据xml
 */
@Service
public class BdcDzzzZzxxYsjServiceImpl implements BdcDzzzZzxxYsjService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String ELEMENT = "ofd:CuslomData";

    @Autowired
    BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzYwxxMapper bdcDzzzYwxxMapper;
    @Autowired
    private BdcDzzzYwxxService bdcDzzzYwxxService;

    /*生成元数据xml*/
    @Override
    public String createBdcDzzzXml(BdcDzzzZzxx bdcDzzzZzxx) {
        String xml = "";
        if (null != bdcDzzzZzxx) {
            //解决传入json 不动产单元号可能存在空格问题
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcdyh())) {
                bdcDzzzZzxx.setBdcdyh(StringUtils.deleteWhitespace(bdcDzzzZzxx.getBdcdyh()));
            }

            List<BdcDzzzYwxxDo> listYwxx = bdcDzzzYwxxMapper.queryYwxxByZzid(bdcDzzzZzxx.getZzid());
            if (CollectionUtils.isNotEmpty(listYwxx)) {
                bdcDzzzYwxxService.getZzxxFromYwxx(listYwxx.get(0), bdcDzzzZzxx);
            }

            Document document = createFwbdcXml(bdcDzzzZzxx);
            if (null != document) {
                xml = document.asXML();
            }
        }

        return xml;
    }

    private Document createFwbdcXml(BdcDzzzZzxx bdcDzzzZzxx) {
        Document document = null;
        try {
            document = DocumentHelper.createDocument();
            document.setXMLEncoding("UTF-8");
            Element root = document.addElement("XMI");
            root.addNamespace("ofd", "org.omg.ofd").addAttribute("xmi.version", "1.0").addAttribute("createtime", PublicUtil.convertDateToStr2(DateUtil.now()));
            Element content = root.addElement("ofd:CuslomDatas");
            //证照id
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzid())) {
                Element cuslomData1 = content.addElement(ELEMENT);
                cuslomData1.addAttribute("name", "ZZID");
                cuslomData1.addText(bdcDzzzZzxx.getZzid());
            }
            //证书id
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZsid())) {
                Element cuslomData1 = content.addElement(ELEMENT);
                cuslomData1.addAttribute("name", "ZSID");
                cuslomData1.addText(bdcDzzzZzxx.getZsid());
            }
            //证照名称
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzmc())) {
                Element cuslomData1 = content.addElement(ELEMENT);
                cuslomData1.addAttribute("name", "ZZMC");
                cuslomData1.addText(bdcDzzzZzxx.getZzmc());
            }
            //证照类型代码
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzlxdm())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZLXDM");
                cuslomData.addText(bdcDzzzZzxx.getZzlxdm());
            }
            //证照编号
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZBH");
                cuslomData.addText(bdcDzzzZzxx.getZzbh());
            }
            //证照标识
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbs())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZBS");
                cuslomData.addText(bdcDzzzZzxx.getZzbs());
            }
            //证照颁发机构
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjg())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZBFJG");
                cuslomData.addText(bdcDzzzZzxx.getZzbfjg());
            }
            //证照颁发机构代码
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjgdm())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZBFJGDM");
                cuslomData.addText(bdcDzzzZzxx.getZzbfjgdm());
            }
            //证照颁发日期
            if (null != bdcDzzzZzxx.getZzbfrq()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getZzbfrq());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZBFRQ");
                cuslomData.addText(shijian);
            }
            //持证主体
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzzt())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "CZZT");
                cuslomData.addText(bdcDzzzZzxx.getCzzt());
            }
            //持证主体代码
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdm())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "CZZTDM");
                cuslomData.addText(bdcDzzzZzxx.getCzztdm());
            }
            //持证主体代码类型
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlx())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "CZZTDMLX");
                cuslomData.addText(bdcDzzzZzxx.getCzztdmlx());
            }
            //持证主体代码类型代码
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlxdm())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "CZZTDMLXDM");
                cuslomData.addText(bdcDzzzZzxx.getCzztdmlxdm());
            }
            //证照有效期起始日期
            if (null != bdcDzzzZzxx.getZzyxqqsrq()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getZzyxqqsrq());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZYXQQSRQ");
                cuslomData.setText(shijian);
            }
            //证照有效期截止日期
            if (null != bdcDzzzZzxx.getZzyxqjzrq()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getZzyxqjzrq());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZYXQJZRQ");
                cuslomData.setText(shijian);
            }
            //证照签章人
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzr())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZQZR");
                cuslomData.addText(bdcDzzzZzxx.getZzqzr());
            }
            //证照签章时间
            if (null != bdcDzzzZzxx.getZzqzsj()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getZzqzsj());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZQZSJ");
                cuslomData.addText(shijian);
            }
            //证照签章名称
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzmc())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "ZZQZMC");
                cuslomData.addText(bdcDzzzZzxx.getZzqzmc());
            }
            //创建时间
            if (null != bdcDzzzZzxx.getCjsj()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getCjsj());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "CJSJ");
                cuslomData.addText(shijian);
            }
            //加注件制作时间
            if (null != bdcDzzzZzxx.getJzjzzsj()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getJzjzzsj());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "JZJZZSJ");
                cuslomData.addText(shijian);
            }
            //加注件制作者
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getJzjzzz())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "JZJZZZ");
                cuslomData.addText(bdcDzzzZzxx.getJzjzzz());
            }
            //加注件制作事由
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getJzjzzsy())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "JZJZZSY");
                cuslomData.addText(bdcDzzzZzxx.getJzjzzsy());
            }
            //加注件有限期截止时间
            if (null != bdcDzzzZzxx.getJzjyxqjzsj()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getJzjyxqjzsj());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "JZJYXQJZSJ");
                cuslomData.addText(shijian);
            }

            //不动产权证号
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcqzh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_BDCQZH");
                cuslomData.addText(bdcDzzzZzxx.getBdcqzh());
            }
            //证号流水号
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZhlsh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_ZHLSH");
                cuslomData.addText(bdcDzzzZzxx.getZhlsh());
            }
            //年份
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getNf())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_NF");
                cuslomData.addText(bdcDzzzZzxx.getNf());
            }
            //编号
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYzh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_YZH");
                cuslomData.addText(bdcDzzzZzxx.getYzh());
            }
            //权利其他状况
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlqtzk())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_QLQTZK");
                cuslomData.addText(bdcDzzzZzxx.getQlqtzk());
            }
            //单位代码
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getDwdm())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_DWDM");
                cuslomData.addText(bdcDzzzZzxx.getDwdm());
            }
            //省区市简称
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSqsjc())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_SQSJC");
                cuslomData.addText(bdcDzzzZzxx.getSqsjc());
            }
            //所在市县全称
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSzsxqc())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_SZSXQC");
                cuslomData.addText(bdcDzzzZzxx.getSzsxqc());
            }
            //区分记录是证明还是证书
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZstype())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_ZSTYPE");
                cuslomData.addText(bdcDzzzZzxx.getZstype());
            }

            //发证日期
            if (null != bdcDzzzZzxx.getFzrq()) {
                String shijian = PublicUtil.convertDateToStr2(bdcDzzzZzxx.getFzrq());
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_FZRQ");
                cuslomData.addText(shijian);
            }
            //附记
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getFj())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_FJ");
                cuslomData.addText(bdcDzzzZzxx.getFj());
            }
            //不动产单元号
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcdyh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_BDCDYH");
                cuslomData.addText(bdcDzzzZzxx.getBdcdyh());
            }
            //坐落
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZl())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_ZL");
                cuslomData.addText(bdcDzzzZzxx.getZl());
            }
            //证明权利或事项
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZmqlsx())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_ZMQLSX");
                cuslomData.addText(bdcDzzzZzxx.getZmqlsx());
            }
            //共有情况
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getGyqk())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_GYQK");
                cuslomData.addText(bdcDzzzZzxx.getGyqk());
            }
            //权利类型
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQllx())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_QLLX");
                cuslomData.addText(bdcDzzzZzxx.getQllx());
            }

            //权利性质
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlxz())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_QLXZ");
                cuslomData.addText(bdcDzzzZzxx.getQlxz());
            }
            //用途
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYt())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_YT");
                cuslomData.addText(bdcDzzzZzxx.getYt());
            }
            //面积
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getMj())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_MJ");
                cuslomData.addText(bdcDzzzZzxx.getMj());
            }
            //使用期限
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSyqx())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_SYQX");
                cuslomData.addText(bdcDzzzZzxx.getSyqx());
            }
            //权利人
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlr())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_QLR");
                cuslomData.addText(bdcDzzzZzxx.getQlr());
            }
            //权利人证件种类
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlrzjzl())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_QLRZJZL");
                cuslomData.addText(bdcDzzzZzxx.getQlrzjzl());
            }
            //权利人证件号
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlrzjh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_QLRZJH");
                cuslomData.addText(bdcDzzzZzxx.getQlrzjh());
            }
            //义务人
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYwr())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_YWR");
                cuslomData.addText(bdcDzzzZzxx.getYwr());
            }
            //义务人证件种类
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYwrzjzl())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_YWRZJZL");
                cuslomData.addText(bdcDzzzZzxx.getYwrzjzl());
            }
            //义务人证件号
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYwrzjh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_YWRZJH");
                cuslomData.addText(bdcDzzzZzxx.getYwrzjh());
            }
            //项目id
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYwh())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_YWH");
                cuslomData.addText(bdcDzzzZzxx.getYwh());
            }
            //其他
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQt())) {
                Element cuslomData = content.addElement(ELEMENT);
                cuslomData.addAttribute("name", "KZ_QT");
                cuslomData.addText(bdcDzzzZzxx.getQt());
            }

        } catch (Exception e) {
            logger.error("生成元数据XML异常", e);

        }
        return document;
    }


    /*元数据xml解析到实体类*/
    @Override
    public BdcDzzzZzxx analyzeBdcDzzzXml(BdcDzzzZzxxDO bdcDzzzZzxxDO) {
        BdcDzzzZzxx bdcDzzzZzxx = null;
        StrBuilder strBuilder = new StrBuilder();
        if (null != bdcDzzzZzxxDO) {
            bdcDzzzZzxx = new BdcDzzzZzxx();
            Document doc = null;
            try {

                // 下面的是通过解析xml字符串的
                // 将字符串转为XML
                doc = DocumentHelper.parseText("");
                // 获取根节点XML(rootElt.getName())
                Element rootElt = doc.getRootElement();

                //获取元数据CuslomDatas节点
                Iterator cuslomDatas = rootElt.elementIterator("CuslomDatas");

                strBuilder.append("{");
                while (cuslomDatas.hasNext()) {
                    Element ofd = (Element) cuslomDatas.next();
                    //获取ABK节点下的所有节点
                    Iterator f = ofd.elementIterator();
                    while (f.hasNext()) {
                        Element itemAtr = (Element) f.next();
                        //获取需要的数据
                        itemAtr.elementText("");
                        String name = itemAtr.attributeValue("name");
                        String value = "";
                        if (StringUtils.equals(name, "ZZBFRQ") || StringUtils.equals(name, "ZZYXQQSRQ") ||
                                StringUtils.equals(name, "ZZYXQJZRQ") || StringUtils.equals(name, "ZZQZSJ") ||
                                StringUtils.equals(name, "CJSJ") || StringUtils.equals(name, "KZ_CZRQ") ||
                                StringUtils.equals(name, "KZ_FZRQ") || StringUtils.equals(name, "JZJZZSJ") || StringUtils.equals(name, "JZJYXQJZSJ")) {
                            value = PublicUtil.dateToStamp(PublicUtil.convertStrTodate(itemAtr.getTextTrim()));
                        } else {
                            value = itemAtr.getTextTrim();
                        }
                        strBuilder.append("\"").append(name).append("\":\"").append(value).append("\",");
                    }
                }

                strBuilder.deleteCharAt(strBuilder.length() - 1);
                strBuilder.append("}");

                BdcDzzzZzxxYsj bdcDzzzZzxxYsj = JSON.parseObject(strBuilder.toString(), BdcDzzzZzxxYsj.class);

                bdcDzzzZzxx = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxYsj(bdcDzzzZzxxYsj, bdcDzzzZzxx);
                bdcDzzzZzxx = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxDO(bdcDzzzZzxxDO, bdcDzzzZzxx);
            } catch (DocumentException e) {
                logger.error("解析元数据XML,xml文件读取异常", e);

            } catch (Exception e) {
                logger.error("解析元数据XML异常", e);

            }
        }

        return bdcDzzzZzxx;
    }

    /**
     * @param bdcDzzzZzxx 证照信息
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn zzbsStrbud 加密后的证照标识
     * @description 加密计算证照标识
     */

    @Override
    public synchronized String encryptCalculateZzbs(BdcDzzzZzxx bdcDzzzZzxx) {
        /*生成证照标识*/
        StrBuilder zzbsStrbud = new StrBuilder();
        /*生成证照标识校验位*/
        StrBuilder jywStrbud = new StrBuilder();
        //电子证照根代码（电子证照固定取值）
        zzbsStrbud.append("1.2.156.3005.2");
        zzbsStrbud.append(".");
        //证照类型代码
        zzbsStrbud.append(bdcDzzzZzxx.getZzlxdm());
        jywStrbud.append(bdcDzzzZzxx.getZzlxdm());
        zzbsStrbud.append(".");
        //证照颁发机构代码
        zzbsStrbud.append(bdcDzzzZzxx.getZzbfjgdm());
        jywStrbud.append(bdcDzzzZzxx.getZzbfjgdm());
        zzbsStrbud.append(".");
        //流水号（可使用证照编号或自行确定）
        zzbsStrbud.append(bdcDzzzZzxx.getZzlsh());
        jywStrbud.append(bdcDzzzZzxx.getZzlsh());
        zzbsStrbud.append(".");
        //版本号
        zzbsStrbud.append("001");
        jywStrbud.append("001");
        zzbsStrbud.append(".");
        //校验位 采用ISO 7064, MOD 37,36 混合系统校验
        //计算校验字符 不含校验字符的字符串
        try {
            String jyzf = ISO7064Util.computeCheckCharacter(jywStrbud.toString(), ISO7064Util.Designation.ISO_7064_MOD_37_HYBRID_36);
            if (StringUtils.isEmpty(jyzf)) {
                return jyzf;
            }
            zzbsStrbud.append(jyzf);

        } catch (Exception e) {
            logger.error("生成证照标识异常", e);
            return null;
        }
        return zzbsStrbud.toString();
    }


    @Override
    public BdcDzzzZzxxYsj convertYsjXmlToZzxxYsj(String ysjxml) {
        BdcDzzzZzxxYsj bdcDzzzZzxxYsj = null;
        try {
            Document doc = DocumentHelper.parseText(ysjxml);
            bdcDzzzZzxxYsj = new BdcDzzzZzxxYsj();
            Element root = doc.getRootElement();
            Element element = root.element("CuslomDatas");
            for (Iterator it = element.elementIterator(); it.hasNext(); ) {
                Element element1 = (Element) it.next();
                Field field = bdcDzzzZzxxYsj.getClass().getDeclaredField(element1.attributeValue("name"));
                field.setAccessible(true);
                if (field.getGenericType().toString().equals("class java.util.Date")) {
                    field.set(bdcDzzzZzxxYsj, PublicUtil.convertStrTodate(element1.getText()));
                    continue;
                }
                field.set(bdcDzzzZzxxYsj, element1.getText());
            }
        } catch (DocumentException e) {
            logger.error("dzzzDownloadFile.convertYsjXmlToZzxxYsj:DocumentException", e);
        } catch (IllegalAccessException e) {
            logger.error("dzzzDownloadFile.convertYsjXmlToZzxxYsj:IllegalAccessException", e);
        } catch (NoSuchFieldException e) {
            logger.error("dzzzDownloadFile.convertYsjXmlToZzxxYsj:NoSuchFieldException", e);
        }

        return bdcDzzzZzxxYsj;
    }

    @Override
    public BdcDzzzZzxxYsj getYsjByZzxxDo(BdcDzzzZzxxDO bdcDzzzZzxxDO) {
        BdcDzzzZzxxYsj bdcDzzzZzxxYsj = new BdcDzzzZzxxYsj();
        if (null != bdcDzzzZzxxDO) {
            BdcDzzzZzxx bdcDzzzZzxx = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxDO(bdcDzzzZzxxDO, null);
            bdcDzzzZzxxYsj = bdcDzzzZzxxService.getBdcDzzzZzxxYsjFromBdcDzzzZzxx(bdcDzzzZzxxYsj, bdcDzzzZzxx);
        }
        return bdcDzzzZzxxYsj;
    }
}
