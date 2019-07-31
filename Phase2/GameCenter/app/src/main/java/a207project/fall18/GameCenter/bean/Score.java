package a207project.fall18.GameCenter.bean;

import java.io.Serializable;

/**
 * A Score class.
 */
public class Score implements Serializable {

    /**
     * The id number of score.
     */
    private int id;


    /**
     * Score user id number.
     */
    private int userId;


    /**
     * The game type of this score.
     */
    private String gameType;
    /**
     * Score user nickname.
     */
    private String nickname;
    /**
     * Final score of game.
     */
    private int finalScore;

    /**
     * A new Score.
     */
    public Score(){}

    /**
     * A new Score.
     *
     * @param user user of this Score
     * @param gameType game type of this Score
     */
    public Score(User user, String gameType){
        this.userId = user.getId();
        this.gameType = gameType;
        this.nickname = user.getNickname();
    }

    /**
     * Returns nickname of player of this score.
     *
     * @return nickname of player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname of player.
     *
     * @param nickname nickname of player to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns id of this score.
     *
     * @return id of score
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of this score.
     *
     * @param id of score
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns user id of score.
     *
     * @return user id of score
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id of score.
     *
     * @param userId of score
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**
     * Returns game type of score.
     *
     * @return game type of this score
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Sets game type of score.
     *
     * @param gameType of score
     */
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    /**
     * Returns final score.
     *
     * @return final score
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Sets final score.
     *
     * @param finalScore final score
     */
    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public String toString() {
        return this.id+"/"+this.userId +"/"+this.gameType+"/"+this.finalScore;
    }
}


