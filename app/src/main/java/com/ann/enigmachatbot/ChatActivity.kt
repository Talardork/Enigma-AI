package com.ann.enigmachatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ann.enigmachatbot.adapter.MessageAdapter
import com.ann.enigmachatbot.api.ApiUtilities
import com.ann.enigmachatbot.databinding.ActivityChatBinding
import com.ann.enigmachatbot.model.MessageModel
import com.ann.enigmachatbot.model.request.ChatRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    private lateinit var adapter : MessageAdapter

    var list = ArrayList<MessageModel>()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.stackFromEnd = true

        adapter = MessageAdapter(list)
        binding.myRecyclerView.adapter = adapter
        binding.myRecyclerView.layoutManager = mLayoutManager


        binding.sendBtn.setOnClickListener {

            if (binding.userMessage.text!!.isEmpty()){
                Toast.makeText(this,"Please enter a text",Toast.LENGTH_SHORT).show()
            }
            else{
                callApi()
                binding.userMessage.text!!.clear()
            }

        }
        binding.backBtn.setOnClickListener {
            finish()
        }


    }

    private fun callApi() {

        list.add(MessageModel(true,false , binding.userMessage.text.toString()))

        adapter.notifyItemInserted(list.size-1)

//        binding.myRecyclerView.recycledViewPool.clear() //last message hamesha dikhega.
        binding.myRecyclerView.smoothScrollToPosition(list.size-1)

        val apiInterface = ApiUtilities.getApiInterface()

        val requestBody = RequestBody.create(MediaType.parse("application/json"),
            Gson().toJson(
                ChatRequest(
                    250,
                    "gpt-3.5-turbo-instruct",
                    binding.userMessage.text.toString(), //jo user puchta hai
                    0.7
                )
            )
        )

        val contentType = "application/json"
        val authorization = "Bearer ${Util.API_KEY}"

        lifecycleScope.launch(Dispatchers.IO){

            try {
                val response = apiInterface.getChat(
                    contentType, authorization, requestBody
                )

                val chatResponse = response.choices.first().text
                list.add(MessageModel(false,false,chatResponse))


                withContext(Dispatchers.Main){

                    adapter.notifyItemInserted(list.size-1)

//                    binding.myRecyclerView.recycledViewPool.clear() //last message hamesha dikhega.
                    binding.myRecyclerView.smoothScrollToPosition(list.size-1)

                }




            }
            catch (e : Exception){

                withContext(Dispatchers.Main){

                    Toast.makeText(this@ChatActivity, e.message,Toast.LENGTH_LONG).show()

                }
            }




        }



    }
}