package cn.gtmap.realestate.building.ui.util;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import jxl.Workbook;
import jxl.write.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/24
 * @description
 */
public class ExportLpb {

    /**
     * 导出列标题与实体成员变量对应Map
     */
    private static Map<String, String> EXPORT_COLUMN = new LinkedHashMap<String, String>();

    static {
        EXPORT_COLUMN.put("物理层数", "wlcs");
        EXPORT_COLUMN.put("定义层数", "dycs");
        EXPORT_COLUMN.put("房间号", "fjh");
        EXPORT_COLUMN.put("单元号", "dyh");
        EXPORT_COLUMN.put("房屋编码", "fwbm");
        EXPORT_COLUMN.put("幢号", "zh");
        EXPORT_COLUMN.put("权利id", "qlid");
        EXPORT_COLUMN.put("层号", "ch");
        EXPORT_COLUMN.put("不动产单元房屋类型", "bdcdyfwlx");
        EXPORT_COLUMN.put("参与分摊土地面积计算", "syfttdmjjs");
        EXPORT_COLUMN.put("共有土地面积(㎡)", "gytdmj");
        EXPORT_COLUMN.put("分摊土地面积(㎡)", "fttdmj");
        EXPORT_COLUMN.put("独用土地面积(㎡)", "dytdmj");
        EXPORT_COLUMN.put("预测建筑面积(㎡)", "ycjzmj");
        EXPORT_COLUMN.put("预测套内建筑面积(㎡)", "yctnjzmj");
        EXPORT_COLUMN.put("预测分摊建筑面积(㎡)", "ycftjzmj");
        EXPORT_COLUMN.put("预测地下部分建筑面积(㎡)", "ycdxbfjzmj");
        EXPORT_COLUMN.put("预测其他建筑面积(㎡)", "ycqtjzmj");
        EXPORT_COLUMN.put("预测分摊系数", "ycftxs");
        EXPORT_COLUMN.put("实测建筑面积(㎡)", "scjzmj");
        EXPORT_COLUMN.put("实测套内建筑面积(㎡)", "sctnjzmj");
        EXPORT_COLUMN.put("实测分摊建筑面积(㎡)", "scftjzmj");
        EXPORT_COLUMN.put("实测地下部分建筑面积(㎡)", "scdxbfjzmj");
        EXPORT_COLUMN.put("实测其他建筑面积(㎡)", "scqtjzmj");
        EXPORT_COLUMN.put("实测分摊系数", "scftxs");
        EXPORT_COLUMN.put("坐落", "zl");
        EXPORT_COLUMN.put("交易价格(万元)", "jyjg");
        EXPORT_COLUMN.put("规划用途", "ghyt");
        EXPORT_COLUMN.put("房屋类型", "fwlx");
        EXPORT_COLUMN.put("房屋结构", "fwjg");
        EXPORT_COLUMN.put("房屋户型", "fwhx");
        EXPORT_COLUMN.put("户型结构", "hxjg");
        EXPORT_COLUMN.put("竣工时间", "jgrq");
        EXPORT_COLUMN.put("房屋性质", "fwxz");
        EXPORT_COLUMN.put("建成时装修程度", "jczxcd");
        EXPORT_COLUMN.put("东", "d");
        EXPORT_COLUMN.put("南", "n");
        EXPORT_COLUMN.put("西", "x");
        EXPORT_COLUMN.put("北", "b");
        EXPORT_COLUMN.put("产权来源", "cqly");
        EXPORT_COLUMN.put("共有情况", "gyqk");
        EXPORT_COLUMN.put("附加说明", "fjsm");
        EXPORT_COLUMN.put("调查意见", "dcyj");
        EXPORT_COLUMN.put("调查者", "dcz");
        EXPORT_COLUMN.put("调查时间", "dcsj");
        EXPORT_COLUMN.put("邮政编码", "yzbm");
        EXPORT_COLUMN.put("合并方向", "hbfx");
        EXPORT_COLUMN.put("合并户室数", "hbhss");
        EXPORT_COLUMN.put("办理状态", "blzt");
        EXPORT_COLUMN.put("权利状态", "qlzt");
        EXPORT_COLUMN.put("权利人1", "qlr1");
        EXPORT_COLUMN.put("权利人编码1", "qlrbm1");
        EXPORT_COLUMN.put("权利人证件类型1", "qlrzjlx1");
        EXPORT_COLUMN.put("权利人证件号1", "qlrzjh1");
        EXPORT_COLUMN.put("权利人性质1", "qlrxz1");
        EXPORT_COLUMN.put("权利人2", "qlr2");
        EXPORT_COLUMN.put("权利人编码2", "qlrbm2");
        EXPORT_COLUMN.put("权利人证件类型2", "qlrzlx2");
        EXPORT_COLUMN.put("权利人证件号2", "qlrzjh2");
        EXPORT_COLUMN.put("权利人性质2", "qlrxz2");
        EXPORT_COLUMN.put("权利人3", "qlr3");
        EXPORT_COLUMN.put("权利人编码3", "qlrbm3");
        EXPORT_COLUMN.put("权利人证件类型3", "qlrzlx3");
        EXPORT_COLUMN.put("权利人证件号3", "qlrzjh3");
        EXPORT_COLUMN.put("权利人性质3", "qlrxz3");
        EXPORT_COLUMN.put("权利人4", "qlr4");
        EXPORT_COLUMN.put("权利人编码4", "qlrbm4");
        EXPORT_COLUMN.put("权利人证件类型4", "qlrzjlx4");
        EXPORT_COLUMN.put("权利人证件号4", "qlrzjh4");
        EXPORT_COLUMN.put("权利人性质4", "qlrxz4");
        EXPORT_COLUMN.put("权利人5", "qlr5");
        EXPORT_COLUMN.put("权利人编码5", "qlrbm5");
        EXPORT_COLUMN.put("权利人证件类型5", "qlrzjlx5");
        EXPORT_COLUMN.put("权利人证件号5", "qlrzjh5");
        EXPORT_COLUMN.put("权利人性质5", "qlrxz5");
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 导出文件
     */
    public static void exportExcel(String fileName, List<Map<String, Object>> exportLpb, HttpServletResponse response, LinkedHashMap<String, String> exportColumnMap, String default0Column) throws WriteException, IOException {
        if (MapUtils.isNotEmpty(exportColumnMap)) {
            EXPORT_COLUMN = exportColumnMap;
        }
        // 取得输出流
        OutputStream os = response.getOutputStream();
        response.reset();
        // 设置文件头
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        // 设置输出类型
        response.setContentType("application/msexcel");

        // 创建excel
        WritableWorkbook workbook = Workbook.createWorkbook(os);

        // 创建sheet
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);

        // 标题样式
        // 粗体
        WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        WritableCellFormat wcfCenter = new WritableCellFormat(boldFont);
        // 边框
        wcfCenter.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        // 竖向居中对齐
        wcfCenter.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        // 横向居中对齐
        wcfCenter.setAlignment(jxl.format.Alignment.CENTRE);
        // 不自动换行
        wcfCenter.setWrap(false);

        // 正文样式
        // 字体
        WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        WritableCellFormat wcfLeft = new WritableCellFormat(normalFont);
        // 边框
        wcfLeft.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.THIN);
        // 竖向居中对齐
        wcfLeft.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        // 横向居中对齐
        wcfLeft.setAlignment(jxl.format.Alignment.CENTRE);
        // 不自动换行
        wcfLeft.setWrap(false);

        // 写入标题行
        int a = 0;
        for (String name : EXPORT_COLUMN.keySet()) {
            sheet.addCell(new Label(a, 0, name, wcfCenter));
            sheet.setColumnView(a, 20);
            a++;
        }

        // 写入列表数据
        int rownum = 1;
        for (Map<String, Object> fwhsMap : exportLpb) {
            int colnum = 0;
            Iterator iter = EXPORT_COLUMN.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object value = entry.getValue();
                if (value == "dcsj") {
                    if (fwhsMap.get(value) != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sheet.addCell(new Label(colnum, rownum, sdf.format(fwhsMap.get(value)), wcfLeft));
                    }
                }else {
                    if (Objects.nonNull(value) && String.valueOf(value).contains(CommonConstantUtils.ZF_YW_XG)) {
                        for (String val : String.valueOf(value).split(CommonConstantUtils.ZF_YW_XG)) {
                            String cellValue = String.valueOf(fwhsMap.get(val));
                            if (!StringUtils.equals(cellValue, "null")) {
                                //不为空判断为0 的情况
                                if (NumberUtils.isNumber(cellValue)) {
                                    Double cell = NumberUtils.toDouble(cellValue, 0);
                                    if (!Objects.equals(0.0, cell)) {
                                        sheet.addCell(new Label(colnum, rownum, cellValue, wcfLeft));
                                        break;
                                    } else {
                                        sheet.addCell(new Label(colnum, rownum, "0", wcfLeft));
                                    }
                                } else {
                                    sheet.addCell(new Label(colnum, rownum, cellValue, wcfLeft));
                                    break;
                                }
                            } else {
                                if (StringUtils.isNotBlank(default0Column)) {
                                    List<String> columnList = Arrays.asList(default0Column.split(CommonConstantUtils.ZF_YW_DH));
                                    if (CollectionUtils.isNotEmpty(columnList) && columnList.contains(val)) {
                                        sheet.addCell(new Label(colnum, rownum, "0", wcfLeft));
                                    }
                                }
                            }
                        }
                    } else {
                        String cellValue = String.valueOf(fwhsMap.get(value));
                        if (!StringUtils.equals(cellValue, "null")) {
                            if (NumberUtils.isNumber(cellValue)) {
                                Double cell = NumberUtils.toDouble(cellValue, 0);
                                if (!Objects.equals(0.0, cell)) {
                                    sheet.addCell(new Label(colnum, rownum, cellValue, wcfLeft));
                                } else {
                                    sheet.addCell(new Label(colnum, rownum, "0", wcfLeft));
                                }
                            } else {
                                sheet.addCell(new Label(colnum, rownum, cellValue, wcfLeft));
                            }
                        } else {
                            if (StringUtils.isNotBlank(default0Column)) {
                                List<String> columnList = Arrays.asList(default0Column.split(CommonConstantUtils.ZF_YW_DH));
                                if (CollectionUtils.isNotEmpty(columnList) && columnList.contains(value)) {
                                    sheet.addCell(new Label(colnum, rownum, "0", wcfLeft));
                                }
                            }
                        }
                    }
                }
                colnum++;
            }
            rownum++;
        }

        workbook.write();
        workbook.close();
    }
}