package cn.gtmap.realestate.init.service.djbxx.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.ZhJzbsb;
import cn.gtmap.realestate.common.core.domain.building.ZhZhjnbdyjlb;
import cn.gtmap.realestate.common.core.dto.building.ZhDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.djbxx.InitBdcdjbAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
@Service
public class InitBdcdjbServiceImpl extends InitBdcdjbAbstractService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private InitBeanFactory initBeanFactory;

    @Override
    public String getVal() {
        return Constants.DEFAULT;
    }

    @Override
    public BdcBdcdjbDO initBdcdjb(InitServiceQO initServiceQO) {
        BdcBdcdjbDO bdcBdcdjbDO = null;
        if(initBeanFactory.isLpbsjCoverDjb() &&initServiceQO.isSfzqlpbsj() &&StringUtils.isNotBlank(initServiceQO.getBdcdyh())){
            BdcBdcdjbDO ybdcBdcdjbDO =entityMapper.selectByPrimaryKey(BdcBdcdjbDO.class, StringUtils.substring(initServiceQO.getBdcdyh(), 0, 19));
            //同步权籍
            if(ybdcBdcdjbDO != null) {
                bdcBdcdjbDO =new BdcBdcdjbDO();
                dozerUtils.sameBeanDateConvert(ybdcBdcdjbDO, bdcBdcdjbDO, false);
            }
        }
        if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {
            if(bdcBdcdjbDO ==null) {
                bdcBdcdjbDO = new BdcBdcdjbDO();
            }
            initDozerMapper.map(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO(), bdcBdcdjbDO);
            if(initServiceQO.getBdcXm()!=null){
                initDozerMapper.map(initServiceQO.getBdcXm(), bdcBdcdjbDO);
            }
        }
        return bdcBdcdjbDO;
    }

    @Override
    public BdcBdcdjbZdjbxxDO initBdcdjbZdjbxx(InitServiceQO initServiceQO) {
        BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxx = null;
        if(initBeanFactory.isLpbsjCoverDjb() &&initServiceQO.isSfzqlpbsj() &&StringUtils.isNotBlank(initServiceQO.getBdcdyh())){
            BdcBdcdjbZdjbxxDO ybdcBdcdjbzdjbxx =entityMapper.selectByPrimaryKey(BdcBdcdjbZdjbxxDO.class, StringUtils.substring(initServiceQO.getBdcdyh(), 0, 19));
            //同步权籍
            if(ybdcBdcdjbzdjbxx != null) {
                bdcBdcdjbZdjbxx =new BdcBdcdjbZdjbxxDO();
                dozerUtils.sameBeanDateConvert(ybdcBdcdjbzdjbxx, bdcBdcdjbZdjbxx, false);
            }
        }

        if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {
            if(bdcBdcdjbZdjbxx ==null) {
                bdcBdcdjbZdjbxx=new BdcBdcdjbZdjbxxDO();
            }

            initDozerMapper.map(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO(), bdcBdcdjbZdjbxx);
            if(initServiceQO.getBdcXm()!=null){
                initDozerMapper.map(initServiceQO.getBdcXm(), bdcBdcdjbZdjbxx);
            }
        }
        return bdcBdcdjbZdjbxx;
    }

    @Override
    public BdcBdcdjbZhjbxxDO initBdcdjbZhjbxx(InitServiceQO initServiceQO) {
        BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxx = null;
        if(initBeanFactory.isLpbsjCoverDjb() &&initServiceQO.isSfzqlpbsj() &&StringUtils.isNotBlank(initServiceQO.getBdcdyh())){
            BdcBdcdjbZhjbxxDO ybdcBdcdjbZhjbxx =entityMapper.selectByPrimaryKey(BdcBdcdjbZhjbxxDO.class, StringUtils.substring(initServiceQO.getBdcdyh(), 0, 19));
            //同步权籍
            if(ybdcBdcdjbZhjbxx != null) {
                bdcBdcdjbZhjbxx =new BdcBdcdjbZhjbxxDO();
                dozerUtils.sameBeanDateConvert(ybdcBdcdjbZhjbxx, bdcBdcdjbZhjbxx, false);
            }
        }
        if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {
            if(bdcBdcdjbZhjbxx ==null) {
                bdcBdcdjbZhjbxx=new BdcBdcdjbZhjbxxDO();
            }
            initDozerMapper.map(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO(), bdcBdcdjbZhjbxx);
            if(initServiceQO.getBdcXm()!=null){
                initDozerMapper.map(initServiceQO.getBdcXm(), bdcBdcdjbZhjbxx);
            }
        }
        return bdcBdcdjbZhjbxx;
    }

    /**
     * @param initServiceQO 初始化所需数据结构
     * @return List<BdcBdcdjbZhjbxxYhzkDO>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 返回登记薄宗海用海状况List
     */
    @Override
    public List<BdcBdcdjbZhjbxxYhzkDO> initBdcdjbZhjbxxYhzkList(InitServiceQO initServiceQO,BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO) {
        List<BdcBdcdjbZhjbxxYhzkDO> bdcBdcdjbZhjbxxYhzkDOList = new ArrayList<>();
        ZhDjdcbResponseDTO zhDjdcbResponseDTO = (ZhDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
        if (zhDjdcbResponseDTO != null && CollectionUtils.isNotEmpty(zhDjdcbResponseDTO.getZhZhjnbdyjlbList())) {
            Double syjze=0.0;
            for(ZhZhjnbdyjlb zhZhjnbdyjlb : zhDjdcbResponseDTO.getZhZhjnbdyjlbList()){
                BdcBdcdjbZhjbxxYhzkDO bdcBdcdjbZhjbxxYhzkDO = new BdcBdcdjbZhjbxxYhzkDO();
                initDozerMapper.map(zhDjdcbResponseDTO,bdcBdcdjbZhjbxxYhzkDO);
                initDozerMapper.map(zhZhjnbdyjlb,bdcBdcdjbZhjbxxYhzkDO);
                if(zhZhjnbdyjlb.getSyjse()!=null){
                    syjze+=zhZhjnbdyjlb.getSyjse();
                }
                bdcBdcdjbZhjbxxYhzkDOList.add(bdcBdcdjbZhjbxxYhzkDO);
            }
            if(bdcBdcdjbZhjbxxDO!=null){
                bdcBdcdjbZhjbxxDO.setSyjze(syjze);
            }
        }
        return bdcBdcdjbZhjbxxYhzkDOList;
    }

    /**
     * @param initServiceQO 初始化所需数据结构
     * @return List<BdcBdcdjbZhjbxxYhydzbDO>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 返回登记薄宗海用海用岛坐标List
     */
    @Override
    public List<BdcBdcdjbZhjbxxYhydzbDO> initBdcdjbZhjbxxYhydzbList(InitServiceQO initServiceQO) {
        List<BdcBdcdjbZhjbxxYhydzbDO> bdcBdcdjbZhjbxxYhydzbDOList = new ArrayList<>();
        ZhDjdcbResponseDTO zhDjdcbResponseDTO = (ZhDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
        if (zhDjdcbResponseDTO != null && CollectionUtils.isNotEmpty(zhDjdcbResponseDTO.getZhJzbsbList())) {
            for(ZhJzbsb zhJzbsb : zhDjdcbResponseDTO.getZhJzbsbList()){
                BdcBdcdjbZhjbxxYhydzbDO bdcBdcdjbZhjbxxYhydzbDO = new BdcBdcdjbZhjbxxYhydzbDO();
                initDozerMapper.map(zhDjdcbResponseDTO,bdcBdcdjbZhjbxxYhydzbDO);
                initDozerMapper.map(zhJzbsb,bdcBdcdjbZhjbxxYhydzbDO);
                bdcBdcdjbZhjbxxYhydzbDOList.add(bdcBdcdjbZhjbxxYhydzbDO);
            }
        }
        return bdcBdcdjbZhjbxxYhydzbDOList;
    }
}
