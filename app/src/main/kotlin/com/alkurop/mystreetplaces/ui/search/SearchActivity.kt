package com.alkurop.mystreetplaces.ui.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.alkurop.mystreetplaces.R
import com.alkurop.mystreetplaces.ui.base.BaseMvpActivity
import com.alkurop.mystreetplaces.ui.navigation.NavigationAction
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity : BaseMvpActivity<SearchViewModel>() {

    @Inject lateinit var presenter: SearchPresenter
    lateinit var adapter: SearchAdapter

    companion object {
        val QUERY = "QUERY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component().inject(this)
        setupRootView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = SearchAdapter {
            presenter.onSearchItemSelected(it)
            onBackPressed()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerDecorator(R.dimen.item_divider))

        RxTextView.afterTextChangeEvents(et_search)
                .debounce(250, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val query = it.editable()?.toString() ?: ""
                    presenter.onSearchQuerySubmit(query)
                    icClose.visibility = if (query.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE

                }

        icClose.setOnClickListener {
            et_search.text = null
        }

        container.setOnClickListener {
            onBackPressed()
        }
        val query = intent.getBundleExtra(BaseMvpActivity.ARGS_KEY).getString(QUERY)
        et_search.setText(query)
        et_search.setSelection(query.length)

    }

    override fun getSubject(): Observable<SearchViewModel> = presenter.viewBus

    override fun getNavigation(): Observable<NavigationAction> = presenter.navBus

    override fun unsubscribe() {
        presenter.unsubscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun renderView(viewModel: SearchViewModel) {
        adapter.updateItems(viewModel.searchResult)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}