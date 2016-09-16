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
note: Does not work atm, attempting to get jCenter/Maven Central to work with BinTray. 
compile 'no.nordli:eventadapter:0.9'

Maven version might come later when milestones are reached.

Tutorial:

This readme is being updated today, so stay frosty...

License
--------

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
