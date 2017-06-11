package com.github.twinsod.notes.ui.detailsNote.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.twinsod.notes.R;
import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.ui.detailsNote.presenter.NotePresenter;

import java.lang.annotation.Retention;

import static com.github.twinsod.notes.AppConst.ADD_NEW_NOTE;
import static com.github.twinsod.notes.AppConst.EDIT_NOTE;
import static com.github.twinsod.notes.AppConst.SHOW_NOTE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener, NoteView {
    private static final String PARAM_TYPE = "extra_type";

    @Retention(SOURCE)
    @IntDef({ADD_NEW_NOTE, EDIT_NOTE})
    @interface DetailMode {
    }

    private EditText mEditView;
    private NotePresenter presenter;
    private int type;
    private NoteModel mNoteModel;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment getInstance(@DetailMode int type) {
        DetailsFragment postFragment = new DetailsFragment();
        Bundle args = new Bundle(1);
        args.putInt(PARAM_TYPE, type);
        postFragment.setArguments(args);
        return postFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(PARAM_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);
        TextView titleView = (TextView) view.findViewById(R.id.title_note);
        mEditView = (EditText) view.findViewById(R.id.edit_view);
        Button okButton = (Button) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);

        switch (type) {
            case ADD_NEW_NOTE:
                titleView.setText(R.string.add_new_note);
                okButton.setText(R.string.save);
                break;
            case EDIT_NOTE:
            case SHOW_NOTE:
                titleView.setText(R.string.edit_note);
                okButton.setText(R.string.save);
                break;
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().loadPost();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:
                if (type == ADD_NEW_NOTE) {
                    presenter.saveNote(mEditView.getText().toString());
                } else {
                    mNoteModel.setDescription(mEditView.getText().toString());
                    presenter.updateNote(mNoteModel);
                }
                break;
        }
    }

    @Override
    public void bindPresenter(NotePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public NotePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showNote(@NonNull NoteModel noteModel) {
        mEditView.setText(noteModel.getDescription());
        mNoteModel = noteModel;
    }

    @Override
    public void onCompleted() {
        //hide keyboard
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        getActivity().onBackPressed();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_details_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_note) {
            presenter.deleteNote(mNoteModel.getId());
        }
        return true;
    }
}
