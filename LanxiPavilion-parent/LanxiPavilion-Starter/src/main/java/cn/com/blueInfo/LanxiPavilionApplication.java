package cn.com.blueInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
public class LanxiPavilionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanxiPavilionApplication.class, args);
    }

}
