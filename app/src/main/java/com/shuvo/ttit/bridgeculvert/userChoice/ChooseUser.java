package com.shuvo.ttit.bridgeculvert.userChoice;
import static com.shuvo.ttit.bridgeculvert.Constants.api_pre_url;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shuvo.ttit.bridgeculvert.R;
import com.shuvo.ttit.bridgeculvert.login.Login;
import com.shuvo.ttit.bridgeculvert.mainmenu.HomePage;
import com.shuvo.ttit.bridgeculvert.progressbar.WaitProgress;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ChooseUser extends AppCompatActivity {

    LinearLayout guest;
    LinearLayout admin;

    WaitProgress waitProgress = new WaitProgress();
    ArrayList<String> urls;
    String text_url = "https://raw.githubusercontent.com/shuvo934/Story/refs/heads/master/BridgeCulvertServer";

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

        guest.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseUser.this, HomePage.class);
            intent.putExtra("USER","GUEST");
            startActivity(intent);
        });

        admin.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseUser.this, Login.class);
            startActivity(intent);
        });

        readApiText();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseUser.this);
                builder.setTitle("EXIT!")
                        .setMessage("Do you want to Exit?")
                        .setPositiveButton("YES", (dialog, which) -> finish())
                        .setNegativeButton("NO", (dialog, which) -> {

                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void readApiText() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);
        new Thread(() -> {
            urls = new ArrayList<>();
            try {
                URL url = new URL(text_url);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(60000); // timing out in a minute
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    urls.add(str);
                }
                in.close();
            }
            catch (Exception e) {
                urls.add("http://103.56.208.123:8086/terrain/bridge_culvert/");
            }

            runOnUiThread(() -> {
                if (urls.isEmpty()) {
                    api_pre_url = "http://103.56.208.123:8086/terrain/bridge_culvert/";
                }
                else {
                    for (int i = 0; i < urls.size(); i++) {
                        api_pre_url= urls.get(i);
                    }
                }
                waitProgress.dismiss();
            });

        }).start();
    }
}