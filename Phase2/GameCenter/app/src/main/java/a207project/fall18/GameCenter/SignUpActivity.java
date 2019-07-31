package a207project.fall18.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import a207project.fall18.GameCenter.bean.User;
import a207project.fall18.GameCenter.dao.UserDao;


/**
 * A SignUp User interface
 */
public class SignUpActivity extends AppCompatActivity {

    private UserDao userAccountManager;

    /**
     * Player name
     */
    private EditText username;

    /**
     * User password
     */
    private EditText password;

    /**
     * Player nickname
     */
    private EditText nickname;

    /**
     * password verification
     */
    private EditText verifypassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign up!");
        Button registerButton = findViewById(R.id.Register);
        username = findViewById(R.id.Username);
        nickname = findViewById(R.id.Nickname);
        password = findViewById(R.id.NPassword);
        verifypassword = findViewById(R.id.ConfirmPassword);

        userAccountManager = new UserDao(this);
        registerButton.setOnClickListener(this::onClick);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.Register:
                register();
                break;
        }
    }

    /**
     * Register as a new user
     */
    private void register(){
        User user=new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setNickname(nickname.getText().toString());
        if(username.getText().toString().matches("")||password.getText().
                toString().matches("")||verifypassword.getText().toString().
                matches("")){
            Toast.makeText(SignUpActivity.this,
                    "username/password is empty！",Toast.LENGTH_SHORT).show();
        }else if(!password.getText().toString().equals(verifypassword.getText().toString())){
            Toast.makeText(SignUpActivity.this,
                    "password confirm fail",Toast.LENGTH_SHORT).show();
        } else{
            long registerResult = MyApplication.getInstance().getUserDao().register(user);
            if(registerResult>0){
                Toast.makeText(SignUpActivity.this,"Regist success！" +
                        registerResult,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(SignUpActivity.this,"Regist fail,username exist！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}