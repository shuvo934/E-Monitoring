package com.shuvo.ttit.bridgeculvert.projects;

import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuvo.ttit.bridgeculvert.R;
import com.shuvo.ttit.bridgeculvert.adapter.ProjectAdapter;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import static com.shuvo.ttit.bridgeculvert.mainmenu.HomePage.projectlists;


public class Projects extends AppCompatActivity implements ProjectAdapter.ClickedItem{

    RecyclerView itemView;
    ProjectAdapter projectAdapter;
    RecyclerView.LayoutManager layoutManager;

    TextView totalProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        itemView = findViewById(R.id.project_details_report_view);
        totalProjects = findViewById(R.id.total_projects_no_project);

        itemView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(itemView.getContext(),DividerItemDecoration.VERTICAL);
        itemView.addItemDecoration(dividerItemDecoration);
        //AppCompatActivity activity1 = (AppCompatActivity) Projects.this;



        projectAdapter = new ProjectAdapter(projectlists, Projects.this, Projects.this);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(projectAdapter);
        animationAdapter.setDuration(500);
        animationAdapter.setInterpolator(new AccelerateDecelerateInterpolator());
        animationAdapter.setFirstOnly(false);
        itemView.setAdapter(animationAdapter);

        String text = "Total " + projectlists.size() + " Projects";
        totalProjects.setText(text);

    }

    @Override
    public void onCategoryClicked(int CategoryPosition) {

    }
}