package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.domain.engine.*;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.engine.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzLcZhgzGxQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzCheckRelatedQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.core.mapper.BdcGzZhGzMapper;
import cn.gtmap.realestate.engine.core.service.BdcGzGxService;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzService;
import cn.gtmap.realestate.engine.core.service.BdcGzZhGzService;
import cn.gtmap.realestate.engine.util.ResourceUtil;
import com.alibaba.fastjson.JSON;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/5
 * @description
 */
@Service
public class  BdcGzZhGzServiceImpl implements BdcGzZhGzService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzZhGzServiceImpl.class);

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcGzZhGzMapper bdcGzZhGzMapper;
    @Autowired
    private BdcGzGxService bdcGzGxService;
    @Autowired
    private BdcGzZgzService bdcGzZgzService;
    @Autowired
    private ProcessDefinitionClient processDefinitionClient;
    @Autowired
    BdcGzZgzServiceImpl bdcGzZgzFeignService;
    @Autowired
    BdcGzGxServiceImpl bdcGzGxFeignService;

    /**
     * 文件资源处理
     */
    @Autowired
    private ResourceUtil resourceUtil;

    /**
     * 缓存基本子规则信息
     * key  : 子规则ID
     * value: 子规则名称
     */
    private static Map<String, String> baseZgzMap = new HashMap<>(50);
    private static List<String> baseZgzNameList = new ArrayList<>(50);


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取基本规则信息
     */
    @PostConstruct
    public void init() {
        // 1、获取基础子规则（固化在文件中）
        String  content = resourceUtil.getBaseZgzContent();
        if(StringUtils.isBlank(content)){
            return;
        }

        // 2、转换为实体对象，获取ID、名称信息
        List<BdcGzZgzDTO> bdcGzZgzDTOList = JSON.parseArray(content, BdcGzZgzDTO.class);
        if(CollectionUtils.isNotEmpty(bdcGzZgzDTOList)){
            bdcGzZgzDTOList.stream().forEach(bdcGzZgzDTO -> {
                baseZgzMap.put(bdcGzZgzDTO.getGzid(), bdcGzZgzDTO.getGzmc());
                baseZgzNameList.add(bdcGzZgzDTO.getGzmc());
            });
        }
    }

    /**
     * @param pageable
     * @param map
     * @return list<BdcGzZhGz>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 分页查询不动产规则组合规则信息
     */
    @Override
    public Page<BdcGzZhgzDO> listBdcXzLwByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listBdcGzZhGzByPage", map, pageable);
    }

    /**
     * @param  zhidList 组合id集合
     * @param  gzid 子规则id
     * @return int 新增关联关系记录数
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     * @description 子规则关联批量组合规则
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int glgx(String[] zhidList, String gzid){
        int count = 0;
        //删除所有关联关系，再新增；
        bdcGzGxService.delBdcGzGxByGzid(gzid);
        for(String zhid : zhidList){
            if (StringUtils.isBlank(zhid)) {
                continue;
            }
            BdcGzGxDO bdcGzGxDO = new BdcGzGxDO();
            bdcGzGxDO.setZhid(zhid);
            bdcGzGxDO.setGzid(gzid);
            bdcGzGxService.insertBdcGzGx(bdcGzGxDO);
            count++;
        }
        return count;
    }


    /**
     * @param bdcGzZhgzDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增保存bdcGzZhGz信息
     */
    @Override
    public BdcGzZhgzDO insertBdcGzZhGz(BdcGzZhgzDO bdcGzZhgzDO) {
        if(bdcGzZhgzDO!=null){
            if(StringUtils.isBlank(bdcGzZhgzDO.getZhid())){
                bdcGzZhgzDO.setZhid(UUIDGenerator.generate());
            }
            entityMapper.insertSelective(bdcGzZhgzDO);
        }
        return bdcGzZhgzDO;
    }

    /**
     * @param bdcGzZhgzDO
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 更新BdcGzZhGzDO信息
     */
    @Override
    public int updateBdcGzZhGz(BdcGzZhgzDO bdcGzZhgzDO) {
        if (bdcGzZhgzDO == null || StringUtils.isBlank(bdcGzZhgzDO.getZhid())) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return entityMapper.updateByPrimaryKeySelective(bdcGzZhgzDO);
    }

    /**
     * @param bdcGzZhgzDO
     * @return list<BdcGzZhGzDO>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 查询不动产规则组合规则
     */
    @Override
    public List<BdcGzZhgzDO> queryBdcGzZhGzList(BdcGzZhgzDO bdcGzZhgzDO) {
        return entityMapper.selectByObj(bdcGzZhgzDO);
    }

    /**
     * @param zhid
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过组合id删除bdcGzZhGz记录
     */
    @Override
    public void delBdcGzZhGz(String zhid) {
        if(StringUtils.isNotBlank(zhid)){
            entityMapper.deleteByPrimaryKey(BdcGzZhgzDO.class,zhid);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs   组合标识
     * @return {BdcGzZhgzDO} 组合规则
     * @description 获取组合规则
     */
    @Override
    public BdcGzZhgzDO getBdcGzZhgzDO(String zhbs) {
        if(StringUtils.isBlank(zhbs)){
            return null;
        }

        Example example = new Example(BdcGzZhgzDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("zhbs", zhbs);

        List<BdcGzZhgzDO> bdcGzZhgzDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcGzZhgzDOList)){
            return bdcGzZhgzDOList.get(0);
        }
        return null;
    }

    /**
     * 查询组合标识是否唯一
     * @param bdcGzZhgzDO bdcGzZhgzDO
     * @return int 条数
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public int countZhbs(BdcGzZhgzDO bdcGzZhgzDO) {
        if (bdcGzZhgzDO == null) {
            throw new NullPointerException("查询对象不可为空！");
        }
        return bdcGzZhGzMapper.countZhbs(bdcGzZhgzDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhGzQO 验证查询参数
     * @return {List} 组合标识集合
     * @description 查询组合规则标识信息
     */
    @Override
    public List<String> listBdcGzZhgzBs(BdcGzZhGzQO bdcGzZhGzQO) {
        if(null == bdcGzZhGzQO){
            return Collections.emptyList();
        }

        return bdcGzZhGzMapper.listBdcGzZhgzBs(bdcGzZhGzQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLcZhgzGxDO 流程规则关系
     * @return 操作数据记录数
     * @description 保存流程和组合规则对照关系
     */
    @Override
    public int saveBdcLcZhgzGx(BdcGzLcZhgzGxDO bdcGzLcZhgzGxDO) {
        if(null == bdcGzLcZhgzGxDO || StringUtils.isBlank(bdcGzLcZhgzGxDO.getLcbs()) || StringUtils.isBlank(bdcGzLcZhgzGxDO.getZhgzbs())){
            throw new NullPointerException("不动产子规则：保存流程与组合规则关系配置参数为空！");
        }

        if(StringUtils.isBlank(bdcGzLcZhgzGxDO.getId())){
            bdcGzLcZhgzGxDO.setId(UUIDGenerator.generate());
        }

        // 验证流程是否已经存在和规则匹配关系
        Example example = new Example(BdcGzLcZhgzGxDO.class);
        example.createCriteria().andEqualTo("lcbs", bdcGzLcZhgzGxDO.getLcbs());
        List<BdcGzLcZhgzGxDO> bdcGzLcZhgzGxDOList = entityMapper.selectByExample(example);

        /// 已经存在则更新流程对应规则
        if(CollectionUtils.isNotEmpty(bdcGzLcZhgzGxDOList)){
            BdcGzLcZhgzGxDO lcZhgzGxDO = bdcGzLcZhgzGxDOList.get(0);
            lcZhgzGxDO.setZhgzbs(bdcGzLcZhgzGxDO.getZhgzbs());
            lcZhgzGxDO.setZhgzmc(bdcGzLcZhgzGxDO.getZhgzmc());
            return entityMapper.updateByPrimaryKey(lcZhgzGxDO);
        }
        /// 不存在新增
        return entityMapper.insert(bdcGzLcZhgzGxDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLcZhgzGxDOList 待删除记录
     * @return 删除记录数
     * @description 删除流程和组合规则对照关系信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBdcLcZhgzGx(List<BdcGzLcZhgzGxDO> bdcGzLcZhgzGxDOList) {
        if(CollectionUtils.isEmpty(bdcGzLcZhgzGxDOList)){
            return 0;
        }

        int count = 0;
        for (BdcGzLcZhgzGxDO bdcGzLcZhgzGxDO : bdcGzLcZhgzGxDOList){
            count += entityMapper.delete(bdcGzLcZhgzGxDO);
        }
        return count;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable        分页对象
     * @param bdcGzLcZhgzGxQO 查询条件
     * @return 流程和规则关系列表
     * @description 查询流程和组合规则对照关系信息
     */
    @Override
    public Page<BdcGzLcZhgzGxDO> listBdcLcZhgzGx(Pageable pageable, BdcGzLcZhgzGxQO bdcGzLcZhgzGxQO) {
        return repo.selectPaging("listBdcLcZhgzGxByPageOrder", bdcGzLcZhgzGxQO, pageable);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param lcbs 流程标识
     * @return 组合规则标识
     * @description 根据流程标识查询匹配的组合规则标识
     */
    @Override
    public String getZhgzbsByLcbs(String lcbs) {
        if(StringUtils.isBlank(lcbs)){
            return null;
        }

        Example example = new Example(BdcGzLcZhgzGxDO.class);
        example.createCriteria().andEqualTo("lcbs", lcbs);
        List<BdcGzLcZhgzGxDO> bdcGzLcZhgzGxDOList = entityMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(bdcGzLcZhgzGxDOList)){
            return null;
        }
        return bdcGzLcZhgzGxDOList.get(0).getZhgzbs();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String copyBdcGzZhgz(String zhid) {
        //查询原有组合规则信息
        BdcGzZhgzDO bdcGzZhgzDO = this.entityMapper.selectByPrimaryKey(BdcGzZhgzDO.class, zhid);
        if(StringUtils.isEmpty(bdcGzZhgzDO.getZhid())){
            throw new NullPointerException("未获取到组合规则信息！");
        }
        //复制组合规则信息，创建新的规则内容
        bdcGzZhgzDO.setZhid(null);
        bdcGzZhgzDO.setPzrq(new Date());
        final String zhbs = bdcGzZhgzDO.getZhbs();
        if(StringUtils.isEmpty(zhbs)){
            throw new NullPointerException("未获取到组合规则的组合标识信息！");
        }
        //创建新的组合标识：1、XXXX_XXX格式的,保留‘_XXX’内容，替换‘_’前标识符。2、其余格式直接替换为16位编码
        if(zhbs.indexOf("_")>-1){
            bdcGzZhgzDO.setZhbs(UUIDGenerator.generate16().concat(zhbs.substring(zhbs.indexOf("_"))));
        }else{
            bdcGzZhgzDO.setZhbs(UUIDGenerator.generate16());
        }
        final String newZhid = this.insertBdcGzZhGz(bdcGzZhgzDO).getZhid();

        //查询原有的组合关系内容
        final List<BdcGzGxDO> bdcGzGxDOList = this.bdcGzGxService.queryBdcGzGxListByZhid(zhid);
        //复制组合关系内容
        for(BdcGzGxDO entity : bdcGzGxDOList){
            BdcGzGxDO bdcGzGxDO = new BdcGzGxDO();
            bdcGzGxDO.setZhid(newZhid);
            bdcGzGxDO.setGzid(entity.getGzid());
            this.bdcGzGxService.insertBdcGzGx(bdcGzGxDO);
        }
        return newZhid;
    }

    /**
     * @author: <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @param: String zhgzJSON
     * @return: BdcCommonResponse
     * @description 导入组合规则
     */
    @Override
    public BdcCommonResponse importZhgz(String zhgzJSON) {
        if(StringUtils.isBlank(zhgzJSON)){
            throw new AppException("组合规则导入出错，组合规则文件内容为空!");
        }

        // 步骤1：从文件中读取BdcGzZhgzDTO对象
        BdcGzZhgzDTO bdcGzZhgzDTO = JSON.parseObject(zhgzJSON,BdcGzZhgzDTO.class);
        if(null == bdcGzZhgzDTO){
            throw new AppException("组合规则导入出错，组合规则文件内容为空!");
        }

        //步骤2：重置DTO部分信息;
        String zhid = UUIDGenerator.generate16();
        bdcGzZhgzDTO.setZhid(zhid);
        bdcGzZhgzDTO.setPzrq(new Date());

        //步骤3：获取组合规则信息zhbs
        String zhbs = bdcGzZhgzDTO.getZhbs();
        if(StringUtils.isBlank(zhbs)){
            return BdcCommonResponse.fail("组合规则导入出错，缺少组合规则标识!");
        }

        //步骤4：获取重置后的组合规则DO对象；
        BdcGzZhgzDO bdcGzZhgzDO = new BdcGzZhgzDO();
        bdcGzZhgzDO.setZhid(bdcGzZhgzDTO.getZhid());
        bdcGzZhgzDO.setPzrq(bdcGzZhgzDTO.getPzrq());
        bdcGzZhgzDO.setZhbs(zhbs);
        bdcGzZhgzDO.setZhmc(bdcGzZhgzDTO.getZhmc());
        bdcGzZhgzDO.setZhsm(bdcGzZhgzDTO.getZhsm());

        //步骤5：判断库中是否有相同的zhbs；
        int zhbsFlag = this.countZhbs(bdcGzZhgzDO);
        if (zhbsFlag == 0){
            //库中没有相同的zhbs时，组合规则信息直接入库;
            entityMapper.insertSelective(bdcGzZhgzDO);
        } else {
            //库中有相同的zhbs时，从库中查询已存在的组合规则信息，以覆盖之前新建的组合规则信息；
            List<String> zhbsList = new ArrayList<>();
            zhbsList.add(zhbs);
            List<BdcGzZhgzDO> bdcGzZhgzDOS = bdcGzZhGzMapper.listZhGzByZhBsList(zhbsList);
            bdcGzZhgzDO = bdcGzZhgzDOS.get(0);
            //更新组合规则DTO的zhid,方便建立规则关联关系；
            bdcGzZhgzDTO.setZhid(bdcGzZhgzDO.getZhid());
        }

        //步骤6：获取导入文件中的子规则DTO集合
        List<BdcGzZgzDTO> bdcGzZgzDTOList= bdcGzZhgzDTO.getBdcGzZgzDTOList();
        //6.1 子规则集合为空
        if(CollectionUtils.isEmpty(bdcGzZgzDTOList)){
            return BdcCommonResponse.ok("组合规则导入成功，文件中没有子规则!");
        }

        //6.2 子规则集合不为空,判断每条子规则是否存在库中，不存在的话新增子规则并入库；
        bdcGzZgzDTOList.stream().forEach(bdcGzZgzDTO -> {
            String zgzid = bdcGzZgzDTO.getGzid();
            if (StringUtils.isBlank(zgzid) || bdcGzZgzService.getZgz(zgzid) == null){
                if (StringUtils.isBlank(zgzid)){
                    zgzid = UUIDGenerator.generate16();
                    bdcGzZgzDTO.setGzid(zgzid);
                }

                bdcGzZgzDTO.setPzrq(new Date());

                //重置子规则关联的表达式、提示信息集合的gzid
                List<BdcGzBdsTsxxDO> bdsList = bdcGzZgzDTO.getBdcGzBdsTsxxDOList();
                String finalZgzid = zgzid;
                if(CollectionUtils.isNotEmpty(bdsList)){
                    bdsList.forEach(bdsTsxxDO -> bdsTsxxDO.setGzid(finalZgzid));
                }

                //重置子规则关联的数据流集合的gzid
                List<BdcGzSjlDTO> sjlDTOList = bdcGzZgzDTO.getBdcGzSjlDTOList();
                if(CollectionUtils.isNotEmpty(sjlDTOList)){
                    sjlDTOList.forEach(sjlDTO -> sjlDTO.setGzid(finalZgzid));
                }

                //保存子规则及关联的数据流、表达式等信息
                bdcGzZgzFeignService.saveBdcGzZgz(bdcGzZgzDTO);
            }
        });

        //步骤7：判断规则关联关系是否存在，不存在则新增关联
        bdcGzZgzDTOList.stream().forEach(bdcGzZgzDTO -> {
            BdcGzGxDO bdcGzGxDO = new BdcGzGxDO();
            bdcGzGxDO.setGzid(bdcGzZgzDTO.getGzid());
            bdcGzGxDO.setZhid(bdcGzZhgzDTO.getZhid());
            if (!bdcGzZgzService.queryBdcGzGx(bdcGzGxDO)){
                bdcGzGxService.insertBdcGzGx(bdcGzGxDO);
            }
        });

        return BdcCommonResponse.ok("组合规则导入成功");
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证项
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    @Override
    public List<BdcGzZhgzDO> listBdcGzZhgzQzyz() {
        Example example = new Example(BdcGzZhgzDO.class);
        example.createCriteria().andLike("zhbs", "_QZYZ");
        return entityMapper.selectByExample(example);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证信息（包括关联的关联关系、子规则）
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    @Override
    public BdcGzQzyzDTO listBdcGzQzyzDTO() {
        // 获取强制验证的组合规则
        List<BdcGzZhgzDO> bdcGzZhgzDOList = this.listBdcGzZhgzQzyz();
        if(CollectionUtils.isEmpty(bdcGzZhgzDOList)) {
            throw new AppException("不存在强制验证组合规则！");
        }

        BdcGzQzyzDTO bdcGzQzyzDTO = new BdcGzQzyzDTO();
        bdcGzQzyzDTO.setBdcGzZhgzDOList(bdcGzZhgzDOList);

        // 获取组合规则和子规则关联关系
        List<BdcGzGxDO> bdcGzGxDOList = bdcGzZhGzMapper.listBdcGzQzyzGx();
        if(CollectionUtils.isEmpty(bdcGzGxDOList)) {
            throw new AppException("强制验证没有关联子规则！");
        }
        bdcGzQzyzDTO.setBdcGzGxDOList(bdcGzGxDOList);

        // 获取关联的子规则信息
        Set<String> gzidSet = new HashSet<>(20);
        List<BdcGzZgzDTO> zgzList = new ArrayList<>(10);
        for(BdcGzZhgzDO bdcGzZhgzDO : bdcGzZhgzDOList){
            List<BdcGzZgzDTO> list = bdcGzZgzService.queryBdcGzZgzDTOListByZhid(bdcGzZhgzDO.getZhid());
            if(CollectionUtils.isNotEmpty(list)) {
                for (BdcGzZgzDTO zgzdto : list) {
                    if (null != zgzdto && !gzidSet.contains(zgzdto.getGzid())) {
                        gzidSet.add(zgzdto.getGzid());
                        zgzList.add(zgzdto);
                    }
                }
            }
        }
        if(CollectionUtils.isEmpty(zgzList)) {
            throw new AppException("强制验证不存在对应的子规则！");
        }
        bdcGzQzyzDTO.setBdcGzZgzDTOList(zgzList);

        return bdcGzQzyzDTO;
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcGzQzyzYzDTO} 验证结果
     * @description 验证强制验证配置内容
     *
     * 强制验证项组合规则标识：流程标识_QZYZ
     */
    @Override
    public BdcGzQzyzYzDTO checkQzyz() {
        // 1、验证强制验证内容是否存在
        BdcGzQzyzDTO bdcGzQzyzDTO = this.listBdcGzQzyzDTO();
        if(null == bdcGzQzyzDTO || CollectionUtils.isEmpty(bdcGzQzyzDTO.getBdcGzZhgzDOList())
                || CollectionUtils.isEmpty(bdcGzQzyzDTO.getBdcGzGxDOList())){
            return new BdcGzQzyzYzDTO("1", "验证未通过", "未配置强制验证内容，无法导出！");
        }

        List<ProcessDefData> proList = processDefinitionClient.getAllProcessDefData();
        if(CollectionUtils.isEmpty(proList)){
            return new BdcGzQzyzYzDTO("2", "验证未通过", "未获取到流程信息，请重试！");
        }

        // 2、验证不同的流程是否都配置了强制验证内容
        List<String> notSetQzyzProList = new ArrayList<>();
        List<BdcGzZhgzDO> zhgzDOList = bdcGzQzyzDTO.getBdcGzZhgzDOList();
        Map<String, String> zhbsMap = zhgzDOList.stream().collect(Collectors.toMap(BdcGzZhgzDO::getZhbs, BdcGzZhgzDO::getZhmc));
        proList.stream().forEach(processDefData -> {
            if(!zhbsMap.containsKey(processDefData.getProcessDefKey() + CommonConstantUtils.LC_ZHGZBS_QZYZ)){
                notSetQzyzProList.add(processDefData.getName());
            }
        });

        // 3、验证每个流程是否配置了必要子规则
        // 未设置基本子规则的强制验证流程集合
        Map<String, List<String>> notSetBaseZgzProMap = new HashMap<>(100);
        if(MapUtils.isNotEmpty(baseZgzMap)){
            // 从组合规则和子规则关系中，分组每个强制验证对应的子规则关系
            Map<String, List<BdcGzGxDO>> gxMap = bdcGzQzyzDTO.getBdcGzGxDOList().stream().collect(Collectors.groupingBy(BdcGzGxDO::getZhid));

            zhgzDOList.stream().forEach(qzyzZhgzDTO -> {
                // 获取单个强制验证组合规则关联的子规则信息
                List<BdcGzGxDO> bdcGzGxDOList = gxMap.get(qzyzZhgzDTO.getZhid());
                if (CollectionUtils.isNotEmpty(bdcGzGxDOList)) {
                    Map<String, BdcGzGxDO> qzyzZgzMap = bdcGzGxDOList.stream().collect(Collectors.toMap(BdcGzGxDO::getGzid, bdcGzGxDO -> bdcGzGxDO));

                    // 判断获取单个强制验证组合规则没有关联的基本子规则信息
                    List<String> baseZgzList = new ArrayList<>(baseZgzMap.size());
                    for(Map.Entry<String, String> entry : baseZgzMap.entrySet()){
                        if(!qzyzZgzMap.containsKey(entry.getKey())){
                            baseZgzList.add(entry.getValue());
                        }
                    }
                    notSetBaseZgzProMap.put(qzyzZhgzDTO.getZhmc(), baseZgzList);
                } else {
                    // 当前强制验证组合规则没有子规则，那么肯定不包含所有的固化子规则
                    notSetBaseZgzProMap.put(qzyzZhgzDTO.getZhmc(), baseZgzNameList);
                }
            });
        }

        // 4、结果处理
        if(CollectionUtils.isNotEmpty(notSetQzyzProList) ||  MapUtils.isNotEmpty(notSetBaseZgzProMap)){
            BdcGzQzyzYzDTO bdcGzQzyzYzDTO = new BdcGzQzyzYzDTO("3", "以下流程未配置强制验证，是否继续导出", "");
            bdcGzQzyzYzDTO.setProList(notSetQzyzProList);
            bdcGzQzyzYzDTO.setQzyzMap(notSetBaseZgzProMap);
            return bdcGzQzyzYzDTO;
        }
        return new BdcGzQzyzYzDTO("0", "验证通过", "");
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param bdcGzZhGzQO  组合规则参数信息
     * @description 分页获取组合规则关联的子规则信息
     */
    @Override
    public Page<BdcGzZgzDO> listBdcZhgzZgzPage(Pageable pageable, BdcGzZhGzQO bdcGzZhGzQO) {
        return repo.selectPaging("listZhgzZgzByPageOrder", bdcGzZhGzQO, pageable);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs 组合标识
     * @description 根据组合标识获取组合规则
     *
     *  业务流程选择不动产单元创建项目、流程转发时候进行强制规则验证
     *  一个验证设置的普通子规则；
     *  二个强制验证：
     *    A、如果配置了当前流程关联的强制验证组合规则 则针对设置的高级规则进行验证；
     *    B、如果没有设置当前流程对应强制验证组合规则，则默认对所有高级子规则验证；
     */
    @Override
    public BdcGzZhgzDTO listBdcZgzByZhbs(String zhbs){
        if(this.isNeedQzyz(zhbs)) {
            // 1、获取组合规则基本信息
            BdcGzZhgzDO bdcGzZhgzDO = this.getBdcGzZhgzDO(zhbs);

            // 2、没配置直接用强制验证
            if(null == bdcGzZhgzDO || StringUtils.isBlank(bdcGzZhgzDO.getZhid())){
                return this.generateQzyzZhgz(zhbs);
            }

            // 3、获取普通子规则集合
            List<BdcGzZgzDTO> bdcGzZgzDTOList = bdcGzZgzService.queryBdcGzZgzDTOListByZhid(bdcGzZhgzDO.getZhid());
            if(CollectionUtils.isEmpty(bdcGzZgzDTOList)){
                bdcGzZgzDTOList = new ArrayList<>(10);
            }

            // 4、加上强制验证的规则
            bdcGzZgzDTOList.addAll(this.getQzyzZgzList(zhbs));
            if(CollectionUtils.isEmpty(bdcGzZgzDTOList)){
                LOGGER.error("未获取到对应的组合规则且没有强制验证规则：{}", zhbs);
                throw new AppException(ErrorCode.RULE_CHECK_ZGZ_EX, "获取组合规则验证结果中止，原因：无子规则且无强制验证规则！");
            }
            // 去重
            bdcGzZgzDTOList = this.distinctZgzList(bdcGzZgzDTOList);

            // 5、处理返回结果
            BdcGzZhgzDTO bdcGzZhgzDTO = new BdcGzZhgzDTO();
            bdcGzZhgzDTO.setBdcGzZgzDTOList(bdcGzZgzDTOList);
            BeanUtils.copyProperties(bdcGzZhgzDO, bdcGzZhgzDTO);
            return bdcGzZhgzDTO;
        } else {
            // 非创建项目、流程转发场景只需要验证配置的普通规则
            // 1、获取组合规则
            BdcGzZhgzDO bdcGzZhgzDO = this.getBdcGzZhgzDO(zhbs);
            if(null == bdcGzZhgzDO || StringUtils.isBlank(bdcGzZhgzDO.getZhid())){
                LOGGER.error("未获取到对应的组合规则：{}", zhbs);
                throw new AppException(ErrorCode.RULE_CHECK_EX, "获取组合规则验证结果中止，原因：未获取到对应的组合规则！");
            }

            // 2、获取子规则集合
            List<BdcGzZgzDTO> bdcGzZgzDTOList = bdcGzZgzService.queryBdcGzZgzDTOListByZhid(bdcGzZhgzDO.getZhid());
            if(CollectionUtils.isEmpty(bdcGzZgzDTOList)){
                LOGGER.error("{}：未获取到验证的子规则", zhbs);
                throw new AppException(ErrorCode.RULE_CHECK_ZGZ_EX, "获取组合规则验证结果中止，原因：未获取到验证的子规则！");
            }

            BdcGzZhgzDTO bdcGzZhgzDTO = new BdcGzZhgzDTO();
            bdcGzZhgzDTO.setBdcGzZgzDTOList(bdcGzZgzDTOList);
            BeanUtils.copyProperties(bdcGzZhgzDO, bdcGzZhgzDTO);
            return bdcGzZhgzDTO;
        }
    }

    @Override
    public  BdcGzZhGzCheckRelatedDTO checkRelated(BdcGzZhGzCheckRelatedQO bdcGzZhGzCheckRelatedQO) {

        Map<String,String> map = new ConcurrentHashMap<>(16);
        map.put("_QZYZ","强制验证");
        map.put("_LCZF","流程转发");
        map.put("_XZBDCDY","新建项目");
        map.put("_DBYZ","登簿验证");
        map.put("_LCTH","流程退回");
        map.put("_CJYZ","创建验证");

        // 获取所有流程
        List<ProcessDefData> processDefDataList = processDefinitionClient.getAllProcessDefData();
        List<String> params = new ArrayList<>();
        bdcGzZhGzCheckRelatedQO.getZhBsList().forEach(zhBs -> {
            processDefDataList.forEach(processDefData -> {
                StringBuffer sb = new StringBuffer();
                if(StringUtils.equals("ZHCX_",zhBs)){
                    sb.append(zhBs);
                    sb.append(processDefData.getProcessDefKey());
                }else{
                    sb.append(processDefData.getProcessDefKey());
                    sb.append(zhBs);
                }
                params.add(sb.toString());
            });
        });

        // 根据验证场景查询规则组合
        List<BdcGzZhgzDO> bdcGzZhgzDOS = bdcGzZhGzMapper.listZhGzByZhBsList(params);
        List<String> zhBsList = bdcGzZhgzDOS.stream().map(BdcGzZhgzDO::getZhbs).collect(toList());
        List<String> reduce = params.stream().filter(item -> !zhBsList.contains(item)).collect(toList());

        Map<String,ProcessDefData> processDefDataMap = processDefDataList.stream().collect(Collectors.toMap(ProcessDefData::getProcessDefKey, v -> v , (v1, v2) -> v2));

        List<BdcGzZhGzYzCjCheckDTO> bdcGzZhGzYzCjCheckDTOList = new ArrayList<>();

        for(String zhBs : bdcGzZhGzCheckRelatedQO.getZhBsList()){
            BdcGzZhGzYzCjCheckDTO bdcGzZhGzYzCjCheckDTO  = new BdcGzZhGzYzCjCheckDTO();
            List<ProcessDefData> processDefDatas = new ArrayList<>();
            for(String data : reduce){
                if(StringUtils.contains(data,zhBs)){

                    String[] dataArr = data.split("_");
                    String processDefKey;
                    if(StringUtils.equals("ZHCX_",zhBs)){
                        processDefKey = dataArr[1];
                    }else{
                        processDefKey = dataArr[0];
                    }
                    processDefDatas.add(processDefDataMap.get(processDefKey));
                }
            }
            bdcGzZhGzYzCjCheckDTO.setZhbs(zhBs);
            bdcGzZhGzYzCjCheckDTO.setZhmc(map.get(zhBs));
            bdcGzZhGzYzCjCheckDTO.setProcessDefDataList(processDefDatas);
            bdcGzZhGzYzCjCheckDTOList.add(bdcGzZhGzYzCjCheckDTO);
        }

        // 获取所有未关联子规则的组合规则
        List<BdcGzZhgzDO> zhgzDOList = bdcGzZhGzMapper.listZhGzNotReleatedZgz();

        BdcGzZhGzCheckRelatedDTO bdcGzZhGzCheckRelatedDTO = new BdcGzZhGzCheckRelatedDTO();

        bdcGzZhGzCheckRelatedDTO.setBdcGzZhgzDOList(zhgzDOList);
        bdcGzZhGzCheckRelatedDTO.setBdcGzZhGzYzCjCheckDTOList(bdcGzZhGzYzCjCheckDTOList);
        return bdcGzZhGzCheckRelatedDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs 组合标识
     * @return {boolean} true : 需要 ； false：不需要
     * @description  判断是否需要强制验证
     *
     *   根据与业务子系统约定，利用组合规则后缀标识判断是否是创建项目、流程转发场景
     *   增加匹配单元号和土地证的强制验证
     */
    private boolean isNeedQzyz(String zhbs) {
        return zhbs.endsWith(CommonConstantUtils.LC_ZHGZBS_XZBDCDY)
                || zhbs.endsWith(CommonConstantUtils.LC_ZHGZBS_LCZF)
                || zhbs.endsWith(CommonConstantUtils.LC_ZHGZBS_WLZS)
                || CommonConstantUtils.LC_ZHGZBS_PPDYH.equals(zhbs);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs 组合标识
     * @description  生成虚拟强制验证组合规则
     */
    private BdcGzZhgzDTO generateQzyzZhgz(String zhbs) {
        List<BdcGzZgzDTO> qzyzList = this.getQzyzZgzList(zhbs);
        if(CollectionUtils.isEmpty(qzyzList)){
            LOGGER.error("无组合规则且无强制验证规则：{}", zhbs);
            throw new AppException(ErrorCode.RULE_CHECK_EX, "获取组合规则验证结果中止，原因：无组合规则且无强制验证规则！");
        }

        BdcGzZhgzDTO zhgzDTO = new BdcGzZhgzDTO();
        zhgzDTO.setZhbs(zhbs);
        zhgzDTO.setZhmc("流程" + zhbs + "规则验证");
        zhgzDTO.setBdcGzZgzDTOList(qzyzList);
        return zhgzDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs 组合标识
     * @description  获取强制验证关联的子规则
     *
     *   说明：对于选择不动产单元准备创建项目时候、流程转发时候，判断当前流程是否关联了强制验证；
     *        如果关联了则按照设置的强制验证验证，没有的话则默认执行所有的强制验证；
     */
    private List<BdcGzZgzDTO> getQzyzZgzList(String zhbs){
        String content = resourceUtil.getQzyzFileContent();
        if(StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }

        BdcGzQzyzDTO bdcGzQzyzDTO = JSON.parseObject(content, BdcGzQzyzDTO.class);
        if(null == bdcGzQzyzDTO || CollectionUtils.isEmpty(bdcGzQzyzDTO.getBdcGzZgzDTOList())){
            return Collections.emptyList();
        }

        // 根据强制验证的组合规则标识获取对应的组合规则ID
        String zhid = "";
        String qzyzZhbs = this.getQzyzZhbs(zhbs);
        for(BdcGzZhgzDO zhgzDO : bdcGzQzyzDTO.getBdcGzZhgzDOList()){
            if(null != zhgzDO && StringUtils.equals(qzyzZhbs, zhgzDO.getZhbs())){
                zhid = zhgzDO.getZhid();
                break;
            }
        }

        if(StringUtils.isBlank(zhid)){
            // 没有设置则执行所有强制验证子规则
            return bdcGzQzyzDTO.getBdcGzZgzDTOList();
        }

        // 获取强制验证的组合规则关联的子规则ID
        List<String> gzidList = new ArrayList<>(5);
        for(BdcGzGxDO bdcGzGxDO : bdcGzQzyzDTO.getBdcGzGxDOList()){
            if(StringUtils.equals(zhid, bdcGzGxDO.getZhid())){
                gzidList.add(bdcGzGxDO.getGzid());
            }
        }
        if(CollectionUtils.isEmpty(gzidList)) {
            // 没有设置则执行所有强制验证子规则
            return bdcGzQzyzDTO.getBdcGzZgzDTOList();
        }

        // 设置了则验证指定强制验证子规则
        List<BdcGzZgzDTO> qzyzZgzList = new ArrayList<>(10);
        for(String gzid : gzidList){
            for(BdcGzZgzDTO zgz : bdcGzQzyzDTO.getBdcGzZgzDTOList()){
                if(null != zgz && StringUtils.equals(gzid, zgz.getGzid())){
                    qzyzZgzList.add(zgz);
                }
            }
        }
        return qzyzZgzList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 子规则集合
     * @description  子规则集合去重
     */
    private List<BdcGzZgzDTO> distinctZgzList(List<BdcGzZgzDTO> bdcGzZgzDTOList) {
        Set<String> zgzSet = new HashSet<>(bdcGzZgzDTOList.size());

        for(BdcGzZgzDTO bdcGzZgzDTO : bdcGzZgzDTOList) {
            zgzSet.add(JSON.toJSONString(bdcGzZgzDTO));
        }

        List<BdcGzZgzDTO> result = new ArrayList<>(zgzSet.size());
        for(String json : zgzSet) {
            result.add(JSON.parseObject(json, BdcGzZgzDTO.class));
        }
        return result;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhbs 原组合标识
     * @description  获取强制验证组合规则标识
     */
    private String getQzyzZhbs(String zhbs) {
        String str;
        if(zhbs.endsWith(CommonConstantUtils.LC_ZHGZBS_XZBDCDY)){
            str = CommonConstantUtils.LC_ZHGZBS_XZBDCDY;
        } else if(zhbs.endsWith(CommonConstantUtils.LC_ZHGZBS_LCZF)) {
            str = CommonConstantUtils.LC_ZHGZBS_LCZF;
        } else if(zhbs.endsWith(CommonConstantUtils.LC_ZHGZBS_WLZS)) {
            str = CommonConstantUtils.LC_ZHGZBS_WLZS;
        } else {
            return zhbs + CommonConstantUtils.LC_ZHGZBS_QZYZ;
        }

        return zhbs.replace(str, CommonConstantUtils.LC_ZHGZBS_QZYZ);
    }
}
