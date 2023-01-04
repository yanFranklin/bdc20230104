package cn.gtmap.realestate.common.core.support.excel;

import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/12/14
 * @description 连云港批量查询导出excel统计信息处理
 */
@Service
public class ExcelExpandLygFcplcxService implements ExcelExpandService {
    @Override
    public void expand(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO) {
        // 统计信息
        String totalInfo = excelExportDTO.getTotalInfo();
        if (StringUtils.isBlank(totalInfo)) {
            return;
        }
        JSONObject totalInfoJson = JSONObject.parseObject(totalInfo, JSONObject.class);
        String tjr = totalInfoJson.getString("统计人");
        String tjsj = totalInfoJson.getString("统计时间");

        // 标题内容
        String title = excelExportDTO.getCellTitle();
        String[] cellTitleArr = title.split(CommonConstantUtils.ZF_YW_DH);


        // 统计信息样式
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        // 设置统计人信息
        HSSFRow rowtotalTjr = sheet.createRow(sheet.getLastRowNum() + 1);
        HSSFCell celltotalTjr = rowtotalTjr.createCell(cellTitleArr.length - 2);
        celltotalTjr.setCellValue("统计人");
        celltotalTjr.setCellStyle(style);
        HSSFCell celltotalTjrInfo = rowtotalTjr.createCell(cellTitleArr.length - 1);
        celltotalTjrInfo.setCellValue(tjr);
        celltotalTjrInfo.setCellStyle(style);

        // 设置统计时间信息
        HSSFRow rowtotalTjsj = sheet.createRow(sheet.getLastRowNum() + 1);
        HSSFCell celltotalTjsj = rowtotalTjsj.createCell(cellTitleArr.length - 2);
        celltotalTjsj.setCellValue("统计时间");
        celltotalTjsj.setCellStyle(style);
        HSSFCell celltotalvalueTjsjInfo = rowtotalTjsj.createCell(cellTitleArr.length - 1);
        celltotalvalueTjsjInfo.setCellValue(tjsj);
        celltotalvalueTjsjInfo.setCellStyle(style);

    }
}
