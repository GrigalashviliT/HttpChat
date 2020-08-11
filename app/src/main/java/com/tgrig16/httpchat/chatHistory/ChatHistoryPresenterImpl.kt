package com.tgrig16.httpchat.chatHistory

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.ChatHistoryItem
import com.tgrig16.httpchat.entities.*
import com.tgrig16.httpchat.network.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ChatHistoryPresenterImpl(val view : ChatHistoryActivity): ChatHistoryContract.Presenter {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatHistoryItemAdapter
    private lateinit var database: ChatDao
    private val LIMIT = 10
    private var startLoad = 0
    private var items = ArrayList<ChatHistoryItem>()
    private lateinit  var users : List<User>
    private val api by lazy {
        API.init()
    }

    override fun fetchUsers() {
        var call = api.getUsers()

        call.enqueue(object: Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                var result  = response.body()?.users
                if (result != null) {
                    users = result!!
                    createItems()

                }else{
                    Toast.makeText(view.applicationContext,"Couldn't load users", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(view.applicationContext,"Couldn't load users", Toast.LENGTH_SHORT).show()

            }
        })
    }

    fun createItems() {
        users.forEach {
            if (it.id != view.userId) {
                var message = getLastMessage(it)

            }
        }
    }

    fun getLastMessage(user: User): Message? {
        val lastMessageRequest = LastMessageRequest(view.userId , user.id)
        var call = api.getLastMessage(lastMessageRequest)
        var message: Message? = null


        call.enqueue(object: Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                var lastMessage  = response.body()?.text
                var time = response.body()?.time

                if (lastMessage != null && time != null) {
                    var receiverId =   response.body()?.receiverId!!
                    var senderId = response.body()?.senderId!!
                    message =  Message(senderId , receiverId , time , lastMessage)
                    var lastMessage = message?.text ?: ""
                    var time = message?.time ?: ""
                    var chatHistoryItem = ChatHistoryItem(user.nickname , lastMessage ,  time , user.id)
                    Log.e("lastMessage" , lastMessage)
                    items.add(chatHistoryItem)
                    addItemsInRecycler()
                }else{
                    Toast.makeText(view.applicationContext,"Couldn't load last message", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {

                Toast.makeText(view.applicationContext,"Couldn't load last message", Toast.LENGTH_SHORT).show()

            }
        })
        return message
    }



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
        recyclerView.adapter = adapter
        adapter.setDelegate(view)
    }

    override fun viewDidLoad() {
        this.items.clear()
        fetchUsers()
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

    fun addItemsInRecycler() {
        adapter.setData(this.items)

    }
}