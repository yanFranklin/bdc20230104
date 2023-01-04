package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.exchange.util.HtmlToPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-12-3
 * @description
 */
@RequestMapping("/realestate-exchange/rest/v1.0/pdfmb")
public class PdfMbController{

    @Autowired
    private HtmlToPdf htmlToPdf;


    @RequestMapping("/yl")
    public String yl(String mbmc){
        return mbmc;
    }

    @RequestMapping("/download")
    public void download(String mbmc,HttpServletResponse response){
        htmlToPdf.createPDF(response,null,mbmc);
    }
}
