package pl.harpi.hexal;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import pl.harpi.core.domain.UseCase;

@AutoConfiguration
@ComponentScan(
    basePackages = {"pl.harpi.hexal.*"},
    includeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ANNOTATION,
          value = {UseCase.class}),
    })
class HexalAutoConfiguration {}
