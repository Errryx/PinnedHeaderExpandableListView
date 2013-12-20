PinnedHeaderExpandableListView
==============================

Android ExpandableListView with ios style pinned header.

![Screenshots1][1]
![Screenshots2][2]

Usage
==============================

Init view with:

         PHExpandableListView phExpandableListView = new PHExpandableListView(this);
         phExpandableListView.setExpandableListView(expandableListView);

make sure set tag for groupView, like in your adapter:

         @Override
         public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
          // setup the group view and the tag
          convertView.setTag(groupPosition);
         }

And that's it.

TODO
=======
Using view tag to retrieve group views is not really a good implementation, but I didn't come up with other ideas now.

License
=======

    Copyright 2012 Jake Wharton
    Copyright 2011 Patrik Åkerfeldt
    Copyright 2011 Francisco Figueiredo Jr.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
[1]: https://github.com/Errryx/PinnedHeaderExpandableListView/blob/master/sample/2013-12-20-16-17-50.png?raw=true
[2]: https://github.com/Errryx/PinnedHeaderExpandableListView/blob/master/sample/2013-12-20-16-18-09.png?raw=true
