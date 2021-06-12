package it.besolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Theme(value = "myapp")
@PWA(name = "atlas",shortName = "atlas")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0")
@Push(transport = Transport.LONG_POLLING)
public class BeplatformDesignerApplication implements AppShellConfigurator {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		 SpringApplication.run(BeplatformDesignerApplication.class, args);
		}
	
}
