package com.example.studentfirebase;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.createChooser;


public class StudentForm extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    Spinner loc, dep;
    EditText name, expectation;
    Button send, datebutton, choosebutton, list;
    RadioButton genderoption, yearoption;
    RadioGroup gendergroup, yeargroup;
    ImageView imageView;
    CheckBox htrue, hfalse;
    TextView birthdate;
    DatabaseReference reff;
    Student student;
    Context context;
    private FragmentActivity myContext;
    String strgendre, strstudyyear;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_student_form_, container, false);

        student = new Student();

        reff = FirebaseDatabase.getInstance().getReference().child("student");

        context = getActivity();

        name = v.findViewById(R.id.name);
        expectation = v.findViewById(R.id.expectations);
        send = v.findViewById(R.id.button2);
        birthdate = v.findViewById(R.id.birthDate);
        datebutton = v.findViewById(R.id.pickBirthDate);
        gendergroup = v.findViewById(R.id.gendergroup);
        yeargroup = v.findViewById(R.id.studyyear);
        htrue = v.findViewById(R.id.dance);
        hfalse = v.findViewById(R.id.box);
        choosebutton = v.findViewById(R.id.btnChoose);
        imageView = v.findViewById(R.id.imgView);
        list = v.findViewById(R.id.button3);

        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                genderoption = gendergroup.findViewById(i);
                switch (i) {
                    case R.id.male:
                        strgendre = genderoption.getText().toString();
                        break;
                    case R.id.female:
                        strgendre = genderoption.getText().toString();
                        break;
                    default:
                }
            }
        });


        yeargroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                yearoption = yeargroup.findViewById(i);
                switch (i) {
                    case R.id.I:
                        strstudyyear = yearoption.getText().toString();
                        break;
                    case R.id.II:
                        strstudyyear = yearoption.getText().toString();
                        break;
                    case R.id.III:
                        strstudyyear = yearoption.getText().toString();
                        break;
                    case R.id.IV:
                        strstudyyear = yearoption.getText().toString();
                        break;
                    default:
                }
            }
        });


        datebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                FragmentManager fragmentManager = myContext.getSupportFragmentManager();
                //FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                //newFragment.show(fm,"datepicker");
                newFragment.show(fragmentManager, "datepicker");

            }
        });

        loc = v.findViewById(R.id.location);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locspinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loc.setAdapter(adapter);
        loc.setOnItemSelectedListener(this);

        dep = v.findViewById(R.id.department);
        ArrayAdapter<CharSequence> adapterdep = ArrayAdapter.createFromResource(getActivity(), R.array.departmentspinner, android.R.layout.simple_spinner_item);
        adapterdep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dep.setAdapter(adapterdep);
        dep.setOnItemSelectedListener(this);

        choosebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new StudentListFragment());
                fr.commit();

            }
        });
        return v;
    }

    public void addStudent() {


        String stdname = name.getText().toString().trim();
        String stdloc = loc.getSelectedItem().toString();
        //String stdbirthdate= birthdate.getText().toString().trim();
        String stddepartment = dep.getSelectedItem().toString();
        String stdexceptations = expectation.getText().toString().trim();
        student.setStudentName(stdname);
        //student.setDate(stdbirthdate);
        student.setStudentloc(stdloc);
        student.setGendre(strgendre);
        student.setStudyyear(strstudyyear);
        student.setDepartment(stddepartment);
        student.setExpectations(stdexceptations);

        if (htrue.isChecked()) {
            student.setHobby("True");
        } else if (hfalse.isChecked()) {
            student.setHobby("False");
        }

        if (filePath != null) {
            String img = filePath.toString();
            student.setmImageUri(img);
        }

        reff.push().setValue(student);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        birthdate.setText(currentDateString);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            imageView.setImageURI((filePath));
        }
    }
}


