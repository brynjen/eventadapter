[![GitHub license](https://img.shields.io/github/license/brynjen/eventadapter.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/brynjen/eventadapter.svg?branch=master)](https://travis-ci.org/brynjen/eventadapter) 
[![codecov.io](http://codecov.io/github/brynjen/eventadapter/coverage.svg?branch=master)](http://codecov.io/github/brynjen/eventadapter?branch=develop)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.brynjen/eventadapter.svg)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22eventadapter%22)

# Event Adapter
================================

An event-based recyclerAdapter using an eventBus to notify it of any changes in the data source, letting the adapter show RecyclerViews nice animations (or you could add your own animations).
The library is meant to work with Lollipop (api lvl 21) or above, but is planned for a Kitkat later.

EventAdapter:

* Simplifies updating of adapters
* Can handle any kind of object
* Animates default animations when data is updated but can be expanded upon
* Minimizes data storage and handling

How to use:

Add to project with gradle

    compile 'no.nordli:eventadapter:1.0'

Note the library is in jCenter, so use that as a repository.

Maven: Not in maven central yet, but it will be soon.

    <dependency>
      <groupId>no.nordli</groupId>
      <artifactId>eventadapter</artifactId>
      <version>1.0</version>
      <type>pom</type>
    </dependency>

Tutorial:

To use this library, make a container class for whichever object you want to display like so

    class GitHubber implements Event {
        private String email;

        public GitHubber(String email) {
            this.email = email;
        }
        
        // Note I'm ignoring checking for null and the like, but any class should always assert correct values
        public void setEmail(String newEmail) {
            if (!newEmail.isEqualTo(email)) {
                this.email = newEmail;
                notifyObjectChanged();
            }
        }

        @Override
        public void notifyObjectChanged() {
            EventBus.getInstance().notifyObjectChanged(GitHubber.class.getName(), this);
        }
    }

Alternatively, the class can be made like this:

    class GitHubber extends EventObject {
        private String email;
        
        public GitHubber(String email, String topic) {
            super(topic);
            this.email = email;
        }

        public void setEmail(String newEmail) {
            if (!newEmail.isEqualTo(email)) {
                this.email = newEmail;
                notifyObjectChanged();
            }
        }
    }

Note: The "[Event](https://github.com/brynjen/eventadapter/blob/master/eventadapter/src/main/java/no/nordli/eventadapter/Event.java)" interface is to give you the option to create a singular place to do the actual updating, while the important thing is to
notify to the EventBus about the object changed with the correct topic (in this example I use the class name, but it can be any string).

Next is to implement an [EventBasedList](https://github.com/brynjen/eventadapter/blob/master/eventadapter/src/main/java/no/nordli/eventadapter/EventBasedList.java) and an [EventBasedRecyclerAdapter](https://github.com/brynjen/eventadapter/blob/master/eventadapter/src/main/java/no/nordli/eventadapter/EventBasedRecyclerAdapter.java). Note that they do not have to be in the same class.
You can have the data in a manager class and so long as you access it correctly from the adapter there is no problem.

    class MyAdapter extends EventBasedRecyclerAdapter<GitHubber, MyAdapter.ViewHolder> {
        private EventBasedList<GitHubber> data = new EventBasedList<>(GitHubber.class.getName());
        
        @Override
        public List<GitHubber> getData() {
            return data;
        }
        
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.myrow, parent, false));
        }
        
        @Override
        public void onItemClicked(int row, GitHubber obj, MyAdapter.ViewHolder viewHolder) {
            // Do something
        }
        
        @Override
        public boolean onItemLongClicked(int row, GitHubber obj, MyAdapter.ViewHolder viewHolder) {
            return true; // If false, click event is not stopped causing a regular click event to fire
        }
        
        class ViewHolder extends EventBasedViewHolder<GitHubber> {
            private TextView emailTextView;
            ViewHolder(View itemView) {
                super(itemView);
                emailTextView = (TextView)itemView.findViewById(R.id.my_text_view);
            }
            
            @Override
            protected void bind(GitHubber objectModel) {
                // Bind the data from the objectModel to the components
            }
        }
    }

License
====================

    Copyright 2016 Brynje Nordli

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
