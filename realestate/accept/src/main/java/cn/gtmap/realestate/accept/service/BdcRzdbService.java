package cn.gtmap.realestate.accept.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/19.
 * @description 不动产人证对比服务处理类
 */
public interface BdcRzdbService {

    /**
     * 生成人证对比PDF文件，并上传至大云文件中心
     * @param slbh 受理编号
     * @return pdf文件地址
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    String scrzdbPdf(String slbh, String gzlslid);

    /**
     * 获取放置在服务器中打印模板路径下正确与错误的图片
     * @param type 类型（success:正确的图片；error:错误的图片）
     * @return 图片的文件流
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void generateSuccessOrErrorImage(String type, HttpServletResponse response);

}
