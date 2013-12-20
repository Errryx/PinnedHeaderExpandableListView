/*
 * Copyright (C) 2013 Miao Gao
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ek.pinnedheader.expandablelistview;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * ExpandableListView with pinned header using a fake ImageView. <b>NOTE</b> that as the ImageView is fake,
 * the ScrollBar maybe partially invisible.
 * 
 * @author Miao Gao (Eryx.kao@gmail.com)
 * 
 */
public class PHExpandableListView extends FrameLayout {

    private int mGroupPosition = -1;
    private Rect mOutRect = new Rect();
    private Bitmap mBitmap = null;
    private Map<Integer, Bitmap> mViewCache = null;
    private ImageView mPinnedHeader = null;
    private ExpandableListView mExpandableListView = null;
    private OnScrollListener mOnScrollListener = null;

    public PHExpandableListView(Context context) {
        super(context);
    }

    public PHExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PHExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Set an existing ExpandableListView to get pinned header style List
     * 
     * @param expandableListView
     */
    public void setExpandableListView(ExpandableListView expandableListView) {
        mExpandableListView = expandableListView;

        if (mExpandableListView != null) updateView();
    }

    private void updateView() {
        removeAllViews();

        mViewCache = new HashMap<Integer, Bitmap>();

        if (mPinnedHeader == null) {
            mPinnedHeader = new ImageView(getContext());
            mPinnedHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

        if (mOnScrollListener == null) {
            mOnScrollListener = new OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    long position = mExpandableListView.getExpandableListPosition(firstVisibleItem);
                    if (position == ExpandableListView.PACKED_POSITION_VALUE_NULL) return;
                    int groupPosition = ExpandableListView.getPackedPositionGroup(position);

                    if (groupPosition != mGroupPosition) {
                        // If a new group comes to be the first visible one, save the drawing cache
                        // and setup the ImageView with this group view
                        View v = mExpandableListView.findViewWithTag(groupPosition);
                        if (v != null) {
                            v.setDrawingCacheEnabled(true);
                            v.buildDrawingCache(false);
                            if (v.getDrawingCache() == null) return;
                            mBitmap = mViewCache.get(groupPosition);
                            if (mBitmap != null && !mBitmap.isRecycled()) mBitmap.recycle();

                            mViewCache.put(groupPosition, Bitmap.createBitmap(v.getDrawingCache()));
                            v.destroyDrawingCache();
                            v.setDrawingCacheEnabled(false);
                        }

                        // If v is null, it appears that the group view is not visible and so (maybe) we cannot retrieve the drawing cache
                        // so here we use a cached bitmap
                        if (mViewCache.get(groupPosition) == null || mViewCache.get(groupPosition).isRecycled()) {
                            mPinnedHeader.setVisibility(View.GONE);
                        } else {
                            mPinnedHeader.setImageBitmap(mViewCache.get(groupPosition));
                            mPinnedHeader.setVisibility(View.VISIBLE);
                            mPinnedHeader.scrollTo(0, 0);
                            mGroupPosition = groupPosition;
                        }
                    } else {
                        // If next group is about to be the first one, scroll the previous pinned header according
                        // to the next group view's position
                        long positionNext = mExpandableListView.getExpandableListPosition(firstVisibleItem + 1);
                        if (positionNext == ExpandableListView.PACKED_POSITION_VALUE_NULL) return;
                        int groupPositionNext = ExpandableListView.getPackedPositionGroup(positionNext);

                        if (groupPositionNext != mGroupPosition) {
                            View v = mExpandableListView.findViewWithTag(groupPositionNext);
                            if (v == null) return;
                            v.getDrawingRect(mOutRect);
                            offsetDescendantRectToMyCoords(v, mOutRect);
                            if (mOutRect.top < mPinnedHeader.getHeight())
                                mPinnedHeader.scrollTo(0, mPinnedHeader.getHeight() - mOutRect.top);
                        }
                    }
                }
            };
        }

        mExpandableListView.setOnScrollListener(mOnScrollListener);

        addView(mExpandableListView, 0);
        addView(mPinnedHeader, 1);
    }

    /**
     * Recycle the bitmaps cache for the pinned headers 
     */
    public void destroy() {
        if (mBitmap != null && !mBitmap.isRecycled()) mBitmap = null;
        if (mViewCache == null) return;

        Bitmap b = null;
        for (int i = 0; i < mViewCache.size(); i++) {
            b = mViewCache.get(i);
            if (b != null && !b.isRecycled()) {
                b.recycle();
                b = null;
            }

        }
    }

}
