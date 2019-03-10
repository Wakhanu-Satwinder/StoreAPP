package com.gadaffi.mystore.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.gadaffi.mystore.Constants;
import com.gadaffi.mystore.R;
import com.gadaffi.mystore.ServerRequest;
import com.gadaffi.mystore.ServerResponse;
import com.gadaffi.mystore.Models.Student;
import com.gadaffi.mystore.api.RequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class LoginFragment extends Fragment implements View.OnClickListener{

        private AppCompatButton btn_login;
        private EditText et_email,et_password;
        private TextView tv_register;
        private ProgressBar progress;
        private SharedPreferences pref;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_login,container,false);
            initViews(view);
            return view;
        }

        private void initViews(View view){

            pref = getActivity().getSharedPreferences("mistore",MODE_PRIVATE);

            btn_login = (AppCompatButton)view.findViewById(R.id.btn_login);
            tv_register = (TextView)view.findViewById(R.id.tv_register);
            et_email = (EditText)view.findViewById(R.id.et_email);
            et_password = (EditText)view.findViewById(R.id.et_password);

            progress = (ProgressBar)view.findViewById(R.id.progress);

            btn_login.setOnClickListener(this);
            tv_register.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.tv_register:
                    displayFragment(new RegisterFragment());
                    break;

                case R.id.btn_login:
                    String email = et_email.getText().toString();
                    String password = et_password.getText().toString();

                    if(!email.isEmpty() && !password.isEmpty()) {

                        progress.setVisibility(View.VISIBLE);
                        loginProcess(email,password);

                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                    break;

            }
        }
        private void loginProcess(String email,String password){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RequestInterface requestInterface = retrofit.create(RequestInterface.class);

            Student student = new Student();
            student.setEmail(email);
            student.setPassword(password);
            ServerRequest request = new ServerRequest();
            request.setOperation(Constants.LOGIN_OPERATION);
            request.setStudent(student);
            Call<ServerResponse> response = requestInterface.operation(request);

            response.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                        ServerResponse resp = response.body();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                    if(resp.getResult().equals(Constants.SUCCESS)){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean(Constants.IS_LOGGED_IN,true);
                        editor.putString(Constants.EMAIL,resp.getStudent().getEmail());
                        editor.putString(Constants.NAME,resp.getStudent().getName());
                        editor.putString(Constants.UNIQUE_ID,resp.getStudent().getUnique_id());
                        editor.apply();
                        goToProfile();

                    }
                    progress.setVisibility(View.INVISIBLE);

                    }


                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    progress.setVisibility(View.INVISIBLE);
                    Log.e(Constants.TAG,"failed" + t.getMessage());
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

//            response.enqueue(new Callback<ServerResponse>() {
//                @Override
//                public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//
//                    ServerResponse resp = response.body();
//                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
//
//                    if(resp.getResult().equals(Constants.SUCCESS)){
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putBoolean(Constants.IS_LOGGED_IN,true);
//                        editor.putString(Constants.EMAIL,resp.getUser().getEmail());
//                        editor.putString(Constants.NAME,resp.getUser().getName());
//                        editor.putString(Constants.UNIQUE_ID,resp.getUser().getUnique_id());
//                        editor.apply();
//                        goToProfile();
//
//                    }
//                    progress.setVisibility(View.INVISIBLE);
//                }
//
//                @Override
//                public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//                    progress.setVisibility(View.INVISIBLE);
//                    Log.d(Constants.TAG,"failed");
//                    Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
//
//                }
//            });
        }

        private void goToRegister(){

            Fragment register = new Fragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,register);
            ft.commit();
        }

        private void goToProfile(){

         Intent intent = new Intent(getActivity(), MainActivity.class);
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


