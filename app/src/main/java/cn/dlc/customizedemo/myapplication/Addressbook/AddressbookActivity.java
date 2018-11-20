package cn.dlc.customizedemo.myapplication.Addressbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.dlc.dlcpermissionlibrary.PermissionCallback;
import com.dlc.dlcpermissionlibrary.PermissionString;
import com.dlc.dlcpermissionlibrary.PermissionUtil;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.Addressbook.adapter.TongxunluAdapter;
import cn.dlc.customizedemo.myapplication.Addressbook.bean.Contact;
import cn.dlc.customizedemo.myapplication.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengzimin  on  2018/11/14.
 * interface by
 */
public class AddressbookActivity extends BaseCommonActivity {
    @BindView(R.id.sidebar)
    SideBar mSidebar;
    @BindView(R.id.auto_rv)
    AutoRecyclerView mAutoRv;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private TongxunluAdapter mTongxunluAdapter;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.getInstance(getApplicationContext()).onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_address_book;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecycler();
        requestPermission();
    }

    /**
     * 获取权限
     */
    private void requestPermission() {
        PermissionUtil mPermissionUtil = PermissionUtil.getInstance(getApplicationContext());
        mPermissionUtil.requestPermissons(this, new String[]{
                PermissionString.READ_CONTACTS}, new PermissionCallback.OneMethodCallback() {
            @Override
            public void onPermissionCallback(String[] successPermissions, String[] deniedPermissions) {
                if (deniedPermissions.length == 0) {
                    initData();
                } else {
                    showOneToast(PermissionString.getInstance().getPermissionsTip(deniedPermissions));
                }

            }
        });
    }

    private void initRecycler() {
        mAutoRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTongxunluAdapter = new TongxunluAdapter(this);
        mAutoRv.setAdapter(mTongxunluAdapter);

    }

    /**
     * 获取手机通讯录
     */
    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<Contact>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Contact>> emitter) throws Exception {
                List<Contact> contacts = ContactsManager.getContacts(AddressbookActivity.this);
                emitter.onNext(contacts == null ? new ArrayList<Contact>() : contacts);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())//发送操作线程
                .observeOn(AndroidSchedulers.mainThread())//接收操作线程
                .subscribe(new Observer<List<Contact>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Contact> contacts) {
                        mTongxunluAdapter.setNewData(contacts);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //        Observable.create(new ObservableOnSubscribe<List<Contact>>() {
        //            @Override
        //            public void subscribe(ObservableEmitter<List<Contact>> emitter) throws Exception {
        //                List<Contact> contacts = ContactsManager.getContacts(AddressbookActivity.this);
        //                emitter.onNext(contacts == null ? new ArrayList<Contact>() : contacts);
        //                emitter.onComplete();
        //            }
        //        }).subscribeOn(Schedulers.io()).map(new Function<List<Contact>, String>() {//map用于切换函数
        //            @Override
        //            public String apply(List<Contact> contacts) throws Exception {
        //
        //                StringBuilder stringBuilder = new StringBuilder();
        //
        //                for (int i = 0; i < contacts.size(); i++) {
        //
        //                    Contact contact = contacts.get(i);
        //                    List<String> contactPhoneNum = contact.getContactPhoneNum();
        //                    if (contactPhoneNum != null) {
        //
        //                        for (int i1 = 0; i1 < contactPhoneNum.size(); i1++) {
        //                            if (TextUtils.isEmpty(contactPhoneNum.get(i1))) {
        //                                continue;
        //                            }
        //                            stringBuilder.append(contactPhoneNum.get(i1));
        //                            if (i != contacts.size() - 1 || i1 != contactPhoneNum.size() - 1) {
        //                                stringBuilder.append(',');
        //                            }
        //                        }
        //                    }
        //
        //                }
        //                return stringBuilder.toString();
        //            }
        //        }).subscribe(new Observer<String>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //                // 无视
        //            }
        //
        //            @Override
        //            public void onNext(String s) {
        //                // TODO: 2018/4/11
        //                Log.e("dlc", s);
        //                //                "13432391496,17665357249,13929907242,15899661573",
        //
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //                // 无视
        //
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //                // 无视
        //            }
        //        });
    }
}
