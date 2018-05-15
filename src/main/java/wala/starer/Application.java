package wala.starer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import com.alibaba.druid.pool.DruidDataSource;

@SpringBootApplication
@ServletComponentScan
@ComponentScan("wala.controller")
public class Application extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	 
	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	 
	            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
	 
	            container.addErrorPages(error401Page, error404Page, error500Page);
	        }
	    };
	}
	
	@Autowired  
    private Environment env; 
	
	@Bean(destroyMethod =  "close")  
    public DataSource dataSource() {  
        DruidDataSource dataSource = new DruidDataSource();  
        dataSource.setUrl(env.getProperty("spring.datasource.url"));  
        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名  
        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码  
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));  
        dataSource.setInitialSize(2);//初始化时建立物理连接的个数  
        dataSource.setMaxActive(20);//最大连接池数量  
        dataSource.setMinIdle(0);//最小连接池数量  
        dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。  
        dataSource.setValidationQuery("SELECT 1");//用来检测连接是否有效的sql  
        dataSource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效  
        dataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。  
        dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache  
        return dataSource;  
    }  
}
