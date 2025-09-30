package com.shuvo.ttit.bridgeculvert.mainmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.rosemaryapp.amazingspinner.AmazingSpinner;
import com.shuvo.ttit.bridgeculvert.R;
import com.shuvo.ttit.bridgeculvert.arraylist.DistrictLists;
import com.shuvo.ttit.bridgeculvert.arraylist.DivisionLists;
import com.shuvo.ttit.bridgeculvert.arraylist.FinancialYearLists;
import com.shuvo.ttit.bridgeculvert.arraylist.LocationLists;
import com.shuvo.ttit.bridgeculvert.arraylist.ProjectMapsLists;
import com.shuvo.ttit.bridgeculvert.arraylist.ProjectSubTypeLists;
import com.shuvo.ttit.bridgeculvert.arraylist.ProjectTypeLists;
import com.shuvo.ttit.bridgeculvert.arraylist.Projectlists;
import com.shuvo.ttit.bridgeculvert.arraylist.SourceFundLists;
import com.shuvo.ttit.bridgeculvert.arraylist.UnionLists;
import com.shuvo.ttit.bridgeculvert.arraylist.UpazilaLists;
import com.shuvo.ttit.bridgeculvert.progressbar.WaitProgress;
import com.shuvo.ttit.bridgeculvert.projects.Projects;
import com.shuvo.ttit.bridgeculvert.projectsWithMap.ProjectsMaps;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.shuvo.ttit.bridgeculvert.Constants.api_pre_url;
import static com.shuvo.ttit.bridgeculvert.login.Login.userInfoLists;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomePage extends AppCompatActivity {

    AmazingSpinner financialYearStart;
    AmazingSpinner financialYearEnd;
    LinearLayout afterYearSelection;
    AmazingSpinner division;

    AmazingSpinner district;
    TextInputLayout districtLay;

    AmazingSpinner upazila;
    TextInputLayout upazilaLay;

    AmazingSpinner union;
    TextInputLayout unionLay;

    AmazingSpinner sourceOfFund;

    AmazingSpinner projectType;

    AmazingSpinner projectSubType;
    TextInputLayout projectSubTypeLay;

    Button search;
    Button searchMap;

    WaitProgress waitProgress = new WaitProgress();
//    private String message = null;
    private Boolean conn = false;
//    private Boolean connected = false;

//    private Connection connection;

    ArrayList<FinancialYearLists> fysLists;
    ArrayList<FinancialYearLists> fyeLists;

    String fys_id = "";
    String fye_id = "";
//    String fys_name = "";
//    String fye_name = "";

    ArrayList<DivisionLists> divisionLists;
    String div_id = "";
//    String div_name = "";

    ArrayList<DistrictLists> districtLists;
    String dist_id = "";
//    String dist_name = "";

    ArrayList<UpazilaLists> upazilaLists;
    String dd_id = "";
//    String thana_name = "";

    ArrayList<UnionLists> unionLists;
    String ddu_id = "";
//    String union_name = "";

    ArrayList<SourceFundLists> sourceFundLists;
    String fsm_id = "";
//    String fund_name = "";

    ArrayList<ProjectTypeLists> projectTypeLists;
    String ptm_id = "";
//    String project_type_name = "";

    ArrayList<ProjectSubTypeLists> projectSubTypeLists;
    String ptd_Id = "";
//    String project_sub_type_name = "";

    public static ArrayList<Projectlists> projectlists;
    public static ArrayList<ProjectMapsLists> projectMapsLists;

    TextView  userName;

    String userType = "";

    ImageView logOut;

//    private int numberOfRequestsToMake;
//    private boolean hasRequestFailed = false;

    Logger logger = Logger.getLogger(HomePage.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        View navScrim = findViewById(R.id.nav_bar_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_page_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            ViewGroup.LayoutParams lp = navScrim.getLayoutParams();
            lp.height = systemBars.bottom;
            navScrim.setLayoutParams(lp);
            return insets;
        });

        userName = findViewById(R.id.user_full_name);

        Intent intent = getIntent();
        userType = intent.getStringExtra("USER");

        financialYearStart = findViewById(R.id.fy_s_spinner);
        financialYearEnd = findViewById(R.id.fy_e_spinner);
        afterYearSelection = findViewById(R.id.after_selecting_year);
        afterYearSelection.setVisibility(View.GONE);

        division = findViewById(R.id.division_spinner);

        district = findViewById(R.id.district_spinner);
        districtLay = findViewById(R.id.spinner_layout_district);
        districtLay.setEnabled(false);

        upazila = findViewById(R.id.upazila_spinner);
        upazilaLay = findViewById(R.id.spinner_layout_upazila);
        upazilaLay.setEnabled(false);

        union = findViewById(R.id.union_spinner);
        unionLay = findViewById(R.id.spinner_layout_union);
        unionLay.setEnabled(false);

        sourceOfFund = findViewById(R.id.source_fund_spinner);

        projectType = findViewById(R.id.project_type_spinner);

        projectSubType = findViewById(R.id.project_sub_type_spinner);
        projectSubTypeLay = findViewById(R.id.spinner_layout_project_sub_type);
        projectSubTypeLay.setEnabled(false);

        search = findViewById(R.id.search_project_button);
        search.setEnabled(false);
        searchMap = findViewById(R.id.search_map_button);
        searchMap.setEnabled(false);

        logOut = findViewById(R.id.log_out_icon_main_menu);

        fysLists = new ArrayList<>();
        fyeLists = new ArrayList<>();
        divisionLists = new ArrayList<>();
        districtLists = new ArrayList<>();
        upazilaLists = new ArrayList<>();
        sourceFundLists = new ArrayList<>();
        projectTypeLists = new ArrayList<>();
        projectSubTypeLists = new ArrayList<>();
        unionLists = new ArrayList<>();
        projectlists = new ArrayList<>();
        projectMapsLists = new ArrayList<>();

        if (userType.equals("GUEST")) {
            userName.setText(userType);
            searchMap.setVisibility(View.GONE);
            logOut.setVisibility(View.GONE);
        } else if (userType.equals("ADMIN")) {
            String n = userInfoLists.get(0).getUserName();
            userName.setText(n);
            searchMap.setVisibility(View.VISIBLE);
            logOut.setVisibility(View.VISIBLE);
        }

        financialYearStart.setOnItemClickListener((adapterView, view, i, l) -> {
            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < fysLists.size(); j++) {
                if (name.equals(fysLists.get(j).getFinancialYearName())) {
                    fys_id = (fysLists.get(j).getFyId());
                }
            }
            System.out.println(fys_id);
            if (!fye_id.isEmpty()) {
                afterYearSelection.setVisibility(View.VISIBLE);
                if (!div_id.isEmpty() && !fys_id.isEmpty() && !fye_id.isEmpty() && !dist_id.isEmpty()) {
                    search.setEnabled(true);
                    searchMap.setEnabled(true);
                } else {
                    searchMap.setEnabled(false);
                    search.setEnabled(false);
                }
//                    search.setEnabled(true);
//                    searchMap.setEnabled(true);
            }
        });

        financialYearEnd.setOnItemClickListener((adapterView, view, i, l) -> {
            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < fyeLists.size(); j++) {
                if (name.equals(fyeLists.get(j).getFinancialYearName())) {
                    fye_id = (fyeLists.get(j).getFyId());
                }
            }
            System.out.println(fye_id);
            if (!fys_id.isEmpty()) {
                afterYearSelection.setVisibility(View.VISIBLE);
                if (!div_id.isEmpty() && !fys_id.isEmpty() && !fye_id.isEmpty() && !dist_id.isEmpty()) {
                    search.setEnabled(true);
                    searchMap.setEnabled(true);
                } else {
                    searchMap.setEnabled(false);
                    search.setEnabled(false);
                }
//                    search.setEnabled(true);
//                    searchMap.setEnabled(true);
            }
        });

        division.setOnItemClickListener((adapterView, view, i, l) -> {

            districtLay.setEnabled(false);
            district.setText("");
            upazilaLay.setEnabled(false);
            upazila.setText("");
            unionLay.setEnabled(false);
            union.setText("");
            div_id = "";
            dist_id = "";
            dd_id = "";
            ddu_id = "";
            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < divisionLists.size(); j++) {
                if (name.equals(divisionLists.get(j).getDivName())) {
                    div_id = (divisionLists.get(j).getDivId());
                }
            }
            if (name.equals("...")) {
                division.setText("");
            }
            System.out.println(name);
            System.out.println(div_id);

            if (!div_id.isEmpty()) {
//                    new DistrictCheck().execute();
                getDistricts();
            }

            if (!div_id.isEmpty() && !fys_id.isEmpty() && !fye_id.isEmpty() && !dist_id.isEmpty()) {
                search.setEnabled(true);
                searchMap.setEnabled(true);
            } else {
                searchMap.setEnabled(false);
                search.setEnabled(false);
            }

        });

        district.setOnItemClickListener((adapterView, view, i, l) -> {
            upazilaLay.setEnabled(false);
            upazila.setText("");
            unionLay.setEnabled(false);
            union.setText("");
            dist_id = "";
            dd_id = "";
            ddu_id = "";


            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < districtLists.size(); j++) {
                if (name.equals(districtLists.get(j).getDistName())) {
                    dist_id = (districtLists.get(j).getDistId());
                }
            }

            System.out.println(dist_id);

            if (!div_id.isEmpty() && !fys_id.isEmpty() && !fye_id.isEmpty() && !dist_id.isEmpty()) {
                search.setEnabled(true);
                searchMap.setEnabled(true);
            } else {
                searchMap.setEnabled(false);
                search.setEnabled(false);
            }

//                new UpazilaCheck().execute();
            getUpazilas();
        });

        upazila.setOnItemClickListener((adapterView, view, i, l) -> {

            unionLay.setEnabled(false);
            union.setText("");
            dd_id = "";
            ddu_id = "";

            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < upazilaLists.size(); j++) {
                if (name.equals(upazilaLists.get(j).getThanaName())) {
                    dd_id = (upazilaLists.get(j).getDdId());
                }
            }

            System.out.println(dd_id);

//                new UnionCheck().execute();
            getUnions();

        });

        union.setOnItemClickListener((adapterView, view, i, l) -> {
            ddu_id = "";

            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < unionLists.size(); j++) {
                if (name.equals(unionLists.get(j).getUnionName())) {
                    ddu_id = (unionLists.get(j).getDduId());
                }
            }

            System.out.println(ddu_id);
        });

        sourceOfFund.setOnItemClickListener((adapterView, view, i, l) -> {

            fsm_id = "";

            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < sourceFundLists.size(); j++) {
                if (name.equals(sourceFundLists.get(j).getFundName())) {
                    fsm_id = (sourceFundLists.get(j).getFsmId());
                }
            }

            if (name.equals("...")) {
                sourceOfFund.setText("");
            }

            System.out.println(fsm_id);
        });

        projectType.setOnItemClickListener((adapterView, view, i, l) -> {

            projectSubTypeLay.setEnabled(false);
            projectSubType.setText("");
            ptm_id = "";
            ptd_Id = "";
            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < projectTypeLists.size(); j++) {
                if (name.equals(projectTypeLists.get(j).getProjectTypeName())) {
                    ptm_id = (projectTypeLists.get(j).getPtmId());
                }
            }
            System.out.println(name);
            System.out.println(ptm_id);
            if (name.equals("...")) {
                projectType.setText("");
            }

            if (!ptm_id.isEmpty()) {
//                    new ProjectSubTypeCheck().execute();
                getProjectSubType();
            }


        });

        projectSubType.setOnItemClickListener((adapterView, view, i, l) -> {
            ptd_Id = "";
            String name = adapterView.getItemAtPosition(i).toString();
            for (int j = 0; j < projectSubTypeLists.size(); j++) {
                if (name.equals(projectSubTypeLists.get(j).getProjectSubTypeName())) {
                    ptd_Id = (projectSubTypeLists.get(j).getPtdId());
                }
            }
            System.out.println(ptd_Id);
        });

        search.setOnClickListener(view -> {
            if (!fys_id.isEmpty() && !fye_id.isEmpty()) {

//                    new ProjectDataCheck().execute();
                getProjectData();
            }
        });


        searchMap.setOnClickListener(view -> {
            if (!fys_id.isEmpty() && !fye_id.isEmpty()) {
//                    new ProjectMapDataCheck().execute();
                getProjectMapData();
            }
        });

        logOut.setOnClickListener(view -> {
            if (userType.equals("GUEST")) {
                finish();
            } else if (userType.equals("ADMIN")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                builder.setTitle("LOG OUT!")
                        .setMessage("Do you want to Log Out?")
                        .setPositiveButton("YES", (dialog, which) -> {


                            userInfoLists.clear();
                            userInfoLists = new ArrayList<>();


//                        Intent intent = new Intent(HomePage.this, Login.class);
//                        startActivity(intent);
                            finish();
                            //System.exit(0);
                        })
                        .setNegativeButton("NO", (dialog, which) -> {

                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (userType.equals("GUEST")) {
                    finish();
                }
                else if (userType.equals("ADMIN")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                    builder.setTitle("LOG OUT!")
                            .setMessage("Do you want to Log Out?")
                            .setPositiveButton("YES", (dialog, which) -> {
                                userInfoLists.clear();
                                userInfoLists = new ArrayList<>();

//                        Intent intent = new Intent(HomePage.this, Login.class);
//                        startActivity(intent);
                                finish();
                                //System.exit(0);
                            })
                            .setNegativeButton("NO", (dialog, which) -> {

                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        getQuery();

    }


//    public boolean isConnected () {
//        boolean connected = false;
//        boolean isMobile = false;
//        try {
//            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo nInfo = cm.getActiveNetworkInfo();
//            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
//            return connected;
//        } catch (Exception e) {
//            Log.e("Connectivity Exception", e.getMessage());
//        }
//        return connected;
//    }
//
//    public boolean isOnline () {
//
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//            int exitValue = ipProcess.waitFor();
//            return (exitValue == 0);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

//    public class Check extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(), "WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                Query();
//                if (connected) {
//                    conn = true;
//                    message = "Internet Connected";
//                }
//
//            } else {
//                conn = false;
//                message = "Not Connected";
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            waitProgress.dismiss();
//            if (conn) {
//
//                ArrayList<String> type = new ArrayList<>();
//                for(int i = 0; i < fysLists.size(); i++) {
//                    type.add(fysLists.get(i).getFinancialYearName());
//                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);
//
//                financialYearStart.setAdapter(arrayAdapter);
//
//                ArrayList<String> type1 = new ArrayList<>();
//                for(int i = 0; i < fyeLists.size(); i++) {
//                    type1.add(fyeLists.get(i).getFinancialYearName());
//                }
//                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type1);
//
//                financialYearEnd.setAdapter(arrayAdapter1);
//
//                ArrayList<String> type2 = new ArrayList<>();
//                for(int i = 0; i < divisionLists.size(); i++) {
//                    type2.add(divisionLists.get(i).getDivName());
//                }
//                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type2);
//
//                division.setAdapter(arrayAdapter2);
//
//                ArrayList<String> type3 = new ArrayList<>();
//                for(int i = 0; i < sourceFundLists.size(); i++) {
//                    type3.add(sourceFundLists.get(i).getFundName());
//                }
//                ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type3);
//
//                sourceOfFund.setAdapter(arrayAdapter3);
//
//                ArrayList<String> type4 = new ArrayList<>();
//                for(int i = 0; i < projectTypeLists.size(); i++) {
//                    type4.add(projectTypeLists.get(i).getProjectTypeName());
//                }
//                ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type4);
//
//                projectType.setAdapter(arrayAdapter4);
//
//
//                conn = false;
//                connected = false;
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .setNegativeButton("EXIT",null)
//                        .show();
//
//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new Check().execute();
//                        dialog.dismiss();
//                    }
//                });
//
//                Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//                negative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        if (userType.equals("GUEST")) {
//                            finish();
//                        } else if (userType.equals("ADMIN")) {
//                            userInfoLists.clear();
//                            userInfoLists = new ArrayList<>();
//                            finish();
//                        }
//                    }
//                });
//            }
//        }
//    }

//    public class DistrictCheck extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(), "WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                DistrictQuery();
//                if (connected) {
//                    conn = true;
//                    message = "Internet Connected";
//                }
//
//            } else {
//                conn = false;
//                message = "Not Connected";
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            waitProgress.dismiss();
//            if (conn) {
//
//                districtLay.setEnabled(true);
//
//                ArrayList<String> type = new ArrayList<>();
//                for(int i = 0; i < districtLists.size(); i++) {
//                    type.add(districtLists.get(i).getDistName());
//                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);
//
//                district.setAdapter(arrayAdapter);
//
//
//
//                conn = false;
//                connected = false;
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .show();
//
////                dialog.setCancelable(false);
////                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new DistrictCheck().execute();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        }
//    }

//    public class UpazilaCheck extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(), "WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                UpazilaQuery();
//                if (connected) {
//                    conn = true;
//                    message = "Internet Connected";
//                }
//
//            } else {
//                conn = false;
//                message = "Not Connected";
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            waitProgress.dismiss();
//            if (conn) {
//
//                upazilaLay.setEnabled(true);
//
//                ArrayList<String> type = new ArrayList<>();
//                for(int i = 0; i < upazilaLists.size(); i++) {
//                    type.add(upazilaLists.get(i).getThanaName());
//                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);
//
//                upazila.setAdapter(arrayAdapter);
//
//
//
//                conn = false;
//                connected = false;
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .show();
//
////                dialog.setCancelable(false);
////                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new UpazilaCheck().execute();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        }
//    }

//    public class UnionCheck extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(), "WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                UnionQuery();
//                if (connected) {
//                    conn = true;
//                    message = "Internet Connected";
//                }
//
//            } else {
//                conn = false;
//                message = "Not Connected";
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            waitProgress.dismiss();
//            if (conn) {
//
//                unionLay.setEnabled(true);
//
//                ArrayList<String> type = new ArrayList<>();
//                for(int i = 0; i < unionLists.size(); i++) {
//                    type.add(unionLists.get(i).getUnionName());
//                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);
//
//                union.setAdapter(arrayAdapter);
//
//                conn = false;
//                connected = false;
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .show();
//
////                dialog.setCancelable(false);
////                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new UnionCheck().execute();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        }
//    }

//    public class ProjectSubTypeCheck extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(), "WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                ProjectSubTypeQuery();
//                if (connected) {
//                    conn = true;
//                    message = "Internet Connected";
//                }
//
//            } else {
//                conn = false;
//                message = "Not Connected";
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            waitProgress.dismiss();
//            if (conn) {
//
//                projectSubTypeLay.setEnabled(true);
//
//                ArrayList<String> type = new ArrayList<>();
//                for(int i = 0; i < projectSubTypeLists.size(); i++) {
//                    type.add(projectSubTypeLists.get(i).getProjectSubTypeName());
//                }
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);
//
//                projectSubType.setAdapter(arrayAdapter);
//
//
//
//                conn = false;
//                connected = false;
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .show();
//
////                dialog.setCancelable(false);
////                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new ProjectSubTypeCheck().execute();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        }
//    }

//    public class ProjectDataCheck extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(), "WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                ProjectDataQuery();
//                if (connected) {
//                    conn = true;
//                    message = "Internet Connected";
//                }
//
//            } else {
//                conn = false;
//                message = "Not Connected";
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            waitProgress.dismiss();
//            if (conn) {
//
//
//
//                if (projectlists.size() != 0) {
//                    System.out.println(projectlists.size());
//                    Intent intent = new Intent(HomePage.this, Projects.class);
//                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"No Results Found",Toast.LENGTH_SHORT).show();
//                }
//
//
//
//                conn = false;
//                connected = false;
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .show();
//
////                dialog.setCancelable(false);
////                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new ProjectDataCheck().execute();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        }
//    }

//    public class ProjectMapDataCheck extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(), "WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                ProjectMapDataQuery();
//                if (connected) {
//                    conn = true;
//                    message = "Internet Connected";
//                }
//
//            } else {
//                conn = false;
//                message = "Not Connected";
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            waitProgress.dismiss();
//            if (conn) {
//
//
//
//                if (projectMapsLists.size() != 0) {
//                    System.out.println(projectMapsLists.size());
//                    Intent intent = new Intent(HomePage.this, ProjectsMaps.class);
//                    intent.putExtra("DIST_ID",dist_id);
//                    intent.putExtra("DD_ID",dd_id);
//                    intent.putExtra("DDU_ID",ddu_id);
//                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"No Results Found",Toast.LENGTH_SHORT).show();
//                }
//
//
//
//                conn = false;
//                connected = false;
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .show();
//
////                dialog.setCancelable(false);
////                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new ProjectMapDataCheck().execute();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        }
//    }

//    public void Query () {
//
//        try {
//            this.connection = createConnection();
//
//            fysLists = new ArrayList<>();
//            fyeLists = new ArrayList<>();
//            divisionLists = new ArrayList<>();
//            sourceFundLists = new ArrayList<>();
//            projectTypeLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            ResultSet rs = stmt.executeQuery("SELECT FINANCIAL_YEAR.FY_ID P_FY_ID, FINANCIAL_YEAR.FY_FINANCIAL_YEAR_NAME, FINANCIAL_YEAR.FY_FROM_YEAR, FINANCIAL_YEAR.FY_TO_YEAR, FINANCIAL_YEAR.FY_DETAILS, FINANCIAL_YEAR.FY_ACTIVE_FLAG " +
//                    "FROM FINANCIAL_YEAR " +
//                    "WHERE FINANCIAL_YEAR.FY_ACTIVE_FLAG = 1 " +
//                    "ORDER BY FINANCIAL_YEAR.FY_FROM_YEAR ASC");
//
//
//            while (rs.next()) {
//                fysLists.add(new FinancialYearLists(rs.getString(1),rs.getString(2),rs.getString(3),
//                        rs.getString(4),rs.getString(5),rs.getString(6)));
//                fyeLists.add(new FinancialYearLists(rs.getString(1),rs.getString(2),rs.getString(3),
//                        rs.getString(4),rs.getString(5),rs.getString(6)));
//            }
//
//            rs.close();
//
//            ResultSet rs1 = stmt.executeQuery("SELECT DIVISION.DIV_ID P_DIV_ID, DIVISION.DIV_NAME FROM DIVISION WHERE DIV_ACTIVE_FLAG=1 ORDER BY DIV_NAME");
//
//            divisionLists.add(new DivisionLists("","..."));
//            while (rs1.next()) {
//                divisionLists.add(new DivisionLists(rs1.getString(1),rs1.getString(2)));
//            }
//
//            rs1.close();
//
//            ResultSet rs2 = stmt.executeQuery("SELECT FSM_ID P_FSM_ID, FSM_FUND_NAME FROM FUND_SOURCE_MST WHERE FSM_FUND_SOURCE_ACTIVE_FLAG = 1");
//
//            sourceFundLists.add(new SourceFundLists("","..."));
//            while (rs2.next()) {
//                sourceFundLists.add(new SourceFundLists(rs2.getString(1),rs2.getString(2)));
//            }
//
//            rs2.close();
//
//            ResultSet rs3 = stmt.executeQuery("SELECT PTM_ID P_PTM_ID, ptm_project_type_name FROM PROJECT_TYPE_MST WHERE NVL(PTM_ACTIVE_FLAG,0) = 1");
//
//            projectTypeLists.add(new ProjectTypeLists("","..."));
//            while (rs3.next()) {
//                projectTypeLists.add(new ProjectTypeLists(rs3.getString(1),rs3.getString(2)));
//            }
//
//            rs3.close();
//
//            stmt.close();
//
//            connected = true;
//
//            connection.close();
//
//        } catch (Exception e) {
//
//            //   Toast.makeText(MainActivity.this, ""+e,Toast.LENGTH_LONG).show();
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//
//    }

    //    --------------------------Updating UI with Necessary Data-----------------------------
    public void getQuery() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);

        fysLists = new ArrayList<>();
        fyeLists = new ArrayList<>();
        divisionLists = new ArrayList<>();
        sourceFundLists = new ArrayList<>();
        projectTypeLists = new ArrayList<>();

        conn = false;

        String fy_url = api_pre_url + "utility_data/fy_lists";
        String div_url = api_pre_url + "utility_data/division_lists";
        String fund_url = api_pre_url + "utility_data/source_of_fund_lists";
        String p_type_url = api_pre_url + "utility_data/project_type_lists";

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest projectTypeRequest = new StringRequest(Request.Method.GET, p_type_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                projectTypeLists.add(new ProjectTypeLists("","..."));
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject pTypeObject = jsonArray.getJSONObject(i);
                        String p_ptm_id = pTypeObject.getString("p_ptm_id");
                        String ptm_project_type_name = pTypeObject.getString("ptm_project_type_name");

                        ptm_project_type_name = transformText(ptm_project_type_name);

                        projectTypeLists.add(new ProjectTypeLists(p_ptm_id,ptm_project_type_name));
                    }
                }
                conn = true;
                updateUI();

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateUI();
            }
        }, error -> {
            conn = false;
            updateUI();
        });

        StringRequest fundRequest = new StringRequest(Request.Method.GET, fund_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                sourceFundLists.add(new SourceFundLists("","..."));
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject fundObject = jsonArray.getJSONObject(i);
                        String p_fsm_id = fundObject.getString("p_fsm_id");
                        String fsm_fund_name = fundObject.getString("fsm_fund_name");

                        fsm_fund_name = transformText(fsm_fund_name);

                        sourceFundLists.add(new SourceFundLists(p_fsm_id,fsm_fund_name));
                    }
                }
                requestQueue.add(projectTypeRequest);

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateUI();
            }
        }, error -> {
            conn = false;
            updateUI();
        });

        StringRequest divRequest = new StringRequest(Request.Method.GET, div_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    divisionLists.add(new DivisionLists("","..."));
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject divObject = jsonArray.getJSONObject(i);
                        String p_div_id = divObject.getString("p_div_id");
                        String div_name = divObject.getString("div_name");

                        div_name = transformText(div_name);

                        divisionLists.add(new DivisionLists(p_div_id,div_name));
                    }
                    requestQueue.add(fundRequest);
                }
                else {
                    conn = false;
                    updateUI();
                }
            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateUI();
            }
        }, error -> {
            conn = false;
            updateUI();
        });

        StringRequest fyRequest = new StringRequest(Request.Method.GET, fy_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject fyObject = jsonArray.getJSONObject(i);
                        String p_fy_id = fyObject.getString("p_fy_id");
                        String fy_financial_year_name = fyObject.getString("fy_financial_year_name");
                        String fy_from_year = fyObject.getString("fy_from_year");
                        String fy_to_year = fyObject.getString("fy_to_year");
                        String fy_details = fyObject.getString("fy_details");
                        String fy_active_flag = fyObject.getString("fy_active_flag");

                        fysLists.add(new FinancialYearLists(p_fy_id,fy_financial_year_name,fy_from_year,
                                fy_to_year,fy_details,fy_active_flag));
                        fyeLists.add(new FinancialYearLists(p_fy_id,fy_financial_year_name,fy_from_year,
                                fy_to_year,fy_details,fy_active_flag));
                    }
                    requestQueue.add(divRequest);
                }
                else {
                    conn = false;
                    updateUI();
                }
            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateUI();
            }
        }, error -> {
            conn = false;
            updateUI();
        });

        requestQueue.add(fyRequest);
    }

    public void updateUI() {
        waitProgress.dismiss();
        if (conn) {

            ArrayList<String> type = new ArrayList<>();
            for(int i = 0; i < fysLists.size(); i++) {
                type.add(fysLists.get(i).getFinancialYearName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);

            financialYearStart.setAdapter(arrayAdapter);

            ArrayList<String> type1 = new ArrayList<>();
            for(int i = 0; i < fyeLists.size(); i++) {
                type1.add(fyeLists.get(i).getFinancialYearName());
            }
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type1);

            financialYearEnd.setAdapter(arrayAdapter1);

            ArrayList<String> type2 = new ArrayList<>();
            for(int i = 0; i < divisionLists.size(); i++) {
                type2.add(divisionLists.get(i).getDivName());
            }
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type2);

            division.setAdapter(arrayAdapter2);

            ArrayList<String> type3 = new ArrayList<>();
            for(int i = 0; i < sourceFundLists.size(); i++) {
                type3.add(sourceFundLists.get(i).getFundName());
            }
            ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type3);

            sourceOfFund.setAdapter(arrayAdapter3);

            ArrayList<String> type4 = new ArrayList<>();
            for(int i = 0; i < projectTypeLists.size(); i++) {
                type4.add(projectTypeLists.get(i).getProjectTypeName());
            }
            ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type4);

            projectType.setAdapter(arrayAdapter4);

            conn = false;
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .setNegativeButton("EXIT",null)
                    .show();

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                getQuery();
                dialog.dismiss();
            });

            Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negative.setOnClickListener(v -> {
                dialog.dismiss();
                if (userType.equals("GUEST")) {
                    finish();
                } else if (userType.equals("ADMIN")) {
                    userInfoLists.clear();
                    userInfoLists = new ArrayList<>();
                    finish();
                }
            });
        }
    }

//    public void DistrictQuery () {
//
//        try {
//            this.connection = createConnection();
//
//            districtLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            if (div_id != null) {
//                if (div_id.isEmpty()) {
//                    div_id = null;
//                }
//            }
//
//            ResultSet rs = stmt.executeQuery("SELECT DIST_ID P_DIST_ID, DIST_NAME FROM DISTRICT WHERE DIST_ACTIVE_FLAG=1 AND DIST_DIV_ID= "+div_id+" ORDER BY DIST_NAME");
//
//            while (rs.next()) {
//                districtLists.add(new DistrictLists(rs.getString(1),rs.getString(2)));
//            }
//
//            if (div_id == null) {
//                div_id = "";
//            }
//
//            rs.close();
//
//            stmt.close();
//
//            connected = true;
//
//            connection.close();
//
//        } catch (Exception e) {
//
//            //   Toast.makeText(MainActivity.this, ""+e,Toast.LENGTH_LONG).show();
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//
//    }

    //    --------------------------Getting Districts and updating UI-----------------------------
    public void getDistricts() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);
        conn = false;

        districtLists = new ArrayList<>();

        if (div_id == null) {
            div_id = "";
        }

        String dist_url = api_pre_url + "utility_data/dist_lists?div_id="+div_id;

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, dist_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject distObject = jsonArray.getJSONObject(i);
                        String p_dist_id = distObject.getString("p_dist_id");
                        String dist_name = distObject.getString("dist_name");

                        dist_name = transformText(dist_name);

                        districtLists.add(new DistrictLists(p_dist_id,dist_name));
                    }
                }
                conn = true;
                updateDistricts();

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateDistricts();
            }
        }, error -> {
            conn = false;
            updateDistricts();
        });

        requestQueue.add(stringRequest);

    }

    public void updateDistricts() {
        waitProgress.dismiss();
        if (conn) {

            districtLay.setEnabled(true);

            ArrayList<String> type = new ArrayList<>();
            for(int i = 0; i < districtLists.size(); i++) {
                type.add(districtLists.get(i).getDistName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);

            district.setAdapter(arrayAdapter);

            conn = false;

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .show();

//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                getDistricts();
                dialog.dismiss();
            });
        }
    }

//    public void UpazilaQuery () {
//
//        try {
//            this.connection = createConnection();
//
//            upazilaLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            if (dist_id != null) {
//                if (dist_id.isEmpty()) {
//                    dist_id = null;
//                }
//            }
//
//            ResultSet rs = stmt.executeQuery("SELECT DISTRICT_DTL.DD_ID PCU_DD_ID, DISTRICT_DTL.DD_THANA_NAME THANA_UPOZILLA \n" +
//                    "FROM DISTRICT_DTL \n" +
//                    "WHERE DD_DIST_ID = "+dist_id+" \n" +
//                    "AND NVL(DD_ACTIVE_FLAG,0)=1 \n" +
//                    "ORDER BY DISTRICT_DTL.DD_THANA_NAME ASC");
//
//            while (rs.next()) {
//                upazilaLists.add(new UpazilaLists(rs.getString(1),rs.getString(2)));
//            }
//
//            if (dist_id == null) {
//                dist_id = "";
//            }
//
//            rs.close();
//
//            stmt.close();
//
//            connected = true;
//
//            connection.close();
//
//        } catch (Exception e) {
//
//            //   Toast.makeText(MainActivity.this, ""+e,Toast.LENGTH_LONG).show();
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//
//    }

    //    --------------------------Getting Upazillas and updating UI-----------------------------
    public void getUpazilas() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);
        conn = false;

        upazilaLists = new ArrayList<>();

        if (dist_id == null) {
            dist_id = "";
        }

        String upazila_url = api_pre_url + "utility_data/upazila_lists?dist_id="+dist_id;

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest upazilaRequest = new StringRequest(Request.Method.GET, upazila_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject upazilaObject = jsonArray.getJSONObject(i);
                        String pcu_dd_id = upazilaObject.getString("pcu_dd_id");
                        String thana_upozilla = upazilaObject.getString("thana_upozilla");

                        thana_upozilla = transformText(thana_upozilla);

                        upazilaLists.add(new UpazilaLists(pcu_dd_id,thana_upozilla));

                    }
                }
                conn = true;
                updateUpazila();

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateUpazila();
            }
        }, error -> {
            conn = false;
            updateUpazila();
        });

        requestQueue.add(upazilaRequest);
    }

    public void updateUpazila() {
        waitProgress.dismiss();
        if (conn) {

            upazilaLay.setEnabled(true);

            ArrayList<String> type = new ArrayList<>();
            for(int i = 0; i < upazilaLists.size(); i++) {
                type.add(upazilaLists.get(i).getThanaName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);

            upazila.setAdapter(arrayAdapter);

            conn = false;

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .show();

//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                getUpazilas();
                dialog.dismiss();
            });
        }
    }

//    public void UnionQuery () {
//
//        try {
//            this.connection = createConnection();
//
//            unionLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            if (dd_id != null) {
//                if (dd_id.isEmpty()) {
//                    dd_id = null;
//                }
//            }
//
//            ResultSet rs = stmt.executeQuery("SELECT DISTRICT_DTL_UNION.DDU_ID PCUN_DDU_ID, DISTRICT_DTL_UNION.DDU_UNION_NAME \n" +
//                    "FROM DISTRICT_DTL_UNION \n" +
//                    "WHERE DISTRICT_DTL_UNION.DDU_DD_ID = "+dd_id+"\n" +
//                    "ORDER BY DISTRICT_DTL_UNION.DDU_UNION_NAME ASC");
//
//            while (rs.next()) {
//                unionLists.add(new UnionLists(rs.getString(1),rs.getString(2)));
//            }
//
//            if (dd_id == null) {
//                dd_id = "";
//            }
//
//            rs.close();
//
//            stmt.close();
//
//            connected = true;
//
//            connection.close();
//
//        } catch (Exception e) {
//
//            //   Toast.makeText(MainActivity.this, ""+e,Toast.LENGTH_LONG).show();
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//
//    }

    //    --------------------------Getting Unions and updating UI-----------------------------
    public void getUnions() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);
        conn = false;

        unionLists = new ArrayList<>();

        if (dd_id == null) {
            dd_id = "";
        }

        String union_url = api_pre_url + "utility_data/union_lists?dd_id="+dd_id;

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest unionRequest = new StringRequest(Request.Method.GET, union_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject unionObject = jsonArray.getJSONObject(i);
                        String pcun_ddu_id = unionObject.getString("pcun_ddu_id");
                        String ddu_union_name = unionObject.getString("ddu_union_name");

                        ddu_union_name = transformText(ddu_union_name);

                        unionLists.add(new UnionLists(pcun_ddu_id,ddu_union_name));

                    }
                }
                conn = true;
                updateUnion();

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateUnion();
            }
        }, error -> {
            conn = false;
            updateUnion();
        });

        requestQueue.add(unionRequest);
    }

    public void updateUnion() {
        waitProgress.dismiss();
        if (conn) {
            unionLay.setEnabled(true);

            ArrayList<String> type = new ArrayList<>();
            for(int i = 0; i < unionLists.size(); i++) {
                type.add(unionLists.get(i).getUnionName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);

            union.setAdapter(arrayAdapter);

            conn = false;

        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .show();

//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                getUnions();
                dialog.dismiss();
            });
        }
    }

//    public void ProjectSubTypeQuery () {
//
//        try {
//            this.connection = createConnection();
//
//            projectSubTypeLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            if (ptm_id != null) {
//                if (ptm_id.isEmpty()) {
//                    ptm_id = null;
//                }
//            }
//
//            ResultSet rs = stmt.executeQuery("SELECT PTD_ID P_PTD_ID, ptd_project_subtype_name FROM PROJECT_TYPE_DTL WHERE PTD_PTM_ID = "+ptm_id+" AND NVL(PTD_ACTIVE_FLAG,0) = 1 ORDER BY P_PTD_ID");
//
//            while (rs.next()) {
//                projectSubTypeLists.add(new ProjectSubTypeLists(rs.getString(1),rs.getString(2)));
//            }
//
//            if (ptm_id == null) {
//                ptm_id = "";
//            }
//
//            rs.close();
//
//            stmt.close();
//
//            connected = true;
//
//            connection.close();
//
//        } catch (Exception e) {
//
//            //   Toast.makeText(MainActivity.this, ""+e,Toast.LENGTH_LONG).show();
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//
//    }

    //    --------------------------Getting Project Sub Types and updating UI-----------------------------
    public void getProjectSubType() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);
        conn = false;

        projectSubTypeLists = new ArrayList<>();

        if (ptm_id == null) {
            ptm_id = "";
        }

        String pr_sub_type_url = api_pre_url + "utility_data/project_sub_type_lists?ptm_id="+ptm_id;

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest prSubTypeRequest = new StringRequest(Request.Method.GET, pr_sub_type_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject prSubTypeObject = jsonArray.getJSONObject(i);
                        String p_ptd_id = prSubTypeObject.getString("p_ptd_id");
                        String ptd_project_subtype_name = prSubTypeObject.getString("ptd_project_subtype_name");

                        ptd_project_subtype_name = transformText(ptd_project_subtype_name);

                        projectSubTypeLists.add(new ProjectSubTypeLists(p_ptd_id,ptd_project_subtype_name));

                    }
                }
                conn = true;
                updateProjectSubTypes();

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                updateProjectSubTypes();
            }
        }, error -> {
            conn = false;
            updateProjectSubTypes();
        });

        requestQueue.add(prSubTypeRequest);
    }

    public void updateProjectSubTypes() {
        waitProgress.dismiss();
        if (conn) {

            projectSubTypeLay.setEnabled(true);

            ArrayList<String> type = new ArrayList<>();
            for(int i = 0; i < projectSubTypeLists.size(); i++) {
                type.add(projectSubTypeLists.get(i).getProjectSubTypeName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,R.id.drop_down_item,type);

            projectSubType.setAdapter(arrayAdapter);

            conn = false;

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .show();

//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                getProjectSubType();
                dialog.dismiss();
            });
        }
    }

//    public void ProjectDataQuery() {
//        try {
//            this.connection = createConnection();
//
//            projectlists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            if (ptm_id != null) {
//                if (ptm_id.isEmpty()) {
//                    ptm_id = null;
//                }
//            }
//            if (fsm_id != null) {
//                if (fsm_id.isEmpty()) {
//                    fsm_id = null;
//                }
//            }
//
//            if (ddu_id != null) {
//                if (ddu_id.isEmpty()) {
//                    ddu_id = null;
//                }
//            }
//
//            if (dist_id != null) {
//                if (dist_id.isEmpty()) {
//                    dist_id = null;
//                }
//            }
//
//            if (dd_id != null) {
//                if (dd_id.isEmpty()) {
//                    dd_id = null;
//                }
//            }
//            if (ptd_Id != null) {
//                if (ptd_Id.isEmpty()) {
//                    ptd_Id = null;
//                }
//            }
//
//            if (div_id != null) {
//                if (div_id.isEmpty()) {
//                    div_id = null;
//                }
//            }
//
//            int count = 0;
//
//            ResultSet resultSet = stmt.executeQuery("SELECT * FROM (\n" +
//                    "            SELECT p.*, ROW_NUMBER() OVER (ORDER BY p.PCM_PROJECT_DATE DESC, p.PCM_ID DESC) as ROWNUMBER_ FROM ( \n" +
//                    "        SELECT DISTINCT PROJECT_CREATION_MST.PCM_ID,\n" +
//                    "        TO_CHAR(PROJECT_CREATION_MST.PCM_ENTRY_DATE,'DD-MON-RR') ENTRY_DATE,\n" +
//                    "        PROJECT_CREATION_MST.PCM_INTERNAL_NO,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_CODE,\n" +
//                    "        PROJECT_CREATION_MST.PCM_USER,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_NAME,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_NO,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_DATE,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PIC_CHAIRMAN_NAME,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PIC_CHAIRMAN_DETAILS,\n" +
//                    "        PROJECT_CREATION_MST.PCM_ESTIMATE_PROJECT_VALUE,\n" +
//                    "        FINANCIAL_YEAR.FY_FINANCIAL_YEAR_NAME,\n" +
//                    "        FUND_SOURCE_MST.FSM_FUND_NAME,\n" +
//                    "        PROJECT_TYPE_MST.PTM_PROJECT_TYPE_NAME,\n" +
//                    "        PROJECT_TYPE_DTL.PTD_PROJECT_SUBTYPE_NAME,\n" +
//                    "        PROJECT_SANCTION_CATEGORY.PSC_SANCTION_CAT_NAME,\n" +
//                    "        PROJECT_CATEGORY_MST.PCM_CATEGORY_NAME,\n" +
//                    "        --PROJECT_CREATION_UNION.PCUN_DDU_ID,\n" +
//                    "        PROJECT_CREATION_UPOZILA.PCU_DD_ID,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJ_EVALUATION_REMARKS,\n" +
//                    "        PROJECT_CREATION_MST_GPS_DTL.PCMGD_TYPE_FLAG,PROJECT_CREATION_MST.PCM_PROJECT_DETAILS,\n" +
//                    "        TO_CHAR(PROJECT_CREATION_MST.PCM_ESTIMATE_START_DATE,'DD-MON-RR') START_DATE,\n" +
//                    "        TO_CHAR(PROJECT_CREATION_MST.PCM_ESTIMATE_END_DATE,'DD-MON-RR') END_DATE,PROJECT_CREATION_MST.PCM_PROJECT_SANCTION_TYPE,\n" +
//                    "        PROJECT_CREATION_FUND_DTLS.PCFD_PROJECT_PHYSICAL_LENGTH,\n" +
//                    "        PROJECT_CREATION_FUND_DTLS.PCFD_PROJECT_PHYSICAL_WIDTH,\n" +
//                    "        PCFD_DISTANCE_MEASURE_UNIT\n" +
//                    "    FROM\n" +
//                    "        PROJECT_CREATION_MST,\n" +
//                    "        PROJECT_CREATION_UPOZILA,\n" +
//                    "        PROJECT_CREATION_UNION,\n" +
//                    "        PROJECT_CREATION_VILLAGE,\n" +
//                    "        PROJECT_CREATION_WARD," +
//                    "        PROJECT_CREATION_FUND_DTLS,\n" +
//                    "        FINANCIAL_YEAR,\n" +
//                    "        FUND_SOURCE_MST,\n" +
//                    "        PROJECT_TYPE_MST,\n" +
//                    "        PROJECT_TYPE_DTL,\n" +
//                    "        PROJECT_SANCTION_CATEGORY,\n" +
//                    "        PROJECT_CATEGORY_MST,\n" +
//                    "        PROJECT_CREATION_MST_GPS_DTL\n" +
//                    "    WHERE FINANCIAL_YEAR.FY_ID = PROJECT_CREATION_MST.PCM_FY_ID\n" +
//                    "        AND PROJECT_CREATION_FUND_DTLS.PCFD_PCM_ID = PROJECT_CREATION_MST.PCM_ID\n" +
//                    "        AND FUND_SOURCE_MST.FSM_ID = PROJECT_CREATION_MST.PCM_FSM_ID\n" +
//                    "        AND PROJECT_TYPE_MST.PTM_ID = PROJECT_CREATION_MST.PCM_PTM_ID\n" +
//                    "        AND PROJECT_TYPE_DTL.PTD_ID = PROJECT_CREATION_MST.PCM_PTD_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_PSC_ID = PROJECT_SANCTION_CATEGORY.PSC_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_PCM_ID = PROJECT_CATEGORY_MST.PCM_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_ID = PROJECT_CREATION_UPOZILA.PCU_PCM_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_ID = PROJECT_CREATION_MST_GPS_DTL.PCMGD_PCM_ID\n" +
//                    "        AND PROJECT_CREATION_UNION.PCUN_PCU_ID = PROJECT_CREATION_UPOZILA.PCU_ID\n" +
//                    "        AND PROJECT_CREATION_UNION.PCUN_ID = PROJECT_CREATION_WARD.PCW_PCUN_ID (+)\n" +
//                    "        AND PROJECT_CREATION_WARD.PCW_ID = PROJECT_CREATION_VILLAGE.PCV_PCW_ID (+)\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_PROJ_EVALUATION_FLAG = 1 \n" +
//                    "        AND (PROJECT_CREATION_MST.PCM_PTD_ID = "+ptd_Id+" OR "+ptd_Id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_MST.PCM_PTM_ID = "+ptm_id+" OR "+ptm_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_MST.PCM_FSM_ID = "+fsm_id+" OR "+fsm_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UNION.PCUN_DDU_ID = "+ddu_id+" OR "+ddu_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UPOZILA.PCU_DD_ID = "+dd_id+" OR "+dd_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UPOZILA.PCU_DIST_ID = "+dist_id+" OR "+dist_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UPOZILA.PCU_DIV_ID = "+div_id+" OR "+div_id+" IS NULL )\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_FY_ID BETWEEN "+fys_id+" AND "+fye_id+" ) p\n" +
//                    "    ORDER BY p.PCM_PROJECT_DATE DESC, p.PCM_ID DESC )\n");
//
//            while (resultSet.next()) {
//
//                count++;
//                String stype = "";
//                switch (resultSet.getString(24)) {
//                    case "0":
//                        stype = "Taka(টাকা)";
//                        break;
//                    case "1":
//                        stype = "Rice(চাল) (MT)";
//                        break;
//                    case "2":
//                        stype = "Wheat(গম) (MT)";
//                        break;
//                }
//
//                String measureUnit = "";
//                switch (resultSet.getString(27)) {
//                    case "1":
//                        measureUnit = " Meter [MT]";
//                        break;
//                    case "2":
//                        measureUnit = " Feet [FT]";
//                        break;
//                }
//
//                String pCount = "#"+count;
//                String length = resultSet.getString(25) + measureUnit;
//                String width = resultSet.getString(26) + measureUnit;
////                projectlists.add(new Projectlists(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
////                        resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),
////                        resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),
////                        resultSet.getString(10),resultSet.getString(11),resultSet.getString(12),
////                        resultSet.getString(13),resultSet.getString(14),resultSet.getString(15),
////                        resultSet.getString(16),resultSet.getString(17),null,
////                        resultSet.getString(19),resultSet.getString(20),resultSet.getString(21),
////                        resultSet.getString(22),resultSet.getString(23),resultSet.getString(24),
////                        stype,resultSet.getString(26),new ArrayList<>()));
//
//                projectlists.add(new Projectlists(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
//                        resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),
//                        resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),
//                        resultSet.getString(10),resultSet.getString(11),resultSet.getString(12),
//                        resultSet.getString(13),resultSet.getString(14),resultSet.getString(15),
//                        resultSet.getString(16),resultSet.getString(17),null,
//                        resultSet.getString(18),resultSet.getString(19),resultSet.getString(20),
//                        resultSet.getString(21), resultSet.getString(22),resultSet.getString(23),
//                        stype,length,width,resultSet.getString(28),pCount,new ArrayList<>()));
//            }
//
//            resultSet.close();
//
//            for (int i = 0 ; i < projectlists.size(); i++) {
//                String pcmid = projectlists.get(i).getPcmId();
//                ArrayList<LocationLists> locationLists = new ArrayList<>();
//                ResultSet resultSet1 = stmt.executeQuery("SELECT \n" +
//                        "PCMGD_LATITUDE,\n" +
//                        "PCMGD_LONGITUDE,\n" +
//                        "PCMGD_LATITUDE_NUM,\n" +
//                        "PCMGD_LONGITUDE_NUM,\n" +
//                        "NVL(PCMGD_SEGMENT,0)\n"+
//                        "FROM project_creation_mst_gps_dtl WHERE PCMGD_PCM_ID = "+pcmid+" AND PCMGD_ACTIVE_FLAG=1 order by pcmgd_id ASC");
//
//                while (resultSet1.next()) {
//                    locationLists.add(new LocationLists(resultSet1.getString(1),resultSet1.getString(2),resultSet1.getInt(5)));
//                }
//
//                resultSet1.close();
//
//                projectlists.get(i).setLocationLists(locationLists);
//
//            }
//
//            stmt.close();
//
//            if (ptm_id == null) {
//                ptm_id = "";
//            }
//
//            if (fsm_id == null) {
//                fsm_id = "";
//            }
//
//            if (ddu_id == null) {
//                ddu_id = "";
//            }
//
//            if (dist_id == null) {
//                dist_id = "";
//            }
//
//            if (dd_id == null) {
//                dd_id = "";
//            }
//
//            if (ptd_Id == null) {
//                ptd_Id = "";
//            }
//
//            if (div_id == null) {
//                div_id = "";
//            }
//
//            connected = true;
//
//            connection.close();
//
//
//        } catch (Exception e) {
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//    }

    //    --------------------------Getting Project Data and going to project lists-----------------------------
    public void getProjectData() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);
        conn = false;
//        numberOfRequestsToMake = 0;
//        hasRequestFailed = false;

        projectlists = new ArrayList<>();

        if (ptm_id == null) {
            ptm_id = "";
        }

        if (fsm_id == null) {
            fsm_id = "";
        }

        if (ddu_id == null) {
            ddu_id = "";
        }

        if (dist_id == null) {
            dist_id = "";
        }

        if (dd_id == null) {
            dd_id = "";
        }

        if (ptd_Id == null) {
            ptd_Id = "";
        }

        if (div_id == null) {
            div_id = "";
        }

        final int[] countingNum = {0};

        String projectDataUrl = api_pre_url + "projects/projectData?ptd_Id="+ptd_Id+"&ptm_id="+ptm_id+
                "&fsm_id="+fsm_id+"&ddu_id="+ddu_id+"&dd_id="+dd_id+"&dist_id="+dist_id+"&div_id="+div_id+"&fys_id="+fys_id+"&fye_id="+fye_id;

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest projectDataRequest = new StringRequest(Request.Method.GET, projectDataUrl, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject projectDataObject = jsonArray.getJSONObject(i);

                        String pcm_id = projectDataObject.getString("pcm_id");
                        String entry_date = projectDataObject.getString("entry_date");
                        String pcm_internal_no = projectDataObject.getString("pcm_internal_no");

                        String pcm_project_code = projectDataObject.getString("pcm_project_code");
                        pcm_project_code = transformText(pcm_project_code);

                        String pcm_user = projectDataObject.getString("pcm_user");

                        String pcm_project_name = projectDataObject.getString("pcm_project_name");
                        pcm_project_name = transformText(pcm_project_name);

                        String pcm_project_no = projectDataObject.getString("pcm_project_no");
                        pcm_project_no = transformText(pcm_project_no);

                        String pcm_project_date = projectDataObject.getString("pcm_project_date");

                        String pcm_pic_chairman_name = projectDataObject.getString("pcm_pic_chairman_name");
                        pcm_pic_chairman_name = transformText(pcm_pic_chairman_name);

                        String pcm_pic_chairman_details = projectDataObject.getString("pcm_pic_chairman_details");
                        pcm_pic_chairman_details = transformText(pcm_pic_chairman_details);

                        String pcm_estimate_project_value = projectDataObject.getString("pcm_estimate_project_value");
                        String fy_financial_year_name = projectDataObject.getString("fy_financial_year_name");

                        String fsm_fund_name = projectDataObject.getString("fsm_fund_name");
                        fsm_fund_name = transformText(fsm_fund_name);

                        String ptm_project_type_name = projectDataObject.getString("ptm_project_type_name");
                        ptm_project_type_name = transformText(ptm_project_type_name);

                        String ptd_project_subtype_name = projectDataObject.getString("ptd_project_subtype_name");
                        ptd_project_subtype_name = transformText(ptd_project_subtype_name);

                        String psc_sanction_cat_name = projectDataObject.getString("psc_sanction_cat_name");
                        psc_sanction_cat_name = transformText(psc_sanction_cat_name);

                        String pcm_category_name = projectDataObject.getString("pcm_category_name");
                        pcm_category_name = transformText(pcm_category_name);

                        String pcu_dd_id = projectDataObject.getString("pcu_dd_id");
                        String pcm_proj_evaluation_remarks = projectDataObject.getString("pcm_proj_evaluation_remarks");
                        pcm_proj_evaluation_remarks = transformText(pcm_proj_evaluation_remarks);

                        String pcmgd_type_flag = projectDataObject.getString("pcmgd_type_flag");
                        pcmgd_type_flag = transformText(pcmgd_type_flag);

                        String pcm_project_details = projectDataObject.getString("pcm_project_details");
                        pcm_project_details = transformText(pcm_project_details);

                        String start_date = projectDataObject.getString("start_date");
                        String end_date = projectDataObject.getString("end_date");
                        String pcm_project_sanction_type = projectDataObject.getString("pcm_project_sanction_type");
                        String pcfd_project_physical_length = projectDataObject.getString("pcfd_project_physical_length");
                        String pcfd_project_physical_width = projectDataObject.getString("pcfd_project_physical_width");
                        String pcfd_distance_measure_unit = projectDataObject.getString("pcfd_distance_measure_unit");
                        String rownumber_ = projectDataObject.getString("rownumber_");

                        String map_data_available = projectDataObject.getString("map_data_available");

                        boolean map_data;
                        map_data = !map_data_available.equals("0");

                        String image_data_available = projectDataObject.getString("image_data_available");

                        boolean image_data;
                        image_data = !image_data_available.equals("0");

                        countingNum[0]++;
                        String stype = "";
                        switch (pcm_project_sanction_type) {
                            case "0":
                                stype = "Taka(টাকা)";
                                break;
                            case "1":
                                stype = "Rice(চাল) (MT)";
                                break;
                            case "2":
                                stype = "Wheat(গম) (MT)";
                                break;
                        }

                        String measureUnit = "";
                        switch (pcfd_distance_measure_unit) {
                            case "1":
                                measureUnit = " Meter [MT]";
                                break;
                            case "2":
                                measureUnit = " Feet [FT]";
                                break;
                        }

                        String pCount = "#"+countingNum[0];
                        String length = pcfd_project_physical_length + measureUnit;
                        String width = pcfd_project_physical_width + measureUnit;

                        projectlists.add(new Projectlists(pcm_id,entry_date,pcm_internal_no,
                                pcm_project_code,pcm_user,pcm_project_name,
                                pcm_project_no,pcm_project_date,pcm_pic_chairman_name,
                                pcm_pic_chairman_details,pcm_estimate_project_value,fy_financial_year_name,
                                fsm_fund_name,ptm_project_type_name,ptd_project_subtype_name,
                                psc_sanction_cat_name,pcm_category_name,null,
                                pcu_dd_id,pcm_proj_evaluation_remarks,pcmgd_type_flag,
                                pcm_project_details,start_date,end_date,
                                stype,length,width,map_data,image_data,rownumber_,pCount,new ArrayList<>()));


//                        numberOfRequestsToMake++;
//                        System.out.println(projectlists.size() +", index: "+ i + "number of requests: "+ numberOfRequestsToMake);
//                        getLocations(pcm_id,i,projectlists);


                    }
                    getLocations();
                }
                else {
                    conn = true;
                    goToProjectLists();
                }

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                goToProjectLists();
            }
        }, error -> {
            conn = false;
            goToProjectLists();
        });

        requestQueue.add(projectDataRequest);
    }

    public void getLocations() {
        String url = api_pre_url + "all_locations/project_locations";

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");

                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject locationObject = jsonArray.getJSONObject(i);
                        String pcmgd_latitude = locationObject.getString("pcmgd_latitude");
                        String pcmgd_longitude = locationObject.getString("pcmgd_longitude");
                        int segment = locationObject.getInt("segment");
                        String pcmgd_pcm_id = locationObject.getString("pcmgd_pcm_id");

                        for (int j = 0; j < projectlists.size(); j++) {
                            if (pcmgd_pcm_id.equals(projectlists.get(j).getPcmId())) {
                                ArrayList<LocationLists> locationLists = projectlists.get(j).getLocationLists();
                                locationLists.add(new LocationLists(pcmgd_latitude,pcmgd_longitude,segment));
                                projectlists.get(j).setLocationLists(locationLists);
                            }
                        }
                    }
                }
                conn = true;
                goToProjectLists();

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                goToProjectLists();
            }
        }, error -> {
            conn = false;
            goToProjectLists();
        });

        requestQueue.add(stringRequest);
    }
//    public void getLocations(String pcm_ID, int index, ArrayList<Projectlists> arrayList) {
//
//        String url = api_pre_url + "projects/projectLocation?pcm_id="+pcm_ID;
//
//        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
//            try {
//                numberOfRequestsToMake--;
//                JSONObject jsonObject = new JSONObject(response);
//                String items = jsonObject.getString("items");
//                String count = jsonObject.getString("count");
//                ArrayList<LocationLists> locationLists = new ArrayList<>();
//                if (!count.equals("0")) {
//                    JSONArray jsonArray = new JSONArray(items);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject locationObject = jsonArray.getJSONObject(i);
//                        String pcmgd_latitude = locationObject.getString("pcmgd_latitude");
//                        String pcmgd_longitude = locationObject.getString("pcmgd_longitude");
//                        int segment = locationObject.getInt("segment");
//                        locationLists.add(new LocationLists(pcmgd_latitude,pcmgd_longitude,segment));
//
//                    }
//                }
//                arrayList.get(index).setLocationLists(locationLists);
////                System.out.println("number of requests remain: "+numberOfRequestsToMake);
//                if (numberOfRequestsToMake == 0) {
//                    if (!hasRequestFailed) {
//                        conn = true;
//                        goToProjectLists();
//                    }
//                    else {
//                        conn = false;
//                        goToProjectLists();
//                    }
//                }
//
//            } catch (JSONException e) {
//                numberOfRequestsToMake--;
//                e.printStackTrace();
//                hasRequestFailed = true;
//                if(numberOfRequestsToMake == 0) {
//                    //The last request failed
//                    conn = false;
//                    goToProjectLists();
//                }
//            }
//        }, error -> {
//            numberOfRequestsToMake--;
//            hasRequestFailed = true;
//            if(numberOfRequestsToMake == 0) {
//                //The last request failed
//                conn = false;
//                goToProjectLists();
//            }
//        });
//
//        requestQueue.add(stringRequest);
//    }

    public void goToProjectLists() {
        waitProgress.dismiss();
        if (conn) {
            if (!projectlists.isEmpty()) {
                System.out.println(projectlists.size());
                Intent intent = new Intent(HomePage.this, Projects.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(),"No Project Found",Toast.LENGTH_SHORT).show();
            }
            conn = false;

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .show();

//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                getProjectData();
                dialog.dismiss();
            });
        }
    }

//    public void ProjectMapDataQuery() {
//        try {
//            this.connection = createConnection();
//
//            projectMapsLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            if (ptm_id != null) {
//                if (ptm_id.isEmpty()) {
//                    ptm_id = null;
//                }
//            }
//            if (fsm_id != null) {
//                if (fsm_id.isEmpty()) {
//                    fsm_id = null;
//                }
//            }
//
//            if (ddu_id != null) {
//                if (ddu_id.isEmpty()) {
//                    ddu_id = null;
//                }
//            }
//
//            if (dist_id != null) {
//                if (dist_id.isEmpty()) {
//                    dist_id = null;
//                }
//            }
//
//            if (dd_id != null) {
//                if (dd_id.isEmpty()) {
//                    dd_id = null;
//                }
//            }
//            if (ptd_Id != null) {
//                if (ptd_Id.isEmpty()) {
//                    ptd_Id = null;
//                }
//            }
//
//            if (div_id != null) {
//                if (div_id.isEmpty()) {
//                    div_id = null;
//                }
//            }
//
//            int count = 0;
//            ResultSet resultSet = stmt.executeQuery("SELECT * FROM (\n" +
//                    "            SELECT p.*, ROW_NUMBER() OVER (ORDER BY p.PCM_PROJECT_DATE DESC, p.PCM_ID DESC) as ROWNUMBER_ FROM ( \n" +
//                    "        SELECT DISTINCT PROJECT_CREATION_MST.PCM_ID,\n" +
//                    "        TO_CHAR(PROJECT_CREATION_MST.PCM_ENTRY_DATE,'DD-MON-RR') ENTRY_DATE,\n" +
//                    "        PROJECT_CREATION_MST.PCM_INTERNAL_NO,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_CODE,\n" +
//                    "        PROJECT_CREATION_MST.PCM_USER,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_NAME,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_NO,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJECT_DATE,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PIC_CHAIRMAN_NAME,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PIC_CHAIRMAN_DETAILS,\n" +
//                    "        PROJECT_CREATION_MST.PCM_ESTIMATE_PROJECT_VALUE,\n" +
//                    "        FINANCIAL_YEAR.FY_FINANCIAL_YEAR_NAME,\n" +
//                    "        FUND_SOURCE_MST.FSM_FUND_NAME,\n" +
//                    "        PROJECT_TYPE_MST.PTM_PROJECT_TYPE_NAME,\n" +
//                    "        PROJECT_TYPE_DTL.PTD_PROJECT_SUBTYPE_NAME,\n" +
//                    "        PROJECT_SANCTION_CATEGORY.PSC_SANCTION_CAT_NAME,\n" +
//                    "        PROJECT_CATEGORY_MST.PCM_CATEGORY_NAME,\n" +
//                    "        --PROJECT_CREATION_UNION.PCUN_DDU_ID,\n" +
//                    "        PROJECT_CREATION_UPOZILA.PCU_DD_ID,\n" +
//                    "        PROJECT_CREATION_MST.PCM_PROJ_EVALUATION_REMARKS,\n" +
//                    "        PROJECT_CREATION_MST_GPS_DTL.PCMGD_TYPE_FLAG,PROJECT_CREATION_MST.PCM_PROJECT_DETAILS,\n" +
//                    "        TO_CHAR(PROJECT_CREATION_MST.PCM_ESTIMATE_START_DATE,'DD-MON-RR') START_DATE,\n" +
//                    "        TO_CHAR(PROJECT_CREATION_MST.PCM_ESTIMATE_END_DATE,'DD-MON-RR') END_DATE,PROJECT_CREATION_MST.PCM_PROJECT_SANCTION_TYPE,\n" +
//                    "        PROJECT_CREATION_FUND_DTLS.PCFD_PROJECT_PHYSICAL_LENGTH,\n" +
//                    "        PROJECT_CREATION_FUND_DTLS.PCFD_PROJECT_PHYSICAL_WIDTH,\n" +
//                    "        PCFD_DISTANCE_MEASURE_UNIT\n" +
//                    "    FROM\n" +
//                    "        PROJECT_CREATION_MST,\n" +
//                    "        PROJECT_CREATION_UPOZILA,\n" +
//                    "        PROJECT_CREATION_UNION,\n" +
//                    "        PROJECT_CREATION_VILLAGE,\n" +
//                    "        PROJECT_CREATION_WARD,\n" +
//                    "        PROJECT_CREATION_FUND_DTLS,\n" +
//                    "        FINANCIAL_YEAR,\n" +
//                    "        FUND_SOURCE_MST,\n" +
//                    "        PROJECT_TYPE_MST,\n" +
//                    "        PROJECT_TYPE_DTL,\n" +
//                    "        PROJECT_SANCTION_CATEGORY,\n" +
//                    "        PROJECT_CATEGORY_MST,\n" +
//                    "        PROJECT_CREATION_MST_GPS_DTL\n" +
//                    "    WHERE FINANCIAL_YEAR.FY_ID = PROJECT_CREATION_MST.PCM_FY_ID\n" +
//                    "        AND PROJECT_CREATION_FUND_DTLS.PCFD_PCM_ID = PROJECT_CREATION_MST.PCM_ID\n" +
//                    "        AND FUND_SOURCE_MST.FSM_ID = PROJECT_CREATION_MST.PCM_FSM_ID\n" +
//                    "        AND PROJECT_TYPE_MST.PTM_ID = PROJECT_CREATION_MST.PCM_PTM_ID\n" +
//                    "        AND PROJECT_TYPE_DTL.PTD_ID = PROJECT_CREATION_MST.PCM_PTD_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_PSC_ID = PROJECT_SANCTION_CATEGORY.PSC_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_PCM_ID = PROJECT_CATEGORY_MST.PCM_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_ID = PROJECT_CREATION_UPOZILA.PCU_PCM_ID\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_ID = PROJECT_CREATION_MST_GPS_DTL.PCMGD_PCM_ID\n" +
//                    "        AND PROJECT_CREATION_UNION.PCUN_PCU_ID = PROJECT_CREATION_UPOZILA.PCU_ID\n" +
//                    "        AND PROJECT_CREATION_UNION.PCUN_ID = PROJECT_CREATION_WARD.PCW_PCUN_ID (+)\n" +
//                    "        AND PROJECT_CREATION_WARD.PCW_ID = PROJECT_CREATION_VILLAGE.PCV_PCW_ID (+)\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_PROJ_EVALUATION_FLAG = 1 \n" +
//                    "        AND (PROJECT_CREATION_MST.PCM_PTD_ID = "+ptd_Id+" OR "+ptd_Id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_MST.PCM_PTM_ID = "+ptm_id+" OR "+ptm_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_MST.PCM_FSM_ID = "+fsm_id+" OR "+fsm_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UNION.PCUN_DDU_ID = "+ddu_id+" OR "+ddu_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UPOZILA.PCU_DD_ID = "+dd_id+" OR "+dd_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UPOZILA.PCU_DIST_ID = "+dist_id+" OR "+dist_id+" IS NULL )\n" +
//                    "        AND (PROJECT_CREATION_UPOZILA.PCU_DIV_ID = "+div_id+" OR "+div_id+" IS NULL )\n" +
//                    "        AND PROJECT_CREATION_MST.PCM_FY_ID BETWEEN "+fys_id+" AND "+fye_id+" ) p\n" +
//                    "    ORDER BY p.PCM_PROJECT_DATE DESC, p.PCM_ID DESC )\n");
//
//            while (resultSet.next()) {
//
//                count++;
//                String stype = "";
//                switch (resultSet.getString(24)) {
//                    case "0":
//                        stype = "Taka(টাকা)";
//                        break;
//                    case "1":
//                        stype = "Rice(চাল) (MT)";
//                        break;
//                    case "2":
//                        stype = "Wheat(গম) (MT)";
//                        break;
//                }
//
//                String measureUnit = "";
//                switch (resultSet.getString(27)) {
//                    case "1":
//                        measureUnit = " Meter [MT]";
//                        break;
//                    case "2":
//                        measureUnit = " Feet [FT]";
//                        break;
//                }
//
//                String pCount = "#"+count;
//                String length = resultSet.getString(25) + measureUnit;
//                String width = resultSet.getString(26) + measureUnit;
//
////                projectMapsLists.add(new ProjectMapsLists(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
////                        resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),
////                        resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),
////                        resultSet.getString(10),resultSet.getString(11),resultSet.getString(12),
////                        resultSet.getString(13),resultSet.getString(14),resultSet.getString(15),
////                        resultSet.getString(16),resultSet.getString(17),resultSet.getString(18),
////                        resultSet.getString(19),resultSet.getString(20),resultSet.getString(21),
////                        resultSet.getString(22), resultSet.getString(23),resultSet.getString(24),
////                        stype,resultSet.getString(26), new ArrayList<>()));
//                projectMapsLists.add(new ProjectMapsLists(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
//                        resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),
//                        resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),
//                        resultSet.getString(10),resultSet.getString(11),resultSet.getString(12),
//                        resultSet.getString(13),resultSet.getString(14),resultSet.getString(15),
//                        resultSet.getString(16),resultSet.getString(17),null,
//                        resultSet.getString(18),resultSet.getString(19),resultSet.getString(20),
//                        resultSet.getString(21), resultSet.getString(22),resultSet.getString(23),
//                        stype,length,width,resultSet.getString(28), pCount, new ArrayList<>()));
//            }
//
//            resultSet.close();
//
//            for (int i = 0 ; i < projectMapsLists.size(); i++) {
//                String pcmid = projectMapsLists.get(i).getPcmId();
//                ArrayList<LocationLists> locationLists = new ArrayList<>();
//                ResultSet resultSet1 = stmt.executeQuery("SELECT \n" +
//                        "PCMGD_LATITUDE,\n" +
//                        "PCMGD_LONGITUDE,\n" +
//                        "PCMGD_LATITUDE_NUM,\n" +
//                        "PCMGD_LONGITUDE_NUM,\n" +
//                        "NVL(PCMGD_SEGMENT,0)\n"+
//                        "FROM project_creation_mst_gps_dtl WHERE PCMGD_PCM_ID = "+pcmid+" AND PCMGD_ACTIVE_FLAG=1 order by pcmgd_id ASC");
//
//                while (resultSet1.next()) {
//                    locationLists.add(new LocationLists(resultSet1.getString(1),resultSet1.getString(2),resultSet1.getInt(5)));
//                }
//
//                resultSet1.close();
//
//                projectMapsLists.get(i).setLocationLists(locationLists);
//
//            }
//
//            stmt.close();
//
//            if (ptm_id == null) {
//                ptm_id = "";
//            }
//
//            if (fsm_id == null) {
//                fsm_id = "";
//            }
//
//            if (ddu_id == null) {
//                ddu_id = "";
//            }
//
//            if (dist_id == null) {
//                dist_id = "";
//            }
//
//            if (dd_id == null) {
//                dd_id = "";
//            }
//
//            if (ptd_Id == null) {
//                ptd_Id = "";
//            }
//
//            if (div_id == null) {
//                div_id = "";
//            }
//
//            connected = true;
//
//            connection.close();
//
//
//        } catch (Exception e) {
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//    }

//    --------------------------Getting Project Map Data and going to project lists-----------------------------
    public void getProjectMapData() {
        waitProgress.show(getSupportFragmentManager(), "WaitBar");
        waitProgress.setCancelable(false);
        conn = false;
//        numberOfRequestsToMake = 0;
//        hasRequestFailed = false;

        projectMapsLists = new ArrayList<>();

        if (ptm_id == null) {
            ptm_id = "";
        }

        if (fsm_id == null) {
            fsm_id = "";
        }

        if (ddu_id == null) {
            ddu_id = "";
        }

        if (dist_id == null) {
            dist_id = "";
        }

        if (dd_id == null) {
            dd_id = "";
        }

        if (ptd_Id == null) {
            ptd_Id = "";
        }

        if (div_id == null) {
            div_id = "";
        }

        final int[] countingNum = {0};

        String projectMapDataUrl = api_pre_url + "projects/projectMapData?ptd_Id="+ptd_Id+"&ptm_id="+ptm_id+
                "&fsm_id="+fsm_id+"&ddu_id="+ddu_id+"&dd_id="+dd_id+"&dist_id="+dist_id+"&div_id="+div_id+"&fys_id="+fys_id+"&fye_id="+fye_id;

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, projectMapDataUrl, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject projectMapDataObject = jsonArray.getJSONObject(i);

                        String pcm_id = projectMapDataObject.getString("pcm_id");
                        String entry_date = projectMapDataObject.getString("entry_date");
                        String pcm_internal_no = projectMapDataObject.getString("pcm_internal_no");

                        String pcm_project_code = projectMapDataObject.getString("pcm_project_code");
                        pcm_project_code = transformText(pcm_project_code);

                        String pcm_user = projectMapDataObject.getString("pcm_user");

                        String pcm_project_name = projectMapDataObject.getString("pcm_project_name");
                        pcm_project_name = transformText(pcm_project_name);

                        String pcm_project_no = projectMapDataObject.getString("pcm_project_no");
                        pcm_project_no = transformText(pcm_project_no);

                        String pcm_project_date = projectMapDataObject.getString("pcm_project_date");

                        String pcm_pic_chairman_name = projectMapDataObject.getString("pcm_pic_chairman_name");
                        pcm_pic_chairman_name = transformText(pcm_pic_chairman_name);

                        String pcm_pic_chairman_details = projectMapDataObject.getString("pcm_pic_chairman_details");
                        pcm_pic_chairman_details = transformText(pcm_pic_chairman_details);

                        String pcm_estimate_project_value = projectMapDataObject.getString("pcm_estimate_project_value");
                        String fy_financial_year_name = projectMapDataObject.getString("fy_financial_year_name");

                        String fsm_fund_name = projectMapDataObject.getString("fsm_fund_name");
                        fsm_fund_name = transformText(fsm_fund_name);

                        String ptm_project_type_name = projectMapDataObject.getString("ptm_project_type_name");
                        ptm_project_type_name = transformText(ptm_project_type_name);

                        String ptd_project_subtype_name = projectMapDataObject.getString("ptd_project_subtype_name");
                        ptd_project_subtype_name = transformText(ptd_project_subtype_name);

                        String psc_sanction_cat_name = projectMapDataObject.getString("psc_sanction_cat_name");
                        psc_sanction_cat_name = transformText(psc_sanction_cat_name);

                        String pcm_category_name = projectMapDataObject.getString("pcm_category_name");
                        pcm_category_name = transformText(pcm_category_name);

                        String pcu_dd_id = projectMapDataObject.getString("pcu_dd_id");
                        String pcm_proj_evaluation_remarks = projectMapDataObject.getString("pcm_proj_evaluation_remarks");
                        pcm_proj_evaluation_remarks = transformText(pcm_proj_evaluation_remarks);

                        String pcmgd_type_flag = projectMapDataObject.getString("pcmgd_type_flag");
                        pcmgd_type_flag = transformText(pcmgd_type_flag);

                        String pcm_project_details = projectMapDataObject.getString("pcm_project_details");
                        pcm_project_details = transformText(pcm_project_details);

                        String start_date = projectMapDataObject.getString("start_date");
                        String end_date = projectMapDataObject.getString("end_date");
                        String pcm_project_sanction_type = projectMapDataObject.getString("pcm_project_sanction_type");
                        String pcfd_project_physical_length = projectMapDataObject.getString("pcfd_project_physical_length");
                        String pcfd_project_physical_width = projectMapDataObject.getString("pcfd_project_physical_width");
                        String pcfd_distance_measure_unit = projectMapDataObject.getString("pcfd_distance_measure_unit");
                        String rownumber_ = projectMapDataObject.getString("rownumber_");

                        countingNum[0]++;
                        String stype = "";
                        switch (pcm_project_sanction_type) {
                            case "0":
                                stype = "Taka(টাকা)";
                                break;
                            case "1":
                                stype = "Rice(চাল) (MT)";
                                break;
                            case "2":
                                stype = "Wheat(গম) (MT)";
                                break;
                        }

                        String measureUnit = "";
                        switch (pcfd_distance_measure_unit) {
                            case "1":
                                measureUnit = " Meter [MT]";
                                break;
                            case "2":
                                measureUnit = " Feet [FT]";
                                break;
                        }

                        String pCount = "#"+countingNum[0];
                        String length = pcfd_project_physical_length + measureUnit;
                        String width = pcfd_project_physical_width + measureUnit;

                        projectMapsLists.add(new ProjectMapsLists(pcm_id,entry_date,pcm_internal_no,
                                pcm_project_code,pcm_user,pcm_project_name,
                                pcm_project_no,pcm_project_date,pcm_pic_chairman_name,
                                pcm_pic_chairman_details,pcm_estimate_project_value,fy_financial_year_name,
                                fsm_fund_name,ptm_project_type_name,ptd_project_subtype_name,
                                psc_sanction_cat_name,pcm_category_name,null,
                                pcu_dd_id,pcm_proj_evaluation_remarks,pcmgd_type_flag,
                                pcm_project_details,start_date,end_date,
                                stype,length,width,rownumber_,pCount,new ArrayList<>()));


//                        numberOfRequestsToMake++;
//                        System.out.println(projectMapsLists.size() +", index: "+ i + "number of requests: "+ numberOfRequestsToMake);
//                        getMapLocations(pcm_id,i,projectMapsLists);

                    }
                    getMapLocations();
                }
                else {
                    conn = true;
                    goToProjectMapLists();
                }

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                goToProjectMapLists();
            }
        }, error -> {
            conn = false;
            goToProjectMapLists();
        });

        requestQueue.add(stringRequest);
    }

    public void getMapLocations() {
        String url = api_pre_url + "all_locations/project_locations";

        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");

                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject locationObject = jsonArray.getJSONObject(i);
                        String pcmgd_latitude = locationObject.getString("pcmgd_latitude");
                        String pcmgd_longitude = locationObject.getString("pcmgd_longitude");
                        int segment = locationObject.getInt("segment");
                        String pcmgd_pcm_id = locationObject.getString("pcmgd_pcm_id");

                        for (int j = 0; j < projectMapsLists.size(); j++) {
                            if (pcmgd_pcm_id.equals(projectMapsLists.get(j).getPcmId())) {
                                ArrayList<LocationLists> locationLists = projectMapsLists.get(j).getLocationLists();
                                locationLists.add(new LocationLists(pcmgd_latitude,pcmgd_longitude,segment));
                                projectMapsLists.get(j).setLocationLists(locationLists);
                            }
                        }
                    }
                }
                conn = true;
                goToProjectMapLists();

            } catch (JSONException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                conn = false;
                goToProjectMapLists();
            }
        }, error -> {
            conn = false;
            goToProjectMapLists();
        });

        requestQueue.add(stringRequest);
    }
//    public void getMapLocations(String pcm_ID, int index, ArrayList<ProjectMapsLists> arrayList) {
//        String url = api_pre_url + "projects/projectLocation?pcm_id="+pcm_ID;
//
//        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
//            try {
//                numberOfRequestsToMake--;
//                JSONObject jsonObject = new JSONObject(response);
//                String items = jsonObject.getString("items");
//                String count = jsonObject.getString("count");
//                ArrayList<LocationLists> locationLists = new ArrayList<>();
//                if (!count.equals("0")) {
//                    JSONArray jsonArray = new JSONArray(items);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject locationObject = jsonArray.getJSONObject(i);
//                        String pcmgd_latitude = locationObject.getString("pcmgd_latitude");
//                        String pcmgd_longitude = locationObject.getString("pcmgd_longitude");
//                        int segment = locationObject.getInt("segment");
//                        locationLists.add(new LocationLists(pcmgd_latitude,pcmgd_longitude,segment));
//
//                    }
//                }
//                arrayList.get(index).setLocationLists(locationLists);
////                System.out.println("number of requests remain: "+numberOfRequestsToMake);
//                if (numberOfRequestsToMake == 0) {
//                    if (!hasRequestFailed) {
//                        conn = true;
//                        goToProjectMapLists();
//                    }
//                    else {
//                        conn = false;
//                        goToProjectMapLists();
//                    }
//                }
//
//            } catch (JSONException e) {
//                numberOfRequestsToMake--;
//                e.printStackTrace();
//                hasRequestFailed = true;
//                if(numberOfRequestsToMake == 0) {
//                    //The last request failed
//                    conn = false;
//                    goToProjectMapLists();
//                }
//            }
//        }, error -> {
//            numberOfRequestsToMake--;
//            hasRequestFailed = true;
//            if(numberOfRequestsToMake == 0) {
//                //The last request failed
//                conn = false;
//                goToProjectLists();
//            }
//        });
//
//        requestQueue.add(stringRequest);
//    }

    public void goToProjectMapLists() {
        waitProgress.dismiss();
        if (conn) {
            if (!projectMapsLists.isEmpty()) {
                System.out.println(projectMapsLists.size());
                Intent intent = new Intent(HomePage.this, ProjectsMaps.class);
                intent.putExtra("DIST_ID",dist_id);
                intent.putExtra("DD_ID",dd_id);
                intent.putExtra("DDU_ID",ddu_id);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(),"No Project Found",Toast.LENGTH_SHORT).show();
            }

            conn = false;

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .show();

//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                getProjectMapData();
                dialog.dismiss();
            });
        }
    }

    //    --------------------------Transforming Bangla Text-----------------------------
    private String transformText(String text) {
        byte[] bytes = text.getBytes(ISO_8859_1);
        return new String(bytes, UTF_8);
    }
}