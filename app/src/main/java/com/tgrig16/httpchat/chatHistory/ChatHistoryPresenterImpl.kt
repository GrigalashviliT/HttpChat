package com.tgrig16.httpchat.chatHistory

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.AsyncTask
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.chat.ChatItemAdapter
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.ChatHistoryItem
import com.tgrig16.httpchat.database.MessageItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class ChatHistoryPresenterImpl(val view : ChatHistoryActivity): ChatHistoryContract.Presenter {

    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: ChatHistoryItemAdapter
    private lateinit var database: ChatDao
    private val LIMIT = 10
    private var startLoad = 0
    private var items  = ArrayList<ChatHistoryItem>()

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
        adapter = ChatHistoryItemAdapter()
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
            recyclerView.scrollToPosition(1)
            adapter.notifyItemRangeInserted(startLoad, LIMIT);
            startLoad += LIMIT
        }else if (this.items.size  > startLoad) {
            adapter.setData(this.items.subList(0 , this.items.size))
            adapter.notifyItemRangeInserted(this.items.size - startLoad, LIMIT);
            startLoad = this.items.size
        }
    }

fun getChatHistoryItems(m: List <MessageItem> , personName: String) {
    for (i in m){
        var r = ChatHistoryItem(personName , m[0].message , m[0].time )
        this.items.add(r)
    }
}

    fun addItemsInRecycler(personName: String) {
        Handler().postDelayed({
            var id :Long = 1

            database.loadStringList(0 , 1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ item ->
                    getChatHistoryItems(item.chats.reversed() , personName)
                    adapter.setData(this.items.subList(0 , startLoad + LIMIT))
                    startLoad += LIMIT
                    recyclerView.adapter = adapter

                }, {err ->
                    println(err.stackTrace)
                }
                )
        },
            1000)
    }
}

open class OnSwipeTouchListener(ctx: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    companion object {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }


    }

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onSwipeTop() {}

    open fun onSwipeBottom() {}
}