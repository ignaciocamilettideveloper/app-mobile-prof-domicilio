package cl.innovatti.geoappgoogle.includes;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cl.innovatti.geoappgoogle.R;

public class MyToolbar {

    public  static void show(AppCompatActivity activity, String titulo, boolean upButton){
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(titulo);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
