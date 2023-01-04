package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.ZdxxByJqxxMapper;
import cn.gtmap.realestate.building.core.service.ZdxxByJqxxService;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.building.ZdxxByJqxxDTO;
import cn.gtmap.realestate.common.core.qo.building.ZdxxByJqxxQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
 * @version 1.0  2021-11-17
 * @description 根据籍权信息查询宗地信息查询
 */
@Service
public class ZdxxByJqxxServiceImpl implements ZdxxByJqxxService {
    @Autowired
    private ZdxxByJqxxMapper zdxxByJqxxMapper;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    /**
     * 查询宗地信息
     *
     * @param zdxxByJqxxQO
     * @return
     */
    @Override
    public Page<ZdxxByJqxxDTO> findZdxxByJqxx(Pageable pageable, ZdxxByJqxxQO zdxxByJqxxQO) {
        List<ZdxxByJqxxDTO> zdxxByJqxxDTOList = findZdDjdcb(zdxxByJqxxQO);
        Page<ZdxxByJqxxDTO> page = null;
        // 判空分页
        if (CollectionUtils.isNotEmpty(zdxxByJqxxDTOList)) {
             page  = PageUtils.listToPage(zdxxByJqxxDTOList,pageable);
        }
        return page;
    }

    /**
     * 获取宗地信息
     *
     * @param zdxxByJqxxQO 实体入参
     * @return
     */
    private List<ZdxxByJqxxDTO> findZdDjdcb(ZdxxByJqxxQO zdxxByJqxxQO) {
        List<ZdxxByJqxxDTO> zdxxByJqxxDTOList = new ArrayList<>();
        // 不动产权证号 为空
        if (StringUtils.isBlank(zdxxByJqxxQO.getBdcqzh())) {
            // 查询宗地信息
             zdxxByJqxxDTOList = findZdDjdcbByjq(zdxxByJqxxQO);
        } else {
            // 不动产权证号不为空，其他几个参数为空
            if (StringUtils.isBlank(zdxxByJqxxQO.getBdcdyh())
                    && StringUtils.isBlank(zdxxByJqxxQO.getDjh())
                    && StringUtils.isBlank(zdxxByJqxxQO.getZl())
                    && StringUtils.isBlank(zdxxByJqxxQO.getQlrmc())) {
                zdxxByJqxxDTOList = findZdDjdcbByBdcqzh(zdxxByJqxxQO.getBdcqzh());
            } else {
                // 入参较全，则两个结果求交集
                List<ZdxxByJqxxDTO> zdxxByJqxxDTOListOne = findZdDjdcbByBdcqzh(zdxxByJqxxQO.getBdcqzh());
                List<ZdxxByJqxxDTO> zdxxByJqxxDTOListTwo = findZdDjdcbByjq(zdxxByJqxxQO);
                // 两个集合都不为空则取交集
                if (CollectionUtils.isNotEmpty(zdxxByJqxxDTOListOne) && CollectionUtils.isNotEmpty(zdxxByJqxxDTOListTwo)) {
                    zdxxByJqxxDTOList = zdxxByJqxxDTOListOne.stream().filter(item -> zdxxByJqxxDTOListTwo.stream().map(e -> e.getBdcdyh()).collect(Collectors.toList()).contains(item.getBdcdyh())).collect(Collectors.toList());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(zdxxByJqxxDTOList)) {
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

            //处理不动产类型，根据bdcdyh判断
            for (ZdxxByJqxxDTO zdxxByJqxxDTO : zdxxByJqxxDTOList) {
                String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(zdxxByJqxxDTO.getBdcdyh(), "");
                String bdclxmc = StringToolUtils.convertBeanPropertyValueOfZdByString(
                        bdclx, zdMap.get(CommonConstantUtils.BDCLX));
                zdxxByJqxxDTO.setBdclx(bdclx);
                zdxxByJqxxDTO.setBdclxmc(bdclxmc);
            }
        }
        return zdxxByJqxxDTOList;
    }


    /**
     * 不动产权证号获取数据
     *
     * @param bdcqzh 不动产权证号
     * @return
     */
    private List<ZdxxByJqxxDTO> findZdDjdcbByBdcqzh(String bdcqzh) {
        // 则用产权证号获取不动产单元号
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(bdcqzh);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        // 不动产权证号是唯一索引
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            // 不动产单元号前19位
            String bdcdyh = bdcZsDOList.get(0).getBdcdyh().substring(0, 19);
           return findZdDjdcbBybdcdyh(bdcdyh);
        }
        return null;
    }

    /**
     * 根据坐落，不动产单元号，地籍号查询 宗地信息 ZdDjdcb
     *
     * @param zdxxByJqxxQO
     * @return
     */
    private List<ZdxxByJqxxDTO> findZdDjdcbByjq(ZdxxByJqxxQO zdxxByJqxxQO) {
        return zdxxByJqxxMapper.findZdDjdcb(zdxxByJqxxQO);
    }

    /**
     * 根据不动产单元号获取宗地信息 ，取不动产单元号的前19位匹配数据
     *
     * @param bdcdyh 不动产单元号
     * @return
     */
    private List<ZdxxByJqxxDTO> findZdDjdcbBybdcdyh(String bdcdyh) {
        return zdxxByJqxxMapper.findZdDjdcbByBdcdyh(bdcdyh);
    }


}
