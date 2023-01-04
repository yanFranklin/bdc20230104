package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.building.ZdJzwsuqgydcDO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcFdcq3AbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从楼盘表加载数据到建筑物区分所有权业主共有部分登记信息
 */
@Service
public class InitLpbToBdcFdcq3ServiceImpl extends InitBdcFdcq3AbstractService {

    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        // 通过地籍信息为建筑物区分业主共有部分赋值
        BdcFdcq3DO bdcFdcq3DO=initFwFromLpb(initServiceQO, BdcFdcq3DO.class);
        return bdcFdcq3DO;
    }

    /**
     * 处理房建筑物区分所有权业主共有部分登记信息_共有部分
     *
     * @param initServiceQO
     * @param qlid
     * @return
     */
    @Override
    public List<BdcFdcq3GyxxDO> initFdcq3Gyxx(InitServiceQO initServiceQO, String qlid) throws InstantiationException, IllegalAccessException {
        List<BdcFdcq3GyxxDO> gyxxList = new ArrayList<>();
        //建筑物所有权共有调查实体
        List<ZdJzwsuqgydcDO> list = initServiceQO.getDjxxResponseDTO().getZdJzwsuqgydcDOList();
        BdcFdcq3GyxxDO gyxxDO = initFromLpbXm(initServiceQO, BdcFdcq3GyxxDO.class, null);
        gyxxDO.setQlid(qlid);
        if (CollectionUtils.isNotEmpty(list)) {
            for (ZdJzwsuqgydcDO zdJzwsuqgydcDO : list) {
                BdcFdcq3GyxxDO bdcFdcq3GyxxDO=new BdcFdcq3GyxxDO();
                BeanUtils.copyProperties(gyxxDO,bdcFdcq3GyxxDO);
                initDozerMapper.map(zdJzwsuqgydcDO, bdcFdcq3GyxxDO);
                gyxxList.add(bdcFdcq3GyxxDO);
            }
        }else if(initServiceQO.getBdcdyDTO()!=null){
            BdcFdcq3GyxxDO bdcFdcq3GyxxDO=new BdcFdcq3GyxxDO();
            BeanUtils.copyProperties(gyxxDO,bdcFdcq3GyxxDO);
            initDozerMapper.map(initServiceQO.getBdcdyDTO(),bdcFdcq3GyxxDO);
            gyxxList.add(bdcFdcq3GyxxDO);
        }
        return gyxxList;
    }

}
