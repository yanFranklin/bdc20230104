package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcJgLzrGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJgLzrGxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXtJgDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXtjgLzrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXtjgQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.mapper.BdcXtJgMapper;
import cn.gtmap.realestate.init.core.service.BdcXtJgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 机构配置实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/2 13:52
 */
@Service
public class BdcXtJgServiceImpl implements BdcXtJgService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private BdcXtJgMapper bdcXtJgMapper;

    @Autowired
    Repo repo;

    @Value("${xtjg.filter.nf:}")
    private String nfFilter;

    /**
     * 获取年份过滤值
     */
    private List<String> getXtjgNfFilter(){
        List<String> nfList = new ArrayList<>();
        if(StringUtils.isNotBlank(nfFilter)){
            String[] nfArray = nfFilter.split(",");
            for(int i = 0; i< nfArray.length; i++){
                if("currentYear".equals(nfArray[i])){
                    nfList.add(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
                }else{
                    nfList.add(nfArray[i]);
                }
            }
        }
        return nfList;
    }

    /**
     * 获取全部信息银行
     *
     * @return list
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcXtJgDO> listBdcXtJg(Integer jglb) {
        Example example = new Example(BdcXtJgDO.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("jgid ASC");
        criteria.andEqualTo("jglb", jglb);
        return selectXtjg(example);
    }

    private List<BdcXtJgDO> selectXtjg(Example example){
        // 获取配置的年份过滤参数
        List years = this.getXtjgNfFilter();
        if(CollectionUtils.isEmpty(years)){
            return entityMapper.selectByExampleNotNull(example);
        }else{
            example.createCriteria().andIn("nf", years);
            List<BdcXtJgDO> bdcXtJgDOList = entityMapper.selectByExampleNotNull(example);
            List<BdcXtJgDO> xtjgFilterList = new ArrayList<>(10);
            if(CollectionUtils.isNotEmpty(bdcXtJgDOList)){
                // 按年份排序，在根据jgmc进行分组，获取最大年份的机构数据
                Map<String, List<BdcXtJgDO>> xtjgMap = bdcXtJgDOList.stream()
                        .sorted(Comparator.comparing(BdcXtJgDO::getNf, Comparator.nullsLast(String::compareTo)).reversed())
                        .collect(Collectors.groupingBy(BdcXtJgDO::getJgmc));
                for(Map.Entry<String, List<BdcXtJgDO>> entry: xtjgMap.entrySet()){
                    xtjgFilterList.add(entry.getValue().get(0));
                }
            }
            return xtjgFilterList;
        }
    }


    @Override
    public List<BdcXtJgDO> listBdcXtJg(BdcXtJgDO bdcXtJgDO) {
        if(Objects.isNull(bdcXtJgDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到系统机构查询参数");
        }
        Example example = new Example(BdcXtJgDO.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("jgid ASC");
        if(StringUtils.isNotBlank(bdcXtJgDO.getSfky())){
            criteria.andEqualTo("sfky", bdcXtJgDO.getSfky());
        }
        if(bdcXtJgDO.getSfxyjg() != null){
            criteria.andEqualTo("sfxyjg", bdcXtJgDO.getSfxyjg());
        }
        if(Objects.nonNull(bdcXtJgDO.getJglb())){
            criteria.andEqualTo("jglb", bdcXtJgDO.getJglb());
        }
        if(StringUtils.isNotBlank(bdcXtJgDO.getJgmc())){
            criteria.andEqualTo("jgmc", bdcXtJgDO.getJgmc());
        }
        if(StringUtils.isNotBlank(bdcXtJgDO.getJgid())){
            criteria.andEqualTo("jgid", bdcXtJgDO.getJgid());
        }
        return this.selectXtjg(example);
    }

    /**
     * 根据机构名称查询出对应机构信息
     *
     * @param jgmc 机构名称
     * @return
     */
    @Override
    public BdcXtJgDO queryJgByJgMc(String jgmc, String jglb) {
        if (StringUtils.isBlank(jgmc)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        Example example = new Example(BdcXtJgDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("jgmc", jgmc);
        if(StringUtils.isNotBlank(jglb)){
            criteria.andEqualTo("jglb", jglb);
        }
        List<BdcXtJgDO> bdcXtJgDOList = this.selectXtjg(example);
        BdcXtJgDO bdcXtJgDO = new BdcXtJgDO();
        if (CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
            bdcXtJgDO = bdcXtJgDOList.get(0);
        }
        return bdcXtJgDO;
    }

    /**
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取按月结算银行信息
     */
    @Override
    public List<BdcXtJgDO> listAyjsBdcXtJg(String yhmc) {
        Example example = new Example(BdcXtJgDO.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sfayjs", 1);
		if (StringUtils.isNotBlank(yhmc)) {
            criteria.andEqualTo("jgmc", yhmc);
		}
        return this.selectXtjg(example);
    }

    @Override
    public List<BdcXtJgDO> listAyjsBdcXtJgLike(String yhmc) {
        Example example = new Example(BdcXtJgDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sfayjs", 1);
        if (StringUtils.isNotBlank(yhmc)) {
            criteria.andLike("jgmc", "%" + yhmc + "%");
        }
        return this.selectXtjg(example);
    }

    /**
     * 根据机构id查询配置的领证人信息
     *
     * @param jgid
     * @return
     */
    @Override
    public List<BdcJgLzrGxDO> queryJgLzrByJgid(String jgid) {
        if (StringUtils.isBlank(jgid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        Example example = new Example(BdcJgLzrGxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("jgid", jgid);
        return entityMapper.selectByExample(example);
    }

    /**
     * 查询机构领证人分页
     *
     * @param pageable
     * @param bdcXtjgLzrQO
     * @return
     */
    @Override
    public Page<BdcJgLzrGxDTO> queryJgLzrByPage(Pageable pageable, BdcXtjgLzrQO bdcXtjgLzrQO) {
        if(StringUtils.isNotBlank(bdcXtjgLzrQO.getJgid())){
            bdcXtjgLzrQO.setJgidList(Arrays.asList(bdcXtjgLzrQO.getJgid().split(",")));
        }
        return repo.selectPaging("queryJgLzrByPage",bdcXtjgLzrQO,pageable);
    }

    /**
     * 查询机构分页
     *
     * @param pageable
     * @param bdcXtjgQO
     * @return BdcXtJgDO
     */
    @Override
    public Page<BdcXtJgDTO> queryJgByPage(Pageable pageable, BdcXtjgQO bdcXtjgQO) {
        return repo.selectPaging("queryXtJgByPage",bdcXtjgQO,pageable);
    }

    /**
     * 删除机构领证人
     *
     * @param gxid
     */
    @Override
    public void deleteJglzr(String gxid) {
        if (StringUtils.isNotBlank(gxid)) {
            entityMapper.deleteByPrimaryKey(BdcJgLzrGxDO.class, gxid);
        }
    }

    /**
     * 新增机构领证人
     *
     * @param bdcJgLzrGxDO
     */
    @Override
    public void insertBdcLzr(BdcJgLzrGxDO bdcJgLzrGxDO) {
        if (bdcJgLzrGxDO != null) {
            if (StringUtils.isBlank(bdcJgLzrGxDO.getGxid())) {
                bdcJgLzrGxDO.setGxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcJgLzrGxDO);
        }
    }

    @Override
    public void batchModifyXtJgSfky(List<String> yhmcList, int sfky) {
        if(CollectionUtils.isNotEmpty(yhmcList)){
            bdcXtJgMapper.batchModifyXtJgSfky(yhmcList, sfky);
        }
    }

    /**
     * 新增机构
     *
     * @param bdcXtJgDO
     */
    @Override
    public void insertBdcXtJg(BdcXtJgDO bdcXtJgDO) {
        if (bdcXtJgDO != null) {
            if (StringUtils.isBlank(bdcXtJgDO.getJgid())) {
                bdcXtJgDO.setJgid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcXtJgDO);
        }
    }
    /**
     * @param jgmclist 机构名称集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @Override
    public List<BdcXtJgDO> queryBatchBdcXtJg(List<String> jgmclist) {
        if(CollectionUtils.isEmpty(jgmclist)){
            return Collections.emptyList();
        }
        return bdcXtJgMapper.queryBatchBdcXtJg(jgmclist);
    }

}
