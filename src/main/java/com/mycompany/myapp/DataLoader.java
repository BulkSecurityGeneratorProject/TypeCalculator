package com.mycompany.myapp;

import com.google.common.collect.ImmutableList;
import com.mycompany.myapp.domain.PackageType;
import com.mycompany.myapp.repository.PackageTypeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@Profile("dev")
public class DataLoader implements ApplicationRunner {
    @Inject
    private PackageTypeRepository repository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        if (repository.findAll().isEmpty()) {
            ImmutableList<PackageType> data = ImmutableList.of(
                PackageType.builder()
                    .type("XS")
                    .name("Hermes-Päckchen")
                    .description("max. 25 kg Summe aus längster und kürzester Seite bis 37 cm")
                    .rule("box.weight <= 25 && box.shortestSide + box.longestSide <= 37")
                    .build(),
                PackageType.builder()
                    .type("S")
                    .name("S-Paket")
                    .description("max. 25 kg Summe aus längster und kürzester Seite bis 50 cm")
                    .rule("box.weight <= 25 && box.shortestSide + box.longestSide <= 50")
                    .build(),
                PackageType.builder()
                    .type("M")
                    .name("M-Paket")
                    .description("max. 25 kg Summe aus längster und kürzester Seite > 50 bis 80 cm")
                    .rule("box.weight <= 25 && box.shortestSide + box.longestSide <= 80")
                    .build(),
                PackageType.builder()
                    .type("L")
                    .name("L-Paket")
                    .description("max. 25 kg Summe aus längster und kürzester Seite > 80 bis 120 cm")
                    .rule("box.weight <= 25 && box.shortestSide + box.longestSide <= 120")
                    .build(),
                PackageType.builder()
                    .type("XL")
                    .name("XL-Paket")
                    .description("Summe aus längster und kürzester Seite > 120 bis 150 cm")
                    .rule("box.shortestSide + box.longestSide <= 150")
                    .build(),
                PackageType.builder()
                    .type("XXL")
                    .name("XXL-Paket")
                    .description("Summe aus längster und kürzester Seite > 150 bis 310 cm 3. Seite max. 50 cm")
                    .rule("box.shortestSide + box.longestSide > 150 && box.shortestSide + box.longestSide <= 310 && box.thirdSide <= 50")
                    .build()
            );
            repository.save(data);
        }
    }
}
