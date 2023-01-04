package cn.gtmap.realestate.common.core.support.excel;

import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

/**
 * @author wutao
 * @date 2022/4/13 10:15
 */
@Service
public class ExcelExpandYcService implements ExcelExpandService {

    @Override
    public void expand(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO) {

        // 标题内容
        String title = excelExportDTO.getCellTitle();
        String[] cellTitleArr = title.split(CommonConstantUtils.ZF_YW_DH);

        // 标题内容
        String totalInfo = excelExportDTO.getTotalInfo();
        String[] totalInfoArr = totalInfo.split(CommonConstantUtils.ZF_YW_DH);
        // 标题样式
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        // 创建表名
        sheet.shiftRows(1, sheet.getLastRowNum(), 1,true,true);//一句代码就能搞定
        HSSFRow rowtotal = sheet.createRow(1);
        HSSFCell celltotal = rowtotal.createCell(0);
        celltotal.setCellValue(totalInfoArr[0]);
        celltotal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(
                1, 1, 0, cellTitleArr.length - 2
        ));
        HSSFCell celltotalvalue = rowtotal.createCell(cellTitleArr.length - 1);
        celltotalvalue.setCellValue(totalInfoArr[1]);
        celltotalvalue.setCellStyle(style);
    }
}
