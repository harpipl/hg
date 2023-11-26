package pl.harpi.hg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class HgApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(HgApplication.class, args)));
    }
}