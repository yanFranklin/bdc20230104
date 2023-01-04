package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcdjbService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.djbxx.InitBdcdjbAbstractService;
import cn.gtmap.realestate.init.service.other.InitDataDealService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/21.
 * @description
 */
@Service
@Validated
public class BdcdjbServiceImpl implements BdcdjbService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private InitBeanFactory initBeanFactory;
    @Autowired
    private InitDataDealService initDataDealService;
    @Autowired
    private InitServiceQoService initServiceQoService;

    /**
     * 通过宗海代码获取用海基本信息
     * @param zhdm
     * @return BdcBdcdjbZhjbxxDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcBdcdjbZhjbxxDO queryBdcdjbZhJbxx(String zhdm) {
        if (StringUtils.isBlank(zhdm)) {
            return null;
        }
        return entityMapper.selectByPrimaryKey(BdcBdcdjbZhjbxxDO.class,zhdm);
    }

    /**
     * 通过宗地代码获取宗地基本信息
     * @param zhdm
     * @return BdcBdcdjbZdjbxxDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcBdcdjbZdjbxxDO queryBdcdjbZdJbxx(String zhdm) {
        if (StringUtils.isBlank(zhdm)) {
            return null;
        }
        return entityMapper.selectByPrimaryKey(BdcBdcdjbZdjbxxDO.class,zhdm);
    }

    /**
     * 通过宗地宗海代码获取登记簿信息
     * @param zhdm
     * @return BdcBdcdjbDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcBdcdjbDO queryBdcdjb(String zhdm) {
        if (StringUtils.isBlank(zhdm)) {
            return null;
        }
        return entityMapper.selectByPrimaryKey(BdcBdcdjbDO.class,zhdm);
    }

    /**
     * 通过宗海代码获取用海情况信息
     * @param zhdm
     * @return List<BdcBdcdjbZhjbxxYhzkDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcBdcdjbZhjbxxYhzkDO> queryBdcdjbYhzk(String zhdm) {
        if (StringUtils.isBlank(zhdm)) {
            return Collections.emptyList();
        }
        BdcBdcdjbZhjbxxYhzkDO bdcBdcdjbZhjbxxYhzkDO = new BdcBdcdjbZhjbxxYhzkDO();
        bdcBdcdjbZhjbxxYhzkDO.setZhdm(zhdm);
        List<BdcBdcdjbZhjbxxYhzkDO> bdcBdcdjbZhjbxxYhzkDOS= entityMapper.selectByObj(bdcBdcdjbZhjbxxYhzkDO);
        if (CollectionUtils.isEmpty(bdcBdcdjbZhjbxxYhzkDOS)) {
            return Collections.emptyList();
        }
        return bdcBdcdjbZhjbxxYhzkDOS;
    }

    /**
     * 通过宗地代码获取宗地变化情况信息
     * @param zddm
     * @return List<BdcBdcdjbZdjbxxZdbhqkDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcBdcdjbZdjbxxZdbhqkDO> queryBdcdjbZdbhqk(String zddm) {
        if (StringUtils.isBlank(zddm)) {
            return Collections.emptyList();
        }
        BdcBdcdjbZdjbxxZdbhqkDO bdcBdcdjbZdjbxxZdbhqkDO = new BdcBdcdjbZdjbxxZdbhqkDO();
        bdcBdcdjbZdjbxxZdbhqkDO.setZddm(zddm);
        List<BdcBdcdjbZdjbxxZdbhqkDO> bdcBdcdjbZdjbxxZdbhqkDOS= entityMapper.selectByObj(bdcBdcdjbZdjbxxZdbhqkDO);
        if (CollectionUtils.isEmpty(bdcBdcdjbZdjbxxZdbhqkDOS)) {
            return Collections.emptyList();
        }
        return bdcBdcdjbZdjbxxZdbhqkDOS;
    }

    /**
     * 通过宗海代码获取宗海变化情况信息
     * @param zhdm
     * @return List<BdcBdcdjbZhjbxxZhbhqkDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcBdcdjbZhjbxxZhbhqkDO> queryBdcdjbZhbhqk(String zhdm) {
        if (StringUtils.isBlank(zhdm)) {
            return Collections.emptyList();
        }
        BdcBdcdjbZhjbxxZhbhqkDO bdcBdcdjbZhjbxxZhbhqkDO = new BdcBdcdjbZhjbxxZhbhqkDO();
        bdcBdcdjbZhjbxxZhbhqkDO.setZhdm(zhdm);
        List<BdcBdcdjbZhjbxxZhbhqkDO> bdcBdcdjbZhjbxxZhbhqkDOS= entityMapper.selectByObj(bdcBdcdjbZhjbxxZhbhqkDO);
        if (CollectionUtils.isEmpty(bdcBdcdjbZhjbxxZhbhqkDOS)) {
            return Collections.emptyList();
        }
        return bdcBdcdjbZhjbxxZhbhqkDOS;
    }

    /**
     * 通过宗海/海岛代码代码获取用海用岛坐标信息
     * @param zhhddm
     * @return List<BdcBdcdjbZhjbxxYhydzbDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcBdcdjbZhjbxxYhydzbDO> queryBdcdjbYhydzb(String zhhddm) {
        if (StringUtils.isBlank(zhhddm)) {
            return Collections.emptyList();
        }
        BdcBdcdjbZhjbxxYhydzbDO bdcBdcdjbZhjbxxYhydzbDO = new BdcBdcdjbZhjbxxYhydzbDO();
        bdcBdcdjbZhjbxxYhydzbDO.setZhhddm(zhhddm);
        List<BdcBdcdjbZhjbxxYhydzbDO> bdcBdcdjbZhjbxxYhydzbDOList= entityMapper.selectByObj(bdcBdcdjbZhjbxxYhydzbDO);
        if (CollectionUtils.isEmpty(bdcBdcdjbZhjbxxYhydzbDOList)) {
            return Collections.emptyList();
        }
        return bdcBdcdjbZhjbxxYhydzbDOList;
    }

    /**
     * 通过djh查询登记簿要删除数据
     * @param djh
     * @return List<Object>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<Object> queryBdcdjbDelData(String djh,Boolean sfdzbflpbsj) {
        List<Object> list = new ArrayList<>();
        if (StringUtils.isNotBlank(djh)) {
            if(!sfdzbflpbsj){
                list.addAll(queryBdcdjbYhydzb(djh));
                list.addAll(queryBdcdjbZhbhqk(djh));
                list.addAll(queryBdcdjbZdbhqk(djh));
                list.addAll(queryBdcdjbYhzk(djh));
            }
            BdcBdcdjbDO bdcBdcdjbDO=queryBdcdjb(djh);
            if(bdcBdcdjbDO!=null){
                list.add(bdcBdcdjbDO);
            }
            BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO=queryBdcdjbZhJbxx(djh);
            if(bdcBdcdjbZhjbxxDO!=null){
                list.add(bdcBdcdjbZhjbxxDO);
            }
            BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxxDO=queryBdcdjbZdJbxx(djh);
            if(bdcBdcdjbZdjbxxDO!=null){
                list.add(bdcBdcdjbZdjbxxDO);
            }
        }
        return list;
    }

    /**
     * 初始化不动产单元号
     * @param bdcdyh
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public void initBdcdjb(@NotBlank(message = "不动产单元号不能为空") String bdcdyh,String qjgldm) throws Exception {
        //非虚拟单元号的做处理
        if (!BdcdyhToolUtils.checkXnbdcdyh(bdcdyh)) {
            List<InitService> list = initBeanFactory.getTrafficMode(null, InitBdcdjbAbstractService.class);
            if (CollectionUtils.isNotEmpty(list)) {
                InitServiceQO initServiceQO = initServiceQoService.creatInitServiceQO();
                initServiceQO.setSfCshDjb(true);
                initServiceQO.setBdcdyh(bdcdyh);
                initServiceQO.setQjgldm(qjgldm);
                //生成逻辑
                InitServiceDTO initServiceDTO = list.get(0).init(initServiceQO, null);
                //删除逻辑
                List<BdcXmDO> listXm = new ArrayList<>(1);
                BdcXmDO bdcXmDO = new BdcXmDO();
                bdcXmDO.setBdcdyh(bdcdyh);
                listXm.add(bdcXmDO);
                List<Object> deleteList = list.get(0).delete(listXm,false,false,false);
                //统一处理
                InitResultDTO initResultDTO = new InitResultDTO();
                if (initServiceDTO != null) {
                    List<InitServiceDTO> listDTO = new ArrayList<>();
                    listDTO.add(initServiceDTO);
                    initResultDTO = initDataDealService.dealServiceDTO(listDTO);
                }
                //删除数据
                if (CollectionUtils.isNotEmpty(deleteList)) {
                    initResultDTO.setDeleteList(deleteList);
                }
                //插入登记薄相关数据
                initDataDealService.dealResultDTO(initResultDTO, Constants.DATA_TYPE_PT);
            }
        }
    }
}
