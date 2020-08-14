# GreenDao 是如何让业务开发享受编写数据库代码的 ?

## ORM设计思想

#### 核心概念: ORM(Object-Relational-Mapping)

对象关系映射: 在关系型数据库和业务实体类之间建立映射关系, 其目的是让开发者在业务开发中:

&nbsp;&nbsp;&nbsp;&nbsp;[√] 集中精力在业务实体类设计和简单的操作接口上

&nbsp;&nbsp;&nbsp;&nbsp;[x] 省去编写对繁琐的数据库CRUD的语句

#### 怎么体现ORM设计理念的 ?

&nbsp;&nbsp;&nbsp;&nbsp;[√] 以`Entity`实体类为核心, 构建数据库管理类

&nbsp;&nbsp;&nbsp;&nbsp;[√] 以CRUD操作为核心, 构建对象
 
#### 其他亮点

&nbsp;&nbsp;&nbsp;&nbsp;[√] 使用注解生成辅助代码

&nbsp;&nbsp;&nbsp;&nbsp;[√] 怎么解决性能问题的 ?

## 能为开发者提供哪些便利 ?

要回答这个问题，就要先想想: 如果注入GreenDao这类ORM利器, 我们要如何编写数据库操作的代码（基于SQLite）？至少应该做以下几件事：

#### 1. 手动创建一个 `SQLiteOpenHelper` 的扩展类来管理数据库：
- 实现数据库的创建、升级相应的函数
- 手写创建数据库的SQL语句（如果有N个表，工作重复N次）
- 定义数据库表格字段（如果有N个表，工作重复N次）

示例代码：
```
public class SQLiteDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "database.db";

    public static final int DB_VERSION = 1;

    public static final String TABLE_STUDENT = "students";

    //创建 students 表的 sql 语句
    private static final String STUDENTS_CREATE_TABLE_SQL = "create table" + TABLE_STUDENT + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "tel_no varchar(11) not null,"
            + "cls_id integer not null"
            + ");";

    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        // 创建 students 表
        db.execSQL(STUDENTS_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
        }
    }
}
```

#### 2. 实现一个数据实体类

示例代码如下：
```
public class Student {

    public long id;
    public String name;
    public String tel_no;
    public int cls_id;
    
}
```

#### 3. 实现CRUD操作

插入操作示例代码如下：
```
// 手动将实体类数据装入 ContentValues 类对象中
ContentValues contentValues = new ContentValues();
contentValues.put("id", student.id);
contentValues.put("name", student.name);
contentValues.put("tel_no", student.tel_no);
contentValues.put("cls_id", student.cls_id);
        
//获取数据库管理对象，并将 ContentValues 插入到数据库
mSQLiteDatabase.insert(SQLiteDbHelper.TABLE_STUDENT, null, values);
```

查询操作示例代码如下：
```
// 手写查询语句
Cursor cursor = mSQLiteDatabase.query(SQLiteDbHelper.TABLE_STUDENT, null,
        "cls_id > ? and id >= 1", new String[]{"3"},
        null, null, null, null);

while (cursor.moveToNext()) {
    int stuId = cursor.getInt(0);

    // 获取 name 的索引值
    String stuName = cursor.getString(cursor.getColumnIndex("name"));
    // 获取 其它 字段的值，代码省略
    .......
    .......            
    //手动 将以上字段跟数据实体类转换，代码省略
    .......
    .......                        
}

// 关闭光标
cursor.close();
```

#### 以上工作总结起来就是
- 数据库管理类
- 数据实体类 跟 数据表字段 双向 转换
- CRUD操作的实现

作为一个程序员，上边这些代码我真的不想写第二遍，完全是重复劳动！！！

#### 看GreenDao是如何让你摆脱这些重复编码劳动的

1. 



## 参考文章
- [ORM 实例教程](http://www.ruanyifeng.com/blog/2019/02/orm-tutorial.html)
- [什么是ORM](https://www.jianshu.com/p/ec971e77dd3c)
