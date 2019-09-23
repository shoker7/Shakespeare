package com.example.shoker.myfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TitlesFragment extends ListFragment {

  boolean dualPane;
  int curCheckPosition=0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Shakespeare.Titles));
        View detailsFrame = getActivity().findViewById(R.id.details);
        dualPane = detailsFrame!=null&&detailsFrame.getVisibility()==View.VISIBLE;

        if(savedInstanceState!=null){
            curCheckPosition =savedInstanceState.getInt("curChoice",0);
        }

        if(dualPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(curCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice",curCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private void showDetails(int index) {
        curCheckPosition =index;

        if(dualPane){
            getListView().setItemChecked(index,true);
              DetailsFragment details = (DetailsFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.details);
              if(details==null||details.getShownIndex()!=index){
                  details = DetailsFragment.newInstance(index);
                  FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                  if(index==0){
                      ft.add(R.id.details,details);
                  }else
                      ft.replace(R.id.details,details);
                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                  ft.commit();
              }
        }else{
            Intent intent = new Intent(getActivity(),DetailsActivity.class);
            intent.putExtra("index",index);
            startActivity(intent);
        }
    }
}
