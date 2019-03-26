package com.gadaffi.mystore.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gadaffi.mystore.Constants;
import com.gadaffi.mystore.R;
import com.gadaffi.mystore.ServerRequest;
import com.gadaffi.mystore.ServerResponse;
import com.gadaffi.mystore.Models.Student;
import com.gadaffi.mystore.api.RequestInterface;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment implements View.OnClickListener{

        private AppCompatButton btn_register;
        private EditText et_email,et_password,et_regno,et_phoneno,et_name;
        private TextView tv_login;
        private ProgressBar progress;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_register,container,false);
            initViews(view);
            return view;
        }

        private void initViews(View view){

            btn_register = view.findViewById(R.id.btn_register);
            tv_login = view.findViewById(R.id.tv_login);
            et_name = view.findViewById(R.id.et_name);
            et_email = view.findViewById(R.id.et_email);
            et_regno = view.findViewById(R.id.et_regno);
            et_phoneno = view.findViewById(R.id.et_phoneno);
            et_password = view.findViewById(R.id.et_password);

            progress = view.findViewById(R.id.progress);

            btn_register.setOnClickListener(this);
            tv_login.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.tv_login:
                    displayFragment(new LoginFragment());
                    break;

                case R.id.btn_register:

                    String name = et_name.getText().toString();
                    String email = et_email.getText().toString();
                    String regno = et_regno.getText().toString();
                    String phoneno = et_phoneno.getText().toString();
                    String password = et_password.getText().toString();

                    if(!name.isEmpty() && !email.isEmpty() && !regno.isEmpty() && !phoneno.isEmpty() && !password.isEmpty()) {

                        progress.setVisibility(View.VISIBLE);
                        registerProcess(name,email,regno,phoneno,password);



                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                    break;

            }

        }

        private void registerProcess(String name, String email,String regno,String phoneno,String password){

            OkHttpClient okHttpClient = new OkHttpClient();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RequestInterface requestInterface = retrofit.create(RequestInterface.class);

            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setRegno(regno);
            student.setPhoneno(phoneno);
            student.setPassword(password);
            ServerRequest request = new ServerRequest();
            request.setOperation(Constants.REGISTER_OPERATION);
            request.setStudent(student);
            Call<ServerResponse> response = requestInterface.operation(request);

            response.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                    ServerResponse resp = response.body();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    progress.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                    progress.setVisibility(View.INVISIBLE);
                    Log.d(Constants.TAG,"failed");
                    Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

                }
            });
        }

    //private void goToLogin(){
       // Fragment login = new Fragment();
        ///getFragmentManager()
               // .beginTransaction().replace(R.id.fragment_frame,login).commit();
    //}
    private void goToLogin(){

        Intent intent = new Intent(getActivity(),LauncherActivity.class);
        startActivity(intent);
    }

    public void displayFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
    }

