package com.shuzhongchen.ecresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shuzhongchen.ecresume.model.BasicInfo;
import com.shuzhongchen.ecresume.model.Custom;
import com.shuzhongchen.ecresume.model.CustomTitle;
import com.shuzhongchen.ecresume.model.Education;
import com.shuzhongchen.ecresume.model.Experience;
import com.shuzhongchen.ecresume.model.Project;
import com.shuzhongchen.ecresume.util.DateUtils;
import com.shuzhongchen.ecresume.util.ImageUtils;
import com.shuzhongchen.ecresume.util.ModelUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_EDIT_EDUCATION = 100;
    private static final int REQ_CODE_EDIT_EXPERIENCE = 101;
    private static final int REQ_CODE_EDIT_PROJECT = 102;
    private static final int REQ_CODE_EDIT_BASIC_INFO = 103;
    private static final int REQ_CODE_EDIT_CUSTOM_TITLE = 104;
    private static final int REQ_CODE_EDIT_CUSTOM = 105;

    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";
    private static final String MODEL_BASIC_INFO = "basic_info";
    private static final String MODEL_CUSTOM_TITLE = "custom_title";
    private static final String MODEL_CUSTOMS = "customs";

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;
    private CustomTitle customTitle;
    private List<Custom> customs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        setupUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_EDIT_BASIC_INFO:
                    BasicInfo basicInfo = data.getParcelableExtra(BasicInfoEditActivity.KEY_BASIC_INFO);
                    updateBasicInfo(basicInfo);
                    break;
                case REQ_CODE_EDIT_EDUCATION:
                    String educationId = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                    if (educationId != null) {
                        deleteEducation(educationId);
                    } else {
                        Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                        updateEducation(education);
                    }
                    break;
                case REQ_CODE_EDIT_EXPERIENCE:
                    String experienceId = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
                    if (experienceId != null) {
                        deleteExperience(experienceId);
                    } else {
                        Experience experience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                        updateExperience(experience);
                    }
                    break;
                case REQ_CODE_EDIT_PROJECT:
                    String projectId = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                    if (projectId != null) {
                        deleteProject(projectId);
                    } else {
                        Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                        updateProject(project);
                    }
                    break;
                case REQ_CODE_EDIT_CUSTOM_TITLE:
                    CustomTitle customTitle = data.getParcelableExtra(CustomTitleEditActivity.KEY_CUSTOM_TITLE);
                    updateCustomTitle(customTitle);
                    break;
                case REQ_CODE_EDIT_CUSTOM:
                    String customId = data.getStringExtra(CustomEditActivity.KEY_CUSTOM_ID);
                    if (customId != null) {
                        deleteCustom(customId);
                    } else {
                        Custom custom = data.getParcelableExtra(CustomEditActivity.KEY_CUSTOM);
                        updateCustom(custom);
                    }
                    break;
            }
        }
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        ImageButton addEducationBtn = (ImageButton) findViewById(R.id.add_education_btn);
        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });

        ImageButton addExperienceBtn = (ImageButton) findViewById(R.id.add_experience_btn);
        addExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
            }
        });

        ImageButton addProjectBtn = (ImageButton) findViewById(R.id.add_project_btn);
        addProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });

        ImageButton addCustomBtn = (ImageButton) findViewById(R.id.add_custom_btn);
        addCustomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_CUSTOM);
            }
        });


        setupBasicInfo();
        setupEducationsUI();
        setupExperiencesUI();
        setupProjectsUI();
        setupCustomTitle();
        setupCustomsUI();
    }

    private void setupBasicInfo() {
        ((TextView) findViewById(R.id.name)).setText(TextUtils.isEmpty(basicInfo.name)
                ? "Your name"
                : basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(TextUtils.isEmpty(basicInfo.email)
                ? "Your email"
                : basicInfo.email);

        ImageView userPicture = (ImageView) findViewById(R.id.user_picture);
        if (basicInfo.imageUri != null) {
            ImageUtils.loadImage(this, basicInfo.imageUri, userPicture);
        } else {
            userPicture.setImageResource(R.drawable.user_ghost);
        }

        findViewById(R.id.edit_basic_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_EDIT_BASIC_INFO);
            }
        });
    }


    private void setupEducationsUI() {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.education_list);
        educationsLayout.removeAllViews();
        for (Education education : educations) {
            educationsLayout.addView(getEducationView(education));
        }
    }

    private View getEducationView(final Education education) {
        View educationView = getLayoutInflater().inflate(R.layout.education_item, null);

        String dateString = education.startDate
                + " ~ " + education.endDate;
        ((TextView) educationView.findViewById(R.id.education_school))
                .setText(education.school + " " + education.major + " (" + dateString + ")");
        ((TextView) educationView.findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));

        ImageButton editEducationBtn = (ImageButton) educationView.findViewById(R.id.edit_education_btn);
        editEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });
        return educationView;
    }

    private void setupExperiencesUI() {
        LinearLayout experiencesLayout = (LinearLayout) findViewById(R.id.experience_list);
        experiencesLayout.removeAllViews();
        for (Experience experience : experiences) {
            experiencesLayout.addView(getExperienceView(experience));
        }
    }

    private View getExperienceView(final Experience experience) {
        View experienceView = getLayoutInflater().inflate(R.layout.experience_item, null);

        String dateString = experience.startDate
                + " ~ " + experience.endDate;
        ((TextView) experienceView.findViewById(R.id.experience_company))
                .setText(experience.company + " " + experience.title + " (" + dateString + ")");
        ((TextView) experienceView.findViewById(R.id.experience_details))
                .setText(formatItems(experience.details));

        ImageButton editExperienceBtn = (ImageButton) experienceView.findViewById(R.id.edit_experience_btn);
        editExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, experience);
                startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
            }
        });
        return experienceView;
    }

    private void setupProjectsUI() {
        LinearLayout projectListLayout = (LinearLayout) findViewById(R.id.project_list);
        projectListLayout.removeAllViews();
        for (Project project : projects) {
            projectListLayout.addView(getProjectView(project));
        }
    }

    private View getProjectView(final Project project) {
        View projectView = getLayoutInflater().inflate(R.layout.project_item, null);

        String dateString = project.startDate
                + " ~ " + project.endDate;
        ((TextView) projectView.findViewById(R.id.project_name))
                .setText(project.name + " (" + dateString + ")");
        ((TextView) projectView.findViewById(R.id.project_details))
                .setText(formatItems(project.details));
        projectView.findViewById(R.id.edit_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });
        return projectView;
    }

    private void setupCustomTitle() {
        ((TextView) findViewById(R.id.customTitle)).setText(TextUtils.isEmpty(customTitle.title)
                ? "Set your own section"
                : customTitle.title);

        findViewById(R.id.edit_custom_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomTitleEditActivity.class);
                intent.putExtra(CustomTitleEditActivity.KEY_CUSTOM_TITLE, customTitle);
                startActivityForResult(intent, REQ_CODE_EDIT_CUSTOM_TITLE);
            }
        });
    }

    private void setupCustomsUI() {
        LinearLayout customListLayout = (LinearLayout) findViewById(R.id.custom_list);
        customListLayout.removeAllViews();
        for (Custom custom : customs) {
            customListLayout.addView(getCustomView(custom));
        }
    }

    private View getCustomView(final Custom custom) {
        View customView = getLayoutInflater().inflate(R.layout.custom_item, null);

        String dateString = custom.startDate
                + " ~ " + custom.endDate;
        ((TextView) customView.findViewById(R.id.custom_item_title))
                .setText(custom.title + " (" + dateString + ")");
        ((TextView) customView.findViewById(R.id.custom_details))
                .setText(formatItems(custom.details));

        customView.findViewById(R.id.edit_custom_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomEditActivity.class);
                intent.putExtra(CustomEditActivity.KEY_CUSTOM, custom);
                startActivityForResult(intent, REQ_CODE_EDIT_CUSTOM);
            }
        });
        return customView;
    }

    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.read(this,
                MODEL_BASIC_INFO,
                new TypeToken<BasicInfo>(){});
        basicInfo = savedBasicInfo == null ? new BasicInfo() : savedBasicInfo;

        List<Education> savedEducation = ModelUtils.read(this,
                MODEL_EDUCATIONS,
                new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;

        List<Experience> savedExperience = ModelUtils.read(this,
                MODEL_EXPERIENCES,
                new TypeToken<List<Experience>>(){});
        experiences = savedExperience == null ? new ArrayList<Experience>() : savedExperience;

        List<Project> savedProjects = ModelUtils.read(this,
                MODEL_PROJECTS,
                new TypeToken<List<Project>>(){});
        projects = savedProjects == null ? new ArrayList<Project>() : savedProjects;

        CustomTitle savedCustomTitle = ModelUtils.read(this,
                MODEL_CUSTOM_TITLE,
                new TypeToken<CustomTitle>(){});
        customTitle = savedCustomTitle == null ? new CustomTitle() : savedCustomTitle;

        List<Custom> savedCustoms = ModelUtils.read(this,
                MODEL_CUSTOMS,
                new TypeToken<List<Custom>>(){});
        customs = savedCustoms == null ? new ArrayList<Custom>() : savedCustoms;
    }

    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private void updateBasicInfo(BasicInfo basicInfo) {
        ModelUtils.save(this, MODEL_BASIC_INFO, basicInfo);

        this.basicInfo = basicInfo;
        setupBasicInfo();
    }

    private void updateEducation(Education education) {
        boolean found = false;
        for (int i = 0; i < educations.size(); ++i) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, education.id)) {
                found = true;
                educations.set(i, education);
                break;
            }
        }

        if (!found) {
            educations.add(education);
        }

        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducationsUI();
    }


    private void updateExperience(Experience experience) {
        boolean found = false;
        for (int i = 0; i < experiences.size(); ++i) {
            Experience e = experiences.get(i);
            if (e.id.equals(experience.id)) {
                found = true;
                experiences.set(i, experience);
                break;
            }
        }

        if (!found) {
            experiences.add(experience);
        }

        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiencesUI();
    }

    private void updateProject(Project project) {
        boolean found = false;
        for (int i = 0; i < projects.size(); ++i) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, project.id)) {
                found = true;
                projects.set(i, project);
                break;
            }
        }

        if (!found) {
            projects.add(project);
        }

        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjectsUI();
    }

    private void updateCustomTitle(CustomTitle customTitle) {
        ModelUtils.save(this, MODEL_CUSTOM_TITLE, customTitle);

        this.customTitle = customTitle;
        setupCustomTitle();
    }

    private void updateCustom(Custom custom) {
        boolean found = false;
        for (int i = 0; i < customs.size(); ++i) {
            Custom c = customs.get(i);
            if (TextUtils.equals(c.id, custom.id)) {
                found = true;
                customs.set(i, custom);
                break;
            }
        }

        if (!found) {
            customs.add(custom);
        }

        ModelUtils.save(this, MODEL_CUSTOMS, customs);
        setupCustomsUI();
    }

    private void deleteEducation(@NonNull String educationId) {
        for (int i = 0; i < educations.size(); ++i) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, educationId)) {
                educations.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducationsUI();
    }

    private void deleteExperience(@NonNull String experienceId) {
        for (int i = 0; i < experiences.size(); ++i) {
            Experience e = experiences.get(i);
            if (TextUtils.equals(e.id, experienceId)) {
                experiences.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiencesUI();
    }

    private void deleteProject(@NonNull String projectId) {
        for (int i = 0; i < projects.size(); ++i) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, projectId)) {
                projects.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjectsUI();
    }

    private void deleteCustom(@NonNull String customId) {
            for (int i = 0; i < customs.size(); ++i) {
            Custom c = customs.get(i);
            if (TextUtils.equals(c.id, customId)) {
                customs.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_CUSTOMS, customs);
        setupCustomsUI();
    }
}
