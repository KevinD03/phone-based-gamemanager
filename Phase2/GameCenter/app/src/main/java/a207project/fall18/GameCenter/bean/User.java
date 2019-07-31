package a207project.fall18.GameCenter.bean;

/**
 * A User class.
 */
public class User {

    /**
     * Id number of user.
     */
    private int id;
    /**
     * Username of user.
     */
    private String username;

    /**
     * Password of this user.
     */
    private String password;
    /**
     * Nickname of this user.
     */
    private String nickname;

    /**
     * Returns the id of this User.
     *
     * @return id of user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of this User.
     *
     * @param id of user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns username of this User.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of this User.
     *
     * @param username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password of User.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password of user.
     *
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns nickname of user.
     *
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets nickname of user.
     *
     * @param nickname od user
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return this.id+"/"+this.username+"/"+this.nickname+"/"+this.password;
    }
}


