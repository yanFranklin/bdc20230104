package cn.gtmap.realestate.etl.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/8/15.
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
@Service("fileUtil")
public class FileUtil {
    @Autowired
    HttpClient httpClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void download(String savePath,String fileName,HttpServletResponse res){
        //下载文件
        String destFile =savePath + File.separator+fileName;
        System.out.println("---------------destFile--------------"+destFile);
        try {
            res.setCharacterEncoding("UTF-8");
            if (destFile != null && destFile.trim().length() > 0) {
                byte buff[] = new byte[512];
                int nLen = 0;
                File file = null;
                FileInputStream fis = null;
                OutputStream sos = null;
                try {
                    file = new File(destFile);
                    fis = new FileInputStream(file);
                    // 清空response
                    res.reset();
                    res.setContentLength((int) file.length());
                    res.setContentType("text/html");
                    res.setHeader("name", destFile);
                    res.setHeader("Content-disposition",
                            "attachment; filename=\""
                                    + URLEncoder.encode(fileName, "UTF-8")+ "\""); //
                    sos = res.getOutputStream();
                    sos.flush();
                    while ((nLen = fis.read(buff)) > 0) {
                        sos.write(buff, 0, nLen);
                    }
                    sos.flush();
                    sos.close();
                }catch (IOException ioex) {
                    throw ioex;
                } finally {
                    try {
                        if (fis != null) {fis.close();}
                    } catch (Exception exception1) {
                    }
                    try {
                        if (sos != null) {sos.close();}
                    } catch (Exception exception2) {
                    }
                    file = null;
                    //response.getWriter().print("{success:true}");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    /**
     * 根据储存在文件中心的地址下载文件
     *
     * @param url
     * @return 文件
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     */
    public  byte[] downloadZz(String url) {
        CloseableHttpResponse htResponse = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream in = null;
        if (StringUtils.isNoneBlank(url)) {
            try {
                HttpGet httpGet = new HttpGet(url);
                htResponse = ((CloseableHttpClient) httpClient).execute(httpGet);
                if (htResponse != null && org.apache.http.HttpStatus.SC_OK == htResponse.getStatusLine().getStatusCode()) {
                    in = htResponse.getEntity().getContent();
                }
                byte[] buffer = new byte[5120];
                int n = 0;
                assert in != null;
                while (-1 != (n = in.read(buffer))) {
                    output.write(buffer, 0, n);
                }
            } catch (ClientProtocolException e) {
                logger.info("请求失败");
            } catch (IOException e) {
                logger.error("读取xml失败",e);
            } finally {
                if (htResponse != null) {
                    try {
                        htResponse.close();
                    } catch (IOException e) {
                        logger.error("error:",e);
                    }
                }
            }
        }
        return output.toByteArray();
    }

}
