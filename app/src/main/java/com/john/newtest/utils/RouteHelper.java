package com.john.newtest.utils;

import com.john.jrouter.IRegister;
import com.john.jrouter.Register;
import com.john.newtest.Main3Activity;
import com.john.newtest.Main4Activity;
import com.john.newtest.MainActivity;

/**
 * Created by JohnZh on 2020/7/9
 *
 * <p></p>
 */
public class RouteHelper implements IRegister {
    @Override
    public void onRegister() {
        Register.register("/app/main", MainActivity.class);
        Register.register("/app/main3", Main3Activity.class);
        Register.register("/app/main4", Main4Activity.class);
    }
}
