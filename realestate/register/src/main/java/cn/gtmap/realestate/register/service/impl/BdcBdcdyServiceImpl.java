package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDyaqQlrDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLsgxXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdySumDTO;
import cn.gtmap.realestate.common.core.dto.register.TdSyqJssjDTO;
import cn.gtmap.realestate.common.core.enums.DytdmjJsfsEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCqQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjhZtFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDyawqdVO;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.core.dto.BdcDyaqDTO;
import cn.gtmap.realestate.register.core.mapper.*;
import cn.gtmap.realestate.register.service.BdcBdcdyService;
import cn.gtmap.realestate.register.service.BdcDypzService;
import cn.gtmap.realestate.register.service.BdcPrintService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/21
 * @description 不动产单元服务处理Service实现
 */
@Service
public class BdcBdcdyServiceImpl implements BdcBdcdyService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBdcdyServiceImpl.class);

    private static final Pattern pattern = Pattern.compile("^[0-9]*$");

    /**
     * 登记历史关系台账需要合并展示一条记录的业务登记小类
     */
    @Value("${djlsgx.djxl:}")
    private String djxl;

    /**
     * 抵押物清单打印-是否区分抵押权人
     */
    @Value("${dyawqd.sfqfdyqr:true}")
    private String sfqfdyqr;

    /**
     * 上下手查询配置
     */
    @Value("${djlsgx.xzql:}")
    private String xzql;

    /**
     * 独用土地面积计算方式
     */
    @Value("${dytdmj.jsfs:2}")
    private String dytdmjJsfs;


    /**
     * 常州登记历史权属状态配置
     */
    @Value("${djlsgx.qsztlist:}")
    private String qsztlist;

    /**
     * 是否查询后手及递归子项目，默认不查询
     */
    @Value("${djlsgx.sfcxhs:2}")
    private String sfcxhs;

    /**
     * 登记历史查询指定案件状态，不配置默认展示所有
     */
    @Value("${djlsgx.ajzt:}")
    private String ajzt;

    /**
     * 不查询历史关系的工作流定义ID（多个英文逗号分隔），这个流程不去查询上下手项目，但是本身还是展示的
     */
    @Value("${djlsgx.bcxlsgxgzl:}")
    private String bcxlsgxgzl;

    /**
     * 登记历史指定工作流ID不展示，这个是控制最终台账不展示的流程，和 bcxlsgxgzl 作用不一样
     */
    @Value("${djlsgx.gzldyid:}")
    private String gzldyid;

    /**
     * 常州 登记历史-抵押表单 需要展示外联证书抵押信息的 流程
     */
    @Value("#{'${djlsgx.wlzsdy.gzldyid:}'.split(',')}")
    protected List<String> wlzsdyGzldyid;

    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;

    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;
    /**
     * 不动产单元ORM操作
     */
    @Autowired
    private BdcBdcdyMapper bdcBdcdyMapper;
    /**
     * 房屋不动产单元查询接口
     */
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    /**
     * 土地不动产单元查询接口
     */
    @Autowired
    private DjxxFeignService djxxFeignService;

    @Autowired
    BdcXmxxService bdcXmxxService;

    @Autowired
    BdcPrintService bdcPrintService;

    @Autowired
    DjhZtFeignService djhZtFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;

    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;

    @Autowired
    BdcFdcqMapper bdcFdcqMapper;

    @Autowired
    BdcDyaqMapper bdcDyaqMapper;

    @Autowired
    private BdcYgMapper bdcYgMapper;

    @Autowired
    BdcJsydsyqMapper bdcJsydsyqMapper;

    @Autowired
    private EntityMapper entityMapper;

     @Autowired
    BdcXmMapper bdcXmMapper;

     @Autowired
    BdcDypzService bdcDypzService;

    /**
     * PDF打印
     */
    @Autowired
    private PdfController pdfController;

    /**
     * @param pageable  分页参数
     * @param bdcLsgxQO 查询参数
     * @return {Page} 项目列表
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产单元号对应的项目登记历史关系
     */
    @Override
    public Page<BdcLsgxXmDTO> listBdcdyLsgx(Pageable pageable, BdcLsgxQO bdcLsgxQO) {
        if (null == bdcLsgxQO || (StringUtils.isBlank(bdcLsgxQO.getBdcdyh()) && StringUtils.isBlank(bdcLsgxQO.getGzlslid()))){
            throw new NullPointerException("场景：查询不动产单元号对应的项目登记历史关系；错误原因：参数不动产单元号或工作流实例ID为空！");
        }

        // 设置需要合并展示的业务登记小类参数
        bdcLsgxQO.setDjxl(djxl);
        // 不查询历史关系的工作流定义ID，这个流程不去查询上下手项目，但是本身还是展示的
        bdcLsgxQO.setBcxlsgxgzl(bcxlsgxgzl);
        //常州登记历史权属状态不展示配置
        bdcLsgxQO.setQsztlist(qsztlist);
        // 是否查询后手及递归子项目
        bdcLsgxQO.setSfcxhs(sfcxhs);
        // 展示指定案件状态
        bdcLsgxQO.setAjzt(ajzt);
        // 登记历史指定工作流ID不展示，这个是控制最终台账不展示的流程，和 bcxlsgxgzl 作用不一样
        bdcLsgxQO.setGzldyid(gzldyid);

        if(StringUtils.isNotBlank(bdcLsgxQO.getCxlx())){
            //限制权利信息
            bdcLsgxQO.setXzql(xzql);
            if("xzql".equals(bdcLsgxQO.getCxlx())){
                bdcLsgxQO.setSfcxls("2");
            }

            if("zhcx".equals(bdcLsgxQO.getCxlx())){
                // 综合查询不需要配置
                bdcLsgxQO.setXzql("");
            }
        }
        if (StringUtils.isNotBlank(bdcLsgxQO.getDangqianGzldyid()) && wlzsdyGzldyid.contains(bdcLsgxQO.getDangqianGzldyid())) {
            bdcLsgxQO.setWlzsdy("1");
        }
        Page<BdcLsgxXmDTO> page = repo.selectPaging("listBdcdyLsgxByPageOrder",bdcLsgxQO,pageable);
        LOGGER.info("登记历史查询:{}", JSONObject.toJSONString(page));
        if (null == page || CollectionUtils.isEmpty(page.getContent())){
            return page;
        }

        // 南通  综合查询登记历史台账中新增是否注销字段
        if(StringUtils.isNotBlank(bdcLsgxQO.getVersion())&&CommonConstantUtils.SYSTEM_VERSION_NT.equals(bdcLsgxQO.getVersion())) {
            for (BdcLsgxXmDTO bdcLsgxXmDTO : page.getContent()) {
                if (null != bdcLsgxXmDTO && StringUtils.isNotBlank(bdcLsgxXmDTO.getXmid())) {
                    Map map = new HashMap();
                    map.put("xmid", bdcLsgxXmDTO.getXmid());
                    List<BdcXmLsgxDO> bdcXmLsgxList = repo.selectList("listBdcSfzxByXmid", map);
                    if (CollectionUtils.isNotEmpty(bdcXmLsgxList) && bdcXmLsgxList.size() > 0) {
                        if (null != bdcXmLsgxList.get(0) && null != bdcXmLsgxList.get(0).getZxyql()) {
                            bdcLsgxXmDTO.setSfzx(String.valueOf(bdcXmLsgxList.get(0).getZxyql()));
                        }
                    }
                }
            }
        }

        // 常州登记历史展示新增建筑面积和房间号字段
        if(StringUtils.isNotBlank(bdcLsgxQO.getVersion())&&CommonConstantUtils.DJLSVERSION_CHANGZHOU.equals(bdcLsgxQO.getVersion())){
            for (BdcLsgxXmDTO bdcLsgxXmDTO : page.getContent()){
                if (null != bdcLsgxXmDTO && StringUtils.isNotBlank(bdcLsgxXmDTO.getXmid())){
                    Example example = new Example(BdcFdcqDO.class);
                    Example.Criteria criteria = example.createCriteria().andEqualTo("xmid", bdcLsgxXmDTO.getXmid());
                    List<BdcFdcqDO> bdcFdcqList = entityMapper.selectByExample(example);
                    if(CollectionUtils.isNotEmpty(bdcFdcqList)&&bdcFdcqList.size()>0){
                        if(null != bdcFdcqList.get(0) && null != bdcFdcqList.get(0).getJzmj()) {
                            bdcLsgxXmDTO.setJzmj(String.valueOf(bdcFdcqList.get(0).getJzmj()));
                        }
                        bdcLsgxXmDTO.setFjh(bdcFdcqList.get(0).getFjh());
                    }
                }
            }
        }

        // 获取受理字典
        Map<String,List<Map>> slzd = bdcSlZdFeignService.listBdcSlzd();
        if (MapUtils.isEmpty(slzd) || CollectionUtils.isEmpty(slzd.get("fkfs"))){
            return page;
        }
        List<String> xmidList = new ArrayList<>();
        // 调用受理获取付款方式信息
        for (BdcLsgxXmDTO bdcLsgxXmDTO : page.getContent()){
            if (null != bdcLsgxXmDTO && StringUtils.isNotBlank(bdcLsgxXmDTO.getXmid())){
                xmidList.add(bdcLsgxXmDTO.getXmid());
                List<BdcSlJyxxDO> jyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(bdcLsgxXmDTO.getXmid());
                LOGGER.info("登记历史查询:" + JSONObject.toJSONString(page));
                if (CollectionUtils.isNotEmpty(jyxxDOList) && null != jyxxDOList.get(0)){
                    String fkfs = jyxxDOList.get(0).getFkfs();
                    for (Map zd : slzd.get("fkfs")){
                        if (MapUtils.isNotEmpty(zd) && StringUtils.equals(String.valueOf(zd.get("DM")),fkfs)){
                            fkfs = String.valueOf(zd.get("MC"));
                            break;
                        }
                    }
                    bdcLsgxXmDTO.setFkfs(fkfs);
                }
            }
        }

        // 海门需求 需要在登记历史台账中增加 查封文号
        if (CollectionUtils.isNotEmpty(xmidList)){
            Map map = new HashMap();
            map.put("xmidList",xmidList);
            List<Map> listCfwh = repo.selectList("listCfwhByXmids",map);
            if (CollectionUtils.isEmpty(listCfwh)){
                return page;
            }
            for (BdcLsgxXmDTO bdcLsgxXmDTO : page.getContent()){
                String xmid = bdcLsgxXmDTO.getXmid();
                for (Map cfMap : listCfwh){
                    if (cfMap.containsKey("XMID") && cfMap.containsKey("CFWH") && cfMap.get("XMID") != null && cfMap.get("CFWH") != null){
                        String cfXmid = cfMap.get("XMID").toString();
                        if (StringUtils.equals(xmid,cfXmid)){
                            bdcLsgxXmDTO.setCfwh(cfMap.get("CFWH").toString());
                            break;
                        }
                    }
                }
            }
        }

        return page;
    }


    /**
     * @param bdcdyh 不动产单元号
     * @return {Boolean} 存在查封：true，不存在查封：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断不动产单元号对应的权利人是否存在查封
     */
    @Override
    public Boolean isBdcdyhQlrCf(String bdcdyh,String qjgldm){
        if (StringUtils.isBlank(bdcdyh) || bdcdyh.length() < 20){
            return false;
        }

        // 查询权利人
        List<BdcQlrDO> bdcQlrDOList = this.listBdcdyQlrDO(bdcdyh,qjgldm);
        if (CollectionUtils.isEmpty(bdcQlrDOList)){
            return false;
        }

        // 判断是否权利人存在查封
        int num = bdcBdcdyMapper.listBdcCfYwr(bdcQlrDOList);
        return num > 0;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {Boolean} 存在异议：true，不存在异议：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断不动产单元号对应的权利人是否存在异议
     */
    @Override
    public Boolean isBdcdyhQlrYy(String bdcdyh,String qjgldm){
        if (StringUtils.isBlank(bdcdyh) || bdcdyh.length() < 20){
            return false;
        }

        // 查询权利人
        List<BdcQlrDO> bdcQlrDOList = this.listBdcdyQlrDO(bdcdyh,qjgldm);
        if (CollectionUtils.isEmpty(bdcQlrDOList)){
            return false;
        }

        // 判断是否权利人存在异议
        int num = bdcBdcdyMapper.listBdcYyYwr(bdcQlrDOList);
        return num > 0;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 锁定则返回提示信息；未锁定则返回空
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 验证所在宗地不动产单元是否锁定
     */
    @Override
    public List<String> isZdBdcdySd(String bdcdyh,String qjgldm){
        if (StringUtils.isBlank(bdcdyh)){
            return Collections.emptyList();
        }

        // 保存验证未通过提示信息
        List<String> result = new ArrayList<>(1);

        //添加当前的不动产单元号
        List<String> bdcdywybhList = new ArrayList<>(10);
        bdcdywybhList.add(StringUtils.substring(bdcdyh,0,19));

        // 批量查询冻结信息
        List<DjhZtResponseDTO> djxxList = djhZtFeignService.listDjhZtByDjh(bdcdywybhList,qjgldm);

        if (CollectionUtils.isNotEmpty(djxxList)){
            for (DjhZtResponseDTO djzt : djxxList){
                if (null != djzt && CollectionUtils.isNotEmpty(djzt.getZdzhDjxxDOList())){
                    String djyy = djzt.getZdzhDjxxDOList().get(0).getDjyy();
                    result.add("当前宗地不动产单元" + bdcdyh + "存在锁定，锁定原因锁定原因:" + djyy + "，请核实无误后解锁办理！");
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param bdcPrintDTO 打印对象
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印xml
     */
    @Override
    public String bdcdyPrintXml(BdcPrintDTO bdcPrintDTO){
        String gzlslid = bdcPrintDTO.getGzlslid();
        String dylx = bdcPrintDTO.getDylx();
        String xmid = bdcPrintDTO.getXmid();
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(dylx)){
            throw new MissingArgumentException("打印缺失gzlslid或dylx！");
        }

        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)){
            throw new MissingArgumentException("工作流实例ID：" + gzlslid + "。未查询到项目信息");
        }
        String slbh = bdcXmDOList.get(0).getSlbh();
        String xmidSql = CommonConstantUtils.ZF_KG_CHAR;
        if (StringUtils.isBlank(xmid)){
            xmid = bdcXmDOList.get(0).getXmid();
        }else{
            // 获取证书关联的所有的xmid
            List<String> zsxmList = bdcXmFeignService.queryZsxmList(xmid);
            // xmid不为空时，将xmid拼接成sql字符串，作为查询参数${}方式注入查询sql，补充查询参数
            xmidSql = " and t.xmid in " + StringToolUtils.appendSqlInStr(zsxmList);
        }

        String zsid = bdcPrintDTO.getZsid();
        if (StringUtils.isBlank(zsid)){
            zsid = CommonConstantUtils.ZF_KG_CHAR;
        }

        Map<String,List<Map>> paramMap = new HashMap(1);
        List<Map> mapList = new ArrayList();
        Map<String,String> map = new HashMap(5);
        map.put("gzlslid",gzlslid);
        map.put("xmid",xmid);
        map.put("ewm",bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + slbh);
        map.put("xmidSql",xmidSql);
        map.put("zsid",zsid);
        // 对于抵押物清单，如果传了抵押权人，需要根据抵押权人的个数进行打印份数
        if (StringUtils.contains(dylx,CommonConstantUtils.DYWQD_DYLX) && StringUtils.isNotBlank(bdcPrintDTO.getQlr())){
            if(StringUtils.equals(CommonConstantUtils.BOOL_TRUE,sfqfdyqr)){
                String[] qlrArr = StringUtils.split(bdcPrintDTO.getQlr(),CommonConstantUtils.ZF_YW_DH);
                if (null != qlrArr){
                    for (String qlr : qlrArr){
                        HashMap mapTemp = new HashMap();
                        // hashMap 实现深拷贝
                        mapTemp.putAll(map);
                        mapTemp.put("qlr",qlr);
                        mapList.add(mapTemp);
                    }
                }
            }else {
                HashMap mapTemp = new HashMap();
                // hashMap 实现深拷贝
                mapTemp.putAll(map);
                mapTemp.put("qlr",bdcPrintDTO.getQlr());
                mapList.add(mapTemp);
            }
        }else{
            mapList.add(map);
        }
        paramMap.put(dylx,mapList);
        return bdcPrintService.print(paramMap);
    }

    @Override
    public String bdcdyPrintXmlPl(BdcPrintDTO bdcPrintDTO) {
        String gzlslids = bdcPrintDTO.getGzlslids();
        String dylx = bdcPrintDTO.getDylx();
        Map<String,List<Map>> paramMap = new HashMap<>(1);
        List<Map> mapList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslids)) {
            String[] gzlslidArr = gzlslids.split(CommonConstantUtils.ZF_YW_DH);
            if (gzlslidArr.length > 0) {
                for (String gzlslid : gzlslidArr) {
                    String xmid = bdcPrintDTO.getXmid();
                    if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(dylx)){
                        throw new MissingArgumentException("打印缺失gzlslid或dylx！");
                    }

                    List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
                    if (CollectionUtils.isEmpty(bdcXmDOList)){
                        throw new MissingArgumentException("工作流实例ID：" + gzlslid + "。未查询到项目信息");
                    }
                    String slbh = bdcXmDOList.get(0).getSlbh();
                    String xmidSql = CommonConstantUtils.ZF_KG_CHAR;
                    if (StringUtils.isBlank(xmid)) {
                        xmid = bdcXmDOList.get(0).getXmid();
                    } else {
                        // 获取证书关联的所有的xmid
                        List<String> zsxmList = bdcXmFeignService.queryZsxmList(xmid);
                        // xmid不为空时，将xmid拼接成sql字符串，作为查询参数${}方式注入查询sql，补充查询参数
                        xmidSql = " and t.xmid in " + StringToolUtils.appendSqlInStr(zsxmList);
                    }

                    String zsid = bdcPrintDTO.getZsid();
                    if (StringUtils.isBlank(zsid)){
                        zsid = CommonConstantUtils.ZF_KG_CHAR;
                    }
                    Map<String,String> map = new HashMap(5);
                    map.put("gzlslid", gzlslid);
                    map.put("xmid", xmid);
                    map.put("ewm", bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + slbh);
                    map.put("xmidSql", xmidSql);
                    map.put("zsid", zsid);
                    // 对于抵押物清单，如果传了抵押权人，需要根据抵押权人的个数进行打印份数
                    if (StringUtils.contains(dylx,CommonConstantUtils.DYWQD_DYLX) && StringUtils.isNotBlank(bdcXmDOList.get(0).getQlr())){
                        if (StringUtils.equals(CommonConstantUtils.BOOL_TRUE, sfqfdyqr)) {
                            String[] qlrArr = StringUtils.split(bdcXmDOList.get(0).getQlr(), CommonConstantUtils.ZF_YW_DH);
                            if (null != qlrArr) {
                                for (String qlr : qlrArr){
                                    HashMap mapTemp = new HashMap();
                                    // hashMap 实现深拷贝
                                    mapTemp.putAll(map);
                                    mapTemp.put("qlr", qlr);
                                    mapList.add(mapTemp);
                                }
                            }
                        } else {
                            HashMap mapTemp = new HashMap();
                            // hashMap 实现深拷贝
                            mapTemp.putAll(map);
                            mapTemp.put("qlr", bdcXmDOList.get(0).getQlr());
                            mapList.add(mapTemp);
                        }
                    } else {
                        mapList.add(map);
                    }
                }
            }
            paramMap.put(dylx,mapList);
        }
        return bdcPrintService.print(paramMap);
    }

    /**
     * @param map 查询参数
     * @return 不动产单元列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询出所有的数据
     */
    @Override
    public List<BdcBdcdyVO> queryBdcdyList(Map<String,Object> map){
        return bdcBdcdyMapper.listBdcdyByPage(map);
    }

    /**
     * @param map 查询参数
     * @return 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询原项目的抵押物清单
     */
    @Override
    public List<BdcDyawqdVO> queryYxmDyawqd(Map<String,Object> map){
        /**
         * 此处逻辑，如果qllx为96（预告），则关联预告表，
         * 否则，为默认的95（抵押），关联抵押表
         * 如果异常，则沿用之前的逻辑保证页面和系统正常运行
         */
        if (map.containsKey("qllx") && CommonConstantUtils.QLLX_YG_DM == Integer.parseInt(map.get("qllx").toString())){
            return bdcBdcdyMapper.listYgYdyawqdByPage(map);
        }else{
            return bdcBdcdyMapper.listYxmDyawqdByPage(map);
        }
    }

    /**
     * @param map 查询参数
     * @return 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询部分注销，抵押变更原项目的抵押物清单
     */
    @Override
    public List<BdcDyawqdVO> queryBgYxmDyawqd(Map<String,Object> map){
        return bdcBdcdyMapper.queryBgYxmDyawqdByPage(map);
    }

    /**
     * @param map 查询参数
     * @return 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 和分页查询同一个逻辑，用于查询抵押物清单
     */
    @Override
    public List<BdcDyawqdVO> queryDyawqd(Map<String,Object> map){
        /**
         * 此处逻辑，如果qllx为96（预告），则关联预告表，
         * 否则，为默认的95（抵押），关联抵押表
         */
        if (map.containsKey("qllx") && CommonConstantUtils.QLLX_YG_DM == Integer.parseInt(map.get("qllx").toString())){
            return bdcBdcdyMapper.listYgDyawqdByPage(map);
        }else{
            return bdcBdcdyMapper.listDyawqdByPage(map);
        }

    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产单元对应的权利人信息（需要注意的是只能查询当前不动产单元对应的，对于同一个权利人名下不同宗地权利人是无法查询的）
     */
    private List<BdcQlrDO> listBdcdyQlrDO(String bdcdyh,String qjgldm){
        // 不动产单元号对应的权利人集合
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>(10);
        // 特征码
        String dzwTzm = bdcdyh.substring(19,20);
        if (StringUtils.equals(dzwTzm,CommonConstantUtils.BDCLX_TZM_F)){
            // 房屋
            BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(bdcdyh,null,qjgldm);
            if (null != bdcdyResponseDTO && CollectionUtils.isNotEmpty(bdcdyResponseDTO.getQlrList())){
                for (FwFcqlrDO fwFcqlrDO : bdcdyResponseDTO.getQlrList()){
                    BdcQlrDO bdcQlrDO = new BdcQlrDO();
                    bdcQlrDO.setQlrmc(fwFcqlrDO.getQlr());
                    bdcQlrDO.setZjh(fwFcqlrDO.getQlrzjh());
                    bdcQlrDOList.add(bdcQlrDO);
                }
            }
        }else if (StringUtils.equals(dzwTzm,CommonConstantUtils.BDCLX_TZM_W)){
            // 土地
            DjxxResponseDTO djxxResponseDTO = djxxFeignService.queryDjxxByBdcdyh(bdcdyh,qjgldm);
            if (null != djxxResponseDTO && CollectionUtils.isNotEmpty(djxxResponseDTO.getQlrList())){
                for (DjxxQlrDTO djxxQlrDTO : djxxResponseDTO.getQlrList()){
                    BdcQlrDO bdcQlrDO = new BdcQlrDO();
                    bdcQlrDO.setQlrmc(djxxQlrDTO.getQlrmc());
                    bdcQlrDO.setZjh(djxxQlrDTO.getZjh());
                    bdcQlrDOList.add(bdcQlrDO);
                }
            }
        }
        return bdcQlrDOList;
    }

    /**
     * @param xmid 项目ID
     * @return {Boolean} 验证通过：true ；不通过：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 验证当前项目权利人权利比例之和是否为1
     */
    @Override
    public Boolean checkQlrQlbl(String xmid){
        if (StringUtils.isBlank(xmid)){
            // 没有相关信息默认都通过
            return true;
        }

        // 1、获取权利人权力比例信息
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb("1");
        List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isEmpty(qlrDOList)){
            return true;
        }

        // 2、判断共有方式
        List<Integer> gyfsList = qlrDOList.stream().map(BdcQlrDO::getGyfs).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(gyfsList)){
            return true;
        }
        // 3、根据共有方式计算共有比例是否正确
        if (gyfsList.size() == 1 && gyfsList.get(0) != null && gyfsList.get(0).intValue() == 2){
            /// 3.1、都是按份共有
            return this.checkQlrQlbl(qlrDOList.stream().map(BdcQlrDO::getQlbl).collect(Collectors.toList()));
        }else if (gyfsList.size() == 2 && this.isAfgyAndGtgy(gyfsList)){
            /// 3.2、按份共有和共同共有结合
            /// 例如：ABC 三个人，A与B共同共有，然后AB与C按份共有，AB两条记录的份数分别是60%，C是40%

            List<String> qlblList = new ArrayList<>(qlrDOList.size());
            /// 获取共同共有的比例
            for (BdcQlrDO bdcQlrDO : qlrDOList){
                if (1 == bdcQlrDO.getGyfs()){
                    if (StringUtils.isNotBlank(bdcQlrDO.getQlbl())) {
                        qlblList.add(bdcQlrDO.getQlbl());
                        break;
                    }
                }
            }
            /// 获取按份共有的比例
            for (BdcQlrDO bdcQlrDO : qlrDOList){
                if (2 == bdcQlrDO.getGyfs()) {
                    if (StringUtils.isNotBlank(bdcQlrDO.getQlbl())) {
                        qlblList.add(bdcQlrDO.getQlbl());
                    }
                }
            }

            return this.checkQlrQlbl(qlblList);
        }

        return true;
    }

    /**
     * @param qlblList 权利比例信息
     * @return {Boolean}  为1或者空信息：true ；  不为1：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 验证权利比例之和是否为1
     */
    public Boolean checkQlrQlbl(List<String> qlblList){
        if (CollectionUtils.isEmpty(qlblList)){
            return false;
        }

        for (String qlbl : qlblList){
            if (StringUtils.isBlank(qlbl)){
                return false;
            }
        }

        if (isAllBfh(qlblList)){
            // 百分数形式
            BigDecimal sum = new BigDecimal(0);
            for (String qlbl : qlblList){
                sum = sum.add(BigDecimal.valueOf(Double.parseDouble(qlbl.replace(Constants.FH_BFH,""))));
            }
            return sum.compareTo(new BigDecimal(Double.valueOf("100"))) == 0;
        }else if (isAllXs(qlblList)){
            // 小数形式
            BigDecimal sum = new BigDecimal(0);
            for (String qlbl : qlblList){
                sum = sum.add(BigDecimal.valueOf(Double.parseDouble(qlbl)));
            }
            return sum.compareTo(new BigDecimal(Double.valueOf("1"))) == 0;
        }else if (isAllFs(qlblList)){
            // 分数形式

            // 分子数组
            int[] fzArr = new int[qlblList.size()];
            // 分母数组
            int[] fmArr = new int[qlblList.size()];
            for (int i = 0; i < qlblList.size(); i++){
                String qlbl = qlblList.get(i);
                fzArr[i] = Integer.parseInt(qlbl.substring(0,qlbl.indexOf(Constants.FH_XG)));
                fmArr[i] = Integer.parseInt(qlbl.substring(qlbl.indexOf(Constants.FH_XG) + 1,StringUtils.length(qlbl)));
            }

            // 计算分母乘积
            BigDecimal pro = new BigDecimal(1);
            for (int fm : fmArr){
                pro = new BigDecimal(fm).multiply(pro);
            }

            // 获取分子乘差和
            BigDecimal fzSum = new BigDecimal(0);
            for (int j = 0; j < fzArr.length; j++){
                fzSum = fzSum.add(new BigDecimal(String.valueOf(pro.divide(new BigDecimal(fmArr[j])).multiply(new BigDecimal(fzArr[j])))));
            }

            return fzSum.equals(pro);
        }else if (isAllInteger(qlblList)){
            int qlblSize = qlblList.size();
            int result = 0;
            for (int i = 0; i < qlblSize; i++){
                result += Integer.parseInt(qlblList.get(i));
            }
            return result == 1;
        }else{
            // 对于形式不统一、汉字之类校验不通过
            return false;
        }
    }

    /**
     * @param qlblList 权利比例信息
     * @return {Boolean}  是：true ； 不是：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断权利比例是否都是百分数形式
     */
    private Boolean isAllBfh(List<String> qlblList){
        for (String qlbl : qlblList){
            if (StringUtils.isBlank(qlbl) || !qlbl.contains(Constants.FH_BFH)){
                return false;
            }
        }
        return true;
    }

    /**
     * @param qlblList 权利比例信息
     * @return {Boolean}  是：true ； 不是：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断权利比例是否都是小数形式
     */
    private Boolean isAllXs(List<String> qlblList){
        for (String qlbl : qlblList){
            if (StringUtils.isBlank(qlbl) || !(!qlbl.contains(Constants.FH_BFH) && qlbl.contains(Constants.FH_DH))){
                return false;
            }
        }
        return true;
    }

    /**
     * @param qlblList 权利比例信息
     * @return {Boolean}  是：true ； 不是：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断权利比例是否都是分数形式
     */
    private Boolean isAllFs(List<String> qlblList){
        for (String qlbl : qlblList){
            if (StringUtils.isBlank(qlbl) || !qlbl.contains(Constants.FH_XG)){
                return false;
            }
        }
        return true;
    }

    /**
     * @param qlblList 权利比例信息
     * @return {Boolean}  是：true ； 不是：false
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 判断权利比例是否都是整数
     */
    private Boolean isAllInteger(List<String> qlblList){
//        Pattern pattern = Pattern.compile("^[0-9]*$");
        for (String qlbl : qlblList){
            if (StringUtils.isBlank(qlbl) || !pattern.matcher(qlbl).matches()){
                return false;
            }
        }
        return true;
    }

    /**
     * @param gyfsList 共有方式信息
     * @return {Boolean}  为1或者空信息：true ；  不为1：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断是否按份共有和共同共有结合
     */
    private Boolean isAfgyAndGtgy(List<Integer> gyfsList){
        boolean isAfgy = false, isGtgy = false;
        for (Integer gyfs : gyfsList){
            if (gyfs.intValue() == 1){
                isGtgy = true;
                continue;
            }

            if (gyfs.intValue() == 2){
                isAfgy = true;
            }
        }
        return isAfgy && isGtgy;
    }

    /**
     * 查询项目来源
     *
     * @param xmid
     * @return
     */
    @Override
    public List queryXmly(String xmid){
        Map map = new HashMap();
        map.put("xmid",xmid);
        return bdcBdcdyMapper.queryXmly(map);
    }

    @Override
    public List<BdcLsgxXmDTO> queryCqdz(String xmid) {
        Map map = new HashMap();
        map.put("xmid",xmid);
        return bdcBdcdyMapper.queryCqdz(map);
    }

    @Override
    public BdcdySumDTO calculatedFdcqMj(String gzlslid,Boolean sfyql,String djxl){
        Map map = new HashMap();
        map.put("gzlslid",gzlslid);
        map.put("sfyql",sfyql);
        map.put("djxl",djxl);
        return bdcFdcqMapper.calculatedFdcqMj(map);
    }

    @Override
    public BdcdySumDTO calculatedDyaqMj(String gzlslid,Boolean sfyql,List<String> xmidList){
        Map map = new HashMap();
        map.put("gzlslid",gzlslid);
        map.put("sfyql",sfyql);
        if (CollectionUtils.isNotEmpty(xmidList)){
            List<List> partList = ListUtils.subList(xmidList,1000);
            map.put("partList",partList);
        }
        BdcdySumDTO bdcdySumTdDTO;
        BdcdySumDTO bdcdySumFcDTO = bdcDyaqMapper.calculatedDyaqFcMj(map);
        //南通和标准版土地抵押面积取总和
        if (StringUtils.equals(DytdmjJsfsEnum.TYPE1.getDm(),dytdmjJsfs)){
            bdcdySumTdDTO = bdcDyaqMapper.calculatedDyaqTdMjAll(map);
        }else{
            bdcdySumTdDTO = bdcDyaqMapper.calculatedDyaqTdMj(map);
        }
        BdcdySumDTO bdcdySumDTO = new BdcdySumDTO();
        if (bdcdySumFcDTO != null && bdcdySumFcDTO.getFwdymj() != null){
            bdcdySumDTO.setFwdymj(bdcdySumFcDTO.getFwdymj());
        }
        if (bdcdySumTdDTO != null && bdcdySumTdDTO.getTddymj() != null){
            bdcdySumDTO.setTddymj(bdcdySumTdDTO.getTddymj());
        }
        return bdcdySumDTO;

    }

    @Override
    public List<BdcdySumDTO> calculatedDyaqMjGyBdclx(String gzlslid,Boolean sfscql){
        Map map = new HashMap();
        map.put("gzlslid",gzlslid);
        map.put("sfscql",sfscql);
        return bdcDyaqMapper.calculatedDyaqMjGyBdclx(map);

    }

    /**
     * @param bdcXmQO 查询参数
     * @return List<BdcDyaqDO> 抵押信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程的原抵押信息
     */
    @Override
    public List<BdcDyaqDO> queryYdyaxx(BdcXmQO bdcXmQO){
        if (null == bdcXmQO || (StringUtils.isBlank(bdcXmQO.getXmid()) && StringUtils.isBlank(bdcXmQO.getGzlslid()))){
            throw new MissingArgumentException("缺失查询参数，xmid和gzlslid不能都为空！");
        }
        return bdcDyaqMapper.queryYdyaxx(bdcXmQO);
    }

    /**
     * @param gzlslid   工作流实例ID
     * @param bdcDyaqDO 抵押信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原抵押信息的抵押注销申请人
     */
    @Override
    public int saveYdyaxxZxsqr(String gzlslid,BdcDyaqDO bdcDyaqDO){
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(bdcDyaqDO.getXmid()) && StringUtils.isBlank(bdcDyaqDO.getQlid())){
            throw new MissingArgumentException("必须要有一个更新参数，gzlslid 或 xmid 或 qlid");
        }
        BdcDyaqDTO bdcDyaqDTO = new BdcDyaqDTO();
        bdcDyaqDTO.setGzlslid(gzlslid);
        bdcDyaqDTO.setDyzxsqr(bdcDyaqDO.getDyzxsqr());
        bdcDyaqDTO.setDyzxsqrzjh(bdcDyaqDO.getDyzxsqrzjh());
        return bdcDyaqMapper.saveYdyaxxZxsqrPl(bdcDyaqDTO);
    }

    /**
     * 查询当前项目上一手的产权
     *
     * @param bdcLsgxQO 历史关系查询对象
     * @return 不动产单元列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcLsgxXmDTO> listBdcdyLsgxLastCq(BdcLsgxQO bdcLsgxQO){
        if (StringUtils.isAnyBlank(bdcLsgxQO.getSlbh(),bdcLsgxQO.getBdcdyh())){
            throw new MissingArgumentException("缺少必要查询条件");
        }
        return bdcBdcdyMapper.listBdcdyLsgxLastCq(bdcLsgxQO);
    }

    /**
     * @param bdcXmQO 抵押项目
     * @return 抵押土地面积
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询抵押土地面积：如果是产权，取所有不动产单元 产权的 fttdmj + dytdmj 之和
     */
    @Override
    public Double getDyaTdmj(BdcXmQO bdcXmQO){
        if (null == bdcXmQO || this.isAllParamBlank(bdcXmQO)){
            // 如果都为空，则返回0，按照旧的模式，用户手填数据
            return Double.valueOf("0");
        }

        if (StringUtils.isBlank(bdcXmQO.getGzlslid())){
            // gzlslid 为空，xmid 不为空，查询当前所在流程 gzlslid，保证下面SQL查询的是整个流程对应的不动产单元
            List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(xmDOList) && null != xmDOList.get(0)){
                bdcXmQO.setGzlslid(xmDOList.get(0).getGzlslid());
            }
        }

        // 获取抵押权对应上一手项目的房地产权信息
        List<BdcFdcqDO> bdcFdcqDOList = bdcBdcdyMapper.listBdcFdcqByDya(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcFdcqDOList)){
            return Double.valueOf("0");
        }

        // 取所有不动产单元产权的 fttdmj + dytdmj 之和
        Double dyaTdmj = Double.valueOf("0");
        for (BdcFdcqDO bdcFdcqDO : bdcFdcqDOList){
            if (null == bdcFdcqDO.getFttdmj()){
                bdcFdcqDO.setFttdmj(Double.valueOf("0"));
            }

            if (null == bdcFdcqDO.getDytdmj()){
                bdcFdcqDO.setDytdmj(Double.valueOf("0"));
            }

            double val = this.add(bdcFdcqDO.getFttdmj(),bdcFdcqDO.getDytdmj());
            dyaTdmj = this.add(dyaTdmj,val);
        }

        return dyaTdmj;
    }

    @Override
    public List<Map> getSumMjByGhyt(BdcBdcdyQO bdcBdcdyQO){
        if (StringUtils.isBlank(bdcBdcdyQO.getGzlslid())){
            throw new AppException("房屋用途统计缺失工作流实例ID");
        }
        Map map = new HashMap();
        if (CollectionUtils.isNotEmpty(bdcBdcdyQO.getXmidList())){
            List<List> partList = ListUtils.subList(bdcBdcdyQO.getXmidList(),1000);
            map.put("partList",partList);

        }
        map.put("gzlslid",bdcBdcdyQO.getGzlslid());
        map.put("sfyql",bdcBdcdyQO.isSfyql());
        return bdcFdcqMapper.getSumFdcqMjByGhyt(map);

    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询预告
     */
    @Override
    public List listBdcYgByXmid(String xmid){
        return bdcYgMapper.listBdcYgByXmid(xmid);
    }

    /**
     * @param bdcXmQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据单元号查询是否办理过抵押权
     * @date : 2020/8/3 14:03
     */
    @Override
    public List<BdcDyaqDO> listExistDyaq(BdcXmQO bdcXmQO){
        if (null == bdcXmQO || (StringUtils.isBlank(bdcXmQO.getBdcdyh()) || StringUtils.isBlank(bdcXmQO.getXmid()))){
            throw new MissingArgumentException("缺失查询参数，xmid和不动产单元号不能都为空！");
        }
        return bdcDyaqMapper.listExistDyaq(bdcXmQO);
    }


    /**
     * @param bdcXmQO 抵押项目
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断 xmid、gzlslid 是否都为空
     */
    private boolean isAllParamBlank(BdcXmQO bdcXmQO){
        return StringUtils.isBlank(bdcXmQO.getGzlslid())
                && StringUtils.isBlank(bdcXmQO.getXmid());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description double 求和
     */
    private double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询预告
     */
    @Override
    public List listBdcYgByBdcdyh(String bdcdyh){
        return bdcBdcdyMapper.listBdcYgBybdcdyh(bdcdyh);
    }

    /**
     * @param bdcdyhList 不动产单元号集合
     * @return {List} 房屋性质
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询抵押物清单列表单元的房屋信息
     */
    @Override
    public List<BdcDyawqdVO> listBdcDyawqdFwxx(List<String> bdcdyhList){
        if (CollectionUtils.isEmpty(bdcdyhList)){
            return Collections.emptyList();
        }

        return bdcBdcdyMapper.listBdcDyawqdFwxx(bdcdyhList);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积和
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 计算建设用地使用权面积之和
     */
    @Override
    public BdcdySumDTO calculatedJsydsyqMj(String gzlslid,Boolean sfyql,String djxl){
        Map map = new HashMap();
        map.put("gzlslid",gzlslid);
        map.put("sfyql",sfyql);
        map.put("djxl",djxl);
        return bdcJsydsyqMapper.calculatedJsydsyqMj(map);
    }

    @Override
    public List<Map> listBdcFdcqXmxx(BdcCqQO bdcCqQO){
        List<Map> list = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcCqQO.getBdcdyhList())) {
            if (bdcCqQO.getBdcdyhList().size() > 500){
                List<List> lists = ListUtils.subList(bdcCqQO.getBdcdyhList(), 500);
                for (List partBdcdys : lists) {
                    bdcCqQO.setBdcdyhList(partBdcdys);
                    List<Map> listBdcFdcqXmxx = bdcBdcdyMapper.listBdcFdcqXmxx(bdcCqQO);
                    if (CollectionUtils.isNotEmpty(listBdcFdcqXmxx)) {
                        list.addAll(listBdcFdcqXmxx);
                    }
                }
                return list;
            }else {
                return bdcBdcdyMapper.listBdcFdcqXmxx(bdcCqQO);
            }
        }
        return list;
    }

    @Override
    public List<Map> listBdcJsydsyqXmxx(BdcCqQO bdcCqQO) {
        if (CollectionUtils.isNotEmpty(bdcCqQO.getBdcdyhList())) {
            return bdcBdcdyMapper.listBdcJsydsyqXmxx(bdcCqQO);
        }
        return new ArrayList<>();
    }

    /**
     * @param bdcDyaQo
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人信息查询抵押权数据
     * @date : 2021/9/14 9:06
     */
    @Override
    public List<BdcDyaqQlrDTO> listBdcDyqByQlrxx(BdcDyaQo bdcDyaQo) {
        List<BdcDyaqQlrDTO> bdcDyaqQlrDTOList =new ArrayList<>();
        //项目ID集合与受理编号集合其中一个有值
        if(CollectionUtils.isEmpty(bdcDyaQo.getXmidList()) &&CollectionUtils.isEmpty(bdcDyaQo.getSlbhList())){
            return bdcDyaqQlrDTOList;

        }
        if(CollectionUtils.isNotEmpty(bdcDyaQo.getXmidList())){
            List<List> partList = ListUtils.subList(bdcDyaQo.getXmidList(), 500);
            for (List partXmidList : partList) {
                bdcDyaQo.setXmidList(partXmidList);
                List<BdcDyaqQlrDTO> bdcDyaqQlrDTOS=bdcDyaqMapper.listBdcDyaqByqlrxx(bdcDyaQo);
                if(CollectionUtils.isNotEmpty(bdcDyaqQlrDTOS)){
                    bdcDyaqQlrDTOList.addAll(bdcDyaqQlrDTOS);
                }

            }
            return bdcDyaqQlrDTOList;
        }else {
            List<List> partList = ListUtils.subList(bdcDyaQo.getSlbhList(), 500);
            for (List partSlbhList : partList) {
                bdcDyaQo.setSlbhList(partSlbhList);
                List<BdcDyaqQlrDTO> bdcDyaqQlrDTOS=bdcDyaqMapper.listBdcDyaqByqlrxx(bdcDyaQo);
                if(CollectionUtils.isNotEmpty(bdcDyaqQlrDTOS)){
                    bdcDyaqQlrDTOList.addAll(bdcDyaqQlrDTOS);
                }
            }
            return bdcDyaqQlrDTOList;
        }

    }

    @Override
    public List<BdcLsgxXmDTO> listBdccfdjls(BdcLsgxQO bdcLsgxQO) {
        if (null == bdcLsgxQO || StringUtils.isBlank(bdcLsgxQO.getBdcdyh())) {
            throw new NullPointerException("参数不动产单元号为空！");
        }


        return bdcBdcdyMapper.listBdccfdjls(bdcLsgxQO);
    }

    /**
     * @param qxdm
     * @param zdtzm
     * @param dzwtzm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 生成虚拟单元号
     * @date : 2021/12/30 15:43
     */
    /**
     * 生成虚拟不动产单元号，修改项目表中对应的 bdcdyh
     * 虚拟号设置规则： bdcdyhPrefix + 0000000X（X 表示顺序号）
     *
     * @return List<BdcXmDO> 初始化后的项目信息
     */
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_XNBDCDYH, description = "获取最大虚拟不动产单元号")
    @Override
    public String createXndyh(String qxdm, String zdtzm, String dzwtzm) {
        String xndyh = "";
        if (StringUtils.isNoneBlank(qxdm, zdtzm, dzwtzm)) {
            String bdcdyhPrefix = qxdm + CommonConstantUtils.ZDZHSXH_XN + zdtzm + "00000" + dzwtzm;
            // 获取数据库中最大的不动产单元号
            String bdcdyh = bdcXmMapper.queryMaxXnBdcdyh(bdcdyhPrefix);
            LOGGER.info("当前生成虚拟单元号qxdm={}zdtzm={},dzwtzm={},生成单元号前缀={},根据前缀获取到的最大单元号{}", qxdm, zdtzm, dzwtzm, bdcdyhPrefix, bdcdyh);
            // 获取最后的 8 位顺序
            String order = StringUtils.substring(bdcdyh, 20, 28);
            // 数据库中没有对应的虚拟单元号 或 数据问题 没有后面 8 位，就用 0 补齐
            if (StringUtils.isBlank(bdcdyh) || StringUtils.isBlank(order)) {
                xndyh = StringUtils.rightPad(bdcdyhPrefix, 28, "0");
                LOGGER.info("数据库中根据前缀{}未找到最大虚拟号,在前缀后拼接8个0，最终单元号为{}", bdcdyhPrefix, xndyh);
            } else {
                // 顺序号 + 1
                int number = Integer.parseInt(order) + 1;
                // 转换后避免位数不够的情况补齐 0
                String bdcdyhSuffix = Integer.toString(number);
                xndyh = bdcdyhSuffix.length() < 8 ? bdcdyhPrefix + StringUtils.leftPad(bdcdyhSuffix, 8, "0") : bdcdyhPrefix + bdcdyhSuffix;
                LOGGER.info("根据前缀{}找到最大虚拟号+1生成虚拟单元号{}", bdcdyhPrefix, xndyh);
            }
        }
        return xndyh;
    }

    @Override
    public BdcdySumDTO computeFdcqMjByDjh(String djh) {
        if (StringUtils.isBlank(djh)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获到地籍号参数");
        }
        return bdcFdcqMapper.computeFdcqMjByDjh(djh);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房地产权使用权结束时间去重后的数据
     * @date : 2022/9/7 19:18
     */
    @Override
    public TdSyqJssjDTO listDistinctTdsyjssj(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            TdSyqJssjDTO tdSyqJssjDTO = new TdSyqJssjDTO();
            tdSyqJssjDTO.setTdsyqjssj1(bdcFdcqMapper.listDistinctTdsyjssj(gzlslid, "tdsyqjssj"));
            tdSyqJssjDTO.setTdsyqjssj2(bdcFdcqMapper.listDistinctTdsyjssj(gzlslid, "tdsyqjssj2"));
            tdSyqJssjDTO.setTdsyqjssj3(bdcFdcqMapper.listDistinctTdsyjssj(gzlslid, "tdsyqjssj3"));
            return tdSyqJssjDTO;
        }
        return new TdSyqJssjDTO();
    }

    @Override
    public String getDywqdPdf(BdcPrintDTO bdcPrintDTO){
        try {
            //获取xml
            String xml = bdcdyPrintXml(bdcPrintDTO);
            if (StringUtils.isBlank(xml) ||"null".equals(xml)) {
                LOGGER.error("{}未获取到抵押物清单xml数据,打印类型：{}", bdcPrintDTO.getGzlslid(), CommonConstantUtils.DYWQD_DYLX);
                return null;
            }
            // 生成PDF
            OfficeExportDTO pdfExportDTO = new OfficeExportDTO();

            // 读取打印数据源上传的模板
            BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
            bdcDysjPzDO.setDylx(bdcPrintDTO.getDylx());
            List<BdcDysjPzDO> dysjPzDOList = bdcDypzService.listBdcDysjPz(bdcDysjPzDO);
            if (CollectionUtils.isNotEmpty(dysjPzDOList) && null != dysjPzDOList.get(0) && StringUtils.isNotBlank(dysjPzDOList.get(0).getPdfpath())) {
                pdfExportDTO.setModelName(dysjPzDOList.get(0).getPdfpath());
            } else {
                pdfExportDTO.setModelName(printPath + bdcPrintDTO.getDylx() + ".docx");
            }
            pdfExportDTO.setFileName("抵押物清单");
            pdfExportDTO.setXmlData(xml);

            String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
            return Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(new File(pdfFilePath)));
        }catch (Exception e){
            LOGGER.error("{}生成抵押物清单失败",bdcPrintDTO.getGzlslid(),e);
            throw new AppException("生成抵押物清单PDF失败");
        }
    }

}