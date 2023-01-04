package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.etl.core.vo.PopupFileDataVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OldSystemFileService {

    /**
     * 获取项目对应的目录信息
     * @param gzlslid
     * @return
     */
    CommonResponse<List<PopupFileDataVO>> fetchCataLog(String gzlslid) throws Exception;

    /**
     * 下载指定文件信息
     *
     * @param path
     * @param fileName
     * @param response
     * @return
     */
    void downloadFile(String path, String fileName, HttpServletResponse response) throws Exception;

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 打印勾选的数据
     * @date : 2021/8/16 10:21
     */
    String getSjclXml(String key);


    /**
     * @param key
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 设置需要打印的收件材料的地址到redis ，生存时长20s
     * @date : 2021/8/16 13:50
     */
    String setSjclUrlToRedis(String json);

}
