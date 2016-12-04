package com.tsbridge.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsbridge.R;
import com.tsbridge.utils.Utils;

public class SendFragment extends Fragment implements View.OnClickListener {
    private final int SELECT_PIC_KITKAT = 121;
    private final int SELECT_PIC = 122;

    private Context mContext = null;
	private String mClassName = null;

    private String sendName = null;
    private String sendContent = null;
    private Uri imageUri = null;
    public static String picturePath = null;

    View mRootView = null;
    private TextView mSendName = null;
    private TextView mSendContent = null;
    private ImageView mSendImage = null;
    private Button mSendImageSel = null;
    private Button mSendImageDel = null;
    private Button mSendBtn = null;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.send_fragment, container, false);

        return mRootView;
    }
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initParams();
        initViews();
    }

    private void initParams() {
        mContext = getActivity();
    }

    private void initViews() {
        mSendName = (TextView) mRootView.findViewById(R.id.send_name);
        mSendContent = (TextView) mRootView.findViewById(R.id.send_content);
        mSendImage = (ImageView) mRootView.findViewById(R.id.send_image);
        mSendImageSel = (Button) mRootView.findViewById(R.id.send_image_sel);
        mSendImageDel = (Button) mRootView.findViewById(R.id.send_image_del);
        mSendBtn = (Button) mRootView.findViewById(R.id.send_btn);

        mSendImageSel.setOnClickListener(this);
        mSendImageDel.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.send_image_sel:
                SelectImage();
                break;
            case R.id.send_image_del:
                ClearImage();
                break;
            case R.id.send_btn:
                SendBulletin();
                break;
            default:
                break;
        }
    }

    private void SelectImage() {
        //选择图片
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
            startActivityForResult(intent, SELECT_PIC_KITKAT);
        }else{
            startActivityForResult(intent, SELECT_PIC);
        }
        Utils.ShowLog("Select send image");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_PIC_KITKAT:
                case SELECT_PIC:
                    imageUri = intent.getData();
                    ImagePreview(imageUri);
                    break;
                default:
                    break;
            }
        } else {
            Utils.ShowToast(mContext, "没有选择图片!");
        }
    }

    private void ImagePreview(Uri uri) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = mContext.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);
        cursor.close();
        Utils.ShowLog(picturePath);
        mSendImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }

    private void ClearImage() {
        if(imageUri != null) {
            imageUri = null;
            mSendImage.setImageResource(R.drawable.bulletin_image);
        }
    }

    private void SendBulletin() {
        //发布公告
        sendName = mSendName.getText().toString();
        sendContent = mSendContent.getText().toString();
        if(!TextUtils.isEmpty(sendName) && (!TextUtils.isEmpty(sendContent) || imageUri != null)) {

        } else {
            Utils.ShowToast(mContext, "请输入名称和内容(或图片)");
        }

    }

    @Override
	public void onDestroyView() {
    	super.onDestroyView();
    }
}