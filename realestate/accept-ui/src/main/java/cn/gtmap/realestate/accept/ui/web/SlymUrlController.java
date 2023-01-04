package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.ui.config.DyaqFjJzzrConfig;
import cn.gtmap.realestate.accept.ui.config.MrlzfsConfig;
import cn.gtmap.realestate.accept.ui.config.SlymAuthorityConfig;
import cn.gtmap.realestate.accept.ui.config.SlymZhlcDyanDTO;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.config.accept.ZdyZlcshXztzPzConfig;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZjjgFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.property.acceptui.DjyyControlColConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/3/19,1.0
 * @description
 */
@Controller
@RequestMapping("/url")
public class SlymUrlController extends BaseController {
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  修改内容高亮显示登记小类集合
     */
    @Value("${default.xgnrglxs:}")
    private String xgnrglxs;

    @Value("#{'${changelcqx.gzldyid:}'.split(',')}")
    private List<String> changeLcqxDyidList;
    /**
     * 页面逻辑版本,默认新版本
     */
    @Value("${ymVersion:new}")
    private String ymVersion;
    /**
     * @param noYzGhytList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 受理页面不验证土地使用结束时间的规划用途
     * @date : 2020/8/12 9:59
     */
    @Value("#{'${slym.noyztdsyjssj:}'.split(',')}")
    private List<String> noYzGhytList;

    /**
     * @param splyList
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 受理页面根据配置的审批来源控制页面属性
     * @date : 2022/5/20
     */
    @Value("#{'${slym.splyList:}'.split(',')}")
    private List<Integer> splyList;
    /*
     * 权利人页面设置默认交易份额
     * */
    @Value("#{'${slym.qlr.jyfemrz:}'.split(',')}")
    private List<String> jyfeMrzDjxlList;

    @Autowired
    DjyyControlColConfig djyyControlColConfig;
    @Autowired
    DyaqFjJzzrConfig dyaqFjJzzrConfig;

    @Autowired
    ZdyZlcshXztzPzConfig zdyZlcshXztzPzConfig;
    @Autowired
    BdcZjjgFeignService bdcZjjgFeignService;

    @Value("${mk.rzdburl:}")
    private String rzdburl;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 金额单位
     */
    @Value("${jedw:万元}")
    private String jedw;

    @Value("#{'${cg:}'.split(',')}")
    private List<String> cgList;

    @Value("${qlr.sfczr:0}")
    private Integer sfczr;

    @Value("#{'${slym.changeqljbxx.gzldyid:}'.split(',')}")
    private List<String> changeqljbxxDyidList;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Value("${slym.zhsjcl:false}")
    private Boolean zhsjcl;
    @Value("#{'${slym.sdqgh.djxl:}'.split(',')}")
    private List<String> sdqghDjxlList;
    @Value("#{'${slym.bxzywr.gzldyids:}'.split(',')}")
    private List<String> bxzYwrGzldyids;
    @Value("#{'${xztz.showZjjg.gzldyids:}'.split(',')}")
    private List<String> showZjjgDyids;
    @Value("${slym.printBtn:}")
    private String slymPrintBtn;
    @Value("#{'${query.plcjlc.gzldyids:}'.split(',')}")
    private List<String> plcjlcGzldyidList;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Value("#{'${slym.ygdjhqspfhtxx.djxl:}'.split(',')}")
    private List<String> ygdjhqspfhtxxDjxlList;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 表单是否验证必填
     */
    @Value("${slym.sfyzbt:true}")
    private Boolean sfyzbt;

    @Value("${slym.qlr.autoclose:false}")
    private Boolean qlrzdgb;

    @Value("${slym.sjcl.djyypz:false}")
    private Boolean addSjclFromDjyyPz;

    @Value("${qlr.sfczr.sxh:false}")
    private boolean sfczrBySxh;

    @Value("#{'${qlr.sfczr.notsxh.djxl:}'.split(',')}")
    private List<String> notSfczrBySxhDjxlList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 项目内多幢,不需要同步分幢面积之和到建筑面积的宗地特征码
     */
    @Value("${xmndz.nosumfzmj.zdtzm:}")
    protected String nosumfzmjZdtzm;

    /**
     * 验证单个权利人默认为单个共有。 配置为false时，允许单个权利人存在共同共有的情况
     */
    @Value("${default.gyfsdgyz:true}")
    private boolean gyfsdgyz;

    @Value("#{'${slym.dysw:1,2,3,4,5}'.split(',')}")
    private List<Integer> dysw;

    @Value("#{'${hyllcgzldyids:}'.split(',')}")
    private List<String> hyllcgzldyidList;

    @Value("${btxbz.size:14}")
    private Integer btxbzSize;

    @Value("${slym.print.zhlcsplit:false}")
    private Boolean zhlcfkdy;

    @Value("#{'${slym.print.zhajlcdyid:}'.split(',')}")
    private List<String> zhajlcList;

    /**收件单签字位置*/
    @Value("${slym.sjdqzwz:0,1,100,100}")
    private String sjdqzwz;
    /**申请书签字位置*/
    @Value("${slym.sqsqzwz:0,1,100,100}")
    private String sqsqzwz;
    /**存量房买卖合同签字位置*/
    @Value("${slym.clfmmht:0,1,100,100}")
    private String clfmmht;
    /**确认书签字位置*/
    @Value("${slym.qrsqzwz:0,1,100,100}")
    private String qrsqzwz;
    /**
     * 需要区分正常状态对应的数据流实例id
     */
    @Value("${bdcdy.zczt:}")
    private String[] zczt;

    /*
     * 规则验证是否忽略相同子规则
     * */
    @Value("${gzyz.hlxtzgz:false}")
    private boolean hlxtzgz;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理页面展示家庭成员按钮的权利类型
     */
    @Value("#{'${slym.showjtcy:9,31,35}'.split(',')}")
    private List<Integer> showjtcyQllx;

    @Value("${slym.sfscwjzx:false}")
    private Boolean sfscwjzx;

    @Value("#{'${zdjbdb.gzldyid:}'.split(',')}")
    private List<String> zdjbdbDyidList;
    /**
     *
     * @description 需要查询维修基金信息的gzldyid
     */
    @Value("#{'${slym.wxjj.gzldyid:}'.split(',')}")
    private List<String> wxjjGzldyid;

    @Value("${slym.scbcsjcl:true}")
    private boolean scbcsjcl;


    /**
     * 受理页面是否二次确认的按钮id
     */
    @Value("${slym.sfecqrids:}")
    private String sfecqrids;

    /**
     * 撤销登记流程定义id
     */
    @Value("${cxdj.gzldyid:}")
    private String cxdjGzldyid;

    @Value("${sjcl.exe:false}")
    private boolean sjclExe;

    /**
     * 匹配页面查询默认模糊还是精确
     */
    @Value("${ppym.mhlx:{\"bdcqzhmh\":1, \"zlmh\":1, \"qlrmcmh\":1, \"bdcdyhmh\":1}}")
    private String ppMhlx;

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:}")
    protected String signImageUrl;

    /**
     * 连云港土地交易服务费计算逻辑
     */
    @Value("${lyg.tdjyfwf:}")
    protected String tdjyfwf;
    /**
     * 权利人类型证件种类对照
     */
    @Value("#{${slym.qlrlxZjzlDzMap:null}}")
    private Map<String,String> qlrlxZjzlDzMap;
    /**
     * 验证证件号是否含有特殊符号的证件种类
     */
    @Value("${slym.yzsfhytsfhzjzl:}")
    private String yzsfhytsfhzjzl;
    @Autowired
    SlymZhlcDyanDTO slymZhlcDyanDTO;

    @Autowired
    SlymAuthorityConfig slymAuthorityConfig;

    @Autowired
    BdcRedisFeignService bdcRedisFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    MrlzfsConfig mrlzfsConfig;

    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;

    /**
     * @return 配置地址
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取配置地址
     */
    @ResponseBody
    @GetMapping()
    public Object querySlymUrl(String gzldyid, String gzlslid, String taskid) {
        BdcUrlDTO bdcSlUrlDTO = new BdcUrlDTO();
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isBlank(gzldyid)) {
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
            if (Objects.nonNull(processInstanceData)) {
                gzldyid = processInstanceData.getProcessDefinitionKey();

            }
        }
        if (StringUtils.isNotBlank(gzldyid)) {
            if (CollectionUtils.isNotEmpty(changeLcqxDyidList) && changeLcqxDyidList.contains(gzldyid)) {
                bdcSlUrlDTO.setSfxglcqx(true);
            } else {
                bdcSlUrlDTO.setSfxglcqx(false);
            }
            if (CollectionUtils.isNotEmpty(showZjjgDyids) && showZjjgDyids.contains(gzldyid)) {
                bdcSlUrlDTO.setSfzxZjjgBtn(true);
            }
            if (changeqljbxxDyidList.contains(gzldyid)) {
                bdcSlUrlDTO.setChangeQlJbxxSx(true);
            }
            if (bxzYwrGzldyids.contains(gzldyid)) {
                bdcSlUrlDTO.setSfxzywr(false);
            }
            if (CollectionUtils.isNotEmpty(plcjlcGzldyidList) && plcjlcGzldyidList.contains(gzldyid)) {
                bdcSlUrlDTO.setPlcjlc(true);
            }
            //获取业务流转定义id
            List<String> gzldyidList = bdcZjjgFeignService.getFdjywlcDyidList("ywlz");
            if (CollectionUtils.isNotEmpty(gzldyidList) && gzldyidList.contains(gzldyid)) {
                bdcSlUrlDTO.setJcywlz(true);
            }

            if (CollectionUtils.isNotEmpty(hyllcgzldyidList) && hyllcgzldyidList.contains(gzldyid)) {
                bdcSlUrlDTO.setHyllc(true);
            }
            if (Objects.nonNull(slymZhlcDyanDTO) && zhlcfkdy) {
                Map<String, Map<String, String>> btnMap = MapUtils.getMap(slymZhlcDyanDTO.getZhlcbtn(), gzldyid, null);
                if (MapUtils.isNotEmpty(btnMap) && StringUtils.isNotBlank(gzlslid)) {
                    //判断是否组合流程
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        //登记小类去重
                        bdcXmDTOList = bdcXmDTOList.stream().filter(bdcXmDTO -> StringUtils.isNotBlank(bdcXmDTO.getDjxl())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmDTO::getDjxl))), ArrayList::new));
                        Map<String, String> sjdBtnMap = new HashMap<>(2);
                        Map<String, String> sqsBtnMap = new HashMap<>(2);
                        for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                            Map<String, String> slymBtnMap = MapUtils.getMap(btnMap, bdcXmDTO.getDjxl(), null);
                            sjdBtnMap.put(bdcXmDTO.getDjxl(), MapUtils.getString(slymBtnMap, "sjd", ""));
                            sqsBtnMap.put(bdcXmDTO.getDjxl(), MapUtils.getString(slymBtnMap, "sqs", ""));
                        }
                        bdcSlUrlDTO.setSjdBtn(sjdBtnMap);
                        bdcSlUrlDTO.setSqsBtn(sqsBtnMap);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(zhajlcList) && zhajlcList.contains(gzldyid)) {
                bdcSlUrlDTO.setZhajlc(true);
            }
            if (CollectionUtils.isNotEmpty(zdjbdbDyidList) && zdjbdbDyidList.contains(gzldyid)) {
                bdcSlUrlDTO.setZdjbdb(true);
            }
            List<String> byslGzldyidList = bdcZjjgFeignService.getFdjywlcDyidList("bysl");
            List<String> bydjGzldyidList = bdcZjjgFeignService.getFdjywlcDyidList("bydj");
            if (CollectionUtils.isNotEmpty(byslGzldyidList) && byslGzldyidList.contains(gzldyid) || CollectionUtils.isNotEmpty(bydjGzldyidList) && bydjGzldyidList.contains(gzldyid)) {
                bdcSlUrlDTO.setBysldj(true);
            }
            if (StringUtils.isNotBlank(cxdjGzldyid) && StringUtils.equals(cxdjGzldyid, gzldyid)) {
                bdcSlUrlDTO.setCxdj(true);
            }
            if (CollectionUtils.isNotEmpty(wxjjGzldyid) && wxjjGzldyid.contains(gzldyid)) {
                bdcSlUrlDTO.setXswxjj(true);
            }
        }

        if (StringUtils.isNotBlank(taskid)) {
            TaskData taskData = processTaskClient.getTaskById(taskid);
            if (Objects.nonNull(taskData)) {
                bdcSlUrlDTO.setJdmc(taskData.getTaskName());
            }
        }
        if (StringUtils.isNotBlank(slymPrintBtn)) {
            Map<String, String> sfxmMap = JSON.parseObject(slymPrintBtn, Map.class);
            bdcSlUrlDTO.setSlymPrintBtnMap(sfxmMap);
        }
        Map<String, String> zdyZlcshXztzPzMap = zdyZlcshXztzPzConfig.getXztzpz();
        if (MapUtils.isNotEmpty(zdyZlcshXztzPzMap)) {
            Map<String, Map<String, String>> xztzPzMap = new HashMap<>(zdyZlcshXztzPzMap.size());
            for (String key : zdyZlcshXztzPzMap.keySet()) {
                xztzPzMap.put(key, JSON.parseObject(zdyZlcshXztzPzMap.get(key), Map.class));
            }
            bdcSlUrlDTO.setZdyZlcshXztzPz(xztzPzMap);
        }

        if(StringUtils.isNotBlank(signImageUrl)){
            bdcSlUrlDTO.setSignImageUrl(signImageUrl);
        }

        if (StringUtils.isNotBlank(tdjyfwf)){
            bdcSlUrlDTO.setTdjyfwf(tdjyfwf);
        }
        bdcSlUrlDTO.setSfczrBySxh(sfczrBySxh);
        bdcSlUrlDTO.setNoSfczrBySxhDjxlList(notSfczrBySxhDjxlList);
        bdcSlUrlDTO.setDyzl(dyzl);
        bdcSlUrlDTO.setYmVersion(ymVersion);
        bdcSlUrlDTO.setNoYzGhytList(noYzGhytList);
        bdcSlUrlDTO.setSplyList(splyList);
        bdcSlUrlDTO.setJedw(jedw);
        bdcSlUrlDTO.setCgList(cgList);
        bdcSlUrlDTO.setSfczr(sfczr);
        bdcSlUrlDTO.setJzzrFj(dyaqFjJzzrConfig.getSfjzzr());
        bdcSlUrlDTO.setXgnrglxs(xgnrglxs);
        bdcSlUrlDTO.setZhsjcl(zhsjcl);
        bdcSlUrlDTO.setSdghDjxlList(sdqghDjxlList);
        bdcSlUrlDTO.setSfyzbt(sfyzbt);
        bdcSlUrlDTO.setAutoClose(qlrzdgb);
        bdcSlUrlDTO.setSjclFromDjyyPz(addSjclFromDjyyPz);
        bdcSlUrlDTO.setColIdAndDjyyValMap(djyyControlColConfig.getColIdAndDjyyValMap());
        bdcSlUrlDTO.setYgdjhqspfhtxxDjxlList(ygdjhqspfhtxxDjxlList);
        bdcSlUrlDTO.setNosumfzmjZdtzm(nosumfzmjZdtzm);
        bdcSlUrlDTO.setGyfsdgyz(gyfsdgyz);
        bdcSlUrlDTO.setDysw(dysw);
        bdcSlUrlDTO.setZhlcfkdy(zhlcfkdy);
        bdcSlUrlDTO.setZcztqf(zczt);
        bdcSlUrlDTO.setIdAuthorityMap(slymAuthorityConfig.getIdMap());
        bdcSlUrlDTO.setGzyzHlxtZgz(hlxtzgz);
        bdcSlUrlDTO.setSfscwjzx(sfscwjzx);
        bdcSlUrlDTO.setClfmmht(clfmmht);
        bdcSlUrlDTO.setSqsqzwz(sqsqzwz);
        bdcSlUrlDTO.setQrsqzwz(qrsqzwz);
        bdcSlUrlDTO.setSjdqzwz(sjdqzwz);
        bdcSlUrlDTO.setJyfeMrzDjxlList(jyfeMrzDjxlList);
        bdcSlUrlDTO.setScbcsjcl(scbcsjcl);
        String xtPzColor = bdcRedisFeignService.getStringValue("xtPzColor");
        JSONObject jsonObject = JSON.parseObject(xtPzColor);
        if (Objects.nonNull(jsonObject)) {
            bdcSlUrlDTO.setBtxbjs(jsonObject.getString("bdc-btx"));
        }
        bdcSlUrlDTO.setBtxbzSize(btxbzSize);
        bdcSlUrlDTO.setShowJtcyQllxList(showjtcyQllx);

        bdcSlUrlDTO.setLzfsQldmMap(mrlzfsConfig.getLzfsQldmMap());
        bdcSlUrlDTO.setSfecqrids(sfecqrids);
        bdcSlUrlDTO.setPpMhlx(ppMhlx);
        bdcSlUrlDTO.setCljgCllxZdList(bdcSlZdFeignService.queryBdcSlzd("cljgcllx"));
        bdcSlUrlDTO.setSjclExe(sjclExe);
        bdcSlUrlDTO.setQlrlxZjzlDzMap(qlrlxZjzlDzMap);
        bdcSlUrlDTO.setYzzjhsfhytsfh(yzsfhytsfhzjzl);
        return bdcSlUrlDTO;
    }

    /**
     * @return  高亮显示登记小类集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  高亮显示登记小类集合
     */
    @ResponseBody
    @GetMapping("/xgnrglxs")
    public String xgnrglxs() {
        return this.xgnrglxs;
    }

    /**
     * @return  交易价格不可编辑的djyy
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description  交易价格不可编辑的djyy
     */
    @ResponseBody
    @GetMapping("/colIdAndDjyyVal")
    public Object colIdAndDjyyVal() {
        return djyyControlColConfig.getColIdAndDjyyValMap();
    }


    /**
     * 或去认证对比页面地址
     * @return 人证对比页面链接
     */
    @ResponseBody
    @GetMapping("/rzdbUrl")
    public String getRzdburl() {
        return rzdburl;
    }

}
