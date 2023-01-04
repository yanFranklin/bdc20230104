package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;


/*
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2019/1/22
 * @description 不动产电子证照PDF业务
 */
public interface BdcDzzzZzxxPdfService {
    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 生成不动产证明电子证照PDF
     */
    byte[] createPdfBdcDzzzZms(BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 生成不动产证书电子证照PDF
     */
    byte[] createPdfBdcDzzzZs(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * @param bdcDzzzZzxx
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 生成电子证照PDF
     */
    byte[] createPdfBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * @param node_Id
     * @param fileName
     * @param out
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 电子证照上传附件中心
     */
    String sendFileCenter(String node_Id, String fileName, byte[] out);

    /**
     * @param nodeid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上传大云文件中心
     * @date : 2022/3/10 11:27
     */
    String sendStorage(String nodeid, String fileName, byte[] out);

    DzzzPdf getDzzzPdf(String zzlxdm);

    DzzzPdf getDzqzPdf(String zzlxdm,String dwdm);

    DzzzResponseModel checkBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx);

    DzzzResponseModel insertBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx);

}
