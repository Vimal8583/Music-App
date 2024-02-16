package com.example.androidpractice.musicApi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.databinding.ActivityMusicBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicActivity : AppCompatActivity() {

    private var songList = mutableListOf<Song>()
    private lateinit var mpAdapter: MusicAdapter
    private lateinit var binding: ActivityMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mpAdapter = MusicAdapter(this, songList)
        binding.recylerView.layoutManager = LinearLayoutManager(this)
        binding.recylerView.adapter = mpAdapter


        binding.btnsearch.setOnClickListener {
            val artistName = binding.tvArtist.text.toString().trim()
            if (artistName.isNotEmpty()) {
                fetchMusicList(artistName)
                hideKeyboard()

                binding.tvArtist.visibility = View.GONE
                binding.btnsearch.visibility = View.GONE


                val layoutParams = binding.recylerView.layoutParams

                if (layoutParams is ConstraintLayout.LayoutParams) {
                    // Initialize layout parameters if not already initialized
                    if (layoutParams.matchConstraintPercentHeight == 0.0f) {
                        layoutParams.matchConstraintPercentHeight = 1.0f
                        binding.recylerView.layoutParams = layoutParams
                    }
                }
            } else {
                Toast.makeText(this, "Please enter the artist name", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun fetchMusicList(artistName: String) {
        ApiClient.init().getSongList(artistName).enqueue(object : Callback<MusicResponse> {
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()?.song
                    res?.let {
                        updateRecyclerView(it)
                    }
                } else {

                    Toast.makeText(
                        this@MusicActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
                Toast.makeText(this@MusicActivity, "Network request failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun updateRecyclerView(songs: MutableList<Song>) {
        mpAdapter.songList.clear()
        mpAdapter.songList.addAll(songs)
        mpAdapter.notifyDataSetChanged()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.tvArtist.windowToken, 0)
    }

    override fun onDestroy() {
        mpAdapter.releaseMediaPlayer()
        super.onDestroy()
    }
}
