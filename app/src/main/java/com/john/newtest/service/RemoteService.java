package com.john.newtest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.john.common.socket.Codec;
import com.john.common.socket.DefaultCodec;
import com.john.newtest.socketest.Req;
import com.john.newtest.aidl.SocketToggle;
import com.john.newtest.socketest.MarketSubscriber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

/**
 * Modified by john on 2020/4/17
 * <p>
 * 假设它是另外一个 app 的 Service
 */
public class RemoteService extends Service {

    private static final String TAG = "Temp";
    // List<People> mList = new ArrayList<>();

    private ServerSocket serverSocket;
    private Thread serviceThread = new ServiceThread();
    private ExecutorService executorService = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE,
            60, TimeUnit.SECONDS,
            new SynchronousQueue<>());

    private final class DataAcceptTask implements Runnable {

        private Timer timer;
        private Codec codec;

        public DataAcceptTask(Socket socket) {
            codec = new DefaultCodec(socket);
        }

        @Override
        public void run() {
            while (true) {
                Log.d("Temp", "Service wait/read message"); // todo remove later
                try {
                    String read = codec.read(); // block
                    if (!TextUtils.isEmpty(read)) {
                        Log.d("Temp", "Service read " + read); // todo remove later
                        Req req = Req.getReq(read);
                        if (req != null) {
                            if (req.getCode() == MarketSubscriber.SUB) {
                                scheduleFakeData(req);
                            } else if (req.getCode() == MarketSubscriber.UNSUB) {
                                stopSendFakeData();
                            }
                        }
                    } else {
                        Log.d("Temp", "Service read empty"); // todo remove later
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    stopSendFakeData();
                    break;
                }
            }
        }

        private void stopSendFakeData() {
            if (timer != null) {
                timer.cancel();
            }
        }

        private void scheduleFakeData(final Req req) {
            Log.d("Temp", "scheduleFakeData: "); // todo remove later
            timer = new Timer();
            Random random = new Random();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        int r = random.nextInt(100);
                        Req resp = new Req(req.getCode(), String.valueOf(r));
                        codec.write(resp.toJSON());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000);
        }
    }

    private final class ServiceThread extends Thread {

        @Override
        public void run() {
            try {
                Log.d("Temp", "serverThread run"); // todo remove later
                serverSocket = new ServerSocket(4444);
                while (true) {
                    Socket accept = serverSocket.accept();
                    executorService.execute(new DataAcceptTask(accept));
                }
            } catch (SocketException e) { // by serverSocket.close
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }

    private IBinder binder = new SocketToggle.Stub() {

        @Override
        public void quit() throws RemoteException {
            try {
                serverSocket.close();
                executorService.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

//  private IBinder mIBinder = new PeopleAidl.Stub() {
//        @Override
//        public List<People> getList() throws RemoteException {
//            return mList;
//        }
//
//        @Override
//        public void addPerson(People p) throws RemoteException {
//            mList.add(p);
//        }
//    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        serviceThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
