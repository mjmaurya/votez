package com.example.votez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Create_and_Vote extends AppCompatActivity {
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    ScrollView add_members,create_contest;
    LinearLayout bothEnterFace;
ImageView candidate_image;
EditText candidate_id,candidate_name,candidate_mobile,candidate_bio,contest_id,contest_name,contest_type,takeCid;
Button add_member,select_image,add_contest,newContest,add_new_member;
DatabaseReference mdatabase;
String image,id_contest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and__vote);
        //both EnterFAce
        bothEnterFace=findViewById(R.id.bothEnterFace);
        newContest=findViewById(R.id.button3);
        add_new_member=findViewById(R.id.button5);
        takeCid=findViewById(R.id.take_cId);
        //two layouts ScrollView
        add_members=findViewById(R.id.add_members);
        create_contest=findViewById(R.id.Create_contest);
        //add contest
        contest_id=findViewById(R.id.contest_id);
        contest_name=findViewById(R.id.contest_name);
        contest_type=findViewById(R.id.contest_type);
        add_contest=findViewById(R.id.button2);

        //add candidate
        candidate_image=findViewById(R.id.imageView6);
        candidate_id=findViewById(R.id.candidate_id);
        candidate_name=findViewById(R.id.candidate_name);
        candidate_mobile=findViewById(R.id.candidate_mobile);
        candidate_bio=findViewById(R.id.candidate_bio);
        add_member=findViewById(R.id.add_member);
        select_image=findViewById(R.id.button4);
        mdatabase= FirebaseDatabase.getInstance().getReference("Contests");
        newContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bothEnterFace.setVisibility(View.GONE);
                create_contest.setVisibility(View.VISIBLE);
                add_members.setVisibility(View.GONE);
            }
        });
        add_contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contestId=contest_id.getText().toString();
                String contestName=contest_name.getText().toString();
                String contestType=contest_type.getText().toString();
                if (TextUtils.isEmpty(contestId)||TextUtils.isEmpty(contestName)||TextUtils.isEmpty(contestType))
                {
                    Toast.makeText(Create_and_Vote.this,"Some Field are Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    id_contest=contestId;
                    AddContestCandidate contest = new AddContestCandidate(contestName,contestType);
                    mdatabase.child(contestId).setValue(contest);
                    Toast.makeText(Create_and_Vote.this, "Contest Create Successful Now add Candidate", Toast.LENGTH_SHORT).show();
                    create_contest.setVisibility(View.GONE);
                    add_members.setVisibility(View.VISIBLE);
                }
            }
        });
        add_new_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_contest=takeCid.getText().toString();
                bothEnterFace.setVisibility(View.GONE);
                create_contest.setVisibility(View.GONE);
                add_members.setVisibility(View.VISIBLE);
            }
        });
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cond_count="0";
               String candidateId=candidate_id.getText().toString();
               String candidateName=candidate_name.getText().toString();
               String candidateMobile=candidate_mobile.getText().toString();
               String candidateBio=candidate_bio.getText().toString();
               String candidateImage=image;
                if (TextUtils.isEmpty(candidateId)||TextUtils.isEmpty(candidateName)||TextUtils.isEmpty(candidateMobile)||TextUtils.isEmpty(candidateBio)||TextUtils.isEmpty(candidateImage))
                {
                    Toast.makeText(Create_and_Vote .this,"Some Fields are Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    AddContestCandidate candidate = new AddContestCandidate(candidateName,candidateMobile,candidateBio,candidateImage, cond_count);
                    mdatabase.child(id_contest).child("Candidates").child(candidateId).setValue(candidate);
                    Toast.makeText(Create_and_Vote.this,"Candidate Added Successfully",Toast.LENGTH_SHORT).show();
                }
                candidate_id.setText(null);
                candidate_name.setText(null);
                candidate_mobile.setText(null);
                candidate_bio.setText(null);
                candidate_image.setImageBitmap(null);
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteFormat = stream.toByteArray();
                String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
                image=encodedImage;
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                candidate_image.setImageBitmap(decodedByte);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
