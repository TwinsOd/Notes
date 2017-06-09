package com.github.twinsod.notes.ui.listNote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.twinsod.notes.LabApp;
import com.github.twinsod.notes.R;
import com.github.twinsod.notes.data.callback.NoteCallback;

import java.util.ArrayList;
import java.util.List;


public class ListNotesFragment extends Fragment {

    private List<NoteModel> mNodeList = new ArrayList<>();
    private OnListFragmentInteractionListener mListener;

    public ListNotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LabApp.getNodesRepository().getNotes(new NoteCallback<List<NoteModel>>() {
            @Override
            public void onEmit(List<NoteModel> data) {
                mNodeList = data;
            }

            @Override
            public void onCompleted() {
                Log.i("***", "onCompleted: NoteCallback<List<NoteModel>>");
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getContext(), "getNotes error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notesmodel_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mNodeList.clear();

        NotesRecyclerViewAdapter mAdapter = new NotesRecyclerViewAdapter(mNodeList, mListener);
        recyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.fab_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddNewNote();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        void onShowNote(NoteModel item);

        void onAddNewNote();
    }
}
