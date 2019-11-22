package br.com.can.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.can.R;
import br.com.can.entity.Jogo;

public class JogoAdapter extends ArrayAdapter<Jogo> {

    private List<Jogo> listaJogos;
    private Context context;
    private TextView nomeCompeticao, dataHoraPartida, nomeEstadio, nomeClubeA, nomeClubeB, numeroGolA, numeroGolB;

    public JogoAdapter(Context context, List<Jogo> listaJogosRecebido) {
        super(context, 0, listaJogosRecebido);
        this.listaJogos = listaJogosRecebido;
        this.context = context;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.lista_jogos, null);
        //Aqui ocorre a mágica no setTag onde é passado a posição da ListView!
        view.setTag(position);
        if (listaJogos.get(position) != null) {

            this.nomeCompeticao = (TextView) view.findViewById(R.id.nomeCompeticao);
            this.nomeCompeticao.setText(listaJogos.get(position).getCategoria());

            this.nomeEstadio = (TextView) view.findViewById(R.id.nomeEstadio);
            this.nomeEstadio.setText(listaJogos.get(position).getLocal());

            this.dataHoraPartida = (TextView) view.findViewById(R.id.dataHoraPartida);
            this.dataHoraPartida.setText(listaJogos.get(position).getHora());

            this.nomeClubeA = (TextView) view.findViewById(R.id.nomeClubeA);
            this.nomeClubeA.setText(listaJogos.get(position).getNomeClubeA());

            this.nomeClubeB = (TextView) view.findViewById(R.id.nomeClubeB);
            this.nomeClubeB.setText(listaJogos.get(position).getNomeClubeB());

            this.numeroGolA = (TextView) view.findViewById(R.id.numeroGolA);
            this.numeroGolA.setText("0");

            this.numeroGolB = (TextView) view.findViewById(R.id.numeroGolB);
            this.numeroGolB.setText("0");
        }
        return view;
    }
}
