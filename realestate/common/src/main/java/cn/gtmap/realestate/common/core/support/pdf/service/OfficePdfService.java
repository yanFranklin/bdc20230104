package cn.gtmap.realestate.common.core.support.pdf.service;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/06
 * @description PDF相关数据处理逻辑接口
 */
public interface OfficePdfService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param officeExportDTO 导出所需参数信息
     * @description 生成PDF文档（多页合并成一个）
     *
     *  注意：调用端需要考虑是否删除生成的临时PDF文件
     */
    String generatePdfFile(OfficeExportDTO officeExportDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param files 要合并的PDF文件
     * @return 临时文件地址
     * @description 合并多个PDF文件为一个
     */
    String mergePdfFiles(Map<String, String> files);
}
