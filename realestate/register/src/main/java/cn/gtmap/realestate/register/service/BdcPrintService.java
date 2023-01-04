package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDysjPzVO;
import groovy.util.Node;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/11
 * @description 不动产打印业务类
 */
public interface BdcPrintService {
    /**
     * @param paramMap
     *  {
     *  	"spb": [{
     *  			"xmid": "difficultid",
     *  			"sjxxid": "1"
     *  		},
     *  		{
     *  			"xmid": "f166cf582831402c803c66cf58280001",
     *  			"sjxxid": "2"
     *  		}
     *  	],
     *  	"sjd": [{
     *  			"xmid": "difficultid",
     *  			"sjxxid": "1"
     *  		},
     *  		{
     *  			"xmid": "f166cf582831402c803c66cf58280001",
     *  			"sjxxid": "2"
     *  		}
     *  	]
     *  }
     *
     * @return xml字符串
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印xml
     */
    String print(Map<String, List<Map>> paramMap);

    String getPage(List<BdcDysjPzDO> bdcDysjPzDOS, List<BdcDysjZbPzDO> bdcDysjZbPzDOS, Node xmlNode, Map configParam, boolean sfdy);

    /**
     * @param bdcDysjDTOList
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 打印已有数据
     */
    String printDatas(List<BdcDysjDTO> bdcDysjDTOList);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    String parsePrintDatasXml(Node page, Map dataMap);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    String processPageXml(Node page, Map dataMap, Map detailDataMap);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    String processOtherDataSourcePageXml(Node page, Map dataMap, Map detailDataMap);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    String processPrintXml(Node xml, Map dataMap, Map detailDataMap);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    String processPrintXmlNoPage(Node xml, Map dataMap, Map detailDataMap);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    String processOtherDataSourcePrintXml(Node xml, Map dataMap, Map detailDataMap);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    String processOtherDataSourcePrintXmlNoPage(Node xml, Map dataMap, Map detailDataMap);

    /**
     * @param bdcDysjPzVO 打印数据配置VO
     * @return 校验结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 校验打印配置
     */
    Object jypzxx(BdcDysjPzVO bdcDysjPzVO);
}
