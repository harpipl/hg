package pl.harpi.hexal;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import pl.harpi.core.domain.UseCase;

@org.springframework.boot.autoconfigure.AutoConfiguration
@ComponentScan(
        basePackages = {"pl.harpi.hexal.*"},
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        value = {UseCase.class}),
        })
class AutoConfiguration {
}