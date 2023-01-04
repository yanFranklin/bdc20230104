package cn.gtmap.realestate.common.core.support.pdf.service;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import com.deepoove.poi.NiceXWPFDocument;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/06
 * @description Word、Pdf 文档处理逻辑接口
 */
public interface OfficeDocService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param officeExportDTO  导出参数信息
     * @description  获取文档对象（所有页面合成一个）
     */
    NiceXWPFDocument getXwpfDocument(OfficeExportDTO officeExportDTO) throws Exception;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param officeExportDTO  导出参数信息
     * @description  获取文档对象
     */
    List<NiceXWPFDocument> getXwpfDocumentList(OfficeExportDTO officeExportDTO) throws Exception;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param modelName 模板文件名称
     * @param data 渲染数据
     * @description  渲染数据获取单个文档对象
     */
    NiceXWPFDocument getNiceXWPFDocument(String modelName, Map<String, Object> data);
}
