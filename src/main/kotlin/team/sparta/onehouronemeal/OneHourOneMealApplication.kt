package team.sparta.onehouronemeal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class OneHourOneMealApplication

fun main(args: Array<String>) {
    runApplication<OneHourOneMealApplication>(*args)
}
