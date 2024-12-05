package cn.com.blueInfo.business.lottery.mapper;

import cn.com.blueInfo.business.lottery.entity.WelfareLottery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WelfareLotteryMapper extends BaseMapper<WelfareLottery> {

    int customBatchInsert(@Param("tableName") String tableName, @Param("list") List<WelfareLottery> welfareLotteryList);

    int createWelfareSubTable(@Param("tableName") String tableName);

    int updateAutoIncrement(@Param("tableName") String tableName, @Param("count") String count);

    String queryLotteryInfoByIdForTable(@Param("tableName") String tableName, @Param("id") Integer countId);

}
