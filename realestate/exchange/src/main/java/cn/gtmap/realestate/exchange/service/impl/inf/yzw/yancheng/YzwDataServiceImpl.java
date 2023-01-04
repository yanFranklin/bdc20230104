package cn.gtmap.realestate.exchange.service.impl.inf.yzw.yancheng;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwYzsbxxDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseacceptDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCasebaseinfoDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseprocessDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseresultDO;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwJgxxDTO;
import cn.gtmap.realestate.exchange.core.mapper.exchange.YzwMapper;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbCshxxVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbQsgcxxVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbSjcsVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbTjwtsjgVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbXmcqbjVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbXmcqwbjVO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbZjhmbghVO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwService;
import cn.gtmap.realestate.exchange.service.inf.yzw.yancheng.YzwDataService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 一张网数据服务(盐城)
 */
@Service("yzwDataServiceImpl_yancheng")
public class YzwDataServiceImpl implements YzwDataService {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网台账问题数据处理默认办结人
     */
    @Value("${yzw.tz.bjr:}")
    private String mrbjr;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网证件种类为身份证对应的代码值
     */
    @Value("${yzw.sfzzjzl:}")
    private  String sfzzjzl;




    /**
     * 常用属性名
     */
    private static final String CASENO = "caseno";

    @Autowired
    @Qualifier("gxEntityMapper")
    private EntityMapper gxEntityMapper;

    @Autowired
    private YzwService yzwService;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private CommonService commonService;
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private YzwMapper yzwMapper;


    @Override
    public TBmCasebaseinfoDO queryTBmCasebaseinfo(String yzwbh) {
        return gxEntityMapper.selectByPrimaryKey(TBmCasebaseinfoDO.class, yzwbh);

    }

    @Override
    public void editSlsj(String yzwbh, Date slsj) {
        List<TBmCaseacceptDO> tBmCaseacceptDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseacceptDO.class, CASENO);
        List<TBmCaseprocessDO> tBmCaseprocessDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseprocessDO.class, CASENO);
        if (CollectionUtils.isNotEmpty(tBmCaseprocessDOList)) {
            tBmCaseprocessDOList.sort(Comparator.comparing(TBmCaseprocessDO::getTachestartdatetime));
            //受理取第一个节点
            TBmCaseprocessDO slTBmCaseprocessDO = tBmCaseprocessDOList.get(0);
            slTBmCaseprocessDO.setTacheenddatetime(slsj);
            gxEntityMapper.updateByPrimaryKey(slTBmCaseprocessDO);
        }
        if (CollectionUtils.isNotEmpty(tBmCaseacceptDOList)) {
            for (TBmCaseacceptDO tBmCaseacceptDO : tBmCaseacceptDOList) {
                tBmCaseacceptDO.setAcceptdate(slsj);
                gxEntityMapper.updateByPrimaryKey(tBmCaseacceptDO);


            }
        }

    }

    @Override
    public YzwYzsbSjcsVO getSlsj(String yzwbh) {
        YzwYzsbSjcsVO yzwYzsbSjcsVO =new YzwYzsbSjcsVO();
        List<TBmCaseprocessDO> tBmCaseprocessDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseprocessDO.class, CASENO);
        if (CollectionUtils.isNotEmpty(tBmCaseprocessDOList)) {
            tBmCaseprocessDOList.sort(Comparator.comparing(TBmCaseprocessDO::getTachestartdatetime));
            //受理取第一个节点
            TBmCaseprocessDO slTBmCaseprocessDO = tBmCaseprocessDOList.get(0);
            yzwYzsbSjcsVO.setSlsj(slTBmCaseprocessDO.getTachestartdatetime());
        }
        return yzwYzsbSjcsVO;

    }

    private YzwYzsbXmcqbjVO getBjsj(String yzwbh) {
        YzwYzsbXmcqbjVO yzwYzsbXmcqbjVO =new YzwYzsbXmcqbjVO();
        TBmCasebaseinfoDO tBmCasebaseinfo = queryTBmCasebaseinfo(yzwbh);
        List<TBmCaseacceptDO> tBmCaseacceptDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseacceptDO.class, CASENO);
        if (tBmCasebaseinfo != null) {
            yzwYzsbXmcqbjVO.setClsj(tBmCasebaseinfo.getCasepromisedate());
            Date bjsj = DateUtils.addHours(tBmCasebaseinfo.getCasepromisedate(), -1);
            yzwYzsbXmcqbjVO.setBjsj(bjsj);
        }
        if (CollectionUtils.isNotEmpty(tBmCaseacceptDOList)) {
            yzwYzsbXmcqbjVO.setSlsj(tBmCaseacceptDOList.get(0).getAcceptdate());
        }
        return yzwYzsbXmcqbjVO;

    }

    @Override
    public void editBjsj(String yzwbh, Date bjsj) {
        List<TBmCaseresultDO> tBmCaseresultDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseresultDO.class, CASENO);
        List<TBmCaseacceptDO> tBmCaseacceptDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseacceptDO.class, CASENO);
        if (CollectionUtils.isNotEmpty(tBmCaseacceptDOList)) {
            if (DateUtils.truncatedCompareTo(tBmCaseacceptDOList.get(0).getAcceptdate(), bjsj, Calendar.MILLISECOND) > 0) {
                throw new AppException("办结时间不能早于受理时间");
            }
        }
        if (CollectionUtils.isNotEmpty(tBmCaseresultDOList)) {
            for (TBmCaseresultDO tBmCaseresultDO : tBmCaseresultDOList) {
                tBmCaseresultDO.setCaseenddatetime(bjsj);
                gxEntityMapper.updateByPrimaryKey(tBmCaseresultDO);

            }
        } else {
            throw new AppException("无结果数据请检查");
        }

    }

    @Override
    public YzwYzsbXmcqwbjVO initBjxx(String yzwbh) {
        YzwYzsbXmcqwbjVO yzwYzsbXmcqwbjVO =new YzwYzsbXmcqwbjVO();
        List<TBmCaseresultDO> tBmCaseresultDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseresultDO.class, CASENO);
        if (CollectionUtils.isEmpty(tBmCaseresultDOList)) {
            //没有结果数据,系统自动初始化初始化生成
            TBmCasebaseinfoDO tBmCasebaseinfoDO = queryTBmCasebaseinfo(yzwbh);
            if (tBmCasebaseinfoDO != null) {
                //办结时间默认为承诺期限减去一小时
                Date bjsj = DateUtils.addHours(tBmCasebaseinfoDO.getCasepromisedate(), -1);
                yzwYzsbXmcqwbjVO.setGcjssj(bjsj);
                yzwYzsbXmcqwbjVO.setJgjssj(bjsj);
                yzwYzsbXmcqwbjVO.setJgclsj(tBmCasebaseinfoDO.getCasepromisedate());
                //取最后节点的办结时间
                List<TBmCaseprocessDO> tBmCaseprocessDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseprocessDO.class, CASENO);
                if (CollectionUtils.isNotEmpty(tBmCaseprocessDOList)) {
                    tBmCaseprocessDOList.sort(Comparator.comparing(TBmCaseprocessDO::getTachestartdatetime));
                    TBmCaseprocessDO beforeTBmCaseprocessDO = tBmCaseprocessDOList.get(tBmCaseprocessDOList.size() - 1);
                    if (beforeTBmCaseprocessDO != null) {
                        //上一节点办结时间
                        yzwYzsbXmcqwbjVO.setGckssj(beforeTBmCaseprocessDO.getTacheenddatetime());
                    }

                }
                //办结人
                yzwYzsbXmcqwbjVO.setGcbjr(mrbjr);
                yzwYzsbXmcqwbjVO.setJgbjr(mrbjr);
            }
        }
        return yzwYzsbXmcqwbjVO;

    }

    @Override
    public void saveYzwJgxxDTO(YzwJgxxDTO yzwJgxxDTO) {
        if (StringUtils.isNotBlank(yzwJgxxDTO.getYzwbh())) {
            if (yzwJgxxDTO.gettBmCaseprocessDO() != null) {
                TBmCaseprocessDO tBmCaseprocessDO = yzwJgxxDTO.gettBmCaseprocessDO();
                tBmCaseprocessDO.setOnlymark(UUIDGenerator.generate16());
                tBmCaseprocessDO.setCaseno(yzwJgxxDTO.getYzwbh());
                //获取最后一个节点
                List<BdcXmDO> bdcXmDOList = commonService.listBdcXmByZfxzspbh(yzwJgxxDTO.getYzwbh());
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    List<TaskData> listLstTask = processTaskClient.processLastTasks(bdcXmDOList.get(0).getGzlslid());
                    if (CollectionUtils.isNotEmpty(listLstTask)) {
                        tBmCaseprocessDO.setNodename(listLstTask.get(0).getTaskName());
                    }
                }
                tBmCaseprocessDO.setTachenote("同意");
                tBmCaseprocessDO.setTachestatus(Constants.YZW_YWDZ_TG);
                tBmCaseprocessDO.setSyncdatetime(new Date());
                tBmCaseprocessDO.setSyncsign(0);
                gxEntityMapper.saveOrUpdate(tBmCaseprocessDO, tBmCaseprocessDO.getOnlymark());

            }
            if (yzwJgxxDTO.gettBmCaseresultDO() != null) {
                TBmCaseresultDO tBmCaseresultDO = yzwJgxxDTO.gettBmCaseresultDO();
                tBmCaseresultDO.setCaseno(yzwJgxxDTO.getYzwbh());
                tBmCaseresultDO.setOnlymark(UUIDGenerator.generate16());
                tBmCaseresultDO.setResultstatus("已办结");
                tBmCaseresultDO.setSyncdatetime(new Date());
                tBmCaseresultDO.setSyncsign(0);
                gxEntityMapper.insert(tBmCaseresultDO);

            }
            if (yzwJgxxDTO.gettBmCaseacceptDO() != null) {
                TBmCaseacceptDO tBmCaseacceptDO =yzwJgxxDTO.gettBmCaseacceptDO();
                List<TBmCaseacceptDO> tBmCaseacceptDOList = yzwService.listGxYzwDataByYzwbh(yzwJgxxDTO.getYzwbh(), TBmCaseacceptDO.class, CASENO);
                if(CollectionUtils.isNotEmpty(tBmCaseacceptDOList)){
                    tBmCaseacceptDO.setOnlymark(tBmCaseacceptDOList.get(0).getOnlymark());
                }else {
                    tBmCaseacceptDO.setOnlymark(UUIDGenerator.generate16());
                }
                gxEntityMapper.saveOrUpdate(tBmCaseacceptDO,tBmCaseacceptDO.getOnlymark());


            }
        }

    }

    @Override
    public YzwYzsbTjwtsjgVO initTjjgxx(String yzwbh) {
        YzwYzsbTjwtsjgVO yzsbTjwtsjgVO =new YzwYzsbTjwtsjgVO();
        List<TBmCaseresultDO> tBmCaseresultDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseresultDO.class, CASENO);
        if (CollectionUtils.isEmpty(tBmCaseresultDOList)) {
            TBmCasebaseinfoDO tBmCasebaseinfoDO = queryTBmCasebaseinfo(yzwbh);
            List<TBmCaseacceptDO> tBmCaseacceptDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseacceptDO.class, CASENO);
            if (tBmCasebaseinfoDO != null && CollectionUtils.isNotEmpty(tBmCaseacceptDOList)) {
                //办结时间默认为承诺期限减去一小时
                Date bjsj = DateUtils.addHours(tBmCasebaseinfoDO.getCasepromisedate(), -1);
                yzsbTjwtsjgVO.setBjsj(bjsj);
                //退件办结人为受理办理人
                yzsbTjwtsjgVO.setBjr(tBmCaseacceptDOList.get(0).getAcceptusername());

            }
        }
        return yzsbTjwtsjgVO;

    }

    @Override
    public YzwYzsbQsgcxxVO initGcxx(String yzwbh) {
        YzwYzsbQsgcxxVO yzwYzsbQsgcxxVO =new YzwYzsbQsgcxxVO();
        TBmCasebaseinfoDO tBmCasebaseinfoDO = queryTBmCasebaseinfo(yzwbh);
        if (tBmCasebaseinfoDO != null) {
            List<TBmCaseacceptDO> tBmCaseacceptDOList = yzwService.listGxYzwDataByYzwbh(yzwbh, TBmCaseacceptDO.class, CASENO);
            //受理人
            String slr = "";
            //受理时间
            Date slkssj = null;
            //受理时间
            Date sljssj = null;
            //受理部门名称
            String slbmmc = "";
            if (CollectionUtils.isNotEmpty(tBmCaseacceptDOList)) {
                slr = tBmCaseacceptDOList.get(0).getAcceptusername();
                sljssj = tBmCaseacceptDOList.get(0).getAcceptdate();
                slbmmc = tBmCaseacceptDOList.get(0).getAcceptdeptname();
            }
            List<BdcXmDO> bdcXmDOList = commonService.listBdcXmByZfxzspbh(yzwbh);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                slkssj = bdcXmDOList.get(0).getSlsj();
                if (StringUtils.isBlank(slr)) {
                    //获取用户信息
                    List<TaskData> list = processTaskClient.listProcessTask(bdcXmDOList.get(0).getGzlslid());
                    if (CollectionUtils.isNotEmpty(list)) {
                        UserDto userDto = userManagerUtils.getUserByName(list.get(0).getTaskAssName());
                        if (userDto != null) {
                            slr = userDto.getAlias();
                            if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                                slbmmc = userDto.getOrgRecordList().get(0).getRegionName();
                            }
                        }
                    }
                }
            }
            if (sljssj == null) {
                sljssj = tBmCasebaseinfoDO.getCaseregdate();
            }
            yzwYzsbQsgcxxVO.setSlr(slr);
            yzwYzsbQsgcxxVO.setSlbm(slbmmc);
            yzwYzsbQsgcxxVO.setSlsj(sljssj);
            yzwYzsbQsgcxxVO.setGcblr(slr);
            yzwYzsbQsgcxxVO.setGckssj(slkssj);
            yzwYzsbQsgcxxVO.setGcjssj(sljssj);

        }
        return yzwYzsbQsgcxxVO;

    }

    @Override
    public void checkAndSaveSqrZjxx(String yzwbh,String zjzl,String zjh){
        boolean zjhmgf =false;
        //1.申请人证件类型和证件号码不为空
        List<String> yzwzjzlList =yzwMapper.listGxYzwZdZjzlDm();
        if(StringUtils.isNoneBlank(zjzl,zjh)){
            if(StringUtils.equals(zjzl,sfzzjzl)){
                //验证身份证号
                zjhmgf =CardNumberTransformation.isIdCard(zjh);
            }else if(CollectionUtils.isNotEmpty(yzwzjzlList) &&yzwzjzlList.indexOf(zjzl) >-1){
                zjhmgf =true;
            }
        }
        if(zjhmgf){
            TBmCasebaseinfoDO tBmCasebaseinfoDO =queryTBmCasebaseinfo(yzwbh);
            if(tBmCasebaseinfoDO != null){
                tBmCasebaseinfoDO.setCaseapplicantpapercode(zjh);
                tBmCasebaseinfoDO.setCaseapplicantpapertype(zjzl);
                gxEntityMapper.saveOrUpdate(tBmCasebaseinfoDO,tBmCasebaseinfoDO.getCaseno());
            }

        }

    }

    @Override
    public YzwYzsbCshxxVO getYzwYzsbTsxx(String yzwbh,String tsxxid){
        YzwYzsbCshxxVO yzwYzsbCshxxVO =new YzwYzsbCshxxVO();
        if(StringUtils.isNotBlank(yzwbh) &&StringUtils.isNotBlank(tsxxid)) {
            Example example =new Example(GxYzwYzsbxxDO.class);
            example.createCriteria().andEqualTo("tsxxid",tsxxid);
            List<GxYzwYzsbxxDO> gxYzwYzsbxxDOList =gxEntityMapper.selectByExampleNotNull(example);
            if(CollectionUtils.isNotEmpty(gxYzwYzsbxxDOList)){
                for(GxYzwYzsbxxDO gxYzwYzsbxxDO : gxYzwYzsbxxDOList){
                    if(StringUtils.equals(Constants.YZWYZLX_SJCS, gxYzwYzsbxxDO.getYzlx())){

                        yzwYzsbCshxxVO.setSjcsxx(getSlsj(yzwbh));
                    }else if(StringUtils.equals(Constants.YZWYZLX_XMCQBJ, gxYzwYzsbxxDO.getYzlx())){
                        yzwYzsbCshxxVO.setXmcqbj(getBjsj(yzwbh));
                    }else if(StringUtils.equals(Constants.YZWYZLX_XMCQWBJ, gxYzwYzsbxxDO.getYzlx())){
                        yzwYzsbCshxxVO.setXmcqwbj(initBjxx(yzwbh));
                    }else if(StringUtils.equals(Constants.YZWYZLX_TJWTSJG, gxYzwYzsbxxDO.getYzlx())){
                        yzwYzsbCshxxVO.setTjwtsjg(initTjjgxx(yzwbh));
                    }else if(StringUtils.equals(Constants.YZWYZLX_QSGCXX, gxYzwYzsbxxDO.getYzlx())){
                        yzwYzsbCshxxVO.setQsgcxx(initGcxx(yzwbh));
                    }else if(StringUtils.equals(Constants.YZWYZLX_ZJHMBGH, gxYzwYzsbxxDO.getYzlx())){
                        YzwYzsbZjhmbghVO yzwYzsbZjhmbghVO =new YzwYzsbZjhmbghVO();

                        TBmCasebaseinfoDO tBmCasebaseinfoDO =queryTBmCasebaseinfo(yzwbh);
                        if(tBmCasebaseinfoDO != null) {
                            yzwYzsbZjhmbghVO.setSqrmc(tBmCasebaseinfoDO.getCaseapplicant());
                            yzwYzsbZjhmbghVO.setZjzl(tBmCasebaseinfoDO.getCaseapplicantpapertype());
                            yzwYzsbZjhmbghVO.setZjh(tBmCasebaseinfoDO.getCaseapplicantpapercode());

                        }

                        yzwYzsbZjhmbghVO.setYzwzjzlList(yzwMapper.listGxYzwZdZjzl());
                        yzwYzsbCshxxVO.setZjhmbgh(yzwYzsbZjhmbghVO);

                    }

                }
            }
        }
        return yzwYzsbCshxxVO;


    }

    @Override
    public void checkAndSaveYzwYzsbTsxgxx(YzwYzsbCshxxVO yzwYzsbCshxxVO){
        if(yzwYzsbCshxxVO != null){
            if(yzwYzsbCshxxVO.getSjcsxx() != null &&yzwYzsbCshxxVO.getSjcsxx().getSlsj() != null){
                editSlsj(yzwYzsbCshxxVO.getYzwbh(),yzwYzsbCshxxVO.getSjcsxx().getSlsj());
            }
            if(yzwYzsbCshxxVO.getXmcqbj() !=null &&yzwYzsbCshxxVO.getXmcqbj().getBjsj() != null){
                editBjsj(yzwYzsbCshxxVO.getYzwbh(),yzwYzsbCshxxVO.getXmcqbj().getBjsj());
            }
            if(yzwYzsbCshxxVO.getXmcqwbj() != null){
                YzwYzsbXmcqwbjVO xmcqwbjVO =yzwYzsbCshxxVO.getXmcqwbj();
                YzwJgxxDTO jgxxDTO =new YzwJgxxDTO();
                TBmCaseprocessDO tBmCaseprocessDO =new TBmCaseprocessDO();
                tBmCaseprocessDO.setTachestartdatetime(xmcqwbjVO.getGckssj());
                tBmCaseprocessDO.setTacheenddatetime(xmcqwbjVO.getGcjssj());
                tBmCaseprocessDO.setUsername(xmcqwbjVO.getGcbjr());
                jgxxDTO.settBmCaseprocessDO(tBmCaseprocessDO);
                TBmCaseresultDO tBmCaseresultDO =new TBmCaseresultDO();
                tBmCaseresultDO.setCaseenddatetime(xmcqwbjVO.getJgjssj());
                tBmCaseresultDO.setCaseenduser(xmcqwbjVO.getJgbjr());
                jgxxDTO.settBmCaseresultDO(tBmCaseresultDO);
                saveYzwJgxxDTO(jgxxDTO);
            }
            if(yzwYzsbCshxxVO.getTjwtsjg() != null){
                YzwYzsbTjwtsjgVO yzsbTjwtsjgVO =yzwYzsbCshxxVO.getTjwtsjg();
                YzwJgxxDTO jgxxDTO =new YzwJgxxDTO();
                TBmCaseresultDO tBmCaseresultDO =new TBmCaseresultDO();
                tBmCaseresultDO.setCaseenddatetime(yzsbTjwtsjgVO.getBjsj());
                tBmCaseresultDO.setCaseenduser(yzsbTjwtsjgVO.getBjr());
                jgxxDTO.settBmCaseresultDO(tBmCaseresultDO);
                saveYzwJgxxDTO(jgxxDTO);
            }
            if(yzwYzsbCshxxVO.getQsgcxx() != null){
                YzwYzsbQsgcxxVO yzwYzsbQsgcxxVO =yzwYzsbCshxxVO.getQsgcxx();
                YzwJgxxDTO jgxxDTO =new YzwJgxxDTO();
                TBmCaseprocessDO tBmCaseprocessDO =new TBmCaseprocessDO();
                tBmCaseprocessDO.setTachestartdatetime(yzwYzsbQsgcxxVO.getGckssj());
                tBmCaseprocessDO.setTacheenddatetime(yzwYzsbQsgcxxVO.getGcjssj());
                tBmCaseprocessDO.setUsername(yzwYzsbQsgcxxVO.getGcblr());
                jgxxDTO.settBmCaseprocessDO(tBmCaseprocessDO);
                TBmCaseacceptDO tBmCaseacceptDO =new TBmCaseacceptDO();
                tBmCaseacceptDO.setAcceptdeptname(yzwYzsbQsgcxxVO.getSlbm());
                tBmCaseacceptDO.setAcceptusername(yzwYzsbQsgcxxVO.getSlr());
                tBmCaseacceptDO.setAcceptdate(yzwYzsbQsgcxxVO.getSlsj());
                jgxxDTO.settBmCaseacceptDO(tBmCaseacceptDO);
                saveYzwJgxxDTO(jgxxDTO);
            }
            if(yzwYzsbCshxxVO.getZjhmbgh() != null){
                checkAndSaveSqrZjxx(yzwYzsbCshxxVO.getYzwbh(),yzwYzsbCshxxVO.getZjhmbgh().getZjzl(),yzwYzsbCshxxVO.getZjhmbgh().getZjh());
            }



        }


    }


}
