package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcFdcq3GyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.ui.util.Constants;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/7/3 15:51
 * @description 组织套打数据
 */
@RestController

public class BdcDjbPdfPrintController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjbPdfPrintController.class);

    private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${print.pdf}")
    private String printPath;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcFdcq3GyxxFeignService bdcFdcq3GyxxFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;


    /**
     * 权利类型为4/6/8/27；
     *
     * @param listBdcQlxx 权利类型为Fdcq的数据信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream fdcqPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：1-4为1,5-8为2
        if (listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "fdcqOne.pdf";
        }
        if (Constants.MOULD < listBdcQlxx.size() && listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "fdcqTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(bdcFdcqDO.getXmid());
                //开始进行字典项转换,格式转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getDjlx(), zdMap.get(Constants.DJLX));
                String ghyt = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getGhyt(), zdMap.get(Constants.GHYT));
                String fwxz = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwxz(), zdMap.get(Constants.FWXZ));
                String fwjg = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwjg(), zdMap.get(Constants.FWJG));

                form.setField(Constants.BDCDYH, bdcFdcqDO.getBdcdyh());
                form.setField("zl", bdcFdcqDO.getZl());
                form.setField("slbh" + i, bdcFdcqDO.getSlbh());
                form.setField("fwsyqr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, bdcFdcqDO.getGyqk());
                form.setField(Constants.QLRLX + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, bdcFdcqDO.getDjyy());
                form.setField(Constants.TDSYQR + i, bdcFdcqDO.getTdsyqr());
                form.setField("dytdmj" + i, (null == bdcFdcqDO.getDytdmj()) ? "" : bdcFdcqDO.getDytdmj().toString());
                form.setField(Constants.FTTDMJ + i, (null == bdcFdcqDO.getFttdmj()) ? "" : bdcFdcqDO.getFttdmj().toString());
                form.setField("tdsyqssj" + i, (null == bdcFdcqDO.getTdsyqssj()) ? "" : dataFormat.format(bdcFdcqDO.getTdsyqssj()));
                form.setField("tdsyjssj" + i, (null == bdcFdcqDO.getTdsyjssj()) ? "" : dataFormat.format(bdcFdcqDO.getTdsyjssj()));
                form.setField("jyjg" + i, (null == bdcFdcqDO.getJyjg()) ? "" : bdcFdcqDO.getJyjg().toString());
                form.setField("ghyt" + i, ghyt);
                form.setField("fwxz" + i, fwxz);
                form.setField("fwjg" + i, fwjg);
                form.setField("szc" + i, bdcFdcqDO.getSzmyc());
                form.setField("zcs" + i, (null == bdcFdcqDO.getZcs()) ? "" : bdcFdcqDO.getZcs().toString());
                form.setField("jzmj" + i, (null == bdcFdcqDO.getJzmj()) ? "" : bdcFdcqDO.getJzmj().toString());
                form.setField("zyjzmj" + i, (null == bdcFdcqDO.getZyjzmj()) ? "" : bdcFdcqDO.getZyjzmj().toString());
                form.setField("ftjzmj" + i, (null == bdcFdcqDO.getFtjzmj()) ? "" : bdcFdcqDO.getFtjzmj().toString());
                form.setField("jgsj" + i, bdcFdcqDO.getJgsj());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == bdcFdcqDO.getDjsj()) ? "" : dataFormat.format(bdcFdcqDO.getDjsj()));
                form.setField("dbr" + i, bdcFdcqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(bdcFdcqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取房地产权模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("房地产权模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 权利类型为4/6/8/27；
     *
     * @param listBdcQlxx 权利类型为Fdcq的多幢
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream fdcqDzPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：1-4为1,5-8为2
        LOGGER.info("===>pdf模板路径:{}",printPath);
        if (listBdcQlxx.size() <= Constants.ONE) {
            pdfTemplate = printPath + "fdcqdzOne.pdf";
        }
        if (listBdcQlxx.size() <= Constants.TWO) {
            pdfTemplate = printPath + "fdcqdzTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(bdcFdcqDO.getXmid());
                List<BdcFdcqFdcqxmDO> fdcqxmList = bdcQllxFeignService.listFdcqXm(bdcFdcqDO.getQlid());
                //开始进行字典项转换,格式转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getDjlx(), zdMap.get(Constants.DJLX));
                form.setField(Constants.BDCDYH, bdcFdcqDO.getBdcdyh());
                form.setField("zl", bdcFdcqDO.getZl());
                form.setField("slbh" + i, bdcFdcqDO.getSlbh());
                form.setField("fwsyqr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, bdcFdcqDO.getGyqk());
                form.setField(Constants.QLRLX + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, bdcFdcqDO.getDjyy());
                form.setField(Constants.TDSYQR + i, bdcFdcqDO.getTdsyqr());
                form.setField("dytdmj" + i, (null == bdcFdcqDO.getDytdmj()) ? "" : bdcFdcqDO.getDytdmj().toString());
                form.setField(Constants.FTTDMJ + i, (null == bdcFdcqDO.getFttdmj()) ? "" : bdcFdcqDO.getFttdmj().toString());
                form.setField("qssj" + i, (null == bdcFdcqDO.getTdsyqssj()) ? "" : dataFormat.format(bdcFdcqDO.getTdsyqssj()));
                form.setField("jssj" + i, (null == bdcFdcqDO.getTdsyjssj()) ? "" : dataFormat.format(bdcFdcqDO.getTdsyjssj()));
                form.setField("jyjg" + i, (null == bdcFdcqDO.getJyjg()) ? "" : bdcFdcqDO.getJyjg().toString());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == bdcFdcqDO.getDjsj()) ? "" : dataFormat.format(bdcFdcqDO.getDjsj()));
                form.setField("dbr" + i, bdcFdcqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(bdcFdcqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
                if (CollectionUtils.isNotEmpty(fdcqxmList)) {
                    for (int j = 0; j < fdcqxmList.size(); j++) {
                        String ghyt = StringToolUtils.convertBeanPropertyValueOfZd(fdcqxmList.get(j).getGhyt(), zdMap.get(Constants.GHYT));
                        String fwjg = StringToolUtils.convertBeanPropertyValueOfZd(fdcqxmList.get(j).getFwjg(), zdMap.get(Constants.FWJG));

                        form.setField("xmmc" + i + j, fdcqxmList.get(j).getXmmc());
                        form.setField("zh" + i + j, fdcqxmList.get(j).getZh());
                        form.setField("zcs" + i + j, (null == fdcqxmList.get(j).getZcs()) ? "" : fdcqxmList.get(j).getZcs().toString());
                        form.setField("ghyt" + i + j, ghyt);
                        form.setField("fwjg" + i + j, fwjg);
                        form.setField("jzmj" + i + j, (null == fdcqxmList.get(j).getJzmj()) ? "" : fdcqxmList.get(j).getJzmj().toString());
                        form.setField("jgsj" + i + j, fdcqxmList.get(j).getJgsj());
                        form.setField("zts" + i + j, (null == fdcqxmList.get(j).getZts()) ? "" : fdcqxmList.get(j).getZts().toString());
                    }
                }
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取房地产权多幢模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("房地产权多幢模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 权利类型为3/5/7；获取登记簿建设用地使用权、宅基地使用权登记信息
     *
     * @param listBdcQlxx 权利类型为Jsyd_zjd的数据信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream jsydZjdPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：1-4为1,5-8为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "jsyd-zjdOne.pdf";
        }
        if (Constants.MOULD < listBdcQlxx.size() && listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "jsyd-zjdTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(bdcJsydsyqDO.getXmid());
                //开始进行字典项转换,格式转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(bdcJsydsyqDO.getDjlx(), zdMap.get(Constants.DJLX));

                form.setField(Constants.BDCDYH, bdcJsydsyqDO.getBdcdyh());
                form.setField("slbh" + i, bdcJsydsyqDO.getSlbh());
                form.setField("qlr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, bdcJsydsyqDO.getGyqk());
                form.setField(Constants.QLRLX + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, bdcJsydsyqDO.getDjyy());
                form.setField(Constants.SYQMJ + i, (null == bdcJsydsyqDO.getSyqmj()) ? "" : bdcJsydsyqDO.getSyqmj().toString());
                form.setField("syqxqssj" + i, (null == bdcJsydsyqDO.getSyqqssj()) ? "" : dataFormat.format(bdcJsydsyqDO.getSyqqssj()));
                form.setField("syqxjssj" + i, (null == bdcJsydsyqDO.getSyqjssj()) ? "" : dataFormat.format(bdcJsydsyqDO.getSyqjssj()));
                form.setField("qdjg" + i, (null == bdcJsydsyqDO.getQdjg()) ? "" : bdcJsydsyqDO.getQdjg().toString());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == bdcJsydsyqDO.getDjsj()) ? "" : dataFormat.format(bdcJsydsyqDO.getDjsj()));
                form.setField("dbr" + i, bdcJsydsyqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(bdcJsydsyqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取登记簿建设用地使用权、宅基地使用权模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("建设用地使用权、宅基地模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }


    /**
     * 权利类型为1/2 土地所有权登记簿信息
     *
     * @param listBdcQlxx 权利类型为tdsyq的数据信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream tdsyqPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：1-4为1,5-8为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "tdsyqOne.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);

            /*BaseFont bf = BaseFont.createFont(registerUiUrl+"/static/font/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont bf = BaseFont.createFont("STSong-Light",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            BaseFont bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);*/
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcTdsyqDO bdcTdsyqDO = (BdcTdsyqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(bdcTdsyqDO.getXmid());
                //开始进行字典项转换,格式转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(bdcTdsyqDO.getDjlx(), zdMap.get(Constants.DJLX));
                String dw = StringToolUtils.convertBeanPropertyValueOfZd(bdcTdsyqDO.getMjdw(), zdMap.get(Constants.MJDW));

                form.setField(Constants.BDCDYH, bdcTdsyqDO.getBdcdyh());
                form.setField("dw", dw);
                form.setField("slbh" + i, bdcTdsyqDO.getSlbh());
                form.setField("qlr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, bdcTdsyqDO.getGyqk());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, bdcTdsyqDO.getDjyy());
                form.setField("nyd" + i, (null == bdcTdsyqDO.getNydmj() ? "" : bdcTdsyqDO.getNydmj().toString()));
                form.setField("gd" + i, (null == bdcTdsyqDO.getGdmj() ? "" : bdcTdsyqDO.getGdmj().toString()));
                form.setField("ld" + i, (null == bdcTdsyqDO.getLdmj() ? "" : bdcTdsyqDO.getLdmj().toString()));
                form.setField("ct" + i, (null == bdcTdsyqDO.getCdmj() ? "" : bdcTdsyqDO.getCdmj().toString()));
                form.setField("qt" + i, (null == bdcTdsyqDO.getQtnydmj() ? "" : bdcTdsyqDO.getQtnydmj().toString()));
                form.setField("jsyd" + i, (null == bdcTdsyqDO.getJsydmj() ? "" : bdcTdsyqDO.getJsydmj().toString()));
                form.setField("wlyd" + i, (null == bdcTdsyqDO.getWlydmj() ? "" : bdcTdsyqDO.getWlydmj().toString()));
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == bdcTdsyqDO.getDjsj()) ? "" : dataFormat.format(bdcTdsyqDO.getDjsj()));
                form.setField("dbr" + i, bdcTdsyqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(bdcTdsyqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取土地所有权模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("土地所有权模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 权利类型为94 建筑物区分所有权业主共有部分登记信息, 包含共有信息
     *
     * @param listBdcQlxx 权利类型为94的数据信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * PdfContentByte under = stamper.getUnderContent(4);
     */
    public ByteArrayOutputStream gybfPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        if (listBdcQlxx.size() <= Constants.MOULDJZWGYONE) {
            pdfTemplate = printPath + "jzwgybfOne.pdf";
        }
        if (Constants.MOULDJZWGYONE < listBdcQlxx.size() && listBdcQlxx.size() <= Constants.MOULDJZWGYTWO) {
            pdfTemplate = printPath + "jzwgybfTwo.pdf";
        }

        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcFdcq3DO fdcq3DO = (BdcFdcq3DO) bdcQl;
                List<BdcFdcq3GyxxDO> bdcFdcq3GyxxDOList = bdcFdcq3GyxxFeignService.queryListBdcQlByXmid(fdcq3DO.getXmid());

                for (int j = 0; j < bdcFdcq3GyxxDOList.size(); j++) {
                    form.setField("qlr", this.getFdcq3Qlr(fdcq3DO.getXmid()));

                    BdcFdcq3GyxxDO fdcq3GyxxDO = bdcFdcq3GyxxDOList.get(j);
                    form.setField("ywh" + i, fdcq3DO.getSlbh());
                    form.setField("jgzwbh" + i, fdcq3GyxxDO.getJgzwbh());
                    form.setField("jgzwmc" + i, fdcq3GyxxDO.getJgzwmc());
                    form.setField("jgzwmj" + i, (null == fdcq3GyxxDO.getJgzwmj()) ? "" : fdcq3GyxxDO.getJgzwmj().toString());
                    form.setField(Constants.FTTDMJ + i, (null == fdcq3GyxxDO.getFttdmj()) ? "" : fdcq3GyxxDO.getFttdmj().toString());
                    form.setField("djsj" + i, (null == fdcq3DO.getDjsj()) ? "" : dataFormat.format(fdcq3DO.getDjsj()));
                    form.setField("dbr" + i, fdcq3DO.getDbr());
                    form.setField("fj" + i, StringUtils.deleteWhitespace(fdcq3GyxxDO.getFj()));
                    form.setField("dysj"+i, dysj);
                    form.setField("jbr"+i, userName);
                }
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取建筑物区分所有权业主共有部分模板失败！,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("建筑物区分所有权业主共有部分模板字段赋值失败!");
        }
        return byteArrayOutputStream;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return xmid 项目ID
     * @description 获取建筑物区分所有权业主共有部分登记权利人信息
     */
    private String getFdcq3Qlr(String xmid) {
        if(StringUtils.isBlank(xmid)) {
            return "";
        }
        return bdcFdcq3GyxxFeignService.getFdcq3Qlr(xmid);
    }

    /**
     * 权利类型为95；获取抵押使用权登记信息
     *
     * @param listBdcQlxx 权利类型为dyaq的数据信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream dyaPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：0-8为1
        if (listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "dyaOne.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcDyaqDO dyaqDO = (BdcDyaqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(dyaqDO.getXmid());
                //开始进行字典项转换,格式转换
                String dybdclx = StringToolUtils.convertBeanPropertyValueOfZd(dyaqDO.getDybdclx(), zdMap.get(Constants.DYBDCLX));
                String dyfs = StringToolUtils.convertBeanPropertyValueOfZd(dyaqDO.getDyfs(), zdMap.get(Constants.DYFS));
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(dyaqDO.getDjlx(), zdMap.get(Constants.DJLX));

                form.setField(Constants.BDCDYH, dyaqDO.getBdcdyh());
                form.setField("dylx", dybdclx);
                form.setField("slbh" + i, dyaqDO.getSlbh());
                form.setField("dyqr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("dyr" + i, bdcXmDO.getYwr());
                form.setField("dyfs" + i, dyfs);
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, dyaqDO.getDjyy());
                form.setField("zjjzwzl" + i, dyaqDO.getZjjzwzl());
                form.setField("zjjzwdyfw" + i, dyaqDO.getZjjzwdyfw());
                form.setField("bdbzzqse" + i, (null == dyaqDO.getBdbzzqse()) ? "" : dyaqDO.getBdbzzqse().toString());
                form.setField("zgzqe" + i, (null == dyaqDO.getZgzqe()) ? "" : dyaqDO.getZgzqe().toString());
                form.setField("dbfw" + i, dyaqDO.getDbfw());
                form.setField("zwlxqssj" + i, (null == dyaqDO.getZwlxqssj()) ? "" : dataFormat.format(dyaqDO.getZwlxqssj()));
                form.setField("zwlxjssj" + i, (null == dyaqDO.getZwlxjssj()) ? "" : dataFormat.format(dyaqDO.getZwlxjssj()));
                form.setField("jzzr" + i, getJzzrMc(dyaqDO.getJzzr()));
                form.setField("zgzqqdse" + i, (null == dyaqDO.getZgzqqdse()) ? "" : dyaqDO.getZgzqqdse().toString());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == dyaqDO.getDjsj()) ? "" : dataFormat.format(dyaqDO.getDjsj()));
                form.setField("dbr" + i, dyaqDO.getDbr());
                form.setField("zxdyywh" + i, dyaqDO.getZxdyywh());
                form.setField("zxdyyy" + i, dyaqDO.getZxdyyy());
                form.setField("zxdyyy" + i, dyaqDO.getZxdyyy());
                form.setField("zxdydjsj" + i, (null == dyaqDO.getZxdydjsj()) ? "" : dataFormat.format(dyaqDO.getZxdydjsj()));
                form.setField("zxdydbr" + i, dyaqDO.getZxdydbr());
                form.setField("fj" + i, StringToolUtils.deleteWhitespace(dyaqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取抵押使用权模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("抵押使用权模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 权利类型为15/17 海域海岛使用权
     *
     * @param listBdcQlxx
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream hysyqPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：0-8为1,9-16为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "hyhdOne.pdf";
        }
        if (Constants.MOULD < listBdcQlxx.size() && listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "hyhdTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcHysyqDO hysyqDO = (BdcHysyqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(hysyqDO.getXmid());
                //开始进行字典项转换,格式转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(hysyqDO.getDjlx(), zdMap.get(Constants.DJLX));

                form.setField(Constants.BDCDYH, hysyqDO.getBdcdyh());
                form.setField("slbh" + i, hysyqDO.getSlbh());
                form.setField("qlr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, hysyqDO.getGyqk());
                form.setField(Constants.QLRLX + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, hysyqDO.getDjyy());
                form.setField(Constants.SYQMJ + i, (null == hysyqDO.getSyqmj()) ? "" : hysyqDO.getSyqmj().toString());
                form.setField("syqqssj" + i, (null == hysyqDO.getSyqqssj()) ? "" : dataFormat.format(hysyqDO.getSyqqssj()));
                form.setField("syqjssj" + i, (null == hysyqDO.getSyqjssj()) ? "" : dataFormat.format(hysyqDO.getSyqjssj()));
                form.setField("syzje" + i, (null == hysyqDO.getSyzje()) ? "" : hysyqDO.getSyzje().toString());
                form.setField("syjbzyj" + i, hysyqDO.getSyjbzyj());
                form.setField("syjjnqk" + i, hysyqDO.getSyjjnqk());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == hysyqDO.getDjsj()) ? "" : dataFormat.format(hysyqDO.getDjsj()));
                form.setField("dbr" + i, hysyqDO.getDbr());
                form.setField("fj" + i, StringToolUtils.deleteWhitespace(hysyqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取海域海岛模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("海域海岛模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 构建筑物所有权  16/18/24/25/26/28
     *
     * @param listBdcQlxx 构建筑物所有权集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream gjzwsyqPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：1-4为1,5-8为2
        if (listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "gjzwsyqOne.pdf";
        }
        if (Constants.MOULD < listBdcQlxx.size() && listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "gjzwsyqTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcGjzwsyqDO gjzwsyqDO = (BdcGjzwsyqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(gjzwsyqDO.getXmid());
                //开始进行字典项转换,格式转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(gjzwsyqDO.getDjlx(), zdMap.get(Constants.DJLX));
                String gjzwlx = StringToolUtils.convertBeanPropertyValueOfZd(gjzwsyqDO.getGjzwlx(), zdMap.get(Constants.GJZWLX));

                form.setField(Constants.BDCDYH, gjzwsyqDO.getBdcdyh());
                form.setField("zl", gjzwsyqDO.getZl());
                form.setField("slbh" + i, gjzwsyqDO.getSlbh());
                form.setField("gjzwsyqr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, gjzwsyqDO.getGyqk());
                form.setField(Constants.QLRLX + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, gjzwsyqDO.getDjyy());
                form.setField("tdhysyqr" + i, gjzwsyqDO.getTdhysyqr());
                form.setField("tdhysymj" + i, (null == gjzwsyqDO.getTdhysymj()) ? "" : gjzwsyqDO.getTdhysymj().toString());
                form.setField("tdhysyqssj" + i, (null == gjzwsyqDO.getTdhysyqssj()) ? "" : dataFormat.format(gjzwsyqDO.getTdhysyqssj()));
                form.setField("tdhysyjssj" + i, (null == gjzwsyqDO.getTdhysyjssj()) ? "" : dataFormat.format(gjzwsyqDO.getTdhysyjssj()));
                form.setField("gjzwlx" + i, gjzwlx);
                form.setField("gjzwghyt" + i, gjzwsyqDO.getGjzwghyt());
                form.setField("gjzwmj" + i, (null == gjzwsyqDO.getGjzwmj()) ? "" : gjzwsyqDO.getGjzwmj().toString());
                form.setField("jgsj" + i, gjzwsyqDO.getJgsj());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == gjzwsyqDO.getDjsj()) ? "" : dataFormat.format(gjzwsyqDO.getDjsj()));
                form.setField("dbr" + i, gjzwsyqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(gjzwsyqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取构建筑物模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("构建筑物模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 异议登记信息  97
     *
     * @param listBdcQlxx 异议登记信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream yyPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        //判断使用哪个模板：0-8为1,9-16为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "yyOne.pdf";
        }
        if (Constants.MOULD < listBdcQlxx.size() && listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "yyTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcYyDO yyDO = (BdcYyDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(yyDO.getXmid());

                form.setField(Constants.BDCDYH, yyDO.getBdcdyh());
                form.setField("slbh" + i, yyDO.getSlbh());
                form.setField("sqr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("yysx" + i, yyDO.getYysx());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == yyDO.getDjsj()) ? "" : dataFormat.format(yyDO.getDjsj()));
                form.setField("dbr" + i, yyDO.getDbr());
                form.setField("zxyyywh" + i, yyDO.getZxyyywh());
                form.setField("zxyyyy" + i, yyDO.getZxyyyy());
                form.setField("zxdjsj" + i, (null == yyDO.getZxyydjsj()) ? "" : dataFormat.format(yyDO.getZxyydjsj()));
                form.setField("zxdbr" + i, yyDO.getZxyydbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(yyDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取异议登记模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("异议登记模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 预告登记信息  96
     *
     * @param listBdcQlxx 预告登记信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream ygPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：0-8为1,9-16为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "ygOne.pdf";
        }
        if (Constants.MOULD < listBdcQlxx.size() && listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "ygTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcYgDO ygDO = (BdcYgDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(ygDO.getXmid());
                //字典项转换
                String ygdjzl = StringToolUtils.convertBeanPropertyValueOfZd(ygDO.getYgdjzl(), zdMap.get(Constants.YGDJZL));
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(ygDO.getDjlx(), zdMap.get(Constants.DJLX));
                String ghyt = StringToolUtils.convertBeanPropertyValueOfZd(ygDO.getGhyt(), zdMap.get(Constants.GHYT));
                String fwxz = StringToolUtils.convertBeanPropertyValueOfZd(ygDO.getFwxz(), zdMap.get(Constants.FWXZ));

                form.setField(Constants.BDCDYH, ygDO.getBdcdyh());
                form.setField("zl", ygDO.getZl());
                form.setField("slbh" + i, ygDO.getSlbh());
                form.setField("qlr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("ywr" + i, bdcXmDO.getYwr());
                form.setField("ywrzjzl" + i, bdcXmDO.getYwrzjzl());
                form.setField("ywrzjh" + i, bdcXmDO.getYwrzjh());
                form.setField("ygdjzl" + i, ygdjzl);
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, ygDO.getDjyy());
                form.setField(Constants.TDSYQR + i, ygDO.getTdsyqr());
                form.setField("ghyt" + i, ghyt);
                form.setField("fwxz" + i, fwxz);
                form.setField("szc" + i, (null == ygDO.getSzc()) ? "" : ygDO.getSzc().toString());
                form.setField("zcs" + i, (null == ygDO.getZcs()) ? "" : ygDO.getZcs().toString());
                form.setField("jzmj" + i, (null == ygDO.getJzmj()) ? "" : ygDO.getJzmj().toString());
                form.setField("qdjg" + i, (null == ygDO.getQdjg()) ? "" : ygDO.getQdjg().toString());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == ygDO.getDjsj()) ? "" : dataFormat.format(ygDO.getDjsj()));
                form.setField("dbr" + i, ygDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(ygDO.getFj()));
                form.setField("dbfw" + i, StringUtils.isBlank(ygDO.getDbfw()) ? "" : ygDO.getDbfw());
                form.setField("jzzr" + i, getJzzrMc(ygDO.getJzzr()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取预告登记模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("预告登记模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    private String getJzzrMc(Integer jzzr) {
        if(null == jzzr) {
            return "";
        }

        if(CommonConstantUtils.SF_S_DM.equals(jzzr)) {
            return "是";
        } else {
            return "否";
        }
    }

    /**
     * 查封登记信息  98
     *
     * @param listBdcQlxx 查封登记信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream cfPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：0-16为1,16-24为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "cfOne.pdf";
        } else if (listBdcQlxx.size() <= Constants.MOULDTWO) {
            pdfTemplate = printPath + "cfTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcCfDO cfDO = (BdcCfDO) bdcQl;
                //字典项转换
                String cflx = StringToolUtils.convertBeanPropertyValueOfZd(cfDO.getCflx(), zdMap.get(Constants.CFLX));

                form.setField(Constants.BDCDYH, cfDO.getBdcdyh());
                form.setField("slbh" + i, cfDO.getSlbh());
                form.setField("cfjg" + i, cfDO.getCfjg());
                form.setField("cflx" + i, cflx);
                form.setField("cfwj" + i, cfDO.getCfwj());
                form.setField("cfwh" + i, cfDO.getCfwh());
                form.setField("cfqssj" + i, (null == cfDO.getCfqssj()) ? "" : dataFormat.format(cfDO.getCfqssj()));
                form.setField("cfjssj" + i, (null == cfDO.getCfjssj()) ? "" : dataFormat.format(cfDO.getCfjssj()));
                form.setField("cffw" + i, cfDO.getCffw());
                form.setField("djsj" + i, (null == cfDO.getDjsj()) ? "" : dataFormat.format(cfDO.getDjsj()));
                form.setField("dbr" + i, cfDO.getDbr());
                form.setField("jfywh" + i, cfDO.getJfywh());
                form.setField("jfjg" + i, cfDO.getJfjg());
                form.setField("jfwj" + i, cfDO.getJfwj());
                form.setField("jfwh" + i, cfDO.getJfwh());
                form.setField("jfdjsj" + i, (null == cfDO.getJfdjsj()) ? "" : dataFormat.format(cfDO.getJfdjsj()));
                form.setField("jfdbr" + i, cfDO.getJfdbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(cfDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取查封模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("查封模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 地役登记信息  19
     *
     * @param listBdcQlxx 地役登记信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream dyiPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：0-8为1,9-16为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "dyiOne.pdf";
        } else if(listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "dyiTwo.pdf";

        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcDyiqDO dyiqDO = (BdcDyiqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(dyiqDO.getXmid());
                //字典项转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(dyiqDO.getDjlx(), zdMap.get(Constants.DJLX));

                form.setField(Constants.BDCDYH, dyiqDO.getGydbdcdyh());
                form.setField("xydzl", dyiqDO.getXydzl());
                form.setField("slbh" + i, dyiqDO.getSlbh());
                form.setField("dyqr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrlx());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gydqlr" + i, bdcXmDO.getYwr());
                form.setField("gydzjzl" + i, bdcXmDO.getYwrzjzl());
                form.setField("gydqlrzjh" + i, bdcXmDO.getYwrzjh());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, dyiqDO.getDjyy());
                form.setField("dyqnr" + i, dyiqDO.getDyqnr());
                form.setField("qlqssj" + i, (null == dyiqDO.getQlqssj()) ? "" : dataFormat.format(dyiqDO.getQlqssj()));
                form.setField("qljssj" + i, (null == dyiqDO.getQljssj()) ? "" : dataFormat.format(dyiqDO.getQljssj()));
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == dyiqDO.getDjsj()) ? "" : dataFormat.format(dyiqDO.getDjsj()));
                form.setField("dbr" + i, dyiqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(dyiqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取地役模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("地役模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 林权登记信息  10/11/12
     *
     * @param listBdcQlxx 林权登记信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream lqPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：1-4为1,5-8为2
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "lqOne.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcLqDO lqDO = (BdcLqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(lqDO.getXmid());
                //字典项转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getDjlx(), zdMap.get(Constants.DJLX));
                String ldsyqxz = StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getLdsyqxz(), zdMap.get(Constants.LDSYQXZ));
                String lz = StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getLz(), zdMap.get(Constants.LZ));
                String qy = StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getQy(), zdMap.get(Constants.QY));

                form.setField(Constants.BDCDYH, lqDO.getBdcdyh());
                form.setField("fbf", lqDO.getFbf());
                form.setField("slbh" + i, lqDO.getSlbh());
                form.setField("qlr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("ldgyqk" + i, lqDO.getGyqk());
                form.setField(Constants.QLRLX + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, lqDO.getDjyy());
                form.setField(Constants.SYQMJ + i, (null == lqDO.getSyqmj()) ? "" : lqDO.getSyqmj().toString());
                form.setField("ldsyqssj" + i, (null == lqDO.getLdsyqssj()) ? "" : dataFormat.format(lqDO.getLdsyqssj()));
                form.setField("ldsyjssj" + i, (null == lqDO.getLdsyjssj()) ? "" : dataFormat.format(lqDO.getLdsyjssj()));
                form.setField("ldsyqxz" + i, ldsyqxz);
                form.setField("sllmsyqr" + i, lqDO.getSllmsyqr1());
                form.setField("lmsyqr" + i, lqDO.getSllmsyqr2());
                form.setField("zysz" + i, lqDO.getZysz());
                form.setField("zs" + i, (null == lqDO.getZs()) ? "" : lqDO.getZs().toString());
                form.setField("lz" + i, lz);
                form.setField("qy" + i, qy);
                form.setField("zlnd" + i, (null == lqDO.getZlnd()) ? "" : lqDO.getZlnd().toString());
                form.setField("xdm" + i, lqDO.getXdm());
                form.setField("lb" + i, lqDO.getLb());
                form.setField("xb" + i, lqDO.getXb());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == lqDO.getDjsj()) ? "" : dataFormat.format(lqDO.getDjsj()));
                form.setField("dbr" + i, lqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(lqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取林权模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("林权模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 土地承包经营权、农用地的其他使用权 登记信息  10/11/12
     *
     * @param listBdcQlxx 土地承包经营权登记信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream tdcbjyNydPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：0-6为1
        LOGGER.info("===>pdf模板路径:{}",printPath);
        if (listBdcQlxx.size() <= Constants.MOULD) {
            pdfTemplate = printPath + "tdcbnydOne.pdf";
        } else if (listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "tdcbnydTwo.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
            //中文字体
            String fontPath = printPath + "simsun.ttc,1";
            BaseFont baseFont = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(baseFont);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcTdcbnydsyqDO tdcbnydsyqDO = (BdcTdcbnydsyqDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(tdcbnydsyqDO.getXmid());
                //字典项转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getDjlx(), zdMap.get(Constants.DJLX));
                String tdsyqxz = StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getTdsyqxz(), zdMap.get(Constants.TDSYQXZ));
                String syttxz = StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getSyttlx(), zdMap.get(Constants.SYTTLX));
                String yzfs = StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getYzyfs(), zdMap.get(Constants.YZYFS));

                form.setField(Constants.BDCDYH, tdcbnydsyqDO.getBdcdyh());
                form.setField("fbf", tdcbnydsyqDO.getFbfmc());
                form.setField("slbh" + i, tdcbnydsyqDO.getSlbh());
                form.setField("qlr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, tdcbnydsyqDO.getGyqk());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, tdcbnydsyqDO.getDjyy());
                form.setField("cbmj" + i, (null == tdcbnydsyqDO.getCbmj()) ? "" : tdcbnydsyqDO.getCbmj().toString());
                form.setField("cbqssj" + i, (null == tdcbnydsyqDO.getCbqssj()) ? "" : dataFormat.format(tdcbnydsyqDO.getCbqssj()));
                form.setField("cbjssj" + i, (null == tdcbnydsyqDO.getCbjssj()) ? "" : dataFormat.format(tdcbnydsyqDO.getCbjssj()));
                form.setField("tdxz" + i, tdsyqxz);
                form.setField("syttxz" + i, syttxz);
                form.setField("yzfs" + i, yzfs);
                form.setField("cyzl" + i, tdcbnydsyqDO.getCyzl());
                form.setField("syzcl" + i, (null == tdcbnydsyqDO.getSyzcl()) ? "" : tdcbnydsyqDO.getSyzcl().toString());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == tdcbnydsyqDO.getDjsj()) ? "" : dataFormat.format(tdcbnydsyqDO.getDjsj()));
                form.setField("dbr" + i, tdcbnydsyqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(tdcbnydsyqDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取土地承包经营权模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("土地承包经营权模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * 其他相关权利 登记信息  10/11/12
     *
     * @param listBdcQlxx 其他相关权利登记信息集合
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ByteArrayOutputStream qtxgqlPdf(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //判断使用哪个模板：0-6为1
        if (listBdcQlxx.size() <= Constants.MOULD_LOW) {
            pdfTemplate = printPath + "qtxgOne.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcQtxgqlDO qtxgqlDO = (BdcQtxgqlDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(qtxgqlDO.getXmid());
                //字典项转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(qtxgqlDO.getDjlx(), zdMap.get(Constants.DJLX));
                String qllx = StringToolUtils.convertBeanPropertyValueOfZd(qtxgqlDO.getQllx(), zdMap.get(Constants.QLLX));

                form.setField(Constants.BDCDYH, qtxgqlDO.getBdcdyh());
                form.setField("slbh" + i, qtxgqlDO.getSlbh());
                form.setField("qllx" + i, qllx);
                form.setField("qlr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, qtxgqlDO.getGyqk());
                form.setField(Constants.QLRLX + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, qtxgqlDO.getDjyy());
                form.setField("qlqssj" + i, (null == qtxgqlDO.getQlqssj()) ? "" : dataFormat.format(qtxgqlDO.getQlqssj()));
                form.setField("cbjssj" + i, (null == qtxgqlDO.getQljssj()) ? "" : dataFormat.format(qtxgqlDO.getQljssj()));
                form.setField("qsfs" + i, qtxgqlDO.getQsfs());
                form.setField("sylx" + i, qtxgqlDO.getSylx());
                form.setField("qsl" + i, (null == qtxgqlDO.getQsl()) ? "" : qtxgqlDO.getQsl().toString());
                form.setField("qsyt" + i, qtxgqlDO.getQsyt());
                form.setField("kcmj" + i, (null == qtxgqlDO.getKcmj()) ? "" : qtxgqlDO.getKcmj().toString());
                form.setField("kckz" + i, qtxgqlDO.getKckz());
                form.setField("kcfs" + i, qtxgqlDO.getKcfs());
                form.setField("scgm" + i, qtxgqlDO.getScgm());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == qtxgqlDO.getDjsj()) ? "" : dataFormat.format(qtxgqlDO.getDjsj()));
                form.setField("dbr" + i, qtxgqlDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(qtxgqlDO.getFj()));
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取其他相关权利权模板失败,模板位置:{}",pdfTemplate);
        } catch (DocumentException e) {
            LOGGER.error("其他相关权利模板字段赋值失败");
        }
        return byteArrayOutputStream;
    }

    /**
     * @param xmid 项目ID
     * @return BdcXmDO 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目信息
     */
    public BdcXmDO queryBdcXm(String xmid) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        if (StringUtils.isBlank(xmid)) {
            return bdcXmDO;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return bdcXmDO;
        }
        return bdcXmDOList.get(0);
    }

    public void pdfTest(List<BdcQl> listBdcQlxx, HttpServletResponse response,String userName) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String pdfTemplate = "";

        //判断使用哪个模板：0-8为1,9-16为2
        if (listBdcQlxx.size() <= Constants.MOULDONE) {
            pdfTemplate = printPath + "ygOne.pdf";
        }
        try {
            PdfReader reader = new PdfReader(pdfTemplate);
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
//            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //取出所有字段;为字段赋值
            AcroFields form = stamper.getAcroFields();
//            form.addSubstitutionFont(bf);
            String dysj = DateUtils.formateTime(new Date(),DateUtils.DATE_FORMATYMDHM);
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcYgDO ygDO = (BdcYgDO) bdcQl;
                BdcXmDO bdcXmDO = queryBdcXm(ygDO.getXmid());
                //开始进行字典项转换,格式转换

                form.setField("qlr", bdcXmDO.getQlr());
                form.setField("zjh", bdcXmDO.getQlrzjh());
                form.setField("cqzh", bdcXmDO.getBdcqzh());
                form.setField("ghyt", "教育、医疗、卫生、科研");
                form.setField("fj", ygDO.getFj());
                form.setField("dysj"+i, dysj);
                form.setField("jbr"+i, userName);


            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("读取模板失败");
        } catch (DocumentException e) {
            LOGGER.error("字段赋值失败");
        }
        response.setContentType(Constants.APPLICATION);
        response.setContentLength(byteArrayOutputStream.size());
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            byteArrayOutputStream.writeTo(out);
            out.flush();
        } catch (IOException e) {
            LOGGER.error("关闭流失败");
        } finally {
            try {
                out.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                LOGGER.error("finally关闭流失败");
            }
        }
    }


}
