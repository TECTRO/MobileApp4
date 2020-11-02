package com.tectro.mobileapp4.ConnectionModule;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.tectro.mobileapp4.R;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConnectionManager implements IConnection {
    private ArrayList<IConnection> connections = new ArrayList<>();

    public void Register(IConnection connection) {
        if (!connections.contains(connection)) connections.add(connection);
    }

    public void Unregister(IConnection connection) {
        if (connections.contains(connection)) connections.remove(connection);
    }

    private void doCommand(Consumer<IConnection> invoke) {
        for (IConnection cntn : connections)
            if (cntn != null)
                invoke.accept(cntn);
    }

    @Override
    public void Update(String Key, Object value) {
        doCommand(ttt -> ttt.Update(Key, value));
    }

    public boolean TryAttach(Context context, @IdRes int fragmentID) {

        try {
            if (context instanceof AppCompatActivity)
                Register((IConnection) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(fragmentID));
            else if (context instanceof Activity)
                Register((IConnection) ((Activity) context).getFragmentManager().findFragmentById(fragmentID));

            return true;
        } catch (Exception ignored) {
            return false;
        }

    }
}