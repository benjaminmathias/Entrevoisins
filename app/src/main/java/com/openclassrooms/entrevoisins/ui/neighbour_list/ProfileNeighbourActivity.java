package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.name_text_view)
    public TextView mNameTextView;
    @BindView(R.id.nested_name_text_view)
    public TextView mNestedNameTextView;
    @BindView(R.id.nested_address_text_view)
    public TextView mNestedLocationTextView;
    @BindView(R.id.nested_phone_text_view)
    public TextView mNestedPhoneTextView;
    @BindView(R.id.nested_web_text_view)
    public TextView mNestedWebTextView;
    @BindView(R.id.about_description_text_view)
    public TextView mAboutTextView;
    @BindView(R.id.back_button)
    public ImageButton mBackButton;
    @BindView(R.id.user_avatar_imageview)
    public ImageView mAvatarImageView;
    @BindView(R.id.favorite_button)
    public FloatingActionButton mFavoriteButton;

    private static final String EXTRA_KEY_NAME = "name";
    private static final String EXTRA_KEY_ADDRESS = "address";
    private static final String EXTRA_KEY_ABOUT = "about";
    private static final String EXTRA_KEY_PHONE = "phone";

    public static void route(Context context, String name, String address, String phone, String about){
        context.startActivity(getIntent(context, name, address, phone, about));
    }

    private static Intent getIntent(Context context, String name, String address, String phone, String about){
        Intent intent = new Intent(context, ProfileNeighbourActivity.class);
        Bundle extras = new Bundle();
        extras.putString(EXTRA_KEY_NAME, name);
        extras.putString(EXTRA_KEY_ADDRESS, address);
        extras.putString(EXTRA_KEY_PHONE, phone);
        extras.putString(EXTRA_KEY_ABOUT, about);
        intent.putExtras(extras);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);
        ButterKnife.bind(this);
        retrieveUserInfo();
        setAvatar();



        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFavoriteButton.setImageResource(R.drawable.ic_star_white_24dp);
                //mFavoriteButton.setImageResource(R.drawable.ic_star_border_white_24dp);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void retrieveUserInfo(){
        Intent intent = getIntent();
        String nName = intent.getStringExtra(EXTRA_KEY_NAME);
        String nAddress = intent.getStringExtra(EXTRA_KEY_ADDRESS);
        String nPhone = intent.getStringExtra(EXTRA_KEY_PHONE);
        String nAbout = intent.getStringExtra(EXTRA_KEY_ABOUT);
        Toast.makeText(this, nName, Toast.LENGTH_SHORT).show();
        mNameTextView.setText(nName);
        mNestedNameTextView.setText(nName);
        mNestedLocationTextView.setText(nAddress);
        mNestedPhoneTextView.setText(nPhone);
        mNestedWebTextView.setText("www.facebook.fr/" + nName.toLowerCase());
        mAboutTextView.setText(nAbout);
    }

    public void setAvatar(){
        Intent intent = getIntent();
        String nAvatar = intent.getStringExtra("avatar");
        Glide.with(mAvatarImageView.getContext())
                .load(nAvatar)
                .error(Glide.with(mAvatarImageView).load(R.drawable.ic_account))
                .fitCenter()
                .into(mAvatarImageView);
    }

}