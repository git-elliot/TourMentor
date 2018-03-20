package paras.developers.com.tourmentor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sunny Parihar on 20-03-2018.
 */

public class ExpandableListView extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String,HashMap<String,String>> _listDataChild;
DatabaseReference ref;
    public ExpandableListView(Context context, List<String> listDataHeader,
                              HashMap<String, HashMap<String,String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return _listDataChild.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            ref = FirebaseDatabase.getInstance().getReference().child("Places");
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.listplace, null);
        }

       TextView lblListHeader = (TextView) view
                .findViewById(R.id.placesinlistplace);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
 ListClass lists = (ListClass)getChild(i,i1);
       if(view ==null){
           LayoutInflater inflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(R.layout.places,null);
           TextView texts = view.findViewById(R.id.placetext);
           RatingBar bar = view.findViewById(R.id.ratingbar);
           TextView textst = view.findViewById(R.id.textFare);
           texts.setText(lists.getName());
           int rate = Integer.parseInt(lists.getRating());
           bar.setRating(rate);
           textst.setText(lists.getTicket());
           return view;
       }
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
