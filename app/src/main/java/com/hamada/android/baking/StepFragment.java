package com.hamada.android.baking;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hamada.android.baking.Adapter.StepPagerAdapter;
import com.hamada.android.baking.Model.Step;
import com.squareup.picasso.Picasso;

public class StepFragment extends Fragment  {
    //implements ExoPlayer.EventListener
    public static final String TAG=StepFragment.class.getSimpleName();
    private Step mStep;
    private TextView mTextViewDescription;
    private String mDescrption,mUrl,mThumbnailURL,mVideoUrl;
    private boolean pane;
    private MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder playbackBuilder;
    private Uri videoUri;
    private ImageView placeHolderImage;
    private SimpleExoPlayer simpleExoPlayer;
    private long positionPlayer=-1;
    private boolean playWhenReady=true;
    private SimpleExoPlayerView simpleExoPlayerView;
    private static final String POSITION_KEY="position_key";
    private static final String PLAY_WHEN_READY_KEY="play_ready";

    public StepFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=this.getArguments();
        if (bundle!=null) {
            mStep = bundle.getParcelable(StepPagerAdapter.EXTRASTEP);
            mDescrption=mStep.getDescription();
            mUrl=mStep.getVideoURL();
            mThumbnailURL=mStep.getThumbnailURL();

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(POSITION_KEY, simpleExoPlayer.getCurrentPosition());
        outState.putBoolean(PLAY_WHEN_READY_KEY,simpleExoPlayer.getPlayWhenReady());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        mTextViewDescription = view.findViewById(R.id.descriptionStep);
        placeHolderImage = view.findViewById(R.id.imageUrl);
        simpleExoPlayerView = view.findViewById(R.id.playerView);
        if (savedInstanceState !=null&&savedInstanceState.containsKey(POSITION_KEY)){
            positionPlayer=savedInstanceState.getLong(POSITION_KEY);
            playWhenReady=savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
            simpleExoPlayer.seekTo(positionPlayer);
        }
        if (mUrl != null) {
            if (mUrl.equals("")) {
                Log.d(TAG, "EMPTY URL");
                simpleExoPlayerView.setVisibility(View.GONE);
                placeHolderImage.setVisibility(View.VISIBLE);
                mTextViewDescription.setText(mDescrption);
                if (!mThumbnailURL.equals("")) {
                    //Load thumbnail if present
                    Picasso.get().load(mThumbnailURL).into(placeHolderImage);
                }else {
                    placeHolderImage.setVisibility(View.GONE);
                }

            } else {

                placeHolderImage.setVisibility(View.GONE);
                initializeMedia();
                Log.d(TAG, "URL " + mUrl);
                initializePlayer(Uri.parse(mUrl));
                videoUri = Uri.parse(mUrl);
                mTextViewDescription.setText(mDescrption);
            }

        }

            return view;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getContext(), "landscape", Toast.LENGTH_SHORT).show();
            hideUI();
//            simpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//            simpleExoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            mTextViewDescription.setVisibility(View.GONE);
            initializeMedia();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(getContext(), "portrait", Toast.LENGTH_SHORT).show();
            mTextViewDescription.setText(mDescrption);
            initializeMedia();
        }
    }

    private void initializeMedia() {
        mediaSessionCompat = new MediaSessionCompat(getActivity(), TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        playbackBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSessionCompat.setPlaybackState(playbackBuilder.build());
       // mediaSessionCompat.setCallback(new SessionCallBacks());
        mediaSessionCompat.setActive(true);
    }
    private void hideUI() {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            //Use Google's "LeanBack" mode to get fullscreen in landscape
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance
                    (getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            //simpleExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getActivity(),
                    "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }
    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mediaSessionCompat != null) {
            mediaSessionCompat.setActive(false);
        }
    }

    @Override
    public void onPause() {
        //releasing in Pause and saving current position for resuming
        super.onPause();

        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (simpleExoPlayer != null) {
            //resuming properly
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(positionPlayer);
        } else {
            //Correctly initialize and play properly fromm seekTo function
            initializeMedia();
            initializePlayer(videoUri);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(positionPlayer);
        }
    }

}
