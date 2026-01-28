package com.semihsahinoglu.standing_service.mapper;

import com.semihsahinoglu.standing_service.dto.LeagueAndTeamName;
import com.semihsahinoglu.standing_service.dto.StandingCreateRequest;
import com.semihsahinoglu.standing_service.dto.StandingResponse;
import com.semihsahinoglu.standing_service.entity.Standing;
import org.springframework.stereotype.Component;

@Component
public class StandingMapper {

    public StandingResponse toDto(Standing standing, LeagueAndTeamName leagueAndTeamName) {
        if (standing == null || leagueAndTeamName == null) return null;

        return StandingResponse.builder()
                .leagueId(standing.getLeagueId())
                .teamId(standing.getTeamId())
                .leagueName(leagueAndTeamName.leagueName())
                .teamName(leagueAndTeamName.teamName())
                .played(standing.getPlayed())
                .won(standing.getWon())
                .draw(standing.getDraw())
                .lost(standing.getLost())
                .goalsFor(standing.getGoalsFor())
                .goalsAgainst(standing.getGoalsAgainst())
                .goalDifference(standing.getGoalDifference())
                .points(standing.getPoints())
                .build();
    }

    public Standing toEntity(StandingCreateRequest standingCreateRequest) {
        if (standingCreateRequest == null) return null;

        return Standing.builder()
                .leagueId(standingCreateRequest.leagueId())
                .teamId(standingCreateRequest.teamId())
                .played(standingCreateRequest.played())
                .won(standingCreateRequest.won())
                .draw(standingCreateRequest.draw())
                .lost(standingCreateRequest.lost())
                .goalsFor(standingCreateRequest.goalsFor())
                .goalsAgainst(standingCreateRequest.goalsAgainst())
                .build();
    }

}
