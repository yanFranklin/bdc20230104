package cn.gtmap.realestate.common.core.support.excel;

import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/01/29
 * @description Excel自定义功能扩展接口
 */
public interface ExcelExpandService {
    /**
     * 自定义扩展Excel功能
     * @param workbook 当前Excel工作簿对象
     * @param sheet 当前分页
     * @param excelExportDTO 业务参数对象
     */
    void expand(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO);
}
