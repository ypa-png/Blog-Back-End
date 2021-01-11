package top.yangbq.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource (  );
    }

    //后台监控 web.xml ServletRegistrationBean
    //springboot内置了servlet容器，所以没有web.xml，替代方法 ServletRegistrationBean
    @Bean
    public ServletRegistrationBean a(){
        ServletRegistrationBean < StatViewServlet > statViewServletServletRegistrationBean = new ServletRegistrationBean <> ( new StatViewServlet ( ) , "/druid/*" );

        //后台登录需要账号密码
        HashMap < String, String > objectObjectHashMap = new HashMap <> ( );

        //增加配置
        //允许谁可以访问
        objectObjectHashMap.put ( "allow","" );
        objectObjectHashMap.put ( "loginUsername","admin" );
        objectObjectHashMap.put ( "loginPassword","123456" );

        //禁止谁访问
//        objectObjectHashMap.put ( "username","ip" );

        statViewServletServletRegistrationBean.setInitParameters ( objectObjectHashMap );
        return statViewServletServletRegistrationBean;
    }



    //filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean( );
        bean.setFilter ( new WebStatFilter () );
        //可以过滤那些请求
        Map < String, String > map = new HashMap < String, String >();
        //这些东西不进行统计
        map.put ( "exclusions","*.js,*.css,/druid/*" );
        bean.setInitParameters ( map );
        return bean;
    }
}
