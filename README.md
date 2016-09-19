[![GitHub license](https://img.shields.io/github/license/brynjen/eventadapter.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/brynjen/eventadapter.svg?branch=develop)](https://travis-ci.org/brynjen/eventadapter) 
[![codecov.io](http://codecov.io/github/brynjen/eventadapter/coverage.svg?branch=develop)](http://codecov.io/github/brynjen/eventadapter?branch=develop)
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

Note that the project is awaiting response from jCenter and is not available with gradle/maven yet.

Add to project with gradle
    
    compile 'no.nordli:eventadapter:0.9'

Maven:

    <dependency>
      <groupId>no.nordli</groupId>
      <artifactId>eventadapter</artifactId>
      <version>0.9</version>
      <type>pom</type>
    </dependency>

Tutorial:

This readme is being updated today, so stay frosty...

Build Metrics
====================

Working on the metrics with codecov.io and travis-ci.org. Not completely implemented yet.

[![Build Status](https://travis-ci.org/brynjen/eventadapter.svg?branch=develop)](https://travis-ci.org/brynjen/eventadapter) 
[![codecov.io](http://codecov.io/github/brynjen/eventadapter/coverage.svg?branch=develop)](http://codecov.io/github/brynjen/eventadapter?branch=develop)

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
