package com.carcassonneteam.gloomhavenhelper.views

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
import com.carcassonneteam.gloomhavenhelper.adapters.CharacterAdapter
import com.carcassonneteam.gloomhavenhelper.viewmodels.CharacterViewModel
import com.carcassonneteam.gloomhavenhelper.models.Character
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CharacterActivity : AppCompatActivity(),
    CreateCharacterDialogFragment.CreateCharacterListener {
    private val tag = "CharacterActivity"

    private lateinit var titleView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter
    private var party: String? = ""
    private var playerId = 0

    private val compositeDisposable = CompositeDisposable()
    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        titleView = findViewById(R.id.toolbar_title)
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.players_recycler)
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        intToolbar()
        characterViewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        val extras = intent.extras
        if (extras != null) {
            party = extras.getString("party")
            playerId = extras.getInt("uid")
            Log.d(tag, "onStart: $playerId$party")
            titleView.setText(party)
        }
        val disposable: Disposable =
            characterViewModel.getAllCharacters(playerId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { characters ->
                    Log.d(tag, "accept: getAllPlayers")
                    characterAdapter = CharacterAdapter(characters)
                    recyclerView.adapter = characterAdapter
                    for (character in characters) {
                        Log.d(tag, "accept: " + character.getName())
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
                characterViewModel.delete(characterAdapter.getCharacterAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
        characterViewModel.isLoading.observe(this,
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
        val fab = findViewById<FloatingActionButton>(R.id.fab_characters)
        fab.setOnClickListener { openAddCharacterDialog() }
    }

    private fun openAddCharacterDialog() {
        val createCharacterDialog = CreateCharacterDialogFragment(this)
        createCharacterDialog.show(supportFragmentManager, "Create Character Dialog")
    }

    private fun intToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
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
            deleteAllCharacters()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllCharacters() {
        characterViewModel.deleteAllCharactersByPlayer(playerId)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

   override fun saveNewCharacter(character: Character) {
       character.setPlayerId(playerId.toString())
       characterViewModel.insert(character)
   }
}