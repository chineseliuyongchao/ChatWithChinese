package com.example.lab.android.nuc.chat.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.lab.android.nuc.chat.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText account, name, password, ensure_password;
    EditText email, hobby, chat_person, goal;
    Spinner native_language, study_language, level;
    Button finish_button;
    String s1, s2, s3;
    String a,n, p, ep, e, h, c, pi, id, g;
    int l;
    ImageView picture;
    Uri imageuri;
    File path;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        account = findViewById(R.id.user_account);
        name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        ensure_password = findViewById(R.id.ensure_password);
        email = findViewById(R.id.email);
        hobby = findViewById(R.id.hobby);
        chat_person = findViewById(R.id.chat_person);
        native_language = findViewById(R.id.native_language);
        study_language = findViewById(R.id.study_language);
        level = findViewById(R.id.level);
        finish_button = findViewById(R.id.finish_button);
        picture = findViewById(R.id.picture);
        goal = findViewById(R.id.goal);
        final String[] ctype = new String[]{"汉语", "英语", "俄语", "西班牙语", "法语", "柬埔寨语", "捷克语", "匈牙利语"
                , "印度尼西亚语", "哈萨克语", "老挝语", "蒙古语", "波兰语", "塞尔维亚语", "土耳其语", "越南语", "日语", "韩语"};
        final String[] ctype1 = new String[]{"低", "中", "高"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ctype1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        native_language.setAdapter(adapter);
        study_language.setAdapter(adapter);
        level.setAdapter(adapter1);
        native_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s1 = ctype[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        study_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s2 = ctype[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s3 = ctype1[i];
                if (s3.equals("低")) {
                    l = 0;
                } else if (s3.equals("中")) {
                    l = 1;
                } else
                    l = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    PictureSelector.create(RegisterActivity.this)
                            .openGallery( PictureMimeType.ofImage())
                            .selectionMode( PictureConfig.SINGLE)
                            .enablePreviewAudio( true )
                            .openClickSound( true )
                            .previewVideo( true )
                            .previewImage(true)
                            .compress(true)
                            .isCamera(false)
                            .theme(R.style.picture_Sina_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                            .maxSelectNum(9)// 最大图片选择数量
                            .minSelectNum(1)// 最小选择数量
                            .imageSpanCount(4)// 每行显示个数
                            .selectionMode(PictureConfig.SINGLE)//单选
                            .previewImage(true)// 是否可预览图片
                            .previewVideo(true)// 是否可预览视频
                            .enablePreviewAudio(true) // 是否可播放音频
                            .isCamera(true)// 是否显示拍照按钮
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            .enableCrop(false)// 是否裁剪
                            .compress(true)// 是否压缩
                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
                            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                            .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                            .isGif(true)// 是否显示gif图片
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                            .circleDimmedLayer(false)// 是否圆形裁剪
                            .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                            .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                            .openClickSound(true)// 是否开启点击声音
                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                }
            }
        });
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InformationActivity.ACCOUNT = account.getText().toString();
                n = name.getText().toString();
                InformationActivity.NICHENG = n;
                p = password.getText().toString();
                ep = ensure_password.getText().toString();
                e = email.getText().toString();
                InformationActivity.EMAIL = e;
                h = hobby.getText().toString();
                c = chat_person.getText().toString();
                id = account.getText().toString();
                g = goal.getText().toString();
                InformationActivity.MOTHER_LANGUAGE = s1;
                InformationActivity.LEARN_LANGUAGE = s2;
                if (!p.equals(ep)) {
                    Toast.makeText(RegisterActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("test", "test");
                    OkGo.<String>post("http://47.95.7.169:8080/login")
                            .tag(this)
                            .isMultipart(true)
                            .params("name", n)
                            .params("password", p)
                            .params("email", e)
                            .params("nativeLanguage", s1)
                            .params("studyLanguage", s2)
                            .params("level", l)
                            .params("picture", pi)
                            .params("UserID", id)
                            .params("interest", h)
                            .params("toPerson", c)
                            .params("toGoal", g)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Map<String, Object> map1 = JSON.parseObject(response.body(), new TypeReference<Map<String, Object>>() {
                                    });
                                    if (map1.get("status").equals("ok")) {
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else
                                        Toast.makeText(RegisterActivity.this, "注册不成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private void putPicture() {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)
                .putThreshhold(1024 * 1024)
                .connectTimeout(10)
                .useHttps(true)
                .responseTimeout(60)
                .zone(FixedZone.zone0)
                .build();
        UploadManager uploadManager = new UploadManager(config);
        String key = null;
        Auth auth = Auth.create("9gthfNqzOiOOAP3ZrPeDscx-4bF6VY6a6U3r8WJ8", "cTKHAJb4azTcPH1o7-C5JTpaPZ6ymHzC8dYEEQ7i");
        String token = auth.uploadToken("yuban");
        uploadManager.put(path, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (!info.isOK()) {
                            Log.i("qiniu", "Upload faild");
                        } else {
                            Log.i("qiniu", "Upload Success");
                        }
                        try {
                            pi = (String) response.get("key");
//                            pi = response.getString("key");
                            Log.e("picUploadKey:", pi);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }, null);
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT);
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageuri ) );
                        picture.setImageBitmap( bitmap );
                        path = new File( imageuri.getPath() );
//                        putPicture();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat( data );
                    } else {
                        handleImageBeforeKitKat( data );
                    }
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                // 图片选择结果回调
                if (PictureSelector.obtainMultipleResult( data ) != null) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult( data );
                    LocalMedia media = selectList.get( 0 );
                    String path = media.getPath();
                    File file = new File( path );
                    Uri uri = Uri.fromFile( file );
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap( this.getContentResolver(), uri );
                        if (bitmap == null) {
                            picture.setImageResource( R.drawable.ic_add_black_24dp );
                        } else {
                            picture.setImageBitmap( bitmap );
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    picture.setImageResource( R.drawable.ic_add_black_24dp);
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagepath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagepath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagepath = getImagePath(contenturi, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagepath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagepath = uri.getPath();
        }
        disPlayImage(imagepath);
        path = new File(imagepath);
//        putPicture();
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagepath = getImagePath(uri, null);
        disPlayImage(imagepath);
        path = new File(imagepath);
//        putPicture();
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();

        }
        return path;
    }

    private void disPlayImage(String imagepath) {
        if (imagepath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT);
        }
    }

}