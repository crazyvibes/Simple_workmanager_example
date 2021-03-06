package in.waycool.simple_workmanager_example;

import android.content.Context;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DemoWorker extends Worker {

    public static final String KEY_WORKER="key_worker";

    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

//    @NonNull    //simple
//    @Override
//    public Result doWork() {
//
//        for(int i=0;i<100000;i++){
//            Log.i("MYTAG"," Count is "+i);
//        }
//
//        return Result.success();
//    }

    @NonNull           //with get data
    @Override
    public Result doWork() {

        Data data=getInputData();
        int countLimit=data.getInt(MainActivity.KEY_COUNT_VALUE,0);


        for(int i=0;i<countLimit;i++){
            Log.i("MYTAG"," Count is "+i);
        }

        Data dataToSend=new Data.Builder()
                .putString(KEY_WORKER,"Task Done Success")
                .build();

        return Result.success(dataToSend);
    }
}

       /**------------------------Start Implimenting hints----------------------------

        -Require compileSdkVersion 28 or higher.

        -I am selecting a basic activity. Now let’s add dependencies for work manager. WorkManager requires compileSdk version
        twenty eight or higher.


        def work_version = "1.0.0-rc02"
        implementation "android.arch.work:work-runtime:$work_version"

        -If it is not 28 in your project you should change it to 28.


        -To execute background tasks we need to create a subclass of Worker class. Create a new Java Class.

        -I am naming it as DemoWorker.

        -Superclass should be worker.

        -Now, let’s generate , overridden doWork method and the constructor of this class.

        -This doWork method is there to implement to background task we need to perform.

        -It can be sending a request to a remote server and download some data.

        -It can be uploading backups to a backup server.

        -Or It can be showing a notification. But for now just to show how this works.

        -Here in the doWork() method I am writing a for loop which will log a number for 100000 times.

        -After the execution of the task you should return the success result.
        So, let’s change this to Result.SUCCESS .

        Let’s go to main activity now. In the onCreate method, I am going to build a one time work request
        using work manager’s OneTimeWorkRequest class.

        -And in this floating action button’s onClick method, I am going to write codes to execute the background task
        using that request final OneTimeWorkRequest new OneTimeWorkRequest.Builder

        -Here You should give the worker class name. DemoWorker.class Then . Invoke build() to build the request.

        -Now WorkManager.getInstance().enqueue pass the request we created here.

        -Now, let’s run the app and see how this works.

        -We can see the log results. You can see background task is running. While

        -this background task is running, user can move to other views of this app and do different things without interrupting



        -So, What you learnt during this lesson. To do a background task using work manager

        -we need to create a sub class of the Worker class. In the Worker class we have doWork method.

        -We write code to implement the task in the doWork() method.

        -In the MainActivity We created a OneTimeWorkRequest for the Worker class.

        -We invoked the enqueue method of the WorkManager to execute the background task.**/
