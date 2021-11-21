package com.carcassonneteam.gloomhavenhelper.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.carcassonneteam.gloomhavenhelper.R
import com.carcassonneteam.gloomhavenhelper.adapters.PartyAdapter
import com.carcassonneteam.gloomhavenhelper.models.Party
import com.carcassonneteam.gloomhavenhelper.viewmodels.PartyActivityViewModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PartyActivity : AppCompatActivity(), CreatePartyDialogFragment.CreatePartyListener,
    PartyAdapter.OnPartyClickListener {
    private val tag = "PartyActivity"

    private lateinit var partyActivityViewModel: PartyActivityViewModel
    private val compositeDisposable = CompositeDisposable()
    private lateinit var recyclerView: RecyclerView
    private lateinit var partyAdapter: PartyAdapter
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party)
        this.setTheme(R.style.Theme_GloomhavenHelper)
        intToolbar()
        val fab: FloatingActionButton = findViewById(R.id.fab_parties)
        val ib: Button = findViewById(R.id.items_button)
        recyclerView = findViewById(R.id.party_recycler)
        progressbar = findViewById(R.id.progress)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        partyActivityViewModel = ViewModelProvider(this as AppCompatActivity).get(PartyActivityViewModel::class.java)

        val disposable: Disposable =
            partyActivityViewModel.getAllParty().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { parties ->
                    Log.d(tag, "accept: Called")
                    setDataToRecyclerView(parties)
                }

        compositeDisposable.add(disposable)

        partyActivityViewModel.getIsLoading().observe(
            this,
            { aBoolean ->
                Log.d(tag, "onChanged: $aBoolean")
                if (aBoolean != null) {
                    if (aBoolean) {
                        progressbar.visibility = View.VISIBLE
                    } else {
                        progressbar.visibility = View.GONE
                    }
                }
            })
        fab.setOnClickListener { openCreatePartyDialog() }
        ib.setOnClickListener {
            val i = Intent(applicationContext, ItemsActivity::class.java)
            startActivity(i)
        }
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
                partyActivityViewModel.deleteParty(partyAdapter.getPartyAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun setDataToRecyclerView(parties: List<Party>) {
        partyAdapter = PartyAdapter(parties)
        partyAdapter.setItemClickListener(this)
        recyclerView.adapter = partyAdapter
    }

    private fun intToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }


   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            deleteParty()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteParty() {
        partyActivityViewModel.deleteAllParty()
    }

    private fun openCreatePartyDialog() {
        val createPartyDialog = CreatePartyDialogFragment(this)
        createPartyDialog.show(supportFragmentManager, "create dialog")
    }
        override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onPartyClick(party: Party) {
        Log.d(tag, "onPartyClick: onItemClick")
        moveToMoviesActivity(party)
    }

    private fun moveToMoviesActivity(party: Party) {
        val intent = Intent(this@PartyActivity, PlayerActivity::class.java)
        intent.putExtra("party", party.getName())
        intent.putExtra("id", party.getId())
        intent.putExtra("reputation", party.getReputation())
        startActivity(intent)
    }

    override fun saveNewParty(party: Party) {
        Log.d(tag, "saveNewParty: " + party.getName())
        partyActivityViewModel.insert(party)
    }
}