package com.bearm.owlbotdictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.owlbotdictionary.Adapters.MyAdapter;
import com.bearm.owlbotdictionary.DAL.WordDAO;
import com.bearm.owlbotdictionary.Interfaces.IVolleyCallback;
import com.bearm.owlbotdictionary.Model.Word;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url, input, finalUrl;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<Word> arrayWord;
    TextInputEditText editSearch;
    TextView tvWord;
    TextView tvPronun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.edit_search);
        tvWord = findViewById(R.id.tv_word);
        tvPronun = findViewById(R.id.tv_pronunciation);


        arrayWord = new ArrayList<>();
        mAdapter = new MyAdapter(arrayWord);

        //recyclerView = findViewById(R.id.recycler_list);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);



        url = getString(R.string.url_dictionary);

        MaterialButton searchBtn = findViewById(R.id.search_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Saving user's input in search field
                input = String.valueOf(editSearch.getText());

                //Checking if input is valid (not empty and only alphabet characters)
                if (!input.equals("") && (input.matches("[a-zA-Z]+"))) {
                    //Sending input to request
                    searchWord(input);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.input_invalid_message_toast), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    /**
     * Method for calling WordDAO class and managing the response of the request it executes
     *
     * @param word user input that will be sent in the WordDAO request
     */
    private void searchWord(String word) {
        //Adding user's input to the url that will be used in the request
        finalUrl = url + word;
        final WordDAO jsonParserVolley = new WordDAO(this);
        jsonParserVolley.requestData(finalUrl, new IVolleyCallback() {

                    @Override
                    public void getResponse(JSONObject response) {

                        Log.d("VOLLEY", "RES" + response);
                        //Sending response to be managed
                        parseData(response);
                    }
                }
        );
    }

    /**
     * Method for extracting the info from the request response
     *
     * @param response JSONObject obteined in the request made by WordDAO class
     *
     */
    private void parseData(JSONObject response) {
        try {

            //Extracting single values of the word, like pronunciation and image from the JSON object
            String word = response.getString("word");
            String pronunciation = response.getString("pronunciation");
            //TODO - Extract image

            Log.e("WORDS", word + " " + pronunciation);

            //Getting multiple values of the word, like type, definition and examples from the JSON object
            JSONArray definitions = response.getJSONArray("definitions");
            //TODO - Extract definitions
            for (int j = 0; j < definitions.length(); j++) {
                JSONObject entry = definitions.getJSONObject(j);

            }
            //TODO - Extract types
            //TODO - Extract examples


            //Log.e("WORDS", definitions + "/ " + types + "/ " + examples);


            //Clearing array before adding new search value
            arrayWord.clear();
            arrayWord.add(new Word(word, pronunciation));

//            recyclerView.setAdapter(mAdapter);

            tvWord.setText(word);
            tvPronun.setText(pronunciation);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
