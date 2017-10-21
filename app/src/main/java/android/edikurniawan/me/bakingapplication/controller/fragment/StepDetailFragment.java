package android.edikurniawan.me.bakingapplication.controller.fragment;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.databinding.FragmentDetailStepBinding;
import android.edikurniawan.me.bakingapplication.model.Step;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.infideap.stylishwidget.view.ATextView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * Created by Edi Kurniawan on 9/26/2017.
 */

public class StepDetailFragment extends BaseFragment {
    public static final String EXTRA1 = "EXTRA1";
    public static final String EXTRA2 = "EXTRA2";

    Step step;
    long currentPosition;

    FragmentDetailStepBinding binding;
    SimpleExoPlayer player;

    public static StepDetailFragment newInstance(Step category) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA1, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            step = getArguments().getParcelable(EXTRA1);
        }
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_detail_step,container,false); // LayoutInflater.from(context).inflate(R.layout.content_progressbar,view,false);
        currentPosition = 0;

        if(savedInstanceState != null){
            Timber.e("SAVEDINSTACESTATE");
            step = savedInstanceState.getParcelable(EXTRA1);
            currentPosition = savedInstanceState.getLong(EXTRA2);
        }

        if (step != null){
            initExo(binding.playerView,currentPosition);
            initDescription(binding.textViewDescription);
            initThumbnail();
        }
        return binding.getRoot();
    }

    private void initExo(SimpleExoPlayerView playerView,long position){
        if (step.getVideoURL() != null){
            if (!step.getVideoURL().isEmpty()){
                try{
                    // Measures bandwidth during playback. Can be null if not required.
                    DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    // Produces DataSource instances through which media data is loaded.
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                            Util.getUserAgent(getContext(), "yourApplicationName"), bandwidthMeter);
                    // Produces Extractor instances for parsing the media data.
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    // This is the MediaSource representing the media to be played.
                    Handler mainHandler = new Handler();
                    TrackSelection.Factory videoTrackSelectionFactory =
                            new AdaptiveTrackSelection.Factory(bandwidthMeter);
                    TrackSelector trackSelector =
                            new DefaultTrackSelector(videoTrackSelectionFactory);

                    player =
                            ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                    playerView.requestFocus();
                    playerView.setPlayer(player);

                    MediaSource videoSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                            dataSourceFactory, extractorsFactory, null, null);
                    Timber.
                            e("POSITION : "+position);
                    player.prepare(videoSource);
                }catch (Exception e) {
                    Timber.e("ERROR VIDEO : "+e.getMessage());
                    player = null;
                }

            }else{
                player = null;
            }


        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        currentPosition = player.getCurrentPosition();
        outState.putParcelable(EXTRA1,step);
        outState.putLong(EXTRA2,currentPosition);
        super.onSaveInstanceState(outState);
    }
    private void initDescription(ATextView textViewDesc){
        textViewDesc.setText(step.getDescription());
    }

    private void initThumbnail(){
        if (step.getThumbnailURL() != null){
            if (!step.getThumbnailURL().isEmpty()){
                Picasso.with(getContext())
                        .load(step.getThumbnailURL())
                        .placeholder(R.drawable.ic_image) //this is optional the image to display while the url image is downloading
                        .error(R.drawable.ic_image)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(binding.imageThumbnail);
            }else{
                step.setThumbnailURL(null);
                Picasso.with(getContext())
                        .load(step.getThumbnailURL())
                        .placeholder(R.drawable.ic_image) //this is optional the image to display while the url image is downloading
                        .error(R.drawable.ic_image)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(binding.imageThumbnail);
            }
        }else{
            Picasso.with(getContext())
                    .load(step.getThumbnailURL())
                    .placeholder(R.drawable.ic_image) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.ic_image)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(binding.imageThumbnail);
        }


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Timber.e("ONRESUME STEP DETAIL FRAGMENT");
        if (player != null) {
            player.seekTo(currentPosition);
            player.setPlayWhenReady(true);
        }
    }
}
