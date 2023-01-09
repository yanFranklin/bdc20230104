package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.workflow.clients.define.v1.WorkDayClient;
import cn.gtmap.gtc.workflow.domain.define.Work;
import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import cn.gtmap.realestate.common.core.domain.BdcWtrDO;
import cn.gtmap.realestate.common.core.domain.BdcWtsDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcWtsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.WorkDayVO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.core.mapper.BdcWtsMapper;
import cn.gtmap.realestate.inquiry.service.BdcWtsService;
import cn.gtmap.server.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
* @return
* @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
* @description 出具委托书相关业务服务
*/
@Service
public class BdcWtsServiceImpl implements BdcWtsService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcWtsMapper bdcWtsMapper;

    @Autowired
    WorkDayClient workDayClient;

    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 新增委托书信息
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveWts(BdcWtsDTO bdcWtsDTO) {
        if (StringToolUtils.existItemNullOrEmpty(bdcWtsDTO.getWtrxm(),bdcWtsDTO.getStrxm(),bdcWtsDTO.getWtsbh())) {
            throw new NullPointerException("请检查必填项是否为空！");
        }
        if (StringUtils.isBlank(bdcWtsDTO.getWtksrq())) {
            bdcWtsDTO.setWtksrq(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        Date nextDate = null;
        try {
            nextDate = DateUtils.getSameDayOfNextMonth(new SimpleDateFormat("yyyy-MM-dd").parse((bdcWtsDTO.getWtksrq())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bdcWtsDTO.setId(UUIDGenerator.generate16());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String ksrq = bdcWtsDTO.getWtksrq();
        ksrq = ksrq.split("-")[0]+'年'+ksrq.split("-")[1]+"月"+ksrq.split("-")[2]+"日";
        String jsrq = sdf.format(nextDate);
        bdcWtsDTO.setWtqj("自  "+ksrq+"起至 "+jsrq+"止");
        if (StringUtils.isBlank(bdcWtsDTO.getJzr())){
            bdcWtsDTO.setJzr(userManagerUtils.getCurrentUserName());
        }
        BdcWtsDO bdcWtsDO = new BdcWtsDO();
        BeanUtils.copyProperties(bdcWtsDTO,bdcWtsDO);
        bdcWtsDO = this.handleDatas(bdcWtsDO);
        Example example = new Example(BdcWtsDO.class);
        example.createCriteria().andEqualTo("wtsbh",bdcWtsDO.getWtsbh());
        List<BdcWtsDO> wtsList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(wtsList)) {
            throw new AppException("存在相同委托书编号！");
        }
        //保存委托人信息
        this.saveWtrxx(bdcWtsDO);
        return entityMapper.insertSelective(bdcWtsDO);
    }

    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description  保存委托人信息
    */
    private void saveWtrxx(BdcWtsDO bdcWtsDO) {
        BdcWtrDO bdcWtrDO = new BdcWtrDO();
        bdcWtrDO.setWtsbh(bdcWtsDO.getWtsbh());
        bdcWtrDO.setWtsj(new Date());
        if (bdcWtsDO.getWtrxm().split(",").length > 1) {
            String[] wtrArr = bdcWtsDO.getWtrxm().split(",");
            String[] zjhArr = bdcWtsDO.getWtrzjh().split(",");
            String[] xbArr = bdcWtsDO.getWtrxb().split(",");
            String[] dhArr = bdcWtsDO.getWtrdh().split(",");

            for (int i = 0; i < wtrArr.length; i++) {
                bdcWtrDO.setId(UUIDGenerator.generate16());
                bdcWtrDO.setWtrxm(wtrArr[i]);
                bdcWtrDO.setWtrzjh(zjhArr[i]);
                bdcWtrDO.setWtrxb(xbArr[i]);
                bdcWtrDO.setWtrdh(dhArr[i]);
                entityMapper.insert(bdcWtrDO);
            }
        }else {
            bdcWtrDO.setId(UUIDGenerator.generate16());
            bdcWtrDO.setWtrxm(bdcWtsDO.getWtrxm());
            bdcWtrDO.setWtrzjh(bdcWtsDO.getWtrzjh());
            bdcWtrDO.setWtrxb(bdcWtsDO.getWtrxb());
            bdcWtrDO.setWtrdh(bdcWtsDO.getWtrdh());
            entityMapper.insert(bdcWtrDO);
        }

    }

    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 获取委托书编号
    */
    @Override
    public Object getWtsDatas() {
        CommonResponse response = new CommonResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        List<BdcWtsDO> wtsList = bdcWtsMapper.getWtsDatas();
        if (CollectionUtils.isEmpty(wtsList)) {
            dateStr = dateStr+"0001";
            response.setData(dateStr);
            return CommonResponse.ok(dateStr);
        }
        List<BdcWtsDO> list = wtsList.stream().sorted(Comparator.comparing(BdcWtsDO::getXh).reversed()).collect(Collectors.toList());
        dateStr = dateStr + String.format("%04d",list.get(0).getXh()+1);
        return CommonResponse.ok(dateStr);
    }

    @Override
    public Object updateWts(String wtsbh) {
        return bdcWtsMapper.updateByWtsbh(wtsbh);
    }

    private BdcWtsDO handleDatas(BdcWtsDO bdcWtsDO){
        bdcWtsDO.setZt(CommonConstantUtils.SF_F_DM);
        bdcWtsDO.setWtsj(new Date());
        bdcWtsDO.setXh(Integer.parseInt(bdcWtsDO.getWtsbh().substring(bdcWtsDO.getWtsbh().length()-4)));
        return bdcWtsDO;
    }
}
