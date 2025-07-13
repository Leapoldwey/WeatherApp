package org.example.weatherconsumer.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.weatherconsumer.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {
    @Query(value = """
        select city, count(distinct date(time)) AS rainy_days from weather
        where condition = 'Дождливо'
          and time >= :start
          and time <= :end
        group by city
        order by rainy_days desc
        limit 1
        """, nativeQuery = true)
    Object[] findTheRainiestWeek(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = """
        select city, max(temperature) as max_temp, date(time) AS hottest_day from weather
        group by city, date(time)
        order by max_temp desc
        limit 1
        """, nativeQuery = true)
    Object[] findTheHottestDay();

    @Query(value = """
        select city, avg(temperature) as avg_temp from weather
        where time >= :start
          and time <= :end
        group by city
        order by avg_temp
        limit 1
        """, nativeQuery = true)
    Object[] findCityWithLowestAvgTemp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Modifying
    @Transactional
    @Query(value = "delete from weather where time < :before", nativeQuery = true)
    void deleteOldWeatherByTimeBefore(@Param("before") LocalDateTime before);
}
