package cz.muni.fi.pa165.soccermanager.entity;

import cz.muni.fi.pa165.soccermanager.enums.StadiumEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Random;


/**
 * @author 456519 Filip Lux
 * @version 10/27/2017.
 * Class represents one match. Every Match has home and away team and date, all three attributes have to be be set.
 * There is a stadium, where is the event held. After the Match is finished, match score is stored in
 * here. Result of the match is computed by function that is owned by the class.
 * You should use TeamBuilder to create instances of Team.
 */
@Entity
@Table(name = "\"Match\"")
public class Match {

    public static class MatchBuilder {

        private LocalDate date;
        private StadiumEnum stadium;
        private final Team homeTeam;
        private final Team awayTeam;

        public MatchBuilder(Team homeTeam, Team awayTeam, LocalDate date) {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.stadium = homeTeam.getStadium();
            this.date = date;
        }

        public MatchBuilder stadium(StadiumEnum stadium) {
            this.stadium = stadium;
            return this;
        }

        public Match build() {
            return new Match(this);
        }

    }

    public Match() { }

    private Match(MatchBuilder builder) {
        homeTeam = builder.homeTeam;
        awayTeam = builder.awayTeam;
        date = builder.date;
        stadium = builder.stadium;
        finished = false;
        homeTeamGoals = 0;
        awayTeamGoals = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private StadiumEnum stadium;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    @Column(nullable = false)
    private boolean finished;

    @Column(nullable = false)
    private int homeTeamGoals;

    @Column(nullable = false)
    private int awayTeamGoals;

    public long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getAwayTeamGoals() { return awayTeamGoals; }

    public void setAwayTeamGoals(int awayTeamGoals) { this.awayTeamGoals = awayTeamGoals; }

    public int getHomeTeamGoals() { return homeTeamGoals; }

    public void setHomeTeamGoals(int homeTeamGoals) { this.homeTeamGoals = homeTeamGoals; }

    public Team getAwayTeam() { return awayTeam; }

    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }

    public Team getHomeTeam() { return homeTeam; }

    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public StadiumEnum getStadium() { return stadium; }

    public void setStadium(StadiumEnum stadium) { this.stadium = stadium; }

    public boolean isFinished() { return finished; }

    public void setFinished(boolean finished) { this.finished = finished; }

    /**
     * sets score of the match by some default values
     */
    public void playMatch() {

        if (!isFinished()) {

            Random generator = new Random();

            int homeGoals = generator.nextInt(3) + 1;
            int awayGoals = generator.nextInt(3);

            setHomeTeamGoals(homeGoals);
            setAwayTeamGoals(awayGoals);
            setFinished(true);

            homeTeam.setGoalsScored(homeTeam.getGoalsScored() + homeGoals);
            homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + awayGoals);
            awayTeam.setGoalsScored(awayTeam.getGoalsScored() + awayGoals);
            awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + homeGoals);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;

        Match match= (Match) o;

        if (getHomeTeam() != match.getHomeTeam()) return false;
        if (getAwayTeam() != match.getAwayTeam()) return false;
        if (getDate() != match.getDate()) return false;
        if (isFinished() != match.isFinished()) return false;
        if (getStadium() != null ? !getStadium().equals(match.getStadium()) : match.getStadium() != null) return false;
        if (getHomeTeamGoals() != match.getHomeTeamGoals()) return false;
        return (getAwayTeamGoals() != match.getAwayTeamGoals());
    }

    @Override
    public int hashCode() {
        int result = getHomeTeam() != null ? getHomeTeam().hashCode() : 0;
        result = 51 * result + (getAwayTeam() != null ? getAwayTeam().hashCode() : 0);
        result = 51 * result + (isFinished() ? 1 : 0);
        result = 51 * result + getHomeTeamGoals();
        result = 51 * result + getAwayTeamGoals();
        result = 51 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 51 * result + (getStadium() != null ? getStadium().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String string = "Match {" +
                "id=" + getId() +
                ", date=" + getDate() + '\'' +
                "home team=" + getHomeTeam() +
                ", away team=" + getAwayTeam() +
                ", stadium=" + getStadium() + '\'';
        if (isFinished())
            string += "result=" + getHomeTeamGoals() + ':' + getAwayTeamGoals() + " }\'";
        else
            string += "match was not finished yet }\'";
        return string;
    }

}
