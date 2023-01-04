package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.etl.service.ExportService;
import cn.gtmap.realestate.etl.util.GetQRcode;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lisongtao on 2019/12/1.
 * 导出PDF功能
 */
@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean sendFileCenter(String path, String gzlslid, String fileName) {
        boolean bol = false;
        if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(gzlslid)) {
            StorageDto storageDto = storageClient.createRootFolder("clientId", gzlslid, "不动产电子证照", null);
            if (storageDto != null) {
                //判断是否存在受理文件夹，不存在新增
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, "不动产电子证照");
                if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                    BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
                    }
                    bdcSlSjclDO.setGzlslid(gzlslid);
                    bdcSlSjclDO.setWjzxid(storageDto.getId());
                    bdcSlSjclDO.setClmc("不动产电子证照");
                    bdcSlSjclDO.setFs(1);
                    bdcSlSjclDO.setYs(1);
                    bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
                    bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                }
                File file = new File(path);
                if (file.exists()) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                        if (multipartFile != null) {
                            MultipartDto multipartDto = new MultipartDto();
                            multipartDto.setClientId("clientId");
                            multipartDto.setData(multipartFile.getBytes());
                            multipartDto.setContentType(multipartFile.getContentType());
                            multipartDto.setName(multipartFile.getName());
                            multipartDto.setOriginalFilename(multipartFile.getOriginalFilename());
                            multipartDto.setSize(multipartFile.getSize());
                            multipartDto.setNodeId(storageDto.getId());
                            multipartDto.setSpaceCode(gzlslid);
                            storageClient.multipartUpload(multipartDto);
                            bol=true;
                        }
                    } catch (IOException e) {
                        logger.error(null,e);
                    }
                }
            }
        }
        return bol;
    }

    // 创建目录
    public boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            return true;
        } else {
            return false;
        }
    }

    //递归删除目录下的所有文件及子目录下所有文件
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    @Override
    public void ExportPdf(String gzlslid, String xmid) {
        String year = "";
        String month = "";
        String day = "";
        String bh = "";
        String nf = "";
        String sqsjc = "";
        String szsxqc = "";
        String zhlsh = "";
        String bdcqzh = "";
        String qlr = "";
        String ywr = "";
        String zl = "";
        String bdcdyh = "";
        String qlqtzk = "";
        String fj = "";
        String gyqk = "";
        String qllx = "";
        String qlxz = "";
        String yt = "";
        String mj = "";
        String syqx = "";
        String fileName = "";
        String imageName = "";
        String temp = "";
        String path = "";
        String imgPath = "";
        List<BdcZsDO> listZs = bdcZsInitFeignService.queryBdcqz(xmid);
        if(CollectionUtils.isNotEmpty(listZs)){
            for(BdcZsDO bdcZsDO:listZs){
                bdcqzh+=bdcZsDO.getBdcqzh()+"、";
            }
            bdcqzh=bdcqzh.substring(0,bdcqzh.length()-1);
            fileName = bdcqzh + ".pdf";  //pdf文件名
            temp = "C:/ZZTEMP/"; //临时文件夹名
            createDir(temp);//创建临时文件夹
            path = temp + fileName; //pdf临时路径
            imageName = bdcqzh + ".jpg";//二维码名称
            imgPath = temp + imageName;//二维码临时路径
            BdcZsDO bdcZsDO=listZs.get(0);
            GetQRcode.encoderQRCode(StringValue(bdcZsDO.getEwmnr()),imgPath,null);
            ywr = bdcZsDO.getYwr();
            Date fzrq= bdcZsDO.getFzsj();
            if(fzrq!=null){
                Calendar ca = Calendar.getInstance();
                ca.setTime(fzrq);
                year = String.valueOf(ca.get(Calendar.YEAR));
                month = String.valueOf(ca.get(Calendar.MONTH));
                day = String.valueOf(ca.get(Calendar.DAY_OF_MONTH));
            }
            bh =bdcZsDO.getQzysxlh();
            nf=bdcZsDO.getNf();
            zhlsh=bdcZsDO.getZhlsh();
            qlr=bdcZsDO.getQlr();
            zl=bdcZsDO.getZl();
            bdcdyh=bdcZsDO.getBdcdyh();
            //字典
            List<Map> listGyfs=bdcZdFeignService.queryBdcZd("gyfs");
            if(CollectionUtils.isNotEmpty(listGyfs) && bdcZsDO.getGyfs()!=null){
                gyqk= StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(),listGyfs);
            }
            List<Map> listQllx=bdcZdFeignService.queryBdcZd("qllx");
            if(CollectionUtils.isNotEmpty(listQllx) && bdcZsDO.getQllx()!=null){
                qllx= StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getQllx(),listQllx);
            }
            qlxz =bdcZsDO.getQlxz();
            yt =bdcZsDO.getYt();
            mj= bdcZsDO.getMj();
            qlqtzk =StringUtils.defaultString(bdcZsDO.getQlqtzk(),StringUtils.EMPTY);
            fj=StringUtils.defaultString(bdcZsDO.getFj(),StringUtils.EMPTY);
            syqx=bdcZsDO.getSyqx();
            szsxqc=bdcZsDO.getSzsxqc();
            sqsjc=bdcZsDO.getSqsjc();

            String filePath=this.getClass().getResource("/").getPath();
            //证明
            if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx())) {
                Rectangle pageSize = new Rectangle(2980 / 3, 2110 / 3);
                //新建document
                Document document = new Document(pageSize);

                PdfWriter writer = null;
                try {
                    writer = PdfWriter.getInstance(document, new FileOutputStream(path));
                } catch (DocumentException e) {
                    logger.error(null,e);
                } catch (FileNotFoundException e) {
                    logger.error(null,e);
                }
                //打开文档
                document.open();


                Image image1 = null;
                try {
                    image1 = Image.getInstance(filePath+"../../../../conf/img/证明1.JPG");
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                image1.setAbsolutePosition(0, 0);
                image1.scaleAbsolute(2980 / 3, 2110 / 3);
                try {
                    document.add(image1);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }

                //新增一页
                document.newPage();
                //插入文本
                PdfContentByte cb = writer.getDirectContent();
                //文本字体
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont(filePath+"../../../../conf/img/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                } catch (DocumentException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                cb.beginText();//添加文本
                cb.setFontAndSize(bf, 14);//设置字体
                //给文字设置绝对定位
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(year), 870 / 3, 627 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(month), 1040 / 3, 627 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(day), 1170 / 3, 627 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(bh), 760 / 3, 295 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(sqsjc), 1610 / 3, 1795 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(nf), 1735 / 3, 1795 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(szsxqc), 1950 / 3, 1795 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(zhlsh), 2500 / 3, 1795 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qllx), 1990 / 3, 1650 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qlr), 1990 / 3, 1525 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(ywr), 1990 / 3, 1400 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(zl), 1990 / 3, 1270 / 3, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(bdcdyh), 1990 / 3, 1150 / 3, 0);
                //其他和附记由于字数可能过多，对其拆分换行、调节字体，有待优化
                cb.setFontAndSize(bf, 10);
                String[] qtstr = qlqtzk.split("\n");
                int count = 0;
                for (int i = 0; i < qtstr.length; i++) {
                    while (qtstr[i].length() > 35) {
                        String qtText = qtstr[i].substring(0, 35);
                        qtstr[i] = qtstr[i].substring(35, qtstr[i].length());
                        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qtText), 1990 / 3, (1020 - count * 60) / 3, 0);
                        count = count + 1;
                    }
                    cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qtstr[i]), 1990 / 3, (1020 - count * 60) / 3, 0);
                    count = count + 1;
                }

                String[] fjstr = fj.split("\n");
                count = 0;
                for (int i = 0; i < fjstr.length; i++) {
                    while (fjstr[i].length() > 35) {
                        String fjText = fjstr[i].substring(0, 35);
                        fjstr[i] = fjstr[i].substring(35, fjstr[i].length());
                        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(fjText), 1990 / 3, (580 - count * 60) / 3, 0);
                        count = count + 1;
                    }
                    cb.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(fjstr[i]), 1990 / 3, (580 - count * 60) / 3, 0);
                    count = count + 1;
                }
                cb.endText();
                //添加图片
                Image image2 = null;
                try {
                    image2 = Image.getInstance(filePath+"../../../../conf/img/证明2.JPG");
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                image2.setAbsolutePosition(0, 0);
                image2.scaleAbsolute(2980 / 3, 2110 / 3);
                try {
                    document.add(image2);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }

                //添加二维码
                Image QRCode = null;
                try {
                    QRCode = Image.getInstance(imgPath);
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                QRCode.setAbsolutePosition(300/3,1600/3);
                QRCode.scaleAbsolute(200/3,200/3);
                try {
                    document.add(QRCode);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }
                //关闭流
                document.close();
                writer.close();
                //上传PDF
                sendFileCenter(path, gzlslid, fileName);
                //删除临时文件夹
                deleteDir(new File(temp));
            } else {
                Rectangle pageSize = new Rectangle(3320 / 3, 2390 / 3);
                //新建document
                Document document = new Document(pageSize);
                PdfWriter writer = null;
                try {
                    writer = PdfWriter.getInstance(document, new FileOutputStream(path));
                } catch (DocumentException e) {
                    logger.error(null,e);
                } catch (FileNotFoundException e) {
                    logger.error(null,e);
                }

                document.open();

                Image image1 = null;
                try {
                    image1 = Image.getInstance(filePath+"../../../../conf/img/证书1.JPG");
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                image1.setAbsolutePosition(0, 0);
                image1.scaleAbsolute(3320 / 3, 2390 / 3);
                try {
                    document.add(image1);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }

                document.newPage();

                //插入文本
                PdfContentByte cb1 = writer.getDirectContent();
                //文本字体
                BaseFont bf1 = null;
                try {
                    bf1 = BaseFont.createFont( filePath+"../../../../conf/img/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                } catch (DocumentException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                cb1.beginText();//添加文本
                cb1.setFontAndSize(bf1, 15);//设置字体
                //给文本设置绝对定位
                cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(year), 2540 / 3, 755 / 3, 0);
                cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(month), 2730 / 3, 755 / 3, 0);
                cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(day), 2875 / 3, 755 / 3, 0);
                cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(bh), 2475 / 3, 320 / 3, 0);
                cb1.endText();

                Image image2 = null;
                try {
                    image2 = Image.getInstance( filePath+"../../../../conf/img/证书2.JPG");
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                image2.setAbsolutePosition(0, 0);
                image2.scaleAbsolute(3320 / 3, 2390 / 3);
                try {
                    document.add(image2);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }

                //添加二维码
                Image QRCode = null;
                try {
                    QRCode = Image.getInstance(imgPath);
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                QRCode.setAbsolutePosition(2800/3,1850/3);
                QRCode.scaleAbsolute(200/3,200/3);
                try {
                    document.add(QRCode);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }

                //新增一页
                document.newPage();

                //插入文本
                PdfContentByte cb2 = writer.getDirectContent();
                //文本字体
                BaseFont bf2 = null;
                try {
                    bf2 = BaseFont.createFont( filePath+"../../../../conf/img/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                } catch (DocumentException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                cb2.beginText();
                cb2.setFontAndSize(bf2, 15);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(sqsjc), 210 / 3, 2205 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(nf), 385 / 3, 2205 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(szsxqc), 640 / 3, 2205 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(zhlsh), 1210 / 3, 2205 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qlr), 440 / 3, 2065 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(gyqk), 440 / 3, 1927 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(zl), 440 / 3, 1790 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(bdcdyh), 440 / 3, 1655 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qllx), 440 / 3, 1525 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qlxz), 440 / 3, 1385 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(yt), 440 / 3, 1250 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(mj), 440 / 3, 1115 / 3, 0);
                cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(syqx), 440 / 3, 975 / 3, 0);

                //其他和附记由于字数可能过多，对其拆分换行、调节字体，有待优化
                String[] qtstr = qlqtzk.split("\n");
                int count = 0;
                for (int i = 0; i < qtstr.length; i++) {
                    while (qtstr[i].length() > 35) {
                        String qtText = qtstr[i].substring(0, 35);
                        qtstr[i] = qtstr[i].substring(35, qtstr[i].length());
                        cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qtText), 440 / 3, (845 - count * 80) / 3, 0);
                        count = count + 1;
                    }
                    cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(qtstr[i]), 440 / 3, (845 - count * 80) / 3, 0);
                    count = count + 1;
                }

                String[] fjstr = fj.split("\n");
                count = 0;
                for (int i = 0; i < fjstr.length; i++) {
                    while (fjstr[i].length() > 35) {
                        String fjText = fjstr[i].substring(0, 35);
                        fjstr[i] = fjstr[i].substring(35, fjstr[i].length());
                        cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(fjText), 1870 / 3, (2070 - count * 80) / 3, 0);
                        count = count + 1;
                    }
                    cb2.showTextAligned(PdfContentByte.ALIGN_LEFT, StringValue(fjstr[i]), 1870 / 3, (2070 - count * 80) / 3, 0);
                    count = count + 1;
                }
                cb2.endText();

                Image image3 = null;
                try {
                    image3 = Image.getInstance(filePath+"../../../../conf/img/证书3.JPG");
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                image3.setAbsolutePosition(0, 0);
                image3.scaleAbsolute(3320 / 3, 2390 / 3);
                try {
                    document.add(image3);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }

                document.newPage();

                Image image4 = null;
                try {
                    image4 = Image.getInstance(filePath+"../../../../conf/img/证书4.JPG");
                } catch (BadElementException e) {
                    logger.error(null,e);
                } catch (IOException e) {
                    logger.error(null,e);
                }
                image4.setAbsolutePosition(0, 0);
                image4.scaleAbsolute(3320 / 3, 2390 / 3);
                try {
                    document.add(image4);
                } catch (DocumentException e) {
                    logger.error(null,e);
                }
                //关闭流
                document.close();
                writer.close();
                //上传PDF
                sendFileCenter(path, gzlslid, fileName);
                //删除临时文件夹
                deleteDir(new File(temp));
            }
        }
    }

    @Override
    public void ExportPdfPl(String gzlslid) {
        if(StringUtils.isNotBlank(gzlslid)){
            List<BdcXmDTO> list=bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(list)) {
                for (BdcXmDTO bdcXm : list) {
                    ExportPdf(gzlslid,bdcXm.getXmid());
                }
            }
        }
    }

    /**
     * 获取字符串数据，空的话赋值""
     * @param value
     * @return
     */
    private String StringValue(Object value){
        String val=StringUtils.EMPTY;
        if(value!=null){
            val=value.toString();
        }
        return val;
    }

    private  byte[] getFileByte(String filePath) {
        byte[] result = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            InputStream certStream = classPathResource.getInputStream();
            result = IOUtils.toByteArray(certStream);
        } catch (IOException e) {
            logger.error("getFileByte", e);
        }
        return result;
    }

}