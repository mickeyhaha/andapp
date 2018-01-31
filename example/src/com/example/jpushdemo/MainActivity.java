package com.example.jpushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.ZkUtils;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;

import org.apache.zookeeper.CreateMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;



public class MainActivity extends InstrumentedActivity implements OnClickListener{

	private Button mInit;
	private Button mSetting;
	private Button mStopPush;
	private Button mResumePush;
	private Button mGetRid;
	private TextView mRegId;
	private EditText msgText;
	
	public static boolean isForeground = false;

	// Serial port device
	public static final String DEVICE = "device";
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();

		registerMessageReceiver();  // used for receive msg

		// init JPush
		initJPush();

		// init serial port
		initSerial();

		// need public IP, not good
		// (new ServerSocket()).start();

        //new ZkThread().start();

		/*
		ZkUtils.getZKClient(zkAddress).subscribeDataChanges(new IZkDataListener() {

            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.print(s);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.print(s);
            }
        });*/
	}

	private void initSerial() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        ArrayList<Device> devices = serialPortFinder.getDevices();

        if(devices == null || devices.size() == 0) {
            Log.i(TAG, "found no device");
            return;
        }

        for (Device d : devices) {
            Log.i(TAG, "find device: " + d.getName() + ":" + d.getRoot() + ":" + d.getFile());
        }

		Device device = devices.get(0); //(Device) getIntent().getSerializableExtra(DEVICE);
		Log.i(TAG, "onCreate: device = " + device);
		if (null == device) {
			Log.i("initSerial", "device is null");
			return;
		}
		SerialPortManager mSerialPortManager = new SerialPortManager();

		// 添加打开串口监听
        boolean openSerialPort = mSerialPortManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
			@Override
			public void onSuccess(File device) {
                Toast.makeText(getApplicationContext(), String.format("串口 [%s] 打开成功", device.getPath()), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(File device, Status status) {
                switch (status) {
                    case NO_READ_WRITE_PERMISSION:
                        showDialog(device.getPath(), "没有读写权限");
                        break;
                    case OPEN_FAIL:
                    default:
                        showDialog(device.getPath(), "串口打开失败");
                        break;
                }
			}
		})
         // 添加数据通信监听
        .setOnSerialPortDataListener(new OnSerialPortDataListener() {
			@Override
			public void onDataReceived(byte[] bytes) {
				Log.i(TAG, "onDataReceived [ byte[] ]: " + Arrays.toString(bytes));
				Log.i(TAG, "onDataReceived [ String ]: " + new String(bytes));
				final byte[] finalBytes = bytes;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showToast(String.format("接收\n%s", new String(finalBytes)));
					}
				});

			}

			@Override
			public void onDataSent(byte[] bytes) {
                Log.i(TAG, "onDataSent [ byte[] ]: " + Arrays.toString(bytes));
                Log.i(TAG, "onDataSent [ String ]: " + new String(bytes));
                final byte[] finalBytes = bytes;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(String.format("发送\n%s", new String(finalBytes)));
                    }
                });

			}
		})
        /* 打开串口
            - 参数1：串口
            - 参数2：波特率
            - 返回：串口打开是否成功
         */
        .openSerialPort(device.getFile(), 115200);


		//发送数据
		byte[] sendContentBytes = "test data".getBytes();
		boolean sendBytes = mSerialPortManager.sendBytes(sendContentBytes);

		//关闭串口
		mSerialPortManager.closeSerialPort();
	}

	private Toast mToast;
	/**
	 * Toast
	 *
	 * @param content content
	 */
	private void showToast(String content) {
		if (null == mToast) {
			mToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
		}
		mToast.setText(content);
		mToast.show();
	}

    /**
     * 显示提示框
     *
     * @param title   title
     * @param message message
     */
    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
	
	private void initView(){
		TextView mImei = (TextView) findViewById(R.id.tv_imei);
		String udid =  ExampleUtil.getImei(getApplicationContext(), "");
        if (null != udid) mImei.setText("IMEI: " + udid);
        
		TextView mAppKey = (TextView) findViewById(R.id.tv_appkey);
		String appKey = ExampleUtil.getAppKey(getApplicationContext());
		if (null == appKey) appKey = "AppKey异常";
		mAppKey.setText("AppKey: " + appKey);

		mRegId = (TextView) findViewById(R.id.tv_regId);
		mRegId.setText("RegId:");

		String packageName =  getPackageName();
		TextView mPackage = (TextView) findViewById(R.id.tv_package);
		mPackage.setText("PackageName: " + packageName);

		String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
		TextView mDeviceId = (TextView) findViewById(R.id.tv_device_id);
		mDeviceId.setText("deviceId:" + deviceId);
		
		String versionName =  ExampleUtil.GetVersion(getApplicationContext());
		TextView mVersion = (TextView) findViewById(R.id.tv_version);
		mVersion.setText("Version: " + versionName);
		
	    mInit = (Button)findViewById(R.id.init);
		mInit.setOnClickListener(this);
		
		mStopPush = (Button)findViewById(R.id.stopPush);
		mStopPush.setOnClickListener(this);
		
		mResumePush = (Button)findViewById(R.id.resumePush);
		mResumePush.setOnClickListener(this);

		mGetRid = (Button) findViewById(R.id.getRegistrationId);
		mGetRid.setOnClickListener(this);

		mSetting = (Button)findViewById(R.id.setting);
		mSetting.setOnClickListener(this);
		
		msgText = (EditText)findViewById(R.id.msg_rec);
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.init:
			initJPush();
			break;
		case R.id.setting:
			Intent intent = new Intent(MainActivity.this, PushSetActivity.class);
			startActivity(intent);
			break;
		case R.id.stopPush:
			JPushInterface.stopPush(getApplicationContext());
			break;
		case R.id.resumePush:
			JPushInterface.resumePush(getApplicationContext());
			break;
		case R.id.getRegistrationId:
			String rid = JPushInterface.getRegistrationID(getApplicationContext());
			if (!rid.isEmpty()) {
				mRegId.setText("RegId:" + rid);
			} else {
				Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
	
	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void initJPush(){
		 JPushInterface.init(getApplicationContext());
	}


	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	

	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
					String messge = intent.getStringExtra(KEY_MESSAGE);
					String extras = intent.getStringExtra(KEY_EXTRAS);
					StringBuilder showMsg = new StringBuilder();
					showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
					if (!ExampleUtil.isEmpty(extras)) {
						showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
					}
					setCostomMsg(showMsg.toString());
				}
			} catch (Exception e){
			}
		}
	}
	
	private void setCostomMsg(String msg){
		 if (null != msgText) {
			 msgText.setText(msg);
			 msgText.setVisibility(android.view.View.VISIBLE);
         }
	}

}

class ZkThread extends Thread {
    @Override
    public void run() {

        String zkAddress = "127.0.0.1:2181";
        ZkUtils.createNode(zkAddress, "/vmreg", "", CreateMode.PERSISTENT);

        System.out.println("zk started");
    }
}