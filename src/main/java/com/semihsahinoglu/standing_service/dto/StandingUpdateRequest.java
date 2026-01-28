package com.semihsahinoglu.standing_service.dto;

import java.util.Optional;

public record StandingUpdateRequest(
        Optional<Integer> played,
        Optional<Integer> won,
        Optional<Integer> draw,
        Optional<Integer> lost,
        Optional<Integer> goalsFor,
        Optional<Integer> goalsAgainst
) {
}
