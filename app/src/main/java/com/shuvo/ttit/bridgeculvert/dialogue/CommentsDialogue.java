package com.shuvo.ttit.bridgeculvert.dialogue;

import android.app.Dialog;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.shuvo.ttit.bridgeculvert.R;
import com.shuvo.ttit.bridgeculvert.progressbar.WaitProgress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.shuvo.ttit.bridgeculvert.Constants.api_pre_url;
import static com.shuvo.ttit.bridgeculvert.projectDetails.ProjectDetails.PCM_ID_PD;

import org.json.JSONException;
import org.json.JSONObject;


public class CommentsDialogue extends AppCompatDialogFragment {

    AppCompatActivity activity;
    AlertDialog alertDialog;
    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText comment;

    TextView emailMiss;
    TextView nameMiss;
    TextView commentMiss;

    Button submit;
    TextView cancel;

    WaitProgress waitProgress = new WaitProgress();
    private Boolean conn = false;
    private Boolean connected = false;
//    private Connection connection;

    String pcm = "";

    String nameOfUser = "";
    String emailOfUser = "";
    String commentOfUser = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.comment_dialogue_view, null);
        activity = (AppCompatActivity) requireContext();

        name = view.findViewById(R.id.name_edit);
        comment = view.findViewById(R.id.comment_edit);
        email = view.findViewById(R.id.email_edit);

        nameMiss = view.findViewById(R.id.name_edit_miss);
        emailMiss = view.findViewById(R.id.email_edit_miss);
        commentMiss = view.findViewById(R.id.comment_edit_miss);

        submit = view.findViewById(R.id.submit_button_dial);
        cancel = view.findViewById(R.id.cancel_comments_dial);

        pcm = PCM_ID_PD;

        builder.setView(view);

        alertDialog = builder.create();

        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        cancel.setOnClickListener(view1 -> alertDialog.dismiss());

        submit.setOnClickListener(view2 -> {
            System.out.println("ASHE POSITIVE");


            nameOfUser = Objects.requireNonNull(name.getText()).toString();
            emailOfUser = Objects.requireNonNull(email.getText()).toString();
            commentOfUser = Objects.requireNonNull(comment.getText()).toString();


            if (!nameOfUser.isEmpty()) {
                System.out.println("NAME");
                nameMiss.setVisibility(View.GONE);

                if (!emailOfUser.isEmpty()) {
                    System.out.println("EMAIL");
                    emailMiss.setVisibility(View.GONE);

                    if (!commentOfUser.isEmpty()) {
                        System.out.println("COMMENT");
                        commentMiss.setVisibility(View.GONE);
                        AlertDialog dialog;
                        dialog = new AlertDialog.Builder(requireContext())
                                .setMessage("Do you want to submit your Comment?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No",null)
                                .show();

                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positive.setOnClickListener(v -> {

//                                    new Check().execute();
                            postComment();
                            dialog.dismiss();
                        });

                        Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negative.setOnClickListener(v -> dialog.dismiss());
                    }
                    else {
                        commentMiss.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    emailMiss.setVisibility(View.VISIBLE);
                }
            } else {
                nameMiss.setVisibility(View.VISIBLE);
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (nameMiss.getVisibility() == View.VISIBLE) {
                    if (!editable.toString().isEmpty()) {
                        nameMiss.setVisibility(View.GONE);
                    }
                }

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (emailMiss.getVisibility() == View.VISIBLE) {
                    if (!editable.toString().isEmpty()) {
                        emailMiss.setVisibility(View.GONE);
                    }
                }

            }
        });

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (commentMiss.getVisibility() == View.VISIBLE ) {
                    if (!editable.toString().isEmpty()) {
                        commentMiss.setVisibility(View.GONE);
                    }
                }

            }
        });

        return alertDialog;
    }

//    public boolean isConnected() {
//        boolean connected = false;
//        boolean isMobile = false;
//        try {
//            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo nInfo = cm.getActiveNetworkInfo();
//            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
//            return connected;
//        } catch (Exception e) {
//            Log.e("Connectivity Exception", e.getMessage());
//        }
//        return connected;
//    }
//
//    public boolean isOnline() {
//
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//            int     exitValue = ipProcess.waitFor();
//            return (exitValue == 0);
//        }
//        catch (IOException | InterruptedException e)          { e.printStackTrace(); }
//
//        return false;
//    }

//    public class Check extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            waitProgress.show(activity.getSupportFragmentManager(),"WaitBar");
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
//                Toast.makeText(getContext(), "Comment Submitted Successfully!", Toast.LENGTH_SHORT).show();
//
//                waitProgress.dismiss();
//                alertDialog.dismiss();
//
//            }else {
//                waitProgress.dismiss();
//                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                AlertDialog dialog;
//                dialog = new AlertDialog.Builder(getContext())
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
//                        alertDialog.dismiss();
//
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
//            Statement stmt = connection.createStatement();
//
//            String pcof_id = "";
//
//            ResultSet resultSet = stmt.executeQuery("select nvl(max(pcof_id),0)+1 from project_creation_online_feed");
//
//            while (resultSet.next()) {
//                pcof_id = resultSet.getString(1);
//            }
//
//            stmt.executeUpdate("INSERT INTO PROJECT_CREATION_ONLINE_FEED(PCOF_ID, PCOF_PCM_ID, PCOF_SUBMITTER_NAME, PCOF_SUBMITTER_EMAIL, PCOF_SUBMITTER_MESSAGE, PCOF_TIME,\n" +
//                    "PCOF_APPROVAL_FLAG) VALUES ("+pcof_id+", "+pcm+", '"+nameOfUser+"', '"+emailOfUser+"','"+commentOfUser+"', SYSDATE, 1)");
//
//            resultSet.close();
//            stmt.close();
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

    public void postComment() {
        waitProgress.show(activity.getSupportFragmentManager(),"WaitBar");
        waitProgress.setCancelable(false);
        conn = false;
        connected = false;

        String post_url = api_pre_url+"comments/uploadComments";

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url, response -> {
            try {
                conn = true;
                JSONObject jsonObject = new JSONObject(response);
                String string_out = jsonObject.getString("string_out");
                if (string_out.equals("Successfully Created")) {
                    connected = true;
                    updateUI();
                }
                else {
                    connected = false;
                    updateUI();
                }
            } catch (JSONException e) {
                conn = false;
                updateUI();
            }
        }, error -> {
            conn = false;
            updateUI();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("pcm_id",pcm);
                headers.put("submitter_name",nameOfUser);
                headers.put("submiiter_email",emailOfUser);
                headers.put("submitter_message",commentOfUser);
                return headers;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void updateUI() {
        if (conn) {
            waitProgress.dismiss();
            if (connected) {
                Toast.makeText(requireContext(), "Comment Submitted Successfully!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
            else {
                Toast.makeText(requireContext(), "Comment Failed to Submit", Toast.LENGTH_SHORT).show();
                AlertDialog dialog;
                dialog = new AlertDialog.Builder(requireContext())
                        .setMessage("Comment Submission Failed")
                        .setPositiveButton("Retry", null)
                        .setNegativeButton("Cancel",null)
                        .show();

//            dialog.setCancelable(false);
//            dialog.setCanceledOnTouchOutside(false);
                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setOnClickListener(v -> {

                    conn = false;
                    connected = false;
                    postComment();
                    dialog.dismiss();
                });
            }

        }
        else {
            waitProgress.dismiss();
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog dialog;
            dialog = new AlertDialog.Builder(requireContext())
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Retry", null)
                    .setNegativeButton("Cancel",null)
                    .show();

//            dialog.setCancelable(false);
//            dialog.setCanceledOnTouchOutside(false);
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setOnClickListener(v -> {

                postComment();
                dialog.dismiss();
            });
        }
    }
}
