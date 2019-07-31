package a207project.fall18.GameCenter;

import android.app.Application;

import a207project.fall18.GameCenter.bean.User;
import a207project.fall18.GameCenter.dao.SaveDao;
import a207project.fall18.GameCenter.dao.ScoreDao;
import a207project.fall18.GameCenter.dao.UserDao;

/**
 * A class for MyApplication.
 */
public class MyApplication extends Application {

    /**
     * A user
     */
    public User user;
    /**
     * Game type
     */
    public String gameType;
    /**
     * A board manager
     */
    public BoardManager boardManager;


    /**
     * A scoreDao
     */
    public ScoreDao scoreDao = new ScoreDao(this);
    /**
     * A user account manager
     */
    public UserDao userAccountManager = new UserDao(this);
    /**
     * A save manager
     */
    public SaveDao savingManager;

    /**
     * An instance of MyApplication
     */
    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * Initializes an instance.
     */
    public void initSavingManager(){

        savingManager = new SaveDao(this, gameType, user.getUsername());
    }

    /**
     * Returns a user Dao
     *
     * @return
     */
    public UserDao getUserDao(){return userAccountManager;}


    /**
     * Returns a score Dao
     *
     * @return score Dao
     */
    public ScoreDao getScoreDao(){return scoreDao;}


    /**
     * Returns a board manager
     *
     * @return boardManager
     */
    public BoardManager getBoardManager(){return boardManager;}

    /**
     * Sets a boardManager
     *
     * @param bm boardManager
     */
    public void setBoardManager(BoardManager bm){this.boardManager = bm;}

    /**
     * Returns a save manager
     *
     * @return save manager
     */
    public SaveDao getSavingManager(){return this.savingManager;}
    public void setSavingManager(SaveDao sm){this.savingManager = sm;}


    /**
     * Returns an instance of MyApplication
     *
     * @return MyApplication
     */
    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * Returns game type
     *
     * @return gameType
     */
    public String getGame() {
        return gameType;
    }

    /**
     * Sets the game
     *
     * @param game game
     */
    public void setGame(String game) {
        this.gameType = game;
    }


    /**
     * Returns the user
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     *
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
