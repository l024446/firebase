package br.unicamp.ft.firebase;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static br.unicamp.ft.firebase.Alunos.alunos;


/**
 * A simple {@link Fragment} subclass.
 */
public class JogoTimeFragment extends Fragment {

    Aluno aluno;
    ImageView imagemTime;
    ImageView imagemAluno;
    TextView resultado;
    View view;
    Boolean jogoRodando = true;


    int times[] = {
            R.drawable.corinthians,
            R.drawable.celtic_fc,
            R.drawable.fla,
            R.drawable.palmeiras,
            R.drawable.guarani,
            R.drawable.paysandu_pa,
            R.drawable.pontepreta,
            R.drawable.saopaulo,
            R.drawable.undefined,
            R.drawable.santos,
            R.drawable.riobranco_ac,};

    public JogoTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view == null){
                view = inflater.inflate(R.layout.fragment_jogo_time, container, false);
        }
        imagemTime = view.findViewById(R.id.jogoTimeImgTime);
        imagemAluno = view.findViewById(R.id.jogoTimeImgAluno);
        resultado = view.findViewById(R.id.jogoTimeResultado);

        return view;

    }

    public void onInserir (View view ){
        new JogoAsyncTask().execute();

    }

    public class JogoAsyncTask extends AsyncTask<Integer, Integer, Void> {

        int imgTime, imgAlun;
        int delay = 3000;
        Random r = new Random();




        @Override
        protected void onPreExecute(){}


        @Override
        protected Void doInBackground(Integer... integers) {
            imgTime = integers[0].intValue();
            int i = 0;
                    while (jogoRodando){

                        do{

                            try{
                                Thread.sleep(delay);
                            } catch (InterruptedException e) {
                                System.out.println("erro no delay do jogo");
                            }
                            imgTime = i;
                            onProgressUpdate(imgTime, imgAlun);
                            }while(i<times.length);

                            imgAlun= r.nextInt(0 - alunos.length);

                        onProgressUpdate(imgTime, imgAlun);
                    }

            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... integers){

            imagemTime.setImageResource(imgTime);
            imagemAluno.setImageResource(imgAlun);
        }


    }

}

