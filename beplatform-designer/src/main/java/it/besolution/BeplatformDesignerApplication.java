package it.besolution;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.besolution.utils.Constants;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BeplatformDesignerApplication{


	@Value("${api-solution-get-all}")
	String API_SOLUTION_GET_ALL;
	
	@Value("${api-solution-new}")
	String API_SOLUTION_NEW;
	
    public static void main(String[] args) {
        SpringApplication.run(BeplatformDesignerApplication.class, args);
    }
    
    @Bean
	public void  contextInitialized() {

		try 
		{
			Constants.API_SOLUTION_GET_ALL = API_SOLUTION_GET_ALL;
			Constants.API_SOLUTION_NEW = API_SOLUTION_NEW;
			System.out.println(Constants.API_SOLUTION_GET_ALL+ " FK#####################");

		} 
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
    }
}
