package cn.com.blueInfo.business.lottery.mapper;

import cn.com.blueInfo.business.lottery.entity.SportsLottery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SportsLotteryMapper extends BaseMapper<SportsLottery> {

    int delAllData();

    int customBatchInsert(@Param("tableName") String tableName, @Param("list") List<SportsLottery> sportsLotteryList);

    int createSportsSubTable(@Param("tableName") String tableName);

    int updateAutoIncrement(@Param("tableName") String tableName, @Param("count") String count);

}
