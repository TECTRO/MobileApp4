package com.tectro.mobileapp4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tectro.mobileapp4.ConnectionModule.ConnectionManager;

import java.util.function.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    //region Manager
    ConnectionManager connectionManager = new ConnectionManager();

    //endregion

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        connectionManager.TryAttach(context,R.id.GameTableFragment);

    }

    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_list, container, false);

        v.findViewById(R.id.tstbtn).setOnClickListener(o ->
        {
            counter++;
            connectionManager.Update("",counter);
        });


        return v;
    }
}
/*
class ConnectionManager implements IConnection {
    private ArrayList<IConnection> connections = new ArrayList<>();

    public void Register(IConnection connection) {
        if (!connections.contains(connection)) connections.add(connection);
    }

    public void Unregister(IConnection connection) {
        if (connections.contains(connection)) connections.remove(connection);
    }

    private void doCommand(Consumer<IConnection> invoke) {
        for (IConnection cntn : connections)
            invoke.accept(cntn);
    }

    @Override
    public void TestFunc(int f) {
        doCommand(ttt->ttt.TestFunc(f));
    }
}*/