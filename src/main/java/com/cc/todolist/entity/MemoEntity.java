package com.cc.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MemoEntity {
    Integer id;
    String title;
    String content;
    String start;
    String end;
    Integer owner;
    boolean disable;
    String derive;
    List<Integer> derive_list;
}
