package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileNeighbourActivity extends AppCompatActivity {

    private final NeighbourApiService mApiService = DI.getNeighbourApiService();
    private Neighbour mNeighbour;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mNeighbour = intent.getParcelableExtra("neighbour");

        retrieveUserInfo();
        updateFavButton();
        setAvatar();
        
        Log.d("FAV LIST", Arrays.toString(mApiService.getFavorites().toArray()));
        Log.d("USER ID", Long.toString(mNeighbour.getId()));

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mApiService.getFavorites().contains(mNeighbour)){
                    mApiService.addToFavorite(mNeighbour);
                } else {
                    mApiService.deleteFavorite(mNeighbour);
                }
                Log.d("FAV LIST", mApiService.getFavorites().toString());
                updateFavButton();
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
        String nAddress = mNeighbour.getAddress();
        String replaceTarget = "\\b ; \\b";
        String finalAddress = nAddress.replaceAll(replaceTarget, " à ");

        String userName = mNeighbour.getName();
        String facebookUrl = "www.facebook.fr/";
        String finalUrl = facebookUrl + userName;

        mNameTextView.setText(userName);
        mNestedNameTextView.setText(userName);
        mNestedLocationTextView.setText(finalAddress);
        mNestedPhoneTextView.setText(mNeighbour.getPhoneNumber());
        mNestedWebTextView.setText(finalUrl);
        mAboutTextView.setText(mNeighbour.getAboutMe());
    }

    public void setAvatar(){
        String nAvatar = mNeighbour.getAvatarUrl();
        String baseChangeTarget = "\\b.cc/150\\b";
        String baseChange = nAvatar.replaceAll(baseChangeTarget, ".cc/300");
        Glide.with(mAvatarImageView.getContext())
                .load(baseChange)
                .error(Glide.with(mAvatarImageView).load(R.drawable.ic_account))
                .centerCrop()
                .into(mAvatarImageView);
    }

    private void updateFavButton(){
        if(mApiService.getFavorites().contains(mNeighbour)){
            mFavoriteButton.setImageResource(R.drawable.ic_star_white_24dp);
        } else {
            mFavoriteButton.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }
}