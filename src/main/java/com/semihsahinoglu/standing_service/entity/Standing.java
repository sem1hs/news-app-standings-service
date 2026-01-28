package com.semihsahinoglu.standing_service.entity;

import com.semihsahinoglu.standing_service.dto.StandingUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "standings")
public class Standing extends Auditable {

    @NotNull(message = "leagueId is required")
    @Column(nullable = false)
    private Long leagueId;

    @NotNull(message = "teamId is required")
    @Column(nullable = false)
    private Long teamId;

    @Min(0)
    @Column(nullable = false)
    private Integer played;

    @Min(0)
    @Column(nullable = false)
    private Integer won;

    @Min(0)
    @Column(nullable = false)
    private Integer draw;

    @Min(0)
    @Column(nullable = false)
    private Integer lost;

    @Min(0)
    @Column(name = "goals_for", nullable = false)
    private Integer goalsFor;

    @Min(0)
    @Column(name = "goals_against", nullable = false)
    private Integer goalsAgainst;

    @Column(name = "goal_difference", nullable = false)
    private Integer goalDifference;

    @Min(0)
    @Column(nullable = false)
    private Integer points;

    protected Standing() {
    }

    public Standing(Long leagueId, Long teamId, Integer played, Integer won, Integer draw, Integer lost, Integer goalsFor, Integer goalsAgainst) {
        this.leagueId = leagueId;
        this.teamId = teamId;
        this.played = played;
        this.won = won;
        this.draw = draw;
        this.lost = lost;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        recalc();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long leagueId;
        private Long teamId;
        private Integer played;
        private Integer won;
        private Integer draw;
        private Integer lost;
        private Integer goalsFor;
        private Integer goalsAgainst;

        private Builder() {
        }

        public Builder leagueId(Long leagueId) {
            this.leagueId = leagueId;
            return this;
        }

        public Builder teamId(Long teamId) {
            this.teamId = teamId;
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

        public Standing build() {
            return new Standing(leagueId, teamId, played, won, draw, lost, goalsFor, goalsAgainst);
        }
    }

    public void recalc() {
        this.goalDifference = this.goalsFor - this.goalsAgainst;
        this.points = (this.won * 3) + this.draw;
    }

    public void applyMatchResult(Integer goalFor, Integer golAgainst) {
        this.played += 1;
        this.goalsFor += goalFor;
        this.goalsAgainst += golAgainst;

        if (goalFor > golAgainst) this.won += 1;
        else if (goalFor.equals(golAgainst)) this.draw += 1;
        else this.lost += 1;

        recalc();
    }

    public void updateEntity(StandingUpdateRequest request) {
        request.played().ifPresent(value -> this.played = value);
        request.won().ifPresent(value -> this.won = value);
        request.draw().ifPresent(value -> this.draw = value);
        request.lost().ifPresent(value -> this.lost = value);
        request.goalsFor().ifPresent(value -> this.goalsFor = value);
        request.goalsAgainst().ifPresent(value -> this.goalsAgainst = value);

        if (this.played < this.won + this.draw + this.lost)
            throw new IllegalStateException("Played, won + draw + lost toplamından küçük olamaz");

        recalc();
    }


    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getPlayed() {
        return played;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public Integer getWon() {
        return won;
    }

    public void setWon(Integer won) {
        this.won = won;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public Integer getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(Integer goalsFor) {
        this.goalsFor = goalsFor;
    }

    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public Integer getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(Integer goalDifference) {
        this.goalDifference = goalDifference;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
