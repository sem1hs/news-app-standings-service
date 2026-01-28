package com.semihsahinoglu.standing_service.repository;

import com.semihsahinoglu.standing_service.entity.Standing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StandingRepository extends JpaRepository<Standing, Long> {

    List<Standing> findByLeagueIdOrderByPointsDescGoalDifferenceDescGoalsForDesc(Long leagueId);
    Optional<Standing> findByLeagueIdAndTeamId(Long leagueId, Long teamId);
}
