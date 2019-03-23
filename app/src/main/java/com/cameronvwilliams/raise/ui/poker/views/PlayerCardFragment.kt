package com.cameronvwilliams.raise.ui.poker.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.poker.views.adapter.ActiveCardListAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.poker_player_card_fragment.*
import javax.inject.Inject

class PlayerCardFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    private val subscriptions: CompositeDisposable = CompositeDisposable()
    private lateinit var layoutManager: LinearLayoutManager
    private val adapter = ActiveCardListAdapter(listOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.poker_player_card_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        activeCardList.layoutManager = layoutManager
        activeCardList.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    override fun onResume() {
        super.onResume()
        val subscription = dm.getActivePlayersCards()
            .subscribe { result ->
                adapter.updateActiveCardList(result.first!!)
                result.second!!.dispatchUpdatesTo(adapter)
            }

        subscriptions.add(subscription)
    }

    companion object {
        fun newInstance(): PlayerCardFragment {
            return PlayerCardFragment()
        }
    }
}
