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
    private OnItemClickListener listener; // Interface de callback para cliques

    public ContatoAdapter(List<Contato> contatos) {
        // Ordena a lista por nome antes de atribuir
        this.fullList = new ArrayList<>(contatos);
        sortAlphabetically(this.fullList);

        this.filteredList = new ArrayList<>(fullList);
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contato, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        Contato contato = filteredList.get(position);
        holder.bind(contato);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    /**
     * Ordena a lista de contatos em ordem alfabética pelo nome.
     */
    private void sortAlphabetically(List<Contato> contatos) {
        Collections.sort(contatos, new Comparator<Contato>() {
            @Override
            public int compare(Contato c1, Contato c2) {
                return c1.getNome().compareToIgnoreCase(c2.getNome());
            }
        });
    }

    /**
     * Atualiza a lista de contatos no adapter (por exemplo, quando se adiciona ou edita um contato).
     */
    public void updateList(List<Contato> novaLista) {
        this.fullList = new ArrayList<>(novaLista);
        sortAlphabetically(this.fullList);

        this.filteredList.clear();
        this.filteredList.addAll(fullList);
        notifyDataSetChanged();
    }

    /**
     * Implementação de busca/filtragem de nomes na lista.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Contato> filtered = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // Se não há texto digitado, mostra todos
                    filtered.addAll(fullList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Contato c : fullList) {
                        // Filtra pelo nome (pode adaptar para telefone, email, etc.)
                        if (c.getNome().toLowerCase().contains(filterPattern)) {
                            filtered.add(c);
                        }
                    }
                }

                // Ordena o resultado filtrado
                sortAlphabetically(filtered);

                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<Contato>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    /**
     * Interface de callback para cliques nos itens da lista.
     */
    public interface OnItemClickListener {
        void onItemClick(Contato contato);
    }

    /**
     * Configura o listener para cliques no item.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * ViewHolder interno para o item_contato.xml
     */
    class ContatoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFoto, imgFavorito;
        TextView txtNome, txtTelefone;

        ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgFotoItemContato);
            imgFavorito = itemView.findViewById(R.id.imgFavoritoItem);
            txtNome = itemView.findViewById(R.id.txtNomeItem);
            txtTelefone = itemView.findViewById(R.id.txtTelefoneItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(filteredList.get(position));
                        }
                    }
                }
            });
        }

        void bind(Contato contato) {
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());

            // Exemplo simples para mostrar foto se houver
            // Você pode usar Glide/Picasso para carregar imagens de URL
            if (contato.getFoto() != null && !contato.getFoto().isEmpty()) {
                // Carrega a foto com biblioteca ou setImageURI...
            } else {
                imgFoto.setImageResource(R.drawable.ic_contact_placeholder);
            }

            // Mostra ou esconde o ícone de favorito
            if (contato.isContatoFavorito()) {
                imgFavorito.setVisibility(View.VISIBLE);
            } else {
                imgFavorito.setVisibility(View.GONE);
            }
        }
    }
}
