package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.dto.ZzqzConfig;
import cn.gtmap.realestate.certificate.core.mapper.BdcZsMapper;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.ECertificate2Service;
import cn.gtmap.realestate.certificate.service.ECertificateService;
import cn.gtmap.realestate.certificate.util.ECertificateUtils;
import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcGgywsjDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcCzDzqzZtxxDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzCsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzEZsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsTimeStampDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.common.core.enums.BdcGglxEnum;
import cn.gtmap.realestate.common.core.enums.BdcZzqzTsztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.changzhou.BdcGgFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzqzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.inquiryui.BdcGgVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.validator.certificate.DzzzZm;
import cn.gtmap.realestate.common.util.validator.certificate.DzzzZs;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;


/**
 * 常州电子签章
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 上午9:55 2021/1/8
 */
@Service
public class ECertificate2ServiceImpl implements ECertificate2Service {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ECertificate2ServiceImpl.class.getName());

    /**
     * 金坛区行政区划
     */
    @Value("${xzqh.jintan:320413}")
    private String jintanXzqh;
    @Value("${print.path:}")
    private String printPath;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    PdfController pdfController;

    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private ECertificateUtils eCertificateUtils;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private ECertificateService eCertificateService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private BdcZsxmFeignService zsxmFeignService;
    @Autowired
    ZzqzConfig zzqzConfig;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZsMapper bdcZsMapper;
    @Autowired
    BdcDzqzFeignService bdcDzqzFeignService;
    @Value("${dzqz.new:false}")
    private boolean newDzqz;

    @Autowired
    BdcGgFeignService bdcGgFeignService;

    /**
     * 注销/作废 电子印章
     *
     * @param xmid xmid
     * @param bgyy 当前用户名
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<DzzzResponseData> cancelZzqz(String xmid, Integer bgyy) {
        List<DzzzResponseData> responseDatas = Lists.newArrayList();
        try {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setXmid(xmid);
            List<BdcZsDO> bdcZsDOList = bdcZsService.listBdcZs(bdcZsQO);
            for (BdcZsDO bdcZsDO : bdcZsDOList) {
                responseDatas.add(this.zzzx(bdcZsDO.getZsid(), bgyy));
            }
        } catch (Exception e) {
            LOGGER.error("项目{}注销电子印章失败！", xmid, e);
        }
        return responseDatas;
    }

    /**
     * 生成项目对应的证书电子印章
     *
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<DzzzResponseData> mbpzPdf(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        List<DzzzResponseData> responseDatas = Lists.newArrayList();

        BdcXmDO bdcXmDO = bdcXmService.queryBdcXm(xmid);
        String smzt = this.getSmct(bdcXmDO);
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(xmid);
        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            if (StringUtils.isNotBlank(bdcZsDO.getZzid())) {
                continue;
            }
            responseDatas.add(this.tsdzqz(bdcXmDO, smzt, bdcZsDO));
        }
        return responseDatas;
    }

    /**
     * 推送单个证书电子印章（常州）
     * @param zsid 证书id
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public void tsBdcZsDzqz(String zsid) {
        if(StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("未定义要推送印章的证书信息");
        }

        BdcZsDO bdcZsDO = bdcZsService.queryBdcZs(zsid);
        if(null == bdcZsDO || StringUtils.isBlank(bdcZsDO.getBdcqzh())) {
            throw new MissingArgumentException("未查询到证号信息，无法推送");
        }

        List<BdcXmDO> xmDOList = bdcXmService.listBdcXmByZsid(zsid);
        if(CollectionUtils.isEmpty(xmDOList) || null == xmDOList.get(0)) {
            throw new MissingArgumentException("未查询到证书关联的项目信息，无法推送");
        }

        BdcXmDO bdcXmDO = xmDOList.get(0);
        String smzt = this.getSmct(bdcXmDO);
        tsdzqz(bdcXmDO, smzt, bdcZsDO);
    }

    /**
     * 生成电子印章扫码查图字段信息
     * @param bdcXmDO 项目信息
     * @return {String} 扫码查图字段
     */
    private String getSmct(BdcXmDO bdcXmDO) {
        //	smct字段由bdcdyh&bdcdywybm&slbh组合而成，例如 320412001006GB95101F02730009&4667849&202110060009
        String smzt = "";
        if (StringUtils.isNotBlank(bdcXmDO.getSlbh()) && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
            smzt = bdcXmDO.getBdcdyh() + CommonConstantUtils.TS_FH_1 + (StringUtils.isNotBlank(bdcXmDO.getBdcdywybh()) ? bdcXmDO.getBdcdywybh() : "") + CommonConstantUtils.TS_FH_1 + bdcXmDO.getSlbh();
        }
        return smzt;
    }

    /**
     * （常州）推送电子印章
     * @param bdcXmDO 项目信息
     * @param smct 扫码查图字段
     * @param bdcZsDO 证书信息
     * @return {DzzzResponseData} 推送返回信息
     */
    private DzzzResponseData tsdzqz(BdcXmDO bdcXmDO, String smct, BdcZsDO bdcZsDO) {
        try {
            EZsTimeStampDTO eZsTimeStampDTO = eCertificateUtils.generateEZsTimeStampDTO(bdcXmDO, bdcZsDO);
            // 校验参数
            ValidatorUtils.validate(eZsTimeStampDTO, CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx()) ? DzzzZs.class : DzzzZm.class);

            DzqzEZsDTO dzqzEZsDTO = new DzqzEZsDTO();
            BeanUtils.copyProperties(eZsTimeStampDTO, dzqzEZsDTO);
            dzqzEZsDTO.setSeal(zzqzConfig.getSeal());
            dzqzEZsDTO.setPosition(zzqzConfig.getPosition());
            dzqzEZsDTO.setSmct(smct);
            dzqzEZsDTO.setSmjt("我的常州APP→不动产登记→扫码见图");

            JSONObject param = new JSONObject();
            param.put("data", dzqzEZsDTO);
            LOGGER.info("常州电子印章最终组合传入参数：{}", param);
            Object mbpzpdf;
            if (newDzqz) {
                mbpzpdf = bdcDzqzFeignService.czdzqz(JSON.toJSONString(param));
            } else {
                String beanName = jintanXzqh.equals(bdcXmDO.getQxdm()) ? "mbpzpdf_jt" : "mbpzpdf";
                mbpzpdf = exchangeInterfaceFeignService.requestInterface(beanName, param);
            }
            DzzzResponseData data = eCertificateUtils.dealDzzzResponse(mbpzpdf, "生成证照");

            // 回写证照标示到证书表，同步处理证照签发时间
            BdcZsDO bdcZsDOTemp = new BdcZsDO();
            bdcZsDOTemp.setZsid(bdcZsDO.getZsid());
            // 常州电子印章 zzbs 使用合肥 zzid 字段
            bdcZsDOTemp.setZzid(data.getZzbs());
            LOGGER.info("电子印章印章完成，更新证书id:{}, dzqzbs ：{}", bdcZsDOTemp.getZsid(), bdcZsDOTemp.getZzid());
            if (StringUtils.isNotBlank(data.getZzbs())) {
                bdcZsService.updateBdcZs(bdcZsDOTemp);
            }

            if(data.isStatus()){
                saveTsqzZtxx(bdcZsDO, BdcZzqzTsztEnum.YTSWXZ.getValue(), data.getInfo());
            }else{
                saveTsqzZtxx(bdcZsDO, BdcZzqzTsztEnum.TSSB.getValue(), data.getInfo());
            }
            return data;
        } catch (Exception e) {
            saveTsqzZtxx(bdcZsDO, BdcZzqzTsztEnum.TSSB.getValue(), JSON.toJSONString(e));
            LOGGER.error("推送电子印章失败", e);
            throw new AppException("推送印章失败");
        }
    }

    /**
     * 保存推送电子印章状态信息
     * @param bdcZsDO 证书信息
     * @param tszt 推送状态
     * @param rzxx 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void saveTsqzZtxx(BdcZsDO bdcZsDO, Integer tszt, String rzxx) {
        BdcCzDzqzZtxxDO dzqzZtxxDO = new BdcCzDzqzZtxxDO();
        dzqzZtxxDO.setZsid(bdcZsDO.getZsid());
        dzqzZtxxDO.setBdcqzh(bdcZsDO.getBdcqzh());
        dzqzZtxxDO.setTsry(userManagerUtils.getCurrentUserName());
        dzqzZtxxDO.setTssj(new Date());
        dzqzZtxxDO.setTszt(tszt);
        dzqzZtxxDO.setRzxx(StringUtils.substring(rzxx, 0 ,1000));
        entityMapper.saveOrUpdate(dzqzZtxxDO, dzqzZtxxDO.getZsid());
    }

    /**
     * 常州电子印章 文件下载
     *
     * @param gzlslid gzlslid
     * @param zsid    证书id
     * @param symd    使用目的
     * @return path 大云平台下载地址
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    public String zzxxxz(String gzlslid, String zsid, Integer symd) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("未定义下载印章项目证书信息");
        }
        List<BdcXmDO> xmDOList = zsxmFeignService.listBdcXmByZsid(zsid);
        if (CollectionUtils.isEmpty(xmDOList) || null == xmDOList.get(0)) {
            throw new MissingArgumentException("未查询到项目信息");
        }
        if (StringUtils.isBlank(gzlslid) || "null".equals(gzlslid)) {
            gzlslid = xmDOList.get(0).getGzlslid();
        }
        String slbh = xmDOList.get(0).getSlbh();

        BdcZsDO zsxx = this.queryZzbsByZsid(zsid);

        // 组织下载证照请求参数
        JSONObject data = new JSONObject();
        data.put("zzbs", zsxx.getZzid());
        data.put("dzzzsymd", symd);
        data.put("slbh", slbh);
        JSONObject request = new JSONObject();
        request.put("data", data);

        try {
            String beanName = jintanXzqh.equals(xmDOList.get(0).getQxdm()) ? "zzxxxz_jt" : "zzxxxz";
            Object mbpzpdf = exchangeInterfaceFeignService.requestInterface(beanName, request);
            DzzzResponseData responseData = eCertificateUtils.dealDzzzResponse(mbpzpdf, "下载证照");
            if (StringUtils.isBlank(responseData.getContent())) {
                throw new AppException("下载证照失败，返回内容为空！");
            }

            // 上传 base64 文件到大云
            String currentUserName = userManagerUtils.getCurrentUserName();
            StorageDto storageDto = eCertificateService.uploadECertificateToWjzx(gzlslid, null,
                    eCertificateUtils.getZzqzConfig().getFoldName() + CommonConstantUtils.WJHZ_PDF, currentUserName,
                    responseData.getContent());
            if (storageDto == null) {
                throw new AppException("上传附件到大云失败！");
            }

            // 保存下载地址到证照地址
            if (StringUtils.isBlank(storageDto.getDownUrl())) {
                LOGGER.info("上传大云平台后，返回 storage 对象：{}", JSON.toJSONString(storageDto));
                throw new AppException("上传大云后未返回 path 或下载地址！");
            }

            BdcZsDO bdcZsDO = new BdcZsDO();
            bdcZsDO.setZsid(zsid);
            // 常州电子印章地址使用合肥 zzdz 字段
            bdcZsDO.setZzdz(storageDto.getDownUrl());
            bdcZsService.updateBdcZs(bdcZsDO);
            LOGGER.info("电子印章下载文件，更新证书:{}, 证照地址：{}", zsid, storageDto.getDownUrl());
            saveTsqzZtxx(zsxx, BdcZzqzTsztEnum.YTSYXZ.getValue(), "");

            return storageDto.getDownUrl();
        } catch (Exception e) {
            saveTsqzZtxx(zsxx, BdcZzqzTsztEnum.YTSXZSB.getValue(), JSON.toJSONString(e));
            throw e;
        }
    }

    /**
     * 注销/作废 电子印章
     *
     * @param zsid 证书 id
     * @param bgyy 变更原因
     * @return void
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    public DzzzResponseData zzzx(String zsid, Integer bgyy) {
        if(null != bgyy && 2 == bgyy) {
            // 作废
            if (StringUtils.isBlank(zsid)) {
                throw new MissingArgumentException("zsid");
            }
            // 查询证书信息
            BdcZsDO zsxx = bdcZsService.queryBdcZs(zsid);
            if (null == zsxx) {
                throw new AppException("未查询到证书信息，zsid：" + zsid);
            }

            // 作废直接清除 zzid 和 zzdz 字段值
            bdcZsMapper.updateBdcZsZzqzNull(zsid);
            LOGGER.info("作废电子签章，已清除zzid、zzdz，对应zsid：{}", zsid);
            saveTsqzZtxx(zsxx, BdcZzqzTsztEnum.YZX.getValue(), "");
            DzzzResponseData responseData = new DzzzResponseData();
            responseData.setStatus(Boolean.TRUE);
            return responseData;
        } else {
            // 注销
            BdcZsDO zsxx = this.queryZzbsByZsid(zsid);
            // 组织请求参数
            JSONObject data = new JSONObject();
            data.put("zzbs", zsxx.getZzid());
            data.put("zzbgyy", bgyy);

            String beanName = jintanXzqh.equals(zsxx.getQxdm()) ? "zzzx_jt" : "zzzx";
            Object zzzx = exchangeInterfaceFeignService.requestInterface(beanName, data);
            DzzzResponseData responseData = eCertificateUtils.dealDzzzResponse(zzzx, "注销证照");
            BdcZsDO bdcZsDO = new BdcZsDO();
            bdcZsDO.setZsid(zsid);
            // 常州电子印章 zzbs 使用合肥 zzid 字段
            bdcZsDO.setZzid(responseData.getZzbs());
            bdcZsService.updateBdcZs(bdcZsDO);
            LOGGER.info("电子印章作废/注销， 更新证书:{}, 证照标识别：{}", zsid, responseData.getZzbs());
            saveTsqzZtxx(zsxx, BdcZzqzTsztEnum.YZX.getValue(), "");
            return responseData;
        }
    }


    /**
     * 常州证照印章服务
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void changzhouZzqz(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("processsInsId");
        }

        List<BdcXmDO> bdcXmDOS = bdcXmService.listBdcXmByProInsID(processInsId);
        for (BdcXmDO bdcXmDO : bdcXmDOS) {
            this.mbpzPdf(bdcXmDO.getXmid());
        }
    }

    /**
     * @param ggid 公告信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 公告推送签章
     * @date : 2022/3/14 9:30
     */
    @Override
    public void changZhouGgtsqz(String ggid) {
        //根据工作流实例id查询证书和项目信息，组织数据推送签章
        if (StringUtils.isBlank(ggid)) {
            throw new MissingArgumentException("未获取到公告信息ID");
        }

        List<BdcXmDO> bdcXmDOList = this.bdcGgFeignService.queryBdcGgGlBdcXmxx(ggid);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            tsggqz(bdcXmDOList.get(0), ggid,true);
        }
        LOGGER.info("公告ID:{}未关联不动产项目务获取关联的公告业务数据推送签章",ggid);
        List<BdcGgywsjDO> bdcGgywsjDOS = bdcGgFeignService.queryBdcGgywsjByGgid(ggid);
        if(CollectionUtils.isNotEmpty(bdcGgywsjDOS)){
            BdcXmDO bdcXmDO = new BdcXmDO();
            BeanUtils.copyProperties(bdcGgywsjDOS.get(0), bdcXmDO);
            bdcXmDO.setSlbh(UUIDGenerator.generate16());
            tsggqz(bdcXmDO, ggid,false);
        }

    }

    /**
     * @param dzqzCsDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取生成电子签章需要的参数
     * @date : 2022/8/24 13:51
     */
    @Override
    public EZsDTO getDzqzCs(DzqzCsDTO dzqzCsDTO) {
        BdcZsDO bdcZsDO = new BdcZsDO();
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcZsDO.setBdcqzh(dzqzCsDTO.getBdcqzh());
        bdcZsDO.setBdcdyh(dzqzCsDTO.getBdcdyh());
        bdcXmDO.setBdcdyh(dzqzCsDTO.getBdcdyh());
        bdcXmDO.setDjbmdm(dzqzCsDTO.getDjbmdm());
        bdcXmDO.setDjjg(dzqzCsDTO.getDjjg());
        bdcXmDO.setQxdm(dzqzCsDTO.getQxdm());
        bdcXmDO.setSlbh(dzqzCsDTO.getSlbh());
        bdcXmDO.setZl(dzqzCsDTO.getZl());
        return eCertificateUtils.setDzqzxx(bdcZsDO, bdcXmDO, dzqzCsDTO.getQzlx());
    }

    /**
     * 根据证书 ID 查询 zzbs
     * 未查询到相关信息，直接抛出异常
     *
     * @param zsid zsid
     * @return java.lang.String zzbs
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private BdcZsDO queryZzbsByZsid(String zsid) {
        if (StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("zsid");
        }
        // 查询证书信息
        BdcZsDO zsDO = bdcZsService.queryBdcZs(zsid);
        if (zsDO == null) {
            throw new AppException("未查询到证书信息，zsid：" + zsid);
        }
        String zzbs = zsDO.getZzid();
        if (StringUtils.isBlank(zzbs)) {
            throw new AppException("当前证书未生成电子印章，dzqzbs 为空，zsid：" + zsid);
        }
        return zsDO;
    }


    /**
     * @param bdcxmxx 不动产项目信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送公告签章组织参数
     * @date : 2022/3/14 10:07
     */
    private List<DzzzResponseData> tsggqz(BdcXmDO bdcxmxx, String ggid,boolean sfglyw) {
        List<DzzzResponseData> responseDatas = Lists.newArrayList();
        //推送公告查询上一手的证书信息--公告签章只需要组织证书信息，不作更新操作
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setBdcqzh("登记推送公告默认证号" + bdcxmxx.getSlbh());
        bdcZsDO.setZsid(bdcxmxx.getSlbh());
        if(sfglyw){
            //公告数据先用关联的流程查当前的证书，没有查上一手证书，还没有就给默认值
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(bdcxmxx.getGzlslid());
            List<BdcZsDO> nowZsList = bdcZsService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isEmpty(nowZsList)) {
                List<BdcZsDO> yZsDOList = bdcZsService.queryYxmZsSortByXmid(bdcxmxx.getGzlslid(), null);
                if (CollectionUtils.isNotEmpty(yZsDOList)) {
                    LOGGER.warn("当前公告关联项目{}未查询到证书，获取上一手证书信息{}", bdcxmxx.getSlbh(), yZsDOList.get(0).getBdcqzh());
                    bdcZsDO = yZsDOList.get(0);
                }
            } else {
                bdcZsDO = nowZsList.get(0);
            }
        }
        BdcGgDO bdcGgDO = bdcGgFeignService.queryBdcGgByGgid(ggid);
        if(Objects.isNull(bdcGgDO)){
            throw new AppException("当前公告信息ID: "+ggid+" , 流程受理编号:" + bdcxmxx.getSlbh() + "未获取到公告信息");
        }
        Integer gglx = bdcGgDO.getGglx();
        if (Objects.isNull(gglx)) {
            throw new AppException("当前流程受理编号" + bdcxmxx.getSlbh() + "未获取到公告类型，无法找到公告类型模板");
        }
        try {
            EZsTimeStampDTO eZsTimeStampDTO = eCertificateUtils.generateGgDTO(bdcxmxx, bdcZsDO, bdcGgDO.getGglx());
            // 校验参数
//            ValidatorUtils.validate(eZsTimeStampDTO, CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx()) ? DzzzZs.class : DzzzZm.class);

            DzqzEZsDTO dzqzEZsDTO = new DzqzEZsDTO();
            BeanUtils.copyProperties(eZsTimeStampDTO, dzqzEZsDTO);
            dzqzEZsDTO.setSeal(zzqzConfig.getSeal());
            dzqzEZsDTO.setPosition(zzqzConfig.getPosition());
            dzqzEZsDTO.setGglx(gglx);

//            dzqzEZsDTO.setSmct(smct);

            JSONObject param = new JSONObject();
            param.put("data", dzqzEZsDTO);
            LOGGER.info("常州电子印章最终组合传入参数：{}", param);
            OfficeExportDTO officeExportDTO = new OfficeExportDTO();
            //模板路径
            Map<String, List<Map>> paramMap = Maps.newHashMap();
            String dylx = BdcGglxEnum.getGglxEnum(gglx).getDm();
            List<Map> maps = new ArrayList<>(1);
            Map map = new HashMap(1);
            map.put("gzlslid", "");
            map.put("ggid", bdcGgDO.getGgid());
            String slbh = dzqzEZsDTO.getYwh();
            BdcGgVO bdcGgVO = new BdcGgVO();
            Map bdcGgVOMap = JSON.parseObject(JSON.toJSONString(bdcGgVO, SerializerFeature.WriteNullStringAsEmpty), Map.class);
            map.putAll(bdcGgVOMap);
            String ggmbnr = bdcGgDO.getGgmbnr();
            if (StringUtils.isNotBlank(ggmbnr)) {
                Map bdcGgMap = JSON.parseObject(ggmbnr, Map.class);
                map.putAll(bdcGgMap);
            }
            map.put("slbh", slbh);
            maps.add(map);
            paramMap.put(dylx, maps);
            LOGGER.warn("当前受理编号{},生成电子签章pdf打印类型{}", bdcxmxx.getSlbh(), dylx);
            String xml = bdcPrintFeignService.print(paramMap);
            String modelPath = printPath + dylx + CommonConstantUtils.WJHZ_DOCX;
            officeExportDTO.setModelName(modelPath);
            officeExportDTO.setXmlData(xml);
            officeExportDTO.setFileName(dylx + slbh);
            String path = pdfController.generatePdfFile(officeExportDTO);
            byte[] bytes = fileUtils.fileToByte(new File(path));
            dzqzEZsDTO.setPdfByte(bytes);
            Object mbpzpdf = bdcDzqzFeignService.czggdzqz(JSON.toJSONString(param));
            DzzzResponseData data = eCertificateUtils.dealDzzzResponse(mbpzpdf, "生成证照");
            if (data.isStatus()) {
                saveTsqzZtxx(bdcZsDO, BdcZzqzTsztEnum.YTSWXZ.getValue(), data.getInfo());
                //更新公告表签章标识
                String zzbs = data.getZzbs();
                bdcGgDO.setQzbs(zzbs);
                entityMapper.updateByPrimaryKeySelective(bdcGgDO);
            } else {
                saveTsqzZtxx(bdcZsDO, BdcZzqzTsztEnum.TSSB.getValue(), data.getInfo());
            }
            responseDatas.add(data);
            return responseDatas;
        } catch (Exception e) {
            saveTsqzZtxx(bdcZsDO, BdcZzqzTsztEnum.TSSB.getValue(), JSON.toJSONString(e));
            LOGGER.error("推送电子印章失败", e);
            throw new AppException("推送印章失败");
        }
    }
}
