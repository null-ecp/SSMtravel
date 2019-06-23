package com.weison.Configration;

import com.weison.util.JDBCUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// 配置类(注解)
@Configuration
// 开启注解事务支持
@EnableTransactionManagement
public class SpringCfg {

    /**
     * Springjdbc 对象
     * @return
     */
    @Bean(name = "jdbcTemplate", autowire = Autowire.BY_NAME)
    @Scope("singleton")
    public JdbcTemplate createJdbcTemplate(){
        return new JdbcTemplate(JDBCUtils.getDataSource());
    }

    /**
     * 连接池对象
     * @return
     */
    @Bean(name = "datasource", autowire = Autowire.BY_NAME)
    public DataSource createdataSource(){
        return JDBCUtils.getDataSource();
    }

    /**
     * SPring 事务控制 对象
     * @return
     */
    @Bean(name = "transactionManager", autowire = Autowire.BY_TYPE)
    public DataSourceTransactionManager createTransactionManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(createdataSource());
        return transactionManager;
    }

    /**
     * mybatis 工厂对象
     * @return
     */
    @Bean(name = "sqlSessionFactory", autowire = Autowire.BY_TYPE)
    public SqlSessionFactoryBean createSqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(createdataSource());
        return sqlSessionFactoryBean;
    }

    /**
     * 注解开启扫描包
     * @return
     */
    @Bean(name = "mapperScanner", autowire = Autowire.BY_TYPE)
    public MapperScannerConfigurer creteMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.weison.dao");
        return mapperScannerConfigurer;
    }
}
