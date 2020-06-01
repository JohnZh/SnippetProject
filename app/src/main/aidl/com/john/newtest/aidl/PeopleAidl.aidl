package com.john.newtest.aidl;

import com.john.newtest.aidl.People;

interface PeopleAidl {
    List<People> getList();

    void addPerson(in People p);
}
