package com.github.twinsod.notes.ui.listNote.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.twinsod.notes.R;
import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.ui.customView.DataTextView;
import com.github.twinsod.notes.ui.listNote.view.NoteInteractors;

import java.util.List;


public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private List<NoteModel> mListNotes;
    private final NoteInteractors mListener;

    public NotesRecyclerViewAdapter(List<NoteModel> items, NoteInteractors listener) {
        mListNotes = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItemModel = mListNotes.get(position);
        holder.mDataView.setLongDate(mListNotes.get(position).getDate());
        holder.mContentView.setText(mListNotes.get(position).getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onNoteClick(holder.mItemModel.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListNotes.size();
    }

    public void update(List<NoteModel> list) {
        mListNotes = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final DataTextView mDataView;
        final TextView mContentView;
        NoteModel mItemModel;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mDataView = (DataTextView) view.findViewById(R.id.data);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
