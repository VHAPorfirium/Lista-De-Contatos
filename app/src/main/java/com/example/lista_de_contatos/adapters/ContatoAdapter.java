package com.example.lista_de_contatos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.models.Contato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> implements Filterable {

    private List<Contato> fullList;       // Lista original
    private List<Contato> filteredList;   // Lista filtrada ou em exibição
    private OnItemClickListener listener; // Callback para cliques

    // Construtor que ordena a lista ao criar o adapter
    public ContatoAdapter(List<Contato> contatos) {
        this.fullList = new ArrayList<>(contatos);
        sortAlphabetically(this.fullList);
        this.filteredList = new ArrayList<>(fullList);
    }

    // Infla o layout do item e retorna um ViewHolder
    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contato, parent, false);
        return new ContatoViewHolder(view);
    }

    // Vincula os dados do contato à view na posição indicada
    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        holder.bind(filteredList.get(position));
    }

    // Retorna quantos itens estão na lista filtrada
    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    // Ordena a lista de contatos em ordem alfabética pelo nome
    private void sortAlphabetically(List<Contato> contatos) {
        Collections.sort(contatos, new Comparator<Contato>() {
            @Override
            public int compare(Contato c1, Contato c2) {
                return c1.getNome().compareToIgnoreCase(c2.getNome());
            }
        });
    }

    // Atualiza todo o conteúdo do adapter com uma nova lista
    public void updateList(List<Contato> novaLista) {
        this.fullList = new ArrayList<>(novaLista);
        sortAlphabetically(this.fullList);
        this.filteredList.clear();
        this.filteredList.addAll(fullList);
        notifyDataSetChanged();
    }

    // Retorna o filtro usado para busca no nome do contato
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Contato> filtered = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filtered.addAll(fullList);
                } else {
                    String pattern = constraint.toString().toLowerCase().trim();
                    for (Contato c : fullList) {
                        if (c.getNome().toLowerCase().contains(pattern)) {
                            filtered.add(c);
                        }
                    }
                }
                sortAlphabetically(filtered);
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                //noinspection unchecked
                filteredList.addAll((List<Contato>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    // Configura o listener que será chamado ao clicar em um item
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // ViewHolder que gerencia as views de cada item de contato
    class ContatoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFoto, imgFavorito;
        TextView txtNome, txtTelefone;

        ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto     = itemView.findViewById(R.id.imgFotoItemContato);
            imgFavorito = itemView.findViewById(R.id.imgFavoritoItem);
            txtNome     = itemView.findViewById(R.id.txtNomeItem);
            txtTelefone = itemView.findViewById(R.id.txtTelefoneItem);

            // Dispara callback quando o item for clicado
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(filteredList.get(pos));
                    }
                }
            });
        }

        // Preenche as views com os dados do contato
        void bind(Contato contato) {
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());
            if (contato.getFoto() != null && !contato.getFoto().isEmpty()) {
                // carregar foto via URI ou biblioteca
            } else {
                imgFoto.setImageResource(R.drawable.ic_contact_placeholder);
            }
            imgFavorito.setVisibility(contato.isContatoFavorito() ? View.VISIBLE : View.GONE);
        }
    }

    // Interface para callback de clique em um contato
    public interface OnItemClickListener {
        void onItemClick(Contato contato);
    }
}
