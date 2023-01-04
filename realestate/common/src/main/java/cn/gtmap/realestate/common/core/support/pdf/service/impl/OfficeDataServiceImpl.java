package cn.gtmap.realestate.common.core.support.pdf.service.impl;

import cn.gtmap.realestate.common.core.support.pdf.service.OfficeDataService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/06
 * @description Office相关数据处理逻辑
 */
@Service
public class OfficeDataServiceImpl implements OfficeDataService {
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeDataServiceImpl.class);

    private static final Pattern pattern = Pattern.compile("[0-9]*");
    /**
     * 字体存放目录，同打印模板同一目录
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 复选框图片字节数据：选中格式数据
     */
    public static byte[] checkBoxY;

    /**
     * 复选框图片字节数据：未选中格式数据
     */
    public static byte[] checkBoxN;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 初始化数据处理
     */
    @PostConstruct
    public void init() {
        try {
            checkBoxY = FileUtils.readFileToByteArray(new File(printPath + CommonConstantUtils.PDF_WORD_CHECKBOX_PICTURE_Y));
            checkBoxN = FileUtils.readFileToByteArray(new File(printPath + CommonConstantUtils.PDF_WORD_CHECKBOX_PICTURE_N));
        } catch (IOException e) {
            LOGGER.error("导出PDF、WORD初始化获取复选框图片数据失败，因为未找到对应文件!");
        }
    }


    /**
     * @param xmlData xml打印数据内容
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据XML打印数据内容抽取其中的数据键值对
     * 说明：
     * 1、xml数据内容中一个page代表一页，则其中的<data>节点都转换到一个 map中，多页则对应 List<Map>
     * 2、xml数据内容中没有page则代表当前打印只有一页，List<Map> 数量为 1
     */
    @Override
    public List<Map<String, Object>> getValDataList(String xmlData) throws DocumentException {
        List<Map<String, Object>> result = new ArrayList<>(10);
        Document document = DocumentHelper.parseText(xmlData.replaceAll("&", "&amp;"));
        Element root = document.getRootElement();

        if (StringUtils.contains(xmlData, "page")) {
            // 获取所有的分页
            List<Element> pageList = root.elements("page");
            for (Element page : pageList) {
                result.add(this.getValDataMap(page));
            }
        } else {
            result.add(this.getValDataMap(root));
        }

        return result;
    }

    /**
     * @param element XML节点
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取节点下面<data>数据内容
     */
    private Map getValDataMap(Element element) {
        if (null == element) {
            return null;
        }

        // 获取所有的<datas>节点
        List<Element> datasList = element.elements("datas");
        if (CollectionUtils.isEmpty(datasList) || null == datasList.get(0)) {
            return null;
        }

        // 获取所有的<data>节点
        List<Element> dataList = datasList.get(0).elements("data");
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }

        // 获取
        Map<String, Object> map = new HashMap<>(dataList.size());
        for (Element data : dataList) {
            String name = data.attribute("name").getValue();
            String type = data.attribute("type").getValue();

            if (this.isImageType(type)) {
                // 图片
                this.addImageType(map, data, name, false);
            } else if (this.isStrType(type)) {
                // 文本
                if (this.isCheckBox(name)) {
                    // 复选框
                    this.addCheckBoxType(map, data, false);
                } else {
                    // 普通字段
                    map.put(name, data.getText());
                }
            }
        }

        // 获取所有的子表信息并返回最终数据
        return this.getSubTables(element, map);
    }

    /**
     * @param type XML节点类型
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断是否是图片节点
     */
    private boolean isImageType(String type) {
        if ("image".equals(type) || "Image".equals(type) || "IMAGE".equals(type)) {
            return true;
        }
        return false;
    }

    /**
     * @param type XML节点类型
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断是否是字符串节点
     */
    private boolean isStrType(String type) {
        if ("String".equals(type) || "string".equals(type) || "STRING".equals(type)) {
            return true;
        }
        return false;
    }

    /**
     * @param map             字段键值集合
     * @param data            某个字段Element
     * @param isSetPictureUrl 是否设值图片路径（true: 设值路径；false：设值图片数据对象）
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 处理二维码对应字段
     */
    private void addImageType(Map<String, Object> map, Element data, String name, boolean isSetPictureUrl) {
        try {
            String text = data.getText();
            if (StringUtils.isBlank(text)) {
                LOGGER.error("导出PDF、WORD处理图片中止，错误原因：未定义图片路径，对应字段名称：{}", name);
                return;
            }
            if (isSetPictureUrl) {
                map.put(name, text);
            } else {
                //处理图片大小
                String[] tempArr = name.split("_");
                // 图片高度
                int height=90;
                // 图片宽度
                int width=90;
                if(tempArr.length > 2){
                    for(int i=1;i<tempArr.length;i++){
                        String items = tempArr[i];
                        if(items.startsWith(CommonConstantUtils.PDF_WORD_EWMNR_HEIGHT)){
                            //判断高度大小是否为数字
//                            Pattern pattern = Pattern.compile("[0-9]*");
                            Matcher isNum = pattern.matcher(items.substring(CommonConstantUtils.PDF_WORD_EWMNR_HEIGHT.length()));
                            if(isNum.matches()){
                                height=Integer.parseInt(items.substring(CommonConstantUtils.PDF_WORD_EWMNR_HEIGHT.length()));
                            }

                        }
                        if(items.startsWith(CommonConstantUtils.PDF_WORD_EWMNR_WIDTH)){
                            //判断宽度大小是否为数字
//                            Pattern pattern = Pattern.compile("[0-9]*");
                            Matcher isNum = pattern.matcher(items.substring(CommonConstantUtils.PDF_WORD_EWMNR_WIDTH.length()));
                            if(isNum.matches()){
                                width=Integer.parseInt(items.substring(CommonConstantUtils.PDF_WORD_EWMNR_WIDTH.length()));
                            }
                        }
                    }
                }
                if (text.startsWith("http") || text.startsWith("https")) {
                    map.put(name, new PictureRenderData(width, height, ".png", BytePictureUtils.getUrlBufferedImage(text)));
                } else {
                    // 设置的图片为文件路径
                    map.put(name, new PictureRenderData(width, height, ".png", ImageIO.read(new File(text))));
                }
            }
        } catch (Exception e) {
            LOGGER.error("导出PDF、WORD处理图片错误，对应字段：{}，错误原因：{}", name, e.getMessage());
        }
    }

    /**
     * @param map              字段键值集合
     * @param data             某个字段Element
     * @param isSetPictureName 是否设值复选框图片名称（true: 设值名称；false：设值图片数据对象）
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 处理复选框对应字段
     * <p>
     * checkbox字段名称SQL查询时候默认 CHECKBOX# 开头
     * 字段值默认 需要选中的值_剩余其它值选项，都以逗号分隔
     * 例如 select '99' || '_' || (select WM_CONCAT(dm) from bdc_zd_qllx where dm <> '99') as CHECKBOX#qllx  from dual
     */
    private void addCheckBoxType(Map<String, Object> map, Element data, boolean isSetPictureName) {
        String name = data.attribute("name").getValue();

        String itemName = name.replace(CommonConstantUtils.PDF_WORD_CHECKBOX_UPPER_PRE, "");
        itemName = itemName.replace(CommonConstantUtils.PDF_WORD_CHECKBOX_LOWER_PRE, "");

        String text = data.getText();
        if (StringUtils.isNotBlank(text)) {
            String[] textArr = text.split("_");
            // 模板中动态替换图片，名称 {{@字段实际名称_对应值}} ， 例如 {{@qllx_100}}
            String[] valArr = textArr[0].split(",");
            for (String val : valArr) {
                if (isSetPictureName) {
                    map.put(itemName + "_" + val, CommonConstantUtils.PDF_WORD_CHECKBOX_PICTURE_Y);
                } else {
                    map.put(itemName + "_" + val, new PictureRenderData(12, 12, ".png", OfficeDataServiceImpl.checkBoxY));
                }
            }

            if (textArr.length > 1) {
                String[] otherValArr = textArr[1].split(",");
                for (String val : otherValArr) {
                    if (isSetPictureName) {
                        map.put(itemName + "_" + val, CommonConstantUtils.PDF_WORD_CHECKBOX_PICTURE_N);
                    } else {
                        map.put(itemName + "_" + val, new PictureRenderData(12, 12, ".png", OfficeDataServiceImpl.checkBoxN));
                    }
                }
            }
        }
    }

    /**
     * @param element 根节点
     * @param map     最终数据Mao
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取所有子表数据，对应<detail>节点
     */
    private Map getSubTables(Element element, Map<String, Object> map) {
        List<Element> detailElements = element.elements("detail");
        if (CollectionUtils.isEmpty(detailElements)) {
            return map;
        }

        for (Element detailElement : detailElements) {
            // 子表名称
            String tableId = detailElement.attribute("ID").getValue();

            // 子表所有行数据
            List<Element> detailRowElements = detailElement.elements("row");
            if (CollectionUtils.isEmpty(detailRowElements)) {
                continue;
            }

            // 嵌套子表序号
            int maxZbxh = 0;

            // 一个Map代表一行数据，Map中每个KEY代表某一列数据
            List<Map<String, Object>> rowDataList = new ArrayList<>(detailRowElements.size());
            for (Element row : detailRowElements) {
                // 循环获取每一行对应的列数据
                List<Element> rowDatas = row.elements("data");
                if (CollectionUtils.isNotEmpty(rowDatas)) {
                    Map<String, Object> dataMap = new HashMap<>(rowDatas.size());
                    for (Element rowData : rowDatas) {
                        String name = rowData.attribute("name").getValue();
                        String type = rowData.attribute("type").getValue();
                        String value = rowData.getText();

                        if (this.isImageType(type)) {
                            // 图片
                            this.addImageType(dataMap, rowData, name, true);
                        } else if (this.isStrType(type)) {
                            if (this.isCheckBox(name)) {
                                // 子表复选框格式
                                this.addCheckBoxType(dataMap, rowData, true);
                            } else {
                                // 普通文本格式
                                dataMap.put(name, value);
                            }
                        }

                        if ("zbxh".equals(name) && StringUtils.isNotBlank(value) && Integer.valueOf(value).intValue() > maxZbxh) {
                            maxZbxh = Integer.valueOf(value);
                        }
                    }
                    rowDataList.add(dataMap);
                }
            }

            // 所有子表ID需要在文档模板中固定以 TABLE_ 开头，数据部分匹配
            if (tableId.startsWith("ZB_") || tableId.startsWith("zb_")) {
                // 嵌套子表数据处理：数据源查询时候同一个父表的所有嵌套子表一起查询，这里需要将它们分开
                for (Map<String, Object> rowData : rowDataList) {
                    String zbxh = String.valueOf(rowData.get("zbxh"));

                    // TABLE_ZB_子表名称_1
                    String subTableId = CommonConstantUtils.PDF_WORD_SUBTABLE_PRE + tableId + "_" + zbxh;
                    if (map.keySet().contains(subTableId)) {
                        ((List<Map<String, Object>>) map.get(subTableId)).add(rowData);
                    } else {
                        List<Map<String, Object>> subTableDataList = new ArrayList<>(5);
                        subTableDataList.add(rowData);
                        map.put(subTableId, subTableDataList);
                    }
                }
            } else if(tableId.startsWith("NQ_") || tableId.startsWith("nq_")) {
                // 普通表格内嵌子表，word中占位符格式：{{TABLE_NQ_子表名称_所在行_所在列_&&_分号分隔的字段}}
                map.put(CommonConstantUtils.PDF_WORD_SUBTABLE_PRE + tableId, rowDataList);
            }  else {
                // 普通表格数据源XML节点名称没有要求，但是如果是整体扩展子表需要在配置数据源时候设置为 ZT_开头
                // 最终转换为PDF处理的数据时候，普通表格ID形式：TABLE_*** , 整体扩展表格ID形式：TABLE_ZT_***
                map.put(CommonConstantUtils.PDF_WORD_SUBTABLE_PRE + tableId, rowDataList);
            }
        }
        return map;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断是否是复选框字段
     */
    private boolean isCheckBox(String name) {
        return name.startsWith(CommonConstantUtils.PDF_WORD_CHECKBOX_UPPER_PRE) ||
                name.startsWith(CommonConstantUtils.PDF_WORD_CHECKBOX_LOWER_PRE);
    }
}
