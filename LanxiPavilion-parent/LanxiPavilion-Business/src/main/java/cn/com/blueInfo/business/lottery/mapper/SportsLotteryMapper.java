package cn.com.blueInfo.business.lottery.mapper;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SportsLotteryMapper extends BaseMapper<SportsLottery> {

    int delAllData();

}
