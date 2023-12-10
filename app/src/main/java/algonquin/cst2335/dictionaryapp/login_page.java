package algonquin.cst2335.dictionaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class login_page extends AppCompatActivity {

    private EditText EmailView;
    private EditText passwordview;
    private CheckBox RemenberMe;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        // Set up the login form.
        EmailView = (EditText) findViewById(R.id.Email);
        passwordview = (EditText) findViewById(R.id.password);


        Button EmailloginButton = (Button) findViewById(R.id.b1);
        EmailloginButton.setOnClickListener(view -> attemptLogin());



        RemenberMe = (CheckBox) findViewById(R.id.b2);
        //Here we will validate saved preferences
        if (!new PrefManager(this).isUserLogedOut()) {
            //user's email and password both are saved in preferences
            startHomeActivity();
        }



            //drawer layout

            drawerLayout = findViewById(R.id.drawerlayout);
            navigationView = findViewById(R.id.navigationView);
            toolbar = findViewById(R.id.toolbar);



            //step one to toggle the drawer




            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.closeDrawer);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(item -> {

                int id = item.getItemId();

                if (id == R.id.Home) {

                    recreate();

                } else if (id == R.id.documentation) {

                    Intent intent = new Intent(this, Documentation.class);
                    startActivity(intent);


                } else if (id == R.id.Aboutus) {

                    Toast.makeText(this, "Created By: Rishabh Puri", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.logout) {

                    Intent intent = new Intent(this, MainActivity
                            .class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else if (id == R.id.Help) {

                    Toast.makeText(this, "To use app Just login with your Email and enter the desired pixel and generate a beautiful picture of Bear ", Toast.LENGTH_LONG).show();

                }

                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            });
        };
        //





    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        EmailView.setError(null);
        passwordview.setError(null);

        // Store values at the time of the login attempt.
        String email = EmailView.getText().toString();
        String password = passwordview.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordview.setError("password invalid");
            focusView = passwordview;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            EmailView.setError("Email Required");
            focusView = EmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            EmailView.setError("Invalid Email");
            focusView = EmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // save data in local shared preferences
            if (RemenberMe.isChecked())
                saveLoginDetails(email, password);
            startHomeActivity();
        }


    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, DictionaryApi.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginDetails(String email, String password) {
        new PrefManager(this).saveLoginDetails(email, password);
    }


    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 8;
    }
}
