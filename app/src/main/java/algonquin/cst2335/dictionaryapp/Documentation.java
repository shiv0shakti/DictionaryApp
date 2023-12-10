
package algonquin.cst2335.dictionaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class Documentation extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documentation);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        //step one to toggle the drawer

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.closeDrawer );

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id==R.id.Home){

                Intent intent= new Intent(this, DictionaryApi.class);
                startActivity(intent);
                finish();

            }else if(id==R.id.documentation){

                recreate();

            }else if(id == R.id.Aboutus){

                Toast.makeText(this, "Created By: RISHABH PURI", Toast.LENGTH_SHORT).show();

            }else if(id == R.id.Help){

                Toast.makeText(this, "To use app Just login with your Email and enter the desired pixel and generate a beautiful picture of Bear ", Toast.LENGTH_LONG).show();

            }else if(id==R.id.logout){

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }

            drawerLayout.closeDrawer(GravityCompat.START);



            return true;
        });

    }
}
