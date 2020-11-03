package com.tectro.mobileapp4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tectro.mobileapp4.Adapter.GameTableAdapter;
import com.tectro.mobileapp4.ConnectionModule.IConnection;
import com.tectro.mobileapp4.GameModel.GameModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class GameTableFragment extends Fragment implements IConnection {

    private int numberOfColumns = 4;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameTableFragment newInstance(String param1, String param2) {
        GameTableFragment fragment = new GameTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GameTableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    TextView rrr;

    RecyclerView GameMatrixHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_table, container, false);

        GameModel GModel = GameModel.CreateInstance(2);

        GameMatrixHolder = (RecyclerView)view.findViewById(R.id.TableHolder);
        GameMatrixHolder.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        GameMatrixHolder.setAdapter(new GameTableAdapter(getActivity(),GModel.getTableFigures(),GModel.getDHelper(),(Integer r, Integer s)->
        {

        }));

/*
*
*
        IConnection connection  = (IConnection) getSupportFragmentManager().findFragmentById(R.id.GameTableFragment);
        connection.Update("setAdapter", new GameTableAdapter(this,gameModel.getMaxPlayers(),gameModel.getTableFigures(),gameModel.getDHelper(),(Integer e,Integer s)->
        {
            //todo do some
        }));
* */
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);



    }

    @Override
    public void Update(String Key, Object value) {
        switch (Key)
        {
            case "setAdapter":
                {
                    if(value instanceof GameTableAdapter) {
                        GameMatrixHolder.setAdapter((GameTableAdapter) value);
                       // GameMatrixHolder.notify();
                    }
                }break;
        }
    }

}