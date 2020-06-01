package com.john.newtest;

import android.os.Bundle;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private MyViewModel mMyViewModel;

    static class MyViewModel extends ViewModel {

        private MutableLiveData<List<User>> mLiveData;

        void loadUser() {
            WebService.fetchUser(users -> {
                mLiveData.setValue(users);
            });
        }
    }

    static class User {

    }

    static class WebService {

        public static void fetchUser(Callback callback) {

        }
    }

    public interface Callback {
        void onFetchUser(List<User> users);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyViewModel mainViewMode
                = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(MyViewModel.class);

        mainViewMode.loadUser();

        mainViewMode.mLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // update ui
            }
        });
    }
}
