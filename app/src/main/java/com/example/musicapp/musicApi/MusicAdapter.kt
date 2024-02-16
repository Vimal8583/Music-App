package com.example.androidpractice.musicApi

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.MusicLayoutBinding

class MusicAdapter(
    var context: Context,
    var songList: MutableList<Song>,
    private var currentlyPlayingPosition: Int = -1
) : RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null

    class MyViewHolder(var binding: MusicLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MusicLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val song = songList[position]

        Glide.with(context).load(song.artist.picture).into(holder.binding.imageView4)
        holder.binding.textView3.text = song.title
        holder.binding.tvName.text = song.artist.name

        holder.binding.btnplay.setOnClickListener {
            handlePlayPause(position, song.music)
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    private fun handlePlayPause(position: Int, songUrl: String) {
        if (position == currentlyPlayingPosition) {

        } else {

            pauseCurrentSong()
            currentlyPlayingPosition = position
            initializeMediaPlayer(songUrl)
            startMediaPlayer()
        }
    }

    private fun togglePlayPause() {
        if (mediaPlayer == null) {

        } else {
            if (mediaPlayer!!.isPlaying) {
                pauseMediaPlayer()
            } else {
                startMediaPlayer()
            }
        }
    }

    private fun initializeMediaPlayer(songUrl: String) {
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        mediaPlayer?.setDataSource(songUrl)
        mediaPlayer?.prepareAsync()

        mediaPlayer?.setOnPreparedListener {
            // MediaPlayer is prepared
            startMediaPlayer()
        }
    }

    private fun startMediaPlayer() {
        mediaPlayer?.start()

    }

    private fun pauseMediaPlayer() {
        mediaPlayer?.pause()

    }

    private fun pauseCurrentSong() {
        if (currentlyPlayingPosition != -1) {
            notifyItemChanged(currentlyPlayingPosition)
            currentlyPlayingPosition = -1
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
