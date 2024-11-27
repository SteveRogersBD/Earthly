package com.example.earthly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.earthly.MainActivity;
import com.example.earthly.R;
import com.example.earthly.RetrofitInstance;
import com.example.earthly.apiIterfaces.AuthApiInterface;
import com.example.earthly.databinding.ActivitySignUpBinding;
import com.example.earthly.requests.LogInRequest;
import com.example.earthly.requests.SignUpRequest;
import com.example.earthly.responses.LogInResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    ProgressBar pd;
    AuthApiInterface authApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pd = new ProgressBar(this);
        pd.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pd.setIndeterminate(true);

        // Add ProgressBar to the layout dynamically
        ((ViewGroup) binding.getRoot()).addView(pd);
        pd.setVisibility(View.INVISIBLE); // Initially hidden

        binding.signInTvSignUp.setOnClickListener((v) -> {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        });

        binding.signUpBtnSignUp.setOnClickListener((v) -> {
            String username = binding.nameSignUp.getText().toString();
            String email = binding.emailSignUp.getText().toString();
            String password = binding.passwordSignUp.getText().toString();

            // Validate inputs
            if (username.isEmpty()) {
                binding.nameSignUp.setError("Enter a valid name!!!");
                return;
            }
            if (email.isEmpty()) {
                binding.emailSignUp.setError("Enter a valid email!!!");
                return;
            }
            if (password.isEmpty()) {
                binding.passwordSignUp.setError("Enter a valid password!!!");
                return;
            }

            // Show the progress bar
            pd.setVisibility(View.VISIBLE);

            // Create request
            SignUpRequest request = new SignUpRequest(username, email, password);

            // Make the API call
            authApi = RetrofitInstance.authApiInterface();
            authApi.registerUser(request).enqueue(new Callback<LogInResponse>() {
                @Override
                public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Delay navigation to the next activity
                        pd.setVisibility(View.VISIBLE);
                        storeInSharedPreferences(username,email,password);
                        binding.signUpBtnSignUp.postDelayed(() -> {
                            pd.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this, "Registration Successful!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish(); // Close this activity
                        }, 2000); // Delay of 2 seconds
                    } else {
                        pd.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignUpActivity.this, response.message(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LogInResponse> call, Throwable throwable) {
                    pd.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUpActivity.this, throwable.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
    private void storeInSharedPreferences(String username,String email,String password)
    {
        SharedPreferences namedPreference = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = namedPreference.edit();
        editor.putString("username",username);
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putBoolean("isLoggedIn",true);
        editor.apply();
    }

}