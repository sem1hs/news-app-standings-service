package com.semihsahinoglu.standing_service.service;

import com.semihsahinoglu.standing_service.dto.StandingUpdateFromFixtureRequest;
import com.semihsahinoglu.standing_service.entity.Standing;
import com.semihsahinoglu.standing_service.repository.StandingRepository;
import org.springframework.stereotype.Service;

@Service
public class StandingInternalService {

    private final StandingRepository standingRepository;

    public StandingInternalService(StandingRepository standingRepository) {
        this.standingRepository = standingRepository;
    }

    public void applyMatchResultFromFixture(StandingUpdateFromFixtureRequest standingUpdateFromFixtureRequest) {
        Standing homeStanding = findOrCreate(standingUpdateFromFixtureRequest.leagueId(), standingUpdateFromFixtureRequest.homeTeamId());
        Standing awayStanding = findOrCreate(standingUpdateFromFixtureRequest.leagueId(), standingUpdateFromFixtureRequest.awayTeamId());

        homeStanding.applyMatchResult(standingUpdateFromFixtureRequest.homeScore(), standingUpdateFromFixtureRequest.awayScore());
        awayStanding.applyMatchResult(standingUpdateFromFixtureRequest.awayScore(), standingUpdateFromFixtureRequest.homeScore());

        standingRepository.save(homeStanding);
        standingRepository.save(awayStanding);
    }

    private Standing findOrCreate(Long leagueId, Long teamId) {
        return standingRepository.findByLeagueIdAndTeamId(leagueId, teamId).orElseGet(() -> standingRepository.save(
                Standing.builder()
                        .leagueId(leagueId)
                        .teamId(teamId)
                        .played(0)
                        .won(0)
                        .draw(0)
                        .lost(0)
                        .goalsFor(0)
                        .goalsAgainst(0)
                        .build()));
    }
}
