// AidlInterface.aidl
package com.john.newtest;

// Declare any non-default types here with import statements

interface AidlInterface {
    List<Person> getList();

    void addPerson(in Person p);
}
