/*
 * Copyright (C) 2013 Miao Gao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ek.pinnedheader.expandablelistview.example;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ek.pinnedheader.expandablelistview.PHExpandableListView;

public class MainActivity extends Activity {

    String[] stringsA = new String[] {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10"};
    String[] stringsB = new String[] {"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10"};
    String[] stringsC = new String[] {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10"};
    String[] stringsD = new String[] {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10"};
    String[] stringsE = new String[] {"E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10"};
    String[] stringsF = new String[] {"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"};
    String[] stringsG = new String[] {"G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10"};
    
    String[][] list = new String[][]{stringsA, stringsB, stringsC, stringsD, stringsE, stringsF, stringsG}; 
    
    PHExpandableListView listView  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new PHExpandableListView(this);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ExpandableListView expandableListView = new ExpandableListView(this);
        expandableListView.setAdapter(adapter);
        listView.setExpandableListView(expandableListView);

        setContentView(listView);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listView != null) listView.destroy();
    }

    private ExpandableListAdapter adapter = new ExpandableListAdapter() {

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(MainActivity.this); 
                convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
                convertView.setBackgroundColor(Color.BLACK);
                convertView.setPadding(10, 10, 10, 10);
                ((TextView) convertView).setTextColor(Color.WHITE);
            }
            
            ((TextView) convertView).setText(String.valueOf(groupPosition));
            
            // NOTE THAT always set the tag with groupPosition, so the groupView can be retrieved. 
            convertView.setTag(groupPosition);
            return convertView;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return list.length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return list[groupPosition];
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return groupId;
        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return childId;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list[groupPosition].length;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(MainActivity.this); 
                convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
                convertView.setBackgroundColor(Color.WHITE);
                convertView.setPadding(10, 10, 10, 10);
                ((TextView) convertView).setTextColor(Color.BLACK);
            }
            
            ((TextView) convertView).setText(list[groupPosition][childPosition]);
            return convertView;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            int sum = list.length;
            for (int i = 0; i < groupPosition; i++)
                sum += list[i].length;
            
            sum += childPosition;
            return sum;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return list[groupPosition][childPosition];
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }
    };

}
