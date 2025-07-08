package com.shuvo.ttit.bridgeculvert.userChoice;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.shuvo.ttit.bridgeculvert.R;
import com.shuvo.ttit.bridgeculvert.login.Login;
import com.shuvo.ttit.bridgeculvert.mainmenu.HomePage;


public class ChooseUser extends AppCompatActivity {

    LinearLayout guest;
    LinearLayout admin;
    AppUpdateManager appUpdateManager;

    ActivityResultLauncher<IntentSenderRequest> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
                    result -> {
                        if (result.getResultCode() != RESULT_OK) {

                            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(ChooseUser.this)
                                    .setTitle("Update Failed!")
                                    .setMessage("Failed to update the app. Please retry again.")
                                    .setIcon(R.drawable.bd_icon)
                                    .setPositiveButton("Retry", (dialog, which) -> getAppUpdate())
                                    .setNegativeButton("Cancel", (dialog, which) -> finish());
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        View navScrim = findViewById(R.id.nav_bar_chose_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chose_user_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            ViewGroup.LayoutParams lp = navScrim.getLayoutParams();
            lp.height = systemBars.bottom;
            navScrim.setLayoutParams(lp);
            return insets;
        });

        guest = findViewById(R.id.guest_button);
        admin = findViewById(R.id.admin_button);
        appUpdateManager = AppUpdateManagerFactory.create(ChooseUser.this);

        guest.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseUser.this, HomePage.class);
            intent.putExtra("USER","GUEST");
            startActivity(intent);
        });

        admin.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseUser.this, Login.class);
            startActivity(intent);
        });

        getAppUpdate();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseUser.this);
                builder.setTitle("EXIT!")
                        .setMessage("Do you want to Exit?")
                        .setPositiveButton("YES", (dialog, which) -> System.exit(0))
                        .setNegativeButton("NO", (dialog, which) -> {

                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void getAppUpdate() {
        System.out.println("HELLO1");
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE))  {
//                try {
//                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
//                            (IntentSenderForResultStarter) activityResultLauncher,
//                            AppUpdateOptions
//                                    .newBuilder(IMMEDIATE)
//                                    .build(),
//                            10101);
//                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
//                            Dashboard.this,AppUpdateOptions.newBuilder(IMMEDIATE).build(),
//                            101010);
//                } catch (IntentSender.SendIntentException e) {
//                    e.printStackTrace();
//                }
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                        activityResultLauncher, AppUpdateOptions
                                .newBuilder(IMMEDIATE)
                                .build());
            }
            else {
                System.out.println("No update available");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
//                                try {
//                                    appUpdateManager.startUpdateFlowForResult(
//                                            appUpdateInfo,
//                                            this,
//                                            AppUpdateOptions.defaultOptions(IMMEDIATE),
//                                            10101);
//                                } catch (IntentSender.SendIntentException e) {
//                                    e.printStackTrace();
//                                }
                                appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                                        activityResultLauncher,AppUpdateOptions
                                                .newBuilder(IMMEDIATE)
                                                .build());
                            }
                        });
    }
}