package it.danieltrosko.gastro.ui.newOrder;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.danieltrosko.gastro.R;

public class NewOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Spinner selectTable;

    public NewOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new order fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}