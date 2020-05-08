package ch.olmero.tender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenderApplication.class, args);
    }

}
