package it.danieltrosko.gastro.ui.newOrder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.danieltrosko.gastro.R;

public class NewOrderFragment extends Fragment {

    private NewOrderViewModel newOrderViewModel;
    private Spinner selectTable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newOrderViewModel =
                ViewModelProviders.of(this).get(NewOrderViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_new_order, container, false);
        final TextView textView = root.findViewById(R.id.text_new_order);
        newOrderViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        selectTable = root.findViewById(R.id.spinner2);

        ArrayAdapter dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, getTable());

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTable.setAdapter(dataAdapter);


        return root;
    }


    private static List<String> getTable() {
        final List<String> listOfCategory = new ArrayList<>();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                URL url;
                HttpURLConnection myConnection = null;

                try {
                    url = new URL("http://10.0.2.2:8080/table/getall");
                    myConnection = (HttpURLConnection) url.openConnection();

                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    if (myConnection.getResponseCode() == 200) {
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {

                                String key = jsonReader.nextName();
                                if (key.equals("name")) {
                                    String value = jsonReader.nextString();
                                    listOfCategory.add(value);
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    myConnection.disconnect();
                }
            }
        });

        return listOfCategory;
    }
}