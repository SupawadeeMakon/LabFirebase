package piw.ruts.ac.th.labfirebase;

import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference LED, Refer,Refer1;
    public static final String TAG ="LEDs Control";
    public Button Switch;
    public  Integer Value1;
    public Integer Value0;
    public String Data;
    public TextView textView1,textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //เรียก database References ให้ตรงกับที่อยู่บน firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        LED = firebaseDatabase.getReference("LED_Switch");
        Refer = firebaseDatabase.getReference();
        Refer1 = firebaseDatabase.getReference();
        //Value = firebaseDatabase.getReference("Control/Switch/LED1");


        Switch = (Button) findViewById(R.id.Led1);
        textView1 = (TextView) findViewById(R.id.txtView);

        textView2 = (TextView) findViewById(R.id.txtView2);


        ///Read Data
        Refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                Data = String.valueOf(map.get("LED_Switch"));
                textView1.setText(Data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        Refer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                Data = String.valueOf(map.get("Name"));
                textView2.setText(Data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        ///End Read Data


        ///Control Switch
        LED.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Value1 = dataSnapshot.getValue(Integer.class);


                Log.d(TAG, "Value is Value1-->" + Value1);
                if (Value1==1){
                    Switch.setText("LED1_ON");
                    Value0=0;
                    Log.d(TAG, "Value is Value0" + Value0);
                }
                else {
                    Switch.setText("LED1_OFF");
                    Value0=1;
                    Log.d(TAG, "Value is Value0" + Value0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.w(TAG, "Failed", databaseError.toException());

            }
        });
        ///End Control Switch





        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LED.setValue(Value0);
            }
        });




    }
}
