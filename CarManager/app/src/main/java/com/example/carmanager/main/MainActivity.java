package com.example.carmanager.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.carmanager.DBManager.DBCarManager;
import com.example.carmanager.R;
import com.example.carmanager.adapter.CarAdapter;
import com.example.carmanager.model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ArrayList<Car> listCar;
    Button btnFind, btnAdd, btnSortPrice, btnSortYear, btnShowAll;
    EditText edtFind, edtID, edtName, edtPrice, edtYear;
    ListView lvListCar;
    AlertDialog dialog;
    DBCarManager dbCarManager;
    View alertLayout;
    CarAdapter carAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findCar();
            }
        });
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configListView(dbCarManager.getAllCar());
            }
        });
        dbCarManager = new DBCarManager(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddCar();
                configListView(dbCarManager.getAllCar());
            }
        });
        configListView(dbCarManager.getAllCar());
        deleteCar();
        btnSortPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortByPrice();
                configListView(listCar);
            }
        });
        btnSortYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortByYear();
                configListView(listCar);
            }
        });



    }
    void sortByPrice(){
        listCar = dbCarManager.getAllCar();
        Collections.sort(listCar, new Comparator<Car>() {
            @Override
            public int compare(Car car, Car t1) {
                if(car.getmPrice() > t1.getmPrice()){
                    return 1;
                }
                if(car.getmPrice() < t1.getmPrice()){
                    return -1;
                }
                return 0;
            }
        });
    }

    void findCar(){
        ArrayList<Car> listCarFind = new ArrayList<>();
        listCar = dbCarManager.getAllCar();
        for(Car c:listCar){
            if ((edtFind.getText().toString()).equalsIgnoreCase(String.valueOf(c.getmId()))
                    || edtFind.getText().toString().equalsIgnoreCase(c.getmName())
                    || edtFind.getText().toString().equalsIgnoreCase(String.valueOf((int)Math.round(c.getmPrice()))) ){
                listCarFind.add(c);
            }
        }
        if(listCarFind.size() > 0 ){
            configListView(listCarFind);
        }else{
            Toast.makeText(this, "Deo tim thay car nao", Toast.LENGTH_SHORT).show();
        }
    }
    void sortByYear(){
        listCar = dbCarManager.getAllCar();
        Collections.sort(listCar, new Comparator<Car>() {
            @Override
            public int compare(Car car, Car t1) {
                if(car.getmYear() > t1.getmYear()){
                    return -1;
                }
                if(car.getmYear() < t1.getmYear()){
                    return 1;
                }
                return 0;
            }
        });
    }
    void deleteCar(){
        lvListCar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final int n = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Ban co muon xoa khong");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Car car;
                        listCar = dbCarManager.getAllCar();
                        car = listCar.get(n);
                        listCar.remove(n);
                        dbCarManager.deleteCar(car.getmId());
                        configListView(dbCarManager.getAllCar());
                        Toast.makeText(MainActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }
    void configListView(ArrayList<Car> list){
        carAdapter  = new CarAdapter(this, R.layout.list_item_car,list);
        lvListCar.setAdapter(carAdapter);
        carAdapter.notifyDataSetChanged();
    }

    private void dialogAddCar() {
        LayoutInflater inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.dialog_add_car, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        initDialog();
        alert.setTitle("ADD CAR");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("Add Car", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog = alert.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkEmptyString()) {
                    if (checkValid(edtID.getText().toString()) && checkValid(edtPrice.getText().toString()) && checkValid(edtYear.getText().toString())) {
                        Car carDialogg = new Car();
                        carDialogg.setmId(Integer.parseInt(edtID.getText().toString()));
                        carDialogg.setmName(edtName.getText().toString());
                        carDialogg.setmPrice(Float.parseFloat(edtPrice.getText().toString()));
                        carDialogg.setmYear(Integer.parseInt(edtYear.getText().toString()));
                        dbCarManager.addCar(carDialogg);
                        configListView(dbCarManager.getAllCar());
                        Toast.makeText(MainActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(MainActivity.this, "ID, Price, Year phai la chu so", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(MainActivity.this, "Khong duoc bi trong cac o nhap", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public boolean checkValid(String value) {
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    private boolean checkEmptyString() {
        if (edtYear.getText().length() == 0 || edtPrice.getText().length() == 0 || edtName.getText().length() == 0 || edtID.getText().length() == 0) {
            return false;
        }
        return true;
    }

    void initDialog() {
        edtID = alertLayout.findViewById(R.id.edt_id);
        ;
        edtName = alertLayout.findViewById(R.id.edt_name);
        edtPrice = alertLayout.findViewById(R.id.edt_price);
        edtYear = alertLayout.findViewById(R.id.edt_year);
    }

    void init() {
        btnShowAll = findViewById(R.id.btn_show_all);
        btnAdd = findViewById(R.id.btn_add_car);
        btnFind = findViewById(R.id.btn_find);
        btnSortPrice = findViewById(R.id.btn_sort_by_price);
        btnSortYear = findViewById(R.id.btn_sort_by_year);
        edtFind = findViewById(R.id.edt_find);
        lvListCar = findViewById(R.id.lv_list_car);
    }
}
