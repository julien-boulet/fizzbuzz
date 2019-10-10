package com.boubou.fizzbuzz.repositories.impl;

import com.boubou.fizzbuzz.entities.Statistic;
import com.boubou.fizzbuzz.repositories.StatisticRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class StatisticRepositoryImpl implements StatisticRepository {

    public static final String SELECT_TOP = "SELECT * FROM statistic ORDER BY count DESC LIMIT 1";
    public static final String INSERT_UPDATE = "UPDATE statistic SET count = (count +1) WHERE int1=:int1 AND int2=:int2 AND lim=:lim AND str1=:str1 AND str2=:str2;" +
            "INSERT INTO statistic (int1, int2, lim, str1, str2, count) " +
            "SELECT :int1, :int2, :lim, :str1, :str2, 1 " +
            "WHERE NOT EXISTS (SELECT 1 FROM statistic WHERE int1=:int1 AND int2=:int2 AND lim=:lim AND str1=:str1 AND str2=:str2);";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<Statistic> findFirstByOrderByCountDesc() {
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_TOP, new MapSqlParameterSource(),
                (rs, rowNum) ->
                        Optional.of(new Statistic(
                                rs.getInt("int1"),
                                rs.getInt("int2"),
                                rs.getInt("lim"),
                                rs.getString("str1"),
                                rs.getString("str2"),
                                rs.getInt("count")
                        ))
        );
    }

    @Override
    public int insertOrUpdate(int int1, int int2, int limit, String str1, String str2) {
        MapSqlParameterSource mapSqlParameterSource = getMapSqlParameterSource(int1, int2, limit, str1, str2);

        return this.namedParameterJdbcTemplate.update(INSERT_UPDATE, mapSqlParameterSource);
    }

    private MapSqlParameterSource getMapSqlParameterSource(int int1, int int2, int limit, String str1, String str2) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("int1", int1);
        mapSqlParameterSource.addValue("int2", int2);
        mapSqlParameterSource.addValue("lim", limit);
        mapSqlParameterSource.addValue("str1", str1);
        mapSqlParameterSource.addValue("str2", str2);
        return mapSqlParameterSource;
    }
}
