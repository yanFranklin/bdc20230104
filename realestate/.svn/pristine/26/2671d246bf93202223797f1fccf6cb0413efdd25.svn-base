package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcGdxxFphhFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/11/19
 * @Description: 档案目录打印档案号导入
 */
@Controller
@RequestMapping("/rest/v1.0/dah/import")
public class BdcDamlDahImportController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(BdcDamlDahImportController.class);

    @Autowired
    private BdcGdxxFphhFeignService bdcGdxxFphhFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcSlZdFeignService slZdFeignService;

    @PostMapping("/excel")
    @ResponseBody
    public Object ImportDah(MultipartHttpServletRequest httpServletRequest){
        if(httpServletRequest == null){
            return null;
        }
        int count = 0;
        InputStream fileIn = null;
        try {
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);
                count = importExcelToObject1(BdcGdxxFphhDO.class, 0, workbook);
            }
        } catch (IOException | BiffException | IllegalAccessException | InstantiationException | ParseException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new AppException("导入失败");
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }
        return count;
    }

    private int importExcelToObject1(Class dataClass, Integer sheetNumber, Workbook workbook) throws InstantiationException, IllegalAccessException, ParseException {
        Sheet dataSheet = workbook.getSheet(sheetNumber);

        List<String> dataList = new ArrayList();
        ArrayList<BdcGdxxFphhDO> bdcGdxxFphhDOS = new ArrayList<>();
        // 遍历Excel
        for (int i = 1; i < dataSheet.getRows(); i++) {

            Object dataObject = dataClass.newInstance();
            BdcGdxxFphhDO bdcGdxxFphhDO = new BdcGdxxFphhDO();

            for (int j = 1; j < 3; j++) {
                String contentData = dataSheet.getCell(j, i).getContents();
                switch (j){
                    case 1 :
                        bdcGdxxFphhDO.setSlbh(contentData);
                        break;
                    case 2:
                        bdcGdxxFphhDO.setAjh(contentData);
                        break;
                    default:
                        break;
                }
            }
            bdcGdxxFphhDOS.add(bdcGdxxFphhDO);
        }
        logger.info("根据excel生成的bdcGdxxFphhDOS:{}", bdcGdxxFphhDOS);
        if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOS)){
            for (BdcGdxxFphhDO bdcGdxxFphhDO : bdcGdxxFphhDOS) {
                if(bdcGdxxFphhDO!=null && bdcGdxxFphhDO.getSlbh()!=null&&bdcGdxxFphhDO.getAjh()!=null){
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setSlbh(bdcGdxxFphhDO.getSlbh());
                    List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
                        bdcGdxxFphhDO.setXmid(bdcXmDO.getXmid());
                        bdcGdxxFphhDO.setQlr(bdcXmDO.getQlr());
                        bdcGdxxFphhDO.setGzlslid(bdcXmDO.getGzlslid());
                        bdcGdxxFphhDO.setXzdm(BdcdyhToolUtils.getXzdm(bdcXmDO.getBdcdyh()));
                        List<Map> zdList = slZdFeignService.queryBdcSlzd("jddm");
                        if(CollectionUtils.isNotEmpty(zdList)) {
                            for(Map zd : zdList) {
                                if(StringUtils.equals(bdcGdxxFphhDO.getXzdm(), (String) zd.get("DM"))) {
                                    bdcGdxxFphhDO.setXzmc((String) zd.get("MC"));
                                }
                            }
                        }
                        bdcGdxxFphhDO.setNf(DateUtils.getCurrYear());
                        if(StringUtils.contains(bdcGdxxFphhDO.getAjh(),"ZM")){
                            bdcGdxxFphhDO.setDahlx("ZM");
                        }else{
                            bdcGdxxFphhDO.setDahlx("ZS");
                        }
                        int length = bdcGdxxFphhDO.getAjh().length();
                        bdcGdxxFphhDO.setDahscsj(DateUtils.formatDate(bdcGdxxFphhDO.getAjh().substring(length-12,length-4),"yyyyMMdd"));
                        dataList.add(bdcGdxxFphhFeignService.saveDah(bdcGdxxFphhDO));
                    }
                }
            }
        }
        return dataList.size();
    }
}
