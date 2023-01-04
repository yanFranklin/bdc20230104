package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcReturnData;
import cn.gtmap.realestate.common.core.enums.BdcZssyqkEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityExistsException;
import cn.gtmap.realestate.common.core.qo.config.BdcYzhQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcYzhVO;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.config.core.mapper.BdcXtZsyzhMapper;
import cn.gtmap.realestate.config.service.BdcXtZsyzhService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/1/30
 * @description 证书印印制号配置逻辑处理实现
 */
@Service
public class BdcXtZsyzhServiceImpl implements BdcXtZsyzhService {
    private static final Logger logger = LoggerFactory.getLogger(BdcXtZsyzhServiceImpl.class);

    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;
    /**
     * MyBatis ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;
    /**
     * 印制号 ORM操作
     */
    @Autowired
    private BdcXtZsyzhMapper bdcXtZsyzhMapper;
    /**
     * 用户操作
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * 证书印制号作废是否同步清除证书中已使用的印制号
     */
    @Value("${zsyzh.zftbqczs:false}")
    private Boolean zftbqczs;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页对象
     * @param bdcYzhQO 查询条件
     * @return {Page} 证书印制号配置分页数据
     * @description 查询证书印制号配置数据列表
     */
    @Override
    public Page<BdcYzhDO> listBdcXtZsyzh(Pageable pageable, BdcYzhQO bdcYzhQO) {
        return repo.selectPaging("listBdcXtZsyzhByPageOrder", bdcYzhQO, pageable);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhVO 证书印制号模板
     * @return {int} 操作数据记录数
     * @description 生成证书印制号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int generateBdcZsyzh(BdcYzhVO bdcYzhVO) {
        // 判断模板必填属性值，避免前端验证无效或者直接请求接口生成不走前端
        if(StringToolUtils.existItemNullOrEmpty(bdcYzhVO.getNf(), bdcYzhVO.getSqdm(), bdcYzhVO.getQxdm())
                || StringToolUtils.existIntegerItemNullOrEmpty(bdcYzhVO.getZslx(), bdcYzhVO.getBhws(), bdcYzhVO.getQsbh(), bdcYzhVO.getJsbh())){
            throw new NullPointerException("证书印制号模板存在必填项为空问题！");
        }
        // 判断编号
        if(bdcYzhVO.getQsbh() > bdcYzhVO.getJsbh()){
            throw new AppException("错误：证书印制号模板起始编号大于结束编号！");
        }
        // 判断证书印制号是否已经存在
        int num = bdcXtZsyzhMapper.countYzh(bdcYzhVO);
        if(num > 0){
            throw new EntityExistsException("指定区间范围的部分证书印制号已经存在！");
        }

        // 获取用户信息（只调用一次服务）
        UserDto userDTO = userManagerUtils.getCurrentUser();
        String userName = null;
        String userId = null;
        if(null != userDTO){
            userName = userDTO.getAlias();
            userId = userDTO.getId();
        }

        // 生成印制号
        int size = bdcYzhVO.getJsbh() - bdcYzhVO.getQsbh() + 1;
        List<BdcYzhDO> bdcYzhDOList = new ArrayList<>(size);
        for(int qsbh = bdcYzhVO.getQsbh(); qsbh <= bdcYzhVO.getJsbh(); qsbh++){
            BdcYzhDO bdcYzhDO = new BdcYzhDO();
            bdcYzhDO.setYzhid(UUIDGenerator.generate());
            bdcYzhDO.setNf(bdcYzhVO.getNf());
            bdcYzhDO.setQxdm(bdcYzhVO.getQxdm());
            bdcYzhDO.setZslx(bdcYzhVO.getZslx());
            bdcYzhDO.setQzysxlh(this.getQzysxlh(bdcYzhVO, qsbh));
            // 初始状态为已领用
            bdcYzhDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
            bdcYzhDO.setCjr(userName);
            bdcYzhDO.setCjrid(userId);
            bdcYzhDO.setCjsj(new Date());
            bdcYzhDO.setLqbm(bdcYzhVO.getLqbm());
            bdcYzhDO.setLqbmid(bdcYzhVO.getLqbmid());
            bdcYzhDO.setLqr(bdcYzhVO.getLqr());
            bdcYzhDO.setLqrid(bdcYzhVO.getLqrid());

            bdcYzhDOList.add(bdcYzhDO);
            /// 为了防止要生成的印制号数量太大，采用分批量保存，避免全部一次保存严重耗时
            if(bdcYzhDOList.size() >= 1000){
                entityMapper.insertBatchSelective(bdcYzhDOList);
                bdcYzhDOList.clear();
            }
        }
        if(CollectionUtils.isNotEmpty(bdcYzhDOList)){
            entityMapper.insertBatchSelective(bdcYzhDOList);
        }

        return size;
    }

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 提取领取印制号
     */
    @Transactional
    @Override
    public BdcReturnData extractData(BdcYzhVO bdcYzhVO) {
        List<BdcYzhDO> bdcYzhDOS = new ArrayList<>();
        BdcReturnData bdcReturnData = new BdcReturnData();
        if(StringUtils.isBlank(bdcYzhVO.getLqrid()) && StringUtils.isNotBlank(bdcYzhVO.getLqbmid())){
            //提取
            bdcReturnData = this.yzsj(bdcYzhVO,bdcYzhDOS,true);
            if (bdcReturnData.getCode() == -1){
                return bdcReturnData;
            }
            bdcYzhDOS = (List<BdcYzhDO>) bdcReturnData.getRes();
        } else if (StringUtils.isBlank(bdcYzhVO.getLqbmid()) && StringUtils.isNotBlank(bdcYzhVO.getLqrid())){
            //领取
            bdcReturnData = this.yzsj(bdcYzhVO,bdcYzhDOS,false);
            if (bdcReturnData.getCode() == -1){
                return bdcReturnData;
            }
            bdcYzhDOS = (List<BdcYzhDO>) bdcReturnData.getRes();
        } else if (StringUtils.isBlank(bdcYzhVO.getLqbmid()) && StringUtils.isBlank(bdcYzhVO.getLqrid())){
            //撤销
            bdcReturnData = this.rollBack(bdcYzhVO,bdcYzhDOS);
            if (bdcReturnData.getCode() == -1){
                return bdcReturnData;
            }
            bdcYzhDOS = (List<BdcYzhDO>) bdcReturnData.getRes();
        }
        if (bdcYzhDOS == null){
            bdcReturnData.setMsg("数据获取异常");
            bdcReturnData.setCode(-1);
            return bdcReturnData;
        }
        for (BdcYzhDO bdcYzhDO:bdcYzhDOS){
            entityMapper.updateByPrimaryKeyNull(bdcYzhDO);
        }

        bdcReturnData.setCode(1);
        bdcReturnData.setMsg("操作成功");
        return bdcReturnData;
    }

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 将印制号撤回至未提取状态
    */
    private BdcReturnData rollBack(BdcYzhVO bdcYzhVO,List<BdcYzhDO> bdcYzhDOS){
        BdcReturnData bdcReturnData = new BdcReturnData();
        for (int qsbh = bdcYzhVO.getQsbh();qsbh<=bdcYzhVO.getJsbh();qsbh++){
            String qzysxlh = this.getQzysxlh(bdcYzhVO, qsbh);
            Example example = new Example(BdcYzhDO.class);
            example.createCriteria().andEqualTo("qzysxlh",qzysxlh)
                    .andEqualTo("syqk",6)
                    .andEqualTo("zslx",bdcYzhVO.getZslx());
            List<BdcYzhDO> bdcYzhDOList = entityMapper.selectByExample(example);
            if (bdcYzhDOList == null || bdcYzhDOList.size() == 0){
                bdcReturnData.setCode(-1);
                bdcReturnData.setMsg("部分印制号未新增或已被使用！");
                return bdcReturnData;
            }
            bdcYzhDOList.get(0).setLqr(null);
            bdcYzhDOList.get(0).setLqrid(null);
            bdcYzhDOList.get(0).setLqsj(null);
            bdcYzhDOList.get(0).setLqbm(null);
            bdcYzhDOList.get(0).setLqbmid(null);
            bdcYzhDOList.get(0).setTqsj(null);
            bdcYzhDOS.add(bdcYzhDOList.get(0));
        }
        bdcReturnData.setCode(0);
        bdcReturnData.setRes(bdcYzhDOS);
        return bdcReturnData;
    }
    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description flag:true（提取） false（领取）
     */
    private BdcReturnData yzsj(BdcYzhVO bdcYzhVO, List<BdcYzhDO> bdcYzhDOS, boolean flag){
        BdcReturnData bdcReturnData = new BdcReturnData();
        for (int qsbh = bdcYzhVO.getQsbh();qsbh<=bdcYzhVO.getJsbh();qsbh++){
            String qzysxlh = this.getQzysxlh(bdcYzhVO, qsbh);
            Example example = new Example(BdcYzhDO.class);
            example.createCriteria().andEqualTo("qzysxlh",qzysxlh)
                    .andEqualTo("syqk",6)
                    .andEqualTo("zslx",bdcYzhVO.getZslx());
            List<BdcYzhDO> bdcYzhDOList = entityMapper.selectByExample(example);
            if (bdcYzhDOList == null || bdcYzhDOList.size() == 0){
                bdcReturnData.setCode(-1);
                bdcReturnData.setMsg("部分印制号未新增或已被使用");
                return bdcReturnData;
            }
            if (flag){
                if (StringUtils.isBlank(bdcYzhDOList.get(0).getLqrid()) && StringUtils.isBlank(bdcYzhDOList.get(0).getLqbmid())){
                    if (StringUtils.isNotBlank(bdcYzhVO.getLqbmid())){
                        bdcYzhDOList.get(0).setLqbmid(bdcYzhVO.getLqbmid());
                        bdcYzhDOList.get(0).setLqbm(bdcYzhVO.getLqbm());
                        bdcYzhDOList.get(0).setTqsj(new Date());
                    }
                    bdcYzhDOS.add(bdcYzhDOList.get(0));
                }else {
                    bdcReturnData.setCode(-1);
                    bdcReturnData.setMsg("部分印制号已被提取，请检查！");
                    return bdcReturnData;
                }
            }else {
                if (StringUtils.isBlank(bdcYzhDOList.get(0).getLqbmid())){
                    bdcReturnData.setCode(-1);
                    bdcReturnData.setMsg("部分印制号未提取，请先提取再领取！");
                    return bdcReturnData;
                }
                if (StringUtils.isNotBlank(bdcYzhDOList.get(0).getLqbmid()) && StringUtils.isBlank(bdcYzhDOList.get(0).getLqrid())){
                    //判断领取人是否在领取部门下
                    List<OrganizationDto> organizationDtoList = userManagerUtils.getOrgListByUserName(bdcYzhVO.getLqrName());
                    if (organizationDtoList != null){
                        OrganizationDto organizationDto = organizationDtoList.stream()
                                .filter(o -> bdcYzhDOList.get(0).getLqbm().equals(o.getName()))
                                .findFirst().orElse(null);
                        if (organizationDto == null){
                            bdcReturnData.setCode(-1);
                            bdcReturnData.setMsg("该领取人不在领取部门下，不可领取");
                            return bdcReturnData;
                        }
                        bdcYzhDOList.get(0).setLqrid(bdcYzhVO.getLqrid());
                        bdcYzhDOList.get(0).setLqr(bdcYzhVO.getLqr());
                        bdcYzhDOList.get(0).setLqsj(new Date());
                        bdcYzhDOS.add(bdcYzhDOList.get(0));
                    }
                }else {
                    bdcReturnData.setCode(-1);
                    bdcReturnData.setMsg("部分印制号已被领取，请检查！");
                    return bdcReturnData;
                }
            }
        }
        bdcReturnData.setCode(0);
        bdcReturnData.setRes(bdcYzhDOS);
        return bdcReturnData;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhDO 证书印制号配置实体
     * @return {int} 操作数据记录数
     * @description 保存证书印制号配置配置
     */
    @Override
    public int saveBdcZsyzh(BdcYzhDO bdcYzhDO) {
        // 判断模板必填属性值，避免前端验证无效或者直接请求接口生成不走前端
        if(StringToolUtils.existItemNullOrEmpty(bdcYzhDO.getNf(), bdcYzhDO.getQxdm(), bdcYzhDO.getQzysxlh()) || null == bdcYzhDO.getZslx()){
            throw new NullPointerException("证书印制号模板存在必填项为空问题！");
        }

        // 判断是否已经存在指定印制号：如果当前记录印制号更改，但是其他记录已经对应其印制号，则冲突
        Example example = new Example(BdcYzhDO.class);
        example.createCriteria().andEqualTo("qzysxlh", bdcYzhDO.getQzysxlh());
        List<BdcYzhDO> bdcYzhDOList = entityMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(bdcYzhDOList) && null != bdcYzhDOList.get(0)
                && !StringUtils.equals(bdcYzhDO.getYzhid(), bdcYzhDOList.get(0).getYzhid())){
            throw new EntityExistsException("指定证书印制号已经存在！");
        }

        return entityMapper.updateByPrimaryKeySelective(bdcYzhDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhDOList 证书印制号配置实体集合
     * @return {int} 操作数据记录数
     * @description 删除证书印制号配置
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBdcZsyzh(List<BdcYzhDO> bdcYzhDOList) {
        if(CollectionUtils.isEmpty(bdcYzhDOList)){
            return 0;
        }

        int count = 0;
        for (BdcYzhDO bdcYzhDO : bdcYzhDOList){
            count += entityMapper.deleteByPrimaryKey(BdcYzhDO.class, bdcYzhDO.getYzhid());
        }
        return count;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description 作废证书印制号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBdcZsyzh(BdcYzhsymxDO bdcYzhsymxDO) {
        // 保存证书作废明细信息
        bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate());
        bdcYzhsymxDO.setSyr(userManagerUtils.getCurrentUser().getAlias());
        bdcYzhsymxDO.setSyrid(userManagerUtils.getCurrentUser().getId());
        bdcYzhsymxDO.setSysj(new Date());
        bdcYzhsymxDO.setSyqk(BdcZssyqkEnum.ZF.getCode());
        // 作废时候需要保存当前印制号对应证书关联的项目受理编号（如果有对应项目）
        List<String> slbhList = bdcXtZsyzhMapper.listBdcXmSlbhByZsyzh(bdcYzhsymxDO.getYzhid());
        if(CollectionUtils.isNotEmpty(slbhList) && StringUtils.isNotBlank(slbhList.get(0))){
            bdcYzhsymxDO.setSlbh(slbhList.get(0));
        }
        entityMapper.insertSelective(bdcYzhsymxDO);

        // 修改印制号使用情况
        BdcYzhDO bdcYzhDO = new BdcYzhDO();
        bdcYzhDO.setYzhid(bdcYzhsymxDO.getYzhid());
        bdcYzhDO.setSyqk(BdcZssyqkEnum.ZF.getCode());
        entityMapper.updateByPrimaryKeySelective(bdcYzhDO);

        // 同步清除证书中已使用的证书印制号
        if(zftbqczs) {
            BdcYzhDO yzh = entityMapper.selectByPrimaryKey(BdcYzhDO.class, bdcYzhsymxDO.getYzhid());
            if(null == yzh || StringUtils.isBlank(yzh.getQzysxlh())) {
                return;
            }
            Example example = new Example(BdcZsDO.class);
            example.createCriteria().andEqualTo("qzysxlh", yzh.getQzysxlh());
            List<BdcZsDO> zsDOList = entityMapper.selectByExampleNotNull(example);
            if(CollectionUtils.isEmpty(zsDOList) || null == zsDOList.get(0) || StringUtils.isBlank(zsDOList.get(0).getZsid())) {
                return;
            }
            int count = bdcXtZsyzhMapper.clearZsyzh(zsDOList.get(0).getZsid());
            if(count > 1) {
                throw new AppException("清空证书印制号出错！！！");
            }
            logger.warn("证书印制号{}作废时同步清空证书记录{}中印制号数据", yzh.getQzysxlh(), zsDOList.get(0).getZsid());
        }
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description 还原证书印制号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void revertBdcZsyzh(BdcYzhsymxDO bdcYzhsymxDO) {

        // 更新证书作废明细信息
        bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate());
        bdcYzhsymxDO.setSyr(userManagerUtils.getCurrentUser().getAlias());
        bdcYzhsymxDO.setSyrid(userManagerUtils.getCurrentUser().getId());
        bdcYzhsymxDO.setSysj(new Date());
        bdcYzhsymxDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
        // 作废时候需要保存当前印制号对应证书关联的项目受理编号（如果有对应项目）
        List<String> slbhList = bdcXtZsyzhMapper.listBdcXmSlbhByZsyzh(bdcYzhsymxDO.getYzhid());
        if(CollectionUtils.isNotEmpty(slbhList) && StringUtils.isNotBlank(slbhList.get(0))){
            bdcYzhsymxDO.setSlbh(slbhList.get(0));
        }
        entityMapper.insertSelective(bdcYzhsymxDO);

        // 修改印制号使用情况
        BdcYzhDO bdcYzhDO = new BdcYzhDO();
        bdcYzhDO.setYzhid(bdcYzhsymxDO.getYzhid());
        bdcYzhDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
        entityMapper.updateByPrimaryKeySelective(bdcYzhDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhVO 证书印制号模板
     * @param qsbh 编号序列号
     * @return {String} 获取印制号位数
     * @description 获取印制号
     */
    private String getQzysxlh(BdcYzhVO bdcYzhVO, int qsbh) {
        String qzysxlh = String.valueOf(qsbh);

        // 拼接 0
        if(qzysxlh.length() < bdcYzhVO.getBhws()){
            do{
                qzysxlh = StringUtils.join("0", qzysxlh);
            }while (qzysxlh.length() < bdcYzhVO.getBhws());
        }

        // 拼接省区代码
        return bdcYzhVO.getSqdm() + qzysxlh.substring(bdcYzhVO.getSqdm().length());
    }
}
