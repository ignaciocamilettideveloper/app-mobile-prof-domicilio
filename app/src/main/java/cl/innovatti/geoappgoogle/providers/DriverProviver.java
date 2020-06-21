package cl.innovatti.geoappgoogle.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cl.innovatti.geoappgoogle.model.Driver;

public class DriverProviver {

DatabaseReference mDatabase;

    public DriverProviver() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
    }


    public Task<Void> create(Driver driver){
    return mDatabase.child(driver.getId()).setValue(driver);
    }

}
