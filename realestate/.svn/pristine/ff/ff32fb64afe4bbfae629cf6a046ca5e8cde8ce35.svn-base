package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjMxDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjdDO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZjMapper;
import cn.gtmap.realestate.inquiry.service.BdcZjService;
import cn.gtmap.realestate.inquiry.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class BdcZjServiceImpl implements BdcZjService {

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZjMapper bdcZjMapper;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    // 随机抽查频率
    @Value("${zjcx.ccpl:20}")
    private Integer ccpl;

    // 随机抽查频率
    @Value("${zjcx.ccsl:100}")
    private Integer ccsl;

    @Override
    public Page<BdcXmDO> listBdcZjxx(Pageable pageable, BdcZjQO bdcZjQO) {
        Page<BdcXmDO> bdcXmDOPage =  repo.selectPaging("listBdcZjxxByPage", bdcZjQO, pageable);
        return bdcXmDOPage;
    }

    @Override
    public String generateRandomZjd(BdcZjQO bdcZjQO) {
        List<BdcXmDO> bdcXmDOList = bdcZjMapper.listBdcZjxxByPage(bdcZjQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new MissingArgumentException("随机筛选到质检信息为空，请调整查询条件重新筛选。");
        }
        // 获取质检数据，进行随机筛选
        Map<String, BdcXmDO> bdcXmMap = bdcXmDOList.stream().collect(Collectors.toMap(BdcXmDO::getGzlslid, a -> a,(k1, k2)->k1));
        List<String> gzlslids = bdcXmDOList.stream().map(x -> x.getGzlslid()).collect(Collectors.toList());
        Set<String> randomGzlslids = this.getRandomGzlslids(gzlslids);
        if(CollectionUtils.isEmpty(randomGzlslids)){
            throw new MissingArgumentException("随机筛选出错，未筛选出需要质检的信息。");
        }

        UserDto userDto = this.userManagerUtils.getCurrentUser();
        // 生成质检单数据
        String zjdid = this.generateZjd(userDto);

        // 生成质检信息数据
        List<BdcZjDO> bdcZjDOList = new ArrayList<>(randomGzlslids.size());
        for(String gzlslid: randomGzlslids){
            BdcXmDO bdcXmDO = bdcXmMap.get(gzlslid);
            if(Objects.nonNull(bdcXmDO)){
                bdcZjDOList.add(this.generateZjxx(bdcXmMap.get(gzlslid), zjdid));
            }
        }
        this.entityMapper.insertBatchSelective(bdcZjDOList);
        return zjdid;
    }

    @Override
    public String generateManualZjd(List<BdcXmDO> bdcXmDOList) {
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new MissingArgumentException("创建手动筛选质检信息失败，为获取到筛选信息。");
        }
        UserDto userDto = this.userManagerUtils.getCurrentUser();
        // 生成质检单数据
        String zjdid = this.generateZjd(userDto);

        // 生成质检信息数据
        List<BdcZjDO> bdcZjDOList = new ArrayList<>(bdcXmDOList.size());
        for(BdcXmDO bdcXmDO : bdcXmDOList){
            bdcZjDOList.add(this.generateZjxx(bdcXmDO, zjdid));
        }
        this.entityMapper.insertBatchSelective(bdcZjDOList);
        return zjdid;
    }


    /**
     * 生成质检单数据
     */
    private String generateZjd(UserDto userDto){
        BdcZjdDO bdcZjdDO = new BdcZjdDO();
        bdcZjdDO.setZjdid(UUIDGenerator.generate16());
        bdcZjdDO.setZjdcjsj(new Date());
        if(Objects.nonNull(userDto)){
            bdcZjdDO.setZjr(userDto.getAlias());
        }
        bdcZjdDO.setZjzt(CommonConstantUtils.ZJZT_WZJ);
        String zjdbh = bdcBhFeignService.queryCommonBh(Constants.BDC_ZJD_BH, CommonConstantUtils.ZZSJFW_DAY, 4, "");
        bdcZjdDO.setZjdbh(zjdbh);
        entityMapper.insertSelective(bdcZjdDO);
        return bdcZjdDO.getZjdid();
    }

    /**
     * 生成质检信息数据
     */
    private BdcZjDO generateZjxx(BdcXmDO bdcXmDO, String zjdid){
        BdcZjDO bdcZjDO  = new BdcZjDO();
        BeanUtils.copyProperties(bdcXmDO, bdcZjDO);
        bdcZjDO.setLcmc(bdcXmDO.getGzldymc());
        bdcZjDO.setZjdid(zjdid);
        bdcZjDO.setZjid(UUIDGenerator.generate16());
        String zjbh = bdcBhFeignService.queryCommonBh(Constants.BDC_ZJ_BH, CommonConstantUtils.ZZSJFW_DAY, 6, "");
        bdcZjDO.setZjbh(zjbh);
        bdcZjDO.setZjzt(CommonConstantUtils.ZJZT_WZJ);
        bdcZjDO.setHdr(this.getLcHdr(bdcXmDO.getGzlslid()));
        return bdcZjDO;
    }

    /**
     * 获取核定人信息
     */
    private String getLcHdr(String gzlslid){
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setGzlslid(gzlslid);
        bdcShxxQO.setJdmc("核定");
        List<BdcShxxDO> bdcShxxDOList = this.bdcShxxFeignService.queryBdcShxx(bdcShxxQO);
        if(CollectionUtils.isNotEmpty(bdcShxxDOList)){
            String hdrxm = bdcShxxDOList.get(0).getShryxm();
            UserDto userDto = this.userManagerUtils.getUserByName(hdrxm);
            if(Objects.nonNull(userDto)){
                return userDto.getAlias();
            }
        }
        return "";
    }

    /**
     * 随机获取抽查的项目
     */
    private Set<String> getRandomGzlslids(List<String> gzlslids){
        Set<String> gzlslidSet = new HashSet<>();
        // 根据当前可抽查数据和抽查频率 确定抽查数据个数
        Integer count = (int) Math.ceil((double) gzlslids.size() / (double) ccpl);
        // 随机抽取的工作流实例id
        while (gzlslidSet.size() < count) {
            gzlslidSet.add(gzlslids.get((int) (Math.random() * (gzlslids.size() - 1))));
        }
        return gzlslidSet;
    }

    /**
     * 随机获取抽查的项目
     */
    private Set<String> getRandomGzlslids(List<String> gzlslids, int count){
        Set<String> gzlslidSet = new HashSet<>();
        if(gzlslids.size() <= count){
            gzlslidSet.addAll(gzlslids);
        }else{
            while (gzlslidSet.size() < count) {
                gzlslidSet.add(gzlslids.get((int) (Math.random() * (gzlslids.size() - 1))));
            }
        }
        return gzlslidSet;
    }

    @Override
    public void deleteZjxx(String zjid) {
        if(StringUtils.isNotBlank(zjid)){
            this.entityMapper.deleteByPrimaryKey(BdcZjDO.class, zjid);
        }
    }

    @Override
    public BdcZjxxDTO queryBdcZjxxAndMx(String zjid) {
        if(StringUtils.isBlank(zjid)){
            throw new AppException("未获取到质检ID");
        }
        BdcZjxxDTO bdcZjxxDTO = new BdcZjxxDTO();
        BdcZjDO bdcZjDO = entityMapper.selectByPrimaryKey(BdcZjDO.class, zjid);
        if(Objects.nonNull(bdcZjDO)){
            BeanUtils.copyProperties(bdcZjDO, bdcZjxxDTO);
            List<BdcZjMxDO> bdcZjMxDOList = this.queryBdcZjMx(zjid);
            if(CollectionUtils.isNotEmpty(bdcZjMxDOList)){
                bdcZjxxDTO.setZjMxDOList(bdcZjMxDOList);
            }
        }
        return bdcZjxxDTO;
    }

    /**
     * 根据质检ID获取质检明细信息
     */
    private List<BdcZjMxDO> queryBdcZjMx(String zjid){
        Example example = new Example(BdcZjMxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("zjid", zjid);
        example.setOrderByClause("sxh asc");
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcZjDO> queryBdcZjxx(BdcZjDO bdcZjDO) {
        if(StringUtils.isBlank(bdcZjDO.getZjdid()) && StringUtils.isBlank(bdcZjDO.getZjid())){
           throw new AppException("未获取到质检单ID");
        }
        Example example = new Example(BdcZjDO.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(bdcZjDO.getZjid())){
            criteria.andEqualTo("zjid", bdcZjDO.getZjid());
        }
        if(StringUtils.isNotBlank(bdcZjDO.getZjdid())){
            criteria.andEqualTo("zjdid", bdcZjDO.getZjdid());
        }
        return entityMapper.selectByExample(example);
    }

    @Override
    public void modifyZjdZt(String zjdid) {
        if(StringUtils.isBlank(zjdid)){
            throw new AppException("未获取到质检单ID。");
        }
        BdcZjdDO bdcZjdDO = new BdcZjdDO();
        bdcZjdDO.setZjdid(zjdid);
        bdcZjdDO.setZjzt(CommonConstantUtils.ZJZT_YZJ);
        this.entityMapper.updateByPrimaryKeySelective(bdcZjdDO);
    }

    @Override
    public BdcZjdDTO queryBdcZjdAndZjxx(String zjdid) {

        BdcZjdDTO bdcZjdDTO = new BdcZjdDTO();
        BdcZjdDO bdcZjdDO = this.entityMapper.selectByPrimaryKey(BdcZjdDO.class, zjdid);
        if(Objects.nonNull(bdcZjdDO)){
            BeanUtils.copyProperties(bdcZjdDO, bdcZjdDTO);
            List<BdcZjDO> bdcZjDOList = this.queryBdcZjxxByZjdId(zjdid);
            if(CollectionUtils.isNotEmpty(bdcZjDOList)){
                bdcZjdDTO.setBdcZjDOList(bdcZjDOList);
            }
        }
        return bdcZjdDTO;
    }

    @Override
    public List<BdcZjDO> queryBdcZjxxByZjdId(String zjdid) {
        if(StringUtils.isBlank(zjdid)){
            throw new AppException("未获取到质检单ID。");
        }
        Example example = new Example(BdcZjDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("zjdid", zjdid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public void saveZjxxAndZjmx(BdcZjxxDTO bdcZjxxDTO) {
        BdcZjDO bdcZjDO = new BdcZjDO();
        BeanUtils.copyProperties(bdcZjxxDTO, bdcZjDO);
        if(StringUtils.isBlank(bdcZjDO.getZjid())){
            throw new AppException("未获取到质检ID。");
        }
        // 更新质检信息
        this.entityMapper.updateByPrimaryKeySelective(bdcZjDO);

        // 更新质检明细信息
        if(CollectionUtils.isNotEmpty(bdcZjxxDTO.getZjMxDOList())){
            this.removeZjmxByZjid(bdcZjxxDTO.getZjid());
            for(BdcZjMxDO bdcZjMxDO : bdcZjxxDTO.getZjMxDOList()){
                bdcZjMxDO.setZjmxid(UUIDGenerator.generate16());
            }
            this.entityMapper.insertBatchSelective(bdcZjxxDTO.getZjMxDOList());
        }

        //如果有质检单且全部质检过的话则更新质检单的状态
        bdcZjDO = entityMapper.selectByPrimaryKey(BdcZjDO.class, bdcZjDO.getZjid());
        modifyZjdZtByCondition(bdcZjDO.getZjdid());
    }

    @Override
    public void deleteZjd(String zjdid) {
        if(StringUtils.isNotBlank(zjdid)){
            this.entityMapper.deleteByPrimaryKey(BdcZjdDO.class, zjdid);
        }
    }

    @Override
    public List<BdcZjXmxxDTO> randomBdcZjXmxx(BdcZjQO bdcZjQO) {
        List<BdcZjXmxxDTO> bdcZjXmxxDTOList = bdcZjMapper.listBdcZjXmxx(bdcZjQO);
        if(CollectionUtils.isEmpty(bdcZjXmxxDTOList)){
            return new ArrayList<>();
        }
        if(bdcZjQO.isAllsearch()){
            return bdcZjXmxxDTOList;
        }
        // 获取质检数据，进行随机筛选100条数据
        Map<String, BdcZjXmxxDTO> bdcZjXmxxDTOMap = bdcZjXmxxDTOList.stream().collect(Collectors.toMap(BdcZjXmxxDTO::getGzlslid, a -> a,(k1, k2)->k1));
        List<String> gzlslids = bdcZjXmxxDTOList.stream().map(x -> x.getGzlslid()).collect(Collectors.toList());
        Set<String> randomGzlslids = this.getRandomGzlslids(gzlslids, ccsl);
        if(CollectionUtils.isEmpty(randomGzlslids)){
            throw new MissingArgumentException("随机筛选出错，未筛选出需要质检的信息。");
        }
        List<BdcZjXmxxDTO> randomZjXmxxList = new ArrayList<>(randomGzlslids.size());
        randomGzlslids.stream().forEach(t->{
            randomZjXmxxList.add(bdcZjXmxxDTOMap.get(t));
        });

        return randomZjXmxxList;
    }

    @Override
    public BdcZjDO saveBdcZjDO(BdcZjDO bdcZjDO) {
        if(StringUtils.isNotBlank(bdcZjDO.getZjid())){
            this.entityMapper.updateByPrimaryKeySelective(bdcZjDO);
        }else{
            bdcZjDO.setZjid(UUIDGenerator.generate16());
            this.entityMapper.insertSelective(bdcZjDO);
        }
        return bdcZjDO;
    }

    @Override
    public List<BdcZjXmxxDTO> queryBdcZjjgHzxx(BdcZjQO bdcZjQO) {
        return this.bdcZjMapper.queryBdcZjjgHz(bdcZjQO);
    }

    /**
     * 批量生成质检
     *
     * @param bdcPlZjDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcZjdDO plCreateBdcZj(BdcPlZjDTO bdcPlZjDTO) {
        if(CollectionUtils.isEmpty(bdcPlZjDTO.getXmids())){
            throw new MissingArgumentException("批量生成质检错误，请选择要生成的数据!");
        }
        UserDto userDto = this.userManagerUtils.getCurrentUser();
        //生成质检单
        BdcZjdDO bdcZjdDO = new BdcZjdDO();
        bdcZjdDO.setZjdid(UUIDGenerator.generate16());
        String zjdbh = bdcBhFeignService.queryCommonBh(Constants.BDC_ZJD_BH, CommonConstantUtils.ZZSJFW_DAY, 6, "");
        bdcZjdDO.setZjdbh(zjdbh);
        bdcZjdDO.setZjdcjsj(new Date());
        if(Objects.nonNull(userDto)){
            bdcZjdDO.setZjr(userDto.getUsername());
        }
        entityMapper.insertSelective(bdcZjdDO);
        //
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(bdcPlZjDTO.getXmids());
        if(CollectionUtils.isEmpty(bdcXmDOS)){
            throw new MissingArgumentException("批量生成质检错误，未找到对应项目数据!");
        }
        //生成质检明细
        bdcXmDOS.forEach(bdcXmDO -> {
            String zjbh = bdcBhFeignService.queryCommonBh(Constants.BDC_ZJ_BH, CommonConstantUtils.ZZSJFW_DAY, 6, "");
            BdcZjDO bdcZjDO  = new BdcZjDO();
            BeanUtils.copyProperties(bdcXmDO, bdcZjDO);
            bdcZjDO.setZjdid(bdcZjdDO.getZjdid());
            bdcZjDO.setLcmc(bdcXmDO.getGzldymc());
            bdcZjDO.setZjbh(zjbh);
            if(Objects.nonNull(userDto)){
                bdcZjDO.setHdr(userDto.getAlias());
            }
            bdcZjDO.setZjsj(new Date());
            saveBdcZjDO(bdcZjDO);
        });
        return bdcZjdDO;
    }

    /**
     * 批量质检质检单详情
     *
     * @param zjdbh
     */
    @Override
    public BdcPlZjxxDTO plZjInfo(String zjdbh) {
        BdcPlZjxxDTO bdcPlZjxxDTO = new BdcPlZjxxDTO();
        //查询质检单
        Example example = new Example(BdcZjdDO.class);
        example.createCriteria().andEqualTo("zjdbh",zjdbh);
        List<BdcZjdDO> bdcZjdList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcZjdList)){
            BdcZjdDO bdcZjdDO = bdcZjdList.get(0);
            //查询质检单的明细
            BdcZjQO bdcZjQO = new BdcZjQO();
            bdcZjQO.setZjdid(bdcZjdDO.getZjdid());
            List<BdcZjXmxxDTO> bdcZjXmxxDTOS = queryBdcZjjgHzxx(bdcZjQO);
            bdcPlZjxxDTO.setBdcZjdDO(bdcZjdDO);
            bdcPlZjxxDTO.setBdcZjDOList(bdcZjXmxxDTOS);
        }
        return bdcPlZjxxDTO;
    }

    /**
     * 根据质检ID删除质检明细信息
     */
    private void removeZjmxByZjid(String zjid){
        Example example = new Example(BdcZjMxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("zjid", zjid);
        entityMapper.deleteByExample(example);
    }


    public void modifyZjdZtByCondition(String zjdid) {
        if(StringUtils.isBlank(zjdid)){
            return;
        }
        BdcZjdDO bdcZjdDO = new BdcZjdDO();
        bdcZjdDO.setZjdid(zjdid);
        //查询质检单的明细
        Example example = new Example(BdcZjDO.class);
        example.createCriteria().andEqualTo("zjdid",bdcZjdDO.getZjdid());
        List<BdcZjDO> bdcZjList = entityMapper.selectByExample(example);
        //
        boolean allMatch = bdcZjList.stream().allMatch(bdcZjDO -> Objects.nonNull(bdcZjDO.getZjzt()) && (!bdcZjDO.getZjzt().equals(0)));
        if(allMatch){
            bdcZjdDO.setZjzt(CommonConstantUtils.ZJZT_YZJ);
            this.entityMapper.updateByPrimaryKeySelective(bdcZjdDO);
        }
    }
}
