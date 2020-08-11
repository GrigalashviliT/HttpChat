package com.tgrig16.httpchat.chat

import android.icu.text.SimpleDateFormat
import android.media.audiofx.DynamicsProcessing
import android.os.AsyncTask
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.MessageItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class ChatPresenterImpl(val view : ChatActivity): ChatContract.Presenter {

    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: ChatItemAdapter
    private lateinit var database: ChatDao
    private val LIMIT = 10
    private var startLoad = 0
    private var items : List<MessageItem> = ArrayList<MessageItem>()

    fun loadParentId(personName: String) : Long{
        var id :Long = 1
        Handler().postDelayed({

            database.loadParentId(personName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ item ->
                    id = item
                }, {err ->
                    println(err.stackTrace)
                }
                )

        }, 1000)
        return id
    }

    fun addingMessageToPersonChat() {
        var id = loadParentId("ვიღაცა")
        val sdf = SimpleDateFormat("hh:mm:ss")
        val currentDate = sdf.format(Date())
        var l = MessageItem("exla gvian vwer", true, currentDate, id)
        var n = MessageItem("ლეკვი ლომისა სწორია , ძუ იყოს თუნდა ხვადია", false, currentDate, id)
        var n1 = MessageItem("ასი ათასსა აჯობებს, თუ გამორჩევით მქმნელია.", false, currentDate, id)

        var n2 = MessageItem("ბინდის გვარია სოფელი, ესე თუნდ ამად ბინდდების,/n"
            +"კოკასა შიგან რაცა სდგას, იგივე წარმოდინდების!", false, "currentDate", id)
        var lists = ArrayList<MessageItem>()
        lists.add(l)
        lists.add(n)
        lists.add(l)
        lists.add(n1)
        lists.add(n2)
        lists.add(l)
        lists.add(n)
        lists.add(l)
        lists.add(n1)
        lists.add(n2)
        AsyncTask
            .execute { database.addChatToList(lists)}

    }

    override fun implementRecyclerView(viewManager: LinearLayoutManager , recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        viewManager.setOrientation(LinearLayoutManager.VERTICAL)
        viewManager.setStackFromEnd(true)
        viewManager.setReverseLayout(true)
        recyclerView.layoutManager = viewManager
        adapter = ChatItemAdapter()
        database = view.getDataBase()
    }

    override fun viewDidLoad(personName: String) {
        addItemsInRecycler(personName)
    }

    override fun clickedSend() {
        var id = loadParentId("ვიღაცა")
        val sdf = SimpleDateFormat("hh:mm:ss")
        val currentDate = sdf.format(Date())
        var message = view.getMessageFromEditText()
        var lists = ArrayList<MessageItem>()
        var l = MessageItem(message, true, currentDate, id)
        lists.add(l)
        AsyncTask
            .execute { database.addChatToList(lists)}
        viewDidLoad("ვიღაცა")
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


    fun addItemsInRecycler(personName: String) {
        Handler().postDelayed({
            var id :Long = 1

                    database.loadStringList(0 , 1).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe ({ item ->
                            this.items = item.chats.reversed()
                            adapter.setData(this.items.subList(0 , startLoad + LIMIT))
                            startLoad += LIMIT
                            recyclerView.scrollToPosition(0)
                            recyclerView.adapter = adapter

                        }, {err ->
                            println(err.stackTrace)
                        }
                        )
                },
         1000)
    }
}