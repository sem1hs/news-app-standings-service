package com.semihsahinoglu.standing_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StandingCreateRequest (
        @NotNull(message = "leagueId is required")
        Long leagueId,

        @NotNull(message = "teamId is required")
        Long teamId,

        @Min(0)
        Integer played,

        @Min(0)
        Integer won,

        @Min(0)
        Integer draw,

        @Min(0)
        Integer lost,

        @Min(0)
        Integer goalsFor,

        @Min(0)
        Integer goalsAgainst
){
}
