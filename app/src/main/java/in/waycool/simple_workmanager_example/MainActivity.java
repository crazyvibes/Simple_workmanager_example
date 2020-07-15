package in.waycool.simple_workmanager_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_COUNT_VALUE = "key_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView tvWorkStatus = (TextView) findViewById(R.id.tvWorkStatus);
        setSupportActionBar(toolbar);


        ///////////////////////adding constraints////////////////////

        Data data = new Data.Builder()
                .putInt(KEY_COUNT_VALUE, 1750)    //sending data to work manager
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)  // we can have more than one constraints
                // .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        ///////////////////////


        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DemoWorker.class) //with constraints
                .setInputData(data) //passing data
                .setConstraints(constraints)
                .build();

//        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DemoWorker.class) //without constraints
//                .build();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }
        });


        /**to see status update*/

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {                         //simple
                            tvWorkStatus.setText(workInfo.getState().name());
                        }


                        if (workInfo.getState().isFinished())     //get success data here
                        {
                            Data data1 = workInfo.getOutputData();
                            String message = data1.getString(DemoWorker.KEY_WORKER);
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}