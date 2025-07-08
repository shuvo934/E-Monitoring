package com.shuvo.ttit.bridgeculvert.threesixtyimage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shuvo.ttit.bridgeculvert.R;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

//import pl.rjuszczyk.panorama.viewer.PanoramaGLSurfaceView;

import static com.shuvo.ttit.bridgeculvert.projectDetails.ProjectDetails.URL_360;


public class ThreeSixtyImage extends AppCompatActivity {


//    private PanoramaGLSurfaceView panoramaGLSurfaceView;
    ImageView loading;
    WebView webView;

//    private VrPanoramaView mVRPanoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_sixty_image);

        loading = findViewById(R.id.loading_image);
        loading.setVisibility(View.GONE);
        webView = findViewById(R.id.webview);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String htmlContent = getHtmlFromRaw(R.raw.three_sixty_web);

        webView.loadDataWithBaseURL("file:///android_res/raw/", htmlContent, "text/html", "UTF-8", null);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String base = convertImageToBase64(URL_360);
                // Example of setting a dynamic image
//                String jsCode = "setImage('" +"https://cors-anywhere.herokuapp.com/"+ URL_360 + "');";

                String jsCode = "setImage('" + "data:image/jpeg;base64,["+base+"]" + "');";
                webView.evaluateJavascript(jsCode, null);
            }
        });

//        mVRPanoramaView = findViewById(R.id.vr_panorama_image);

//        loadPhotoSphere();
//        panoramaGLSurfaceView = findViewById(R.id.panorama);

//        Glide.with(ThreeSixtyImage.this)
//                .asBitmap()
//                .load(URL_360)
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.loading_error)
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Matrix matrix = new Matrix();
//                        matrix.preScale(-1.0f, 1.0f);
//                        resource = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight(), matrix, true);
//                        panoramaGLSurfaceView.setPanoramaBitmap(resource);
//                        System.out.println("getting resource");
//                        loading.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//                        loading.setVisibility(View.GONE);
//                        System.out.println("LOADING FINISH");
//                    }
//
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        super.onLoadFailed(errorDrawable);
//                        loading.setVisibility(View.VISIBLE);
//                        loading.setImageResource(R.drawable.loading_error);
//                        System.out.println("LOADING ERROR");
//                    }
//
//                });

    }

    public String convertImageToBase64(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getHtmlFromRaw(int rawResource) {
        InputStream inputStream = getResources().openRawResource(rawResource);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            int character;
            while ((character = inputStream.read()) != -1) {
                stringBuilder.append((char) character);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    private void loadPhotoSphere() {
//        VrPanoramaView.Options options = new VrPanoramaView.Options();
//        InputStream inputStream = null;

//        AssetManager assetManager = getAssets();
//        try {
//            inputStream = assetManager.open("image.jpg");
//            options.inputType = VrPanoramaView.Options.TYPE_MONO;
//            mVRPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
//            inputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Glide.with(ThreeSixtyImage.this)
                .asBitmap()
                .load(URL_360)
                .placeholder(R.drawable.three_sixty_loading)
                .error(R.drawable.loading_error)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Matrix matrix = new Matrix();
//                        matrix.preScale(-1.0f, 1.0f);
//                        resource = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight(), matrix, true);
//                        panoramaGLSurfaceView.setPanoramaBitmap(resource);
//                        System.out.println("getting resource");
//                        loading.setVisibility(View.GONE);
//                        options.inputType = VrPanoramaView.Options.TYPE_MONO;
//                        mVRPanoramaView.loadImageFromBitmap(resource, options);
                        System.out.println("getting resource");
                        loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        loading.setVisibility(View.GONE);
                        System.out.println("LOADING FINISH");
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        loading.setVisibility(View.VISIBLE);
                        loading.setImageResource(R.drawable.loading_error);
                        System.out.println("LOADING ERROR");
                    }

                });

    }



    @Override
    protected void onResume() {
        super.onResume();
        //panoramaGLSurfaceView.onResume();
//        mVRPanoramaView.resumeRendering();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //panoramaGLSurfaceView.onPause();
//        mVRPanoramaView.pauseRendering();

    }

    @Override
    protected void onDestroy() {
//        mVRPanoramaView.shutdown();
        super.onDestroy();
    }
}