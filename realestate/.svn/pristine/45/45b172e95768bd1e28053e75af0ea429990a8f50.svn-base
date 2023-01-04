package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/10
 * @description 不动产权证号不换证处理
 */
@Service
public class BdcBdcqzhBhzServiceImpl{
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBdcqzhBhzServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcZsService bdcZsService;

    @Autowired
    private BdcXmService bdcXmService;

    @Autowired
    private BdcBdcqzhGgServiceImpl baseBdcBdcqzhService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {List} 不动产权证号信息集合
     * @description
     *  <p>
     *      生成不动产项目证书（明）号 <br>
     *    1、处理不换证场景，如果不换证则沿用之前的证号 <br>
     *    2、分别持证要么都换，要么都不换
     *  </p>
     */
    public List<BdcBdcqzhDTO> generateBdcqzh(String xmid) {
        // 获取当前项目对应的证书
        List<BdcZsDO> dqBdcZsDOList = baseBdcBdcqzhService.getBdcZs(xmid);
        if(CollectionUtils.isEmpty(dqBdcZsDOList)) {
            throw new AppException("获取证号不换证场景，当前项目对应证书为空！");
        }

        // 获取项目历史关系
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmService.queryBdcXmLsgxByXmid(xmid);
        if(CollectionUtils.isEmpty(bdcXmLsgxDOList)) {
            throw new AppException("获取证号不换证场景，项目历史关系为空！");
        }

        // 查询出之前的证书记录信息
        String yxmid = "";
        for(BdcXmLsgxDO lsgx : bdcXmLsgxDOList){
            if(null == lsgx.getWlxm() || 0 == lsgx.getWlxm()){
                yxmid = lsgx.getYxmid();
                break;
            }
        }
        if(StringUtils.isBlank(yxmid)){
            throw new AppException("获取证号不换证场景，未获取到原证号内容！");
        }

        List<BdcZsDO> yBdcZsDOList = bdcZsService.queryBdcZsByXmid(yxmid);
        if(CollectionUtils.isEmpty(yBdcZsDOList)){
            throw new AppException("获取证号不换证场景，未获取到原证号内容！");
        }

        // 匹配生成每条证书记录的证号
        List<BdcBdcqzhDTO> bdcBdcqzhDTOList = new ArrayList<>(yBdcZsDOList.size());
        for (int i = 0; i < yBdcZsDOList.size(); i++){
            // 获取上一手、当前证书记录
            BdcZsDO yBdcZsDO = (BdcZsDO) CollectionUtils.get(yBdcZsDOList, i);
            BdcZsDO dqBdcZsDO = (BdcZsDO) CollectionUtils.get(dqBdcZsDOList, i);

            // 生成当前证号
            BdcBdcqzhDTO bdcBdcqzhDTO = this.generateBdcqzhDTO(yBdcZsDO, dqBdcZsDO);
            if(null == bdcBdcqzhDTO){
               continue;
            }

            // 更新上一手证号状态
            this.updateZhzt(yBdcZsDO);
            // 保存当前新证书记录证号
            baseBdcBdcqzhService.saveBdcqzh(bdcBdcqzhDTO);

            bdcBdcqzhDTOList.add(bdcBdcqzhDTO);
        }
        return bdcBdcqzhDTOList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   yBdcZsDO 原不动产证书信息
     * @param   dqBdcZsDO 当前证书信息
     * @return  {BdcBdcqzhDTO} 不动产权证号信息
     * @description   根据上一手证书信息生成当前证号信息内容
     */
    private BdcBdcqzhDTO generateBdcqzhDTO(BdcZsDO yBdcZsDO, BdcZsDO dqBdcZsDO) {
        if(null == yBdcZsDO || StringUtils.isBlank(yBdcZsDO.getBdcqzh()) ||
                null == dqBdcZsDO || StringUtils.isBlank(dqBdcZsDO.getZsid())){
            return null;
        }

        BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();
        // --生成的证号应该保存到当前证书记录中
        bdcBdcqzhDTO.setZsid(dqBdcZsDO.getZsid());
        bdcBdcqzhDTO.setZslx(yBdcZsDO.getZslx());
        // --避免重复获取不换证场景下原证号多出的注销标识
        bdcBdcqzhDTO.setBdcqzh(yBdcZsDO.getBdcqzh().replace(CommonConstantUtils.BDCQZH_ZX, ""));
        bdcBdcqzhDTO.setNf(yBdcZsDO.getNf());
        bdcBdcqzhDTO.setSqsjc(yBdcZsDO.getSqsjc());
        bdcBdcqzhDTO.setSzsxqc(yBdcZsDO.getSzsxqc());
        bdcBdcqzhDTO.setZhlsh(yBdcZsDO.getZhlsh());
        bdcBdcqzhDTO.setZhxlh(yBdcZsDO.getZhxlh());

        return bdcBdcqzhDTO;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   yBdcZsDO 原不动产证书记录
     * @description  更新上一手证书号为注销状态
     */
    private void updateZhzt(BdcZsDO yBdcZsDO) {
        // 如果之前的证号已经包含注销标识说明已经获取过，不再更新
        if(null == yBdcZsDO || StringUtils.contains(yBdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_ZX)) {
            return;
        }

        BdcZsDO yBdcZsDo = new BdcZsDO();
        yBdcZsDo.setZsid(yBdcZsDO.getZsid());
        String zxbdcqzh = StringUtils.join(yBdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_ZX);

        // 查询注销的证号是否已经存在，例如多次换证情况下可能出现多次(销)
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setBdcqzh(zxbdcqzh);
        Example example = new Example(BdcZsDO.class);
        example.createCriteria().andRightLike("bdcqzh", zxbdcqzh);
        int count = entityMapper.countByExample(example);
        // count为0说明没有 苏(2022)淮安市不动产权第0000001号(销) 这样的证号，则保存为 苏(2022)淮安市不动产权第0000001号(销)
        // 大于0，比如 1，则说明 苏(2022)淮安市不动产权第0000001号(销) 已经存在1次，那么后面追加数字，变成 苏(2022)淮安市不动产权第0000001号(销)2
        // 大于0，比如 2，则说明 苏(2022)淮安市不动产权第0000001号(销) 已经存在2次，那么后面追加数字，变成 苏(2022)淮安市不动产权第0000001号(销)3
        if(count > 0) {
            zxbdcqzh = StringUtils.join(zxbdcqzh, ++count);
        }

        yBdcZsDo.setBdcqzh(zxbdcqzh);
        bdcZsService.updateBdcZs(yBdcZsDo);
        LOGGER.info("生成证号，不换证注销之前旧的证号：{}", JSON.toJSONString(yBdcZsDo));
    }
}
