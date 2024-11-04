package cn.com.blueInfo.business.lottery.mapper;

import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WelfareLotteryMapper extends BaseMapper<WelfareLottery> {

    void delAllData();

}
