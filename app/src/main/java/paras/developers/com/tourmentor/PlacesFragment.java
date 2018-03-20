package paras.developers.com.tourmentor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    DatabaseReference ref;
    ExpandableListAdapter adapter;
    DatabaseReference placeref;
    HashMap<String, HashMap<String,String>> maps =new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vv =  inflater.inflate(R.layout.fragment_places, container, false);
        expListView = (ExpandableListView)vv.findViewById(R.id.sublist);
    ref = FirebaseDatabase.getInstance().getReference().child("Places");
    listDataHeader = new ArrayList<String>();
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
           for(DataSnapshot post:dataSnapshot.getChildren()){
               for(DataSnapshot nestedPost : post.getChildren()){

                   ListClass details = nestedPost.getValue(ListClass.class);

              listDataHeader.add(nestedPost.getValue().toString());
              HashMap<String,String> hash = new HashMap<>();
               }
           }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
        paras.developers.com.tourmentor.ExpandableListView listitem = new paras.developers.com.tourmentor.ExpandableListView(getActivity(),listDataHeader,maps);
       expListView.setAdapter(listitem);
        return vv;
    }

}
