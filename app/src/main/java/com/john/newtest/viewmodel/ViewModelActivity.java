package com.john.newtest.viewmodel;

import android.os.Bundle;

import com.john.newtest.R;
import com.john.newtest.model.User;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelActivity extends AppCompatActivity {

    private MyViewModel mMyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);

        mMyViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(MyViewModel.class);

        mMyViewModel.loadUser();

        mMyViewModel.mLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // update ui
            }
        });
    }

    static class MyViewModel extends ViewModel {

        private MutableLiveData<List<User>> mLiveData;

        void loadUser() {
            WebService.fetchUser(users -> {
                mLiveData.setValue(users);
            });
        }
    }

    static class WebService {
        public static void fetchUser(Callback callback) {

        }
    }

    public interface Callback {
        void onFetchUser(List<User> users);
    }
}
