package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.building.FwJsydzrzxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmJsydlhxxGxDTO;
import cn.gtmap.realestate.common.core.enums.BdcLhlxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlqtzkFjQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdJsydLhxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.BdcdyRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.core.domain.BdcJsydLhFjxx;
import cn.gtmap.realestate.register.core.mapper.BdcJsydLhxxMapper;
import cn.gtmap.realestate.register.core.thread.BdcLhxxCzrzThread;
import cn.gtmap.realestate.register.service.BdcJsydLhxxService;
import cn.gtmap.realestate.register.service.BdcLhxxCzrzService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 建设用地量化信息服务
 */
@Service
@ConfigurationProperties(prefix = "lhxx")
public class BdcJsydLhxxServiceImpl implements BdcJsydLhxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJsydLhxxServiceImpl.class);

    @Autowired
    private BdcJsydLhxxMapper bdcJsydLhxxMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    ZdJsydLhxxFeignService zdJsydLhxxFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;

    @Autowired
    BdcDypzFeignService bdcDypzFeignService;

    @Autowired
    BdcDysjPzService bdcDysjPzService;

    @Autowired
    BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    BdcLhxxCzrzService bdcLhxxCzrzService;

    @Autowired
    BdcdyRestService bdcdyRestService;

    @Autowired
    PdfController pdfController;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    ThreadEngine threadEngine;

    /**
     * 量化附记初始化展示信息
     */
    private static final Map<String,Object> lhxxMap = new HashMap<>(8);

    static {
        lhxxMap.put("gxlz", "*.*.*.");
        lhxxMap.put("gxfe", "*%");
        lhxxMap.put("wgxlz", "*、*、*");
        lhxxMap.put("wgxfe", "*%");
        lhxxMap.put("sdgxfe","*%");
        lhxxMap.put("sdwgxfe","*%");
        lhxxMap.put("sdgxlz", "*.*.*.");
        lhxxMap.put("sdwgxlz", "*、*、*");
    }

    /**
     * 打印文件路径
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 采用首次登记附记表述方式的流程
     */
    @Value("#{'${lhxx.scdjfj.gzldyids:}'.split(',')}")
    private List<String> scdjGzldyids;
    /**
     * 采用查封登记附记表述方式的流程
     */
    @Value("#{'${lhxx.cfdjfj.gzldyids:}'.split(',')}")
    private List<String> cfdjGzldyids;
    /**
     * 抵押类型的量化附记表述模板
     */
    private List<String> dyaFjModelList;
    /**
     * 首次登记类型的量化附记表述模板
     */
    private List<String> scdjFjModelList;
    /**
     * 查封登记类型的量化附记表述模板
     */
    private List<String> cfdjFjModelList;

    public List<String> getDyaFjModelList() {
        return dyaFjModelList;
    }

    public void setDyaFjModelList(List<String> dyaFjModelList) {
        this.dyaFjModelList = dyaFjModelList;
    }

    public List<String> getScdjFjModelList() {
        return scdjFjModelList;
    }

    public void setScdjFjModelList(List<String> scdjFjModelList) {
        this.scdjFjModelList = scdjFjModelList;
    }

    public List<String> getCfdjFjModelList() {
        return cfdjFjModelList;
    }

    public void setCfdjFjModelList(List<String> cfdjFjModelList) {
        this.cfdjFjModelList = cfdjFjModelList;
    }

    @Override
    public List<FwJsydzrzxxDO> listJsydLhxx(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("工作流实例ID为空");
        }

        //查询关系表获取建设用地逻辑幢主键集合
        List<String> jsydzrzxxidList =bdcJsydLhxxMapper.listJsydzrzxxid(gzlslid);
        if(CollectionUtils.isNotEmpty(jsydzrzxxidList)) {
            FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
            fwJsydzrzxxQO.setFwJsydzrzxxIndexList(jsydzrzxxidList);
            return zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
        }
        return Collections.emptyList();
    }

    @Override
    public void batchInsertJsydlhxxGx(BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO) {
        if(CollectionUtils.isEmpty(bdcXmJsydlhxxGxDTO.getJsydzrzxxidList())
                && org.apache.commons.lang.StringUtils.isBlank(bdcXmJsydlhxxGxDTO.getGzlslid())){
            throw new AppException("缺失建设用地主键信息或工作流实例ID参数。");
        }
        List<BdcXmJsydlhxxGxDO> bdcXmJsydlhxxGxDOList = new ArrayList<>(bdcXmJsydlhxxGxDTO.getJsydzrzxxidList().size());
        String gzlslid = bdcXmJsydlhxxGxDTO.getGzlslid();
        for(String jsydzrzxxid : bdcXmJsydlhxxGxDTO.getJsydzrzxxidList()){
            if(StringUtils.isNotBlank(jsydzrzxxid)){
                BdcXmJsydlhxxGxDO bdcXmJsydlhxxGxDO = new BdcXmJsydlhxxGxDO();
                bdcXmJsydlhxxGxDO.setGzlslid(gzlslid);
                bdcXmJsydlhxxGxDO.setJsydzrzxxid(jsydzrzxxid);
                bdcXmJsydlhxxGxDO.setGxid(UUIDGenerator.generate16());
                bdcXmJsydlhxxGxDOList.add(bdcXmJsydlhxxGxDO);
            }
        }
        if(CollectionUtils.isNotEmpty(bdcXmJsydlhxxGxDOList)){
            this.entityMapper.batchSaveSelective(bdcXmJsydlhxxGxDOList);
        }
    }

    @Override
    public void deleteJsydlhxxGxByGzlslid(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)) {
           throw new AppException("缺失参数工作流实例ID。");
        }
        Example example = new Example(BdcXmJsydlhxxGxDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        entityMapper.deleteByExample(example);
    }

    /**
     * 登簿时修改建设用地量化抵押状态逻辑如下：
     * 宗地上包含楼幢 1、2、3、4、5、6
     * （1）当前流程选择了 1、2、3楼幢， 则将4、5、6更改为量化抵押状态。
     * （2）若1、2 为量化抵押状态， 当前流程选择的3、4楼幢，则将 1、2、5、6更改为量化抵押状态。
     */
    @Override
    public void updateJsydLhDyaqZt(String gzlslid) {
        if(StringUtils.isNotBlank(gzlslid) && isDyaqlc(gzlslid)){
            // 获取选择的楼盘所属宗地
            List<String> jsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(gzlslid);
            if(CollectionUtils.isNotEmpty(jsydzrzxxidList)) {
                FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
                fwJsydzrzxxQO.setFwJsydzrzxxIndexList(jsydzrzxxidList);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
                // 修改当前宗地上的楼盘为量化抵押状态
                if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList) && StringUtils.isNotBlank(fwJsydzrzxxDOList.get(0).getLszd())){
                    FwJsydzrzxxQO param = new FwJsydzrzxxQO();
                    param.setLszd(fwJsydzrzxxDOList.get(0).getLszd());
                    param.setLhdycs(CommonConstantUtils.SF_S_DM);
                    this.zdJsydLhxxFeignService.updateFwJsydzrzxxLhdycsPl(param);
                }
                // 修改选择的楼幢为正常状态
                fwJsydzrzxxQO.setLhdycs(CommonConstantUtils.SF_F_DM);
                this.zdJsydLhxxFeignService.updateFwJsydzrzxxLhdycsPl(fwJsydzrzxxQO);
            }
        }
    }

    /**
     * 登簿时修改建设用地量化首登权利状态，更新逻辑如下：
     * <p> 宗地上楼幢：1、2、3、4、5
     *  当前流程选择了：1、2，则更新 1、2 为量化首登状态</p>
     */
    @Override
    public void updateJsydLhsdqlzt(String gzlslid) {
        if(StringUtils.isNotBlank(gzlslid)){
            // 获取选择的楼盘所属宗地
            List<String> jsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(gzlslid);
            if(CollectionUtils.isNotEmpty(jsydzrzxxidList)) {
                FwJsydzrzxxQO param = new FwJsydzrzxxQO();
                param.setFwJsydzrzxxIndexList(jsydzrzxxidList);
                param.setLhsdqlzt(CommonConstantUtils.SF_S_DM);
                this.zdJsydLhxxFeignService.updateFwJsydzrzxxZtPl(param);
            }
        }
    }

    /**
     * 抵押权变更流程，对勾选的楼幢的状态进行 -1 操作
     */
    @Override
    public void updateJsydLhDyaBgZt(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            return;
        }
        // 获取原项目信息
        List<BdcXmDO> yxmList = this.bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), gzlslid, null);
        if(CollectionUtils.isEmpty(yxmList)){
            return;
        }
        // 获取原项目选择的楼盘所属宗地
        List<String> yxmJsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(yxmList.get(0).getGzlslid());
        // 获取当前勾选的量化逻辑幢
        List<String> jsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(gzlslid);
        // 和上一手比对，相同不做处理。 新增的 -1 ； 缺失的 +1

        if(CollectionUtils.isEmpty(yxmJsydzrzxxidList) && CollectionUtils.isEmpty(jsydzrzxxidList)){
            return;
        }
        List<String> addList = new ArrayList<>();  // 新增的逻辑幢
        List<String> missList = new ArrayList<>();  // 缺少的逻辑幢

        if(CollectionUtils.isEmpty(yxmJsydzrzxxidList) && CollectionUtils.isNotEmpty(jsydzrzxxidList)){
            // 原项目关联为空，当前项目存在关联，则当前勾选的 lhdycs -1
            missList = jsydzrzxxidList;
        }
        if(CollectionUtils.isNotEmpty(yxmJsydzrzxxidList) && CollectionUtils.isEmpty(jsydzrzxxidList)){
            // 原项目关联不为空，当前关联为空时， 则原项目关联的 lhdycs +1
            addList = yxmJsydzrzxxidList;
        }

        // 比对当前与上一手关联的量化楼幢信息
        if(CollectionUtils.isNotEmpty(yxmJsydzrzxxidList) && CollectionUtils.isNotEmpty(jsydzrzxxidList)){
            for(String yxmJsydZrzxxId : yxmJsydzrzxxidList){
                if(!jsydzrzxxidList.contains(yxmJsydZrzxxId)){
                    addList.add(yxmJsydZrzxxId);
                }
            }
            for(String jsydzrzxxid : jsydzrzxxidList){
                if(!yxmJsydzrzxxidList.contains(jsydzrzxxid)){
                    missList.add(jsydzrzxxid);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(addList)){
            this.addJsydZrzLhdycs(queryFwJsydzrzxxById(addList));
        }
        if(CollectionUtils.isNotEmpty(missList)){
            this.subtractJsydZrzLhdycs(queryFwJsydzrzxxById(missList));
        }
    }

    // 根据建设用地自然幢ID获取建设用地自然幢信息
    private List<FwJsydzrzxxDO> queryFwJsydzrzxxById(List<String> jsydzrzxxidList){
        if(CollectionUtils.isNotEmpty(jsydzrzxxidList)){
            FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
            fwJsydzrzxxQO.setFwJsydzrzxxIndexList(jsydzrzxxidList);
            return this.zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
        }
        return new ArrayList<>();
    }

    /**
     * 抵押权流程：
     * （1）对勾选的楼幢状态不变，其余楼幢状态进行 +1 操作
     * （2）未勾选楼幢，对宗地上面所有的楼幢状态进行 +1 操作
     */
    @Override
    public void updateJsydLhDyZt(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            return;
        }
        // 获取当前地籍号
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList) && StringUtils.isNotBlank(bdcXmDTOList.get(0).getBdcdyh())){
            String djh = StringUtils.substring(bdcXmDTOList.get(0).getBdcdyh(), 0, 19);
            // 获取选择的楼盘所属宗地
            List<String> jsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(gzlslid);
            if(CollectionUtils.isEmpty(jsydzrzxxidList)){
                // 未选择楼幢，对宗地上所有楼幢 lhdycs+1
                FwJsydzrzxxQO param = new FwJsydzrzxxQO();
                param.setLszd(djh);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(param);
                this.addJsydZrzLhdycs(fwJsydzrzxxDOList);
            }else{
                // 已勾选楼幢， 获取宗地上未勾选的楼幢
                FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
                fwJsydzrzxxQO.setLszd(djh);
                fwJsydzrzxxQO.setNotInfwJsydzrzxxIndexList(jsydzrzxxidList);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
                this.addJsydZrzLhdycs(fwJsydzrzxxDOList);
            }
        }
    }

    /**
     * 对自然幢的量化抵押次数 +1
     */
    private void addJsydZrzLhdycs(List<FwJsydzrzxxDO> fwJsydzrzxxDOList){
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
            for(FwJsydzrzxxDO fwJsydzrzxxDO: fwJsydzrzxxDOList){
                Integer lhdycs = fwJsydzrzxxDO.getLhdycs();
                // 量化权利状态为空，或者为 0 时，设置 lhdycs 为0
                if(null == lhdycs){
                    fwJsydzrzxxDO.setLhdycs(1);
                }else{
                    fwJsydzrzxxDO.setLhdycs(++lhdycs);
                }
            }
            this.zdJsydLhxxFeignService.updateFwJsydzrzxxPl(fwJsydzrzxxDOList);
        }
    }

    /**
     * <p>抵押注销，更改量化抵押次数
     *  根据项目历史关系，获取上一手勾选量化信息
     *  （1）上一手未勾选量化信息时，获取宗地上所有量化抵押楼幢 lhdycs-1 处理
     *  （2）上一手勾选量化信息时，将未勾选的楼幢 lhdycs-1 处理
     * </p>
     */
    @Override
    public void updateJsydLhDyaZxZt(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            return;
        }
        // 判断是否土地还是房屋，房屋不做处理
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            if(!Objects.equals(Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM), bdcXmDOList.get(0).getBdclx())){
                return;
            }
        }

        // 获取原项目信息
        List<BdcXmDO> yxmList = this.bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), gzlslid, null);
        if(CollectionUtils.isEmpty(yxmList)){
            return;
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList) && StringUtils.isNotBlank(bdcXmDTOList.get(0).getBdcdyh())){
            // 获取原项目选择的楼盘所属宗地
            List<String> jsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(yxmList.get(0).getGzlslid());
            String djh = StringUtils.substring(bdcXmDTOList.get(0).getBdcdyh(), 0, 19);
            if(CollectionUtils.isEmpty(jsydzrzxxidList)){
                FwJsydzrzxxQO param = new FwJsydzrzxxQO();
                param.setLszd(djh);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(param);
                // 量化抵押次数 -1 操作
                this.subtractJsydZrzLhdycs(fwJsydzrzxxDOList);
            }else{
                // 有勾选量化信息，获取未勾选的楼幢，抵押次数 -1 操作
                FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
                fwJsydzrzxxQO.setNotInfwJsydzrzxxIndexList(jsydzrzxxidList);
                fwJsydzrzxxQO.setLszd(djh);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
                // 量化抵押次数 -1 操作
                this.subtractJsydZrzLhdycs(fwJsydzrzxxDOList);
            }
        }
    }

    /**
     * 对量化抵押次数 -1 操作
     */
    private void subtractJsydZrzLhdycs(List<FwJsydzrzxxDO> fwJsydzrzxxDOList){
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
            for(FwJsydzrzxxDO fwJsydzrzxxDO: fwJsydzrzxxDOList){
                Integer lhdycs = fwJsydzrzxxDO.getLhdycs();
                // 量化权利状态为空，或者为 0 时，设置 lhdycs 为0
                if(null == lhdycs || Objects.equals(0, lhdycs)){
                    fwJsydzrzxxDO.setLhdycs(0);
                }else{
                    fwJsydzrzxxDO.setLhdycs(--lhdycs);
                }
            }
            this.zdJsydLhxxFeignService.updateFwJsydzrzxxPl(fwJsydzrzxxDOList);
        }
    }

    /**
     * 根据工作流实例ID判断当前流程是否为抵押流程
     */
    private boolean isDyaqlc(String gzlslid){
        boolean isDyaqlc = false;
        List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(gzlslid);
        for(String qllx:qllxList){
            if(CommonConstantUtils.QLLX_DYAQ_DM.toString().equals(qllx)){
                isDyaqlc = true;
                break;
            }
        }
        return isDyaqlc;
    }

    @Override
    public Object initLhxxFj(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID.");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目信息");
        }
        String gzldyid = bdcXmDOList.get(0).getGzldyid();
        return  this.getFjModel(gzldyid);
    }

    /**
     * 根据工作流实例ID获取附记模板
     * @param gzldyid 工作流定义ID
     * @return Map<附记模板（用于后续生成附记内容）, 占位符替换后的附记模板（用于页面展现）>
     */
    public Map<String, String> getFjModel(String gzldyid){
        Map<String, String> modelMap = new LinkedHashMap<>(4);
        if(StringUtils.isBlank(gzldyid)){
            return modelMap;
        }
        if(CollectionUtils.isNotEmpty(scdjGzldyids) && scdjGzldyids.contains(gzldyid)){
            // 采用首次登记量化附记模板
            if(CollectionUtils.isNotEmpty(scdjFjModelList)){
                for(String scdjFjModel: scdjFjModelList){
                    modelMap.put(scdjFjModel, mbParamReplace(scdjFjModel, lhxxMap));
                }
            }
        }else if(CollectionUtils.isNotEmpty(cfdjGzldyids) && cfdjGzldyids.contains(gzldyid)){
            // 采用查封登记量化附记模板
            if(CollectionUtils.isNotEmpty(cfdjFjModelList)){
                for(String cfdjFjModel: cfdjFjModelList){
                    modelMap.put(cfdjFjModel, mbParamReplace(cfdjFjModel, lhxxMap));
                }
            }
        }else{
            // 采用抵押登记量化附记模板
            if(CollectionUtils.isNotEmpty(dyaFjModelList)){
                for(String dyaFjModel: dyaFjModelList){
                    modelMap.put(dyaFjModel, mbParamReplace(dyaFjModel, lhxxMap));
                }
            }
        }
        return modelMap;
    }

    /**
     * 替换模板中 #{} 标签
     */
    private static String mbParamReplace(String mb, Map map) {
        // 获取参数
        if (map != null && StringUtils.isNotBlank(mb)) {
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                String key = object.toString();
                if (StringUtils.isNotBlank(key) && map.get(key) != null) {
                    mb = mb.replaceFirst("(?i)#\\{" + key + "\\}", map.get(key).toString());
                }
            }
        }
        return mb;
    }

    @Override
    public void generateLhFjxxAndModifyFj(String gzlslid, String fjModel) {
        if(StringUtils.isAnyBlank(gzlslid, fjModel)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID或附记表述内容。");
        }

        List<FwJsydzrzxxDO> ygxFwJsydzrzxxDOList = this.listJsydLhxx(gzlslid);
        if(CollectionUtils.isNotEmpty(ygxFwJsydzrzxxDOList)){
            // 获取未勾选的逻辑幢
            FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
            fwJsydzrzxxQO.setLszd(ygxFwJsydzrzxxDOList.get(0).getLszd());
            List<FwJsydzrzxxDO> allFwJsydzrzxxList = zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);

            // 创建量化附记领域模型
            BdcJsydLhFjxx bdcJsydLhFjxx = new BdcJsydLhFjxx(fjModel, ygxFwJsydzrzxxDOList, allFwJsydzrzxxList);
            String lhxxfj = bdcJsydLhFjxx.generateLhfj();

            // 获取附记内容并追加量化附记内容
            addFjxxAndModifyFj(lhxxfj, gzlslid);
        }
    }

    @Override
    public void generateLhLjzPdf(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID.");
        }
        List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.listJsydLhxx(gzlslid);
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
            String lszd = fwJsydzrzxxDOList.get(0).getLszd();
            if(StringUtils.isBlank(lszd)){
                throw new AppException(ErrorCode.CUSTOM, "缺失参数隶属宗地信息");
            }
            FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
            fwJsydzrzxxQO.setLszd(lszd);
            List<FwJsydzrzxxDO> zrzxxList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
            LOGGER.error("获取到的逻辑幢信息为：{}", JSON.toJSONString(zrzxxList));
            // 构建 BdcPdfDTO 对象
            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
            bdcPdfDTO.setDylx(Constants.DYLX_JSYD_LJZ);
            bdcPdfDTO.setGzlslid(gzlslid);
            bdcPdfDTO.setFoldName("量化信息");
            bdcPdfDTO.setPdffjmc("所有楼幢信息");
            bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);

            // 生成PDF文件
            OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
            pdfExportDTO.setModelName(printPath + bdcPdfDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX);
            pdfExportDTO.setFileName(bdcPdfDTO.getDylx() + bdcPdfDTO.getGzlslid());
            String xmlData = this.getJsydZrzxxPrintXml(gzlslid, zrzxxList);
            LOGGER.error("逻辑幢xmldata: {}", xmlData);
            pdfExportDTO.setXmlData(xmlData);
            String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

            // 5、上传pdf文件至大云
            try{
                bdcPdfDTO.setPdfFilePath(pdfFilePath);

                this.bdcUploadFileUtils.uploadPdfByFilePath(bdcPdfDTO);
            }catch (IOException e){
                throw new AppException("上传PDF文件至大云中心出错，错误信息为：" + e.getMessage());
            }
        }
    }

    @Override
    public boolean checkXsDyaByLszd(String lszd) {
        if(StringUtils.isNotBlank(lszd)){
            String zdbdcdyh = lszd + "W00000000";
            Example example = new Example(BdcDyaqDO.class);
            example.createCriteria().andEqualTo("bdcdyh", zdbdcdyh);
            example.createCriteria().andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
            List<BdcDyaqDO> bdcDyaqDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcDyaqDOList)){
                return true;
            }
        }
        return false;
    }

    /**
     * 生成建设用地自然幢打印数据
     */
    private String getJsydZrzxxPrintXml(String gzlslid, List<FwJsydzrzxxDO> zrzxxList){
        // 1、查询打印配置
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(Constants.DYLX_JSYD_LJZ);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)){
            throw new AppException("未获取到打印配置,请检查配置");
        }
        // 2、主表数据
        Map<String, String> parentData = new HashMap<>(2);
        parentData.put("gzlslid", gzlslid);
        parentData.put("lszd", zrzxxList.get(0).getLszd());
        //通过打印数据源sql配置转换接口数据
        if(StringUtils.isNotBlank(bdcDysjPzDOList.get(0).getDysjy())) {
            Map sjldatas = bdcDysjPzService.queryPrintDatasList( parentData, "bdcRegisterConfigMapper", bdcDysjPzDOList);
            if (sjldatas != null) {
                parentData.putAll(sjldatas);
            }
        }
        // 3、设置子表数据
        Multimap<String, List> childData = ArrayListMultimap.create();
        childData.put("ljzxx", zrzxxList);

        // 4、设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * 获取附记内容并追加量化附记内容
     * @param lhfj  量化附记内容
     * @param gzlslid  工作流实例ID
     */
    private void addFjxxAndModifyFj(String lhfj, String gzlslid){
        if(StringUtils.isNotBlank(lhfj) && StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                for(BdcXmDO bdcXmDO : bdcXmDOList){
                    String mode = CommonConstantUtils.XT_QLQTZK_FJ_MODE_FJ;
                    // 调用初始化服务，根据附记模板生成附记内容
                    String mbnr = bdcZsInitFeignService.queryQlqtzkFj(bdcXmDO.getXmid(), mode);
                    // 往附记内容中追加量化信息的附记信息
                    if(StringUtils.isNotBlank(mbnr)){
                        mbnr += CommonConstantUtils.ZF_HH_CHAR + CommonConstantUtils.ZF_HH_CHAR + lhfj;
                    }else{
                        mbnr = lhfj;
                    }
                    // 更新附记信息
                    List<String> modeList = new ArrayList<>();
                    modeList.add(mode);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("fj", mbnr);
                    jsonObject.put("xmid", bdcXmDO.getXmid());

                    BdcQlqtzkFjQO bdcQlqtzkFjQO = new BdcQlqtzkFjQO();
                    bdcQlqtzkFjQO.setModeList(modeList);
                    bdcQlqtzkFjQO.setJsonStr(JSONObject.toJSONString(jsonObject));
                    // 将模板内容直接更新到数据库
                    bdcZsInitFeignService.updateQlqtzkAndFj(bdcQlqtzkFjQO);
                }
            }
        }
    }

    @Override
    public String checkWgxLzjSfYygOrYsd(BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO) {
        if(CollectionUtils.isEmpty(bdcXmJsydlhxxGxDTO.getJsydzrzxxidList()) || StringUtils.isBlank(bdcXmJsydlhxxGxDTO.getDjh())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到自然幢逻辑幢信息和地籍号信息");
        }
        FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
        fwJsydzrzxxQO.setLszd(bdcXmJsydlhxxGxDTO.getDjh());
        fwJsydzrzxxQO.setNotInfwJsydzrzxxIndexList(bdcXmJsydlhxxGxDTO.getJsydzrzxxidList());

        // 获取宗地上未勾选的逻辑幢信息
        List<FwJsydzrzxxDTO> fwJsydzrzxxDTOList = this.zdJsydLhxxFeignService.listFwJsydzrzxxWithZt(fwJsydzrzxxQO);
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDTOList)){
            List<String> dhList = fwJsydzrzxxDTOList.stream().filter(t-> Objects.equals(t.getSfyg(), 1) || Objects.equals(t.getLhsdqlzt(), 1))
                    .map(FwJsydzrzxxDTO::getDh).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(dhList)){
                return StringUtils.join(dhList, ",") + "幢号已预告/已首登，不应在抵押范围内，是否确定办理？";
            }
        }
        return null;
    }


    @Override
    public void updateJsydLhCfZt(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            return;
        }
        // 获取当前地籍号
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList) && StringUtils.isNotBlank(bdcXmDTOList.get(0).getBdcdyh())){
            String djh = StringUtils.substring(bdcXmDTOList.get(0).getBdcdyh(), 0, 19);
            // 获取选择的楼盘所属宗地
            List<String> jsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(processInsId);
            if(CollectionUtils.isEmpty(jsydzrzxxidList)){
                // 未选择楼幢，对宗地上所有楼幢 lhcfcs+1
                FwJsydzrzxxQO param = new FwJsydzrzxxQO();
                param.setLszd(djh);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(param);
                this.addJsydZrzLhcfcs(fwJsydzrzxxDOList, processInsId);
            }else{
                // 已勾选楼幢， 获取宗地上未勾选的楼幢
                FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
                fwJsydzrzxxQO.setLszd(djh);
                fwJsydzrzxxQO.setNotInfwJsydzrzxxIndexList(jsydzrzxxidList);
                List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
                this.addJsydZrzLhcfcs(fwJsydzrzxxDOList, processInsId);
            }
        }
    }

    @Override
    public void updateJsydLhcfzxZt(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            return;
        }
        // 获取原项目信息
        List<BdcXmDO> yxmList = this.bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), processInsId, null);
        if(CollectionUtils.isEmpty(yxmList) || StringUtils.isBlank(yxmList.get(0).getBdcdyh())){
            return;
        }

        // 获取原项目选择的楼盘所属宗地
        List<String> jsydzrzxxidList = bdcJsydLhxxMapper.listJsydzrzxxid(yxmList.get(0).getGzlslid());
        String djh = StringUtils.substring(yxmList.get(0).getBdcdyh(), 0, 19);
        if(CollectionUtils.isEmpty(jsydzrzxxidList)){
            // 未获取到原项目勾选的楼幢， 则将宗地上所有楼幢 lhcfcs - 1
            FwJsydzrzxxQO param = new FwJsydzrzxxQO();
            param.setLszd(djh);
            List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(param);
            this.subtractJsydZrzLhcfcs(fwJsydzrzxxDOList, processInsId);
        }else{
            // 有勾选楼幢，获取未勾选的楼幢将 lhcfcs - 1
            FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
            fwJsydzrzxxQO.setNotInfwJsydzrzxxIndexList(jsydzrzxxidList);
            fwJsydzrzxxQO.setLszd(djh);
            List<FwJsydzrzxxDO> fwJsydzrzxxDOList = this.zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
            this.subtractJsydZrzLhcfcs(fwJsydzrzxxDOList, processInsId);
        }
    }

    /**
     * 量化查封次数 +1 操作
     */
    private void addJsydZrzLhcfcs(List<FwJsydzrzxxDO> fwJsydzrzxxDOList, String gzlslid){
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
            for(FwJsydzrzxxDO fwJsydzrzxxDO: fwJsydzrzxxDOList){
                Integer lhcfcs = fwJsydzrzxxDO.getLhcfcs();
                if(null == lhcfcs){
                    fwJsydzrzxxDO.setLhcfcs(1);
                }else{
                    fwJsydzrzxxDO.setLhcfcs(++lhcfcs);
                }
            }
            this.zdJsydLhxxFeignService.updateFwJsydzrzxxPl(fwJsydzrzxxDOList);
            this.addLhxxCzrz(fwJsydzrzxxDOList, BdcLhlxEnum.LHCF.getCode(), Constants.LHCZ_ADD, gzlslid);
        }
    }

    /**
     * 对量化查封次数 -1 操作
     */
    private void subtractJsydZrzLhcfcs(List<FwJsydzrzxxDO> fwJsydzrzxxDOList, String gzlslid){
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
            for(FwJsydzrzxxDO fwJsydzrzxxDO: fwJsydzrzxxDOList){
                Integer lhcfcs = fwJsydzrzxxDO.getLhcfcs();
                // 量化权利状态为空，或者为 0 时，设置 lhdycs 为0
                if(null == lhcfcs || Objects.equals(0, lhcfcs)){
                    fwJsydzrzxxDO.setLhcfcs(0);
                }else{
                    fwJsydzrzxxDO.setLhcfcs(--lhcfcs);
                }
            }
            this.zdJsydLhxxFeignService.updateFwJsydzrzxxPl(fwJsydzrzxxDOList);
            this.addLhxxCzrz(fwJsydzrzxxDOList, BdcLhlxEnum.LHCF.getCode(), Constants.LHCZ_SUBTRACT, gzlslid);
        }
    }

    /**
     * 开启线程执行记录量化信息操作日志
     * @param fwJsydzrzxxDOList 需要更改状态的建设用地自然幢信息
     * @param lhlx 量化类型（量化查封、抵押、首登）
     * @param lhcz 量化操作（+1, -1）
     */
    private void addLhxxCzrz(List<FwJsydzrzxxDO> fwJsydzrzxxDOList, Integer lhlx, Integer lhcz, String gzlslid){
        List<CommonThread> listThread = new ArrayList(1);
        BdcLhxxCzrzThread bdcLhxxCzrzThread = new BdcLhxxCzrzThread(lhlx, lhcz, gzlslid, fwJsydzrzxxDOList, bdcLhxxCzrzService, bdcXmFeignService);
        listThread.add(bdcLhxxCzrzThread);
        threadEngine.excuteThread(listThread, true);
    }

}
