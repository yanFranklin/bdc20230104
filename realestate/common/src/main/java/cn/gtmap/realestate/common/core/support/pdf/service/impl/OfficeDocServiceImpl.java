package cn.gtmap.realestate.common.core.support.pdf.service.impl;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.support.pdf.service.OfficeDataService;
import cn.gtmap.realestate.common.core.support.pdf.service.OfficeDocService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/06
 * @description Word、Pdf 文档处理逻辑
 */
@Service
public class OfficeDocServiceImpl implements OfficeDocService {
    /**
     * 数据处理
     */
    @Autowired
    private OfficeDataService officeDataService;


    /**
     * @param officeExportDTO 导出参数信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取文档对象（合并成一个文档）
     */
    @Override
    public NiceXWPFDocument getXwpfDocument(OfficeExportDTO officeExportDTO) throws Exception {
        if (null == officeExportDTO) {
            throw new NullPointerException("导出文件失败，原因：未传入打印有效参数!");
        }
        if (StringUtils.isBlank(officeExportDTO.getModelName())) {
            throw new NullPointerException("导出文件失败，原因：未指定打印模板!");
        }
        if (StringUtils.isBlank(officeExportDTO.getXmlData())) {
            throw new NullPointerException("导出文件失败，原因：未指定打印数据源!");
        }

        // 解析打印数据
        List<Map<String, Object>> dataList = officeDataService.getValDataList(officeExportDTO.getXmlData());
        if (CollectionUtils.isEmpty(dataList)) {
            throw new NullPointerException("导出文件失败，原因：未获取到打印数据!");
        }

        // 获取第一个模板
        NiceXWPFDocument firstXWPFDoc = this.getNiceXWPFDocument(officeExportDTO.getModelName(), dataList.get(0));
        // 添加分页符，保证单独一页
        if (dataList.size() > 1) {
            firstXWPFDoc.createParagraph().setPageBreak(true);
        }

        // 循环后续模板并拼接成一个文档
        if (dataList.size() > 1) {
            for (int i = 1; i < dataList.size(); i++) {
                NiceXWPFDocument document = this.getNiceXWPFDocument(officeExportDTO.getModelName(), dataList.get(i));
                if (i != dataList.size() - 1) {
                    document.createParagraph().setPageBreak(true);
                }
                // 合并文档
                firstXWPFDoc = firstXWPFDoc.merge(document);
            }
        }

        return firstXWPFDoc;
    }

    /**
     * @param officeExportDTO 导出参数信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取文档对象集合
     */
    @Override
    public List<NiceXWPFDocument> getXwpfDocumentList(OfficeExportDTO officeExportDTO) throws Exception {
        if (null == officeExportDTO) {
            throw new NullPointerException("导出文件失败，原因：未传入打印有效参数!");
        }
        if (StringUtils.isBlank(officeExportDTO.getModelName())) {
            throw new NullPointerException("导出文件失败，原因：未指定打印模板!");
        }
        if (StringUtils.isBlank(officeExportDTO.getXmlData())) {
            throw new NullPointerException("导出文件失败，原因：未指定打印数据源!");
        }

        // 解析打印数据
        List<Map<String, Object>> dataList = officeDataService.getValDataList(officeExportDTO.getXmlData());
        if (CollectionUtils.isEmpty(dataList)) {
            throw new NullPointerException("导出文件失败，原因：未获取到打印数据!");
        }

        List<NiceXWPFDocument> documentList = new ArrayList<>(dataList.size());
        // 循环模板并生成文档
        for (int i = 0; i < dataList.size(); i++) {
            NiceXWPFDocument document = this.getNiceXWPFDocument(officeExportDTO.getModelName(), dataList.get(i));
            documentList.add(document);
        }

        return documentList;
    }

    /**
     * @param modelName 模板文件名称
     * @param data      渲染数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 渲染数据获取文档对象
     */
    @Override
    public NiceXWPFDocument getNiceXWPFDocument(String modelName, Map<String, Object> data) {
        // 判断是否有子表
        Set<String> keySet = data.keySet();
        Set<String> subTableIDSet = new HashSet<>(10);
        if (CollectionUtils.isNotEmpty(keySet)) {
            for (String key : keySet) {
                if (StringUtils.isNotBlank(key) && key.startsWith(CommonConstantUtils.PDF_WORD_SUBTABLE_PRE)) {
                    subTableIDSet.add(key);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(subTableIDSet)) {
            // 有子表
            Configure configs = Configure.createDefault();
            for (String subTableID : subTableIDSet) {
                if (subTableID.startsWith(CommonConstantUtils.PDF_WORD_INCELL_TABLE_PRE1) || subTableID.startsWith(CommonConstantUtils.PDF_WORD_INCELL_TABLE_PRE2)) {
                    configs.customPolicy(subTableID, new OfficeInCellTablePolicy(data));
                } else {
                    configs.customPolicy(subTableID, new OfficeDocTablePolicy(data));
                }
            }

            registerUnderLineRender(data, configs);

            // 数据中子表名称去匹配word模板中占位符，匹配上进入对应处理器处理，
            // 例如数据中TABLE_ZT_nssb匹配文档中{{TABLE_ZT_nssb}}，匹配到了则获取到对应table，然后进行处理
            XWPFTemplate template = XWPFTemplate.compile(modelName, configs);
            template.render(data);
            return template.getXWPFDocument();
        } else {
            Configure configs = Configure.createDefault();
            registerUnderLineRender(data, configs);

            // 没有子表
            XWPFTemplate template = XWPFTemplate.compile(modelName, configs);
            template.render(data);
            return template.getXWPFDocument();
        }
    }

    /**
     * 普通字段中特殊格式处理,例如换行 下划线
     * @param data 模板对应数据
     * @param configs
     */
    private void registerUnderLineRender(Map<String, Object> data, Configure configs) {
        for(Map.Entry<String, Object> entry : data.entrySet()) {
            if(StringUtils.contains(String.valueOf(entry.getKey()), CommonConstantUtils.PDF_HHBS)) {
                // 换行处理
                configs.customPolicy(entry.getKey(), new OfficeTextRenderPolicy(data));
            }
        }
    }
}
