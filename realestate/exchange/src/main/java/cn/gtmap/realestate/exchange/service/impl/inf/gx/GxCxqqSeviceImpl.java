package cn.gtmap.realestate.exchange.service.impl.inf.gx;

import cn.gtmap.gtc.formclient.common.result.TreeModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqqXm;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeZdrzczlx;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxsq.request.SjptCxsqCtrlRequestDTO;
import cn.gtmap.realestate.exchange.core.mapper.sjpt.CxqqMapper;
import cn.gtmap.realestate.exchange.service.inf.gx.GxCxqqService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.CxjgService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.QueryCxjgService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.thread.GxPeCxqqServiceThread;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-09-10
 * @description ????????????
 */
public class GxCxqqSeviceImpl implements GxCxqqService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GxCxqqSeviceImpl.class);

    @Resource(name = "sjptEntityMapper")
    private EntityMapper entityMapper;

    @Resource(name = "sjptRepository")
    private Repo repository;
    @Autowired
    AccessLogTypeService accessLogTypeService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private CxjgService cxjgService;
    private Set<QueryCxjgService> queryCxjgServiceSet;
    @Autowired
    private CxqqMapper cxqqMapper;

    @Autowired
    private GxPeCxqqServiceThread gxPeCxqqServiceThread;

    @Override
    public Page<Object> listCxqqByPages(Pageable pageable, Map<String ,String> map) {
        return repository.selectPaging("listCxqqByPage", map, pageable);
    }

    @Override
    public Page<Object> getCxrByPage(Pageable pageable, Map<String, String> map) {
        return repository.selectPaging("listCxrByPage", map, pageable);
    }

    @Override
    public Page<Object> getRzByPage(Pageable pageable, Map<String, String> map) {
        return repository.selectPaging("getRzByPage", map, pageable);
    }

    @Override
    public List<GxPeZdrzczlx> getCzlx() {
        GxPeZdrzczlx gxPeZdrzczlx = new GxPeZdrzczlx();
        return entityMapper.select(gxPeZdrzczlx);
    }

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param kssj , jssj
     * @description ????????????????????????????????????????????????
     */
    @Override
    public Integer getSjsjlcxCount(String kssj,String jssj) {
        if(StringUtil.isBlank(kssj) || StringUtil.isBlank(jssj)){
            throw new MissingArgumentException("?????????????????????????????????????????????");
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("kssj",kssj);
        paramMap.put("jssj",jssj);
        paramMap.put("zt","2");
        return cxqqMapper.querySjsjlcxCount(paramMap);

    }

    @Override
    public Object executeCommit(String cxsqdh) {
        Example example = new Example(GxPeCxqq.class);
        example.createCriteria().andEqualTo("cxsqdh", cxsqdh);
        List<GxPeCxqq> gxPeCxqqList = entityMapper.selectByExample(example);
        Object responseBody = null;
        for(GxPeCxqq cxqq : gxPeCxqqList){
            ExchangeBean exchangeBean = ExchangeBean.getExchangeBean("sjpt_commitCxjgWithLog");
            if(exchangeBean != null){
                SjptCxsqCtrlRequestDTO requestDTO = new SjptCxsqCtrlRequestDTO();
                requestDTO.setBeanName("sjpt_cxjg");
                requestDTO.setRequestObject(cxqq);
                responseBody = exchangeBeanRequestService.request("sjpt_commitCxjgWithLog",requestDTO);
                if (responseBody != null && responseBody instanceof JSONArray) {
                    JSONObject responseJson = ((JSONArray)responseBody).getJSONObject(0);
                    if(responseJson.get("head") != null){
                        JSONObject headJson = responseJson.getJSONObject("head");
                        if(headJson != null && StringUtils.equals(headJson.getString("code"),"0000")){
                            cxqq.setZt(SjptConstants.CXQQ_ZT_YCLYTS);
                            cxqq.setSbsj(new Date());
                            entityMapper.updateByPrimaryKey(cxqq);
                        }
                        if(headJson != null && StringUtils.equals(headJson.getString("code"),"2013")) {
                            cxqq.setZt(SjptConstants.CXQQ_ZT_CS);
                            entityMapper.updateByPrimaryKey(cxqq);
                        }
                    }
                }
            }else{
                throw new AppException("????????????????????????sjpt_commitCxjgWithLog???????????????");
            }
        }
        return responseBody;
    }

    @Override
    public List<Object> executeCommitList(List<String> cxsqdhList){
        List<Object> responseBodyList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(cxsqdhList)) {
            for (String cxsqdh : cxsqdhList) {
                responseBodyList.add(executeCommit(cxsqdh));
            }
        }
        return responseBodyList;
    }

    @Override
    public void executeQuery(String cxsqdh) {
        Example example = new Example(GxPeCxqq.class);
        example.createCriteria().andEqualTo("cxsqdh", cxsqdh);
        List<GxPeCxqq> gxPeCxqqList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(gxPeCxqqList)) {
            try {
                gxPeCxqqServiceThread.batchExexuteQueryCxqq(gxPeCxqqList, false);
            } catch (Exception e) {
                LOGGER.error("errorMsg:", e);
            }
        }
    }

    @Override
    @Transactional(value = "sjptTransactionManager", rollbackFor = Exception.class)
    public String executeQueryCxqqXm(String xmid) {
        String msg = null;
        if(StringUtils.isNotBlank(xmid)){
            GxPeCxqqXm gxPeCxqqXm = entityMapper.selectByPrimaryKey(GxPeCxqqXm.class,xmid);
            if (gxPeCxqqXm != null) {
                deleteCxjgByCxqqXm(gxPeCxqqXm);
                try {
                    cxjgService.queryAndSaveApply(gxPeCxqqXm);
                    gxPeCxqqXm.setCxzt("1");
                    entityMapper.saveOrUpdate(gxPeCxqqXm,gxPeCxqqXm.getXmid());
                    // ??????cxqq??????zt
                    String cxsqdh = gxPeCxqqXm.getCxsqdh();
                    Example example = new Example(GxPeCxqq.class);
                    example.createCriteria().andEqualTo("cxsqdh", cxsqdh);
                    List<GxPeCxqq> gxPeCxqqList = entityMapper.selectByExample(example);
                    if(CollectionUtils.isNotEmpty(gxPeCxqqList)){
                       entityMapper.saveOrUpdate(cxjgService.flushCxqqZt(gxPeCxqqList.get(0)), gxPeCxqqList.get(0).getCxqqid());
                    }
                    msg = "??????!";
                } catch (Exception e) {
                    LOGGER.error("errorMsg:",e);
                    msg = "???????????????"+e.getMessage();
                }
            }
        }
        return  msg;
    }

    @Override
    public List<TreeModel> initQlTreeModel(GxPeCxqqXm gxPeCxqqXm, List<TreeModel> treeModelList) {
        PeCommitCxsqjg peCommitCxsqjg = new PeCommitCxsqjg();
        for (QueryCxjgService queryCxjgService : queryCxjgServiceSet) {
            List dataItem = queryCxjgService.queryCxjg(gxPeCxqqXm.getCxsqdh(), gxPeCxqqXm.getWsbh());
            peCommitCxsqjg = initPeCommitCxsqjgQl(queryCxjgService, dataItem, peCommitCxsqjg);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getTdsyq())) {
            TreeModel treeModel = initTreeModel("tdsyq", gxPeCxqqXm.getXmid(), "???????????????", peCommitCxsqjg.getTdsyq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getJsydsyq())) {
            TreeModel treeModel = initTreeModel("jsydsyq", gxPeCxqqXm.getXmid(), "??????????????????????????????????????????", peCommitCxsqjg.getJsydsyq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getFdcq())) {
            TreeModel treeModel = initTreeModel("fdcq", gxPeCxqqXm.getXmid(), "????????????", peCommitCxsqjg.getFdcq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getHysyq())) {
            TreeModel treeModel = initTreeModel("hysyq", gxPeCxqqXm.getXmid(), "???????????????????????????????????????", peCommitCxsqjg.getHysyq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getGjzwsyq())) {
            TreeModel treeModel = initTreeModel("gjzwsyq", gxPeCxqqXm.getXmid(), "???????????????????????????", peCommitCxsqjg.getGjzwsyq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getLq())) {
            TreeModel treeModel = initTreeModel("lq", gxPeCxqqXm.getXmid(), "??????", peCommitCxsqjg.getLq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getNydsyq())) {
            TreeModel treeModel = initTreeModel("nydsyq", gxPeCxqqXm.getXmid(), "??????????????????", peCommitCxsqjg.getNydsyq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getDyaq())) {
            TreeModel treeModel = initTreeModel("dyaq", gxPeCxqqXm.getXmid(), "?????????", peCommitCxsqjg.getDyaq());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getYgdj())) {
            TreeModel treeModel = initTreeModel("ygdj", gxPeCxqqXm.getXmid(), "??????", peCommitCxsqjg.getYgdj());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getCfdj())) {
            TreeModel treeModel = initTreeModel("cfdj", gxPeCxqqXm.getXmid(), "??????", peCommitCxsqjg.getCfdj());
            treeModelList.add(treeModel);
        }
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getYydj())) {
            TreeModel treeModel = initTreeModel("yydj", gxPeCxqqXm.getXmid(), "??????", peCommitCxsqjg.getYydj());
            treeModelList.add(treeModel);
        }
        return treeModelList;
    }

    private PeCommitCxsqjg initPeCommitCxsqjgQl(QueryCxjgService queryCxjgService, List dataItem, PeCommitCxsqjg peCommitCxsqjg) {
        if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgCfServiceImpl")) {
            peCommitCxsqjg.setCfdj(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgDyaqServiceImpl")) {
            peCommitCxsqjg.setDyaq(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgFdcqServiceImpl")) {
            peCommitCxsqjg.setFdcq(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgGjzwsyqServiceImpl")) {
            peCommitCxsqjg.setGjzwsyq(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgHysyqServiceImpl")) {
            peCommitCxsqjg.setHysyq(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgJsydsyqServiceImpl")) {
            peCommitCxsqjg.setJsydsyq(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgLqServiceImpl")) {
            peCommitCxsqjg.setLq(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgTdsyqServiceImpl")) {
            peCommitCxsqjg.setTdsyq(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgYgServiceImpl")) {
            peCommitCxsqjg.setYgdj(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgYyServiceImpl")) {
            peCommitCxsqjg.setYydj(dataItem);
        } else if (StringUtils.equals(queryCxjgService.getClass().getSimpleName(), "QueryCxjgNydsyqServiceImpl")) {
            peCommitCxsqjg.setNydsyq(dataItem);
        }
        return peCommitCxsqjg;
    }

    private TreeModel initTreeModel(String id, String pId, String name, List dataList) {
        TreeModel treeModel = new TreeModel();
        treeModel.setId(id);
        treeModel.setpId(pId);
        treeModel.setName(name);
        return treeModel;
    }

    public Set<QueryCxjgService> getQueryCxjgServiceSet() {
        return queryCxjgServiceSet;
    }

    public void setQueryCxjgServiceSet(Set<QueryCxjgService> queryCxjgServiceSet) {
        this.queryCxjgServiceSet = queryCxjgServiceSet;
    }

    @Override
    public void deleteCxjgByCxqqXm(GxPeCxqqXm gxPeCxqqXm) {
        if (gxPeCxqqXm != null) {
            for (QueryCxjgService queryCxjgService : queryCxjgServiceSet) {
                queryCxjgService.deleteCxjg(gxPeCxqqXm.getCxsqdh(), gxPeCxqqXm.getWsbh());
            }
        }
        //?????????????????? zt?????????0
        gxPeCxqqXm.setCxzt("0");
        entityMapper.updateByPrimaryKeySelective(gxPeCxqqXm);
    }


}
