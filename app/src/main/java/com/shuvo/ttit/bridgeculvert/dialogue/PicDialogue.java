package com.shuvo.ttit.bridgeculvert.dialogue;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.shuvo.ttit.bridgeculvert.R;

import java.util.Objects;


public class PicDialogue extends AppCompatDialogFragment {

    AppCompatActivity activity;
    AlertDialog alertDialog;

    PhotoView photoView;
    ImageView clear;

//    WaitProgress waitProgress = new WaitProgress();
//    private Boolean conn = false;
//    private Boolean connected = false;
//    private Connection connection;
//
//    String pcm = "";
//
//    RecyclerView recyclerView;
//    PhotoAdapter photoAdapter;
//    RecyclerView.LayoutManager layoutManager;
//
//    TextView noPhotoMsg;
//
//    ArrayList<PhotoList> photoLists;
    Bitmap bitmap;
    String url;
    public PicDialogue(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.pic_dial_view, null);
        activity = (AppCompatActivity) view.getContext();
        photoView=  view.findViewById(R.id.photo_view);
        clear = view.findViewById(R.id.clear_image);

//        Glide
//                .with(requireContext())
//                .load(url)
//                .error(R.drawable.loading_error)
//                .placeholder(R.drawable.loading)
//                .into(photoView);

        Glide.with(requireContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_error)
                .listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (bitmap != null) {
                            Glide.with(requireContext())
                                    .load(bitmap)
                                    .error(R.drawable.loading_error)
                                    .placeholder(R.drawable.loading)
                                    .into(photoView);
                        } else {
                            // If bitmap is also null, let Glide show the error drawable
                            photoView.setImageResource(R.drawable.loading_error);
                        }
                        return true; // true = we handled the error ourselves
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(photoView);


//        recyclerView = view.findViewById(R.id.photo_list_recyclerView);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        noPhotoMsg = view.findViewById(R.id.no_photo_msg);
//        noPhotoMsg.setVisibility(View.GONE);
//
//        pcm = PCM_ID_PD;
//
//        new CheckFORLISt().execute();

        builder.setView(view);

        alertDialog = builder.create();

        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

//        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                alertDialog.dismiss();
//            }
//        });

        clear.setOnClickListener(view1 -> alertDialog.dismiss());

        return alertDialog;
    }

//    public boolean isConnected() {
//        boolean connected = false;
//        boolean isMobile = false;
//        try {
//            ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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

//    public class CheckFORLISt extends AsyncTask<Void, Void, Void> {
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
//                if (photoLists.size() == 0) {
//                    noPhotoMsg.setVisibility(View.VISIBLE);
//                } else {
//                    noPhotoMsg.setVisibility(View.GONE);
//                }
//                photoAdapter = new PhotoAdapter(photoLists,getContext());
//                recyclerView.setAdapter(photoAdapter);
//                photoAdapter.notifyDataSetChanged();
//
//
//                waitProgress.dismiss();
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
//                        alertDialog.dismiss();
//
//                    }
//                });
//            }
//        }
//
//    }
//
//    public void ItemData() {
//        try {
//            this.connection = createConnection();
//            //    Toast.makeText(MainActivity.this, "Connected",Toast.LENGTH_SHORT).show();
//
//            photoLists = new ArrayList<>();
//
//            Statement stmt = connection.createStatement();
//
//            if (USERNAME_CONNECTION.equals("MICROTERRAIN")) {
//
//                ResultSet resultSet1 = stmt.executeQuery("SELECT UD_DB_GENERATED_FILE_NAME, TO_CHAR(UD_DATE, 'DD-MON-RR'),UD_DOC_UPLOAD_STAGE \n" +
//                        "FROM UPLOADED_DOCS\n" +
//                        "WHERE UD_PCM_ID = "+pcm+"");
//
//                while (resultSet1.next()) {
//
//                    String stype = "";
//                    if (resultSet1.getString(3) != null) {
//                        switch (resultSet1.getString(3)) {
//                            case "1":
//                                stype = "Pre-Work";
//                                break;
//                            case "2":
//                                stype = "On-Working";
//                                break;
//                            case "3":
//                                stype = "Finish-Work";
//                                break;
//                        }
//                    }
//
//                    System.out.println(resultSet1.getString(1));
//                    String url = "";
//                    url = "http://103.56.208.123:8865/assets/project_image/"+resultSet1.getString(1);
//
//                    photoLists.add(new PhotoList(url, resultSet1.getString(2), stype));
//
//                }
//
//                resultSet1.close();
//                stmt.close();
//
//            } else {
//
//                ResultSet resultSet1 = stmt.executeQuery("SELECT UD_DB_GENERATED_FILE_NAME, TO_CHAR(UD_DATE, 'DD-MON-RR'),UD_DOC_UPLOAD_STAGE \n" +
//                        "FROM UPLOADED_DOCS WHERE NVL(UD_THREESIXTY_FLAG,0) = 0\n" +
//                        "AND UD_PCM_ID = "+pcm+"");
//
//                while (resultSet1.next()) {
//
//                    String stype = "";
//                    if (resultSet1.getString(3) != null) {
//                        switch (resultSet1.getString(3)) {
//                            case "1":
//                                stype = "Pre-Work";
//                                break;
//                            case "2":
//                                stype = "On-Working";
//                                break;
//                            case "3":
//                                stype = "Finish-Work";
//                                break;
//                        }
//                    }
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
//                stmt.close();
//
//            }
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
}
