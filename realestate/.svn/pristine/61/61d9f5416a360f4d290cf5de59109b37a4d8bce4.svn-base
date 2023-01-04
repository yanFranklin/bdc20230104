package cn.gtmap.realestate.exchange.util;

import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/12/1.
 * @description
 */
@Component
public class HtmlToPdf {

    private static Logger LOGGER = LoggerFactory.getLogger(HtmlToPdf.class);

    /**
     * 获取html
     * @param data  数据集合
     * @param templateName  模板名称
     * @return
     */
    public  String getHtmlString (final Object data, final String templateName) {
        String html = null;
        StringWriter stringWriter = null;
        BufferedWriter writer = null;
        try {
            //创建一个负责管理FreeMarker模板的Configuration实例
            final Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            /*String path=this.getClass().getClassLoader().getResource("templates").getPath();
            //指定FreeMarker模板文件位置
		    configuration.setDirectoryForTemplateLoading(new File(path));*/
            configuration.setClassForTemplateLoading(this.getClass(), "/templates");
            //设置模板的编码格式
            configuration.setEncoding(Locale.CHINA, "UTF-8");
            //获取模板
            final Template template = configuration.getTemplate(templateName, "UTF-8");
            stringWriter = new StringWriter();
            writer = new BufferedWriter(stringWriter);
            //将数据输出到html中
            template.process(data, writer);
            writer.flush();
            html = stringWriter.toString();
        } catch (Exception e) {
            LOGGER.error(null,e);
        } finally {
            if (stringWriter !=null) {
                try {
                    stringWriter.close();
                } catch (IOException e) {
                    LOGGER.error(null,e);
                }
            }
            if (writer !=null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOGGER.error(null,e);
                }
            }
        }
        return html;
    }

    /**
     * 根据数据和模板名称下载
     * @param response
     * @param data
     * @param templateName
     */
    public void createPDF(HttpServletResponse response,final Object data, final String templateName) {
        OutputStream outputStream=null;
        try {
            outputStream =response.getOutputStream();
            outputStream.flush();
        } catch (final Exception e) {
            LOGGER.error(null,e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (final IOException e) {
                    LOGGER.error(null,e);
                }
            }
        }
    }

    /**
     * 生成pdf流数据
     * @param outputStream
     * @param html
     */
    public void createPDF(OutputStream outputStream,final String html) {
        final ITextRenderer renderer = new ITextRenderer();
        // 解决中文问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        // 设置字体样式
        try {
            fontResolver.addFont("/templates/SimSun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 把html代码传入渲染器中
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
            outputStream.flush();
        } catch (final Exception e) {
            LOGGER.error(null,e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (final IOException e) {
                    LOGGER.error(null,e);
                }
            }
        }
    }
}
