package com.cc.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MemoEntity {
    Integer id;
    String title;
    String content;
    boolean have_start;
    boolean have_end;
    Timestamp start_time;
    Timestamp end_time;
    Integer owner;
    boolean disable;
    List<Integer> derive;
}
