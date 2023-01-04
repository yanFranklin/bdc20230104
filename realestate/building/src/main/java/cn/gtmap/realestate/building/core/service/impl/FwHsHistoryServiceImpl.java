package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.FwHsHistoryService;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.SSjHsbgjlbService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.HFwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryNewDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/4
 * @description 房屋户室历史服务实现
 */
@Service
public class FwHsHistoryServiceImpl implements FwHsHistoryService {

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private SSjHsbgjlbService sSjHsbgjlbService;
    @Autowired
    private FwHsService fwHsService;


    /**
     * @param bdcdyh
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询户室变更记录(新)
     */
    @Override
    public List<FwHsBgHistoryNewDTO> getHsBgHistoryNewByBdcdyh(String bdcdyh) {
        List<FwHsBgHistoryNewDTO> list = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<String> fwHsIndexList = new ArrayList<>();
            FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
            if (fwHsDO != null) {
                fwHsIndexList.add(fwHsDO.getFwHsIndex());
                //进行数据整理
                getHsBgHistoryByList(fwHsIndexList, list, 0);
            }
        }
        return list;
    }

    /**
     * @param
     * @return cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理数据
     */
    private void getHsBgHistoryByList(List<String> fwHsIndexList, List<FwHsBgHistoryNewDTO> list, int code) {
        List<String> yfwHsIndexList = new ArrayList<>();
        if (fwHsIndexList != null) {
            //处理下重复的list
            BuildingUtils.removeDuplicate(fwHsIndexList);
            FwHsBgHistoryNewDTO fwHsBgHistoryNewDTO = null;
            for (String fwHsIndex : fwHsIndexList) {
                //根据当前主键查询变更记录表
                List<SSjHsbgljbDO> sSjHsbgljbDOList = sSjHsbgjlbService.listHsBgjlByFwHsIndex(fwHsIndex);
                if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
                    fwHsBgHistoryNewDTO = initDTO(sSjHsbgljbDOList, code);
                } else {
                    //如果变更记录表中没有数据 说明这是最原始的户室，去备份表查
                    fwHsBgHistoryNewDTO = initHDto(fwHsIndex, code);
                }
                //将查到整理好的数据放入list中
                list.add(fwHsBgHistoryNewDTO);
            }
            //将变更前的户室主键作为下一次查询的参数
            if (CollectionUtils.isNotEmpty(fwHsBgHistoryNewDTO.getYfwHsIndex())) {
                yfwHsIndexList.addAll(fwHsBgHistoryNewDTO.getYfwHsIndex());
                code++;
                getHsBgHistoryByList(yfwHsIndexList, list, code);
            }
        }
    }

    /**
     * @param sSjHsbgljbDOList
     * @return cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 初始化DTO（记录表中数据）
     */
    private FwHsBgHistoryNewDTO initDTO(List<SSjHsbgljbDO> sSjHsbgljbDOList, int code) {
        FwHsBgHistoryNewDTO fwHsBgHistoryNewDTO = new FwHsBgHistoryNewDTO();
        if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
            SSjHsbgljbDO sSjHsbgljbDO = sSjHsbgljbDOList.get(0);
            FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(sSjHsbgljbDO.getFwIndex());
            fwHsBgHistoryNewDTO.setId(sSjHsbgljbDO.getFwIndex());
            fwHsBgHistoryNewDTO.setName(sSjHsbgljbDO.getHsbgjlbIndex());
            if(fwHsDO!=null){
                fwHsBgHistoryNewDTO.setZl(fwHsDO.getZl());
                fwHsBgHistoryNewDTO.setBdcdyh(fwHsDO.getBdcdyh());
            }else {
                fwHsDO=new FwHsDO();
                HFwHsDO hFwHsDO = entityMapper.selectByPrimaryKey(HFwHsDO.class, sSjHsbgljbDO.getFwIndex());
                if(hFwHsDO!=null){
                    BeanUtils.copyProperties(hFwHsDO, fwHsDO);
                    fwHsBgHistoryNewDTO.setZl(fwHsDO.getZl());
                    fwHsBgHistoryNewDTO.setBdcdyh(fwHsDO.getBdcdyh());
                }
            }

            fwHsBgHistoryNewDTO.setBgrq(sSjHsbgljbDO.getBgrq());
            fwHsBgHistoryNewDTO.setBglx(sSjHsbgljbDO.getBglx());
            //处理关系，上一次户室信息
            List<String> child = new ArrayList<>();
            for (SSjHsbgljbDO hsbgljbDO : sSjHsbgljbDOList) {
                child.add(hsbgljbDO.getYfwIndex());
            }
            fwHsBgHistoryNewDTO.setFrom(child);
            //设置节点
            fwHsBgHistoryNewDTO.setLevel(code);
            //需要继续往上查的户室主键
            List<String> yfwHsIndex = new ArrayList<>();
            for (SSjHsbgljbDO sSjHsbgljb : sSjHsbgljbDOList) {
                yfwHsIndex.add(sSjHsbgljb.getYfwIndex());
            }
            fwHsBgHistoryNewDTO.setYfwHsIndex(yfwHsIndex);
        }
        return fwHsBgHistoryNewDTO;
    }

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 初始化DTO（备份中数据）最原始的户室
     */
    private FwHsBgHistoryNewDTO initHDto(String fwHsIndex, int code) {
        FwHsBgHistoryNewDTO fwHsBgHistoryNewDTO = new FwHsBgHistoryNewDTO();
        if (StringUtils.isNotBlank(fwHsIndex)) {
            HFwHsDO hFwHsDO = entityMapper.selectByPrimaryKey(HFwHsDO.class, fwHsIndex);
            if(hFwHsDO!=null){
                fwHsBgHistoryNewDTO.setId(hFwHsDO.getFwHsIndex());
                fwHsBgHistoryNewDTO.setName(hFwHsDO.getFwHsIndex());
                fwHsBgHistoryNewDTO.setZl(hFwHsDO.getZl());
                fwHsBgHistoryNewDTO.setFrom(new ArrayList<>(0));
                fwHsBgHistoryNewDTO.setBdcdyh(hFwHsDO.getBdcdyh());
                fwHsBgHistoryNewDTO.setLevel(code);
            }
        }
        return fwHsBgHistoryNewDTO;
    }


    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询户室历史变更记录
     */
    @Override
    public Page<SSjHsbgljbDO> listHsbgHsitroyByPageJson(Pageable pageable, Map map) {
        return repo.selectPaging("listHsbgHsitroyByPageOrder", map, pageable);
    }

    /**
     * @param fwHsIndex
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询当前户室变更记录
     */
    @Override
    public List<List<FwHsBgHistoryDTO>> getHsBgHistory(String fwHsIndex) {
        List<List<FwHsBgHistoryDTO>> lists = new ArrayList<>();
        List<String> fwHsIndexList = new ArrayList<>();
        //整理做第一次查询的数据
        if (StringUtils.isNotBlank(fwHsIndex)) {
            fwHsIndexList.add(fwHsIndex);
        }
        getLastBgHistory(fwHsIndexList, lists, 1);
        Collections.reverse(lists);
        return lists;
    }

    /**
     * @param bdcdyh
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询户室变更记录通过不动产单元号
     */
    @Override
    public List<List<FwHsBgHistoryDTO>> getHsBgHistoryByBdcdyh(String bdcdyh) {
        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
        if (fwHsDO != null) {
            return getHsBgHistory(fwHsDO.getFwHsIndex());
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param fwHsIndexList 本次需要装载的户室主键
     * @param code          标识码，主要是第一次装载的时候 对户室进行标示
     * @param lists         最后需要返回给页面的list
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description
     */
    private void getLastBgHistory(List<String> fwHsIndexList, List<List<FwHsBgHistoryDTO>> lists, int code) {
        List<FwHsBgHistoryDTO> fwHsBgHistoryDTOList = new ArrayList<>();
        List<String> yFwHsIndexList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwHsIndexList)) {
            BuildingUtils.removeDuplicate(fwHsIndexList);
            for (String fwHsIndex : fwHsIndexList) {
                List<SSjHsbgljbDO> sSjHsbgljbDOList = sSjHsbgjlbService.listHsBgjlByFwHsIndex(fwHsIndex);
                FwHsBgHistoryDTO fwHsBgHistoryDTO;
                if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
                    fwHsBgHistoryDTO = initHsHbDTO(sSjHsbgljbDOList, code);
                } else {
                    //如果变更记录表中没有数据 说明这是最原始的户室，去备份表查
                    fwHsBgHistoryDTO = initHFwHsDTO(fwHsIndex);
                }
                fwHsBgHistoryDTOList.add(fwHsBgHistoryDTO);
                if (CollectionUtils.isNotEmpty(fwHsBgHistoryDTO.getYfwHsIndex())) {
                    yFwHsIndexList.addAll(fwHsBgHistoryDTO.getYfwHsIndex());
                }
            }
        }
        lists.add(fwHsBgHistoryDTOList);

        //继续往上找
        if (CollectionUtils.isNotEmpty(yFwHsIndexList)) {
            code++;
            getLastBgHistory(yFwHsIndexList, lists, code);
        }
    }

    /**
     * @param sSjHsbgljbDOList
     * @return cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 初始化DTO（记录表中数据）
     */
    private FwHsBgHistoryDTO initHsHbDTO(List<SSjHsbgljbDO> sSjHsbgljbDOList, int code) {
        FwHsBgHistoryDTO fwHsBgHistoryDTO = new FwHsBgHistoryDTO();
        if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
            SSjHsbgljbDO sSjHsbgljbDO = sSjHsbgljbDOList.get(0);
            fwHsBgHistoryDTO.setName(sSjHsbgljbDO.getHsbgjlbIndex());
            fwHsBgHistoryDTO.setBgbh(sSjHsbgljbDO.getBgbh());
            fwHsBgHistoryDTO.setId(sSjHsbgljbDO.getFwIndex());
            fwHsBgHistoryDTO.setBgrq(sSjHsbgljbDO.getBgrq());

            fwHsBgHistoryDTO.setBglx(sSjHsbgljbDO.getBglx());

            //需要继续往上查的户室主键
            List<String> yfwHsIndex = new ArrayList<>();
            for (SSjHsbgljbDO sSjHsbgljb : sSjHsbgljbDOList) {
                yfwHsIndex.add(sSjHsbgljb.getYfwIndex());
            }
            fwHsBgHistoryDTO.setYfwHsIndex(yfwHsIndex);


            //是否是最后一次变更
            if (code == 1) {
                fwHsBgHistoryDTO.setLast(true);
            }

            //处理child（本次变更之后的变更）
            fwHsBgHistoryDTO.setChildren(initChild(sSjHsbgljbDO.getFwIndex()));

            //房间号
            fwHsBgHistoryDTO.setFjh(getFjhByFwHsIndex(sSjHsbgljbDO.getFwIndex()));


        }
        return fwHsBgHistoryDTO;
    }


    /**
     * @param
     * @return cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 初始化DTO（房屋户室备份表中数据）
     */
    private FwHsBgHistoryDTO initHFwHsDTO(String fwHsIndex) {
        FwHsBgHistoryDTO fwHsBgHistoryDTO = new FwHsBgHistoryDTO();
        if (StringUtils.isNotBlank(fwHsIndex)) {
            HFwHsDO hFwHsDO = entityMapper.selectByPrimaryKey(HFwHsDO.class, fwHsIndex);
            if (hFwHsDO != null) {
                fwHsBgHistoryDTO = new FwHsBgHistoryDTO();
                fwHsBgHistoryDTO.setId(hFwHsDO.getFwHsIndex());
                fwHsBgHistoryDTO.setName(hFwHsDO.getFwHsIndex());
                fwHsBgHistoryDTO.setChildren(initChild(fwHsIndex));
                fwHsBgHistoryDTO.setFjh(hFwHsDO.getFjh());
                fwHsBgHistoryDTO.setBglx("原始户室");
            }
        }
        return fwHsBgHistoryDTO;
    }

    /**
     * @param fwHsIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室主键查询房间号
     */
    private String getFjhByFwHsIndex(String fwHsIndex) {
        String fjh = "";
        if (StringUtils.isNotBlank(fwHsIndex)) {
            FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwHsIndex);
            if (fwHsDO != null) {
                if (StringUtils.isNotBlank(fwHsDO.getFjh())) {
                    fjh = fwHsDO.getFjh();
                }
            } else {
                HFwHsDO hFwHsDO = entityMapper.selectByPrimaryKey(HFwHsDO.class, fwHsIndex);
                if (hFwHsDO != null) {
                    if (StringUtils.isNotBlank(hFwHsDO.getFjh())) {
                        fjh = hFwHsDO.getFjh();
                    }
                }
            }
        }
        return fjh;
    }

    /**
     * @param fwHsIndex
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理child数据
     */
    private List<String> initChild(String fwHsIndex) {
        List<String> child = new ArrayList<>();
        List<SSjHsbgljbDO> hsbgljbDOList = sSjHsbgjlbService.listHsBgjlByYFwHsIndex(fwHsIndex);
        if (hsbgljbDOList != null) {
            for (SSjHsbgljbDO hsbgljbDO : hsbgljbDOList) {
                child.add(hsbgljbDO.getFwIndex());
            }
        }
        return child;
    }

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.HFwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键查询房屋户室信息
     */
    @Override
    public FwHsDO getHFwHsByFwHsIndex(String fwHsIndex, boolean last) {
        FwHsDO fwHsDO = new FwHsDO();
        if (last) {
            fwHsDO = fwHsService.queryFwHsByIndex(fwHsIndex);
        } else {
            HFwHsDO hFwHsDO = entityMapper.selectByPrimaryKey(HFwHsDO.class, fwHsIndex);
            BeanUtils.copyProperties(hFwHsDO, fwHsDO);
        }
        return fwHsDO;
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.HFwHsDO
     * @description 根据房屋户室主键查询房屋户室信息(新)
     */
    @Override
    public FwHsDO getHFwHsByFwHsIndexNew(String fwHsIndex) {
        FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwHsIndex);
        if (fwHsDO == null) {
            fwHsDO=new FwHsDO();
            HFwHsDO hFwHsDO = entityMapper.selectByPrimaryKey(HFwHsDO.class, fwHsIndex);
            BeanUtils.copyProperties(hFwHsDO, fwHsDO);
        }
        return fwHsDO;
    }
}