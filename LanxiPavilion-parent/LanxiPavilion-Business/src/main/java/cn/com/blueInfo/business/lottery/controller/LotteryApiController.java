package cn.com.blueInfo.business.lottery.controller;

import cn.com.blueInfo.business.lottery.service.SportsLotteryService;
import cn.com.blueInfo.business.lottery.service.WelfareLotteryService;
import cn.com.blueInfo.utils.entity.ResultInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "彩票生成接口")
@RequestMapping("/lottery")
@RestController
@RequiredArgsConstructor
public class LotteryApiController {

    @Autowired
    private WelfareLotteryService welfareLotteryService;

    @Autowired
    private SportsLotteryService sportsLotteryService;

    @ApiOperation("获取下一期彩票数据")
    @GetMapping("/getNextLotteryInfo")
    public ResultInfo getNextLotteryInfo(@ApiParam("随机数范围") @RequestParam String birthday) {
        ResultInfo result = new ResultInfo();
        List<String> welfareResultList = welfareLotteryService.queryWelfareLotteryInfo(birthday);
        List<String> sportsResultList = sportsLotteryService.querySportsLotteryInfo(birthday);
        JSONObject resultInfo = new JSONObject();
        resultInfo.put("双色球", welfareResultList);
        resultInfo.put("大乐透", sportsResultList);
        result.setData(resultInfo);
        return result;
    }

    @ApiOperation("获取原彩票信息")
    @GetMapping("/getOldLotteryInfo")
    public ResultInfo getOldLotteryInfo(@ApiParam("双色球") @RequestParam(required = false) String welfareLotteryInfo,
                                        @ApiParam("大乐透") @RequestParam(required = false) String sportsLotteryInfo) {
        ResultInfo result = new ResultInfo();
        if (StringUtils.isNotEmpty(welfareLotteryInfo)) {
            JSONArray welfareInfo = welfareLotteryService.queryLotteryInfoByString(welfareLotteryInfo);
            result.setData(welfareInfo);
        }
        if (StringUtils.isNotEmpty(sportsLotteryInfo)) {
            JSONArray sportsInfo = sportsLotteryService.queryLotteryInfoByString(sportsLotteryInfo);
            result.setData(sportsInfo);
        }
        return result;
    }

}
