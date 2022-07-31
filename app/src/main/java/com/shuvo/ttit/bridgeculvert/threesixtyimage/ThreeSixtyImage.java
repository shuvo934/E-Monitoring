package com.shuvo.ttit.bridgeculvert.threesixtyimage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.shuvo.ttit.bridgeculvert.R;


import java.io.InputStream;

//import pl.rjuszczyk.panorama.viewer.PanoramaGLSurfaceView;

import static com.shuvo.ttit.bridgeculvert.projectDetails.ProjectDetails.URL_360;


public class ThreeSixtyImage extends AppCompatActivity {


//    private PanoramaGLSurfaceView panoramaGLSurfaceView;
    ImageView loading;

    private VrPanoramaView mVRPanoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_sixty_image);

        loading = findViewById(R.id.loading_image);
        loading.setVisibility(View.VISIBLE);

        mVRPanoramaView = findViewById(R.id.vr_panorama_image);

        loadPhotoSphere();
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

    private void loadPhotoSphere() {
        VrPanoramaView.Options options = new VrPanoramaView.Options();
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
                        options.inputType = VrPanoramaView.Options.TYPE_MONO;
                        mVRPanoramaView.loadImageFromBitmap(resource, options);
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
        mVRPanoramaView.resumeRendering();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //panoramaGLSurfaceView.onPause();
        mVRPanoramaView.pauseRendering();

    }

    @Override
    protected void onDestroy() {
        mVRPanoramaView.shutdown();
        super.onDestroy();
    }
}