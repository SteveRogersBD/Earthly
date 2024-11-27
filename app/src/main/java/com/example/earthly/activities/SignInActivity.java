package com.example.earthly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.earthly.MainActivity;
import com.example.earthly.R;
import com.example.earthly.RetrofitInstance;
import com.example.earthly.apiIterfaces.AuthApiInterface;
import com.example.earthly.databinding.ActivitySignInBinding;
import com.example.earthly.requests.LogInRequest;
import com.example.earthly.responses.LogInResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    AuthApiInterface authApi;
    ProgressBar pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pd = new ProgressBar(this);
        pd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        pd.setIndeterminate(true);
        pd.setVisibility(View.INVISIBLE);
        ((ViewGroup)binding.getRoot()).addView(pd);
        binding.signInBtnSignIn.setOnClickListener((v)->{
            pd.setVisibility(View.VISIBLE);
            String email = binding.emailSignIn.getText().toString();
            String password = binding.passwordSignIn.getText().toString();
            if(email.equals("")) binding.emailSignIn.setError("Enter a valid email!!!");
            if(password.equals("")) binding.passwordSignIn.setError("Enter a valid password!!!");
            LogInRequest request = new LogInRequest(email,password);
            authApi = RetrofitInstance.authApiInterface();
            authApi.logInUser(request).enqueue(new Callback<LogInResponse>() {
                @Override
                public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                    pd.setVisibility(View.VISIBLE);
                    if(response.isSuccessful() && response.body()!=null)
                    {
                        try{
                            binding.signInBtnSignIn.postDelayed(()->{
                                pd.setVisibility(View.INVISIBLE);
                                String name = response.body().data.username;
                                goToMain(name);
                            },2000);

                        }catch(Exception e)
                        {
                            Toast.makeText(SignInActivity.this, e.getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignInActivity.this, response.message(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LogInResponse> call, Throwable throwable) {
                    pd.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignInActivity.this, throwable.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });


        });

        //
        binding.googleBtnSignIn.setOnClickListener((v)->{
            //do something with Firebase
        });

        binding.forgotPassword.setOnClickListener((v)->{
            startActivity(new Intent(SignInActivity.this,ForgotPasswordActivity.class));
        });
        binding.signUpTvSignIn.setOnClickListener((v)->{
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        if(isLoggedIn)
        {
            String name = sharedPreferences.getString("username",null);
            goToMain(name);
        }
    }

    private void goToMain(String name)
    {
        if(name!=null && name.equals(""))
        {
            String message = "Welcome "+name+" !!!";
            Toast.makeText(SignInActivity.this, message, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }
    }
}