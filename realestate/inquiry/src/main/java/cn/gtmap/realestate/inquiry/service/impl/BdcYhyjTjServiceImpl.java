package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhyjTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYhyjTjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.inquiry.service.BdcYhyjTjService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/7/28.
 * @description 银行月结统计实现类
 */
@Service
public class BdcYhyjTjServiceImpl implements BdcYhyjTjService {

    public static final Logger LOGGER = LoggerFactory.getLogger(BdcYhyjTjServiceImpl.class);

    @Autowired
    private Repo repo;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;



    /**
     * 查询银行月结统计数据
     * @param bdcYhyjTjQO  银行月结统计QO
     * @return 银行月结统计数据
     */
    @Override
    public List<BdcYhyjTjDTO> listBdcYhyjTj(BdcYhyjTjQO bdcYhyjTjQO) {
        List<BdcYhyjTjDTO> bdcYhyjTjDTOList = repo.selectList("listBdcYhyjTjByPage", bdcYhyjTjQO);
        if(CollectionUtils.isNotEmpty(bdcYhyjTjDTOList)){
            // 当数量大于500时，进行分段查询
            if(bdcYhyjTjDTOList.size() > 500){
                List<List> subList = ListUtils.subList(bdcYhyjTjDTOList, 500);
                List<BdcYhyjTjDTO> filterList = new ArrayList<>(bdcYhyjTjDTOList.size());
                for (List data : subList) {
                    List copyList = new ArrayList(data);
                    this.addHj(copyList);
                    filterList.addAll(copyList);
                }
                return filterList;
            }else{
                this.addHj(bdcYhyjTjDTOList);
            }
        }
        return bdcYhyjTjDTOList;
    }

    // 调用受理收费信息接口，通过xmid获取对应的收费信息
    private void addHj(List<BdcYhyjTjDTO> bdcYhyjTjDTOList){
        if(CollectionUtils.isNotEmpty(bdcYhyjTjDTOList)){
            List<String> xmids = bdcYhyjTjDTOList.stream().map(t -> t.getXmid()).collect(Collectors.toList());
            Map<String,Object> hjMap = this.bdcSlSfxxFeignService.queryHjGroupByXmids(xmids);
            if(MapUtils.isNotEmpty(hjMap)){
                // 过滤掉收费信息为空的数据
                Iterator<BdcYhyjTjDTO> it = bdcYhyjTjDTOList.iterator();
                while(it.hasNext()){
                    BdcYhyjTjDTO bdcYhyjTjDTO = it.next();
                    String xmid = bdcYhyjTjDTO.getXmid();
                    if(hjMap.containsKey(xmid)){
                        bdcYhyjTjDTO.setHj((Double) hjMap.get(xmid));
                    }else{
                        it.remove();
                    }
                }
            }else{
                // 未获取到收费信息，将月结统计查询到的项目数据置空
                bdcYhyjTjDTOList.clear();
            }
        }
    }

}
