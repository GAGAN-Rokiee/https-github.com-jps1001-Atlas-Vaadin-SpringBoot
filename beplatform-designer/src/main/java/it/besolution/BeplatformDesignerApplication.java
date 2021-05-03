package it.besolution;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BeplatformDesignerApplication{

	@Autowired
	ServletContext context;
	
    public static void main(String[] args) {
        SpringApplication.run(BeplatformDesignerApplication.class, args);
    }
    
    @Bean
	public void  contextInitialized() {

		try 
		{
			Constants.CONTEXT_REAL_PATH = context.getRealPath("/");

			StringBuffer request = new StringBuffer();
			
			request = new StringBuffer();
			request.append(Constants.CONTEXT_REAL_PATH);request.append(File.separator);
			request.append("WEB-INF");request.append(File.separator);request.append("CONSTANTS.properties");
			Properties prop = CommonUtils.loadPropertyFile(request.toString());
			
			Constants.SOLUTION_GET_ALL = CommonUtils.getValue(prop, "api-solution-get-all");
			

		} 
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
    }
}
