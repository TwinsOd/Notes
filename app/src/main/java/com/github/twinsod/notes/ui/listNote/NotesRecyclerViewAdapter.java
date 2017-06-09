package com.github.twinsod.notes.ui.listNote;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.twinsod.notes.R;
import com.github.twinsod.notes.ui.customView.DataTextView;

import java.util.List;


public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private final List<NoteModel> mListNotes;
    private final ListNotesFragment.OnListFragmentInteractionListener mListener;

    public NotesRecyclerViewAdapter(List<NoteModel> items, ListNotesFragment.OnListFragmentInteractionListener listener) {
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
                    mListener.onShowNote(holder.mItemModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListNotes.size();
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
