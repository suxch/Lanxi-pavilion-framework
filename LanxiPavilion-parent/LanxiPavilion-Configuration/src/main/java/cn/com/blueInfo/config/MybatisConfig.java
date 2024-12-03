package cn.com.blueInfo.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptorByMySql() {
        // 1. 初始化核心插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 2. 添加分页插件
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        pageInterceptor.setMaxLimit(1000L);
        interceptor.addInnerInterceptor(pageInterceptor);

        // 3. 防止全表更新与删除
//        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 4. 添加非法SQL拦截器
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());

        // 5. 配置安全阈值，限制批量更新或删除的记录数不超过 1000 条
//        DataChangeRecorderInnerInterceptor dataChangeRecorderInnerInterceptor = new DataChangeRecorderInnerInterceptor();
//        dataChangeRecorderInnerInterceptor.setBatchUpdateLimit(10).openBatchUpdateLimitation();
//        interceptor.addInnerInterceptor(dataChangeRecorderInnerInterceptor);

        return interceptor;
    }

}
