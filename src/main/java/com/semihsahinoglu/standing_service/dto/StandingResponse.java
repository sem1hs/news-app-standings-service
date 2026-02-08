package com.semihsahinoglu.standing_service.dto;

public record StandingResponse(
        Long id,
        Long leagueId,
        Long teamId,

        String leagueName,
        String teamName,

        Integer played,
        Integer won,
        Integer draw,
        Integer lost,
        Integer goalsFor,
        Integer goalsAgainst,
        Integer goalDifference,
        Integer points
) implements ApiResponse {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long leagueId;
        private Long teamId;
        private String leagueName;
        private String teamName;
        private Integer played;
        private Integer won;
        private Integer draw;
        private Integer lost;
        private Integer goalsFor;
        private Integer goalsAgainst;
        private Integer goalDifference;
        private Integer points;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder leagueId(Long leagueId) {
            this.leagueId = leagueId;
            return this;
        }

        public Builder teamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder leagueName(String leagueName) {
            this.leagueName = leagueName;
            return this;
        }

        public Builder teamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder played(Integer played) {
            this.played = played;
            return this;
        }

        public Builder won(Integer won) {
            this.won = won;
            return this;
        }

        public Builder draw(Integer draw) {
            this.draw = draw;
            return this;
        }

        public Builder lost(Integer lost) {
            this.lost = lost;
            return this;
        }

        public Builder goalsFor(Integer goalsFor) {
            this.goalsFor = goalsFor;
            return this;
        }

        public Builder goalsAgainst(Integer goalsAgainst) {
            this.goalsAgainst = goalsAgainst;
            return this;
        }

        public Builder goalDifference(Integer goalDifference) {
            this.goalDifference = goalDifference;
            return this;
        }

        public Builder points(Integer points) {
            this.points = points;
            return this;
        }

        public StandingResponse build() {
            return new StandingResponse(id, leagueId, teamId, leagueName, teamName, played, won, draw, lost, goalsFor, goalsAgainst, goalDifference, points);
        }
    }
}

