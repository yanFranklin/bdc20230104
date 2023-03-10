package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyDjdyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyCshDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyGgxxDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZrzkYwxxDTO;
import cn.gtmap.realestate.common.core.enums.natural.ZrzyZrzklxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.natural.*;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZrzkVO;
import cn.gtmap.realestate.common.util.Base64Util;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.QRcodeUtils;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.security.auth.login.Configuration;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description ????????????????????????
 */
@RestController
@RequestMapping("/rest/v1.0/ywxx")
public class ZrzyYwxxController {

    @Autowired
    ZrzyYwxxFeignService zrzyYwxxFeignService;

    @Autowired
    ZrzyEntityFeignService zrzyEntityFeignService;

    @Autowired
    ZrzyZdFeignService zrzyZdFeignService;

    @Autowired
    private ZrzyInitFeignService zrzyInitFeignService;

    @Value("${bdcswt.url:}")
    private String bdcswtUrl;

    @Value("${bdcswt.ftpath:F:/}")
    private String ftPath;

    @Value("${djgg.dh:}")
    private String djggdh;

    @Value("${djgg.dz:}")
    private String djggdz;


    @Value("${ywxx.slr:?????????}")
    private String slr;

    @Value("${ywxx.shr:?????????}")
    private String shr;

    @Autowired
    ZrzyGgFeignService zrzyGgFeignService;

    /**
     * @param gzlslid ???????????????ID
     * @return ??????????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????ID????????????????????????????????????
     */
    @GetMapping("/{gzlslid}")
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(@PathVariable(name = "gzlslid") String gzlslid) {
        return zrzyYwxxFeignService.queryZrzySlymYwxxDTO(gzlslid);
    }

    /**
     * @param gzlslid ???????????????ID
     * @return ??????????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????ID????????????????????????????????????
     */
    @GetMapping("/zrzkxx/{gzlslid}")
    public ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(@PathVariable(name = "gzlslid") String gzlslid) {
        return zrzyYwxxFeignService.queryZrzyZrzkYwxxDTO(gzlslid);
    }

    /**
     * @param zkxxid ????????????ID
     * @param zrzklx ??????????????????
     * @return ??????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    @GetMapping("/zrzkxx/{zkxxid}/{zrzklx}")
    public ZrzyZrzk queryZrzyZrzk(@PathVariable(name = "zkxxid") String zkxxid, @PathVariable(name = "zrzklx") String zrzklx) {
        return zrzyYwxxFeignService.queryZrzyZrzk(zkxxid, zrzklx);
    }

    /**
     * @param json ???????????????
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    @PatchMapping("/zrzydjdy")
    public Integer updatZrzyDjdj(@RequestBody String json) {
        return zrzyEntityFeignService.updateByJsonEntity(json, ZrzyDjdyDO.class.getName());
    }

    /**
     * @param json ???????????????
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    @PatchMapping("/zrzkxx/{zrzklx}")
    public Integer updatZrzyZrzk(@RequestBody String json, @PathVariable(name = "zrzklx") String zrzklx) {
        //????????????
        Class<?> zrzkClass = ZrzyZrzklxEnum.getZrzkClassByCode(zrzklx);
        if (zrzkClass == null) {
            throw new AppException("????????????????????????,?????????");
        }
        return zrzyEntityFeignService.updateByJsonEntity(json, zrzkClass.getName());
    }

    /**
     * @return ?????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????
     */
    @GetMapping("/zd")
    public Map<String, List<Map>> queryZrzyZd() {
        return zrzyZdFeignService.listZrzyzd();
    }


    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "?????????????????????????????????", notes = "?????????????????????????????????")
    @ApiImplicitParam(name = "zrzyCshDTO", value = "?????????????????????", required = true, dataType = "ZrzyCshDTO", paramType = "body")
    @PostMapping(value = "/csh")
    public String ywxxCsh(@RequestBody ZrzyCshDTO zrzyCshDTO) {
        return zrzyInitFeignService.ywxxCsh(zrzyCshDTO);
    }


    /**
     * @param json ???????????????
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    @GetMapping("/bdcswt")
    public String bdcswt() {
        return bdcswtUrl;
    }


    /**
     * @param gzlslid ???????????????ID
     * @return ??????????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????ID????????????????????????
     */
    @GetMapping("/ggxx/{gzlslid}")
    public List<ZrzyGgxxDTO> queryZrzyGgmbxxDTO(@PathVariable(name = "gzlslid") String gzlslid) {
        List<ZrzyGgxxDTO> zrzyGgxxDTOS = new ArrayList<>();
        ZrzySlymYwxxDTO zrzySlymYwxxDTO = zrzyYwxxFeignService.queryZrzySlymYwxxDTO(gzlslid);
        if (CollectionUtils.isNotEmpty(zrzySlymYwxxDTO.getZrzyZrzkList())) {
            for (ZrzyZrzkVO zrzyZrzkVO : zrzySlymYwxxDTO.getZrzyZrzkList()) {
                ZrzyGgxxDTO zrzyGgxxDTO = new ZrzyGgxxDTO();
                BeanUtils.copyProperties(zrzyZrzkVO, zrzyGgxxDTO);
                Double mj = new Double(0);
                if (Objects.nonNull(zrzyZrzkVO.getGymj())) {
                    mj += zrzyZrzkVO.getGymj();
                }
                if (Objects.nonNull(zrzyZrzkVO.getJtmj())) {
                    mj += zrzyZrzkVO.getJtmj();
                }
                if (Objects.nonNull(zrzyZrzkVO.getZyqmj())) {
                    mj += zrzyZrzkVO.getZyqmj();
                }
                zrzyGgxxDTO.setMj(mj);
                BeanUtils.copyProperties(zrzySlymYwxxDTO.getZrzyXm(), zrzyGgxxDTO);
                BeanUtils.copyProperties(zrzySlymYwxxDTO.getZrzyDjdy(), zrzyGgxxDTO);
                zrzyGgxxDTOS.add(zrzyGgxxDTO);
            }
        }
        return zrzyGgxxDTOS;
    }

    /**
     * @param gzlslid
     */
    @GetMapping("/ggxx/mb/{gzlslid}")
    public Object getGgmbxx(@PathVariable(name = "gzlslid") String gzlslid) {
        return zrzyYwxxFeignService.queryZrzyGgmbxxDTO(gzlslid);
    }

    /**
     * @param xmid
     * @param mbnr
     */
    @GetMapping("/ggxx/mb/update/{xmid}/{mbnr}")
    public void updateGgmbxx(@PathVariable(name = "xmid") String xmid,
                             @PathVariable(name = "mbnr") String mbnr) {
        zrzyGgFeignService.updateZrzyGgmb(xmid, mbnr);
    }

    /**
     * @param xmid
     * @param mbnr
     */
    @GetMapping("/ggxx/mb/add/{xmid}/{mbnr}")
    public void addGgmbxx(@PathVariable(name = "xmid") String xmid,
                          @PathVariable(name = "mbnr") String mbnr) {
        zrzyGgFeignService.insertZrzyGgmb(xmid, mbnr);
    }

    /**
     * ?????????????????????word
     *
     * @param xmid
     * @param mbnr
     */
    @PostMapping("/ggxx/mb/download")
    public void downloadGgmbxx(@RequestParam(value = "nr") String nr, HttpServletResponse response) {
        try {
            String content = "<!DOCTYPE html>\n" +
                    "<html lang=\"zh-cn\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "    <title>????????????</title>\n" +
                    "    <meta name=\"renderer\" content=\"webkit\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0\">\n" +
                    "    <style>\n" +
                    "        /*??????????????????*/\n" +
                    "        body, html {\n" +
                    "            font-family: \"Microsoft YaHei\", Helvetica, Arial, sans-serif !important;\n" +
                    "            font-size: 14px;\n" +
                    "        }\n" +
                    "        div{\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            -webkit-tap-highlight-color: rgba(0,0,0,0);\n" +
                    "        }\n" +
                    "        button, input, optgroup, option, select, textarea {\n" +
                    "            display:none" +
                    "            font-family: inherit;\n" +
                    "            font-size: inherit;\n" +
                    "            font-style: inherit;\n" +
                    "            font-weight: inherit;\n" +
                    "            outline: 0;\n" +
                    "        }\n" +
                    "        blockquote, body, button, dd, div, dl, dt, form, h1, h2, h3, h4, h5, h6, input, li, ol, p, pre, td, textarea, th, ul {\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            -webkit-tap-highlight-color: rgba(0,0,0,0);\n" +
                    "        }\n" +
                    "       .title-btn-area  {\n" +
                    "           display:none;\n" +
                    "        }\n" +
                    "        p{\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            -webkit-tap-highlight-color: rgba(0,0,0,0);\n" +
                    "        }\n" +
                    "        .content-main-head {\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "        table {\n" +
                    "            position: relative !important;\n" +
                    "        }\n" +
                    "        .layui-table {\n" +
                    "            width: 100%;\n" +
                    "            margin-bottom: 20px;\n" +
                    "        }\n" +
                    "        table {\n" +
                    "            border-collapse: collapse;\n" +
                    "            border-spacing: 0;\n" +
                    "            border: 0px;\n" +
                    "        }\n" +
                    "        tr, td {\n" +
                    "            color: #333;\n" +
                    "        }\n" +
                    "        .layui-table td.set-back-color {\n" +
                    "            background-color: #f3f4f6;\n" +
                    "            padding: 0 6px;\n" +
                    "        }\n" +
                    "        .layui-table tr {\n" +
                    "            transition: all .3s;\n" +
                    "            -webkit-transition: all .3s;\n" +
                    "        }\n" +
                    "        .layui-table td, .layui-table th {\n" +
                    "            padding: 0;\n" +
                    "            height: 39px;\n" +
                    "            overflow: visible;\n" +
                    "            position: static;\n" +
                    "        }\n" +
                    "        .layui-table,.layui-table td, .layui-table th,.layui-table tr,.layui-table[lay-skin=line], .layui-table[lay-skin=row] {\n" +
                    "            border-color: #d0d5da;\n" +
                    "        }\n" +
                    "        /*??????????????????*/\n" +
                    "        .djgg_table tbody td{\n" +
                    "            height:90px;\n" +
                    "        }\n" +
                    "        .content-title {\n" +
                    "            padding: 10px 0;\n" +
                    "            border: 0px;\n" +
                    "            background-color: #fff;\n" +
                    "            height: 58px;\n" +
                    "            clear: both;\n" +
                    "            top: 10px;\n" +
                    "            left: 10px;\n" +
                    "            z-index: 9;\n" +
                    "            box-sizing: border-box;\n" +
                    "            padding-top: 10px;\n" +
                    "        }\n" +
                    "        .content-div {\n" +
                    "            width: 1200px;\n" +
                    "            height: 100%;\n" +
                    "            padding: 68px 10px 10px 10px;\n" +
                    "            box-sizing: border-box;\n" +
                    "            margin: 0 auto;\n" +
                    "        }\n" +
                    "        .content-div .bdc-content-box {\n" +
                    "            width: 100%;\n" +
                    "            min-height: 100%;\n" +
                    "            overflow-x: auto;\n" +
                    "            background-color: #fff;\n" +
                    "        }\n" +
                    "        .content{\n" +
                    "           margin-bottom: 20px;\n" +
                    "        }\n" +
                    "        .content-div form {\n" +
                    "            background-color: #fff;\n" +
                    "            width: 1000px;\n" +
                    "            margin: 0 auto;\n" +
                    "            box-sizing: border-box;\n" +
                    "            padding: 20px  10px 0px 10px;\n" +
                    "        }\n" +
                    "        .bdc-content-fix {\n" +
                    "            width: 100%;\n" +
                    "            height: 58px;\n" +
                    "            padding: 0 10px;\n" +
                    "            box-sizing: border-box;\n" +
                    "            position: fixed;\n" +
                    "            top: 10px;\n" +
                    "            left: 0;\n" +
                    "            z-index: 999;\n" +
                    "        }\n" +
                    "        .content-title p {\n" +
                    "            font-size: 16px;\n" +
                    "            color: #333;\n" +
                    "            float: left;\n" +
                    "            margin-left: 10px;\n" +
                    "            line-height: 38px;\n" +
                    "        }\n" +
                    "        .content-title p {\n" +
                    "            float: none;\n" +
                    "            text-align: center;\n" +
                    "            font-size: 24px;\n" +
                    "            font-weight:bold;\n" +
                    "        }\n" +
                    "        .content-title input{\n" +
                    "            border: 0;\n" +
                    "            outline: 0;\n" +
                    "            max-width: 100px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .content-main-head,.content-foot{\n" +
                    "            font-size: 20px;\n" +
                    "        }\n" +
                    "        .content-main-head input{\n" +
                    "            border: 0px;\n" +
                    "            width: 20px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .content-main-head .year{\n" +
                    "            border: 0px;\n" +
                    "            width: 20px;\n" +
                    "            text-align: left;\n" +
                    "        }\n" +
                    "        .content-main-head .content{\n" +
                    "            text-indent:40px;\n" +
                    "        }\n" +
                    "        .djgg_table td{\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .content-foot{\n" +
                    "            margin-bottom: 20px;\n" +
                    "        }\n" +
                    "        .content-foot input{\n" +
                    "            border: 0;\n" +
                    "            outline: 0;\n" +
                    "            width: 400px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .content-foot p,.fj p{\n" +
                    "            text-indent:40px;\n" +
                    "        }\n" +
                    "        .foot_sign{\n" +
                    "            float: right;\n" +
                    "            text-align: left;\n" +
                    "            margin-top: 20px;margin-left: 1000px;\n" +
                    "        }\n" +
                    "        .foot_sign input{\n" +
                    "            border: 0px;\n" +
                    "            width: 60px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .fj{\n" +
                    "            margin-top: 20px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"content-div\">\n" +
                    "    {content}\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            //????????????
            content = content.replace("{content}", URLDecoder.decode(nr));
            content = content.replace("border:2px solid black;", "");

            //???????????????????????????????????????
            String imgSrc = getImgSrc(content);
            if(StringUtils.isNotBlank(imgSrc)){
                String imageFilePath = ftPath + UUID.randomUUID() + ".jpg";
                String ewmnr = imgSrc.substring(imgSrc.indexOf("ewmnr=") + 6, imgSrc.length());
                try {
                    QRcodeUtils.encoderQRCode(ewmnr, imageFilePath, response);
                    String imageContent = "data:image/gif;base64," + Base64Util.ImageToBase64ByLocal(imageFilePath);
                    content = content.replace(imgSrc, imageContent);
                    //?????????????????????
                    File file = new File(imageFilePath);
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //?????????word
            String fileName = URLEncoder.encode("????????????" + DateUtils.formateTimeYmdhms(new Date()) + ".doc", "utf-8");
            byte b[] = content.getBytes("utf-8");
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            POIFSFileSystem poifs = new POIFSFileSystem();
            poifs.createDocument(bais, "WordDocument");
            try (OutputStream ostream = response.getOutputStream();) {
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/msword");
                poifs.writeFilesystem(ostream);
                bais.close();
            } catch (Exception e) {
                throw new AppException("???????????????????????????");

            }
        } catch (Exception e) {

        }
    }


    /**
     * @param gzlslid ???????????????ID
     * @return ??????????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????ID????????????????????????????????????
     */
    @GetMapping("/djggdh")
    public String queryDjggdh() {
        if (StringUtils.isNotBlank(djggdh)) {
            return djggdh;
        } else {
            return "";
        }
    }

    /**
     * @param gzlslid ???????????????ID
     * @return ??????????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????ID????????????????????????????????????
     */
    @GetMapping("/djggdz")
    public String queryDjggdz() {
        if (StringUtils.isNotBlank(djggdz)) {
            return djggdz;
        } else {
            return "";
        }
    }


    /**
     * @param gzlslid ???????????????ID
     * @return ??????????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????ID????????????????????????????????????
     */
    @GetMapping("/ft/{gzlslid}")
    public void queryZrzySlymYwxxFt(@PathVariable(name = "gzlslid") String gzlslid,
                                    HttpServletResponse response) {
        try {
            ZrzySlymYwxxDTO zrzySlymYwxxDTO = zrzyYwxxFeignService.queryZrzySlymYwxxDTO(gzlslid);
            String zrzydjdyh = zrzySlymYwxxDTO.getZrzyXm().getZrzydjdyh();
            File file = new File(ftPath + zrzydjdyh + ".jpg");
            BufferedImage image = ImageIO.read(new FileInputStream(file));
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "jpg", os);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param ewmnr ???????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("?????????????????????")
    @ApiImplicitParam(name = "ewmnr", value = "???????????????", required = true, dataType = "string", paramType = "path")
    @GetMapping("/ewm")
    public void packEwmPic(HttpServletResponse response,
                           @RequestParam(name = "ewmnr", required = false) String ewmnr,
                           @RequestParam(name = "tmlx", required = false) String tmlx
    ) {
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        if (StringUtils.isBlank(tmlx) || "ewm".equals(tmlx)) {
            //?????????
            QRcodeUtils.encoderQRCode(ewmnr, null, response);
        } else if ("txm".startsWith(tmlx)) {
            //?????????
            QRcodeUtils.encoderCode(ewmnr, null, BarcodeFormat.CODE_128, response);
        }
    }


    /**
     * ??????img?????????src
     */
    public static String getImgSrc(String content) {

        List<String> list = new ArrayList<String>();
        //??????img???????????????3????????????
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //????????????content??????<img />??????
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //??????????????????<img />??????????????????
                String str_img = m_img.group(2);

                //????????????<img />????????????src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    list.add(str_src);
                }
                //????????????<img />????????????src

                //??????content????????????????????????<img />???????????????????????????????????????<img />????????????src
                result_img = m_img.find();
            }
        }
        //?????????????????????????????????????????????
        List<String> srcstemp = new ArrayList<>();
        for (String imageSrc : list) {
            if (!imageSrc.contains("/static") && !imageSrc.contains("/emotion")) {
                srcstemp.add(imageSrc);
            }
        }
        list = srcstemp;
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    /**
     * ?????????????????????
     * @return
     */
    @GetMapping("/fqslr")
    public String querySlr() {
        if (StringUtils.isNotBlank(slr)) {
            return slr;
        } else {
            return "";
        }
    }

    /**
     * ?????????????????????
     * @return
     */
    @GetMapping("/fqshr")
    public String queryShr() {
        if (StringUtils.isNotBlank(shr)) {
            return shr;
        } else {
            return "";
        }
    }
}
