package com.app.vtn.fastfoodcalorie;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class FoodDatabase extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = FoodDatabase.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "managefood";


    // Table Names
    private static final String TABLE_RESTAURANT = "restaurant";
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_RESTAURANT_FOOD = "restaurant_food";

    // Common column names restaurant
    private static final String KEY_RESTAURANT = "id";
    private static final String KEY_RESTAURANT_NAME = "restaurant_name";

    // Food Table - column names
    private static final String KEY_FOOD = "food_id";
    private static final String KEY_FOOD_NAME= "food_name";
    private static final String KEY_FOOD_GROUP="groupfood";
    private static final String KEY_FOOD_Calories="calories";
    private static final String KEY_FOOD_Total_Fat="total_fat";
    private static final String KEY_FOOD_Protein="protein";
    private static final String KEY_FOOD_Vitamin="vitamin";
    private static final String KEY_FOOD_Iron="iron";


    // RESTAURANT_FOOD Table - column names
    private static final String KEY_RESTAURANT_ID = "restaurant_id";
    private static final String KEY_FOOD_ID = "food_id";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE "
            + TABLE_RESTAURANT + "(" + KEY_RESTAURANT + " INTEGER PRIMARY KEY," + KEY_RESTAURANT_NAME
            + " TEXT )";

    // Tag table create statement
    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD
            + "(" + KEY_FOOD + " INTEGER PRIMARY KEY," + KEY_FOOD_NAME + " TEXT,"
            + KEY_FOOD_GROUP + " TEXT,"+ KEY_FOOD_Calories + " INTEGER," +
            KEY_FOOD_Total_Fat + " INTEGER,"+ KEY_FOOD_Protein + " INTEGER,"
            + KEY_FOOD_Vitamin + " INTEGER,"+ KEY_FOOD_Iron + " INTEGER )";

    // todo_tag table create statement
    private static final String CREATE_TABLE_RESTAURANT_FOOD = "CREATE TABLE "
            + TABLE_RESTAURANT_FOOD + "(" + KEY_RESTAURANT_ID + " INTEGER ,"
            + KEY_FOOD_ID + " INTEGER )";
    private Context dbContext;
    private SQLiteDatabase dataBase;
    public FoodDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //onCreate(dataBase);
        this.dbContext = context;

    }

    //end new code

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_RESTAURANT_FOOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_FOOD);

        // create new tables
        onCreate(db);
    }

    // ------------------------ "todos" table methods ----------------//

    /**
     * Creating a todo
     */
    public long createRestaurant(Restaurant res, long[] food_ids) {

        SQLiteDatabase db = this.getWritableDatabase();
        //SQLiteDatabase db = dataBase;

        ContentValues values = new ContentValues();
        values.put(KEY_RESTAURANT_NAME, res.getName());

        // insert row
        long restaurant_id = db.insert(TABLE_RESTAURANT, null, values);

        // insert tag_ids
        for (long food_id : food_ids) {
            createRestaurantFood(restaurant_id, food_id);
        }

        return restaurant_id;
    }

    /**
     * get single todo
     */
    public Restaurant getRestaurant(long restaurant_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE "
                + KEY_RESTAURANT + " = " + restaurant_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Restaurant res=new Restaurant();
        res.setId(c.getInt(c.getColumnIndex(KEY_RESTAURANT)));
        res.setName((c.getString(c.getColumnIndex(KEY_RESTAURANT_NAME))));


        return res;
    }

    /**
     * getting all todos
     * */
    public List<Restaurant> getAllRestaurant() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Restaurant res=new Restaurant();
                res.setId(c.getInt(c.getColumnIndex(KEY_RESTAURANT)));
                res.setName((c.getString(c.getColumnIndex(KEY_RESTAURANT_NAME))));

                // adding to todo list
                restaurants.add(res);
            } while (c.moveToNext());
        }

        return restaurants;
    }

    /**
     * getting all todos under single tag
     * */
    public List<Food> getAllFoodByRestaurant(String restaurant_name) {
        List<Food> foods = new ArrayList<Food>();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " td, "
                + TABLE_FOOD + " tg, " + TABLE_RESTAURANT_FOOD + " tt WHERE td."
                + KEY_RESTAURANT_NAME + " = '" + restaurant_name + "'" + " AND td." + KEY_RESTAURANT
                + " = " + "tt." + KEY_RESTAURANT_ID + " AND tg." + KEY_FOOD + " = "
                + "tt." + KEY_FOOD_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(c.getInt((c.getColumnIndex(KEY_FOOD))));
                food.setFoodName((c.getString(c.getColumnIndex(KEY_FOOD_NAME))));
                food.food_group=c.getString(c.getColumnIndex(KEY_FOOD_GROUP));
                food.Calories=c.getInt(c.getColumnIndex(KEY_FOOD_Calories));
                food.Total_Fat=c.getInt(c.getColumnIndex(KEY_FOOD_Total_Fat));
                food.Protein=c.getInt(c.getColumnIndex(KEY_FOOD_Protein));
                food.Vitamin=c.getInt(c.getColumnIndex(KEY_FOOD_Vitamin));
                food.Iron=c.getInt(c.getColumnIndex(KEY_FOOD_Iron));


                // adding to todo list
                foods.add(food);
            } while (c.moveToNext());
        }

        return foods;
    }

    /**
     * getting todo count
     */
    public int getRestaurantCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating a todo
     */
    public int updateRestaurant(Restaurant res) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RESTAURANT_NAME, res.getName());


        // updating row
        return db.update(TABLE_RESTAURANT, values, KEY_RESTAURANT + " = ?",
                new String[] { String.valueOf(res.getId()) });
    }

    /**
     * Deleting a todo
     */
    public void deleteToDo(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANT, KEY_RESTAURANT + " = ?",
                new String[] { String.valueOf(tado_id) });
    }

    // ------------------------ "tags" table methods ----------------//

    /**
     * Creating tag
     */
    public long createFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_NAME, food.getFoodName());
        values.put(KEY_FOOD_GROUP, food.food_group);
        values.put(KEY_FOOD_Calories, food.Calories);
        values.put(KEY_FOOD_Total_Fat, food.Total_Fat);
        values.put(KEY_FOOD_Protein, food.Protein);
        values.put(KEY_FOOD_Vitamin, food.Vitamin);
        values.put(KEY_FOOD_Iron, food.Iron);

        // insert row
        long tag_id = db.insert(TABLE_FOOD, null, values);

        return tag_id;
    }

    /**
     * getting all tags
     * */
    public List<Food> getAllFoods() {
        List<Food> foods = new ArrayList<Food>();
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Food f = new Food();
                f.setId(c.getInt((c.getColumnIndex(KEY_FOOD))));
                f.setFoodName(c.getString(c.getColumnIndex(KEY_FOOD_NAME)));

                // adding to tags list
                foods.add(f);
            } while (c.moveToNext());
        }
        return foods;
    }

    /**
     * Updating a tag
     */
    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_NAME, food.getFoodName());

        // updating row
        return db.update(TABLE_FOOD, values, KEY_FOOD + " = ?",
                new String[] { String.valueOf(food.getId()) });
    }

    /**
     * Deleting a tag
     */
    public long createRestaurantFood(long restaurant_id, long food_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RESTAURANT_ID, restaurant_id);
        values.put(KEY_FOOD_ID, food_id);


        long id = db.insert(TABLE_RESTAURANT_FOOD, null, values);

        return id;
    }

    /**
     * Updating a todo tag
     */

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
