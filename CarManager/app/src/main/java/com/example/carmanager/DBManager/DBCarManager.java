package com.example.carmanager.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.carmanager.model.Car;

import java.time.Year;
import java.util.ArrayList;

public class DBCarManager extends SQLiteOpenHelper {
    private  String TAG = "CarDBManager";
    private static final  String DATABASE_NAME = "DataCar";
    private static final String TABLE_NAME = "car_info";
    private static final String ID = "id";
    private static final String NAME_CAR = "name";
    private static final String PRICE = "price";
    private static final String YEAR = "year";
    private String SQLQUERY_CAR = " CREATE TABLE " + TABLE_NAME + " ( "  +
            ID +" integer primary key, " +
            NAME_CAR + " nvarchar, " +
            PRICE + " float, " +
            YEAR + " integer )";
    private static int VERSION = 1;
    public DBCarManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQUERY_CAR);
        Log.e(TAG, "onCreate Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addCar(Car car){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, car.getmId());
        values.put(NAME_CAR, car.getmName());
        values.put(PRICE, car.getmPrice());
        values.put(YEAR, car.getmYear());
        db.insert(TABLE_NAME, null,values);
        db.close();
        Log.e(TAG, "Add Success");
    }
    public ArrayList<Car> getAllCar(){
        ArrayList<Car> listCar = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Car car = new Car();
                car.setmId(cursor.getInt(0));
                car.setmName(cursor.getString(1));
                car.setmPrice(cursor.getFloat(2));
                car.setmYear(cursor.getInt(3));
                listCar.add(car);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return listCar;
    }
    public int deleteCar(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int reusult = db.delete(TABLE_NAME, ID+"=?" , new String[]{String.valueOf(id)});
        db.close();
        return reusult;
    }
}
