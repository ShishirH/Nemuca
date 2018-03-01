package com.it.acumen.acumeneventslocal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private String result;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private ArrayList<ViewGroup> parentList = new ArrayList<ViewGroup>();
    private HashMap<String, Game> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String,Game> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.result = null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Game childItem = (Game) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        final TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

//        String gameName = childItem.getGameId()+"\t"+childItem.getPlayerList().get(0).getPlayerName();
        String x = "";
        for(int i=0;i<childItem.getPlayerList().size();i++)
        {
            x+=childItem.getPlayerList().get(i).getPlayerName();
        }
        txtListChild.setText(x);
        Button addPlayer = (Button) convertView.findViewById(R.id.AddPlayer);


        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                txtListChild.setText(newPlayer.getText().toString());
//                String result = txtListChild.getText().toString();
//                result = result + "\n" + newPlayer.getText().toString();
//
//                txtListChild.setText(result);
                Intent i = new Intent(_context, EnterNameActivity.class);
                Activity origin = (Activity) _context;
                origin.startActivityForResult(new Intent(_context, EnterNameActivity.class), 1);

                if(result != null) {

                    childItem.getPlayerList().add(new PlayerDetails("45465",result));



                }

            }
        });

        Button update = (Button) convertView.findViewById(R.id.update);
        final View cView = convertView;

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText R1 = (EditText) cView.findViewById(R.id.roundone);
                EditText R2 = (EditText) cView.findViewById(R.id.roundtwo);
                EditText R3 = (EditText) cView.findViewById(R.id.roundthree);

//                R2.setInputType(InputType.TYPE_CLASS_NUMBER);
//                R3.setInputType(InputType.TYPE_CLASS_NUMBER);
                String scoreOne = R1.getText().toString();
                String scoreTwo = R2.getText().toString();
                String scoreThree = R3.getText().toString();

                if(R1.isEnabled() && scoreOne.compareTo("") != 0) {
                    if(scoreOne.compareTo("0") != 0) {
                        childItem.setRound1Score(Integer.parseInt(scoreOne));
                        R1.setEnabled(false);
                    }
                }

                else if(R2.isEnabled() && scoreTwo.compareTo("") != 0)
                {
                    if(scoreTwo.compareTo("0") != 0) {
                        childItem.setRound2Score(Integer.parseInt(scoreTwo));
                        R2.setEnabled(false);
                    }
                }

                else if(R3.isEnabled() && scoreThree.compareTo("") != 0)
                {
                    if(scoreThree.compareTo("0") != 0) {
                        childItem.setRound3Score(Integer.parseInt(scoreThree));
                        R3.setEnabled(false);
                    }
                }

            }
        });

        final int gPosition = groupPosition;
        Button submit = (Button) convertView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View a = null;
                View group = getGroupView(gPosition, true, a, parentList.get(gPosition));
                TextView header = (TextView) group.findViewById(R.id.lblListHeader);
                header.setText("Hello");
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Game childItem = (Game) getChild(groupPosition,0);
        int a =0;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);

        }
        String headerTitle = childItem.getGameId()+"\t"+childItem.getPlayerList().get(0).getPlayerName();
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        parentList.add(groupPosition, parent);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                 result=data.getStringExtra("result");
                 Log.e(TAG,"result : "+result);
                View convertView = null;
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) this._context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.list_item, null);
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}