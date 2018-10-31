package br.unicamp.ft.firebase;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {
    View view;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_stats, container, false);
        }

        textView1 = view.findViewById(R.id.acerto);
        textView2 = view.findViewById(R.id.erro);
        textView3 = view.findViewById(R.id.media_mais);
        textView4 = view.findViewById(R.id.media_menos);
        textView5 = view.findViewById(R.id.pessoas_error);

        DataBaseController crud = new DataBaseController(getContext());
        crud.onSelecionar(textView1, textView2, textView3, textView4);
        crud.onSelecionarTop3Erros(textView5);
        return view;
    }
}