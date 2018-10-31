package br.unicamp.ft.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.content.ContentValues;
import android.support.design.widget.FloatingActionButton;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.unicamp.ft.firebase.interfaces.OnBiografiaRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        FragmentManager fragmentManager;
    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    final int size = Alunos.alunos.length;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    EditText edtId;
    EditText edtTexto;
    TextView txtOutput;


    public void replaceFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(MainActivity.this);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser == null){
           //Not Signed in, launch the Sign In Activity
            startActivity(new Intent(this, SignInActivity.class));
        }else{
           Toast.makeText(this, mFirebaseUser.getDisplayName().toString(), Toast.LENGTH_SHORT).show();

           //código após logado
        }


        for(int i=0;i<=size;i++) {
            //nomet = Alunos.alunos[i].getNome();
            //rat = Alunos.alunos[i].getRa();
            //idadepadrao = Alunos.alunos[i].getIdade();
            //raint = Integer.parseInt(rat);
            contentValues.put("_id",1);
            contentValues.put("pessoas", "teste");
            contentValues.put("padrao", 22);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                replaceFragment(new MailFragment(),"mail");

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        MyFirstFragment f1 = (MyFirstFragment)fragmentManager.findFragmentById(R.id.frag1);

        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyFirstFragment f2 = new MyFirstFragment();
            fragmentTransaction.add(R.id.frame,f2,"f2_tag");
            fragmentTransaction.commit();
        }



    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.email_settings){
            Toast.makeText(this,"voce cliclou em email",Toast.LENGTH_SHORT).show();
            Fragment mailFragment = fragmentManager.findFragmentByTag("mail");
            if (mailFragment == null){
                mailFragment = new MailFragment();
            }
            replaceFragment(mailFragment, "mail");
            return true;

        }else if (id == R.id.stats){
            Toast.makeText(this,"voce cliclou em stats",Toast.LENGTH_SHORT).show();
            Fragment statsFragment = fragmentManager.findFragmentByTag("stats");
            if (statsFragment == null){
                statsFragment = new StatsFragment();
            }
            replaceFragment(statsFragment, "stats");
            return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Alunos) {
            Toast.makeText(this,"cliclou em Aluno",Toast.LENGTH_SHORT).show();

            Fragment alunosFragmento = fragmentManager.findFragmentByTag("alunos");
            if (alunosFragmento == null){
                alunosFragmento = new AlunosFragmento();
                ((AlunosFragmento)alunosFragmento).setOnBiografiaRequest(
                        new OnBiografiaRequest() {
                            @Override
                            public void onRequest(int position) {
                                Fragment biografiasFragment = fragmentManager.findFragmentByTag("bio");
                                biografiasFragment = new BiografiasFragment();
                                ((BiografiasFragment)biografiasFragment).setArguments(position);
                                replaceFragment(biografiasFragment,"bio");
                            }
                        }
                );

            }
            replaceFragment(alunosFragmento,"alunos");

        } else if (id == R.id.Biografias) {
            Toast.makeText(this,"cliclou em Biografias",Toast.LENGTH_SHORT).show();
            Fragment biografiasFragment = fragmentManager.findFragmentByTag("bio");
            if (biografiasFragment == null){
                biografiasFragment = new BiografiasFragment();
            }
            replaceFragment(biografiasFragment,"bio");

        } else if (id == R.id.Idade) {
            Fragment idadeFragment = fragmentManager.findFragmentByTag("idade");
                idadeFragment = new IdadeFragment();
                ((IdadeFragment)idadeFragment).setOnBiografiaRequest(
                        new OnBiografiaRequest() {
                            @Override
                            public void onRequest(int position) {
                                Fragment biografiasFragment = fragmentManager.findFragmentByTag("bio");
                                biografiasFragment = new BiografiasFragment();
                                ((BiografiasFragment)biografiasFragment).setArguments(position);
                                replaceFragment(biografiasFragment,"bio");
                            }
                        }
                );
            replaceFragment(idadeFragment,"idade");

        } else if (id == R.id.jogoTime) {
            Fragment jogoTimeFragment= fragmentManager.findFragmentByTag("jogoTime");
            if (jogoTimeFragment == null){
                jogoTimeFragment = new JogoTimeFragment();
            }
            replaceFragment(jogoTimeFragment,"jogoTime");

        } else if (id == R.id.jogoFirebaseIdade) {
            Fragment idadeFirebaseFragment = fragmentManager.findFragmentByTag("idadeFirebase");
            idadeFirebaseFragment = new IdadeFirebaseFragment();
            ((IdadeFirebaseFragment)idadeFirebaseFragment).setOnBiografiaRequest(
                    new OnBiografiaRequest() {
                        @Override
                        public void onRequest(int position) {
                            Fragment biografiasFragment = fragmentManager.findFragmentByTag("bio");
                            biografiasFragment = new BiografiasFragment();
                            ((BiografiasFragment)biografiasFragment).setArguments(position);
                            replaceFragment(biografiasFragment,"bio");
                        }
                    }
            );
            replaceFragment(idadeFirebaseFragment,"idadeFirebase");

        } else if (id == R.id.Coracao2) {
            Toast.makeText(this,"cliclou em coracao 2",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"cliclou na share",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {


            Toast.makeText(this,"send ... voce sabe" + ("\ud83d\ude0b"),Toast.LENGTH_SHORT).show();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onInserir(View view){
        int id       = Integer.parseInt(edtId.getText().toString());
        String texto = edtTexto.getText().toString();

        String nomet;
        String rat;
        int idadepadrao,raint;
        int zero = 0;
        ContentValues contentValues = new ContentValues();

        for(int i=0;i<=size;i++ ) {
            nomet = Alunos.alunos[i].getNome();
            rat = Alunos.alunos[i].getRa();
            idadepadrao = Alunos.alunos[i].getIdade();
            raint = Integer.parseInt(rat);
            contentValues.put("_id",raint);
            contentValues.put("pessoas", nomet);
            contentValues.put("padrao", idadepadrao);
            sqLiteDatabase.insert("tabela", null, contentValues);

        }
    }

    public void onAtualizar(View view){
        int id       = Integer.parseInt(edtId.getText().toString());
        String texto = edtTexto.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("texto", texto);

        String   whereClause = "_id = ?";
        String[] whereArgs   = new String[]{Integer.toString(id)};

        sqLiteDatabase.update("tabela", contentValues, whereClause, whereArgs);
    }

    public void onRemover(View view){
        int id       = Integer.parseInt(edtId.getText().toString());

        String   whereClause = "_id = ?";
        String[] whereArgs   = new String[]{Integer.toString(id)};

        sqLiteDatabase.delete("tabela", whereClause, whereArgs);
    }

    public void onSelecionar(View view){
        String sql = "Select * from tabela";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            String str = "";
            do {
                int    id    = cursor.getInt(0);
                String texto = cursor.getString(1);

                str = str + id +"," + texto + "\n";

            }while(cursor.moveToNext());
            txtOutput.setText(str);
        }
        cursor.close();

    }

    public void onStop(){
        super.onStop();
        sqLiteDatabase.close();
        dbHelper.close();
    }
}

