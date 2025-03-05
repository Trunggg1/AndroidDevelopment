// FlashcardAdapter.java
package com.example.myeduapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Flashcard> flashcardList;
    private Context context;

    public FlashcardAdapter(Context context, List<Flashcard> flashcardList) {
        this.context = context;
        this.flashcardList = flashcardList;
    }

    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcardList.get(position);
        holder.flashcardImage.setImageResource(flashcard.getImageResId());
        holder.flashcardText.setText(flashcard.getWord());

        // Click to flip the flashcard
        holder.itemView.setOnClickListener(v -> {
            if (holder.flashcardImage.getVisibility() == View.VISIBLE) {
                holder.flashcardImage.setVisibility(View.GONE);
                holder.flashcardText.setVisibility(View.VISIBLE);
            } else {
                holder.flashcardImage.setVisibility(View.VISIBLE);
                holder.flashcardText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flashcardList.size();
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        ImageView flashcardImage;
        TextView flashcardText;

        public FlashcardViewHolder(View itemView) {
            super(itemView);
            flashcardImage = itemView.findViewById(R.id.flashcardImage);
            flashcardText = itemView.findViewById(R.id.flashcardText);
        }
    }
}
