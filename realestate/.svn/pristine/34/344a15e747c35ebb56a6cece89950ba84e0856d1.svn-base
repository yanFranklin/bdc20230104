package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.core.service.BdcXnbdcdyhGxService;
import cn.gtmap.realestate.init.core.service.BdcZsService;
import cn.gtmap.realestate.init.core.service.BdcdjbService;
import cn.gtmap.realestate.init.service.other.InitDataDealService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

/**
 * 虚拟不动产单元关系服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/4.
 * @description
 */
@Service
@Validated
public class BdcXnbdcdyhGxServiceImpl implements BdcXnbdcdyhGxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXnbdcdyhGxServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcdjbService bdcdjbService;
    @Autowired
    private InitDataDealService initDataDealService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcCzrzServiceImpl bdcCzrzFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsxmFeignService bdcZsxmFeignService;

    /**
     * 落宗服务
     *
     * @param lsdyh
     * @param bdcdyh
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @LogNote(value = "落宗服务", action = LogActionConstans.XNBDCDY_LZ, custom = LogConstans.LOG_TYPE_PP)
    public boolean lz(@NotBlank(message = "临时单元号参数不能为空")String lsdyh,
                      @NotBlank(message = "不动产单元号参数不能为空")String bdcdyh,
                      Integer lsdyhbdclx,
                      Boolean gxbdcdyfwlx,
                      String ip,
                      Integer gxlb) throws Exception {
        //判断当前是否已有匹配关系
        if(CommonConstantUtils.GXLB_DYHLZ.equals(gxlb)){
            //如果是页面上得请求会带ip,这个时候要做验证,如果是土地证的请求不强制验证
            Example example = new Example(BdcXnbdcdyhGxDO.class);
            example.createCriteria()
                    .andEqualTo("bdcdyh",bdcdyh);
            List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcXnbdcdyhGxList)){
                throw new AppException(ErrorCode.SERVER_EX, "单元号已经存在匹配关系，不允许再被匹配");
            }
        }

        boolean lzcg = false;
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setBdcdyh(lsdyh);
        //主要用于房屋土地匹配替换时防止替换错误
        if(lsdyhbdclx != null){
            bdcXmDO.setBdclx(lsdyhbdclx);
        }
        List<BdcXmDO> list = entityMapper.selectByObj(bdcXmDO);
        if (CollectionUtils.isNotEmpty(list)) {
            String qjgldm ="";
            if(StringUtils.isNotBlank(list.get(0).getXmid())) {
                BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, list.get(0).getXmid());
                if (bdcXmFbDO != null) {
                    qjgldm = bdcXmFbDO.getQjgldm();
                }
            }
            //获取需要更新的单元号数据
            InitResultDTO initResultDTO=bdcXmService.updateAllBdcdyXx(list,bdcdyh,qjgldm,gxbdcdyfwlx, CommonConstantUtils.SF_S_DM);
            //增加落宗关系
            BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
            bdcXnbdcdyhGxDO.setXnbdcdyh(lsdyh);
            //先删除单元关系数据
            entityMapper.delete(bdcXnbdcdyhGxDO);
            //存储关系数据
            List<BdcXnbdcdyhGxDO> listXndyhgx=new ArrayList<>();
            String czr =userManagerUtils.getUserAlias();
            //循环生成落宗关系
            for(BdcXmDO xm:list){
                BdcXnbdcdyhGxDO xnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
                xnbdcdyhGxDO.setBdcdyh(bdcdyh);
                xnbdcdyhGxDO.setGxid(UUIDGenerator.generate16());
                xnbdcdyhGxDO.setXnbdcdyh(lsdyh);
                xnbdcdyhGxDO.setXnbdcdyhxmid(xm.getXmid());
                xnbdcdyhGxDO.setCzr(czr);
                xnbdcdyhGxDO.setCzsj(new Date());
                xnbdcdyhGxDO.setGxlb(gxlb);
                listXndyhgx.add(xnbdcdyhGxDO);
            }
            //插入临时单元号和不动产单元号的关系
            entityMapper.insertBatchSelective(listXndyhgx);
            //将InitResultDTO中数据提交入库
            initDataDealService.dealResultDTO(initResultDTO,Constants.DATA_TYPE_PT);
            //初始化登记簿
            bdcdjbService.initBdcdjb(bdcdyh,qjgldm);
            lzcg = true;
            addModifyCzrz(lsdyh,bdcdyh,BdcCzrzLxEnum.CZRZ_CZLX_LZ.key(),ip);
        }
        return lzcg;
    }





    /**
     * 取消落宗服务
     *
     * @param bdcdyh
     * @param qxlzxmid
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @LogNote(value = "取消落宗服务", action = LogActionConstans.XNBDCDY_QXLZ, custom = LogConstans.LOG_TYPE_PP)
    public boolean qxlz(@NotBlank(message = "不动产单元号参数不能为空") String bdcdyh, @NotBlank(message = "取消落宗的项目ID不能为空") String qxlzxmid) throws Exception {
        boolean qxlzcg = false;
        //删除落宗关系
        BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
        bdcXnbdcdyhGxDO.setBdcdyh(bdcdyh);
        bdcXnbdcdyhGxDO.setXnbdcdyhxmid(qxlzxmid);
        List<BdcXnbdcdyhGxDO> list = entityMapper.selectByObj(bdcXnbdcdyhGxDO);
        if (CollectionUtils.isNotEmpty(list)) {
            if(list.size() > 1){
                throw new AppException("同一个项目落宗数据存在多条,请查看");
            }

            String lsdyh=list.get(0).getXnbdcdyh();
            if(StringUtils.isNotBlank(lsdyh)){
                bdcXnbdcdyhGxDO.setXnbdcdyh(lsdyh);
                bdcXnbdcdyhGxDO.setXnbdcdyhxmid(null);
                //通过临时号和单元号去查询落宗的数据
                list = entityMapper.selectByObj(bdcXnbdcdyhGxDO);
                if(CollectionUtils.isNotEmpty(list)){

                    List<BdcXmDO> listXm=new ArrayList<>();
                    for(BdcXnbdcdyhGxDO gxDO:list){
                        BdcXmDO bdcXmDO=entityMapper.selectByPrimaryKey(BdcXmDO.class,gxDO.getXnbdcdyhxmid());
                        if(bdcXmDO!=null){
                            listXm.add(bdcXmDO);
                        }
                    }
                    String qjgldm = "";
                    if(CollectionUtils.isNotEmpty(listXm)) {
                        if (StringUtils.isNotBlank(listXm.get(0).getXmid())) {
                            BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, listXm.get(0).getXmid());
                            if (bdcXmFbDO != null) {
                                qjgldm = bdcXmFbDO.getQjgldm();
                            }
                        }
                    }
                    //获取需要更新的单元号数据
                    InitResultDTO initResultDTO=bdcXmService.updateAllBdcdyXx(listXm,lsdyh,qjgldm,null, CommonConstantUtils.SF_S_DM);
                    //将InitResultDTO中数据提交入库
                    initDataDealService.dealResultDTO(initResultDTO, Constants.DATA_TYPE_PT);
                    //删除单元关系数据
                    entityMapper.delete(bdcXnbdcdyhGxDO);
                    qxlzcg = true;
                    addModifyCzrz(lsdyh,bdcdyh,BdcCzrzLxEnum.CZRZ_CZLX_QXLZ.key(),null);
                }
            }
        }
        return qxlzcg;
    }

    /**
     * 根据项目id获取虚拟单元号关系
     *
     * @param xndyhxmid
     * @return  List<BdcXnbdcdyhGxDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public  List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhGx(@NotBlank(message = "参数不能为空") String xndyhxmid) {
        BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
        bdcXnbdcdyhGxDO.setXnbdcdyhxmid(xndyhxmid);
        List<BdcXnbdcdyhGxDO> list = entityMapper.selectByObj(bdcXnbdcdyhGxDO);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * 根据条件查询
     *@author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     *@param bdcXnbdcdyhGxDO
     *@return List<BdcXnbdcdyhGxDO>
     *@description
     */
    @Override
    public List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhByCondition(BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO) {
        return entityMapper.selectByObj(bdcXnbdcdyhGxDO);
    }

    /**
     * @param bdcXmDOList
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新单元号信息
     * @date : 2021/12/30 18:05
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @LogNote(value = "更新单元号信息", action = "XNBDCDY_UPDATEBDCDY")
    public void updateBdcdyh(List<BdcXmDO> bdcXmDOList, String bdcdyh,String qjgldm,Integer bhdysd,Boolean gxbdcdyfwlx) throws Exception {
        //获取需要更新的单元号数据
        InitResultDTO initResultDTO = bdcXmService.updateAllBdcdyXx(bdcXmDOList, bdcdyh, qjgldm, gxbdcdyfwlx,bhdysd);
        //将InitResultDTO中数据提交入库
        initDataDealService.dealResultDTO(initResultDTO, Constants.DATA_TYPE_PT);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertXndyhGx(List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOS){
        if(CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOS)){
            entityMapper.insertBatchSelective(bdcXnbdcdyhGxDOS);
        }
    }


    /**
     * 添加操作日志
     * @param lsdyh
     * @param bdcdyh
     * @param czlx
     */
    private void addModifyCzrz(String lsdyh,
                               String bdcdyh,
                               Integer czlx,
                               String requestClientRealIP) {
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzzt(1);
        bdcCzrzDO.setCzlx(czlx);
        bdcCzrzDO.setCzyy(BdcCzrzLxEnum.codeTodesc(czlx));
        if(StringUtils.isNotBlank(lsdyh)) {
            //查询项目信息
            BdcXmDO bdcXmDO = new BdcXmDO();
            bdcXmDO.setBdcdyh(lsdyh);
            List<BdcXmDO> list = entityMapper.selectByObj(bdcXmDO);
            if (CollectionUtils.isNotEmpty(list)) {
                BeanUtils.copyProperties(list.get(0), bdcCzrzDO);
                bdcCzrzDO.setLcmc(list.get(0).getGzldymc());
                bdcCzrzDO.setCzcs("证号:" + list.get(0).getBdcqzh() + " "
                        + BdcCzrzLxEnum.codeTodesc(czlx) +
                        ",落宗单元号由" + lsdyh +
                        " 变为:" + bdcdyh);
            }
        }
        bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
        if(StringUtils.isNotBlank(requestClientRealIP)) {
            bdcCzrzDO.setIp(requestClientRealIP);
        }
        bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }
}
