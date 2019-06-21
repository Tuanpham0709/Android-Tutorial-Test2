package com.example.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.carmanager.R;
import com.example.carmanager.model.Car;

import java.util.ArrayList;

public class CarAdapter extends ArrayAdapter {
    ArrayList<Car> listCar;
    private int resource;
    private Context context;
    public CarAdapter(Context context, int resource, ArrayList<Car> objects) {
        super(context, resource, objects);
        this.listCar = objects;
        this.resource = resource;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarViewHolder viewHolder ;
        if(convertView == null){
            viewHolder = new CarViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_car, parent, false);
            viewHolder.tvID = convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
            viewHolder.tvYear = convertView.findViewById(R.id.tv_year);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CarViewHolder) convertView.getTag();
        }
        Car car = listCar.get(position);
        viewHolder.tvID.setText(car.getmId()+"");
        viewHolder.tvName.setText(car.getmName());
        viewHolder.tvPrice.setText(car.getmPrice()+"");
        viewHolder.tvYear.setText(car.getmYear()+"");
        return convertView;

    }

    private class CarViewHolder{
        TextView tvID, tvName, tvPrice, tvYear;

    }
}
