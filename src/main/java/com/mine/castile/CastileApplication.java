package com.mine.castile;

import de.flapdoodle.embed.process.distribution.BitSize;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CastileApplication {

    public static void main(String[] args) {
        System.out.println("OS bits (mongo detect): " + BitSize.detect());

        new SpringApplicationBuilder(CastileApplication.class)
                .headless(false).run(args);
    }

}
