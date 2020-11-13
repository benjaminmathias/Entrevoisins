package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements MyNeighbourRecyclerViewAdapter.onItemClickListener {

    private NeighbourApiService mApiService;
    private List<Neighbour> mFavorites;
    private MyNeighbourRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        mFavorites = mApiService.getFavorites();
        mAdapter = new MyNeighbourRecyclerViewAdapter(this.mFavorites, this);
        mRecyclerView.setAdapter(this.mAdapter);
    }


    @Subscribe
    public void onDeleteFavorite(DeleteFavoriteEvent event)
    {
        mApiService.deleteFavorite(event.neighbour);
        initList();
    }

    @Override
    public void onItemClick(int position) {
        Context context = getActivity();
        Neighbour neighbour = mFavorites.get(position);
        Intent intent = new Intent(context, ProfileNeighbourActivity.class);
        intent.putExtra("neighbour", neighbour);
        context.startActivity(intent);
    }
}
