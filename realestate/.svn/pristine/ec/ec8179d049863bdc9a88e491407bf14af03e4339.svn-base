package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxmPzMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmPzService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmSfbzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2019/1/3
 * @description 不动产受理收费项目数据服务
 */
@Service
public class BdcSlSfxmPzServiceImpl implements BdcSlSfxmPzService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private Repo repo;
    @Autowired
    private BdcSlSfxmPzMapper bdcSlSfxmPzMapper;
    @Autowired
    private BdcSlSfxmSfbzService bdcSlSfxmSfbzService;

    /**
     * @param djxl 登记小类
     * @return 不动产受理收费项目配置
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据登记小类获取不动产受理收费项目配置
     */
    @Override
    public List<BdcSlSfxmPzDO> listBdcSlSfxmPzByDjxl(String djxl) {
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(djxl)) {
            Example example = new Example(BdcSlSfxmPzDO.class);
            example.createCriteria().andEqualTo("djxl", djxl);
            example.setOrderByClause("xh");
            bdcSlSfxmPzDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxmPzDOList)) {
            bdcSlSfxmPzDOList = Collections.emptyList();
        }
        return bdcSlSfxmPzDOList;
    }

    /**
     * @param djxl  登记小类
     * @param qlrlb 权利人类别
     * @return 不动产受理收费项目配置
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据登记小类和权利人类别获取不动产受理收费项目配置
     */
    @Override
    public List<BdcSlSfxmPzDO> listBdcSlSfxmPzByDjxlAndQlrlb(String djxl, String qlrlb) {
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(djxl) && StringUtils.isNotBlank(qlrlb)) {
            Example example = new Example(BdcSlSfxmPzDO.class);
            example.createCriteria().andEqualTo("djxl", djxl).andEqualTo("qlrlb", qlrlb);
            example.setOrderByClause("xh");
            bdcSlSfxmPzDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxmPzDOList)) {
            bdcSlSfxmPzDOList = Collections.emptyList();
        }
        return bdcSlSfxmPzDOList;
    }

    /**
     * @param bdcSlSfxmPzDO 不动产受理收费项目配置
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收费项目配置
     */
    @Override
    public int saveBdcSlSfxmPzDO(BdcSlSfxmPzDO bdcSlSfxmPzDO) {
        if (bdcSlSfxmPzDO == null) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        if (StringUtils.isBlank(bdcSlSfxmPzDO.getSfxmpzid())) {
            bdcSlSfxmPzDO.setSfxmpzid(UUIDGenerator.generate16());
            return entityMapper.insertSelective(bdcSlSfxmPzDO);
        }
        return entityMapper.updateByPrimaryKey(bdcSlSfxmPzDO);
    }

    /**
     * @param bdcSlSfxmPzDOList 收费项目配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产受理收费项目配置
     */
    @Override
    public int deleteBdcSlSfxmPzDO(List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList) {
        int count = 0;
        if (CollectionUtils.isEmpty(bdcSlSfxmPzDOList)) {
            return count;
        }
        for (BdcSlSfxmPzDO bdcSlSfxmPzDO : bdcSlSfxmPzDOList) {
            count += entityMapper.delete(bdcSlSfxmPzDO);
        }
        return count;
    }

    /**
     * @param pageable
     * @param bdcSlSfxmPzJson 收费项目配置
     * @return 分页
     * @author <a href="mailto:gailining@gtmap.cn">gailining</a>
     * @description 分页查询收费项目配置
     */
    @Override
    public Page<BdcSlSfxmPzDO> listBdcSlSfxmPzByPage(Pageable pageable, String bdcSlSfxmPzJson) {
        BdcSlSfxmPzDO bdcSlSfxmPzDO = new BdcSlSfxmPzDO();
        if (StringUtils.isNotBlank(bdcSlSfxmPzJson)) {
            bdcSlSfxmPzDO = JSONObject.parseObject(bdcSlSfxmPzJson, BdcSlSfxmPzDO.class);
        }
        return repo.selectPaging("listBdcSlSfxmPzByPage", bdcSlSfxmPzDO, pageable);
    }

    /**
     * @param bdcSlSfxmPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取受理收费项目配置 最大序号
     */
    @Override
    public Integer querySfxmPzMaxSxh(BdcSlSfxmPzDO bdcSlSfxmPzDO) {
        if(bdcSlSfxmPzDO==null){
            throw new MissingArgumentException("获取受理收费项目配置 最大序号 参数不能为空！");
        }
        return bdcSlSfxmPzMapper.querySfxmPzMaxSxh(bdcSlSfxmPzDO);
    }

    @Override
    public List<BdcSlSfxmDTO> listAllBdcSlSfxmDTO(){
        List<BdcSlSfxmDTO> bdcSlSfxmDTOList = new ArrayList<>();
        //获取所有收费项目配置
        Example example = new Example(BdcSlSfxmPzDO.class);
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcSlSfxmPzDOList)) {
            //根据收费项目代码去重
            List<BdcSlSfxmPzDO> nulldmSfxmpzList = bdcSlSfxmPzDOList.stream().filter(bdcSlSfxmPzDO -> StringUtils.isBlank(bdcSlSfxmPzDO.getSfxmdm())).collect(Collectors.toList());
            bdcSlSfxmPzDOList.removeAll(nulldmSfxmpzList);
            List<BdcSlSfxmPzDO> bdcSlSfxmPzDOS = bdcSlSfxmPzDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getSfxmdm()))), ArrayList::new));
            if (CollectionUtils.isNotEmpty(bdcSlSfxmPzDOS)) {
                for (int i = 0; i < bdcSlSfxmPzDOS.size(); i++) {
                    BdcSlSfxmPzDO bdcSlSfxmPzDO = bdcSlSfxmPzDOS.get(i);
                    BdcSlSfxmDTO bdcSlSfxmDTO = new BdcSlSfxmDTO(bdcSlSfxmPzDO.getSfxmdm(), bdcSlSfxmPzDO.getSfxmmc(), bdcSlSfxmPzDO.getSl(), bdcSlSfxmPzDO.getJedw(), null, bdcSlSfxmPzDO.getQlrlb());
                    List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = bdcSlSfxmSfbzService.listBdcSlSfxmSfbzDO(bdcSlSfxmPzDO.getSfxmdm());
                    if (CollectionUtils.isNotEmpty(bdcSlSfxmSfbzDOList)) {
                        bdcSlSfxmDTO.setBdcSlSfxmSfbzList(bdcSlSfxmSfbzDOList);
                    }
                    bdcSlSfxmDTOList.add(bdcSlSfxmDTO);
                }
            }
        }
        return bdcSlSfxmDTOList;

    }

    @Override
    public List<BdcSlSfxmDTO> listBdcSlSfxmDTOByDjxl(String djxl) {
        if(StringUtils.isBlank(djxl)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到登记小类信息。");
        }

        List<BdcSlSfxmDTO> bdcSlSfxmDTOList = new ArrayList<>();

        // 根据登记小类获取收费项目配置
        Example example = new Example(BdcSlSfxmPzDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("djxl", djxl);
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList = entityMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(bdcSlSfxmPzDOList)) {
            //根据收费项目代码去重
            List<BdcSlSfxmPzDO> nulldmSfxmpzList = bdcSlSfxmPzDOList.stream().filter(bdcSlSfxmPzDO -> StringUtils.isBlank(bdcSlSfxmPzDO.getSfxmdm())).collect(Collectors.toList());
            bdcSlSfxmPzDOList.removeAll(nulldmSfxmpzList);
            List<BdcSlSfxmPzDO> bdcSlSfxmPzDOS = bdcSlSfxmPzDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getSfxmdm()))), ArrayList::new));
            if (CollectionUtils.isNotEmpty(bdcSlSfxmPzDOS)) {
                for (int i = 0; i < bdcSlSfxmPzDOS.size(); i++) {
                    BdcSlSfxmPzDO bdcSlSfxmPzDO = bdcSlSfxmPzDOS.get(i);
                    BdcSlSfxmDTO bdcSlSfxmDTO = new BdcSlSfxmDTO(bdcSlSfxmPzDO.getSfxmdm(), bdcSlSfxmPzDO.getSfxmmc(), bdcSlSfxmPzDO.getSl(), bdcSlSfxmPzDO.getJedw(), null, bdcSlSfxmPzDO.getQlrlb());
                    List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = bdcSlSfxmSfbzService.listBdcSlSfxmSfbzDO(bdcSlSfxmPzDO.getSfxmdm());
                    if (CollectionUtils.isNotEmpty(bdcSlSfxmSfbzDOList)) {
                        bdcSlSfxmDTO.setBdcSlSfxmSfbzList(bdcSlSfxmSfbzDOList);
                    }
                    bdcSlSfxmDTOList.add(bdcSlSfxmDTO);
                }
            }
        }
        return bdcSlSfxmDTOList;
    }
}
