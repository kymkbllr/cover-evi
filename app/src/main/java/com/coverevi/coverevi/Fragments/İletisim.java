package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.MainActivity;
import com.coverevi.coverevi.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by cezvedici on 26.04.2017.
 */

public class İletisim extends Fragment {
    private View view;

    EditText isim, email, telefon, mesaj;
    String isim_, email_, telefon_, mesaj_;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_iletisim, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        isim = (EditText) view.findViewById(R.id.isim);
        email = (EditText) view.findViewById(R.id.eposta);
        telefon = (EditText) view.findViewById(R.id.telefon);
        mesaj = (EditText) view.findViewById(R.id.mesaj);

        Button gonderButonu = (Button) view.findViewById(R.id.btnSend);
        gonderButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();

                isim_ = isim.getText().toString();
                email_ = email.getText().toString();
                telefon_ = telefon.getText().toString();
                mesaj_= mesaj.getText().toString();

                new SendForm().execute();
            }
        });

    }

    public void showProgressBar()
    {
        progressDialog = ProgressDialog.show(getActivity(), "Lütfen Bekleyin!", "Mesajınız gönderiliyor..");
        progressDialog.setCancelable(false);
    }

    public class SendForm extends AsyncTask<Void, Void, Void> {
        String response;

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(response);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle)
                        .setPositiveButton("Tamam", null)
                        .setTitle("İletişim")
                        .setMessage(jsonObject.getString("result"));

                builder.show();

            } catch (JSONException e) {
                Log.i("İLETİŞİM", e.getMessage());
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> postFields = new ArrayList<>();

            postFields.add(new BasicNameValuePair("isim", isim_));
            postFields.add(new BasicNameValuePair("email", email_));
            postFields.add(new BasicNameValuePair("telefon", telefon_));
            postFields.add(new BasicNameValuePair("mesaj", mesaj_));

            HttpHandler httpHandler = new HttpHandler("http://www.coverevi.com/api/iletisim.php");
            response = httpHandler.createPOSTRequest(postFields);


            return null;
        }
    }
}
