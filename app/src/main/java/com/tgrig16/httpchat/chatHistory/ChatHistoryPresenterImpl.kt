package com.tgrig16.httpchat.chatHistory

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.ChatHistoryItem
import kotlin.collections.ArrayList

class ChatHistoryPresenterImpl(val view : ChatHistoryActivity): ChatHistoryContract.Presenter {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatHistoryItemAdapter
    private lateinit var database: ChatDao
    private val LIMIT = 10
    private var startLoad = 0
    private var items = ArrayList<ChatHistoryItem>()

    override fun implementRecyclerView(
        viewManager: LinearLayoutManager,
        recyclerView: RecyclerView
    ) {
        this.recyclerView = recyclerView
        viewManager.orientation = LinearLayoutManager.VERTICAL
        viewManager.stackFromEnd = true
        viewManager.reverseLayout = true
        recyclerView.layoutManager = viewManager
        adapter = ChatHistoryItemAdapter()
        database = view.getDataBase()

    }

    override fun viewDidLoad(personName: String) {
        addItemsInRecycler(personName)
    }

    override fun clickedSend() {
    }

    override fun lazyLoader() {
        if (this.items.size > startLoad && this.items.size > startLoad + LIMIT) {
            adapter.setData(this.items.subList(0, startLoad + LIMIT))
            recyclerView.scrollToPosition(1)
            adapter.notifyItemRangeInserted(startLoad, LIMIT);
            startLoad += LIMIT
        } else if (this.items.size > startLoad) {
            adapter.setData(this.items.subList(0, this.items.size))
            adapter.notifyItemRangeInserted(this.items.size - startLoad, LIMIT);
            startLoad = this.items.size
        }
    }

    fun addItemsInRecycler(personName: String) {
    }
}