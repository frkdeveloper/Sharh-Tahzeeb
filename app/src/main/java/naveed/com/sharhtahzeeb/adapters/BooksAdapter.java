package naveed.com.sharhtahzeeb.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import naveed.com.sharhtahzeeb.R;
import naveed.com.sharhtahzeeb.model.BookModel;


/**
 * Naveed Ahmad
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.AnimeViewHolder> {
    private List<BookModel> items;

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;

        public AnimeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.image_id);
            nombre = (TextView) v.findViewById(R.id.bookName);
            visitas = (TextView) v.findViewById(R.id.volume);
        }
    }

    public BooksAdapter(List<BookModel> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.anime_card, viewGroup, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImage_id());
        viewHolder.nombre.setText(items.get(i).getBookName());
        viewHolder.visitas.setText(items.get(i).getVolume());
    }
}
