package into.designing.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "user_id")
    private int userId;

    @Property(nameInDb = "user_name")
    private String userName;

    @Generated(hash = 435610697)
    public User(Long id, int userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
