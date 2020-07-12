package in.waycool.simple_workmanager_example;

import android.content.Context;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DemoWorker extends Worker {

    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        for(int i=0;i<100000;i++){
            Log.i("MYTAG"," Count is "+i);
        }

        return Result.success();
    }
}
