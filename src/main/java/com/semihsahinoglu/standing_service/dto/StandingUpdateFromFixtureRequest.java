package com.semihsahinoglu.standing_service.dto;

public record StandingUpdateFromFixtureRequest(
        Long leagueId,
        Long homeTeamId,
        Long awayTeamId,
        int homeScore,
        int awayScore
) {}
