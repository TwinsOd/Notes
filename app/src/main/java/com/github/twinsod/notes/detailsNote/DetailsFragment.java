package com.github.twinsod.notes.detailsNote;


import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.twinsod.notes.LabApp;
import com.github.twinsod.notes.R;
import com.github.twinsod.notes.data.callback.NoteCallback;
import com.github.twinsod.notes.listNote.NoteModel;

import java.lang.annotation.Retention;

import static com.github.twinsod.notes.AppConst.ADD_NEW_NOTE;
import static com.github.twinsod.notes.AppConst.EDIT_NOTE;
import static com.github.twinsod.notes.AppConst.SHOW_NOTE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    private EditText mEditView;

    @Retention(SOURCE)
    @IntDef({ADD_NEW_NOTE, EDIT_NOTE})
    @interface DetailMode {
    }

    private static final String PARAM_TYPE = "extra_type";
    private static final String PARAM_ID = "extra_id";
    private int type;
    private long id;
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

    public static DetailsFragment getInstance(@DetailMode int type, long id) {
        DetailsFragment postFragment = new DetailsFragment();
        Bundle args = new Bundle(2);
        args.putInt(PARAM_TYPE, type);
        args.putLong(PARAM_ID, id);
        postFragment.setArguments(args);
        return postFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(PARAM_TYPE);
            if (bundle.containsKey(PARAM_ID)) {
                id = bundle.getLong(PARAM_ID);
                if (id != 0)
                    getNoteById(id);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.title_note);
        mEditView = (EditText) view.findViewById(R.id.edit_view);
        mEditView.setOnClickListener(this);
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
                if (!TextUtils.isEmpty(mNoteModel.getDescription()))
                    mEditView.setText(mNoteModel.getDescription());
                okButton.setText(R.string.save);
                break;
        }
        return view;
    }

    private void getNoteById(long id) {
        LabApp.getNodesRepository().getNote(id, new NoteCallback<NoteModel>() {
            @Override
            public void onEmit(NoteModel data) {
                mNoteModel = data;
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getContext(), "get Note error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:
                if (type == ADD_NEW_NOTE)
                    saveNewNote();
                else
                    updateNote();
                break;
            case R.id.edit_view:
                mEditView.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                mEditView.setClickable(true);
                mEditView.setFocusable(true);
                break;
        }
    }

    private void saveNewNote() {
        LabApp.getNodesRepository().saveNote(mEditView.getText().toString(), new NoteCallback() {
            @Override
            public void onEmit(Object data) {

            }

            @Override
            public void onCompleted() {
                Toast.makeText(getContext(), "Saved id successful", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getContext(), "error saved new Note", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateNote() {
        LabApp.getNodesRepository().updateNote(id, mEditView.getText().toString(), new NoteCallback() {
            @Override
            public void onEmit(Object data) {

            }

            @Override
            public void onCompleted() {
                Toast.makeText(getContext(), "Saved id successful", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
