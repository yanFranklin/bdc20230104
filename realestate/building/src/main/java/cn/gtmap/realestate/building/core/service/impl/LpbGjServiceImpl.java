package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.SDmDwxxDO;
import cn.gtmap.realestate.common.core.dto.building.LpbGJRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/15
 * @description
 */
@Service
public class LpbGjServiceImpl implements LpbGjService {
    @Autowired
    private BdcdyhService bdcdyhService;
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private BdcZdCache bdcZdCache;
    @Autowired
    private FwHsService fwHsService;

    /**
     * @param lpbGJRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 楼盘表构建服务
     */
    @Override
    public void lpbGj(LpbGJRequestDTO lpbGJRequestDTO) {
        if (lpbGJRequestDTO != null && StringUtils.isNotBlank(lpbGJRequestDTO.getGjfs())) {
            String gjfs = lpbGJRequestDTO.getGjfs();
            String xzqmc = null;
            //获取行政区名称
            FwLjzDO fwLjzDO = null;
            if (StringUtils.isNotBlank(lpbGJRequestDTO.getFwDcbIndex())) {
                fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(lpbGJRequestDTO.getFwDcbIndex());
                if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getLszd())) {
                    String xzqdm = fwLjzDO.getLszd().substring(0, 9);
                    Object zdmc = bdcZdCache.getFeildValue("SDmDwxxDO", xzqdm, "DWMC", "DWDM", SDmDwxxDO.class);
                    xzqmc = zdmc !=null ? zdmc.toString():"";
                }
            }

            //根据规则构建
            List<FwHsDO> allFwhsList=new ArrayList<>();
            for (int i = 1; i <= lpbGJRequestDTO.getCs(); i++) {
                List<FwHsDO> fwHsDOList=new ArrayList<>();
                if (StringUtils.equals(Constants.LPBGJ_AHGJ, gjfs)) {
                    fwHsDOList=lpbGjByMchs(lpbGJRequestDTO, i, xzqmc,fwLjzDO);
                } else if (StringUtils.equals(Constants.LPBGJ_DYHSPJGJ, gjfs)) {
                    fwHsDOList=lpbGjByDyhsPj(lpbGJRequestDTO, i, xzqmc,fwLjzDO);
                } else if (StringUtils.equals(Constants.LPBGJ_DYHSDTGJ, gjfs)) {
                    fwHsDOList=lpbGjByDyhsDt(lpbGJRequestDTO, i, xzqmc,fwLjzDO);
                }
                if(CollectionUtils.isNotEmpty(fwHsDOList)){
                    allFwhsList.addAll(fwHsDOList);
                }
            }
            if(CollectionUtils.isNotEmpty(allFwhsList)){
                disposeFwHsList(lpbGJRequestDTO.getFwDcbIndex(),allFwhsList);
                batchSetBdcdyh(allFwhsList,fwLjzDO);
            }
            fwHsService.batchInsertFwHs(allFwhsList);
        }
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理逻辑幢下原有的户室
     */
    private void disposeFwHsList(String fwDcbIndex,List<FwHsDO> allFwHsDOList){
        if(StringUtils.isNotBlank(fwDcbIndex)){
            List<String> fjhList=new ArrayList<>();
            List<FwHsDO> fwHsDOList=fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                for(FwHsDO fwHsDO:fwHsDOList){
                    if(fwHsDO!=null){
                        StringBuilder sb=new StringBuilder();
                        if(fwHsDO.getWlcs()!=null){
                            sb.append(fwHsDO.getWlcs());
                        }
                        if(StringUtils.isNotBlank(fwHsDO.getDyh())){
                            sb.append(fwHsDO.getDyh());
                        }
                        if(StringUtils.isNotBlank(fwHsDO.getFjh())){
                            sb.append(fwHsDO.getFjh());
                        }
                        fjhList.add(sb.toString());
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(allFwHsDOList)){
                Iterator<FwHsDO> iterator = allFwHsDOList.iterator();
                while (iterator.hasNext()) {
                    FwHsDO newFwHsDO = iterator.next();
                    StringBuilder newSb=new StringBuilder();
                    if(newFwHsDO.getWlcs()!=null){
                        newSb.append(newFwHsDO.getWlcs());
                    }
                    if(StringUtils.isNotBlank(newFwHsDO.getDyh())){
                        newSb.append(newFwHsDO.getDyh());
                    }
                    if(StringUtils.isNotBlank(newFwHsDO.getFjh())){
                        newSb.append(newFwHsDO.getFjh());
                    }
                    if(fjhList.contains(newSb.toString())){
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void batchSetBdcdyh(List<FwHsDO> fwHsDOList,FwLjzDO fwLjzDO){
        if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_H)) {
            if(StringUtils.isBlank(fwLjzDO.getLszd()) || StringUtils.isBlank(fwLjzDO.getZrzh())){
                throw new AppException("逻辑幢未挂接宗地或自然幢，不能构建");
            }
            List<String> bdcdyhList = bdcdyhService.batchCreateFwBdcdyhByLszdAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh(),fwHsDOList.size());
            if(CollectionUtils.isNotEmpty(bdcdyhList)
                    && bdcdyhList.size() == fwHsDOList.size()){
                for(int i = 0;i < fwHsDOList.size(); i++){
                    fwHsDOList.get(i).setBdcdyh(bdcdyhList.get(i));
                }
            }
        }
    }

    /**
     * @param lpbGJRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 无单元按户构建
     */
    private List<FwHsDO> lpbGjByMchs(LpbGJRequestDTO lpbGJRequestDTO, int cs, String xzqmc,FwLjzDO fwLjzDO) {
        if (StringUtils.isNotBlank(lpbGJRequestDTO.getFwDcbIndex())
                && lpbGJRequestDTO.getMchs() != null) {
            List<FwHsDO> fwHsDOList = new ArrayList<>();
            lpbGjByDy(cs, -1, lpbGJRequestDTO.getMchs(), fwLjzDO, fwHsDOList, xzqmc);
            return fwHsDOList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param lpbGJRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 按单元户数平均构建
     */
    private List<FwHsDO> lpbGjByDyhsPj(LpbGJRequestDTO lpbGJRequestDTO, int cs, String xzqmc,FwLjzDO fwLjzDO) {
        if (StringUtils.isNotBlank(lpbGJRequestDTO.getFwDcbIndex())
            && lpbGJRequestDTO.getDys() != null && lpbGJRequestDTO.getDymshs() != null) {
            List<FwHsDO> fwHsDOList = new ArrayList<>();
            for (int i = 1; i <= lpbGJRequestDTO.getDys(); i++) {
                lpbGjByDy(cs, i, lpbGJRequestDTO.getDymshs(), fwLjzDO, fwHsDOList, xzqmc);
            }
            return fwHsDOList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param lpbGJRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 按单元户数动态构建
     */
    private List<FwHsDO> lpbGjByDyhsDt(LpbGJRequestDTO lpbGJRequestDTO, int cs, String xzqmc,FwLjzDO fwLjzDO) {
        if (CollectionUtils.isNotEmpty(lpbGJRequestDTO.getHsdtgj()) && StringUtils.isNotBlank(lpbGJRequestDTO.getFwDcbIndex())) {
            List<FwHsDO> fwHsDOList = new ArrayList<>();
            for (String dyhAndMchs : lpbGJRequestDTO.getHsdtgj()) {
                int dyh = Integer.parseInt(dyhAndMchs.substring(0, dyhAndMchs.indexOf(',')));
                int mchs = Integer.parseInt(dyhAndMchs.substring(dyhAndMchs.indexOf(',') + 1));
                lpbGjByDy(cs, dyh, mchs, fwLjzDO, fwHsDOList, xzqmc);
            }
            return fwHsDOList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param cs
     * @param fwHsDOList
     * @param dyh
     * @param mchs
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据单元号和单元户室构建
     */
    private void lpbGjByDy(int cs, int dyh, int mchs, FwLjzDO fwLjzDO, List<FwHsDO> fwHsDOList, String xzqmc) {
        if (fwLjzDO != null) {
            for (int j = 1; j <= mchs; j++) {
                if (fwLjzDO != null) {
                    FwHsDO fwHsDO = initFwHsDO(fwLjzDO, cs, dyh, j, xzqmc);
                    if (fwHsDO != null) {
                        fwHsDOList.add(fwHsDO);
                    }
                }
            }
        }
    }

    /**
     * @param fwLjzDO
     * @param dyh
     * @param cs
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 初始化户室信息
     */
    private FwHsDO initFwHsDO(FwLjzDO fwLjzDO, int cs, int dyh, int fjxh, String xzqmc) {

        String fjh = initFjh(cs, fjxh);
        /*Example example = new Example(FwHsDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wlcs", cs)
                .andEqualTo("fjh", fjh)
                .andEqualTo("fwDcbIndex", fwLjzDO.getFwDcbIndex());
        if (dyh >= 0) {
            criteria.andEqualTo("dyh", String.valueOf(dyh));
        }
        List<FwHsDO> fwHsDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(fwHsDOList)) {*/
            FwHsDO fwHsDO = new FwHsDO();
            fwHsDO.setBdczt(Constants.BDCZT_KY);
            fwHsDO.setWlcs(cs);
            if (dyh >= 0) {
                fwHsDO.setDyh(String.valueOf(dyh));
            }
            fwHsDO.setSxh(fjxh);
            fwHsDO.setFwHsIndex(UUIDGenerator.generate());
            fwHsDO.setFwDcbIndex(fwLjzDO.getFwDcbIndex());
            fwHsDO.setDycs(String.valueOf(cs));

            if (StringUtils.isNotBlank(fjh)) {
                fwHsDO.setFjh(fjh);
            }
            String zl = initZlByFwDcbIndex(fwLjzDO, dyh, fjh, xzqmc);
            if (StringUtils.isNotBlank(zl)) {
                fwHsDO.setZl(zl);
            }
            return fwHsDO;
    }

    /**
     * @param fwLjzDO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 设置坐落
     */
    private String initZlByFwDcbIndex(FwLjzDO fwLjzDO, int dyh, String fjh, String xzqmc) {
        StringBuilder zlBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(xzqmc)) {
            zlBuilder.append(xzqmc);
        }
        if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getZldz())) {
            zlBuilder.append(fwLjzDO.getZldz());
        }
        if (dyh >= 0) {
            zlBuilder.append(dyh + "单元");
        }
        zlBuilder.append(fjh + "室");
        return zlBuilder.toString();
    }

    private String initFjh(int cs, int fjxh) {
        String fjh = null;
        if (cs > 0 && fjxh > 0) {
            fjh = cs + BuildingUtils.formatNumberByLengh(fjxh, "00");
        }
        return fjh;
    }

}