package cn.gtmap.realestate.common.core.support.pdf.service.impl;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.render.WhereDelegate;
import com.deepoove.poi.template.ElementTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.springframework.stereotype.Service;

import java.util.Map;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.PDF_HHF;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2021/10/20 13:50
 * @description 普通占位符字段中特殊字段处理(例如换行 下划线)
 */
@Service
public class OfficeTextRenderPolicy extends AbstractRenderPolicy<String> {
    /**
     * 整个文档数据
     * 备注：这里因为OfficeDocTablePolicy每次new新建实例，不需要考虑线程安全问题
     */
    private Map<String, Object> officeData;

    public OfficeTextRenderPolicy(){

    }

    public OfficeTextRenderPolicy(Map<String, Object> data) {
        this.officeData = data;
    }

    @Override
    public void doRender(RenderContext<String> renderContext) throws Exception {
        ElementTemplate template = renderContext.getEleTemplate();
        // 占位符名称
        String templateName = template.getTagName();
        // 对应值
        String data = String.valueOf(renderContext.getData());
        WhereDelegate where = renderContext.getWhereDelegate();

        if(StringUtils.startsWith(templateName, CommonConstantUtils.PDF_HHBS)) {
            // 先置空清除占位符
            where.renderText("");
            // 解析换行符号
            String[] array = data.split(PDF_HHF);
            for(int i = 0; i < array.length; i++) {
                where.getRun().setText(array[i]);
                where.getRun().addBreak();
            }
        }
    }
}
