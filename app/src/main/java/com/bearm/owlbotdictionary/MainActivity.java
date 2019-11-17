package com.bearm.owlbotdictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.owlbotdictionary.Adapters.MyAdapter;
import com.bearm.owlbotdictionary.DAL.WordDAO;
import com.bearm.owlbotdictionary.Interfaces.IVolleyCallback;
import com.bearm.owlbotdictionary.Model.Word;
import com.bearm.owlbotdictionary.Model.WordEntry;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url, input, finalUrl;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<WordEntry> arrayWord;
    TextInputEditText editSearch;
    TextView tvWord;
    TextView tvPronunciation;
    TextView tvExampleSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.edit_search);
        tvWord = findViewById(R.id.tv_word);
        tvPronunciation = findViewById(R.id.tv_pronunciation);

        arrayWord = new ArrayList<>();
        mAdapter = new MyAdapter(arrayWord, getApplicationContext());

        recyclerView = findViewById(R.id.recycler_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


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
                        cleanData();
                        //Sending response to be managed
                        parseData(response);
                    }
                }
        );
    }


    /**
     * Method to clear the data of all the elements of the word entry before displaying new info.
     */
    private void cleanData() {
        //Word
        tvWord.setText("");

        //Pronunciation
        tvPronunciation.setText("");

        //Array: Definitions, types, image and examples
        arrayWord.clear();


    }

    /**
     * Method for extracting the info from the request response
     *
     * @param response JSONObject obteined in the request made by WordDAO class
     */
    private void parseData(JSONObject response) {
        try {

            JSONObject entry;
            String definition;
            String type;
            String example;
            String urlImage;

            //Extracting single values of the word, like pronunciation, from the JSON object
            String word = response.getString("word");
            String pronunciation = response.getString("pronunciation");

            Log.e("WORDS", word + " " + pronunciation);

            //Extracting multiple values of the word, like type, definition and examples, from the JSON object
            JSONArray definitions = response.getJSONArray("definitions");
            //TODO - Extract multiple values
            Log.e("DEFINITIONS.LENGHT", String.valueOf(definitions.length()));

            for (int i = 0; i < definitions.length(); i++) {
                entry = definitions.getJSONObject(i);
                definition = String.valueOf(entry.get("definition"));
                type = entry.getString("type");
                example = entry.getString("example");
                urlImage = String.valueOf(entry.get("image_url"));

                Log.e("VALUES", definition + " " + type + " " + example + " " + urlImage);

                arrayWord.add(new WordEntry(definition, type, urlImage, example));
            }


            //Log.e("WORDS", definitions + "/ " + types + "/ " + examples);
            tvWord.setText(word);
            if(!pronunciation.equals("null")){
                tvPronunciation.setText("/" + pronunciation + "/");
            }

            recyclerView.setAdapter(mAdapter);

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }


}
