package ru.jelezov.stockstracker.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.model.CompanyBrief
import ru.jelezov.stockstracker.model.PopularRequestGenerated

class FragmentSearch: Fragment() {


    private var listener: StocksListItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is StocksListItemClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EditText>(R.id.field_search).showKeyboard()

        view.findViewById<RecyclerView>(R.id.popular_requests_recycler).apply {
            this.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)

            val adapter = AdapterFragmentSearchPopularRequest { companyBrief ->
                listener?.onStocksSelected(companyBrief)
            }

            this.adapter = adapter

            adapter.submitList(PopularRequestGenerated.addPopularRequests())
        }


      clickSearchButton()

    }

    private fun clickSearchButton(){

        view?.findViewById<View>(R.id.image_back)?.setOnClickListener {
            findNavController().popBackStack()
        }

        view?.findViewById<View>(R.id.image_clean)?.setOnClickListener {
            view?.findViewById<EditText>(R.id.field_search)?.setText(null)
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    interface StocksListItemClickListener {
        fun onStocksSelected(stocks: CompanyBrief)
    }

    fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        this.requestFocus()
        imm.showSoftInput(this, 0)
    }

}

