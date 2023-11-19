package pl.harpi.hexal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class HexalApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(HexalApplication.class, args)));
    }
}