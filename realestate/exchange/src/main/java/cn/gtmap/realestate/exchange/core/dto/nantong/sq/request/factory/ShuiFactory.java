package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.factory;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.shui.ShuiGhxxDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.shui.ShuiGhxxZjDTO;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //构建水相关的参数工厂
 * @Date 2022/5/26 9:30
 **/
public class ShuiFactory {

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //pushStyle为4, 权利人、义务人均推送，多个  的ShuiGhxxZjDTO构建
     * @Date 2022/5/26 9:04
     **/
    public static ShuiGhxxZjDTO buildGhxxZjParam(BdcSdqghDO bdcSdqghDO,
                                          BdcXmDO bdcXmDO,
                                          List<ShuiGhxxZjDTO.ShuiFileInfo> fileInfo,
                                          List<BdcQlrDO> allQrl,
                                          List<BdcQlrDO> allYwl) {
        ShuiGhxxZjDTO req = new ShuiGhxxZjDTO();
        req.setConsno(bdcSdqghDO.getConsno());
        req.setAddress(bdcXmDO.getZl());
        List<ShuiGhxxZjDTO.ShuiNewUser> newUserList = allQrl.stream().map(item -> {
            ShuiGhxxZjDTO.ShuiNewUser qiNewUser = new ShuiGhxxZjDTO.ShuiNewUser();
            qiNewUser.setNewUserName(item.getQlrmc());
            qiNewUser.setNewUserCard(item.getZjh());
            qiNewUser.setNewMobile(item.getDh());
            return qiNewUser;
        }).collect(Collectors.toList());
        List<ShuiGhxxZjDTO.ShuiOriginalUser> originalUserList = allYwl.stream().map(item -> {
            ShuiGhxxZjDTO.ShuiOriginalUser originalUser = new ShuiGhxxZjDTO.ShuiOriginalUser();
            originalUser.setOriginalUserName(item.getQlrmc());
            originalUser.setOriginalUserCard(item.getZjh());
            originalUser.setOldMobile(item.getDh());
            return originalUser;
        }).collect(Collectors.toList());
        req.setNewUserList(newUserList);
        req.setOriginalUserList(originalUserList);
        List<ShuiGhxxZjDTO.ShuiFileInfo> fileInfoList = new ArrayList<>();
        fileInfoList.addAll(fileInfo);
        req.setData(fileInfoList);
        return req;
    }
}
