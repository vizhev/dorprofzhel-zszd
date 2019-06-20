package pro.dprof.dorprofzhelzszd.ui.dialogs;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;

public final class DocumentPagesDialog extends DialogFragment {

    public static final String TAG = "DocumentPagesDialog";

    @BindView(R.id.tv_document_pages_page) TextView mTvPage;
    @BindView(R.id.sb_document_pages) AppCompatSeekBar mSeekBar;

    private int mAllPages = 0;
    private int mCurrentPage = 0;
    private final StringBuilder mStringBuffer = new StringBuilder();
    private OnDocumentPagesDialogListener mOnDocumentPagesDialogListener;

    public interface OnDocumentPagesDialogListener {

        void onSetPage(int page);

    }

    public DocumentPagesDialog setPages(int allPages, int currentPage) {
        mAllPages = allPages;
        mCurrentPage = currentPage;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mAllPages = savedInstanceState.getInt("allPages");
            mCurrentPage = savedInstanceState.getInt("currentPage");
        }
        final DialogInterface.OnClickListener onClickListener = createDialogClickListener();
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.document_viewer_action_pages)
                .setView(R.layout.element_document_pages)
                .setNegativeButton(R.string.document_viewer_dialog_btn_negative, onClickListener)
                .setPositiveButton(R.string.document_viewer_dialog_btn_positive, onClickListener)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(this, Objects.requireNonNull(getDialog()));
        mStringBuffer
                .append(mCurrentPage)
                .append(" ")
                .append(getResources().getString(R.string.document_viewer_dialog_page_of_pages))
                .append(" ")
                .append(mAllPages);
        mTvPage.setText(mStringBuffer);
        mSeekBar.setMax(mAllPages - 1);
        mSeekBar.setProgress(mCurrentPage - 1);
        mSeekBar.setOnSeekBarChangeListener(createSeekBarListener());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnDocumentPagesDialogListener = (OnDocumentPagesDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("allPages", mAllPages);
        outState.putInt("currentPage", mCurrentPage);
    }

    private DialogInterface.OnClickListener createDialogClickListener() {
        return (dialogInterface, i) -> {
            switch (i) {
                case AlertDialog.BUTTON_NEGATIVE:
                    Objects.requireNonNull(getDialog()).cancel();
                    break;
                case AlertDialog.BUTTON_POSITIVE:
                    mOnDocumentPagesDialogListener.onSetPage(mCurrentPage);
                    break;
            }
        };
    }

    private SeekBar.OnSeekBarChangeListener createSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, int progress, boolean b) {
                mCurrentPage = progress + 1;
                mStringBuffer
                        .delete(0, mStringBuffer.length())
                        .append(progress + 1)
                        .append(" ")
                        .append(getResources().getString(R.string.document_viewer_dialog_page_of_pages))
                        .append(" ")
                        .append(mAllPages);
                mTvPage.setText(mStringBuffer);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }
}
