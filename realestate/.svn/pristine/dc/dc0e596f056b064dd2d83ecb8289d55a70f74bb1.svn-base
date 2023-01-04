package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.dto.natural.ZrzyDysjDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyPrintDTO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyDysjPzVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/14
 * @description 不动产打印
 */
public interface ZrzyPrintRestService {
    /**
     * @param { "spb":[
     *          {
     *          "xmid":"",
     *          "sjxxid":""
     *          },
     *          {
     *          "xmid":"",
     *          "sjxxid":""
     *          }
     *          ],
     *          "sjd":[
     *          {
     *          "xmid":"",
     *          "sjxxid":""
     *          },
     *          {
     *          "xmid":"",
     *          "sjxxid":""
     *          }
     *          ]
     *          }
     * @return fr3 xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 不动产打印服务
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/print", method = RequestMethod.POST)
    String print(@RequestBody Map<String, List<Map>> paramMap);

    /**
     * @param zyzyDysjDTOList
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 打印已有数据
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/print/qtsjy", method = RequestMethod.POST)
    String printDatas(@RequestBody List<ZrzyDysjDTO> zyzyDysjDTOList);


    /**
     * @param zrzyDysjPzVO 打印数据配置VO
     * @return 校验结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 校验打印配置
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/print/jgjy", method = RequestMethod.POST)
    Object jypzxx(@RequestBody ZrzyDysjPzVO zrzyDysjPzVO);

    /**
     * @param zrzyPrintDTO 打印参数对象
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个证书的打印XML
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/zs/print/singleXml")
    String singleZsPrintXml(@RequestBody ZrzyPrintDTO zrzyPrintDTO);
}
