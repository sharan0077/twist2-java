package com.thoughtworks.twist2;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<String> headers;
    private final List<List<String>> rows;

    public Table(List<String> headers) {
        this.headers = headers;
        rows = new ArrayList<List<String>>();
    }

    public void addRow(List<String> row) {
        rows.add(row);
    }
}
