package a207project.fall18.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import a207project.fall18.GameCenter.bean.User;

/**
 * A SignIn User interface
 */
public class SignInActivity extends AppCompatActivity {

    /**
     * Username
     */
    private EditText username;

    /**
     * Password
     */
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle("Game Centre");

        Button signin = findViewById(R.id.SignInBT);
        Button signup = findViewById(R.id.SignUpBT);
        username = findViewById(R.id.UserInputBT);
        password = findViewById(R.id.PasswordBT);

        signin.setOnClickListener(this::onClick);
        signup.setOnClickListener(this::onClick);
    }

    private void onClick(View v){
        switch (v.getId()){
            case R.id.SignInBT:
                signin();
                break;
            case R.id.SignUpBT:
                Signup();
                break;
        }
    }

    /**
     * Activate to sign in
     */
    private void signin(){
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        if(username.getText().toString().matches("") || password.getText().toString().matches("")){
            Toast.makeText(SignInActivity.this,"username/password is empty！",Toast.LENGTH_SHORT).show();
        }else{
            boolean loginResult = MyApplication.getInstance().getUserDao().login(user);
            if(loginResult){
                MyApplication.getInstance().setUser(user);
                Toast.makeText(SignInActivity.this,"login success！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, GameSelectionActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(SignInActivity.this,"Login fail！",Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * Activate to sign up
     */
    private void Signup(){
        Intent intent=new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}