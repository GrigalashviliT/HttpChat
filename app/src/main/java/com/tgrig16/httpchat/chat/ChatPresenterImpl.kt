package com.tgrig16.httpchat.chat

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.MessageItem
import com.tgrig16.httpchat.entities.*
import com.tgrig16.httpchat.network.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatPresenterImpl(val view : ChatActivity): ChatContract.Presenter {

    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: ChatItemAdapter
    private val LIMIT = 10
    private var startLoad = 0
    private var items  = ArrayList<MessageItem>()
    private val api by lazy {
        API.init()
    }

    override fun implementRecyclerView(viewManager: LinearLayoutManager , recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        viewManager.orientation = LinearLayoutManager.VERTICAL
        viewManager.stackFromEnd = true
        viewManager.reverseLayout = true
        recyclerView.layoutManager = viewManager
        adapter = ChatItemAdapter()
        recyclerView.adapter = adapter
    }

    override fun viewDidLoad(firstUser: Long , secondUser: Long) {
        Log.e("firstUser" , firstUser.toString())
        loadMessages(firstUser , secondUser)
    }


    override fun clickedSend(message: String , myId : Long , yourId: Long) {
        val sdf = SimpleDateFormat("hh:mm:ss")
        val currentDate = sdf.format(Date())
        val  messageItem = Message(myId , yourId , currentDate , message)
        val call = api.sendMessage(messageItem)
        call.enqueue(object: Callback<MessageSentStatus> {
            override fun onResponse(
                call: Call<MessageSentStatus>,
                response: Response<MessageSentStatus>
            ) {
                var resp = response.body()?.success
                if (resp == null) {
                    Toast.makeText(view.applicationContext, "Couldn't sent message", Toast.LENGTH_SHORT).show()
                } else {
                    var messageItem = MessageItem(message , true ,currentDate)
                    items.add(0 ,messageItem )
                    adapter.setData(items)
                    recyclerView.scrollToPosition(0)
                    adapter.notifyItemRangeInserted(0, 1);
                }
            }

            override fun onFailure(call: Call<MessageSentStatus>, t: Throwable) {
                Toast.makeText(view.applicationContext,"Couldn't sent message", Toast.LENGTH_SHORT).show()

            }

        })
    }

    fun loadMessages(firstUser: Long , secondUser: Long) {
        Log.e("secondUser" , secondUser.toString())
        val message = LastMessageRequest(firstUser , secondUser)
        val call = api.getMessages(message)
        Log.e("call" , call.toString())
        call.enqueue(object: Callback<Messages>{

            override fun onResponse(call: Call<Messages>, response: Response<Messages>) {
                Log.e("response" , response.toString())
                val messages  = response.body()?.messages

                if( messages != null) {
                    messages.forEach{
                        val messageItem = MessageItem(it.text , it.senderId == firstUser , it.time)
                        items.add(messageItem)
                    }
                    addItemsInRecycler()
                }else{
                    Toast.makeText(view.applicationContext,"Couldn't load messages", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Messages>, t: Throwable) {
                Toast.makeText(view.applicationContext,"Couldn't load messages", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun lazyLoader() {
        if (this.items.size  > startLoad && this.items.size > startLoad + LIMIT) {
            adapter.setData(this.items.subList(0 , startLoad + LIMIT))
            recyclerView.scrollToPosition(startLoad)
            adapter.notifyItemRangeInserted(startLoad, LIMIT);
            startLoad += LIMIT
        }else if (this.items.size  > startLoad) {
            adapter.setData(this.items.subList(0 , this.items.size))
            recyclerView.scrollToPosition(startLoad)
            adapter.notifyItemRangeInserted(this.items.size - startLoad, LIMIT);
            startLoad = this.items.size
        }
    }


    fun addItemsInRecycler() {
        Log.e("messages" , this.items.toString())
        adapter.setData(this.items)
    }
}