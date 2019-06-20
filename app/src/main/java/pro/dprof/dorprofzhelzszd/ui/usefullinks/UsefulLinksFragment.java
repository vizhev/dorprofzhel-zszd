package pro.dprof.dorprofzhelzszd.ui.usefullinks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;

public final class UsefulLinksFragment extends BaseFragment implements UsefulLinksMvpView {

    public final static String TAG = "UsefulLinksFragment";

    @BindView(R.id.iv_useful_links) ImageView mIvLogo;
    @BindView(R.id.btn_useful_link_dorprofzhel_site) Button mBtnDorprofzhelSite;
    @BindView(R.id.btn_useful_link_dorprofzhel_vk) Button mBtnDorprofzhelVk;
    @BindView(R.id.btn_useful_link_dorprofzhel_vk_ms) Button mBtnDorprofzhelVkMs;
    @BindView(R.id.btn_useful_link_dorprofzhel_instagram) Button mBtnDorprofzhelInstagram;
    @BindView(R.id.btn_useful_link_dorprofzhel_youtube) Button mBtnDorprofzhelYoutube;
    @BindView(R.id.btn_useful_link_rosprofzhel_site) Button mBtnRosprofzhelSite;
    @BindView(R.id.btn_useful_link_rosprofzhel_vk) Button mBtnRosprofzhelVk;
    @BindView(R.id.btn_useful_link_rosprofzhel_vk_ms) Button mBtnRosprofzhelVkMs;
    @BindView(R.id.btn_useful_link_rosprofzhel_instagram) Button mBtnRosprofzhelInstagram;
    @BindView(R.id.btn_useful_link_rosprofzhel_youtube) Button mBtnRosprofzhelYoutube;
    @BindView(R.id.btn_useful_link_vk_yong_rzd) Button mBtnVkYongRzd;
    @BindView(R.id.btn_useful_link_vk_yong_omsk) Button mBtnVkYongOmsk;

    private UsefulLinksMvpPresenter<UsefulLinksMvpView> mPresenter;
    private Unbinder mUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_useful_links, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        Picasso.get()
                .load(R.drawable.ic_like)
                .resize(500,500)
                .into(mIvLogo);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = getActivityComponent().getUsefulLinksPresenter();
        mPresenter.onAttach(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @OnClick(R.id.btn_useful_link_dorprofzhel_site)
    void onBtnDorprofzhelSiteClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://dprof.pro"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_dorprofzhel_vk)
    void onBtnDorprofzhelVkClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://vk.com/id420313757"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_dorprofzhel_vk_ms)
    void onBtnDorprofzhelVkMsClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://vk.com/ms_dprof_zszd"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_dorprofzhel_instagram)
    void onBtnDorprofzhelInstagramClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.instagram.com/dorprofzhel_zszd"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_dorprofzhel_youtube)
    void onBtnDorprofzhelYoutubeClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/channel/UC4qxrnCR0JFUu7gby1L6PgQ/featured"));
        startActivity(intent);
    }


    @OnClick(R.id.btn_useful_link_rosprofzhel_site)
    void onBtnRosprofzhelSiteClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://rosprofzhel.rzd.ru"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_rosprofzhel_vk)
    void onBtnRosprofzhelVkClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://vk.com/club137996689"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_rosprofzhel_vk_ms)
    void onBtnRosprofzhelVkMsClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://vk.com/ms_rosprofzhel"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_rosprofzhel_instagram)
    void onBtnRosprofzhelVkInstagramClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.instagram.com/rosprofgel_1905"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_rosprofzhel_youtube)
    void onBtnRosprofzhelVkYoutubeClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/channel/UCM_sZeXdZoasLC80onSDT2Q"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_vk_yong_rzd)
    void onBtnYongRzdClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://vk.com/molodez_zszd"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_useful_link_vk_yong_omsk)
    void onBtnYongOmskClick() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://vk.com/club166190653"));
        startActivity(intent);
    }
}
