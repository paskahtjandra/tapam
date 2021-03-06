package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private LinearLayout layAddContact;
    private EditText etName, etNumber, etInstagram, etGroup;
    private Button btnClear, btnSubmit;
    private ArrayList<ContactModel> contactList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    private FirebaseUser user;
    private String userID;
    private DatabaseReference reference;
    private ImageView profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        final TextView namausers = (TextView) findViewById(R.id.namauser);

        reference.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot dataSnapshot = task.getResult();
                    String name = String.valueOf(dataSnapshot.child("name").getValue());
                    namausers.setText(name);
                }
            }
        });

        profil = (ImageView) findViewById(R.id.profil);
        profil.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycle_contact);
        recyclerView.setHasFixedSize(true);
        layAddContact = findViewById(R.id.layout_add);
        etName = findViewById(R.id.et_name);
        etNumber = findViewById(R.id.et_number);
        etInstagram = findViewById(R.id.et_instagram);
        etGroup = findViewById(R.id.et_group);
        btnClear = findViewById(R.id.btn_clear);
        btnSubmit = findViewById(R.id.btn_submit);
        recyclerView.setVisibility(View.VISIBLE);
        layAddContact.setVisibility(View.GONE);


        btnClear.setOnClickListener(v -> {
            clearData();
        });
        btnSubmit.setOnClickListener(v -> {
            if (etName.getText().toString().equals("")
                    ||
                    etNumber.getText().toString().equals("") ||
                    etInstagram.getText().toString().equals("") ||
                    etGroup.getText().toString().equals("") ){
                Toast.makeText(this, "Please fill inthe entire form", Toast.LENGTH_SHORT).show();
            } else {
                contactList.add(new
                        ContactModel(etName.getText().toString(),
                        etNumber.getText().toString(),
                        etGroup.getText().toString(),
                        etInstagram.getText().toString()));
                contactAdapter = new
                        ContactAdapter(this, contactList);
                recyclerView.setAdapter(contactAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                layAddContact.setVisibility(View.GONE);
            }
        });
        contactList.add(new ContactModel("RS Hermina", "+62878555504", "Astra, Sinovac", "rshermina"));
        contactList.add(new ContactModel("Puskesmas Dau", "+628785555041", "Pfizer", "puskesmas_dau"));
        contactList.add(new ContactModel("RS UB", "+628785555042", "Sinovac, Pfizer", "rsub"));
        contactList.add(new ContactModel("RS Lafalet", "+628785555043", "Astra, Pfizer, Sinovac", "rslafalet"));
        contactList.add(new ContactModel("Puskesmas Suhat", "+628785555044", "Sinovac", "puskesmas_suhat"));
        contactAdapter = new ContactAdapter(this, contactList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        contactAdapter.setOnItemClickListener((position, v) ->
        {

            contactList.remove(position);
            contactAdapter = new ContactAdapter(this,
                    contactList);
            recyclerView.setAdapter(contactAdapter);
        });
        recyclerView.setAdapter(contactAdapter);



    }
    public void clearData(){
        etName.setText("");
        etNumber.setText("");
        etInstagram.setText("");
        etGroup.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profil:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }
}