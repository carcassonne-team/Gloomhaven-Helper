package com.carcassonneteam.gloomhavenhelper.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.carcassonneteam.gloomhavenhelper.R
import com.carcassonneteam.gloomhavenhelper.adapters.PlayerAdapter
import com.carcassonneteam.gloomhavenhelper.models.Player
import com.carcassonneteam.gloomhavenhelper.viewmodels.PlayerActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PlayerActivity() : AppCompatActivity(), CreatePlayerDialogFragment.OnCreatePlayerListener,
    PlayerAdapter.OnPlayerClickListener {

    private val tag = "PlayerActivity"

    private lateinit var titleView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var playersAdapter: PlayerAdapter
    private var party = ""
    private var partyId = 0

    private val compositeDisposable = CompositeDisposable()
    lateinit var playerActivityViewModel: PlayerActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        titleView = findViewById(R.id.toolbar_title)
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.players_recycler)
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        intToolbar()
        playerActivityViewModel = ViewModelProvider(this).get(PlayerActivityViewModel::class.java)
        val extras: Bundle = intent.extras!!
        party = extras.getString("party")!!
        partyId = extras.getInt("id")
        Log.d(tag, "onStart: $partyId $party")
        titleView.text = party
        val disposable: Disposable =
            playerActivityViewModel.getAllPlayers(partyId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { players ->
                    Log.d(tag, "accept: getAllPlayers")
                    setDataToRecyclerView(players)
                    for (player in players) {
                        Log.d(tag, "accept: " + player.getName())
                    }
                }
        compositeDisposable.add(disposable)
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                playerActivityViewModel.delete(playersAdapter.getPlayerAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)

        playerActivityViewModel.getIsLoading().observe(this,
            { aBoolean ->
                Log.d(tag, "onChanged: $aBoolean")
                if (aBoolean != null) {
                    if (aBoolean) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
            })
        val fab: FloatingActionButton = findViewById(R.id.fab_players)
        fab.setOnClickListener { openAddPlayerDialog() }
    }

    private fun openAddPlayerDialog() {
        this.setTheme(R.style.Theme_GloomhavenHelper)
        val createPlayerDialog = CreatePlayerDialogFragment(this)
        createPlayerDialog.show(supportFragmentManager, "Create Player Dialog")
    }

     private fun setDataToRecyclerView(players: List<Player>) {
        playersAdapter = PlayerAdapter(players)
        playersAdapter.setItemClickListener(this)
        recyclerView.adapter = playersAdapter
    }

    private fun intToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            deleteAllPlayers()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllPlayers() {
        playerActivityViewModel.deleteAllPlayersByParty(partyId)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun saveNewPlayer(player: Player) {
        val currentPlayer: Player = player
        currentPlayer.setPartyId(partyId)
        playerActivityViewModel.insert(currentPlayer)
    }

    fun onPlayerClick(player: Player) {
        moveToCharactersActivity(player)
    }

    private fun moveToCharactersActivity(player: Player) {
        val intent = Intent(this@PlayerActivity, CharacterActivity::class.java)
        intent.putExtra("party", player.getName())
        intent.putExtra("id", player.getId())
        startActivity(intent)
    }

    override fun onPartyClick(player: Player) {
        moveToCharactersActivity(player)
    }
}