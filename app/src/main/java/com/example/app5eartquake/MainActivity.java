package com.example.app5eartquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;
import com.example.app5eartquake.databinding.ActivityMainBinding;

import org.intellij.lang.annotations.JdkConstants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel viewModel= new ViewModelProvider(this).get(MainViewModel.class);

        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));


        //Carga de datos
        EqAdapter adapter = new EqAdapter();

        adapter.setOnItemClickListener(earthquake -> abrir(earthquake.getId(),earthquake.getPlace(),earthquake.getMagnitude(), earthquake.getTime(), earthquake.getLatidude(),earthquake.getLongitude()));





        binding.eqRecycler.setAdapter(adapter);




        viewModel.getEqList().observe(this,eqList ->{
            adapter.submitList(eqList);
            mayormag(eqList);
            for (int i=0; i<eqList.size(); i++){
                if(eqList.get(i).getMagnitude()== mayormag(eqList)){
                    int rotacion = getWindowManager().getDefaultDisplay().getRotation();
                    if (rotacion == Surface.ROTATION_0 ) {

                    }else{
                        binding.txtFechahora.setText(eqList.get(i).getTime()+"");
                        binding.txtLatitude.setText(eqList.get(i).getLatidude()+"");
                        binding.txtLongitud.setText(eqList.get(i).getLongitude()+"");
                        binding.txtMagnitud.setText(eqList.get(i).getMagnitude()+"");
                        binding.txtUbicacion.setText(eqList.get(i).getPlace());
                    }


                }
            }
            if(eqList.isEmpty()){
                binding.emptyView.setVisibility(View.VISIBLE);
            }else{
                binding.emptyView.setVisibility(View.GONE);

            }
        });
        viewModel.getEarthquakes();

    }
    public double mayormag(List<Earthquake> eqlist){
        double  mayor;
        mayor=eqlist.get(0).getMagnitude();
        for (int i = 0; i<eqlist.size(); i++){
            if(eqlist.get(i).getMagnitude()> mayor){
                mayor=eqlist.get(i).getMagnitude();
            }
        }
        System.out.println(mayor);
        return mayor;
    }
    public void abrir(String id, String place, double magnitude, long time, double lat, double longi){
        Toast.makeText(MainActivity.this,place , Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, Monitor.class);
        intent.putExtra("id", id);
        intent.putExtra("place", place);
        intent.putExtra("magn", magnitude);
        intent.putExtra("time", time);
        intent.putExtra("lat", lat);
        intent.putExtra("longitud", longi);
        startActivity(intent);
    }



}