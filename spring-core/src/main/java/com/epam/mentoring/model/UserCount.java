package com.epam.mentoring.model;

public class UserCount {
    private int id;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserCount{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
