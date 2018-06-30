package com.wftd.kongyan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.wftd.kongyan.view.address.City;
import java.util.ArrayList;

/**
 * 创建数据库和表
 */
public class DBHelper extends SQLiteOpenHelper {
    //区县表
    private String TABLE_AREA = "CREATE TABLE area(\n"
        + "id Integer AUTO_INCREMENT PRIMARY KEY NOT NULL,\n"
        + "areaid varchar(20) NOT NULL,\n"
        + "area varchar(50) NOT NULL,\n"
        + "cityid  varchar(20) NOT NULL\n"
        + ")";
    //城市表
    private String TABLE_CITY = "CREATE TABLE city(\n"
        + "id Integer AUTO_INCREMENT PRIMARY KEY NOT NULL ,\n"
        + "cityid varchar(20) NOT NULL ,\n"
        + "city varchar(50) NOT NULL ,\n"
        + "provinceid varchar(20) NOT NULL\n"
        + ")";
    //省份表
    private String TABLE_PROVINCE = "CREATE TABLE province(\n"
        + "id Integer AUTO_INCREMENT PRIMARY KEY NOT NULL ,\n"
        + "provinceid varchar(20) NOT NULL,\n"
        + "province varchar(50)  NOT NULL \n"
        + ") ";
    //用户表
    private String TABLE_USER = "CREATE TABLE user(\n"
        + "id Integer AUTO_INCREMENT PRIMARY KEY ,\n"
        + "userCode varchar(50) NOT NULL,\n"
        + "userRealName varchar(50)  DEFAULT NULL, \n"
        + "userName varchar(20) NOT NULL,\n"
        + "passWord varchar(20) NOT NULL\n"
        + ")";

    //调查问卷表(respondent:被调查者)
    private String TABLE_QUESTION = "CREATE TABLE question (\n"
        + "id Integer AUTO_INCREMENT PRIMARY KEY NOT NULL ,\n"
        + "questionCode varchar(50) NULL,\n"
        + "sbp Integer NOT NULL DEFAULT 0,\n"
        + "dbp Integer NOT NULL DEFAULT 0 ,\n"
        + "kfy Integer NOT NULL DEFAULT 0 ,\n"
        + "r_province varchar(30) NULL ,\n"
        + "r_name varchar(30) NULL ,\n"
        + "r_phone varchar(30) NULL ,\n"
        + "r_age Integer NOT NULL ,\n"
        + "r_sex Integer NOT NULL ,\n"
        + "r_height Integer NOT NULL, \n"
        + "r_weight Integer NOT NULL ,\n"
        + "hypertension varchar(100) DEFAULT NULL,\n"
        + "hypotensor varchar(100) DEFAULT NULL,\n"
        + "appetite varchar(30) NULL ,\n"
        + "appetiteScore Integer NOT NULL,\n"
        + "salty varchar(30) NULL ,\n"
        + "saltyScore Integer NOT NULL,\n"
        + "lunch varchar(30) NULL ,\n"
        + "lunchScore Integer NOT NULL,\n"
        + "supper varchar(30) NULL ,\n"
        + "supperScore Integer NOT NULL,\n"
        + "water varchar(30) NULL ,\n"
        + "waterScore Integer NOT NULL,\n"
        + "syoyu varchar(30) NULL ,\n"
        + "syoyuScore Integer NOT NULL,\n"
        + "flavour varchar(30) NULL,\n"
        + "flavourScore Integer NOT NULL,\n"
        + "paocai varchar(30) NULL ,\n"
        + "paocaiScore Integer NOT NULL,\n"
        + "noodles varchar(30) NOT NULL,\n"
        + "noodlesScore Integer NOT NULL,\n"
        + "egg varchar(30) NOT NULL , \n"
        + "eggScore Integer NOT NULL,\n"
        + "ham varchar(30) NOT NULL ,\n"
        + "hamScore Integer NOT NULL,\n"
        + "meat varchar(30) NOT NULL , \n"
        + "meatScore Integer NOT NULL,\n"
        + "snacks varchar(30) NOT NULL , \n"
        + "snacksScore Integer NOT NULL,\n"
        + "flag Integer DEFAULT 0\n"
        + ")";

    //用户和调查问卷关系
    private String TABLE_USER2QUESTION = "CREATE TABLE user2question(\n"
        + "id Integer AUTO_INCREMENT PRIMARY KEY NOT NULL,\n"
        + "userCode varchar(50) NOT NULL ,\n"
        + "questionCode varchar(50) NOT NULL\n"
        + ")";

    private Context mContext;

    public DBHelper(Context context) {
        super(context, "kongyan.db", null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER);
        db.execSQL(TABLE_QUESTION);
        db.execSQL(TABLE_USER2QUESTION);
        db.execSQL(TABLE_AREA);
        db.execSQL(TABLE_CITY);
        db.execSQL(TABLE_PROVINCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 查省
     */
    public ArrayList<City> queryProvince() {
        ArrayList<City> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("province", null, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String provinceName = c.getString(c.getColumnIndex("province"));
            String provinceCode = c.getString(c.getColumnIndex("provinceid"));
            City city = new City(id, provinceName, provinceCode);
            list.add(city);
        }
        closeResource(c, db);
        return list;
    }

    /**
     * 查城市
     *
     * @return public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
     * throw new RuntimeException("Stub!");
     * }
     */
    public ArrayList<City> queryCity(String provinceid) {
        ArrayList<City> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("city", null, "provinceid=?", new String[] { provinceid }, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String cityName = c.getString(c.getColumnIndex("city"));
            String cityCode = c.getString(c.getColumnIndex("cityid"));
            String cityParent = c.getString(c.getColumnIndex("provinceid"));
            City city = new City(id, cityName, cityCode, cityParent);
            list.add(city);
        }
        closeResource(c, db);
        return list;
    }

    /**
     * 查地区
     */
    public ArrayList<City> queryArea(String cityid) {
        ArrayList<City> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("area", null, "cityid=?", new String[] { cityid }, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String areaName = c.getString(c.getColumnIndex("area"));
            String areaCode = c.getString(c.getColumnIndex("areaid"));
            String areaParent = c.getString(c.getColumnIndex("cityid"));
            City city = new City(id, areaName, areaCode, areaParent);
            list.add(city);
        }
        closeResource(c, db);
        return list;
    }

    private void closeResource(Cursor c, SQLiteDatabase db) {
        if (null != c) {
            c.close();
        }
        if (null != db) {
            db.close();
        }
    }

    /**
     * 检查用户是否存在
     */
    //    public User getUser(String user_name, String pwd) {
    //        SQLiteDatabase db = getReadableDatabase();
    //        User user = null;
    //        Cursor c = db.query("user", null, "userName=? and passWord=?", new String[]{user_name, pwd}, null, null, null, null);
    //        if (c.moveToNext()) {
    //            int id = c.getInt(c.getColumnIndex("id"));
    //            String userCode = c.getString(c.getColumnIndex("userCode"));
    //            String userRealName = c.getString(c.getColumnIndex("userRealName"));
    //            String userName = c.getString(c.getColumnIndex("userName"));
    //            String passWord = c.getString(c.getColumnIndex("passWord"));
    //            user = new User(id, userCode, userRealName, userName, passWord);
    //        }
    //        closeResource(c, db);
    //        return user;
    //    }

    /**
     * 添加用户
     */
    public boolean addUser(String userCode, String userRealName, String userName, String passWord) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userCode", userCode);
        values.put("userRealName", userRealName);
        values.put("userName", userName);
        values.put("passWord", passWord);
        long result = db.insert("user", "id", values);
        //        db.execSQL("INSERT into user('id','userCode','userRealName','userName','passWord') values(0,"+userCode+","+userRealName+","+userName+","+passWord+");");

        closeResource(null, db);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 添加调查问卷
     */
    //    public boolean addQuestion(Question question) {
    //        String questionCode = question.getQuestionCode();
    //        String sbp = question.getSbp();
    //        String dbp = question.getDbp();
    //        int kfy = question.getKfy();//口服盐编号
    //        String r_province = question.getR_province();
    //        String r_name = question.getR_name();
    //        String r_phone = question.getR_phone();
    //        int r_age = question.getR_age();
    //        int r_sex = question.getR_sex();
    //        int r_height = question.getR_height();
    //        int r_weight = question.getR_weight();
    //        String hypertension = question.getHypertension();//是否高血压患者
    //        String hypotensor = question.getHypotensor();//目前是否服用降压药
    //        String appetite = question.getAppetite();//饭量
    //        int appetiteScore = question.getAppetiteScore();//
    //        String salty = question.getSalty();//盐量
    //        int saltyScore = question.getSaltyScore();
    //        String lunch = question.getLunch();//盐量
    //        int lunchScore = question.getLunchScore();
    //        String supper = question.getSupper();//盐量
    //        int supperScore = question.getSupperScore();
    //        String water = question.getWater();//盐量
    //        int waterScore = question.getWaterScore();
    //        String syoyu = question.getSyoyu();//酱油
    //        int syoyuScore = question.getSyoyuScore();
    //        String flavour = question.getFlavour();//调料
    //        int flavourScore = question.getFlavourScore();
    //        String paocai = question.getPaocai();//泡菜
    //        int paocaiScore = question.getPaocaiScore();
    //        String noodles = question.getNoodles();
    //        int noodlesScore = question.getNoodlesScore();
    //        String egg = question.getEgg();
    //        int eggScore = question.getEggScore();
    //        String ham = question.getHam();
    //        int hamScore = question.getHamScore();
    //        String meat = question.getMeat();
    //        int meatScore = question.getMeatScore();
    //        String snacks = question.getSnacks();
    //        int snacksScore = question.getSnacksScore();
    //        int flag = question.getFlag();
    //        SQLiteDatabase db = getWritableDatabase();
    //        ContentValues values = new ContentValues();
    //        values.put("questionCode", questionCode);
    //        values.put("sbp", sbp);
    //        values.put("dbp", dbp);
    //        values.put("kfy", kfy);
    //        values.put("r_province", r_province);
    //        values.put("r_name", r_name);
    //        values.put("r_phone", r_phone);
    //
    //        values.put("r_age", r_age);
    //        values.put("r_sex", r_sex);
    //        values.put("r_height", r_height);
    //        values.put("r_weight", r_weight);
    //
    //        values.put("hypertension", hypertension);
    //        values.put("hypotensor", hypotensor);
    //        values.put("appetite", appetite);
    //        values.put("appetiteScore", appetiteScore);
    //
    //        values.put("salty", salty);
    //        values.put("saltyScore", saltyScore);
    //        values.put("lunch", lunch);
    //        values.put("lunchScore", lunchScore);
    //
    //        values.put("supper", supper);
    //        values.put("supperScore", supperScore);
    //        values.put("water", water);
    //        values.put("waterScore", waterScore);
    //
    //        values.put("syoyu", syoyu);
    //        values.put("syoyuScore", syoyuScore);
    //        values.put("flavour", flavour);
    //        values.put("flavourScore", flavourScore);
    //
    //        values.put("paocai", paocai);
    //        values.put("paocaiScore", paocaiScore);
    //        values.put("noodles", noodles);
    //        values.put("noodlesScore", noodlesScore);
    //
    //        values.put("egg", egg);
    //        values.put("eggScore", eggScore);
    //        values.put("ham", ham);
    //        values.put("hamScore", hamScore);
    //        values.put("meat", meat);
    //        values.put("meatScore", meatScore);
    //
    //        values.put("snacks", snacks);
    //        values.put("snacksScore", snacksScore);
    //        values.put("flag", flag);
    //
    //        long result = db.insert("question", null, values);
    //        if (result > 0) {
    //            return true;
    //        }
    //        return false;
    //    }

    /**
     * 添加调查问卷和用户关系
     */
    public boolean addUser2Question(String questionCode, String userCode) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userCode", userCode);
        values.put("questionCode", questionCode);
        long result = db.insert("user2question", null, values);
        if (result > 0) {
            return true;
        }
        return false;
    }
}