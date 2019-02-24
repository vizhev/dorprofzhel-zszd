package pro.dprof.dorprofzhelzszd.ui.publiсinspectors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewActivity;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class PublicInspectorsFragment extends BaseFragment {

    public final static String TAG = "PublicInspectorsFragment";

    @BindView(R.id.iv_public_inspectors_logo) ImageView mIvLogo;
    @BindView(R.id.btn_driving_safety_act) Button mBtnAct;
    @BindView(R.id.btn_driving_safety_checklist) Button mBtnChecklist;
    @BindView(R.id.btn_driving_safety_provision) Button mBtnProvision;
    @BindView(R.id.btn_driving_safety_toolkit) Button mBtnToolkit;

    private Unbinder mUnbinder;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_public_inspectors, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        final View.OnClickListener onClickListener = createListener();
        mBtnAct.setOnClickListener(onClickListener);
        mBtnChecklist.setOnClickListener(onClickListener);
        mBtnProvision.setOnClickListener(onClickListener);
        mBtnToolkit.setOnClickListener(onClickListener);
        Picasso.get()
                .load(R.drawable.ic_public_inspectors_logo)
                .into(mIvLogo);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private View.OnClickListener createListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btn_driving_safety_act:
                        intent = new Intent(getActivity(), DocumentViewActivity.class);
                        intent.putExtra(Constants.INTENT_TAG_ASSET_NAME, "act.pdf");
                        intent.putExtra(Constants.INTENT_TAG_ACTIVITY_TITLE, getResources().getString(R.string.public_inspectors_act_activity_title));
                        break;
                    case R.id.btn_driving_safety_checklist:
                        intent = new Intent(getActivity(), DocumentViewActivity.class);
                        intent.putExtra(Constants.INTENT_TAG_ASSET_NAME, "checklist.pdf");
                        intent.putExtra(Constants.INTENT_TAG_ACTIVITY_TITLE, getResources().getString(R.string.public_inspectors_checklist_activity_title));
                        break;
                    case R.id.btn_driving_safety_provision:
                        intent = new Intent(getActivity(), DocumentViewActivity.class);
                        intent.putExtra(Constants.INTENT_TAG_ASSET_NAME, "provision.pdf");
                        intent.putExtra(Constants.INTENT_TAG_ACTIVITY_TITLE, getResources().getString(R.string.public_inspectors_provision_activity_title));
                        break;
                    case R.id.btn_driving_safety_toolkit:
                        intent = new Intent(getActivity(), DocumentViewActivity.class);
                        intent.putExtra(Constants.INTENT_TAG_ASSET_NAME, "toolkit.pdf");
                        intent.putExtra(Constants.INTENT_TAG_ACTIVITY_TITLE, getResources().getString(R.string.public_inspectors_toolkit_activity_title));
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        };
    }
}
