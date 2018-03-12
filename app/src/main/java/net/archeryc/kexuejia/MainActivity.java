package net.archeryc.kexuejia;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final float PER_X = 0.859f;

    private static final float PER_Y = 0.805f;

    private static final float PER_REWARD_X = 0.660f;

    private static final float PER_REWARD_Y = 0.688f;

    private int targetX;

    private int targetY;

    private int targetRewardX;

    private int targetRewardY;

    private ScheduledExecutorService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShellUtils.upgradeRootPermission(getPackageCodePath());
        targetX = (int) (getResources().getDisplayMetrics().widthPixels * PER_X);
        targetY = (int) (getResources().getDisplayMetrics().heightPixels * PER_Y);

        targetRewardX= (int) (getResources().getDisplayMetrics().widthPixels*PER_REWARD_X);
        targetRewardY= (int) (getResources().getDisplayMetrics().heightPixels*PER_REWARD_Y);
    }

    public void start(View view) {
        if (service != null) {
            service.shutdown();
        }
        service = new ScheduledThreadPoolExecutor(1);
        service.scheduleAtFixedRate(new SingleTapTask(), 0, 2000, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(new TapRewardTask(), 0, 5000, TimeUnit.MILLISECONDS);
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn=new ComponentName("com.netease.onmyoji",
                "com.netease.onmyoji.Launcher");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
    }

    class SingleTapTask implements Runnable {

        @Override
        public void run() {
            ShellUtils.singleTap(targetX, targetY);
        }
    }

    class TapRewardTask implements Runnable{

        @Override
        public void run() {
            ShellUtils.singleTap(targetRewardX,targetRewardY);
        }
    }
}
