package br.unicamp.ft.firebase;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.Random;

import br.unicamp.ft.firebase.interfaces.OnBiografiaRequest;

import static br.unicamp.ft.firebase.Alunos.alunos;



/**
 * A simple {@link Fragment} subclass.
 */
public class IdadeFragment extends Fragment {
    public ImageView imageView1;
    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public Button mButton1;
    public Button mButton2;
    public Button mButton3;
    public Button mButton4;
    public Button mButton5;
    public Button mButton6;
    public Button mButton7;
    public Button mButton8;
    public Button mButton9;
    public int idade;
    public int idadedigitada;
    public int tentativas;
    Random gerador = new Random();
    final int size = Alunos.alunos.length;
    public int alunoJOGO = 0;
    View view1;
    private OnBiografiaRequest onBiografiaRequest;
    public String ra;
    public String nome;

    public void setOnBiografiaRequest(OnBiografiaRequest onBiografiaRequest) {
        this.onBiografiaRequest = onBiografiaRequest;
    }


    public IdadeFragment() {
        // Required empty public constructor
    }

    public void startGame() {



        alunoJOGO=gerador.nextInt(size);
        System.out.println(alunoJOGO);
        tentativas = 3;

        ra = alunos[alunoJOGO].getRa();
        nome = alunos[alunoJOGO].getNome();
        imageView1.setImageResource(alunos[alunoJOGO].getFoto());
        textView1.setText(alunos[alunoJOGO].getNome());
        idade = alunos[alunoJOGO].getIdade();
        textView2.setText(String.valueOf("tentativas restantes: "+tentativas));



    }
    public void testejogo(int idade){
        if (alunos[alunoJOGO].idade == idade) {
            DataBaseController crud = new DataBaseController(getContext());
            if (!crud.isRegistrado(ra)) {
                crud.onInserir(ra, nome, 1, 0, 0, 0);
                System.out.println("CRIOU NOVO ACERTO");
            }
            else{
                crud.onAtualizarAcerto(ra);
                System.out.println("JA EXISTE ACERTO");
            }
            correto();
        }
        else {

            incorreto();

        }

    }

    public void correto(){
        textView3.setText("correto!!");
        view1.postDelayed(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textView3.setText("jogue novamente!! ");

                        startGame();
                    }
                }, 2000);


    }
    @SuppressLint("SetTextI18n")
    public void incorreto(){
        if (idade > idadedigitada) {
            int distancia = idade - idadedigitada;
            DataBaseController crud = new DataBaseController(getContext());
            if (!crud.isRegistrado(ra)) {
                crud.onInserir(ra, nome, 0,1,0,distancia);
            }
            else {
                crud.onAtualizarErroMenos(ra, distancia);
            }
            textView3.setText("A idade é maior.");
        }
        if (idade < idadedigitada){
            int distancia = idadedigitada - idade;
            DataBaseController crud = new DataBaseController(getContext());
            if (!crud.isRegistrado(ra)) {
                crud.onInserir(ra, nome, 0,1,distancia,0);
            }
            else {
                crud.onAtualizarErroMais(ra, distancia);
            }
            textView3.setText("A idade é menor");
        }
        tentativas--;
        if (tentativas > 0 ){
            textView2.setText(String.valueOf("tentativas restantes: "+tentativas));

        }

        else {
            textView2.setText(String.valueOf("tentativas restantes: "+tentativas));
            textView3.setText("você perdeu!! ");
            view1.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"Conheça seu colega!! "+ alunos[alunoJOGO].nome,Toast.LENGTH_SHORT).show();
                            if (onBiografiaRequest != null){
                                onBiografiaRequest.onRequest(alunoJOGO);
                            }

                        }
                    }, 2000);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view1 == null) {
            view1 = inflater.inflate(R.layout.fragment_idade, container, false);
        }
        imageView1 = view1.findViewById(R.id.fotojogo);
        textView1 = view1.findViewById(R.id.nomejogo);
        textView2 = view1.findViewById(R.id.tentativas);
        textView3 = view1.findViewById(R.id.resposta);
        mButton1 = view1.findViewById(R.id.dezenove);
        mButton2 = view1.findViewById(R.id.vinte);
        mButton3 = view1.findViewById(R.id.vinteum);
        mButton4 = view1.findViewById(R.id.vintedois);
        mButton5 = view1.findViewById(R.id.vintetres);
        mButton6 = view1.findViewById(R.id.vintequatro);
        mButton7 = view1.findViewById(R.id.vintecinco);
        mButton8 = view1.findViewById(R.id.vinteseis);
        mButton9 = view1.findViewById(R.id.vintesete);

        startGame();

        View.OnClickListener guessButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case (R.id.dezenove):
                        idadedigitada = 19;
                        testejogo(19);
                        break;
                    case (R.id.vinte):
                        idadedigitada = 20;
                        testejogo(20);
                        break;
                    case (R.id.vinteum):
                        idadedigitada = 21;
                        testejogo(21);
                        break;
                    case (R.id.vintedois):
                        idadedigitada = 22;
                        testejogo(22);
                        break;
                    case (R.id.vintetres):
                        idadedigitada = 23;
                        testejogo(23);
                        break;
                    case (R.id.vintequatro):
                        idadedigitada = 24;
                        testejogo(24);
                        break;
                    case (R.id.vintecinco):
                        idadedigitada = 25;
                        testejogo(25);
                        break;
                    case (R.id.vinteseis):
                        idadedigitada = 26;
                        testejogo(26);
                        break;
                    case (R.id.vintesete):
                        idadedigitada = 27;
                        testejogo(27);

                        break;

                }

            }
        };

        mButton1.setOnClickListener(guessButtonListener);
        mButton2.setOnClickListener(guessButtonListener);
        mButton3.setOnClickListener(guessButtonListener);
        mButton4.setOnClickListener(guessButtonListener);
        mButton5.setOnClickListener(guessButtonListener);
        mButton6.setOnClickListener(guessButtonListener);
        mButton7.setOnClickListener(guessButtonListener);
        mButton8.setOnClickListener(guessButtonListener);
        mButton9.setOnClickListener(guessButtonListener);

        return view1;

    }


}
