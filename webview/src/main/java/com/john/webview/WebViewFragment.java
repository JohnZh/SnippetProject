package com.john.webview;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.john.webview.databinding.FragmentWebViewBinding;
import com.john.webview.jscode.JsCode;
import com.john.webview.main.Command;
import com.john.webview.main.HandleWebService;

import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WebViewFragment extends Fragment {

    private FragmentWebViewBinding binding;
    private CountDownLatch countDownLatch;
    private RemoteServiceProxy remoteServiceProxy;

    private void connectMainService() {
        if (getContext() != null) {
            Context context = getContext();
            countDownLatch = new CountDownLatch(1);
            Intent intent = new Intent(context, HandleWebService.class);
            context.bindService(intent, connection, Service.BIND_AUTO_CREATE);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            countDownLatch.countDown();
            remoteServiceProxy = RemoteServiceProxy.Stub.asInterface(service);
            try {
                remoteServiceProxy.asBinder().linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        remoteServiceProxy.asBinder().unlinkToDeath(this, 0);
                        remoteServiceProxy = null;
                        connectMainService();
                    }
                }, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            CommandDispatcher.get().setRemoteServiceProxy(remoteServiceProxy);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWebViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.webView.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(() -> connectMainService()).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        CommandDispatcher.get().setResponseListener(new CommandDispatcher.ResponseListener() {
            @Override
            public void onResponse(int code, String cmd, String response) {
                if (code == Command.SUCCESS) {
                    callbackToWebView(cmd, response);
                }
            }
        });
    }

    private void callbackToWebView(String cmd, String response) {
        String jsCallbackMethod = JsCode.getJsCallbackMethod(cmd, response);
        binding.webView.post(() -> {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                binding.webView.loadUrl(jsCallbackMethod);
            } else {
                binding.webView.evaluateJavascript(jsCallbackMethod, null);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getContext() != null) {
            getContext().unbindService(connection);
        }
        binding = null;
    }

    public SecureWebView getSecureWebView() {
        return binding.webView;
    }
}
