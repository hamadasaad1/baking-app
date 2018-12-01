package com.hamada.android.baking;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.hamada.android.baking.Adapter.StepPagerAdapter;
import com.hamada.android.baking.Model.Step;
import com.squareup.picasso.Picasso;

public class StepActivity extends AppCompatActivity  {

    public static final String TAG=StepActivity.class.getSimpleName();
    private final DefaultBandwidthMeter BANDWITHMATER=new DefaultBandwidthMeter();
    private Step mBaking;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private long positionPlayer;
    private boolean playWhenReady=true;
    private int currentWindos;
    private String mImageUrl,mShortDescription,mDescription,mUrl;
    private TextView mTextViewDescription,mTextViewShortDescription;
    private ImageView mImageView;
    private Uri videoUri;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private CommponentListener commponentListener;
    private boolean isTwoPane;
//    private NotificationManager notificationManager;
//    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        commponentListener=new CommponentListener();
//        mTextViewDescription=findViewById(R.id.descriptionStep);
//        mTextViewShortDescription=findViewById(R.id.short_descriptionStep);
//        mImageView=findViewById(R.id.imageUrl);
//        Intent intent=getIntent();
//         mPlayerView=findViewById(R.id.playerView);
//        if (intent.hasExtra(StepPagerAdapter.EXTRASTEP)){
//            mBaking = getIntent().getParcelableExtra(StepPagerAdapter.EXTRASTEP);
//            mUrl=mBaking.getVideoURL();
//            mDescription=mBaking.getDescription();
//            mShortDescription=mBaking.getShortDescription();
//            mImageUrl=mBaking.getThumbnailURL();
//            mTextViewShortDescription.setText(mShortDescription);
//            mTextViewDescription.setText(mDescription);
//            videoUri = Uri.parse(mUrl);
//
//        }else {
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
//        }
////        videoView=findViewById(R.id.videoView);
//
////        initializeMediaSession();
//        if (mUrl.isEmpty()){
//            setUpView();
//            mPlayerView.setVisibility(View.GONE);
//        }else {
//            initializePlayer();
//            videoUri = Uri.parse(mUrl);
//
//        }

//        if (findViewById(R.id.landScapeView )!=null){
//
//            isTwoPane=true;
//            Bundle bundle=new Bundle();
//            bundle.putParcelable(StepPagerAdapter.EXTRASTEP,getIntent().getParcelableExtra(StepPagerAdapter.EXTRASTEP));
//
//            stepFragment fragment=new stepFragment();
//            fragment.setArguments(bundle);
//            FragmentManager fragmentManager=getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
//                    .commit();
//
//        }else {
//            isTwoPane=false;
//        }



            Bundle bundle=new Bundle();
            bundle.putParcelable(StepPagerAdapter.EXTRASTEP,getIntent().getParcelableExtra(StepPagerAdapter.EXTRASTEP));

            stepFragment fragment=new stepFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                    .commit();

    }

    private void setUpView(){
        if (!mImageUrl.equals("")){
            mImageView.setVisibility(View.VISIBLE);
            Picasso.get().load(mImageUrl).into(mImageView);
        }else {
            mImageView.setVisibility(View.GONE);
        }
    }
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
//        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }
    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelection.Factory trackSelector=new AdaptiveTrackSelection.Factory(BANDWITHMATER);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance
                    (new DefaultRenderersFactory(getApplicationContext()),new DefaultTrackSelector(),
                    new DefaultLoadControl());
//            videoView.setVideoURI(mediaUri);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(commponentListener);
            mExoPlayer.setVideoListener( commponentListener);
            mExoPlayer.setAudioDebugListener(commponentListener);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindos,positionPlayer);
            if (mUrl.isEmpty()){
                Toast.makeText(this, "no Video"
                        , Toast.LENGTH_SHORT).show();
                return;

            }else {


                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
                MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                        this, userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
    }
    private void releasePlayer() {
        if (mExoPlayer != null) {
            positionPlayer=mExoPlayer.getCurrentPosition();
            currentWindos=mExoPlayer.getCurrentWindowIndex();
            playWhenReady=mExoPlayer.getPlayWhenReady();
            mExoPlayer.removeListener(commponentListener);
            mExoPlayer.setVideoListener(null);
            mExoPlayer.setAudioDebugListener(null);
            mExoPlayer.setVideoDebugListener(null);
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
    private void hideSystemUi(){
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
    private MediaSource buildMedia(Uri uri){
        return new ExtractorMediaSource(uri,new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(),null,null);
    }
    private class CommponentListener implements ExoPlayer.EventListener,VideoRendererEventListener,
            AudioRendererEventListener, SimpleExoPlayer.VideoListener {

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            String stata;
            switch (playbackState){
                case ExoPlayer.STATE_IDLE:
                    stata="ExoPlayer.STATE_IDLE  -";
                    break;
                    case ExoPlayer.STATE_READY:
                        stata="ExoPlayer.STATE_READY -";
                        break;
                        case ExoPlayer.STATE_BUFFERING:
                            stata="ExoPlayer.STATE_BUFFERING-";
                            break;
                            case ExoPlayer.STATE_ENDED:
                                stata="ExoPlayer.STATE_ENDED";
                                break;
                                default:
                                    stata="Unknown_State";
                                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }

        @Override
        public void onAudioEnabled(DecoderCounters counters) {

        }

        @Override
        public void onAudioSessionId(int audioSessionId) {

        }

        @Override
        public void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

        }

        @Override
        public void onAudioInputFormatChanged(Format format) {

        }

        @Override
        public void onAudioTrackUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {

        }

        @Override
        public void onAudioDisabled(DecoderCounters counters) {

        }

        @Override
        public void onVideoEnabled(DecoderCounters counters) {

        }

        @Override
        public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

        }

        @Override
        public void onVideoInputFormatChanged(Format format) {

        }

        @Override
        public void onDroppedFrames(int count, long elapsedMs) {

        }

        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

        }

        @Override
        public void onRenderedFirstFrame() {

        }

        @Override
        public void onRenderedFirstFrame(Surface surface) {

        }

        @Override
        public void onVideoDisabled(DecoderCounters counters) {

        }
    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        releasePlayer();
//
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (Util.SDK_INT <=23) {
//
//            releasePlayer();
//        }
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (Util.SDK_INT>23){
//            releasePlayer();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        hideSystemUi();
//        super.onResume();
//        if (Util.SDK_INT<=23 ||mExoPlayer==null) {
//
//            initializePlayer();
//        }
//
//    }
}
