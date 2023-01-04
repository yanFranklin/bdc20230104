package cn.gtmap.realestate.init.service.zsxx;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.InitBdcZsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 初始化不动产权证类型区分服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
public abstract class InitBdcZslxAbstractService extends InitBdcZsBaseAbstractService {

    @Autowired
    private InitBdcZsService initBdcZsService;
    @Autowired
    BdcXmService bdcXmService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcZslxAbstractService.class);

    @Override
    public String getCode() {
        return "zszl";
    }


    /**
     * 更新权利人信息
     *
     * @param bdcQlrList
     * @param zsid
     * @param bdcQlrDO 新权利人对象
     * @return
     */
    private void updateQlr(List<BdcQlrDO> bdcQlrList, String zsid,BdcQlrDO bdcQlrDO) {
        for (int i = 0; i < bdcQlrList.size(); i++) {
            // 更新权利人信息中的证书id
            bdcQlrList.get(i).setZsid(zsid);
        }
        bdcQlrDO.setZsid(zsid);
        bdcQlrDO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrList,"getQlrmc"," "));
        bdcQlrDO.setZjh(StringToolUtils.resolveBeanToAppendStr(bdcQlrList,"getZjh"," "));
        //先清空
        bdcQlrDO.setBz(null);
        //存备注中
        bdcQlrDO.setBz(StringToolUtils.resolveBeanToAppendStr(bdcQlrList,"getZjzl"," "));
    }

    /**
     * 附记信息
     *@param bdcZsDO
     * @param bdcQl
     * @param xmid
     * @param listQlr
     * @param sfhxqlfj 是否回写权利附记
     * @return
     */
    protected String setFj(BdcZsDO bdcZsDO,
                           BdcQl bdcQl,
                           String xmid,
                           List<BdcQlrDO> listQlr,
                           boolean zsyl,
                           boolean sjbl,
                           boolean sfhxqlfj,
                           boolean sfyqlfj,
                           boolean mbqlfj
                           ) {
        // 附记
        StringBuilder fj=new StringBuilder();
        // 数据补录附记取权利表附记
        if(sjbl){
            if (bdcQl != null && StringUtils.isNotBlank(bdcQl.getFj())) {
                fj.append(bdcQl.getFj());
            }
        }else{
            if (bdcQl != null && StringUtils.isNotBlank(bdcQl.getFj()) && sfyqlfj) {
                if (mbqlfj){
                    fj = mbfj(xmid, bdcZsDO,fj, bdcQl, listQlr, sfhxqlfj, zsyl );
                }else {
                    fj.append(bdcQl.getFj());
                }
            }else {
                fj = mbfj(xmid, bdcZsDO,fj, bdcQl, listQlr, sfhxqlfj, zsyl );
            }
        }
        // 存储附记
        if(bdcZsDO!=null){
            bdcZsDO.setFj(fj.toString());
        }
        return fj.toString();
    }

    private StringBuilder mbfj(String xmid, BdcZsDO bdcZsDO, StringBuilder fj, BdcQl bdcQl,List<BdcQlrDO> listQlr, Boolean sfhxqlfj, Boolean zsyl){
        //不初始化附记的话
        if (!initBeanFactory.isInitFj()) {
            String fjmbsj = bdcXtQlqtzkFjPzService.getFjMessageByZsxx(xmid, bdcZsDO.getZsid(), listQlr);
            if (StringUtils.isNotBlank(fjmbsj)) {
                fj.append(fjmbsj);
            }
        }
        if (bdcQl != null) {
            if (StringUtils.isNotBlank(bdcQl.getFj()) && StringUtils.isNotBlank(initBeanFactory.getFjWz())) {
                // 设定附记信息
                //判断附记是否已存在权利附记,防止重复
                if (fj.indexOf(bdcQl.getFj()) < 0) {
                    if (StringUtils.equals(initBeanFactory.getFjWz(), "bottom")) {
                        fj.insert(0, "\n").insert(0, bdcQl.getFj());
                    } else {
                        fj.append("\n").append(bdcQl.getFj());
                    }
                }
            }
            //权利不初始化附记并且要回写的做处理
            if (!zsyl && !initBeanFactory.isInitFj() && initBeanFactory.isHxFj() && sfhxqlfj) {
                if (StringUtils.isNotBlank(initBeanFactory.getFjWz()) || StringUtils.isBlank(bdcQl.getFj())) {
                    bdcQl.setFj(fj.toString());
                } else if (StringUtils.isNotBlank(fj) && !bdcQl.getFj().contains(fj)) {
                    //判断权利附记是否已存在模板附记,防止重复
                    bdcQl.setFj(bdcQl.getFj() + "\n" + fj.toString());
                }
                entityMapper.updateByPrimaryKeySelective(bdcQl);
            }
        }
        return fj;

    }

    protected void setZsQlqtzk(BdcZsDO bdcZs,BdcXmDO bdcXm,InitServiceQO initServiceQO,List<BdcQlrDO> bdcQlrList){
        // 获得权利其他状况
        if(initBeanFactory.isDqXmQlqtzk()){
            LOGGER.info("证书{}读取项目表{}的权利其它状况", bdcZs.getZsid(), bdcXm.getXmid());
            bdcZs.setQlqtzk(bdcXm.getBfqlqtzk());
            //拼接内容
            bdcZs.setQlqtzk(bdcXtQlqtzkFjPzService.appendQlqtzk(bdcZs.getQlqtzk(),bdcXm.getXmid(),bdcZs.getZsid(),bdcQlrList));
        }else if(!initServiceQO.isSjbl()){
            // 数据补录权利其他状况不走模板配置
            // 获得权利其他状况
            bdcZs.setQlqtzk(bdcXtQlqtzkFjPzService.getQlqtzkMessageByZsxx(bdcXm.getXmid(),bdcZs.getZsid(),bdcQlrList));
        }
    }

    @Override
    public List<BdcZsDO> initZsxx(InitServiceQO initServiceQO) throws Exception {
        List<BdcZsDO> bdcZsList = new ArrayList<>();
        BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, initServiceQO.getXmid());
        // 不动产项目存在时
        if (bdcXm != null) {
            //是否增加虚拟权利人数据
            boolean zjqlr=false;
            // 查询权利人数据
            List<BdcQlrDO> bdcQlrList = bdcQlrService.listBdcQlrOrderBySxh(bdcXm.getXmid(), CommonConstantUtils.QLRLB_QLR);
            // 权利人不存在时增加一个空的
            if (CollectionUtils.isEmpty(bdcQlrList)) {
                bdcQlrList=new ArrayList<>();
                bdcQlrList.add(new BdcQlrDO());
                zjqlr=true;
            }
            //是否查询证书数量
            boolean zssl=initServiceQO.isZssl();
            //存储循环集合
            List<BdcQlrDO> qlrList = new ArrayList<>();
            //查询证书数量直接做返回对象
            if(zssl){
                if (CommonConstantUtils.SF_S_DM.equals(bdcXm.getSqfbcz())) {
                    for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                        BdcZsDO bdcZs = new BdcZsDO();
                        bdcZsList.add(bdcZs);
                    }
                } else {
                    BdcZsDO bdcZs = new BdcZsDO();
                    bdcZsList.add(bdcZs);
                }
                return bdcZsList;
            }
            //分别持证处理
            if (CommonConstantUtils.SF_S_DM.equals(bdcXm.getSqfbcz())) {
                for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                    bdcQlrDO.setZsid(UUIDGenerator.generate16());
                    //新对象
                    BdcQlrDO qlr = new BdcQlrDO();
                    BeanUtils.copyProperties(bdcQlrDO, qlr);
                    //清空备注防止影响逻辑
                    qlr.setBz(null);
                    qlrList.add(qlr);
                }
            } else {
                //取其中一个  更新权利人名称
                BdcQlrDO bdcQlrDO = new BdcQlrDO();
                BeanUtils.copyProperties(bdcQlrList.get(0), bdcQlrDO);
                //整合权利人名称并赋值id到权利人
                updateQlr(bdcQlrList, UUIDGenerator.generate16(),bdcQlrDO);
                qlrList.add(bdcQlrDO);
            }

            //生成证书
            bdcZsList = setBdcZs(bdcXm, qlrList,initServiceQO,checkDqLzr());
            //将赋值zsid的权利人信息返回到上层
            //添加前对默认增加的空权利人数据做清理
            if(zjqlr){
                bdcQlrList.remove(0);
            }
            initServiceQO.setBdcQlrList(bdcQlrList);
        }
        return bdcZsList;
    }

    /**
     * 生成不动产证书
     *
     * @param bdcXm
     * @param bdcQlrList
     * @param  initServiceQO
     * @param  dqLzr 是否读取领证人
     * @return
     */
    public abstract List<BdcZsDO> setBdcZs(BdcXmDO bdcXm, List<BdcQlrDO> bdcQlrList,InitServiceQO initServiceQO,boolean dqLzr);

    /**
     * 获取领证人
     */
    public BdcLzrDO queryLzr(BdcQlrDO bdcQlrDO,String xmid){
        //参数不满足返回空 不做查询
        if (StringUtils.isBlank(xmid) || bdcQlrDO == null || StringUtils.isBlank(bdcQlrDO.getQlrid())) {
            return null;
        }
        BdcLzrDO bdcLzrDO = new BdcLzrDO();
        //参数
        BdcLzrDO bdcLzrParam = new BdcLzrDO();
        bdcLzrParam.setXmid(xmid);
        bdcLzrParam.setQlrid(bdcQlrDO.getQlrid());
        //俩个条件查询
        List<BdcLzrDO> list = bdcLzrService.queryBdcLzr(bdcLzrParam, null);
        BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);
        //非分别持证，且生成多本证时，领证人查询所有的拼接展现
        if (CollectionUtils.isEmpty(list) || (Objects.nonNull(bdcXmDO) && Objects.equals(CommonConstantUtils.SF_F_DM, bdcXmDO.getSqfbcz()))) {
            //空的话一个条件查询
            bdcLzrParam.setQlrid(null);
            list = bdcLzrService.queryBdcLzr(bdcLzrParam, null);
        }
        //非空做处理
        if (CollectionUtils.isNotEmpty(list)) {
            bdcLzrDO.setLzrmc(StringToolUtils.resolveBeanToAppendStr(list, "getLzrmc", CommonConstantUtils.ZF_YW_DH));
            bdcLzrDO.setLzrzjh(StringToolUtils.resolveBeanToAppendStr(list, "getLzrzjh", CommonConstantUtils.ZF_YW_DH));
            bdcLzrDO.setLzrdh(StringToolUtils.resolveBeanToAppendStr(list, "getLzrdh", CommonConstantUtils.ZF_YW_DH));
            bdcLzrDO.setLzrzjzl(list.get(0).getLzrzjzl());
        }
        return bdcLzrDO;
    }

    /**
     * 验证是否读取领证人
     * 存值对照
     */
    public boolean checkDqLzr(){
        boolean result=false;
        BdcLzrDO bdcLzrDO=new BdcLzrDO();
        bdcLzrDO.setLzrmc(CommonConstantUtils.BDC_QLLX);
        BdcZsDO bdcZsDO=new BdcZsDO();
        dozerUtils.initBeanDateConvert(bdcLzrDO,bdcZsDO);
        //相等则返回true
        if(StringUtils.equals(bdcZsDO.getLzr(),bdcLzrDO.getLzrmc())){
            result=true;
        }
        return result;
    }
}
