package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.QRcodeUtils;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/rest/v1.0/print")
public class TestController {
    @Autowired
    PdfController pdfController;

    @Autowired
    QRcodeUtils qRcodeUtils;

    @Value("${print.path}")
    private String path;

    @GetMapping("/{modelname}/pdf/{size}")
    public void exportSczmdPdf(HttpServletResponse response, @PathVariable("size") int size, @PathVariable("modelname") String modelname) throws IOException {
        OfficeExportDTO officeExportDTO = this.getOfficeExportDTO(size);
        officeExportDTO.setModelName(path + modelname + ".docx");
        pdfController.exportPdf(response, officeExportDTO);
    }

    @GetMapping("/qrcode")
    public void qrcode(HttpServletResponse response) throws IOException {
        QRcodeUtils.encoderQRCode("12312", path + "erm.png", response);
    }

    @GetMapping("/pt")
    public void exportSczmdPdf(HttpServletResponse response) {
        try {
            OfficeUtil.convertDocxToPDF("", "C:\\GTIS\\print\\temp\\5AKB0515D3QK003J.docx", "C:\\GTIS\\print\\temp\\222.pdf", OfficeUtil.getFontMapper("C:\\GTIS\\print"), "", "");
//            convert2Html("C:\\GTIS\\print\\temp\\111.doc","C:\\GTIS\\print\\temp\\1.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/sczmd/word/{size}")
    public void exportSczmdWord(HttpServletResponse response, @PathVariable("size") int size) throws IOException {
        pdfController.exportWord(response, this.getOfficeExportDTO(size));
    }

    @GetMapping("/{modelname}/pdf2/{size}")
    public void exportSczmdWord(HttpServletResponse response, @PathVariable("size") int size, @PathVariable("modelname") String modelname) {
        OfficeExportDTO officeExportDTO = this.getOfficeExportDTO2(size);
        officeExportDTO.setModelName(path + modelname + ".docx");
        pdfController.exportPdf(response, officeExportDTO);
    }

    // /zfxx/#{bdcdyh}/#{xmid}/xml?bdcdyh=#{bdcdyh}
    @GetMapping("/zfxx/{bdcdyh}/{xmid}/xml")
    public Map<String, Object> testPrintUrl(@PathVariable("bdcdyh") String bdcdyh,
                                            @PathVariable("xmid") String xmid,
                                            @RequestParam("bdcdyh") String bdcdyh2) {
        System.out.println("bdcdyh:" + bdcdyh);
        System.out.println("xmid:" + xmid);
        System.out.println("bdcdyh2:" + bdcdyh2);

        Map<String, Object> result = new HashMap<>(10);
        result.put("XH", "1");
        result.put("BDCQZH", "ZH232132131213");
        result.put("FJ", "附记内容");
        result.put("QLR", "李鑫");
        result.put("GYQK", "单独所有");

        return result;
    }

    // /zfxx/#{bdcdyh}/#{xmid}/xml?bdcdyh=#{bdcdyh}
    @GetMapping("/zfxx2/{bdcdyh}/{xmid}/xml")
    public List<Map<String, Object>> testPrintUrl2(@PathVariable("bdcdyh") String bdcdyh,
                                                   @PathVariable("xmid") String xmid,
                                                   @RequestParam("bdcdyh") String bdcdyh2) {
        System.out.println("bdcdyh:" + bdcdyh);
        System.out.println("xmid:" + xmid);
        System.out.println("bdcdyh2:" + bdcdyh2);

        Map<String, Object> result = new HashMap<>(10);
        result.put("ZSID", "1");
        result.put("BDCQZH", "ZH232132131213");
        result.put("FJ", "附记内容");
        result.put("LZR", "李鑫");
        result.put("GYQK", "单独所有");
        result.put("CZR", "南京市中级人民法院");
        result.put("LZRZJH", "查封121212121");
        result.put("LZRDH", "南京市鼓楼区");
        result.put("LZRZJZL", "身份证");

        Map<String, Object> result2 = new HashMap<>(10);
        result2.put("ZSID", "2");
        result2.put("BDCQZH", "ZH232132131213");
        result2.put("FJ", "附记内容2");
        result2.put("LZR", "李鑫2");
        result2.put("GYQK", "单独所有2");
        result2.put("CZR", "南京市中级人民法院2");
        result2.put("LZRZJH", "查封121212121");
        result2.put("LZRDH", "南京市鼓楼区2");
        result2.put("LZRZJZL", "身份证");

        List<Map<String, Object>> list = new ArrayList<>(1);
        list.add(result);
        list.add(result2);

        return list;
    }

    // /zfxx/#{bdcdyh}/#{xmid}/xml?bdcdyh=#{bdcdyh}
    @GetMapping("/zfxx3/{bdcdyh}/{xmid}/xml")
    public List<BdcXmDO> testPrintUrl3(@PathVariable("bdcdyh") String bdcdyh,
                                       @PathVariable("xmid") String xmid,
                                       @RequestParam("bdcdyh") String bdcdyh2) {
        System.out.println("bdcdyh:" + bdcdyh);
        System.out.println("xmid:" + xmid);
        System.out.println("bdcdyh2:" + bdcdyh2);

        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setBdcqzh("ZH232132131213");
        bdcXmDO.setQlr("李世民");
        bdcXmDO.setDjsj(new Date());

        BdcXmDO bdcXmDO2 = new BdcXmDO();
        bdcXmDO2.setBdcqzh("ZH232132131213");
        bdcXmDO2.setQlr("李世民2");
        bdcXmDO2.setDjsj(new Date());

        List<BdcXmDO> list = new ArrayList<>(1);
        list.add(bdcXmDO);
        list.add(bdcXmDO2);

        return list;
    }

    private OfficeExportDTO getOfficeExportDTO(int size) throws IOException {
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(path + "sczmd.docx");
        pdfExportDTO.setFileName("首次证明单");
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<fetchdatas>\n");
        for (int i = 0; i < size; i++) {
            builder.append(" <page>\n" +
                    "        <datas>\n" +
                    "            <data name=\"slbh\" type=\"String\">20210002812</data>\n" +
                    "            <data name=\"fwtp\" type=\"String\">1</data>\n" +
                    "            <data name=\"bdcqzh\" type=\"String\">皖(2019)合肥市不动产权第0000号</data>\n" +
                    "            <data name=\"ysxkzh\" type=\"String\">XKZH1212121</data>\n" +
                    "            <data name=\"zl\" type=\"String\">合肥市工农路18号</data>\n" +
                    "            <data name=\"bdcdyh\" type=\"String\">3201212131313131313</data>\n" +
                    "            <data name=\"fwxz\" type=\"String\">商品房</data>\n" +
                    "            <data name=\"tdcqzh\" type=\"String\">ZH121232131</data>\n" +
                    "            <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "            <data name=\"zdzhh\" type=\"String\">ZDZH1231213</data>\n" +
                    "            <data name=\"fwjg\" type=\"String\">混凝土</data>\n" +
                    "            <data name=\"tdsyjssj\" type=\"String\">2089-09-11</data>\n" +
                    "            <data name=\"zcs\" type=\"String\">12</data>\n" +
                    "            <data name=\"szc\" type=\"String\">2</data>\n" +
                    "            <data name=\"qllx\" type=\"String\">100</data>\n" +
                    "            <!-- 复选框 -->\n" +
                    "            <data name=\"CHECKBOX#qllx\" type=\"String\">100,700,800_200,300,400,500,600</data>\n" +
                    "            <data name=\"tdqllx\" type=\"String\">2</data>\n" +
                    "            <data name=\"jgsj\" type=\"String\">2020-01-29</data>\n" +
                    "            <data name=\"zdzhyt\" type=\"String\">房屋</data>\n" +
                    "            <data name=\"dzwmj\" type=\"String\">130.1</data>\n" +
                    "            <data name=\"zdzhmj\" type=\"String\">132</data>\n" +
                    "            <data name=\"ftjzmj\" type=\"String\">13</data>\n" +
                    "            <data name=\"dytdmj\" type=\"String\">15</data>\n" +
                    "            <data name=\"tnjzmj\" type=\"String\">120</data>\n" +
                    "            <data name=\"djjg\" type=\"String\">合肥市不动产登记中心</data>\n" +
                    "            <data name=\"szr\" type=\"String\">张梦</data>\n" +
                    "            <data name=\"slr\" type=\"String\">张梦</data>\n" +
                    "            <data name=\"djsj\" type=\"String\">2019-12-09</data>\n" +
                    "            <data name=\"rq\" type=\"String\">2020-01-09</data>\n" +
                    "            <data name=\"zl\" type=\"String\">经***莱路8号江汽五村9幢102</data>\n" +
                    "            <data name=\"bdczl\" type=\"String\">经***莱路8号江汽五村9幢102</data>\n" +
                    "            <data name=\"mm\" type=\"String\">李露鹿</data>\n" +
                    "            <data name=\"qlr\" type=\"String\">李露鹿</data>\n" +
                    "            <data name=\"gyr\" type=\"String\">赵飞燕</data>\n" +
                    "            <data name=\"n\" type=\"String\">2021</data>\n" +
                    "            <data name=\"y\" type=\"String\">10</data>\n" +
                    "            <data name=\"r\" type=\"String\">9</data>\n" +
                    "            <data name=\"lcmc\" type=\"String\">房屋抵押权首次登记</data>\n" +
                    "            <data name=\"slqx\" type=\"String\">10</data>\n" +
                    "            <data name=\"underline\" type=\"String\">下划线</data>\n" +
                    "            <data name=\"UL_slbh_Width5\" type=\"String\">BH12321311</data>\n" +
                    "            <data name=\"HH_fj\" type=\"String\">我是附记我是附记1&HH&换行测试2&HH&我是附记我是附记3&HH&</data>\n" +
                    "\n" +
                    "            <!-- 图片，二维码也是图片 -->\n" +
                    "            <data name=\"ewmnr\" type=\"image\">http://127.0.0.1:8030/realestate-accept/rest/v1.0/print/OEg1aG5IcUxVclZjTGFWeWYvemwwZ2hhcE04b2dyODZyTDZPelBlZnVXaDUvZ1lCVCs4UHMrNGJsWjN6d0FlaEwvUU1xYTVlRm9wMwpnM01aTWZtRU5JTFErZWxrNVJ1TGIvZ3pFRDlwYXB4TEtlMEYrUlkrNkVzcDNQRUlwOGhnWExVK1pnNm5tRXkzK0x6aXNOYzVmSkhxCjYyMUQxSGNKQUVDcG1TZVJVcWh1Y1J1MWV0Z3ZMblJUdWZsL21LenJZWFZydExKOURib2N4NXkwWVhtVlhacGErdjVQT05Qc3NST3cKRndjeFlveGhScjFibE9aeVFYc0Z6M3JLbGRDcDU4RE5PS3dRTWRiRTMvaXJwTHlzSUpZMFBYb3RxUFAvTXdrT3dhZ240ZG9xbFdJaApNUHFMcmluUHdQRGpHTzEyelBRS09NUjVxV3c9/ewm</data>\n" +
                    "            <data name=\"jfsewmurl\" type=\"image\">http://127.0.0.1:8030/realestate-accept/rest/v1.0/print/OEg1aG5IcUxVclZjTGFWeWYvemwwZ2hhcE04b2dyODZyTDZPelBlZnVXaDUvZ1lCVCs4UHMrNGJsWjN6d0FlaEwvUU1xYTVlRm9wMwpnM01aTWZtRU5JTFErZWxrNVJ1TGIvZ3pFRDlwYXB4TEtlMEYrUlkrNkVzcDNQRUlwOGhnWExVK1pnNm5tRXkzK0x6aXNOYzVmSkhxCjYyMUQxSGNKQUVDcG1TZVJVcWh1Y1J1MWV0Z3ZMblJUdWZsL21LenJZWFZydExKOURib2N4NXkwWVhtVlhacGErdjVQT05Qc3NST3cKRndjeFlveGhScjFibE9aeVFYc0Z6M3JLbGRDcDU4RE5PS3dRTWRiRTMvaXJwTHlzSUpZMFBYb3RxUFAvTXdrT3dhZ240ZG9xbFdJaApNUHFMcmluUHdQRGpHTzEyelBRS09NUjVxV3c9/ewm</data>\n" +
                    "            <data name=\"slbhewmurl\" type=\"image\">http://127.0.0.1:8030/realestate-accept/rest/v1.0/print/qwertyuioplkjhgfd4444eeeeeeeerrrrrrrrrrrdddddddddddddddddddddddddddddddddddddddrrrrrrrddddddvvfvvvvvvvddddddddddddddddddddddddddddddddddvvdddddddddddddddddddddddddddddddddddddddvvvvvvvvvvvvvdddds/ewm</data>\n" +
                    "        </datas>\n" +
                    "\n" +
                    "        <!-- 普通表格 -->\n" +
                    "        <detail ID=\"bdcsjcl_FontSize11_RowHeight450\">\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">1</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"clmc\" type=\"String\">不动产登记申请表</data>\n" +
                    "                <data name=\"sjlx\" type=\"String\">原件正本</data>\n" +
                    "                <data name=\"dz\" type=\"String\">南京市</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153IP2B303Q\">\n" +
                    "                <data name=\"xh\" type=\"String\">2</data>\n" +
                    "                <data name=\"clmc\" type=\"String\">申请人身份证明</data>\n" +
                    "                <data name=\"sjlx\" type=\"String\">原件副本</data>\n" +
                    "                <data name=\"fs\" type=\"String\">2</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153JO2B303R\">\n" +
                    "                <data name=\"xh\" type=\"String\">3</data>\n" +
                    "                <data name=\"clmc\" type=\"String\">房屋所有权证或房地产权证</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"dz\" type=\"String\">淮安市</data>\n" +
                    "                <data name=\"sjlx\" type=\"String\">原件正本</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153KN2B303S\">\n" +
                    "                <data name=\"xh\" type=\"String\">4</data>\n" +
                    "                <data name=\"sjlx\" type=\"String\">原件正本</data>\n" +
                    "                <data name=\"fs\" type=\"String\">2</data>\n" +
                    "                <data name=\"clmc\" type=\"String\">抵押合同</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153KN2B303S\">\n" +
                    "                <data name=\"clmc\" type=\"String\">主债权合同</data>\n" +
                    "                <data name=\"xh\" type=\"String\">5</data>\n" +
                    "                <data name=\"sjlx\" type=\"String\">原件正本</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"dz\" type=\"String\">扬州市</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "        <!-- 普通表格 -->\n" +
                    "        <detail ID=\"qsxx_FontSize10_RowHeight600\">\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">1</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽&HH&五村9幢102</data>\n" +
                    "                <data name=\"zh\" type=\"String\">2幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积&HH&100平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2018-01-12&HH&至&HH&2018-01-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">2</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢202</data>\n" +
                    "                <data name=\"zh\" type=\"String\">3幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-01-12&HH&至&HH&2019-01-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">3</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">4</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">5</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"fs\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"zl\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"zh\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"mj\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"qx\" type=\"String\">第PAGE页/共TOTAL页</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">6</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">7</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">8</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">9</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">10</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">11</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">12</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">13</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">14</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"fs\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"zl\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"zh\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"mj\" type=\"String\">&NONE&</data>\n" +
                    "                <data name=\"qx\" type=\"String\">第PAGE页/共TOTAL页</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">15</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">16</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">17</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">18</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">19</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">20</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">21</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">22</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">23</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">24</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">25</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">26</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">27</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">28</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">29</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">30</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">31</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">32</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">33</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">34</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">35</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">36</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">37</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">38</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">39</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">40</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">41</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">42</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">43</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">44</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xh\" type=\"String\">45</data>\n" +
                    "                <data name=\"fs\" type=\"String\">1</data>\n" +
                    "                <data name=\"zl\" type=\"String\">莱路8号江汽五村9幢203</data>\n" +
                    "                <data name=\"zh\" type=\"String\">4幢</data>\n" +
                    "                <data name=\"dzwyt\" type=\"String\">住宅</data>\n" +
                    "                <data name=\"mj\" type=\"String\">面积200平米</data>\n" +
                    "                <data name=\"qx\" type=\"String\">2019-02-12&HH&至&HH&2019-02-12</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "\n" +
                    "        <!-- 整体扩展表格：领证情况 -->\n" +
                    "        <detail ID=\"ZT_lzqk\">\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"bdcqzh\" type=\"String\">23423423</data>\n" +
                    "                <data name=\"qzysxlh\" type=\"String\">ZH31231312</data>\n" +
                    "                <data name=\"lzr\" type=\"String\">张飞1</data>\n" +
                    "                <data name=\"zjzl\" type=\"String\">身份证</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">3208112121212</data>\n" +
                    "                <data name=\"lxdh\" type=\"String\">17692121232</data>\n" +
                    "                <data name=\"czr\" type=\"String\">李四</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303A\">\n" +
                    "                <data name=\"bdcqzh\" type=\"String\">AA32323</data>\n" +
                    "                <data name=\"qzysxlh\" type=\"String\">ZH31231312</data>\n" +
                    "                <data name=\"lzr\" type=\"String\">张飞2</data>\n" +
                    "                <data name=\"zjzl\" type=\"String\">身份证</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">3208112121212</data>\n" +
                    "                <data name=\"lxdh\" type=\"String\">17692121232</data>\n" +
                    "                <data name=\"czr\" type=\"String\">刘备</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "\n" +
                    "        <!-- 整体扩展表格（嵌套子表） -->\n" +
                    "        <detail ID=\"ZT_qlrxx\">\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"qlrmc\" type=\"String\">张三</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">ZH11111111</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB1153HM2B303A\">\n" +
                    "                <data name=\"qlrmc\" type=\"String\">李四</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">ZH22222222</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "\n" +
                    "        <!-- 整体扩展表格（内嵌复选框） -->\n" +
                    "        <detail ID=\"ZT_nssb\">\n" +
                    "            <row ID=\"3CVB1153HM2B303P\">\n" +
                    "                <data name=\"xm\" type=\"String\">张三</data>\n" +
                    "                <data name=\"bdcqzh\" type=\"String\">皖(2020)蜀山区不动产权第6090003号</data>\n" +
                    "                <data name=\"CHECKBOX#qs\" type=\"String\">first_second,third</data>\n" +
                    "                <!-- 内嵌图片 -->\n" +
                    "                <data name=\"tx\" type=\"image\">https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1819216937,2118754409&fm=26&gp=0.jpg</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "\n" +
                    "        <!-- 内嵌到整体扩展表格中的子表 -->\n" +
                    "        <detail ID=\"ZB_jtcy\">\n" +
                    "            <row ID=\"3CVB115121B303P\">\n" +
                    "                <data name=\"zbxh\" type=\"String\">1</data>\n" +
                    "                <data name=\"xm\" type=\"String\">张一</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">JTCY111111111</data>\n" +
                    "                <data name=\"gx\" type=\"String\">父子</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB115121B303P\">\n" +
                    "                <data name=\"zbxh\" type=\"String\">1</data>\n" +
                    "                <data name=\"xm\" type=\"String\">张五</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">JTCY55555555</data>\n" +
                    "                <data name=\"gx\" type=\"String\">父子</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB32423HM2B303P\">\n" +
                    "                <data name=\"zbxh\" type=\"String\">2</data>\n" +
                    "                <data name=\"xm\" type=\"String\">李二</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">JTCY2222222</data>\n" +
                    "                <data name=\"gx\" type=\"String\">父女</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB32423HM2B303P\">\n" +
                    "                <data name=\"zbxh\" type=\"String\">2</data>\n" +
                    "                <data name=\"xm\" type=\"String\">李三</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">JTCY33333333</data>\n" +
                    "                <data name=\"gx\" type=\"String\">父女</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB32423HM2B303P\">\n" +
                    "                <data name=\"zbxh\" type=\"String\">2</data>\n" +
                    "                <data name=\"xm\" type=\"String\">李八</data>\n" +
                    "                <data name=\"zjh\" type=\"String\">JTCY8888888</data>\n" +
                    "                <data name=\"gx\" type=\"String\">父女</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "\n" +
                    "        <!-- 普通表格中的内嵌子表 -->\n" +
                    "        <detail ID=\"NQ_YWRXX_0_0_FontSize9\">\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_30\" type=\"String\">姓名</data>\n" +
                    "                <data name=\"zjh_1_40\" type=\"String\">证号</data>\n" +
                    "                <data name=\"gx_2_30\" type=\"String\">关系</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB115121B303P\">\n" +
                    "                <data name=\"xm_0_30\"  type=\"String\">张洞庭</data>\n" +
                    "                <data name=\"zjh_1_40\" type=\"String\">ZDT111111111</data>\n" +
                    "                <data name=\"gx_2_30\"  type=\"String\">ZB_身份证_3201002_&&_户口簿_320100_&&_FontSize9_&&_Border20</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB115121B303P\">\n" +
                    "                <data name=\"xm_0_30\"  type=\"String\">王西湖</data>\n" +
                    "                <data name=\"zjh_1_40\" type=\"String\">WXH55555555</data>\n" +
                    "                <data name=\"gx_2_30\"  type=\"String\">兄弟</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "        <detail ID=\"NQ_YWRXX_16_1_FontSize9_Border15\">\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_30\" type=\"String\">姓名</data>\n" +
                    "                <data name=\"zjh_1_40\" type=\"String\">证号</data>\n" +
                    "                <data name=\"gx_2_30\" type=\"String\">关系</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB115121B303P\">\n" +
                    "                <data name=\"xm_0_30\"  type=\"String\">张洞庭</data>\n" +
                    "                <data name=\"zjh_1_40\" type=\"String\">ZDT111111111</data>\n" +
                    "                <data name=\"gx_2_30\"  type=\"String\">ZB_身份证_3201002_&&_户口簿_320100_&&_FontSize15_&&_FontsSimKai</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"3CVB115121B303P\">\n" +
                    "                <data name=\"xm_0_30\"  type=\"String\">王西湖</data>\n" +
                    "                <data name=\"zjh_1_40\" type=\"String\">WXH55555555</data>\n" +
                    "                <data name=\"gx_2_30\"  type=\"String\">兄弟</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "        \n" +
                    "        <detail ID=\"TABLE_NQ_QLRXX_17_2_FontSize9_FontsSimKai_Border6\">\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_50\" type=\"String\">姓名</data>\n" +
                    "                <data name=\"zjh_1_50\" type=\"String\">证号</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_50\"  type=\"String\">张洞庭</data>\n" +
                    "                <data name=\"zjh_1_50\" type=\"String\">ZDT111111111</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_50\"  type=\"String\">李洪泽</data>\n" +
                    "                <data name=\"zjh_1_50\" type=\"String\">LHZ111111111</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "        <detail ID=\"NQ_DYRXX_14_2_Border10\">\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_50\"  type=\"String\">王浩</data>\n" +
                    "                <data name=\"gx_1_50\"  type=\"String\">ZB_证件类型_身份证_&&_证件号码_342301197111174412_&&_FontSize9_&&_Border10</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_50\"  type=\"String\">ZB_证件类型_身份证_&&_证件号码_320402197309262528_&&_FontSize9_&&_Border10</data>\n" +
                    "                <data name=\"gx_1_50\"  type=\"String\">林少云</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_50\"  type=\"String\">王维</data>\n" +
                    "                <data name=\"gx_1_50\"  type=\"String\">ZB_证件类型_身份证_&&_证件号码_320402197309265432_&&_FontSize9_&&_Border10</data>\n" +
                    "            </row>\n" +
                    "            <row ID=\"\">\n" +
                    "                <data name=\"xm_0_50\"  type=\"String\">ZB_证件类型_营业执照_&&_证件号码_3453453453453453_&&_FontSize9_&&_Border10</data>\n" +
                    "                <data name=\"gx_1_50\"  type=\"String\">顾明月</data>\n" +
                    "            </row>\n" +
                    "        </detail>\n" +
                    "    </page>");
        }
        builder.append("</fetchdatas>\n");
//        StringBuilder builder = new StringBuilder();
//        builder.append(IOUtils.toString(new FileInputStream(new File("H:\\qsqc.txt")), "UTF-8"));
        pdfExportDTO.setXmlData(builder.toString());
        return pdfExportDTO;
    }

    private OfficeExportDTO getOfficeExportDTO2(int size) {
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setFileName("申请书");
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<fetchdatas>\n" +
                "    <datas>\n" +
                "        <data  name=\"fwtp\" type=\"String\">1</data>\n" +
                "        <data  name=\"ywrdlrmc\" type=\"String\">李飞</data>\n" +
                "        <data  name=\"ywrdlrzjzl\" type=\"String\">身份证</data>\n" +
                "        <data  name=\"ywrdlrzjh\" type=\"String\">329123456789098761</data>\n" +
                "        <data  name=\"ywrdldh\" type=\"String\">18912345678</data>\n" +
                "        <data  name=\"CHECKBOX#qlsdfs\" type=\"String\">2_1,3</data>\n" +
                "        <data  name=\"CHECKBOX#qdfs\" type=\"String\"></data>\n" +
                "        <data  name=\"CHECKBOX#dyfs\" type=\"String\"></data>\n" +
                "        <data  name=\"CHECKBOX#djlx\" type=\"String\">200_9999</data>\n" +
                "        <data  name=\"CHECKBOX#sdq\" type=\"String\">9999_99,98,97,96,95</data>\n" +
                "        <data  name=\"CHECKBOX#jzzr\" type=\"String\">1_0</data>\n" +
                "        <data  name=\"CHECKBOX#djyy\" type=\"String\">5_1,2,3,4</data>\n" +
                "        <data  name=\"djsj\" type=\"String\"></data>\n" +
                "        <data  name=\"dyqrmc\" type=\"String\">常州工商银行</data>\n" +
                "        <data  name=\"dyqrzjzl\" type=\"String\">营业证</data>\n" +
                "        <data  name=\"dyqrzjh\" type=\"String\">YH123121221</data>\n" +
                "        <data  name=\"dyqrdh\" type=\"String\">020-1223445</data>\n" +
                "        <data  name=\"dyqrdlrmc\" type=\"String\">吴强</data>\n" +
                "        <data  name=\"dyqrdlrzjzl\" type=\"String\">身份证</data>\n" +
                "        <data  name=\"dyqrdlrdh\" type=\"String\">15712356171</data>\n" +
                "        <data  name=\"qlrfrmc\" type=\"String\">赵梦艳</data>\n" +
                "        <data  name=\"fddbrzjzl\" type=\"String\">身份证</data>\n" +
                "        <data  name=\"fddbrzjh\" type=\"String\">213192929292929292</data>\n" +
                "        <data  name=\"qlrfrdh\" type=\"String\">16272928272</data>\n" +
                "        <data  name=\"ycqzh\" type=\"String\">00087021</data>" +
                "        <data  name=\"zl\" type=\"String\">蜀山区怀宁路2299号丰盛华庭4幢603</data>\n" +
                "        <data  name=\"qlxz\" type=\"String\">划拨</data>\n" +
                "        <data  name=\"HH_tdsyjssj\" type=\"String\">2019-12-09&HH&至&HH&2029-12-09</data>\n" +
                "        <data  name=\"mj\" type=\"String\">391.5</data>\n" +
                "        <data  name=\"zdzhyt\" type=\"String\">住宿餐饮用地</data>\n" +
                "        <data  name=\"zwr\" type=\"String\">李西西</data>\n" +
                "        <data  name=\"bdbzzqse\" type=\"String\">126</data>\n" +
                "        <data  name=\"zgzqe\" type=\"String\">126</data>\n" +
                "        <data  name=\"dbfw\" type=\"String\">范围</data>\n" +
                "        <data  name=\"fwdyjg\" type=\"String\">123万</data>\n" +
                "        <data  name=\"zwlxjssj\" type=\"String\">2019-12-09至2035-12-09</data>\n" +
                "        <data  name=\"ywrdlrmc\" type=\"String\">李飞</data>\n" +
                "        <data  name=\"ywrdlrzjzl\" type=\"String\">身份证</data>\n" +
                "        <data  name=\"ywrdlrzjh\" type=\"String\">329123456789098761</data>\n" +
                "        <data  name=\"ywrdldh\" type=\"String\">18912345678</data>\n" +
                "        <data  name=\"qlrdlr\" type=\"String\">吴强</data>\n" +
                "        <data  name=\"dlrzjzl\" type=\"String\">身份证</data>\n" +
                "        <data  name=\"dlrzjh\" type=\"String\">3218888888111122323</data>\n" +
                "        <data  name=\"dlrdh\" type=\"String\">15712356171</data>\n" +
                "        <data  name=\"dyqrmc\" type=\"String\">常州工商银行</data>\n" +
                "        <data  name=\"dyqrzjzl\" type=\"String\">营业证</data>\n" +
                "        <data  name=\"dyqrzjh\" type=\"String\">YH123121221</data>\n" +
                "        <data  name=\"dyqrdh\" type=\"String\">020-1223445</data>\n" +
                "        <data  name=\"qlrdlrmc\" type=\"String\">岳起</data>\n" +
                "        <data  name=\"dydlrzjzl\" type=\"String\">身份证</data>\n" +
                "        <data  name=\"dyqrdlrzjh\" type=\"String\">3212321312321312</data>\n" +
                "        <data  name=\"qlrdldh\" type=\"String\">15131232131</data>\n" +
                "    </datas>\n" +
                "    <detail  ID=\"NQ_qlrxx_2_2_FontsSimSun_FontSize11_Border6_RowHeight475\">\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"qlrmc_0_18.4\" type=\"String\">姓名或名称</data>\n" +
                "            <data  name=\"qlrgyqk_1_18.4\" type=\"String\">共有情况</data>\n" +
                "            <data  name=\"qlrzjzl_2_13.5\" type=\"String\">证件种类</data>\n" +
                "            <data  name=\"qlrzjh_3_31.03\" type=\"String\">证件号码</data>\n" +
                "            <data  name=\"qlrdh_4_18.1\" type=\"String\">联系电话</data>\n" +
                "        </row>\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"qlrmc_0_18.4\" type=\"String\">黄源</data>\n" +
                "            <data  name=\"qlrgyqk_1_18.4\" type=\"String\">共同共有</data>\n" +
                "            <data  name=\"qlrzjzl_2_13.5\" type=\"String\">身份证</data>\n" +
                "            <data  name=\"qlrzjh_3_31.03\" type=\"String\">3231322132121312</data>\n" +
                "            <data  name=\"qlrdh_4_18.1\" type=\"String\">17612211211</data>\n" +
                "        </row>\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"qlrmc_0_18.4\" type=\"String\">王菲</data>\n" +
                "            <data  name=\"qlrgyqk_1_18.4\" type=\"String\">单独所有&HH&测试换行&HH&测试换行</data>\n" +
                "            <data  name=\"qlrzjzl_2_13.5\" type=\"String\">身份证</data>\n" +
                "            <data  name=\"qlrzjh_3_31.03\" type=\"String\">340311199109190261</data>\n" +
                "            <data  name=\"qlrdh_4_18.1\" type=\"String\">15105522706</data>\n" +
                "        </row>\n" +
                "    </detail>\n" +
                "    <detail  ID=\"NQ_ywrxx_0_2_FontsSimSun_FontSize11_Border6_RowHeight475\">\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"ywrmc_0_18.4\" type=\"String\">姓名或名称</data>\n" +
                "            <data  name=\"ywrgyqk_1_18.4\" type=\"String\">共有情况</data>\n" +
                "            <data  name=\"ywrzjzl_2_13.5\" type=\"String\">证件种类</data>\n" +
                "            <data  name=\"ywrzjh_3_31.03\" type=\"String\">证件号码</data>\n" +
                "            <data  name=\"ywrdh_4_18.1\" type=\"String\">联系电话</data>\n" +
                "        </row>\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"ywrmc_0_18.4\" type=\"String\">张浅</data>\n" +
                "            <data  name=\"ywrgyqk_1_18.4\" type=\"String\">单独所有</data>\n" +
                "            <data  name=\"ywrzjzl_2_13.5\" type=\"String\">身份证</data>\n" +
                "            <data  name=\"ywrzjh_3_31.03\" type=\"String\">320402470112003</data>\n" +
                "            <data  name=\"ywrdh_4_18.1\" type=\"String\">/</data>\n" +
                "        </row>\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"ywrmc_0_18.4\" type=\"String\">&NR</data>\n" +
                "            <data  name=\"ywrgyqk_1_18.4\" type=\"String\">&NR</data>\n" +
                "            <data  name=\"ywrzjzl_2_13.5\" type=\"String\">&NR</data>\n" +
                "            <data  name=\"ywrzjh_3_31.03\" type=\"String\">&NR</data>\n" +
                "            <data  name=\"ywrdh_4_18.1\" type=\"String\">&NR</data>\n" +
                "        </row>\n" +
                "    </detail>\n" +
                "    <detail  ID=\"NQ_fwxx_9_2_FontsSimSun_FontSize11_Border6_RowHeight385\">\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"zh_0_12.5\" type=\"String\">幢号</data>\n" +
                "            <data  name=\"fjh_1_12.45\" type=\"String\">房号</data>\n" +
                "            <data  name=\"zcs_2_12.35\" type=\"String\">总层数</data>\n" +
                "            <data  name=\"szmyc_3_13.5\" type=\"String\">所在层</data>\n" +
                "            <data  name=\"jzmj_4_12.4\" type=\"String\">建筑面积</data>\n" +
                "            <data  name=\"ghyt_5_12.4\" type=\"String\">规划用途</data>\n" +
                "            <data  name=\"fwjg_6_12.4\" type=\"String\">结构</data>\n" +
                "            <data  name=\"bz_7_12.3\" type=\"String\">备注</data>\n" +
                "        </row>\n" +
                "        <row  ID=\"\">\n" +
                "            <data  name=\"zh_0_12.5\" type=\"String\">40</data>\n" +
                "            <data  name=\"fjh_1_12.45\" type=\"String\">一#101</data>\n" +
                "            <data  name=\"zcs_2_12.35\" type=\"String\">5</data>\n" +
                "            <data  name=\"szmyc_3_13.5\" type=\"String\">1</data>\n" +
                "            <data  name=\"jzmj_4_12.4\" type=\"String\">63.7</data>\n" +
                "            <data  name=\"ghyt_5_12.4\" type=\"String\">住宅</data>\n" +
                "            <data  name=\"fwjg_6_12.4\" type=\"String\">钢筋混凝土</data>\n" +
                "            <data  name=\"bz_7_12.3\" type=\"String\">/</data>\n" +
                "        </row>\n" +
                "    </detail>\n" +
                "</fetchdatas>");


//        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<fetchdatas>\n" +
//                "    <datas>\n" +
//                "        <data  name=\"xzq\" type=\"String\">常州市</data>\n" +
//                "        <data  name=\"zl\" type=\"String\">凯纳华侨城3幢2503室</data>\n" +
//                "        <data  name=\"bdcdyh\" type=\"String\">320404001001GB01612F07240096</data>\n" +
//                "        <data  name=\"tdyt\" type=\"String\">住宅用地</data>\n" +
//                "        <data  name=\"zdmj\" type=\"String\">535.46</data>\n" +
//                "        <data  name=\"cqdjdw\" type=\"String\">\n" +
//                "        </data>\n" +
//                "        <data  name=\"cqdjsj\" type=\"String\">\n" +
//                "        </data>\n" +
//                "        <data  name=\"djsj\" type=\"String\">2013/11/13</data>\n" +
//                "        <data  name=\"fwxz\" type=\"String\">\n" +
//                "        </data>\n" +
//                "        <data  name=\"syqx\" type=\"String\">2078/10/12</data>\n" +
//                "        <data  name=\"tdqlxz\" type=\"String\">出让</data>\n" +
//                "        <data  name=\"fj\" type=\"String\">123</data>\n" +
//                "        <data  name=\"cxsj\" type=\"String\">2021/10/16  10:08:27</data>\n" +
//                "        <data  name=\"cdr\" type=\"String\">罗希</data>\n" +
//                "        <data  name=\"jycx\" type=\"image\">0</data>\n" +
//                "    </datas>\n" +
//                "    <detail  ID=\"NQ_FWXX_1_1_FontsSimSun_FontSize8_Border10_RowHeight345\">\n" +
//                "        <row ID=\"\">\n" +
//                "            <data name=\"zh_0_7\" type=\"String\">幢号</data>\n" +
//                "            <data name=\"fh_1_8\" type=\"String\">房号</data>\n" +
//                "            <data name=\"szc_2_10\" type=\"String\">所在层</data>\n" +
//                "            <data name=\"zcs_3_10\" type=\"String\">总层数</data>\n" +
//                "            <data name=\"fwjg_4_15\" type=\"String\">房屋结构</data>\n" +
//                "            <data name=\"jzmj_5_15\" type=\"String\">建筑面积</data>\n" +
//                "            <data name=\"tnmj_6_18\" type=\"String\">套内建筑面积</data>\n" +
//                "            <data name=\"ghyt_7_14.8\" type=\"String\">规划用途</data>\n" +
//                "        </row>\n" +
//                "        <row ID=\"\">\n" +
//                "            <data name=\"zh_0_7\" type=\"String\">3幢</data>\n" +
//                "            <data name=\"fh_1_8\" type=\"String\">2503</data>\n" +
//                "            <data name=\"szc_2_10\" type=\"String\">25</data>\n" +
//                "            <data name=\"zcs_3_10\" type=\"String\">35</data>\n" +
//                "            <data name=\"fwjg_4_15\" type=\"String\">钢筋混凝土结构</data>\n" +
//                "            <data name=\"jzmj_5_15\" type=\"String\">96.49</data>\n" +
//                "            <data name=\"tnmj_6_18\" type=\"String\">78.36</data>\n" +
//                "            <data name=\"ghyt_7_14.8\" type=\"String\">住宅</data>\n" +
//                "        </row>" +
//                "    </detail>\n" +
//                "    <detail  ID=\"NQ_QLRXX_3_1_FontsSimSun_FontSize8_Border10_RowHeight345\">\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"qlr_0_20\" type=\"String\">权利人</data>\n" +
//                "            <data  name=\"gyqk_1_20\" type=\"String\">共有情况</data>\n" +
//                "            <data  name=\"bdcqz_2_35\" type=\"String\">房屋所有权证号/不动产权证号</data>\n" +
//                "            <data  name=\"tdz_3_24.8\" type=\"String\">土地使用权证号</data>\n" +
//                "        </row>\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"qlr_0_20\" type=\"String\">罗希</data>\n" +
//                "            <data  name=\"gyqk_1_20\" type=\"String\">共同共有</data>\n" +
//                "            <data  name=\"bdcqz_2_35\" type=\"String\">00654190-1</data>\n" +
//                "            <data  name=\"tdz_3_24.8\" type=\"String\">常国用(2013)第61658号</data>\n" +
//                "        </row>\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"qlr_0_20\" type=\"String\">刘国英</data>\n" +
//                "            <data  name=\"gyqk_1_20\" type=\"String\">共同共有</data>\n" +
//                "            <data  name=\"bdcqz_2_35\" type=\"String\">00654190</data>\n" +
//                "            <data  name=\"tdz_3_24.8\" type=\"String\">常国用(2013)第61658号</data>\n" +
//                "        </row>\n" +
//                "    </detail>\n" +
//                "    <detail  ID=\"NQ_DYXX_7_1_FontsSimSun_FontSize8_Border10_RowHeight345\">\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"bdczm_0_20\" type=\"String\">不动产证明号</data>\n" +
//                "            <data  name=\"dyqr_1_20\" type=\"String\">抵押权人</data>\n" +
//                "            <data  name=\"dyqx_2_15\" type=\"String\">抵押期限</data>\n" +
//                "            <data  name=\"dbfw_3_10\" type=\"String\">担保范围</data>\n" +
//                "            <data  name=\"jg_4_18\" type=\"String\">被担保主债权金额/最高债权额</data>\n" +
//                "            <data  name=\"sfjz_5_17\" type=\"String\">是否存在禁止或限制转让抵押不动产的约定</data>\n" +
//                "        </row>\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"bdczm_0_20\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"dyqr_1_20\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"dyqx_2_15\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"dbfw_3_10\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"jg_4_18\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"sfjz_5_17\" type=\"String\">&NR</data>\n" +
//                "        </row>\n" +
//                "    </detail>\n" +
//                "    <detail  ID=\"NQ_JZXX_8_1_FontsSimSun_FontSize8_Border10_RowHeight345\">\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"jzzmh_0_40\" type=\"String\">不动产证明号</data>\n" +
//                "            <data  name=\"jzr_1_25\" type=\"String\">居住权人</data>\n" +
//                "            <data  name=\"jzqx_2_34\" type=\"String\">居住权期限</data>\n" +
//                "        </row>\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"jzzmh_0_40\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"jzr_1_25\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"jzqx_2_34\" type=\"String\">&NR</data>\n" +
//                "        </row>\n" +
//                "    </detail>\n" +
//                "    <detail  ID=\"NQ_XZXX_9_1_FontsSimSun_FontSize8_Border10_RowHeight345\">\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"cfjg_0_70\" type=\"String\">查封机构及文号</data>\n" +
//                "            <data  name=\"cfsj_1_29\" type=\"String\">查封时间</data>\n" +
//                "        </row>\n" +
//                "        <row  ID=\"\">\n" +
//                "            <data  name=\"cfjg_0_70\" type=\"String\">&NR</data>\n" +
//                "            <data  name=\"cfsj_1_29\" type=\"String\">&NR</data>\n" +
//                "        </row>\n" +
//                "    </detail>\n" +
//                "</fetchdatas>");
        pdfExportDTO.setXmlData(builder.toString());
        return pdfExportDTO;
    }

    @Test
    public void tes() {
        System.out.println("   <page>\n" +
                "\t\t<datas>\n" +
                "\t\t\t<data name=\"slbh\" type=\"String\">2010120001</data>\n" +
                "\t\t\t<data name=\"bdcqzh\" type=\"String\">皖(2019)合肥市不动产权第0001号</data> \n" +
                "\t\t\t<data name=\"ysxkzh\" type=\"String\">XKZH1212121</data> \n" +
                "\t\t\t<data name=\"zl\" type=\"String\">合肥市工农路18号</data> \n" +
                "\t\t\t<data name=\"bdcdyh\" type=\"String\">3201212131313131313</data> \n" +
                "\t\t\t<data name=\"fwxz\" type=\"String\">商品房</data> \n" +
                "\t\t\t<data name=\"tdcqzh\" type=\"String\">ZH121232131</data> \n" +
                "\t\t\t<data name=\"dzwyt\" type=\"String\">住宅</data> \n" +
                "\t\t\t<data name=\"zdzhh\" type=\"String\">ZDZH1231213</data> \n" +
                "\t\t\t<data name=\"fwjg\" type=\"String\">混凝土</data> \n" +
                "\t\t\t<data name=\"tdsyjssj\" type=\"String\">2089-09-11</data> \n" +
                "\t\t\t<data name=\"zcs\" type=\"String\">12</data> \n" +
                "\t\t\t<data name=\"szc\" type=\"String\">2</data> \n" +
                "\t\t\t<data name=\"tdqllx\" type=\"String\">2</data> \n" +
                "\t\t\t<data name=\"jgsj\" type=\"String\">2020-01-29</data> \n" +
                "\t\t\t<data name=\"zdzhyt\" type=\"String\">房屋</data> \n" +
                "\t\t\t<data name=\"dzwmj\" type=\"String\">130.1</data> \n" +
                "\t\t\t<data name=\"zdzhmj\" type=\"String\">132</data> \n" +
                "\t\t\t<data name=\"ftjzmj\" type=\"String\">13</data> \n" +
                "\t\t\t<data name=\"dytdmj\" type=\"String\">15</data> \n" +
                "\t\t\t<data name=\"tnjzmj\" type=\"String\">120</data> \n" +
                "\t\t\t<data name=\"djjg\" type=\"String\">合肥市不动产登记中心</data> \n" +
                "\t\t\t<data name=\"szr\" type=\"String\">张三</data> \n" +
                "\t\t\t<data name=\"djsj\" type=\"String\">2019-12-09</data> \n" +
                "\t\t</datas>\n" +

                "\t</page>\n");
    }

    public static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos,"GB2312"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null){
                    bw.close();
                }
                if (fos != null){
                    fos.close();
                }
            } catch (IOException ie) {
            }
        }
    }
}
