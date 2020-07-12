package in.waycool.simple_workmanager_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView tvWorkStatus = (TextView) findViewById(R.id.tvWorkStatus);
        setSupportActionBar(toolbar);


        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DemoWorker.class)
                .build();

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
                        if (workInfo != null) {
                            tvWorkStatus.setText(workInfo.getState().name());
                        }
                    }
                });
    }
}