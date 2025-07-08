package com.shuvo.ttit.bridgeculvert.projectPicture;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shuvo.ttit.bridgeculvert.R;
import com.shuvo.ttit.bridgeculvert.adapter.PhotoAdapter;
import com.shuvo.ttit.bridgeculvert.arraylist.PhotoList;
import com.shuvo.ttit.bridgeculvert.progressbar.WaitProgress;

//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;

//import static com.shuvo.ttit.bridgeculvert.connection.OracleConnection.createConnection;
import static com.shuvo.ttit.bridgeculvert.projectDetails.ProjectDetails.PCM_ID_PD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProjectPicture extends AppCompatActivity {

    WaitProgress waitProgress = new WaitProgress();
    private Boolean conn = false;
//    private Boolean connected = false;
//    private Connection connection;

    String pcm = "";

    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    RecyclerView.LayoutManager layoutManager;

    TextView noPhotoMsg;

    ArrayList<PhotoList> photoLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_picture);
        View navScrim = findViewById(R.id.nav_bar_project_pictures);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.project_picture_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            ViewGroup.LayoutParams lp = navScrim.getLayoutParams();
            lp.height = systemBars.bottom;
            navScrim.setLayoutParams(lp);
            return insets;
        });

        recyclerView = findViewById(R.id.photo_list_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        noPhotoMsg = findViewById(R.id.no_photo_msg);
        noPhotoMsg.setVisibility(View.GONE);

        pcm = PCM_ID_PD;

//        new CheckFORLISt().execute();
        getImages();
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

//    public class CheckFORLISt extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(getSupportFragmentManager(),"WaitBar");
//            waitProgress.setCancelable(false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (isConnected() && isOnline()) {
//
//                ItemData();
//                if (connected) {
//                    conn = true;
//                }
//
//            } else {
//                conn = false;
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//
//            if (conn) {
//
//                conn = false;
//                connected = false;
//
//                if (photoLists.size() == 0) {
//                    noPhotoMsg.setVisibility(View.VISIBLE);
//                } else {
//                    noPhotoMsg.setVisibility(View.GONE);
//                }
//                photoAdapter = new PhotoAdapter(photoLists,ProjectPicture.this);
//                recyclerView.setAdapter(photoAdapter);
//                photoAdapter.notifyDataSetChanged();
//
//
//                waitProgress.dismiss();
//
//            }else {
//                waitProgress.dismiss();
//                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog;
//                dialog = new AlertDialog.Builder(getApplicationContext())
//                        .setMessage("Please Check Your Internet Connection")
//                        .setPositiveButton("Retry", null)
//                        .setNegativeButton("Cancel",null)
//                        .show();
//
//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new CheckFORLISt().execute();
//                        dialog.dismiss();
//                    }
//                });
//
//                Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//                negative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//            }
//        }
//
//    }

//    public void ItemData() {
//        try {
//            this.connection = createConnection();
//            //    Toast.makeText(MainActivity.this, "Connected",Toast.LENGTH_SHORT).show();
//
//            photoLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
////            if (USERNAME_CONNECTION.equals("MICROTERRAIN")) {
////
////                ResultSet resultSet3 = stmt.executeQuery("SELECT UD_DB_GENERATED_FILE_NAME, TO_CHAR(UD_DATE, 'DD-MON-RR'),UD_DOC_UPLOAD_STAGE \n" +
////                        "FROM UPLOADED_DOCS\n" +
////                        "WHERE UD_PCM_ID = "+pcm+"");
////
////                while (resultSet3.next()) {
////
////                    String stype = "";
////                    if (resultSet3.getString(3) != null) {
////                        switch (resultSet3.getString(3)) {
////                            case "1":
////                                stype = "Pre-Work";
////                                break;
////                            case "2":
////                                stype = "On-Working";
////                                break;
////                            case "3":
////                                stype = "Finish-Work";
////                                break;
////                        }
////                    }
////
////                    System.out.println(resultSet3.getString(1));
////                    String url = "";
////                    url = "http://103.56.208.123:8869/assets/project_image/"+resultSet3.getString(1);
////
////                    photoLists.add(new PhotoList(url, resultSet3.getString(2), stype));
////
////                }
////
////                resultSet3.close();
////                stmt.close();
////
////            } else {
//
//                ResultSet resultSet1 = stmt.executeQuery("SELECT UD_DB_GENERATED_FILE_NAME, TO_CHAR(UD_DATE, 'DD-MON-RR'),UD_DOC_UPLOAD_STAGE \n" +
//                        "FROM UPLOADED_DOCS WHERE NVL(UD_THREESIXTY_FLAG,0) = 0 AND UD_DOC_UPLOAD_STAGE = 1\n" +
//                        "AND UD_PCM_ID = "+pcm+"");
//
//                while (resultSet1.next()) {
//
//                    String stype = "Before Construction";
////                    if (resultSet1.getString(3) != null) {
////                        switch (resultSet1.getString(3)) {
////                            case "1":
////                                stype = "Before Construction";
////                                break;
////                            case "2":
////                                stype = "During Construction";
////                                break;
////                            case "3":
////                                stype = "After Construction";
////                                break;
////                        }
////                    }
//
//                    System.out.println(resultSet1.getString(1));
//                    String url = "";
//                    url = "http://103.56.208.123:8863/assets/project_image/" +resultSet1.getString(1);
//
//                    photoLists.add(new PhotoList(url, resultSet1.getString(2), stype));
//
//                }
//
//                resultSet1.close();
//
//            ResultSet resultSet2 = stmt.executeQuery("SELECT UD_DB_GENERATED_FILE_NAME, TO_CHAR(UD_DATE, 'DD-MON-RR'),UD_DOC_UPLOAD_STAGE \n" +
//                    "FROM UPLOADED_DOCS WHERE NVL(UD_THREESIXTY_FLAG,0) = 0 AND UD_DOC_UPLOAD_STAGE = 2\n" +
//                    "AND UD_PCM_ID = "+pcm+"");
//
//            while (resultSet2.next()) {
//
//                String stype = "During Construction";
////                if (resultSet2.getString(3) != null) {
////                    switch (resultSet2.getString(3)) {
////                        case "1":
////                            stype = "Before Construction";
////                            break;
////                        case "2":
////                            stype = "During Construction";
////                            break;
////                        case "3":
////                            stype = "After Construction";
////                            break;
////                    }
////                }
//
//                System.out.println(resultSet2.getString(1));
//                String url = "";
//                url = "http://103.56.208.123:8863/assets/project_image/" +resultSet2.getString(1);
//
//                photoLists.add(new PhotoList(url, resultSet2.getString(2), stype));
//
//            }
//
//            resultSet2.close();
//
//
////            ResultSet resultSetNew = stmt.executeQuery("SELECT PPI_IMAGE_DB_NAME, PPI_CSM_ID,\n" +
////                    "(SELECT CSM_STEP_FACTOR_NAME FROM CONS_STEP_MST WHERE CONS_STEP_MST.csm_id = PPI_CSM_ID)\n" +
////                    "FROM PROJ_PROGRESS_IMAGE\n" +
////                    "WHERE PPI_PCM_ID = "+pcm+"\n" +
////                    "ORDER BY PPI_CSM_ID");
////
////            while (resultSetNew.next()) {
////
////                String stype = "During Construction:\n" + resultSetNew.getString(3);
////
////                System.out.println(resultSetNew.getString(1));
////                String url = "";
////                url = "http://103.56.208.123:8086/terrain/f?p=102:93:11136922291540:" +resultSetNew.getString(1);
////
////                photoLists.add(new PhotoList(url, "", stype));
////
////            }
////
////            resultSetNew.close();
//
//
//            ResultSet resultSet3 = stmt.executeQuery("SELECT UD_DB_GENERATED_FILE_NAME, TO_CHAR(UD_DATE, 'DD-MON-RR'),UD_DOC_UPLOAD_STAGE \n" +
//                    "FROM UPLOADED_DOCS WHERE NVL(UD_THREESIXTY_FLAG,0) = 0 AND UD_DOC_UPLOAD_STAGE = 3\n" +
//                    "AND UD_PCM_ID = "+pcm+"");
//
//            while (resultSet3.next()) {
//
//                String stype = "After Construction";
////                if (resultSet3.getString(3) != null) {
////                    switch (resultSet3.getString(3)) {
////                        case "1":
////                            stype = "Before Construction";
////                            break;
////                        case "2":
////                            stype = "During Construction";
////                            break;
////                        case "3":
////                            stype = "After Construction";
////                            break;
////                    }
////                }
//
//                System.out.println(resultSet3.getString(1));
//                String url = "";
//                url = "http://103.56.208.123:8863/assets/project_image/" +resultSet3.getString(1);
//
//                photoLists.add(new PhotoList(url, resultSet3.getString(2), stype));
//
//            }
//
//            resultSet3.close();
//
//
//            stmt.close();
//
////            }
//
//
//
//            connected = true;
//
//            connection.close();
//
//        }
//        catch (Exception e) {
//
//            //   Toast.makeText(MainActivity.this, ""+e,Toast.LENGTH_LONG).show();
//            Log.i("ERRRRR", e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//    }

    public void getImages() {
        waitProgress.show(getSupportFragmentManager(),"WaitBar");
        waitProgress.setCancelable(false);
        conn = false;

        photoLists = new ArrayList<>();

        String image_url = "http://103.56.208.123:8086/terrain/bridge_culvert/images/getImages?pcm_id="+pcm;

        RequestQueue requestQueue = Volley.newRequestQueue(ProjectPicture.this);

        StringRequest imageRequest = new StringRequest(Request.Method.GET, image_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String items = jsonObject.getString("items");
                String count = jsonObject.getString("count");
                if (!count.equals("0")) {
                    JSONArray jsonArray = new JSONArray(items);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject imageObject = jsonArray.getJSONObject(i);

                        String ud_db_generated_file_name = imageObject.getString("ud_db_generated_file_name");
                        String ud_date = imageObject.getString("ud_date");
                        String ud_doc_upload_stage = imageObject.getString("ud_doc_upload_stage");

                        String stype = "";
                        switch (ud_doc_upload_stage) {
                            case "1":
                                stype = "Before Construction";
                                break;
                            case "2":
                                stype = "During Construction";
                                break;
                            case "3":
                                stype = "After Construction";
                                break;
                        }
                        String url = "";
                        url = "http://103.56.208.123:8863/assets/project_image/" +ud_db_generated_file_name;

                        photoLists.add(new PhotoList(url, ud_date, stype));

                    }
                }
                conn = true;
                updatePICUI();
            } catch (JSONException e) {
                e.printStackTrace();
                conn = false;
                updatePICUI();
            }
        }, error -> {
            conn = false;
            updatePICUI();
        });

        requestQueue.add(imageRequest);

    }

    public void updatePICUI() {
        if (conn) {
            conn = false;

            if (photoLists.size() == 0) {
                noPhotoMsg.setVisibility(View.VISIBLE);
            } else {
                noPhotoMsg.setVisibility(View.GONE);
            }
            photoAdapter = new PhotoAdapter(photoLists,ProjectPicture.this);
            recyclerView.setAdapter(photoAdapter);
            photoAdapter.notifyDataSetChanged();


            waitProgress.dismiss();

        }
        else {
            waitProgress.dismiss();
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog;
            dialog = new AlertDialog.Builder(ProjectPicture.this)
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .setNegativeButton("Cancel",null)
                    .show();

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getImages();
                    dialog.dismiss();
                }
            });

            Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }
}