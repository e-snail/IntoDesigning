package into.designing;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import into.designing.db.DaoMaster;
import into.designing.db.DaoSession;
import into.designing.db.User;

/**
 * @author wilbur.wu
 * @date 2020.08.10
 *
 * 主 Activity 入口
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGreenDao();

        dbOperations();
    }

    private static DaoSession mDaoSession;

    void initGreenDao() {
        //创建数据库mydb.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplication(), "mydb.db");
        //获取可写数据库
        SQLiteDatabase database = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(database);
        //获取Dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }

    public void dbOperations() {
        User user = new User();
        user.setUserId(1000);
        user.setUserName("Wilbur.Wu");

        long id = getDaoSession().insert(user);
        Log.d(TAG, "dbOperations: insert return id " + id);

        Log.d(TAG, "dbOperations: user's id " + user.getId());

        getDaoSession().delete(user);

        Log.d(TAG, "dbOperations: delete user's success");
    }
}