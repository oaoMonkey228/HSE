package com.myproject.hse

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import android.content.Intent




class MainActivity : AppCompatActivity() {


    val client = OkHttpClient();

    var character: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val name = findViewById<TextView>(R.id.name);
        val race = findViewById<TextView>(R.id.race);
        val gender = findViewById<TextView>(R.id.gender);
        val status = findViewById<TextView>(R.id.status);
        
        val button = findViewById<Button>(R.id.info);
        
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, CharacterInfo::class.java);
            intent.putExtra("character", character);
            startActivity(intent)
        }

        name.text = "";
        race.text = "";
        gender.text = "";
        status.text = "";

        val editText = findViewById<EditText>(R.id.characterName);

        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val request = Request.Builder()
                    .url("https://rickandmortyapi.com/api/character/?name=" + editText.text)
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {}
                    override fun onResponse(call: Call, response: Response) {
                        val body = response?.body()?.string().toString();
                        character = body;
                        val gson = GsonBuilder().create();
                        if (body != "{\"error\":\"There is nothing here\"}") {
                            val characters = gson.fromJson(body, Characters::class.java);

                            setText(name, characters.results[0].name)
                            setText(race, characters.results[0].type)
                            setText(status, characters.results[0].status)
                            setText(gender, characters.results[0].gender)



                        }
                    }
                })
            }
        })






    }
    private fun setText(text: TextView, value: String) {
        runOnUiThread { text.text = value }
    }

}



class Characters(val info: Info, val results: ArrayList<Character>)
{

}


class Info(val count: Int, val pages: Int, val next: String, val prev: String)
{

}

public class Character(var id: Int, var name: String, var status: String, var species: String, val type: String,
                       var gender: String, var origin: Origin, var location: com.myproject.hse.Location,
                       var image: String, var episode:List<String>, var url: String, var created: String)
{

}

class Origin(val name: String, val url: String) {
}
class Location (val name: String, val url: String){

}
