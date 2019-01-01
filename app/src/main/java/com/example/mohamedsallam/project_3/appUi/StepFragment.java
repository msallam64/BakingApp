package com.example.mohamedsallam.project_3.appUi;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamedsallam.project_3.R;
import com.example.mohamedsallam.project_3.models.*;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.List;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import static com.example.mohamedsallam.project_3.appUi.RecipeActivity.*;

public class StepFragment extends Fragment {
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private BandwidthMeter bandwidthMeter;
    private ArrayList<Step> msteps = new ArrayList<>();
    private int selectedposition;
    private Handler handler;
    ArrayList<Recipe> modelModelRecipe;
    String mrecipeName;
    long pos;
    Uri videoUri;
    boolean isPlayWhenReady;


    public StepFragment() {
    }

    private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> allModelModelSteps, int Index, String recipeName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView;
        handler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        listItemClickListener = (DetailActivity) getActivity();
        modelModelRecipe = new ArrayList<>();
        if (savedInstanceState != null) {
            pos = savedInstanceState.getLong("pos", C.TIME_UNSET);
            msteps = savedInstanceState.getParcelableArrayList(selected_steps);
            selectedposition = savedInstanceState.getInt(selected_index);
            mrecipeName = savedInstanceState.getString("Title");
        } else {
            msteps = getArguments().getParcelableArrayList(selected_steps);
            if (msteps != null) {
                msteps = getArguments().getParcelableArrayList(selected_steps);
                selectedposition = getArguments().getInt(selected_index);
                mrecipeName = getArguments().getString("Title");
            } else {
                modelModelRecipe = getArguments().getParcelableArrayList(selected_recipe);
                msteps = (ArrayList<Step>) modelModelRecipe.get(0).getSteps();
                selectedposition = 0;
            }
        }
        View rootView = inflater.inflate(R.layout.step_detail_fragment_body, container, false);
        textView = (TextView) rootView.findViewById(R.id.recipe_step_textview);
        textView.setText(msteps.get(selectedposition).getDescription());
        textView.setVisibility(View.VISIBLE);
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.media_playerView);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        String videoURL = msteps.get(selectedposition).getVideoURL();
        if (rootView.findViewWithTag("sw600dp-port-recipe_step_detail") != null) {
            mrecipeName = ((DetailActivity) getActivity()).mrecipeName;
            ((DetailActivity) getActivity()).getSupportActionBar().setTitle(mrecipeName);
        }
        String imageUrl = msteps.get(selectedposition).getThumbnailURL();
        if (imageUrl != "") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            ImageView thumbImage = (ImageView) rootView.findViewById(R.id.media_image);
            Picasso.with(getContext()).load(builtUri).into(thumbImage);
        }
        if (!videoURL.isEmpty()) {
            videoUri = Uri.parse(msteps.get(selectedposition).getVideoURL());
            initializePlayer(videoUri);
            if (rootView.findViewWithTag("sw600dp-land-recipe_step_detail") != null) {
                getActivity().findViewById(R.id.fragment_detail_container2).setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            } else if (isInLandscapeMode(getContext())) {
                textView.setVisibility(View.GONE);
            }
        } else {
            simpleExoPlayer = null;
            simpleExoPlayerView.setForeground(ContextCompat.
                    getDrawable(getContext(), R.drawable.ic_off_white_36dp));
            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        }
        Button mPrevStep = (Button) rootView.findViewById(R.id.previous);
        Button mNextstep = (Button) rootView.findViewById(R.id.nextStep);
        mPrevStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (msteps.get(selectedposition).getId() > 0) {
                    if (simpleExoPlayer != null) {
                        simpleExoPlayer.stop();
                    }
                    listItemClickListener.onListItemClick(msteps, msteps.get(selectedposition).getId() - 1, mrecipeName);
                } else {
                    Toast.makeText(getActivity(), "This is First step ", Toast.LENGTH_LONG).show();

                }
            }
        });

        mNextstep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int lastIndex = msteps.size() - 1;
                if (msteps.get(selectedposition).getId() < msteps.get(lastIndex).getId()) {
                    if (simpleExoPlayer != null) {
                        simpleExoPlayer.stop();
                    }
                    listItemClickListener.onListItemClick(msteps, msteps.get(selectedposition).getId() + 1, mrecipeName);
                } else {
                    Toast.makeText(getContext(), "This is  Last step ", Toast.LENGTH_LONG).show();

                }
            }
        });

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(handler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (pos != C.TIME_UNSET) {
                simpleExoPlayer.seekTo(pos);
            }
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putBoolean("playstate", isPlayWhenReady);
        currentState.putLong("pos", pos);
        currentState.putParcelableArrayList(selected_steps, msteps);
        currentState.putInt(selected_index, selectedposition);
        currentState.putString("Title", mrecipeName);
    }

    public boolean isInLandscapeMode(Context context) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            simpleExoPlayer.seekTo(pos);
            simpleExoPlayer.setPlayWhenReady(isPlayWhenReady);
        }catch (NullPointerException e){
            simpleExoPlayerView.setForeground(ContextCompat.
                    getDrawable(getContext(), R.drawable.ic_off_white_36dp));
            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoUri != null){
            initializePlayer(videoUri);}



    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            pos = simpleExoPlayer.getCurrentPosition();
            isPlayWhenReady=simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

}
