package cn.gtmap.realestate.etl.service;

/**
 * Created by lisongtao on 2019/12/1.
 */
public interface ExportService {

    void ExportPdf(String gzlslid, String xmid);

    void ExportPdfPl(String gzlslid);
}
