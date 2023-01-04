package cn.gtmap.realestate.certificate.core.model.abstraction;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/5 水印
 */
public abstract class Watermark {

    protected abstract boolean addWatermark(float oddTextX, float oddTextY
            ,float evenTextX,float evenTextY,int yInterval)  throws DocumentException, IOException;

}
