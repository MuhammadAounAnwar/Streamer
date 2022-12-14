package com.ono.streamer.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.Util
import com.ono.streamer.R
import com.ono.streamer.databinding.ActivityVideoPlayerBinding
import com.ono.streamer.ui.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class VideoPlayerActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var injector: VideoPlayerVMInjector
    private lateinit var simpleExoPlayer: ExoPlayer

    private val binding: ActivityVideoPlayerBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_video_player)
    }

    private val viewModel by lazy {
        val factory = ViewModelFactory(injector, this, null)
        ViewModelProvider(this, factory)[VideoPlayerViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (application is HasAndroidInjector) {
            AndroidInjection.inject(this)
        }
        super.onCreate(savedInstanceState)
        binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

//        if (intent != null && intent.hasExtra(STREAM_URL)) {
//            intent.extras?.let {
//                viewModel.initViewModel(it.getString(VIDEO_URL)!!)
//            }
//        }
    }

    private fun initializePlayer() {
        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(STREAM_URL))
        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)
        simpleExoPlayer = ExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build()
        simpleExoPlayer.addMediaSource(mediaSource)
        simpleExoPlayer.playWhenReady = true
        binding.playerView.player = simpleExoPlayer
        binding.playerView.requestFocus()
    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()
    }

    companion object {
        const val STREAM_URL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"

        //        const val STREAM_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        const val VIDEO_URL = "VIDEO_URL"

        fun initiateActivity(context: Context, videoUrl: String) {
            val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                putExtra(VIDEO_URL, videoUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}